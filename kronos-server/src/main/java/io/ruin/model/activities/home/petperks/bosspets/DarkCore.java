package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class DarkCore {

    public static void sendPetDarkCore(Player p) {
        p.getPacketSender().sendItems(INT, 310, 0, new Item(12816));
        p.getPacketSender().sendString(INT, 307, "Pet Dark Core");
        p.getPacketSender().sendString(INT, 308, "Dark core drains 50% less hp");
        if (p.getCollectionLog().contains(12816, 1) || p.getInventory().contains(12816)) {
            p.getPacketSender().setHidden(INT, 311, true);
            p.getPacketSender().setHidden(INT, 312, false);
        }
    }

}