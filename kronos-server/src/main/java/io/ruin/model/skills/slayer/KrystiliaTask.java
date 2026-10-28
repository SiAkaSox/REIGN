package io.ruin.model.skills.slayer;

import io.ruin.model.item.Item;
import io.ruin.model.map.Position;

public enum KrystiliaTask {
    ABYSSAL_DEMON(42, "abyssaldemons",
            new int[]{415,
                    416,
                    5886,
                    5887,
                    5888,
                    5890,
                    5891,
                    5908,
                    7038,
                    7241,
                    7410,},
            85,
            85,
            40,
            70
    ),
    ANKOU(79,
            "ankou",
            new int[]{
                    2514,
                    2515,
                    2516,
                    2517,
                    2518,
                    2519,
                    6608,
                    7257,
                    7296,
                    7864
            },
            40,
            1,
            40,
            60
    ),
    AVIANSIE(94,
            "aviansies",
            new int[]{
                    3162,
                    3163,
                    3164,
                    3165,
                    3169,
                    3170,
                    3171,
                    3172,
                    3173,
                    3174,
                    3175,
                    3176,
                    3177,
                    3178,
                    3179,
                    3180,
                    3181,
                    3182,
                    3183,
                    6492,
                    6587,
                    7037
            },
            40,
            1,
            40,
            60
    ),
    BANDITS(102,
            "bandit",
            new int[]{
                    301,
                    302,
                    303,
                    526,
                    1026,
                    1027,
                    6605
            },
            1,
            1,
            40,
            60
    ),
    BEAR(13,
            "bear",
            new int[]{
                    2838,
                    2839,
                    3423,
                    3424,
                    3425,
                    3908,
                    3909,
                    6503,
                    6609
            },
            1,
            1,
            50,
            100
    ),
    BLACK_DEMON(30,
            "blackdemons",
            new int[]{
                    240,
                    1432,
                    1609,
                    2048,
                    2049,
                    2050,
                    2051,
                    2052,
                    3132,
                    5874,
                    5875,
                    5876,
                    5877,
                    6295,
                    6357,
                    7242,
                    7243,
                    7286,
                    7860,
                    7874,
                    7875,
                    7876
            },
            75,
            1,
            40,
            60
    ),
    BLACK_DRAGON(27,
            "blackdragons",
            new int[]{
                    239,
                    252,
                    253,
                    254,
                    255,
                    256,
                    257,
                    258,
                    259,
                    1871,
                    1872,
                    6502,
                    7275,
                    7861,
                    7862,
                    7863,
                    7955,
                    8085
            },
            1,
            1,
            5,
            20
    ),
    CHAOS_DRUID(110,
            "chaosdruid",
            new int[]{
                    520,
                    7995,
                    6607
            },
            1,
            1,
            40,
            60
    ),
    DARK_WARRIOR(103,
            "darkwarrior",
            new int[]{
                    531,
                    523,
                    6606
            },
            1,
            1,
            40,
            60
    ),
    EARTH_WARRIOR(54,
            "earthwarrior",
            new int[]{
                    2840
            },
            1,
            1,
            40,
            60
    ),
    ENT(101,
            "ents",
            new int[]{6594},
            1,
            1,
            40,
            60
    ),
    FIRE_GIANT(16,
            "firegiants",
            new int[]{
                    2075,
                    2076,
                    2077,
                    2078,
                    2079,
                    2080,
                    2081,
                    2082,
                    2083,
                    2084,
                    7251,
                    7252
            },
            1,
            1,
            40,
            60
    ),
    GREATER_DEMON(29,
            "greaterdemons",
            new int[]{
                    2025,
                    2026,
                    2027,
                    2028,
                    2029,
                    2030,
                    2031,
                    2032,
                    3129,
                    3130,
                    6495,
                    7244,
                    7245,
                    7246,
                    7286,
                    7871,
                    7872,
                    7873
            },
            1,
            1,
            40,
            60
    ),
    GREEN_DRAGON(24,
            "greendragon",
            new int[]{
                    260,
                    261,
                    262,
                    263,
                    264,
                    2918,
                    5194,
                    5872,
                    5873,
                    6118,
                    6349,
                    7868,
                    7869,
                    7870,
                    8033,
                    8081,
                    8082
            },
            1,
            1,
            40,
            60
    ),
    HELLHOUND(31,
            "hellhounds",
            new int[]{
                    104,
                    105,
                    1224,
                    3133,
                    5054,
                    5862,
                    6326,
                    6355,
                    6387,
                    6613,
                    6614,
                    7256,
                    7877
            },
            1,
            1,
            40,
            60
    ),
    ICE_GIANT(15,
            "icegiant",
            new int[]{2085,
                    2086,
                    2087,
                    2088,
                    2089,
                    7878,
                    7879,
                    7880},
            1,
            1,
            40,
            60
    ),
    ICE_WARRIOR(19,
            "icewarrior",
            new int[]{
                    2085,
                    2086,
                    2087,
                    2088,
                    2089,
                    7878,
                    7879,
                    7880
            },
            1,
            1,
            40,
            60
    ),
    LAVA_DRAGON(104,
            "lavadragon",
            new int[]{6593},
            1,
            1,
            12,
            30
    ),
    LESSER_DEMON(28,
            "lesserdemon",
            new int[]{
                    2005,
                    2006,
                    2007,
                    2008,
                    2018,
                    3131,
                    3982,
                    7247,
                    7248,
                    7656,
                    7657,
                    7664,
                    7865,
                    7866,
                    7867
            },
            1,
            1,
            40,
            60
    ),
    MAGIC_AXE(91,
            "magicaxe",
            new int[]{
                    2844,
                    7269
            },
            1,
            1,
            40,
            60
    ),
    MAMMOTH(99,
            "mammoth",
            new int[]{
                    6604
            },
            1,
            1,
            40,
            60
    ),
    NECHRYAEL(52, "nechryael",
            new int[]{8,
                    11,
                    7278,
                    7411},
            85,
            80,
            40,
            70
    ),
    REVENANTS(107,
            "revenant",
            new int[]{
                    7881,
                    7931,
                    7932,
                    7933,
                    7934,
                    7935,
                    7936,
                    7937,
                    7938,
                    7939,
                    7940,
                    9048,
                    9047,
                    9041,
                    9045,
                    9043
            },
            1,
            1,
            40,
            60
    ),
    ROGUE(100,
            "rogue",
            new int[]{
                    526,
                    6603
            },
            1,
            1,
            40,
            60
    ),
    SCORPION(7,
            "scorpion",
            new int[]{
                    2479,
                    2480,
                    2994,
                    3024,
                    3025,
                    3026,
                    3027,
                    4800,
                    5242,
                    5372,
                    6615,
                    6616,
                    6617,
                    7022,
                    7796
            },
            1,
            1,
            60,
            100
    ),
    SKELETON(11,
            "skeleton",
            new int[]{
                    70,
                    71,
                    72,
                    73,
                    74,
                    75,
                    76,
                    77,
                    78,
                    79,
                    80,
                    81,
                    82,
                    83,
                    84,
                    680,
                    681,
                    872,
                    878,
                    879,
                    924,
                    1537,
                    1538,
                    1539,
                    1540,
                    1541,
                    1685,
                    1686,
                    1687,
                    1688,
                    2520,
                    2521,
                    2522,
                    2523,
                    2524,
                    2525,
                    2526,
                    3565,
                    3615,
                    3972,
                    3973,
                    3974,
                    4319,
                    4491,
                    4492,
                    4493,
                    4494,
                    4495,
                    4496,
                    4497,
                    4498,
                    4499,
                    5237,
                    5342,
                    5343,
                    5344,
                    5345,
                    6441,
                    6442,
                    6443,
                    6444,
                    6445,
                    6446,
                    6447,
                    6448,
                    6467,
                    6468,
                    6611,
                    6612,
                    6613,
                    6614,
                    7265,
                    7604,
                    7605,
                    7606,
                    8070,
                    8071,
                    8072
            },
            1,
            1,
            40,
            60
    ),
    SPIDER(4,
            "spider",
            new int[]{
                    626,
                    1683,
                    1684,
                    2477,
                    2478,
                    3016,
                    3017,
                    3018,
                    3019,
                    3020,
                    3021,
                    3022,
                    3023,
                    4533,
                    4561,
                    5238,
                    5243,
                    5373,
                    6267,
                    6271,
                    6504,
                    6610,
                    8137,
                    8138,
                    8703,
                    8713,
                    8714,
                    8715,
                    8978
            },
            1,
            1,
            60,
            100
    ),
    SPIRITUAL_CREATURES(89,
            "spiritualcreatures",
            new int[]{
                    2210,
                    2211,
                    2212,
                    2242,
                    2243,
                    2244,
                    3159,
                    3160,
                    3161,
                    3166,
                    3167,
                    3168
            },
            1,
            63,
            40,
            60
    )
    ;

