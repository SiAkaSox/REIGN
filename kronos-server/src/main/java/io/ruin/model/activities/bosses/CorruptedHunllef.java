package io.ruin.model.activities.bosses;

import io.ruin.utility.Random;
import io.ruin.cache.Color;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;

import java.util.*;

import com.google.common.collect.Lists;

import io.ruin.model.entity.npc.ScriptSettings;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Pair;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.CombatType;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Misc;
import io.ruin.utility.TickDelay;
import lombok.Setter;

@ScriptSettings( npcIds = { 9021, 9022, 9023, 9024, 9035, 9036, 9037, 9038 })
public class CorruptedHunllef extends NPCCombat {

    private static final Projectile MAGIC_PROJ = new Projectile(1708, 40, 35, 0, 100, 0, 50, 0);
    private static final Projectile MAGIC_DISABLE_PRAYER = new Projectile(1714, 40, 35, 0, 100, 0, 50, 0);
    private static final int MAGIC_SPLASH = 1703;
    private static final Projectile RANGE_PROJ = new Projectile(1712, 40, 35, 0, 100, 0, 50, 0);

    private static final int ATTACK = 8419, SWITCH_STYLES = 8418;

    public static boolean hunllefDead = false;

    private TickDelay floorDelay = new TickDelay();

    //npc 9025 is tornado, 9039 for corrupted
    //8416, 8417, 8418, 8419, 8420, 8421, 8423, 8424, 8427, 8428

    @Setter private CombatType type = Misc.randomTypeOfList(Arrays.asList(CombatType.RANGE, CombatType.MAGE)); //start with random combat type.
    private final ArrayList<NPC> tornados = Lists.newArrayList();
    private int attack;
    private int firstAttack;
    private int rangedAttackCount = 0;
    private int magicAttackCount = 0;
    private int playerAttacks;
    private int stompCooldown = 3;
    private int floorCooldown = 10;
    private boolean tornadoTime;
    private boolean switchedToMagic;
    private boolean switchedToRanged;

    private void postDefend(Hit hit) {
        playerAttacks++;
        if (playerAttacks == 6) {
            npc.transform((hit.attackStyle.isMelee()) ? 9021 : (hit.attackStyle.isMagic() ? 9023 : 9022));
            playerAttacks = 0;
        }
    }

    public int attack(NPC npc, Entity target) {
        if (changeAttackStyle(npc)) {
            tornadoTime = !tornadoTime;
            return 5;
        }
        return basic_attack(npc, target);
    }

    public void stomp(NPC npc, Entity target) {
        if (target != null) {
            Position bossPos = npc.getLastPosition();
            boolean stomp = bossPos == target.getLastPosition();
            stompCooldown--;
            floorCooldown--;
            if (stomp && stompCooldown <= 0) {
                npc.animate(SWITCH_STYLES);
                Hit hit = new Hit(target, AttackStyle.MAGICAL_MELEE).randDamage(30, 50);
                target.hit(hit);
                stompCooldown = 3;
            }
//                if (floorCooldown <= 0) {
//                    doFloors(npc, target);
//                    floorCooldown = 30;
//                }
        }
    }

