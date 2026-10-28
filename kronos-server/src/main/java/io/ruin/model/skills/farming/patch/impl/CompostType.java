package io.ruin.model.skills.farming.patch.impl;

import lombok.Getter;

@Getter
public enum CompostType {

    NOTHING(-1, -1, -1),
    COMPOST(6032, 0, 4.5),
    SUPERCOMPOST(6034, 1, 8.5),
    ROTTEN_TOMATOES(2518, 2, 4.5),
    ULTRACOMPOST(21483, 3, 10);

    private int itemId, type;
    private double experience;

    CompostType(int itemId, int type, double experience) {
        this.itemId = itemId;
        this.type = type;
        this.experience = experience;
    }
}
