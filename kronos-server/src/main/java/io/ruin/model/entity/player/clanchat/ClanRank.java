package io.ruin.model.entity.player.clanchat;

public enum ClanRank {

    FRIEND,
    RECRUIT,
    CORPORAL,
    SERGEANT,
    LIEUTENANT,
    CAPTAIN,
    GENERAL,
    OWNER,
    ADMIN(127);

    public final int id;

    ClanRank() {
        this.id = this.ordinal();
    }

    ClanRank(int id) {
        this.id = id;
    }

    public static ClanRank get(int ordinal, ClanRank defaultRank) {
        ClanRank[] ranks = ClanRank.values();
        if (ordinal < 0 || ordinal >= ranks.length) {
            return defaultRank;
        }
        return ranks[ordinal];
    }
}
