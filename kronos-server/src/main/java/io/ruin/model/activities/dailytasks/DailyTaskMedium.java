package io.ruin.model.activities.dailytasks;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.listeners.DailyResetListener;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.ruin.model.activities.dailytasks.DailyTaskDifficulty.*;
import static io.ruin.model.activities.dailytasks.NPCKillTaskListener.*;
import static io.ruin.model.activities.dailytasks.PlayerKillTaskListener.*;

public enum DailyTaskMedium {
    //PvM tasks
    KILL_CALLISTO(CALLISTO, HARD, "Kill Callisto", "Kill Callisto once.", 1, 24565, 1),
    KILL_VETION(VETION, HARD, "Kill Vet'ion", "Kill Vet'ion once.", 1, 24565, 1),
    KILL_VENENATIS(VENENATIS, HARD, "Kill Venenatis", "Kill Venenatis once.", 1, 24565, 1),
    KILL_SCORPIA(SCORPIA, HARD, "Kill Scorpia", "Kill Scorpia once.", 1, 24565, 1),
    KILL_ZULRAH(ZULRAH, HARD, "Kill Zulrah", "Kill Zulrah 5 times.", 5, 23071, 3),
    KILL_ABYSSAL_SIRE(ABYSSAL_SIRE, HARD, "Kill Abyssal Sire", "Kill Abyssal Sire 3 times.", 3, 23071, 3),
    KILL_GWD_GENERALS(GODWARS_GENERALS, HARD, "Kill GWD Generals", "Kill Godwars bosses 4 times", 4, 995, 500000),
    KILL_CERBERUS(CERBERUS, HARD, "Kill Cerberus", "Kill Cerberus twice.", 2, 23071, 3),
    KILL_BARROWS_BROTHERS(BARROWS_BROTHERS, MEDIUM, "Kill Barrows Brothers", "Kill barrows brothers 12 times.", 12, 23071, 3),
    KILL_THERMONUCLEAR_SMOKE_DEVIL(THERMONUCLEAR_SMOKE_DEVIL, EASY, "Kill Thermo Smoke Devil", "Kill the Thermo boss 3 times.", 3, 995, 500000),
    KILL_REVENANTS_HARD(REVENANTS, HARD, "Kill Revenants", "Kill 30 of any revenants.", 30, 4067, 2),

    //PvP Tasks
    EDGEVILLE_MEDIUM(EDGEVILLE, MEDIUM, "Edgeville Kills", "Get 3 kills in Edgeville wild.", 3, 4067, 3),
    MAIN_MEDIUM(MAIN, MEDIUM, "Main Kills", "Get 3 kills as a main.", 3, 25342, 3),
    PURE_MEDIUM(PURE, MEDIUM, "Pure Kills", "Get 3 kills with 1 defence.", 3, 49050, 2),
    HOTSPOT_MEDIUM(HOTSPOT, MEDIUM, "Hotspot Kills", "Get 3 kills in a hotspot.", 3, 4067, 3),
    DEEP_MEDIUM(DEEP_WILD, MEDIUM, "Deep Wild Kill", "Get a kill in level 50+ wild.", 1, 995, 2500000),
    REVENANT_CAVE_MEDIUM(REV_CAVE, EASY, "Rev Cave Kills", "Kill 2 players in the rev cave.", 2, 25342, 3),

    ;

    DailyTaskMedium(TaskListener listener, DailyTaskDifficulty difficulty, String shortDescription, String longDescription, int amountToKill, int itemReward, int rewardAmount) {
        this.listener = listener;
        this.difficulty = difficulty;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.amountToKill = amountToKill;
        this.itemReward = itemReward;
        this.rewardAmount = rewardAmount;
    }

    private final TaskListener listener;
    private final DailyTaskDifficulty difficulty;
    private final String shortDescription;
    private final String longDescription;
    public final int amountToKill;
    public final int itemReward;
    public final int rewardAmount;

    public DailyTaskDifficulty difficulty() {
        return difficulty;
    }

    public String shortDescription() {
        return shortDescription;
    }

    public String longDescription() {
        return longDescription;
    }

    public boolean isComplete(int currentProgress) {
        return currentProgress >= amountToKill;
    }

    public TaskListener getListener() {
        return listener;
    }

