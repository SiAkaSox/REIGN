package io.ruin.model.activities.wilderness.events.seren;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Projectile;
import io.ruin.utility.Misc;

public class SerenClone extends NPCCombat {

    private static final Projectile RANGED_PROJECTILE = new Projectile(660, 0, 0, 0, 50, 6, 16, 30);

    @Override
    public void init() {
        npc.hitsUpdate.hpBarType = 3;
        npc.hitListener = new HitListener().postDefend(this::postDefend);
    }

    private void postDefend(Hit hit) {
        if (hit.damage > 100 && !hit.isBlocked())
            hit.damage = 100;
    }

    @Override
    public void follow() {
        follow(0);
    }

    @Override
    public boolean attack() {
        if(!withinDistance(12))
            return false;
        if (npc.getHp() >= 1) {
            rangedAttack();
            return true;
        }
        return true;
    }

    @Override
    public boolean isAggressive() {
        return true;
    }

    private void rangedAttack() {
        npc.animate(8376);
        npc.localPlayers().forEach(p -> {
            npc.targetPlayer(p, false);
            npc.attackTargetPlayer();
            int delay = RANGED_PROJECTILE.send(npc, p);
            p.hit(new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).clientDelay(delay));
        });
    }
}