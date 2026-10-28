package io.ruin.model.item.actions.impl.scrolls.boosts;

import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;

public class BloodMoneyBooster {

    private static final int BLOOD_MONEY_BOOST_SCROLL = 26500;

    static {
        ItemAction.registerInventory(BLOOD_MONEY_BOOST_SCROLL, "read", (player, item) -> {
            if(player.bloodmoneyBoost.isDelayed()) {
                player.dialogue(new MessageDialogue("You already have the blood money boost activated!"));
                return;
            }
            player.dialogue(new OptionsDialogue("Are you sure you want to active this boost?",
                    new Option("Yes, active my blood money boost!", () -> {
                        item.remove();
                        player.bloodmoneyBoost.delaySeconds(60 * 30);
                        player.dialogue(new ItemDialogue().one(item.getId(), "You have activated your blood money boost. Your random " +
                                "blood money drop amounts will now be doubled for the next 30 minutes!"));
                    }),
                    new Option("No, I'm not ready yet!")));
        });
        LoginListener.register(player -> {
            if (player.bloodmoneyBoostTimeLeft > 0) {
                player.bloodmoneyBoost.delay(player.bloodmoneyBoostTimeLeft);
                player.bloodmoneyBoostTimeLeft = 0;
            }
        });
    }
}
