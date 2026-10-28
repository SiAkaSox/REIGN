package io.ruin.model.activities.home.petperks.skillingpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class Beaver {

    public static void sendBeaver(Player p) {
        p.getPacketSender().sendItems(INT, 118, 0, new Item(13322));
        p.getPacketSender().sendString(INT, 115, "Beaver");
        p.getPacketSender().sendString(INT, 116, "Gives noted logs while woodcutting");
        if (p.getCollectionLog().contains(13322, 1) || p.getInventory().contains(13322)) {
            p.getPacketSender().setHidden(INT, 119, true);
            p.getPacketSender().setHidden(INT, 120, false);
        }
    }

}