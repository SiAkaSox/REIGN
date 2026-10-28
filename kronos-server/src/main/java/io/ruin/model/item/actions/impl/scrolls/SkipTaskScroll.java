package io.ruin.model.item.actions.impl.scrolls;

import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;

public class SkipTaskScroll {

    private static final int SKIP_SCROLL = 786;

    static {
        ItemAction.registerInventory(SKIP_SCROLL, "read", (player, item) -> {
            int left = Config.SLAYER_TASK_AMOUNT.get(player);
            if (left == 0) {
                player.dialogue(new MessageDialogue("You do not currently have a slayer task to cancel."));
                return;
            }
            player.dialogue(new OptionsDialogue("Cancel your slayer task?",
                    new Option("Yes, cancel my task", () -> {
                        item.remove(1);
                        Config.SLAYER_TASK_AMOUNT.set(player, 0);
                        Config.SLAYER_TASK.set(player, 0);
                        Config.BOSS_TASK.set(player, 0);
                        player.dialogue(new ItemDialogue().one(item.getId(), "You have successfully cancelled your task!"));
                    }),
                    new Option("Nevermind")));
        });
    }

}
