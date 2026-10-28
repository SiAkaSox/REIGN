package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

public class Cockatrice extends NPCCombat {

    private static StatType[] DRAIN = { StatType.Attack, StatType.Strength, StatType.Defence, StatType.Ranged, StatType.Magic };

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (withinDistance(1))
            basicAttack();
        if (target.player != null && !hasShield(target.player)) {
            for (StatType statType : DRAIN) {
                target.player.getStats().get(statType).drain(8);
            }
            target.player.sendMessage("<col=ff0000>The cockatrice's piercing gaze drains your stats!");
            target.player.sendMessage("<col=ff0000>A mirror shield can protect you from this attack.");
        }
        return true;
    }

    private boolean hasShield(Player player) {
        return player.getEquipment().contains(4156) || player.getEquipment().contains(24266);
    }
}
