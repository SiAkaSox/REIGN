package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.Server;
import io.ruin.utility.Random;
import io.ruin.cache.Color;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.route.RouteFinder;

public class DonatorDungeon {

    static {
        
            ObjectAction.register(42819, 1, (player, obj) -> enterDungeon(player));
    }

    public static void enterDungeon(Player p) {
        if (p.storeAmountSpent < 100) {
            p.sendMessage("You must be a <img=21> <col=FFFFFF>Ruby Donator</col> to use this dungeon!");
        } else {
           teleport(p, 1250, 4301, 0);
        }
    }

    public static void teleport(Player player, int x, int y, int z) {
        player.resetActions(true, true, true);
        player.lock(LockType.FULL_NULLIFY_DAMAGE); //keep lock outside of event!
        player.startEvent(e -> {
            player.getPacketSender().fadeOut();
            e.delay(2);
            player.getMovement().teleport(x, y, z);
            player.getPacketSender().clearFade();
            PlayerCounter.TELEPORT_PORTAL_USES.increment(player, 1);
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

    public static void spawnAhrims(Player p) {
        if(p.npcTarget) {
            p.sendMessage("You are under attack!");
            return;
        }
        Position pos = RouteFinder.findWalkable(p.getPosition());
        NPC npc = new NPC(15010).spawn(pos).targetPlayer(p, true);
        npc.forceText("You dare disturb my rest!");
        npc.attackTargetPlayer(() -> !p.getPosition().isWithinDistance(npc.getPosition()));
        npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            rollForItem(p, npc, npc.getPosition());
            npc.remove();
        };
    }
    public static void spawnDharok(Player p) {
        if(p.npcTarget) {
            p.sendMessage("You are under attack!");
            return;
        }
        Position pos = RouteFinder.findWalkable(p.getPosition());
        NPC npc = new NPC(15011).spawn(pos).targetPlayer(p, true);
        npc.forceText("You dare disturb my rest!");
        npc.attackTargetPlayer(() -> !p.getPosition().isWithinDistance(npc.getPosition()));
        npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            rollForItem(p, npc, npc.getPosition());
            npc.remove();
        };
    }
    public static void spawnGuthans(Player p) {
        if(p.npcTarget) {
            p.sendMessage("You are under attack!");
            return;
        }
        Position pos = RouteFinder.findWalkable(p.getPosition());
        NPC npc = new NPC(15012).spawn(pos).targetPlayer(p, true);
        npc.forceText("You dare disturb my rest!");
        npc.attackTargetPlayer(() -> !p.getPosition().isWithinDistance(npc.getPosition()));
        npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            rollForItem(p, npc, npc.getPosition());
            npc.remove();
        };
    }
    public static void spawnKaril(Player p) {
        if(p.npcTarget) {
            p.sendMessage("You are under attack!");
            return;
        }
        Position pos = RouteFinder.findWalkable(p.getPosition());
        NPC npc = new NPC(15013).spawn(pos).targetPlayer(p, true);
        npc.forceText("You dare disturb my rest!");
        npc.attackTargetPlayer(() -> !p.getPosition().isWithinDistance(npc.getPosition()));
        npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            rollForItem(p, npc, npc.getPosition());
            npc.remove();
        };
    }
    public static void spawnTorag(Player p) {
        if(p.npcTarget) {
            p.sendMessage("You are under attack!");
            return;
        }
        Position pos = RouteFinder.findWalkable(p.getPosition());
        NPC npc = new NPC(15014).spawn(pos).targetPlayer(p, true);
        npc.forceText("You dare disturb my rest!");
        npc.attackTargetPlayer(() -> !p.getPosition().isWithinDistance(npc.getPosition()));
        npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            rollForItem(p, npc, npc.getPosition());
            npc.remove();
        };
    }
    public static void spawnVerac(Player p) {
        if(p.npcTarget) {
            p.sendMessage("You are under attack!");
            return;
        }
        Position pos = RouteFinder.findWalkable(p.getPosition());
        NPC npc = new NPC(15015).spawn(pos).targetPlayer(p, true);
        npc.forceText("You dare disturb my rest!");
        npc.attackTargetPlayer(() -> !p.getPosition().isWithinDistance(npc.getPosition()));
        npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            rollForItem(p, npc, npc.getPosition());
            npc.remove();
        };
    }

    private static final Item[] AHRIMS = {
            new Item(4708), //Hood
            new Item(4712), //Top
            new Item(4714), //Bottom
            new Item(4710), //Staff
    };
    private static final Item[] DHAROK = {
            new Item(4716), //Helm
            new Item(4720), //Top
            new Item(4722), //Bottom
            new Item(4718), //Axe
    };
    private static final Item[] GUTHANS = {
            new Item(4724), //Helm
            new Item(4728), //Top
            new Item(4730), //Bottom
            new Item(4726), //Spear
    };
    private static final Item[] KARIL = {
            new Item(4732), //Helm
            new Item(4736), //Top
            new Item(4738), //Bottom
            new Item(4734), //Xbow
    };
    private static final Item[] TORAG = {
            new Item(4745), //Helm
            new Item(4749), //Top
            new Item(4751), //Bottom
            new Item(4747), //Hammers
    };
    private static final Item[] VERAC = {
            new Item(4753), //Helm
            new Item(4757), //Top
            new Item(4759), //Bottom
            new Item(4755), //Flail
    };

    public static void rollForItem(Player player, NPC npc, Position dropPosition) {
        int chance;
        Item[] table;
        switch (npc.getDef().id) {
            case 15010:
                chance = 20;
                table = AHRIMS;
                break;
            case 15011:
                chance = 20;
                table = DHAROK;
                break;
            case 15012:
                chance = 20;
                table = GUTHANS;
                break;
            case 15013:
                chance = 20;
                table = KARIL;
                break;
            case 15014:
                chance = 20;
                table = TORAG;
                break;
            case 15015:
                chance = 20;
                table = VERAC;
                break;
            default:
                return;
        }
        if (Random.rollDie(chance)) {
            Item drop = Random.get(table);
            new GroundItem(drop).position(dropPosition).owner(player).spawn();
            player.sendMessage(Color.RED.wrap("Rare drop: "+drop.getDef().name));
        }
    }
}