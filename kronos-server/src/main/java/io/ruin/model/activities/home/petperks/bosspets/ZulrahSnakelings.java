package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class ZulrahSnakelings {

    public static void sendPetSnakeling(Player p) {
        p.getPacketSender().sendItems(INT, 370, 0, new Item(12921));
        p.getPacketSender().sendString(INT, 367, "Pet Snakeling");
        p.getPacketSender().sendString(INT, 368, "10% chance to envenom pvm target");
            if (p.getCollectionLog().contains(12921, 1) || p.getInventory().contains(12921)) {
            p.getPacketSender().setHidden(INT, 371, true);
                p.getPacketSender().setHidden(INT, 372, false);
        }
    }

}