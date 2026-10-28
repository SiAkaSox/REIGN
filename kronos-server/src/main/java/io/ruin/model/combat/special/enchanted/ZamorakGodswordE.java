package io.ruin.model.combat.special.enchanted;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;

//Ice Cleave: Deal an attack that inflicts 10% more damage with double
//accuracy and freezes your target for 20 seconds if it successfully hits. (50%)
public class ZamorakGodswordE implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return def.id == 31069;
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(player.getEquipment().getId(Equipment.SLOT_WEAPON) == 31069 ? 7638 : 7639);
        player.graphics(1210);
        player.publicSound(3869);
        target.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostDamage(0.10).boostAttack(1.0));
        target.graphics(369);
        target.freeze(20, player);
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}