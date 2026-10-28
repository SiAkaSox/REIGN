package io.ruin.model.activities.raids.theatre_of_blood.boss.nylocas_vasilias;

import io.ruin.model.activities.raids.theatre_of_blood.boss.maiden_of_sugadinti.MaidenOfSugadintiCombat;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Misc;

public class NylocasVasiliasCombat extends NPCCombat {

    private static final int MAGIC_PROJECTILE = 1610, RANGED_PROJECTILE = 1596;

    private NylocasVasiliasPlugin nylocas;

    NylocasVasiliasCombat(NylocasVasiliasPlugin nylocas) {
        this.nylocas = nylocas;
    }

    @Override
    public void init() {

    }

    @Override
    public void follow() {

    }

    @Override
    public boolean attack() {
        switch(nylocas.getId()) {
            case NylocasVasiliasPlugin.MELEE: {
                nylocas.animate(nylocas.getCombat().getInfo().attack_animation);
                nylocas.addEvent(e -> {
                    int damage = target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE) ? 0 : Misc.random(70);
                    target.hit(new Hit().fixedDamage(damage));
                });
                break;
            }
            case NylocasVasiliasPlugin.MAGIC: {
                nylocas.animate(7990);
                nylocas.addEvent(e -> {
                    for (Entity p : nylocas.getPossibleTargets()) {

                        int damage = target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ? 0 : Misc.random(70);
                        Projectile PROJECTILE = new Projectile(MAGIC_PROJECTILE, 110, 0, 0, 60, 0, 15, 0);
                        PROJECTILE.send(nylocas, target);
                        final Position dest = p.getPosition().copy();
                        int delay = 60;
                        if (nylocas.getCentrePosition().distance(dest) == 1) {
                            delay = 60;
                        } else if (nylocas.getCentrePosition().distance(dest) <= 5) {
                            delay = 80;
                        } else if (nylocas.getCentrePosition().distance(dest) <= 8) {
                            delay = 100;
                        } else {
                            delay = 120;
                        }
                        final int del = delay;
                        p.addEvent(dE -> {
                            dE.delay((del / 20));
                            target.hit(new Hit().fixedDamage(damage));
                        });
                    }
                });
                break;
            }
            case NylocasVasiliasPlugin.RANGE: {
                nylocas.animate(7999);
                nylocas.addEvent(e -> {
                    for (Entity p : nylocas.getPossibleTargets()) {
                        int damage = target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES) ? 0 : Misc.random(70);
                        Projectile PROJECTILE = new Projectile(RANGED_PROJECTILE, 110, 0, 0, 60, 0, 15, 0);
                        PROJECTILE.send(nylocas, target);
                        final Position dest = p.getPosition().copy();
                        int delay = 60;
                        if (nylocas.getCentrePosition().distance(dest) == 1) {
                            delay = 60;
                        } else if (nylocas.getCentrePosition().distance(dest) <= 5) {
                            delay = 80;
                        } else if (nylocas.getCentrePosition().distance(dest) <= 8) {
                            delay = 100;
                        } else {
                            delay = 120;
                        }
                        final int del = delay;
                        p.addEvent(dE -> {
                            dE.delay((del / 20));
                            target.hit(new Hit().fixedDamage(damage));
                        });
                    }
                });
                break;
            }
        }
        return true;
    }
}
