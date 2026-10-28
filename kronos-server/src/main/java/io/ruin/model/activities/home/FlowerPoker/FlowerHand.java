package io.ruin.model.activities.home.FlowerPoker;

import java.util.Arrays;

public class FlowerHand {
    private final Flowers[] flowers;

    public FlowerHand(Flowers[] flowers) {
        this.flowers = flowers;
    }

    public int getFlowerCount() {
        return flowers.length;
    }

    public HandOutcome getHandOutcome() {
        Arrays.sort(flowers);

        if (hasFiveOfAKind()) {
            return HandOutcome.FIVE_OF_A_KIND;
        } else if (hasFourOfAKind()) {
            return HandOutcome.FOUR_OF_A_KIND;
        } else if (hasThreeOfAKind()) {
            return HandOutcome.THREE_OF_A_KIND;
        } else if (hasPair()) {
            return HandOutcome.ONE_PAIR;
        } else {
            return HandOutcome.HIGH_CARD;
        }
    }

    public int compareHands(FlowerHand otherHand) {
        Arrays.sort(flowers);
        Arrays.sort(otherHand.flowers);

        for (int i = flowers.length - 1; i >= 0; i--) {
            int comparison = flowers[i].compareTo(otherHand.flowers[i]);
            if (comparison != 0) {
                return comparison;
            }
        }

        return 0;
    }

    private boolean hasFiveOfAKind() {
        return flowers[0] == flowers[4];
    }

    private boolean hasFourOfAKind() {
        return flowers[0] == flowers[3] || flowers[1] == flowers[4];
    }

    private boolean hasThreeOfAKind() {
        return flowers[0] == flowers[2] || flowers[1] == flowers[3] || flowers[2] == flowers[4];
    }

    private boolean hasPair() {
        return flowers[0] == flowers[1] || flowers[1] == flowers[2] || flowers[2] == flowers[3] || flowers[3] == flowers[4];
    }
}