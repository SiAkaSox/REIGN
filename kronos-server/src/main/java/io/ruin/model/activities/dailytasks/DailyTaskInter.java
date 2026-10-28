package io.ruin.model.activities.dailytasks;

import io.ruin.Server;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;

import static io.ruin.Server.STARTUP_THREAD;
import static io.ruin.model.inter.questtabs.QuestTab.*;
import static io.ruin.model.inter.achievementsInter.DailyAchievesInter.*;
import static io.ruin.model.inter.achievementsInter.DailyAchievesInter.openEliteDailies;

public class DailyTaskInter {

    static {
            InterfaceHandler.register(931, h -> {
                h.actions[1] = (SlotAction) (p, slot) -> switchMain(p);
                h.actions[61] = (SlotAction) (p, slot) -> switchServerTab(p);
                h.actions[65] = (SlotAction) (p, slot) -> switchDailyAchieveTab(p);
                h.actions[69] = (SlotAction) (p, slot) -> switchEventsTab(p);
                h.actions[234] = (SlotAction) (p, slot) -> openEasyDailies(p);
                h.actions[289] = (SlotAction) (p, slot) -> openMediumDailies(p);
                h.actions[300] = (SlotAction) (p, slot) -> openHardDailies(p);
                h.actions[311] = (SlotAction) (p, slot) -> openEliteDailies(p);
                h.actions[408] = (SlotAction) (p, slot) -> switchAchieveTab(p);
                h.actions[407] = (SlotAction) (p, slot) -> p.sendMessage("Work in progress!");
            });
    }

    public static void sendUpdatesDailiesTab(Player p) {
        if (PlayerCounter.DAILY_TASKS_COMPLETED.get(p) < 3) {
            p.getPacketSender().sendString(931, 236, PlayerCounter.DAILY_TASKS_COMPLETED.get(p) +"/3");//1st task amount
        }
        if (PlayerCounter.DAILY_TASKS_COMPLETED.get(p) == 3) {
            p.getPacketSender().sendString(931, 236, "<col=27ae60>"+PlayerCounter.DAILY_TASKS_COMPLETED.get(p) +"/3");//1st task amount
        }

        if (PlayerCounter.MEDIUM_DAILY_TASKS_COMPLETED.get(p) < 3) {
            p.getPacketSender().sendString(931, 291, PlayerCounter.MEDIUM_DAILY_TASKS_COMPLETED.get(p) +"/3");//1st task amount
        }
        if (PlayerCounter.MEDIUM_DAILY_TASKS_COMPLETED.get(p) == 3) {
            p.getPacketSender().sendString(931, 291, "<col=27ae60>"+PlayerCounter.MEDIUM_DAILY_TASKS_COMPLETED.get(p) +"/3");//1st task amount
        }

        if (PlayerCounter.HARD_DAILY_TASKS_COMPLETED.get(p) < 3) {
            p.getPacketSender().sendString(931, 302, PlayerCounter.HARD_DAILY_TASKS_COMPLETED.get(p) +"/3");//1st task amount
        }
        if (PlayerCounter.HARD_DAILY_TASKS_COMPLETED.get(p) == 3) {
            p.getPacketSender().sendString(931, 302, "<col=27ae60>"+PlayerCounter.HARD_DAILY_TASKS_COMPLETED.get(p) +"/3");//1st task amount
        }

        if (PlayerCounter.ELITE_DAILY_TASKS_COMPLETED.get(p) < 3) {
            p.getPacketSender().sendString(931, 313, PlayerCounter.ELITE_DAILY_TASKS_COMPLETED.get(p) +"/3");//1st task amount
        }
        if (PlayerCounter.ELITE_DAILY_TASKS_COMPLETED.get(p) == 3) {
            p.getPacketSender().sendString(931, 313, "<col=27ae60>"+PlayerCounter.ELITE_DAILY_TASKS_COMPLETED.get(p) +"/3");//1st task amount
        }
        p.getPacketSender().sendString(931, 414, ""+DailyTaskReset.getDailyTaskReset());//Time remaining
        if (PlayerCounter.DAILY_TASKS_COMPLETED.get(p) == 0) {
            p.getPacketSender().setHidden(931, 423, false);
            p.getPacketSender().setHidden(931, 424, false);
            p.getPacketSender().setHidden(931, 425, false);
            p.getPacketSender().setHidden(931, 241, true);
        }
        if (PlayerCounter.DAILY_TASKS_COMPLETED.get(p) == 1) {
            p.getPacketSender().setHidden(931, 423, true);
            p.getPacketSender().setHidden(931, 424, false);
            p.getPacketSender().setHidden(931, 425, false);
            p.getPacketSender().setHidden(931, 241, true);
        }
        if (PlayerCounter.DAILY_TASKS_COMPLETED.get(p) == 2) {
            p.getPacketSender().setHidden(931, 423, true);
            p.getPacketSender().setHidden(931, 424, true);
            p.getPacketSender().setHidden(931, 425, false);
            p.getPacketSender().setHidden(931, 241, true);
        }
        if (PlayerCounter.DAILY_TASKS_COMPLETED.get(p) == 3) {
            p.getPacketSender().setHidden(931, 423, true);
            p.getPacketSender().setHidden(931, 424, true);
            p.getPacketSender().setHidden(931, 425, true);
            p.getPacketSender().setHidden(931, 241, true);
        }
        if (PlayerCounter.MEDIUM_DAILY_TASKS_COMPLETED.get(p) == 0) {
            p.getPacketSender().setHidden(931, 426, false);
            p.getPacketSender().setHidden(931, 427, false);
            p.getPacketSender().setHidden(931, 428, false);
            p.getPacketSender().setHidden(931, 296, true);
        }
        if (PlayerCounter.MEDIUM_DAILY_TASKS_COMPLETED.get(p) == 1) {
            p.getPacketSender().setHidden(931, 426, true);
            p.getPacketSender().setHidden(931, 427, false);
            p.getPacketSender().setHidden(931, 428, false);
            p.getPacketSender().setHidden(931, 296, true);
        }
        if (PlayerCounter.MEDIUM_DAILY_TASKS_COMPLETED.get(p) == 2) {
            p.getPacketSender().setHidden(931, 426, true);
            p.getPacketSender().setHidden(931, 427, true);
            p.getPacketSender().setHidden(931, 428, false);
            p.getPacketSender().setHidden(931, 296, true);
        }
        if (PlayerCounter.MEDIUM_DAILY_TASKS_COMPLETED.get(p) == 3) {
            p.getPacketSender().setHidden(931, 426, true);
            p.getPacketSender().setHidden(931, 427, true);
            p.getPacketSender().setHidden(931, 428, true);
            p.getPacketSender().setHidden(931, 296, true);
        }
    }

}