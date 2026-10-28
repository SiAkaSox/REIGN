package io.ruin.model.activities.timerift.skillingtasks.tasks.fishing;

import io.ruin.utility.Random;
import io.ruin.cache.Color;
import io.ruin.model.activities.timerift.IndividualTasks;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;

import static io.ruin.model.activities.timerift.TimeRift.hasTask;

public class FishingTask {

        public static void assignFishingTask(Player p, FishingTaskType contractType) {
            switch (contractType) {
                case LOBSTER:
                    p.hasAcceptedFishingTask = true;
                    p.fishingTaskAmount = Random.get(10, 20);
                    p.fishingTaskType = FishingTaskType.LOBSTER;
                    p.sendMessage("" + Color.ORANGE_RED.wrap("<shad>New Task: </col></shad>Catch x" + p.fishingTaskAmount + " Raw Lobsters"));
                    p.getPacketSender().sendString(822, 134, "Catch x" + p.fishingTaskAmount + " Raw Lobsters");
                    hasTask = true;
                    break;
                case SHARK:
                    p.hasAcceptedFishingTask = true;
                    p.fishingTaskAmount = Random.get(10, 20);
                    p.fishingTaskType = FishingTaskType.SHARK;
                    p.sendMessage("" + Color.ORANGE_RED.wrap("<shad>New Task: </col></shad>Catch x" + p.fishingTaskAmount + " Raw Sharks"));
                    p.getPacketSender().sendString(822, 134, "Catch x" + p.fishingTaskAmount + " Raw Sharks");
                    hasTask = true;
                    break;
                case DARK_CRAB:
                    p.hasAcceptedFishingTask = true;
                    p.fishingTaskAmount = Random.get(10, 20);
                    p.fishingTaskType = FishingTaskType.DARK_CRAB;
                    p.sendMessage("" + Color.ORANGE_RED.wrap("<shad>New Task: </col></shad>Catch x" + p.fishingTaskAmount + " Raw Dark Crab"));
                    p.getPacketSender().sendString(822, 134, "Catch x" + p.fishingTaskAmount + " Raw Dark Crab");
                    hasTask = true;
                    break;
            }
        }

        public static void advanceFishingContract(Player p) {
            if (p.hasAcceptedFishingTask) {
                p.fishingTaskAmount -= 1;
                p.getInventory().addOrDrop(24709, 1);
                p.sendMessage("+1 Rift Shards");
                return;
            }
            if (p.fishingTaskAmount == 0) {
                p.hasCompletedFishingTask = true;
                IndividualTasks.completeTask(p);
            }
        }

        public static void resetFishingContractStatus(Player p) {
            p.hasAcceptedFishingTask = false;
            p.fishingTaskAmount = 0;
            p.fishingTaskType = FishingTaskType.NONE;
            p.hasCompletedFishingTask = false;
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