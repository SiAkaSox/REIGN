package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class PrinceBlackDragon {

    public static void sendPrinceBlackDragon(Player p) {
        p.getPacketSender().sendItems(INT, 380, 0, new Item(12653));
        p.getPacketSender().sendString(INT, 377, "Prince Black Dragon");
        p.getPacketSender().sendString(INT, 378, "Provides anti-fire immunity while active");
        if (p.getCollectionLog().contains(12653, 1) || p.getInventory().contains(12653)) {
            p.getPacketSender().setHidden(INT, 381, true);
            p.getPacketSender().setHidden(INT, 382, false);
        }
    }

}