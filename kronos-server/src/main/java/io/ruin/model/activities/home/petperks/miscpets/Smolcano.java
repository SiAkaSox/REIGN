package io.ruin.model.activities.home.petperks.miscpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class Smolcano {

    public static void sendSmolcano(Player p) {
        p.getPacketSender().sendItems(INT, 450, 0, new Item(23760));
        p.getPacketSender().sendString(INT, 447, "Smolcano");
        p.getPacketSender().sendString(INT, 448, "Coming soon!");
        if (p.getCollectionLog().contains(23760, 1) || p.getInventory().contains(23760)) {
            p.getPacketSender().setHidden(INT, 451, true);
            p.getPacketSender().setHidden(INT, 452, false);
        }
    }

}