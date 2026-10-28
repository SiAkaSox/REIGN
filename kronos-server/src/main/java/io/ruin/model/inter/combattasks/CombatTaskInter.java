package io.ruin.model.inter.combattasks;

import io.ruin.Server;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;

import static io.ruin.Server.STARTUP_THREAD;

public class CombatTaskInter {

    public static int MAIN_INTERFACE = 853;
    public static int EASY_INTERFACE = 880;
    public static int MEDIUM_INTERFACE = 881;
    public static int HARD_INTERFACE = 882;
    public static int ELITE_INTERFACE = 883;
    public static int MASTER_INTERFACE = 884;
    public static int GRANDMASTER_INTERFACE = 885;

    static {

            NPCAction.register(3525, "Talk-to", (player, npc) -> openInter(player));
            InterfaceHandler.register(MAIN_INTERFACE, h -> {//Main interface
                h.actions[96] = (SlotAction) (player, slot) -> openEasyInter(player);
                h.actions[122] = (SlotAction) (player, slot) -> openMediumInter(player);
                h.actions[123] = (SlotAction) (player, slot) -> openHardInter(player);
                h.actions[124] = (SlotAction) (player, slot) -> openEliteInter(player);
                h.actions[125] = (SlotAction) (player, slot) -> openMasterInter(player);
                h.actions[126] = (SlotAction) (player, slot) -> openGrandMasterInter(player);
            });
            InterfaceHandler.register(EASY_INTERFACE, h -> {//Easy interface
                h.actions[96] = (SlotAction) (player, slot) -> openEasyInter(player);
                h.actions[122] = (SlotAction) (player, slot) -> openMediumInter(player);
                h.actions[123] = (SlotAction) (player, slot) -> openHardInter(player);
                h.actions[124] = (SlotAction) (player, slot) -> openEliteInter(player);
                h.actions[125] = (SlotAction) (player, slot) -> openMasterInter(player);
                h.actions[126] = (SlotAction) (player, slot) -> openGrandMasterInter(player);
                h.actions[270] = (SlotAction) (player, slot) -> openInter(player);
            });
            InterfaceHandler.register(MEDIUM_INTERFACE, h -> {//Medium interface
                h.actions[96] = (SlotAction) (player, slot) -> openEasyInter(player);
                h.actions[122] = (SlotAction) (player, slot) -> openMediumInter(player);
                h.actions[123] = (SlotAction) (player, slot) -> openHardInter(player);
                h.actions[124] = (SlotAction) (player, slot) -> openEliteInter(player);
                h.actions[125] = (SlotAction) (player, slot) -> openMasterInter(player);
                h.actions[126] = (SlotAction) (player, slot) -> openGrandMasterInter(player);
                h.actions[315] = (SlotAction) (player, slot) -> openInter(player);
            });
            InterfaceHandler.register(HARD_INTERFACE, h -> {//Hard interface
                h.actions[96] = (SlotAction) (player, slot) -> openEasyInter(player);
                h.actions[122] = (SlotAction) (player, slot) -> openMediumInter(player);
                h.actions[123] = (SlotAction) (player, slot) -> openHardInter(player);
                h.actions[124] = (SlotAction) (player, slot) -> openEliteInter(player);
                h.actions[125] = (SlotAction) (player, slot) -> openMasterInter(player);
                h.actions[126] = (SlotAction) (player, slot) -> openGrandMasterInter(player);
                h.actions[357] = (SlotAction) (player, slot) -> openInter(player);
            });
            InterfaceHandler.register(ELITE_INTERFACE, h -> {//Elite interface
                h.actions[96] = (SlotAction) (player, slot) -> openEasyInter(player);
                h.actions[122] = (SlotAction) (player, slot) -> openMediumInter(player);
                h.actions[123] = (SlotAction) (player, slot) -> openHardInter(player);
                h.actions[124] = (SlotAction) (player, slot) -> openEliteInter(player);
                h.actions[125] = (SlotAction) (player, slot) -> openMasterInter(player);
                h.actions[126] = (SlotAction) (player, slot) -> openGrandMasterInter(player);
                h.actions[469] = (SlotAction) (player, slot) -> openInter(player);
            });
            InterfaceHandler.register(MASTER_INTERFACE, h -> {//Master interface
                h.actions[96] = (SlotAction) (player, slot) -> openEasyInter(player);
                h.actions[122] = (SlotAction) (player, slot) -> openMediumInter(player);
                h.actions[123] = (SlotAction) (player, slot) -> openHardInter(player);
                h.actions[124] = (SlotAction) (player, slot) -> openEliteInter(player);
                h.actions[125] = (SlotAction) (player, slot) -> openMasterInter(player);
                h.actions[126] = (SlotAction) (player, slot) -> openGrandMasterInter(player);
                h.actions[445] = (SlotAction) (player, slot) -> openInter(player);
            });
            InterfaceHandler.register(GRANDMASTER_INTERFACE, h -> {//Grand Master interface
                h.actions[96] = (SlotAction) (player, slot) -> openEasyInter(player);
                h.actions[122] = (SlotAction) (player, slot) -> openMediumInter(player);
                h.actions[123] = (SlotAction) (player, slot) -> openHardInter(player);
                h.actions[124] = (SlotAction) (player, slot) -> openEliteInter(player);
                h.actions[125] = (SlotAction) (player, slot) -> openMasterInter(player);
                h.actions[126] = (SlotAction) (player, slot) -> openGrandMasterInter(player);
                h.actions[312] = (SlotAction) (player, slot) -> openInter(player);
            });
    }

    public static void openInter(Player player) {
        player.openInterface(InterfaceType.MAIN, MAIN_INTERFACE);
        player.getPacketSender().sendString(MAIN_INTERFACE, 157, "PvM Profile - "+player.name());
        player.getPacketSender().sendString(MAIN_INTERFACE, 145, ""+player.totalTasksCompleted);
        player.getPacketSender().sendString(MAIN_INTERFACE, 146, ""+player.totalBossKills);
        player.getPacketSender().sendString(MAIN_INTERFACE, 147, ""+player.totalSkillingBossKills);
        player.getPacketSender().sendString(MAIN_INTERFACE, 148, "0");
        player.getPacketSender().sendString(MAIN_INTERFACE, 149, "None!");
        player.getPacketSender().sendString(MAIN_INTERFACE, 150, "None!");
        player.getPacketSender().sendString(MAIN_INTERFACE, 151, "None!");
        //Easy
        if (player.combatTaskEasyCompleted == 0) {//Not started
            player.getPacketSender().sendString(MAIN_INTERFACE, 134, "" + player.combatTaskEasyCompleted + "/27");
        }
        if (player.combatTaskEasyCompleted >= 1 && player.combatTaskEasyCompleted < 27) {//In progress
            player.getPacketSender().sendString(MAIN_INTERFACE, 134, "<col=ffff00>" + player.combatTaskEasyCompleted + "/27");
        }
        if (player.combatTaskEasyCompleted == 27) {//Completed
            player.getPacketSender().sendString(MAIN_INTERFACE, 134, "<col=0fd60f>" + player.combatTaskEasyCompleted + "/27");
        }
        //Medium
        if (player.combatTaskMediumCompleted == 0) {//Not started
            player.getPacketSender().sendString(MAIN_INTERFACE, 135, "" + player.combatTaskMediumCompleted + "/34");
        }
        if (player.combatTaskMediumCompleted > 1 && player.combatTaskMediumCompleted < 34) {//In progress
            player.getPacketSender().sendString(MAIN_INTERFACE, 135, "<col=ffff00>" + player.combatTaskMediumCompleted + "/34");
        }
        if (player.combatTaskMediumCompleted == 34) {//Completed
            player.getPacketSender().sendString(MAIN_INTERFACE, 135, "<col=0fd60f>" + player.combatTaskMediumCompleted + "/34");
        }
        //Hard
        if (player.combatTaskHardCompleted == 0) {//Not started
            player.getPacketSender().sendString(MAIN_INTERFACE, 136, "" + player.combatTaskHardCompleted + "/45");
        }
        if (player.combatTaskHardCompleted > 1 && player.combatTaskHardCompleted < 45) {//In progress
            player.getPacketSender().sendString(MAIN_INTERFACE, 136, "<col=ffff00>" + player.combatTaskHardCompleted + "/45");
        }
        if (player.combatTaskHardCompleted == 45) {//Completed
            player.getPacketSender().sendString(MAIN_INTERFACE, 136, "<col=0fd60f>" + player.combatTaskHardCompleted + "/45");
        }
        //Elite
        if (player.combatTaskEliteCompleted == 0) {//Not started
            player.getPacketSender().sendString(MAIN_INTERFACE, 137, "" + player.combatTaskEliteCompleted + "/64");
        }
        if (player.combatTaskEliteCompleted > 1 && player.combatTaskEliteCompleted < 64) {//In progress
            player.getPacketSender().sendString(MAIN_INTERFACE, 137, "<col=ffff00>" + player.combatTaskEliteCompleted + "/64");
        }
        if (player.combatTaskEliteCompleted == 64) {//Completed
            player.getPacketSender().sendString(MAIN_INTERFACE, 137, "<col=0fd60f>" + player.combatTaskEliteCompleted + "/64");
        }
        //Master
        if (player.combatTaskMasterCompleted == 0) {//Not started
            player.getPacketSender().sendString(MAIN_INTERFACE, 138, "" + player.combatTaskMasterCompleted + "/38");
        }
        if (player.combatTaskMasterCompleted > 1 && player.combatTaskMasterCompleted < 38) {//In progress
            player.getPacketSender().sendString(MAIN_INTERFACE, 138, "<col=ffff00>" + player.combatTaskMasterCompleted + "/38");
        }
        if (player.combatTaskMasterCompleted == 38) {//Completed
            player.getPacketSender().sendString(MAIN_INTERFACE, 138, "<col=0fd60f>" + player.combatTaskMasterCompleted + "/38");
        }
        //Grandmaster
        if (player.combatTaskGrandmasterCompleted == 0) {//Not started
            player.getPacketSender().sendString(MAIN_INTERFACE, 139, "" + player.combatTaskGrandmasterCompleted + "/34");
        }
        if (player.combatTaskGrandmasterCompleted > 1 && player.combatTaskGrandmasterCompleted < 34) {//In progress
            player.getPacketSender().sendString(MAIN_INTERFACE, 139, "<col=ffff00>" + player.combatTaskGrandmasterCompleted + "/34");
        }
        if (player.combatTaskGrandmasterCompleted == 34) {//Completed
            player.getPacketSender().sendString(MAIN_INTERFACE, 139, "<col=0fd60f>" + player.combatTaskGrandmasterCompleted + "/34");
        }
    }

    public static void openEasyInter(Player player) {
        player.openInterface(InterfaceType.MAIN, EASY_INTERFACE);
        sendEasyList(player);
        easyTaskCountSender(player);
    }
    public static void openMediumInter(Player player) {
        player.openInterface(InterfaceType.MAIN, MEDIUM_INTERFACE);
        mediumTaskCountSender(player);
        sendMediumList(player);
    }
    public static void openHardInter(Player player) {
        player.openInterface(InterfaceType.MAIN, HARD_INTERFACE);
        hardTaskCountSender(player);
        sendHardList(player);
    }
    public static void openEliteInter(Player player) {
        player.openInterface(InterfaceType.MAIN, ELITE_INTERFACE);
        eliteTaskCountSender(player);
        sendEliteList(player);
    }
    public static void openMasterInter(Player player) {
        player.openInterface(InterfaceType.MAIN, MASTER_INTERFACE);
        masterTaskCountSender(player);
        sendMasterList(player);
    }
    public static void openGrandMasterInter(Player player) {
        player.openInterface(InterfaceType.MAIN, GRANDMASTER_INTERFACE);
        grandmasterTaskCountSender(player);
        sendGrandMasterList(player);
    }

    public static void easyTaskCountSender(Player player) {
        player.getPacketSender().sendString(EASY_INTERFACE, 228, "PvM Profile - "+player.name());
        //Easy
        if (player.combatTaskEasyCompleted == 0) {//Not started
            player.getPacketSender().sendString(EASY_INTERFACE, 134, "" + player.combatTaskEasyCompleted + "/27");
        }
        if (player.combatTaskEasyCompleted >= 1 && player.combatTaskEasyCompleted < 27) {//In progress
            player.getPacketSender().sendString(EASY_INTERFACE, 134, "<col=ffff00>" + player.combatTaskEasyCompleted + "/27");
        }
        if (player.combatTaskEasyCompleted == 27) {//Completed
            player.getPacketSender().sendString(EASY_INTERFACE, 134, "<col=0fd60f>" + player.combatTaskEasyCompleted + "/27");
        }
        //Medium
        if (player.combatTaskMediumCompleted == 0) {//Not started
            player.getPacketSender().sendString(EASY_INTERFACE, 135, "" + player.combatTaskMediumCompleted + "/34");
        }
        if (player.combatTaskMediumCompleted >= 1 && player.combatTaskMediumCompleted < 34) {//In progress
            player.getPacketSender().sendString(EASY_INTERFACE, 135, "<col=ffff00>" + player.combatTaskMediumCompleted + "/34");
        }
        if (player.combatTaskMediumCompleted == 34) {//Completed
            player.getPacketSender().sendString(EASY_INTERFACE, 135, "<col=0fd60f>" + player.combatTaskMediumCompleted + "/34");
        }
        //Hard
        if (player.combatTaskHardCompleted == 0) {//Not started
            player.getPacketSender().sendString(EASY_INTERFACE, 136, "" + player.combatTaskHardCompleted + "/45");
        }
        if (player.combatTaskHardCompleted >= 1 && player.combatTaskHardCompleted < 45) {//In progress
            player.getPacketSender().sendString(EASY_INTERFACE, 136, "<col=ffff00>" + player.combatTaskHardCompleted + "/45");
        }
        if (player.combatTaskHardCompleted == 45) {//Completed
            player.getPacketSender().sendString(EASY_INTERFACE, 136, "<col=0fd60f>" + player.combatTaskHardCompleted + "/45");
        }
        //Elite
        if (player.combatTaskEliteCompleted == 0) {//Not started
            player.getPacketSender().sendString(EASY_INTERFACE, 137, "" + player.combatTaskEliteCompleted + "/64");
        }
        if (player.combatTaskEliteCompleted >= 1 && player.combatTaskEliteCompleted < 64) {//In progress
            player.getPacketSender().sendString(EASY_INTERFACE, 137, "<col=ffff00>" + player.combatTaskEliteCompleted + "/64");
        }
        if (player.combatTaskEliteCompleted == 64) {//Completed
            player.getPacketSender().sendString(EASY_INTERFACE, 137, "<col=0fd60f>" + player.combatTaskEliteCompleted + "/64");
        }
        //Master
        if (player.combatTaskMasterCompleted == 0) {//Not started
            player.getPacketSender().sendString(EASY_INTERFACE, 138, "" + player.combatTaskMasterCompleted + "/38");
        }
        if (player.combatTaskMasterCompleted >= 1 && player.combatTaskMasterCompleted < 38) {//In progress
            player.getPacketSender().sendString(EASY_INTERFACE, 138, "<col=ffff00>" + player.combatTaskMasterCompleted + "/38");
        }
        if (player.combatTaskMasterCompleted == 38) {//Completed
            player.getPacketSender().sendString(EASY_INTERFACE, 138, "<col=0fd60f>" + player.combatTaskMasterCompleted + "/38");
        }
        //Grandmaster
        if (player.combatTaskGrandmasterCompleted == 0) {//Not started
            player.getPacketSender().sendString(EASY_INTERFACE, 139, "" + player.combatTaskGrandmasterCompleted + "/34");
        }
        if (player.combatTaskGrandmasterCompleted >= 1 && player.combatTaskGrandmasterCompleted < 34) {//In progress
            player.getPacketSender().sendString(EASY_INTERFACE, 139, "<col=ffff00>" + player.combatTaskGrandmasterCompleted + "/34");
        }
        if (player.combatTaskGrandmasterCompleted == 34) {//Completed
            player.getPacketSender().sendString(EASY_INTERFACE, 139, "<col=0fd60f>" + player.combatTaskGrandmasterCompleted + "/34");
        }
    }

