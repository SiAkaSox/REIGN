package io.ruin.model.activities.godwars.combat.impl.zaros;

import io.ruin.Server;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.actions.ObjectAction;

public class BossRoomDoor {

    private static final int id = 42967; // = 42937; // right click was

    static {

        // based on wilderness ditch
        ObjectAction.register(id, 1, (p, obj) -> p.sendMessage("Nex is not active until Week 2!"));
//        ObjectAction.register(id, 1, (p, obj) -> {
//            p.startEvent(event -> {
//                Direction dir;
//                int diffX = 0, diffY = 0;
//                if (p.getPosition().getX() == 2908) {
//                    dir = Direction.EAST;
//                    diffX = 2;
//                } else { // 2910
//                    p.sendMessage("You have to leave using the altar on the East side of the room.");
//                    return;
//                }
//                p.face(obj);
//                p.lock();
//                p.animate(4282);
//                p.privateSound(3997, 1, 25);
//                p.getMovement().force(diffX, diffY, 0, 0, 33, 60, dir);
//                event.delay(1);
//                p.unlock();
//                if (NexUtils.shouldSpawnNex()) {
//                    NPC spawn = new NPC(Nex.Forms.UNATTACKABLE_NEX).spawn(2924, 5202, p.getPosition().getZ(), 40);
//                    p.sendMessage("You're not alone in there...");
//                }
//            });
//        });
        ObjectAction.register(id, 3, (p, obj) -> {
            p.startEvent(event -> {
                p.animate(915);
                final long count = NexUtils.countPlayersInNex();
                final boolean shouldSpawn = NexUtils.shouldSpawnNex();
                p.sendMessage("There " + (count == 1 ? "is 1 player" : "are " + count + " players") + " inside, and Nex " + (shouldSpawn ? "is not alive" : "is alive") + ".");
            });
        });
    }
}
