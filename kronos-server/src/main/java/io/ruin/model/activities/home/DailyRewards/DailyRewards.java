package io.ruin.model.activities.home.DailyRewards;

import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.ruin.model.activities.home.DailyRewards.DailyRewardsInter.createItemList;

public class DailyRewards {

    private static final Map<Integer, LocalDate> firstLoginDates = new HashMap<>();

    public static void ensureFirstLoginDateIsSet(Player p) {
        if (!firstLoginDates.containsKey(p.getUserId())) {
            firstLoginDates.put(p.getUserId(), LocalDate.now());
            System.out.println("First Login Date For: " + p.name() + " --- " + LocalDate.now());
        }
    }

    public static void claimReward(Player p) {
        ensureFirstLoginDateIsSet(p);
        LocalDate firstLoginDate = getFirstLoginDate(p);
        int currentDay = getCurrentDay(firstLoginDate);
        List<DailyRewardsInter.ItemEntry> itemList = createItemList();
        if (currentDay > itemList.size()) {
            p.sendMessage(Color.RED.wrap("You have already claimed today's reward!"));
        } else {
            if (currentDay == p.getLastClaimedDay()) {
                p.sendMessage(Color.RED.wrap("You have already claimed today's reward!"));
                return;
            }

            DailyRewardsInter.ItemEntry itemEntry = itemList.get(currentDay);
            int itemId = itemEntry.getItemId();
            int amount = itemEntry.getAmount();
            int childId = itemEntry.getChildId();
            String formattedAmount = NumberFormat.getNumberInstance().format(amount);
            p.getInventory().addOrDrop(itemId, amount);
            p.getPacketSender().setHidden(982, childId, false);
            p.sendMessage("You received your daily reward: " + Color.RED.wrap("x" + formattedAmount + " " + ItemDef.get(itemId).name));

            if (currentDay - p.getLastClaimedDay() == 1) {
                p.setCurrentDailyStreak(p.getCurrentDailyStreak());

                if (p.getCurrentDailyStreak() > p.getHighestDailyStreak()) {
                    p.setHighestDailyStreak(p.getCurrentDailyStreak());
                }
            } else {
                p.setCurrentDailyStreak(1);
            }
            p.setLastClaimedDay(currentDay);
        }
    }

    private static LocalDate getFirstLoginDate(Player p) {
        return firstLoginDates.getOrDefault(p.getUserId(), LocalDate.now());
    }

    private static int getCurrentDay(LocalDate firstLoginDate) {
        return firstLoginDate.until(LocalDate.now()).getDays();
    }

    public static Player getPlayerWithHighestStreak() {
        Player playerWithHighestStreak = null;
        int highestStreak = 0;

        for (Player player : World.players) {
            if (player != null && player.getHighestDailyStreak() > highestStreak) {
                highestStreak = player.getHighestDailyStreak();
                playerWithHighestStreak = player;
            }
        }
        return playerWithHighestStreak;
    }

    public static String sendTopPlayer() {
        Player playerWithHighestStreak = getPlayerWithHighestStreak();
        String msg = "";

        if (playerWithHighestStreak != null) {
            msg = (playerWithHighestStreak.name() + " (" + playerWithHighestStreak.getHighestDailyStreak()+")");
        }
        return ""+msg;
    }
}