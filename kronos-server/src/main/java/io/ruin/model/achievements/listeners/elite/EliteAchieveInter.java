package io.ruin.model.achievements.listeners.elite;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.item.Item;

import static io.ruin.model.achievements.Achievement.*;

public class EliteAchieveInter {

    public static int ACHIEVEMENTS_TAB = 858;
    public static int ELITE_INTER = 903;

    public static void clearIntTitles(Player p) {
        p.openInterface(InterfaceType.MAIN, ELITE_INTER);
        p.getPacketSender().sendString(ELITE_INTER, 278, "Select an achievement");
        p.getPacketSender().sendString(ELITE_INTER, 336, "");
        p.getPacketSender().sendString(ELITE_INTER, 279, "");
        p.getPacketSender().sendItems(ELITE_INTER, 370, 0, new Item(-1));
        p.getPacketSender().sendItems(ELITE_INTER, 374, 0, new Item(-1));
        p.getPacketSender().sendItems(ELITE_INTER, 368, 0, new Item(-1));
    }
    public static void clearTitles(Player p) {
        p.openInterface(InterfaceType.MAIN, ELITE_INTER);
        p.getPacketSender().sendString(ELITE_INTER, 88, ""+ RATING.getListener().name());
        p.getPacketSender().sendString(ELITE_INTER, 89, ""+ THE_LAVA_CAPE_II.getListener().name());
        p.getPacketSender().sendString(ELITE_INTER, 90, ""+ CHAMBER_NOVICE.getListener().name());
        p.getPacketSender().sendString(ELITE_INTER, 170, ""+ HAIL_HYDRA.getListener().name());
        p.getPacketSender().sendString(ELITE_INTER, 171, ""+ MASTER_TREASURE_FINDER.getListener().name());
        p.getPacketSender().sendString(ELITE_INTER, 172, ""+ TOURNY_KING.getListener().name());
        p.getPacketSender().sendString(ELITE_INTER, 177, ""+ THE_VAULT.getListener().name());
        p.getPacketSender().sendString(ELITE_INTER, 178, ""+ NO_ASSEMBLY_REQUIRED.getListener().name());
        p.getPacketSender().sendString(ELITE_INTER, 179, ""+ NOT_JUST_A_FRAGMENT.getListener().name());
        p.getPacketSender().sendString(ELITE_INTER, 184, ""+ FORT_HOLDER.getListener().name());
        p.getPacketSender().sendString(ELITE_INTER, 185, ""+ LIKE_A_BOSS.getListener().name());
        p.getPacketSender().sendString(ELITE_INTER, 186, ""+ TAKE_ME_TO_THE_TOP.getListener().name());
    }
    public static void sendInfo(Player p, String info) {
        p.getPacketSender().sendString(ELITE_INTER, 336, ""+ info);
    }
    public static void sendDirective(Player p, String info) {
        p.getPacketSender().sendString(ELITE_INTER, 279, ""+ info);
    }
    public static void sendTitle(Player p, String name, int id) {
        p.getPacketSender().sendString(ELITE_INTER, id, "<col=ffffff>"+ name);
        p.getPacketSender().sendString(ELITE_INTER, 278, ""+ name);
    }
    public static void sendRewards(Player p, int itemOne, int itemOneAmt, int itemTwo, int itemTwoAmt, int itemThree, int itemThreeAmt) {
        p.getPacketSender().sendItems(ELITE_INTER, 368, 0, new Item(itemOne, itemOneAmt));
        p.getPacketSender().sendItems(ELITE_INTER, 370, 0, new Item(itemTwo, itemTwoAmt));
        p.getPacketSender().sendItems(ELITE_INTER, 374, 0, new Item(itemThree, itemThreeAmt));
    }

