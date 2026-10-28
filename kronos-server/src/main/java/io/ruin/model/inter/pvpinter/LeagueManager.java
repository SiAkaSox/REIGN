package io.ruin.model.inter.pvpinter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.ruin.Server;
import io.ruin.utility.JsonUtils;
import io.ruin.utility.NumberUtils;
import io.ruin.utility.StringUtils;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class LeagueManager {

    public static Map<String, EloRating> leaguePlayerMap = Maps.newConcurrentMap();

    private static final File SAVE_FOLDER = new File("./data/saves/pvp");

    private static final Deque<EloRating> rating_queue = new ArrayDeque<>();

    public static void setPvpRatingOnKill(Player player) {
        player.pvpRating += 15;
        player.sendMessage(Color.RED.wrap("You have received +15 pvp rating for killing your opponent."));
        updatePlayerLeague(player);
    }

    public static void setPvpRatingOnDeath(Player player) {
        player.pvpRating -= 15;
        player.sendMessage(Color.RED.wrap("You have lost -15 pvp rating for dying to your opponent."));
        updatePlayerLeague(player);
    }

    public static String calculateLeague(Player player) {//not used old method tho possible for future purposes
        int rating = player.getPvpRating();
        Optional<League> league = Arrays.stream(League.values()).filter(l -> rating >= l.minRating && rating <= l.maxRating).findAny();
        return league.map(value -> StringUtils.capitalizeFirst(value.name().toLowerCase())).orElse("");
    }

    public static void openScoreboard(Player player) {
        player.openInterface(InterfaceType.MAIN, 978);
        player.getPacketSender().sendString(978, 528, "Bronze");
        player.getPacketSender().sendString(978, 529, "Top 7 players with a rating of 500 or less");
        //player.getPacketSender().setHidden(978, 531, true);//doesnt exist
        player.getPacketSender().setHidden(978, 527, true);
        //player.getPacketSender().setHidden(978, 533, true);//doesnt exist
        //player.getPacketSender().setHidden(978, 532, false);//doesnt exist
        clean(player);
        sendScoreboard(player, League.BRONZE);//setup with dropdown later for different leagues..
    }

    private static void sendScoreboard(Player player, League league) {
        int[] childIdsNames = {444, 462, 470, 478, 486, 494, 502};//bad comps..
        int[] childIdsRating = {455, 463, 471, 479, 487, 495, 503};
        int[] childIdsKills = {456, 464, 472, 480, 488, 496, 504};
        int[] childIdsDeaths = {457, 465, 473, 481, 489, 497, 505};
        int[] childIdsKdr = {458, 466, 474, 482, 490, 498, 506};
        List<EloRating> leagueList = getLeagueList(league);
        if (leagueList.isEmpty()) {
            return;
        }
        int listSize = Math.min(leagueList.size(), 7);
        for (int index = 0; index < listSize; index++) {
            EloRating eloRating = leagueList.get(index);
            if (eloRating == null)
                continue;
            String playerName = eloRating.getName();
            int kills = eloRating.getKills();
            int deaths = eloRating.getDeaths();
            int rating = eloRating.getRating();
            player.getPacketSender().sendString(978, childIdsNames[index], playerName);
            player.getPacketSender().sendString(978, childIdsRating[index], String.valueOf(rating));
            player.getPacketSender().sendString(978, childIdsKills[index], String.valueOf(kills));
            player.getPacketSender().sendString(978, childIdsDeaths[index], String.valueOf(deaths));
            player.getPacketSender().sendString(978, childIdsKdr[index], NumberUtils.formatTwoPlaces(kills / Math.max(1D, deaths)));
        }
    }

    private static void clean(Player player) {
        int[] childIdsNames = {444, 462, 470, 478, 486, 494, 502};//bad comps..
        int[] childIdsRating = {455, 463, 471, 479, 487, 495, 503};
        int[] childIdsKills = {456, 464, 472, 480, 488, 496, 504};
        int[] childIdsDeaths = {457, 465, 473, 481, 489, 497, 505};
        int[] childIdsKdr = {458, 466, 474, 482, 490, 498, 506};
        for (int index = 0; index < childIdsNames.length; index++) {
            player.getPacketSender().sendString(978, childIdsNames[index], "");
            player.getPacketSender().sendString(978, childIdsRating[index], "");
            player.getPacketSender().sendString(978, childIdsKills[index], "");
            player.getPacketSender().sendString(978, childIdsDeaths[index], "");
            player.getPacketSender().sendString(978, childIdsKdr[index], "");
        }
    }

    public static void updatePlayerLeague(Player player) {
        rating_queue.add(new EloRating(player.name(), player.seasonKills, player.seasonDeaths, player.getPvpRating()));
    }

    static {
        load();
        /** Queue for ratings **/
        World.startEvent(e -> {
           while(true) {
               e.delay(1);
               if (rating_queue.isEmpty())
                   continue;
               EloRating rating = rating_queue.poll();
               if (rating != null) {
                   String name = rating.getName();
                   if (leaguePlayerMap.containsKey(name))
                       leaguePlayerMap.remove(name);
                   leaguePlayerMap.put(name, rating);
               }
           }
        });
    }

    public static void save() {
        if (leaguePlayerMap.isEmpty()) {
            System.err.println("not saving leagues! no players in list!");
            return;
        }
        CompletableFuture.runAsync(() -> {
            try {
                String json = JsonUtils.GSON_PRETTY.toJson(leaguePlayerMap);
                if (!SAVE_FOLDER.exists() && !SAVE_FOLDER.mkdirs())
                    throw new IOException("clan directory could not be created!");
                Files.write(new File(SAVE_FOLDER, "elo_ratings.json").toPath(), json.getBytes());
                log.info("Successfully saved elo_ratings.json");
            } catch (Exception e) {
                Server.logError(e.getMessage());
            }
        });
    }

    public static void load() {
        File file = new File(SAVE_FOLDER, "elo_ratings.json");
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                Type type = new TypeToken<Map<String, EloRating>>(){}.getType();
                leaguePlayerMap = new Gson().fromJson(fileReader, type);
            } catch (Exception e) {
                e.printStackTrace();
                new RuntimeException("Error loading elo_ratings.json");
                return;
            }
            log.info("Successfully loaded elo_ratings.json. "+leaguePlayerMap.size()+"x entries!");
        }
    }

    private static List<EloRating> getLeagueList(League league) {
        List<EloRating> collected = Lists.newArrayList();
        int minRating = league.getMinRating();
        int maxRating = league.getMaxRating();
        for (Map.Entry<String, EloRating> entries : leaguePlayerMap.entrySet()) {
            EloRating eloRating = entries.getValue();
            if (eloRating == null)
                continue;
            int rating = eloRating.getRating();
            if (rating >= minRating && rating <= maxRating) {
                collected.add(eloRating);
            }
        }
        collected.sort(Comparator.comparingInt(s -> s.rating));
        Collections.reverse(collected);
        return collected;
    }
}
