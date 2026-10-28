package io.ruin.model.activities.home.petperks.raidpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class Vanguard {

    public static void sendVanguard(Player p) {
        p.getPacketSender().sendItems(INT, 490, 0, new Item(22380));
        p.getPacketSender().sendString(INT, 487, "Vanguard");
        p.getPacketSender().sendString(INT, 488, "x2 points in CoX");
        if (p.getCollectionLog().contains(22380, 1) || p.getInventory().contains(22380)) {
            p.getPacketSender().setHidden(INT, 491, true);
            p.getPacketSender().setHidden(INT, 492, false);
        }
    }

}