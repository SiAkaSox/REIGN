package io.ruin.model.achievements.listeners.hard;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.item.Item;

import static io.ruin.model.achievements.Achievement.*;

public class HardAchieveInter {

    public static int ACHIEVEMENTS_TAB = 858;
    public static int HARD_INTER = 902;

    public static void clearIntTitles(Player p) {
        p.openInterface(InterfaceType.MAIN, HARD_INTER);
        p.getPacketSender().sendString(HARD_INTER, 278, "Select an achievement");
        p.getPacketSender().sendString(HARD_INTER, 336, "");
        p.getPacketSender().sendString(HARD_INTER, 279, "");
        p.getPacketSender().sendItems(HARD_INTER, 370, 0, new Item(-1));
        p.getPacketSender().sendItems(HARD_INTER, 368, 0, new Item(-1));
        p.getPacketSender().sendItems(HARD_INTER, 374, 0, new Item(-1));
    }
    public static void clearTitles(Player p) {
        p.getPacketSender().sendString(HARD_INTER, 88, ""+ ARENA_CHAMPION.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 89, ""+ TIME_TRAVELER.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 90, ""+ BRIM_CHEST.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 170, ""+ THE_SERPANT_KING.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 171, ""+ DOING_MY_PART.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 172, ""+ SUPERIOR_KILLER.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 177, ""+ TREASURE_FINDER.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 178, ""+ A_REAL_CHALLENGE.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 179, ""+ THE_LAST_MAN.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 184, ""+ TOURNY_TIME.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 185, ""+ HOW_MANY_HEADS.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 186, ""+ COLD_BLOODED.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 191, ""+ TIGHTROPE_WALKER.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 192, ""+ ON_A_CONQUEST.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 193, ""+ DRAGON_SLAYER.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 198, ""+ THE_REVENANT.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 199, ""+ KEY_HOLDER.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 200, ""+ THE_ELITE_OF_THE_ELITE.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 361, ""+ ETHEREAL.getListener().name());
        p.getPacketSender().sendString(HARD_INTER, 362, ""+ SACRIFICIAL.getListener().name());
    }
    public static void sendInfo(Player p, String info) {
        p.getPacketSender().sendString(HARD_INTER, 336, ""+ info);
    }
    public static void sendDirective(Player p, String info) {
        p.getPacketSender().sendString(HARD_INTER, 279, ""+ info);
    }
    public static void sendTitle(Player p, String name, int id) {
        p.getPacketSender().sendString(HARD_INTER, id, "<col=ffffff>"+ name);
        p.getPacketSender().sendString(HARD_INTER, 278, ""+ name);
    }
    public static void sendRewards(Player p, int itemOne, int itemOneAmt, int itemTwo, int itemTwoAmt, int itemThree, int itemThreeAmt) {
        p.getPacketSender().sendItems(HARD_INTER, 374, 0, new Item(itemOne, itemOneAmt));
        p.getPacketSender().sendItems(HARD_INTER, 370, 0, new Item(itemTwo, itemTwoAmt));
        p.getPacketSender().sendItems(HARD_INTER, 368, 0, new Item(itemThree, itemThreeAmt));
    }

