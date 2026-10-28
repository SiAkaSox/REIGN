package io.ruin.model.item.actions.impl.chargable;

import io.ruin.utility.Random;
import io.ruin.cache.ItemDef;

public class StaffOfLightE {

    private static final int CHARGED = 31065;

    static {
        ItemDef.get(CHARGED).addPostTargetDefendListener((player, item, hit, target) -> {
            if(hit.attackSpell != null && Random.rollDie(4, 1))
                target.envenom(6);
        });
    }

}
