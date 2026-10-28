package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class CorporealCritter {

    public static void sendCorporealCritter(Player p) {
        p.getPacketSender().sendItems(INT, 150, 0, new Item(22318));
        p.getPacketSender().sendString(INT, 147, "Corporeal Critter");
        p.getPacketSender().sendString(INT, 148, "Double points at castle wars");
        if (p.getCollectionLog().contains(22318, 1) || p.getInventory().contains(22318)) {
            p.getPacketSender().setHidden(INT, 151, true);
            p.getPacketSender().setHidden(INT, 152, false);
        }
    }

}