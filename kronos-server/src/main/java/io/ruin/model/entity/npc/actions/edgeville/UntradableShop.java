package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.shared.listeners.SpawnListener;

public class UntradableShop {

    private final static int SHOPKEEPER = 4225;

    static {
        SpawnListener.register(SHOPKEEPER, npc -> npc.skipReachCheck = p -> p.equals(3078, 3507));
        SpawnListener.register(1307, npc -> npc.skipReachCheck = p -> p.equals(3076, 3512));
        SpawnListener.register(3216, npc -> npc.skipReachCheck = p -> p.equals(3076, 3510));
        SpawnListener.register(7045, npc -> npc.skipReachCheck = p -> p.equals(3076, 3511));
        SpawnListener.register(2814, npc -> npc.skipReachCheck = p -> p.equals(3076, 3509));
        SpawnListener.register(7663, npc -> npc.skipReachCheck = p -> p.equals(3083, 3484));
    }
}
