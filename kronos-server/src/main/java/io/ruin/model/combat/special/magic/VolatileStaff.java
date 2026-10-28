package io.ruin.model.combat.special.magic;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Utils;

import java.util.Objects;
import java.util.Optional;

import static io.ruin.model.entity.player.PlayerCombat.UNDEAD_NPCS;

public class VolatileStaff implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return def.id == 24424;
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
            player.animate(8532);
            player.graphics(1760);
            target.graphics(1759, 0, 0);
            int magicLevel = player.getStats().get(StatType.Magic).currentLevel;
            int maxHit = (int) ((magicLevel < 75 ? 0 : 50 + ((magicLevel - 73) / 1.5)) * player.getCombat().getRegularMagicDamageBoost(true));
            target.hit(new Hit(player, attackStyle, attackType).randDamage(maxHit));
            //player.getCombat().reset();
            player.getCombat().delayAttack(4);
        return true;
    }



    private boolean salveAmulatBoost(Player player, Entity entity) {
        final int salveAmuletImbued = 12017;

        Item amulet = player.getEquipment().get(Equipment.SLOT_AMULET);
        if (amulet == null) {
            return false;
        }

        Optional<String> npc = UNDEAD_NPCS.stream().filter(Objects::isNull)
                .filter(s -> s.equalsIgnoreCase(entity.npc.getDef().name)
                        || Utils.containsIgnoreCase(entity.npc.getDef().name, s))
                .findAny();

        if (!npc.isPresent()) {
            return false;
        }

        if (amulet.getId() == salveAmuletImbued) {
            return true;
        }
        return false;
    }

    @Override
    public int getDrainAmount() {
        return 55;
    }

}