    public static void loadEliteAchievements(Player p) {
        clearIntTitles(p);
        clearTitles(p);
        if (!RATING.isFinished(p)) {
            p.getPacketSender().setHidden(ELITE_INTER, 375, true);
        } else {
            p.getPacketSender().setHidden(ELITE_INTER, 315, true);
        }
        if (!THE_LAVA_CAPE_II.isFinished(p)) {
            p.getPacketSender().setHidden(ELITE_INTER, 376, true);
        } else {
            p.getPacketSender().setHidden(ELITE_INTER, 316, true);
        }
        if (!CHAMBER_NOVICE.isFinished(p)) {
            p.getPacketSender().setHidden(ELITE_INTER, 377, true);
        } else {
            p.getPacketSender().setHidden(ELITE_INTER, 317, true);
        }
        if (!HAIL_HYDRA.isFinished(p)) {
            p.getPacketSender().setHidden(ELITE_INTER, 378, true);
        } else {
            p.getPacketSender().setHidden(ELITE_INTER, 318, true);
        }
        if (!MASTER_TREASURE_FINDER.isFinished(p)) {
            p.getPacketSender().setHidden(ELITE_INTER, 379, true);
        } else {
            p.getPacketSender().setHidden(ELITE_INTER, 319, true);
        }
        if (!TOURNY_KING.isFinished(p)) {
            p.getPacketSender().setHidden(ELITE_INTER, 380, true);
        } else {
            p.getPacketSender().setHidden(ELITE_INTER, 320, true);
        }
        if (!THE_VAULT.isFinished(p)) {
            p.getPacketSender().setHidden(ELITE_INTER, 381, true);
        } else {
            p.getPacketSender().setHidden(ELITE_INTER, 321, true);
        }
        if (!NO_ASSEMBLY_REQUIRED.isFinished(p)) {
            p.getPacketSender().setHidden(ELITE_INTER, 382, true);
        } else {
            p.getPacketSender().setHidden(ELITE_INTER, 322, true);
        }
        if (!NOT_JUST_A_FRAGMENT.isFinished(p)) {
            p.getPacketSender().setHidden(ELITE_INTER, 388, true);
        } else {
            p.getPacketSender().setHidden(ELITE_INTER, 383, true);
        }
        if (!FORT_HOLDER.isFinished(p)) {
            p.getPacketSender().setHidden(ELITE_INTER, 389, true);
        } else {
            p.getPacketSender().setHidden(ELITE_INTER, 384, true);
        }
        if (!LIKE_A_BOSS.isFinished(p)) {
            p.getPacketSender().setHidden(ELITE_INTER, 390, true);
        } else {
            p.getPacketSender().setHidden(ELITE_INTER, 385, true);
        }
        if (!TAKE_ME_TO_THE_TOP.isFinished(p)) {
            p.getPacketSender().setHidden(ELITE_INTER, 391, true);
        } else {
            p.getPacketSender().setHidden(ELITE_INTER, 386, true);
        }
    }

