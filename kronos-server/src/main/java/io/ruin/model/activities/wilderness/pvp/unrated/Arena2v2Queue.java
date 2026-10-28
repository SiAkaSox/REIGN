package io.ruin.model.activities.wilderness.pvp.unrated;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.utility.TickDelay;

import java.util.ArrayList;

public class Arena2v2Queue {

    private static int lobbySize() { return players3v3TeamOne.size() + players3v3TeamTwo.size(); }

    public static TickDelay gameStarts = new TickDelay();

    public static Arena2v2 game;

    private static final int MINIMUM_PARTY_SIZE = 2;

    public static Arena2v2Settings settings;

    private static final ArrayList<Player> players3v3TeamOne = new ArrayList<>();

    private static final ArrayList<Player> players3v3TeamTwo = new ArrayList<>();

    /**
     * Constructs a new {@link Arena2v2Queue} instance with the respective properties.
     * @param settings
     */
    Arena2v2Queue(Arena2v2Settings settings) {
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
     * @param player
     * @return
     */
    public static void joinQueue(Player player) {
        if (players3v3TeamOne.stream().anyMatch(p -> p == player) || players3v3TeamTwo.stream().anyMatch(p -> p == player)) {
            player.sendMessage("You are already queued for an Arena.");
            return;
        }
        if (player.getCombat().getLevel() < 115) {
            player.dialogue(new MessageDialogue("You must be combat level 115 to enter Arenas."));
            return;
        }
        if (lobbySize() >= 1) {
            players3v3TeamTwo.add(player);
            player.openInterface(InterfaceType.TARGET_OVERLAY, 822);
            player.closeInterface(InterfaceType.MAIN);
            player.getPacketSender().sendString(822, 134, " Unrated 3v3 Arena");
            player.getPacketSender().sendString(822, 135, + lobbySize() +"/4");
            player.sendMessage("You have joined the queue for: 3v3 Unrated Arena");
            //player.sendMessage("team two");
        } else {
            players3v3TeamOne.add(player);
            player.openInterface(InterfaceType.TARGET_OVERLAY, 822);
            player.closeInterface(InterfaceType.MAIN);
            player.getPacketSender().sendString(822, 134, "    Unrated 3v3 Arena");
            player.getPacketSender().sendString(822, 135, + lobbySize() +"/4");
            player.sendMessage("You have joined the queue for: 3v3 Unrated Arena");
            //player.sendMessage("team one");
        }
        updateOverlay();
        player.teleportListener = p -> {
            leaveQueue(p);
            return true;
        };
        player.logoutListener = new LogoutListener().onLogout(p -> {
            leaveQueue(p);
            p.getMovement().teleport(3092, 3508, 0);
        });
        player.unlock();
    }

    public static void leaveQueue(Player player) {
        player.lock();
        players3v3TeamOne.removeIf(p -> p == player);
        players3v3TeamTwo.removeIf(p -> p == player);
        player.closeInterface(InterfaceType.TARGET_OVERLAY);
        player.logoutListener = null;
        player.teleportListener = null;
        player.unlock();
        player.sendMessage("You have left the queue.");
        player.unrated2v2Ticks = 0;
    }
    public static void leave(Player player) {
        player.lock();
        players3v3TeamOne.removeIf(p -> p == player);
        players3v3TeamTwo.removeIf(p -> p == player);
        player.closeInterface(InterfaceType.TARGET_OVERLAY);
        player.logoutListener = null;
        player.teleportListener = null;
        player.unlock();
        //player.sendMessage("method 'leave' worked here");
        //player.unratedBattlegroundTicks = 0;
        //playersTeamOne.forEach(p -> p.unratedBattlegroundTicks = 0);
        //playersTeamTwo.forEach(p -> p.unratedBattlegroundTicks = 0);
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
     * Starts a new {@link Arena2v2} session and inherits the specified difficulty.
     */
    public static void startWaitingRoom() {
        game = new Arena2v2(settings);
        players3v3TeamOne.forEach(player -> player.sendMessage("The battle will begin in 60 seconds!"));
        players3v3TeamOne.forEach(player -> player.getMovement().teleport(3022, 4126, player.getHeight()*4));
        players3v3TeamTwo.forEach(player -> player.sendMessage("The battle will begin in 60 seconds!"));
        players3v3TeamTwo.forEach(player -> player.getMovement().teleport(3060, 4125, player.getHeight()*4));
        World.startEvent(e -> {
            while(true) {
                e.delay(10);
                startGame();
                return;
            }
        });
    }

    public static void startGame() {
        players3v3TeamOne.forEach(player -> player.sendMessage("<col=7f007f>The arena has begun!"));
        players3v3TeamTwo.forEach(player -> player.sendMessage("<col=7f007f>The arena has begun!"));
        players3v3TeamOne.forEach(player -> player.openInterface(InterfaceType.TARGET_OVERLAY, 822));
        players3v3TeamTwo.forEach(player -> player.openInterface(InterfaceType.TARGET_OVERLAY, 822));
        players3v3TeamOne.forEach(player -> player.closeInterface(InterfaceType.MAIN));
        players3v3TeamTwo.forEach(player -> player.closeInterface(InterfaceType.MAIN));
        players3v3TeamOne.forEach(player -> player.getPacketSender().sendString(822, 134, "Players Remaining"));
        players3v3TeamTwo.forEach(player -> player.getPacketSender().sendString(822, 134, "Players Remaining"));
        players3v3TeamOne.forEach(player -> startGameTimer());
        players3v3TeamTwo.forEach(player -> startGameTimer());
        players3v3TeamOne.forEach(player -> player.getPacketSender().sendString(822, 135, +player.unrated2v2Ticks +""));
        players3v3TeamTwo.forEach(player -> player.getPacketSender().sendString(822, 135, +player.unrated2v2Ticks +""));
        players3v3TeamOne.forEach(player -> player.unrated2v2Ticks--);
        players3v3TeamTwo.forEach(player -> player.unrated2v2Ticks--);

        //playersTeamOne.forEach(player -> player.setAction(1, PlayerAction.ATTACK));
        //playersTeamTwo.forEach(player -> player.setAction(1, PlayerAction.ATTACK));
       /* player.deathEndListener = (DeathListener.Simple) () -> {
            player.getMovement().teleport(FFA_DEATH_BOUNDS.randomPosition());
            player.sendMessage("Oh dear, you have died!");
        };*/
    }

    public static void startGameTimer() {
        players3v3TeamOne.forEach(player -> player.unrated2v2Ticks += 60);
        players3v3TeamTwo.forEach(player -> player.unrated2v2Ticks += 60);
        players3v3TeamOne.forEach(player -> player.getPacketSender().sendWidget(Widget.EXTENDED_ANTIFIRE, (int) (100 * 0.6)));
        players3v3TeamTwo.forEach(player -> player.getPacketSender().sendWidget(Widget.EXTENDED_ANTIFIRE, (int) (100 * 0.6)));
    }

    public static void endGame(Player player) {
        players3v3TeamOne.forEach(p -> leave(player));
        players3v3TeamTwo.forEach(p -> leave(player));
        players3v3TeamOne.forEach(p -> player.logoutListener = null);
        players3v3TeamTwo.forEach(p -> player.logoutListener = null);
        players3v3TeamOne.forEach(p -> player.closeInterface(InterfaceType.TARGET_OVERLAY));
        players3v3TeamOne.forEach(p -> player.getMovement().teleport(3104, 3484, 0));
        players3v3TeamOne.forEach(p -> player.sendMessage("<col=7f007f>The arena has ended"));
        players3v3TeamTwo.forEach(p -> player.closeInterface(InterfaceType.TARGET_OVERLAY));
        players3v3TeamTwo.forEach(p -> player.getMovement().teleport(3104, 3484, 0));
        players3v3TeamTwo.forEach(p -> player.sendMessage("<col=7f007f>The arena has ended"));
        players3v3TeamOne.clear();
        players3v3TeamTwo.clear();
        players3v3TeamOne.forEach(p -> player.unrated2v2Ticks = 0);
        players3v3TeamTwo.forEach(p -> player.unrated2v2Ticks = 0);
        game.ended();
    }

    private static void updateOverlay() {
        if (lobbySize() >= MINIMUM_PARTY_SIZE) {
            startWaitingRoom();
        }
    }

}