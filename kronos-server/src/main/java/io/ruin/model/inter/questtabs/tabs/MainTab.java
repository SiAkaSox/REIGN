package io.ruin.model.inter.questtabs.tabs;

import io.ruin.Server;
import io.ruin.utility.TimeUtils;
import io.ruin.model.activities.dailytasks.DailyTaskReset;
import io.ruin.model.combat.CombatUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.item.Item;

import java.text.NumberFormat;

import static io.ruin.Server.STARTUP_THREAD;
import static io.ruin.model.achievements.listeners.easy.EasyAchieveInter.loadEasyAchievements;
import static io.ruin.model.inter.questtabs.QuestTab.*;

public class MainTab {

    public static int MAIN_TAB = 939;

    public static boolean timeRevealed = false;

    public static void openEdgePvp(Player p) {
        p.openInterface(InterfaceType.MAIN, 957);
        p.getPacketSender().sendString(957, 78, "Time Remaining: <col=27ae60>"+ DailyTaskReset.getDailyTaskReset());
        p.getPacketSender().sendString(957, 86, "Edge kills today: <col=27ae60>"+p.dailyEdgeKills);
        p.getPacketSender().sendItems(957, 63, 0, new Item(25646));
        p.getPacketSender().sendItems(957, 84, 0, new Item(25648));
    }

    static {

            InterfaceHandler.register(MAIN_TAB, h -> {
                h.actions[1] = (SlotAction) (p, slot) -> switchMain(p);
                h.actions[61] = (SlotAction) (p, slot) -> switchServerTab(p);
                h.actions[65] = (SlotAction) (p, slot) -> switchAchieveTab(p);
                h.actions[69] = (SlotAction) (p, slot) -> switchEventsTab(p);
                h.actions[229] = (SlotAction) (p, slot) -> openEdgePvp(p);
                h.actions[235] = (SlotAction) (p, slot) -> p.getCollectionLog().openCollectionLog(p);
                h.actions[233] = (SlotAction) (p, slot) -> p.sendMessage("This feature is currently under construction!");
                h.actions[231] = (SlotAction) (p, slot) -> loadEasyAchievements(p);
                h.actions[244] = (SlotAction) (p, slot) -> {
                    if (!timeRevealed) {
                        timeRevealed = true;
                    } else {
                        p.sendMessage("You have already revealed your time played.");
                    }
                };
            });
    }

    private static String toTime(Player p) {
        return TimeUtils.fromMs(p.playTime * Server.tickMs(), false);
    }

    public static void sendUpdatesMainTab(Player p) {
        String totalXpString = ""+p.getStats().totalXp;
        String totalLevelString = ""+(p.getStats().totalLevel);
        int totalXpInt = Integer.parseInt(totalXpString);
        int totalLevelInt = Integer.parseInt(totalLevelString);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedNumber = numberFormat.format(totalXpInt);
        String formattedTotalLevel = numberFormat.format(totalLevelInt);
        int totalAchievementsComleted = (p.introAchievementsCompleted) + (p.mediumAchievementsCompleted) + (p.hardAchievementsCompleted) + (p.eliteAchievementsCompleted) + (p.masterAchievementsCompleted);
        p.getPacketSender().sendString(MAIN_TAB, 120, ""+p.name());
        p.getPacketSender().sendString(MAIN_TAB, 224, ""+ CombatUtils.getCombatLevel(p));
        p.getPacketSender().sendString(MAIN_TAB, 226, ""+formattedTotalLevel);
        p.getPacketSender().sendString(MAIN_TAB, 234, ""+p.totalTasksCompleted+"/209");
        p.getPacketSender().sendString(MAIN_TAB, 228, ""+ formattedNumber);
        p.getPacketSender().sendString(MAIN_TAB, 232, totalAchievementsComleted+"/64");
        p.getPacketSender().sendString(MAIN_TAB, 236, p.totalCollections+"/1,025");
        p.getPacketSender().sendString(MAIN_TAB, 230, p.dailyEdgeKillsQuestTab+"/2");
        if (timeRevealed) {
            p.getPacketSender().sendString(MAIN_TAB, 244, "<col=0fd60f>"+toTime(p));
        } else {
            p.getPacketSender().sendString(MAIN_TAB, 244, "Click to reveal.");
        }
    }

}