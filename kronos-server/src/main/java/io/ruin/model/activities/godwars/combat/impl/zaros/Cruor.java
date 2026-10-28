package io.ruin.model.activities.godwars.combat.impl.zaros;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Projectile;

public class Cruor extends NPCCombat {

    static final int ID = 11285;
    private static final Projectile PROJECTILE = new Projectile(374, 15, 30, 51, 56, 10, 16, 64);

    // max hit 29
    // spawns immune to damage
    // attacks with Blood Barrage and can heal himself a percentage of the damage inflicted.
    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(hit -> {
            if (npc.isInvincible())
                hit.nullify();
        });
    }

    @Override
    public void follow() {
        //follow(1);
    }

    @Override
    public boolean attack() {
        if (npc.isInvincible())
            return false;
        if (!withinDistance(10))
            return false;
        npc.publicSound(178, 1, 0);
        npc.animate(info.attack_animation);
        int delay = PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC, null)
                .randDamage(29)
                .clientDelay(delay);
        hit.postDamage(t -> {
            if (hit.damage > 0) {
                npc.publicSound(102, 1, 0);
                npc.incrementHp((int) (hit.damage * 0.25));
                t.graphics(377, 0, 0);
            } else {
                t.publicSound(227, 1, 0);
                t.graphics(85, 124, 0);
                hit.hide();
            }
        });
        target.hit(hit);
        return true;
    }

    public void process() {

    }
}
