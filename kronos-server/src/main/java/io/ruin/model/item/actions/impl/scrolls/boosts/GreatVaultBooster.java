package io.ruin.model.item.actions.impl.scrolls.boosts;

import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;

public class GreatVaultBooster {

    private static final int GREAT_VAULT_SCROLL = 24460;

    static {
        ItemAction.registerInventory(GREAT_VAULT_SCROLL, "read", (player, item) -> {
            if(player.greatvaultBoost > 0) {
                player.dialogue(new MessageDialogue("You already have the great vault boost activated!"));
                return;
            }
            player.dialogue(new OptionsDialogue("Are you sure you want to active this boost?",
                    new Option("Yes, active my great vault boost!", () -> {
                        item.remove();
                        player.greatvaultBoost = 5;
                        player.dialogue(new ItemDialogue().one(item.getId(), "You have activated your great vault boost. Your next 5 great vault keys will give 2 loot rolls per key."));
                    }),
                    new Option("No, I'm not ready yet!")));
        });
    }
}
