package io.ruin.model;

import com.google.common.collect.Maps;
import io.ruin.Server;
import io.ruin.cache.Icon;
import io.ruin.content.activities.event.TimedEventManager;
import io.ruin.model.activities.pvminstances.PVMInstance;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.EntityList;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerFile;
import io.ruin.model.entity.player.clanchat.ClanChat;
import io.ruin.model.map.Position;
import io.ruin.model.map.Region;
import io.ruin.model.map.object.GlobalObjects;
import io.ruin.model.object.owned.OwnedObject;
import io.ruin.model.object.owned.impl.DwarfCannon;
import io.ruin.network.login.protocol.world.WorldType;
import io.ruin.process.event.EventWorker;
import io.ruin.services.database.DatabaseStatement;
import io.ruin.services.database.DatabaseUtils;
import io.ruin.services.referrals.ReferralHandler;
import io.ruin.services.punishment.PunishmentManager;
import io.ruin.utility.Broadcast;
import io.ruin.utility.SecondsTimer;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class World extends EventWorker {

    public static int id;

    public static WorldType type;

    public static String host;

    public static int port;

    public static boolean toggleLogin;

    public static boolean isDev() {
        return type == WorldType.DEV;
    }

    public static boolean isBeta() {
        return type == WorldType.BETA;
    }

    public static boolean isLive() {
        return type == WorldType.LIVE;
    }

    public static boolean isEco() {
        return type == WorldType.LIVE;
    }

    public static final int spawnableOffset = 100000;

    public static final Position HOME = new Position(3090, 3505, 0);
    public static final Position EDGEHOME = new Position(3090, 3505, 0);

    /**
     * The delay for handling pid switching
     **/
    public static final SecondsTimer PID_SHUFFLE_TIMER = new SecondsTimer();

    /**
     * Players
     */
    public static final EntityList<Player> players = new EntityList<>(new Player[1000]);

    public static Stream<Player> getPlayerStream() {
        return StreamSupport.stream(players.spliterator(), false)
                .filter(Objects::nonNull);
    }

    public static Player getPlayer(int index) {
        return players.get(index);
    }

    public static Player getPlayer(String name) {
        for (Player player : players) {
            if (player.name().equalsIgnoreCase(name))
                return player;
        }
        return null;
    }

    public static Player getPlayer(int userId, boolean onlineReq) {
        if (onlineReq) {
            for (Player player : players) {
                if (player != null && player.getUserId() == userId)
                    return player;
            }
        } else {
            for (Player player : players.entityList) {
                if (player != null && player.getUserId() == userId)
                    return player;
            }
        }
        return null;
    }

    public static void sendSupplyChestBroadcast(String message) {
        players.forEach(p -> {
            if (p.broadcastSupplyChest)
                p.sendNotification(message);
        });
    }

    public static void sendGraphics(int id, int height, int delay, Position dest) {
        sendGraphics(id, height, delay, dest.getX(), dest.getY(), dest.getZ());
    }

    public static void sendGraphics(int id, int height, int delay, int x, int y, int z) {
        for (Player p : Region.get(x, y).players)
            p.getPacketSender().sendGraphics(id, height, delay, x, y, z);
    }

    /**
     * Npcs
     */
    public static final EntityList<NPC> npcs = new EntityList<>(new NPC[1000]);

    public static NPC getNpc(int index) {
        return npcs.get(index);
    }

    /**
     * PLAYER SAVERS
     */
    public static boolean doubleXP = false;

    public static boolean doubleDrops;

    public static boolean doubleBm;

    public static boolean doubleSlayer;

    public static boolean doublePest;

    public static boolean raidBoost;

    public static boolean doubleVotes;

    public static int xpMultiplier = 0;

    public static int playerModifier = 0;

    public static int bmMultiplier = 0;

    public static void toggleWeekendExpBoost() {
        doubleXP = !doubleXP;
        Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "The 25% experience boost is now " + (doubleXP ? "enabled" : "disabled") + ".");
    }

    public static void toggleDoubleDrops() {
        doubleDrops = !doubleDrops;
        Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Double rare drop chance has been " + (doubleDrops ? "enabled" : "disabled") + ".");
    }

    public static void toggledoubleBloodMoney() {
        doubleBm = !doubleBm;
        Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Double Blood Money (pvp kills only) has been " + (doubleBm ? "enabled" : "disabled") + ".");
    }

    public static void toggleDoubleSlayer() {
        doubleSlayer = !doubleSlayer;
        Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Double Slayer Points have been " + (doubleSlayer ? "enabled" : "disabled") + ".");
    }

    public static void toggleDoublePest() {
        doublePest = !doublePest;
        Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Double Pest Control Points have been " + (doublePest ? "enabled" : "disabled") + ".");
    }

    public static void toggleDoubleVotes() {
        doubleVotes = !doubleVotes;
        Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Double Vote Points have been " + (doubleVotes ? "enabled" : "disabled") + ".");
    }

    public static void toggleBonusRaidPts() {
        raidBoost = !raidBoost;
        Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "15% Bonus Raid Points have been " + (raidBoost ? "enabled" : "disabled") + ".");
    }

    public static void boostXp(int multiplier) {
        xpMultiplier = multiplier;
        if (xpMultiplier == 1)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Experience is now normal. (x1)");
        else if (xpMultiplier == 2)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Experience is now being doubled! (x2)");
        else if (xpMultiplier == 3)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Experience is now being tripled! (x3)");
        else if (xpMultiplier == 4)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Experience is now being quadrupled! (x4)");
        else
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Experience is now boosted! (x" + multiplier + ")");
    }

    /*
     * Sets the base amount of blood money user can get per kill
     */
    public static void setBaseBloodMoney(int baseBloodMoney) {
        Killer.BASE_BM_REWARD = baseBloodMoney;
    }

    public static void boostBM(int multiplier) {
        bmMultiplier = multiplier;
        if (bmMultiplier == 1)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Blood money drops from player kills are now normal. (x1)");
        else if (bmMultiplier == 2)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Blood money drops from player kills are now being doubled! (x2)");
        else if (bmMultiplier == 3)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Blood money drops from player kills are now being tripled! (x3)");
        else if (bmMultiplier == 4)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Blood money drops from player kills are now being quadrupled! (x4)");
        else
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Blood money drops from player kills are now boosted! (x" + multiplier + ")");
    }

    public static boolean wildernessDeadmanKeyEvent = false;

    public static void toggleDmmKeyEvent() {
        wildernessDeadmanKeyEvent = !wildernessDeadmanKeyEvent;
    }

    public static boolean wildernessKeyEvent = false;

    public static void toggleWildernessKeyEvent() {
        wildernessKeyEvent = !wildernessKeyEvent;
    }

    public static Optional<Player> getPlayerByUid(int userId) {
        return getPlayerStream().filter(plr -> plr.getUserId() == userId).findFirst();
    }

    @Getter
    protected static final Map<String, OwnedObject> ownedObjects = Maps.newConcurrentMap();

    public static void registerOwnedObject(OwnedObject object) {
        ownedObjects.put(object.getOwnerUID() + ":" + object.getIdentifier(), object);
    }

    public static OwnedObject getOwnedObject(Player owner, String identifier) {
        return ownedObjects.get(owner.getUserId() + ":" + identifier);
    }

    public static void deregisterOwnedObject(OwnedObject object) {
        ownedObjects.remove(object.getOwnerUID() + ":" + object.getIdentifier());
    }

    public static void addCannonReclaim(int userId) {
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO lost_cannons (user_id) VALUES (?)");
            statement.setInt(1, userId);
            statement.executeQuery();
        });
    }

    public static void doCannonReclaim(int userId, Consumer<Boolean> consumer) {
        Server.gameDb.execute(new DatabaseStatement() {

            private boolean result;

            @Override
            public void execute(Connection connection) throws SQLException {

                PreparedStatement statement = null;
                ResultSet rs = null;
                try {
                    statement = connection.prepareStatement("SELECT count(user_id) FROM lost_cannons WHERE user_id = ?");
                    statement.setInt(1, userId);
                    rs = statement.executeQuery();
                    while (rs.next()) {
                        result = Integer.parseInt(rs.getString(1)) > 0;
                    }
                    Server.worker.execute(() -> consumer.accept(result));
                } finally {
                    DatabaseUtils.close(statement, rs);
                }

            }
        });
    }

    public static void removeCannonReclaim(int userId) {
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM lost_cannons WHERE user_id = ?");
            statement.setInt(1, userId);
            statement.execute();
        });
    }

    /**
     * Updating
     */
    public static boolean updating = false;

    public static boolean update(int minutes) {
        if (minutes <= 0) {
            updating = false;
            for (Player player : players)
                player.getPacketSender().sendSystemUpdate(0);
            System.out.println("System Update Cancelled");
            return true;
        }
        if (updating)
            return false;
        updating = true;
        System.out.println("System Update: " + minutes + " minutes");
        for (Player player : players)
            player.getPacketSender().sendSystemUpdate(minutes * 60);
        startEvent(e -> {
            int ticks = minutes * 100;
            while (updating) {
                if (--ticks <= 0 && removeBots() && removePlayers())
                    return;
                e.delay(1);
            }
        });
        return true;
    }

    public static void saveAll() {
        ClanChat.save();
        PunishmentManager.save();
        ReferralHandler.save();
        for (Player p : World.players)
            PlayerFile.save(p, -1);
        System.err.println("Saved all important server data.");
    }

    public static boolean removePlayers() {
        int pCount = players.count();
        if (pCount > 0) {
            System.out.println("Attempting to remove " + pCount + " players...");
            saveAll();
            for (Player player : players) {
                if (World.getOwnedObject(player, DwarfCannon.IDENTIFIER) != null) {
                    addCannonReclaim(player.getUserId());
                }
                player.forceLogout();
            }
            return false;
        }
        PVMInstance.destroyAll();
        System.out.println("Players removed from world successfully!");
        return true;
    }

    private static boolean removeBots() {
        for (Player p : World.players) {
            if (p.getChannel().id() == null)
                p.logoutStage = -1;
        }
        return true;
    }

    public static boolean christmas;

    public static boolean isChristmas() {
        return christmas;
    }

    /*
     * Save event
     */
    static {
        startEvent(e -> {
            while (true) {
                e.delay(100); //every 1 minute just in case..
                for (Player player : players)
                    PlayerFile.save(player, -1);
            }
        });
        startEvent(e -> {
            while (true) {
                e.delay(100 * 30); //every 30mins
                saveAll();
            }
        });
        startEvent(e -> {
            while (true) {
                e.delay(1);
                TimedEventManager.INSTANCE.tick();
            }
        });
    }

    /*
     * Announcement event
     */
    static {
        Server.afterData.add(() -> {
            List<String> announcements;
            announcements = Arrays.asList(
                    "Need help? Join the \"help\" cc!",
                    "Make sure to vote to gain access to exclusive items!",
                    "Take the time to protect your account and set a bank pin and 2FA!",
                    "Please take the time to vote for us. It helps us out and takes two seconds! ::vote",
                    "Join ::discord to get closer to the community!",
                    "Item Upgrades can be a great way to get the most from your gear!"
            );

            Collections.shuffle(announcements);
            startEvent(e -> {
                int offset = 0;
                while (true) {
                    e.delay(500); //5 minutes
                    //Broadcast.WORLD.sendNews(Icon.ANNOUNCEMENT, "Announcements", announcements.get(offset));
                    if (++offset >= announcements.size())
                        offset = 0;
                }
            });
        });
    }

    @SneakyThrows
    public static void parse(Properties properties) {
        type = WorldType.valueOf(properties.getProperty("world_type"));
        id = type.getId();
        christmas = Boolean.parseBoolean(properties.getProperty("christmas"));
        port = type.getPort();
    }

    public static Player getPlayerByName(String name) {
        Iterator var1 = players.iterator();

        Player player;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            player = (Player) var1.next();
        } while (player == null || !player.name().equalsIgnoreCase(name));

        return player;
    }

    private GlobalObjects globalObjects = new GlobalObjects();

    public static void sendWorldMessage(String message) {
        getPlayerStream().forEach(p -> p.sendMessage(message));
    }

    public static String getName() {
        return type.name();
    }

    public static File getSavePath(String mainDirectory) {
        return new File("./data/saves/"+getName()+"/"+mainDirectory);
    }

    public static File getMainSavePath() {
        return new File("./data/saves/"+getName()+"/");
    }
}