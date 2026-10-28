package io.ruin.model.activities.raids.theatre_of_blood.boss.verzik_vitur;

import io.ruin.model.World;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreOfBlood;
import io.ruin.model.activities.raids.theatre_of_blood.TheatrePartiesManager;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreParty;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.ClipUtils;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;

public enum VerzikStage {

    UNDISRUPTED(8369),

    FIGHT_SHIELD(8370) {
        @Override
        public void animate(VerzikViturPlugin n)
        {
            n.getCombat().delayAttack(2);
            n.transform(getMesh());
            n.remove("transitioning");
        }
    },

    FIGHT_MUTABLE(8372) {
        @Override
        public void animate(VerzikViturPlugin n)
        {
            n.set("transitioning", true);
            n.getCombat().delayAttack(8);
            n.getCombat().reset();
            for (Player p : n.getPosition().getRegion().players) {
                if (p.getCombat().isDead()) continue;
                if (p.getCombat().getTarget() == n) {
                    p.getCombat().reset();
                }
            }

            new GameObject(32686, n.getPosition().translated(0, 1, 0), 10, 0).spawn();

            for (VerzikPillar p : n.getPillars()) {
                if (p.getCombat().isDead()) continue;
                p.getRender().remove();
                p.hit(new Hit().fixedDamage(1000));
            }
            n.getCombat().reset();
            for (Player p : n.getPosition().getRegion().players) {
                if (p.getCombat().getTarget() == n) {
                    p.getCombat().reset();
                }
            }
            n.animate(-1);
            n.startEvent(e -> {
                e.delay(1);
                n.animate(8111);
                e.delay(1);
                for (Player p : n.getPosition().getRegion().players) {
                    if (p.getCombat().getTarget() == n) {
                        p.getCombat().reset();
                    }
                }
                n.forceText("You think you can defeat me?");
                n.getCombat().getStat(StatType.Hitpoints).fixedLevel = 3250;
                n.getCombat().getStat(StatType.Hitpoints).currentLevel = 3250;
                n.transform(8371);
                n.animate(8112);
                e.delay(2);
                n.animate(-1);
                n.getRouteFinder().customClipUtils = ClipUtils.NONE;
                Position dest = n.getSpawnLocation().translated(0, -10, 0);
                n.stepAbs(dest.getX(), dest.getY(), StepType.FORCE_WALK);
                e.delay(1);
                n.getRouteFinder().customClipUtils = null;
                for (Player p : n.getPosition().getRegion().players) {
                    if (p.getCombat().getTarget() == n) {
                        p.getCombat().reset();
                    }
                }
                e.delay(1);
                for (Player p : n.getPosition().getRegion().players) {
                    if (p.getCombat().getTarget() == n) {
                        p.getCombat().reset();
                    }
                }
                e.delay(9);
                n.getCombat().resetAttackDelay();
                n.getMovement().teleport(n.getSpawnLocation().translated(0, -10, 0));
                n.transform(getMesh());
                n.remove("transitioning");
            });
        }
    },

    FIGHT_SPIDER(8374) {
        @Override
        public void animate(VerzikViturPlugin n)
        {
            n.getCombat().delayAttack(5);
            n.animate(-1);
            n.set("transitioning", true);
            n.startEvent(e -> {
                e.delay(1);
                n.animate(8118);
                e.delay(2);
                n.transform(8373);
                n.getCombat().getStat(StatType.Hitpoints).fixedLevel = 3250;
                n.getCombat().getStat(StatType.Hitpoints).restore();
                n.animate(8119);
                e.delay(3);
                n.transform(getMesh());
                n.animate(-1);
                n.remove("transitioning");
                n.getCombat().resetAttackDelay();
            });
        }

    },

    FLEEING(8375) {
        @Override
        public void animate(VerzikViturPlugin n)
        {
            n.set("transitioning", true);
            n.getCombat().delayAttack(10);
            n.animate(-1);
            n.startEvent(e -> {
                e.delay(1);
                n.animate(8128);
                for (GameObject o : n.getPosition().getRegion().getAllObjects()) {
                    if (o.id == 32686) {
                        o.remove();
                    }
                }
                e.delay(2);
                n.transform(getMesh());
                n.animate(-1);
                e.delay(2);
                TheatreOfBlood theatre = TheatrePartiesManager.getRaid(n.getPosition());
                if (theatre != null) {
                    TheatreParty party = TheatrePartiesManager.getRaid(n.getPosition()).getParty();
                    for (int j = 0; j < party.members.size(); j++) {
                        if (theatre.deaths[j]) {
                            if (party.members.get(j) != null) {
                                party.members.get(j).getMovement().teleport(party.members.get(j).getPosition().translated(0, 5, 0));
                            }
                            theatre.deaths[j] = false;
                        }
                    }
                }
                n.getMovement().teleport(n.getAbsX(), n.getAbsY() - 50);
                n.throne = new GameObject(32738, n.getSpawnLocation().translated(1, 0, 0), 10, 0);
                n.throne.spawn();
                n.destroy();
                n.remove("transitioning");
            });
        }

    };

    private int mesh;

    private VerzikStage(int mesh)
    {
        this.mesh = mesh;
    }

    public int getMesh()
    {
        return mesh;
    }

    public void animate(VerzikViturPlugin n)
    {
        // TODO Auto-generated method stub

    }
}
