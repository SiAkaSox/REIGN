package io.ruin.model.activities.timerift.skillingtasks.tasks.woodcutting;

import io.ruin.utility.Random;
import io.ruin.cache.Color;
import io.ruin.model.activities.timerift.IndividualTasks;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;

import static io.ruin.model.activities.timerift.TimeRift.hasTask;

public class WoodcuttingTask {

    public static void assignWoodcuttingTask(Player p, WoodcuttingTaskType taskType) {
        switch (taskType) {
            case TREE:
                p.hasAcceptedWoodcuttingTask = true;
                p.woodcuttingTaskAmount = Random.get(10, 20);
                p.woodcuttingTaskType = WoodcuttingTaskType.TREE;
                p.sendMessage("" + Color.ORANGE_RED.wrap("<shad>New Task: </col></shad>Cut x" + p.woodcuttingTaskAmount + " Normal Logs"));
                p.getPacketSender().sendString(822, 134, "Cut x"+p.woodcuttingTaskAmount +" Logs");
                hasTask = true;
                break;
            case OAK:
                p.hasAcceptedWoodcuttingTask = true;
                p.woodcuttingTaskAmount = Random.get(10, 20);
                p.woodcuttingTaskType = WoodcuttingTaskType.OAK;
                p.sendMessage("" + Color.ORANGE_RED.wrap("<shad>New Task: </col></shad>Cut x" + p.woodcuttingTaskAmount + " Oak Logs"));
                p.getPacketSender().sendString(822, 134, "Cut x"+p.woodcuttingTaskAmount +" Oak Logs");
                hasTask = true;
                break;
            case MAGIC:
                p.hasAcceptedWoodcuttingTask = true;
                p.woodcuttingTaskAmount = Random.get(10, 20);
                p.woodcuttingTaskType = WoodcuttingTaskType.MAGIC;
                p.sendMessage("" + Color.ORANGE_RED.wrap("<shad>New Task: </col></shad>Cut x" + p.woodcuttingTaskAmount + " Magic Logs"));
                p.getPacketSender().sendString(822, 134, "Cut x"+p.woodcuttingTaskAmount +" Magic Logs");
                hasTask = true;
                break;
        }
        return;
    }

    public static void advanceWoodcuttingContract(Player p) {
        if (p.hasAcceptedWoodcuttingTask) {
            p.woodcuttingTaskAmount -= 1;
            p.getInventory().addOrDrop(24709, 1);
            p.sendMessage("+1 Rift Shards");
            return;
        }
        if (p.woodcuttingTaskAmount == 0) {
            p.hasCompletedWoodcuttingTask = true;
            IndividualTasks.completeTask(p);
        }
    }

    public static void resetWoodcuttingContract(Player p) {
        p.hasAcceptedWoodcuttingTask = false;
        p.woodcuttingTaskAmount = 0;
        p.woodcuttingTaskType = WoodcuttingTaskType.NONE;
    }

    public static void sendTaskCompleteOverlay(Player p) {
        p.startEvent(event -> {
            p.openInterface(InterfaceType.MAIN, 660);
            p.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Task Completed", "Turn in for new task!");
            event.delay(2);
            p.closeInterface(InterfaceType.MAIN);
        });
    }

}