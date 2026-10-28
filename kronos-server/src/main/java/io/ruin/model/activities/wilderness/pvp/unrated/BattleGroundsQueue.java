package io.ruin.model.activities.wilderness.pvp.unrated;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.utility.TickDelay;

import java.util.ArrayList;

public class BattleGroundsQueue {

    private static int lobbySize() { return playersTeamOne.size() + playersTeamTwo.size(); }

    public static TickDelay gameStarts = new TickDelay();

    public static BattleGrounds game;

    private static final int MINIMUM_PARTY_SIZE = 2;

    public static BattleGroundsSettings settings;

    private static final ArrayList<Player> playersTeamOne = new ArrayList<>();

    private static final ArrayList<Player> playersTeamTwo = new ArrayList<>();

    /**
     * Constructs a new {@link BattleGroundsQueue} instance with the respective properties.
     * @param settings
     */
    BattleGroundsQueue(BattleGroundsSettings settings) {
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
        if (playersTeamOne.stream().anyMatch(p -> p == player) || playersTeamTwo.stream().anyMatch(p -> p == player)) {
            player.sendMessage("You are already queued for a battleground.");
            return;
        }
        if (player.getCombat().getLevel() < 115) {
            player.dialogue(new MessageDialogue("You must be combat level 115 to enter Battlegrounds."));
            return;
        }
        if (lobbySize() >= 1) {
            playersTeamTwo.add(player);
            player.openInterface(InterfaceType.TARGET_OVERLAY, 822);
            player.closeInterface(InterfaceType.MAIN);
            player.getPacketSender().sendString(822, 134, "    Unrated Battlegrounds");
            player.getPacketSender().sendString(822, 135, + lobbySize() +"/20");
            player.sendMessage("You have joined the queue for: 10v10 Unrated Battlegrounds");
            player.sendMessage("team two");
        } else {
            playersTeamOne.add(player);
            player.openInterface(InterfaceType.TARGET_OVERLAY, 822);
            player.closeInterface(InterfaceType.MAIN);
            player.getPacketSender().sendString(822, 134, "    Unrated Battlegrounds");
            player.getPacketSender().sendString(822, 135, +lobbySize() + "/20");
            player.sendMessage("You have joined the queue for: 10v10 Unrated Battlegrounds");
            player.sendMessage("team one");
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
        playersTeamOne.removeIf(p -> p == player);
        playersTeamTwo.removeIf(p -> p == player);
        player.closeInterface(InterfaceType.TARGET_OVERLAY);
        player.logoutListener = null;
        player.teleportListener = null;
        player.unlock();
        player.sendMessage("You have left the queue.");
        player.unratedBattlegroundTicks = 0;
    }
    public static void leave(Player player) {
        player.lock();
        playersTeamOne.removeIf(p -> p == player);
        playersTeamTwo.removeIf(p -> p == player);
        player.closeInterface(InterfaceType.TARGET_OVERLAY);
        player.logoutListener = null;
        player.teleportListener = null;
        player.unlock();
        player.sendMessage("method 'leave' worked here");
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
     * Starts a new {@link BattleGrounds} session and inherits the specified difficulty.
     */
    public static void startWaitingRoom() {
        game = new BattleGrounds(settings);
        playersTeamOne.forEach(player -> player.sendMessage("The battle will begin in 60 seconds!"));
        playersTeamOne.forEach(player -> player.getMovement().teleport(3022, 4126, player.getHeight()*4));
        playersTeamTwo.forEach(player -> player.sendMessage("The battle will begin in 60 seconds!"));
        playersTeamTwo.forEach(player -> player.getMovement().teleport(3060, 4125, player.getHeight()*4));
        World.startEvent(e -> {
            while(true) {
                e.delay(10);
                startGame();
                return;
            }
        });
    }

    public static void startGame() {
        playersTeamOne.forEach(player -> player.sendMessage("<col=7f007f>The battleground has begun!"));
        playersTeamTwo.forEach(player -> player.sendMessage("<col=7f007f>The battleground has begun!"));
        playersTeamOne.forEach(player -> player.openInterface(InterfaceType.TARGET_OVERLAY, 822));
        playersTeamTwo.forEach(player -> player.openInterface(InterfaceType.TARGET_OVERLAY, 822));
        playersTeamOne.forEach(player -> player.closeInterface(InterfaceType.MAIN));
        playersTeamTwo.forEach(player -> player.closeInterface(InterfaceType.MAIN));
        playersTeamOne.forEach(player -> player.getPacketSender().sendString(822, 134, "    Players Remaining"));
        playersTeamTwo.forEach(player -> player.getPacketSender().sendString(822, 134, "    Players Remaining"));
        playersTeamOne.forEach(player -> startGameTimer());
        playersTeamTwo.forEach(player -> startGameTimer());
        playersTeamOne.forEach(player -> player.getPacketSender().sendString(822, 135, +player.unratedBattlegroundTicks+""));
        playersTeamTwo.forEach(player -> player.getPacketSender().sendString(822, 135, +player.unratedBattlegroundTicks+""));
        playersTeamOne.forEach(player -> player.unratedBattlegroundTicks--);
        playersTeamTwo.forEach(player -> player.unratedBattlegroundTicks--);

        //playersTeamOne.forEach(player -> player.setAction(1, PlayerAction.ATTACK));
        //playersTeamTwo.forEach(player -> player.setAction(1, PlayerAction.ATTACK));
       /* player.deathEndListener = (DeathListener.Simple) () -> {
            player.getMovement().teleport(FFA_DEATH_BOUNDS.randomPosition());
            player.sendMessage("Oh dear, you have died!");
        };*/
    }

    public static void startGameTimer() {
        playersTeamOne.forEach(player -> player.unratedBattlegroundTicks += 60);
        playersTeamTwo.forEach(player -> player.unratedBattlegroundTicks += 60);
        playersTeamOne.forEach(player -> player.getPacketSender().sendWidget(Widget.EXTENDED_ANTIFIRE, (int) (100 * 0.6)));
        playersTeamTwo.forEach(player -> player.getPacketSender().sendWidget(Widget.EXTENDED_ANTIFIRE, (int) (100 * 0.6)));
    }

    public static void endGame(Player player) {
        playersTeamOne.forEach(p -> leave(player));
        playersTeamTwo.forEach(p -> leave(player));
        playersTeamOne.forEach(p -> player.logoutListener = null);
        playersTeamTwo.forEach(p -> player.logoutListener = null);
        playersTeamOne.forEach(p -> player.closeInterface(InterfaceType.TARGET_OVERLAY));
        playersTeamOne.forEach(p -> player.getMovement().teleport(3104, 3484, 0));
        playersTeamOne.forEach(p -> player.sendMessage("<col=7f007f>The battleground has ended"));
        playersTeamTwo.forEach(p -> player.closeInterface(InterfaceType.TARGET_OVERLAY));
        playersTeamTwo.forEach(p -> player.getMovement().teleport(3104, 3484, 0));
        playersTeamTwo.forEach(p -> player.sendMessage("<col=7f007f>The battleground has ended"));
        playersTeamOne.clear();
        playersTeamTwo.clear();
        playersTeamOne.forEach(p -> player.unratedBattlegroundTicks = 0);
        playersTeamTwo.forEach(p -> player.unratedBattlegroundTicks = 0);
        game.ended();
    }

    private static void updateOverlay() {
        if (lobbySize() >= MINIMUM_PARTY_SIZE) {
            startWaitingRoom();
        }
    }

}