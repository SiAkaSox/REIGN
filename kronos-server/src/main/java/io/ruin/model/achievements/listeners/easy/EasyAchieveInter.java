package io.ruin.model.achievements.listeners.easy;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.item.Item;

import static io.ruin.model.achievements.Achievement.*;
import static io.ruin.model.achievements.Achievement.THE_WILDERNESS_EXPANSION;

public class EasyAchieveInter {

    public static int ACHIEVEMENTS_TAB = 858;
    public static int EASY_INTER = 900;

    public static void clearIntTitles(Player p) {
        p.openInterface(InterfaceType.MAIN, EASY_INTER);
        p.getPacketSender().sendString(EASY_INTER, 278, "Select an achievement");
        p.getPacketSender().sendString(EASY_INTER, 336, "");
        p.getPacketSender().sendString(EASY_INTER, 279, "");
        p.getPacketSender().sendItems(EASY_INTER, 370, 0, new Item(-1));
        p.getPacketSender().sendItems(EASY_INTER, 374, 0, new Item(-1));
        p.getPacketSender().sendItems(EASY_INTER, 368, 0, new Item(-1));
    }
    public static void clearTitles(Player p) {
        p.openInterface(InterfaceType.MAIN, EASY_INTER);
        p.getPacketSender().sendString(EASY_INTER, 88, ""+ SLAYER_BEGINNER.getListener().name());
        p.getPacketSender().sendString(EASY_INTER, 89, ""+ THE_TRADING_POST.getListener().name());
        p.getPacketSender().sendString(EASY_INTER, 90, ""+ INTRO_TO_ENCHANTING.getListener().name());
        p.getPacketSender().sendString(EASY_INTER, 170, ""+ GETTING_LEARNT.getListener().name());
        p.getPacketSender().sendString(EASY_INTER, 171, ""+ IMBUED_KNOWLEDGE.getListener().name());
        p.getPacketSender().sendString(EASY_INTER, 172, ""+ TELEPORTER.getListener().name());
        p.getPacketSender().sendString(EASY_INTER, 177, ""+ INTRO_TO_PERKS.getListener().name());
        p.getPacketSender().sendString(EASY_INTER, 178, ""+ THE_WILDERNESS_EXPANSION.getListener().name());
        p.getPacketSender().sendString(EASY_INTER, 179, ""+ LOOMING_THREAT.getListener().name());
    }
    public static void sendInfo(Player p, String info) {
        p.getPacketSender().sendString(EASY_INTER, 336, ""+info);
    }
    public static void sendDirective(Player p, String info) {
        p.getPacketSender().sendString(EASY_INTER, 279, ""+info);

    }
    public static void sendTitle(Player p, String name, int id) {
        p.getPacketSender().sendString(EASY_INTER, id,"<col=ffffff>"+ name);
        p.getPacketSender().sendString(EASY_INTER, 278, ""+ name);
    }
    public static void sendRewards(Player p, int itemOne, int itemOneAmt, int itemTwo, int itemTwoAmt, int itemThree, int itemThreeAmt) {
        p.getPacketSender().sendItems(EASY_INTER, 370, 0, new Item(itemOne, itemOneAmt));
        p.getPacketSender().sendItems(EASY_INTER, 374, 0, new Item(itemTwo, itemTwoAmt));
        p.getPacketSender().sendItems(EASY_INTER, 368, 0, new Item(itemThree, itemThreeAmt));
    }

