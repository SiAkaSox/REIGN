package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class BabyMole {

    public static void sendBabyMole(Player p) {
        p.getPacketSender().sendItems(INT, 108, 0, new Item(12646));
        p.getPacketSender().sendString(INT, 105, "Baby Mole");
        p.getPacketSender().sendString(INT, 106, "5% chance to double casket GP");
        if (p.getCollectionLog().contains(12646, 1) || p.getInventory().contains(12646)) {
            p.getPacketSender().setHidden(INT, 109, true);
            p.getPacketSender().setHidden(INT, 110, false);
        }
    }

}