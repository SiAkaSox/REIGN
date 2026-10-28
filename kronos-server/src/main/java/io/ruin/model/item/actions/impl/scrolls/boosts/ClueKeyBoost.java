package io.ruin.model.item.actions.impl.scrolls.boosts;

import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;

public class ClueKeyBoost {

    private static final int CLUE_KEY_BOOST_SCROLL = 20238;

    static {
        ItemAction.registerInventory(CLUE_KEY_BOOST_SCROLL, "read", (player, item) -> {
            if(player.cluekeyBoost.isDelayed()) {
                player.dialogue(new MessageDialogue("You already have the clue key boost activated!"));
                return;
            }
            player.dialogue(new OptionsDialogue("Are you sure you want to active this boost?",
                    new Option("Yes, active my clue key boost!", () -> {
                        item.remove();
                        player.cluekeyBoost.delaySeconds(60 * 30);
                        player.dialogue(new ItemDialogue().one(item.getId(), "You have activated your clue key boost. You now " +
                                "have a 25% higher chance of receiving wilderness clue keys for the next 30 minutes!"));
                    }),
                    new Option("No, I'm not ready yet!")));
        });
        LoginListener.register(player -> {
            if (player.cluekeyboostTimeLeft > 0) {
                player.cluekeyBoost.delay(player.cluekeyboostTimeLeft);
                player.cluekeyboostTimeLeft = 0;
            }
        });
    }
}
