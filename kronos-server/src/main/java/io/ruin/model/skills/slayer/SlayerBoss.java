package io.ruin.model.skills.slayer;

import java.util.Arrays;

public enum SlayerBoss {
    KREE_ARRA(0, 3162, 6492),
    COMMANDER_ZILYANA(1, 2205, 6493),
    GENERAL_GRAARDOR(2, 2215, 6494),
    KRIL_TSUTSAROTH(3, 3129, 6495),
    DAGANNOTH_KINGS(4, 2266, 6497, 2267, 6498, 2265, 6496),
    THE_GIANT_MOLE(5, 5779, 6499),
    THE_KALPHITE_QUEEN(6, 963, 965),
    THE_KING_BLACK_DRAGON(7, 239, 6502),
    CALLISTO(true, 8, 6503, 6609),
    VENENATIS(true, 9, 6504, 6610),
    VET_ION(true, 10, 6611),
    THE_CHAOS_ELEMENTAL(true, 11, 2054, 6505),
    THE_CHAOS_FNATIC(true, 12, 6619),
    CRAZY_ARCHAEOLOGIST(true, 13, 6618),
    SCORPIA(true, 14, 6615),
    ZULRAH(15, 2042, 2043, 2044),
    BARROWS_BROTHERS(16, 1675, 1673, 1677, 1672, 1676, 1674),
//    THE_GROTESQUE_GUARDIANS(21),
    VORKATH(22, 8059, 8061),
//    SARACHNIS(24),

    ;
    public final int[] ids;
    public boolean wilderness;
    public int configId;


    SlayerBoss(int configId, int... ids){
        this.configId = configId;
        this.wilderness = false;
        this.ids = ids;
    }

    SlayerBoss(boolean wilderness, int configId, int... ids){
        this.configId = configId;
        this.wilderness = wilderness;
        this.ids = ids;
    }

    public static SlayerBoss getSlayerBoss(int configID) {
        return Arrays.stream(SlayerBoss.values()).filter(sb -> sb.configId == configID).findFirst().orElse(null);
    }

    public boolean isTask(int id) {
        for(int npcId : ids) {
            if(npcId == id) {
                return true;
            }
        }
        return false;
    }
}