    public static void loadEasyAchievements(Player p) {
        clearIntTitles(p);
        clearTitles(p);
        if (!SLAYER_BEGINNER.isFinished(p)) {
            p.getPacketSender().setHidden(EASY_INTER, 375, true);
        } else {
            p.getPacketSender().setHidden(EASY_INTER, 315, true);
        }
        if (!THE_TRADING_POST.isFinished(p)) {
            p.getPacketSender().setHidden(EASY_INTER, 376, true);
        } else {
            p.getPacketSender().setHidden(EASY_INTER, 316, true);
        }
        if (!INTRO_TO_ENCHANTING.isFinished(p)) {
            p.getPacketSender().setHidden(EASY_INTER, 377, true);
        } else {
            p.getPacketSender().setHidden(EASY_INTER, 317, true);
        }
        if (!GETTING_LEARNT.isFinished(p)) {
            p.getPacketSender().setHidden(EASY_INTER, 378, true);
        } else {
            p.getPacketSender().setHidden(EASY_INTER, 318, true);
        }
        if (!IMBUED_KNOWLEDGE.isFinished(p)) {
            p.getPacketSender().setHidden(EASY_INTER, 379, true);
        } else {
            p.getPacketSender().setHidden(EASY_INTER, 319, true);
        }
        if (!TELEPORTER.isFinished(p)) {
            p.getPacketSender().setHidden(EASY_INTER, 380, true);
        } else {
            p.getPacketSender().setHidden(EASY_INTER, 320, true);
        }
        if (!INTRO_TO_PERKS.isFinished(p)) {
            p.getPacketSender().setHidden(EASY_INTER, 381, true);
        } else {
            p.getPacketSender().setHidden(EASY_INTER, 321, true);
        }
        if (!THE_WILDERNESS_EXPANSION.isFinished(p)) {
            p.getPacketSender().setHidden(EASY_INTER, 382, true);
        } else {
            p.getPacketSender().setHidden(EASY_INTER, 322, true);
        }
        if (!LOOMING_THREAT.isFinished(p)) {
            p.getPacketSender().setHidden(EASY_INTER, 384, true);
        } else {
            p.getPacketSender().setHidden(EASY_INTER, 383, true);
        }
    }

    public static void sendTrackingArrow(Player p, int x, int y) {
        p.startEvent(e -> {
            p.getPacketSender().sendHintIcon(x, y);
            e.delay(15);
            p.getPacketSender().resetHintIcon(false);
        });
    }

