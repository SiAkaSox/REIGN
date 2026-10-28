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

public enum DailyTaskHard {
    //PvM tasks
    KILL_CALLISTO(CALLISTO, HARD, "Kill Callisto", "Kill Callisto 10 times.", 10, 24567, 1),
    KILL_VETION(VETION, HARD, "Kill Vet'ion", "Kill Vet'ion 10 times.", 10, 24567, 1),
    KILL_VENENATIS(VENENATIS, HARD, "Kill Venenatis", "Kill Venenatis 10 times.", 10, 24567, 1),
    KILL_SCORPIA(SCORPIA, HARD, "Kill Scorpia", "Kill Scorpia 10 times.", 10, 24567, 1),
    KILL_ZULRAH(ZULRAH, HARD, "Kill Zulrah", "Kill Zulrah 20 times.", 20, 23071, 3),
    KILL_ABYSSAL_SIRE(ABYSSAL_SIRE, HARD, "Kill Abyssal Sire", "Kill Abyssal Sire 20 times.", 20, 23071, 3),
    KILL_GWD_GENERALS(GODWARS_GENERALS, HARD, "Kill GWD Generals", "Kill Godwars bosses 20 times", 20, 995, 1000000),
    KILL_CERBERUS(CERBERUS, HARD, "Kill Cerberus", "Kill Cerberus 20 times.", 20, 23071, 3),
    KILL_THERMONUCLEAR_SMOKE_DEVIL(THERMONUCLEAR_SMOKE_DEVIL, EASY, "Kill Thermo Smoke Devil", "Kill the Thermo boss 20 times.", 20, 995, 1000000),
    KILL_REVENANTS_HARD(REVENANTS, HARD, "Kill Revenants", "Kill 100 of any revenants.", 100, 4067, 5),

    //PvP Tasks
    EDGEVILLE_MEDIUM(EDGEVILLE, HARD, "Edgeville Kills", "Get 10 kills in Edgeville wild.", 10, 4067, 5),
    MAIN_MEDIUM(MAIN, HARD, "Main Kills", "Get 10 kills as a main.", 10, 25342, 5),
    PURE_MEDIUM(PURE, HARD, "Pure Kills", "Get 10 kills with 1 defence.", 10, 49050, 3),
    HOTSPOT_MEDIUM(HOTSPOT, HARD, "Hotspot Kills", "Get 10 kills in a hotspot.", 10, 4067, 5),
    DEEP_MEDIUM(DEEP_WILD, HARD, "Deep Wild Kill", "Get 5 kills in level 50+ wild.", 5, 4067, 5),
    REVENANT_CAVE_MEDIUM(REV_CAVE, HARD, "Rev Cave Kills", "Kill 5 players in the rev cave.", 5, 25342, 5),

    ;

    DailyTaskHard(TaskListener listener, DailyTaskDifficulty difficulty, String shortDescription, String longDescription, int amountToKill, int itemReward, int rewardAmount) {
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
        if (p.dailyTasksHard[0] != null || p.dailyTasksHard[1] != null || p.dailyTasksHard[2] != null) {
            for (int i = 0; i < p.dailyTasksHard.length; i++) {
                DailyTaskHard dailyTasksHard = p.dailyTasksHard[i];
                int progress = dailyTasksHard.getListener().onNPCKill(p, npc);
                if (progress <= 0)
                    continue;
                boolean wasComplete = dailyTasksHard.isComplete(p.dailyTaskHardProgress[i]);
                p.dailyTaskHardProgress[i] += progress;
                if (!wasComplete && dailyTasksHard.isComplete(p.dailyTaskHardProgress[i])) {
                    dailyTasksHard.complete(p);
                }
            }
        }
    }

    public static void checkPlayerKill(Player p, Player killed) {
        if (PlayerCounter.PVP_TASKS_SELECTED.get(p) == 1) {
            if (p.dailyTasksHard[0] != null || p.dailyTasksHard[1] != null || p.dailyTasksHard[2] != null) {
                for (int i = 0; i < p.dailyTasksHard.length; i++) {
                    DailyTaskHard dailyTasksHard = p.dailyTasksHard[i];
                    int progress = dailyTasksHard.getListener().onPlayerKill(p, killed);
                    if (progress <= 0)
                        continue;
                    boolean wasComplete = dailyTasksHard.isComplete(p.dailyTaskHardProgress[i]);
                    p.dailyTaskHardProgress[i] += progress;
                    if (!wasComplete && dailyTasksHard.isComplete(p.dailyTaskHardProgress[i])) {
                        dailyTasksHard.complete(p);
                    }
                }
            }
        }
    }

    private void complete(Player p) {
        PlayerCounter.HARD_DAILY_TASKS_COMPLETED.increment(p, 1);
        p.getInventory().addOrDrop(itemReward, rewardAmount);
        p.dailyTaskPoints += difficulty().getPointsReward();
        p.sendMessage("You have completed a hard daily achievement: <col=800080>" + shortDescription() + "</col>");
    }

    public static boolean isComplete(Player p, int taskIndex) {
        return p.dailyTasksHard[taskIndex].isComplete(p.dailyTaskHardProgress[taskIndex]);
    }

    public static void assignTasks(Player p) {
        p.dailytaskReset = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24);
        LinkedList<DailyTaskHard> pvmTasks = getPvMTasks();
        LinkedList<DailyTaskHard> pvpTasks = getPvPTasks();
        Collections.shuffle(pvmTasks);
        Collections.shuffle(pvpTasks);
        p.dailyTasksHard = new DailyTaskHard[3];
        int pvpSelection = PlayerCounter.PVP_TASKS_SELECTED.get(p);
        for (int i = 0; i < 3; i++) {
            DailyTaskHard selected;
            if (pvpSelection == 1) {
                selected = pvpTasks.pop();
            } else {
                selected = pvmTasks.pop();
            }
            p.dailyTasksHard[i] = selected;
            pvpTasks.removeIf(dt -> dt.getListener() == selected.getListener());
        }
        Arrays.fill(p.dailyTaskHardProgress, 0);
    }

    private static LinkedList<DailyTaskHard> getPvMTasks() {
        LinkedList<DailyTaskHard> list = new LinkedList<>(Arrays.asList(values()));
        list.removeIf(t -> t.getListener() instanceof PlayerKillTaskListener);
        return list;
    }

    private static LinkedList<DailyTaskHard> getPvPTasks() {
        LinkedList<DailyTaskHard> list = new LinkedList<>(Arrays.asList(values()));
        list.removeIf(t -> t.getListener() instanceof NPCKillTaskListener);
        return list;
    }

    static {
        DailyResetListener.register(DailyTaskHard::assignTasks);
    }
}
