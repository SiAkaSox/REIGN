package io.ruin.model.activities.dailytasks;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.listeners.DailyResetListener;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.ruin.model.activities.dailytasks.DailyTaskDifficulty.*;
import static io.ruin.model.activities.dailytasks.NPCKillTaskListener.*;
import static io.ruin.model.activities.dailytasks.PlayerKillTaskListener.*;

public enum DailyTask {
    //PvM tasks
    KILL_DAGANNOTH_KINGS(DAGANNOTH_KINGS, HARD, "Kill Dagannoth Kings", "Kill Dagannoth Kings 10 times", 10, 995, 1000000),
    KILL_BLOODVELDS(BLOODVELD, EASY, "Kill Bloodvelds", "Kill 30 Bloodvelds", 30, 23071, 1),
    KILL_GREATER_DEMONS(GREATER_DEMON, EASY, "Kill Greater Demons", "Kill 30 Greater Demons", 30, 786, 1),
    KILL_YAKS(YAK, EASY, "Kill Yaks", "Kill 30 Yaks", 30, 23071, 1),
    KILL_ENTS(ENT, EASY, "Kill Ents", "Kill 30 Ents", 30, 24565, 1),
    KILL_ELDER_CHAOS(ELDER_CHAOS, EASY, "Kill Elder Chaos Druids", "Kill 20 Elder Chaos Druids", 20, 24565, 1),
    KILL_LIZARDMAN_SHAMAN(LIZARDMAN_SHAMAN, EASY, "Kill Lizardmen Shaman", "Kill 2 Lizardmen Shaman", 2, 786, 1),
    KILL_BARROWS_BROTHERS_EASY(BARROWS_BROTHERS, EASY, "Kill Barrows Brothers", "Kill barrows brothers 6 times.", 6, 995, 500000),
    KILL_REVENANTS_MEDIUM(REVENANTS, MEDIUM, "Kill Revenants", "Kill 15 of any revenants.", 15, 4067, 1),
    KILL_SAND_CRABS(SAND_CRABS, EASY, "Kill Sand Crabs", "Kill 30 Sand Crabs", 30, 23071, 1),
    KILL_ROCK_CRABS(ROCK_CRABS, EASY, "Kill Rock Crabs", "Kill 30 Rock Crabs", 30, 23071, 1),

    //PvP Tasks
    EDGEVILLE_EASY(EDGEVILLE, EASY, "Edgeville Kill", "Kill a player in Edgeville wild.", 1, 4067, 2),
    MAIN_EASY(MAIN, EASY, "Main Kill", "Get a kill as a main.", 1, 25342, 1),
    PURE_EASY(PURE, EASY, "Pure Kill", "Get a kill with 1 defence.", 1, 49050, 1),
    HOTSPOT_EASY(HOTSPOT, EASY, "Hotspot Kill", "Get a kill in a hotspot.", 1, 4067, 2),
    DEEP_MEDIUM(DEEP_WILD, MEDIUM, "Deep Wild Kill", "Get a kill in level 50+ wild.", 1, 995, 2500000),
    REVENANT_CAVE_EASY(REV_CAVE, EASY, "Rev Cave Kill", "Kill a player in the rev cave.", 1, 25342, 1),
    ;