    public static void mediumTaskCountSender(Player player) {
        player.getPacketSender().sendString(MEDIUM_INTERFACE, 228, "PvM Profile - "+player.name());
        //Easy
        if (player.combatTaskEasyCompleted == 0) {//Not started
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 134, "" + player.combatTaskEasyCompleted + "/27");
        }
        if (player.combatTaskEasyCompleted >= 1 && player.combatTaskEasyCompleted < 27) {//In progress
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 134, "<col=ffff00>" + player.combatTaskEasyCompleted + "/27");
        }
        if (player.combatTaskEasyCompleted == 27) {//Completed
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 134, "<col=0fd60f>" + player.combatTaskEasyCompleted + "/27");
        }
        //Medium
        if (player.combatTaskMediumCompleted == 0) {//Not started
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 135, "" + player.combatTaskMediumCompleted + "/34");
        }
        if (player.combatTaskMediumCompleted >= 1 && player.combatTaskMediumCompleted < 34) {//In progress
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 135, "<col=ffff00>" + player.combatTaskMediumCompleted + "/34");
        }
        if (player.combatTaskMediumCompleted == 34) {//Completed
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 135, "<col=0fd60f>" + player.combatTaskMediumCompleted + "/34");
        }
        //Hard
        if (player.combatTaskHardCompleted == 0) {//Not started
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 136, "" + player.combatTaskHardCompleted + "/45");
        }
        if (player.combatTaskHardCompleted >= 1 && player.combatTaskHardCompleted < 45) {//In progress
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 136, "<col=ffff00>" + player.combatTaskHardCompleted + "/45");
        }
        if (player.combatTaskHardCompleted == 45) {//Completed
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 136, "<col=0fd60f>" + player.combatTaskHardCompleted + "/45");
        }
        //Elite
        if (player.combatTaskEliteCompleted == 0) {//Not started
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 137, "" + player.combatTaskEliteCompleted + "/64");
        }
        if (player.combatTaskEliteCompleted > 1 && player.combatTaskEliteCompleted < 64) {//In progress
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 137, "<col=ffff00>" + player.combatTaskEliteCompleted + "/64");
        }
        if (player.combatTaskEliteCompleted == 64) {//Completed
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 137, "<col=0fd60f>" + player.combatTaskEliteCompleted + "/64");
        }
        //Master
        if (player.combatTaskMasterCompleted == 0) {//Not started
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 138, "" + player.combatTaskMasterCompleted + "/38");
        }
        if (player.combatTaskMasterCompleted > 1 && player.combatTaskMasterCompleted < 38) {//In progress
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 138, "<col=ffff00>" + player.combatTaskMasterCompleted + "/38");
        }
        if (player.combatTaskMasterCompleted == 38) {//Completed
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 138, "<col=0fd60f>" + player.combatTaskMasterCompleted + "/38");
        }
        //Grandmaster
        if (player.combatTaskGrandmasterCompleted == 0) {//Not started
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 139, "" + player.combatTaskGrandmasterCompleted + "/34");
        }
        if (player.combatTaskGrandmasterCompleted > 1 && player.combatTaskGrandmasterCompleted < 34) {//In progress
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 139, "<col=ffff00>" + player.combatTaskGrandmasterCompleted + "/34");
        }
        if (player.combatTaskGrandmasterCompleted == 34) {//Completed
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 139, "<col=0fd60f>" + player.combatTaskGrandmasterCompleted + "/34");
        }
    }
    public static void hardTaskCountSender(Player player) {
        player.getPacketSender().sendString(HARD_INTERFACE, 228, "PvM Profile - "+player.name());
        //Easy
        if (player.combatTaskEasyCompleted == 0) {//Not started
            player.getPacketSender().sendString(HARD_INTERFACE, 134, "" + player.combatTaskEasyCompleted + "/27");
        }
        if (player.combatTaskEasyCompleted > 1 && player.combatTaskEasyCompleted < 27) {//In progress
            player.getPacketSender().sendString(HARD_INTERFACE, 134, "<col=ffff00>" + player.combatTaskEasyCompleted + "/27");
        }
        if (player.combatTaskEasyCompleted == 27) {//Completed
            player.getPacketSender().sendString(HARD_INTERFACE, 134, "<col=0fd60f>" + player.combatTaskEasyCompleted + "/27");
        }
        //Medium
        if (player.combatTaskMediumCompleted == 0) {//Not started
            player.getPacketSender().sendString(HARD_INTERFACE, 135, "" + player.combatTaskMediumCompleted + "/34");
        }
        if (player.combatTaskMediumCompleted > 1 && player.combatTaskMediumCompleted < 34) {//In progress
            player.getPacketSender().sendString(HARD_INTERFACE, 135, "<col=ffff00>" + player.combatTaskMediumCompleted + "/34");
        }
        if (player.combatTaskMediumCompleted == 34) {//Completed
            player.getPacketSender().sendString(HARD_INTERFACE, 135, "<col=0fd60f>" + player.combatTaskMediumCompleted + "/34");
        }
        //Hard
        if (player.combatTaskHardCompleted == 0) {//Not started
            player.getPacketSender().sendString(HARD_INTERFACE, 136, "" + player.combatTaskHardCompleted + "/45");
        }
        if (player.combatTaskHardCompleted > 1 && player.combatTaskHardCompleted < 45) {//In progress
            player.getPacketSender().sendString(HARD_INTERFACE, 136, "<col=ffff00>" + player.combatTaskHardCompleted + "/45");
        }
        if (player.combatTaskHardCompleted == 45) {//Completed
            player.getPacketSender().sendString(HARD_INTERFACE, 136, "<col=0fd60f>" + player.combatTaskHardCompleted + "/45");
        }
        //Elite
        if (player.combatTaskEliteCompleted == 0) {//Not started
            player.getPacketSender().sendString(HARD_INTERFACE, 137, "" + player.combatTaskEliteCompleted + "/64");
        }
        if (player.combatTaskEliteCompleted > 1 && player.combatTaskEliteCompleted < 64) {//In progress
            player.getPacketSender().sendString(HARD_INTERFACE, 137, "<col=ffff00>" + player.combatTaskEliteCompleted + "/64");
        }
        if (player.combatTaskEliteCompleted == 64) {//Completed
            player.getPacketSender().sendString(HARD_INTERFACE, 137, "<col=0fd60f>" + player.combatTaskEliteCompleted + "/64");
        }
        //Master
        if (player.combatTaskMasterCompleted == 0) {//Not started
            player.getPacketSender().sendString(HARD_INTERFACE, 138, "" + player.combatTaskMasterCompleted + "/38");
        }
        if (player.combatTaskMasterCompleted > 1 && player.combatTaskMasterCompleted < 38) {//In progress
            player.getPacketSender().sendString(HARD_INTERFACE, 138, "<col=ffff00>" + player.combatTaskMasterCompleted + "/38");
        }
        if (player.combatTaskMasterCompleted == 38) {//Completed
            player.getPacketSender().sendString(HARD_INTERFACE, 138, "<col=0fd60f>" + player.combatTaskMasterCompleted + "/38");
        }
        //Grandmaster
        if (player.combatTaskGrandmasterCompleted == 0) {//Not started
            player.getPacketSender().sendString(HARD_INTERFACE, 139, "" + player.combatTaskGrandmasterCompleted + "/34");
        }
        if (player.combatTaskGrandmasterCompleted > 1 && player.combatTaskGrandmasterCompleted < 34) {//In progress
            player.getPacketSender().sendString(HARD_INTERFACE, 139, "<col=ffff00>" + player.combatTaskGrandmasterCompleted + "/34");
        }
        if (player.combatTaskGrandmasterCompleted == 34) {//Completed
            player.getPacketSender().sendString(HARD_INTERFACE, 139, "<col=0fd60f>" + player.combatTaskGrandmasterCompleted + "/34");
        }
    }
    public static void eliteTaskCountSender(Player player) {
        player.getPacketSender().sendString(ELITE_INTERFACE, 228, "PvM Profile - "+player.name());
        //Easy
        if (player.combatTaskEasyCompleted == 0) {//Not started
            player.getPacketSender().sendString(ELITE_INTERFACE, 134, "" + player.combatTaskEasyCompleted + "/27");
        }
        if (player.combatTaskEasyCompleted > 1 && player.combatTaskEasyCompleted < 27) {//In progress
            player.getPacketSender().sendString(ELITE_INTERFACE, 134, "<col=ffff00>" + player.combatTaskEasyCompleted + "/27");
        }
        if (player.combatTaskEasyCompleted == 27) {//Completed
            player.getPacketSender().sendString(ELITE_INTERFACE, 134, "<col=0fd60f>" + player.combatTaskEasyCompleted + "/27");
        }
        //Medium
        if (player.combatTaskMediumCompleted == 0) {//Not started
            player.getPacketSender().sendString(ELITE_INTERFACE, 135, "" + player.combatTaskMediumCompleted + "/34");
        }
        if (player.combatTaskMediumCompleted > 1 && player.combatTaskMediumCompleted < 34) {//In progress
            player.getPacketSender().sendString(ELITE_INTERFACE, 135, "<col=ffff00>" + player.combatTaskMediumCompleted + "/34");
        }
        if (player.combatTaskMediumCompleted == 34) {//Completed
            player.getPacketSender().sendString(ELITE_INTERFACE, 135, "<col=0fd60f>" + player.combatTaskMediumCompleted + "/34");
        }
        //Hard
        if (player.combatTaskHardCompleted == 0) {//Not started
            player.getPacketSender().sendString(ELITE_INTERFACE, 136, "" + player.combatTaskHardCompleted + "/45");
        }
        if (player.combatTaskHardCompleted > 1 && player.combatTaskHardCompleted < 45) {//In progress
            player.getPacketSender().sendString(ELITE_INTERFACE, 136, "<col=ffff00>" + player.combatTaskHardCompleted + "/45");
        }
        if (player.combatTaskHardCompleted == 45) {//Completed
            player.getPacketSender().sendString(ELITE_INTERFACE, 136, "<col=0fd60f>" + player.combatTaskHardCompleted + "/45");
        }
        //Elite
        if (player.combatTaskEliteCompleted == 0) {//Not started
            player.getPacketSender().sendString(ELITE_INTERFACE, 137, "" + player.combatTaskEliteCompleted + "/64");
        }
        if (player.combatTaskEliteCompleted > 1 && player.combatTaskEliteCompleted < 64) {//In progress
            player.getPacketSender().sendString(ELITE_INTERFACE, 137, "<col=ffff00>" + player.combatTaskEliteCompleted + "/64");
        }
        if (player.combatTaskEliteCompleted == 64) {//Completed
            player.getPacketSender().sendString(ELITE_INTERFACE, 137, "<col=0fd60f>" + player.combatTaskEliteCompleted + "/64");
        }
        //Master
        if (player.combatTaskMasterCompleted == 0) {//Not started
            player.getPacketSender().sendString(ELITE_INTERFACE, 138, "" + player.combatTaskMasterCompleted + "/38");
        }
        if (player.combatTaskMasterCompleted > 1 && player.combatTaskMasterCompleted < 38) {//In progress
            player.getPacketSender().sendString(ELITE_INTERFACE, 138, "<col=ffff00>" + player.combatTaskMasterCompleted + "/38");
        }
        if (player.combatTaskMasterCompleted == 38) {//Completed
            player.getPacketSender().sendString(ELITE_INTERFACE, 138, "<col=0fd60f>" + player.combatTaskMasterCompleted + "/38");
        }
        //Grandmaster
        if (player.combatTaskGrandmasterCompleted == 0) {//Not started
            player.getPacketSender().sendString(ELITE_INTERFACE, 139, "" + player.combatTaskGrandmasterCompleted + "/34");
        }
        if (player.combatTaskGrandmasterCompleted > 1 && player.combatTaskGrandmasterCompleted < 34) {//In progress
            player.getPacketSender().sendString(ELITE_INTERFACE, 139, "<col=ffff00>" + player.combatTaskGrandmasterCompleted + "/34");
        }
        if (player.combatTaskGrandmasterCompleted == 34) {//Completed
            player.getPacketSender().sendString(ELITE_INTERFACE, 139, "<col=0fd60f>" + player.combatTaskGrandmasterCompleted + "/34");
        }
    }
    public static void masterTaskCountSender(Player player) {
        player.getPacketSender().sendString(MASTER_INTERFACE, 228, "PvM Profile - "+player.name());
        //Easy
        if (player.combatTaskEasyCompleted == 0) {//Not started
            player.getPacketSender().sendString(MASTER_INTERFACE, 134, "" + player.combatTaskEasyCompleted + "/27");
        }
        if (player.combatTaskEasyCompleted > 1 && player.combatTaskEasyCompleted < 27) {//In progress
            player.getPacketSender().sendString(MASTER_INTERFACE, 134, "<col=ffff00>" + player.combatTaskEasyCompleted + "/27");
        }
        if (player.combatTaskEasyCompleted == 27) {//Completed
            player.getPacketSender().sendString(MASTER_INTERFACE, 134, "<col=0fd60f>" + player.combatTaskEasyCompleted + "/27");
        }
        //Medium
        if (player.combatTaskMediumCompleted == 0) {//Not started
            player.getPacketSender().sendString(MASTER_INTERFACE, 135, "" + player.combatTaskMediumCompleted + "/34");
        }
        if (player.combatTaskMediumCompleted > 1 && player.combatTaskMediumCompleted < 34) {//In progress
            player.getPacketSender().sendString(MASTER_INTERFACE, 135, "<col=ffff00>" + player.combatTaskMediumCompleted + "/34");
        }
        if (player.combatTaskMediumCompleted == 34) {//Completed
            player.getPacketSender().sendString(MASTER_INTERFACE, 135, "<col=0fd60f>" + player.combatTaskMediumCompleted + "/34");
        }
        //Hard
        if (player.combatTaskHardCompleted == 0) {//Not started
            player.getPacketSender().sendString(MASTER_INTERFACE, 136, "" + player.combatTaskHardCompleted + "/45");
        }
        if (player.combatTaskHardCompleted > 1 && player.combatTaskHardCompleted < 45) {//In progress
            player.getPacketSender().sendString(MASTER_INTERFACE, 136, "<col=ffff00>" + player.combatTaskHardCompleted + "/45");
        }
        if (player.combatTaskHardCompleted == 45) {//Completed
            player.getPacketSender().sendString(MASTER_INTERFACE, 136, "<col=0fd60f>" + player.combatTaskHardCompleted + "/45");
        }
        //Elite
        if (player.combatTaskEliteCompleted == 0) {//Not started
            player.getPacketSender().sendString(MASTER_INTERFACE, 137, "" + player.combatTaskEliteCompleted + "/64");
        }
        if (player.combatTaskEliteCompleted > 1 && player.combatTaskEliteCompleted < 64) {//In progress
            player.getPacketSender().sendString(MASTER_INTERFACE, 137, "<col=ffff00>" + player.combatTaskEliteCompleted + "/64");
        }
        if (player.combatTaskEliteCompleted == 64) {//Completed
            player.getPacketSender().sendString(MASTER_INTERFACE, 137, "<col=0fd60f>" + player.combatTaskEliteCompleted + "/64");
        }
        //Master
        if (player.combatTaskMasterCompleted == 0) {//Not started
            player.getPacketSender().sendString(MASTER_INTERFACE, 138, "" + player.combatTaskMasterCompleted + "/38");
        }
        if (player.combatTaskMasterCompleted > 1 && player.combatTaskMasterCompleted < 38) {//In progress
            player.getPacketSender().sendString(MASTER_INTERFACE, 138, "<col=ffff00>" + player.combatTaskMasterCompleted + "/38");
        }
        if (player.combatTaskMasterCompleted == 38) {//Completed
            player.getPacketSender().sendString(MASTER_INTERFACE, 138, "<col=0fd60f>" + player.combatTaskMasterCompleted + "/38");
        }
        //Grandmaster
        if (player.combatTaskGrandmasterCompleted == 0) {//Not started
            player.getPacketSender().sendString(MASTER_INTERFACE, 139, "" + player.combatTaskGrandmasterCompleted + "/34");
        }
        if (player.combatTaskGrandmasterCompleted > 1 && player.combatTaskGrandmasterCompleted < 34) {//In progress
            player.getPacketSender().sendString(MASTER_INTERFACE, 139, "<col=ffff00>" + player.combatTaskGrandmasterCompleted + "/34");
        }
        if (player.combatTaskGrandmasterCompleted == 34) {//Completed
            player.getPacketSender().sendString(MASTER_INTERFACE, 139, "<col=0fd60f>" + player.combatTaskGrandmasterCompleted + "/34");
        }
    }
    public static void grandmasterTaskCountSender(Player player) {
        player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 228, "PvM Profile - "+player.name());
        //Easy
        if (player.combatTaskEasyCompleted == 0) {//Not started
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 134, "" + player.combatTaskEasyCompleted + "/27");
        }
        if (player.combatTaskEasyCompleted > 1 && player.combatTaskEasyCompleted < 27) {//In progress
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 134, "<col=ffff00>" + player.combatTaskEasyCompleted + "/27");
        }
        if (player.combatTaskEasyCompleted == 27) {//Completed
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 134, "<col=0fd60f>" + player.combatTaskEasyCompleted + "/27");
        }
        //Medium
        if (player.combatTaskMediumCompleted == 0) {//Not started
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 135, "" + player.combatTaskMediumCompleted + "/34");
        }
        if (player.combatTaskMediumCompleted > 1 && player.combatTaskMediumCompleted < 34) {//In progress
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 135, "<col=ffff00>" + player.combatTaskMediumCompleted + "/34");
        }
        if (player.combatTaskMediumCompleted == 34) {//Completed
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 135, "<col=0fd60f>" + player.combatTaskMediumCompleted + "/34");
        }
        //Hard
        if (player.combatTaskHardCompleted == 0) {//Not started
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 136, "" + player.combatTaskHardCompleted + "/45");
        }
        if (player.combatTaskHardCompleted > 1 && player.combatTaskHardCompleted < 45) {//In progress
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 136, "<col=ffff00>" + player.combatTaskHardCompleted + "/45");
        }
        if (player.combatTaskHardCompleted == 45) {//Completed
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 136, "<col=0fd60f>" + player.combatTaskHardCompleted + "/45");
        }
        //Elite
        if (player.combatTaskEliteCompleted == 0) {//Not started
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 137, "" + player.combatTaskEliteCompleted + "/64");
        }
        if (player.combatTaskEliteCompleted > 1 && player.combatTaskEliteCompleted < 64) {//In progress
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 137, "<col=ffff00>" + player.combatTaskEliteCompleted + "/64");
        }
        if (player.combatTaskEliteCompleted == 64) {//Completed
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 137, "<col=0fd60f>" + player.combatTaskEliteCompleted + "/64");
        }
        //Master
        if (player.combatTaskMasterCompleted == 0) {//Not started
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 138, "" + player.combatTaskMasterCompleted + "/38");
        }
        if (player.combatTaskMasterCompleted > 1 && player.combatTaskMasterCompleted < 38) {//In progress
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 138, "<col=ffff00>" + player.combatTaskMasterCompleted + "/38");
        }
        if (player.combatTaskMasterCompleted == 38) {//Completed
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 138, "<col=0fd60f>" + player.combatTaskMasterCompleted + "/38");
        }
        //Grandmaster
        if (player.combatTaskGrandmasterCompleted == 0) {//Not started
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 139, "" + player.combatTaskGrandmasterCompleted + "/34");
        }
        if (player.combatTaskGrandmasterCompleted > 1 && player.combatTaskGrandmasterCompleted < 34) {//In progress
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 139, "<col=ffff00>" + player.combatTaskGrandmasterCompleted + "/34");
        }
        if (player.combatTaskGrandmasterCompleted == 34) {//Completed
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 139, "<col=0fd60f>" + player.combatTaskGrandmasterCompleted + "/34");
        }
    }

    public static void sendEasyList(Player player) {
        if (player.noxiousFoe == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 227, "" + CombatTaskList.NOXIOUS_FOE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 145, ""+CombatTaskList.NOXIOUS_FOE.description());//Description
        }
        if (player.noxiousFoe >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 227, "<col=0fd60f>" + CombatTaskList.NOXIOUS_FOE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 145, ""+CombatTaskList.NOXIOUS_FOE.description());//Description
        }
        if (player.barrowsNovice == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 45, "" + CombatTaskList.BARROWS_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 146, "" + CombatTaskList.BARROWS_NOVICE.description());//Description
        }
        if (player.barrowsNovice > 0 && player.barrowsNovice < 10) {
            player.getPacketSender().sendString(EASY_INTERFACE, 45, "<col=ffff00>" + CombatTaskList.BARROWS_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 146, "" + CombatTaskList.BARROWS_NOVICE.description());//Description
        }
        if (player.barrowsNovice >= 10) {
            player.getPacketSender().sendString(EASY_INTERFACE, 45, "<col=0fd60f>" + CombatTaskList.BARROWS_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 146, "" + CombatTaskList.BARROWS_NOVICE.description());//Description
        }
        if (player.defencewhatDefence == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 47, "" + CombatTaskList.DEFENCE_WHAT_DEFENCE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 147, "" + CombatTaskList.DEFENCE_WHAT_DEFENCE.description());//Description
        }
        if (player.defencewhatDefence >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 47, "<col=0fd60f>" + CombatTaskList.DEFENCE_WHAT_DEFENCE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 147, "" + CombatTaskList.DEFENCE_WHAT_DEFENCE.description());//Description
        }
        if (player.bigblackandFiery == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 49, "" + CombatTaskList.BIG_BLACK_AND_FIERY.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 148, "" + CombatTaskList.BIG_BLACK_AND_FIERY.description());//Description
        }
        if (player.bigblackandFiery >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 49, "<col=0fd60f>" + CombatTaskList.BIG_BLACK_AND_FIERY.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 148, "" + CombatTaskList.BIG_BLACK_AND_FIERY.description());//Description
        }
        if (player.thedemonicpunchingBag == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 51, "" + CombatTaskList.THE_DEMONIC_PUNCHING_BAG.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 149, "" + CombatTaskList.THE_DEMONIC_PUNCHING_BAG.description());//Description
        }
        if (player.thedemonicpunchingBag >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 51, "<col=0fd60f>" + CombatTaskList.THE_DEMONIC_PUNCHING_BAG.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 149, "" + CombatTaskList.THE_DEMONIC_PUNCHING_BAG.description());//Description
        }
        if (player.bryophytaNovice == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 53, ""+CombatTaskList.BRYOPHYTA_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 150, ""+CombatTaskList.BRYOPHYTA_NOVICE.description());//Description
        }
        if (player.bryophytaNovice >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 53, "<col=0fd60f>"+CombatTaskList.BRYOPHYTA_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 150, ""+CombatTaskList.BRYOPHYTA_NOVICE.description());//Description
        }
        if (player.protectionfromMoss == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 55, "" + CombatTaskList.PROTECTION_FROM_MOSS.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 151, "" + CombatTaskList.PROTECTION_FROM_MOSS.description());//Description
        }
        if (player.protectionfromMoss >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 55, "<col=0fd60f>" + CombatTaskList.PROTECTION_FROM_MOSS.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 151, "" + CombatTaskList.PROTECTION_FROM_MOSS.description());//Description
        }
        if (player.preparationisKey == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 180, "" + CombatTaskList.PREPARATION_IS_KEY.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 200, "" + CombatTaskList.PREPARATION_IS_KEY.description());//Description
        }
        if (player.preparationisKey >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 180, "<col=0fd60f>" + CombatTaskList.PREPARATION_IS_KEY.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 200, "" + CombatTaskList.PREPARATION_IS_KEY.description());//Description
        }
        if (player.aslowDeath == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 181, "" + CombatTaskList.A_SLOW_DEATH.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 201, "" + CombatTaskList.A_SLOW_DEATH.description());//Description
        }
        if (player.aslowDeath >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 181, "<col=0fd60f>" + CombatTaskList.A_SLOW_DEATH.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 201, "" + CombatTaskList.A_SLOW_DEATH.description());//Description
        }
        if (player.derangedarchaeologistNovice == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 182, "" + CombatTaskList.DERANGED_ARCHAEOLOGIST_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 202, "" + CombatTaskList.DERANGED_ARCHAEOLOGIST_NOVICE.description());//Description
        }
        if (player.derangedarchaeologistNovice > 0 && player.derangedarchaeologistNovice < 10) {
            player.getPacketSender().sendString(EASY_INTERFACE, 182, "<col=ffff00>" + CombatTaskList.DERANGED_ARCHAEOLOGIST_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 202, "" + CombatTaskList.DERANGED_ARCHAEOLOGIST_NOVICE.description());//Description
        }
        if (player.derangedarchaeologistNovice >= 10) {
            player.getPacketSender().sendString(EASY_INTERFACE, 182, "<col=0fd60f>" + CombatTaskList.DERANGED_ARCHAEOLOGIST_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 202, "" + CombatTaskList.DERANGED_ARCHAEOLOGIST_NOVICE.description());//Description
        }
        if (player.thewalkingVolcano == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 183, "" + CombatTaskList.THE_WALKING_VOLCANO.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 203, "" + CombatTaskList.THE_WALKING_VOLCANO.description());//Description
        }
        if (player.thewalkingVolcano >= 10) {
            player.getPacketSender().sendString(EASY_INTERFACE, 183, "<col=0fd60f>" + CombatTaskList.THE_WALKING_VOLCANO.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 203, "" + CombatTaskList.THE_WALKING_VOLCANO.description());//Description
        }
        if (player.intothedenofGiants == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 184, "" + CombatTaskList.INTO_THE_DEN_OF_GIANTS.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 204, "" + CombatTaskList.INTO_THE_DEN_OF_GIANTS.description());//Description
        }
        if (player.intothedenofGiants > 0 && player.intothedenofGiants < 3) {
            player.getPacketSender().sendString(EASY_INTERFACE, 184, "<col=ffff00>" + CombatTaskList.INTO_THE_DEN_OF_GIANTS.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 204, "" + CombatTaskList.INTO_THE_DEN_OF_GIANTS.description());//Description
        }
        if (player.intothedenofGiants >= 10) {
            player.getPacketSender().sendString(EASY_INTERFACE, 184, "<col=0fd60f>" + CombatTaskList.INTO_THE_DEN_OF_GIANTS.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 204, "" + CombatTaskList.INTO_THE_DEN_OF_GIANTS.description());//Description
        }
        if (player.agreaterFoe == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 185, "" + CombatTaskList.A_GREATER_FOE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 205, "" + CombatTaskList.A_GREATER_FOE.description());//Description
        }
        if (player.agreaterFoe >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 185, "<col=0fd60f>" + CombatTaskList.A_GREATER_FOE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 205, "" + CombatTaskList.A_GREATER_FOE.description());//Description
        }
        if (player.notsogreatafterAll == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 186, "" + CombatTaskList.NOT_SO_GREAT_AFTER_ALL.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 206, "" + CombatTaskList.NOT_SO_GREAT_AFTER_ALL.description());//Description
        }
        if (player.notsogreatafterAll >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 186, "<col=0fd60f>" + CombatTaskList.NOT_SO_GREAT_AFTER_ALL.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 206, "" + CombatTaskList.NOT_SO_GREAT_AFTER_ALL.description());//Description
        }
        if (player.ademonsbestFriend == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 187, "" + CombatTaskList.A_DEMONS_BEST_FRIEND.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 207, "" + CombatTaskList.A_DEMONS_BEST_FRIEND.description());//Description
        }
        if (player.ademonsbestFriend >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 187, "<col=0fd60f>" + CombatTaskList.A_DEMONS_BEST_FRIEND.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 207, "" + CombatTaskList.A_DEMONS_BEST_FRIEND.description());//Description
        }
        if (player.oborNovice == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 188, "" + CombatTaskList.OBOR_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 208, "" + CombatTaskList.OBOR_NOVICE.description());//Description
        }
        if (player.oborNovice >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 188, "<col=0fd60f>" + CombatTaskList.OBOR_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 208, "" + CombatTaskList.OBOR_NOVICE.description());//Description
        }
        if (player.sleepingGiant == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 189, "" + CombatTaskList.SLEEPING_GIANT.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 209, "" + CombatTaskList.SLEEPING_GIANT.description());//Description
        }
        if (player.sleepingGiant >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 189, "" + CombatTaskList.SLEEPING_GIANT.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 209, "" + CombatTaskList.SLEEPING_GIANT.description());//Description
        }
        if (player.kingblackdragonNovice == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 190, "" + CombatTaskList.KING_BLACK_DRAGON_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 210, "" + CombatTaskList.KING_BLACK_DRAGON_NOVICE.description());//Description
        }
        if (player.kingblackdragonNovice > 0 && player.kingblackdragonNovice < 10) {
            player.getPacketSender().sendString(EASY_INTERFACE, 190, "<col=ffff00>" + CombatTaskList.KING_BLACK_DRAGON_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 210, "" + CombatTaskList.KING_BLACK_DRAGON_NOVICE.description());//Description
        }
        if (player.kingblackdragonNovice >= 10) {
            player.getPacketSender().sendString(EASY_INTERFACE, 190, "<col=0fd60f>" + CombatTaskList.KING_BLACK_DRAGON_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 210, "" + CombatTaskList.KING_BLACK_DRAGON_NOVICE.description());//Description
        }
        if (player.ascaleyEncounter == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 191, "" + CombatTaskList.A_SCALEY_ENCOUNTER.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 211, "" + CombatTaskList.A_SCALEY_ENCOUNTER.description());//Description
        }
        if (player.ascaleyEncounter >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 191, "<col=0fd60f>" + CombatTaskList.A_SCALEY_ENCOUNTER.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 211, "" + CombatTaskList.A_SCALEY_ENCOUNTER.description());//Description
        }
        if (player.shayzienProtector == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 192, "" + CombatTaskList.SHAYZIEN_PROTECTOR.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 212, "" + CombatTaskList.SHAYZIEN_PROTECTOR.description());//Description
        }
        if (player.shayzienProtector >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 192, "" + CombatTaskList.SHAYZIEN_PROTECTOR.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 212, "" + CombatTaskList.SHAYZIEN_PROTECTOR.description());//Description
        }
        if (player.giantmoleNovice == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 193, "" + CombatTaskList.GIANT_MOLE_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 213, "" + CombatTaskList.GIANT_MOLE_NOVICE.description());//Description
        }
        if (player.giantmoleNovice > 0 && player.giantmoleNovice < 10) {
            player.getPacketSender().sendString(EASY_INTERFACE, 193, "<col=ffff00>" + CombatTaskList.GIANT_MOLE_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 213, "" + CombatTaskList.GIANT_MOLE_NOVICE.description());//Description
        }
        if (player.giantmoleNovice >= 10) {
            player.getPacketSender().sendString(EASY_INTERFACE, 193, "<col=0fd60f>" + CombatTaskList.GIANT_MOLE_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 213, "" + CombatTaskList.GIANT_MOLE_NOVICE.description());//Description
        }
        if (player.sarachnisNovice == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 194, "" + CombatTaskList.SARACHNIS_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 214, "" + CombatTaskList.SARACHNIS_NOVICE.description());//Description
        }
        if (player.sarachnisNovice > 0 && player.sarachnisNovice < 10) {
            player.getPacketSender().sendString(EASY_INTERFACE, 194, "<col=ffff00>" + CombatTaskList.SARACHNIS_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 214, "" + CombatTaskList.SARACHNIS_NOVICE.description());//Description
        }
        if (player.sarachnisNovice >= 10) {
            player.getPacketSender().sendString(EASY_INTERFACE, 194, "<col=0fd60f>" + CombatTaskList.SARACHNIS_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 214, "" + CombatTaskList.SARACHNIS_NOVICE.description());//Description
        }
        if (player.wintertodtNovice == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 195, "" + CombatTaskList.WINTERTODT_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 215, "" + CombatTaskList.WINTERTODT_NOVICE.description());//Description
        }
        if (player.wintertodtNovice > 0 && player.wintertodtNovice < 10) {
            player.getPacketSender().sendString(EASY_INTERFACE, 195, "<col=ffff00>" + CombatTaskList.WINTERTODT_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 215, "" + CombatTaskList.WINTERTODT_NOVICE.description());//Description
        }
        if (player.wintertodtNovice >= 10) {
            player.getPacketSender().sendString(EASY_INTERFACE, 195, "<col=0fd60f>" + CombatTaskList.WINTERTODT_NOVICE.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 215, "" + CombatTaskList.WINTERTODT_NOVICE.description());//Description
        }
        if (player.mummy == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 196, "" + CombatTaskList.MUMMY.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 216, "" + CombatTaskList.MUMMY.description());//Description
        }
        if (player.mummy >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 196, "<col=0fd60f>" + CombatTaskList.MUMMY.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 216, "" + CombatTaskList.MUMMY.description());//Description
        }
        if (player.handyMan == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 197, "" + CombatTaskList.HANDYMAN.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 217, "" + CombatTaskList.HANDYMAN.description());//Description
        }
        if (player.handyMan >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 197, "<col=0fd60f>" + CombatTaskList.HANDYMAN.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 217, "" + CombatTaskList.HANDYMAN.description());//Description
        }
        if (player.cosy == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 198, "" + CombatTaskList.COSY.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 218, "" + CombatTaskList.COSY.description());//Description
        }
        if (player.cosy >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 198, "<col=0fd60f>" + CombatTaskList.COSY.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 218, "" + CombatTaskList.COSY.description());//Description
        }
        if (player.aslitheryEncounter == 0) {
            player.getPacketSender().sendString(EASY_INTERFACE, 271, "" + CombatTaskList.A_SLITHERY_ENCOUNTER.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 272, "" + CombatTaskList.A_SLITHERY_ENCOUNTER.description());//Description
        }
        if (player.aslitheryEncounter >= 1) {
            player.getPacketSender().sendString(EASY_INTERFACE, 271, "<col=0fd60f>" + CombatTaskList.A_SLITHERY_ENCOUNTER.title());//Title
            player.getPacketSender().sendString(EASY_INTERFACE, 272, "" + CombatTaskList.A_SLITHERY_ENCOUNTER.description());//Description
        }
    }
    public static void sendMediumList(Player player) {
        if (player.prayforSuccess == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 227, "" + CombatTaskList.PRAY_FOR_SUCCESS.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 145, ""+CombatTaskList.PRAY_FOR_SUCCESS.description());//Description
        }
        if (player.prayforSuccess >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 227, "<col=0fd60f>" + CombatTaskList.PRAY_FOR_SUCCESS.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 145, ""+CombatTaskList.PRAY_FOR_SUCCESS.description());//Description
        }
        if (player.barrowsChampion == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 45, "" + CombatTaskList.BARROWS_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 146, "" + CombatTaskList.BARROWS_CHAMPION.description());//Description
        }
        if (player.barrowsChampion > 0 && player.barrowsChampion < 25) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 45, "<col=ffff00>" + CombatTaskList.BARROWS_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 146, "" + CombatTaskList.BARROWS_CHAMPION.description());//Description
        }
        if (player.barrowsChampion >= 25) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 45, "<col=0fd60f>" + CombatTaskList.BARROWS_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 146, "" + CombatTaskList.BARROWS_CHAMPION.description());//Description
        }
        if (player.brutalbigblackandFiery == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 47, "" + CombatTaskList.BRUTAL_BIG_BLACK_AND_FIERY.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 147, "" + CombatTaskList.BRUTAL_BIG_BLACK_AND_FIERY.description());//Description
        }
        if (player.brutalbigblackandFiery >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 47, "<col=0fd60f>" + CombatTaskList.BRUTAL_BIG_BLACK_AND_FIERY.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 147, "" + CombatTaskList.BRUTAL_BIG_BLACK_AND_FIERY.description());//Description
        }
        if (player.bryophytaChampion == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 49, "" + CombatTaskList.BRYOPHYTA_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 148, "" + CombatTaskList.BRYOPHYTA_CHAMPION.description());//Description
        }
        if (player.bryophytaChampion > 0 && player.bryophytaChampion < 5) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 49, "<col=ffff00>" + CombatTaskList.BRYOPHYTA_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 148, "" + CombatTaskList.BRYOPHYTA_CHAMPION.description());//Description
        }
        if (player.bryophytaChampion >= 5) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 49, "<col=0fd60f>" + CombatTaskList.BRYOPHYTA_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 148, "" + CombatTaskList.BRYOPHYTA_CHAMPION.description());//Description
        }
        if (player.sorrywhatwasThat == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 51, "" + CombatTaskList.SORRY_WHAT_WAS_THAT.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 149, "" + CombatTaskList.SORRY_WHAT_WAS_THAT.description());//Description
        }
        if (player.sorrywhatwasThat >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 51, "<col=0fd60f>" + CombatTaskList.SORRY_WHAT_WAS_THAT.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 149, "" + CombatTaskList.SORRY_WHAT_WAS_THAT.description());//Description
        }
        if (player.chaosfanaticChampion == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 53, ""+CombatTaskList.CHAOS_FANATIC_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 150, ""+CombatTaskList.CHAOS_FANATIC_CHAMPION.description());//Description
        }
        if (player.chaosfanaticChampion > 0 && player.chaosfanaticChampion < 10) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 53, "<col=ffff00>" + CombatTaskList.CHAOS_FANATIC_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 148, "" + CombatTaskList.CHAOS_FANATIC_CHAMPION.description());//Description
        }
        if (player.chaosfanaticChampion >= 10) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 53, "<col=0fd60f>"+CombatTaskList.CHAOS_FANATIC_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 150, ""+CombatTaskList.CHAOS_FANATIC_CHAMPION.description());//Description
        }
        if (player.idrathernotLearn == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 55, "" + CombatTaskList.ID_RATHER_NOT_LEARN.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 151, "" + CombatTaskList.ID_RATHER_NOT_LEARN.description());//Description
        }
        if (player.idrathernotLearn >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 55, "<col=0fd60f>" + CombatTaskList.ID_RATHER_NOT_LEARN.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 151, "" + CombatTaskList.ID_RATHER_NOT_LEARN.description());//Description
        }
        if (player.mageoftheRuins == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 229, "" + CombatTaskList.MAGE_OF_THE_RUINS.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 200, "" + CombatTaskList.MAGE_OF_THE_RUINS.description());//Description
        }
        if (player.mageoftheRuins >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 229, "<col=0fd60f>" + CombatTaskList.MAGE_OF_THE_RUINS.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 200, "" + CombatTaskList.MAGE_OF_THE_RUINS.description());//Description
        }
        if (player.crazyarchaeologistChampion == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 230, "" + CombatTaskList.CRAZY_ARCHAEOLOGIST_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 201, "" + CombatTaskList.CRAZY_ARCHAEOLOGIST_CHAMPION.description());//Description
        }
        if (player.crazyarchaeologistChampion > 0 && player.crazyarchaeologistChampion < 10) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 230, "<col=ffff00>" + CombatTaskList.CRAZY_ARCHAEOLOGIST_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 148, "" + CombatTaskList.CRAZY_ARCHAEOLOGIST_CHAMPION.description());//Description
        }
        if (player.crazyarchaeologistChampion >= 10) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 230, "<col=0fd60f>" + CombatTaskList.CRAZY_ARCHAEOLOGIST_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 201, "" + CombatTaskList.CRAZY_ARCHAEOLOGIST_CHAMPION.description());//Description
        }
        if (player.dagannothprimeChampion == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 231, "" + CombatTaskList.DAGANNOTH_PRIME_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 202, "" + CombatTaskList.DAGANNOTH_PRIME_CHAMPION.description());//Description
        }
        if (player.dagannothprimeChampion > 0 && player.dagannothprimeChampion < 10) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 231, "<col=ffff00>" + CombatTaskList.DAGANNOTH_PRIME_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 202, "" + CombatTaskList.DAGANNOTH_PRIME_CHAMPION.description());//Description
        }
        if (player.dagannothprimeChampion >= 10) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 231, "<col=0fd60f>" + CombatTaskList.DAGANNOTH_PRIME_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 202, "" + CombatTaskList.DAGANNOTH_PRIME_CHAMPION.description());//Description
        }
        if (player.afrozenKing == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 232, "" + CombatTaskList.A_FROZEN_KING.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 203, "" + CombatTaskList.A_FROZEN_KING.description());//Description
        }
        if (player.afrozenKing >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 232, "<col=0fd60f>" + CombatTaskList.A_FROZEN_KING.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 203, "" + CombatTaskList.A_FROZEN_KING.description());//Description
        }
        if (player.dagannothrexChampion == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 233, "" + CombatTaskList.DAGANNOTH_REX_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 204, "" + CombatTaskList.DAGANNOTH_REX_CHAMPION.description());//Description
        }
        if (player.dagannothrexChampion > 0 && player.dagannothrexChampion < 10) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 233, "<col=ffff00>" + CombatTaskList.DAGANNOTH_REX_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 204, "" + CombatTaskList.DAGANNOTH_REX_CHAMPION.description());//Description
        }
        if (player.dagannothrexChampion >= 10) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 233, "<col=0fd60f>" + CombatTaskList.DAGANNOTH_REX_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 204, "" + CombatTaskList.DAGANNOTH_REX_CHAMPION.description());//Description
        }
        if (player.dagannothsupremeChampion == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 234, "" + CombatTaskList.DAGANNOTH_SUPREME_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 205, "" + CombatTaskList.DAGANNOTH_SUPREME_CHAMPION.description());//Description
        }
        if (player.dagannothsupremeChampion > 0 && player.dagannothsupremeChampion < 10) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 234, "<col=ffff00>" + CombatTaskList.DAGANNOTH_SUPREME_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 204, "" + CombatTaskList.DAGANNOTH_SUPREME_CHAMPION.description());//Description
        }
        if (player.dagannothsupremeChampion >= 10) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 234, "<col=0fd60f>" + CombatTaskList.DAGANNOTH_SUPREME_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 205, "" + CombatTaskList.DAGANNOTH_SUPREME_CHAMPION.description());//Description
        }
        if (player.idratherbeIlliterate == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 235, "" + CombatTaskList.ID_RATHER_BE_ILLITERATE.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 206, "" + CombatTaskList.ID_RATHER_BE_ILLITERATE.description());//Description
        }
        if (player.idratherbeIlliterate >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 235, "<col=0fd60f>" + CombatTaskList.ID_RATHER_BE_ILLITERATE.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 206, "" + CombatTaskList.ID_RATHER_BE_ILLITERATE.description());//Description
        }
        if (player.mageoftheSwamp == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 236, "" + CombatTaskList.MAGE_OF_THE_SWAMP.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 207, "" + CombatTaskList.MAGE_OF_THE_SWAMP.description());//Description
        }
        if (player.mageoftheSwamp >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 236, "<col=0fd60f>" + CombatTaskList.MAGE_OF_THE_SWAMP.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 207, "" + CombatTaskList.MAGE_OF_THE_SWAMP.description());//Description
        }
        if (player.derangedarchaeologistChampion == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 237, "" + CombatTaskList.DERANGED_ARCHAEOLOGIST_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 208, "" + CombatTaskList.DERANGED_ARCHAEOLOGIST_CHAMPION.description());//Description
        }
        if (player.derangedarchaeologistChampion > 0 && player.derangedarchaeologistChampion < 25) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 237, "<col=ffff00>" + CombatTaskList.DERANGED_ARCHAEOLOGIST_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 204, "" + CombatTaskList.DERANGED_ARCHAEOLOGIST_CHAMPION.description());//Description
        }
        if (player.derangedarchaeologistChampion >= 25) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 237, "<col=0fd60f>" + CombatTaskList.DERANGED_ARCHAEOLOGIST_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 208, "" + CombatTaskList.DERANGED_ARCHAEOLOGIST_CHAMPION.description());//Description
        }
        if (player.asmashingTime == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 238, "" + CombatTaskList.A_SMASHING_TIME.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 209, "" + CombatTaskList.A_SMASHING_TIME.description());//Description
        }
        if (player.asmashingTime >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 238, "" + CombatTaskList.A_SMASHING_TIME.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 209, "" + CombatTaskList.A_SMASHING_TIME.description());//Description
        }
        if (player.avoidingthoselittleArms == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 239, "" + CombatTaskList.AVOIDING_THOSE_LITTLE_ARMS.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 210, "" + CombatTaskList.AVOIDING_THOSE_LITTLE_ARMS.description());//Description
        }
        if (player.avoidingthoselittleArms >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 239, "<col=0fd60f>" + CombatTaskList.AVOIDING_THOSE_LITTLE_ARMS.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 210, "" + CombatTaskList.AVOIDING_THOSE_LITTLE_ARMS.description());//Description
        }
        if (player.giantmoleChampion == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 240, "" + CombatTaskList.GIANT_MOLE_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 211, "" + CombatTaskList.GIANT_MOLE_CHAMPION.description());//Description
        }
        if (player.giantmoleChampion > 0 && player.giantmoleChampion < 25) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 240, "<col=ffff00>" + CombatTaskList.GIANT_MOLE_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 213, "" + CombatTaskList.GIANT_MOLE_CHAMPION.description());//Description
        }
        if (player.giantmoleChampion >= 25) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 240, "<col=0fd60f>" + CombatTaskList.GIANT_MOLE_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 211, "" + CombatTaskList.GIANT_MOLE_CHAMPION.description());//Description
        }
        if (player.antifireProtection == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 241, "" + CombatTaskList.ANTIFIRE_PROTECTION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 212, "" + CombatTaskList.ANTIFIRE_PROTECTION.description());//Description
        }
        if (player.antifireProtection >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 241, "" + CombatTaskList.ANTIFIRE_PROTECTION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 212, "" + CombatTaskList.ANTIFIRE_PROTECTION.description());//Description
        }
        if (player.hidePenetration == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 242, "" + CombatTaskList.HIDE_PENETRATION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 213, "" + CombatTaskList.HIDE_PENETRATION.description());//Description
        }
        if (player.hidePenetration >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 242, "<col=0fd60f>" + CombatTaskList.HIDE_PENETRATION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 213, "" + CombatTaskList.HIDE_PENETRATION.description());//Description
        }
        if (player.clawClipper == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 243, "" + CombatTaskList.CLAW_CLIPPER.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 214, "" + CombatTaskList.CLAW_CLIPPER.description());//Description
        }
        if (player.clawClipper >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 243, "<col=0fd60f>" + CombatTaskList.CLAW_CLIPPER.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 214, "" + CombatTaskList.CLAW_CLIPPER.description());//Description
        }
        if (player.kingblackdragonChampion == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 244, "" + CombatTaskList.KING_BLACK_DRAGON_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 215, "" + CombatTaskList.KING_BLACK_DRAGON_CHAMPION.description());//Description
        }
        if (player.kingblackdragonChampion > 0 && player.kingblackdragonChampion < 25) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 244, "<col=ffff00>" + CombatTaskList.KING_BLACK_DRAGON_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 215, "" + CombatTaskList.KING_BLACK_DRAGON_CHAMPION.description());//Description
        }
        if (player.kingblackdragonChampion >= 25) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 244, "<col=0fd60f>" + CombatTaskList.KING_BLACK_DRAGON_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 215, "" + CombatTaskList.KING_BLACK_DRAGON_CHAMPION.description());//Description
        }
        if (player.masterofbroadWeaponry == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 245, "" + CombatTaskList.MASTER_OF_BROAD_WEAPONRY.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 216, "" + CombatTaskList.MASTER_OF_BROAD_WEAPONRY.description());//Description
        }
        if (player.masterofbroadWeaponry >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 245, "<col=0fd60f>" + CombatTaskList.MASTER_OF_BROAD_WEAPONRY.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 216, "" + CombatTaskList.MASTER_OF_BROAD_WEAPONRY.description());//Description
        }
        if (player.squashingtheGiant == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 246, "" + CombatTaskList.SQUASHING_THE_GIANT.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 217, "" + CombatTaskList.SQUASHING_THE_GIANT.description());//Description
        }
        if (player.squashingtheGiant >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 246, "<col=0fd60f>" + CombatTaskList.SQUASHING_THE_GIANT.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 217, "" + CombatTaskList.SQUASHING_THE_GIANT.description());//Description
        }
        if (player.oborChampion == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 247, "" + CombatTaskList.OBOR_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 218, "" + CombatTaskList.OBOR_CHAMPION.description());//Description
        }
        if (player.oborChampion > 0 && player.oborChampion < 5) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 247, "<col=ffff00>" + CombatTaskList.OBOR_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 218, "" + CombatTaskList.OBOR_CHAMPION.description());//Description
        }
        if (player.oborChampion >= 5) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 247, "<col=0fd60f>" + CombatTaskList.OBOR_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 218, "" + CombatTaskList.OBOR_CHAMPION.description());//Description
        }
        if (player.newspaperEnthusiast == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 248, "" + CombatTaskList.NEWSPAPER_ENTHUSIAST.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 316, "" + CombatTaskList.NEWSPAPER_ENTHUSIAST.description());//Description
        }
        if (player.newspaperEnthusiast >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 248, "<col=0fd60f>" + CombatTaskList.NEWSPAPER_ENTHUSIAST.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 316, "" + CombatTaskList.NEWSPAPER_ENTHUSIAST.description());//Description
        }
        if (player.sarachnisChampion == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 304, "" + CombatTaskList.SARACHNIS_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 293, "" + CombatTaskList.SARACHNIS_CHAMPION.description());//Description
        }
        if (player.sarachnisChampion > 0 && player.sarachnisChampion < 25) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 304, "<col=ffff00>" + CombatTaskList.SARACHNIS_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 293, "" + CombatTaskList.SARACHNIS_CHAMPION.description());//Description
        }
        if (player.sarachnisChampion >= 25) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 304, "<col=0fd60f>" + CombatTaskList.SARACHNIS_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 293, "" + CombatTaskList.SARACHNIS_CHAMPION.description());//Description
        }
        if (player.afrozenfoefromthePast == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 305, "" + CombatTaskList.A_FROZEN_FOE_FROM_THE_PAST.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 294, "" + CombatTaskList.A_FROZEN_FOE_FROM_THE_PAST.description());//Description
        }
        if (player.afrozenfoefromthePast >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 305, "<col=0fd60f>" + CombatTaskList.A_FROZEN_FOE_FROM_THE_PAST.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 294, "" + CombatTaskList.A_FROZEN_FOE_FROM_THE_PAST.description());//Description
        }
        if (player.demonicWeakening == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 306, "" + CombatTaskList.DEMONIC_WEAKENING.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 295, "" + CombatTaskList.DEMONIC_WEAKENING.description());//Description
        }
        if (player.demonicWeakening >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 306, "<col=0fd60f>" + CombatTaskList.DEMONIC_WEAKENING.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 295, "" + CombatTaskList.DEMONIC_WEAKENING.description());//Description
        }
        if (player.skotizoChampion == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 307, "" + CombatTaskList.SKOTIZO_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 296, "" + CombatTaskList.SKOTIZO_CHAMPION.description());//Description
        }
        if (player.skotizoChampion >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 307, "<col=0fd60f>" + CombatTaskList.SKOTIZO_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 296, "" + CombatTaskList.SKOTIZO_CHAMPION.description());//Description
        }
        if (player.leavingnooneBehind == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 308, "" + CombatTaskList.LEAVING_NO_ONE_BEHIND.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 297, "" + CombatTaskList.LEAVING_NO_ONE_BEHIND.description());//Description
        }
        if (player.leavingnooneBehind >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 308, "<col=0fd60f>" + CombatTaskList.LEAVING_NO_ONE_BEHIND.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 297, "" + CombatTaskList.LEAVING_NO_ONE_BEHIND.description());//Description
        }
        if (player.canwefixIt == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 309, "" + CombatTaskList.CAN_WE_FIX_IT.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 298, "" + CombatTaskList.CAN_WE_FIX_IT.description());//Description
        }
        if (player.canwefixIt >= 1) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 309, "<col=0fd60f>" + CombatTaskList.CAN_WE_FIX_IT.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 298, "" + CombatTaskList.CAN_WE_FIX_IT.description());//Description
        }
        if (player.wintertodtChampion == 0) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 310, "" + CombatTaskList.WINTERTODT_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 299, "" + CombatTaskList.WINTERTODT_CHAMPION.description());//Description
        }
        if (player.wintertodtChampion > 0 && player.wintertodtChampion < 10) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 310, "<col=ffff00>" + CombatTaskList.WINTERTODT_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 299, "" + CombatTaskList.WINTERTODT_CHAMPION.description());//Description
        }
        if (player.wintertodtChampion >= 10) {
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 310, "<col=0fd60f>" + CombatTaskList.WINTERTODT_CHAMPION.title());//Title
            player.getPacketSender().sendString(MEDIUM_INTERFACE, 299, "" + CombatTaskList.WINTERTODT_CHAMPION.description());//Description
        }
    }
    public static void sendHardList(Player player) {
        if (player.dontstopMoving == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 227, "" + CombatTaskList.DONT_STOP_MOVING.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 145, "" + CombatTaskList.DONT_STOP_MOVING.description());//Description
        }
        if (player.dontstopMoving >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 227, "<col=0fd60f>" + CombatTaskList.DONT_STOP_MOVING.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 145, "" + CombatTaskList.DONT_STOP_MOVING.description());//Description
        }
        if (player.dontwhipMe == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 45, "" + CombatTaskList.DONT_WHIP_ME.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 146, "" + CombatTaskList.DONT_WHIP_ME.description());//Description
        }
        if (player.dontwhipMe >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 45, "<col=0fd60f>" + CombatTaskList.DONT_WHIP_ME.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 146, "" + CombatTaskList.DONT_WHIP_ME.description());//Description
        }
        if (player.theygrowuptooFast == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 47, "" + CombatTaskList.THEY_GROW_UP_TOO_FAST.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 147, "" + CombatTaskList.THEY_GROW_UP_TOO_FAST.description());//Description
        }
        if (player.theygrowuptooFast >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 47, "<col=0fd60f>" + CombatTaskList.THEY_GROW_UP_TOO_FAST.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 147, "" + CombatTaskList.THEY_GROW_UP_TOO_FAST.description());//Description
        }
        if (player.abyssalAdept == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 49, "" + CombatTaskList.ABYSSAL_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 148, "" + CombatTaskList.ABYSSAL_ADEPT.description());//Description
        }
        if (player.abyssalAdept > 0 && player.abyssalAdept < 20) {
            player.getPacketSender().sendString(HARD_INTERFACE, 49, "<col=ffff00>" + CombatTaskList.ABYSSAL_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 148, "" + CombatTaskList.ABYSSAL_ADEPT.description());//Description
        }
        if (player.abyssalAdept >= 20) {
            player.getPacketSender().sendString(HARD_INTERFACE, 49, "<col=0fd60f>" + CombatTaskList.ABYSSAL_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 148, "" + CombatTaskList.ABYSSAL_ADEPT.description());//Description
        }
        if (player.faithlesscryptRun == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 51, "" + CombatTaskList.FAITHLESS_CRYPT_RUN.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 149, "" + CombatTaskList.FAITHLESS_CRYPT_RUN.description());//Description
        }
        if (player.faithlesscryptRun >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 51, "<col=0fd60f>" + CombatTaskList.FAITHLESS_CRYPT_RUN.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 149, "" + CombatTaskList.FAITHLESS_CRYPT_RUN.description());//Description
        }
        if (player.justlikeThat == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 53, "" + CombatTaskList.JUST_LIKE_THAT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 150, "" + CombatTaskList.JUST_LIKE_THAT.description());//Description
        }
        if (player.justlikeThat >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 53, "<col=0fd60f>" + CombatTaskList.JUST_LIKE_THAT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 150, "" + CombatTaskList.JUST_LIKE_THAT.description());//Description
        }
        if (player.callistoAdept == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 55, "" + CombatTaskList.CALLISTO_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 151, "" + CombatTaskList.CALLISTO_ADEPT.description());//Description
        }
        if (player.callistoAdept > 0 && player.callistoAdept < 10) {
            player.getPacketSender().sendString(HARD_INTERFACE, 55, "<col=ffff00>" + CombatTaskList.CALLISTO_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 151, "" + CombatTaskList.CALLISTO_ADEPT.description());//Description
        }
        if (player.callistoAdept >= 10) {
            player.getPacketSender().sendString(HARD_INTERFACE, 55, "<col=0fd60f>" + CombatTaskList.CALLISTO_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 151, "" + CombatTaskList.CALLISTO_ADEPT.description());//Description
        }
        if (player.theFlincher == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 180, "" + CombatTaskList.THE_FLINCHER.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 200, "" + CombatTaskList.THE_FLINCHER.description());//Description
        }
        if (player.theFlincher >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 180, "<col=0fd60f>" + CombatTaskList.THE_FLINCHER.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 200, "" + CombatTaskList.THE_FLINCHER.description());//Description
        }
        if (player.hoarder == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 181, "" + CombatTaskList.HOARDER.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 201, "" + CombatTaskList.HOARDER.description());//Description
        }
        if (player.hoarder >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 181, "<col=0fd60f>" + CombatTaskList.HOARDER.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 201, "" + CombatTaskList.HOARDER.description());//Description
        }
        if (player.chaoselementalAdept == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 182, "" + CombatTaskList.CHAOS_ELEMENTAL_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 202, "" + CombatTaskList.CHAOS_ELEMENTAL_ADEPT.description());//Description
        }
        if (player.chaoselementalAdept > 0 && player.chaoselementalAdept < 10) {
            player.getPacketSender().sendString(HARD_INTERFACE, 182, "<col=ffff00>" + CombatTaskList.CHAOS_ELEMENTAL_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 202, "" + CombatTaskList.CHAOS_ELEMENTAL_ADEPT.description());//Description
        }
        if (player.chaoselementalAdept >= 10) {
            player.getPacketSender().sendString(HARD_INTERFACE, 182, "<col=0fd60f>" + CombatTaskList.CHAOS_ELEMENTAL_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 202, "" + CombatTaskList.CHAOS_ELEMENTAL_ADEPT.description());//Description
        }
        if (player.prayingtotheGods == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 183, "" + CombatTaskList.PRAYING_TO_THE_GODS.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 203, "" + CombatTaskList.PRAYING_TO_THE_GODS.description());//Description
        }
        if (player.prayingtotheGods > 0 && player.prayingtotheGods < 10) {
            player.getPacketSender().sendString(HARD_INTERFACE, 183, "<col=ffff00>" + CombatTaskList.PRAYING_TO_THE_GODS.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 203, "" + CombatTaskList.PRAYING_TO_THE_GODS.description());//Description
        }
        if (player.prayingtotheGods >= 10) {
            player.getPacketSender().sendString(HARD_INTERFACE, 183, "<col=0fd60f>" + CombatTaskList.PRAYING_TO_THE_GODS.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 203, "" + CombatTaskList.PRAYING_TO_THE_GODS.description());//Description
        }
        if (player.chaosfanaticAdept == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 184, "" + CombatTaskList.CHAOS_FANATIC_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 204, "" + CombatTaskList.CHAOS_FANATIC_ADEPT.description());//Description
        }
        if (player.chaosfanaticAdept > 0 && player.chaosfanaticAdept < 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 184, "<col=ffff00>" + CombatTaskList.CHAOS_FANATIC_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 204, "" + CombatTaskList.CHAOS_FANATIC_ADEPT.description());//Description
        }
        if (player.chaosfanaticAdept >= 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 184, "<col=0fd60f>" + CombatTaskList.CHAOS_FANATIC_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 204, "" + CombatTaskList.CHAOS_FANATIC_ADEPT.description());//Description
        }
        if (player.commanderShowdown == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 185, "" + CombatTaskList.COMMANDER_SHOWDOWN.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 205, "" + CombatTaskList.COMMANDER_SHOWDOWN.description());//Description
        }
        if (player.commanderShowdown >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 185, "<col=0fd60f>" + CombatTaskList.COMMANDER_SHOWDOWN.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 205, "" + CombatTaskList.COMMANDER_SHOWDOWN.description());//Description
        }
        if (player.commanderzilyanaAdept == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 186, "" + CombatTaskList.COMMAND_ZILYANA_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 206, "" + CombatTaskList.COMMAND_ZILYANA_ADEPT.description());//Description
        }
        if (player.commanderzilyanaAdept > 0 && player.commanderzilyanaAdept < 50) {
            player.getPacketSender().sendString(HARD_INTERFACE, 186, "<col=ffff00>" + CombatTaskList.COMMAND_ZILYANA_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 206, "" + CombatTaskList.COMMAND_ZILYANA_ADEPT.description());//Description
        }
        if (player.commanderzilyanaAdept >= 50) {
            player.getPacketSender().sendString(HARD_INTERFACE, 186, "<col=0fd60f>" + CombatTaskList.COMMAND_ZILYANA_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 206, "" + CombatTaskList.COMMAND_ZILYANA_ADEPT.description());//Description
        }
        if (player.crazyarchaeologistAdept == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 187, "" + CombatTaskList.CRAZY_ARCHAEOLOGIST_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 207, "" + CombatTaskList.CRAZY_ARCHAEOLOGIST_ADEPT.description());//Description
        }
        if (player.crazyarchaeologistAdept > 0 && player.crazyarchaeologistAdept < 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 187, "<col=ffff00>" + CombatTaskList.CRAZY_ARCHAEOLOGIST_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 207, "" + CombatTaskList.CRAZY_ARCHAEOLOGIST_ADEPT.description());//Description
        }
        if (player.crazyarchaeologistAdept >= 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 187, "<col=0fd60f>" + CombatTaskList.CRAZY_ARCHAEOLOGIST_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 207, "" + CombatTaskList.CRAZY_ARCHAEOLOGIST_ADEPT.description());//Description
        }
        if (player.dagannothprimeAdept== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 188, "" + CombatTaskList.DAGANNOTH_PRIME_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 208, "" + CombatTaskList.DAGANNOTH_PRIME_ADEPT.description());//Description
        }
        if (player.dagannothprimeAdept > 0 && player.dagannothprimeAdept < 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 188, "<col=ffff00>" + CombatTaskList.DAGANNOTH_PRIME_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 208, "" + CombatTaskList.DAGANNOTH_PRIME_ADEPT.description());//Description
        }
        if (player.dagannothprimeAdept >= 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 188, "<col=0fd60f>" + CombatTaskList.DAGANNOTH_PRIME_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 208, "" + CombatTaskList.DAGANNOTH_PRIME_ADEPT.description());//Description
        }
        if (player.dagannothrexAdept== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 189, "" + CombatTaskList.DAGANNOTH_REX_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 209, "" + CombatTaskList.DAGANNOTH_REX_ADEPT.description());//Description
        }
        if (player.dagannothrexAdept > 0 && player.dagannothrexAdept < 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 189, "<col=ffff00>" + CombatTaskList.DAGANNOTH_REX_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 209, "" + CombatTaskList.DAGANNOTH_REX_ADEPT.description());//Description
        }
        if (player.dagannothrexAdept >= 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 189, "<col=0fd60f>" + CombatTaskList.DAGANNOTH_REX_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 209, "" + CombatTaskList.DAGANNOTH_REX_ADEPT.description());//Description
        }
        if (player.dagannothsupremeAdept== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 190, "" + CombatTaskList.DAGANNOTH_SUPREME_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 210, "" + CombatTaskList.DAGANNOTH_SUPREME_ADEPT.description());//Description
        }
        if (player.dagannothsupremeAdept > 0 && player.dagannothsupremeAdept < 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 190, "<col=ffff00>" + CombatTaskList.DAGANNOTH_SUPREME_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 210, "" + CombatTaskList.DAGANNOTH_SUPREME_ADEPT.description());//Description
        }
        if (player.dagannothsupremeAdept >= 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 190, "<col=0fd60f>" + CombatTaskList.DAGANNOTH_SUPREME_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 210, "" + CombatTaskList.DAGANNOTH_SUPREME_ADEPT.description());//Description
        }
        if (player.generalShowdown == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 191, "" + CombatTaskList.GENERAL_SHOWDOWN.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 211, "" + CombatTaskList.GENERAL_SHOWDOWN.description());//Description
        }
        if (player.generalShowdown >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 191, "<col=0fd60f>" + CombatTaskList.GENERAL_SHOWDOWN.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 211, "" + CombatTaskList.GENERAL_SHOWDOWN.description());//Description
        }
        if (player.ourgFreezer == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 192, "" + CombatTaskList.OURG_FREEZER.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 212, "" + CombatTaskList.OURG_FREEZER.description());//Description
        }
        if (player.ourgFreezer >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 192, "<col=0fd60f>" + CombatTaskList.OURG_FREEZER.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 212, "" + CombatTaskList.OURG_FREEZER.description());//Description
        }
        if (player.generalgraardorAdept== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 193, "" + CombatTaskList.GENERAL_GRAARDOR_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 213, "" + CombatTaskList.GENERAL_GRAARDOR_ADEPT.description());//Description
        }
        if (player.generalgraardorAdept > 0 && player.generalgraardorAdept < 50) {
            player.getPacketSender().sendString(HARD_INTERFACE, 193, "<col=ffff00>" + CombatTaskList.GENERAL_GRAARDOR_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 213, "" + CombatTaskList.GENERAL_GRAARDOR_ADEPT.description());//Description
        }
        if (player.generalgraardorAdept >= 50) {
            player.getPacketSender().sendString(HARD_INTERFACE, 193, "<col=0fd60f>" + CombatTaskList.GENERAL_GRAARDOR_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 213, "" + CombatTaskList.GENERAL_GRAARDOR_ADEPT.description());//Description
        }
        if (player.whackaMole == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 194, "" + CombatTaskList.WHACK_A_MOLE.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 214, "" + CombatTaskList.WHACK_A_MOLE.description());//Description
        }
        if (player.whackaMole >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 194, "<col=0fd60f>" + CombatTaskList.WHACK_A_MOLE.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 214, "" + CombatTaskList.WHACK_A_MOLE.description());//Description
        }
        if (player.whyareyouRunning == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 195, "" + CombatTaskList.WHY_ARE_YOU_RUNNING.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 215, "" + CombatTaskList.WHY_ARE_YOU_RUNNING.description());//Description
        }
        if (player.whyareyouRunning >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 195, "<col=0fd60f>" + CombatTaskList.WHY_ARE_YOU_RUNNING.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 215, "" + CombatTaskList.WHY_ARE_YOU_RUNNING.description());//Description
        }
        if (player.demonbaneWeaponryII == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 196, "" + CombatTaskList.DEMONBANE_WEAPONRY_II.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 216, "" + CombatTaskList.DEMONBANE_WEAPONRY_II.description());//Description
        }
        if (player.demonbaneWeaponryII >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 196, "<col=0fd60f>" + CombatTaskList.DEMONBANE_WEAPONRY_II.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 216, "" + CombatTaskList.DEMONBANE_WEAPONRY_II.description());//Description
        }
        if (player.demonicShowdown == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 197, "" + CombatTaskList.DEMONIC_SHOWDOWN.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 217, "" + CombatTaskList.DEMONIC_SHOWDOWN.description());//Description
        }
        if (player.demonicShowdown >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 197, "<col=0fd60f>" + CombatTaskList.DEMONIC_SHOWDOWN.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 217, "" + CombatTaskList.DEMONIC_SHOWDOWN.description());//Description
        }
        if (player.yarrnoMore == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 198, "" + CombatTaskList.YARR_NO_MORE.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 218, "" + CombatTaskList.YARR_NO_MORE.description());//Description
        }
        if (player.yarrnoMore >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 198, "<col=0fd60f>" + CombatTaskList.YARR_NO_MORE.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 218, "" + CombatTaskList.YARR_NO_MORE.description());//Description
        }
        if (player.krilAdept== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 358, "" + CombatTaskList.KRIL_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 359, "" + CombatTaskList.KRIL_ADEPT.description());//Description
        }
        if (player.krilAdept > 0 && player.krilAdept < 50) {
            player.getPacketSender().sendString(HARD_INTERFACE, 358, "<col=ffff00>" + CombatTaskList.KRIL_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 359, "" + CombatTaskList.KRIL_ADEPT.description());//Description
        }
        if (player.krilAdept >= 50) {
            player.getPacketSender().sendString(HARD_INTERFACE, 358, "<col=0fd60f>" + CombatTaskList.KRIL_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 359, "" + CombatTaskList.KRIL_ADEPT.description());//Description
        }
        if (player.chitinPenetrator == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 304, "" + CombatTaskList.CHITIN_PENETRATOR.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 293, "" + CombatTaskList.CHITIN_PENETRATOR.description());//Description
        }
        if (player.chitinPenetrator >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 304, "<col=0fd60f>" + CombatTaskList.CHITIN_PENETRATOR.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 293, "" + CombatTaskList.CHITIN_PENETRATOR.description());//Description
        }
        if (player.kalphitequeenAdept== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 305, "" + CombatTaskList.KALPHITE_QUEEN_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 294, "" + CombatTaskList.KALPHITE_QUEEN_ADEPT.description());//Description
        }
        if (player.kalphitequeenAdept > 0 && player.kalphitequeenAdept < 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 305, "<col=ffff00>" + CombatTaskList.KALPHITE_QUEEN_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 294, "" + CombatTaskList.KALPHITE_QUEEN_ADEPT.description());//Description
        }
        if (player.kalphitequeenAdept >= 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 305, "<col=0fd60f>" + CombatTaskList.KALPHITE_QUEEN_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 294, "" + CombatTaskList.KALPHITE_QUEEN_ADEPT.description());//Description
        }
        if (player.whoisthekingNow== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 306, "" + CombatTaskList.WHO_IS_THE_KING_NOW.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 295, "" + CombatTaskList.WHO_IS_THE_KING_NOW.description());//Description
        }
        if (player.whoisthekingNow > 0 && player.whoisthekingNow < 10) {
            player.getPacketSender().sendString(HARD_INTERFACE, 306, "<col=ffff00>" + CombatTaskList.WHO_IS_THE_KING_NOW.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 295, "" + CombatTaskList.WHO_IS_THE_KING_NOW.description());//Description
        }
        if (player.whoisthekingNow >= 10) {
            player.getPacketSender().sendString(HARD_INTERFACE, 306, "<col=0fd60f>" + CombatTaskList.WHO_IS_THE_KING_NOW.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 295, "" + CombatTaskList.WHO_IS_THE_KING_NOW.description());//Description
        }
        if (player.krakanthurtMe== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 307, "" + CombatTaskList.KRAKANT_HURT_ME.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 296, "" + CombatTaskList.KRAKANT_HURT_ME.description());//Description
        }
        if (player.krakanthurtMe > 0 && player.krakanthurtMe < 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 307, "<col=ffff00>" + CombatTaskList.KRAKANT_HURT_ME.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 296, "" + CombatTaskList.KRAKANT_HURT_ME.description());//Description
        }
        if (player.krakanthurtMe >= 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 307, "<col=0fd60f>" + CombatTaskList.KRAKANT_HURT_ME.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 296, "" + CombatTaskList.KRAKANT_HURT_ME.description());//Description
        }
        if (player.unnecessaryOptimization == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 308, "" + CombatTaskList.UNNECESSARY_OPTIMIZATION.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 297, "" + CombatTaskList.UNNECESSARY_OPTIMIZATION.description());//Description
        }
        if (player.unnecessaryOptimization >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 308, "<col=0fd60f>" + CombatTaskList.UNNECESSARY_OPTIMIZATION.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 297, "" + CombatTaskList.UNNECESSARY_OPTIMIZATION.description());//Description
        }
        if (player.krakenAdept== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 309, "" + CombatTaskList.KRAKEN_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 298, "" + CombatTaskList.KRAKEN_ADEPT.description());//Description
        }
        if (player.krakenAdept > 0 && player.krakenAdept < 20) {
            player.getPacketSender().sendString(HARD_INTERFACE, 309, "<col=ffff00>" + CombatTaskList.KRAKEN_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 298, "" + CombatTaskList.KRAKEN_ADEPT.description());//Description
        }
        if (player.krakenAdept >= 20) {
            player.getPacketSender().sendString(HARD_INTERFACE, 309, "<col=0fd60f>" + CombatTaskList.KRAKEN_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 298, "" + CombatTaskList.KRAKEN_ADEPT.description());//Description
        }
        if (player.airborneShowdown == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 310, "" + CombatTaskList.AIRBORNE_SHOWDOWN.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 299, "" + CombatTaskList.AIRBORNE_SHOWDOWN.description());//Description
        }
        if (player.airborneShowdown >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 310, "<col=0fd60f>" + CombatTaskList.AIRBORNE_SHOWDOWN.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 299, "" + CombatTaskList.AIRBORNE_SHOWDOWN.description());//Description
        }
        if (player.krearraAdept== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 311, "" + CombatTaskList.KREARRA_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 300, "" + CombatTaskList.KREARRA_ADEPT.description());//Description
        }
        if (player.krearraAdept > 0 && player.krearraAdept < 50) {
            player.getPacketSender().sendString(HARD_INTERFACE, 311, "<col=ffff00>" + CombatTaskList.KREARRA_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 300, "" + CombatTaskList.KREARRA_ADEPT.description());//Description
        }
        if (player.krearraAdept >= 50) {
            player.getPacketSender().sendString(HARD_INTERFACE, 311, "<col=0fd60f>" + CombatTaskList.KREARRA_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 300, "" + CombatTaskList.KREARRA_ADEPT.description());//Description
        }
        if (player.insectRepellant == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 312, "" + CombatTaskList.INSPECT_REPELLANT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 301, "" + CombatTaskList.INSPECT_REPELLANT.description());//Description
        }
        if (player.insectRepellant >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 312, "<col=0fd60f>" + CombatTaskList.INSPECT_REPELLANT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 301, "" + CombatTaskList.INSPECT_REPELLANT.description());//Description
        }
        if (player.guardiansnoMore == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 313, "" + CombatTaskList.GUARDIANS_NO_MORE.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 360, "" + CombatTaskList.GUARDIANS_NO_MORE.description());//Description
        }
        if (player.guardiansnoMore >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 313, "<col=0fd60f>" + CombatTaskList.GUARDIANS_NO_MORE.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 360, "" + CombatTaskList.GUARDIANS_NO_MORE.description());//Description
        }
        if (player.icantreachThat == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 314, "" + CombatTaskList.I_CANT_REACH_THAT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 303, "" + CombatTaskList.I_CANT_REACH_THAT.description());//Description
        }
        if (player.icantreachThat >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 314, "<col=0fd60f>" + CombatTaskList.I_CANT_REACH_THAT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 303, "" + CombatTaskList.I_CANT_REACH_THAT.description());//Description
        }
        if (player.scorpiaAdept== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 350, "" + CombatTaskList.SCORPIA_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 343, "" + CombatTaskList.SCORPIA_ADEPT.description());//Description
        }
        if (player.scorpiaAdept > 0 && player.scorpiaAdept < 50) {
            player.getPacketSender().sendString(HARD_INTERFACE, 350, "<col=ffff00>" + CombatTaskList.SCORPIA_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 343, "" + CombatTaskList.SCORPIA_ADEPT.description());//Description
        }
        if (player.scorpiaAdept >= 50) {
            player.getPacketSender().sendString(HARD_INTERFACE, 350, "<col=0fd60f>" + CombatTaskList.SCORPIA_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 343, "" + CombatTaskList.SCORPIA_ADEPT.description());//Description
        }
        if (player.skotizoAdept== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 351, "" + CombatTaskList.SKOTIZO_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 344, "" + CombatTaskList.SKOTIZO_ADEPT.description());//Description
        }
        if (player.skotizoAdept > 0 && player.skotizoAdept < 5) {
            player.getPacketSender().sendString(HARD_INTERFACE, 351, "<col=ffff00>" + CombatTaskList.SKOTIZO_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 344, "" + CombatTaskList.SKOTIZO_ADEPT.description());//Description
        }
        if (player.skotizoAdept >= 5) {
            player.getPacketSender().sendString(HARD_INTERFACE, 351, "<col=0fd60f>" + CombatTaskList.SKOTIZO_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 344, "" + CombatTaskList.SKOTIZO_ADEPT.description());//Description
        }
        if (player.venenatisAdept== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 352, "" + CombatTaskList.VENENATIS_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 345, "" + CombatTaskList.VENENATIS_ADEPT.description());//Description
        }
        if (player.venenatisAdept > 0 && player.venenatisAdept < 10) {
            player.getPacketSender().sendString(HARD_INTERFACE, 352, "<col=ffff00>" + CombatTaskList.VENENATIS_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 345, "" + CombatTaskList.VENENATIS_ADEPT.description());//Description
        }
        if (player.venenatisAdept >= 10) {
            player.getPacketSender().sendString(HARD_INTERFACE, 352, "<col=0fd60f>" + CombatTaskList.VENENATIS_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 345, "" + CombatTaskList.VENENATIS_ADEPT.description());//Description
        }
        if (player.vetionAdept== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 353, "" + CombatTaskList.VETION_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 346, "" + CombatTaskList.VETION_ADEPT.description());//Description
        }
        if (player.vetionAdept > 0 && player.vetionAdept < 10) {
            player.getPacketSender().sendString(HARD_INTERFACE, 353, "<col=ffff00>" + CombatTaskList.VETION_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 346, "" + CombatTaskList.VETION_ADEPT.description());//Description
        }
        if (player.vetionAdept >= 10) {
            player.getPacketSender().sendString(HARD_INTERFACE, 353, "<col=0fd60f>" + CombatTaskList.VETION_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 346, "" + CombatTaskList.VETION_ADEPT.description());//Description
        }
        if (player.whyFletch == 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 354, "" + CombatTaskList.WHY_FLETCH.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 347, "" + CombatTaskList.WHY_FLETCH.description());//Description
        }
        if (player.whyFletch >= 1) {
            player.getPacketSender().sendString(HARD_INTERFACE, 354, "<col=0fd60f>" + CombatTaskList.WHY_FLETCH.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 347, "" + CombatTaskList.WHY_FLETCH.description());//Description
        }
        if (player.zulrahAdept== 0) {
            player.getPacketSender().sendString(HARD_INTERFACE, 355, "" + CombatTaskList.ZULRAH_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 348, "" + CombatTaskList.ZULRAH_ADEPT.description());//Description
        }
        if (player.zulrahAdept > 0 && player.zulrahAdept < 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 355, "<col=ffff00>" + CombatTaskList.ZULRAH_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 348, "" + CombatTaskList.ZULRAH_ADEPT.description());//Description
        }
        if (player.zulrahAdept >= 25) {
            player.getPacketSender().sendString(HARD_INTERFACE, 355, "<col=0fd60f>" + CombatTaskList.ZULRAH_ADEPT.title());//Title
            player.getPacketSender().sendString(HARD_INTERFACE, 348, "" + CombatTaskList.ZULRAH_ADEPT.description());//Description
        }
    }
    public static void sendEliteList(Player player) {
        if (player.perfectSire == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 227, "" + CombatTaskList.PERFECT_SIRE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 145, "" + CombatTaskList.PERFECT_SIRE.description());//Description
        }
        if (player.perfectSire >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 227, "<col=0fd60f>" + CombatTaskList.PERFECT_SIRE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 145, "" + CombatTaskList.PERFECT_SIRE.description());//Description
        }
        if (player.demonicRebound == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 45, "" + CombatTaskList.DEMONIC_REBOUND.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 146, "" + CombatTaskList.DEMONIC_REBOUND.description());//Description
        }
        if (player.demonicRebound >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 45, "<col=0fd60f>" + CombatTaskList.DEMONIC_REBOUND.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 146, "" + CombatTaskList.DEMONIC_REBOUND.description());//Description
        }
        if (player.respiratoryRunner == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 47, "" + CombatTaskList.RESPIRATORY_RUNNER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 147, "" + CombatTaskList.RESPIRATORY_RUNNER.description());//Description
        }
        if (player.respiratoryRunner >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 47, "<col=0fd60f>" + CombatTaskList.RESPIRATORY_RUNNER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 147, "" + CombatTaskList.RESPIRATORY_RUNNER.description());//Description
        }
        if (player.abyssalVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 49, "" + CombatTaskList.ABYSSAL_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 148, "" + CombatTaskList.ABYSSAL_VETERAN.description());//Description
        }
        if (player.abyssalVeteran > 0 && player.abyssalVeteran < 50) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 49, "<col=ffff00>" + CombatTaskList.ABYSSAL_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 148, "" + CombatTaskList.ABYSSAL_VETERAN.description());//Description
        }
        if (player.abyssalVeteran >= 50) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 49, "<col=0fd60f>" + CombatTaskList.ABYSSAL_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 148, "" + CombatTaskList.ABYSSAL_VETERAN.description());//Description
        }
        if (player.alchemicalVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 51, "" + CombatTaskList.ALCHEMICAL_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 149, "" + CombatTaskList.ALCHEMICAL_VETERAN.description());//Description
        }
        if (player.alchemicalVeteran > 0 && player.alchemicalVeteran < 75) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 51, "<col=ffff00>" + CombatTaskList.ALCHEMICAL_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 149, "" + CombatTaskList.ALCHEMICAL_VETERAN.description());//Description
        }
        if (player.alchemicalVeteran >= 75) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 51, "<col=0fd60f>" + CombatTaskList.ALCHEMICAL_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 149, "" + CombatTaskList.ALCHEMICAL_VETERAN.description());//Description
        }
        if (player.reflectingonthisEncounter == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 53, "" + CombatTaskList.REFLECTING_ON_THIS_ENCOUNTER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 150, "" + CombatTaskList.REFLECTING_ON_THIS_ENCOUNTER.description());//Description
        }
        if (player.reflectingonthisEncounter >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 53, "<col=0fd60f>" + CombatTaskList.REFLECTING_ON_THIS_ENCOUNTER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 150, "" + CombatTaskList.REFLECTING_ON_THIS_ENCOUNTER.description());//Description
        }
        if (player.callistoVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 55, "" + CombatTaskList.CALLISTO_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 151, "" + CombatTaskList.CALLISTO_VETERAN.description());//Description
        }
        if (player.callistoVeteran > 0 && player.callistoVeteran < 20) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 55, "<col=ffff00>" + CombatTaskList.CALLISTO_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 151, "" + CombatTaskList.CALLISTO_VETERAN.description());//Description
        }
        if (player.callistoVeteran >= 20) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 55, "<col=0fd60f>" + CombatTaskList.CALLISTO_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 151, "" + CombatTaskList.CALLISTO_VETERAN.description());//Description
        }
        if (player.antibiteMechanics == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 180, "" + CombatTaskList.ANTI_BITE_MECHANICS.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 200, "" + CombatTaskList.ANTI_BITE_MECHANICS.description());//Description
        }
        if (player.antibiteMechanics >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 180, "<col=0fd60f>" + CombatTaskList.ANTI_BITE_MECHANICS.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 200, "" + CombatTaskList.ANTI_BITE_MECHANICS.description());//Description
        }
        if (player.unrequiredAntifire == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 181, "" + CombatTaskList.UNREQUIRED_ANTIFIRE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 201, "" + CombatTaskList.UNREQUIRED_ANTIFIRE.description());//Description
        }
        if (player.unrequiredAntifire >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 181, "<col=0fd60f>" + CombatTaskList.UNREQUIRED_ANTIFIRE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 201, "" + CombatTaskList.UNREQUIRED_ANTIFIRE.description());//Description
        }
        if (player.ghostBuster == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 182, "" + CombatTaskList.GHOST_BUSTER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 202, "" + CombatTaskList.GHOST_BUSTER.description());//Description
        }
        if (player.ghostBuster >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 182, "<col=0fd60f>" + CombatTaskList.GHOST_BUSTER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 202, "" + CombatTaskList.GHOST_BUSTER.description());//Description
        }
        if (player.cerberusVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 183, "" + CombatTaskList.CERBERUS_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 203, "" + CombatTaskList.CERBERUS_VETERAN.description());//Description
        }
        if (player.cerberusVeteran > 0 && player.cerberusVeteran < 75) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 183, "<col=ffff00>" + CombatTaskList.CERBERUS_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 203, "" + CombatTaskList.CERBERUS_VETERAN.description());//Description
        }
        if (player.cerberusVeteran >= 75) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 183, "<col=0fd60f>" + CombatTaskList.CERBERUS_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 203, "" + CombatTaskList.CERBERUS_VETERAN.description());//Description
        }
        if (player.killitwithFire == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 184, "" + CombatTaskList.KILL_IT_WITH_FIRE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 204, "" + CombatTaskList.KILL_IT_WITH_FIRE.description());//Description
        }
        if (player.killitwithFire >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 184, "<col=0fd60f>" + CombatTaskList.KILL_IT_WITH_FIRE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 204, "" + CombatTaskList.KILL_IT_WITH_FIRE.description());//Description
        }
        if (player.blizzardDodger == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 185, "" + CombatTaskList.BLIZZARD_DODGER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 205, "" + CombatTaskList.BLIZZARD_DODGER.description());//Description
        }
        if (player.blizzardDodger >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 185, "<col=0fd60f>" + CombatTaskList.BLIZZARD_DODGER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 205, "" + CombatTaskList.BLIZZARD_DODGER.description());//Description
        }
        if (player.cryonoMore == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 186, "" + CombatTaskList.CRYO_NO_MORE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 206, "" + CombatTaskList.CRYO_NO_MORE.description());//Description
        }
        if (player.cryonoMore >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 186, "<col=0fd60f>" + CombatTaskList.CRYO_NO_MORE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 206, "" + CombatTaskList.CRYO_NO_MORE.description());//Description
        }
        if (player.shayzienSpecialist == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 187, "" + CombatTaskList.SHAYZIEN_SPECIALIST.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 207, "" + CombatTaskList.SHAYZIEN_SPECIALIST.description());//Description
        }
        if (player.shayzienSpecialist >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 187, "<col=0fd60f>" + CombatTaskList.SHAYZIEN_SPECIALIST.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 207, "" + CombatTaskList.SHAYZIEN_SPECIALIST.description());//Description
        }
        if (player.undyingraidTeam == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 188, "" + CombatTaskList.UNDYING_RAID_TEAM.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 208, "" + CombatTaskList.UNDYING_RAID_TEAM.description());//Description
        }
        if (player.undyingraidTeam >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 188, "<col=0fd60f>" + CombatTaskList.UNDYING_RAID_TEAM.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 208, "" + CombatTaskList.UNDYING_RAID_TEAM.description());//Description
        }
        if (player.muttaDiet == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 189, "" + CombatTaskList.MUTTA_DIET.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 209, "" + CombatTaskList.MUTTA_DIET.description());//Description
        }
        if (player.muttaDiet >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 189, "<col=0fd60f>" + CombatTaskList.MUTTA_DIET.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 209, "" + CombatTaskList.MUTTA_DIET.description());//Description
        }
        if (player.togetherwellFall == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 190, "" + CombatTaskList.TOGETHER_WELL_FALL.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 210, "" + CombatTaskList.TOGETHER_WELL_FALL.description());//Description
        }
        if (player.togetherwellFall >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 190, "<col=0fd60f>" + CombatTaskList.TOGETHER_WELL_FALL.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 210, "" + CombatTaskList.TOGETHER_WELL_FALL.description());//Description
        }
        if (player.perfectlyBalanced == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 191, "" + CombatTaskList.PERFECTLY_BALANCED.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 211, "" + CombatTaskList.PERFECTLY_BALANCED.description());//Description
        }
        if (player.perfectlyBalanced >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 191, "<col=0fd60f>" + CombatTaskList.PERFECTLY_BALANCED.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 211, "" + CombatTaskList.PERFECTLY_BALANCED.description());//Description
        }
        if (player.chambersofxericVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 192, "" + CombatTaskList.CHAMBERS_OF_XERIC_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 212, "" + CombatTaskList.CHAMBERS_OF_XERIC_VETERAN.description());//Description
        }
        if (player.chambersofxericVeteran > 0 && player.chambersofxericVeteran < 25) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 192, "<col=ffff00>" + CombatTaskList.CHAMBERS_OF_XERIC_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 212, "" + CombatTaskList.CHAMBERS_OF_XERIC_VETERAN.description());//Description
        }
        if (player.chambersofxericVeteran >= 25) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 192, "<col=0fd60f>" + CombatTaskList.CHAMBERS_OF_XERIC_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 212, "" + CombatTaskList.CHAMBERS_OF_XERIC_VETERAN.description());//Description
        }
        if (player.chaoselementalVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 193, "" + CombatTaskList.CHAOS_ELEMENTAL_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 213, "" + CombatTaskList.CHAOS_ELEMENTAL_VETERAN.description());//Description
        }
        if (player.chaoselementalVeteran > 0 && player.chaoselementalVeteran < 25) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 193, "<col=ffff00>" + CombatTaskList.CHAOS_ELEMENTAL_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 213, "" + CombatTaskList.CHAOS_ELEMENTAL_VETERAN.description());//Description
        }
        if (player.chaoselementalVeteran >= 25) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 193, "<col=0fd60f>" + CombatTaskList.CHAOS_ELEMENTAL_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 213, "" + CombatTaskList.CHAOS_ELEMENTAL_VETERAN.description());//Description
        }
        if (player.reminisce == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 194, "" + CombatTaskList.REMINISCE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 214, "" + CombatTaskList.REMINISCE.description());//Description
        }
        if (player.reminisce >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 194, "<col=0fd60f>" + CombatTaskList.REMINISCE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 214, "" + CombatTaskList.REMINISCE.description());//Description
        }
        if (player.commanderzilyanaVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 195, "" + CombatTaskList.COMMANDER_ZILYANA_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 215, "" + CombatTaskList.COMMANDER_ZILYANA_VETERAN.description());//Description
        }
        if (player.commanderzilyanaVeteran > 0 && player.commanderzilyanaVeteran < 100) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 195, "<col=ffff00>" + CombatTaskList.COMMANDER_ZILYANA_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 215, "" + CombatTaskList.COMMANDER_ZILYANA_VETERAN.description());//Description
        }
        if (player.commanderzilyanaVeteran >= 100) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 195, "<col=0fd60f>" + CombatTaskList.COMMANDER_ZILYANA_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 215, "" + CombatTaskList.COMMANDER_ZILYANA_VETERAN.description());//Description
        }
        if (player.chickenKiller == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 196, "" + CombatTaskList.CHICKEN_KILLER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 216, "" + CombatTaskList.CHICKEN_KILLER.description());//Description
        }
        if (player.chickenKiller >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 196, "<col=0fd60f>" + CombatTaskList.CHICKEN_KILLER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 216, "" + CombatTaskList.CHICKEN_KILLER.description());//Description
        }
        if (player.findingtheweakSpot == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 197, "" + CombatTaskList.FINDING_THE_WEAK_SPOT.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 217, "" + CombatTaskList.FINDING_THE_WEAK_SPOT.description());//Description
        }
        if (player.findingtheweakSpot >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 197, "<col=0fd60f>" + CombatTaskList.FINDING_THE_WEAK_SPOT.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 217, "" + CombatTaskList.FINDING_THE_WEAK_SPOT.description());//Description
        }
        if (player.hotonyourFeet == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 198, "" + CombatTaskList.HOT_ON_YOUR_FEET.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 218, "" + CombatTaskList.HOT_ON_YOUR_FEET.description());//Description
        }
        if (player.hotonyourFeet >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 198, "<col=0fd60f>" + CombatTaskList.HOT_ON_YOUR_FEET.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 218, "" + CombatTaskList.HOT_ON_YOUR_FEET.description());//Description
        }
        if (player.corporealbeastVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 470, "" + CombatTaskList.CORPOREAL_BEAST_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 292, "" + CombatTaskList.CORPOREAL_BEAST_VETERAN.description());//Description
        }
        if (player.corporealbeastVeteran > 0 && player.corporealbeastVeteran < 25) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 470, "<col=ffff00>" + CombatTaskList.CORPOREAL_BEAST_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 292, "" + CombatTaskList.CORPOREAL_BEAST_VETERAN.description());//Description
        }
        if (player.corporealbeastVeteran >= 25) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 470, "<col=0fd60f>" + CombatTaskList.CORPOREAL_BEAST_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 292, "" + CombatTaskList.CORPOREAL_BEAST_VETERAN.description());//Description
        }
        if (player.deathtotheseerKing == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 304, "" + CombatTaskList.DEAD_TO_THE_SEER_KING.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 293, "" + CombatTaskList.DEAD_TO_THE_SEER_KING.description());//Description
        }
        if (player.deathtotheseerKing >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 304, "<col=0fd60f>" + CombatTaskList.DEAD_TO_THE_SEER_KING.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 293, "" + CombatTaskList.DEAD_TO_THE_SEER_KING.description());//Description
        }
        if (player.deathtothewarriorKing == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 305, "" + CombatTaskList.DEAD_TO_THE_WARRIOR_KING.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 294, "" + CombatTaskList.DEAD_TO_THE_WARRIOR_KING.description());//Description
        }
        if (player.deathtothewarriorKing >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 305, "<col=0fd60f>" + CombatTaskList.DEAD_TO_THE_WARRIOR_KING.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 294, "" + CombatTaskList.DEAD_TO_THE_WARRIOR_KING.description());//Description
        }
        if (player.deathtothearcherKing == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 306, "" + CombatTaskList.DEAD_TO_THE_ARCHER_KING.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 295, "" + CombatTaskList.DEAD_TO_THE_ARCHER_KING.description());//Description
        }
        if (player.deathtothearcherKing >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 306, "<col=0fd60f>" + CombatTaskList.DEAD_TO_THE_ARCHER_KING.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 295, "" + CombatTaskList.DEAD_TO_THE_ARCHER_KING.description());//Description
        }
        if (player.hittingthemwhereitHurts == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 307, "" + CombatTaskList.HITTING_THEM_WHERE_IT_HURTS.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 296, "" + CombatTaskList.HITTING_THEM_WHERE_IT_HURTS.description());//Description
        }
        if (player.hittingthemwhereitHurts >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 307, "<col=0fd60f>" + CombatTaskList.HITTING_THEM_WHERE_IT_HURTS.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 296, "" + CombatTaskList.HITTING_THEM_WHERE_IT_HURTS.description());//Description
        }
        if (player.ifgorillascouldFly == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 308, "" + CombatTaskList.IF_GORILLAS_COULD_FLY.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 297, "" + CombatTaskList.IF_GORILLAS_COULD_FLY.description());//Description
        }
        if (player.ifgorillascouldFly >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 308, "<col=0fd60f>" + CombatTaskList.IF_GORILLAS_COULD_FLY.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 297, "" + CombatTaskList.IF_GORILLAS_COULD_FLY.description());//Description
        }
        if (player.facingjadheadOn == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 309, "" + CombatTaskList.FACING_JAD_HEAD_ON.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 298, "" + CombatTaskList.FACING_JAD_HEAD_ON.description());//Description
        }
        if (player.facingjadheadOn >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 309, "<col=0fd60f>" + CombatTaskList.FACING_JAD_HEAD_ON.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 298, "" + CombatTaskList.FACING_JAD_HEAD_ON.description());//Description
        }
        if (player.fightcavesVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 310, "" + CombatTaskList.FIGHT_CAVES_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 299, "" + CombatTaskList.FIGHT_CAVES_VETERAN.description());//Description
        }
        if (player.fightcavesVeteran >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 310, "<col=0fd60f>" + CombatTaskList.FIGHT_CAVES_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 299, "" + CombatTaskList.FIGHT_CAVES_VETERAN.description());//Description
        }
        if (player.fragmentofserenspeedTrialist == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 311, "" + CombatTaskList.FRAGMENT_OF_SEREN_SPEED_TRIALIST.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 300, "" + CombatTaskList.FRAGMENT_OF_SEREN_SPEED_TRIALIST.description());//Description
        }
        if (player.fragmentofserenspeedTrialist >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 311, "<col=0fd60f>" + CombatTaskList.FRAGMENT_OF_SEREN_SPEED_TRIALIST.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 300, "" + CombatTaskList.FRAGMENT_OF_SEREN_SPEED_TRIALIST.description());//Description
        }
        if (player.galvekspeedTrialist == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 312, "" + CombatTaskList.GALVEK_SPEED_TRIALIST.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 301, "" + CombatTaskList.GALVEK_SPEED_TRIALIST.description());//Description
        }
        if (player.galvekspeedTrialist >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 312, "<col=0fd60f>" + CombatTaskList.GALVEK_SPEED_TRIALIST.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 301, "" + CombatTaskList.GALVEK_SPEED_TRIALIST.description());//Description
        }
        if (player.ourgfreezerIi == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 313, "" + CombatTaskList.OURG_FREEZER_II.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 302, "" + CombatTaskList.OURG_FREEZER_II.description());//Description
        }
        if (player.ourgfreezerIi >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 313, "<col=0fd60f>" + CombatTaskList.OURG_FREEZER_II.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 302, "" + CombatTaskList.OURG_FREEZER_II.description());//Description
        }
        if (player.generalgraardorVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 314, "" + CombatTaskList.GENERAL_GRAARDOR_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 303, "" + CombatTaskList.GENERAL_GRAARDOR_VETERAN.description());//Description
        }
        if (player.generalgraardorVeteran > 0 && player.generalgraardorVeteran < 100) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 314, "<col=ffff00>" + CombatTaskList.GENERAL_GRAARDOR_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 303, "" + CombatTaskList.GENERAL_GRAARDOR_VETERAN.description());//Description
        }
        if (player.generalgraardorVeteran >= 100) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 314, "<col=0fd60f>" + CombatTaskList.GENERAL_GRAARDOR_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 303, "" + CombatTaskList.GENERAL_GRAARDOR_VETERAN.description());//Description
        }
        if (player.hardHitter == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 350, "" + CombatTaskList.HARD_HITTER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 343, "" + CombatTaskList.HARD_HITTER.description());//Description
        }
        if (player.hardHitter >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 350, "<col=0fd60f>" + CombatTaskList.HARD_HITTER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 343, "" + CombatTaskList.HARD_HITTER.description());//Description
        }
        if (player.halfwayThere == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 351, "" + CombatTaskList.HALF_WAY_THERE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 344, "" + CombatTaskList.HALF_WAY_THERE.description());//Description
        }
        if (player.halfwayThere >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 351, "<col=0fd60f>" + CombatTaskList.HALF_WAY_THERE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 344, "" + CombatTaskList.HALF_WAY_THERE.description());//Description
        }
        if (player.demonicDefence == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 352, "" + CombatTaskList.DEMONIC_DEFENCE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 345, "" + CombatTaskList.DEMONIC_DEFENCE.description());//Description
        }
        if (player.demonicDefence >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 352, "<col=0fd60f>" + CombatTaskList.DEMONIC_DEFENCE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 345, "" + CombatTaskList.DEMONIC_DEFENCE.description());//Description
        }
        if (player.thebaneofDemons == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 353, "" + CombatTaskList.THE_BANE_OF_DEMONS.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 346, "" + CombatTaskList.THE_BANE_OF_DEMONS.description());//Description
        }
        if (player.thebaneofDemons >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 353, "<col=0fd60f>" + CombatTaskList.THE_BANE_OF_DEMONS.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 346, "" + CombatTaskList.THE_BANE_OF_DEMONS.description());//Description
        }
        if (player.krilVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 354, "" + CombatTaskList.KRIL_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 347, "" + CombatTaskList.KRIL_VETERAN.description());//Description
        }
        if (player.krilVeteran > 0 && player.krilVeteran < 100) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 354, "<col=ffff00>" + CombatTaskList.KRIL_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 347, "" + CombatTaskList.KRIL_VETERAN.description());//Description
        }
        if (player.krilVeteran >= 100) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 354, "<col=0fd60f>" + CombatTaskList.KRIL_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 347, "" + CombatTaskList.KRIL_VETERAN.description());//Description
        }
        if (player.prayerSmasher == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 355, "" + CombatTaskList.PRAYER_SMASHER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 348, "" + CombatTaskList.PRAYER_SMASHER.description());//Description
        }
        if (player.prayerSmasher >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 355, "<col=0fd60f>" + CombatTaskList.PRAYER_SMASHER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 348, "" + CombatTaskList.PRAYER_SMASHER.description());//Description
        }
        if (player.insectDeflection == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 356, "" + CombatTaskList.INSECT_DEFLECTION.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 349, "" + CombatTaskList.INSECT_DEFLECTION.description());//Description
        }
        if (player.insectDeflection >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 356, "<col=0fd60f>" + CombatTaskList.INSECT_DEFLECTION.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 349, "" + CombatTaskList.INSECT_DEFLECTION.description());//Description
        }
        if (player.kalphitequeenVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 413, "" + CombatTaskList.KALPHITE_QUEEN_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 441, "" + CombatTaskList.KALPHITE_QUEEN_VETERAN.description());//Description
        }
        if (player.kalphitequeenVeteran > 0 && player.kalphitequeenVeteran < 50) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 413, "<col=ffff00>" + CombatTaskList.KALPHITE_QUEEN_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 441, "" + CombatTaskList.KALPHITE_QUEEN_VETERAN.description());//Description
        }
        if (player.kalphitequeenVeteran >= 50) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 413, "<col=0fd60f>" + CombatTaskList.KALPHITE_QUEEN_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 441, "" + CombatTaskList.KALPHITE_QUEEN_VETERAN.description());//Description
        }
        if (player.tenTacles == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 414, "" + CombatTaskList.TEN_TACLES.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 442, "" + CombatTaskList.TEN_TACLES.description());//Description
        }
        if (player.tenTacles > 0 && player.tenTacles < 50) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 414, "<col=ffff00>" + CombatTaskList.TEN_TACLES.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 442, "" + CombatTaskList.TEN_TACLES.description());//Description
        }
        if (player.tenTacles >= 50) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 414, "<col=0fd60f>" + CombatTaskList.TEN_TACLES.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 442, "" + CombatTaskList.TEN_TACLES.description());//Description
        }
        if (player.kreearraVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 415, "" + CombatTaskList.KREEARRA_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 443, "" + CombatTaskList.KREEARRA_VETERAN.description());//Description
        }
        if (player.kreearraVeteran > 0 && player.kreearraVeteran < 100) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 415, "<col=ffff00>" + CombatTaskList.KREEARRA_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 443, "" + CombatTaskList.KREEARRA_VETERAN.description());//Description
        }
        if (player.kreearraVeteran >= 100) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 415, "<col=0fd60f>" + CombatTaskList.KREEARRA_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 443, "" + CombatTaskList.KREEARRA_VETERAN.description());//Description
        }
        if (player.scorpiaVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 416, "" + CombatTaskList.SCORPIA_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 444, "" + CombatTaskList.SCORPIA_VETERAN.description());//Description
        }
        if (player.scorpiaVeteran > 0 && player.scorpiaVeteran < 25) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 416, "<col=ffff00>" + CombatTaskList.SCORPIA_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 444, "" + CombatTaskList.SCORPIA_VETERAN.description());//Description
        }
        if (player.scorpiaVeteran >= 25) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 416, "<col=0fd60f>" + CombatTaskList.SCORPIA_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 444, "" + CombatTaskList.SCORPIA_VETERAN.description());//Description
        }
        if (player.upfortheChallenge == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 417, "" + CombatTaskList.UP_FOR_THE_CHALLENGE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 445, "" + CombatTaskList.UP_FOR_THE_CHALLENGE.description());//Description
        }
        if (player.upfortheChallenge >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 417, "<col=0fd60f>" + CombatTaskList.UP_FOR_THE_CHALLENGE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 445, "" + CombatTaskList.UP_FOR_THE_CHALLENGE.description());//Description
        }
        if (player.demonEvasion == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 418, "" + CombatTaskList.DEMON_EVASION.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 446, "" + CombatTaskList.DEMON_EVASION.description());//Description
        }
        if (player.demonEvasion >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 418, "<col=0fd60f>" + CombatTaskList.DEMON_EVASION.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 446, "" + CombatTaskList.DEMON_EVASION.description());//Description
        }
        if (player.hazardPrevention == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 419, "" + CombatTaskList.HAZARD_PREVENTION.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 447, "" + CombatTaskList.HAZARD_PREVENTION.description());//Description
        }
        if (player.hazardPrevention >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 419, "<col=0fd60f>" + CombatTaskList.HAZARD_PREVENTION.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 447, "" + CombatTaskList.HAZARD_PREVENTION.description());//Description
        }
        if (player.thermonuclearVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 420, "" + CombatTaskList.THERMONUCLEAR_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 448, "" + CombatTaskList.THERMONUCLEAR_VETERAN.description());//Description
        }
        if (player.thermonuclearVeteran > 0 && player.thermonuclearVeteran < 20) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 420, "<col=ffff00>" + CombatTaskList.THERMONUCLEAR_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 448, "" + CombatTaskList.THERMONUCLEAR_VETERAN.description());//Description
        }
        if (player.thermonuclearVeteran >= 20) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 420, "<col=0fd60f>" + CombatTaskList.THERMONUCLEAR_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 448, "" + CombatTaskList.THERMONUCLEAR_VETERAN.description());//Description
        }
        if (player.facingjadheadonIii == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 421, "" + CombatTaskList.FACING_JAD_HEAD_ON_III.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 449, "" + CombatTaskList.FACING_JAD_HEAD_ON_III.description());//Description
        }
        if (player.facingjadheadonIii >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 421, "<col=0fd60f>" + CombatTaskList.FACING_JAD_HEAD_ON_III.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 449, "" + CombatTaskList.FACING_JAD_HEAD_ON_III.description());//Description
        }
        if (player.tzhaarketraksSpeedTrialist == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 422, "" + CombatTaskList.TZHAAR_KET_RAKS_SPEED_TRIALIST.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 450, "" + CombatTaskList.TZHAAR_KET_RAKS_SPEED_TRIALIST.description());//Description
        }
        if (player.tzhaarketraksSpeedTrialist >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 422, "<col=0fd60f>" + CombatTaskList.TZHAAR_KET_RAKS_SPEED_TRIALIST.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 450, "" + CombatTaskList.TZHAAR_KET_RAKS_SPEED_TRIALIST.description());//Description
        }
        if (player.theiijadChallenge == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 423, "" + CombatTaskList.THE_II_JAD_CHALLENGE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 451, "" + CombatTaskList.THE_II_JAD_CHALLENGE.description());//Description
        }
        if (player.theiijadChallenge >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 423, "<col=0fd60f>" + CombatTaskList.THE_II_JAD_CHALLENGE.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 451, "" + CombatTaskList.THE_II_JAD_CHALLENGE.description());//Description
        }
        if (player.venenatisVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 424, "" + CombatTaskList.VENENATIS_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 452, "" + CombatTaskList.VENENATIS_VETERAN.description());//Description
        }
        if (player.venenatisVeteran > 0 && player.venenatisVeteran < 20) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 424, "<col=ffff00>" + CombatTaskList.VENENATIS_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 452, "" + CombatTaskList.VENENATIS_VETERAN.description());//Description
        }
        if (player.venenatisVeteran >= 20) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 424, "<col=0fd60f>" + CombatTaskList.VENENATIS_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 452, "" + CombatTaskList.VENENATIS_VETERAN.description());//Description
        }
        if (player.vetEran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 425, "" + CombatTaskList.VET_ERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 453, "" + CombatTaskList.VET_ERAN.description());//Description
        }
        if (player.vetEran > 0 && player.vetEran < 20) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 425, "<col=ffff00>" + CombatTaskList.VET_ERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 453, "" + CombatTaskList.VET_ERAN.description());//Description
        }
        if (player.vetEran >= 20) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 425, "<col=0fd60f>" + CombatTaskList.VET_ERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 453, "" + CombatTaskList.VET_ERAN.description());//Description
        }
        if (player.stickemwiththepointyEnd == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 426, "" + CombatTaskList.STICK_EM_WITH_THE_POINTY_END.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 454, "" + CombatTaskList.STICK_EM_WITH_THE_POINTY_END.description());//Description
        }
        if (player.stickemwiththepointyEnd >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 426, "<col=0fd60f>" + CombatTaskList.STICK_EM_WITH_THE_POINTY_END.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 454, "" + CombatTaskList.STICK_EM_WITH_THE_POINTY_END.description());//Description
        }
        if (player.zombieDestroyer == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 427, "" + CombatTaskList.ZOMBIE_DESTROYER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 455, "" + CombatTaskList.ZOMBIE_DESTROYER.description());//Description
        }
        if (player.zombieDestroyer >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 427, "<col=0fd60f>" + CombatTaskList.ZOMBIE_DESTROYER.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 455, "" + CombatTaskList.ZOMBIE_DESTROYER.description());//Description
        }
        if (player.vorkathVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 428, "" + CombatTaskList.VORKATH_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 456, "" + CombatTaskList.VORKATH_VETERAN.description());//Description
        }
        if (player.vorkathVeteran > 0 && player.vorkathVeteran < 50) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 428, "<col=ffff00>" + CombatTaskList.VORKATH_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 456, "" + CombatTaskList.VORKATH_VETERAN.description());//Description
        }
        if (player.vorkathVeteran >= 50) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 428, "<col=0fd60f>" + CombatTaskList.VORKATH_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 456, "" + CombatTaskList.VORKATH_VETERAN.description());//Description
        }
        if (player.zulrahspeedTrialist == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 429, "" + CombatTaskList.ZULRAH_SPEED_TRIALIST.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 457, "" + CombatTaskList.ZULRAH_SPEED_TRIALIST.description());//Description
        }
        if (player.zulrahspeedTrialist >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 429, "<col=0fd60f>" + CombatTaskList.ZULRAH_SPEED_TRIALIST.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 457, "" + CombatTaskList.ZULRAH_SPEED_TRIALIST.description());//Description
        }
        if (player.snakeRebound == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 430, "" + CombatTaskList.SNAKE_REBOUND.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 458, "" + CombatTaskList.SNAKE_REBOUND.description());//Description
        }
        if (player.snakeRebound >= 1) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 430, "<col=0fd60f>" + CombatTaskList.SNAKE_REBOUND.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 458, "" + CombatTaskList.SNAKE_REBOUND.description());//Description
        }
        if (player.zulrahVeteran == 0) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 431, "" + CombatTaskList.ZULRAH_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 459, "" + CombatTaskList.ZULRAH_VETERAN.description());//Description
        }
        if (player.zulrahVeteran > 0 && player.zulrahVeteran < 50) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 431, "<col=ffff00>" + CombatTaskList.ZULRAH_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 459, "" + CombatTaskList.ZULRAH_VETERAN.description());//Description
        }
        if (player.zulrahVeteran >= 50) {
            player.getPacketSender().sendString(ELITE_INTERFACE, 431, "<col=0fd60f>" + CombatTaskList.ZULRAH_VETERAN.title());//Title
            player.getPacketSender().sendString(ELITE_INTERFACE, 459, "" + CombatTaskList.ZULRAH_VETERAN.description());//Description
        }
    }
    public static void sendMasterList(Player player) {
        if (player.workingOvertime == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 227, "" + CombatTaskList.WORKING_OVERTIME.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 145, "" + CombatTaskList.WORKING_OVERTIME.description());//Description
        }
        if (player.workingOvertime > 0 && player.workingOvertime < 15) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 227, "<col=ffff00>" + CombatTaskList.WORKING_OVERTIME.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 145, "" + CombatTaskList.WORKING_OVERTIME.description());//Description
        }
        if (player.workingOvertime >= 15) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 227, "<col=0fd60f>" + CombatTaskList.WORKING_OVERTIME.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 145, "" + CombatTaskList.WORKING_OVERTIME.description());//Description
        }
        if (player.alchemicalspeedChaser == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 45, "" + CombatTaskList.ALCHEMICAL_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 146, "" + CombatTaskList.ALCHEMICAL_SPEED_CHASER.description());//Description
        }
        if (player.alchemicalspeedChaser >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 45, "<col=0fd60f>" + CombatTaskList.ALCHEMICAL_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 146, "" + CombatTaskList.ALCHEMICAL_SPEED_CHASER.description());//Description
        }
        if (player.alcleanicalHydra == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 47, "" + CombatTaskList.ALCLEANICAL_HYDRA.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 147, "" + CombatTaskList.ALCLEANICAL_HYDRA.description());//Description
        }
        if (player.alcleanicalHydra >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 47, "<col=0fd60f>" + CombatTaskList.ALCLEANICAL_HYDRA.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 147, "" + CombatTaskList.ALCLEANICAL_HYDRA.description());//Description
        }
        if (player.theflameSkipper == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 49, "" + CombatTaskList.THE_FLAME_SKIPPER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 148, "" + CombatTaskList.THE_FLAME_SKIPPER.description());//Description
        }
        if (player.theflameSkipper >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 49, "<col=0fd60f>" + CombatTaskList.THE_FLAME_SKIPPER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 148, "" + CombatTaskList.THE_FLAME_SKIPPER.description());//Description
        }
        if (player.mixingCorrectly == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 51, "" + CombatTaskList.MIXING_CORRECTLY.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 149, "" + CombatTaskList.MIXING_CORRECTLY.description());//Description
        }
        if (player.mixingCorrectly >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 51, "<col=0fd60f>" + CombatTaskList.MIXING_CORRECTLY.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 149, "" + CombatTaskList.MIXING_CORRECTLY.description());//Description
        }
        if (player.dontflameMe == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 53, "" + CombatTaskList.DONT_FLAME_ME.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 150, "" + CombatTaskList.DONT_FLAME_ME.description());//Description
        }
        if (player.dontflameMe >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 53, "<col=0fd60f>" + CombatTaskList.DONT_FLAME_ME.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 150, "" + CombatTaskList.DONT_FLAME_ME.description());//Description
        }
        if (player.lightningLure == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 55, "" + CombatTaskList.LIGHTNING_LURE.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 151, "" + CombatTaskList.LIGHTNING_LURE.description());//Description
        }
        if (player.lightningLure >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 55, "<col=0fd60f>" + CombatTaskList.LIGHTNING_LURE.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 151, "" + CombatTaskList.LIGHTNING_LURE.description());//Description
        }
        if (player.unrequiredAntipoisons == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 229, "" + CombatTaskList.UNREQUIRED_ANTIPOISONS.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 200, "" + CombatTaskList.UNREQUIRED_ANTIPOISONS.description());//Description
        }
        if (player.unrequiredAntipoisons >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 229, "<col=0fd60f>" + CombatTaskList.UNREQUIRED_ANTIPOISONS.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 200, "" + CombatTaskList.UNREQUIRED_ANTIPOISONS.description());//Description
        }
        if (player.alchemicalMaster == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 230, "" + CombatTaskList.ALCHEMICAL_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 201, "" + CombatTaskList.ALCHEMICAL_MASTER.description());//Description
        }
        if (player.alchemicalMaster > 0 && player.alchemicalMaster < 150) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 230, "<col=ffff00>" + CombatTaskList.ALCHEMICAL_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 201, "" + CombatTaskList.ALCHEMICAL_MASTER.description());//Description
        }
        if (player.alchemicalMaster >= 150) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 230, "<col=0fd60f>" + CombatTaskList.ALCHEMICAL_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 201, "" + CombatTaskList.ALCHEMICAL_MASTER.description());//Description
        }
        if (player.arooonoMore == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 231, "" + CombatTaskList.AROOO_NO_MORE.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 202, "" + CombatTaskList.AROOO_NO_MORE.description());//Description
        }
        if (player.arooonoMore >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 231, "<col=0fd60f>" + CombatTaskList.AROOO_NO_MORE.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 202, "" + CombatTaskList.AROOO_NO_MORE.description());//Description
        }
        if (player.cerberusMaster == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 232, "" + CombatTaskList.CERBERUS_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 203, "" + CombatTaskList.CERBERUS_MASTER.description());//Description
        }
        if (player.cerberusMaster > 0 && player.cerberusMaster < 150) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 232, "<col=ffff00>" + CombatTaskList.CERBERUS_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 203, "" + CombatTaskList.CERBERUS_MASTER.description());//Description
        }
        if (player.cerberusMaster >= 150) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 232, "<col=0fd60f>" + CombatTaskList.CERBERUS_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 203, "" + CombatTaskList.CERBERUS_MASTER.description());//Description
        }
        if (player.coxtriospeedChaser == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 233, "" + CombatTaskList.COX_TRIO_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 204, "" + CombatTaskList.COX_TRIO_SPEED_CHASER.description());//Description
        }
        if (player.coxtriospeedChaser >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 233, "<col=0fd60f>" + CombatTaskList.COX_TRIO_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 204, "" + CombatTaskList.COX_TRIO_SPEED_CHASER.description());//Description
        }
        if (player.coxfivescalespeedChaser == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 234, "" + CombatTaskList.COX_FIVE_SCALE_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 205, "" + CombatTaskList.COX_FIVE_SCALE_SPEED_CHASER.description());//Description
        }
        if (player.coxfivescalespeedChaser >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 234, "<col=0fd60f>" + CombatTaskList.COX_FIVE_SCALE_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 205, "" + CombatTaskList.COX_FIVE_SCALE_SPEED_CHASER.description());//Description
        }
        if (player.coxsolospeedChaser == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 235, "" + CombatTaskList.COX_SOLO_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 206, "" + CombatTaskList.COX_SOLO_SPEED_CHASER.description());//Description
        }
        if (player.coxsolospeedChaser >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 235, "<col=0fd60f>" + CombatTaskList.COX_SOLO_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 206, "" + CombatTaskList.COX_SOLO_SPEED_CHASER.description());//Description
        }
        if (player.blindSpot == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 236, "" + CombatTaskList.BLIND_SPOT.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 207, "" + CombatTaskList.BLIND_SPOT.description());//Description
        }
        if (player.blindSpot >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 236, "<col=0fd60f>" + CombatTaskList.BLIND_SPOT.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 207, "" + CombatTaskList.BLIND_SPOT.description());//Description
        }
        if (player.undyingRaider == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 237, "" + CombatTaskList.UNDYING_RAIDER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 208, "" + CombatTaskList.UNDYING_RAIDER.description());//Description
        }
        if (player.undyingRaider >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 237, "<col=0fd60f>" + CombatTaskList.UNDYING_RAIDER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 208, "" + CombatTaskList.UNDYING_RAIDER.description());//Description
        }
        if (player.anvilnoMore == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 238, "" + CombatTaskList.ANVIL_NO_MORE.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 209, "" + CombatTaskList.ANVIL_NO_MORE.description());//Description
        }
        if (player.anvilnoMore >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 238, "<col=0fd60f>" + CombatTaskList.ANVIL_NO_MORE.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 209, "" + CombatTaskList.ANVIL_NO_MORE.description());//Description
        }
        if (player.stopdropandRoll == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 239, "" + CombatTaskList.STOP_DROP_AND_ROLL.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 210, "" + CombatTaskList.STOP_DROP_AND_ROLL.description());//Description
        }
        if (player.stopdropandRoll >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 239, "<col=0fd60f>" + CombatTaskList.STOP_DROP_AND_ROLL.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 210, "" + CombatTaskList.STOP_DROP_AND_ROLL.description());//Description
        }
        if (player.puttingitolmtheLine == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 240, "" + CombatTaskList.PUTTING_IT_OLM_THE_LINE.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 211, "" + CombatTaskList.PUTTING_IT_OLM_THE_LINE.description());//Description
        }
        if (player.puttingitolmtheLine >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 240, "<col=0fd60f>" + CombatTaskList.PUTTING_IT_OLM_THE_LINE.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 211, "" + CombatTaskList.PUTTING_IT_OLM_THE_LINE.description());//Description
        }
        if (player.coxMaster == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 241, "" + CombatTaskList.COX_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 212, "" + CombatTaskList.COX_MASTER.description());//Description
        }
        if (player.coxMaster > 0 && player.coxMaster < 75) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 241, "<col=ffff00>" + CombatTaskList.COX_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 212, "" + CombatTaskList.COX_MASTER.description());//Description
        }
        if (player.coxMaster >= 75) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 241, "<col=0fd60f>" + CombatTaskList.COX_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 212, "" + CombatTaskList.COX_MASTER.description());//Description
        }
        if (player.corporealbeastMaster == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 242, "" + CombatTaskList.CORPOREAL_BEAST_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 213, "" + CombatTaskList.CORPOREAL_BEAST_MASTER.description());//Description
        }
        if (player.corporealbeastMaster > 0 && player.corporealbeastMaster < 50) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 242, "<col=ffff00>" + CombatTaskList.CORPOREAL_BEAST_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 213, "" + CombatTaskList.CORPOREAL_BEAST_MASTER.description());//Description
        }
        if (player.corporealbeastMaster >= 50) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 242, "<col=0fd60f>" + CombatTaskList.CORPOREAL_BEAST_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 213, "" + CombatTaskList.CORPOREAL_BEAST_MASTER.description());//Description
        }
        if (player.fightcavesspeedChaser == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 243, "" + CombatTaskList.FIGHT_CAVES_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 214, "" + CombatTaskList.FIGHT_CAVES_SPEED_CHASER.description());//Description
        }
        if (player.fightcavesspeedChaser >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 243, "<col=0fd60f>" + CombatTaskList.FIGHT_CAVES_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 214, "" + CombatTaskList.FIGHT_CAVES_SPEED_CHASER.description());//Description
        }
        if (player.denyingtheHealers == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 244, "" + CombatTaskList.DENYING_THE_HEALERS.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 215, "" + CombatTaskList.DENYING_THE_HEALERS.description());//Description
        }
        if (player.denyingtheHealers >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 244, "<col=0fd60f>" + CombatTaskList.DENYING_THE_HEALERS.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 215, "" + CombatTaskList.DENYING_THE_HEALERS.description());//Description
        }
        if (player.fightcavesMaster == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 245, "" + CombatTaskList.FIGHT_CAVES_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 216, "" + CombatTaskList.FIGHT_CAVES_MASTER.description());//Description
        }
        if (player.fightcavesMaster > 0 && player.fightcavesMaster < 5) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 245, "<col=ffff00>" + CombatTaskList.FIGHT_CAVES_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 216, "" + CombatTaskList.FIGHT_CAVES_MASTER.description());//Description
        }
        if (player.fightcavesMaster >= 5) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 245, "<col=0fd60f>" + CombatTaskList.FIGHT_CAVES_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 216, "" + CombatTaskList.FIGHT_CAVES_MASTER.description());//Description
        }
        if (player.nibblersBegone == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 246, "" + CombatTaskList.NIBBLERS_BEGONE.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 217, "" + CombatTaskList.NIBBLERS_BEGONE.description());//Description
        }
        if (player.nibblersBegone >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 246, "<col=0fd60f>" + CombatTaskList.NIBBLERS_BEGONE.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 217, "" + CombatTaskList.NIBBLERS_BEGONE.description());//Description
        }
        if (player.onehundredTentacles == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 247, "" + CombatTaskList.ONE_HUNDRED_TENTACLES.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 218, "" + CombatTaskList.ONE_HUNDRED_TENTACLES.description());//Description
        }
        if (player.onehundredTentacles > 0 && player.onehundredTentacles < 100) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 247, "<col=ffff00>" + CombatTaskList.ONE_HUNDRED_TENTACLES.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 218, "" + CombatTaskList.ONE_HUNDRED_TENTACLES.description());//Description
        }
        if (player.onehundredTentacles >= 100) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 247, "<col=0fd60f>" + CombatTaskList.ONE_HUNDRED_TENTACLES.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 218, "" + CombatTaskList.ONE_HUNDRED_TENTACLES.description());//Description
        }
        if (player.swoopnoMore == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 248, "" + CombatTaskList.SWOOP_NO_MORE.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 292, "" + CombatTaskList.SWOOP_NO_MORE.description());//Description
        }
        if (player.swoopnoMore >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 248, "<col=0fd60f>" + CombatTaskList.SWOOP_NO_MORE.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 292, "" + CombatTaskList.SWOOP_NO_MORE.description());//Description
        }
        if (player.precisePositioning == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 304, "" + CombatTaskList.PRECISE_POSITIONING.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 293, "" + CombatTaskList.PRECISE_POSITIONING.description());//Description
        }
        if (player.precisePositioning >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 304, "<col=0fd60f>" + CombatTaskList.PRECISE_POSITIONING.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 293, "" + CombatTaskList.PRECISE_POSITIONING.description());//Description
        }
        if (player.supplieswhoneedsEm == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 305, "" + CombatTaskList.SUPPLIES_WHO_NEEDS_EM.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 294, "" + CombatTaskList.SUPPLIES_WHO_NEEDS_EM.description());//Description
        }
        if (player.supplieswhoneedsEm >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 305, "<col=0fd60f>" + CombatTaskList.SUPPLIES_WHO_NEEDS_EM.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 294, "" + CombatTaskList.SUPPLIES_WHO_NEEDS_EM.description());//Description
        }
        if (player.facingjadheadonIv == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 306, "" + CombatTaskList.FACING_JAD_HEAD_ON_IV.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 295, "" + CombatTaskList.FACING_JAD_HEAD_ON_IV.description());//Description
        }
        if (player.facingjadheadonIv >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 306, "<col=0fd60f>" + CombatTaskList.FACING_JAD_HEAD_ON_IV.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 295, "" + CombatTaskList.FACING_JAD_HEAD_ON_IV.description());//Description
        }
        if (player.tzhaarketraksspeedChaser == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 307, "" + CombatTaskList.TZHAAR_KET_RAKS_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 296, "" + CombatTaskList.TZHAAR_KET_RAKS_SPEED_CHASER.description());//Description
        }
        if (player.tzhaarketraksspeedChaser >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 307, "<col=0fd60f>" + CombatTaskList.TZHAAR_KET_RAKS_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 296, "" + CombatTaskList.TZHAAR_KET_RAKS_SPEED_CHASER.description());//Description
        }
        if (player.theivjadChallenge == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 308, "" + CombatTaskList.THE_IV_JAD_CHALLENGE.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 297, "" + CombatTaskList.THE_IV_JAD_CHALLENGE.description());//Description
        }
        if (player.theivjadChallenge >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 308, "<col=0fd60f>" + CombatTaskList.THE_IV_JAD_CHALLENGE.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 297, "" + CombatTaskList.THE_IV_JAD_CHALLENGE.description());//Description
        }
        if (player.extendedEncounter == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 309, "" + CombatTaskList.EXTENDED_ENCOUNTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 298, "" + CombatTaskList.EXTENDED_ENCOUNTER.description());//Description
        }
        if (player.extendedEncounter > 0 && player.extendedEncounter < 10) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 309, "<col=ffff00>" + CombatTaskList.EXTENDED_ENCOUNTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 298, "" + CombatTaskList.EXTENDED_ENCOUNTER.description());//Description
        }
        if (player.extendedEncounter >= 10) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 309, "<col=0fd60f>" + CombatTaskList.EXTENDED_ENCOUNTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 298, "" + CombatTaskList.EXTENDED_ENCOUNTER.description());//Description
        }
        if (player.vorkathspeedChaser == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 310, "" + CombatTaskList.VORKATH_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 299, "" + CombatTaskList.VORKATH_SPEED_CHASER.description());//Description
        }
        if (player.vorkathspeedChaser >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 310, "<col=0fd60f>" + CombatTaskList.VORKATH_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 299, "" + CombatTaskList.VORKATH_SPEED_CHASER.description());//Description
        }
        if (player.vorkathMaster == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 311, "" + CombatTaskList.VORKATH_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 300, "" + CombatTaskList.VORKATH_MASTER.description());//Description
        }
        if (player.vorkathMaster > 0 && player.vorkathMaster < 100) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 311, "<col=ffff00>" + CombatTaskList.VORKATH_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 300, "" + CombatTaskList.VORKATH_MASTER.description());//Description
        }
        if (player.vorkathMaster >= 100) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 311, "<col=0fd60f>" + CombatTaskList.VORKATH_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 300, "" + CombatTaskList.VORKATH_MASTER.description());//Description
        }
        if (player.zulrahspeedChaser == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 312, "" + CombatTaskList.ZULRAH_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 301, "" + CombatTaskList.ZULRAH_SPEED_CHASER.description());//Description
        }
        if (player.zulrahspeedChaser >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 312, "<col=0fd60f>" + CombatTaskList.ZULRAH_SPEED_CHASER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 301, "" + CombatTaskList.ZULRAH_SPEED_CHASER.description());//Description
        }
        if (player.perfectZulrah == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 313, "" + CombatTaskList.PERFECT_ZULRAH.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 302, "" + CombatTaskList.PERFECT_ZULRAH.description());//Description
        }
        if (player.perfectZulrah >= 1) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 313, "<col=0fd60f>" + CombatTaskList.PERFECT_ZULRAH.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 302, "" + CombatTaskList.PERFECT_ZULRAH.description());//Description
        }
        if (player.zulrahMaster == 0) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 314, "" + CombatTaskList.ZULRAH_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 303, "" + CombatTaskList.ZULRAH_MASTER.description());//Description
        }
        if (player.zulrahMaster > 0 && player.zulrahMaster < 100) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 314, "<col=ffff00>" + CombatTaskList.ZULRAH_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 303, "" + CombatTaskList.ZULRAH_MASTER.description());//Description
        }
        if (player.zulrahMaster >= 100) {
            player.getPacketSender().sendString(MASTER_INTERFACE, 314, "<col=0fd60f>" + CombatTaskList.ZULRAH_MASTER.title());//Title
            player.getPacketSender().sendString(MASTER_INTERFACE, 303, "" + CombatTaskList.ZULRAH_MASTER.description());//Description
        }
    }
    public static void sendGrandMasterList(Player player) {
        if (player.alchemicalspeedRunner == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 227, "" + CombatTaskList.ALCHEMICAL_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 145, "" + CombatTaskList.ALCHEMICAL_SPEED_RUNNER.description());//Description
        }
        if (player.alchemicalspeedRunner >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 227, "<col=0fd60f>" + CombatTaskList.ALCHEMICAL_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 145, "" + CombatTaskList.ALCHEMICAL_SPEED_RUNNER.description());//Description
        }
        if (player.noPressure == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 45, "" + CombatTaskList.NO_PRESSURE.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 146, "" + CombatTaskList.NO_PRESSURE.description());//Description
        }
        if (player.noPressure >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 45, "<col=0fd60f>" + CombatTaskList.NO_PRESSURE.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 146, "" + CombatTaskList.NO_PRESSURE.description());//Description
        }
        if (player.coxtriospeedRunner == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 47, "" + CombatTaskList.COX_TRIO_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 147, "" + CombatTaskList.COX_TRIO_SPEED_RUNNER.description());//Description
        }
        if (player.coxtriospeedRunner >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 47, "<col=0fd60f>" + CombatTaskList.COX_TRIO_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 147, "" + CombatTaskList.COX_TRIO_SPEED_RUNNER.description());//Description
        }
        if (player.coxfivescalespeedRunner == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 49, "" + CombatTaskList.COX_FIVE_SCALE_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 148, "" + CombatTaskList.COX_FIVE_SCALE_SPEED_RUNNER.description());//Description
        }
        if (player.coxfivescalespeedRunner >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 49, "<col=0fd60f>" + CombatTaskList.COX_FIVE_SCALE_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 148, "" + CombatTaskList.COX_FIVE_SCALE_SPEED_RUNNER.description());//Description
        }
        if (player.coxsolospeedRunner == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 51, "" + CombatTaskList.COX_SOLO_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 149, "" + CombatTaskList.COX_SOLO_SPEED_RUNNER.description());//Description
        }
        if (player.coxsolospeedRunner >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 51, "<col=0fd60f>" + CombatTaskList.COX_SOLO_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 149, "" + CombatTaskList.COX_SOLO_SPEED_RUNNER.description());//Description
        }
        if (player.coxgrandMaster == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 53, "" + CombatTaskList.COX_GRANDMASTER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 150, "" + CombatTaskList.COX_GRANDMASTER.description());//Description
        }
        if (player.coxgrandMaster > 0 && player.coxgrandMaster < 150) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 53, "<col=ffff00>" + CombatTaskList.COX_GRANDMASTER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 150, "" + CombatTaskList.COX_GRANDMASTER.description());//Description
        }
        if (player.coxgrandMaster >= 150) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 53, "<col=0fd60f>" + CombatTaskList.COX_GRANDMASTER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 150, "" + CombatTaskList.COX_GRANDMASTER.description());//Description
        }
        if (player.peachConjurer == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 55, "" + CombatTaskList.PEACH_CONJURER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 151, "" + CombatTaskList.PEACH_CONJURER.description());//Description
        }
        if (player.peachConjurer > 0 && player.peachConjurer < 50) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 55, "<col=ffff00>" + CombatTaskList.PEACH_CONJURER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 151, "" + CombatTaskList.PEACH_CONJURER.description());//Description
        }
        if (player.peachConjurer >= 50) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 55, "<col=0fd60f>" + CombatTaskList.PEACH_CONJURER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 151, "" + CombatTaskList.PEACH_CONJURER.description());//Description
        }
        if (player.animalWhisperer == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 229, "" + CombatTaskList.ANIMAL_WHISPERER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 200, "" + CombatTaskList.ANIMAL_WHISPERER.description());//Description
        }
        if (player.animalWhisperer >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 229, "<col=0fd60f>" + CombatTaskList.ANIMAL_WHISPERER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 200, "" + CombatTaskList.ANIMAL_WHISPERER.description());//Description
        }
        if (player.fightcavesspeedRunner == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 230, "" + CombatTaskList.FIGHT_CAVES_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 201, "" + CombatTaskList.FIGHT_CAVES_SPEED_RUNNER.description());//Description
        }
        if (player.fightcavesspeedRunner >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 230, "<col=0fd60f>" + CombatTaskList.FIGHT_CAVES_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 201, "" + CombatTaskList.FIGHT_CAVES_SPEED_RUNNER.description());//Description
        }
        if (player.notimeforaDrink == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 231, "" + CombatTaskList.NO_TIME_FOR_A_DRINK.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 202, "" + CombatTaskList.NO_TIME_FOR_A_DRINK.description());//Description
        }
        if (player.notimeforaDrink >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 231, "<col=0fd60f>" + CombatTaskList.NO_TIME_FOR_A_DRINK.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 202, "" + CombatTaskList.NO_TIME_FOR_A_DRINK.description());//Description
        }
        if (player.denyingthehealersIi == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 232, "" + CombatTaskList.DENYING_THE_HEALERS_II.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 203, "" + CombatTaskList.DENYING_THE_HEALERS_II.description());//Description
        }
        if (player.denyingthehealersIi >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 232, "<col=0fd60f>" + CombatTaskList.DENYING_THE_HEALERS_II.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 203, "" + CombatTaskList.DENYING_THE_HEALERS_II.description());//Description
        }
        if (player.ourgKiller == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 233, "" + CombatTaskList.OURG_KILLER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 204, "" + CombatTaskList.OURG_KILLER.description());//Description
        }
        if (player.ourgKiller > 0 && player.ourgKiller < 15) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 233, "<col=ffff00>" + CombatTaskList.OURG_KILLER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 204, "" + CombatTaskList.OURG_KILLER.description());//Description
        }
        if (player.ourgKiller >= 15) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 233, "<col=0fd60f>" + CombatTaskList.OURG_KILLER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 204, "" + CombatTaskList.OURG_KILLER.description());//Description
        }
        if (player.keepAway == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 234, "" + CombatTaskList.KEEP_AWAY.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 205, "" + CombatTaskList.KEEP_AWAY.description());//Description
        }
        if (player.keepAway >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 234, "<col=0fd60f>" + CombatTaskList.KEEP_AWAY.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 205, "" + CombatTaskList.KEEP_AWAY.description());//Description
        }
        if (player.infernospeedRunner == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 235, "" + CombatTaskList.INFERNO_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 206, "" + CombatTaskList.INFERNO_SPEED_RUNNER.description());//Description
        }
        if (player.infernospeedRunner >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 235, "<col=0fd60f>" + CombatTaskList.INFERNO_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 206, "" + CombatTaskList.INFERNO_SPEED_RUNNER.description());//Description
        }
        if (player.jadwhatareyoudoingHere == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 236, "" + CombatTaskList.JAD_WHAT_ARE_YOU_DOING_HERE.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 207, "" + CombatTaskList.JAD_WHAT_ARE_YOU_DOING_HERE.description());//Description
        }
        if (player.jadwhatareyoudoingHere >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 236, "<col=0fd60f>" + CombatTaskList.JAD_WHAT_ARE_YOU_DOING_HERE.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 207, "" + CombatTaskList.JAD_WHAT_ARE_YOU_DOING_HERE.description());//Description
        }
        if (player.facingjadheadonIi == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 237, "" + CombatTaskList.FACING_JAD_HEAD_ON_II.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 208, "" + CombatTaskList.FACING_JAD_HEAD_ON_II.description());//Description
        }
        if (player.facingjadheadonIi >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 237, "<col=0fd60f>" + CombatTaskList.FACING_JAD_HEAD_ON_II.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 208, "" + CombatTaskList.FACING_JAD_HEAD_ON_II.description());//Description
        }
        if (player.nibblerChaser == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 238, "" + CombatTaskList.NIBBLER_CHASER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 209, "" + CombatTaskList.NIBBLER_CHASER.description());//Description
        }
        if (player.nibblerChaser >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 238, "<col=0fd60f>" + CombatTaskList.NIBBLER_CHASER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 209, "" + CombatTaskList.NIBBLER_CHASER.description());//Description
        }
        if (player.budgetSetup == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 239, "" + CombatTaskList.BUDGET_SETUP.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 210, "" + CombatTaskList.BUDGET_SETUP.description());//Description
        }
        if (player.budgetSetup >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 239, "<col=0fd60f>" + CombatTaskList.BUDGET_SETUP.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 210, "" + CombatTaskList.BUDGET_SETUP.description());//Description
        }
        if (player.wasntevenClose == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 240, "" + CombatTaskList.WASNT_EVEN_CLOSE.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 211, "" + CombatTaskList.WASNT_EVEN_CLOSE.description());//Description
        }
        if (player.wasntevenClose >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 240, "<col=0fd60f>" + CombatTaskList.WASNT_EVEN_CLOSE.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 211, "" + CombatTaskList.WASNT_EVEN_CLOSE.description());//Description
        }
        if (player.noluckRequired == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 241, "" + CombatTaskList.NO_LUCK_REQUIRED.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 212, "" + CombatTaskList.NO_LUCK_REQUIRED.description());//Description
        }
        if (player.noluckRequired >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 241, "<col=0fd60f>" + CombatTaskList.NO_LUCK_REQUIRED.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 212, "" + CombatTaskList.NO_LUCK_REQUIRED.description());//Description
        }
        if (player.playingwithJads == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 242, "" + CombatTaskList.PLAYING_WITH_JADS.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 213, "" + CombatTaskList.PLAYING_WITH_JADS.description());//Description
        }
        if (player.playingwithJads >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 242, "<col=0fd60f>" + CombatTaskList.PLAYING_WITH_JADS.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 213, "" + CombatTaskList.PLAYING_WITH_JADS.description());//Description
        }
        if (player.thefloorisLava == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 243, "" + CombatTaskList.THE_FLOOR_IS_LAVA.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 214, "" + CombatTaskList.THE_FLOOR_IS_LAVA.description());//Description
        }
        if (player.thefloorisLava >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 243, "<col=0fd60f>" + CombatTaskList.THE_FLOOR_IS_LAVA.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 214, "" + CombatTaskList.THE_FLOOR_IS_LAVA.description());//Description
        }
        if (player.infernoGrandmaster == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 244, "" + CombatTaskList.INFERNO_GRANDMASTER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 215, "" + CombatTaskList.INFERNO_GRANDMASTER.description());//Description
        }
        if (player.infernoGrandmaster > 0 && player.infernoGrandmaster < 5) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 244, "<col=ffff00>" + CombatTaskList.INFERNO_GRANDMASTER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 215, "" + CombatTaskList.INFERNO_GRANDMASTER.description());//Description
        }
        if (player.infernoGrandmaster >= 5) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 244, "<col=0fd60f>" + CombatTaskList.INFERNO_GRANDMASTER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 215, "" + CombatTaskList.INFERNO_GRANDMASTER.description());//Description
        }
        if (player.ashCollector == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 245, "" + CombatTaskList.ASH_COLLECTOR.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 216, "" + CombatTaskList.ASH_COLLECTOR.description());//Description
        }
        if (player.ashCollector > 0 && player.ashCollector < 20) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 245, "<col=ffff00>" + CombatTaskList.ASH_COLLECTOR.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 216, "" + CombatTaskList.ASH_COLLECTOR.description());//Description
        }
        if (player.ashCollector >= 20) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 245, "<col=0fd60f>" + CombatTaskList.ASH_COLLECTOR.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 216, "" + CombatTaskList.ASH_COLLECTOR.description());//Description
        }
        if (player.demonWhisperer == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 246, "" + CombatTaskList.DEMON_WHISPERER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 217, "" + CombatTaskList.DEMON_WHISPERER.description());//Description
        }
        if (player.demonWhisperer >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 246, "<col=0fd60f>" + CombatTaskList.DEMON_WHISPERER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 217, "" + CombatTaskList.DEMON_WHISPERER.description());//Description
        }
        if (player.theworstrangedWeapon == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 247, "" + CombatTaskList.THE_WORST_RANGED_WEAPON.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 218, "" + CombatTaskList.THE_WORST_RANGED_WEAPON.description());//Description
        }
        if (player.theworstrangedWeapon >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 247, "<col=0fd60f>" + CombatTaskList.THE_WORST_RANGED_WEAPON.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 218, "" + CombatTaskList.THE_WORST_RANGED_WEAPON.description());//Description
        }
        if (player.itwasntaFluke == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 248, "" + CombatTaskList.IT_WASNT_A_FLUKE.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 292, "" + CombatTaskList.IT_WASNT_A_FLUKE.description());//Description
        }
        if (player.itwasntaFluke >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 248, "<col=0fd60f>" + CombatTaskList.IT_WASNT_A_FLUKE.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 292, "" + CombatTaskList.IT_WASNT_A_FLUKE.description());//Description
        }
        if (player.tzhaarketraksspeedRunner == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 304, "" + CombatTaskList.TZHAAR_KET_RAKS_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 293, "" + CombatTaskList.TZHAAR_KET_RAKS_SPEED_RUNNER.description());//Description
        }
        if (player.tzhaarketraksspeedRunner >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 304, "<col=0fd60f>" + CombatTaskList.TZHAAR_KET_RAKS_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 293, "" + CombatTaskList.TZHAAR_KET_RAKS_SPEED_RUNNER.description());//Description
        }
        if (player.thevijadChallenge == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 305, "" + CombatTaskList.THE_VI_JAD_CHALLENGE.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 294, "" + CombatTaskList.THE_VI_JAD_CHALLENGE.description());//Description
        }
        if (player.thevijadChallenge >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 305, "<col=0fd60f>" + CombatTaskList.THE_VI_JAD_CHALLENGE.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 294, "" + CombatTaskList.THE_VI_JAD_CHALLENGE.description());//Description
        }
        if (player.vorkathspeedRunner == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 306, "" + CombatTaskList.VORKATH_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 295, "" + CombatTaskList.VORKATH_SPEED_RUNNER.description());//Description
        }
        if (player.vorkathspeedRunner >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 306, "<col=0fd60f>" + CombatTaskList.VORKATH_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 295, "" + CombatTaskList.VORKATH_SPEED_RUNNER.description());//Description
        }
        if (player.thefremennikWay == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 307, "" + CombatTaskList.THE_FREMENNIK_WAY.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 296, "" + CombatTaskList.THE_FREMENNIK_WAY.description());//Description
        }
        if (player.thefremennikWay >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 307, "<col=0fd60f>" + CombatTaskList.THE_FREMENNIK_WAY.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 296, "" + CombatTaskList.THE_FREMENNIK_WAY.description());//Description
        }
        if (player.faithlessEncounter == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 308, "" + CombatTaskList.FAITHLESS_ENCOUNTER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 297, "" + CombatTaskList.FAITHLESS_ENCOUNTER.description());//Description
        }
        if (player.faithlessEncounter >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 308, "<col=0fd60f>" + CombatTaskList.FAITHLESS_ENCOUNTER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 297, "" + CombatTaskList.FAITHLESS_ENCOUNTER.description());//Description
        }
        if (player.zulrahspeedRunner == 0) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 309, "" + CombatTaskList.ZULRAH_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 298, "" + CombatTaskList.ZULRAH_SPEED_RUNNER.description());//Description
        }
        if (player.zulrahspeedRunner >= 1) {
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 309, "<col=0fd60f>" + CombatTaskList.ZULRAH_SPEED_RUNNER.title());//Title
            player.getPacketSender().sendString(GRANDMASTER_INTERFACE, 298, "" + CombatTaskList.ZULRAH_SPEED_RUNNER.description());//Description
        }
    }


}