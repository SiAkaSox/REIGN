package io.ruin.model.activities.wilderness.events.seren;

import io.ruin.utility.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.utility.Misc;

public class Seren extends NPCCombat {

    private static final Projectile RANGED_PROJECTILE = new Projectile(856, 0, 0, 50, 65, 6, 0, 255);

    @Override
    public void init() {
        npc.hitListener = new HitListener().postDefend(this::postDefend);
    }

    private void postDefend(Hit hit) {
        if (hit.damage > 100 && !hit.isBlocked())
            hit.damage = 100;
    }



    @Override
    public void updateLastDefend(Entity attacker) {
        super.updateLastDefend(attacker);
        super.updateLastDefend(attacker);
    }


    @Override
    public void startDeath(Hit killHit) {
        super.startDeath(killHit);
        SerenEvent.serenDied();
            Killer firstPlaceKiller = null; //TOP 1st
            Killer secondPlaceKiller = null; //TOP 2nd
            Killer thirdPlaceKiller = null; //TOP 3rd
            for(Killer killas : npc.getCombat().killers.values()) {
                if (killas == null || killas.player == null || killas.damage < 1) {
                    continue;
                }
                if(firstPlaceKiller == null || killas.damage > firstPlaceKiller.damage)
                {
                    thirdPlaceKiller = secondPlaceKiller;
                    secondPlaceKiller = firstPlaceKiller;
                    firstPlaceKiller = killas;
                }
                else if (secondPlaceKiller == null || killas.damage > secondPlaceKiller.damage)
                {
                    thirdPlaceKiller = secondPlaceKiller;
                    secondPlaceKiller = killas;
                }
                else if  (thirdPlaceKiller == null || killas.damage > thirdPlaceKiller.damage)
                {
                    thirdPlaceKiller = killas;
                }
            }

            if (firstPlaceKiller != null) {
                firstPlaceKiller.player.getInventory().addOrDrop(new Item(3462, 1));
                firstPlaceKiller.player.sendMessage("You receive a key for killing The Fragment of Seren!");
            }
            if (secondPlaceKiller != null) {
                secondPlaceKiller.player.getInventory().addOrDrop(new Item(3462, 1));
                secondPlaceKiller.player.sendMessage("You receive a key for killing The Fragment of Seren!");
            }
            if (thirdPlaceKiller != null) {
                thirdPlaceKiller.player.getInventory().addOrDrop(new Item(3462, 1));
                thirdPlaceKiller.player.sendMessage("You receive a key for killing The Fragment of Seren!");
            }

        /*npc.addEvent(e -> {
            setDead(true);
            npc.faceNone(false);
            npc.animate(8380);
            e.delay(2);
            npc.remove();
            npc.localPlayers().forEach(p -> {
            });
        });*/
    }

    public static void startRewards(Player p) {
        p.getInventory().addOrDrop(3462, 1);
        p.sendMessage("You receive a key for killing The Fragment of Seren!");
        p.getPacketSender().sendString(859, 144, "Spawned: <col=d22222>Inactive");
    }

    @Override
    public void follow() {
        follow(0);
    }

    @Override
    public boolean attack() {
        if(!withinDistance(12))
            return false;
        else if(Random.rollDie(12, 1)) {
            crystalAttack();
        } else if(Random.rollDie(18, 1)) {
            cloneAttack();
        } else if(Random.rollDie(15, 1)) {
            teleportAttack();
        }
         rangedAttack();
        return true;
    }

    private void teleportAttack() {
        npc.addEvent(event -> {
            event.delay(1);
            npc.animate(8378);
            npc.forceText("She acted like I was a monster! Come... look upon her monster!");
            event.delay(1);
            npc.localPlayers().forEach(player -> {
                player.getMovement().teleport(npc.getPosition().getX(), npc.getPosition().getY() - 1, npc.getPosition().getZ());
            player.sendMessage("<col=1a0068>She acted like I was a monster! Come... look upon her monster!");
            });
            event.delay(1);
            npc.animate(8379, 2);
            event.delay(3);
            npc.localPlayers().forEach(player -> {
                if (withinDistance(2)) {
                    player.hit(new Hit().randDamage(20));
                }
            });
        });
    }

    private static final Projectile HEALER_PROJECTILE = new Projectile(660, 15, 80, 0, 50, 0, 16, 32);

