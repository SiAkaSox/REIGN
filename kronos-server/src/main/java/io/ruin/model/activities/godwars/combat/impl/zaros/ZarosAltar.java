package io.ruin.model.activities.godwars.combat.impl.zaros;

import io.ruin.Server;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Common;

import java.util.concurrent.TimeUnit;

public class ZarosAltar {

    private static final int id = 42965;

    static {
        ObjectAction.register(id, 1, (p, obj) -> {
            p.startEvent(event -> {
                if (p.nexAltarDelay > System.currentTimeMillis()) { // if altar delay is less than current time
                    p.sendMessage("You can use this again in " + Common.getRemainingTime(p.nexAltarDelay) + ".");
                    return;
                }
                if (p.getCombat().isDefending(5) | p.getCombat().isAttacking(5)) {
                    p.sendFilteredMessage("You cannot use this altar while in combat.");
                    return;
                }
                p.nexAltarDelay = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10); // 10 minutes
                p.getStats().get(StatType.Prayer).restore(); // restore prayer regardless
                p.startEvent(e -> {
                    e.delay(1);
                    p.animate(645);
                    p.privateSound(2674);
                    if (ZarosItems.hasZarosItems(p)) { // if has zaros items
                        Config.SPECIAL_ENERGY.set(p, 1000); // restore spec
                        p.getMovement().restoreEnergy(100); // restore run
                        p.setHp(p.getMaxHp()); // heal to full
                    }
                    p.sendFilteredMessage("You pray at the altar. It is on cooldown for 10 minutes.");
                });
            });
        });
        ObjectAction.register(id, 2, (p, obj) -> {
            p.startEvent(event -> { // tele out
                p.lock(LockType.FULL_NULLIFY_DAMAGE);
                p.face(obj);
                p.animate(3945);
                p.privateSound(200, 0, 10);
                event.delay(2);
                p.getMovement().teleport(NexUtils.NEX_WAITING_ROOM);
                p.animate(-1);
                p.unlock();
            });
        });
    }
}
