package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class HellPuppy {

    public static void sendHellpuppy(Player p) {
        p.getPacketSender().sendItems(INT, 160, 0, new Item(13247));
        p.getPacketSender().sendString(INT, 157, "Hellpuppy");
        p.getPacketSender().sendString(INT, 158, "Summon souls only drain 15 prayer");
        if (p.getCollectionLog().contains(13247, 1) || p.getInventory().contains(13247)) {
            p.getPacketSender().setHidden(INT, 161, true);
            p.getPacketSender().setHidden(INT, 162, false);
        }
    }

}