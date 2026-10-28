package io.ruin.model.activities.raids.theatre_of_blood.boss.maiden_of_sugadinti;

import io.ruin.model.World;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.utility.Misc;

public class BloodSplatter extends GameObject {

    private static final int BLOOD_POOL_OBJ = 32984;

    private TheatreNPC maiden;

    public BloodSplatter(Position pos, TheatreNPC maiden) {
        super(BLOOD_POOL_OBJ, pos, 10, 0);
        this.maiden = maiden;
        spawn();
    }

    public void register() {
        final Position p = new Position(x, y, z);
        World.startEvent(e -> {
            int cycle = 0;
            while (cycle < 40) {
                if (++cycle %2 == 0) {
                    for (Entity player : maiden.getPossibleTargets(14, true, false)) {
                        e.delay(1);
                        if (player.getAbsX() == p.getX() && player.getAbsY() == p.getY()) {
                            int hit = Misc.random(2, 5);
                            player.hit(new Hit().fixedDamage(hit));
                            maiden.hit(new Hit(HitType.HEAL).fixedDamage(hit));
                        }
                    }
                }
                e.delay(1);
            }
            BloodSpawn.getSplats().remove(p.tileHash());
            BloodSplatter.this.remove();
        });
    }

}
