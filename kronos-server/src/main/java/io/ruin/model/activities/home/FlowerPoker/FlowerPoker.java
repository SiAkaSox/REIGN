package io.ruin.model.activities.home.FlowerPoker;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;

import java.util.Random;
import java.util.Scanner;

public class FlowerPoker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Flower Poker!");

        System.out.println("Players, type 'start' when you're ready to begin.");
        waitForInput();

        FlowerHand player1Hand = plantFlowers("Player 1", new Player());
        FlowerHand player2Hand = plantFlowers("Player 2", new Player());

        System.out.println("Player 1's hand: " + player1Hand);
        System.out.println("Player 2's hand: " + player2Hand);

        int winner = determineWinner(player1Hand, player2Hand);
        System.out.println("Player " + winner + " wins!");

        scanner.close();
    }

    private static void waitForInput() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.nextLine().equalsIgnoreCase("start")) {
            System.out.println("Invalid input. Type '" + "start" + "' to continue.");
        }
    }

    private static FlowerHand plantFlowers(String playerName, Player player) {
        System.out.println(playerName + ", it's time to plant your flowers!");
        initiatePlanting(player);
        return player.getFlowerHand();
    }

    private static void initiatePlanting(io.ruin.model.entity.player.Player player) {
        player.lock(LockType.FULL);
        // Add player force-walk here
        System.out.println(player.name() + " is walking to a planting location.");
        // player.walkTo(x, y);

        for (int i = 0; i < 5; i++) {
            Flowers flower = Flowers.getRandomFlower();
            System.out.println(player.name() + " is planting a " + flower + ".");
            plant(player, flower);
        }

        player.unlock();
        FlowerHand playerHand = player.getFlowerHand();
        HandOutcome outcome = playerHand.getHandOutcome();

        if (outcome == HandOutcome.FIVE_OF_A_KIND) {
            giveReward(player, Reward.FIVE_OF_A_KIND);
        } else if (outcome == HandOutcome.FOUR_OF_A_KIND) {
            giveReward(player, Reward.FOUR_OF_A_KIND);
        } else if (outcome == HandOutcome.THREE_OF_A_KIND) {
            giveReward(player, Reward.THREE_OF_A_KIND);
        } else if (outcome == HandOutcome.ONE_PAIR) {
            giveReward(player, Reward.ONE_PAIR);
        } else {
            System.out.println("No winner. Restarting the game.");
            // Restart the game by simulating walking back to the starting coordinates
            initiatePlanting(player);
        }
    }

    private static void giveReward(Player player, Reward reward) {
        System.out.println(player.name() + " wins! Reward: " + reward);
        // player.giveReward(reward);
    }

    private static int determineWinner(FlowerHand hand1, FlowerHand hand2) {
        HandOutcome outcome1 = hand1.getHandOutcome();
        HandOutcome outcome2 = hand2.getHandOutcome();

        int outcomeComparison = outcome2.compareTo(outcome1);
        if (outcomeComparison == 0) {
            return outcome1.compareHands(hand1, hand2);
        }
        return outcomeComparison;
    }

    public static void plant(Player player, Flowers flower) {
        //flower planting method
    }

}


    enum HandOutcome {
        FIVE_OF_A_KIND, FOUR_OF_A_KIND, THREE_OF_A_KIND, ONE_PAIR, HIGH_CARD;

    public int compareHands(FlowerHand hand1, FlowerHand hand2) {
        return hand1.compareHands(hand2);
        }
    }

    enum Reward {
        FIVE_OF_A_KIND, FOUR_OF_A_KIND, THREE_OF_A_KIND, ONE_PAIR;
    }

    enum Flowers {
        ROSE, DAFFODIL, TULIP, LILY, ORCHID;

        private static final Random RANDOM = new Random();

        public static Flowers getRandomFlower() {
            return values()[RANDOM.nextInt(values().length)];
        }
}