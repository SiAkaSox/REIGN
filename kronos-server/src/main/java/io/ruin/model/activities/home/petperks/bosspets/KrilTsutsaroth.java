package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class KrilTsutsaroth {

    public static void sendPetKrilTsutsaroth(Player p) {
        p.getPacketSender().sendItems(INT, 350, 0, new Item(12652));
        p.getPacketSender().sendString(INT, 347, "Pet K'ril Tsutsaroth");
        p.getPacketSender().sendString(INT, 348, "Provides +8 magic boost");
        if (p.getCollectionLog().contains(12652, 1) || p.getInventory().contains(12652)) {
            p.getPacketSender().setHidden(INT, 351, true);
            p.getPacketSender().setHidden(INT, 352, false);
        }
    }

}