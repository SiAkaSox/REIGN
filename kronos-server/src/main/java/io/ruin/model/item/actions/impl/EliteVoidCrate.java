package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;

public class EliteVoidCrate {

    static {
        ItemAction.registerInventory(7630, "search", (player, item) -> openCrate(player));
    }

    public static void openCrate(Player p) {
        p.getInventory().addOrDrop(11663, 1);
        p.getInventory().addOrDrop(11664, 1);
        p.getInventory().addOrDrop(11665, 1);
        p.getInventory().addOrDrop(13072, 1);
        p.getInventory().addOrDrop(13073, 1);
        p.getInventory().addOrDrop(8842, 1);
        p.getInventory().remove(7630, 1);
    }

}