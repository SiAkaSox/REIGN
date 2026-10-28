package io.ruin.model.activities.home.petperks.minigamespets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class TzrekJad {

    public static void sendTzrekJad(Player p) {
        p.getPacketSender().sendItems(INT, 560, 0, new Item(13225));
        p.getPacketSender().sendString(INT, 557, "Tzrek-Jad");
        p.getPacketSender().sendString(INT, 558, "Pk Points are doubled");
        if (p.getCollectionLog().contains(13225, 1) || p.getInventory().contains(13225)) {
            p.getPacketSender().setHidden(INT, 561, true);
            p.getPacketSender().setHidden(INT, 562, false);
        }
    }

}