package io.ruin.model.inter.worldeventsinter;

import io.ruin.model.achievements.listeners.medium.ReignEvents;
import io.ruin.model.activities.wilderness.events.GlodEvent;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;

import static io.ruin.model.inter.worldeventsinter.WorldEventsInter.*;

public class GlodInter {

    public static int INTER_ID = 967;

    public static void sendGlod(Player p) {
        ReignEvents.complete(p);
        p.openInterface(InterfaceType.MAIN, INTER_ID);
        sendTitles(p, INTER_ID);
        sendModel(p, INTER_ID, 293, 26074, 3600, 0, 0);
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

        if (GlodEvent.eventActive && !GlodEvent.wasKilled) {
            p.getPacketSender().sendString(INTER_ID, 276, "Glod - <col=0fd60f>Active "+ GlodEvent.ACTIVE.positionHint);
        } else {
            p.getPacketSender().sendString(INTER_ID, 276, "Glod - <col=d22222>Not Active");
        }
        p.getPacketSender().sendString(INTER_ID, 280, "Glod is a fun, old school world boss found in a lot" +
                " of RSPS's. He is a fairly easy and straight forward boss to kill, appealing to players who are new at" +
                " participating in world bosses." +
                "<br>" +
                "<br>" +
                "He can, however, prove to be very deadly if you don't dodge his attacks! Glod has three different attacks." +
                " The first, is his standard ranged auto attack that will hit all players within 8 tiles of him. The second," +
                " is his taunt attack. This will force all players to run towards him, and then he will perform his" +
                " devastating Earthquake attack to players that are still within 8 tilesof him after his taunt!");
    }

}