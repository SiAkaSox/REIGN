package io.ruin.model.activities.home.petperks.skillingpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class Tangleroot {

    public static void sendTangleroot(Player p) {
        p.getPacketSender().sendItems(INT, 470, 0, new Item(20661));
        p.getPacketSender().sendString(INT, 467, "Tangleroot");
        p.getPacketSender().sendString(INT, 468, "Farming produces x2 herbs");
        if (p.getCollectionLog().contains(20661, 1) || p.getInventory().contains(20661)) {
            p.getPacketSender().setHidden(INT, 471, true);
            p.getPacketSender().setHidden(INT, 472, false);
        }
    }

}