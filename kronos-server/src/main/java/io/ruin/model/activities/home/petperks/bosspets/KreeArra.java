package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class KreeArra {

    public static void sendPetKreeArra(Player p) {
        p.getPacketSender().sendItems(INT, 340, 0, new Item(12649));
        p.getPacketSender().sendString(INT, 337, "Pet Kree'Arra");
        p.getPacketSender().sendString(INT, 338, "Provides +8 ranged boost");
        if (p.getCollectionLog().contains(12649, 1) || p.getInventory().contains(12649)) {
            p.getPacketSender().setHidden(INT, 341, true);
            p.getPacketSender().setHidden(INT, 342, false);
        }
    }

}