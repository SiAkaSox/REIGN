package io.ruin.model.activities.mimic;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum Candy {

    PINK(1670, 1674),
    GREEN(1671, 1675),
    RED(1672, 1676),
    BLUE(1673, 1677);

    Candy(final int projectile, final int gfxId) {
        this.projectile = projectile;
        this.gfxId = gfxId;
    }

    @Getter public final int projectile;
    @Getter public final int gfxId;

    public static final List<Candy> CANDY_LIST = Arrays.asList(PINK, PINK, PINK, RED, GREEN, BLUE);

    public static List<Candy> getCandyList(int players) {
        int listSize = players >= 20 ? 12 : 6;
        List<Candy> candies = Lists.newLinkedList();
        for (int i = 0; i < listSize; i++) {
             candies.addAll(CANDY_LIST);
        }
        return candies;
    };
}
