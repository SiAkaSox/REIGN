package io.ruin.model.activities.raids.theatre_of_blood.boss.maiden_of_sugadinti;

import com.google.common.collect.Sets;
import io.netty.util.internal.ConcurrentSet;
import io.ruin.model.World;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreOfBlood;
import io.ruin.model.activities.raids.theatre_of_blood.TheatrePartiesManager;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreParty;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Misc;

import java.util.List;
import java.util.Set;

public class MaidenOfSugadintiCombat extends NPCCombat {

    private static final int BLOOD_THROWN = 1578, BLOOD_SPLAT = 1579, TORNADO_THROWM = 1577;

    private Set<BloodSplatterFloor> activeBlood = Sets.newConcurrentHashSet();

    enum Attacks {

        BLOOD(BLOOD_THROWN, 1576, BLOOD_SPLAT),
        TORNADO(TORNADO_THROWM, -1, -1);

        int thrown, impact, floor;

        Attacks(int thrown, int impact, int floor) {
            this.thrown = thrown;
            this.impact = impact;
            this.floor = floor;
        }

    }

    @Override
    public void init() {

    }

    @Override
    public void follow() {

    }

    private int mageAttackTicks = 10;

    @Override
    public boolean attack() {
        TheatreNPC n = (TheatreNPC) npc;
        List<Entity> meleeTargets = n.getPossibleTargets(6, true, false);
        int randomNum = Misc.random(5);
        boolean successfulMeleeAttack = randomNum <= 3;
        if (meleeTargets.size() > 0 && successfulMeleeAttack) {
            n.animate(8092);
            for (Entity target : meleeTargets) {
                int damage = Misc.random(target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE) ? (35 / 2) : 35);
                target.hit(new Hit().fixedDamage(damage).ignorePrayer());
            }
        } else {
            Attacks attack = Misc.random(10) > 5 ? Attacks.BLOOD : Attacks.TORNADO;
            System.err.println("Attack chosen... = " + attack.name());
            n.animate(8092);//TODO find
            n.addEvent(e -> {
                e.delay(1);
                for (Entity target : n.getPossibleTargets(18, true, false)) {
                    Projectile projectile = new Projectile(attack.thrown, 50, 0, 1, 60, 0, 15, 0);
                    if (attack == Attacks.TORNADO) {
                        projectile.setCurve(0);
                        projectile.setStartHeight(0);
                        projectile.send(n, target);
                    } else {
                        projectile.send(n.getCentrePosition().getX(), n.getCentrePosition().getY(), target.getPosition().getX(), target.getPosition().getY());
                    }
                    final Position dest = target.getPosition().copy();
                    int delay = 60;
                    if (n.getCentrePosition().distance(dest) == 1) {
                        delay = 60;
                    } else if (n.getCentrePosition().distance(dest) <= 5) {
                        delay = 80;
                    } else if (n.getCentrePosition().distance(dest) <= 8) {
                        delay = 100;
                    } else {
                        delay = 120;
                    }
                    handleDamage(n, target, delay, dest, Prayer.PROTECT_FROM_MAGIC, attack.impact, attack.floor, attack == Attacks.BLOOD);
                }
            });
        }
        n.getCombat().delayAttack(10);
        return true;
    }

    public static int getHitDelay(Entity attacker, Entity victim, int delay, int speed) {
        /** The distance between the entities. */
        int distance = attacker.getPosition().distance(victim.getPosition());
        /** The speed at which the projectile is traveling. */
        int projectileSpeed = delay + speed + distance * 5;
        /** The delay of the hit. */
        double hitDelay = projectileSpeed * .02857;
        /** Returns the hit delay. */
        return (int) hitDelay;
    }

    private void handleDamage(Entity attacker, Entity victim, int delay, final Position dest, Prayer prayer, int splat, int bloodSplat, boolean bloodAttack) {
        MaidenOfSugadintiPlugin n = (MaidenOfSugadintiPlugin) npc;

        victim.addEvent(e -> {
            e.delay((delay / 20) - 2);
            for (Entity player : n.getPossibleTargets(64, true, false)) {
                /** SHOULD ALWAYS BE NPC AS ATTACKER **/
                int maxHit = attacker instanceof NPC ? getMaxDamage() : 0;
                TheatreOfBlood tob = TheatrePartiesManager.getMyParty(victim.player).theatre;

                if (player.player.getPrayer().isActive(prayer)) {
                    maxHit = maxHit > 0 ? (maxHit / 2) : 0;
                }
                /** Per Spider is 1.8+ extra damage for boss basic attacks **/
                if (tob.maiden != null) {
                    maxHit += tob.maiden.spiderFaction != null ? (int) (tob.maiden.spiderFaction.size() * 1.8) : 0;
                }
                if (bloodAttack) {
                    player.player.getPacketSender().sendGraphics(bloodSplat, 0, 0, dest.getX(), dest.getY(), dest.getZ());
                    bloodSplat(dest, player);
                } else {
                    if (player != victim)
                        continue;
                    player.hit(new Hit().randDamage(maxHit).ignorePrayer());
                }
            }
        });
    }

    private void bloodSplat(final Position target, Entity player) {
        activeBlood.add(new BloodSplatterFloor(target));
        World.startEvent(e -> {
            while(activeBlood.size() > 0) {
                e.delay(1);
                MaidenOfSugadintiPlugin n = (MaidenOfSugadintiPlugin) npc;
                boolean onTile = player.getAbsX() == target.getX() && player.getAbsY() == target.getY();
                if (onTile) {
                    int hit = Misc.random(1, 5);
                    player.hit(new Hit().fixedDamage(hit).ignorePrayer());
                    npc.hit(new Hit(HitType.HEAL).fixedDamage(hit).ignorePrayer());
                    if (Math.random() < .1) {
                        n.addBloodSpawn(target);
                    }
                }
                for (BloodSplatterFloor floor : activeBlood) {
                    if (floor == null)
                        continue;
                    activeBlood.removeIf(f -> f.duration < System.currentTimeMillis());
                }
            }
        });
    }
}
