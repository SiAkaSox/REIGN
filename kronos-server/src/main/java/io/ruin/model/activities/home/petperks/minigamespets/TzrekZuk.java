package io.ruin.model.activities.home.petperks.minigamespets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class TzrekZuk {

    public static void sendTzrekZuk(Player p) {
        p.getPacketSender().sendItems(INT, 570, 0, new Item(22319));
        p.getPacketSender().sendString(INT, 567, "Tzrek-Zuk");
        p.getPacketSender().sendString(INT, 568, "Emblem trader gives x2 coins");
        if (p.getCollectionLog().contains(22319, 1) || p.getInventory().contains(22319)) {
            p.getPacketSender().setHidden(INT, 571, true);
            p.getPacketSender().setHidden(INT, 572, false);
        }
    }

}