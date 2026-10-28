package io.ruin.model.activities.raids.theatre_of_blood.boss.maiden_of_sugadinti;

import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Position;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.utility.Misc;

import java.util.HashMap;

public class BloodSpawn extends TheatreNPC {

    private static HashMap<Integer, BloodSplatter> splats = new HashMap<>();

    private TheatreNPC boss;
    private Position to = null;

    public BloodSpawn(Position base, TheatreNPC boss) {
        super(8367, base);
        this.boss = boss;
    }

    @Override
    public void process() {
        super.process();

        if (getSplats().get(getPosition().tileHash()) == null) {
            BloodSplatter splatter = new BloodSplatter(getPosition(), boss);
            getSplats().put(getPosition().tileHash(), splatter);
            splatter.register();
        }

        if (lastPosition.equals(position)) {
            to = null;
        }

        if (to == null) {
            while (to == null || !ProjectileRoute.allow(this, to.getX(), to.getY())) {
                to = getPosition().translated(-3,-3,0).translate(Misc.random(6), Misc.random(6), 0);
            }
        }
        if (to != null) {
            if (!getPosition().equals(to)) {
                stepAbs(to.getX(),to.getY(), StepType.NORMAL);
            } else {
                to = null;
            }
        }
    }
    
    /**
     * @return the splats
     */
    public static HashMap<Integer, BloodSplatter> getSplats() {
        return splats;
    }
}
