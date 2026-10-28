package io.ruin.model.activities.raids.theatre_of_blood.boss.sotetseg;

import io.ruin.model.World;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.activities.raids.theatre_of_blood.TheatrePartiesManager;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreParty;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SotetsegPlugin extends TheatreNPC {

    private static final int ENERGY_PROJECTILE = 1604, ENERGY_IMPACT = 1605;

    private int attackDelta = 0, nextTarget;

    private Map<Player, Integer> damage = new HashMap<Player, Integer>();

    public SotetsegPlugin(int id, Position base) {
        super(id, base);
        deathStartListener = (entity, killer, killHit) -> onDeath();
    }

    private void onDeath() {
        GameObject chest = new GameObject(37383, getSpawnPosition().translated(4, -33, 0), 10, 0).spawn();
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
    public SotetsegCombat getCombat() {
        if (combat == null || !(combat instanceof SotetsegCombat)) {
            initCombat();
        }
        return (SotetsegCombat) combat;
    }

    public void initCombat() {
        combat = new SotetsegCombat();
        combat.init(this, getDef().combatInfo);
    }

    @Override
    public boolean isMovementBlocked(boolean message, boolean ignoreFreeze) {
        return true;
    }

    @Override
    public void process() {
        super.process();
        if (++attackDelta % 10 == 0 && Misc.random(10) == 7) {
            List<Entity> targets = getPossibleTargets(14, true, false);
            Entity target = null;
            if (targets.size() > nextTarget) {
                target = targets.get(nextTarget++);
            } else if (targets.size() > 0) {
                nextTarget = 0;
                target = targets.get(0);
            } else {
                return;
            }
            animate(getCombat().getInfo().attack_animation + 1);
            target.player.sendMessage("<col=ff0000>A ball of energy is shot your way...");
            Position dest = target.player.getPosition();
            Projectile PROJECTILE = new Projectile(ENERGY_PROJECTILE, 110, 0, 1, 300, 0, 15, 50);
            PROJECTILE.send(this, target);
            final Entity t = target;
            World.startEvent(e -> {
                e.delay(6);
                t.graphics(ENERGY_IMPACT);
                int dmg = t.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ? 70 : t.player.getStats().get(StatType.Hitpoints).currentLevel;
                for (Entity p : getPossibleTargets(64, true, false)) {
                    if (!p.isPlayer() || t == p) continue;
                    if (p.getPosition().distance(t.getPosition()) <= 2) {
                        dmg /= 2;
                        t.hit(new Hit().fixedDamage(t.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ? 0 : dmg));
                    }
                }
                t.hit(new Hit().fixedDamage(dmg));
            });
        }
    }

    @Override
    public ArrayList<Entity> getPossibleTargets(int ratio, boolean players, boolean npcs) {
        ArrayList<Entity> possibleTargets = new ArrayList<Entity>();
        if (players) {
            for (Player player : World.players) {
                if (player == null) {
                    continue;
                }
                if (player == null || (TheatrePartiesManager.getRaid(player.getPosition()) != null && TheatrePartiesManager.getRaid(player.getPosition()).isDead(player)) || player.getCombat().isDead() || player.getPosition().distance(this.getCentrePosition()) > ratio) {
                    continue;
                }
                possibleTargets.add(player);
            }
        }
        if (npcs) {
            for (NPC npc : World.npcs) {
                if (npc == null || npc == this || npc.getCombat() == null || npc.getCombat().isDead() || npc.getCentrePosition().distance(this.getCentrePosition()) > ratio) {
                    continue;
                }
                possibleTargets.add(npc);
            }
        }
        return possibleTargets;
    }

    @Override
    public int hit(Hit... hits) {
        boolean above66 = getCombat().getStat(StatType.Hitpoints).currentLevel > (getCombat().getStat(StatType.Hitpoints).fixedLevel * 0.66);
        boolean above33 = getCombat().getStat(StatType.Hitpoints).currentLevel > (getCombat().getStat(StatType.Hitpoints).fixedLevel * 0.33);
        Entity attacker = null;
        for(Hit hit : hits) {
            if (hit.attacker != null && attacker == null) {
                attacker = hit.attacker;
            }
        }
        int dmg = super.hit(hits);
        if (attacker != null && attacker.player != null) {
            int current = damage.get(attacker.player) == null ? 0 : damage.get(attacker.player);
            damage.put(attacker.player, current + dmg);
        }
        boolean dead = getCombat().getStat(StatType.Hitpoints).currentLevel <= 10 || getCombat().isDead();
        return dmg;
    }
}
