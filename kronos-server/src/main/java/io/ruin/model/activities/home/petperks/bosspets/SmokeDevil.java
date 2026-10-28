package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class SmokeDevil {

    public static void sendPetSmokeDevil(Player p) {
        p.getPacketSender().sendItems(INT, 360, 0, new Item(12648));
        p.getPacketSender().sendString(INT, 357, "Pet Smoke Devil");
        p.getPacketSender().sendString(INT, 358, "10% chance at double gold casket");
        if (p.getCollectionLog().contains(12648, 1) || p.getInventory().contains(12648)) {
            p.getPacketSender().setHidden(INT, 361, true);
            p.getPacketSender().setHidden(INT, 362, false);
        }
    }

}