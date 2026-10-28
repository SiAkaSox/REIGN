package io.ruin.model.activities.home;

import io.ruin.Server;
import io.ruin.cache.NPCDef;
import io.ruin.cache.NpcID;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

import static io.ruin.Server.STARTUP_THREAD;

public class AFKZone {

    public static final Bounds AFK_ZONE = new Bounds(3085, 3475, 3096, 3490, -1);

    static {
        final int[] BARRIER = {20839};
        for (int barriers : BARRIER) {
            ObjectAction.register(barriers, "Enter", (player, obj) -> {
                boolean atObjX = obj.x == player.getAbsX();
                boolean atObjY = obj.y == player.getAbsY();
                if (obj.direction == 0 && atObjX)
                    teleportPlayer(player, player.getAbsX() - 1, player.getAbsY());
                else if (obj.direction == 1 && atObjY)
                    teleportPlayer(player, obj.x, obj.y + 1);
                else if (obj.direction == 2 && atObjX)
                    teleportPlayer(player, obj.x + 1, obj.y);
                else if (obj.direction == 3 && atObjY)
                    teleportPlayer(player, obj.x, obj.y - 1);
                else
                    teleportPlayer(player, obj.x, obj.y);
            });
        }
        ObjectAction.register(34918, "chop-down", (player, obj) -> cutTree(player));
        ObjectAction.register(20210, "Squeeze-through", AFKZone::Agility);
        NPCAction.register(NpcID.HESPORI_AFK, 2, (player, npc) -> Hespori(player));
        NPCAction.register(NpcID.HERBIBOAR, 1, (player, npc) -> Herbi(player));

        NPCAction.register(NpcID.ZALCANO_9050, 1, (player, npc) -> {
            // npc.walkTo = new Position(3122, 3477, 0);
            mineZalcano(player);
        });
        NPCAction.register(NpcID.SPIRITPOOL, 1, (player, npc) -> SpiritPool(player));
        MapListener.registerBounds(AFK_ZONE).onEnter(
                player -> player.getPacketSender().sendWidget(Widget.AFK_ZONE, Server.toTicks(player.afkzoneTicks * 100))
        ).onExit((player, logout) -> player.closeInterface(InterfaceType.PRIMARY_OVERLAY));
    }

    public static void enterArea(Player p) {

    }

    private static void teleportPlayer(Player p, int x, int y) {
        if (p.afkzoneTicks <= 0) {
            p.dialogue(
                    new ItemDialogue().one(757, "You must vote today before being granted access to the AFK Zone. To vote, type ::vote and claim votes on all required sites.")
            );
        } else {
            p.startEvent(event -> {
                p.lock();
                p.animate(4282);
                event.delay(1);
                p.getMovement().teleport(x, y);
                event.delay(1);
                p.unlock();
            });
        }
    }

    private static void Herbi(Player p) {
        //Hunter & herblore
        p.startEvent(e -> {
            while (true) {
                e.delay(1);
                p.animate(2282);
                p.getInventory().addOrDrop(25527, 3); // 6k an hour
                p.getStats().addXp(StatType.Herblore, 20.0, false);
                p.getStats().addXp(StatType.Hunter, 20.0, false);
                e.delay(3);
            }
        });
    }

    private static void Agility(Player p, GameObject object) {
        if (object.x == 3087) {
            p.startEvent(e -> {
                while (true) {
                    p.face(object);
                    p.animate(749);
                    e.delay(2);
                    p.getMovement().teleport(3087, 3484, 0);
                    p.getStats().addXp(StatType.Agility, 10, false);
                    e.delay(3);
                    p.face(object);
                    p.animate(749);
                    e.delay(2);
                    p.getMovement().teleport(3087, 3481, 0);
                    p.getStats().addXp(StatType.Agility, 10, false);
                    p.getInventory().addOrDrop(25527, 3); // 6k an hour
                    e.delay(2);
                }
            });
        }
    }

    private static void Hespori(Player p) {
        //Farming
        p.startEvent(e -> {
            while (true) {
                int secateurs = 0;
                if (p.getEquipment().hasId(7409) || p.getInventory().hasId(7409)) {
                    secateurs = 1;
                } else if (p.getInventory().hasId(5329)) {
                    secateurs = 0;
                }
                int anim = secateurs == 1 ? 3342 : 2279;
                e.delay(1);
                p.animate(anim);
                p.getInventory().addOrDrop(25527, 3); // 6k an hour
                p.getStats().addXp(StatType.Farming, 20.0, false);
                e.delay(3);
            }
        });
    }

    private static void mineZalcano(Player p) {
        // Mining & Runecrafting
        p.startEvent(e -> {
            while (true) {
                e.delay(1);
                p.animate(624);
                p.getInventory().addOrDrop(25527, 3); // 6k an hour
                p.getStats().addXp(StatType.Mining, 20.0, false);
                p.getStats().addXp(StatType.Runecrafting, 20.0, false);
                e.delay(3);
            }
        });
    }

    private static void SpiritPool(Player p) {
        // Fishing
        p.startEvent(e -> {
            while (true) {
                e.delay(1);
                p.animate(618);
                p.getInventory().addOrDrop(25527, 3); // 6k an hour
                p.getStats().addXp(StatType.Fishing, 20.0, false);
                e.delay(3);
            }
        });
    }

    private static void cutTree(Player p) {
        p.startEvent(e -> {
            while (true) {
                e.delay(1);
                p.animate(867);
                p.getInventory().addOrDrop(25527, 3);
                p.getStats().addXp(StatType.Woodcutting, 20.0, false);
                e.delay(3);
            }
        });
    }

}