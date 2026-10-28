package io.ruin.model.activities.home.petperks.minigamespets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class JalNibRek {

    public static void sendJalNibRek(Player p) {
        p.getPacketSender().sendItems(INT, 200, 0, new Item(21291));
        p.getPacketSender().sendString(INT, 197, "Jal-Nib-Rek");
        p.getPacketSender().sendString(INT, 198, "Inferno starts on wave 30");
        if (p.getCollectionLog().contains(21291, 1) || p.getInventory().contains(21291)) {
            p.getPacketSender().setHidden(INT, 201, true);
            p.getPacketSender().setHidden(INT, 202, false);
        }
    }

}