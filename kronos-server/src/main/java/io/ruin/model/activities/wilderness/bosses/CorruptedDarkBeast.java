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
import io.ruin.utility.Misc;

public class CorruptedDarkBeast extends NPCCombat {

    private static final Projectile HEALER_PROJECTILE = new Projectile(1708, 60, 20, 0, 50, 0, 16, 32);

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
        } else if(Random.rollDie(5, 1)) {
            mageAttack();
        }
        basicAttack();
        return true;
    }

    private void heal() {
        npc.graphics(157);
        npc.incrementHp(Random.get(4, 10)); //Heal: A blast will appear under Callisto. Even though the game states that Callisto will prepare to heal himself based on the damage dealt to him, he actually heals himself during this time for a small amount. -- source: wiki
    }

    private void mageAttack() {
        npc.animate(2731);
        npc.localPlayers().forEach(p -> {
            if(!canAttack(p) || Misc.getEffectiveDistance(npc, p) > 8)
                return;
            int delay = HEALER_PROJECTILE.send(npc, p);
            Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay);
            if (braceletOfEthereumEffect(target))
                hit.block();
            target.hit(hit);
        });
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
