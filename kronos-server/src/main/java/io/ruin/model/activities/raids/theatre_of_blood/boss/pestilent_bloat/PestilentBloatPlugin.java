package io.ruin.model.activities.raids.theatre_of_blood.boss.pestilent_bloat;

import io.ruin.model.World;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.activities.raids.theatre_of_blood.TheatrePartiesManager;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreParty;
import io.ruin.model.activities.raids.theatre_of_blood.boss.maiden_of_sugadinti.NylocasMatomenosPlugin;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.ClipUtils;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;
import io.ruin.utility.Shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PestilentBloatPlugin extends TheatreNPC {

    private Position[] destinations;

    private boolean clockwise, animatable = true;

    private int pointer, restDelta, clawDelta;

    private Shape area;

    private Map<Player, Integer> damage = new HashMap<Player, Integer>();

    public PestilentBloatPlugin(int id, Position base) {
        super(id, base);
        destinations = new Position[] { base.copy(), base.translated(11, 0, 0), base.translated(11, -11, 0), base.translated(0, -11, 0) };
        pointer = restDelta = clawDelta = 0;
        getRouteFinder().customClipUtils = ClipUtils.NONE;
        area = new Shape(base.translated(16, 5, 0), base.translated(-1, -14, 0));
        deathStartListener = (entity, killer, killHit) -> onDeath();
    }

    private void onDeath() {
        GameObject chest = new GameObject(37383, getSpawnPosition().translated(-13, -3, 0), 10, 2).spawn();
        chest.skipReachCheck = p -> p.distance(chest.position()) < 2;
        for (Entity p : getPossibleTargets(64, true, false)) {
            if (!p.isPlayer()) continue;
            TheatreParty party = TheatrePartiesManager.getParty(p.player);
            int points = 0, accumulatedDamage = damage.get(p.player) == null ? 0 : damage.get(p.player);
            if (accumulatedDamage >= 200 && accumulatedDamage < 400) {
                points = 3;
            } else if (accumulatedDamage >= 400 && accumulatedDamage < 600) {
                points = 8;
            } else if (accumulatedDamage >= 600 && accumulatedDamage < 800) {
                points = 12;
            } else if (accumulatedDamage >= 800 && accumulatedDamage < 1000) {
                points = 15;
            } else if (accumulatedDamage >= 1000 && accumulatedDamage < 1500) {
                points = 20;
            } else if (accumulatedDamage >= 1500) {
                points = 24;
            }
            party.addPoints(p.player, points);
        }
    }

    @Override
    public boolean hasTarget() {
        return false;
    }

    @Override
    public BloatCombat getCombat() {
        if (combat == null || !(combat instanceof BloatCombat)) {
            initCombat();
        }
        return (BloatCombat) combat;
    }


    public void initCombat() {
        combat = new BloatCombat();
        combat.init(this, getDef().combatInfo);
    }

    @Override
    public void face(Entity target) {

    }

    @Override
    public void face(int absX, int absY) {

    }

    @Override
    public int animate(int id, int delay) {
        if (restDelta > 0 && id != 8082) {
            return 0;
        }
        return super.animate(id, delay);
    }

    @Override
    public void process()
    {
        if (restDelta > 0) {
            animatable = false;
            restDelta--;
            if (restDelta == 5) {
                for (Entity m : getPossibleTargets(24, true, false)) {
                    m.player.getPacketSender().shakeCamera(0,3);
                    if (m.get("stomped") != null) {
                        long stomped = m.get("stomped");
                        if (stomped > System.currentTimeMillis()) {
                            continue;
                        }
                    }
                    if (m.isPlayer() && m.getPosition().distance(getCentrePosition()) <= 4) {
                        m.hit(new Hit().randDamage(50, 80));
                        m.player.sendMessage("You feel crushed under the Pestilent Bloats enormous feet.");
                        m.set("stomped", System.currentTimeMillis() + 3000);
                    }
                }
            }
            if (restDelta == 0) {
                for (Entity m : getPossibleTargets(24, true, false)) {
                   m.player.getPacketSender().resetCamera();
                }
            }
            if (restDelta > 3) {
                super.process();
                return;
            }
        } else if (!animatable) {
            animatable = true;
        }
        super.process();
        if (getCombat().getStat(StatType.Hitpoints).currentLevel <= 1500 && ++clawDelta %5 == 0) {
            Position b = base.translated(0, -11, 0);
            int count = Misc.random(12);
            for (int i = 0; i < count; i++) {
                Position dest = b.translated(Misc.random(16), Misc.random(16), 0);
                World.sendGraphics(1570 + Misc.random(3), 0,0,  dest.getX(), dest.getY(), dest.getZ());
                World.startEvent(e -> {
                    e.delay(4);
                    for (Player p : getPosition().getRegion().players) {
                        if (p.getPosition().equals(dest)) {
                            p.hit(new Hit().randDamage(50));
                            p.stun(2, true);
                        }
                    }
                });
            }
        }
        if (getPosition().equals(destinations[pointer])) {
            if (Misc.random(5) == 3) {
                clockwise = !clockwise;
            }
            if (Misc.random(5) < 3 && getPossibleTargets(24, true, false).size() > 0) {
                animate(8082);
                restDelta = 30;
                getMovement().teleport(destinations[pointer]);
                return;
            }
            if (!clockwise)
            {
                pointer++;
            } else
            {
                pointer--;
            }
            if (pointer < 0) {
                pointer = 3;
            }
            if (pointer == 4) {
                pointer = 0;
            }
        } else {

            /*
             * Line of sight
             */
            List<Player> targets = new ArrayList<Player>();
            for (Entity m : getPossibleTargets(24, true, false)) {
				int dist = getPosition().distance(destinations[pointer]);
                if (!area.inside(m.getPosition())) continue;
                if (pointer == 1 || pointer == 3) {
                    if (Math.abs(m.getPosition().getY() - getPosition().getY()) <= 2) {
                        if (ProjectileRoute.allow(m, this.getCentrePosition().getX(), this.getPosition().getY())) {
                            targets.add(m.player);
                        }
                    }

                } else {
                    if (Math.abs(m.getPosition().getX() - getPosition().getX()) <= 2) {
                        if (ProjectileRoute.allow(m, this.getCentrePosition().getX(), this.getPosition().getY())) {
                            targets.add(m.player);
                        }
                    }
                }

            }
            if (targets.size() > 0) {
                for (Player p : targets) {
                    Projectile PROJECTILE = new Projectile(1568, 110, 0, 0, 60, 0, 15, 0);
                    PROJECTILE.send(this, p);
                    final Position dest = p.getPosition().copy();
                    int delay = 60;
                    if (getCentrePosition().distance(dest) == 1) {
                        delay = 60;
                    } else if (getCentrePosition().distance(dest) <= 5) {
                        delay = 80;
                    } else if (getCentrePosition().distance(dest) <= 8) {
                        delay = 100;
                    } else {
                        delay = 120;
                    }
                    final int max = 10, del = delay;
                    p.addEvent(e -> {
                        e.delay((del / 20) - 2);
                        p.hit(new Hit().randDamage(max));
                        p.graphics(1569);
                    });

                }
            }
            stepAbs(destinations[pointer].getX(), destinations[pointer].getY(), StepType.FORCE_WALK);
        }
    }

    @Override
    public int animate(int id) {
        if (!animatable || restDelta > 0 && id != getCombat().getInfo().death_animation) return 0;
        return super.animate(id);
    }

    @Override
    public boolean isMovementBlocked(boolean message, boolean ignoreFreeze) {
        return false;
    }

    @Override
    public int hit(Hit... hits) {
        int dmg = super.hit(hits);
        Entity attacker = null;
        for(Hit hit : hits) {
            if (hit.attacker != null && attacker == null) {
                attacker = hit.attacker;
            }
        }
        if (attacker != null && attacker.player != null) {
            int current = damage.get(attacker.player) == null ? 0 : damage.get(attacker.player);
            damage.put(attacker.player, current + dmg);
        }
        boolean dead = getCombat().getStat(StatType.Hitpoints).currentLevel <= 10 || getCombat().isDead();
        return dmg;
    }

}
