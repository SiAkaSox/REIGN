package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class Noon {

    public static void sendNoon(Player p) {
        p.getPacketSender().sendItems(INT, 240, 0, new Item(21748));
        p.getPacketSender().sendString(INT, 237, "Noon");
        p.getPacketSender().sendString(INT, 238, "x2 Rift Shards at Time Rifts");
        if (p.getCollectionLog().contains(21748, 1) || p.getInventory().contains(21748)) {
            p.getPacketSender().setHidden(INT, 241, true);
            p.getPacketSender().setHidden(INT, 242, false);
        }
    }

}