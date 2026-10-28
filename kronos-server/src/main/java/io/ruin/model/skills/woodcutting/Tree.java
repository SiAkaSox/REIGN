package io.ruin.model.skills.woodcutting;

import io.ruin.model.entity.player.PlayerCounter;

public enum Tree {

    REGULAR(1511, 1512,"logs", 1, 82.5, 25.0, 75, -1, true, 31764, PlayerCounter.CHOPPED_REGULAR),
    SAPLING(20799, -1,"kindling", 1, 82.5, 45.0, 75, -1, false, 31764, PlayerCounter.CHOPPED_SAPLING),
    ACHEY(2862, 2863, "achey logs", 1, 55, 25.0, 75, -1, true, 31764, PlayerCounter.CHOPPED_ACHEY),
    OAK(1521, 1522,"oak logs", 15, 95, 37.5, 15, 6043, false, 36114, PlayerCounter.CHOPPED_OAK),
    WILLOW(1519, 1520,"willow logs", 30, 140, 67.5, 10, 6045, false, 28928, PlayerCounter.CHOPPED_WILLOW),
    TEAK(6333, 6334,"teak logs", 35, 140, 85.0, 10, -1, false, 28928, PlayerCounter.CHOPPED_TEAK),
    JUNIPER(13355, -1,"juniper logs", 42, 150, 35.0, 30, -1, false, 36000, PlayerCounter.CHOPPED_JUNIPER),
    MAPLE(1517, 1518, "maple logs", 45, 180, 100.0, 60, 6047, false, 22191, PlayerCounter.CHOPPED_MAPLE),
    MAHOGANY(6332, 8836, "mahogany logs", 50, 200, 125.0, 80, -1, false, 16541, PlayerCounter.CHOPPED_MAHOGANY),
    YEW(1515, 1516, "yew logs", 60, 225, 175.0, 100, 6049, false, 14501, PlayerCounter.CHOPPED_YEW),
    MAGIC(1513, 1514, "magic logs", 75, 340, 250.0, 100, 6051, false, 7232, PlayerCounter.CHOPPED_MAGIC),
    REDWOOD(19669, 19670, "redwood logs", 90, 460, 380.0, 200, -1, false, 6200, PlayerCounter.CHOPPED_REDWOOD),
    CORRUPT_TREE(30105, -1 , "corrupt logs", 95, 340, 300.0, 100, -1, false, 6200, PlayerCounter.CHOPPED_CORRUPT);

    public final int log, notedLog, levelReq, respawnTime, petOdds;
    public final double experience, difficulty;
    public final String treeName;
    public final boolean single;
    public final PlayerCounter counter;
    public int roots;

    Tree(int log, int notedLog, String treeName, int levelReq, double difficulty, double experience, int respawnTime, int roots, boolean single, int petOdds, PlayerCounter counter) {
        this.log = log;
        this.notedLog = notedLog;
        this.treeName = treeName;
        this.levelReq = levelReq;
        this.difficulty = difficulty;
        this.experience = experience;
        this.respawnTime = respawnTime;
        this.roots = roots;
        this.single = single;
        this.petOdds = petOdds;
        this.counter = counter;
    }
}
