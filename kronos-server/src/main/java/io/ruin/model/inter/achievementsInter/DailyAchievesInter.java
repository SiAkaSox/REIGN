package io.ruin.model.inter.achievementsInter;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;

public class DailyAchievesInter {

    public static void openEasyDailies(Player p) {
        if (p.dailyTasks[0] == null) {
            p.dialogue(
                    new MessageDialogue("You do not currently have any daily achievements. Use ::dailies to get new ones!")
            );
        }
        p.openInterface(InterfaceType.MAIN, 932);
        p.getPacketSender().sendString(932, 13, "Daily Achievements - Easy");
        p.getPacketSender().sendString(932, 72, ""+p.dailyTasks[0].longDescription());
        p.getPacketSender().sendString(932, 34, ""+p.dailyTasks[1].longDescription());
        p.getPacketSender().sendString(932, 44, ""+p.dailyTasks[2].longDescription());
        if (p.dailyTaskProgress[0] >= p.dailyTasks[0].amountToKill) {
            p.getPacketSender().sendString(932, 73, "Progress: <col=27ae60>"+p.dailyTaskProgress[0]+"/"+p.dailyTasks[0].amountToKill);
        } else {
            p.getPacketSender().sendString(932, 73, "Progress: <col=FF0000>"+p.dailyTaskProgress[0]+"/"+p.dailyTasks[0].amountToKill);
        }
        if (p.dailyTaskProgress[1] >= p.dailyTasks[1].amountToKill) {
            p.getPacketSender().sendString(932, 35, "Progress: <col=27ae60>"+p.dailyTaskProgress[1]+"/"+p.dailyTasks[1].amountToKill);
        } else {
            p.getPacketSender().sendString(932, 35, "Progress: <col=FF0000>"+p.dailyTaskProgress[1]+"/"+p.dailyTasks[1].amountToKill);
        }
        if (p.dailyTaskProgress[2] >= p.dailyTasks[2].amountToKill) {
            p.getPacketSender().sendString(932, 45, "Progress: <col=27ae60>"+p.dailyTaskProgress[2]+"/"+p.dailyTasks[2].amountToKill);
        } else {
            p.getPacketSender().sendString(932, 45, "Progress: <col=FF0000>"+p.dailyTaskProgress[2]+"/"+p.dailyTasks[2].amountToKill);
        }
        p.getPacketSender().sendItems(932, 77, 0, new Item(+p.dailyTasks[0].itemReward, +p.dailyTasks[0].rewardAmount));
        p.getPacketSender().sendItems(932, 63, 0, new Item(+p.dailyTasks[1].itemReward, +p.dailyTasks[1].rewardAmount));
        p.getPacketSender().sendItems(932, 65, 0, new Item(+p.dailyTasks[2].itemReward, +p.dailyTasks[2].rewardAmount));
    }
    public static void openMediumDailies(Player p) {
        p.openInterface(InterfaceType.MAIN, 932);
        p.getPacketSender().sendString(932, 13, "Daily Achievements - Medium");
        p.getPacketSender().sendString(932, 72, ""+p.dailyTasksMedium[0].longDescription());
        p.getPacketSender().sendString(932, 34, ""+p.dailyTasksMedium[1].longDescription());
        p.getPacketSender().sendString(932, 44, ""+p.dailyTasksMedium[2].longDescription());
        if (p.dailyTaskMediumProgress[0] >= p.dailyTasksMedium[0].amountToKill) {
            p.getPacketSender().sendString(932, 73, "Progress: <col=27ae60>"+p.dailyTaskMediumProgress[0]+"/"+p.dailyTasksMedium[0].amountToKill);
        } else {
            p.getPacketSender().sendString(932, 73, "Progress: <col=FF0000>"+p.dailyTaskMediumProgress[0]+"/"+p.dailyTasksMedium[0].amountToKill);
        }
        if (p.dailyTaskMediumProgress[1] >= p.dailyTasksMedium[1].amountToKill) {
            p.getPacketSender().sendString(932, 35, "Progress: <col=27ae60>"+p.dailyTaskMediumProgress[1]+"/"+p.dailyTasksMedium[1].amountToKill);
        } else {
            p.getPacketSender().sendString(932, 35, "Progress: <col=FF0000>"+p.dailyTaskMediumProgress[1]+"/"+p.dailyTasksMedium[1].amountToKill);
        }
        if (p.dailyTaskMediumProgress[2] >= p.dailyTasksMedium[2].amountToKill) {
            p.getPacketSender().sendString(932, 45, "Progress: <col=27ae60>"+p.dailyTaskMediumProgress[2]+"/"+p.dailyTasksMedium[2].amountToKill);
        } else {
            p.getPacketSender().sendString(932, 45, "Progress: <col=FF0000>"+p.dailyTaskMediumProgress[2]+"/"+p.dailyTasksMedium[2].amountToKill);
        }
        p.getPacketSender().sendItems(932, 77, 0, new Item(+p.dailyTasksMedium[0].itemReward, +p.dailyTasksMedium[0].rewardAmount));
        p.getPacketSender().sendItems(932, 63, 0, new Item(+p.dailyTasksMedium[1].itemReward, +p.dailyTasksMedium[1].rewardAmount));
        p.getPacketSender().sendItems(932, 65, 0, new Item(+p.dailyTasksMedium[2].itemReward, +p.dailyTasksMedium[2].rewardAmount));
    }
    public static void openHardDailies(Player p) {
        p.openInterface(InterfaceType.MAIN, 932);
        p.getPacketSender().sendString(932, 13, "Daily Achievements - Hard");
        p.getPacketSender().sendString(932, 72, ""+p.dailyTasksHard[0].longDescription());
        p.getPacketSender().sendString(932, 34, ""+p.dailyTasksHard[1].longDescription());
        p.getPacketSender().sendString(932, 44, ""+p.dailyTasksHard[2].longDescription());
        if (p.dailyTaskHardProgress[0] >= p.dailyTasksHard[0].amountToKill) {
            p.getPacketSender().sendString(932, 73, "Progress: <col=27ae60>"+p.dailyTaskHardProgress[0]+"/"+p.dailyTasksHard[0].amountToKill);
        } else {
            p.getPacketSender().sendString(932, 73, "Progress: <col=FF0000>"+p.dailyTaskHardProgress[0]+"/"+p.dailyTasksHard[0].amountToKill);
        }
        if (p.dailyTaskHardProgress[1] >= p.dailyTasksHard[1].amountToKill) {
            p.getPacketSender().sendString(932, 35, "Progress: <col=27ae60>"+p.dailyTaskHardProgress[1]+"/"+p.dailyTasksHard[1].amountToKill);
        } else {
            p.getPacketSender().sendString(932, 35, "Progress: <col=FF0000>"+p.dailyTaskHardProgress[1]+"/"+p.dailyTasksHard[1].amountToKill);
        }
        if (p.dailyTaskHardProgress[2] >= p.dailyTasksHard[2].amountToKill) {
            p.getPacketSender().sendString(932, 45, "Progress: <col=27ae60>"+p.dailyTaskHardProgress[2]+"/"+p.dailyTasksHard[2].amountToKill);
        } else {
            p.getPacketSender().sendString(932, 45, "Progress: <col=FF0000>"+p.dailyTaskHardProgress[2]+"/"+p.dailyTasksHard[2].amountToKill);
        }
        p.getPacketSender().sendItems(932, 77, 0, new Item(+p.dailyTasksHard[0].itemReward, +p.dailyTasksHard[0].rewardAmount));
        p.getPacketSender().sendItems(932, 63, 0, new Item(+p.dailyTasksHard[1].itemReward, +p.dailyTasksHard[1].rewardAmount));
        p.getPacketSender().sendItems(932, 65, 0, new Item(+p.dailyTasksHard[2].itemReward, +p.dailyTasksHard[2].rewardAmount));
    }
    public static void openEliteDailies(Player p) {
        p.openInterface(InterfaceType.MAIN, 932);
        p.getPacketSender().sendString(932, 13, "Daily Achievements - Elite");
        p.getPacketSender().sendString(932, 72, ""+p.dailyTasksElite[0].longDescription());
        p.getPacketSender().sendString(932, 34, ""+p.dailyTasksElite[1].longDescription());
        p.getPacketSender().sendString(932, 44, ""+p.dailyTasksElite[2].longDescription());
        if (p.dailyTaskEliteProgress[0] >= p.dailyTasksElite[0].amountToKill) {
            p.getPacketSender().sendString(932, 73, "Progress: <col=27ae60>"+p.dailyTaskEliteProgress[0]+"/"+p.dailyTasksElite[0].amountToKill);
        } else {
            p.getPacketSender().sendString(932, 73, "Progress: <col=FF0000>"+p.dailyTaskEliteProgress[0]+"/"+p.dailyTasksElite[0].amountToKill);
        }
        if (p.dailyTaskEliteProgress[1] >= p.dailyTasksElite[1].amountToKill) {
            p.getPacketSender().sendString(932, 35, "Progress: <col=27ae60>"+p.dailyTaskEliteProgress[1]+"/"+p.dailyTasksElite[1].amountToKill);
        } else {
            p.getPacketSender().sendString(932, 35, "Progress: <col=FF0000>"+p.dailyTaskEliteProgress[1]+"/"+p.dailyTasksElite[1].amountToKill);
        }
        if (p.dailyTaskEliteProgress[2] >= p.dailyTasksElite[2].amountToKill) {
            p.getPacketSender().sendString(932, 45, "Progress: <col=27ae60>"+p.dailyTaskEliteProgress[2]+"/"+p.dailyTasksElite[2].amountToKill);
        } else {
            p.getPacketSender().sendString(932, 45, "Progress: <col=FF0000>"+p.dailyTaskEliteProgress[2]+"/"+p.dailyTasksElite[2].amountToKill);
        }
        p.getPacketSender().sendItems(932, 77, 0, new Item(+p.dailyTasksElite[0].itemReward, +p.dailyTasksElite[0].rewardAmount));
        p.getPacketSender().sendItems(932, 63, 0, new Item(+p.dailyTasksElite[1].itemReward, +p.dailyTasksElite[1].rewardAmount));
        p.getPacketSender().sendItems(932, 65, 0, new Item(+p.dailyTasksElite[2].itemReward, +p.dailyTasksElite[2].rewardAmount));
    }

}