package io.ruin.model.activities.timerift;

import io.ruin.Server;
import io.ruin.utility.Random;
import io.ruin.model.activities.timerift.skillingtasks.SkillingTasks;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;

import static io.ruin.Server.STARTUP_THREAD;
import static io.ruin.model.activities.timerift.IndividualTasks.assignSoloTask;
import static io.ruin.model.activities.timerift.TeamTasks.assignTeamTask;
import static io.ruin.model.activities.timerift.TimeRiftEvent.phaseOne;
import static io.ruin.model.activities.timerift.TimeRiftEvent.phaseTwo;

public class TimeRift {

    public static boolean isActive = true;
    public static boolean canGetSoloTask;
    public static boolean hasTask;
    public static boolean leftArea;

    private static Player player;

    public TimeRift(Player player) {
       TimeRift.player = player;
    }

    public static void assignTask(Player p) {
            if ( !phaseTwo && p.teamTasks[0] == null && p.soloTasks[0] == null && SkillingTasks.hasNoTask(p)) {
                switch (Random.get(3)) {
                    case 1:
                        SkillingTasks.assignTask(p);
                        break;
                    case 2:
                        assignTeamTask(p);
                        break;
                    case 3:
                        assignSoloTask(p);
                        break;
                }
            }
            if (!hasTask && phaseOne && !phaseTwo) {
                assignSoloTask(p);
            }
    }

    public static void leaveArea(Player p) {
        p.teamTasks[0] = null;
        p.soloTasks[0] = null;
        SkillingTasks.resetSkillingTasks(p);
        leftArea = true;
        hasTask = false;
    }

    private static void teleportPlayer(Player player, int x, int y) {
        if(player.getCombat().checkTb())
            return;
        player.startEvent(event -> {
            player.lock();
            player.animate(4282);
            event.delay(1);
            player.getMovement().teleport(x, y);
            event.delay(1);
            player.unlock();
        });
    }

    static {

            ObjectAction.register(38451, "Teleport", (player, obj) -> {
                player.dialogue(
                        new OptionsDialogue("Select a destination",
                                new Option("North", () -> teleNorth(player)),
                                new Option("South", () -> teleSouth(player)),
                                new Option("East", () -> teleEast(player)),
                                new Option("West", () -> teleWest(player)),
                                new Option("Center", () -> teleCenter(player))
                        )
                );
            });
            final int[] BARRIER = {41199, 41200};
            for (int barriers : BARRIER) {
                ObjectAction.register(barriers, "Pass", (player, obj) -> {
                    boolean atObjX = obj.x == player.getAbsX();
                    boolean atObjY = obj.y == player.getAbsY();
                    if (obj.direction == 0 && atObjX)
                        teleportPlayer(player, player.getAbsX() - 1, player.getAbsY());
                    else if (obj.direction == 1 && atObjY)
                        teleportPlayer(player, obj.x, obj.y + 1);
                    else if (obj.direction == 2 && atObjX)
                        teleportPlayer(player, obj.x + 1, obj.y);
                    else if (obj.direction == 3 && atObjY)
                        teleportPlayer(player, obj.x, obj.y - 1);
                    else
                        teleportPlayer(player, obj.x, obj.y);
                });
            }
    }

    public static void teleCenter(Player p) {
        p.getMovement().startTeleport(event -> {
            p.privateSound(965, 1, 15);
            p.animate(4071);
            p.graphics(678);
            event.delay(2);
            p.getMovement().teleport(2207, 2909, 0);
        });
    }
    public static void teleNorth(Player p) {
        p.getMovement().startTeleport(event -> {
            p.privateSound(965, 1, 15);
            p.animate(4071);
            p.graphics(678);
            event.delay(2);
            p.getMovement().teleport(2206, 2979, 0);
        });
    }
    public static void teleSouth(Player p) {
        p.getMovement().startTeleport(event -> {
            p.privateSound(965, 1, 15);
            p.animate(4071);
            p.graphics(678);
            event.delay(2);
            p.getMovement().teleport(2210, 2843, 0);
        });
    }
    public static void teleEast(Player p) {
        p.getMovement().startTeleport(event -> {
            p.privateSound(965, 1, 15);
            p.animate(4071);
            p.graphics(678);
            event.delay(2);
            p.getMovement().teleport(2293, 2918, 0);
        });
    }
    public static void teleWest(Player p) {
        p.getMovement().startTeleport(event -> {
            p.privateSound(965, 1, 15);
            p.animate(4071);
            p.graphics(678);
            event.delay(2);
            p.getMovement().teleport(2123, 2903, 0);
        });
    }

}