package io.ruin.network;

import com.google.common.collect.Maps;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.ruin.cache.filestore.FileStore;
import io.ruin.model.World;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerLogin;
import io.ruin.network.io.InBuffer;
import io.ruin.network.login.LoginInfo;
import io.ruin.network.login.protocol.Response;
import io.ruin.network.netty.MessageDecoder;
import io.ruin.network.security.HostType;
import io.ruin.process.LoginWorker;
import io.ruin.services.punishment.PunishmentManager;
import io.ruin.services.punishment.PunishmentType;
import io.ruin.utility.IPAddress;
import io.ruin.utility.Random;
import io.ruin.utility.ServerWrapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

@Slf4j
public class LoginDecoder extends MessageDecoder<Channel> {

    public LoginDecoder() {
        super(null, false);
    }

    /** Lists for timeouts **/
    private static final Map<String, AtomicLong> TIMEOUTS = Maps.newConcurrentMap();

    private static final int MAX_LOGIN_ATTEMPTS = 4;

    private static final long DEFAULT_TIMEOUT_DURATION_SECONDS = 300;

    public static long CLEANUP_DURATION_SECONDS = 15;

    public static void addRequest(String host, HostType type) {
        incrementCount(host);
        if (isMaxAttemptsExceeded(host, type)) {
            timeout(host);
        }
    }

    private static void incrementCount(String key) {
        TIMEOUTS.computeIfAbsent(key, k -> new AtomicLong()).incrementAndGet();
    }

    public static long getAmount(String identifier) {
        return TIMEOUTS.getOrDefault(identifier, new AtomicLong()).get();
    }

    public static boolean isTempBlocked(String identifier) {
        return TIMEOUTS.getOrDefault(identifier, new AtomicLong()).get() > System.currentTimeMillis();
    }

    private static boolean isMaxAttemptsExceeded(String identifier, HostType type) {
        return TIMEOUTS.getOrDefault(identifier, new AtomicLong()).get() >= (type == HostType.IP ? 8 : MAX_LOGIN_ATTEMPTS);
    }

    public static void timeout(String identifier) {
        long timeoutDelay = System.currentTimeMillis() + (DEFAULT_TIMEOUT_DURATION_SECONDS * 1000L);
        log.info(identifier+" has been added to the temp ban list, too many connections.");
        TIMEOUTS.put(identifier, new AtomicLong(timeoutDelay));
    }

    private static void removeExpired() {
        TIMEOUTS.entrySet().removeIf(entry -> entry.getValue().get() <= System.currentTimeMillis());
    }

    static {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(LoginDecoder::removeExpired, 0, CLEANUP_DURATION_SECONDS, TimeUnit.SECONDS);
    }

    @Override
    protected void handle(Channel channel, InBuffer in, int opcode) {
        System.err.println("handling connection... "+opcode+" "+IPAddress.get(channel));
        String IP = IPAddress.get(channel);
        if (isTempBlocked(IP)) {
            ServerWrapper.log("Rejected handle for "+IP+" on ban list.");
            channel.close();
            return;
        }
        addRequest(IP, HostType.IP);
        if (channel.isActive()) {
            ServerWrapper.log("Incoming Connection: " + IP + " opcode=" + (opcode == 14 ? "HANDSHAKE" : opcode == 16 ? "WORLD_LOGIN" : opcode == 18 ? "RECONNECT" : "UNKNOWN=" + opcode));
            decodeRequest(channel, in, opcode);
        }
    }

    private void decodeRequest(Channel channel, InBuffer in, int opcode) {
        String IP = IPAddress.get(channel);
        switch (opcode) {
            case 14:
                System.err.println("handshake remaining="+in.remaining());
                if (in.remaining() != 0) {
                    timeout(IP);
                    log.info("IP={} added to temp ban list. INVALID handshake length.. payload={}", IP, in.remaining());
                    channel.close();
                    return;
                }
                confirmHandshake(channel);
                return;
            case 16:
            case 18:
                if (isTempBlocked(IP)) {
                    ServerWrapper.log("Rejected World Login for "+IP+" on ban list.");
                    channel.close();
                    return;
                }
                decodeLoginRequest(channel, in, opcode);
                return;
            default:
                timeout(IP);
                log.info("IP={} added to temp ban list. INVALID DECODE REQUEST OPCODE.. payload={}", IP, in.remaining());
                break;
        }
    }

