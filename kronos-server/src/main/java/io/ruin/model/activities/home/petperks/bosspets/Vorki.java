package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class Vorki {

    public static void sendVorki(Player p) {
        p.getPacketSender().sendItems(INT, 540, 0, new Item(21992));
        p.getPacketSender().sendString(INT, 537, "Vorki");
        p.getPacketSender().sendString(INT, 538, "Zombie spawn does 50% less damage");
        if (p.getCollectionLog().contains(21992, 1) || p.getInventory().contains(21992)) {
            p.getPacketSender().setHidden(INT, 541, true);
            p.getPacketSender().setHidden(INT, 542, false);
        }
    }

}