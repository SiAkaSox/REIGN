package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class VenenatisSpiderling {

    public static void sendVenenatisSpirderling(Player p) {
        p.getPacketSender().sendItems(INT, 520, 0, new Item(13177));
        p.getPacketSender().sendString(INT, 517, "Venenatis Spiderling");
        p.getPacketSender().sendString(INT, 518, "Provides extra blood money on drops");
        if (p.getCollectionLog().contains(13177, 1) || p.getInventory().contains(13177)) {
            p.getPacketSender().setHidden(INT, 521, true);
            p.getPacketSender().setHidden(INT, 522, false);
        }
    }

}