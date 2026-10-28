package io.ruin.model.activities.timerift;

import io.ruin.cache.Color;
import io.ruin.model.activities.dailytasks.DailyTask;
import io.ruin.model.activities.timerift.skillingtasks.SkillingTasks;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import static io.ruin.model.activities.timerift.NPCTaskListener.*;
import static io.ruin.model.activities.timerift.TimeRift.assignTask;
import static io.ruin.model.activities.timerift.TimeRift.hasTask;
import static io.ruin.model.activities.timerift.TimeRiftEvent.phaseTwo;

public enum TeamTasks {

    KILL_BOSS_ONE(MINI_BOSS_ONE, "Kill The Mimic", "Take a portal to the Southern area and kill Elvarg", 1, 24709, 150, 2194, 2842),
    KILL_BOSS_TWO(MINI_BOSS_TWO, "Kill Barrelchest", "Take a portal to the Southern area and kill Barrelchest", 1, 24709, 150, 2226, 2842),
    KILL_BOSS_THREE(MINI_BOSS_THREE, "Kill Delrith", "Take a portal to the Eastern area and kill Delrith", 1, 24709, 150, 2287, 2931),
    KILL_BOSS_FOUR(MINI_BOSS_THREE, "Kill Treus Dayth", "Take a portal to the Western area and kill Treus Dayth", 1, 24709, 150, 2127, 2921)

    ;

    TeamTasks(TaskListener listener, String shortDescription, String longDescription,
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

    public static LinkedList<TeamTasks> getTeamTasks() {
        return new LinkedList<>(Arrays.asList(values()));
    }

    public static void assignTeamTask(Player p) {
        LinkedList<TeamTasks> teamTasks = getTeamTasks();
        Collections.shuffle(teamTasks);
        TeamTasks selected;
        selected = teamTasks.pop();
        p.teamTasks[0] = selected;
        p.sendMessage("" + Color.ORANGE_RED.wrap("<shad>New Task: </col></shad>"+selected.longDescription));
        p.getPacketSender().sendString(822, 134, ""+selected.shortDescription);
        hasTask = true;
        p.getPacketSender().sendHintIcon(p.teamTasks[0].hintX, p.teamTasks[0].hintY);
    }

    public boolean isComplete(int currentProgress) {
        return currentProgress >= killAmount;
    }

    public static void checkNPCKill(Player p, NPC npc) {
        if (p.teamTasks[0] != null) {
            for (int i = 0; i < p.teamTasks.length; i++) {
                TeamTasks teamTasks = p.teamTasks[i];
                int progress = teamTasks.getListener().onNPCKill(p, npc);
                if (progress <= 0) {
                    continue;
                }
                boolean wasComplete = teamTasks.isComplete(p.teamTaskProgress[i]);
                p.teamTaskProgress[i] += progress;
                if (!wasComplete && teamTasks.isComplete(p.teamTaskProgress[i])) {
                    completeTask(p);
                }
            }
        }
    }

    public static void pulse(Player p) {
        p.getPacketSender().sendString(822, 132, "Time Rift");
        p.getPacketSender().sendString(822, 133, "Team Shards:");
        if (p.soloTasks[0] == null && p.teamTasks[0] == null) {
            p.getPacketSender().sendString(822, 134, "No Task");
        }
        p.getPacketSender().sendString(822, 135, "    "+teamShardsCollected+"/750");
        if (phaseTwo) {
            p.getPacketSender().resetHintIcon(false);
            p.teamTasks[0] = null;
            p.soloTasks[0] = null;
            SkillingTasks.resetSkillingTasks(p);
        }
    }

    public static int teamShardsCollected;

    public static void completeTask(Player p) {
        p.teamTasks[0] = null;
        SkillingTasks.resetSkillingTasks(p);
        p.sendMessage("+150 Rift Shards");
        teamShardsCollected += 150;
        assignTask(p);
        hasTask = false;
        p.getPacketSender().resetHintIcon(false);
    }

}