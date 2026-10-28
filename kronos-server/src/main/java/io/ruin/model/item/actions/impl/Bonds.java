package io.ruin.model.item.actions.impl;

import io.ruin.Server;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

import static io.ruin.Server.STARTUP_THREAD;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/20/2020
 */
public class Bonds {

    private static void redeem(Player player, int amount, int tokens, Item item) {
        player.dialogue(new ItemDialogue().one(item.getId(), "Redeeming this bond will award you "+tokens+" donator<br>" +
                        "tokens, and add $"+amount+" to your total amount donated.<br>"),
                new OptionsDialogue(
                        new Option("Yes!", (player1 -> {
                            player.getInventory().remove(item);
                            player.getInventory().addOrDrop(20527, tokens);
                            player.sendMessage("You have redeemed a $"+amount+" Bond! Your new total donated is: $"+player.storeAmountSpent);
                        })),
                        new Option("I'll keep it for now.", player::closeDialogue)
                ));
    }

    static {

            ItemAction.registerInventory(30276, 1, ((player, item) -> redeem(player, 5, 50, item)));
            ItemAction.registerInventory(30279, 1, ((player, item) -> redeem(player, 10, 100, item)));
            ItemAction.registerInventory(30282, 1, ((player, item) -> redeem(player, 25, 250, item)));
            ItemAction.registerInventory(30285, 1, ((player, item) -> redeem(player, 50, 500, item)));
            ItemAction.registerInventory(30288, 1, ((player, item) -> redeem(player, 100, 1000, item)));
            ItemAction.registerInventory(30291, 1, ((player, item) -> redeem(player, 250, 2500, item)));
            ItemAction.registerInventory(30294, 1, ((player, item) -> redeem(player, 500, 5000, item)));
    }
}
