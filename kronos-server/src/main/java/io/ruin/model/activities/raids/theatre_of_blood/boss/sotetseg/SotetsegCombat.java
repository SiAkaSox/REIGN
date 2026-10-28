package io.ruin.model.activities.raids.theatre_of_blood.boss.sotetseg;

import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Misc;

import java.util.ArrayList;
import java.util.List;

public class SotetsegCombat extends NPCCombat {
    @Override
    public void init() {

    }

    @Override
    public void follow() {

    }

    @Override
    public boolean attack() {
        SotetsegPlugin sotetseg = (SotetsegPlugin) npc;
        List<Entity> targets = sotetseg.getPossibleTargets(24, true, false);
        List<Entity> melee_targets = new ArrayList<Entity>();
        for (Entity p : targets) {
            if (p.getPosition().distance(npc.getCentrePosition()) <= 3) {
                melee_targets.add(p);
            }
        }
        if (melee_targets.size() > 0) {
            npc.animate(sotetseg.getCombat().getInfo().attack_animation);
            World.startEvent(e -> {
                e.delay(1);
                for (Entity p : melee_targets) {
                    p.hit(target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE) ? new Hit().fixedDamage(Misc.random(22)) : new Hit().randDamage(45));
                }
            });
            npc.getCombat().delayAttack(5);
            return true;
        }
        npc.animate(sotetseg.getCombat().getInfo().attack_animation + 1);
        if (targets.size() > 0) {
            boolean magic = Misc.random(10) > 5;
            for (Entity p : targets) {
                Projectile PROJECTILE = new Projectile(magic ? 1382 : 1379, 110, 0, 0, 60, 0, 15, 0);
                PROJECTILE.send(sotetseg, p);
                int delay = 100;
                if (sotetseg.getCentrePosition().distance(p.getPosition()) == 1) {
                    delay = 60;
                } else if (sotetseg.getCentrePosition().distance(p.getPosition()) <= 5) {
                    delay = 80;
                } else if (sotetseg.getCentrePosition().distance(p.getPosition()) <= 8) {
                    delay = 100;
                } else {
                    delay = 120;
                }
                final int del = delay / 20;
                World.startEvent(e -> {
                    e.delay(del);
                    p.hit(target.player.getPrayer().isActive(magic ? Prayer.PROTECT_FROM_MAGIC : Prayer.PROTECT_FROM_MISSILES) ? new Hit().fixedDamage(0) : new Hit().randDamage(50));
                });
            }
        }
        npc.getCombat().delayAttack(5);
        return true;
    }
}
