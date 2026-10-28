package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.activities.raids.theatre_of_blood.boss.verzik_vitur.VerzikStage;
import io.ruin.model.activities.raids.theatre_of_blood.boss.verzik_vitur.VerzikViturPlugin;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Projectile;
import io.ruin.utility.Misc;

public class Dawnbringer implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return def.id == 22516;
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        if (target.isPlayer() && player.wildernessLevel > 0) {
            player.sendMessage("You cannot cast this spell against other players in the Wilderness.");
            return false;
        }
        player.animate(1167);
        int entityX = player.getAbsX();
        int entityY = player.getAbsY();
        int targetX = target.getAbsX();
        int targetY = target.getAbsY();
        int distance = Misc.getDistance(entityX, entityY, targetX, targetY);
        int duration = 5 + (5 * Math.max(0, distance - 1));
        int delay = 41 + duration;
        player.graphics(1540,100,0);
        Projectile proj = new Projectile(1539, 43, 31, 51, 56, 10, 16, 64);
        proj.send(player, target);
        target.graphics(1541,0,0);
        int min = 0;
        if (target.npc != null && target.npc instanceof VerzikViturPlugin && ((VerzikViturPlugin)target).getStage() == VerzikStage.FIGHT_SHIELD) {
            maxDamage = 150;
            min = 75;
        } else {
            maxDamage = 15;
        }
        target.hit(new Hit(player, AttackStyle.MAGIC, AttackType.DEFENSIVE).fixedDamage(Misc.random(min, maxDamage)).clientDelay(delay));
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 35;
    }

}
