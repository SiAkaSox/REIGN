package io.ruin.model.activities.home.petperks.skillingpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class Heron {

    public static void sendHeron(Player p) {
        p.getPacketSender().sendItems(INT, 180, 0, new Item(13320));
        p.getPacketSender().sendString(INT, 177, "Heron");
        p.getPacketSender().sendString(INT, 178, "Gives noted fish while fishing");
        if (p.getCollectionLog().contains(13320, 1) || p.getInventory().contains(13320)) {
            p.getPacketSender().setHidden(INT, 181, true);
            p.getPacketSender().setHidden(INT, 182, false);
        }
    }

}