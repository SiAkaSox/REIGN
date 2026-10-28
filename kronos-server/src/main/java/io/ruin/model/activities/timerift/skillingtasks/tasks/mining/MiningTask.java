package io.ruin.model.activities.timerift.skillingtasks.tasks.mining;

import io.ruin.utility.Random;
import io.ruin.cache.Color;
import io.ruin.model.activities.timerift.IndividualTasks;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;

import static io.ruin.model.activities.timerift.TimeRift.hasTask;

public class MiningTask {

    public static void assignMiningTask(Player p, MiningTaskType contractType) {
        switch (contractType) {
            case NONE:
                p.hasAcceptedMiningTask = false;
                p.miningTaskAmount = 0;
                p.miningTaskType = MiningTaskType.NONE;
                break;
            case GOLD:
                p.hasAcceptedMiningTask = true;
                p.miningTaskAmount = Random.get(10, 20);
                p.miningTaskType = MiningTaskType.COAL;
                p.sendMessage("" + Color.ORANGE_RED.wrap("<shad>New Task: </col></shad>Mine x" + p.miningTaskAmount + " Gold Ore"));
                hasTask = true;
                break;
            case COAL:
                p.hasAcceptedMiningTask = true;
                p.miningTaskAmount = Random.get(10, 20);
                p.miningTaskType = MiningTaskType.COAL;
                p.sendMessage("" + Color.ORANGE_RED.wrap("<shad>New Task: </col></shad>Mine x" + p.miningTaskAmount + " Coal"));
                hasTask = true;
                break;
            case MITHRIL:
                p.hasAcceptedMiningTask = true;
                p.miningTaskAmount = Random.get(10, 20);
                p.miningTaskType = MiningTaskType.MITHRIL;
                p.sendMessage("" + Color.ORANGE_RED.wrap("<shad>New Task: </col></shad>Mine x" + p.miningTaskAmount + " Mithril Ore"));
                hasTask = true;
                break;
        }
    }

    public static void advanceMiningTask(Player p) {
        if (p.hasAcceptedMiningTask) {
            p.miningTaskAmount -= 1;
            p.getInventory().addOrDrop(24709, 1);
            p.sendMessage("+1 Rift Shards");
            return;
        }
        if (p.miningTaskAmount == 0) {
            p.hasCompletedMiningTask = true;
            IndividualTasks.completeTask(p);
        }
    }

    public static void resetMiningTask(Player p) {
        p.hasAcceptedMiningTask = false;
        p.miningTaskAmount = 0;
        p.miningTaskType = MiningTaskType.NONE;
    }

    public static void sendTaskCompleteOverlay(Player player) {
        player.startEvent(event -> {
            player.openInterface(InterfaceType.MAIN, 660);
            player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Task Complete", "Turn in for new task!");
            event.delay(2);
            player.closeInterface(InterfaceType.MAIN);
        });
    }

}
