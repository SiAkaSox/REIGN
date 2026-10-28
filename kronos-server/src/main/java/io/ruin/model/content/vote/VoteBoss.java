package io.ruin.model.content.vote;

import com.google.api.client.util.Lists;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;
import io.ruin.utility.Random;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class VoteBoss extends NPCCombat {

    private List<NPC> troll_spectators = new CopyOnWriteArrayList<>();

    public VoteBossAttack phase = VoteBossAttack.JUMP_TO_TARGET;

    @Override
    public void init() {
        List<Position> tiles = Arrays.asList(
                new Position(2899, 3608, 0),
                new Position(2905, 3600, 0),
                new Position(2913, 3599, 0),
                new Position(2920, 3602, 0),
                new Position(2923, 3608, 0),
                new Position(2923, 3618, 0),
                new Position(2911, 3625, 0),
                new Position(2903, 3622, 0));

        tiles.stream().forEach(t -> {
            NPC spectator = new NPC(4127).spawn(t);
            spectator.walkRange = 0;
            spectator.forceText("Daddy!");
            spectator.freeze(Integer.MAX_VALUE, spectator);
            spectator.attackBounds = new Bounds(2900, 3597, 2925, 3628, 0);
            spectator.addEvent(e -> {
                e.delay(3);
                spectator.face(npc);
            });
            troll_spectators.add(spectator);
        });
        npc.hitListener = new HitListener().postDamage(VoteBoss::postDamage);
        Broadcast.WORLD.sendNews("The Vote boss has appeared! (::voteboss)");
    }

    private static void postDamage(Hit hit) {
        int damage = hit.damage;
        if (damage >= 40) {
            if (Math.random() < .25) {
                hit.damage = 0;
                Player player = hit.attacker.player;
                player.sendMessage("Half of the damage you dealt was reflected back into you! ");
                player.forceText("Ow!");
                hit.attacker.hit(new Hit().fixedDamage(damage / 2).ignorePrayer().ignoreDefence());
            }
        }
    }

    @Override
    public void follow() {
        if (phase != null)
            return;
        follow(2);
    }

    @Override
    public boolean attack() {

        if (npc.isFrozen()) {
            npc.resetFreeze();
        }

        if (Math.random() < 0.15) {
            npc.graphics(444, 250, 0);
            npc.incrementHp(Random.get(25, 50));
        }

        if (Math.random() <= .25) {
            specialAttack();
            return true;
        }

        if (!withinDistance(2)) {
            follow(2);
            return true;
        }

        //1158 = log swing
        //4991.. jump to target deal deal damage to people within 4 tiles
        //5779 = punch
        //4989 = death
        //4988 = defend
        basicAttack(info.attack_animation, AttackStyle.CRUSH, info.max_damage);
        return true;
    }

    private void specialAttack() {
        phase = VoteBossAttack.values()[Misc.random(VoteBossAttack.values().length - 1)];//random attack

        switch (phase) {
            case FREEZE: {
                npc.addEvent(e -> {
                    e.delay(1);
                    npc.forceText("Stop running from me!");
                    npc.animate(1474);
                    e.delay(1);
                    npc.publicSound(171);
                    int delay = new Projectile(368, 43, 0, 51, 56, 10, 16, 64).send(npc, target);
                    e.delay(delay * 25 / 600);
                    for (Player p : npc.localPlayers()) {
                        if (p == null || p.dead() || p.getMovement().teleporting())
                            continue;
                        if (p.getPosition().isWithinDistance(target.getPosition(), 3)) {
                            p.graphics(369);
                            p.hit(new Hit().randDamage(20).ignoreDefence().ignorePrayer());
                            p.freeze(30, p);
                        }
                    }
                });
                phase = VoteBossAttack.LOG_SWING;
                specialAttack();
                return;
            }
            case JUMP_TO_TARGET: {
                AtomicInteger totalEffected = new AtomicInteger();
                Player randomTarget = findMostDamageTarget();
                if (randomTarget == null)
                    break;
                AtomicReference<Position> toDest = new AtomicReference<>();
                npc.addEvent(e -> {
                    e.delay(1);
                    toDest.set(randomTarget.getPosition().copy());
                    npc.forceText(randomTarget.name() + "! come ere'");
                    e.delay(1);
                    npc.face(randomTarget);
                    e.delay(1);
                    npc.animate(4991);
                    randomTarget.forceText("Everyone watch out!");
                    e.delay(1);
                    if (toDest.get().inBounds(getAreaBounds()))
                        npc.getMovement().teleport(toDest.get().getX(), toDest.get().getY());
                    for (Player player : npc.localPlayers()) {
                        if (player == null || player.dead() || player.getMovement().teleporting())
                            continue;
                        if (player.getPosition().isWithinDistance(npc.getCentrePosition(), 4)) {
                            totalEffected.getAndIncrement();
                            Hit hit = new Hit(npc, AttackStyle.DAWNBRINGER, AttackType.AGGRESSIVE).fixedDamage(40).ignorePrayer().ignoreDefence();
                            player.getPacketSender().shakeCamera(0, 10);
                            player.hit(hit);
                            player.animate(531);
                            player.lock(LockType.FULL);
                            player.sendMessage("The impact of Dad's jump cause you to lose balance.");
                            e.delay(1);
                            player.getPacketSender().resetCamera();
                            e.delay(1);
                            player.unlock();
                        }
                    }
                    if (totalEffected.get() > 0) {
                        npc.setHp(npc.getHp() + (totalEffected.get() * 100));
                    }
                });
                phase = null;
                return;
            }

            case LOG_SWING: {
                npc.animate(1158);
                for (Player player : npc.localPlayers()) {
                    if (player == null)
                        continue;
                    if (player.getPosition().isWithinDistance(npc.getCentrePosition(), 3)) {
                        Hit hit = new Hit(npc, AttackStyle.DAWNBRINGER, AttackType.AGGRESSIVE).fixedDamage(25).ignorePrayer().ignoreDefence();
                        player.lock();
                        player.startEvent(e -> {
                            boolean north = player.getPosition().getY() > npc.getCentrePosition().getY();
                            player.getMovement().force(0, north ? 4 : -4, 10, 50, north ? Direction.SOUTH : Direction.NORTH);
                            player.hit(hit);
                            player.forceText("Ow!");
                            player.graphics(245, 5, 124);
                            player.animate(1157);
                            player.stun(2, true);
                            e.delay(1);
                            player.unlock();
                        });
                    }
                }
                phase = null;
            }
        }
    }

    @Override
    public void startDeath(Hit killHit) {
        World.startEvent(e -> {
            List<Player> local = npc.localPlayers();
            local.forEach(l -> l.getPacketSender().shakeCamera(0, 10));
            troll_spectators.stream().filter(t -> t != null).forEach(t -> t.remove());
            e.delay(2);
            int maxSize = npc.getCombat().killers.size();
            local.forEach(l -> l.dialogue(new PlayerDialogue("What was that...?")));
            e.delay(2);
            Position start = npc.getPosition();
            local.forEach(l -> l.getPacketSender().resetCamera());
            List<Position> destinations = Lists.newArrayList();
            int delay = -1;
            for (int i = 0; i < maxSize; i++) {
                Position dest = getAreaBounds().randomPosition();
                destinations.add(dest);
                delay = new Projectile(5034, 228, 0, 0, 30, 0, 20, 0).send(start, dest);
            }
            e.delay(delay * 25 / 600);
            destinations.forEach(d -> {
                new GroundItem(26920, 1).position(d).spawn();
            });
            Player killer = npc.getCombat().getKiller().player;
            if (killer != null) {
                Pet pet = Pet.DAD;
                if (Random.rollDie(pet.dropAverage))
                    pet.unlock(killer);
            }
            Broadcast.WORLD.sendNews("The Vote Boss has been slain by " + killer.player.getUsername() + "!");
            VoteManager.voteBoss = null;
        });
        super.startDeath(killHit);
    }

    @Override
    public void tick() {
        /**
         * Not inbounds..
         */
        for (Player l : npc.localPlayers()) {
            if (l == null || l.dead())
                continue;
            if (!l.getPosition().inBounds(getAreaBounds()) && l.lastRegion != null && l.lastRegion.id == 11576) {
                l.hit(new Hit().fixedDamage(Misc.random(5, 10)).ignoreDefence().ignorePrayer());
                l.sendMessage("Quick, get back into the fight area before you die!");
            }
        }

        if (!npc.getPosition().inBounds(getAreaBounds())) {
            npc.addEvent(e -> {
                npc.forceText("Arghhhh! Enough!");
                e.delay(2);
                npc.getMovement().teleport(2911, 3611, 0);
                int localCount = npc.localPlayers().size();
                npc.incrementHp(localCount * 100);
            });
        }
    }

    public static Bounds getAreaBounds() {
        return new Bounds(2904, 3603, 2921, 3619, 0);
    }

    public Player findMostDamageTarget() {
        Killer mostDamage = npc.getCombat().getKiller();
        if (mostDamage != null && !mostDamage.player.dead() && !mostDamage.player.getMovement().teleporting()) {
            return mostDamage.player;
        }
        return npc.localPlayers().get(Misc.random(0, npc.localPlayers().size() - 1));
    }
}
