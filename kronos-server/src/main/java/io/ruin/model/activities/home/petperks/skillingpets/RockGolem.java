package io.ruin.model.activities.home.petperks.skillingpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class RockGolem {

    public static void sendRockGolem(Player p) {
        p.getPacketSender().sendItems(INT, 410, 0, new Item(13321));
        p.getPacketSender().sendString(INT, 407, "Rock Golem");
        p.getPacketSender().sendString(INT, 408, "Gives noted ore while mining");
        if (p.getCollectionLog().contains(13321, 1) || p.getInventory().contains(13321)) {
            p.getPacketSender().setHidden(INT, 411, true);
            p.getPacketSender().setHidden(INT, 412, false);
        }
    }

}