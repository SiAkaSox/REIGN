package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class AbyssalOrphan {

    public static void sendAbyssalOrphan(Player p) {
        p.getPacketSender().sendItems(INT, 63, 0, new Item(13262));
        p.getPacketSender().sendString(INT, 34, "Abyssal Orphan");
        p.getPacketSender().sendString(INT, 35, "20% chance to double Larrans Keys");
        if (p.getCollectionLog().contains(13262, 1) || p.getInventory().contains(13262)) {
            p.getPacketSender().setHidden(INT, 79, true);
            p.getPacketSender().setHidden(INT, 80, false);
        }
    }

}