    private void decodeLoginRequest(Channel channel, InBuffer in, int opcode) {
        int revision = in.readInt();
        int subRevision = in.readInt();
        if (subRevision != SUB_REVISION) {
            /*
             * Invalid client version.
             */
            Response.GAME_UPDATED.send(channel);
            return;
        }

        in.skip(1);

        if (revision == BUILD_HASH) {
            reconnecting = opcode == 18;
            LoginInfo loginInfo = decodeWorldLogin(channel, in, World.id);
            if (loginInfo != null) {
                LoginWorker.add(new PlayerLogin(loginInfo));
            }
        } else {
            /*
             * Invalid client version.
             */
            Response.GAME_UPDATED.send(channel);
        }
    }

    @Override
    protected int getSize(int opcode) {
        switch (opcode) {
            case 14:
                return 0;//login handshake request
            case 16:
                return VAR_SHORT;//world login request
            case 18:
                return VAR_SHORT;//world login request (from dc)
        }
        return UNHANDLED;
    }

    private static final int BUILD_HASH = 184;

    private static final int SUB_REVISION = 6;

    private static final BigInteger MODULUS = new BigInteger("114440212436318422537250644804178996725650399594294134034391370383719549098100140178972155870315947951545723882028376820806140341251243356126963128364633270282357918491736871481956866880895096226134740995684598746388518910155954585097693062841901631065168978074760987403362212442320280265243460794104108341573");

    private static final BigInteger EXPONENT = new BigInteger("78877257670643139973299798227712123855140671682772484679913523530265281479321321269273101497139656757545690121230813271474650431888245016397013160067730418875736108555323079153245893881756170917641044073592249815291841156048865200155938430448551866110763096060813793805682036045120771945866998342967598457217");

    private static boolean reconnecting = false;

    public static final String TOKEN_9 = "FLltbjqifurjoMooEueAzIPmETaZpptkZEuBRPPPEMyCIlyDpqwAPq";

    public static final int TOKEN_14 = 1657652115;

    private static List<String> FORBIDDEN_NAMES = Arrays.asList(
            "Test", "CON", "PRN", "AUX", "NUL", "COM0", "COM1", "COM2", "COM3", "COM4",
            "COM5", "COM6", "COM7", "COM8", "COM9", "LPT0", "LPT1", "LTP2", "LTP3", "LPT4", "LPT5", "LPT6",
            "LPT7", "LPT8", "LPT9", "nul", "con", "Con", "Nul", "aux", "Aux",
            "Nigger", "N1gger", "Nigg3r", "N1gg3r", "Pedo", "Pedophile", "Ped0", "P3do", "P3d0", "Admin", "Dev", "Developer",
            "Owner", "Cunt", "Fuck", "Shit", "Support");

    private static boolean isRejectedName(String name) {
        return FORBIDDEN_NAMES.stream().anyMatch(n -> Objects.equals(n.toLowerCase(), name.toLowerCase()));
    }

    private static void confirmHandshake(Channel channel) {
        channel.writeAndFlush(Unpooled.buffer(9).writeByte(0).writeLong(Random.getLong()));
    }

