package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;

public class GhommalsHilts {

    static {
        ItemAction.registerInventory(25936, 4, (player, item) -> {
            morulrekTele(player);
        });
    }

    public static void morulrekTele(Player p) {
        p.startEvent(event -> {
            p.animate(9131);
            p.graphics(1945);
            event.delay(4);
            p.animate(9133);
            p.graphics(1946);
            p.graphics(1946);
            event.delay(1);
            p.animate(-1);
            p.getMovement().teleport(2495, 5099, 0);
        });
    }

}