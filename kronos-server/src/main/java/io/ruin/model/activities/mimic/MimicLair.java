package io.ruin.model.activities.mimic;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.ruin.Server;
import io.ruin.cache.Icon;
import io.ruin.cache.ItemID;
import io.ruin.model.World;
import io.ruin.model.activities.minigames.inferno.Inferno;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MimicLair {

    private static Map<String, MimicLair> instaces = Maps.newConcurrentMap();

    private static List<GameObject> portals = Lists.newArrayList();

    private static final Position ENTER = new Position(2719, 4314, 1);

    private static final Position MIMIC_SPAWN = new Position(2718, 4319, 1);

    private static final Position EXIT = new Position(3090, 3505, 0);

    private static List<Position> BOSS_ENTER = Arrays.asList(new Position(2263, 4686, 0), new Position(2264, 4704, 0), new Position(2279, 4704, 0), new Position(2279, 4686, 0));

    @Getter
    protected DynamicMap map;

    public static DynamicMap bossMap;


    static {
        ObjectAction.register(34733, 1, (player, obj) -> handleOptions(player));
        ObjectAction.register(26735, 1, (player, obj) -> handlePortalExit(player, obj));
        ItemAction.registerInventory(23184, "open", (player, item) -> {
            player.dialogue(new MessageDialogue("This item can be used to fight the Mimic!<br>Make sure you have enough supplies and when<br>you're ready, interact the strange chest east of the home area!"));
        });
    }

    private static void handlePortalExit(Player player, GameObject obj) {
        if (obj == null)
            return;
        if (!obj.isSpawned())
            return;
        obj.setSpawned(false);
        obj.remove();
        onExit(player, true);
    }

    private static void handleOptions(Player player) {
        if (bossMap != null) {
            player.dialogue(new ItemDialogue().one(405, "The Global Mimic has... found you!<br><br>Will you fight now?"),
                    new OptionsDialogue(
                            new Option("Fight the Boss Mimic", () -> joinBossInstance(player)),
                            new Option("Come back later", () -> {
                            })
                    ));
            return;
        }
        if (player.getInventory().contains(23184, 1)) {
            player.dialogue(new ItemDialogue().one(405, "The Mimic has... found you!<br><br>Will you fight now?"),
                    new OptionsDialogue(
                            new Option("Yes", () -> checkAndCreate(player)),
                            new Option("Come back later", () -> {
                            })));
        } else {
            player.dialogue(new ItemDialogue().one(23184, "You need 1x Mimic chest to fight The Mimic."));
        }
    }

    private static void checkAndCreate(Player player) {
        final String name = player.name();
        if (instaces.get(name) != null) {
            //already exists
            return;
        }
        instaces.put(player.name(), new MimicLair(player));
    }

    private Player player;

    public MimicLair(Player player) {
        this.player = player;
        this.createSoloInstance();
    }

    private void createSoloInstance() {
        player.getInventory().remove(23184, 1);
        this.map = new DynamicMap().build(10819, 1);
        player.lock(LockType.FULL);
        player.mimicLair = this;
        player.deathEndListener = (DeathListener.Simple) this::handleDeath;
        player.logoutListener = new LogoutListener().onLogout(p -> handleDeath());
        player.teleportListener = player1 -> {
            player1.sendMessage("A magical force stops you from teleporting.");
            return false;
        };
        player.startEvent(e -> {
            e.delay(1);
            player.getPacketSender().fadeOut();
            e.delay(3);
            spawnNPC(7979);
            player.getMovement().teleport(map.convertPosition(ENTER));
            e.delay(2);
            player.face(Direction.NORTH);
            player.getPacketSender().fadeIn();
            e.delay(3);
            player.unlock();
        });
    }

    public static void createBossInstance() {
        if (bossMap != null)
            return;
        bossMap = new DynamicMap().build(9033, 1);
        World.startEvent(e -> {
            Broadcast.WORLD.sendNews(Icon.BLUE_INFO_BADGE, "The Cursed Mimic Boss will spawn in 10 Minutes! use the strange chest to teleport.");
            e.delay(100 * 10);
            spawnBoss();
        });
    }

    private static void spawnBoss() {
        NPC npc = new NPC(7979).spawn(bossMap.convertPosition(new Position(2271, 4695, 0)));
        npc.set("BossMimic", true);
        npc.setIgnoreMulti(true);
        npc.getCombat().setAllowRespawn(false);
        npc.walkRange = 25;
        npc.getDef().ignoreOccupiedTiles = true;
        npc.deathEndListener = (entity, killer, killHit) -> {
            npc.remove();
            World.sendWorldMessage("The Cursed Mimic has been defeated! Portals will begin to spawn in the next 10 seconds.");
            addPortalTask();
        };
        bossMap.addNpc(npc);
        Broadcast.WORLD.sendNews(Icon.BLUE_INFO_BADGE, "The Cursed Mimic has spawned. Visit the strange chest at home to teleport.");
    }

    private static void addPortalTask() {
        World.startEvent(e -> {
            bossMap.forPlayers(x -> {
                x.attackPlayerListener = (player, pTarget, message) -> atBossEvent(player);
                x.sendMessage("Safe PvP has been enabled! the event will end in the next 60 seconds.");
            });
            spawnPortal();
            e.delay(100);
            finishGlobal();
            //REMOVE ALL
        });
    }

    private static void finishGlobal() {
        World.sendWorldMessage("The Cursed Mimic event has ended!");
        bossMap.forPlayers(p -> onExit(p, true));
        bossMap.destroy();
        portals.stream().filter(portals -> portals != null).forEach(portal -> portal.remove());
        portals = Lists.newArrayList();
        bossMap = null;
    }

    private static void spawnPortal() {
        World.startEvent(e -> {
            while (bossMap != null) {
                e.delay(18);
                if (bossMap == null)
                    break;
                int portalAmount = Math.max(1, (bossMap.getPlayersSize() / 5));
                if (portalAmount == 0)
                    return;
                for (int i = 0; i < portalAmount; i++) {
                    Bounds bounds = new Bounds(2262, 4686, 2281, 4704, 0);
                    Position portalSpawn = bossMap.convertPosition(bounds.randomPosition());
                    GameObject portal = GameObject.spawn(26735, portalSpawn.getX(), portalSpawn.getY(), portalSpawn.getZ(), 10, 0);
                    portals.add(portal);
                    bossMap.forPlayers(p -> p.sendMessage(portalAmount + " " + (portalAmount > 1 ? "portals" : "portal") + " have randomly appeared! it only has 1 use, be quick to exit!"));
                }
            }
        });
    }

    public static boolean atBossEvent(Player player) {
        return bossMap.inArea(player);
    }

    private static void joinBossInstance(Player player) {//TODO fix dropping items on death.. add to the middle every 1 minute.
        player.lock(LockType.FULL);
        player.deathEndListener = (entity, killer, killHit) -> {
            player.attackPlayerListener = null;
            List<Item> dropping = Lists.newArrayList();
            for (Item inv : player.getInventory().getItems()) {
                if (inv == null)
                    continue;
                if (inv.hasAttribute("BossMimic")) {
                    dropping.add(inv);
                    inv.remove();
                }
            }
            for (Item equip : player.getEquipment().getItems()) {
                if (equip == null)
                    continue;
                if (equip.hasAttribute("BossMimic")) {
                    dropping.add(equip);
                    equip.remove();
                }
            }

            if (dropping.size() > 0) {
                for (Item drop : dropping) {
                    GroundItem ground = new GroundItem(drop).owner(killer == null ? player : killer.player).position(player.getPosition().copy());
                    if (killer == null)
                        ground.spawnPublic();
                    else
                        ground.spawn();
                }
                player.sendMessage("Oh dear, you've lost all the items you've looted!");
            }

            onExit(player, false);
        };
        player.logoutListener = new LogoutListener().onAttempt(e -> {
            player.sendMessage("You cannot logout from the Global Mimic event!");
            return false;
        });
        player.teleportListener = player1 -> {
            player1.sendMessage("A magical force stops you from teleporting.");
            return false;
        };
        player.startEvent(e -> {
            e.delay(1);
            player.getPacketSender().fadeOut();
            e.delay(3);
            player.getMovement().teleport(bossMap.convertPosition(BOSS_ENTER.get(Misc.random(BOSS_ENTER.size() - 1))));
            e.delay(2);
            player.face(Direction.NORTH);
            player.getPacketSender().fadeIn();
            e.delay(3);
            player.unlock();
        });
    }

    private List<Integer> giveSoloReward() {
        List<Integer> rewards = Lists.newArrayList();
        for (int i = 0; i < 2; i++) {
            double chance = Math.random();
            if (chance <= 0.10) {
                rewards.add(ItemID.REWARD_CASKET_MASTER);
            } else if (chance <= 0.20) {
                rewards.add(ItemID.REWARD_CASKET_ELITE);
            } else if (chance <= 0.30)
                rewards.add(ItemID.REWARD_CASKET_HARD);
            else
                rewards.add(ItemID.REWARD_CASKET_MEDIUM);
        }
        return rewards;
    }

    private void spawnNPC(int id) {
        NPC npc = new NPC(id).spawn(map.convertPosition(MIMIC_SPAWN));
        npc.set("Mimic", true);
        npc.setIgnoreMulti(true);
        npc.getDef().ignoreOccupiedTiles = true;
        npc.walkRange = 20;
        npc.getCombat().setAllowRespawn(false);

        npc.deathEndListener = (entity, killer, killHit) -> {
            player.startEvent(e -> {
                player.lock();
                map.getNpcs().stream().forEach(n -> n.remove());
                player.getPacketSender().fadeOut();
                e.delay(2);
                handleDeath();
                e.delay(1);
                player.getPacketSender().fadeIn();
                e.delay(1);
                player.dialogue(new MessageDialogue("You have defeated The Mimic!"));
                e.delay(3);
                List<Integer> rewards = giveSoloReward();
                player.dialogue(new ItemDialogue().two(rewards.get(0), rewards.get(1), "Take these chests as a reward."));
                rewards.stream().forEach(r -> player.getInventory().add(r, 1));
                player.unlock();
            });
        };
        map.addNpc(npc);
    }

    private static void onExit(Player player, boolean boss) {
        player.deathEndListener = null;
        player.logoutListener = null;
        player.teleportListener = null;
        player.getCombat().resetTb();
        if (boss) {
            player.attackPlayerListener = null;
            player.sendMessage("You made it out alive!");
        }
        player.getMovement().teleport(EXIT);
        player.getCombat().restore();
    }


    private void handleDeath() {
        onExit(player, false);
        map.getNpcs().stream().filter(n -> n != null).forEach(n -> n.remove());
        map.destroy();
        instaces.remove(player.name());
        player.mimicLair = null;
    }
}
