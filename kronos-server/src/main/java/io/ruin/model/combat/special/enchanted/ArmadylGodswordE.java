package io.ruin.model.combat.special.enchanted;

import io.ruin.utility.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.containers.Equipment;

//The Judgement: Deal an attack that inflicts
//37.5% more damage with double accuracy. (50%)
public class ArmadylGodswordE implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return def.id == 31050;
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(player.getEquipment().getId(Equipment.SLOT_WEAPON) == 11802 ? 7644 : 7645);
        player.graphics(1211);
        player.publicSound(3869);
        target.hit(new Hit(player, attackStyle, attackType)
                .randDamage(maxDamage)
                .boostDamage(0.375)
                .boostAttack(1.0) //100% aka double
        );
        int vengDamage = (int) Math.ceil(maxDamage / 3);
        if (Random.rollPercent(5)) {
            Config.VENG_COOLDOWN.set(player, 0);
            player.vengeanceActive = false;
            player.forceText("Taste Vengeance!");
            target.hit(new Hit(player).fixedDamage(vengDamage));
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}