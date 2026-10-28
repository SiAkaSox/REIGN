package io.ruin.model.activities.timerift;

import io.ruin.cache.Color;
import io.ruin.model.activities.timerift.skillingtasks.SkillingTasks;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Widget;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import static io.ruin.model.activities.timerift.NPCTaskListener.*;
import static io.ruin.model.activities.timerift.TeamTasks.teamShardsCollected;
import static io.ruin.model.activities.timerift.TimeRift.assignTask;
import static io.ruin.model.activities.timerift.TimeRift.hasTask;
import static io.ruin.model.activities.timerift.TimeRiftEvent.phaseTwo;

public enum IndividualTasks {

    KILL_MOBS_NORTH(NORTH_MOBS, "Kill North Mobs", "Travel North and kill as many monsters as you can in the next 60 seconds!", 100, 24709, 100, 2199, 2965),
    KILL_MOBS_SOUTH(SOUTH_MOBS, "Kill South Mobs", "Travel South and kill as many monsters as you can in the next 60 seconds!", 100, 24709, 100, 2211, 2824),
    KILL_MOBS_EAST(EAST_MOBS, "Kill East Mobs", "Travel East and kill as many monsters as you can in the next 60 seconds!", 100, 24709, 100, 2287, 2898),
    KILL_MOBS_WEST(WEST_MOBS, "Kill West Mobs", "Travel West and kill as many monsters as you can in the next 60 seconds!", 100, 24709, 100, 2127, 2893)

    ;

    IndividualTasks(TaskListener listener, String shortDescription, String longDescription,
                    int killAmount, int rewardId, int rewardAmount, int hintX, int hintY) {
        this.listener = listener;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.killAmount = killAmount;
        this.rewardId = rewardId;
        this.rewardAmount = rewardAmount;
        this.hintX = hintX;
        this.hintY = hintY;
    }

    private final TaskListener listener;
    private final String shortDescription;
    private final String longDescription;
    private final int killAmount;
    private final int rewardId;
    private final int rewardAmount;
    private final int hintX;
    private final int hintY;

    public TaskListener getListener() {
        return listener;
    }

    private static final int TASK_DURATION = 60 * 1000; //1 minute
    private static long taskStartTime;

    public static long getElapsedTime() {
        return System.currentTimeMillis() - taskStartTime;
    }

    public static int getTimeRemaining() {
        long elapsedTime = getElapsedTime();
        long remainingTime;
        remainingTime = TASK_DURATION - elapsedTime;
        return (int) (remainingTime / 1000);
    }

    public static void updateTimeRemaining(Player p) {
        int timeRemaining = getTimeRemaining();
        if (p.soloTasks[0] == KILL_MOBS_NORTH || p.soloTasks[0] == KILL_MOBS_SOUTH ||
                p.soloTasks[0] == KILL_MOBS_EAST || p.soloTasks[0] == KILL_MOBS_WEST) {
            p.getPacketSender().sendWidget(Widget.TIME_RIFT, timeRemaining);
        } else {
            TimeRiftEvent.updateTimeRemaining(p);
        }
    }

    public static void pulse(Player p) {
        p.getPacketSender().sendString(822, 132, "Time Rift");
        p.getPacketSender().sendString(822, 133, "Team Shards:");
        if (p.soloTasks[0] == null && p.teamTasks[0] == null) {
            p.getPacketSender().sendString(822, 134, "No Task");
        }
        p.getPacketSender().sendString(822, 135, "    "+teamShardsCollected+"/750");

        if (p.soloTasks[0] != null) {
            if (getTimeRemaining() == 0) {
                completeTask(p);
            }
            if (phaseTwo) {
                p.getPacketSender().resetHintIcon(false);
                p.teamTasks[0] = null;
                p.soloTasks[0] = null;
                SkillingTasks.resetSkillingTasks(p);

            }
        }
    }

    public static LinkedList<IndividualTasks> getIndividualTasks() {
        return new LinkedList<>(Arrays.asList(values()));
    }

    public static void assignSoloTask(Player p) {
        LinkedList<IndividualTasks> soloTasks = getIndividualTasks();
        Collections.shuffle(soloTasks);
        IndividualTasks selected;
        selected = soloTasks.pop();
        p.soloTasks[0] = selected;
        p.sendMessage("" + Color.ORANGE_RED.wrap("<shad>New Task: </col></shad>"+selected.longDescription));
        p.getPacketSender().sendString(822, 134, " "+selected.shortDescription);
        hasTask = true;
        p.getPacketSender().sendHintIcon(p.soloTasks[0].hintX, p.soloTasks[0].hintY);
        if (p.soloTasks[0] == KILL_MOBS_EAST) {
            updateTimeRemaining(p);
            taskStartTime = System.currentTimeMillis();
            if (phaseTwo) {
                completeTask(p);
            }
        }
        if (p.soloTasks[0] == KILL_MOBS_WEST) {
            updateTimeRemaining(p);
            taskStartTime = System.currentTimeMillis();
            if (phaseTwo) {
                completeTask(p);
            }
        }
        if (p.soloTasks[0] == KILL_MOBS_NORTH) {
            updateTimeRemaining(p);
            taskStartTime = System.currentTimeMillis();
            if (phaseTwo) {
                completeTask(p);
            }
        }
        if (p.soloTasks[0] == KILL_MOBS_SOUTH) {
            updateTimeRemaining(p);
            taskStartTime = System.currentTimeMillis();
            if (phaseTwo) {
                completeTask(p);
            }
        }
    }

    public boolean isComplete(int currentProgress) {
        return currentProgress >= killAmount;
    }

    public static void checkNPCKill(Player p, NPC npc) {
        if (p.soloTasks[0] != null) {
        for (int i = 0; i < p.soloTasks.length; i++) {
            IndividualTasks soloTasks = p.soloTasks[i];
            int progress = soloTasks.getListener().onNPCKill(p, npc);
            if (progress <= 0) {
                continue;
            }
            boolean wasComplete = soloTasks.isComplete(p.soloTaskProgress[i]);
            p.soloTaskProgress[i] += progress;
            if (!wasComplete && progress > 0) {
                p.riftShards += 1;
                p.sendMessage("+1 Rift Shard");
                TeamTasks.teamShardsCollected += 1;
            }
            if (!wasComplete && soloTasks.isComplete(p.soloTaskProgress[i])) {
                completeTask(p);
                }
            }
        }
    }

    public static void completeTask(Player p) {
        p.getPacketSender().resetHintIcon(false);
        TimeRiftEvent.updateTimeRemaining(p);
        p.teamTasks[0] = null;
        p.soloTasks[0] = null;
        SkillingTasks.resetSkillingTasks(p);
        p.getInventory().addOrDrop(24709, 100 * p.midnightandnoonBonus);
        p.sendMessage("+100 Rift Shards");
        TeamTasks.teamShardsCollected += 100;
        hasTask = false;
        p.startEvent(e -> {
            e.delay(1);
            assignTask(p);
            e.delay(1);
            updateTimeRemaining(p);
        });
    }

}