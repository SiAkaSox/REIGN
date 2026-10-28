package io.ruin.model.activities.raids.theatre_of_blood.boss.xarpus;

import io.ruin.model.World;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.activities.raids.theatre_of_blood.TheatrePartiesManager;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreParty;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;
import io.ruin.utility.Shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XarpusPlugin extends TheatreNPC {

    private static final int POISON_PROJECTILE = 1555, POISON_GRAPHIC = 1556, SECONDARY_PROJECTILE = 1557;

    private int attackDelta = 0, nextTarget = 0, quadrant = -1;

    private List<XarpusSkeleton> skeletons = new ArrayList<XarpusSkeleton>();
    private List<XarpusPoison> poison = new ArrayList<XarpusPoison>();
    private List<Integer> marked = new ArrayList<Integer>();

    public XarpusPlugin(int id, Position b) {
        super(id, b);
        deathStartListener = (entity, killer, killHit) -> onDeath();
    }

    private void onDeath() {
        {
            transform(8341);
            animate(8063);
            GameObject chest = new GameObject(37383, getSpawnPosition().translated(3, 14, 0), 10, 1).spawn();
            chest.skipReachCheck = p -> p.distance(chest.position()) < 2;
            for (Entity p : getPossibleTargets(64, true, false)) {
                if (!p.isPlayer()) continue;
                TheatreParty party = TheatrePartiesManager.getParty(p.player);
                party.addPoints(p.player, 5);
                party.getTheatre().deaths = new boolean[party.members.size()];
            }
            for (XarpusPoison s : poison) {
                s.remove();
            }
        }
    }

    @Override
    public XarpusCombat getCombat() {
        if (combat == null || !(combat instanceof XarpusCombat)) {
            combat = new XarpusCombat();
            combat.init(this, getDef().combatInfo);
            combat.getStat(StatType.Hitpoints).currentLevel = 1400;
        }
        return (XarpusCombat) combat;
    }

    @Override
    public boolean isMovementBlocked(boolean message, boolean ignoreFreeze) {
        return true;
    }

    @Override
    public void process() {
        try {
            super.process();
//            forceText("HP " + getCombat().getStat(StatType.Hitpoints).currentLevel + " quadrant " + quadrant);
            Shape rec = new Shape(getSpawnPosition().translated(9, 9, 0), getSpawnPosition().translated(-7, -7, 0));
            List<Entity> targets = getPossibleTargets(24, true, false);
            List<Entity> possibleTargets = new ArrayList<Entity>();
            for (XarpusPoison p : poison) {
                p.tick();
            }
            for (Entity m : targets) {
                if (rec.inside(m.getPosition())) {
                    possibleTargets.add(m);
                }
            }
            if (getCombat().attackDelayTicks > 0) {
                --getCombat().attackDelayTicks;
            }
            if (possibleTargets.size() > 0) {
                if (++attackDelta == 150 && getId() != 8340) {
                    animate(8061);
                    World.startEvent(e -> {
                        e.delay(2);
                        transform(8340);
                        attackDelta = 0;
                    });
                } else if (getId() == 8338 && Misc.random(10) < 4 && attackDelta < 125 && attackDelta %5 == 0) {
                    Position base = getSpawnPosition().translated(-6, -6, 0);
                    int x = Misc.random(14), y = Misc.random(14);
                    if (x >= 5 && x <= 10) {
                        x = Misc.random(10) < 5 ? 4 : 10;
                    }
                    if (y >= 6 && y <= 9) {
                        y = Misc.random(10) < 5 ? 5 : 10;
                    }


                    Position rand = base.translated(x, y, 0);
                    XarpusSkeleton skeleton = new XarpusSkeleton(rand, this);
                    skeleton.spawn();
                    skeletons.add(skeleton);
                } else if (quadrant == -1 && getId() == 8340 && !getCombat().hasAttackDelay()) {
                    if ((++attackDelta >= 40 && attackDelta < 60) || (attackDelta >= 90 && attackDelta < 110) || (attackDelta >= 140 && attackDelta < 160) || (attackDelta >= 180 && attackDelta < 220)) {
                        Entity target = null;
                        if (possibleTargets.size() > nextTarget) {
                            target = possibleTargets.get(nextTarget++);
                        } else {
                            nextTarget = 0;
                            target = possibleTargets.get(0);
                        }
                        getCombat().reset();
                        final Position dest = target.getPosition().copy();

                        Projectile p1 = new Projectile(SECONDARY_PROJECTILE, 80, 0, 0, 60, 0, 50, 0);
                        p1.send(getAbsX(), getAbsY(), dest.getX(), dest.getY());
                        Projectile p2 = new Projectile(SECONDARY_PROJECTILE, 90, 0, 0, 60, 0, 50, 0);
                        p2.send(getAbsX(), getAbsY(), dest.getX(), dest.getY());
                        int delay = 100;
                        if (getCentrePosition().distance(dest) == 1) {
                            delay = 60;
                        } else if (getPosition().distance(dest) <= 5) {
                            delay = 80;
                        } else if (getPosition().distance(dest) <= 8) {
                            delay = 100;
                        } else {
                            delay = 120;
                        }
                        final Entity victim = target;
                        final int del = (delay / 20) - 2;
                        World.startEvent(e -> {
                            e.delay(del);
                            victim.hit(new Hit().randDamage(2, 6));
                            victim.hit(new Hit().randDamage(2, 6));
                        });
                        getCombat().delayAttack(7);
                    } else {
                        Entity target = null;
                        if (possibleTargets.size() > nextTarget) {
                            target = possibleTargets.get(nextTarget++);
                        } else {
                            nextTarget = 0;
                            target = possibleTargets.get(0);
                        }
                        getCombat().reset();
                        final Position dest = target.getPosition().copy();
                        Projectile p1 = new Projectile(POISON_PROJECTILE, 90, 0, 0, 60, 0, 50, 0);
                        p1.send(getAbsX(), getAbsY(), dest.getX(), dest.getY());
                        int delay = 100;
                        if (getCentrePosition().distance(dest) == 1) {
                            delay = 60;
                        } else if (getPosition().distance(dest) <= 5) {
                            delay = 80;
                        } else if (getPosition().distance(dest) <= 8) {
                            delay = 100;
                        } else {
                            delay = 120;
                        }
                        final Entity lastTarget = target;
                        final int del = (delay / 20) - 2;
                        World.startEvent(e -> {
                            e.delay(del);
                            for (Player player : getPosition().getRegion().players) {
                                player.getPacketSender().sendGraphics(POISON_GRAPHIC, 0, 0, dest.getX(), dest.getY(), dest.getZ());
                            }
                            if (getPosition().getRegion() == dest.getRegion() && !marked.contains(dest.tileHash())) {
                                XarpusPoison p = new XarpusPoison(dest, getPoisonDirection(dest), this);
                                p.spawn();
                                marked.add(dest.tileHash());
                                poison.add(p);
                            }

                            /*
                             * Ricochet
                             */
                            int delay2 = 100;
                            Position dest2 = null;
                            if (possibleTargets.size() == 1) {
                                Position base = getSpawnPosition().translated(-6, -6, 0);
                                int x = Misc.random(15), y = Misc.random(15);
                                if (x >= 5 && x <= 10) {
                                    x = Misc.random(10) < 5 ? 4 : 10;
                                }
                                if (y >= 6 && y <= 9) {
                                    y = Misc.random(10) < 5 ? 5 : 10;
                                }
                                dest2 = base.translated(x, y, 0);
                            } else {
                                List<Position> dests = new ArrayList<Position>();
                                for (Entity pl : possibleTargets) {
                                    if (pl == lastTarget) {
                                        continue;
                                    }
                                    dests.add(pl.getPosition());
                                }
                                Collections.shuffle(dests);
                                dest2 = dests.get(Misc.random(dests.size() - 1));
                            }
                            if (Misc.random(10) <= 5) {
                                delay2 = 100;
                                if (getCentrePosition().distance(dest2) == 1) {
                                    delay2 = 60;
                                } else if (getPosition().distance(dest2) <= 5) {
                                    delay2 = 80;
                                } else if (getPosition().distance(dest2) <= 8) {
                                    delay2 = 100;
                                } else {
                                    delay2 = 120;
                                }
                                final Position ricochet = dest2.copy();
                                Projectile proj = new Projectile(POISON_PROJECTILE, 0, 0, 0, 60, 0, 50, 0);
                                proj.send(getAbsX(), getAbsY(), dest.getX(), dest.getY());
                                final int del2 = (delay2 / 20) - 2;
                                World.startEvent(e2 -> {
                                    e2.delay(del2);
                                    for (Player player : getPosition().getRegion().players) {
                                        player.getPacketSender().sendGraphics(POISON_GRAPHIC, 0, 0, ricochet.getX(), ricochet.getY(), ricochet.getZ());
                                    }
                                    if (getPosition().getRegion() == ricochet.getRegion() && !marked.contains(ricochet.tileHash())) {
                                        XarpusPoison p2 = new XarpusPoison(ricochet, getPoisonDirection(ricochet), this);
                                        p2.spawn();
                                        poison.add(p2);
                                        marked.add(ricochet.tileHash());
                                    }
                                });
                            }
                        });
                    }
                    getCombat().delayAttack(7);
                } else if (quadrant != -1 && getId() == 8340) {
                    if (++attackDelta >= Misc.random(4,8)) {
                        quadrant = Misc.random(3);
                        attackDelta = 0;
                    }
                    Position base = getSpawnPosition().translated(-8, -8, 0);
                    Position[] quadrants = { base, base.translated(0, 16, 0), base.translated(16, 16, 0), base.translated(16, 0, 0) };
                    face(quadrants[quadrant].getX(), quadrants[quadrant].getY());
                    getCombat().delayAttack(700);
                }
                for (XarpusSkeleton s : skeletons) {
                    if (!s.tick()) {
                        skeletons.remove(s);
                    }
                }
            }
        } catch(Exception e) {
//             e.printStackTrace();
        }
    }

    @Override
    public void face(Entity target) {
        if (quadrant != -1) return;
        super.face(target);
    }

    @Override
    public int hit(Hit... hit) {
        boolean above700 = getCombat().getStat(StatType.Hitpoints).currentLevel > 700;
        int damage = super.hit(hit);
        boolean dead = getCombat().getStat(StatType.Hitpoints).currentLevel <= 10 || getCombat().isDead();
        if (quadrant != -1) {
            Entity attacker = null;
            for (Hit h : hit) {
                if (h.attacker != null && attacker == null) {
                    attacker = h.attacker;
                }
            }
            if (attacker != null) {
                Position base = getSpawnPosition().translated(-7, -7, 0);
                Shape rec = new Shape(base.translated(9, 8, 0), base);
                if (quadrant == 0 && rec.inside(attacker.getPosition())) {
                    attacker.hit(new Hit().fixedDamage(50));
                    hit(new Hit(HitType.HEAL).fixedDamage((int) (damage * 0.5)));
                    poison(attacker);
                }
                rec = new Shape(base.translated(9, 16, 0), base.translated(0, 7, 0));
                if (quadrant == 1 && rec.inside(attacker.getPosition())) {
                    attacker.hit(new Hit().fixedDamage(50));
                    hit(new Hit(HitType.HEAL).fixedDamage((int) (damage * 0.5)));
                    poison(attacker);
                }
                rec = new Shape(base.translated(17, 17, 0), base.translated(8, 7, 0));
                if (quadrant == 2 && rec.inside(attacker.getPosition())) {
                    attacker.hit(new Hit().fixedDamage(50));
                    hit(new Hit(HitType.HEAL).fixedDamage((int) (damage * 0.5)));
                    poison(attacker);
                }
                rec = new Shape(base.translated(16, 8, 0), base.translated(8, 0, 0));
                if (quadrant == 3 && rec.inside(attacker.getPosition())) {
                    attacker.hit(new Hit().fixedDamage(50));
                    hit(new Hit(HitType.HEAL).fixedDamage((int) (damage * 0.5)));
                    poison(attacker);
                }
            }
        }
        if (!above700 && quadrant == -1) {
            transform(8340);
            quadrant = Misc.random(3);
            forceText("Screeeech!");
            attackDelta = 0;
        }
        return damage;
    }

    private void poison(Entity target) {
        if (quadrant != -1) return;
        Position dest = target.getPosition();
        Projectile proj = new Projectile(POISON_PROJECTILE, 0, 0, 0, 60, 0, 50, 0);
        proj.send(getAbsX(), getAbsY(), dest.getX(), dest.getY());
        int delay = 100;
        if (getCentrePosition().distance(dest) == 1) {
            delay = 60;
        } else if (getPosition().distance(dest) <= 5) {
            delay = 80;
        } else if (getPosition().distance(dest) <= 8) {
            delay = 100;
        } else {
            delay = 120;
        }
        final Entity lastTarget = target;
        final int del = (delay / 20) - 2;
        World.startEvent(e -> {
           e.delay(del);
            for (Player player : getPosition().getRegion().players) {
                player.getPacketSender().sendGraphics(POISON_GRAPHIC, 0, 0, dest.getX(), dest.getY(), dest.getZ());
            }
            if (getPosition().getRegion() == dest.getRegion() && !marked.contains(dest.tileHash())) {
                XarpusPoison p = new XarpusPoison(dest, getPoisonDirection(dest), this);
                p.spawn();
                poison.add(p);
                marked.add(dest.tileHash());
            }
            /*
             * Ricochet
             */
            int delay2 = 100;
            Position dest2 = null;
            Shape rec = new Shape(getSpawnPosition().translated(9, 9, 0), getSpawnPosition().translated(-6, -6, 0));
            List<Entity> targets = getPossibleTargets(24, true, false);
            List<Entity> possibleTargets = new ArrayList<Entity>();
            for (Entity m : targets) {
                if (rec.inside(m.getPosition())) {
                    possibleTargets.add(m);
                }
            }
            if (possibleTargets.size() == 1) {
                Position base = getSpawnPosition().translated(-5, -5, 0);
                int x = Misc.random(15), y = Misc.random(15);
                if (x >= 5 && x <= 10) {
                    x = Misc.random(10) < 5 ? 4 : 10;
                }
                if (y >= 6 && y <= 9) {
                    y = Misc.random(10) < 5 ? 5 : 10;
                }
                dest2 = base.translated(x, y, 0);
            } else {
                List<Position> dests = new ArrayList<Position>();
                for (Entity pl : possibleTargets) {
                    if (pl == lastTarget) {
                        continue;
                    }
                    dests.add(pl.getPosition());
                }
                Collections.shuffle(dests);
                dest2 = dests.get(Misc.random(dests.size() - 1));
            }
            if (Misc.random(10) <= 5) {
                delay2 = 100;
                if (getCentrePosition().distance(dest2) == 1) {
                    delay2 = 60;
                } else if (getPosition().distance(dest2) <= 5) {
                    delay2 = 80;
                } else if (getPosition().distance(dest2) <= 8) {
                    delay2 = 100;
                } else {
                    delay2 = 120;
                }
                final Position ricochet = dest2;
                Projectile proj2 = new Projectile(POISON_PROJECTILE, 0, 0, 0, 60, 0, 50, 0);
                proj2.send(getAbsX(), getAbsY(), ricochet.getX(), ricochet.getY());
                final int del2 = (delay2 / 20) - 2;
                World.startEvent(ee -> {
                    ee.delay(del2);
                    for (Player player : getPosition().getRegion().players) {
                        player.getPacketSender().sendGraphics(POISON_GRAPHIC, 0, 0, ricochet.getX(), ricochet.getY(), ricochet.getZ());
                    }
                    if (getPosition().getRegion() == ricochet.getRegion() && !marked.contains(ricochet.tileHash())) {
                        XarpusPoison p2 = new XarpusPoison(ricochet, getPoisonDirection(ricochet), this);
                        poison.add(p2);
                        marked.add(ricochet.tileHash());
                    }
                });
            }
        });
    }

    private int getPoisonDirection(Position dest) {
        return 0;
    }


    public int getQuadrant() {
        return quadrant;
    }

}
