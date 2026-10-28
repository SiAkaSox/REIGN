package io.ruin.model.entity.npc.actions.wilderness;

import io.ruin.Server;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.shop.ShopManager;

import static io.ruin.Server.STARTUP_THREAD;

public class BootyBayShops {

    static {

            NPCAction.register(4814, "talk-to", (p, npc) -> openShop(p));
            NPCAction.register(4028, "talk-to", (p, npc) -> ShopManager.openIfExists(p, "e522b145-fbaa-4746-8d71-6ad9808d2338"));
    }

    public static void openShop(Player p) {
        ShopManager.openIfExists(p, "5e31b723-6faf-4f79-bb5d-b8967e35c165");
    }

}