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

public enum DailyTaskElite {
    //PvM tasks
    KILL_CALLISTO(CALLISTO, ELITE, "Kill Callisto", "Kill Callisto 25 times.", 25, 24567, 1),
    KILL_VETION(VETION, ELITE, "Kill Vet'ion", "Kill Vet'ion 25 times.", 25, 24567, 1),
    KILL_VENENATIS(VENENATIS, ELITE, "Kill Venenatis", "Kill Venenatis 25 times.", 25, 24567, 1),
    KILL_SCORPIA(SCORPIA, ELITE, "Kill Scorpia", "Kill Scorpia 25 times.", 25, 24567, 1),
    KILL_ZULRAH(ZULRAH, ELITE, "Kill Zulrah", "Kill Zulrah 30 times.", 30, 23071, 3),
    KILL_ABYSSAL_SIRE(ABYSSAL_SIRE, ELITE, "Kill Abyssal Sire", "Kill Abyssal Sire 30 times.", 30, 23071, 3),
    KILL_GWD_GENERALS(GODWARS_GENERALS, ELITE, "Kill GWD Generals", "Kill Godwars bosses 30 times", 30, 995, 1000000),
    KILL_CERBERUS(CERBERUS, ELITE, "Kill Cerberus", "Kill Cerberus 30 times.", 30, 23071, 3),
    KILL_THERMONUCLEAR_SMOKE_DEVIL(THERMONUCLEAR_SMOKE_DEVIL, ELITE, "Kill Thermo Smoke Devil", "Kill the Thermo boss 30 times.", 30, 995, 1000000),
    KILL_REVENANTS_HARD(REVENANTS, ELITE, "Kill Revenants", "Kill 150 of any revenants.", 150, 4067, 5),

    //PvP Tasks
    EDGEVILLE_MEDIUM(EDGEVILLE, ELITE, "Edgeville Kills", "Get 25 kills in Edgeville wild.", 25, 4067, 5),
    MAIN_MEDIUM(MAIN, ELITE, "Main Kills", "Get 25 kills as a main.", 25, 25342, 5),
    PURE_MEDIUM(PURE, ELITE, "Pure Kills", "Get 25 kills with 1 defence.", 25, 49050, 3),
    HOTSPOT_MEDIUM(HOTSPOT, ELITE, "Hotspot Kills", "Get 25 kills in a hotspot.", 25, 4067, 5),
    DEEP_MEDIUM(DEEP_WILD, ELITE, "Deep Wild Kill", "Get 15 kills in level 50+ wild.", 15, 4067, 5),
    REVENANT_CAVE_MEDIUM(REV_CAVE, ELITE, "Rev Cave Kills", "Kill 15 players in the rev cave.", 15, 25342, 5),

    ;

    DailyTaskElite(TaskListener listener, DailyTaskDifficulty difficulty, String shortDescription, String longDescription, int amountToKill, int itemReward, int rewardAmount) {
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
        if (p.dailyTasksElite[0] != null || p.dailyTasksElite[1] != null || p.dailyTasksElite[2] != null) {
            for (int i = 0; i < p.dailyTasksElite.length; i++) {
                DailyTaskElite dailyTasksElite = p.dailyTasksElite[i];
                int progress = dailyTasksElite.getListener().onNPCKill(p, npc);
                if (progress <= 0)
                    continue;
                boolean wasComplete = dailyTasksElite.isComplete(p.dailyTaskEliteProgress[i]);
                p.dailyTaskEliteProgress[i] += progress;
                if (!wasComplete && dailyTasksElite.isComplete(p.dailyTaskEliteProgress[i])) {
                    dailyTasksElite.complete(p);
                }
            }
        }
    }

    public static void checkPlayerKill(Player p, Player killed) {
        if (PlayerCounter.PVP_TASKS_SELECTED.get(p) == 1) {
            if (p.dailyTasksElite[0] != null || p.dailyTasksElite[1] != null || p.dailyTasksElite[2] != null) {
                for (int i = 0; i < p.dailyTasksElite.length; i++) {
                    DailyTaskElite dailyTasksElite = p.dailyTasksElite[i];
                    int progress = dailyTasksElite.getListener().onPlayerKill(p, killed);
                    if (progress <= 0)
                        continue;
                    boolean wasComplete = dailyTasksElite.isComplete(p.dailyTaskEliteProgress[i]);
                    p.dailyTaskEliteProgress[i] += progress;
                    if (!wasComplete && dailyTasksElite.isComplete(p.dailyTaskEliteProgress[i])) {
                        dailyTasksElite.complete(p);
                    }
                }
            }
        }
    }

    private void complete(Player player) {
        PlayerCounter.ELITE_DAILY_TASKS_COMPLETED.increment(player, 1);
        player.getInventory().addOrDrop(itemReward, rewardAmount);
        player.dailyTaskPoints += difficulty().getPointsReward();
        player.sendMessage("You have completed an elite daily achievement: <col=800080>" + shortDescription() + "</col>");
    }

    public static boolean isComplete(Player player, int taskIndex) {
        return player.dailyTasksElite[taskIndex].isComplete(player.dailyTaskEliteProgress[taskIndex]);
    }

    public static void assignTasks(Player player) {
        player.dailytaskReset = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24);
        LinkedList<DailyTaskElite> pvmTasks = getPvMTasks();
        LinkedList<DailyTaskElite> pvpTasks = getPvPTasks();
        Collections.shuffle(pvmTasks);
        Collections.shuffle(pvpTasks);
        player.dailyTasksElite = new DailyTaskElite[3];
        int pvpSelection = PlayerCounter.PVP_TASKS_SELECTED.get(player);
        for (int i = 0; i < 3; i++) {
            DailyTaskElite selected;
            if (pvpSelection == 1) {
                selected = pvpTasks.pop();
            } else {
                selected = pvmTasks.pop();
            }
            player.dailyTasksElite[i] = selected;
            pvpTasks.removeIf(dt -> dt.getListener() == selected.getListener());
        }
        Arrays.fill(player.dailyTaskEliteProgress, 0);
    }

    private static LinkedList<DailyTaskElite> getPvMTasks() {
        LinkedList<DailyTaskElite> list = new LinkedList<>(Arrays.asList(values()));
        list.removeIf(t -> t.getListener() instanceof PlayerKillTaskListener);
        return list;
    }

    private static LinkedList<DailyTaskElite> getPvPTasks() {
        LinkedList<DailyTaskElite> list = new LinkedList<>(Arrays.asList(values()));
        list.removeIf(t -> t.getListener() instanceof NPCKillTaskListener);
        return list;
    }

    static {
        DailyResetListener.register(DailyTaskElite::assignTasks);
    }
}