    private void crystalAttack() {
        npc.addEvent(event -> {
            event.delay(1);
            npc.animate(8378);
            npc.forceText("Did she think I would rot here? I am eternal, unlike you!");
            npc.localPlayers().forEach(player -> {
                player.sendMessage("<col=1a0068>Did she think I would rot here? I am eternal, unlike you!");
            });
            event.delay(2);
            NPC crystal1 = new NPC(8921).spawn(npc.getPosition().getX() + 3, npc.getPosition().getY() - 3, npc.getPosition().getZ(), 0);
            NPC crystal2 = new NPC(8921).spawn(npc.getPosition().getX() + 3, npc.getPosition().getY() + 3, npc.getPosition().getZ(), 0);
            event.delay(2);
            npc.forceText("I won't let her destroy me! She doesn't get to decide that!");
            npc.localPlayers().forEach(player -> {
                player.sendMessage("<col=1a0068>I won't let her destroy me! She doesn't get to decide that!");
            });
            NPC crystal3 = new NPC(8921).spawn(npc.getPosition().getX() - 3, npc.getPosition().getY() - 3, npc.getPosition().getZ(), 0);
            NPC crystal = new NPC(8921).spawn(npc.getPosition().getX() - 3, npc.getPosition().getY() + 3, npc.getPosition().getZ(), 0);
            crystal.startEvent(event1 -> {
                crystal.lock();
                event1.delay(3);
                crystal.face(npc);
                crystal.unlock();
                while (true) {
                    if (npc.getHp() < npc.getMaxHp()) {
                        HEALER_PROJECTILE.send(crystal, npc);
                        npc.hit(new Hit(HitType.HEAL).randDamage(4, 8).delay(2));
                        event1.delay(4);
                    }
                    event1.delay(1);
                }
            });
            crystal1.startEvent(event2 -> {
                crystal1.lock();
                event2.delay(3);
                crystal1.face(npc);
                crystal1.unlock();
                while (true) {
                    if (npc.getHp() < npc.getMaxHp()) {
                        HEALER_PROJECTILE.send(crystal1, npc);
                        npc.hit(new Hit(HitType.HEAL).randDamage(4, 8).delay(2));
                        event2.delay(4);
                    }
                    event2.delay(1);
                }
            });
            crystal2.startEvent(event3 -> {
                crystal2.lock();
                event3.delay(3);
                crystal2.face(npc);
                crystal2.unlock();
                while (true) {
                    if (npc.getHp() < npc.getMaxHp()) {
                        HEALER_PROJECTILE.send(crystal2, npc);
                        npc.hit(new Hit(HitType.HEAL).randDamage(4, 8).delay(2));
                        event3.delay(4);
                    }
                    event3.delay(1);
                }
            });
            crystal3.startEvent(event4 -> {
                crystal3.lock();
                event4.delay(3);
                crystal3.face(npc);
                crystal3.unlock();
                while (true) {
                    if (npc.getHp() < npc.getMaxHp()) {
                        HEALER_PROJECTILE.send(crystal3, npc);
                        npc.hit(new Hit(HitType.HEAL).randDamage(4, 8).delay(2));
                        event4.delay(4);
                    }
                    event4.delay(1);
                }
            });
        });
    }

    private void cloneAttack() {
        Entity entity = Random.rollDie(2, 1) ? npc : target;
        Position destination = entity.getPosition().copy().translate(Random.get(0, 5), Random.get(0, 5), 0);
        Position cloneDestination = entity.getPosition().copy().translate(Random.get(-5, 0), Random.get(-5, 0), 0);
        npc.addEvent(event -> {
            event.delay(1);
            npc.localPlayers().forEach(player -> {
                target.getCombat().reset();
                player.sendMessage("<col=1a0068>She left me, like I wasn't real! But I know what's real, do you?");
            });
            npc.animate(8378, 2);
            npc.forceText("She left me, like I wasn't real! But I know what's real, do you?");
            event.delay(2);
            if (Random.get(2) == 1) {
                /*if (destination.equals(entity.getPosition()))
                    return;*/
                if (!ProjectileRoute.allow(entity, destination))
                    return;
                entity.getMovement().teleport(destination);
                NPC copy = new NPC(8917).spawn(cloneDestination);
                target.getCombat().reset();
                copy.startEvent(event1 -> {
                    copy.lock();
                    event1.delay(3);
                    copy.unlock();
                    while (true) {
                        if (npc.getHp() < npc.getMaxHp()) {
                            copy.localPlayers().forEach(player -> {
                                copy.targetPlayer(player, false);
                            });
                            event1.delay(4);
                        }
                        event1.delay(1);
                    }
                });
                npc.forceText("She left me, like I wasn't real! But I know what's real, do you?");
                copy.forceText("She left me, like I wasn't real! But I know what's real, do you?");
                npc.localPlayers().forEach(player -> {
                });
            } else if (Random.get(2) == 2) {
                /*if (destination.equals(entity.getPosition()))
                    return;*/
                if (!ProjectileRoute.allow(entity, destination))
                    return;
                entity.getMovement().teleport(destination);
                NPC copy = new NPC(8917).spawn(cloneDestination);
                target.getCombat().reset();
                copy.startEvent(event1 -> {
                    copy.lock();
                    event1.delay(3);
                    copy.unlock();
                    while (true) {
                        if (npc.getHp() < npc.getMaxHp()) {
                            copy.localPlayers().forEach(player -> {
                                copy.targetPlayer(player, false);
                            });
                            event1.delay(4);
                        }
                        event1.delay(1);
                    }
                });
                npc.forceText("She left me, like I wasn't real! But I know what's real, do you?");
                copy.forceText("She left me, like I wasn't real! But I know what's real, do you?");
                npc.localPlayers().forEach(player -> {
                });
            }
            event.delay(2);
        });
    }

    private void rangedAttack() {
        npc.animate(8376);
        npc.localPlayers().forEach(p -> {
            if(!canAttack(p) || Misc.getEffectiveDistance(npc, p) > 8)
                return;
            int delay = HEALER_PROJECTILE.send(npc, p);
            p.hit(new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).clientDelay(delay).ignorePrayer());
        });
    }



}
