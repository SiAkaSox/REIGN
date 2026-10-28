package io.ruin.model.activities.mimic;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class ThirdAgeMage extends NPCCombat {

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(9);
    }

    @Override
    public boolean attack() {
        if (target == null || target.dead())
            return false;
        if (withinDistance(9)) {
            int delay = new Projectile(139, 43, 31, 51, 56, 10, 16, 64).send(npc, target);
            npc.graphics(138, 92, 0);
            npc.animate(1162);
            npc.startEvent(e -> {
                e.delay((delay * 25) / 600 - 1);
                if (target == null || target.dead())
                    return;
                target.graphics(140, 124, 0);
                target.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(0, info.max_damage));
            });
        } else
            follow();
        return true;
    }
}
