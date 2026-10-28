package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.utility.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.content.accountperks.Perk;
import io.ruin.model.content.accountperks.PerkManager;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

import java.util.function.BiFunction;

public class SapphireBoltEffect implements BiFunction<Entity, Hit, Boolean> {

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if (target.player == null)
            return false;
        int chance = 5;
        if (hit.attacker != null && hit.attacker instanceof Player && target instanceof NPC) {
            if (PerkManager.isActive(hit.attacker.player, Perk.LUCKY_SHOT)) {
                chance += 30;
            }
        }
        if(!Random.rollPercent(chance))
            return false;
        Stat prayer = hit.attacker.player.getStats().get(StatType.Prayer);
        int drain = prayer.currentLevel / 20;
        if(drain == 0)
            return false;
        prayer.boost(drain / 2, 0.0);
        target.player.getPrayer().drain(drain);
        target.graphics(751);
        target.hit(hit);
        return true;
    }

}