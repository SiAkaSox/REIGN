package io.ruin.model.activities.home.petperks.skillingpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class Herbi {

    public static void sendHerbi(Player p) {
        p.getPacketSender().sendItems(INT, 170, 0, new Item(21509));
        p.getPacketSender().sendString(INT, 167, "Herbi");
        p.getPacketSender().sendString(INT, 168, "Gives noted herbs while farming");
        if (p.getCollectionLog().contains(21509, 1) || p.getInventory().contains(21509)) {
            p.getPacketSender().setHidden(INT, 171, true);
            p.getPacketSender().setHidden(INT, 172, false);
        }
    }

}