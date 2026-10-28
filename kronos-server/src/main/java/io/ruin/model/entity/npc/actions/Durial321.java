package io.ruin.model.entity.npc.actions;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.utility.Misc;

import static io.ruin.model.skills.magic.spells.TargetSpell.hold;

public class Durial321 extends NPCCombat {

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(Misc.random(1, 3));
    }

    @Override
    public boolean attack() {
        double chance = Math.random();
        if (target == null || target.dead())
            return false;
        npc.face(target);
        follow();

        if (target.getPosition().distance(npc.getPosition()) > 10) {
            npc.getMovement().teleport(target.getAbsX(), target.getAbsY());
            follow(1);
        }

        if (Misc.random(5) == 1) {
            npc.forceText("Hhhhhhhhhhhhh");
        }

        if (chance <= .8 && target.getPosition().distance(npc.getPosition()) == 1) {
            basicAttack(1658, AttackStyle.SLASH, 30);
        } else {
            if (withinDistance(7)) {
                int delay = new Projectile(368, 43, 0, 51, 56, 10, 16, 64).skipTravel().send(npc, target);
                target.publicSound(171, 1, 0);
                npc.animate(1979);
                npc.addEvent(e -> {
                    e.delay(((delay * 25) / 600 - 1));
                    if (target == null || target.dead()) {
                        System.err.println(target == null);
                        return;
                    }
                    Hit myHit = new Hit(npc, AttackStyle.MAGIC).randDamage(0, info.max_damage);
                    if (!myHit.isBlocked()) {
                        hold(myHit, target, 20, true);
                        target.graphics(369);
                        target.publicSound(168);
                        target.hit(myHit);
                    }

                });
            }
        }
        return true;
    }
}
