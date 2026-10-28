package io.ruin.model.activities.home.petperks.raidpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class LilZik {

    public static void sendLilZik(Player p) {
        p.getPacketSender().sendItems(INT, 220, 0, new Item(22473));
        p.getPacketSender().sendString(INT, 217, "Lil' Zik");
        p.getPacketSender().sendString(INT, 218, "Double pkp and votes");
        if (p.getCollectionLog().contains(22473, 1) || p.getInventory().contains(22473)) {
            p.getPacketSender().setHidden(INT, 221, true);
            p.getPacketSender().setHidden(INT, 222, false);
        }
    }

}