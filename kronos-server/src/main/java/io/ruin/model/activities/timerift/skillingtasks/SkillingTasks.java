package io.ruin.model.activities.timerift.skillingtasks;

import io.ruin.utility.Random;
import io.ruin.model.activities.timerift.TimeRift;
import io.ruin.model.activities.timerift.skillingtasks.tasks.fishing.FishingTask;
import io.ruin.model.activities.timerift.skillingtasks.tasks.fishing.FishingTaskType;
import io.ruin.model.activities.timerift.skillingtasks.tasks.mining.MiningTask;
import io.ruin.model.activities.timerift.skillingtasks.tasks.mining.MiningTaskType;
import io.ruin.model.activities.timerift.skillingtasks.tasks.woodcutting.WoodcuttingTask;
import io.ruin.model.activities.timerift.skillingtasks.tasks.woodcutting.WoodcuttingTaskType;
import io.ruin.model.entity.player.Player;

import static io.ruin.model.activities.timerift.TimeRift.hasTask;

public class SkillingTasks {

    public static void assignTask(Player p) {
        TimeRift.canGetSoloTask = false;
        if (Random.get(3) == 1) {
            //Assign Woodcutting
            if (Random.get(3) == 3) {
                WoodcuttingTask.assignWoodcuttingTask(p, WoodcuttingTaskType.TREE);
                p.getPacketSender().sendString(822, 134, "Cut x"+p.woodcuttingTaskAmount +" Logs");
            }
            if (Random.get(3) == 2) {
                WoodcuttingTask.assignWoodcuttingTask(p, WoodcuttingTaskType.OAK);
                p.getPacketSender().sendString(822, 134, "Cut x"+p.woodcuttingTaskAmount +" Oak Logs");
            }
            if (Random.get(3) == 1) {
                WoodcuttingTask.assignWoodcuttingTask(p, WoodcuttingTaskType.MAGIC);
                 p.getPacketSender().sendString(822, 134, "Cut x"+p.woodcuttingTaskAmount +" Magic Logs");
            }
        }
        if (Random.get(3) == 2) {
            //Assign Fishing
            if (Random.get(3) == 3) {
                FishingTask.assignFishingTask(p, FishingTaskType.LOBSTER);
                p.getPacketSender().sendString(822, 134, "Catch x" + p.fishingTaskAmount + " Raw Lobsters");
            }
            if (Random.get(3) == 2) {
                FishingTask.assignFishingTask(p, FishingTaskType.SHARK);
                p.getPacketSender().sendString(822, 134, "Catch x" + p.fishingTaskAmount + " Raw Sharks");
            }
            if (Random.get(3) == 1) {
                FishingTask.assignFishingTask(p, FishingTaskType.DARK_CRAB);
                p.getPacketSender().sendString(822, 134, "Catch x" + p.fishingTaskAmount + " Raw Dark Crab");
            }
        }
        if (Random.get(3) == 1) {
            //Assign Mining
            if (Random.get(3) == 3) {
                MiningTask.assignMiningTask(p, MiningTaskType.COAL);
                p.getPacketSender().sendString(822, 134, "Mine x" + p.miningTaskAmount + " coal");
            }
            if (Random.get(3) == 2) {
                MiningTask.assignMiningTask(p, MiningTaskType.GOLD);
                p.getPacketSender().sendString(822, 134, "Mine x" + p.miningTaskAmount + " gold");
            }
            if (Random.get(3) == 1) {
                MiningTask.assignMiningTask(p, MiningTaskType.MITHRIL);
                p.getPacketSender().sendString(822, 134, "Mine x" + p.miningTaskAmount + " mithril");
            }
        }
    }

    public static void resetSkillingTasks(Player p) {
        FishingTask.resetFishingContractStatus(p);
        MiningTask.resetMiningTask(p);
        WoodcuttingTask.resetWoodcuttingContract(p);
        TimeRift.canGetSoloTask = true;
        hasTask = false;
    }

    public static boolean hasNoTask(Player p) {
        return !p.hasAcceptedFishingTask && !p.hasAcceptedMiningTask && !p.hasAcceptedWoodcuttingTask;
    }



}