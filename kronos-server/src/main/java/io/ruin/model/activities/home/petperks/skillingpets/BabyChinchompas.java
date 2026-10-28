package io.ruin.model.activities.home.petperks.skillingpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class BabyChinchompas {

    public static void sendBabyChinchompa(Player p) {
        p.getPacketSender().sendItems(INT, 88, 0, new Item(13323));
        p.getPacketSender().sendString(INT, 85, "Baby Chinchompa");
        p.getPacketSender().sendString(INT, 86, "+3 extra chins caught from Hunter");
        if (p.getCollectionLog().contains(13323, 1) || p.getInventory().contains(13323)) {
            p.getPacketSender().setHidden(INT, 89, true);
            p.getPacketSender().setHidden(INT, 90, false);
        }
    }

}