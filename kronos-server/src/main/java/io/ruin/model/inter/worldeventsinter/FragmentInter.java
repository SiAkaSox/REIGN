package io.ruin.model.inter.worldeventsinter;

import io.ruin.model.achievements.listeners.medium.ReignEvents;
import io.ruin.model.activities.wilderness.events.seren.SerenEvent;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;

import static io.ruin.model.inter.worldeventsinter.WorldEventsInter.*;

public class FragmentInter {

    public static void sendFragment(Player p) {
        ReignEvents.complete(p);
        p.openInterface(InterfaceType.MAIN, 966);
        sendTitles(p, 966);
        sendModel(p, 966, 293, 38605, 1250, 0, 0);
        sendRewards(p, 966, 282, 0, 11908, 1);//uncharged trident
        sendRewards(p, 966, 284, 0, 19493, 1);//zenyte
        sendRewards(p, 966, 286, 0, 30276, 1);//$5 bond
        sendRewards(p, 966, 288, 0, 2528, 3);//xp lamps
        sendRewards(p, 966, 290, 0, 11832, 1);//bcp
        sendRewards(p, 966, 292, 0, 11830, 1);//arma legs
        sendRewards(p, 966, 295, 0, 11283, 1);//dfs
        sendRewards(p, 966, 297, 0, 11804, 1);//bgs
        sendRewards(p, 966, 299, 0, 11824, 1);//zammy spear
        sendRewards(p, 966, 301, 0, 24460, 1);//great vault boost
        sendRewards(p, 966, 303, 0, 23951, 5);//enhanced crystal key
        sendRewards(p, 966, 305, 0, 995, 5000000);

        if (SerenEvent.eventActive && !SerenEvent.wasKilled) {
            p.getPacketSender().sendString(966, 276, "Fragment of Seren - <col=0fd60f>Active");
        } else {
            p.getPacketSender().sendString(966, 276, "Fragment of Seren - <col=d22222>Not Active");
        }
        p.getPacketSender().sendString(966, 280, "The Fragment of Seren is the most challenging world" +
                " boss event in OSPK. She has a standard ranged attack, as well as 3 difficult special attacks. Her" +
                " special attacks happen at random, and in no specific order." +
                "<br>" +
                "<br>" +
                " The first, she will teleport all players" +
                " one tile away from her and perform a devastating melee attack to any players within one tile, 3 seconds" +
                " after being teleported. " +
                "<br>" +
                "<br>" +
                "The second, she will spawn a clone of herself, which will perfrom the standard" +
                " the standard ranged attack as well as the special attacks, if not killed quickly. " +
                "<br>" +
                "<br>" +
                "The third, she will spawn four crystals that, if not killed quickly, will start to rapidly tick heal her." +
                " It is imperative to take out the healing crystals, quickly." +
                "<br>" +
                "<br>" +
                " This world event boss is not easy to defeat, however, you will be rewarded with a Great Vault Key if you" +
                " are able to overcome her.");
    }
}