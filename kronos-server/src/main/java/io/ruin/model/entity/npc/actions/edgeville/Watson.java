package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.cache.ItemDef;
import io.ruin.model.achievements.listeners.hard.OnAConquest;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemNPCAction;

import static io.ruin.cache.ItemID.COINS_995;

public class Watson {
    static {
        int[][] clues = {
                {2677, 20546}, //Easy
                {2801, 20545}, //Medium
                {2722, 20544}, //Hard
                {12073, 20543}, //Elite
                {19835, 19836} // Master
        };
        for (int[] clue : clues) {
            int id = clue[0];
            int id2 = clue[1];
            ItemDef.get(id).sigmundBuyPrice = clue[1];
            ItemNPCAction.register(id, 7303, (player, item, npc) -> player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Trade your " + ItemDef.get(id).name +
                    " for a "+ItemDef.get(id2).name+"?", item, () -> {
                int amount = player.getInventory().getAmount(clue[0]);
                item.remove(amount);
                player.getInventory().add(id2, amount);
                player.dialogue(new NPCDialogue(npc, "Excellent find, " + player.name() + "! If you find anymore clues, you know where to find me!"));
            })));
        }
        NPCAction.register(7303, "talk-to", (player, npc) -> {
            player.dialogue(new NPCDialogue(npc, "Hello, adventurer. How may I help you?"),
                    new OptionsDialogue(
                            new Option("What do you do here?", () -> {
                                player.dialogue(
                                        new NPCDialogue(npc, "Players can bring me one of each type of clue scroll and I will exchange them for a master clue!")
                                );
                            }),
                            new Option("I want to trade my clue for a casket", () -> tradeClueForCasket(player)),
                            new Option("I'd like to exchange my clues for a master clue!", plr -> exchangeClues(player, npc)),
                            new Option("Nevermind")
                    ));
        });
    }

    public static void tradeClueForCasket(Player p) {
        p.dialogue(
                new NPCDialogue(7303, "If you'd like to exchange your clue scrolls for caskets, simply use the clue on me and I will trade you a casket for it!")
        );
    }

    public static void exchangeClues(Player player, NPC npc) {
        if (player.getInventory().contains(2677, 1) &&
                player.getInventory().contains(2801, 1) &&
                player.getInventory().contains(2722, 1) &&
                player.getInventory().contains(12073, 1)) {
            player.getInventory().remove(2677, 1);
            player.getInventory().remove(2801, 1);
            player.getInventory().remove(2722, 1);
            player.getInventory().remove(12073, 1);
            player.getInventory().add(19835, 1);
            player.dialogue(
                    new MessageDialogue("Watson exchanges your clues for a master clue!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required clues to do this! You need one easy, medium, hard, and elite clue to trade for a master clue.")
            );
        }
    }
}