package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

public class VestasLongsword implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.equalsIgnoreCase("Vesta's longsword");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {

        double minDamagePercentage = 0.20;  // 20% of max hit
        double maxDamagePercentage = 1.20;  // 120% of max hit

        player.animate(7515);

        // Calculate the minimum and maximum damage based on percentages
        int minDamage = (int) (maxDamage * minDamagePercentage);
        int maxDamageValue = (int) (maxDamage * maxDamagePercentage);

        // Calculate the actual damage within the min-max range
        int damage = minDamage + (int) (Math.random() * (maxDamageValue - minDamage + 1));

        target.hit(new Hit(player, attackStyle, attackType).randDamage(damage).boostAttack(1.50));

        return true;
    }


    @Override
    public int getDrainAmount() {
        return 25;
    }
}