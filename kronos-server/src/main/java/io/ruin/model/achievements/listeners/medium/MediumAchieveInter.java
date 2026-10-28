package io.ruin.model.achievements.listeners.medium;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.item.Item;

import static io.ruin.model.achievements.Achievement.*;
import static io.ruin.model.achievements.Achievement.KING_OF_THE_WILD;

public class MediumAchieveInter {

    public static int ACHIEVEMENTS_TAB = 858;
    public static int MEDIUM_INTER = 901;

    public static void clearIntTitles(Player p) {
        p.openInterface(InterfaceType.MAIN, MEDIUM_INTER);
        p.getPacketSender().sendString(MEDIUM_INTER, 278, "Select an achievement");
        p.getPacketSender().sendString(MEDIUM_INTER, 336, "");
        p.getPacketSender().sendString(MEDIUM_INTER, 279, "");
        p.getPacketSender().sendItems(MEDIUM_INTER, 370, 0, new Item(-1));
        p.getPacketSender().sendItems(MEDIUM_INTER, 374, 0, new Item(-1));
        p.getPacketSender().sendItems(MEDIUM_INTER, 368, 0, new Item(-1));
    }
    public static void clearTitles(Player p) {
        p.getPacketSender().sendString(MEDIUM_INTER, 88, ""+ ARENA_NOVICE.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 89, ""+ BATTLEGROUND_NOVICE.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 90, ""+ IMBUED_POWER.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 170, ""+ ENCHANTER.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 171, ""+ GEARING_UP.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 172, ""+ NATURAL_BORN_KILLER.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 177, ""+ YOURE_A_NATURAL_AT_THIS.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 178, ""+ ARE_YOU_MAN_ENOUGH.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 179, ""+ HOME_OWNER.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 184, ""+ WORLD_EVENTS.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 185, ""+ GUIDE_BOOK.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 186, ""+ THE_LAVA_CAPE.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 191, ""+ CYCLOPS_KILLER.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 192, ""+ THE_BROTHERS.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 193, ""+ MOLEY_MOLEY_MOLEY.getListener().name());
        p.getPacketSender().sendString(MEDIUM_INTER, 198, ""+ KING_OF_THE_WILD.getListener().name());
    }
    public static void sendInfo(Player p, String info) {
        p.getPacketSender().sendString(MEDIUM_INTER, 336, ""+ info);
    }
    public static void sendDirective(Player p, String info) {
        p.getPacketSender().sendString(MEDIUM_INTER, 279, ""+ info);
    }
    public static void sendTitle(Player p, String name, int id) {
        p.getPacketSender().sendString(MEDIUM_INTER, id, "<col=ffffff>"+ name);
        p.getPacketSender().sendString(MEDIUM_INTER, 278, ""+ name);
    }
    public static void sendRewards(Player p, int itemOne, int itemOneAmt, int itemTwo, int itemTwoAmt, int itemThree, int itemThreeAmt) {
        p.getPacketSender().sendItems(MEDIUM_INTER, 374, 0, new Item(itemOne, itemOneAmt));
        p.getPacketSender().sendItems(MEDIUM_INTER, 370, 0, new Item(itemTwo, itemTwoAmt));
        p.getPacketSender().sendItems(MEDIUM_INTER, 368, 0, new Item(itemThree, itemThreeAmt));
    }