    public static String getRemainingTime(Player player) {
        long ms = player.dailytaskReset - System.currentTimeMillis();
        long hours = TimeUnit.MILLISECONDS.toHours(ms);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);
        return (hours >= 1 ? (hours + " h and ") : "") +
                Math.max((minutes - TimeUnit.HOURS.toMinutes(hours)), 1) + " m";
    }

    public static void checkNPCKill(Player p, NPC npc) {
        if (p.dailyTasksMedium[0] != null || p.dailyTasksMedium[1] != null || p.dailyTasksMedium[2] != null) {
            for (int i = 0; i < p.dailyTasksMedium.length; i++) {
                DailyTaskMedium dailyTaskMedium = p.dailyTasksMedium[i];
                int progress = dailyTaskMedium.getListener().onNPCKill(p, npc);
                if (progress <= 0)
                    continue;
                boolean wasComplete = dailyTaskMedium.isComplete(p.dailyTaskMediumProgress[i]);
                p.dailyTaskMediumProgress[i] += progress;
                if (!wasComplete && dailyTaskMedium.isComplete(p.dailyTaskMediumProgress[i])) {
                    dailyTaskMedium.complete(p);
                }
            }
        }
    }

    public static void checkPlayerKill(Player p, Player killed) {
        if (PlayerCounter.PVP_TASKS_SELECTED.get(p) == 1) {
            if (p.dailyTasksMedium[0] != null || p.dailyTasksMedium[1] != null || p.dailyTasksMedium[2] != null) {
                for (int i = 0; i < p.dailyTasksMedium.length; i++) {
                    DailyTaskMedium dailyTaskMedium = p.dailyTasksMedium[i];
                    int progress = dailyTaskMedium.getListener().onPlayerKill(p, killed);
                    if (progress <= 0)
                        continue;
                    boolean wasComplete = dailyTaskMedium.isComplete(p.dailyTaskMediumProgress[i]);
                    p.dailyTaskMediumProgress[i] += progress;
                    if (!wasComplete && dailyTaskMedium.isComplete(p.dailyTaskMediumProgress[i])) {
                        dailyTaskMedium.complete(p);
                    }
                }
            }
        }
    }

    private void complete(Player player) {
        PlayerCounter.MEDIUM_DAILY_TASKS_COMPLETED.increment(player, 1);
        player.getInventory().addOrDrop(itemReward, rewardAmount);
        player.dailyTaskPoints += difficulty().getPointsReward();
        player.sendMessage("You have completed a medium daily achievement: <col=800080>" + shortDescription() + "</col>");
    }

    public static boolean isComplete(Player player, int taskIndex) {
        return player.dailyTasksMedium[taskIndex].isComplete(player.dailyTaskMediumProgress[taskIndex]);
    }

    public static void assignTasks(Player player) {
        player.dailytaskReset = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24);
        LinkedList<DailyTaskMedium> pvmTasks = getPvMTasks();
        LinkedList<DailyTaskMedium> pvpTasks = getPvPTasks();
        Collections.shuffle(pvmTasks);
        Collections.shuffle(pvpTasks);
        player.dailyTasksMedium = new DailyTaskMedium[3];
        int pvpSelection = PlayerCounter.PVP_TASKS_SELECTED.get(player);
        for (int i = 0; i < 3; i++) {
            DailyTaskMedium selected;
            if (pvpSelection == 1) {
                selected = pvpTasks.pop();
            } else {
                selected = pvmTasks.pop();
            }
            player.dailyTasksMedium[i] = selected;
            pvpTasks.removeIf(dt -> dt.getListener() == selected.getListener());
        }
        Arrays.fill(player.dailyTaskMediumProgress, 0);
    }

    private static LinkedList<DailyTaskMedium> getPvMTasks() {
        LinkedList<DailyTaskMedium> list = new LinkedList<>(Arrays.asList(values()));
        list.removeIf(t -> t.getListener() instanceof PlayerKillTaskListener);
        return list;
    }

    private static LinkedList<DailyTaskMedium> getPvPTasks() {
        LinkedList<DailyTaskMedium> list = new LinkedList<>(Arrays.asList(values()));
        list.removeIf(t -> t.getListener() instanceof NPCKillTaskListener);
        return list;
    }

    static {
        DailyResetListener.register(DailyTaskMedium::assignTasks);
    }
}
