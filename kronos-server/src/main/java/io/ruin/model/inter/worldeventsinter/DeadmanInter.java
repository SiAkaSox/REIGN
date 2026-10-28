package io.ruin.model.inter.worldeventsinter;

import io.ruin.model.achievements.listeners.medium.ReignEvents;
import io.ruin.model.activities.wilderness.events.DeadmanChestEvent;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;

import static io.ruin.model.inter.worldeventsinter.WorldEventsInter.*;

public class DeadmanInter {

    public static int INTER_ID = 965;

    public static void sendDeadman(Player p) {
        ReignEvents.complete(p);
        p.openInterface(InterfaceType.MAIN, INTER_ID);
        sendTitles(p, INTER_ID);
        sendModel(p, INTER_ID, 293, 34265, 1500, 50, 100);
        sendRewards(p, INTER_ID, 282, 0, 12004, 1);
        sendRewards(p, INTER_ID, 284, 0, 11802, 1);
        sendRewards(p, INTER_ID, 286, 0, 13652, 1);
        sendRewards(p, INTER_ID, 288, 0, 12929, 1);
        sendRewards(p, INTER_ID, 290, 0, 12904, 1);
        sendRewards(p, INTER_ID, 292, 0, 11283, 1);
        sendRewards(p, INTER_ID, 295, 0, 12002, 1);
        sendRewards(p, INTER_ID, 297, 0, 19493, 1);
        sendRewards(p, INTER_ID, 299, 0, 30276, 1);
        sendRewards(p, INTER_ID, 301, 0, 12846, 1);
        sendRewards(p, INTER_ID, 303, 0, 995, 5000000);
        sendRewards(p, INTER_ID, 305, 0, 13307, 300);

        if (DeadmanChestEvent.chest != null) {
            p.getPacketSender().sendString(INTER_ID, 276, "Deadmans Chest - <col=0fd60f>Active "+DeadmanChestEvent.ACTIVE.positionHint);
        } else {
            p.getPacketSender().sendString(INTER_ID, 276, "Deadmans Chest - <col=d22222>Not Active");
        }
        p.getPacketSender().sendString(INTER_ID, 280, "The Deadmans Chest is a dangeous world event that" +
                " occurs in random areas of the wilderness. Once every hour, a Deadman Chest will spawn in the wilderness." +
                " The objective is to locate the chest and loot a Great Vault key from it. Looting the chest will automatically" +
                " give the looter a skull, and broadcast a message that you have looted it. " +
                "<br>" +
                "<br>" +
                "This is considered to be one of the easier world events in ospk, however, keep in mind that looting the chest" +
                " will give you a skull and notify players who has looted the key, so your escape must be hasty!" +
                "<br>" +
                "<br>" +
                "Loot the Deadman Chest for a Great Vault Key.");
    }

}