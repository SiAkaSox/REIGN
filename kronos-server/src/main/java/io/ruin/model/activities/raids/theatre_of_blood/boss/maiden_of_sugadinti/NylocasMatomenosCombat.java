package io.ruin.model.activities.raids.theatre_of_blood.boss.maiden_of_sugadinti;

import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.utility.Misc;

public class NylocasMatomenosCombat extends NPCCombat {

    private NylocasMatomenosPlugin n;

    NylocasMatomenosCombat(NylocasMatomenosPlugin nylocas) {
        this.n = nylocas;
    }

    @Override
    protected boolean withinDistance(int distance) {
        return distance < 2;
    }

    @Override
    public void init() {

    }

    @Override
    public void setTarget(Entity target) {
        if (target.isPlayer())
            return;
        super.setTarget(target);
    }

    @Override
    public void follow() {
        follow(3);
    }

    @Override
    public boolean attack() {
        if(!withinDistance(3)) {
            follow();
            return false;
        }
        return false;
    }
}
