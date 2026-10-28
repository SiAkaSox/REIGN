package io.ruin.model.activities.home.petperks.raidpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class Olmlet {

    public static void sendOlmlet(Player p) {
        p.getPacketSender().sendItems(INT, 250, 0, new Item(20851));
        p.getPacketSender().sendString(INT, 247, "Olmlet");
        p.getPacketSender().sendString(INT, 248, "x2 points in CoX");
        if (p.getCollectionLog().contains(20851, 1) || p.getInventory().contains(20851)) {
            p.getPacketSender().setHidden(INT, 251, true);
            p.getPacketSender().setHidden(INT, 252, false);
        }
    }

}