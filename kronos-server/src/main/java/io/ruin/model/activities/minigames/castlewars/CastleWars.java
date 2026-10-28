package io.ruin.model.activities.minigames.castlewars;

import io.ruin.Server;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static io.ruin.Server.STARTUP_THREAD;
import static io.ruin.model.activities.minigames.castlewars.CastleWarsRewards.giveRewards;

/**
 * @author Jaxx on 7/17/2023
 * https://www.rune-server.ee/members/Jaxx_reign/
 */

public class CastleWars {

    private static int ZAMORAK_TEAM_SIZE;
    private static int ZAMORAK_MAX_SIZE = 1;
    private static int SARADOMIN_TEAM_SIZE;
    private static int SARADOMIN_MAX_SIZE = 1;
    private static final int FLAG_CAPTURE_THRESHOLD = 3; //3 flags to win
    private static final int GAME_DURATION = 10 * 60 * 1000; //10 minutes

    public static final Position ZAMORAK_RESPAWN_LOC = new Position(2372, 3132, 1);
    public static final Position SARADOMIN_RESPAWN_LOC = new Position(2428, 3075, 1);
    public static final Position CASTLE_WARS_LOBBY = new Position(2439, 3090, 0);

    public static int ZAMORAK_PORTAL = 4388;
    public static int ZAMORAK_EXIT_PORTAL = 4390;
    public static int SARADOMIN_PORTAL = 4387;
    public static int SARADOMIN_EXIT_PORTAL = 4389;

    public static int SARADOMIN_FLAG = 4902;
    public static int ZAMORAK_FLAG = 4903;

    private final ArrayList<Player> zamorakTeam = new ArrayList<>();
    private final ArrayList<Player> saradominTeam = new ArrayList<>();

    private int lobbySize() {
        return ZAMORAK_TEAM_SIZE + SARADOMIN_TEAM_SIZE;
    }

    public static boolean queuedOnTeam = false;

    private final Player player;

    private int zamorakScore;
    private int saradominScore;

    private final Map<String, Integer> flagCaptures;

    private boolean gameStarted;
    private final Timer gameTimer;

    CastleWars(Player player) {
        this.player = player;
        zamorakScore = 0;
        saradominScore = 0;
        flagCaptures = new HashMap<>();
        gameStarted = false;
        gameTimer = new Timer();
    }

    static {
        ObjectAction.register(ZAMORAK_PORTAL, "Enter", (player, obj) -> {
            CastleWars castleWars = new CastleWars(player);
            castleWars.addPlayerToZamorakTeam(player);
        });
        ObjectAction.register(ZAMORAK_EXIT_PORTAL, "Exit", (player, obj) -> {
            CastleWars castleWars = new CastleWars(player);
            castleWars.removePlayerFromTeam(player);
            player.getMovement().teleport(2439, 3089, 0);
            player.sendMessage("You leave the Zamorak team queue");
        });
        ObjectAction.register(SARADOMIN_PORTAL, "Enter", (player, obj) -> {
            CastleWars castleWars = new CastleWars(player);
            castleWars.addPlayerToSaradominTeam(player);
        });
        ObjectAction.register(SARADOMIN_EXIT_PORTAL, "Exit", (player, obj) -> {
            CastleWars castleWars = new CastleWars(player);
            castleWars.removePlayerFromTeam(player);
            player.getMovement().teleport(2439, 3089, 0);
            player.sendMessage("You leave the Saradomin team queue");
        });
        ObjectAction.register(ZAMORAK_FLAG, "Capture", (player, obj) -> {
            CastleWars castleWars = new CastleWars(player);
            castleWars.captureFlag("A", player);
        });
        ObjectAction.register(SARADOMIN_FLAG, "Capture", (player, obj) -> {
            CastleWars castleWars = new CastleWars(player);
            castleWars.captureFlag("B", player);
        });
    }

    public void addPlayerToZamorakTeam(Player p) {
        //Add the player to the team they seleceted if not full
        if (ZAMORAK_TEAM_SIZE != ZAMORAK_MAX_SIZE) {
            zamorakTeam.add(p);
            ZAMORAK_TEAM_SIZE++;
            queuedOnTeam = true;
            player.sendMessage("You have joined the queue on the Zamorak team");
            player.getMovement().teleport(2424, 9526, 0);
            System.out.println("Team A Size: " + ZAMORAK_TEAM_SIZE + " Total Size: " + lobbySize());
            checkIfFull();
        } else {
            player.dialogue(
                    new MessageDialogue("This team is currently full. Try joining Zamorak instead.")
            );
        }
    }