    private String taskName;
    private int[] npcIds;
    private Position location;
    private Item[] requiredItems;
    private int combatRequirement;
    private int slayerRequirement;
    private int minAmount;
    private int maxAmount;
    private int taskId;

    KrystiliaTask(int taskId, String taskName, Position location, int[] npcIds, int combatRequirement, int slayerRequirement, int minAmount, int maxAmount) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.npcIds = npcIds;
        this.location = location;
        this.combatRequirement = combatRequirement;
        this.slayerRequirement = slayerRequirement;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.requiredItems = new Item[]{};
    }

    KrystiliaTask(int taskId, String taskName, int[] npcIds, int combatRequirement, int slayerRequirement, int minAmount, int maxAmount) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.npcIds = npcIds;
        this.location = new Position(0,0 ,0);
        this.combatRequirement = combatRequirement;
        this.slayerRequirement = slayerRequirement;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.requiredItems = new Item[]{};
    }

    public int[] getNpcIds() {
        return npcIds;
    }

    public int getCombatRequirement() {
        return combatRequirement;
    }

    public int getSlayerRequirement() {
        return slayerRequirement;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getTaskId() {
        return taskId;
    }

    public Position getLocation() {
        return location;
    }

    public Item[] getRequiredItems() {
        return requiredItems;
    }
}