    public static void loadHardAchievements(Player p) {
        clearIntTitles(p);
        clearTitles(p);
        if (!ARENA_CHAMPION.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 375, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 315, true);
        }
        if (!TIME_TRAVELER.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 376, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 316, true);
        }
        if (!BRIM_CHEST.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 377, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 317, true);
        }
        if (!THE_SERPANT_KING.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 378, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 318, true);
        }
        if (!DOING_MY_PART.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 379, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 319, true);
        }
        if (!SUPERIOR_KILLER.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 380, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 320, true);
        }
        if (!TREASURE_FINDER.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 381, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 321, true);
        }
        if (!A_REAL_CHALLENGE.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 382, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 322, true);
        }
        if (!THE_LAST_MAN.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 388, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 383, true);
        }
        if (!TOURNY_TIME.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 389, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 384, true);
        }
        if (!HOW_MANY_HEADS.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 390, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 385, true);
        }
        if (!COLD_BLOODED.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 391, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 386, true);
        }
        if (!TIGHTROPE_WALKER.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 392, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 387, true);
        }
        if (!ON_A_CONQUEST.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 396, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 393, true);
        }
        if (!DRAGON_SLAYER.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 397, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 394, true);
        }
        if (!THE_REVENANT.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 398, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 395, true);
        }
        if (!KEY_HOLDER.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 410, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 406, true);
        }
        if (!THE_ELITE_OF_THE_ELITE.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 411, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 407, true);
        }
        if (!ETHEREAL.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 412, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 408, true);
        }
        if (!SACRIFICIAL.isFinished(p)) {
            p.getPacketSender().setHidden(HARD_INTER, 413, true);
        } else {
            p.getPacketSender().setHidden(HARD_INTER, 409, true);
        }
    }

    public static void selectArenaChampion(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ARENA_CHAMPION.getListener().name(), 88);
        sendInfo(p, "So you think you're all that because you won one arena huh? Cool, but can you win 25?");
        sendDirective(p, "Win 25 rated or unrated Arenas.");
        sendRewards(p, 25646, 1, 995, 250000, -1, -1);
    }
    public static void selectTimeTraveler(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ TIME_TRAVELER.getListener().name(), 89);
        sendInfo(p, "Travel back in time to a Time Rift and defeat the last boss!");
        sendDirective(p, "Defeat the final Time Rift boss");
        sendRewards(p, 23071, 1, 24709, 250, -1, -1);
    }
    public static void selectBrimChest(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ BRIM_CHEST.getListener().name(), 90);
        sendInfo(p, "During tasks given by Konar, you can receive Brimstone Keys that unlock the chest next to Konar, and reward you greatly they will.");
        sendDirective(p, "Open the Brimstone Chest.");
        sendRewards(p, 23071, 1, 995, 100000, 989, 2);
    }
    public static void selectTheSerpantKing(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ THE_SERPANT_KING.getListener().name(), 170);
        sendInfo(p, "The Serpant King, Zulrah, is one of the more tough and rewarding bosses to kill, if you can master the rotations!");
        sendDirective(p, "Kill Zulrah.");
        sendRewards(p, 12934, 1000, 995, 150000, 23071, 1);
    }
    public static void selectDoingMyPart(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ DOING_MY_PART.getListener().name(), 171);
        sendInfo(p, "Voting for OSPK not only helps the server grow, but also rewards you for helping us!");
        sendDirective(p, "Claim ::vote rewards.");
        sendRewards(p, 20545, 1, 995, 100000, 1464, 1);
    }
    public static void selectSuperiorKiller(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ SUPERIOR_KILLER.getListener().name(), 172);
        sendInfo(p, "The Enhanced Larran's Chest, in the wilderness expansion, gives much better rewards than the normal chest!");
        sendDirective(p, "Open the Enhanced Larran's Chest");
        sendRewards(p, 23071, 1, 995, 150000, -1, -1);

    }
    public static void selectTreasureFinder(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ TREASURE_FINDER.getListener().name(), 177);
        sendInfo(p, "This one is pretty straight forward, not gonna lie.");
        sendDirective(p, "Complete a Hard Clue scroll.");
        sendRewards(p, 20545, 1, 995, 150000, -1, -1);
    }
    public static void selectARealChallenge(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ A_REAL_CHALLENGE.getListener().name(), 178);
        sendInfo(p, "The Fragment of Seren is far and wide the most difficult boss on OSPK. You will not be able to take her down alone.");
        sendDirective(p, "Kill the Fragment of Seren world boss.");
        sendRewards(p, 11158, 1, 995, 250000, -1, -1);
    }
    public static void selectTheLastMan(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ THE_LAST_MAN.getListener().name(), 179);
        sendInfo(p, "Last Man Standing is a rewarding minigame for players of all levels!");
        sendDirective(p, "Finish a game of Last Man Standing.");
        sendRewards(p, 13307, 25, 995, 100000, -1, -1);
    }
    public static void selectTournyTime(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ TOURNY_TIME.getListener().name(), 184);
        sendInfo(p, "Players can compete in Tournaments for PK Points and additional items! Enter in the PvP Hall in Edgeville.");
        sendDirective(p, "Compete in a Tournament.");
        sendRewards(p, 6758, 1, 995, 100000, -1, -1);
    }
    public static void selectHowManyHeads(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ HOW_MANY_HEADS.getListener().name(), 185);
        sendInfo(p, "Known for dropping some of the best boots in the land, Cerberus has 3 heads and will not go down without a fight!");
        sendDirective(p, "Kill Cerberus.");
        sendRewards(p, 23071, 2, 995, 250000, -1, -1);
    }
    public static void selectColdBlooded(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ COLD_BLOODED.getListener().name(), 186);
        sendInfo(p, "So you think you're tough for killing one person in the wild, which was probably your alt account? Well, 25 kills will impress me!");
        sendDirective(p, "Kill 25 players in the wilderness.");
        sendRewards(p, 25646, 1, 995, 500000, 13307, 350);
    }
    public static void selectTightropeWalker(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ TIGHTROPE_WALKER.getListener().name(), 191);
        sendInfo(p, "High level agility makes many aspects of the game more convenient!");
        sendDirective(p, "Complete a lap at the Rellekka Rooftop Course.");
        sendRewards(p, 11849, 10, 995, 150000, -1, -1);
    }
    public static void selectOnAConquest(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ ON_A_CONQUEST.getListener().name(), 192);
        sendInfo(p, "The Emblem Trader in the PvP Hall at home will buy all of your ancient emblems from revenants!");
        sendDirective(p, "Sell any ancient emblem to the Emblem Trader.");
        sendRewards(p, 13307, 25, 995, 150000, -1, -1);
    }
    public static void selectDragonSlayer(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ DRAGON_SLAYER.getListener().name(), 193);
        sendInfo(p, "Vorkath is one of the toughest dragons in the game, as well as one of the most rewarding. Can you face him?");
        sendDirective(p, "Kill Vorkath.");
        sendRewards(p, 23071, 3, 995, 250000, -1, -1);
    }
    public static void selectTheRevenant(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ THE_REVENANT.getListener().name(), 198);
        sendInfo(p, "The Revenant Cave is home to great treasure, at great risk. Take your chances and kill some of the monsters in the cave!");
        sendDirective(p, "Kill 25 Revenants.");
        sendRewards(p, -1, -1, 995, 100000, -1, -1);
    }
    public static void selectKeyHolder(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ KEY_HOLDER.getListener().name(), 199);
        sendInfo(p, "While doing wilderness slayer tasks, you have a chance to receive Larran's Keys to open the chest in deep wilderness, containing great rewards!");
        sendDirective(p, "Open Larran's Chest.");
        sendRewards(p, -1, -1, 995, 100000, -1, -1);
    }
    public static void selectTheEliteoftheElite(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ THE_ELITE_OF_THE_ELITE.getListener().name(), 200);
        sendInfo(p, "Only the Elite of the Elite players will earn their Elite Void Set. Will you be one of them?");
        sendDirective(p, "Equip a piece of Elite Void equipment.");
        sendRewards(p, -1, -1, 995, 250000, -1, -1);
    }
    public static void selectTheBloodDiamond(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ ETHEREAL.getListener().name(), 361);
        sendInfo(p, "The Fire of Sacrifice rewards excellent account perks to those who wish to make a sacrifice of items to the fire.");
        sendDirective(p, "Unlock a Perk from the Fire of Sacrifice");
        sendRewards(p, -1, -1, 995, 250000, -1, -1);
    }
    public static void selectSacrificial(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ SACRIFICIAL.getListener().name(), 362);
        sendInfo(p, "In the Tzhaar Caves, TzHaar-Mej-Jal will wager you a bet. The bet is, gamble a Fire Cape at a chance at receiving a Jad pet.");
        sendDirective(p, "Gamble a Fire Cape to try and receive a Jad pet.");
        sendRewards(p, -1, -1, 995, 250000, -1, -1);
    }
    public static void updateHardBar(Player p) {
        if (p.hardAchievementsCompleted == 0) {
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=d22222>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=d22222>Hard");
        }
        if (p.hardAchievementsCompleted == 1) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 2) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 3) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 4) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 5) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 6) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 7) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 375, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 8) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 375, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 376, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 9) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 375, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 376, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 377, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 10) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 375, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 376, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 377, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 378, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 11) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 375, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 376, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 377, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 378, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 379, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 12) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 375, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 376, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 377, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 378, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 379, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 380, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 13) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 375, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 376, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 377, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 378, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 379, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 380, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 381, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 14) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 375, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 376, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 377, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 378, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 379, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 380, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 381, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 382, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 15) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 375, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 376, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 377, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 378, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 379, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 380, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 381, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 382, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 383, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 16) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 375, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 376, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 377, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 378, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 379, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 380, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 381, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 382, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 383, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 384, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 17) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 375, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 376, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 377, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 378, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 379, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 380, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 381, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 382, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 383, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 384, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 385, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 18) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 375, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 376, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 377, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 378, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 379, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 380, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 381, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 382, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 383, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 384, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 385, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 386, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 19) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 375, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 376, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 377, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 378, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 379, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 380, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 381, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 382, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 383, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 384, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 385, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 386, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 387, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
        if (p.hardAchievementsCompleted == 20) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 307, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 370, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 371, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 372, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 373, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 374, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 375, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 376, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 377, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 378, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 379, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 380, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 381, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 382, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 383, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 384, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 385, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 386, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 387, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 388, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 302, "<col=ffff33>"+p.hardAchievementsCompleted+"/20");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 301, "<col=ffff33>Hard");
        }
    }
}