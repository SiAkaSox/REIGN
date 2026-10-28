package io.ruin.model.activities.wilderness.pvp.rated.ones;

import io.ruin.model.World;
import io.ruin.model.achievements.listeners.hard.ArenaChampion;
import io.ruin.model.achievements.listeners.medium.ArenaNovice;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.GameObject;
import io.ruin.utility.TickDelay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rated1v1Queue extends ItemContainer {

    public static final Bounds ARENA_BOUNDS = new Bounds(new Position(2719, 5646, 2), 32);

    private static int lobbySize() { return lobbyPlayers.size(); }

    public static TickDelay gameStarts = new TickDelay();

    public static Rated1v1 game;

    private static final int MINIMUM_PARTY_SIZE = 2;

    public static Rated1v1Settings settings;

    public static boolean inArena = false;

    private static final List<Player> gamePlayers = new ArrayList<>();

    private static final List<Player> lobbyPlayers = new ArrayList<>();

    /**
     * Constructs a new {@link Rated1v1Queue} instance with the respective properties.
     * @param settings
     */
    Rated1v1Queue(Rated1v1Settings settings) {
        this.settings = settings;
        gameStarts.delay(1000 * 60 * 6 / 600);
        World.startEvent(e -> {
            while(true) {
                e.delay(5);
                pulse();
            }
        });
    }

    /**
     * Processes all actions upon the player entering the queue, if applicable.
     * @param p
     * @return
     */
    public static void joinQueue(Player p) {
        if (lobbyPlayers.stream().anyMatch(player -> player == p)) {
            p.sendMessage("You are already queued for an Arena.");
            return;
        }
        if (p.getCombat().getLevel() < 115) {
            p.dialogue(new MessageDialogue("You must be combat level 115 to enter Arenas."));
            return;
        }

        gamePlayers.add(p);
        lobbyPlayers.add(p);
        p.openInterface(InterfaceType.TARGET_OVERLAY, 822);
        p.getPacketSender().sendString(822, 134, " 1v1 Arena");
        p.getPacketSender().sendString(822, 135, + lobbySize() +"/2");
        p.dialogue(new MessageDialogue("You have joined the queue for: 1v1 Rated Arena"));
        updateOverlay();

        if (lobbySize() >= 2) {
            startArena();
        }

        p.teleportListener = player -> {
            leaveQueue(player);
            return true;
        };
        p.logoutListener = new LogoutListener().onLogout(player -> {
            leaveQueue(player);
            player.getMovement().teleport(3092, 3508, 0);
        });
        p.unlock();
    }

    private static void startArena() {
        if (lobbySize() < 2) {
            return;
        }

        Collections.shuffle(lobbyPlayers);

        Player player1 = lobbyPlayers.get(0);
        Player player2 = lobbyPlayers.get(1);

        lobbyPlayers.remove(player1);
        lobbyPlayers.remove(player2);

        startWaitingRoom();
    }

    public static void leaveQueue(Player player) {
        if (lobbyPlayers.stream().anyMatch(p -> p == player)) {
            gamePlayers.removeIf(p -> p == player);
            lobbyPlayers.removeIf(p -> p == player);
            player.closeInterface(InterfaceType.TARGET_OVERLAY);
            player.logoutListener = null;
            player.teleportListener = null;
            player.unlock();
            player.sendMessage("You have left the queue.");
            player.rated1v1Ticks = 0;
        }
    }

    private void pulse() {
        if (lobbySize() >= MINIMUM_PARTY_SIZE) {
            if (lobbySize() < MINIMUM_PARTY_SIZE) {
                gameStarts.delay(1000 * 60 * 2 / 600);
            } else {
                if (game == null) {
                    startWaitingRoom();
                    gameStarts.delay(1000 * 60 * 6 / 600);
                }
            }
        }
        updateOverlay();
    }

    /**
     * Starts a new {@link Rated1v1} session and inherits the specified difficulty.
     */
    public static void startWaitingRoom() {
        DynamicMap map = new DynamicMap();
        map.build(ARENA_BOUNDS);
        spawnPortaldoors();
        game = new Rated1v1(settings);
        gamePlayers.forEach(player -> player.closeInterface(InterfaceType.TARGET_OVERLAY));
        Player player1 = gamePlayers.get(0);
        Player player2 = gamePlayers.get(1);
        player1.sendMessage("<col=7f007f>The arena will begin in 30 seconds!");
        player2.sendMessage("<col=7f007f>The arena will begin in 30 seconds!");
        player1.getMovement().teleport(map.swRegion.baseX + 39, map.swRegion.baseY + 35, 2);
        player2.getMovement().teleport(map.swRegion.baseX + 39, map.swRegion.baseY + 66, 2);
        startGameTimer();
        World.startEvent(e -> {
            while(true) {
                e.delay(50);
                player1.getMovement().teleport(map.swRegion.baseX + 39, map.swRegion.baseY + 38, 2);
                player2.getMovement().teleport(map.swRegion.baseX + 39, map.swRegion.baseY + 63, 2);
                startGame();
                return;
            }
        });
    }

    public static boolean onteamOne = false;
    public static GameObject portaldoor1 = null;
    public static GameObject portaldoor2 = null;

    private static void spawnPortaldoors() {
        portaldoor1 = GameObject.spawn(4470, 2719, 5644, 2, 10, 0);
        portaldoor2 = GameObject.spawn(4470, 2719, 5672, 2, 10, 0);
    }

    public static void startGame() {
        lobbyPlayers.clear();
        Player player1 = gamePlayers.get(0);
        Player player2 = gamePlayers.get(1);
        player1.sendMessage("<col=7f007f>The arena has begun!");
        player2.sendMessage("<col=7f007f>The arena has begun!");

        gamePlayers.forEach(player -> inArena = true);

        gamePlayers.forEach(player -> player.setAction(1, PlayerAction.ATTACK));
        gamePlayers.forEach(player -> player.closeInterface(InterfaceType.TARGET_OVERLAY));

        player1.teleportListener = Rated1v1Queue::allowTeleport;
        player2.teleportListener = Rated1v1Queue::allowTeleport;

        player2.deathEndListener = (entity, killer, hit) -> {
            if (entity instanceof Player) {
                player2.sendMessage("Oh dear, you have died!");
                player2.sendMessage("<col=FF0000>You lost in the Arena, and did not receive any rewards.");
                normalize(player2);
                teamoneKills++;
                awardPlayer(player1, 0);
                endGame();
                player2.unlock();
            }
        };
        player1.deathEndListener = (entity, killer, hit) -> {
            if (entity instanceof Player) {
                player1.sendMessage("Oh dear, you have died!");
                player1.sendMessage("<col=FF0000>You lost in the Arena, and did not receive any rewards.");
                normalize(player1);
                teamtwoKills++;
                awardPlayer(player2, 1);
                endGame();
                player1.unlock();
            }
        };
    }
    public static Position exitTile() {
        return exitPos;
    }

    private static Position exitPos = new Position(3101, 3513, 0);

    public static void startGameTimer() {
        gamePlayers.forEach(player -> player.rated1v1Ticks += 100);
        gamePlayers.forEach(player -> player.getPacketSender().sendWidget(Widget.EXTENDED_ANTIFIRE, (int) (50 * 0.6)));
        gamePlayers.forEach(player -> player.rated1v1Ticks--);
    }

    private static boolean allowTeleport(Player player) {
        if (inArena) {
            player.sendMessage("You cannot teleport during an arena!");
            return false;
        }
        return true;
    }

    public static String queueSize() {
        return "Lobby size: "+lobbyPlayers.size()+" In arena: "+gamePlayers.size();
    }

    public static void clearArena(Player p) {
        lobbyPlayers.clear();
        gamePlayers.clear();
        p.sendMessage("Lobby and arena players cleared");
        p.sendMessage("Lobby size: "+lobbyPlayers.size()+" In arena: "+gamePlayers.size());
    }

    public static int teamoneKills;
    public static int teamtwoKills;

    public static void endGame() {
        gamePlayers.forEach(player ->
                player.startEvent(e -> {
                    player.unlock();
                    player.resetActions(true, true, true);
                    player.setAction(1, null);
                    player.sendMessage("<col=7f007f>The arena has ended");
                    player.closeInterface(InterfaceType.TARGET_OVERLAY);
                    player.closeInterfaces();
                    player.getMovement().teleport(3102, 3513, 0);
                    player.logoutListener = null;
                    player.teleportListener = null;
                    inArena = false;
                    e.delay(1);
                    player.rated1v1Ticks = 0;
                    onteamOne = false;
                    game.ended();
                }));
        gamePlayers.clear();
    }

    private static void updateOverlay() {
        if (lobbySize() >= MINIMUM_PARTY_SIZE) {
            startWaitingRoom();
        }
    }

    public static void normalize(Player p) {
        p.attackPlayerListener = null;
        p.teleportListener = null;
        p.logoutListener = null;
        p.deathEndListener = null;
        p.setAction(1, null);
        p.getCombat().restore();
        p.getCombat().reset();
        p.getCombat().setDead(false);
    }

    static {
        MapListener.registerBounds(ARENA_BOUNDS)
                .onEnter(player -> {
                    player.setAction(1, PlayerAction.ATTACK);
                    player.attackPlayerListener = Rated1v1Queue::allowAttack;
                    player.deathEndListener = (DeathListener.Simple) () -> {
                        player.sendMessage("Oh dear, you have died!");
                        normalize(player);
                    };
                })
                .onExit((player, logout) -> {
                    normalize(player);
                    player.setAction(1, null);
                    player.getCombat().resetTb();
                    player.getCombat().resetKillers();
                    Config.IN_PVP_AREA.set(player, 0);
                    player.clearHits();
                    player.pvpAttackZone = false;
                    player.attackPlayerListener = null;
                    player.deathEndListener = null;
                });
    }

    private static boolean allowAttack(Player player, Player pTarget, boolean message) {
//        if(!player.getPosition().inBounds(ARENA_BOUNDS)) {
//            player.sendMessage("You can't attack players from where you're standing.");
//            return false;
//        }
//        if(!pTarget.getPosition().inBounds(ARENA_BOUNDS)) {
//            player.sendMessage("You can't attack players who have already been killed.");
//            return false;
//        }
        return true;
    }

    static int rewardAmount = 15;

    public static void awardPlayer(Player p, int index) {
        if (p.storeAmountSpent >= 50 && p.storeAmountSpent < 100) {
            rewardAmount = 20;
        }
        if (p.storeAmountSpent >= 100 && p.storeAmountSpent < 250) {
            rewardAmount = 25;
        }
        if (p.storeAmountSpent >= 250 && p.storeAmountSpent < 500) {
            rewardAmount = 30;
        }
        if (p.storeAmountSpent >= 500 && p.storeAmountSpent < 1000) {
            rewardAmount = 35;
        }
        if (p.storeAmountSpent >= 1000) {
            rewardAmount = 25;
        }
        Player p1 = gamePlayers.get(0);
        Player p2 = gamePlayers.get(1);
        if (index == 0) {
            ArenaNovice.complete(p1);
            p1.pkPoints += rewardAmount;
            p1.pvpRating += 50;
            p1.rated1v1Wins++;
            p1.rated1v1Kills++;
            p1.sendMessage("<col=FF0000>You have successfully won the 1v1 Arena!");
            p1.sendMessage("<col=FF0000>You were awarded "+rewardAmount+" Pk Points, 50 PvP Rating & x1 Spoils of War for the victory.");
            p1.getInventory().addOrDrop(25342, 1);

        }
        if (index == 1) {
            ArenaNovice.complete(p2);
            p2.pkPoints += rewardAmount;
            p2.pvpRating += 50;
            p2.rated1v1Wins++;
            p2.rated1v1Kills++;
            p2.sendMessage("<col=FF0000>You have successfully won the 1v1 Arena!");
            p2.sendMessage("<col=FF0000>You were awarded "+rewardAmount+" Pk Points, 50 PvP Rating & x1 Spoils of War for the victory.");
            p2.getInventory().addOrDrop(25342, 1);
        }
    }

    public static void rewardTeams() {
        Player player1 = gamePlayers.get(0);
        Player player2 = gamePlayers.get(1);
        if (teamtwoKills > teamoneKills) {
            if (player2.getInventory().hasFreeSlots(0)) {
                player2.getBank().add(25342, 1);
                player2.sendMessage("Your inventory is full, and your reward has been sent to your bank.");
            } else {
                player2.getInventory().add(25342, 1);
            }
            player2.pkPoints += rewardAmount;
            player2.pvpRating += 50;
            player2.rated1v1Wins++;
            player2.rated1v1Kills++;
            player2.sendMessage("<col=FF0000>You have successfully won the 1v1 Arena!");
            player2.sendMessage("<col=FF0000>You were awarded "+rewardAmount+" Pk Points, 50 PvP Rating & x1 Spoils of War for the victory.");
            player1.sendMessage("<col=FF0000>You lost in the Arena, and did not receive any rewards.");
        }
        if (teamoneKills > teamtwoKills) {
            if (player1.getInventory().hasFreeSlots(0)) {
                player1.getBank().add(25342, 1);
                player1.sendMessage("Your inventory is full, and your reward has been sent to your bank.");
            } else {
                player1.getInventory().add(25342, 1);
            }
            if (player1.storeAmountSpent >= 50 && player1.storeAmountSpent < 100) {
                rewardAmount = 20;
            }
            if (player1.storeAmountSpent >= 100 && player1.storeAmountSpent < 250) {
                rewardAmount = 25;
            }
            if (player1.storeAmountSpent >= 250 && player1.storeAmountSpent < 500) {
                rewardAmount = 30;
            }
            if (player1.storeAmountSpent >= 500 && player1.storeAmountSpent < 1000) {
                rewardAmount = 35;
            }
            if (player1.storeAmountSpent >= 1000) {
                rewardAmount = 25;
            }
            ArenaNovice.complete(player1);
            player1.pkPoints += rewardAmount;
            player1.pvpRating += 50;
            player1.rated1v1Wins++;
            player1.rated1v1Kills++;
            player1.sendMessage("<col=FF0000>You have successfully won the 1v1 Arena!");
            player1.sendMessage("<col=FF0000>You were awarded 15 Pk Points, 50 PvP Rating & x1 spoils of war for the victory.");
            player2.sendMessage("<col=FF0000>You lost in the Arena, and did not receive any rewards.");
        }
    }



}