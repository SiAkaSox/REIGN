package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;

public class MythCape {

    static {
        ItemAction.registerInventory(22114, "Teleport", (player, item) -> teleport(player));
    }

    public static void teleport(Player p) {
        p.getMovement().teleport(2457, 2851, 0);
    }



}