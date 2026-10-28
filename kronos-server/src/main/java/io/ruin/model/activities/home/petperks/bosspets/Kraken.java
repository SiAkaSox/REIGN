package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class Kraken {

    public static void sendPetKraken(Player p) {
        p.getPacketSender().sendItems(INT, 330, 0, new Item(12655));
        p.getPacketSender().sendString(INT, 327, "Pet Kraken");
        p.getPacketSender().sendString(INT, 328, "5% chance pet heals you in combat");
        if (p.getCollectionLog().contains(12655, 1) || p.getInventory().contains(12655)) {
            p.getPacketSender().setHidden(INT, 331, true);
            p.getPacketSender().setHidden(INT, 332, false);
        }
    }

}