    public static void selectRating(Player p) {
        clearTitles(p);
        sendTitle(p, ""+RATING.getListener().name(), 88);
        sendInfo(p, "PvP Rating is earned through Arenas/Battlegrounds and normal pvp. At the end of each season, players with 5,000 Rating will earn the seasonal rewards!");
        sendDirective(p, "Earn a minimum of 1,500 PvP Rating.");
        sendRewards(p, 25646, 1, 995, 250000, 13307, 500);
    }
    public static void selectTheLavaCapeII(Player p) {
        clearTitles(p);
        sendTitle(p, ""+THE_LAVA_CAPE_II.getListener().name(), 89);
        sendInfo(p, "You got the fire cape. But can you get the Infernal Cape? Only the best of the best will be able to earn their stripes here.");
        sendDirective(p, "Complete The Inferno.");
        sendRewards(p, 23071, 3, 995, 250000, 608, 1);
    }
    public static void selectChamberNovice(Player p) {
        clearTitles(p);
        sendTitle(p, ""+CHAMBER_NOVICE.getListener().name(), 90);
        sendInfo(p, "The Chamber of Xeric raid is one of the top PvM challenges in the game, but also one of the most rewarding! (can complete with a team, or solo)");
        sendDirective(p, "Complete the Chamber of Xeric.");
        sendRewards(p, 13442, 150, 995, 250000, 12696, 25);
    }
    public static void selectHailHydra(Player p) {
        clearTitles(p);
        sendTitle(p, ""+HAIL_HYDRA.getListener().name(), 170);
        sendInfo(p, "The Alchemical Hydra is one of the final challenges for those pursing 99 Slayer and Ferocious Gloves!");
        sendDirective(p, "Kill the Alchemical Hydra 25 times.");
        sendRewards(p, 23071, 1, 995, 250000, 12696, 25);
    }
    public static void selectMasterTreasureFinder(Player p) {
        clearTitles(p);
        sendTitle(p, ""+MASTER_TREASURE_FINDER.getListener().name(), 171);
        sendInfo(p, "Players can trade one of each clue type to Watson, located in the Edgeville bank, to receive a Master Clue Scroll.");
        sendDirective(p, "Complete a Master Clue Scroll.");
        sendRewards(p, -1, -1, 995, 150000, -1, -1);
    }
    public static void selectTournyKing(Player p) {
        clearTitles(p);
        sendTitle(p, ""+TOURNY_KING.getListener().name(), 172);
        sendInfo(p, "Competing in a Tournament doesn't make you a king, but winning one sure does!");
        sendDirective(p, "Win a Tournament.");
        sendRewards(p, 11158, 1, 995, 250000, 13307, 100);
    }
    public static void selectTheVault(Player p) {
        clearTitles(p);
        sendTitle(p, ""+THE_VAULT.getListener().name(), 177);
        sendInfo(p, "The Great Vault rewards players who have received Great Vault keys from various world-event activities throughout OSPK!");
        sendDirective(p, "Open The Great Vault once, located in the PvP Hall in Edgeville.");
        sendRewards(p, -1, -1, 995, 150000, -1, -1);
    }
    public static void selectNoAssemblyRequired(Player p) {
        clearTitles(p);
        sendTitle(p, ""+NO_ASSEMBLY_REQUIRED.getListener().name(), 178);
        sendInfo(p, "Players can use a Vorkath's Head on an Avas Accumulator to receive the Ava's Assembler. This does, however, require you to kill Vorkath and retreive his head!");
        sendDirective(p, "Create an Ava's Assembler.");
        sendRewards(p, -1, -1, 995, 250000, -1, -1);
    }
    public static void selectNotJustAFragment(Player p) {
        clearTitles(p);
        sendTitle(p, ""+NOT_JUST_A_FRAGMENT.getListener().name(), 179);
        sendInfo(p, "The Fragment of Seren is a powerful world boss event that can be very dangerous if you miss a mechanic or two!");
        sendDirective(p, "Kill the Fragment of Seren 25 times.");
        sendRewards(p, 23071, 1, 995, 250000, 30276, 1);
    }
    public static void selectFortHolder(Player p) {
        clearTitles(p);
        sendTitle(p, ""+FORT_HOLDER.getListener().name(), 184);
        sendInfo(p, "Hold The Fort is a deep wilderenss world event that requires a player to take control of the fort and hold it for 60 seconds without dying! Use the events tab to read more.");
        sendDirective(p, "Take control of the fort during the Hold The Fort event (does not require you to win).");
        sendRewards(p, -1, -1, 995, 150000, -1, -1);
    }
    public static void selectLikeABoss(Player p) {
        clearTitles(p);
        sendTitle(p, ""+LIKE_A_BOSS.getListener().name(), 185);
        sendInfo(p, "Not everyone has the willpower to complete the brain-dead grind of 99 Runecrafting. Are you up for the challenge?");
        sendDirective(p, "Achieve level 99 Runecrafting.");
        sendRewards(p, 26784, 1, 995, 250000, -1, -1);
    }
    public static void selectTakeMeToTheTop(Player p) {
        clearTitles(p);
        sendTitle(p, ""+TAKE_ME_TO_THE_TOP.getListener().name(), 186);
        sendInfo(p, "Items crafted from Zenyte are some of the best items in OSPK, however, zenytes are very difficult to obtain!");
        sendDirective(p, "Craft any piece of Zenyte jewelry.");
        sendRewards(p, -1, -1, 995, 250000, -1, -1);
    }
    public static void updateEliteBar(Player p) {
        if (p.eliteAchievementsCompleted == 0) {
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 313, "<col=d22222>"+p.eliteAchievementsCompleted+"/12");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 312, "<col=d22222>Elite");
        }
        if (p.eliteAchievementsCompleted == 1) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 318, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 313, "<col=ffff33>"+p.eliteAchievementsCompleted+"/12");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 312, "<col=ffff33>Elite");
        }
        if (p.eliteAchievementsCompleted == 2) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 318, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 389, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 313, "<col=ffff33>"+p.eliteAchievementsCompleted+"/12");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 312, "<col=ffff33>Elite");
        }
        if (p.eliteAchievementsCompleted == 3) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 318, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 389, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 390, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 313, "<col=ffff33>"+p.eliteAchievementsCompleted+"/12");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 312, "<col=ffff33>Elite");
        }
        if (p.eliteAchievementsCompleted == 4) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 318, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 389, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 390, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 391, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 313, "<col=ffff33>"+p.eliteAchievementsCompleted+"/12");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 312, "<col=ffff33>Elite");
        }
        if (p.eliteAchievementsCompleted == 5) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 318, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 389, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 390, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 391, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 392, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 313, "<col=ffff33>"+p.eliteAchievementsCompleted+"/12");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 312, "<col=ffff33>Elite");
        }
        if (p.eliteAchievementsCompleted == 6) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 318, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 389, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 390, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 391, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 392, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 393, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 313, "<col=ffff33>"+p.eliteAchievementsCompleted+"/12");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 312, "<col=ffff33>Elite");
        }
        if (p.eliteAchievementsCompleted == 7) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 318, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 389, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 390, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 391, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 392, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 393, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 394, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 313, "<col=ffff33>"+p.eliteAchievementsCompleted+"/12");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 312, "<col=ffff33>Elite");
        }
        if (p.eliteAchievementsCompleted == 8) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 318, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 389, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 390, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 391, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 392, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 393, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 394, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 395, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 313, "<col=ffff33>"+p.eliteAchievementsCompleted+"/12");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 312, "<col=ffff33>Elite");
        }
        if (p.eliteAchievementsCompleted == 9) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 318, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 389, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 390, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 391, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 392, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 393, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 394, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 395, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 396, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 313, "<col=ffff33>"+p.eliteAchievementsCompleted+"/12");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 312, "<col=ffff33>Elite");
        }
        if (p.eliteAchievementsCompleted == 10) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 318, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 389, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 390, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 391, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 392, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 393, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 394, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 395, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 396, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 397, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 313, "<col=ffff33>"+p.eliteAchievementsCompleted+"/12");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 312, "<col=ffff33>Elite");
        }
        if (p.eliteAchievementsCompleted == 11) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 318, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 389, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 390, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 391, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 392, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 393, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 394, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 395, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 396, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 397, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 398, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 313, "<col=ffff33>"+p.eliteAchievementsCompleted+"/12");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 312, "<col=ffff33>Elite");
        }
        if (p.eliteAchievementsCompleted == 12) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 318, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 389, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 390, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 391, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 392, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 393, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 394, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 395, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 396, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 397, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 398, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 399, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 313, "<col=ffff33>"+p.eliteAchievementsCompleted+"/12");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 312, "<col=ffff33>Elite");
        }
    }
}