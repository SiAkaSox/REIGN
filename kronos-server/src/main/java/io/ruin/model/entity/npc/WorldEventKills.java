package io.ruin.model.entity.npc;


import io.ruin.model.achievements.listeners.elite.HailHydra;
import io.ruin.model.achievements.listeners.hard.*;
import io.ruin.model.entity.player.Player;

public class WorldEventKills {

    public static void kills(Player p, int npcId) {
        switch (npcId) {

            case 8918:
                ARealChallenge.complete(p);
                break;

            case 2042:
            case 2043:
            case 2044:
                TheSerpantKing.complete(p);
                break;

            case 5862:
                HowManyHeads.complete(p);
                break;

            case 8061:
            case 8060:
                DragonSlayer.complete(p);
                break;

            case 7881:
            case 7931:
            case 7932:
            case 7933:
            case 7934:
            case 7935:
            case 7936:
            case 7937:
            case 7938:
            case 7939:
            case 7940:
                p.therevenantCount++;
                if (p.therevenantCount == 25) {
                    TheRevenant.complete(p);
                }
                break;

        }
    }

}
