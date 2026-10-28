package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class Skotos {

    public static void sendSkotos(Player p) {
        p.getPacketSender().sendItems(INT, 440, 0, new Item(21273));
        p.getPacketSender().sendString(INT, 437, "Skotos");
        p.getPacketSender().sendString(INT, 438, "25% more melee damage to demons");
        if (p.getCollectionLog().contains(21273, 1) || p.getInventory().contains(21273)) {
            p.getPacketSender().setHidden(INT, 441, true);
            p.getPacketSender().setHidden(INT, 442, false);
        }
    }

}