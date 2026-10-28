package io.ruin.model.activities.wilderness.pvp.bounty;

import lombok.Getter;

public enum BountySkull {

    RED(10_000_000, 2, 4, "<col=dc1a0b>"),
    BLUE(7_500_000, 3, 5, "<col=0baddc>"),
    GREEN(5_000_000, 4, 6, "<col=0bdc31>"),
    SILVER(2_500_000, 5, 7, "<col=c8cfc9>"),
    BRONZE(1_000_000, 7, 8, "<col=964706>");

    @Getter
    public int requiredAmount, skullId, crownImg;
    @Getter
    public String colourCode;

    BountySkull(int requiredAmount, int skullId, int crownImg, String colourCode) {
        this.requiredAmount = requiredAmount;
        this.skullId = skullId;
        this.crownImg = crownImg;
        this.colourCode = colourCode;
    }
}

