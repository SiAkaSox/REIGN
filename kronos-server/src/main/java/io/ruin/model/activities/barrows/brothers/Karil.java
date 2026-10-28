package io.ruin.model.activities.barrows.brothers;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class Karil extends NPCCombat {

    @Override
    public void init() {
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if(!withinDistance(8))
            return false;
        projectileAttack(Projectile.BOLT, info.attack_animation, AttackStyle.RANGED, info.max_damage);

        return true;
    }

}