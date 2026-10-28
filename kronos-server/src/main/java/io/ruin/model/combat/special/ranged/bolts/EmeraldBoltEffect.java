package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.utility.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.content.accountperks.Perk;
import io.ruin.model.content.accountperks.PerkManager;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

import java.util.function.BiFunction;

public class EmeraldBoltEffect implements BiFunction<Entity, Hit, Boolean> {

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if (target.player == null)
            return false;
        int chance = target.player != null ? 54 : 55;
        if (hit.attacker != null && hit.attacker instanceof Player && target instanceof NPC) {
            if (PerkManager.isActive(hit.attacker.player, Perk.LUCKY_SHOT)) {
                chance += 30;
            }
        }
        if(!Random.rollPercent(chance))
            return false;
        target.poison(5);
        target.graphics(752);
        target.hit(hit);
        return true;
    }

}