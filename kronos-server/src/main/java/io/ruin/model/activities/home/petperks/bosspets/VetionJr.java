package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class VetionJr {

    public static void sendVetionJr(Player p) {
        p.getPacketSender().sendItems(INT, 530, 0, new Item(13179));
        p.getPacketSender().sendString(INT, 527, "Vet'ion Jr");
        p.getPacketSender().sendString(INT, 528, "Provides extra blood money on drops");
        if (p.getCollectionLog().contains(13179, 1) || p.getInventory().contains(13179)) {
            p.getPacketSender().setHidden(INT, 531, true);
            p.getPacketSender().setHidden(INT, 532, false);
        }
    }

}