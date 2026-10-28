package io.ruin.model.activities.mimic;

import io.ruin.model.entity.npc.NPCCombat;

public class ThirdAgeRanger extends NPCCombat {

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(7);
    }

    @Override
    public boolean attack() {
        if (withinDistance(7)) {
            basicAttack();
        } else
            follow();
        return true;
    }
}
