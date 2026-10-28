package io.ruin.model.activities.timerift;

import io.ruin.model.achievements.listeners.easy.LoomingThreat;
import io.ruin.model.achievements.listeners.elite.HailHydra;
import io.ruin.model.achievements.listeners.medium.KingOfTheWild;
import io.ruin.model.achievements.listeners.medium.MoleyMoleyMoley;
import io.ruin.model.entity.player.Player;

public class TaskKills {

    public static void kills(Player p, int npcId) {
        if (npcId == 239) {
            KingOfTheWild.complete(p);
        }
        if (npcId == 5779 || npcId == 6499) {
            MoleyMoleyMoley.complete(p);
        }
        if (npcId == 1026 || npcId == 3030) {
            p.loomingThreatCount++;
            if (p.loomingThreatCount == 20) {
                LoomingThreat.complete(p);
            }
        }
//        case 8615:
//        case 8616:
//        case 8617:
//        case 8618:
//        case 8619:
//        case 8620:
//        case 8621:
//        case 8622:
        if (npcId == 8615 || npcId == 8616 || npcId == 8617 || npcId == 8618 || npcId == 8619 || npcId == 8620 || npcId == 8621 || npcId == 8622) {
            p.hailhydraCount++;
            if (p.hailhydraCount == 25) {
                HailHydra.complete(p);
            }
        }
    }
}
