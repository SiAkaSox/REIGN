package io.ruin.model.activities.home.petperks.skillingpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class Rocky {

    public static void sendRocky(Player p) {
        p.getPacketSender().sendItems(INT, 420, 0, new Item(20663));
        p.getPacketSender().sendString(INT, 417, "Rocky");
        p.getPacketSender().sendString(INT, 418, "Gives x2 money from thieving");
        if (p.getCollectionLog().contains(20663, 1) || p.getInventory().contains(20663)) {
            p.getPacketSender().setHidden(INT, 421, true);
            p.getPacketSender().setHidden(INT, 422, false);
        }
    }

}