package io.ruin.model.activities.bosses.gauntlet;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import io.ruin.model.entity.npc.ScriptSettings;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Pair;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Misc;
import io.ruin.utility.TickDelay;

import static io.ruin.model.activities.bosses.gauntlet.HunllefAttack.MAGIC;
import static io.ruin.model.activities.bosses.gauntlet.HunllefAttack.RANGED;

@ScriptSettings(npcIds = {9021, 9022, 9023, 9024})
public class Hunllef extends NPCCombat {

    //8416 = walk
    //1713 = gfx for magic attack
    //8418 = beems spawn in corners of the room
    //Your prayers have been disabled! (random.. with msg in light red)
    //8420 = stomp
    //1709 = when magic hits
    //1707 = when range hiits?
    //1711 = range projectile
    //1713 = magic projectile
    //1714 = disable prayer
    //1715 = magic dmg > 0
    //1717 = the barrier things that chase u brev
    //1732 = death?
    //on death turns into 9024 and does emote 8422
    private static final Projectile MAGIC_PROJ = new Projectile(1707, 37, 25, 0, 80, 0, 15, 0);
    private static final Projectile MAGIC_DISABLE_PRAYER = new Projectile(1713, 35, 35, 0, 80, 0, 5, 0);
    private static final int MAGIC_SPLASH = 1703;
    private static final Projectile RANGE_PROJ = new Projectile(1711, 37, 25, 0, 80, 0, 15, 0);

    private List<Position> TORNADO_SPAWNS = Arrays.asList(
            new Position(1917, 5687, 1),//1918, 5676, 1
            new Position(1922, 5687, 1),
            new Position(1917, 5693, 1),
            new Position(1922, 5693, 1)
    );

    private static final int ATTACK = 8419, TORNADOS = 8418;

    private TickDelay floorDelay = new TickDelay();

    private final List<NPC> tornados = new CopyOnWriteArrayList<>();
    public HunllefAttack style = RANGED;
    private int rangedAttackCount;
    private int magicAttackCount;
    private int playerAttacks;

    private void postDefend(Hit hit) {
        playerAttacks++;
        if (playerAttacks == 6) {
            npc.transform((hit.attackStyle.isMelee()) ? 9021 : (hit.attackStyle.isMagic() ? 9023 : 9022));
            playerAttacks = 0;
        }
    }

