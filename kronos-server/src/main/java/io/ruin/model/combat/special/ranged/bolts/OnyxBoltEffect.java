package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.utility.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.content.accountperks.Perk;
import io.ruin.model.content.accountperks.PerkManager;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

import java.util.function.BiFunction;

public class OnyxBoltEffect implements BiFunction<Entity, Hit, Boolean> {

    @Override
    public Boolean apply(Entity target, Hit hit) {
        int chance = target.player != null ? 10 : 11;
        if (hit.attacker != null && hit.attacker instanceof Player && target instanceof NPC) {
            if (PerkManager.isActive(hit.attacker.player, Perk.LUCKY_SHOT)) {
                chance += 30;
            }
        }
        if(!Random.rollPercent(chance))
            return false;
        int damage = target.hit(hit.boostDamage(0.20));
        int heal = (int) (damage * 0.25);
        if(heal > 0) {
            target.graphics(753);
            hit.attacker.incrementHp(heal);
        }
        return true;
    }

}