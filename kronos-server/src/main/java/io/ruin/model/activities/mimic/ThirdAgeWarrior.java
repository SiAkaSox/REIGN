package io.ruin.model.activities.mimic;

import io.ruin.model.entity.npc.NPCCombat;

public class ThirdAgeWarrior extends NPCCombat {

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (withinDistance(1)) {
            basicAttack();
        } else
            follow();
        return true;
    }
}