    private boolean protectPrayersActive(Entity target) {
        return target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ||
                target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE) ||
                target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES);
    }

    /**
     * Handles basic attacks.
     *
     * @param npc
     * @param target
     * @return
     */
    private int basic_attack() {
        switch (style) {
            case RANGED:
                rangedAttack();
                break;
            case MAGIC:
                magicAttack();
                break;
        }
        return 4;
    }

    public void floorAttack() {
        Pair[] layout = npc.getHp() >= 200 ? groundAttackLayoutHighHP[new java.util.Random().nextInt(groundAttackLayoutHighHP.length)] : groundAttackLayoutLowHP[new java.util.Random().nextInt(groundAttackLayoutLowHP.length)];
        for (Pair coords : layout) {
            if (npc.getHp() >= 200 && !npc.getCombat().isDead()) {
                npc.addEvent(event -> {
                    event.delay(1);
                    GameObject object = GameObject.spawn(36150, npc.getSpawnPosition().translated(coords.getX() + 3, coords.getY()), 22, 0);
                    event.delay(7);
                    object.setId(36047);
                    World.startEvent(e -> {
                        int counter = 0;
                        while (counter <= 8) {
                            e.delay(1);
                            if (target == null || target.player == null || target.dead())
                                break;
                            if (!npc.dead() && target.player.getPosition().equals(object.getPosition())) {
                                target.player.hit(new Hit(npc).randDamage(5, 10));
                            }
                            counter++;
                        }
                    });
                    event.delay(9);
                    object.setId(36049);
                });
            } else {
                npc.addEvent(event -> {
                    event.delay(1);
                    GameObject object = GameObject.spawn(36150, npc.getSpawnPosition().translated(coords.getX() + 3, coords.getY()), 22, 0);
                    event.delay(7);
                    object.setId(36047);
                    World.startEvent(e -> {
                        int counter = 0;
                        while (counter <= 6) {
                            if (target == null || target.player == null || target.dead())
                                break;
                            if (Objects.equals(target.player.getPosition(), object.getPosition())) {
                                target.player.hit(new Hit(npc).randDamage(5, 10));
                            }
                            e.delay(1);
                            counter++;
                        }
                    });
                    event.delay(7);
                    object.setId(36049);
                }); //7s
            }
        }
    }

    /**
     * Handles tornado attack
     *
     * @param npc
     */
    private void spawnTornados() {
        int maxHp = npc.getMaxHp();
        int allowedSpawns = npc.getHp() > (maxHp * .75) ? 1 : npc.getHpPercentage() > (maxHp * .50) ? 2 : 3;

        int currentSize = tornados.size();
        if (currentSize >= allowedSpawns) {
            System.err.println("blocked spawn.. size="+currentSize+" allowed="+allowedSpawns);
            return;
        }
        npc.addEvent(e -> {
            npc.animate(TORNADOS);
            e.delay(2);
            sendTornados(allowedSpawns);
        });
    }

    private void sendTornados(int allowedSpawns) {
        for (int index = 0; index < allowedSpawns; index++) {
            Player t = (Player) target;
            Position tSpawn = TORNADO_SPAWNS.get(index);
            Position instancedSpawn = t.currentInstance.convertPosition(tSpawn);
            NPC tornado = new NPC(9025).spawn(instancedSpawn);
            tornado.aliveTicks = 20;
            tornado.getDef().ignoreOccupiedTiles = true;
            tornados.add(tornado);
            tornado.startEvent(tor -> {
                while (!tornado.isRemoved() || tornado != null) {
                    tor.delay(1);
                    if (target == null)
                        break;
                    boolean sameTile = tornado.getPosition().isWithinDistance(target.getPosition(), 1);
                    if (sameTile)
                        target.hit(new Hit().fixedDamage(maxTornadoDamage(target)));
                    else
                        DumbRoute.route(tornado, target.getLastPosition().getX(), target.getLastPosition().getY());
                }
            });
        }
    }

    private int maxTornadoDamage(Entity target) {
        if (target.isNPC())
            return 0;
        Player player = (Player) target;
        if (fullAmour(player, Arrays.asList(23887, 23890, 23893)))//t2
            return Misc.random(7, 13);
        if (fullAmour(player, Arrays.asList(23888, 23891, 23894)))//t3
            return Misc.random(5, 10);
        return Misc.random(10, 20);
    }

    private boolean fullAmour(Player player, List<Integer> armourSet) {
        int count = 0;
        for (Item i : player.getEquipment().getItems()) {
            if (i == null)
                continue;
            if (armourSet.stream().anyMatch(it -> it.intValue() == i.getId()))
                count++;
        }
        return count >= 3;
    }

    @Override
    public void tick() {
        if (tornados == null)
            return;
        for (NPC t : tornados) {
            if (t == null)
                continue;
            if (--t.aliveTicks > 0) {
                if (t.aliveTicks == 1) {
                    t.remove();
                }
            }
        }
        tornados.removeIf(npc1 -> npc1.aliveTicks <= 1 && npc1.isRemoved());
    }

    private void preDefend(Hit hit) {
        if (hit.attackStyle == null)
            return;
        if (hit.attackStyle.isMelee() && npc.getId() == 9021)
            hit.block();
        else if (hit.attackStyle.isRanged() && npc.getId() == 9022)
            hit.block();
        else if (hit.attackStyle.isMagic() && npc.getId() == 9023)
            hit.block();
    }

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend).postDefend(this::postDefend);
    }

    @Override
    public void follow() {
        follow(8);
    }

    private void magicAttack() {
        if (magicAttackCount == 4)
            return;
        if (npc.getCentrePosition().isWithinDistance(target.getPosition(), 2)) {
            stomp();
            return;
        }
        if (Math.random() <= .05 && target.isPlayer() && protectPrayersActive(target)) {
            int delay = MAGIC_DISABLE_PRAYER.send(npc, target);
            final int ticks = (delay * 15) / 600;
            npc.addEvent(e -> {
                e.delay(ticks);
                if (target != null) {
                    target.graphics(1716);
                    target.player.sendMessage(Color.RED.wrap("Your prayers have been disabled!"));
                    target.player.getPrayer().deactivateAll();
                    checkMageAttackCount();
                }
            });
            return;
        }
        Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage);
        npc.animate(ATTACK);
        int delay = MAGIC_PROJ.send(npc, target);
        final int ticks = (delay * 15) / 600;
        npc.addEvent(e -> {
            e.delay(ticks);
            if (target != null) {
                target.hit(hit);
                checkMageAttackCount();
                target.graphics(MAGIC_SPLASH);
            }
        });
    }

    private void checkMageAttackCount() {
        magicAttackCount++;
        if (magicAttackCount == 4) {
            npc.animate(8755);
            magicAttackCount = 0;
            this.attackDelayTicks = 5;
            style = RANGED;
        }
    }

    private void rangedAttack() {
        if (rangedAttackCount == 4)
            return;
        if (npc.getCentrePosition().isWithinDistance(target.getPosition(), 2)) {
            stomp();
            return;
        }
        Hit hit = new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage);
        npc.animate(ATTACK);
        int delay = RANGE_PROJ.send(npc, target);
        final int ticks = (delay * 15) / 600;
        npc.addEvent(e -> {
            e.delay(ticks);
            if (target != null) {
                target.hit(hit);
                rangedAttackCount++;
                if (rangedAttackCount == 4) {
                    rangedAttackCount = 0;
                    npc.animate(8754);
                    this.attackDelayTicks = 5;
                    style = MAGIC;
                }
            }
        });
    }

    private void stomp() {
        npc.addEvent(e -> {
            npc.animate(8420);
            e.delay(1);
            Hit hit = new Hit(npc, AttackStyle.CRUSH).randDamage(30, 50).ignorePrayer();
            target.player.sendMessage("You're trampled beneath the Hunllef.");
            target.hit(hit);
        });
    }

    @Override
    public boolean attack() {
        if (npc.getCombat().isDead()) {
            return false;
        }

        if (Math.random() < .2) {
            spawnTornados();
        } else if (floorDelay.finished()) {
            floorAttack();
            if (npc.getHp() >= 200) {
                floorDelay.delay(16);
            } else {
                floorDelay.delay(14);
            }
        }
        basic_attack();
        return true;
    }

    public void setTarget(Entity target) {
        this.target = target;
        npc.getRouteFinder().routeEntity(target);
    }

    private final Pair[][] groundAttackLayoutHighHP = {
            {new Pair(4, 8), new Pair(5, 8), new Pair(6, 8),
                    new Pair(4, 7), new Pair(5, 7), new Pair(6, 7),
                    new Pair(4, 6), new Pair(5, 6), new Pair(6, 6),

                    new Pair(4, 5), new Pair(5, 5), new Pair(6, 5),
                    new Pair(4, 4), new Pair(5, 4), new Pair(6, 4),
                    new Pair(4, 3), new Pair(5, 3), new Pair(6, 3),
                    new Pair(4, 2), new Pair(5, 2), new Pair(6, 2),
                    new Pair(4, 1), new Pair(5, 1), new Pair(6, 1),
                    new Pair(4, 0), new Pair(5, 0), new Pair(6, 0),
                    new Pair(4, -1), new Pair(5, -1), new Pair(6, -1),
                    new Pair(4, -2), new Pair(5, -2), new Pair(6, -2),
                    new Pair(4, -3), new Pair(5, -3), new Pair(6, -3),

                    new Pair(-3, 8), new Pair(-4, 8), new Pair(-5, 8),
                    new Pair(-3, 7), new Pair(-4, 7), new Pair(-5, 7),
                    new Pair(-3, 6), new Pair(-4, 6), new Pair(-5, 6),
                    new Pair(-3, 5), new Pair(-4, 5), new Pair(-5, 5),
                    new Pair(-3, 4), new Pair(-4, 4), new Pair(-5, 4),
                    new Pair(-3, 3), new Pair(-4, 3), new Pair(-5, 3),
                    new Pair(-3, 2), new Pair(-4, 2), new Pair(-5, 2),
                    new Pair(-3, 1), new Pair(-4, 1), new Pair(-5, 1),
                    new Pair(-3, 0), new Pair(-4, 0), new Pair(-5, 0),
                    new Pair(-3, -1), new Pair(-4, -1), new Pair(-5, -1),
                    new Pair(-3, -2), new Pair(-4, -2), new Pair(-5, -2),
                    new Pair(-3, -3), new Pair(-4, -3), new Pair(-5, -3),

            },
            {new Pair(-3, 8), new Pair(-4, 8), new Pair(-5, 8),
                    new Pair(-3, 7), new Pair(-4, 7), new Pair(-5, 7),
                    new Pair(-3, 6), new Pair(-4, 6), new Pair(-5, 6),
                    new Pair(-3, 5), new Pair(-4, 5), new Pair(-5, 5),
                    new Pair(-3, 4), new Pair(-4, 4), new Pair(-5, 4),
                    new Pair(-3, 3), new Pair(-4, 3), new Pair(-5, 3),
                    new Pair(-3, 2), new Pair(-4, 2), new Pair(-5, 2),
                    new Pair(-3, 1), new Pair(-4, 1), new Pair(-5, 1),
                    new Pair(-3, 0), new Pair(-4, 0), new Pair(-5, 0),
                    new Pair(-3, -1), new Pair(-4, -1), new Pair(-5, -1),
                    new Pair(-3, -2), new Pair(-4, -2), new Pair(-5, -2),
                    new Pair(-3, -3), new Pair(-4, -3), new Pair(-5, -3),

                    new Pair(-2, 8), new Pair(-2, 7), new Pair(-2, 6),
                    new Pair(-2, 5), new Pair(-2, 4), new Pair(-2, 3),
                    new Pair(-2, 2), new Pair(-2, 1), new Pair(-2, 0),
                    new Pair(-2, -1), new Pair(-2, -2), new Pair(-2, -3)
            },
            {new Pair(4, 8), new Pair(5, 8), new Pair(6, 8),
                    new Pair(4, 7), new Pair(5, 7), new Pair(6, 7),
                    new Pair(4, 6), new Pair(5, 6), new Pair(6, 6),
                    new Pair(4, 5), new Pair(5, 5), new Pair(6, 5),
                    new Pair(4, 4), new Pair(5, 4), new Pair(6, 4),
                    new Pair(4, 3), new Pair(5, 3), new Pair(6, 3),
                    new Pair(4, 2), new Pair(5, 2), new Pair(6, 2),
                    new Pair(4, 1), new Pair(5, 1), new Pair(6, 1),
                    new Pair(4, 0), new Pair(5, 0), new Pair(6, 0),
                    new Pair(4, -1), new Pair(5, -1), new Pair(6, -1),
                    new Pair(4, -2), new Pair(5, -2), new Pair(6, -2),
                    new Pair(4, -3), new Pair(5, -3), new Pair(6, -3),
                    new Pair(3, 8), new Pair(3, 7), new Pair(3, 6),
                    new Pair(3, 5), new Pair(3, 4), new Pair(3, 3),
                    new Pair(3, 2), new Pair(3, 1), new Pair(3, 0),
                    new Pair(3, -1), new Pair(3, -2), new Pair(3, -3)
            },
            {new Pair(6, -1), new Pair(6, -2), new Pair(6, -3),
                    new Pair(5, -1), new Pair(5, -2), new Pair(5, -3),
                    new Pair(4, -1), new Pair(4, -2), new Pair(4, -3),
                    new Pair(3, -1), new Pair(3, -2), new Pair(3, -3),
                    new Pair(2, -1), new Pair(2, -2), new Pair(2, -3),
                    new Pair(1, -1), new Pair(1, -2), new Pair(1, -3),
                    new Pair(0, -1), new Pair(0, -2), new Pair(0, -3),
                    new Pair(-1, -1), new Pair(-1, -2), new Pair(-1, -3),
                    new Pair(-2, -1), new Pair(-2, -2), new Pair(-2, -3),
                    new Pair(-3, -1), new Pair(-3, -2), new Pair(-3, -3),
                    new Pair(-4, -1), new Pair(-4, -2), new Pair(-4, -3),
                    new Pair(-5, -1), new Pair(-5, -2), new Pair(-5, -3),

                    new Pair(6, 0), new Pair(5, 0), new Pair(4, 0),
                    new Pair(3, 0), new Pair(2, 0), new Pair(1, 0),
                    new Pair(0, 0), new Pair(-1, 0), new Pair(-2, 0),
                    new Pair(-3, 0), new Pair(-4, 0), new Pair(-5, 0)
            },
            {new Pair(-5, 6), new Pair(-5, 7), new Pair(-5, 8),
                    new Pair(-4, 6), new Pair(-4, 7), new Pair(-4, 8),
                    new Pair(-3, 6), new Pair(-3, 7), new Pair(-3, 8),
                    new Pair(-2, 6), new Pair(-2, 7), new Pair(-2, 8),
                    new Pair(-1, 6), new Pair(-1, 7), new Pair(-1, 8),
                    new Pair(0, 6), new Pair(0, 7), new Pair(0, 8),
                    new Pair(1, 6), new Pair(1, 7), new Pair(1, 8),
                    new Pair(2, 6), new Pair(2, 7), new Pair(2, 8),
                    new Pair(3, 6), new Pair(3, 7), new Pair(3, 8),
                    new Pair(4, 6), new Pair(4, 7), new Pair(4, 8),
                    new Pair(5, 6), new Pair(5, 7), new Pair(5, 8),
                    new Pair(6, 6), new Pair(6, 7), new Pair(6, 8),

                    new Pair(-5, 5), new Pair(-4, 5), new Pair(-3, 5),
                    new Pair(-2, 5), new Pair(-1, 5), new Pair(0, 5),
                    new Pair(1, 5), new Pair(2, 5), new Pair(3, 5),
                    new Pair(4, 5), new Pair(5, 5), new Pair(6, 5)
            },
            {new Pair(6, -1), new Pair(6, -2), new Pair(6, -3),
                    new Pair(5, -1), new Pair(5, -2), new Pair(5, -3),
                    new Pair(4, -1), new Pair(4, -2), new Pair(4, -3),
                    new Pair(3, -1), new Pair(3, -2), new Pair(3, -3),
                    new Pair(2, -1), new Pair(2, -2), new Pair(2, -3),
                    new Pair(1, -1), new Pair(1, -2), new Pair(1, -3),
                    new Pair(0, -1), new Pair(0, -2), new Pair(0, -3),
                    new Pair(-1, -1), new Pair(-1, -2), new Pair(-1, -3),
                    new Pair(-2, -1), new Pair(-2, -2), new Pair(-2, -3),
                    new Pair(-3, -1), new Pair(-3, -2), new Pair(-3, -3),
                    new Pair(-4, -1), new Pair(-4, -2), new Pair(-4, -3),
                    new Pair(-5, -1), new Pair(-5, -2), new Pair(-5, -3),
                    new Pair(-5, 6), new Pair(-5, 7), new Pair(-5, 8),
                    new Pair(-4, 6), new Pair(-4, 7), new Pair(-4, 8),
                    new Pair(-3, 6), new Pair(-3, 7), new Pair(-3, 8),
                    new Pair(-2, 6), new Pair(-2, 7), new Pair(-2, 8),
                    new Pair(-1, 6), new Pair(-1, 7), new Pair(-1, 8),
                    new Pair(0, 6), new Pair(0, 7), new Pair(0, 8),
                    new Pair(1, 6), new Pair(1, 7), new Pair(1, 8),
                    new Pair(2, 6), new Pair(2, 7), new Pair(2, 8),
                    new Pair(3, 6), new Pair(3, 7), new Pair(3, 8),
                    new Pair(4, 6), new Pair(4, 7), new Pair(4, 8),
                    new Pair(5, 6), new Pair(5, 7), new Pair(5, 8),
                    new Pair(6, 6), new Pair(6, 7), new Pair(6, 8),
            },
            {new Pair(-1, 1), new Pair(-1, 2), new Pair(-1, 3),
                    new Pair(-1, 4), new Pair(0, 4), new Pair(1, 4),
                    new Pair(2, 4), new Pair(2, 3), new Pair(2, 2),
                    new Pair(2, 1), new Pair(1, 1), new Pair(0, 1),
                    new Pair(0, 2), new Pair(0, 3), new Pair(1, 3),
                    new Pair(1, 2),

                    new Pair(2, 0), new Pair(2, -1), new Pair(-3, -1),
                    new Pair(1, 0), new Pair(1, -1), new Pair(0, 0),
                    new Pair(0, -1), new Pair(-1, 0),

                    new Pair(-1, -1),
                    new Pair(-2, 0), new Pair(-2, -1), new Pair(-3, 0),

                    new Pair(-3, 1), new Pair(-2, 1), new Pair(-2, 2),
                    new Pair(-3, 2), new Pair(-3, 3), new Pair(-2, 3),
                    new Pair(-3, 4),
                    new Pair(-2, 4),

            },
            {new Pair(-1, 1), new Pair(-1, 2), new Pair(-1, 3),
                    new Pair(-1, 4), new Pair(0, 4), new Pair(1, 4),
                    new Pair(2, 4), new Pair(2, 3), new Pair(2, 2),
                    new Pair(2, 1), new Pair(1, 1), new Pair(0, 1),
                    new Pair(0, 2), new Pair(0, 3), new Pair(1, 3),
                    new Pair(1, 2),

                    new Pair(2, 0), new Pair(2, -1), new Pair(-1, -1),
                    new Pair(1, 0), new Pair(1, -1), new Pair(0, 0),
                    new Pair(0, -1), new Pair(-1, 0),

                    new Pair(3, 0), new Pair(3, -1), new Pair(4, -1),
                    new Pair(4, 0), new Pair(3, 1), new Pair(4, 1),
                    new Pair(3, 2),
                    new Pair(4, 2),
                    new Pair(3, 3), new Pair(4, 3), new Pair(3, 4),
                    new Pair(4, 4),
            },
            {new Pair(-1, 1), new Pair(-1, 2), new Pair(-1, 3),
                    new Pair(-1, 4), new Pair(0, 4), new Pair(1, 4),
                    new Pair(2, 4), new Pair(2, 3), new Pair(2, 2),
                    new Pair(2, 1), new Pair(1, 1), new Pair(0, 1),
                    new Pair(0, 2), new Pair(0, 3), new Pair(1, 3),
                    new Pair(1, 2),

                    new Pair(-1, 6), new Pair(-1, 5), new Pair(0, 6),
                    new Pair(0, 5), new Pair(1, 6), new Pair(1, 5),
                    new Pair(2, 5), new Pair(2, 6),
                    new Pair(3, 6), new Pair(4, 6),
                    new Pair(3, 5),
                    new Pair(4, 5),

                    new Pair(3, 1), new Pair(4, 1),
                    new Pair(3, 2),
                    new Pair(4, 2),
                    new Pair(3, 3), new Pair(4, 3), new Pair(3, 4),
                    new Pair(4, 4),
            },
            {new Pair(-1, 1), new Pair(-1, 2), new Pair(-1, 3),
                    new Pair(-1, 4), new Pair(0, 4), new Pair(1, 4),
                    new Pair(2, 4), new Pair(2, 3), new Pair(2, 2),
                    new Pair(2, 1), new Pair(1, 1), new Pair(0, 1),
                    new Pair(0, 2), new Pair(0, 3), new Pair(1, 3),
                    new Pair(1, 2),

                    new Pair(-3, 6), new Pair(-2, 6), new Pair(-1, 6),
                    new Pair(0, 6), new Pair(1, 6), new Pair(2, 6),
                    new Pair(-3, 5), new Pair(-2, 5), new Pair(-1, 5),
                    new Pair(0, 5), new Pair(1, 5), new Pair(2, 5),

                    new Pair(-3, 2), new Pair(-2, 2),
                    new Pair(-3, 1),
                    new Pair(-2, 1),

                    new Pair(-3, 3), new Pair(-2, 3),
                    new Pair(-3, 4),
                    new Pair(-2, 4),

            },
            {new Pair(-5, 8), new Pair(-4, 8), new Pair(-3, 8),
                    new Pair(-2, 8), new Pair(-1, 8), new Pair(0, 8),

                    new Pair(-5, 7), new Pair(-4, 7), new Pair(-3, 7),
                    new Pair(-2, 7), new Pair(-1, 7), new Pair(0, 7),
                    new Pair(-5, 6), new Pair(-4, 6), new Pair(-3, 6),
                    new Pair(-2, 6), new Pair(-1, 6), new Pair(0, 6),

                    new Pair(-5, 5), new Pair(-4, 5), new Pair(-3, 5),
                    new Pair(-2, 5), new Pair(-1, 5), new Pair(0, 5),
                    new Pair(-5, 4), new Pair(-4, 4), new Pair(-3, 4),
                    new Pair(-2, 4), new Pair(-1, 4), new Pair(0, 4),

                    new Pair(-5, 3), new Pair(-4, 3), new Pair(-3, 3),
                    new Pair(-2, 3), new Pair(-1, 3), new Pair(0, 3),

            },
            {new Pair(1, 8), new Pair(2, 8), new Pair(3, 8),
                    new Pair(4, 8), new Pair(5, 8), new Pair(6, 8),

                    new Pair(1, 7), new Pair(2, 7), new Pair(3, 7),
                    new Pair(4, 7), new Pair(5, 7), new Pair(6, 7),
                    new Pair(1, 6), new Pair(2, 6), new Pair(3, 6),
                    new Pair(4, 6), new Pair(5, 6), new Pair(6, 6),

                    new Pair(1, 5), new Pair(2, 5), new Pair(3, 5),
                    new Pair(4, 5), new Pair(5, 5), new Pair(6, 5),
                    new Pair(1, 4), new Pair(2, 4), new Pair(3, 4),
                    new Pair(4, 4), new Pair(5, 4), new Pair(6, 4),

                    new Pair(1, 3), new Pair(2, 3), new Pair(3, 3),
                    new Pair(4, 3), new Pair(5, 3), new Pair(6, 3),
            },
            {new Pair(-5, 2), new Pair(-4, 2), new Pair(-3, 2),
                    new Pair(-2, 2), new Pair(-1, 2), new Pair(0, 2),

                    new Pair(-5, 1), new Pair(-4, 1), new Pair(-3, 1),
                    new Pair(-2, 1), new Pair(-1, 1), new Pair(0, 1),
                    new Pair(-5, 0), new Pair(-4, 0), new Pair(-3, 0),
                    new Pair(-2, 0), new Pair(-1, 0), new Pair(0, 0),

                    new Pair(-5, -1), new Pair(-4, -1), new Pair(-3, -1),
                    new Pair(-2, -1), new Pair(-1, -1), new Pair(0, -1),
                    new Pair(-5, -2), new Pair(-4, -2), new Pair(-3, -2),
                    new Pair(-2, -2), new Pair(-1, -2), new Pair(0, -2),

                    new Pair(-5, -3), new Pair(-4, -3), new Pair(-3, -3),
                    new Pair(-2, -3), new Pair(-1, -3), new Pair(0, -3),
            },
            {new Pair(1, 2), new Pair(2, 2), new Pair(3, 2),
                    new Pair(4, 2), new Pair(5, 2), new Pair(6, 2),

                    new Pair(1, 1), new Pair(2, 1), new Pair(3, 1),
                    new Pair(4, 1), new Pair(5, 1), new Pair(6, 1),
                    new Pair(1, 0), new Pair(2, 0), new Pair(3, 0),
                    new Pair(4, 0), new Pair(5, 0), new Pair(6, 0),

                    new Pair(1, -1), new Pair(2, -1), new Pair(3, -1),
                    new Pair(4, -1), new Pair(5, -1), new Pair(6, -1),
                    new Pair(1, -2), new Pair(2, -2), new Pair(3, -2),
                    new Pair(4, -2), new Pair(5, -2), new Pair(6, -2),

                    new Pair(1, -3), new Pair(2, -3), new Pair(3, -3),
                    new Pair(4, -3), new Pair(5, -3), new Pair(6, -3),
            },
    };
    private final Pair[][] groundAttackLayoutLowHP = {
            {new Pair(-5, 2), new Pair(-4, 2), new Pair(-3, 2),
                    new Pair(-2, 2), new Pair(-1, 2), new Pair(0, 2),

                    new Pair(-5, 1), new Pair(-4, 1), new Pair(-3, 1),
                    new Pair(-2, 1), new Pair(-1, 1), new Pair(0, 1),
                    new Pair(-5, 0), new Pair(-4, 0), new Pair(-3, 0),
                    new Pair(-2, 0), new Pair(-1, 0), new Pair(0, 0),

                    new Pair(-5, -1), new Pair(-4, -1), new Pair(-3, -1),
                    new Pair(-2, -1), new Pair(-1, -1), new Pair(0, -1),
                    new Pair(-5, -2), new Pair(-4, -2), new Pair(-3, -2),
                    new Pair(-2, -2), new Pair(-1, -2), new Pair(0, -2),

                    new Pair(-5, -3), new Pair(-4, -3), new Pair(-3, -3),
                    new Pair(-2, -3), new Pair(-1, -3), new Pair(0, -3),
            },
            {

                    new Pair(-5, 0), new Pair(-4, 0), new Pair(-3, 0),
                    new Pair(-2, 0),
                    new Pair(-5, -1), new Pair(-4, -1), new Pair(-3, -1),
                    new Pair(-2, -1),
                    new Pair(-5, -2), new Pair(-4, -2), new Pair(-3, -2),
                    new Pair(-2, -2),
                    new Pair(-5, -3), new Pair(-4, -3), new Pair(-3, -3),
                    new Pair(-2, -3),

                    new Pair(-5, 8), new Pair(-4, 8), new Pair(-3, 8),
                    new Pair(-2, 8),
                    new Pair(-5, 7), new Pair(-4, 7), new Pair(-3, 7),
                    new Pair(-2, 7),
                    new Pair(-5, 6), new Pair(-4, 6), new Pair(-3, 6),
                    new Pair(-2, 6),
                    new Pair(-5, 5), new Pair(-4, 5), new Pair(-3, 5),
                    new Pair(-2, 5),

                    new Pair(3, 8), new Pair(4, 8), new Pair(5, 8),
                    new Pair(6, 8),
                    new Pair(3, 7), new Pair(4, 7), new Pair(5, 7),
                    new Pair(6, 7),
                    new Pair(3, 6), new Pair(4, 6), new Pair(5, 6),
                    new Pair(6, 6),
                    new Pair(3, 5), new Pair(4, 5), new Pair(5, 5),
                    new Pair(6, 5),

                    new Pair(3, 0), new Pair(4, 0), new Pair(5, 0),
                    new Pair(6, 0),
                    new Pair(3, -1), new Pair(4, -1), new Pair(5, -1),
                    new Pair(6, -1),
                    new Pair(3, -2), new Pair(4, -2), new Pair(5, -2),
                    new Pair(6, -2),
                    new Pair(3, -3), new Pair(4, -3), new Pair(5, -3),
                    new Pair(6, -3),
            },
            {
                    new Pair(-5, -1), new Pair(-4, -1),
                    new Pair(-3, -1),
                    new Pair(-5, -2), new Pair(-4, -2),
                    new Pair(-3, -2),
                    new Pair(-5, -3), new Pair(-4, -3),
                    new Pair(-3, -3),

                    new Pair(-5, 8), new Pair(-4, 8),
                    new Pair(-3, 8),
                    new Pair(-5, 7), new Pair(-4, 7),
                    new Pair(-3, 7),
                    new Pair(-5, 6), new Pair(-4, 6),
                    new Pair(-3, 6),


                    new Pair(4, 8), new Pair(5, 8),
                    new Pair(6, 8),
                    new Pair(4, 7), new Pair(5, 7),
                    new Pair(6, 7),
                    new Pair(4, 6), new Pair(5, 6),
                    new Pair(6, 6),

                    new Pair(4, -1), new Pair(5, -1),
                    new Pair(6, -1),
                    new Pair(4, -2), new Pair(5, -2),
                    new Pair(6, -2),
                    new Pair(4, -3), new Pair(5, -3),
                    new Pair(6, -3),

                    new Pair(-1, 4), new Pair(0, 4),
                    new Pair(1, 4),
                    new Pair(2, 4), new Pair(-1, 3),
                    new Pair(0, 3),
                    new Pair(1, 3), new Pair(2, 3),
                    new Pair(-1, 2),
                    new Pair(0, 2), new Pair(1, 2),
                    new Pair(2, 2),
                    new Pair(-1, 1),
                    new Pair(0, 1), new Pair(1, 1),
                    new Pair(2, 1),
            },
            {
                    new Pair(-4, -1), new Pair(-3, -1),
                    new Pair(-2, -1), new Pair(-1, -1),
                    new Pair(-4, 0), new Pair(-3, 0),
                    new Pair(-2, 0), new Pair(-1, 0),
                    new Pair(-4, 1), new Pair(-3, 1),
                    new Pair(-2, 1), new Pair(-1, 1),
                    new Pair(-4, -2), new Pair(-3, -2),
                    new Pair(-2, -2), new Pair(-1, -2),

                    new Pair(-4, 7), new Pair(-3, 7),
                    new Pair(-2, 7), new Pair(-1, 7),
                    new Pair(-4, 6), new Pair(-3, 6),
                    new Pair(-2, 6), new Pair(-1, 6),
                    new Pair(-4, 5), new Pair(-3, 5),
                    new Pair(-2, 5), new Pair(-1, 5),
                    new Pair(-4, 4), new Pair(-3, 4),
                    new Pair(-2, 4), new Pair(-1, 4),

                    new Pair(2, 7), new Pair(3, 7),
                    new Pair(4, 7), new Pair(5, 7),
                    new Pair(2, 6), new Pair(3, 6),
                    new Pair(4, 6), new Pair(5, 6),
                    new Pair(2, 5), new Pair(3, 5),
                    new Pair(4, 5), new Pair(5, 5),
                    new Pair(2, 4), new Pair(3, 4),
                    new Pair(4, 4), new Pair(5, 4),

                    new Pair(2, 1), new Pair(3, 1),
                    new Pair(4, 1), new Pair(5, 1),
                    new Pair(2, 0), new Pair(3, 0),
                    new Pair(4, 0), new Pair(5, 0),
                    new Pair(2, -1), new Pair(3, -1),
                    new Pair(4, -1), new Pair(5, -1),
                    new Pair(2, -2), new Pair(3, -2),
                    new Pair(4, -2), new Pair(5, -2),

            },
            {
                    new Pair(-5, 8), new Pair(-5, 7),
                    new Pair(-5, 6), new Pair(-5, 5),
                    new Pair(-5, 4), new Pair(-5, 3),
                    new Pair(-5, 2), new Pair(-5, 1),
                    new Pair(-5, 0), new Pair(-5, -1),
                    new Pair(-5, -2), new Pair(-5, -3),
                    new Pair(-4, 8), new Pair(-4, 7),
                    new Pair(-4, 6), new Pair(-4, 5),
                    new Pair(-4, 4), new Pair(-4, 3),
                    new Pair(-4, 2), new Pair(-4, 1),
                    new Pair(-4, 0), new Pair(-4, -1),
                    new Pair(-4, -2), new Pair(-4, -3),

                    new Pair(-4, 8),
                    new Pair(-3, 8), new Pair(-2, 8),
                    new Pair(-1, 8), new Pair(0, 8),
                    new Pair(1, 8), new Pair(2, 8),
                    new Pair(3, 8), new Pair(4, 8),
                    new Pair(5, 8), new Pair(6, 8),
                    new Pair(-4, 7),
                    new Pair(-3, 7), new Pair(-2, 7),
                    new Pair(-1, 7), new Pair(0, 7),
                    new Pair(1, 7), new Pair(2, 7),
                    new Pair(3, 7), new Pair(4, 7),
                    new Pair(5, 7), new Pair(6, 7),

                    new Pair(-3, -2), new Pair(-2, -2),
                    new Pair(-1, -2), new Pair(0, -2),
                    new Pair(1, -2), new Pair(2, -2),
                    new Pair(3, -2), new Pair(4, -2),
                    new Pair(5, -2), new Pair(6, -2),

                    new Pair(-3, -3), new Pair(-2, -3),
                    new Pair(-1, -3), new Pair(0, -3),
                    new Pair(1, -3), new Pair(2, -3),
                    new Pair(3, -3), new Pair(4, -3),
                    new Pair(5, -3), new Pair(6, -3),

                    new Pair(5, 6), new Pair(6, 6),
                    new Pair(5, 5), new Pair(6, 5),

                    new Pair(5, 4),
                    new Pair(6, 4), new Pair(5, 3),
                    new Pair(6, 3), new Pair(5, 2),
                    new Pair(6, 2), new Pair(5, 1),
                    new Pair(6, 1), new Pair(5, 0),
                    new Pair(6, 0), new Pair(5, -1),
                    new Pair(6, -1),
                    new Pair(-3, 7), new Pair(-2, 7),
                    new Pair(-1, 7), new Pair(0, 7),
                    new Pair(1, 7), new Pair(2, 7),
                    new Pair(3, 7), new Pair(4, 7),
                    new Pair(5, 7), new Pair(6, 7),

            },
    };

    @Override
    public void startDeath(Hit killHit) {
        super.startDeath(killHit);
        npc.addEvent(e -> {
            npc.transform(9024);
            npc.animate(8422);
            e.delay(1);
            Player player = killHit.attacker.player;
            player.getPacketSender().fadeOut();
            e.delay(2);
            player.getMovement().teleport(3030, Misc.random(6120, 6127), 1);
            player.addEvent(p -> {
                p.delay(1);
                Config.GAUNTLET_REWARD.set(player, 1);
                player.getCombat().restore();
                player.getPrayer().deactivateAll();
                player.getPacketSender().fadeIn();
                PlayerCounter.NORMAL_GAUNTLET.increment(player, 1);
                player.sendFilteredMessage("Your Gaunlet completion count is: <col=ff0000>" + PlayerCounter.NORMAL_GAUNTLET.get(player) + "</col>.");
                player.sendFilteredMessage("Your reward awaits you in the nearby chest.");
            });

        });
    }
}