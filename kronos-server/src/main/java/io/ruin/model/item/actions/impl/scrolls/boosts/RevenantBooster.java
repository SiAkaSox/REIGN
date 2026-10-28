package io.ruin.model.item.actions.impl.scrolls.boosts;

import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;

public class RevenantBooster {

    private static final int REVENANT_BOOST_SCROLL = 25087;

    static {
        ItemAction.registerInventory(REVENANT_BOOST_SCROLL, "read", (player, item) -> {
            if(player.revenantBoost.isDelayed()) {
                player.dialogue(new MessageDialogue("You already have the revenant boost activated!"));
                return;
            }
            player.dialogue(new OptionsDialogue("Are you sure you want to active this boost?",
                    new Option("Yes, active my revenant boost!", () -> {
                        item.remove();
                        player.revenantBoost.delaySeconds(60 * 30);
                        player.dialogue(new ItemDialogue().one(item.getId(), "You have activated your revenant boost. You now have x2 chance of receiving pvp weapon drops for the next 30 minutes."));
                    }),
                    new Option("No, I'm not ready yet!")));
        });
        LoginListener.register(player -> {
            if (player.revenantBoostTimeLeft > 0) {
                player.revenantBoost.delay(player.revenantBoostTimeLeft);
                player.revenantBoostTimeLeft = 0;
            }
        });
    }
}
