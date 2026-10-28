package io.ruin.model.activities.home.petperks.raidpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class Tektiny {

    public static void sendTektiny(Player p) {
        p.getPacketSender().sendItems(INT, 480, 0, new Item(22378));
        p.getPacketSender().sendString(INT, 477, "Tektiny");
        p.getPacketSender().sendString(INT, 478, "x2 points in CoX");
        if (p.getCollectionLog().contains(22378, 1) || p.getInventory().contains(22378)) {
            p.getPacketSender().setHidden(INT, 481, true);
            p.getPacketSender().setHidden(INT, 482, false);
        }
    }

}