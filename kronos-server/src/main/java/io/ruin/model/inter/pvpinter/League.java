package io.ruin.model.inter.pvpinter;

import lombok.Getter;

public enum League {

    PLATINUM(2000, 100_000),
    GOLD(1500, 1999),
    SILVER(500, 1499),
    BRONZE(0, 499);

    @Getter
    public int minRating, maxRating;

    League(int minRating, int maxRating) {
        this.minRating = minRating;
        this.maxRating = maxRating;
    }
}
