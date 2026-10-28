package io.ruin.model.inter.worldeventsinter;

import io.ruin.model.achievements.listeners.medium.ReignEvents;
import io.ruin.model.activities.wilderness.events.HoldTheFort;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;

import static io.ruin.model.inter.worldeventsinter.WorldEventsInter.*;

public class HtfInter {

    public static int INTER_ID = 968;

    public static void sendHtf(Player p) {
        ReignEvents.complete(p);
        p.openInterface(InterfaceType.MAIN, 968);
        sendTitles(p, 968);
        sendModel(p, 968, 293, 36085, 1000, 100, 0);
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

        if (HoldTheFort.eventActive) {
            p.getPacketSender().sendString(968, 276, "Hold the Fort - <col=0fd60f>Active ");
        } else {
            p.getPacketSender().sendString(968, 276, "Hold the Fort - <col=d22222>Not Active");
        }
        p.getPacketSender().sendString(968, 280, "Hold The Fort is a dangerous wilderness world event" +
                " located at Rogue's Castle in the deep wild. The objective is simple, hold the forst for 60 seconds to win." +
                " Winning, however, will not come easily, as players are not able to use any prayers inside of the castle" +
                " during the event." +
                "<br>" +
                "<br>" +
                "Players must click the orb, located on the 2nd floor of the castle, and defend it for 60 seconds to win." +
                " To overtake an orb from a fort holder, all a player must do is click the orb to take over control." +
                " If the fort holder dies or leaves the area, the orb will become unclaimed and up for grabs. <br>" +
                "<br>" +
                "<br>" +
                "Hold The Fort for 60 seconds to win a Great Vault Key!");
    }

}