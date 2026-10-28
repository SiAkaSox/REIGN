package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.utility.Misc;

public class AncientGodsword implements Special {

    public static final int id = 26233;
    public static final int GFX_ID = 1996; // blue claws

    @Override
    public boolean accept(ItemDef def, String name) {
        return def.id == id;
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(9171);
        player.graphics(GFX_ID);
        player.publicSound(3869);
        //target.recolour(0, 200, 0, 6, 28, 112);
        Hit hit = new Hit(player, attackStyle, attackType)
                        .randDamage(maxDamage)
                        .boostDamage(0.1) // boost damage 10%
                        .boostAttack(1.0); // double accuracy);
//        target.hit(new Hit(player, attackStyle, attackType)
//                .randDamage(maxDamage)
//                .boostDamage(0.1) // boost damage 10%
//                .boostAttack(1.0) // double accuracy
//        );
        target.hit(hit);
        if (!hit.isBlocked()) {
            World.startEvent(event -> {
                event.delay(6);
                if (Misc.getDistance(player.getPosition().copy(), target.getPosition().copy()) < 5) { // check the distance between the 2 players to see if its < 5
                    target.graphics(2001); // a gfx
                    Hit hit1 = new Hit(player, AttackStyle.MAGICAL_MELEE, attackType).fixedDamage(25).ignorePrayer().ignoreDefence(); // hit a magic based melee for a guaranteed 25. Thru def / prayer
                    target.hit(hit1); // hit the target
                    Hit healhit = new Hit(HitType.HEAL).fixedDamage(25).hide();
                    player.hit(healhit);
                }
            }).setCancelCondition(() -> { // basically, if either player dies, is null, or is offline, this attack should not go off.
                if (target == null) { // if the target is null
                    return true;
                }
                if (target.isPlayer()) {
                    if (!target.player.isOnline()) { // if the target is offline
                        return true;
                    }
                }
                if (!target.alive()) { //if the target is dead
                    return true;
                }
                if (player == null) { // if player is null
                    return true;
                }
                if (!player.alive()) { // if target is not alive
                    return true;
                }
                if (!player.isOnline()) { // if target is offline
                    return true;
                }
                return false;
            });
        }
            return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}