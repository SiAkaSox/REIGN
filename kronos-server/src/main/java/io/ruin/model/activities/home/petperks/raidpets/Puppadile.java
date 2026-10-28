package io.ruin.model.activities.home.petperks.raidpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class Puppadile {

    public static void sendPuppadile(Player p) {
        p.getPacketSender().sendItems(INT, 390, 0, new Item(22376));
        p.getPacketSender().sendString(INT, 387, "Puppadile");
        p.getPacketSender().sendString(INT, 388, "x2 points in CoX");
        if (p.getCollectionLog().contains(22376, 1) || p.getInventory().contains(22376)) {
            p.getPacketSender().setHidden(INT, 391, true);
            p.getPacketSender().setHidden(INT, 392, false);
        }
    }

}