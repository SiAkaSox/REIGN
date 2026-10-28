package io.ruin.model.inter.worldeventsinter;

import io.ruin.model.achievements.listeners.medium.ReignEvents;
import io.ruin.model.activities.wilderness.events.capturetheflag.CaptureTheFlag;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;

import static io.ruin.model.inter.worldeventsinter.WorldEventsInter.*;

public class CtfInter {

    public static int INTER_ID = 964;

    public static void sendCtf(Player p){
        ReignEvents.complete(p);
        p.openInterface(InterfaceType.MAIN, INTER_ID);
        sendTitles(p, INTER_ID);
        sendModel(p, INTER_ID, 293, 15985, 1800, 100, 0);
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
        p.getPacketSender().sendString(INTER_ID, 276, CaptureTheFlag.flag != null ? "Capture the Flag - <col=0fd60f>Active "+CaptureTheFlag.flagLocation.getHint() : "Capture the Flag - <col=d22222>Not Active");
        p.getPacketSender().sendString(INTER_ID, 280, "The Capture the Flag world event is a dangerous" +
                " event located in the deep wilderness. Once every hour, a large black flag will spawn at one of ten different" +
                " locations in the deep wilderness. The objective is simple: Pick up the flag, and return it to the Arena" +
                " Grand Master located in the PvP building in the Home area." +
                "<br>" +
                "<br>" +
                "Although the objective of this event is simple, completing it successfully will not be an easy task." +
                " The flag carrier will be unable to use any sort of teleporting methods, and their base stats will be" +
                " lowered to level 1 until the flag carrier either returns the flag to the Arena Grand Master, or dies trying.<br>" +
                "<br>" +
                "The flag carrier must simply survive.");
    }

}