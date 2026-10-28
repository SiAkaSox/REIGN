package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class DagSupreme {

    public static void sendPetDagannothSupreme(Player p) {
        p.getPacketSender().sendItems(INT, 300, 0, new Item(12643));
        p.getPacketSender().sendString(INT, 297, "Pet Dagannoth Supreme");
        p.getPacketSender().sendString(INT, 298, "Provides +5 defence boost");
        if (p.getCollectionLog().contains(12643, 1) || p.getInventory().contains(12643)) {
            p.getPacketSender().setHidden(INT, 301, true);
            p.getPacketSender().setHidden(INT, 302, false);
        }
    }

}