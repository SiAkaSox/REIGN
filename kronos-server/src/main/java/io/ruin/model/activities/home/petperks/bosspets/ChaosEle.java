package io.ruin.model.activities.home.petperks.bosspets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;
import static io.ruin.model.activities.home.petperks.PetPerkInter.sendModel;

public class ChaosEle {

    public static void sendPetChaosElemental(Player p) {
        p.getPacketSender().sendItems(INT, 260, 0, new Item(11995));
        p.getPacketSender().sendString(INT, 257, "Pet Chaos Elemental");
        p.getPacketSender().sendString(INT, 258, "Grants immunity from disarming attacks");
        if (p.getCollectionLog().contains(11995, 1) || p.getInventory().contains(11995)) {
            p.getPacketSender().setHidden(INT, 261, true);
            p.getPacketSender().setHidden(INT, 262, false);
        }
    }

}