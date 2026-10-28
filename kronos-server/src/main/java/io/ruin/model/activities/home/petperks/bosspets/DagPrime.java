package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class DagPrime {

    public static void sendPetDagannothPrime(Player p) {
        p.getPacketSender().sendItems(INT, 280, 0, new Item(12644));
        p.getPacketSender().sendString(INT, 277, "Pet Dagannoth Prime");
        p.getPacketSender().sendString(INT, 278, "Provides +5 strength boost");
        if (p.getCollectionLog().contains(12644, 1) || p.getInventory().contains(12644)) {
            p.getPacketSender().setHidden(INT, 281, true);
            p.getPacketSender().setHidden(INT, 282, false);
        }
    }

}