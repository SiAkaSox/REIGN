package io.ruin.model.activities.miscpvm;

import io.ruin.utility.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;


public class VyrewatchSentinel extends NPCCombat {

    @Override
    public void init() {
        npc.hitListener = new HitListener()
                .postDefend(this::postDefend);
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if(!withinDistance(1))
            return false;
        if(Random.rollDie(4))
            basicAttack(info.attack_animation, info.attack_style, 5).ignorePrayer();
        else
            basicAttack();
        return true;
    }

    private void postDefend(Hit hit) {
        if (hit.attacker != null && hit.attacker.player != null && hit.attackStyle != null) {
            Item weapon = hit.attacker.player.getEquipment().get(Equipment.SLOT_WEAPON);
            if (!usingFlail(hit.attacker.player)) {
                hit.damage *= 0.0;
                hit.attacker.player.sendMessage("The vyrewatch sentinel is not affected by your weapon!");
            }
        }
    }

    private boolean usingFlail(Player player) {
        return player.getEquipment().contains(22398);
    }
}
