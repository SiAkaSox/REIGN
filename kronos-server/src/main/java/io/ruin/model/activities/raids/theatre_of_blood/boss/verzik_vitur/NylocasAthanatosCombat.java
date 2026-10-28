package io.ruin.model.activities.raids.theatre_of_blood.boss.verzik_vitur;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.utility.Misc;

public class NylocasAthanatosCombat extends NPCCombat {

    private NylocasAthanatosPlugin n;

    public NylocasAthanatosCombat(NylocasAthanatosPlugin nylocas) {
        this.n = nylocas;
    }

    @Override
    public boolean canAttack(Entity target) {
        if (target != n) {
            return false;
        }
        return super.canAttack(target);
    }

    @Override
    protected boolean withinDistance(int distance) {
        return true;
    }

    @Override
    public void init() {

    }

    @Override
    public void follow() {

    }

    @Override
    public boolean attack() {
        Entity victim = n.getCombat().getTarget();
        if (victim.npc != null)
            return false;
        int projectile = Misc.random(10) > 5 ? 1587 : 1588;
        n.animate(n.getCombat().getInfo().attack_animation);
        Projectile PROJECTILE = new Projectile(projectile, 110, 0, 0, 60, 0, 15, 0);
        PROJECTILE.send(n, target);
        int delay = 100;
        Position dest = victim.getPosition();
        if (n.getCentrePosition().distance(dest) == 1) {
            delay = 60;
        } else if (n.getPosition().distance(dest) <= 5) {
            delay = 80;
        } else if (n.getPosition().distance(dest) <= 8) {
            delay = 100;
        } else {
            delay = 120;
        }
        final int del = delay / 20;
        n.startEvent(e -> {
            e.delay(del);
            victim.hit(new Hit().randDamage(22));
        });
        n.getCombat().delayAttack(6);
        return false;
    }
}
