package io.ruin.model.activities.raids.theatre_of_blood.boss.xarpus;

import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;
import io.ruin.utility.Misc;

public class XarpusSkeleton extends GameObject {

    private static final int PROJECTILE = 1550;

    private int ticks = 0;

    private XarpusPlugin xarpus;

    private final Position location;

    public XarpusSkeleton(Position location, XarpusPlugin xarpus) {
        super(32743, location, 22, Misc.random(3));
        this.location = location;
        this.xarpus = xarpus;
        World.startEvent(event -> {
           event.delay(2);
            animate(getDef().animationId + 1);
        });
    }

    public boolean tick() {
        if (ticks > 2 && ++ticks < 18) {
            boolean blocked = false;
            for (Player p : location.getRegion().players) {
                if (p.getPosition().equals(location)) {
                    blocked = true;
                }
            }
            if (!blocked) {
                Projectile p = new Projectile(PROJECTILE, 0, 0, 0, 60, 0, 50, 0);
                p.send(location.getX(), location.getY(), xarpus.getCentrePosition().getX(), xarpus.getCentrePosition().getY());

                final Position dest = location;
                int delay = 60;
                if (xarpus.getCentrePosition().distance(dest) == 1) {
                    delay = 60;
                } else if (xarpus.getCentrePosition().distance(dest) <= 5) {
                    delay = 80;
                } else if (xarpus.getCentrePosition().distance(dest) <= 8) {
                    delay = 100;
                } else {
                    delay = 120;
                }
                final int del = delay;
                xarpus.addEvent(dE -> {
                    dE.delay((del / 20));
                    xarpus.hit(new Hit(HitType.HEAL).fixedDamage(6));
                });
            }
            return true;
        } else if (ticks > 18) {
            for (Player p : location.getRegion().players) {
                p.getPacketSender().sendGraphics(1549, 0, 0, location.getX(), location.getY(), location.getZ());
            }
            remove();
            return false;
        } else {
            ++ticks;
            return true;
        }
    }

}
