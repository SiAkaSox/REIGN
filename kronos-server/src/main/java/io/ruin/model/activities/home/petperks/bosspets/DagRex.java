package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class DagRex {

    public static void sendPetDagannothRex(Player p) {
        p.getPacketSender().sendItems(INT, 290, 0, new Item(12645));
        p.getPacketSender().sendString(INT, 287, "Pet Dagannoth Rex");
        p.getPacketSender().sendString(INT, 288, "Provides +5 attack boost");
        if (p.getCollectionLog().contains(12645, 1) || p.getInventory().contains(12645)) {
            p.getPacketSender().setHidden(INT, 291, true);
            p.getPacketSender().setHidden(INT, 292, false);
        }
    }

}