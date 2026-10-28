package io.ruin.model.achievements.listeners.master;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.item.Item;

import static io.ruin.model.achievements.Achievement.*;

public class MasterAchieveInter {

    public static int ACHIEVEMENTS_TAB = 858;
    public static int MASTER_INTER = 904;

    public static void clearIntTitles(Player p) {
        p.openInterface(InterfaceType.MAIN, MASTER_INTER);
        p.getPacketSender().sendString(MASTER_INTER, 278, "Select an achievement");
        p.getPacketSender().sendString(MASTER_INTER, 336, "");
        p.getPacketSender().sendString(MASTER_INTER, 279, "");
        p.getPacketSender().sendItems(MASTER_INTER, 368, 0, new Item(-1));
        p.getPacketSender().sendItems(MASTER_INTER, 370, 0, new Item(-1));
        p.getPacketSender().sendItems(MASTER_INTER, 374, 0, new Item(-1));
    }
    public static void clearTitles(Player p) {
        p.getPacketSender().sendString(MASTER_INTER, 88, ""+ SLAYER_MASTER.getListener().name());
        p.getPacketSender().sendString(MASTER_INTER, 89, ""+ YOURE_ON_YOUR_OLM.getListener().name());
        p.getPacketSender().sendString(MASTER_INTER, 90, ""+ BIG_OL_FLEX.getListener().name());
        p.getPacketSender().sendString(MASTER_INTER, 170, ""+ GLADIATOR.getListener().name());
        p.getPacketSender().sendString(MASTER_INTER, 171, ""+ PURPLE_PLS.getListener().name());
        p.getPacketSender().sendString(MASTER_INTER, 172, ""+ CATCH_ME_IF_YOU_CAN.getListener().name());
        p.getPacketSender().sendString(MASTER_INTER, 177, ""+ GODBLESSED.getListener().name());
        p.getPacketSender().sendString(MASTER_INTER, 178, ""+ CHICKEN_KILLER.getListener().name());
    }
    public static void sendInfo(Player p, String info) {
        p.getPacketSender().sendString(MASTER_INTER, 336, ""+ info);
    }
    public static void sendDirective(Player p, String info) {
        p.getPacketSender().sendString(MASTER_INTER, 279, ""+ info);
    }
    public static void sendTitle(Player p, String name, int id) {
        p.getPacketSender().sendString(MASTER_INTER, id, "<col=ffffff>"+ name);
        p.getPacketSender().sendString(MASTER_INTER, 278, ""+ name);
    }
    public static void sendRewards(Player p, int itemOne, int itemOneAmt, int itemTwo, int itemTwoAmt, int itemThree, int itemThreeAmt) {
        p.getPacketSender().sendItems(MASTER_INTER, 368, 0, new Item(itemOne, itemOneAmt));
        p.getPacketSender().sendItems(MASTER_INTER, 370, 0, new Item(itemTwo, itemTwoAmt));
        p.getPacketSender().sendItems(MASTER_INTER, 374, 0, new Item(itemThree, itemThreeAmt));
    }

    public static void loadMasterAchievements(Player p) {
        clearIntTitles(p);
        clearTitles(p);
        if (!SLAYER_MASTER.isFinished(p)) {
            p.getPacketSender().setHidden(MASTER_INTER, 375, true);
        } else {
            p.getPacketSender().setHidden(MASTER_INTER, 315, true);
        }
        if (!YOURE_ON_YOUR_OLM.isFinished(p)) {
            p.getPacketSender().setHidden(MASTER_INTER, 376, true);
        } else {
            p.getPacketSender().setHidden(MASTER_INTER, 316, true);
        }
        if (!BIG_OL_FLEX.isFinished(p)) {
            p.getPacketSender().setHidden(MASTER_INTER, 377, true);
        } else {
            p.getPacketSender().setHidden(MASTER_INTER, 317, true);
        }
        if (!GLADIATOR.isFinished(p)) {
            p.getPacketSender().setHidden(MASTER_INTER, 378, true);
        } else {
            p.getPacketSender().setHidden(MASTER_INTER, 318, true);
        }
        if (!PURPLE_PLS.isFinished(p)) {
            p.getPacketSender().setHidden(MASTER_INTER, 379, true);
        } else {
            p.getPacketSender().setHidden(MASTER_INTER, 319, true);
        }
        if (!CATCH_ME_IF_YOU_CAN.isFinished(p)) {
            p.getPacketSender().setHidden(MASTER_INTER, 380, true);
        } else {
            p.getPacketSender().setHidden(MASTER_INTER, 320, true);
        }
        if (!GODBLESSED.isFinished(p)) {
            p.getPacketSender().setHidden(MASTER_INTER, 381, true);
        } else {
            p.getPacketSender().setHidden(MASTER_INTER, 321, true);
        }
        if (!CHICKEN_KILLER.isFinished(p)) {
            p.getPacketSender().setHidden(MASTER_INTER, 382, true);
        } else {
            p.getPacketSender().setHidden(MASTER_INTER, 322, true);
        }
    }

