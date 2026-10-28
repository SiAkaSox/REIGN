package io.ruin.model.activities.godwars.combat.impl.zaros;

import io.ruin.cache.NPCDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Projectile;

public class Fumus extends NPCCombat {
    static final int ID = 11283;
    private static final Projectile PROJECTILE = new Projectile(390, 43, 31, 51, 56, 10, 16, 64).skipTravel();

    static {
        NPCDef def = NPCDef.get(ID);
        def.swimClipping = true;
    }

    // max hit 29
    // spawns immune to damage
    // attacks with smoke barrage and can poison players
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
        npc.publicSound(183, 1, 0);
        npc.animate(info.attack_animation);
        int delay = PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC, null)
                .randDamage(29)
                .clientDelay(delay);
        hit.postDamage(t -> {
            if (hit.damage > 0) {
                npc.publicSound(180, 1, 0);
                t.graphics(391, 0, 0);
                t.player.poison(4);
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
