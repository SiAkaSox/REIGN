package io.ruin.model.content.accountperks;

public enum Perk {

    ARCANE_KNOWLEDGE(22324,"Arcane Knowledge", PerkType.COMBAT, "Cast ancient Magicks with all Elemental Staffs.", 1500, 257),

    CHARGE_SAVOR(24780,"Charge Savor", PerkType.COMBAT, "25% chance to save a Blood Fury Charge on hit.", 4000, 267),//doesnt exist?

    CHEFS_KISS(7946,"Chefs Kiss", PerkType.COMBAT, "All food heal +2 more hitpoints.", 3500, 277),//done

    CRITICAL_STRIKE(4726,"Critical Strike", PerkType.COMBAT, "Stab + slash weapons are able to deal damage to the Corporeal Beast.", 3000, 287),//done

    CRYSTAL_AIM(25867,"Crystal Aim", PerkType.COMBAT, "30% accuracy + 15% damage for Bowfa(c) without crystal armor.", 12000, 297),

    DRAGON_HUNTER(21012,"Dragon Hunter", PerkType.COMBAT, "Dragon hunter Bow / Lance deal an extra 10% to dragons.", 5000, 307),//done

    GUTHANS_BLESSING(22324,"Guthan's Blessing", PerkType.COMBAT, "Master the Guthan's spear, resulting in attack speed reduced by 1 tick.", 2500, 317),//done

    HARMONISED_BLESSING(24423,"Harmonised Blessing", PerkType.COMBAT, "Imbue a Harmoised staff to hold charges (similar to fire surge + fire tome off-hand).", 9000, 327),

    HEAVY_STRIKE(13576,"Heavy Strike", PerkType.COMBAT, "Decrease the Dragon Warhammer special attack to 30% instead of 50%.", 7500, 337),//done

    THE_LEECHER(25904,"The Leecher", PerkType.COMBAT, "Regenerate health equal to 5% of damage dealt per hit.", 12000, 347),//done

    LUCKY_SHOT(21963,"Lucky Shot", PerkType.COMBAT, "Causes crossbow bolt effects to activate 30% more.", 5500, 357),//done

    NECROMANCER(25818,"Necromancer", PerkType.COMBAT, "Summon thralls without the book of the dead, lifespan will also be doubled.",3500, 367),

    QUICK_DRAW(9185,"Quick Draw", PerkType.COMBAT, "Increase the speed of all crossbows by 1 tick.",9000, 377),//done

    SARADOMIN_BLESSING(12804,"Saradomin's Blessing", PerkType.COMBAT, "Gain 1-4 Prayer points on monster kill (based on monster/prayer level).",12000, 387),//done

    SCALEY_PACKER(12926,"Scaley Packer", PerkType.COMBAT, "Your Toxic Blowpipe will hold increased scales & Darts.",2500, 397),

    SECOND_WIND(7671,"Second Wind", PerkType.COMBAT, "Deal 20% more damage when under 30% health.",5500, 407),//done

    STURDY_CRAFTER(12006,"Sturdy Crafter", PerkType.COMBAT, "Your tentacle whip no longer degrade during combat.",7000, 417),//done

    /**
     * Skilling
     */
    BIG_PAWS(15673,"Big Paws", PerkType.SKILLING, "Catch two implings instead of one when using barehands.", 3000, 257),

    BLESSING_OF_FIRE(13890,"Blessing of Fire", PerkType.SKILLING, "Causes your smithing speed to double.", 3000, 267),

    CARDIO_MASTER(18208,"Cardio Master", PerkType.SKILLING, "Your energy no longer depletes when running.", 7000, 277),

    DARK_ESSENCE_MASTER(13446,"Dark Essence", PerkType.SKILLING, "Mine dark essence blocks directly from the mine.", 2000, 287),

    DOUBLE_DIP(301,"Double Dip", PerkType.SKILLING, "Causes your fishing speed to double.", 3000, 297),

    ENDLESS_ORE(11920,"Endless Ore", PerkType.SKILLING, "Stop rocks from depleting while wearing expert mining gloves.", 3000, 307),

    ESSENCE_CATCHER(1436,"Essence Catcher", PerkType.SKILLING, "15% chance of saving essance during runecrafting.", 2000, 317),

    ETERNAL_FIRE(590,"Eternal Fire", PerkType.SKILLING, "Continue burning logs while firemaking as long as you have them.", 1250, 327),

    FORTUNITE_FARMER(1411,"Fortunite Farmer", PerkType.SKILLING, "Causes 50% more yield when farming herb patches.", 2500, 337),

