package io.ruin.model.activities.raids.theatre_of_blood.boss.xarpus;

import io.ruin.model.activities.miscpvm.BasicCombat;
import io.ruin.model.entity.Entity;

public class XarpusCombat extends BasicCombat {


    @Override
    public boolean canAttack(Entity target) {
        XarpusPlugin xarpus = (XarpusPlugin) npc;
        if (xarpus.getQuadrant() != -1 || xarpus.getId() == 8338) {
            return false;
        }
        return super.canAttack(target);
    }

    @Override
    public boolean hasAttackDelay() {
        return attackDelayTicks != 0;
    }
}