    public static void selectSlayerMaster(Player p) {
        clearTitles(p);
        sendTitle(p, ""+SLAYER_MASTER.getListener().name(), 88);
        sendInfo(p, "Your final slayer challenge. Become a master in the Slayer skill by achieving the highest level.");
        sendDirective(p, "Achieve level 99 Slayer.");
        sendRewards(p, 23071, 5, 995, 500000, 2528, 2);
    }
    public static void selectYoureOnYourOlm(Player p) {
        clearTitles(p);
        sendTitle(p, ""+YOURE_ON_YOUR_OLM.getListener().name(), 89);
        sendInfo(p, "Prove your skill by completing the Chamber of Xeric, alone.");
        sendDirective(p, "Complete the Chamber of Xeric solo.");
        sendRewards(p, 11158, 1, 995, 500000, -1, -1);
    }
    public static void selectBigOlFlex(Player p) {
        clearTitles(p);
        sendTitle(p, ""+BIG_OL_FLEX.getListener().name(), 90);
        sendInfo(p, "The best of the best. The master of all skills. Can you achieve that 2277 total?");
        sendDirective(p, "Achieve level 99 in all skills.");
        sendRewards(p, 608, 1, 995, 500000, 13222, 1);
    }
    public static void selectGladiator(Player p) {
        clearTitles(p);
        sendTitle(p, ""+GLADIATOR.getListener().name(), 170);
        sendInfo(p, "Becoming a Gladiator is one of the highest accomplishments on OSPK. Only dedicated players will earn this achievement!");
        sendDirective(p, "Achieve 5,000 PvP Rating before the end of the Season.");
        sendRewards(p, 25648, 1, 995, 500000, -1, -1);
    }
    public static void selectPurplePls(Player p) {
        clearTitles(p);
        sendTitle(p, ""+PURPLE_PLS.getListener().name(), 171);
        sendInfo(p, "Test your luck in Tob and receive a rare from a purple chest! Can be completed solo, or on a team.");
        sendDirective(p, "Receive a purple chest in the Theatre of Blood.");
        sendRewards(p, 11158, 1, 995, 500000, -1, -1);
    }
    public static void selectCatchMeIfYouCan(Player p) {
        clearTitles(p);
        sendTitle(p, ""+CATCH_ME_IF_YOU_CAN.getListener().name(), 172);
        sendInfo(p, "A race to the finish line! Capture the Flag in the wilderness and return it to the Arena Master in the PvP Hall.");
        sendDirective(p, "Win the Capture The Flag world event.");
        sendRewards(p, -1, -1, 995, 250000, -1, -1);
    }
    public static void selectGodblessed(Player p) {
        clearTitles(p);
        sendTitle(p, ""+GODBLESSED.getListener().name(), 177);
        sendInfo(p, "Otto Godblessed, located in the skilling building south of the Trading Post in Edgeville, can convert a Zamorakian Spear into a Hasta, for a fee.");
        sendDirective(p, "Have Otto Godblessed create you a Zamorakian Hasta.");
        sendRewards(p, -1, -1, 2528, 1, -1, -1);
    }
    public static void selectChickenKiller(Player p) {
        clearTitles(p);
        sendTitle(p, ""+CHICKEN_KILLER.getListener().name(), 178);
        sendInfo(p, "Known for dropping sigils to the best shields available, Corporeal Beast is surely a challenge for you to do alone!");
        sendDirective(p, "Kill the Corporeal Beast solo.");
        sendRewards(p, 11158, 1, 995, 500000, -1, -1);
    }

    public static void updateMasterBar(Player p) {
        if (p.masterAchievementsCompleted == 0) {
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 324, "<col=#d22222>"+p.masterAchievementsCompleted+"/8");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 323, "<col=#d22222>Master");
        }
        if (p.masterAchievementsCompleted == 1) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 329, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 324, "<col=ffff33>"+p.masterAchievementsCompleted+"/8");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 323, "<col=ffff33>Master");
        }
        if (p.masterAchievementsCompleted == 2) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 329, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 400, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 324, "<col=ffff33>"+p.masterAchievementsCompleted+"/8");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 323, "<col=ffff33>Master");
        }
        if (p.masterAchievementsCompleted == 3) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 329, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 400, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 401, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 324, "<col=ffff33>"+p.masterAchievementsCompleted+"/8");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 323, "<col=ffff33>Master");
        }
        if (p.masterAchievementsCompleted == 4) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 329, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 400, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 401, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 402, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 324, "<col=ffff33>"+p.masterAchievementsCompleted+"/8");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 323, "<col=ffff33>Master");
        }
        if (p.masterAchievementsCompleted == 5) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 329, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 400, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 401, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 402, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 403, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 324, "<col=ffff33>"+p.masterAchievementsCompleted+"/8");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 323, "<col=ffff33>Master");
        }
        if (p.masterAchievementsCompleted == 6) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 329, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 400, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 401, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 402, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 403, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 404, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 324, "<col=ffff33>"+p.masterAchievementsCompleted+"/8");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 323, "<col=ffff33>Master");
        }
        if (p.masterAchievementsCompleted == 7) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 329, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 400, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 401, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 402, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 403, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 404, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 405, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 324, "<col=ffff33>"+p.masterAchievementsCompleted+"/8");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 323, "<col=ffff33>Master");
        }
        if (p.masterAchievementsCompleted == 8) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 329, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 400, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 401, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 402, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 403, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 404, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 405, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 406, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 324, "<col=ffff33>"+p.masterAchievementsCompleted+"/8");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 323, "<col=ffff33>Master");
        }
    }

}