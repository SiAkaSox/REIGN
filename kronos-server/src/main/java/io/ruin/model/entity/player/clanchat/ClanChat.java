package io.ruin.model.entity.player.clanchat;

import com.google.common.collect.Maps;
import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.network.io.OutBuffer;
import io.ruin.cache.filestore.utility.Huffman;
import io.ruin.services.punishment.Punishment;
import io.ruin.services.punishment.PunishmentManager;
import io.ruin.utility.JsonUtils;
import io.ruin.utility.LogType;
import io.ruin.utility.Random;
import io.ruin.utility.StringUtils;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.friendslist.Friend;
import io.ruin.model.entity.player.friendslist.FriendsList;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class ClanChat {

    @Expose
    public String owner;
    @Expose
    public String name;
    @Expose
    public ClanRank enterRank = null;
    @Expose
    public ClanRank talkRank = null;
    @Expose
    public ClanRank kickRank = ClanRank.CORPORAL;

    public static final String DEFAULT_CC = "help";

    @Expose
    public FriendsList parent;

    private HashMap<Integer, Long> kicked;

    private static final int MAX_MEMBERS = 100;

    private Map<String, Friend> ccMembers;

    public ClanChat() {
        this.ccMembers = Maps.newConcurrentMap();
    }

    public static final Map<String, ClanChat> LOADED = Maps.newConcurrentMap();

    private static final File clan_folder = World.getSavePath("clans");

    public static void save() {
        LOADED.entrySet().stream().forEach(e -> save(e.getValue()));
    }

    public static Queue<ClanJoinQueue> joinQueue = new ConcurrentLinkedQueue<>();

    private static void save(ClanChat clanChat) {
        CompletableFuture.runAsync(() -> {
        try {
            String json = JsonUtils.GSON_EXPOSE_PRETTY.toJson(clanChat);
            if (!clan_folder.exists() && !clan_folder.mkdirs())
                throw new IOException("clan directory could not be created!");
            Files.write(new File(clan_folder, clanChat.owner + ".json").toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (Exception e) {
            Server.logError(e.getMessage());
        }
        });
    }

    public static ClanChat getClanChat(String owner, boolean create) {
        owner = owner.toLowerCase();
        LOADED.entrySet().stream().forEach(e -> e.getValue());
        ClanChat clan = LOADED.get(owner);
        if (clan != null) {
            return clan;
        }
        if (clan == null) {
            /**
             * Loading
             */
            File file = new File(clan_folder, owner + ".json");
            if (file.exists()) {
                try {
                    byte[] bytes = Files.readAllBytes(file.toPath());
                    String json = new String(bytes);
                    clan = (ClanChat) JsonUtils.GSON_EXPOSE.fromJson(json, ClanChat.class);
                } catch (Exception e) {
                    Server.logError(e.getMessage());
                }
            }
        }
        if (clan == null && create) {
            clan = new ClanChat();
            clan.owner = owner;
            clan.parent = World.getPlayer(owner).getFriendsList();
            clan.ccMembers = Maps.newConcurrentMap();
            clan.kicked = new HashMap();
            save(clan);
        }
        if (clan != null) {
            LOADED.put(clan.owner, clan);
        }
        return clan;
    }


    public static void sendSettings(Player player) {
        ClanChat clan = getClanChat(player.name(), false);
        String name = "Chat Disabled";
        int enterRank = -1;
        int talkRank = -1;
        int kickRank = -1;
        if (clan != null) {
            name = clan.name;
            enterRank = clan.enterRank == null ? -1 : clan.enterRank.id;
            talkRank = clan.talkRank == null ? -1 : clan.talkRank.id;
            kickRank = clan.kickRank == null ? ClanRank.CORPORAL.id : clan.kickRank.id;
        }
        OutBuffer out = new OutBuffer(2 + (name.length() + 1) + 3).sendVarBytePacket(86).addString(name).addByte(enterRank).addByte(talkRank).addByte(kickRank);
        player.getPacketSender().write(out);
    }

    public static void changeEnterSettings(Player player, int option) {
        ClanChat clan = getClanChat(player.name(), false);
        if (clan == null)
            return;
        submitChangeRequest(player, clan, () -> clan.enterRank = ClanRank.values()[option]);
    }

    public static void changeTalkSettings(Player player, int option) {
        ClanChat clan = getClanChat(player.name(), false);
        if (clan == null)
            return;
        submitChangeRequest(player, clan, () -> clan.talkRank = ClanRank.values()[option]);
    }

    public static void changeKickSettings(Player player, int option) {
        ClanChat clan = getClanChat(player.name(), false);
        if (clan == null)
            return;
        submitChangeRequest(player, clan, () -> clan.kickRank = ClanRank.values()[option]);
    }

    public static void assignRank(Player player, int rankId, String name) {
        ClanChat clan = getClanChat(player.name(), false);
        if (clan == null)
            return;
        Friend friend = clan.parent.getFriend(name);
        if (friend == null)
            return;
        submitChangeRequest(player, clan, () -> friend.rank = ClanRank.values()[rankId]);
    }

    public void disable() {
        for (Map.Entry<String, Friend> c : ccMembers.entrySet()) {
            Friend member = c.getValue();
            if (member == null)
                continue;
            Player pMember = World.getPlayer(member.name);
            if (pMember == null)
                continue;
            pMember.getPacketSender().sendMessage("The clan chat channel you were in has been disabled.", null, 11);
        }
        if (this.kicked != null) {
            this.kicked.clear();
            this.kicked = null;
        }
    }

    public boolean isDisabled() {
        return this.name == null;
    }

    public static void join(Player player, String name, boolean login, boolean newPlayer, boolean queue) {
        if (!queue) {
            join(player, name, login, newPlayer);
            return;
        }
        joinQueue.add(new ClanJoinQueue(player, name, login, newPlayer));
    }

    private static void join(Player player, String name, boolean login, boolean newPlayer) {
        if (player == null || player != null && !player.getChannel().isOpen())
            return;
        ClanChat clan = getClanChat(name, false);
        if (clan == null) {
            if (login) {
                player.currentClanChat = null;
                player.sendMessage("The clan chat channel you were in has been disabled.");
                return;
            }
            player.sendMessage("The clan chat doesn't exist.");
            return;
        }
        Long kickedUntil;
        if (clan.isDisabled()) {
            player.getPacketSender().sendMessage("The channel you tried to join is currently disabled.", null, 11);
            return;
        }
        if (clan.parent != null && clan.parent.getIgnore(StringUtils.capitalizeName(player.name())) != null) {
            player.sendMessage("You cannot join this channel because you are currently banned from it.");
            return;
        }
        if (clan.kicked != null && (kickedUntil = clan.kicked.get(player.userId)) != null) {
            if (kickedUntil > System.currentTimeMillis()) {
                player.getPacketSender().sendMessage("You cannot join this channel because you are currently banned from it.", null, 11);
                return;
            }
            clan.kicked.remove(player.userId);
        }
        if (!ClanChat.meetsRank(clan.enterRank, clan.getRank(player)) && !player.isAdmin()) {
            player.getPacketSender().sendMessage("You are not a high enough rank to enter this channel.", null, 11);
            return;
        }
        if (clan.ccMembers.size() >= MAX_MEMBERS || clan.inClan(clan.name)) {
            player.getPacketSender().sendMessage("The channel you tried to join is full.", null, 11);
            return;
        }
        clan.ccMembers.put(player.name(), new Friend(player.name, ClanRank.FRIEND));
        player.currentClanChat = clan.owner;
        refreshChannel(clan);
        player.getPacketSender().sendMessage("Now talking in clan chat channel " + clan.name + ".", null, 11);
        player.getPacketSender().sendMessage("To talk, start each line of chat with the / symbol.", null, 11);
        if (newPlayer && Objects.equals(DEFAULT_CC, name))//IF new player and cc is help
            sendDefaultCCMessage("Welcome to the newest member of the OSPK community, "+player.name()+"!");
        player.logger.log(LogType.CLAN_CHAT_JOIN, " joined clan chat name="+clan.name+" owner="+clan.owner);
    }

    private static void refreshChannel(ClanChat clan) {
        try {
            for (Map.Entry<String, Friend> c : clan.ccMembers.entrySet()) {
                Friend member = c.getValue();
                if (member == null)
                    continue;
                Player m = World.getPlayer(member.name);
                if (m == null)
                    continue;
                m.getPacketSender().write(channelBuffer(clan));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static OutBuffer channelBuffer(ClanChat clan) {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(48);
        out.addString(clan.owner);
        out.addString(clan.name);
        out.addByte(getRankId(clan.kickRank));
        out.addByte(clan.ccMembers != null ? clan.ccMembers.size() : 255);
        for (Map.Entry<String, Friend> c : clan.ccMembers.entrySet()) {
            Friend member = c.getValue();
            if (member == null)
                continue;
            Player m = World.getPlayer(member.name);
            if (m == null)
                continue;
            out.addString(member.name);//name
            out.addShort(World.id);//world
            out.addByte(clan.getRank(m) == null ? -1 : clan.getRank(m).id);//rank
            out.addByte(0);//not used
        }
        return out;
    }

    public static OutBuffer leaveBuffer() {
        return new OutBuffer(1).sendVarShortPacket(48);
    }

    public static void leave(Player player, boolean logout) {
        ClanChat clan = getClanChat(player.currentClanChat, false);
        if (clan == null) {
            System.err.println("cannot leave..");
            return;
        }
        clan.removeClanMember(player);
        if (!logout) {
            player.currentClanChat = null;
        }
        if (!clan.isDisabled())
            refreshChannel(clan);
    }

    public static void kick(Player kickedBy, String kickName) {
        ClanChat clan = getClanChat(kickedBy.currentClanChat, false);
        if (clan == null)
            return;
        if (clan.isDisabled()) {
            return;
        }
        ClanRank kickerRank = clan.getRank(kickedBy);
        if (!ClanChat.meetsRank(clan.kickRank, kickerRank)) {
            kickedBy.getPacketSender().sendMessage("You are not a high enough rank to kick from this channel.", null, 11);
            return;
        }
        Player toKick = World.getPlayer(kickName);
        ClanRank toKickRank = clan.getRank(toKick);
        if (toKickRank == ClanRank.OWNER) {
            kickedBy.getPacketSender().sendMessage("You can't kick this channel's owner!", null, 11);
            return;
        }
        if (!ClanChat.meetsRank(toKickRank, kickerRank)) {
            kickedBy.getPacketSender().sendMessage("You are not a high enough rank to kick this member.", null, 11);
            return;
        }
        clan.kicked.put(toKick.userId, System.currentTimeMillis() + 3600000L);
        save(clan);
        clan.leave(toKick, false);
        toKick.getPacketSender().sendMessage("You have been kicked from the channel.", null, 11);
    }

    public static void message(Player sender, String message) {
        ClanChat clan = getClanChat(sender.currentClanChat, false);
        if (clan.isDisabled()) {
            return;
        }
        ClanRank senderRank = clan.getRank(sender);
        if (PunishmentManager.isMuted(sender)) {
            sender.sendMessage("You are muted. You cannot talk in clan chats.");
            return;
        }
        if (!ClanChat.meetsRank(clan.talkRank, senderRank)) {
            sender.getPacketSender().sendMessage("You are not a high enough rank to talk in this channel.", null, 11);
            return;
        }
        message = StringUtils.fixCaps(message);
        sender.logger.log(LogType.CLAN_CHAT_MESSAGE, " spoke in clan chat name="+clan.name+" owner="+clan.owner+", message="+message);
        for (Map.Entry<String, Friend> c : clan.ccMembers.entrySet()) {
            Friend member = c.getValue();
            if (member == null)
                continue;
            Player pMember = World.getPlayer(member.name);
            if (pMember == null)
                continue;
            pMember.getPacketSender().write(clan.getMessageBuffer(sender.name, sender.getIconForProtocol(), message));
        }
    }

    public static void sendDefaultCCMessage(String message) {
        ClanChat clan = getClanChat(DEFAULT_CC, false);
        if (clan.isDisabled()) {
            return;
        }
        message = StringUtils.fixCaps(message);
        for (Map.Entry<String, Friend> c : clan.ccMembers.entrySet()) {
            Friend member = c.getValue();
            if (member == null)
                continue;
            Player pMember = World.getPlayer(member.name);
            if (pMember == null)
                continue;
            pMember.getPacketSender().write(clan.getMessageBuffer("Help", 2, message));
        }
    }

    private OutBuffer getMessageBuffer(String senderName, int rankId, String message) {
        OutBuffer out = new OutBuffer(255).sendVarBytePacket(22);
        out.addString(senderName);
        out.addString(this.name);
        for (int i = 0; i < 5; ++i) {
            out.addByte(Random.get(255));
        }
        out.addByte(rankId);
        Huffman.encrypt(out, message);
        return out;
    }

    public static void updateRanks(Player owner) {
        ClanChat clan = getClanChat(owner.name(), false);
        if (clan == null)
            return;
        submitChangeRequest(owner, clan, () -> clan.parent = owner.getFriendsList());
    }

    public boolean isHelpCC() {
        return Objects.equals("help", owner);
    }

    private ClanRank getRank(Player player) {
        String name = StringUtils.capitalizeName(player.name);
        if (name.toLowerCase().equalsIgnoreCase(owner)) {
            return ClanRank.OWNER;
        }
        if (player.isAdmin()) {
            return ClanRank.ADMIN;
        }
        if (this.isHelpCC()) {
            if (player.isModerator()) {
                return ClanRank.CAPTAIN;
            }
            if (player.isSupport()) {
                return ClanRank.LIEUTENANT;
            }
        }
        Friend friend = parent.getFriend(name);
        return friend == null ? null : friend.rank;
    }

    private static int getRankId(ClanRank rank) {
        return rank == null ? -1 : rank.id;
    }

    private static boolean meetsRank(ClanRank reqRank, ClanRank checkRank) {
        return reqRank == null || checkRank != null && checkRank.id >= reqRank.id;
    }

    public void removeClanMember(Player player) {
        String name = player.name();
        if (this.ccMembers == null) {
            return;
        }
        this.ccMembers.remove(name);
        player.getPacketSender().write(leaveBuffer());
        player.logger.log(LogType.CLAN_CHAT_LEAVE, " left clan chat name="+this.name+" owner="+this.owner);
    }


    public Friend getClanMember(String username) {
        if (this.ccMembers == null) {
            return null;
        }
        return this.ccMembers.get(username);
    }

    public boolean inClan(String name) {
        return this.getClanMember(name) != null;
    }

    public static void setName(Player player, String name) {
        ClanChat clan = ClanChat.getClanChat(player.name, false);
        String ownerName = player.name.toLowerCase();
        if (clan == null) {
            clan = new ClanChat();
            clan.owner = ownerName;
            clan.name = name;
            clan.parent = player.getFriendsList();
            clan.ccMembers = Maps.newConcurrentMap();
            clan.kicked = new HashMap();
            save(clan);
            ClanChat.LOADED.put(ownerName, clan);
            sendSettings(player);
            return;
        }
        ClanChat finalClan = clan;
        submitChangeRequest(player, clan, () -> finalClan.name = name);
    }

    static {
        World.startEvent(e -> {
            while (true) {
                e.delay(1);
                ClanJoinQueue clanChat = joinQueue.poll();
                if (clanChat != null) {
                    join(clanChat.player, clanChat.name, clanChat.login, clanChat.newPlayer);
                }
            }
        });
    }

    private static void submitChangeRequest(Player player, ClanChat clan, Runnable runnable) {
        player.getPacketSender().sendMessage("Changes to your channels will be updated within the next 60 seconds.", null, 11);
        World.startEvent(e -> {
            e.delay(100);
            runnable.run();
            refreshChannel(clan);
            save(clan);
        });
    }
}
