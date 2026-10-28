package io.ruin.model.skills.fishing;

public enum FishingCatch {
    /**
     * Regular
     */
    SHRIMPS(317, 315, 1, 10.0, 0.6, 50000),
    SARDINE(327, 325, 5, 20.0, 0.6, 49000),
    HERRING(345, 347, 10, 30.0, 0.6, 47000),
    ANCHOVIES(321, 319, 15, 40.0, 0.6, 46000),
    MACKEREL(353, 355, 16, 20.0, 0.6, 45000),
    TROUT(335, 333, 20, 50.0, 0.7, 46000),
    COD(341, 339, 23, 45.0, 0.7, 37000),
    PIKE(349, 351, 25, 60.0, 0.7, 35000),
    SALMON(331, 329, 30, 70.0, 0.7, 30000),
    TUNA(359, 361, 35, 80.0, 0.6, 30000),
    LOBSTER(377, 379, 40, 90.0, 0.6, 28000),
    BASS(363, 365, 46, 100.0, 0.6, 27000),
    SWORDFISH(371, 373, 50, 100.0, 0.6, 24000),
    MONKFISH(7944, 7946, 62, 110.0, 0.6, 24000),
    SHARK(383, 385, 76, 125.0, 0.3, 20000),
    ANGLERFISH(13439, 13441, 82, 140.0, 0.18, 20000),
    DARK_CRAB(11934, 11936, 85, 150.0, 0.3, 20000),
    MINNOWS(21356, 21356, 82, 26.0, 1.0, 100000),
    INFERNAL_EEL(21293, 21293, 80, 95.0, 0.3, 10000),
    /**
     * Barbarian
     */
    LEAPING_TROUT(11328, 11328, 48, 15, 15, 50.0, 5.0, 0.65, 50000),
    LEAPING_SALMON(11330, 11330, 58, 30, 30, 70.0, 6.0, 0.45, 50000),
    LEAPING_STURGEON(11332, 11332, 70, 45, 45, 80.0, 7.0, 0.35, 50000),
    /**
     * Barehand
     */
    BARBARIAN_TUNA(359, 361, 55, 15, 35, 80.0, 8.0, 0.6, 50000),
    BARBARIAN_SWORDFISH(371, 373, 70, 15, 50, 100.0, 10.0, 0.6, 50000),
    BARBARIAN_SHARK(383, 385, 96, 15, 76, 110.0, 11.0, 0.6, 50000),
    KARAMBWAN(3142, 3144, 65, 50.0, 0.4, 24000),
    MOLTEN_EEL(30086, 30089, 65, 50.0, 0.4, 24000);

    public final int id;

    public final int cookedId;

    public final int levelReq;

    public final int agilityReq;

    public final int strengthReq;

    public final double xp;

    public final double barbarianXp;

    public final double baseChance;

    public final int petOdds;

    FishingCatch(int id, int cookedId, int levelReq, double xp, double baseChance, int petOdds) {
        this(id, cookedId, levelReq, 0, 0, xp, 0.0, baseChance, petOdds);
    }

    FishingCatch(int id, int cookedId, int levelReq, int agilityReq, int strengthReq, double xp, double barbarianXp, double baseChance, int petOdds) {
        this.id = id;
        this.cookedId = cookedId;
        this.levelReq = levelReq;
        this.agilityReq = agilityReq;
        this.strengthReq = strengthReq;
        this.xp = xp;
        this.barbarianXp = barbarianXp;
        this.baseChance = baseChance;
        this.petOdds = petOdds;
    }

}
