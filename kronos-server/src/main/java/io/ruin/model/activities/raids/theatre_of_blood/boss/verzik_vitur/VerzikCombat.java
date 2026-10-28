package io.ruin.model.activities.raids.theatre_of_blood.boss.verzik_vitur;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import io.ruin.utility.Random;
import io.ruin.model.World;
import io.ruin.model.activities.raids.theatre_of_blood.boss.nylocas_vasilias.NylocasSpawnPlugin;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;

public class VerzikCombat extends NPCCombat
{
	
	public static final int ELECTRIC_ORB = 1580, ELECTRIC_IMPACT = 1581, ELECTRIC_IMPACT_PILLAR = 1582, BOMB_PROJECTILE = 1583, BOMB_IMPACT = 1584, BLUE_SPARK = 1585, PURPLE_SPARK = 1586, RANGED_PROJECTILE = 1593, MAGIC_PROJECTILE = 1594, GREEN_BOMB = 1598, GREEN_BOMB_IMPACT = 1600;

	private VerzikViturPlugin v;

	private int spiderCycle = 0;

	private boolean flowered;
	
	public VerzikCombat(VerzikViturPlugin v)
	{
		this.v = v;
	}

	@Override
	public void init() {

	}

	@Override
	public void follow() {

	}

	@Override
	public boolean attack() {
		if (v.getStage() == VerzikStage.UNDISRUPTED || v.get("transitioning") != null || v.getCombat().attackDelayTicks > 0) {
			return false;
		}
		switch(v.getStage()) {
			case FIGHT_SHIELD: {
				if (v.cycle < 8) return false;
				List<VerzikPillar> pillars = new ArrayList<VerzikPillar>();
				List<Entity> targets = new ArrayList<Entity>();
				for (Player player : v.getPosition().getRegion().players) {
					Entity target = v.closestPillar(player);
					if (target == null) {
						targets.add(player);
					} else {
						if (!pillars.contains(target)) {
							pillars.add((VerzikPillar) target);
						}
					}
				}
				for (VerzikPillar p : pillars) {
					final Position dest = p.getPosition().copy();
					int delay = 60;
					if (v.getCentrePosition().distance(dest) == 1) {
						delay = 60;
					} else if (v.getCentrePosition().distance(dest) <= 5) {
						delay = 80;
					} else if (v.getCentrePosition().distance(dest) <= 8) {
						delay = 100;
					} else {
						delay = 120;
					}
					Projectile PROJECTILE = new Projectile(ELECTRIC_ORB, 110, 0, 1, 90, 0, 30, 0);
					PROJECTILE.send(v.getAbsX(), v.getAbsY(), p.getPosition().getX(), p.getPosition().getY());
					final int del = (delay / 20) - 2;
					World.startEvent(e -> {
						e.delay(del);
						final int dmg = Misc.random(40, 70);
						p.hit(new Hit().fixedDamage(dmg));
						World.sendGraphics(ELECTRIC_IMPACT_PILLAR, 0, 0, p.getPosition());
					});
				}
				for (Entity p : targets) {
					final Position dest = p.getPosition().copy();
					int delay = 60;
					if (v.getCentrePosition().distance(dest) == 1) {
						delay = 60;
					} else if (v.getCentrePosition().distance(dest) <= 5) {
						delay = 80;
					} else if (v.getCentrePosition().distance(dest) <= 8) {
						delay = 100;
					} else {
						delay = 120;
					}
					Projectile PROJECTILE = new Projectile(ELECTRIC_ORB, 110, 0, 0, 60, 0, 15, 0);
					PROJECTILE.send(v, p);
					final int del = (delay / 20) - 2;
					World.startEvent(e -> {
						e.delay(del);
						p.graphics(ELECTRIC_IMPACT);
						final int dmg = Misc.random(target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ? 65 : 138);
						p.hit(new Hit().fixedDamage(dmg));
					});
				}
				v.animate(8109);
				v.getCombat().delayAttack(15);
				return true;
			}

			case FIGHT_MUTABLE: {
				List<Player> targets = v.getPlayers();
				List<Entity> melee_targets = new ArrayList<Entity>();
				for (Entity p : targets) {
					if (p.getPosition().distance(v.getCentrePosition()) <= 2 && !melee_targets.contains(p)) {
						melee_targets.add(p);
					}
				}
				if (targets.size() == 0) {
					v.getCombat().delayAttack(3);
					return true;
				}
				if (Misc.random(10) == 7) {
					Player target = targets.get(Misc.random(targets.size() - 1));
					v.animate(8114);
					int spiderCap = Misc.random(1, targets.size());
					if (Misc.random(10) >= 5) {
						for (int i = 0; i < spiderCap; i++) {
							Position loc = v.getPosition().translated(-6, -6, 0).translated(Misc.random(12), Misc.random(12), 0);
							NylocasSpawnPlugin nylocas = new NylocasSpawnPlugin(8345 + Misc.random(0,2), loc.copy());
							nylocas.setNodes(v.getNodes());
							nylocas.register();
							nylocas.setTarget(targets.get(Misc.random(targets.size() - 1)));
							nylocas.bossRoom = true;
							nylocas.getCombat().setTarget(targets.get(Misc.random(targets.size() - 1)));
						}
						final Position dest = target.getPosition();
						int delay = 60;
						if (v.getCentrePosition().distance(dest) == 1) {
							delay = 60;
						} else if (v.getCentrePosition().distance(dest) <= 5) {
							delay = 100;
						} else if (v.getCentrePosition().distance(dest) <= 8) {
							delay = 100;
						} else {
							delay = 120;
						}
						Projectile PROJECTILE = new Projectile(PURPLE_SPARK, 110, 0, 0, 60, 0, 15, 0);
						PROJECTILE.send(v.getAbsX(), v.getAbsY(), target.getPosition().getX(), target.getPosition().getY());
						final int del = (delay / 20) - 2;
						World.startEvent(e -> {
							e.delay(del);
							NylocasAthanatosPlugin atanatos = new NylocasAthanatosPlugin(v, dest.copy());
							atanatos.register();
							atanatos.getCombat().setTarget(v);
							atanatos.getCombat().attack();
						});
						v.getCombat().delayAttack(3);
						return true;
					} else {
						Position dest = target.getPosition();
						Projectile PROJECTILE = new Projectile(BLUE_SPARK, 110, 0, 0, 60, 0, 15, 0);
						PROJECTILE.send(v, target);
						int delay = 120;
						if (v.getCentrePosition().distance(dest) == 1) {
							delay = 120;
						} else if (v.getCentrePosition().distance(dest) <= 5) {
							delay = 100;
						} else if (v.getCentrePosition().distance(dest) <= 8) {
							delay = 60;
						} else {
							delay = 80;
						}
						final int dmg = Misc.random(50);
						final int del = (delay / 20) - 2;
						World.startEvent(e -> {
							e.delay(del);
							if (target.getPosition().distance(v.getPosition()) < 3) {
								Projectile PROJ = new Projectile(BLUE_SPARK, 0, 110, 0, 60, 0, 15, 0);
								PROJ.send(v, target);
							} else {
								target.hit(new Hit().randDamage(dmg));
							}
						});
					}
					v.getCombat().delayAttack(3);
					return true;
				} else if (melee_targets.size() > 0) {
					v.animate(8116);
					World.startEvent(e -> {
						e.delay(1);
							for (Entity victim : melee_targets) {
								victim.hit(new Hit().randDamage(v.getCombat().getInfo().max_damage));
								ArrayList<String> badDirections = new ArrayList<String>();
								Collection<GameObject> objects = Collections.unmodifiableCollection(new LinkedList<GameObject>(victim.getPosition().getRegion().getAllObjects()));
								badDirections.add("West");
								badDirections.add("North");
								badDirections.add("South");
								badDirections.add("East");
								if(victim.getPosition().getX() < v.getPosition().getX()) {
									badDirections.remove("West");
								} else if(victim.getPosition().getY() > v.getPosition().getY()) {
									badDirections.remove("North");
								} else if (victim.getPosition().getY() < v.getPosition().getY()) {
									badDirections.remove("South");
								} else if(victim.getPosition().getX() > v.getPosition().getX()) {
									badDirections.remove("East");
								}
								for(GameObject obj : objects) {
									if(obj != null && obj.type != 22 && obj.type != 0) {
										if(obj.position().equals(new Position(victim.getPosition().getX() - 1, victim.getPosition().getY(), victim.getPosition().getZ())) && obj.getHeight() == victim.getHeight()) {
											badDirections.add("West");
										} else if(obj.position().equals(new Position(victim.getPosition().getX(), victim.getPosition().getY(), victim.getPosition().getZ())) && obj.getHeight() == victim.getHeight()) {
											badDirections.add("North");
										} else 	if(obj.position().equals(new Position(victim.getPosition().getX(), victim.getPosition().getY(), victim.getPosition().getZ())) && obj.getHeight() == victim.getHeight()) {
											badDirections.add("South");
										} else if(obj.position().equals(new Position(victim.getPosition().getX(), victim.getPosition().getY(), victim.getPosition().getZ())) && obj.getHeight() == victim.getHeight()) {
											badDirections.add("East");
										}
									}
								}
								Position newPosition = null;
								if (!badDirections.contains("West")) {
									newPosition = new Position(victim.getPosition().getX() - 1, victim.getPosition().getY(), 0);
								} else if (!badDirections.contains("North")) {
									newPosition = new Position(victim.getPosition().getX(), victim.getPosition().getY() + 1, 0);
								} else if (!badDirections.contains("South")) {
									newPosition = new Position(victim.getPosition().getX(), victim.getPosition().getY() - 1, 0);
								} else if (!badDirections.contains("East")) {
									newPosition = new Position(victim.getPosition().getX() + 1, victim.getPosition().getY(), 0);
								}
								badDirections.clear();
								if (newPosition == null) {
									return;
								}
								v.getCombat().reset();
								victim.player.getMovement().reset();
								DumbRoute.route(victim.player, newPosition.getX(), newPosition.getY());
								victim.stun(3, true);
							}
					});
					v.getCombat().delayAttack(4);
					return true;
				} else {
					v.animate(8114);
					for (Player p : targets) {
						final Position dest = p.getPosition().copy();
						int delay = 60;
						if (v.getCentrePosition().distance(dest) == 1) {
							delay = 60;
						} else if (v.getCentrePosition().distance(dest) <= 5) {
							delay = 100;
						} else if (v.getCentrePosition().distance(dest) <= 8) {
							delay = 100;
						} else {
							delay = 120;
						}
						Projectile PROJECTILE = new Projectile(BOMB_PROJECTILE, 110, 0, 0, 60, 0, 15, 0);
						PROJECTILE.send(v.getAbsX(), v.getAbsY(), p.getPosition().getX(), p.getPosition().getY());
						final int del = (delay / 20) - 2;
						World.startEvent(e -> {
							e.delay(del);
							for (Player p2 : v.getPosition().getRegion().players) {
								p2.getPacketSender().sendGraphics(BOMB_IMPACT, 0, 0, dest.getX(), dest.getY(), dest.getZ());
								if (p2.getPosition().equals(dest)) {
									p2.hit(new Hit().randDamage(p2.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES) ? 25 : 50));
								}
							}
						});
					}
					v.getCombat().delayAttack(3);
					return true;
				}
			}

			case FIGHT_SPIDER: {
				List<Player> targets = v.getPlayers();
				if (targets.size() == 0 || v.get("special_attack") != null) {
					v.getCombat().delayAttack(4);
					return true;
				}
				switch(++spiderCycle) {
					case 5: {
						if (v.getCombat().getStat(StatType.Hitpoints).currentLevel > 800) {
							Player target = targets.get(Random.get(targets.size() - 1));
							v.animate(8123);
							int spiderCap = Random.get(1, targets.size());
							for (int i = 0; i < spiderCap; i++) {
								Position loc = v.getPosition().translated(-6, -6, 0).translated(Random.get(12), Random.get(12), 0);
								NylocasSpawnPlugin nylocas = new NylocasSpawnPlugin(8345 + Random.get(0, 2), loc);
								nylocas.setNodes(v.getNodes());
								nylocas.register();
								nylocas.setTarget(targets.get(Random.get(targets.size() - 1)));
								nylocas.getCombat().setTarget(nylocas.getTarget());
								nylocas.bossRoom = true;
								nylocas.getCombat().attack();
							}
							v.getCombat().delayAttack(4);
							return true;
						}
					}
					case 10:
					case 15: {
						npc.set("special_attack", spiderCycle == 10 || flowered);
						Position base = (boolean) npc.get("special_attack") ? npc.getSpawnPosition().translated(0, -10, 0) : npc.getPosition();
						npc.getCombat().reset();
						if (!npc.getPosition().equals(base)) {
							npc.set("positioning", true);
							return true;
						}
						if (!flowered && spiderCycle == 15) {
							flowered = true;
						}
						v.getCombat().delayAttack(4);
						spiderCycle = 0;
						return true;
					}
					default: {
						List<Entity> melee_targets = new ArrayList<Entity>();
						for (Entity p : targets) {
							if (p.getPosition().distance(v.getCentrePosition()) <= 2) {
								melee_targets.add(p);
							}
						}
						final AttackStyle style = Misc.random(10) < 5 ? AttackStyle.RANGED : AttackStyle.MAGIC;
						v.animate(style == AttackStyle.RANGED ? 8125 : 8124);
						for (Player p : targets) {

							Projectile PROJECTILE = new Projectile(style == AttackStyle.RANGED ? RANGED_PROJECTILE : MAGIC_PROJECTILE, 110, 0, 0, 60, 0, 15, 0);
							PROJECTILE.send(v, p);

							int delay = 100;
							if (v.getCentrePosition().distance(p.getPosition()) == 1) {
								delay = 60;
							} else if (v.getPosition().distance(p.getPosition()) <= 5) {
								delay = 80;
							} else if (v.getPosition().distance(p.getPosition()) <= 8) {
								delay = 100;
							} else {
								delay = 120;
							}
							final int del = (delay / 20) ;
							World.startEvent(e -> {
								e.delay(del);
								int damage = p.getPrayer().isActive(style == AttackStyle.RANGED ? Prayer.PROTECT_FROM_MISSILES : Prayer.PROTECT_FROM_MAGIC) ? 16 : 50;
								p.hit(new Hit().randDamage(0, damage));
							});
						}
						v.getCombat().delayAttack(4);
						return true;
					}
				}
			}
		}
		return true;
	}

	public void resetAttackDelay() {
		attackDelayTicks = 0;
	}
}