    public void addPlayerToSaradominTeam(Player p) {
        gameStarted = false;
        if (SARADOMIN_TEAM_SIZE >= SARADOMIN_MAX_SIZE) {
            player.dialogue(
                    new MessageDialogue("This team is currently full. Try joining Zamorak instead.")
            );
        } else {
            saradominTeam.add(p);
            SARADOMIN_TEAM_SIZE++;
            queuedOnTeam = true;
            player.sendMessage("You have joined the queue on the Saradomin team");
            player.getMovement().teleport(2378, 9486, 0);
            System.out.println("Team B Size: " + SARADOMIN_TEAM_SIZE + " Total Size: " + lobbySize());
            checkIfFull();
        }
    }

    public void checkIfFull() {
        if (lobbySize() == 2 && !gameStarted) {
            startGame();
            System.out.println("Game started!");
        }
    }

    public void pulse() {
        if (queuedOnTeam) {
            player.teleportListener = p -> {
                removePlayerFromTeam(player);
                return true;
            };
            player.logoutListener = new LogoutListener().onLogout(p -> {
                removePlayerFromTeam(player);
                p.getMovement().teleport(World.EDGEHOME);
            });
        }
    }

    public void removePlayerFromTeam(Player playerName) {
        if (zamorakTeam.contains(playerName)) {
            zamorakTeam.remove(playerName);
            ZAMORAK_TEAM_SIZE--;
            queuedOnTeam = false;
        } else if (saradominTeam.contains(playerName)) {
            saradominTeam.remove(playerName);
            SARADOMIN_TEAM_SIZE--;
            queuedOnTeam = false;
        }
    }

    public void playerDeath(String playerName, Player p) {
        if (zamorakTeam.contains(playerName)) {
            p.getMovement().teleport(ZAMORAK_RESPAWN_LOC);
        } else if (saradominTeam.contains(playerName)) {

            p.getMovement().teleport(SARADOMIN_RESPAWN_LOC);
        }
    }

    public void captureFlag(String team, Player p) {
        if (team.equalsIgnoreCase("A")) {
            zamorakScore++;
        } else if (team.equalsIgnoreCase("B")) {
            saradominScore++;
        }
        if (zamorakScore >= FLAG_CAPTURE_THRESHOLD) {
            System.out.println("Zamorak wins!");
            endGame(p);
        } else if (saradominScore >= FLAG_CAPTURE_THRESHOLD) {
            System.out.println("Saradomin wins!");
            endGame(p);
        }
    }

    public void endGame(Player p) {
        zamorakTeam.forEach(player -> {
            p.sendMessage("The game has ended! " + getWinningTeam() + " won with " + flagCaptures + " flags captured!");
            p.getMovement().teleport(CASTLE_WARS_LOBBY);
        });
        saradominTeam.forEach(player -> {
            p.sendMessage("The game has ended! " + getWinningTeam() + " won with " + flagCaptures + " flags captured!");
            p.getMovement().teleport(CASTLE_WARS_LOBBY);
        });
        String winningTeam = getWinningTeam();
        if (winningTeam != null) {
            rewardWinningTeam(winningTeam);
        }
        zamorakTeam.clear();
        ZAMORAK_TEAM_SIZE--;
        saradominTeam.clear();
        SARADOMIN_TEAM_SIZE--;
        zamorakScore = 0;
        saradominScore = 0;
        flagCaptures.clear();
        gameStarted = false;
        gameTimer.cancel();
    }

    public void rewardWinningTeam(String team) {
        giveRewards(player);
    }

    public String getWinningTeam() {
        if (zamorakScore > saradominScore) {
            return "Team A";
        } else if (zamorakScore < saradominScore) {
            return "Team B";
        } else {
            return null; // It's a tie
        }
    }

    public void startGame() {
        zamorakTeam.forEach(p -> {
            player.sendMessage("The game has begun! You have 10 minutes to capture and return as many flags as you can!");
            player.getMovement().teleport(ZAMORAK_RESPAWN_LOC);
            player.getPacketSender().sendWidget(Widget.CASTLE_WARS, GAME_DURATION);
        });
        saradominTeam.forEach(p -> {
            player.sendMessage("The game has begun! You have 10 minutes to capture and return as many flags as you can!");
            player.getMovement().teleport(SARADOMIN_RESPAWN_LOC);
            player.getPacketSender().sendWidget(Widget.CASTLE_WARS, GAME_DURATION);
        });
        gameStarted = true;
        startTimer();
        pulse();
    }

    public void startTimer() {
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                endGame(player);
            }
        }, GAME_DURATION);
    }
}