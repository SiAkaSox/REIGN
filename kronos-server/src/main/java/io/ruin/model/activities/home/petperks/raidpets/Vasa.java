package io.ruin.model.activities.home.petperks.raidpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class Vasa {

    public static void sendVasa(Player p) {
        p.getPacketSender().sendItems(INT, 500, 0, new Item(22382));
        p.getPacketSender().sendString(INT, 497, "Vasa Minirio");
        p.getPacketSender().sendString(INT, 498, "x2 points in CoX");
        if (p.getCollectionLog().contains(22382, 1) || p.getInventory().contains(22382)) {
            p.getPacketSender().setHidden(INT, 501, true);
            p.getPacketSender().setHidden(INT, 502, false);
        }
    }

}