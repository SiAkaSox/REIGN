package io.ruin.model.inter.pvpinter;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PvpRatingInter {

    public static int SUMMARY = 976;

    public static void openInter(Player p) {
        List<Player> allPlayers = Arrays.asList(World.players.entityList);
        String leagueName = p.getLeagueName();
        //int playerRank = getPlayerRankInLeague(allPlayers, leagueName, p.name());
        p.openInterface(InterfaceType.MAIN, SUMMARY);
        p.getPacketSender().sendString(SUMMARY, 440, "Season I");//Season #
        p.getPacketSender().sendString(SUMMARY, 344, p.name());
        p.getPacketSender().sendString(SUMMARY, 406, ""+p.pvpRating);
        p.getPacketSender().sendString(SUMMARY, 408, p.getLeagueName());//League name
        p.getPacketSender().sendString(SUMMARY, 431, "#");//League rank
        p.getPacketSender().sendString(SUMMARY, 434, ""+p.seasonKills);//Kills
        p.getPacketSender().sendString(SUMMARY, 435, ""+p.seasonDeaths);//Deaths
        p.getPacketSender().sendString(SUMMARY, 436, ""+p.rated1v1Wins);//Arena wins
        p.getPacketSender().sendString(SUMMARY, 437, ""+p.rated1v1Losses);//Arena losses
        p.getPacketSender().sendString(SUMMARY, 438, ""+p.highestKillSpree);//Highest killstreak
        p.getPacketSender().sendString(SUMMARY, 439, "N/A");//Last seasons rating
        sendRecentActivityStrings(p);
        sendRecentActivitySprites(p);
    }

    public static void sendRecentActivityStrings(Player p) {
        int[] childIds = {168, 449, 458, 467, 476, 486, 495, 504, 513, 522};
        for (int i = 0; i < childIds.length; i++) {
            if (i < p.recentActivityPlayerNames.size() && p.recentActivityPlayerNames.get(i) != null) {
                p.getPacketSender().sendString(SUMMARY, childIds[i], p.recentActivityPlayerNames.get(i).name());
            } else {
                p.getPacketSender().sendString(SUMMARY, childIds[i], "None");
            }
        }
    }

    public static void sendRecentActivitySprites(Player p) {
        int[] childIdsWins = {443, 452, 461, 470, 479, 489, 498, 507, 516, 525};
        int[] childIdsLosses = {526, 527, 528, 529, 530, 531, 532, 533, 534, 535};
        for (int i = 0; i < childIdsWins.length; i++) {
            p.getPacketSender().setHidden(SUMMARY, childIdsWins[i], i >= p.recentActivityWins.size() || p.recentActivityWins.get(i) == null);
        }
        for (int i = 0; i < childIdsLosses.length; i++) {
            p.getPacketSender().setHidden(SUMMARY, childIdsLosses[i], i >= p.recentActivityLosses.size() || p.recentActivityLosses.get(i) == null);
        }
    }

}