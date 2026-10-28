package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class GeneralGraardor {

    public static void sendPetGeneralGraardor(Player p) {
        p.getPacketSender().sendItems(INT, 320, 0, new Item(12650));
        p.getPacketSender().sendString(INT, 317, "Pet General Graardor");
        p.getPacketSender().sendString(INT, 318, "Provides +8 strength boost");
        if (p.getCollectionLog().contains(12650, 1) || p.getInventory().contains(12650)) {
            p.getPacketSender().setHidden(INT, 321, true);
            p.getPacketSender().setHidden(INT, 322, false);
        }
    }

}