package io.ruin.model.activities.wilderness.pvp.leaderboard;

import io.ruin.utility.NumberUtils;
import io.ruin.utility.StringUtils;
import io.ruin.model.entity.player.Player;

public class EdgePker implements Pker {
    private Player player;
    private static int kills;
    private int deaths;

    public EdgePker(Player player) {
        this.player = player;
    }

    @Override
    public void addKill() {
        kills++;
    }

    @Override
    public void addDeath() {
        deaths++;
    }

    public Player getPlayer() {
        return player;
    }

    public static int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int compareTo(EdgePker v2) {
        double kdr = kills / Math.max(1D, deaths);
        double kdr2 = v2.kills / Math.max(1D, v2.deaths);
        if (getKills() > v2.getKills() || (getKills() == v2.getKills() && kdr > kdr2)) {
            return -1;
        } else if (getKills() < v2.getKills()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return StringUtils.capitalizeFirst("<col=ff981f>"+getPlayer().name()) + "     <img=4> Kills: <col=27ae60>" + getKills() + "</col>      <col=ff981f> <img=9> Deaths: <col=FF0000>" + + getDeaths() + "</col>       <col=ff981f><img=14> K/D: <col=27ae60>" + NumberUtils.formatTwoPlaces((getDeaths() == 0 ? (double) getKills() : (getKills() / Math.max(1D, getDeaths()))));
    }
}
