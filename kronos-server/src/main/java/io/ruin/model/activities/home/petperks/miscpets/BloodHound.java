package io.ruin.model.activities.home.petperks.miscpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class BloodHound {

    public static void sendBloodhound(Player p) {
        p.getPacketSender().sendItems(INT, 128, 0, new Item(19730));
        p.getPacketSender().sendString(INT, 125, "Bloodhound");
        p.getPacketSender().sendString(INT, 126, "Player gets x2 loot from clues");
        if (p.getCollectionLog().contains(19730, 1) || p.getInventory().contains(19730)) {
            p.getPacketSender().setHidden(INT, 129, true);
            p.getPacketSender().setHidden(INT, 130, false);
        }
    }

}