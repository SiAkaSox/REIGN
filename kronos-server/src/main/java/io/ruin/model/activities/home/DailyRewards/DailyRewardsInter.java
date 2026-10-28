package io.ruin.model.activities.home.DailyRewards;

import io.ruin.Server;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.ArrayList;
import java.util.List;

import static io.ruin.Server.STARTUP_THREAD;
import static io.ruin.model.achievements.listeners.hard.HardAchieveInter.loadHardAchievements;
import static io.ruin.model.achievements.listeners.medium.MediumAchieveInter.loadMediumAchievements;

public class DailyRewardsInter {

    public static int INTER = 982;
    public static int DAY_25 = 6828;
    public static int DAY_50 = 30279;

    static {
        ObjectAction.register(26756, "Open", (player, object) -> {
            openInterface(player);
        });
        ObjectAction.register(26756, "Claim", (player, object) -> {
            DailyRewards.claimReward(player);
        });
        int startChildId = 281;
        int endChildId = 449;
        int increment = 7;
        InterfaceHandler.register(INTER, h -> {
            for (int childId = startChildId; childId <= endChildId; childId += increment) {
                h.actions[childId] = (SlotAction) (player, slot) -> DailyRewards.claimReward(player);
            }
        });
    }

    public static void openInterface(Player p) {
        List<DailyRewardsInter.ItemEntry> itemList = createItemList();

        for (int childId = 487, day = 0; childId <= 559; childId += 3, day++) {
            if (day <= itemList.size() && day == p.getLastClaimedDay()) {
                p.getPacketSender().setHidden(982, childId, false);
            } else {
                p.getPacketSender().setHidden(982, childId, true);
            }
        }
        p.openInterface(InterfaceType.MAIN, INTER);
        sendFeaturedItems(p);
        sendDailyRewards(p);
    }

    public static void sendFeaturedItems(Player p) {
        p.getPacketSender().sendItems(INTER, 467, 0, new Item(DAY_25));
        p.getPacketSender().sendItems(INTER, 478, 0, new Item(DAY_50));
        p.getPacketSender().sendString(INTER, 484, "Current Streak: <col=0fd60f>" + p.currentDailyStreak);
        p.getPacketSender().sendString(INTER, 485, "Highest Personal Streak: <col=0fd60f>" + p.highestDailyStreak);
        p.getPacketSender().sendString(INTER, 486, "Highest Server Streak: <col=0fd60f>" + DailyRewards.sendTopPlayer());
        p.getPacketSender().sendString(INTER, 459, "Pet Mystery Box");
        p.getPacketSender().sendString(INTER, 460, "Feeling lucky?");
        p.getPacketSender().sendString(INTER, 472, "$10 Bond");
        p.getPacketSender().sendString(INTER, 473, "Moneyyy! Spend wisely");
    }

    public static void sendDailyRewards(Player p) {
        List<ItemEntry> itemList = createItemList();

        int interfaceId = 982;
        int startChildIdItems = 282;
        int startChildIdStrings = 283;

        for (int i = 0; i < itemList.size(); i++) {
            int childIdItem = startChildIdItems + (i * 7);
            int childIdString = startChildIdStrings + (i * 7);

            sendItemToInterface(p, interfaceId, childIdItem, itemList.get(i));
            sendStringToInterface(p, interfaceId, childIdString, "Day " + (i + 1));
        }
    }

    static class ItemEntry {
        private final int childId;
        private final int day;
        private final int itemId;
        private final int amount;

        public ItemEntry(int childId, int day, int itemId, int amount) {
            this.childId = childId;
            this.day = day;
            this.itemId = itemId;
            this.amount = amount;
        }

        public int getChildId() {
            return childId;
        }

        public int getDay() {
            return day;
        }

        public int getItemId() {
            return itemId;
        }

        public int getAmount() {
            return amount;
        }
    }

    static List<ItemEntry> createItemList() {
        List<ItemEntry> itemList = new ArrayList<>();
        itemList.add(new ItemEntry(487, 1, 995, 25000));
        itemList.add(new ItemEntry(490, 2, 13442, 50));
        itemList.add(new ItemEntry(493, 3, 12696, 10));
        itemList.add(new ItemEntry(496, 4, 11230, 125));
        itemList.add(new ItemEntry(499, 5, 13307, 35));

        itemList.add(new ItemEntry(502, 6, 990, 3));
        itemList.add(new ItemEntry(505, 7, 2528, 1));
        itemList.add(new ItemEntry(508, 8, 6758, 1));
        itemList.add(new ItemEntry(511, 9, 995, 100000));
        itemList.add(new ItemEntry(514, 10, 24709, 100));

        itemList.add(new ItemEntry(517, 11, 13307, 75));
        itemList.add(new ItemEntry(520, 12, 12696, 20));
        itemList.add(new ItemEntry(523, 13, 607, 1));
        itemList.add(new ItemEntry(526, 14, 13442, 150));
        itemList.add(new ItemEntry(529, 15, 23951, 1));

        itemList.add(new ItemEntry(532, 16, 995, 125000));
        itemList.add(new ItemEntry(535, 17, 1464, 3));
        itemList.add(new ItemEntry(538, 18, 23071, 2));
        itemList.add(new ItemEntry(541, 19, 2528, 1));
        itemList.add(new ItemEntry(544, 20, 11230, 200));

        itemList.add(new ItemEntry(547, 21, 23951, 2));
        itemList.add(new ItemEntry(550, 22, 786, 5));
        itemList.add(new ItemEntry(553, 23, 19836, 2));
        itemList.add(new ItemEntry(556, 24, 995, 250000));
        itemList.add(new ItemEntry(559, 25, 6828, 1));
        return itemList;
    }

    private static void sendItemToInterface(Player p, int interfaceId, int childId, ItemEntry itemEntry) {
        p.getPacketSender().sendItems(interfaceId, childId, 0, new Item(itemEntry.getItemId(), itemEntry.getAmount()));
    }

    private static void sendStringToInterface(Player p, int interfaceId, int childId, String message) {
        p.getPacketSender().sendString(interfaceId, childId, "" + message);
    }

}