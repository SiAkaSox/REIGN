package io.ruin.model.activities.minigames.castlewars;

import io.ruin.model.entity.player.Player;

public class CastleWarsRewards {

    public static void giveRewards(Player p) {
        int totalAmount = 3 + donatorMultiplier(p) + voteMultiplier(p);
        if (p.ikkleHydra || p.corporealCritter) {
            totalAmount *= 2;
        }
        p.castleWarsWins++;
        p.sendMessage("You were awarded: <col=d22222>x"+totalAmount+" Castle Wars Tickets");
        p.sendMessage("You now have (<col=d22222>"+p.castleWarsWins+"</col>) Castle Wars Wins!");
        p.getInventory().addOrDrop(16165, totalAmount);
    }

    public static int donatorMultiplier(Player p) {
        if (p.isDonator())
            return 1;
        if (p.isSuperDonator())
            return 2;
        if (p.isLegendaryDonator())
            return 3;
        if (p.isRespectedDonator())
            return 5;
        if (p.isPlatinumDonator())
            return 8;
        return 0;
    }

    public static int voteMultiplier(Player p) {
        if (p.castleWarsVoteMultiplier) {
            return 2;
        }
        return 0;
    }

}