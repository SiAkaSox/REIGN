package io.ruin.model.activities.home.petperks.miscpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class LavaDragon {

    public static void sendBabyLavaDragon(Player p) {
        p.getPacketSender().sendItems(INT, 98, 0, new Item(30131));
        p.getPacketSender().sendString(INT, 95, "Baby Lava Dragon");
        p.getPacketSender().sendString(INT, 96, "Provides extra blood money on drops");
        if (p.getCollectionLog().contains(30131, 1) || p.getInventory().contains(30131)) {
            p.getPacketSender().setHidden(INT, 99, true);
            p.getPacketSender().setHidden(INT, 100, false);
        }
    }

}