    DailyTask(TaskListener listener, DailyTaskDifficulty difficulty, String shortDescription, String longDescription, int amountToKill, int itemReward, int rewardAmount) {
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

    public static void checkNPCKill(Player p, NPC npc) {
        if (p.dailyTasks[0] != null || p.dailyTasks[1] != null || p.dailyTasks[2] != null) {
            for (int i = 0; i < p.dailyTasks.length; i++) {
                DailyTask dailyTask = p.dailyTasks[i];
                int progress = dailyTask.getListener().onNPCKill(p, npc);
                if (progress <= 0)
                    continue;
                boolean wasComplete = dailyTask.isComplete(p.dailyTaskProgress[i]);
                p.dailyTaskProgress[i] += progress;
                if (!wasComplete && dailyTask.isComplete(p.dailyTaskProgress[i])) {
                    dailyTask.complete(p);
                }
            }
        }
    }

    public static void checkPlayerKill(Player p, Player killed) {
        if (PlayerCounter.PVP_TASKS_SELECTED.get(p) == 1) {
            if (p.dailyTasks[0] != null || p.dailyTasks[1] != null || p.dailyTasks[2] != null) {
                for (int i = 0; i < p.dailyTasks.length; i++) {
                    DailyTask dailyTask = p.dailyTasks[i];
                    int progress = dailyTask.getListener().onPlayerKill(p, killed);
                    if (progress <= 0)
                        continue;
                    boolean wasComplete = dailyTask.isComplete(p.dailyTaskProgress[i]);
                    p.dailyTaskProgress[i] += progress;
                    if (!wasComplete && dailyTask.isComplete(p.dailyTaskProgress[i])) {
                        dailyTask.complete(p);
                    }
                }
            }
        }
    }

    private void complete(Player player) {
        PlayerCounter.DAILY_TASKS_COMPLETED.increment(player, 1);
        player.getInventory().addOrDrop(itemReward, rewardAmount);
        player.dailyTaskPoints += difficulty().getPointsReward();
        player.sendMessage("You have completed an easy daily achievement: <col=800080>" + shortDescription() + "</col>");
    }

    public static boolean isComplete(Player player, int taskIndex) {
        return player.dailyTasks[taskIndex].isComplete(player.dailyTaskProgress[taskIndex]);
    }

    public static void selectedpvpTasks(Player p) {
        PlayerCounter.PVM_TASKS_SELECTED.set(p, 0);
        PlayerCounter.PVP_TASKS_SELECTED.increment(p, 1);
        assignTasks(p);
        DailyTaskMedium.assignTasks(p);
        DailyTaskHard.assignTasks(p);
        DailyTaskElite.assignTasks(p);
    }
    public static void selectedpvmTasks(Player p) {
        PlayerCounter.PVP_TASKS_SELECTED.set(p, 0);
        PlayerCounter.PVM_TASKS_SELECTED.increment(p, 1);
        assignTasks(p);
        DailyTaskMedium.assignTasks(p);
        DailyTaskHard.assignTasks(p);
        DailyTaskElite.assignTasks(p);
    }

    private static int getTodayDate() {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();
        return (year * 10000 + month * 100 + day);
    }

    public static void dailyResetMethod(Player p) {
        if (p.dailyTaskDate != getTodayDate()) {

            Arrays.fill(p.dailyTasks, null);
            Arrays.fill(p.dailyTasksMedium, null);
            Arrays.fill(p.dailyTasksHard, null);
            Arrays.fill(p.dailyTasksElite, null);

            PlayerCounter.DAILY_TASKS_COMPLETED.set(p, 0);
            PlayerCounter.MEDIUM_DAILY_TASKS_COMPLETED.set(p, 0);
            PlayerCounter.HARD_DAILY_TASKS_COMPLETED.set(p, 0);
            PlayerCounter.ELITE_DAILY_TASKS_COMPLETED.set(p, 0);

            LocalDate currentDate = LocalDate.now();
            LocalDate playerTaskDate = LocalDate.ofEpochDay(p.dailyTaskDate);

            if (!playerTaskDate.isEqual(currentDate)) {
                p.sendMessage("Daily achievements have been reset.");
                p.dailyTaskDate = currentDate.toEpochDay();
            } else {
                p.sendMessage("<img=18> <col=FF4500>Use ::dailies to start receiving daily achievements to complete!");
            }
        }
    }

    public static void assignTasks(Player player) {
        if (player.dailyTaskDate == getTodayDate()) {
            if (PlayerCounter.DAILY_TASKS_COMPLETED.get(player) == 4) {
                player.sendMessage("<img=18> <col=FF4500>You have already completed your daily achievements.");
            }
            if (player.dailyTasks[0] != null || player.dailyTasks[1] != null || player.dailyTasks[3] != null) {
                player.sendMessage("<img=18> <col=FF4500>You already have daily achievements. You can check your current tasks from the Dailies quest tab!");
            }
        } else {
            if (player.newPlayer) {
                player.sendMessage("<img=18> <col=FF4500>Use ::dailies to start receiving daily achievements to complete!");
            } else {
                player.dailytaskReset = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24);
                LinkedList<DailyTask> pvmTasks = getPvMTasks();
                LinkedList<DailyTask> pvpTasks = getPvPTasks();
                Collections.shuffle(pvmTasks);
                Collections.shuffle(pvpTasks);
                player.dailyTasks = new DailyTask[3];
                int pvpSelection = PlayerCounter.PVP_TASKS_SELECTED.get(player);
                for (int i = 0; i < 3; i++) {
                    DailyTask selected;
                    if (pvpSelection == 1) {
                        selected = pvpTasks.pop();
                    } else {
                        selected = pvmTasks.pop();
                    }
                    player.dailyTasks[i] = selected;
                    pvpTasks.removeIf(dt -> dt.getListener() == selected.getListener());
                }
                Arrays.fill(player.dailyTaskProgress, 0);
                player.sendMessage("<img=18> <col=FF4500>You have new daily achievements waiting for you!");
                player.dailyTaskDate = getTodayDate();
            }
        }
    }

    private static LinkedList<DailyTask> getPvMTasks() {
        LinkedList<DailyTask> list = new LinkedList<>(Arrays.asList(values()));
        list.removeIf(t -> t.getListener() instanceof PlayerKillTaskListener);
        return list;
    }

    private static LinkedList<DailyTask> getPvPTasks() {
        LinkedList<DailyTask> list = new LinkedList<>(Arrays.asList(values()));
        list.removeIf(t -> t.getListener() instanceof NPCKillTaskListener);
        return list;
    }

    static {
        //DailyResetListener.register(DailyTask::assignTasks);
    }
}
