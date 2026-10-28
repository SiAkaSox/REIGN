package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.utility.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.content.accountperks.Perk;
import io.ruin.model.content.accountperks.PerkManager;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

import java.util.function.BiFunction;

public class RubyBoltEffect implements BiFunction<Entity, Hit, Boolean> {

    @Override
    public Boolean apply(Entity target, Hit hit) {
        int chance = target.player != null ? 11 : 6;
        if (hit.attacker != null && hit.attacker instanceof Player && target instanceof NPC) {
            if (PerkManager.isActive(hit.attacker.player, Perk.LUCKY_SHOT)) {
                chance += 30;
            }
        }
        if(!Random.rollPercent(chance))
            return false;
        Entity attacker = hit.attacker;
        int sacrificeDamage = (int) (attacker.getHp() * 0.10);
        int specialDamage;
        // Quick little fix to prevent abuse for HP events (where admins set their hitpoints to 10k and players hit 1,500 dmg)
        if(target.player != null) {
            if (attacker.player.getEquipment().hasId(26374)) {
                specialDamage = (int) ((target.getHp() > 99 ? 99 : target.getHp()) * 0.22);
            } else {
                specialDamage = (int) ((target.getHp() > 99 ? 99 : target.getHp()) * 0.20);
            }
        } else {
            if (attacker.player.getEquipment().hasId(26374)) {
                specialDamage = (int) ((target.getHp()) * 0.22);
            } else {
                specialDamage = (int) ((target.getHp()) * 0.20);
            }
        }
        if(sacrificeDamage <= 0 || specialDamage <= 0)
            return false;
        if (specialDamage > 100) {
            if (attacker.player.getEquipment().hasId(26374)) {
                specialDamage = 110;
            } else {
                specialDamage = 100;
            }
        }
        attacker.hit(new Hit().fixedDamage(sacrificeDamage));
        target.hit(hit.fixedDamage(specialDamage).ignoreDefence().ignorePrayer());
        target.graphics(754);
        return true;
    }

}