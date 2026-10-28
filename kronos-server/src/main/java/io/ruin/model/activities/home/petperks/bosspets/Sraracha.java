package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class Sraracha {

    public static void sendSraracha(Player p) {
        p.getPacketSender().sendItems(INT, 460, 0, new Item(23495));
        p.getPacketSender().sendString(INT, 457, "Sraracha");
        p.getPacketSender().sendString(INT, 458, "Coming soon!");
        if (p.getCollectionLog().contains(23495, 1) || p.getInventory().contains(23495)) {
            p.getPacketSender().setHidden(INT, 461, true);
            p.getPacketSender().setHidden(INT, 462, false);
        }
    }

}