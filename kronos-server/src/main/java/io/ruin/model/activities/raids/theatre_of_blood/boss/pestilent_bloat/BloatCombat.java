package io.ruin.model.activities.raids.theatre_of_blood.boss.pestilent_bloat;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;

public class BloatCombat extends NPCCombat {

    @Override
    public void init() {

    }

    @Override
    public void follow() {

    }

    @Override
    public boolean attack() {
        return true;
    }

    @Override
    public void setTarget(Entity target) {
        reset();
    }

    @Override
    public void follow(int dist) {
    }

}