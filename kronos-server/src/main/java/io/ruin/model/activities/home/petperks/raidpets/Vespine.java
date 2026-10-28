package io.ruin.model.activities.home.petperks.raidpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class Vespine {

    public static void sendVespina(Player p) {
        p.getPacketSender().sendItems(INT, 510, 0, new Item(22384));
        p.getPacketSender().sendString(INT, 507, "Vespina");
        p.getPacketSender().sendString(INT, 508, "x2 points in CoX");
        if (p.getCollectionLog().contains(511, 1) || p.getInventory().contains(22384)) {
            p.getPacketSender().setHidden(INT, 511, true);
            p.getPacketSender().setHidden(INT, 512, false);
        }
    }

}