    HUNTMASTER(10008,"Huntmaster", PerkType.SKILLING, "Causes 100% catch rate with hunter trap.", 1500, 347),

    INFERNAL_BLESSING(594,"Infernal Blessing", PerkType.SKILLING, "Immune from damage in wintertodt + falling snow (broken braziers still hurt).", 500, 357),

    LEATHER_MASTER(5513,"Leather Master", PerkType.SKILLING, "Causes runecrafting pouches no longer break.", 800, 367),

    MASTER_BAITER(313,"Master Baiter", PerkType.SKILLING, "Causes no bait being required when fishing.", 800, 377),

    POTION_MASTER(23685,"Potion Master", PerkType.SKILLING, "Increases the duration of Divine Potions by 50%.", 3000, 387),

    SLEIGHT_OF_HAND(18427,"Sleight of Hand", PerkType.SKILLING, "Causes 100% success rate when pick pocketing.", 2000, 397),

    WOODSTOCKER(10939,"Woodstocker", PerkType.SKILLING, "Causes 20% chance to get an extra log, and xp while wearing full lumberjack.", 1000, 407),

    NEEDS_NAME(1580,"NEEDS_NAME", PerkType.SKILLING, "Causes 10+ smithing xp within blast furance and benefits of ice gloves without them.", 750, 417),
    /**
     * Misc
     */

    ANIMAL_LOVER(20663,"Animal Lover", PerkType.MISC, "x2 the chance of receiving a pet drop.", 9500, 257),

    ANCIENT_ONES_BLESSING(18342,"Ancient One's Blessing", PerkType.MISC, "Reduces gwd dungeon pray alter timer by half.", 900, 267),

    AURA_OF_LIGHT(13231,"Aura of Light", PerkType.MISC, "Reduces the Summon souls damage by 75%.", 1250, 277),

    OSPK_BLESSING(22320,"OSPK Blessing", PerkType.MISC, "Gives bonus xp for every-non combat skill while training (xp x total level / 10 x xp rate)", 1500, 287),

    BRIMSTONE_BLESSING(31034,"Brimstone Blessing", PerkType.MISC, "Allows the ability to imbue the brimstone ring", 4000, 297),

    EXTERMINATOR(11666,"Exterminator", PerkType.MISC, "Causes experience rewards from pc to increase by 25%.", 750, 307),

    GAS_MASK(26382,"Gas Mask", PerkType.MISC, "Makes you immune to the Nex cough attack", 1500, 317),

    HYDRA_MASTER(22746,"Hydra Master", PerkType.MISC, "You do not need to drag the Hydra to particular vents to do damage.", 3000, 327),

    LOOT_CHASER(30191,"Loot Chaser", PerkType.MISC, "Causes + 1 to the Corporeal Beast drop roll.", 7000, 337),

    MASTER_JEWELER(19553,"Master Jeweler", PerkType.MISC, "Allows blood shards to be fused into Amulets of tortures.", 5500, 347),

    POINT_BOOSTER(20997,"Point Booster", PerkType.MISC, "Causes an extra +1 point per damage in CoX.", 7500, 357),

    RAID_MASTER(22325,"Raid Master", PerkType.MISC, "Causes a 10% damage boost in all raids.", 7500, 367),

    SERPENT_HUNTER(12934,"Serpent Hunter", PerkType.MISC, "Extra 250 scales from Zulrah + dismantling Zulrah items gives 25% more scales.", 1500, 377),

    SHINY_ENTHUSIAST(23962,"Shiny Enthusiast", PerkType.MISC, "Causes crystal shard rewards to double from all sources.", 2000, 387),

    SLAYER_BOOSTER(11864,"Slayer Booster", PerkType.MISC, "Causes extra slayer points on task completion (slayer lvl / 9 = points)", 2500, 397),

    TREASURE_HUNTER(19835,"Treasure Hunter", PerkType.MISC, "Causes double clue scroll drops.", 7000, 407),

    KINDLER(20696,"Kindler", PerkType.MISC, "Receive bruma kindling instead of roots while wooding in wintertodt.", 750, 417),

    ;

    public int itemId;
    public String title;
    public PerkType type;
    public int requiredPoints, child;
    public String description;

    Perk(int itemId, String title, PerkType type, String description, int requiredPoints, int child) {
        this.itemId = itemId;
        this.title = title;
        this.type = type;
        this.description = description;
        this.requiredPoints = requiredPoints;
        this.child = child;
    }
}
