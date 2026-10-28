package io.ruin.model.activities.home.petperks.miscpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class Youngllef {

    public static void sendYounllef(Player p) {
        p.getPacketSender().sendItems(INT, 550, 0, new Item(23757));
        p.getPacketSender().sendString(INT, 547, "Youngllef");
        p.getPacketSender().sendString(INT, 548, "+5% Gauntlet rare item chance");
        if (p.getCollectionLog().contains(23757, 1) || p.getInventory().contains(23757)) {
            p.getPacketSender().setHidden(INT, 551, true);
            p.getPacketSender().setHidden(INT, 552, false);
        }
    }

}