package io.ruin.model.activities.raids.theatre_of_blood.boss.xarpus;

import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;

public class XarpusPoison extends GameObject {

    private XarpusPlugin xarpus;

    private final Position location;

    public XarpusPoison(Position location, int direction, XarpusPlugin xarpus) {
        super(32744, location, 22, direction);
        this.location = location;
        this.xarpus = xarpus;
        World.startEvent(event -> {
            event.delay(2);
            animate(getDef().animationId + 1);
        });
    }

    public void tick() {
        for (Player p : xarpus.getPosition().getRegion().players) {
            if (p.getAbsX() == x && p.getAbsY() == y && p.getHeight() == z) {
                p.hit(new Hit().randDamage(6, 8));
                xarpus.hit(new Hit(HitType.HEAL).randDamage(6, 8));
            }
        }
    }

}
