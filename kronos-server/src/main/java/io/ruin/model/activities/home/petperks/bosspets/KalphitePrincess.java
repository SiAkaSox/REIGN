package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class KalphitePrincess {

    public static void sendKalphitePrincess(Player p) {
        p.getPacketSender().sendItems(INT, 210, 0, new Item(12647));
        p.getPacketSender().sendString(INT, 207, "Kalphite Princess");
        p.getPacketSender().sendString(INT, 208, "Provides immunity against poison");
        if (p.getCollectionLog().contains(12647, 1) || p.getInventory().contains(12647)) {
            p.getPacketSender().setHidden(INT, 211, true);
            p.getPacketSender().setHidden(INT, 212, false);
        }
    }

}