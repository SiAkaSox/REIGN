package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class IkkleHydra {

    public static void sendIkkleHydra(Player p) {
        p.getPacketSender().sendItems(INT, 190, 0, new Item(22746));
        p.getPacketSender().sendString(INT, 187, "Ikkle Hydra");
        p.getPacketSender().sendString(INT, 188, "Double points at castle wars");
        if (p.getCollectionLog().contains(22746, 1) || p.getInventory().contains(22746)) {
            p.getPacketSender().setHidden(INT, 191, true);
            p.getPacketSender().setHidden(INT, 192, false);
        }
    }

}