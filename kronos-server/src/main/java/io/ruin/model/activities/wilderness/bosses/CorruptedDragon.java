package io.ruin.model.activities.wilderness.bosses;

import io.ruin.utility.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.jewellery.BraceletOfEthereum;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;

public class CorruptedDragon extends NPCCombat {

    boolean fire;

    private static final Projectile HEALER_PROJECTILE = new Projectile(1708, 60, 20, 0, 50, 0, 16, 32);

    private static final Projectile FIRE_PROJECTILE = new Projectile(393, 60, 20, 36, 40, 6, 15, 255).tileOffset(1);

    @Override
    public void init() {
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if(!withinDistance(12))
            return false;
        else if(Random.rollPercent(20)) {
            heal();
        }
        if (!withinDistance(1)) {
            if (Random.rollDie(8, 1)) {
                fireBreath();
            } else {
                meleeDragonfire();
            }
        }
        if (withinDistance(1)) {
            basicAttack();
        }
        return true;
    }

    private void heal() {
        npc.animate(81);
        npc.graphics(157);
        npc.incrementHp(Random.get(4, 10)); //Heal: A blast will appear under Callisto. Even though the game states that Callisto will prepare to heal himself based on the damage dealt to him, he actually heals himself during this time for a small amount. -- source: wiki
    }

    private void fireBreath() {
        npc.animate(81);
        int delay = FIRE_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(80).penetrateDragonfireResistance(0.3).clientDelay(delay));
        target.graphics(157, 92, delay);
    }

    void meleeDragonfire() {
        fire = true;
        npc.animate(81);
        npc.graphics(1, 100, 0);
        Hit hit = new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(50);
        if (braceletOfEthereumEffect(target))
            hit.block();
        target.hit(hit);
    }

    private boolean braceletOfEthereumEffect(Entity target) {
        Item bracelet = target.player.getEquipment().get(Equipment.SLOT_HANDS);
        if (bracelet != null && bracelet.getId() == BraceletOfEthereum.CHARGED) {
            BraceletOfEthereum.consumeCharge(target.player, bracelet);
            return true;
        }
        return false;
    }

}
