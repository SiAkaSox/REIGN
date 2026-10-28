package io.ruin.model.inter.worldeventsinter;

import io.ruin.model.achievements.listeners.medium.ReignEvents;
import io.ruin.model.activities.wilderness.events.PirateShipEvent;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;

import static io.ruin.model.inter.worldeventsinter.WorldEventsInter.*;

public class PiratesInter {

    public static void sendPirates(Player p) {
        ReignEvents.complete(p);
        p.openInterface(InterfaceType.MAIN, 969);
        sendTitles(p, 969);
        sendModel(p, 969, 293, 27894, 800, 0, 0);
        sendRewards(p, 969, 282, 0, 12002, 1);//occult
        sendRewards(p, 969, 284, 0, 19493, 1);//zenyte
        sendRewards(p, 969, 286, 0, 30276, 1);//$5 bond
        sendRewards(p, 969, 288, 0, 2528, 3);//xp lamps
        sendRewards(p, 969, 290, 0, 11832, 1);//bcp
        sendRewards(p, 969, 292, 0, 11830, 1);//arma legs
        sendRewards(p, 969, 295, 0, 11283, 1);//dfs
        sendRewards(p, 969, 297, 0, 11804, 1);//bgs
        sendRewards(p, 969, 299, 0, 11824, 1);//zammy spear
        sendRewards(p, 969, 301, 0, 24460, 1);//great vault boost
        sendRewards(p, 969, 303, 0, 23951, 5);//enhanced crystal key
        sendRewards(p, 969, 305, 0, 995, 5000000);

        if (PirateShipEvent.eventActive) {
            p.getPacketSender().sendString(969, 276, "Pirates Ship - <col=0fd60f>Active");
        } else {
            p.getPacketSender().sendString(969, 276, "Pirates Ship - <col=d22222>Not Active");
        }
        p.getPacketSender().sendString(969, 280, "The Pirates Ship world event is a non-wilderness, safe" +
                " event located in Booty Bay. The Pirates of the bay need players help to repair their ship, as they cannot" +
                " do it without a full Supply Depot!" +
                "<br>" +
                "<br>" +
                "Players participating in the event will be required to bring the Pirates any amount of the following items:" +
                " steel or mithril scimitars, cooked lobsters or sharks, silk, steel nails, and any type of planks. When the" +
                " event begins, a Supply Depot box will be spawned on the dock near the ship in Booty Bay, which is where" +
                " players will deposit these items. All the players needs to do is simply have the items in their inventory," +
                " and click the Supply Depot box to deposit them." +
                "<br>" +
                "<br>" +
                "Along with a Great Vault Key, players will also receive x1 currency to spend in the Enchanting Materials " +
                "shop in Booty Bay, for every item that is contributed to the Supply Depot. Once the depot is full, " +
                "the event will end.");
    }

}