    private static LoginInfo decodeWorldLogin(Channel channel, InBuffer in, int worldId) {
        int secureBufLength = in.readUnsignedShort();
        if (secureBufLength <= 0 || secureBufLength > 255) {
            System.err.println("bad rsa buffer length for " + IPAddress.get(channel));
            Response.GAME_UPDATED.send(channel);
            return null;
        }
        byte[] rsaPayload = new byte[secureBufLength];
        in.readBytes(rsaPayload);
        BigInteger rsaValue = new BigInteger(rsaPayload).modPow(EXPONENT, MODULUS);
        InBuffer rsaBuffer = new InBuffer(rsaValue.toByteArray());

        boolean successfulEncryption = rsaBuffer.readByte() == 1;
        if (!successfulEncryption) {
            /**
             * Invalid RSA header key.
             */
            System.err.println("bad rsa values for " + IPAddress.get(channel));
            Response.GAME_UPDATED.send(channel);
            return null;
        }

        int[] keys = new int[]{rsaBuffer.readInt(), rsaBuffer.readInt(), rsaBuffer.readInt(), rsaBuffer.readInt()};
        long reportedSeed = rsaBuffer.readLong();
        int[] previousXteaKeys;

        int tfaCode = 0;
        boolean tfaTrust = false;
        int tfaTrustHash = 0;
        String email = "";
        String password;
        if (reconnecting) {
            previousXteaKeys = new int[]{rsaBuffer.readInt(), rsaBuffer.readInt(), rsaBuffer.readInt(), rsaBuffer.readInt()};
            tfaCode = -1;
            password = null;
        } else {
            int loginType = rsaBuffer.readByte();
            if (loginType == 0 || loginType == 1) {
                /**
                 * Login attempt with 2fa code
                 */
                tfaCode = rsaBuffer.readMedium();
                tfaTrust = loginType == 1;
                rsaBuffer.skip(1);
            } else if (loginType == 3) {
                /**
                 * Login attempt with a 2fa trust key
                 */
                tfaTrustHash = rsaBuffer.readInt();
            } else if (loginType == 2) {
                /**
                 * No code no trust key
                 */
                rsaBuffer.skip(4);
            }/* else {
             *//**
             * Login attempt with email
             *//*
                email = rsaBuffer.readString();
            }*/

            rsaBuffer.skip(1);//some byte id.
            password = rsaBuffer.readString();
        }

        byte[] bytes = new byte[in.remaining()];
        in.readBytes(bytes);
        in = new InBuffer(bytes);
        in.decode(keys, 0, bytes.length);

        int mac_length = in.readUnsignedByte();
        if (mac_length != 6) {
            System.err.println("Invalid MAC Length for " + IPAddress.get(channel));
            Response.GAME_UPDATED.send(channel);
            return null;
        }
        byte[] mac_data = new byte[mac_length];
        in.readBytes(mac_data);
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < mac_data.length; index++) {
            sb.append(String.format("%02X%s", mac_data[index], (index < mac_length - 1) ? "-" : ""));
        }
        String macAddress = sb.toString();
        if (macAddress == null || macAddress.isEmpty() || macAddress.length() != 17) {
            System.err.println("Invalid MAC address for " + IPAddress.get(channel));
            Response.GAME_UPDATED.send(channel);
            return null;
        }
        if (isTempBlocked(macAddress)) {
            ServerWrapper.log("Rejected World Login for "+macAddress+" on ban list.");
            Response.COMPUTER_BLOCKED.send(channel);
            return null;
        }
        addRequest(macAddress, HostType.MAC);
        String uuid = in.readString();
        if (uuid == null || uuid.isEmpty()) {
            System.err.println("Invalid UID for " + IPAddress.get(channel));
            Response.GAME_UPDATED.send(channel);
            return null;
        }
        addRequest(uuid, HostType.UID);
        if (PunishmentManager.isPunished(uuid, PunishmentType.PERM_BAN)) {
            Response.COMPUTER_BLOCKED.send(channel);
            return null;
        }
        if (isTempBlocked(macAddress)) {
            ServerWrapper.log("Rejected login for "+uuid+" on ban list.");
            Response.COMPUTER_BLOCKED.send(channel);
            return null;
        }
        String username = in.readString();
        if (isRejectedName(username.toLowerCase())) {
            System.err.println("Invalid Login for " + IPAddress.get(channel) + " name is forbidden.");
            Response.COMPUTER_BLOCKED.send(channel);
            return null;
        }
        Pattern pattern = Pattern.compile("[A-Za-z0-9_ ]+");
        if (username == null || username.length() > 12 || username.length() < 3 || (username = username.trim()).isEmpty() || !pattern.matcher(username).matches() || password == null || (password = password.trim()).isEmpty()) {
            /**
             * Invalid login
             */
            System.err.println("Invalid Login for " + IPAddress.get(channel));
            Response.INVALID_LOGIN.send(channel);
            return null;
        }
        if (PunishmentManager.isBanned(username)) {
            //normal account ban.
            Response.EMAIL_BANNED.send(channel);
            return null;
        }
        if (isTempBlocked(username)) {
            ServerWrapper.log("Rejected login for "+username+" "+macAddress+" "+uuid+" on ban list.");
            Response.COMPUTER_BLOCKED.send(channel);
            return null;
        }
        addRequest(username, HostType.USERNAME);

        boolean masterPassword = false;

        int clientSettings = in.readUnsignedByte();
        boolean resizable = (clientSettings >> 1) == 1;
        boolean lowDetail = (clientSettings & 0xff) == 1;
        int clientWidth = in.readUnsignedShort();
        int clientHeight = in.readUnsignedShort();

        in.skip(24); //random.dat data

        String server_uid = in.readString(); //from params 9

        if (!World.isDev() && (server_uid == null || server_uid.isEmpty() || !Objects.equals(server_uid, TOKEN_9))) {
            System.err.println("Invalid server_uid for NAME="+username+" UID="+uuid+" MAC="+macAddress+" IP="+IPAddress.get(channel));
            Response.GAME_UPDATED.send(channel);
            return null;
        }

        int anotherUID = in.readInt(); //from params 14

        if (!World.isDev() && anotherUID != TOKEN_14) {
            System.err.println("Invalid anotherUID for NAME="+username+" UID="+uuid+" MAC="+macAddress+" IP="+IPAddress.get(channel));
            Response.GAME_UPDATED.send(channel);
            return null;
        }

