package io.ruin.model.activities.home.petperks.skillingpets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import static io.ruin.model.activities.home.petperks.PetPerkInter.INT;

public class RiftGuardians {

    public static void sendRiftGuardian(Player p) {
        p.getPacketSender().sendItems(INT, 400, 0, new Item(20665));
        p.getPacketSender().sendString(INT, 397, "Rift Guardian");
        p.getPacketSender().sendString(INT, 398, "Doubles runecrafted blood runes");
        if (p.getCollectionLog().contains(20665, 1) || p.getInventory().contains(20665)) {
            p.getPacketSender().setHidden(INT, 401, true);
            p.getPacketSender().setHidden(INT, 402, false);
        }
    }

}