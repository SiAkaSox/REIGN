package io.ruin.model.activities.raids.theatre_of_blood.boss.verzik_vitur;

import io.ruin.model.World;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;

public class VerzikPillar extends TheatreNPC
{

    private GameObject render;

    public VerzikPillar(Position base, int xOffset) {
        super(8379, base.translated(xOffset, 0, 0));
        render = new GameObject(32687, base.copy(), 10, 0);
        render.spawn();
    }


    @Override
    public int hit(Hit... hit)
    {
        int dmg = super.hit(hit);
        if (dmg >= getCombat().getStat(StatType.Hitpoints).currentLevel) {
            getPosition().getRegion().players.stream().forEach(p-> p.getPacketSender().sendRemoveObject(render.x, render.y, render.z, render.type, render.direction));
            GameObject newRender = new GameObject(32688, render.position().translated(-1, -1, 0), render.type, render.direction);
            newRender.spawn();
            World.startEvent(e -> {
                newRender.animate(newRender.getDef().animationId);
                e.delay(7);
                getPosition().getRegion().players.stream().forEach(p-> p.getPacketSender().sendRemoveObject(newRender.x, newRender.y, newRender.z, newRender.type, newRender.direction));
                newRender.remove();
                for (Entity p : getPossibleTargets(64, true, false)) {
                    if (!p.isPlayer() || p.getPosition().distance(getCentrePosition()) > 4) continue;
                    p.hit(new Hit().randDamage(35, 60));
                }
                destroy();
            });
        }
        return dmg;
    }

    @Override
    public void register()
    {
        super.register();
        render.spawn();
    }

    public GameObject getRender()
    {
        return render;
    }

}