    private boolean protectPrayersActive(Entity target) {
        return target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ||
                target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE) ||
                target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES);
    }

    /**
     * Handles basic attacks.
     * @param npc
     * @param target
     * @return
     */
    private int basic_attack(NPC npc, Entity target) {
        npc.animate(ATTACK);
        if (attack == 0) {
            rangedAttack();
        }
        if (rangedAttackCount < 4) {
            rangedAttack();
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
                            if (target == null || target.player == null || target.dead())
                                break;
                            if (Objects.equals(object.getPosition(), target.player.getPosition())) {
                                target.player.hit(new Hit(npc).randDamage(5, 10));
                            }
                            e.delay(1);
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
                            if (target.player.getPosition().equals(object.getPosition())) {
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
     * @param npc
     */
    private void tornados(NPC npc) {
        Position bossPos = npc.getLastPosition();
        sendTrackingTornados(bossPos);
    }

    private void sendTrackingTornados(Position position) {
        int allowedSpawns = npc.getHpPercentage() > 0.75D ? 2 : npc.getHpPercentage() > 0.50D ? 3 : 4;
        target.addEvent(e -> {
            e.delay(1);
            for (int index = 0; index < allowedSpawns; index++) {
                int yOffset = 1;
                NPC tornado = new NPC(9025).spawn(npc.getAbsX() - index, npc.getAbsY() - yOffset, npc.getHeight());
                tornados.add(tornado);
            }

            ListIterator<NPC> iterator = tornados.listIterator();
            while (iterator.hasNext()) {
                NPC tornado = iterator.next();
                int x = position.unitVectorX(target.getLastPosition());
                int y = position.unitVectorY(target.getLastPosition());
                Position position1 = target.getLastPosition().copy();
                DumbRoute.route(tornado, target.getLastPosition().getX(), target.getLastPosition().getY());
                if (x == 0 && y == 0 || position1.equals(target.getPosition())) {
                    target.hit(new Hit().randDamage(20));
                    e.delay(2);
                } else {
                    position.translate(x, y, 0);
                    DumbRoute.route(tornado, target.getLastPosition().getX(), target.getLastPosition().getY());
                }
            }
            e.delay(20);
            for (NPC tornado : tornados) {
                tornado.remove();
            }
            tornados.clear();
        });
    }

    /**
     * Handles style changes.
     * @param npc
     */
    private boolean changeAttackStyle(NPC npc) {
        boolean change = attack > 0 && attack % 4 == 0;
        if (change) {
            npc.startEvent(e -> {
                npc.animate(SWITCH_STYLES);
                e.delay(3);
                CombatType nextType = type == CombatType.MAGE ? CombatType.RANGE : CombatType.MAGE;
                setType(nextType);
                //tornados(npc, target);
                attack = 0;
            });
            return true;
        }
        return false;
    }

    /**
     *
     * @param type
     * @return
     */
    private int getIdForStyle(CombatType type) {
        switch (type) {
            case RANGE:
                return 9022;
            case MAGE:
                return 9023;
            default:
                return 9021;
        }
    }

    private void preDefend(Hit hit) {
        if(hit.attackStyle == null)
            return;
        if(hit.attackStyle.isMelee() && npc.getId() == 9021)
            hit.block();
        else if(hit.attackStyle.isRanged() && npc.getId() == 9022)
            hit.block();
        else if(hit.attackStyle.isMagic() && npc.getId() == 9023)
            hit.block();
    }

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend).postDefend(this::postDefend);
        //stomp(npc, target);
    }

    @Override
    public void follow() {

    }

    private void magicAttack() {
        int delay = MAGIC_PROJ.send(npc, target);
        if (Misc.random(5) == 1) {
            if (target.isPlayer() && protectPrayersActive(target)) {
                int delay1 = MAGIC_DISABLE_PRAYER.send(npc, target);
                Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay1);
                target.hit(hit);
                target.graphics(1716);
                target.player.sendMessage(Color.RED.wrap("Your prayers have been disabled!"));
                target.player.getPrayer().deactivateAll();
            }
        } else {
            Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay);
            target.graphics(MAGIC_SPLASH);
            target.hit(hit);
        }
        attack++;
        magicAttackCount++;
        boolean change = magicAttackCount > 0 && magicAttackCount % 5 == 0;
        if (magicAttackCount == 5 || change) {
            magicAttackCount = 0;
            attack = 0;
            rangedAttack();
            switchedToRanged = true;
            switchedToMagic = false;
        }
    }

    private void rangedAttack() {
        int delay = RANGE_PROJ.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).clientDelay(delay);
        target.hit(hit);
        attack++;
        rangedAttackCount++;
        firstAttack++;
        boolean change = rangedAttackCount > 0 && rangedAttackCount % 5 == 0;
        if (rangedAttackCount == 5 || change) {
            rangedAttackCount = -1;
            attack = 0;
            magicAttack();
            switchedToMagic = true;
            switchedToRanged = false;
        }
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8) || npc.getCombat().isDead())
            return false;
        if (Random.get(5) == 1 && tornados.isEmpty()) {
            tornados(npc);
            return true;
        }
        if (floorDelay.finished()) {
            floorAttack();
            if (npc.getHp() >= 200) {
                floorDelay.delay(16);
            } else {
                floorDelay.delay(14);
            }
        }
//        if (Random.get(8) == 1) {
//            doFloors(npc, target);
//            System.out.println("Floors");
//            return true;
//        }
        Position bossPos = npc.getLastPosition();
        boolean stomp = bossPos == target.getLastPosition();
        if (stomp || DumbRoute.withinDistance(target, npc, 1)) {
            stomp(npc, target);
            System.out.println("Stomped");
            return true;
        }
        if (switchedToRanged || firstAttack == 0 || rangedAttackCount > 0) {
            rangedAttack();
        }
        if (switchedToMagic) {
            magicAttack();
        }
        return true;
    }

    public void setTarget(Entity target) {
        this.target = target;
        npc.getRouteFinder().routeEntity(target);
    }

    static {
        //Makes the tornados able to follow the player with no path obstructions
        NPCDef.get(9025).ignoreOccupiedTiles = true;
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
        npc.getCombat().setAllowRespawn(false);
        npc.dead();
        npc.startEvent(e -> {
            e.delay(1);
            npc.animate(8421);
            e.delay(3);
            npc.animate(8422);
            e.delay(2);
            npc.getCombat().reset();
            npc.remove();
        });
        //System.out.println("Reward granted");
        hunllefDead = true;
    }

    public static void setChest() {
        GameObject obj = Tile.getObject(36088, 3027, 6123, 1, 22, -1);
        if (obj != null) {
            obj.setId(36087);
        }
        //System.out.println("Chest set");
    }

}
