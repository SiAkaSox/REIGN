package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemItemAction;

public class ZaryteCrossbowCombine {

    static {
        ItemItemAction.register(26372, 11785, (player, primary, secondary) -> dialogue(player));
    }

    public static void dialogue(Player p) {
        p.dialogue(
                new ItemDialogue().one(26372, "Do you wish to craft a Zaryte Crossbow?"),
                new OptionsDialogue("Select an option",
                        new Option("Yes, continue", () -> craft(p)),
                        new Option("Nevermind")
                )
        );
    }

    public static void craft(Player p) {
        boolean hasItems = p.getInventory().contains(26372) && p.getInventory().contains(26231, 250) && p.getInventory().contains(11785);
        if (!hasItems) {
            p.sendMessage("You do not have the correct items to craft the crossbow.");
        } else {
            p.getInventory().remove(26372, 1);
            p.getInventory().remove(26231, 250);
            p.getInventory().remove(11785, 1);
            p.animate(4462);
            p.graphics(759);
            p.getInventory().add(26374, 1);
            p.dialogue(
                    new ItemDialogue().one(26374, "You craft the items into a Zaryte Crossbow!")
            );
        }
    }

}