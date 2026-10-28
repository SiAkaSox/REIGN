package io.ruin.model.entity.player;

import com.google.common.collect.Maps;
import com.google.gson.annotations.Expose;
import io.netty.channel.Channel;
import io.ruin.Server;
import io.ruin.model.activities.wilderness.events.capturetheflag.CaptureTheFlag;
import io.ruin.model.content.vote.VoteManager;
import io.ruin.model.entity.player.antibot.AntiBotManager;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.newteleports.MonsterInter;
import io.ruin.network.login.LoginInfo;
import io.ruin.services.Hiscores;
import io.ruin.services.punishment.PunishmentManager;
import io.ruin.utility.*;
import io.ruin.cache.Color;
import io.ruin.cache.InterfaceDef;
import io.ruin.cache.Varp;
import io.ruin.event.GameEventProcessor;
import io.ruin.model.World;
import io.ruin.model.activities.home.AFKZone;
import io.ruin.model.activities.home.DailyRewards.DailyRewards;
import io.ruin.model.activities.home.FlowerPoker.FlowerHand;
import io.ruin.model.activities.wilderness.clanwars.ClanWars;
import io.ruin.model.activities.duelarena.Duel;
import io.ruin.model.activities.duelarena.DuelArena;
import io.ruin.model.activities.wilderness.pvp.bounty.BountyManager;
import io.ruin.model.activities.wilderness.pvp.bounty.PlayerBounty;
import io.ruin.model.activities.wilderness.pvp.leaderboard.Leaderboard;
import io.ruin.model.activities.wilderness.pvp.unrated.Arena2v2Queue;
import io.ruin.model.activities.wilderness.pvp.unrated.BattleGroundsQueue;
import io.ruin.model.activities.wilderness.BountyHunter;
import io.ruin.model.activities.wilderness.events.HoldTheFort;
import io.ruin.model.activities.wilderness.events.PirateShipEvent;
import io.ruin.model.content.UpgradeMachine;
import io.ruin.model.content.accountperks.PerkManager;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.clanchat.ClanChat;
import io.ruin.model.entity.player.friendslist.FriendsList;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.UpdateMask;
import io.ruin.model.entity.shared.listeners.*;
import io.ruin.model.entity.shared.masks.*;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.questtabs.QuestTab;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.handlers.DropViewer;
import io.ruin.model.inter.handlers.TeleportInterface;
import io.ruin.model.inter.handlers.shopinterface.CustomShop;
import io.ruin.model.inter.journal.presets.PresetCustom;
import io.ruin.model.inter.presets.PresetManager;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.impl.boxes.mystery.SuperMysteryBox;
import io.ruin.model.item.actions.impl.chargable.SerpentineHelm;
import io.ruin.model.item.actions.impl.storage.DeathStorage;
import io.ruin.model.item.actions.impl.storage.LootingBag;
import io.ruin.model.item.actions.impl.storage.RunePouch;
import io.ruin.model.item.actions.impl.tradepost.TradePost;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.containers.Inventory;
import io.ruin.model.item.containers.Trade;
import io.ruin.model.item.containers.bank.Bank;
import io.ruin.model.item.containers.bank.BankPin;
import io.ruin.model.item.containers.collectionlog.CollectionLog;
import io.ruin.model.item.lootkey.LootKeyManager;
import io.ruin.model.map.*;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.actions.impl.edgeville.Christmas;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.shop.Shop;
import io.ruin.model.skills.birdhouses.BirdHouseHandler;
import io.ruin.model.skills.construction.House;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.model.skills.farming.Farming;
import io.ruin.model.skills.hunter.Hunter;
import io.ruin.model.skills.magic.spells.AutoCast;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatList;
import io.ruin.model.stat.StatType;
import io.ruin.network.PacketSender;
import io.ruin.network.incoming.IncomingDecoder;
import io.ruin.services.Loggers;
import io.ruin.model.activities.home.wellofgoodwill.WellofGoodWill;
import io.ruin.utility.Random;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.cache.ItemID.PLATINUM_TOKEN;

public class Player extends PlayerAttributes {

    @Expose
    @Getter
    @Setter
    public FriendsList friendsList;

    @Expose
    public String currentClanChat;

    public boolean singlesPlus;

    public int worldId = 1;

    public Shop viewingShop;
    public transient Map<Integer, AutoCast> savedAutocasts = Maps.newHashMap();
    public int tempSoundId;
    @Getter
    public boolean isClientFocused, isTabbedOut;
    @Getter
    public List<Player> spectators;
    public int cameraX;
    public int cameraY;
    public long focusDuration;
    @Expose
    public int riskDef;

    public AntiBotManager antiBotManager;

    public AntiBotManager getAntiBotManager() {
        if (antiBotManager == null)
            antiBotManager = new AntiBotManager();
        return antiBotManager;
    }


    private MonsterInter teleporter;

    public MonsterInter getTeleporter() {
        if (teleporter == null)
            this.teleporter = new MonsterInter();
        return teleporter;
    }

    public boolean isAttackableAdmin() {
        Boolean attackable = get("isAttackableAdmin", false);
        return attackable != null && attackable;
    }

    /**
     * Session
     */

    private Channel channel;

    private String ipAddress;

    private int ipAddressInt;

    private int socialId = -1;

    public int getSocialId() {
        return socialId;
    }

    public int getPvpRating() {
        return this.pvpRating;
    }

    public void setSocialId(int socialId) {
        System.out.println("Setting social id to " + socialId);
        this.socialId = socialId;
    }

    public Player() {
    }

    public Channel getChannel() {
        return channel;
    }

    public String getIp() {
        return ipAddress;
    }

    public int getIpInt() {
        return ipAddressInt;
    }

    @Expose
    public String lastIPAddress;

    @Expose
    private String macAddress;

    public String getMACAddress() {
        return macAddress;
    }

    @Expose
    private String uuid;

    public String getUUID() {
        return uuid;
    }

    @Expose
    public String registeredIPAddress;

    @Expose
    public String registeredMac;

    @Getter
    @Expose
    public String registeredUID;

    @Expose
    public long datedRegisteredLong;

    /**
     * Info
     */

    private LoginInfo loginInfo;

    public LoginInfo getLoginInfo() {
        return this.loginInfo;
    }

    /**
     * Custom Shop System
     */
    private int shopIdentifier = -1;

    public int getShopIdentifier() {
        return shopIdentifier;
    }

    public void setShopIdentifier(int shopIdentifier) {
        this.shopIdentifier = shopIdentifier;
    }

    @Getter
    @Setter
    private CustomShop activeCustomShop;

    public int userId;

    @Expose
    public String name;

    @Expose
    public String lastName;

    @Expose
    private String password;

    public int getUserId() {
        return userId;
    }

