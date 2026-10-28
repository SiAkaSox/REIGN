package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class ScorpiasOffspring {

    public static void sendScorpiasOffspring(Player p) {
        p.getPacketSender().sendItems(INT, 430, 0, new Item(13181));
        p.getPacketSender().sendString(INT, 427, "Scorpias Offspring");
        p.getPacketSender().sendString(INT, 428, "Provides poison immunity while active");
        if (p.getCollectionLog().contains(13181, 1) || p.getInventory().contains(13181)) {
            p.getPacketSender().setHidden(INT, 431, true);
            p.getPacketSender().setHidden(INT, 432, false);
        }
    }

}