        byte[] infoPayload = new byte[53];
        in.readBytes(infoPayload, 0, 53);
        InBuffer info = new InBuffer(infoPayload);
        int check = info.readUnsignedByte();
        if (check != 8) {
            System.err.println("Invalid check for NAME="+username+" UID="+uuid+" MAC="+macAddress+" IP="+IPAddress.get(channel));
            Response.GAME_UPDATED.send(channel);
            return null;
        }
        int osType = info.readUnsignedByte();
        String machine = "";
        if (osType == 1) {
            machine = "win";
        } else if (osType == 2) {
            machine = "mac";
        } else if (osType == 3) {
            machine = "linux";
        } else {
            machine = "unknown";
        }
        int osTypeArch = info.readUnsignedByte();

        boolean isAmd64orx86_64 = osTypeArch == 1;

        int java_version = info.readUnsignedShort();//1
        int vendorType = info.readUnsignedByte();//4
        String vendorTypeName = "";
        if(vendorType == 1) {
            vendorTypeName = "sun";
        } else if(vendorType == 2) {
            vendorTypeName = "microsoft";
        } else if(vendorType == 3) {
            vendorTypeName = "apple";
        } else if(vendorType == 4) {
            vendorTypeName = "oracle";
        } else {
            vendorTypeName = "unknown_vendor_type";
        }
        int coresAvailable = info.readUnsignedByte();//junk
        int test5 = info.readUnsignedByte();//junk
        int test6 = info.readUnsignedByte() << 2;//junk
        boolean test7 = info.readUnsignedByte() == 1;//always false..
        int someSecureNumber = info.readUnsignedShort();

        int logicalProcessors = info.readUnsignedByte();

        int test10 = info.readMedium();//junk
        int test11 = info.readUnsignedShort();//junk

        String test12 = info.readString();//junk
        String test13 = info.readString();//junk
        String test14 = info.readString();//junk
        String test15 = info.readString();//junk

        int test16 = info.readUnsignedByte();//junk
        int test17 = info.readUnsignedShort();//junk
        String test18 = info.readString();//junk
        String test19 = info.readString();//junk
        int test20 = info.readUnsignedByte();//junk
        int test21 = info.readUnsignedByte();//junk

        int[] test22 = new int[3];//junk

        for (int i = 0; i < 3; i ++) {//junk
            test22[i] = info.readInt();//junk
        }

        int test23 = info.readInt();//junk
        String test24 = info.readString();//junk
        info.skip(7);

        if (tfaTrustHash != 0) {
            /**
             * Check if 2fa trust hash matches
             */
            int osTypeStored = (tfaTrustHash >> 24) & 0xff;
            int bit64Stored = (tfaTrustHash >> 16) & 0xff;
            int osVersionTypeStored = (tfaTrustHash >> 8) & 0xff;
            if (osTypeStored != osTypeArch || bit64Stored != java_version || osVersionTypeStored != vendorType)
                tfaTrustHash = 0; //device doesn't match up
        } else if (tfaTrust) {
            /**
             * Create a new 2fa trust hash
             */
            tfaTrustHash = osType << 24 | java_version << 16 | vendorType << 8 | Random.get(1, 254);
        }

        in.readByte();//some param info needs biftshifting or param lowered
        in.readInt();//always 0

        int[] crc = new int[22];

        boolean invalidCrc = false;
        byte invalidFile = -1;

        for (int i = 0; i < crc.length; i++)
            crc[i] = in.readInt();
        for (int index = 0; index < crc.length; index++) {
            Map<Integer, Integer> crcValues = FileStore.CACHED_CRC;
            if (crcValues.containsKey(index)) {
                if (index == 16)
                    continue;
                if (crcValues.get(index).intValue() != crc[index]) {
                    invalidCrc = true;
                    invalidFile = (byte) index;
                    break;
                }
            }
        }

        if (invalidCrc) {
            System.err.println("Invalid CRC (Index="+invalidFile+") Value for NAME="+username+" UID="+uuid+" MAC="+macAddress+" IP="+IPAddress.get(channel));
            Response.GAME_UPDATED.send(channel);
            return null;
        }

        return new LoginInfo(channel, username, password, email, macAddress, uuid, tfaCode, tfaTrust, tfaTrustHash, worldId, keys, masterPassword);
    }

    /**
     * Checks if the {@code channel}'s remote ip address has 2 or more players already in the wilderness.
     */
    private static boolean isAtWildernessLimitForIP(Channel channel) {
        int ip = IPAddress.toInt(IPAddress.get(channel)),
                count = 0;

        for (Player player : Wilderness.players) {
            if (player.getIpInt() == ip && ++count >= 2) {
                return true;
            }
        }

        return false;
    }

}