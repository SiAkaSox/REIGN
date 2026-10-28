package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.shop.ShopManager;

public class PkpShop {

    private final static int MANDRITH = 6599;

    static {
        NPCAction.register(MANDRITH, "Talk-to", (p, n) -> ShopManager.openIfExists(p, "183ba085-4bec-44e0-96b5-50752b4a7ec9"));
    }
}
