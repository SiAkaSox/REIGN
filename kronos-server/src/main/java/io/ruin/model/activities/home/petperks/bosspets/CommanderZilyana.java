package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class CommanderZilyana {

    public static void sendPetCommanderZilyana(Player p) {
        p.getPacketSender().sendItems(INT, 270, 0, new Item(12651));
        p.getPacketSender().sendString(INT, 267, "Pet Commander Zilyana");
        p.getPacketSender().sendString(INT, 268, "Provides +8 magic boost");
        if (p.getCollectionLog().contains(12651, 1) || p.getInventory().contains(12651)) {
            p.getPacketSender().setHidden(INT, 271, true);
            p.getPacketSender().setHidden(INT, 272, false);
        }
    }

}