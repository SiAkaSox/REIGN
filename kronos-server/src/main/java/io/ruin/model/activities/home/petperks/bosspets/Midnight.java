package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class Midnight {

    public static void sendMidnight(Player p) {
        p.getPacketSender().sendItems(INT, 230, 0, new Item(21750));
        p.getPacketSender().sendString(INT, 227, "Midnight");
        p.getPacketSender().sendString(INT, 228, "x2 Rift Shards at Time Rifts");
        if (p.getCollectionLog().contains(21750, 1) || p.getInventory().contains(21750)) {
            p.getPacketSender().setHidden(INT, 231, true);
            p.getPacketSender().setHidden(INT, 232, false);
        }
    }

}