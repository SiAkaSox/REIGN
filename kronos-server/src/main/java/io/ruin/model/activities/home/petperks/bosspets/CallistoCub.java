package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class CallistoCub {

    public static void sendCallistoCub(Player p) {
        p.getPacketSender().sendItems(INT, 140, 0, new Item(13178));
        p.getPacketSender().sendString(INT, 137, "Callisto Cub");
        p.getPacketSender().sendString(INT, 138, "x2 chance for wildy clue keys");
        if (p.getCollectionLog().contains(13178, 1) || p.getInventory().contains(13178)) {
            p.getPacketSender().setHidden(INT, 141, true);
            p.getPacketSender().setHidden(INT, 142, false);
        }
    }

}