    public String name() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        getAppearance().update();
    }

    public String getPassword() {
        return password;
    }

    public PlayerGroup getPlayerGroup() {
        return this.primaryGroup;
    }

    /**
     * Groups & Rank (Used to display client icons)
     */
    private boolean[] groups;

    @Expose
    private PlayerGroup primaryGroup;

    private PlayerGroup clientGroup; //the group displayed on clients

    public PlayerGroup getPrimaryGroup() {
        return primaryGroup;
    }

    public void setGroups(List<Integer> groupIds) {
        groups = new boolean[PlayerGroup.GROUPS_BY_ID.length];
        for (Integer id : groupIds) {
            PlayerGroup group = PlayerGroup.GROUPS_BY_ID[id];
            if (group != null)
                groups[group.id] = true;
        }
        primaryGroup = PlayerGroup.GROUPS_BY_ID[groupIds.get(0)];
        updateClientGroup();
    }

    public final DropViewer dropViewer = new DropViewer(this);

    public void updateClientGroup() {
        if (primaryGroup != null && primaryGroup.clientImgId != -1) {
            clientGroup = primaryGroup;
            return;
        }
        for (PlayerGroup group : PlayerGroup.values()) {
            if (groups[group.id] && group.clientImgId != -1) {
                clientGroup = group;
                return;
            }
        }
        clientGroup = PlayerGroup.REGISTERED;
    }

    public void join(PlayerGroup g) {
        groups[g.id] = true;
        updateClientGroup();
    }

    public void leave(PlayerGroup g) {
        groups[g.id] = false;
        updateClientGroup();
    }

    public boolean isGroup(PlayerGroup g) {
        if (primaryGroup == null)
            return false;
        return primaryGroup.equals(g);
    }

    public boolean isSupport() {
        return staffRank == 1;
    }

    public boolean isModerator() {
        return staffRank == 2;
    }

    public boolean isAdmin() {
        return staffRank == 3;
    }

    public boolean isAdminAbove() {
        return staffRank >= 3;
    }

    public boolean isManager() {
        return staffRank >= 4;
    }

    public boolean isDev() {
        return staffRank >= 5;
    }

    public boolean isOwner() {
        return staffRank >= 6;
    }

    public boolean isStaff() {
        return staffRank > 0;
    }

    public boolean isSapphire() {
        return isGroup(PlayerGroup.DONATOR) || isRuby();
    }

    public boolean isRuby() {
        return isGroup(PlayerGroup.DONATOR) || isDiamond();
    }

    public boolean isDiamond() {
        return isGroup(PlayerGroup.RESPECTED_DONATOR) || isDragonStone();
    }

    public boolean isDragonStone() {
        return isGroup(PlayerGroup.PLATINUM_DONATOR) || isOnyx();
    }

    public boolean isOnyx() {
        return isGroup(PlayerGroup.ONYX) || isZenyte();
    }

    public boolean isZenyte() {
        return isGroup(PlayerGroup.ZENYTE);
    }

    public boolean isAnyDonator() {
        return isGroup(PlayerGroup.DONATOR) || isGroup(PlayerGroup.SUPER_DONATOR) || isGroup(PlayerGroup.LEGENDARY_DONATOR) || isGroup(PlayerGroup.RESPECTED_DONATOR) || isGroup(PlayerGroup.PLATINUM_DONATOR);
    }

    public boolean isDonator() {
        return isGroup(PlayerGroup.DONATOR);
    }

    public boolean isSuperDonator() {
        return isGroup(PlayerGroup.SUPER_DONATOR);
    }

    public boolean isLegendaryDonator() {
        return isGroup(PlayerGroup.LEGENDARY_DONATOR);
    }

    public boolean isRespectedDonator() {
        return isGroup(PlayerGroup.RESPECTED_DONATOR);
    }

    public boolean isPlatinumDonator() {
        return isGroup(PlayerGroup.PLATINUM_DONATOR);
    }

    public boolean[] getGroups() {
        return groups;
    }

    public PlayerGroup getClientGroup() {
        return hidePlayerIcon ? PlayerGroup.REGISTERED : clientGroup;
    }

    public void toggleHide() {
        hidden = !hidden;
        this.getAppearance().update();
    }

    public int getClientGroupId() {
        if (hidePlayerIcon)
            return 0;
        if (isDonator()) {
            return 11;
        }
        if (isSuperDonator()) {
            return 10;
        }
        if (isLegendaryDonator()) {
            return 11;
        }
        if (isRespectedDonator()) {
            return 12;
        }
        if (isPlatinumDonator()) {
            return 9;
        }
        if (isSupport()) {
            return 8;
        }
        if (isModerator()) {
            return 1;
        }
        if (isDev()) {
            return 6;
        }
        return clientGroup.clientId;
    }

    /**
     * Private message information
     */
    private int unreadPMs;

    public int getUnreadPMs() {
        return unreadPMs;
    }

    /**
     * Incoming packets
     */
    private IncomingDecoder decoder;

    public IncomingDecoder getDecoder() {
        return decoder;
    }

    /**
     * Outgoing packets
     */
    private PacketSender packetSender;

    public void sendMessage(String message) {
        packetSender.sendMessage(message, null, 0);
    }

    public void sendMessage(Color color, String message) {
        packetSender.sendMessage(color.wrap(message), null, 0);
    }

    public void sendURL(String message) {
        packetSender.sendMessage(message, World.type.getWebsiteUrl(), 4);
    }

    public void sendNotification(String message) {
        packetSender.sendMessage(message, "", 14);
    }

    public void sendFilteredMessage(String message) {
        packetSender.sendMessage(message, null, 105);
    }

    public void openUrl(String title, String url) {
        dialogue(new MessageDialogue("Opening " + title + "...<br>If this page fails to open please navigate your browser to:<br><col=880088>" + url));
        packetSender.sendUrl(url, false);
    }

    public void openUrl(String url) {
        packetSender.sendUrl(url, false);
    }

    public void sendScroll(String title, String... lines) {
        if (isVisibleInterface(119))
            closeInterface(InterfaceType.MAIN);
        packetSender.sendString(119, 2, title);
        int childId = 4;
        packetSender.sendString(119, childId++, "");
        for (String s : lines)
            packetSender.sendString(119, childId++, s);
        packetSender.sendClientScript(917, "ii", -1, -1);
        openInterface(InterfaceType.MAIN, 119);
        packetSender.sendClientScript(2523, "1i", 1, lines.length);
    }

    public void sendHintArrow(Entity target) {
        packetSender.sendHintIcon(target);
    }

    public void sendHintArray(Position tile) {
        packetSender.sendHintIcon(tile);
    }

    public void clearHintArrow() {
        packetSender.resetHintIcon(false);
    }

    public PacketSender getPacketSender() {
        return packetSender;
    }

    /**
     * Online
     */

    private boolean online;

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }

    /**
     * Display
     */
    private int displayMode = -1;

    public void setDisplayMode(int displayMode) {
        this.displayMode = displayMode;
    }

    public boolean hasDisplay() {
        return displayMode != -1;
    }

    public boolean isFixedScreen() {
        return displayMode < 2;
    }

    /**
     * Game frame
     */
    private int gameFrameId;

    public void setGameFrameId(int gameFrameId) {
        this.gameFrameId = gameFrameId;
    }

    public int getGameFrameId() {
        return gameFrameId;
    }

    /**
     * Interface visibility
     */
    private boolean[] visibleInterfaces = new boolean[InterfaceDef.COUNTS.length];

    private Integer[][] visibleInterfaceIds = new Integer[InterfaceDef.COUNTS.length][];

    public void setVisibleInterface(int interfaceId, int parentId, int childId) {
        if (visibleInterfaceIds[parentId] == null)
            visibleInterfaceIds[parentId] = new Integer[InterfaceDef.COUNTS[parentId]];
        else if (visibleInterfaceIds[parentId] != null) {
            Integer id = visibleInterfaceIds[parentId][childId];
            if (id != null)
                visibleInterfaces[id] = false;
        }
        visibleInterfaces[interfaceId] = true;
        visibleInterfaceIds[parentId][childId] = interfaceId;
    }

    public void removeVisibleInterface(int parentId, int childId) {
        if (visibleInterfaceIds[parentId][childId] != null)
            visibleInterfaces[visibleInterfaceIds[parentId][childId]] = false;
        visibleInterfaceIds[parentId][childId] = null;
    }

    public void moveVisibleInterface(int fromParentId, int fromChildId, int toParentId, int toChildId) {
        Integer interfaceId = visibleInterfaceIds[fromParentId][fromChildId];
        if (interfaceId == null)
            return;
        setVisibleInterface(interfaceId, toParentId, toChildId);
        visibleInterfaceIds[fromParentId][fromChildId] = null;
    }

    public boolean isVisibleInterface(int interfaceId) {
        return interfaceId == gameFrameId || visibleInterfaces[interfaceId];
    }

    public boolean hasInterfaceOpen(int id, InterfaceType type) {
        InterfaceHandler activeHandler = activeInterfaceHandlers[type.ordinal()];
        if (activeHandler == null)
            return false;

        return activeHandler.id == id;
    }

    /**
     * Interfaces - Opening, Closing & Handling
     */

    private InterfaceHandler[] activeInterfaceHandlers = new InterfaceHandler[InterfaceType.values().length];

    public void openInterface(InterfaceType type, int interfaceId, InterfaceHandler handler) {
        closeChatbox(type == InterfaceType.CHATBOX); //dupe prevention
        InterfaceHandler activeHandler = activeInterfaceHandlers[type.ordinal()];
        if (activeHandler != null && activeHandler.closedAction != null)
            activeHandler.closedAction.accept(this, interfaceId);
        type.open(this, interfaceId);
        activeInterfaceHandlers[type.ordinal()] = handler == null ? InterfaceHandler.EMPTY_HANDLER : handler;
    }

    public void openInterface(InterfaceType type, int interfaceId) {
        openInterface(type, interfaceId, InterfaceHandler.HANDLERS[interfaceId]);
    }

    public void setInterfaceUnderlay(int color, int transparency) {
        CS2Script.TOPLEVEL_MAINMODAL_OPEN.sendScript(this, color, transparency);
    }

    public void closeInterface(InterfaceType type) {
        InterfaceHandler activeHandler = activeInterfaceHandlers[type.ordinal()];
        if (activeHandler == null) {
            return;
        }
        if (activeHandler.closedAction != null)
            activeHandler.closedAction.accept(this, -1);
        type.close(this);
        activeInterfaceHandlers[type.ordinal()] = null;
    }

    public void closeInterfaces() {
        closeInterfaces(false);
    }

    public void closeInterfaces(boolean skipDialogues) {
        for (InterfaceType type : InterfaceType.values()) {
            if (type.overlaySetting != 1)
                closeInterface(type);
        }
        if (trade != null)
            trade.close();
        if (duel != null)
            duel.close();
        closeChatbox(skipDialogues);
    }

    public void closeChatbox(boolean skipDialogues) {
        if (!skipDialogues && dialogues != null)
            closeDialogue();
        if (consumerInt != null || consumerString != null) {
            consumerInt = null;
            consumerString = null;
            packetSender.sendClientScript(299, "ii", 1, 1);
        }
    }

    /**
     * Dialogue
     */

    private int dialogueStage;
    private Dialogue[] dialogues;
    public Dialogue lastDialogue;
    public OptionsDialogue optionsDialogue;
    public SkillDialogue skillDialogue;
    public YesNoDialogue yesNoDialogue;

    public void openDialogue(boolean closeInterfaces, Dialogue... dialogues) {
        if (closeInterfaces) { //important to be true in almost every case to prevent dupes!
            if (!this.isVisibleInterface(832))
                closeInterfaces(true);
        }
        this.dialogueStage = 1;
        this.dialogues = dialogues;
        this.optionsDialogue = null;
        this.skillDialogue = null;
        this.yesNoDialogue = null;
        (lastDialogue = dialogues[0]).open(this);
    }

    public void dialogue(Dialogue... dialogues) {
        openDialogue(true, dialogues);
    }

    public void unsafeDialogue(Dialogue... dialogues) {
        openDialogue(false, dialogues);
    }

    public void continueDialogue() {
        onDialogueContinued();
        if (dialogues == null || dialogueStage >= dialogues.length) {
            closeDialogue();
        } else {
            (lastDialogue = dialogues[dialogueStage++]).open(this);
        }
    }

    public void closeDialogue() {
        dialogues = null;
        if (lastDialogue != null) {
            lastDialogue.closed(this);
            lastDialogue = null;
        }
        optionsDialogue = null;
        skillDialogue = null;
        yesNoDialogue = null;
        closeInterface(InterfaceType.CHATBOX);
    }

    public boolean hasDialogue() {
        return dialogues != null || optionsDialogue != null || skillDialogue != null || yesNoDialogue != null;
    }

    /**
     * Input
     */

    public Consumer<Integer> consumerInt;

    public boolean retryIntConsumer;

    public Consumer<String> consumerString;

    public boolean retryStringConsumer;

    /**
     * Integer input (Whole numbers!!)
     */

    public void integerInput(String message, Consumer<Integer> consumer) {
        consumerInt = consumer;
        retryIntConsumer = false;
        packetSender.sendClientScript(108, "s", message);
    }

    public void retryIntegerInput(String message) {
        integerInput(message, consumerInt);
        retryIntConsumer = true;
    }

    /**
     * Item input
     */

    public void itemSearch(String message, boolean allItems, Consumer<Integer> consumer) {
        consumerInt = consumer;
        retryIntConsumer = false;
        packetSender.sendClientScript(750, "s1g", message, (allItems ? 2 : 1), -1);
    }

    /**
     * String input (Basically any group of characters!)
     */

    public void stringInput(String message, Consumer<String> consumer) {
        consumerString = consumer;
        retryStringConsumer = false;
        packetSender.sendClientScript(110, "s", message);
    }

    public void retryStringInput(String message) {
        stringInput(message, consumerString);
        retryStringConsumer = true;
    }

    /**
     * Name input (Basically only allows characters used in player names!)
     */

    public void nameInput(String message, Consumer<String> consumer) {
        consumerString = consumer;
        retryStringConsumer = false;
        packetSender.sendClientScript(109, "s", message);
    }

    public void retryNameInput(String message) {
        nameInput(message, consumerString);
        retryStringConsumer = true;
    }

    /**
     * Options
     */

    private PlayerAction[] actions = new PlayerAction[8];

    public void setAction(int option, PlayerAction action) {
        if (action == null) {
            PlayerAction previousAction = actions[option - 1];
            if (previousAction == null)
                return;
            actions[option - 1] = null;
            packetSender.sendPlayerAction("null", false, option);
        } else {
            actions[option - 1] = action;
            packetSender.sendPlayerAction(action.name, action.top, option);
        }
    }

    private void setInvisibleAction(int option, PlayerAction action) {
        actions[option - 1] = action;
    }

    public PlayerAction getAction(int option) {
        return actions[option - 1];
    }

    /**
     * Configs
     */

    @Expose
    public HashMap<Integer, Integer> savedConfigs = new HashMap<>();

    public int[] varps = new int[Varp.LOADED.length];

    private boolean[] updatedVarps = new boolean[varps.length];

    private int[] updatedVarpIds = new int[varps.length];

    private int updatedVarpCount;

    public void updateVarp(int id) {
        if (updatedVarps[id])
            return;
        updatedVarps[id] = true;
        updatedVarpIds[updatedVarpCount++] = id;
    }

    public void sendVarps() {
        if (updatedVarpCount == 0)
            return;
        for (int i = 0; i < updatedVarpCount; i++) {
            int id = updatedVarpIds[i];
            int value = varps[id];
            updatedVarps[id] = false;
            packetSender.sendVarp(id, value);
        }
        updatedVarpCount = 0;
    }

    /**
     * Region
     */

    public Region lastRegion;

    private ArrayList<Region> regions = new ArrayList<>();

    public void addRegion(Region region) {
        region.players.add(this);
        regions.add(region);
    }

    public void removeFromRegions() {
        regions.removeIf(region -> {
            region.players.remove(this);
            return true;
        });
    }

    public ArrayList<Region> getRegions() {
        return regions;
    }

    /**
     * Player updating
     */

    private PlayerUpdater updater;

    public Iterable<Player> localPlayers() {
        return updater.localIterator();
    }

    public boolean isLocal(Player p) {
        return updater.local[p.getIndex()];
    }

    public PlayerUpdater getUpdater() {
        return updater;
    }

    /**
     * Npc updating
     */

    private PlayerNPCUpdater npcUpdater;

    public Collection<NPC> localNpcs() {
        return npcUpdater.localNpcs;
    }

    public PlayerNPCUpdater getNpcUpdater() {
        return npcUpdater;
    }

    /**
     * Movement
     */

    @Expose
    private PlayerMovement movement;

    public PlayerMovement getMovement() {
        return movement;
    }

    /**
     * Masks
     */

    private UpdateMask[] masks;

    @Expose
    private Appearance appearance;

    private ChatUpdate chatUpdate;

    protected ForceMovementUpdate forceMovementUpdate;

    protected MovementModeUpdate movementModeUpdate;

    protected TeleportModeUpdate teleportModeUpdate;

    public UpdateMask[] getMasks() {
        return masks;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public ChatUpdate getChatUpdate() {
        return chatUpdate;
    }

    /**
     * Items
     */

    @Expose
    private Inventory inventory;

    @Expose
    private Equipment equipment;

    private Trade trade;

    private Duel duel;

    private ClanWars clanWars;

    @Expose
    private Bank bank;

    @Expose
    private BankPin bankPin;

    @Expose
    private LootingBag lootingBag;

    @Expose
    private RunePouch runePouch;

    public RunePouch getTournamentRunePouch() {
        return tournamentRunePouch;
    }

    @Expose
    private RunePouch tournamentRunePouch;

    private SuperMysteryBox box;

    @Expose
    private DeathStorage deathStorage;

    @Expose
    private CollectionLog collectionLog;

    @Expose
    private ItemContainer privateRaidStorage;

    @Expose
    private ItemContainer uimStorage;

    @Expose
    private ItemContainer raidRewards = new ItemContainer();

    public Inventory getInventory() {
        return inventory;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Trade getTrade() {
        return trade;
    }

    public Duel getDuel() {
        return duel;
    }

    public ClanWars getClanWars() {
        return clanWars;
    }

    public Bank getBank() {
        return bank;
    }

    private PresetManager presetManager;//not used.

    public BankPin getBankPin() {
        return bankPin;
    }

    public LootingBag getLootingBag() {
        return lootingBag;
    }

    public RunePouch getRunePouch() {
        return runePouch;
    }

    public SuperMysteryBox getBox() {
        return box;
    }

    public DeathStorage getDeathStorage() {
        return deathStorage;
    }


    /**
     * Collection Log
     */

    public CollectionLog getCollectionLog() {
        return collectionLog;
    }

    /**
     * Farming
     */
    @Expose
    public Farming farming;

    public Farming getFarming() {
        return farming;
    }

    /**
     * Stats
     */

    @Expose
    private StatList stats;

    public StatList getStats() {
        return stats;
    }

    /**
     * Prayer
     */

    @Expose
    private PlayerPrayer prayer;

    public PlayerPrayer getPrayer() {
        return prayer;
    }

    public PlayerCounter counter;

    public PlayerCounter getCounter() {
        return this.counter;
    }

    /**
     * Combat
     */

    @Expose
    private PlayerCombat combat;

    public PlayerCombat getCombat() {
        return combat;
    }

    /**
     * Bounty Hunter
     */

    @Expose
    private BountyHunter bountyHunter;

    public BountyHunter getBountyHunter() {
        return bountyHunter;
    }

    /**
     * Trade post
     */
    @Expose
    private TradePost tradePost;

    public TradePost getTradePost() {
        return tradePost;
    }

    /**
     * Hitpoints
     */

    @Override
    public int setHp(int newHp) {
        return stats.get(StatType.Hitpoints).alter(newHp);
    }

    @Override
    public int setMaxHp(int newHp) {
        return combat == null ? 0 : stats.get(StatType.Hitpoints.ordinal()).set(newHp);
    }

    @Override
    public int getHp() {
        return stats.get(StatType.Hitpoints).currentLevel;
    }

    @Override
    public int getMaxHp() {
        return stats.get(StatType.Hitpoints).fixedLevel;
    }

    /**
     * Map listeners
     */

    private List<MapListener> activeMapListeners = new ArrayList<>();

    private boolean[] activeStaticMapListeners = new boolean[MapListener.LISTENERS.length];

    public void addActiveMapListener(MapListener listener) {
        //Warning: If the listener isn't already active this won't function properly!
        //Another important note: listener.onEnter(p) will never be called when using this method.
        activeMapListeners.add(listener);
    }

    private void validateMapListeners() {
        if (!activeMapListeners.isEmpty()) {
            activeMapListeners.removeIf(listener -> {
                if (!listener.isActive(this)) {
                    if (listener.exitAction != null)
                        listener.exitAction.exited(this, false);
                    if (listener.staticId != -1)
                        activeStaticMapListeners[listener.staticId] = false;
                    return true;
                }
                return false;
            });
        }
        for (int i = 0; i < MapListener.LISTENERS.length; i++) {
            if (activeStaticMapListeners[i])
                continue;
            MapListener listener = MapListener.LISTENERS[i];
            if (listener.isActive(this)) {
                addActiveMapListener(listener);
                if (listener.enterAction != null)
                    listener.enterAction.entered(this);
                activeStaticMapListeners[i] = true;
            }
        }
    }

    public void collectResource(int harvestId, int amount) {
        collectResource(new Item(harvestId, amount));
    }

    public void collectResource(Item resource) {
        for (Item item : player.getEquipment().getItems()) {
            if (item != null && item.getDef() != null) {
                List<String> upgrades = AttributeExtensions.getEffectUpgrades(item);
                boolean hasEffect = upgrades != null;
                if (hasEffect) {
                    for (String s : upgrades) {
                        try {
                            if (s.equalsIgnoreCase("empty"))
                                continue;
                            ItemEffect effect = ItemEffect.valueOf(s);
                            effect.getUpgrade().collectResource(player, resource);
                        } catch (Exception ex) {
                            System.err.println("Unknown upgrade { " + s + " } found!");
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void removeMapListeners() {
        if (!activeMapListeners.isEmpty()) {
            activeMapListeners.removeIf(listener -> {
                if (listener.exitAction != null)
                    listener.exitAction.exited(this, true);
                return true;
            });
        }
        activeMapListeners = null;
    }

    /**
     * Misc listeners
     */

    public LogoutListener logoutListener;
    public transient Set<LogoutListener> logoutListeners;

    public void addLogoutListener(LogoutListener listener) {
        this.logoutListeners.add(listener);
    }

    public void removeLogoutListener(LogoutListener listener) {
        this.logoutListeners.remove(listener);
    }

    public AllowPrayerListener allowPrayerListener;
    public ActivatePrayerListener activatePrayerListener;

    public TeleportListener teleportListener;

    @Getter
    @Setter
    @Expose
    public LootKeyManager lootKeyManager;

    /**
     * Init
     */

    public void init(LoginInfo info) {
        this.channel = info.channel;
        this.ipAddress = info.ipAddress;
        this.lastIPAddress = info.ipAddress;
        this.macAddress = info.macAddress;
        this.ipAddressInt = info.ipAddressInt;
        this.userId = info.userId;
        if (this.registeredIPAddress == null)
            this.registeredIPAddress = info.ipAddress;
        if (this.registeredMac == null)
            this.registeredMac = info.macAddress;
        if (this.registeredUID == null)
            this.registeredUID = info.uuid;
        if (this.datedRegisteredLong == 0)
            this.datedRegisteredLong = System.currentTimeMillis();
        if (this.name == null)
            this.name = StringUtils.capitalizeFirst(info.name);
        if (this.password == null)
            this.password = info.password;
        this.tfa = info.tfaCode != 0;
        this.unreadPMs = info.unreadPMs;
        this.uuid = info.uuid;
        this.loginInfo = info;

        //setGroups(info.groupIds);

        if (position == null)
            position = new Position(3090, 3505, 0);
        else
            position.updateFirstChunk();
        lastPosition = position.copy();

        if (antiBotManager == null)
            antiBotManager = new AntiBotManager();
        antiBotManager.init(this);
        decoder = new IncomingDecoder(new ISAACCipher(info.keys));
        for (int i = 0; i < 4; i++)
            info.keys[i] += 50;
        packetSender = new PacketSender(this, new ISAACCipher(info.keys));

        updater = new PlayerUpdater(this);
        npcUpdater = new PlayerNPCUpdater(this);
        movement = new PlayerMovement(this);

        if (appearance == null)
            appearance = new Appearance();
        appearance.setPlayer(this);

        masks = new UpdateMask[]{ //order same way as client reads
                graphicsUpdate = new GraphicsUpdate(),
                forceTextUpdate = new ForceTextUpdate(),
                appearance,
                chatUpdate = new ChatUpdate(),
                movementModeUpdate = new MovementModeUpdate(),
                animationUpdate = new AnimationUpdate(),
                teleportModeUpdate = new TeleportModeUpdate(),
                forceMovementUpdate = new ForceMovementUpdate(),
                hitsUpdate = new HitsUpdate(),
                entityDirectionUpdate = new EntityDirectionUpdate(),
                mapDirectionUpdate = new MapDirectionUpdate(),
        };

        if (birdHouseHandler == null)
            birdHouseHandler = new BirdHouseHandler(this);
        birdHouseHandler.init(player);

        if (inventory == null)
            inventory = new Inventory();
        inventory.init(this, 28, Interface.INVENTORY, 0, 93, false);
        inventory.sendAll = true;

        if (equipment == null)
            equipment = new Equipment();
        equipment.init(this, 14, -1, 64208, 94, false);
        equipment.update(Equipment.SLOT_WEAPON);
        equipment.sendAll = true;

        if (lootKeyManager == null)
            lootKeyManager = new LootKeyManager(this);
        lootKeyManager.init(this);

        trade = new Trade(this);

        duel = new Duel(this);

        if (bank == null)
            bank = new Bank();
        bank.init(this, 800, -1, 64207, 95, true);

        if (collectionLog == null)
            collectionLog = new CollectionLog();
        collectionLog.init(this, 500, -1, -1, 620, true);//621, 35

        if (lootingBag == null)
            lootingBag = new LootingBag();
        lootingBag.init(this, 28, -1, 63786, 516, false);

        if (runePouch == null)
            runePouch = new RunePouch();
        runePouch.init(this, 3, -1, -1, -1, false);

        if (tournamentRunePouch == null)
            tournamentRunePouch = new RunePouch();
        tournamentRunePouch.init(this, 3, -1, -1, -1, false);

        if (box == null)
            box = new SuperMysteryBox();
        box.init(this, 24, -1, -1, 510, false);

        if (deathStorage == null)
            deathStorage = new DeathStorage();
        deathStorage.init(this, 100, -1, -1, 525, false);

        if (privateRaidStorage == null)
            privateRaidStorage = new ItemContainer();
        privateRaidStorage.init(player, 90, -1, -1, 583, false);
        privateRaidStorage.sendAll = true;

        if (raidRewards == null)
            raidRewards = new ItemContainer();
        raidRewards.init(player, 3, -1, -1, 581, false);

        if (bankPin == null)
            bankPin = new BankPin();
        bankPin.init(this);

        if (stats == null)
            stats = new StatList();
        stats.init(this);

        if (prayer == null)
            prayer = new PlayerPrayer();
        prayer.init(this);

        if (farming == null)
            farming = new Farming();
        farming.init(this);

        if (combat == null)
            combat = new PlayerCombat();
        combat.init(this);

        if (bountyHunter == null)
            bountyHunter = new BountyHunter();
        bountyHunter.init(this);

        if (tradePost == null) {
            tradePost = new TradePost();
        }
        tradePost.init(player);

        if (presets == null)
            presets = new PresetManager();

        if (teleports == null) {
            teleports = new TeleportInterface();
        }
        teleports.setPlayer(this);

        if (upgradeMachine == null) {
            upgradeMachine = new UpgradeMachine();
        }
        upgradeMachine.setPlayer(this);

        if (friendsList == null)
            friendsList = new FriendsList();

        if (perkManager == null)
            perkManager = new PerkManager();
        perkManager.init(this);

        checkMulti();
        Tile.occupy(this);

        //UltraLogger.addLog("Player Object Created", info, "PlayerConstructor");
    }

    public boolean isEasyModeWithEasyXP() {
        return !isRealism && !this.getGameMode().isAnyIron() && this.xpMode == XpMode.EASY;
    }

    /**
     * Start & Finish
     */

    private Bounds EDGEVILLE = new Bounds(3063, 3457, 3121, 3519, -1);

    public void start() {
        player.setOnline(true);
        combat.start();
        movement.sendEnergy(-1);
        getCombat().resetTb();
        /*
         * Misc
         */
        PresetCustom.check(this);
        bankPin.loggedIn();
        friendsList.init(this);
        /*
         * Actions
         */
        setAction(2, PlayerAction.FOLLOW);
        setAction(3, PlayerAction.TRADE);
        setInvisibleAction(4, PlayerAction.TRADE);
        /*
         * Messages
         */
        sendMessage("Welcome to " + World.type.getWorldName() + "");
        //BiHourlyBonusSkill.skillOfTheDayMessage(player);
        //BiHourlyBonusMinigame.currentMinigameSelection(player);
        DailyRewards.ensureFirstLoginDateIsSet(player);
        packetSender.sendClientScript(110, "i", "");
        packetSender.sendDiscordPresence("Idle");

        updateRank();
        //LEAGUE STUFF HERE TODO

        //PvpRatingInter.updateLeagueName(this);
        //DailyTask.dailyResetMethod(this);
        Leaderboard.resetDailyFifteen(this);
        /*
         * Daily
         */
        if (lastLoginDate == null || LocalDate.parse(lastLoginDate).isBefore(LocalDate.now())) {
            dailyReset();
        }
        /*
         * Listeners
         */
        LoginListener.executeAll(this);

        /*
         * Misc 2
         */

        if (pet != null)
            pet.spawn(this);

        //Loggers.addOnlinePlayer(userId, name, World.id, ipAddress, player.isSupport(), player.isModerator(), player.isAdmin());

        Title.load(this);

        /*
         * Clean those who have a crazy amount of money on update
         */
        if (lastLoginDate != null && LocalDate.parse(lastLoginDate).isBefore(LocalDate.of(2019, 11, 10))) {
            if (player.inventory.getAmount(COINS_995) > 10000000) {
                player.inventory.findItem(COINS_995).setAmount(2000000);
            }
            if (player.bank.getAmount(COINS_995) > 10000000) {
                player.bank.findItem(COINS_995).setAmount(2000000);
            }

            if (player.inventory.getAmount(PLATINUM_TOKEN) > 10000) {
                player.inventory.findItem(PLATINUM_TOKEN).setAmount(2000);
            }
            if (player.bank.getAmount(PLATINUM_TOKEN) > 10000) {
                player.bank.findItem(PLATINUM_TOKEN).setAmount(2000);
            }
        }

        if (this.currentClanChat != null) {
            ClanChat.join(this, this.currentClanChat, true, false, true);
        }
        packetSender.sendVarp(1737, -2147483648);
        packetSender.sendClientScript(1105, "i", 1);//xp drop
        packetSender.sendClientScript(423, "s", name);//slayer rewards tasks canceled?
        packetSender.sendClientScript(2498, "i11", 1, 0, 0);//account info update
        Config.HAS_DISPLAY_NAME.set(player, 1);
        packetSender.sendClientScript(828, "i", 1);//player member?
        packetSender.sendClientScript(233, "ImiiiiiiA", 24772664, 38864, 15, 200, 81, 1885, 0, 2000, 8498);//message of the week set model
        packetSender.sendClientScript(233, "ImiiiiiiA", 24772665, 38864, 10, 180, 78, 158, 0, 2000, 8500);//message of the week set model
        inform();
    }

    private void inform() {
        /*
         * Donator Benefit: [1 Daily Lottery Ticket]
         */
        if (lastLoginDate != null && LocalDate.parse(lastLoginDate).isBefore(LocalDate.now()) && isSapphire()) {
            sendMessage("You have received your daily lottery ticket. Thanks for supporting the server!");
            bank.add(1464, 1);
        }
        /**
         * Well of Good will
         */
        if (WellofGoodWill.isBonusxpactivated()) {
            if ((WellofGoodWill.getWELL_START_TIMESTAMP() - System.currentTimeMillis()) >= 7200000 && WellofGoodWill.getWELL_START_TIMESTAMP() > 0) {
                Broadcast.WORLD.sendNews("The double xp from the well has ended!");
                WellofGoodWill.activateBonusXp(false, player);
                World.toggleWeekendExpBoost();
                WellofGoodWill.setWELL_START_TIMESTAMP(0);
            } else {
                this.sendMessage(Color.DARK_RED.tag() + "The Well of Goodwill is currently active!");
            }
        }

        int boost = World.xpMultiplier;
        if (World.doubleXP)
            this.sendMessage(Color.DARK_RED.wrap("The 25% experience weekend boost is currently active!"));
        if (World.doubleDrops)
            this.sendMessage(Color.DARK_RED.tag() + "Npc drops are currently being doubled! (x2)");
        if (World.xpMultiplier > 1)
            this.sendMessage(Color.DARK_RED.tag() + "Experience is currently being " + (boost == 2 ? "doubled" : boost == 3 ? "tripled" : "quadrupled") + "! (x2)");

        if (!this.newPlayer && VoteManager.voteBoss != null) {
            this.sendMessage(Color.BLUE.wrap("- The Vote boss has appeared! (::voteboss)"));
        }
        /*if (World.isDev())
            player.join(PlayerGroup.OWNER);*/
        BountyManager.checkSkull(this);
    }

    public void finish() {
        /*
         * Scrolls Timer Saving
         */
        if (player.rareDropBonus.remaining() > 0) {
            player.rareDropBonusTimeLeft = player.rareDropBonus.remaining();
        }
        if (player.expBonus.remaining() > 0) {
            player.expBonusTimeLeft = player.expBonus.remaining();
        }
        if (player.first3.remaining() > 0) {
            player.first3TimeLeft = player.first3.remaining();
        }
        if (player.petDropBonus.remaining() > 0) {
            player.petDropBonusTimeLeft = player.petDropBonus.remaining();
        }
        if (player.blackChinchompaBoost.remaining() > 0) {
            player.blackChinchompaBoostTimeLeft = player.blackChinchompaBoost.remaining();
        }
        if (player.darkCrabBoost.remaining() > 0) {
            player.darkCrabBoostTimeLeft = player.darkCrabBoost.remaining();
        }
        GameEventProcessor.killFor(this);
        Hunter.collapseAll(this);
        resetActions(true, true, true);
        bankPin.loggedOut();
        bountyHunter.loggedOut();
        if (logoutListener != null && logoutListener.logoutAction != null) {
            logoutListener.logoutAction.logout(this);
            logoutListener = null;
        }
        if (spectators != null) {
            spectators.forEach(e -> e.stopSpectating());
            spectators = null;
        }
        if (currentClanChat != null) {
            ClanChat.leave(this, true);
        }
        removeMapListeners();
        removeFromRegions();
        movement.finishTeleport(getPosition()); //if a teleport is queued, finish it!
        setHidden(true);
        FriendsList.check();
        PunishmentManager.checkPunishment(this, null);
        saveHiscores();
    }

    public void saveHiscores() {
        if (isAdmin() || isManager() || isDev() || !World.isLive())
            return;
        if (this.playTime < 500)
            return;
        Server.HISCORES.execute(new Hiscores(this));
    }

    private void stopSpectating() {
        setHidden(false);
        getAppearance().update();
    }

    /**
     * Reset Actions
     */

    public void resetActions(boolean closeInterfaces, boolean resetMovement, boolean resetCombat) {
        GameEventProcessor.killFor(this);
        if (!isLocked())
            stopEvent(resetCombat);
        if (closeInterfaces)
            closeInterfaces();
        if (resetMovement) {
            movement.reset();
            movement.following = null;
            faceNone(false);
            TargetRoute.reset(this);
            if (seat != null)
                seat.stand(player);
        }
        if (resetCombat && combat.getTarget() != null)
            combat.reset();
    }

    private FlowerHand flowerHand;

    public FlowerHand getFlowerHand() {
        return this.flowerHand;
    }

    /**
     * Logout & Saving
     */

    public int logoutStage; //0=no logout, 1=logout required, -1=logout accepted

    private final Object saveLock = new Object();

    private int saveAttempt;

    private long saveRetry = -1;

    public long lastBoxHeal = 0;

    private TickDelay xLogDelay;

    public void attemptLogout() {
        if (combat.isDead() || combat.isDefending(17)) {
            sendMessage("You can't log out until 10 seconds after the end of combat.");
            return;
        }
        if (CaptureTheFlag.isFlagHolder(this)) {
            sendMessage("You can't logout while carrying the flag!");
            return;
        }
        if (supplyChestRestricted) {
            sendMessage("You recently completed a world event, and cannot log out until you exit the wilderness!");
            return;
        }
        if (isLockedExclude(LockType.FULL_ALLOW_LOGOUT) && player.getAppearance().getNpcId() == -1)
            return;
        if (logoutListener != null && logoutListener.attemptAction != null && !logoutListener.attemptAction.allow(this))
            return;
        logoutStage = 1;
        packetSender.sendDiscordPresence("At Main Menu");
        packetSender.sendLogout();
    }

    public void forceLogout() {
        logoutStage = -1;
        packetSender.sendDiscordPresence("At Main Menu");
        packetSender.sendLogout();
    }

    public void checkLogout() {
        /**
         * Validate channel & decode packets.
         */
        if (logoutStage == 0) {
            if (!channel.isActive() || decoder.timeout()) {
                channel.close();
                logoutStage = 1;
            } else {
                if (!decoder.process(this, 250))
                    Server.logWarning(name + " has suspicious packet count!");
                if (logoutStage == 0) //This player hasn't tried to log out, we good.
                    return;
            }
        }
        /**
         * Attempt to logout if a logout is required.
         */
        if (logoutStage == 1) {
            if (xLogDelay == null) {
                if (combat.isDead() || combat.isDefending(17)) {
                    xLogDelay = new TickDelay();
                    xLogDelay.delay(100); //after 60 seconds of xlogging on rs, no matter if you're in combat you dc.
                    return;
                }
            } else if (xLogDelay.isDelayed()) {
                if (combat.isDead() || combat.isDefending(17)) {
                    //This player is in combat, wait...
                    return;
                }
            }
            if (isLockedExclude(LockType.FULL_ALLOW_LOGOUT)) {
                //This player is locked, we must wait!
                return;
            }
            logoutStage = -1;
        }
        /**
         * Logout accepted, finish this player.
         */
        if (online) {
            online = false;
            finish();
        }
        /**
         * Attempt to save this player. (Player won't be fully logged out until save is complete!)
         */
        if (saveRetry != -1) {
            if (!Server.isPast(saveRetry))
                return;
            Server.logWarning(name + " is taking too long to save, retrying...");
        }
        int attempt;
        synchronized (saveLock) {
            if (++saveAttempt > Byte.MAX_VALUE)
                saveAttempt = 0;
            attempt = saveAttempt;
        }
        saveRetry = Server.getEnd(15);
        PlayerFile.save(this, attempt);
    }

    public void finishLogout(int attempt) {
        synchronized (saveLock) {
            if (attempt != saveAttempt)
                return;
        }
        World.players.remove(getIndex());
        /**
         * Misc things like SQL updates
         */
        //XenGroup.update(this);
        //Hiscores.save(this);
        Loggers.logPlayer(this);
        Loggers.updateItems(this);
        Loggers.removeOnlinePlayer(userId, World.id);
        //UltraLogger.addLog("Attempting to finish logout attempt: " + attempt, this.getLoginInfo(), "finishLogout");
    }

    /**
     * Processing
     */

    public void process() {

        processEvent();

        inventory.sendUpdates();
        equipment.sendUpdates();
        bank.sendUpdates();
        collectionLog.sendUpdates();
        lootingBag.sendUpdates();
        runePouch.sendUpdates();
        tournamentRunePouch.sendUpdates();
        box.sendUpdates();
        QuestTab.sendLoginUpdates(player);
        //BiHourlyBonusMinigame.assignMinigame();
        //IndividualTasks.pulse(player);
        // IndividualTasks.pulse(player);
        //TeamTasks.pulse(player);
        PirateShipEvent.pulse(this);
        combat.preAttack();
        TargetRoute.beforeMovement(this);
        movement.process();
        TargetRoute.afterMovement(this);

        Region region;
        if (movement.hasMoved() && lastRegion != (region = getPosition().getRegion()))
            lastRegion = region;
        validateMapListeners();

        combat.attack();

        prayer.process();
        stats.process();

        tick();
        processHits();
    }

    public int getGlodDamageCounter() {
        return raidsDamageCounters[0];
    }

    public void setGlodDamageCounter(int damage) {
        this.raidsDamageCounters[0] = damage;
    }

    public int getSerenDamageCounter() {
        return raidsDamageCounters[1];
    }

    public void setSerenDamageCounter(int damage) {
        this.raidsDamageCounters[1] = damage;
    }

    /**
     * Tick
     */

    private void tick() {
        playTime++;
        if (unratedBattlegroundTicks > 0) {
            unratedBattlegroundTicks--;
            if (unratedBattlegroundTicks == 0) {
                BattleGroundsQueue.endGame(this);
            }
        }
//        if (unrated1v1Ticks > 0) {
//            unrated1v1Ticks--;
//            if (unrated1v1Ticks == 0) {
//                Arena1v1Queue.endGame();
//            }
//        }
        if (unrated2v2Ticks > 0) {
            unrated2v2Ticks--;
            if (unrated2v2Ticks == 0) {
                Arena2v2Queue.endGame(this);
            }
        }
        if (holdthefortTicks > 0) {
            holdthefortTicks--;
            if (holdthefortTicks == 0) {
                HoldTheFort.winEvent(this);
            }
        }
        if (++appreciationTicks >= 100) {
            appreciationPoints += Random.get(5, 15);
            appreciationTicks = 0;
        }
        if (afkzoneTicks >= 1) {
            afkzoneTicks--;
            if (afkzoneTicks == 3000) {
                sendMessage(Color.RED.wrap("You have 30 minutes of AFK Zone time remaining."));
            } else if (afkzoneTicks == 0) {
                sendMessage(Color.RED.wrap("Your AFK Zone time has expired. Vote on all sites again to regain access!"));
                if (getPosition().inBounds(AFKZone.AFK_ZONE)) {
                    getMovement().teleport(3091, 3491, 0);
                }
            }
        }
        if (++specialRestoreTicks >= 50) {
            specialRestoreTicks = 0;
            combat.restoreSpecial(10);
        }
        if (antifireTicks > 0) {
            antifireTicks--;
            if (antifireTicks == 30) {
                sendMessage("<col=7f007f>Your antifire potion is about to expire.");
                privateSound(3120, 3, 0);
            } else if (antifireTicks == 0) {
                sendMessage("<col=7f007f>Your antifire potion has expired.");
                privateSound(2607, 1, 0);
            }
        }
        if (poolTicks > 0) {
            poolTicks--;
        }
        if (superAntifireTicks > 0) {
            superAntifireTicks--;
            if (superAntifireTicks == 30) {
                sendMessage("<col=7f007f>Your super antifire potion is about to expire.");
                privateSound(3120, 3, 0);
            } else if (superAntifireTicks == 0) {
                sendMessage("<col=7f007f>Your super antifire potion has expired.");
                privateSound(2607, 1, 0);
            }
        }
        if (gameModeTicks > 0) {
            gameModeTicks--;
        }
        if (staminaTicks > 0) {
            staminaTicks--;
            if (staminaTicks == 17) {
                sendMessage("<col=8f4808>Your stamina potion is about to expire.");
                privateSound(3120, 3, 0);
            } else if (staminaTicks == 0) {
                sendMessage("<col=8f4808>Your stamina potion has expired.");
                privateSound(2672, 3, 0);
                Config.STAMINA_POTION.set(this, 0);
            }
        }
        if (dragonAxeSpecial > 0) { // really need a better system to handle things like this :)
            --dragonAxeSpecial;
            if (dragonAxeSpecial == 0) {
                player.sendMessage("<col=ff0000>Your woodcutting buff has expired.</col>");
            }
        }
        if (infernalAxeSpecial > 0) {
            --infernalAxeSpecial;
            if (infernalAxeSpecial == 0) {
                player.sendMessage("<col=ff0000>Your infernal woodcutting buff has expired.</col>");
            }
        }

        if (dragonPickaxeSpecial > 0) { // really need a better system to handle things like this :)
            --dragonPickaxeSpecial;
            if (dragonPickaxeSpecial == 0) {
                player.sendMessage("<col=ff0000>Your mining buff has expired.</col>");
            }
        }
        if (infernalPickaxeSpecial > 0) {
            --infernalPickaxeSpecial;
            if (infernalPickaxeSpecial == 0) {
                player.sendMessage("<col=ff0000>Your infernal mining buff has expired.</col>");
            }
        }
        if (lastTimeKilledDonatorBoss > 0) {
            if (System.currentTimeMillis() >= lastTimeKilledDonatorBoss + 86400000) {
                player.sendMessage("<col=ff0000>You can now kill the donator boss again.</col>");
                lastTimeKilledDonatorBoss = 0;
                timesKilledDonatorBoss = 0;
            }
        }

        if (movement.hasMoved()) {
            idleTicks = 0;
            isIdle = false;
        } else if (++idleTicks >= 1000) {
            isIdle = true;
        }
        for (Item item : equipment.getItems()) {
            if (item != null && item.getDef() != null)
                item.getDef().onTick(this, item);
        }

        if (player.wildernessLevel <= 0 && !player.pvpAttackZone && player.snowballPeltOption &&
                !player.getEquipment().hasId(Christmas.SNOWBALL) && !player.getPosition().inBounds(DuelArena.BOUNDS)
        ) {
            player.setAction(1, null);
            player.snowballPeltOption = false;
        }

    }

    /**
     * Bm
     */
    public void rewardBloodMoney(Player target, int amount) {
        String format = String.format("BloodMoneyKill:[Player:[%s] Position:%s IPAddress:[%s] Target:[%s] IPAddress:[%s] Amount:[%d]]", player.name(), player.getPosition(), player.getIp(), target.name(), target.getIp(), amount);
        ServerWrapper.log(format);
        getInventory().add(13307, amount);
        sendFilteredMessage("You received <col=6f0000>" + NumberUtils.formatNumber(amount) + " blood money</col> for killing: " + target.name() + ".");
    }

    @Override
    public boolean isPoisonImmune() {
        return super.isPoisonImmune() ||
                player.getEquipment().hasId(SerpentineHelm.SERPENTINE.getChargedId()) ||
                player.getEquipment().hasId(SerpentineHelm.MAGMA.getChargedId()) ||
                player.getEquipment().hasId(SerpentineHelm.TANZANITE.getChargedId());
    }

    @Override
    public boolean isVenomImmune() {
        return super.isVenomImmune() ||
                player.getEquipment().hasId(SerpentineHelm.SERPENTINE.getChargedId()) ||
                player.getEquipment().hasId(SerpentineHelm.MAGMA.getChargedId()) ||
                player.getEquipment().hasId(SerpentineHelm.TANZANITE.getChargedId());
    }

    public GameMode getGameMode() {
        return GameMode.values()[Config.IRONMAN_MODE.get(this)];
    }

    public Title getTitle() {
        return title;
    }

    public House getCurrentHouse() {
        return getPosition().getRegion().getHouse();
    }

    public Room getCurrentRoom() {
        if (getCurrentHouse() == null)
            return null;
        return getCurrentHouse().getCurrentRoom(this);
    }

    public boolean isInOwnHouse() {
        return house != null && getCurrentHouse() == house;
    }


    public void dailyReset() {
        lastLoginDate = LocalDate.now().toString();
        DailyResetListener.executeAll(this);
    }

    public boolean showHitAsExperience() {
        return showHitAsExperience;
    }

    public ItemContainer getPrivateRaidStorage() {
        return privateRaidStorage;
    }

    public ItemContainer getUimStorage() {
        return uimStorage;
    }

    public ItemContainer getRaidRewards() {
        return raidRewards;
    }

    public static PlayerGroup getGroup(Player player) {
        if (player.storeAmountSpent >= 1000)
            return PlayerGroup.PLATINUM_DONATOR;
        if (player.storeAmountSpent >= 500)
            return PlayerGroup.PLATINUM_DONATOR;
        if (player.storeAmountSpent >= 100)
            return PlayerGroup.LEGENDARY_DONATOR;
        if (player.storeAmountSpent >= 50)
            return PlayerGroup.SUPER_DONATOR;
        if (player.storeAmountSpent >= 10)
            return PlayerGroup.DONATOR;
        return null;
    }

    public void updateRank1() {
        if (storeAmountSpent <= 0) {
            storeAmountSpent = 0;
            getPacketSender().sendString(830, 297, "Current Rank: <col=27ae60>None");
        }
        if (storeAmountSpent >= 15 && storeAmountSpent < 50) {//SAPPHIRE
            getPacketSender().sendString(830, 297, "Current Rank: <img=19> <col=2c51e2>Sapphire");
        }
        if (storeAmountSpent >= 50 && storeAmountSpent < 100) {//EMERALD
            getPacketSender().sendString(830, 297, "Current Rank: <img=20> <col=27a127>Emerald");
        }
        if (storeAmountSpent >= 100 && storeAmountSpent < 250) {//RUBY
            getPacketSender().sendString(830, 297, "Current Rank: <img=21> <col=bd0f0f>Ruby");
        }
        if (storeAmountSpent >= 250 && storeAmountSpent < 500) {//DIAMOND
            getPacketSender().sendString(830, 297, "Current Rank: <img=22> <col=ffffff>Diamond");
        }
        if (storeAmountSpent >= 500 && storeAmountSpent < 1000) {//ONYX
            getPacketSender().sendString(830, 297, "Current Rank: <img=24> <col=616060>Onyx");
        }
        if (storeAmountSpent >= 1000) {//ZENYTE
            getPacketSender().sendString(830, 297, "Current Rank: <img=25> <col=e37e1a>Zenyte");
        }
    }

    public void updateRank() {
        if (storeAmountSpent <= 0) {
            storeAmountSpent = 0;
            getPacketSender().sendString(857, 204, "Rank: <col=0fd60f>Registered");
        }
        if (storeAmountSpent >= 15 && storeAmountSpent < 50) {//SAPPHIRE
            getPacketSender().sendString(857, 204, "Rank: <img=19> <col=0fd60f>Sapphire Donator");
        }
        if (storeAmountSpent >= 50 && storeAmountSpent < 100) {//EMERALD
            getPacketSender().sendString(857, 204, "Rank: <img=20> <col=0fd60f>Emerald Donator");
        }
        if (storeAmountSpent >= 100 && storeAmountSpent < 250) {//RUBY
            getPacketSender().sendString(857, 204, "Rank: <img=21> <col=0fd60f>Ruby Donator");
        }
        if (storeAmountSpent >= 250 && storeAmountSpent < 500) {//DIAMOND
            getPacketSender().sendString(857, 204, "Rank: <img=22> <col=0fd60f>Diamond Donator");
        }
        if (storeAmountSpent >= 500 && storeAmountSpent < 1000) {//ONYX
            getPacketSender().sendString(857, 204, "Rank: <img=24> <col=0fd60f>Onyx Donator");
        }
        if (storeAmountSpent >= 1000) {//ZENYTE
            getPacketSender().sendString(857, 204, "Rank: <img=25> <col=0fd60f>Zenyte Donator");
        }
    }

    public void rewardsDialogue(String line) {
        this.dialogue(new NPCDialogue(7045, line));
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    public void onDialogueContinued() {
        if (onDialogueContinued != null) {
            onDialogueContinued.run();
            System.out.println("Running onDialogueContinued runnable.");
        }
        onDialogueContinued = null;
    }

    public DynamicMap dynamicMap;

    public int getIconForProtocol() {
        if (this.isAdmin())
            return 2;
        else if (this.isModerator())
            return 1;
        else if (this.isSupport())
            return 8;
        return 0;//normal player
    }

    @Expose
    @Getter
    @Setter
    public PlayerBounty playerBounty;

    @Expose
    @Getter
    @Setter
    public PerkManager perkManager;

    public ActionLogger logger = new ActionLogger(this);

    public void restorePrayerPoints(int amount) {
        Stat stat = getStats().get(StatType.Prayer);
        stat.restore(amount, 0);
        getStats().get(StatType.Prayer).alter(stat.currentLevel);
    }

    public boolean isBot;

    @Expose
    @Getter
    @Setter
    public boolean isBanned;

    public boolean inWilderness() {
        return this.wildernessLevel > 0;
    }

    public void combatSound(int id) {
        if (id == -1)
            return;
        privateSound(id);
        PlayerCombat cb = this.getCombat();
        if (cb.getTarget() == null)
            return;
        if (cb.getTarget() instanceof Player) {
            cb.getTarget().player.privateSound(id);
        }
    }

    public boolean hasOneHourPlayed() {
        return datedRegisteredLong + (60_000 * 60) < System.currentTimeMillis();
    }

    @Getter @Setter @Expose
    private PresetManager presets;
}