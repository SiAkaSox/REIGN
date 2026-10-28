package io.ruin.model.combat.special.enchanted;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Projectile;

//Armadyl Eye: Deal an attack with double accuracy. (0%)
public class ArmadylCrossbowE implements Special {

    private static final Projectile PROJECTILE = new Projectile(301, 38, 36, 41, 51, 5, 5, 11);

    @Override
    public boolean accept(ItemDef def, String name) {
        return def.id == 31051;
    }

    @Override
    public boolean handle(Player player, Entity victim, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(4230);
        int delay = PROJECTILE.send(player, victim);
        victim.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostAttack(1.0).clientDelay(delay));
        if (!player.getCombat().getTarget().isPlayer()) {
            victim.freeze(15, player);
            victim.getMovement().reset();
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}