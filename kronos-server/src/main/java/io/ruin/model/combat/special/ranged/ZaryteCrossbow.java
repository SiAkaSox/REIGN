package io.ruin.model.combat.special.ranged;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Projectile;

//Armadyl Eye: Deal an attack with double accuracy. (0%)
public class ZaryteCrossbow implements Special {

    private static final Projectile PROJECTILE = new Projectile(1995, 38, 36, 41, 51, 5, 5, 11);

    @Override
    public boolean accept(ItemDef def, String name) {
        return def.id == 26374;
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(4230);
        int delay = PROJECTILE.send(player, target);
        if (player.getEquipment().contains(9242) || player.getEquipment().contains(21944)) {
            int sacrificeDamage = (int) (player.getHp() * 0.10);
            int specialDamage;

            if(target.player != null) {
                if (player.getEquipment().hasId(26374)) {
                    specialDamage = (int) ((target.getHp() > 99 ? 99 : target.getHp()) * 0.22);
                } else {
                    specialDamage = (int) ((target.getHp() > 99 ? 99 : target.getHp()) * 0.20);
                }
            } else {
                if (player.getEquipment().hasId(26374)) {
                    specialDamage = (int) ((target.getHp()) * 0.22);
                } else {
                    specialDamage = (int) ((target.getHp()) * 0.20);
                }
            }
            if(sacrificeDamage <= 0 || specialDamage <= 0)
                return false;
            if (specialDamage > 100) {
                if (player.getEquipment().hasId(26374)) {
                    specialDamage = 110;
                } else {
                    specialDamage = 100;
                }
            }
            player.hit(new Hit().fixedDamage(sacrificeDamage));
            target.hit(new Hit(player, attackStyle, attackType).fixedDamage(specialDamage).ignoreDefence().ignorePrayer());
            target.graphics(754);
        }

        if (player.getEquipment().contains(9244) || player.getEquipment().contains(21948)) {
            if (player.getEquipment().contains(9244)) {
                target.hit(new Hit(player, attackStyle, attackType).randDamage(1, 69).ignoreDefence());
            }
            if (player.getEquipment().contains(21948)) {
                target.hit(new Hit(player, attackStyle, attackType).randDamage(1, 70).ignoreDefence());
            }
            target.graphics(756);
        }

       if (player.getEquipment().contains(9243) || player.getEquipment().contains(21946)) {
           target.graphics(758);
           if (player.getEquipment().contains(9243)) {
               target.hit(new Hit(player, attackStyle, attackType).randDamage(1, 55).boostDamage(0.25).ignoreDefence());
           }
           if (player.getEquipment().contains(21946)) {
               target.hit(new Hit(player, attackStyle, attackType).randDamage(1, 56).boostDamage(0.25).ignoreDefence());
           }
       }
        if (player.getEquipment().contains(9245) || player.getEquipment().contains(21950)) {
            int damage = maxDamage;
            int heal = (int) (damage * 0.25);
            if (player.getEquipment().contains(9245)) {
                target.hit(new Hit(player, attackStyle, attackType).randDamage(1, 55).ignoreDefence());
            }
            if (player.getEquipment().contains(21950)) {
                target.hit(new Hit(player, attackStyle, attackType).randDamage(1, 56).ignoreDefence());
            }
            target.graphics(753);
            player.incrementHp(heal);
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 75;
    }

}