    public static void selectSlayerBeginner(Player p) {
        clearTitles(p);
        sendTitle(p, ""+SLAYER_BEGINNER.getListener().name(), 88);
        sendInfo(p, "Completing Slayer tasks is a great way to quickly train your stats and start building your bank up!");
        sendDirective(p, "Complete a Slayer task.");
        sendRewards(p, 995, 25000, -1, -1, -1, -1);
        sendTrackingArrow(p, 3083, 3486);
    }
    public static void selectTheTradingPost(Player p) {
        clearTitles(p);
        sendTitle(p, ""+THE_TRADING_POST.getListener().name(), 89);
        sendInfo(p, "The Trading Post, located at the center of Edgeville, allows you to buy and sell any items you may need!");
        sendDirective(p, "Open the Trading Post, located in the center of Edgeville.");
        sendRewards(p, 995, 10000, -1, -1, -1, -1);
        sendTrackingArrow(p, 3080, 3500);
    }
    public static void selectIntroToEnchanting(Player p) {
        clearTitles(p);
        sendTitle(p, ""+INTRO_TO_ENCHANTING.getListener().name(), 90);
        sendInfo(p, "Enchanting items and farming enchanting materials are a great way to get BiS items and earn tons of money!");
        sendDirective(p, "Open the Altar of Enchanting east of the Trading Post in Edgeville.");
        sendRewards(p, 995, 10000, -1, -1, -1, -1);
        sendTrackingArrow(p, 3112, 3503);
    }
    public static void selectGettingLearnt(Player p) {
        clearTitles(p);
        sendTitle(p, ""+GETTING_LEARNT.getListener().name(), 170);
        sendInfo(p, "Arenas and Battlegrounds are the main source of earning Conquest Points in OSPK. The Arena Master knows everything about this!");
        sendDirective(p, "Speak to the Arena Master near the Tournament Portal in Edgeville.");
        sendRewards(p, 995, 10000, -1, -1, -1, -1);
        sendTrackingArrow(p, 3098, 3514);
    }
    public static void selectImbuedKnowledge(Player p) {
        clearTitles(p);
        sendTitle(p, ""+IMBUED_KNOWLEDGE.getListener().name(), 171);
        sendInfo(p, "Imbueing items makes them stronger and ensures you will not lose them upon your inevitable death!");
        sendDirective(p, "Open the Lectern of Imbuement in Edgeville, in the building just east of the Trading Post.");
        sendRewards(p, 995, 10000, -1, -1, -1, -1);
        sendTrackingArrow(p, 3095, 3511);
    }
    public static void selectTeleporter(Player p) {
        clearTitles(p);
        sendTitle(p, ""+TELEPORTER.getListener().name(), 172);
        sendInfo(p, "The OSPK teleporter is located directly at the center of Edgeville, making it conveniant to travel anywhere!");
        sendDirective(p, "Use the teleporter, located at the center of Edgeville.");
        sendRewards(p, 995, 10000, -1, -1, -1, -1);
        sendTrackingArrow(p, 3094, 3501);
    }
    public static void selectintrotoPerks(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ INTRO_TO_PERKS.getListener().name(), 177);
        sendInfo(p, "Perks are permenant account perks that you can purchase with points earned by sacrificing unwanted items to the Fire of Sacrifice!");
        sendDirective(p, "Use the Fire of Sacrifice outside the shops building to view the Perks list.");
        sendRewards(p, 995, 10000, -1, -1, -1, -1);
        sendTrackingArrow(p, 3083, 3509);
    }
    public static void selectWildExpansion(Player p) {
        clearTitles(p);
        sendTitle(p, ""+THE_WILDERNESS_EXPANSION.getListener().name(), 178);
        sendInfo(p, "The OSPK Wilderness Expansion offers a vast amount of new wilderness content and gives a new meaning to 'deep wilderness'!");
        sendDirective(p, "Teleport to the Wilderness Expansion Bank.");
        sendRewards(p, 995, 10000, -1, -1, -1, -1);
        sendTrackingArrow(p, 3094, 3501);
    }
    public static void selectLoomingThreat(Player p) {
        clearTitles(p);
        sendTitle(p, ""+LOOMING_THREAT.getListener().name(), 179);
        sendInfo(p, "A group of goblins and bandits are planning an attack on OSPK in the souther part of home! You must thwart their plan immediately!");
        sendDirective(p, "Kill 20 goblins or bandits in total");
        sendRewards(p, 995, 15000, -1, -1, -1, -1);
        sendTrackingArrow(p, 3083, 3469);
    }
    public static void updateEasyBar(Player p) {
        if (p.introAchievementsCompleted == 0) {
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 236, "<col=d22222>"+p.introAchievementsCompleted+"/9");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 235, "<col=d22222>Easy");
        }
        if (p.introAchievementsCompleted == 1) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 241, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 236, "<col=ffff33>"+p.introAchievementsCompleted+"/9");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 235, "<col=ffff33>Easy");
        }
        if (p.introAchievementsCompleted == 2) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 241, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 348, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 236, "<col=ffff33>"+p.introAchievementsCompleted+"/9");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 235, "<col=ffff33>Easy");
        }
        if (p.introAchievementsCompleted == 3) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 241, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 348, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 349, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 236, "<col=ffff33>"+p.introAchievementsCompleted+"/9");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 235, "<col=ffff33>Easy");
        }
        if (p.introAchievementsCompleted == 4) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 241, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 348, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 349, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 350, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 236, "<col=ffff33>"+p.introAchievementsCompleted+"/9");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 235, "<col=ffff33>Easy");
        }
        if (p.introAchievementsCompleted == 5) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 241, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 348, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 349, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 350, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 351, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 236, "<col=ffff33>"+p.introAchievementsCompleted+"/9");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 235, "<col=ffff33>Easy");
        }
        if (p.introAchievementsCompleted == 6) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 241, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 348, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 349, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 350, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 351, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 352, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 236, "<col=ffff33>"+p.introAchievementsCompleted+"/9");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 235, "<col=ffff33>Easy");
        }
        if (p.introAchievementsCompleted == 7) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 241, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 348, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 349, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 350, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 351, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 352, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 353, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 236, "<col=ffff33>"+p.introAchievementsCompleted+"/9");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 235, "<col=ffff33>Easy");
        }
        if (p.introAchievementsCompleted == 8) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 241, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 348, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 349, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 350, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 351, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 352, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 353, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 354, false);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 236, "<col=ffff33>"+p.introAchievementsCompleted+"/9");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 235, "<col=ffff33>Easy");
        }
        if (p.introAchievementsCompleted > 8) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 241, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 348, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 349, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 350, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 351, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 352, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 353, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 354, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 236, "<col=2eff00>9/9");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 235, "<col=2eff00>Easy");
        }
    }
}