    public static void loadMediumAchievements(Player p) {
        clearIntTitles(p);
        clearTitles(p);
        if (!ARENA_NOVICE.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 375, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 315, true);
        }
        if (!BATTLEGROUND_NOVICE.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 376, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 316, true);
        }
        if (!IMBUED_POWER.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 377, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 317, true);
        }
        if (!ENCHANTER.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 378, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 318, true);
        }
        if (!GEARING_UP.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 379, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 319, true);
        }
        if (!NATURAL_BORN_KILLER.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 380, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 320, true);
        }
        if (!YOURE_A_NATURAL_AT_THIS.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 381, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 321, true);
        }
        if (!ARE_YOU_MAN_ENOUGH.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 382, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 322, true);
        }
        if (!HOME_OWNER.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 388, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 383, true);
        }
        if (!WORLD_EVENTS.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 389, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 384, true);
        }
        if (!GUIDE_BOOK.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 390, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 385, true);
        }
        if (!THE_LAVA_CAPE.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 391, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 386, true);
        }
        if (!CYCLOPS_KILLER.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 392, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 387, true);
        }
        if (!THE_BROTHERS.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 396, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 393, true);
        }
        if (!MOLEY_MOLEY_MOLEY.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 397, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 394, true);
        }
        if (!KING_OF_THE_WILD.isFinished(p)) {
            p.getPacketSender().setHidden(MEDIUM_INTER, 398, true);
        } else {
            p.getPacketSender().setHidden(MEDIUM_INTER, 395, true);
        }
    }

    public static void selectArenaNovice(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ ARENA_NOVICE.getListener().name(), 88);
        sendInfo(p, "Arenas and Battlegrounds are the main source of earning Conquest Points in OSPK. Can you win one?");
        sendDirective(p, "Win a rated or unrated Arena.");
        sendRewards(p, 13442, 50, 995, 100000, 12696, 10);
    }
    public static void selectBattlegroundNovice(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ BATTLEGROUND_NOVICE.getListener().name(), 89);
        sendInfo(p, "Deadmans Chest is a dangerous wilderness world event that has potential for great rewards! Only one person can unlock the chest..");
        sendDirective(p, "Open the Deadmans Chest.");
        sendRewards(p, 12696, 10, 995, 50000, 13307, 100);
    }
    public static void selectImbuedPower(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ IMBUED_POWER.getListener().name(), 90);
        sendInfo(p, "Imbueing items makes them stronger and ensures you will not lose them upon your inevitable death!");
        sendDirective(p, "Imbue an item at the Lectern of Imbuement.");
        sendRewards(p, -1, -1, 1464, 3, -1, -1);
    }
    public static void selectEnchanter(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ ENCHANTER.getListener().name(), 170);
        sendInfo(p, "Enchanting items and farming enchanting materials are a great way to get BiS items and earn tons of money!");
        sendDirective(p, "Enchant any item at the Altar of Enchantment.");
        sendRewards(p, 989, 3, 995, 100000, 1464, 3);
    }
    public static void selectGearingUp(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ GEARING_UP.getListener().name(), 171);
        sendInfo(p, "Did you know: On OSPK, our Gauntlet is no-prep?! Jump right into the action against Hunllef, but don't forget to grab gear inside!");
        sendDirective(p, "Enter the normal gauntlet and gear up.");
        sendRewards(p, -1, -1, 995, 100000, -1, -1);
    }
    public static void selectNaturalBornKiller(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ NATURAL_BORN_KILLER.getListener().name(), 172);
        sendInfo(p, "Player-Killing is at the forefront of OSPK, earning you PK Points and Conquest to spend in shops!");
        sendDirective(p, "PK a player in the wilderness.");
        sendRewards(p, 13307, 50, 995, 100000, -1, -1);
    }
    public static void selectYoureANaturalAtThis(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ YOURE_A_NATURAL_AT_THIS.getListener().name(), 177);
        sendInfo(p, "Hey, you're pretty good at this Slayer thing! But are you good enough to keep going?");
        sendDirective(p, "Complete 5 Slayer tasks.");
        sendRewards(p, 23071, 1, 995, 100000, -1, -1);
    }
    public static void selectAreYouManEnough(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ ARE_YOU_MAN_ENOUGH.getListener().name(), 178);
        sendInfo(p, "Wilderness Slayer is a great method for earning more slayer points at a faster pace, however, it is much riskier!");
        sendDirective(p, "Complete a wilderness slayer task.");
        sendRewards(p, -1, -1, 23490, 1, -1, -1);
    }
    public static void selectHomeOwner(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ HOME_OWNER.getListener().name(), 179);
        sendInfo(p, "Player Owned Houses are essential for all player types in OSPK! You can find out more from the Estate Agent in Edgeville!");
        sendDirective(p, "Purchase a Player Owned House.");
        sendRewards(p, 8783, 25, 995, 100000, -1, -1);
    }
    public static void selectReignEvents(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ WORLD_EVENTS.getListener().name(), 184);
        sendInfo(p, "OSPK is home to many unique and rewarding world events, some much more challenging than others!");
        sendDirective(p, "Open any event window in the Server Events quest tab.");
        sendRewards(p, -1, -1, 989, 1, -1, -1);
    }
    public static void selectGuideBook(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ GUIDE_BOOK.getListener().name(), 185);
        sendInfo(p, "The Guide Book is very useful to players new and old who are unsure about monsters, skilling, player commands and more!");
        sendDirective(p, "Use ::guides to open the player Guide Book.");
        sendRewards(p, -1, -1, 995, 25000, -1, -1);
    }
    public static void selectTheLavaCape(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ THE_LAVA_CAPE.getListener().name(), 186);
        sendInfo(p, "The Fire Cape is historically one of the best mid-game items. You must take on Jad to win the cape!");
        sendDirective(p, "Earn a Fire Cape from the Fight Caves.");
        sendRewards(p, -1, -1, 995, 1000000, -1, -1);
    }
    public static void selectCyclopsKiller(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ CYCLOPS_KILLER.getListener().name(), 191);
        sendInfo(p, "Like the Fire Cape, the Dragon Defender is also essential for mid-game gameplay!");
        sendDirective(p, "Obtain a Dragon Defender drop from the Warrior's Guild.");
        sendRewards(p, -1, -1, 995, 1000000, -1, -1);
    }
    public static void selectTheBrothers(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ THE_BROTHERS.getListener().name(), 192);
        sendInfo(p, "Six undead brothers dwell in Morytania, guarding their chest of loot. Take from it!");
        sendDirective(p, "Loot the Barrows Chest 10 times.");
        sendRewards(p, -1, -1, 995, 1000000, -1, -1);
    }
    public static void selectMoleyMoleyMoley(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ MOLEY_MOLEY_MOLEY.getListener().name(), 193);
        sendInfo(p, "There's not much to say here. There's a big mole living underneath Falador.");
        sendDirective(p, "Kill the Giant Mole.");
        sendRewards(p, -1, -1, 995, 1000000, -1, -1);
    }
    public static void selectKingoftheWild(Player p) {
        clearTitles(p);
        sendTitle(p, ""+ KING_OF_THE_WILD.getListener().name(), 198);
        sendInfo(p, "There's only one true king of the wilderness, living in a lair deep in the wild. Are you up to the challenge?");
        sendDirective(p, "Kill the King Black Dragon 5 times.");
        sendRewards(p, -1, -1, 995, 1000000, -1, -1);
    }
    public static void updateMediumBar(Player p) {
        if (p.mediumAchievementsCompleted == 0) {
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=d22222>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=d22222>Medium");
        }
        if (p.mediumAchievementsCompleted == 1) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 2) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 3) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 356, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 4) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 356, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 357, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 5) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 356, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 357, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 358, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 6) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 356, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 357, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 358, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 359, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 7) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 356, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 357, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 358, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 359, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 360, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 8) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 356, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 357, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 358, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 359, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 360, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 361, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 9) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 356, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 357, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 358, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 359, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 360, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 361, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 362, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 10) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 356, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 357, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 358, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 359, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 360, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 361, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 362, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 363, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 11) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 356, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 357, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 358, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 359, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 360, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 361, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 362, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 363, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 364, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 12) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 356, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 357, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 358, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 359, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 360, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 361, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 362, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 363, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 364, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 365, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 13) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 356, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 357, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 358, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 359, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 360, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 361, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 362, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 363, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 364, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 365, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 366, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 14) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 356, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 357, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 358, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 359, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 360, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 361, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 362, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 363, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 364, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 365, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 366, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 367, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 15) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 356, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 357, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 358, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 359, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 360, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 361, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 362, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 363, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 364, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 365, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 366, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 367, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 368, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=ffff33>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=ffff33>Medium");
        }
        if (p.mediumAchievementsCompleted == 16) {
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 296, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 355, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 356, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 357, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 358, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 359, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 360, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 361, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 362, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 363, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 364, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 365, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 366, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 367, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 368, true);
            p.getPacketSender().setHidden(ACHIEVEMENTS_TAB, 369, true);
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 291, "<col=2eff00>"+p.mediumAchievementsCompleted+"/16");
            p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 290, "<col=2eff00>Medium");
        }
    }
}