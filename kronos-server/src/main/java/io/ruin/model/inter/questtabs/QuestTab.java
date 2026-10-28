package io.ruin.model.inter.questtabs;

import io.ruin.model.entity.player.Player;
import static io.ruin.model.activities.dailytasks.DailyTaskInter.sendUpdatesDailiesTab;
import static io.ruin.model.inter.questtabs.tabs.AccountManagementTab.sendUpdatesAccManageTab;
import static io.ruin.model.inter.questtabs.tabs.AchievementsTab.ACHIEVEMENTS_TAB;
import static io.ruin.model.inter.questtabs.tabs.AchievementsTab.sendUpdatesAchieveTab;
import static io.ruin.model.inter.questtabs.tabs.MainTab.MAIN_TAB;
import static io.ruin.model.inter.questtabs.tabs.MainTab.sendUpdatesMainTab;
import static io.ruin.model.inter.questtabs.tabs.ServerEventsTab.SERVER_EVENTS_TAB;
import static io.ruin.model.inter.questtabs.tabs.ServerEventsTab.sendUpdatesServerEventsTab;
import static io.ruin.model.inter.questtabs.tabs.ServerInfoTab.SERVER_INFO_TAB;
import static io.ruin.model.inter.questtabs.tabs.ServerInfoTab.sendUpdatesServerInfoTab;

public class QuestTab {

    public static void sendLoginUpdates(Player p) {
        sendUpdatesMainTab(p);
        sendUpdatesServerInfoTab(p);
        sendUpdatesAchieveTab(p);
        sendUpdatesDailiesTab(p);
        sendUpdatesServerEventsTab(p);
        sendUpdatesAccManageTab(p);
    }

    public static void switchMain(Player p) {
        swap(p, MAIN_TAB);
        sendUpdatesMainTab(p);
    }
    public static void switchServerTab(Player p) {
        swap(p, SERVER_INFO_TAB);
        sendUpdatesServerInfoTab(p);
    }
    public static void switchAchieveTab(Player p) {
        swap(p, ACHIEVEMENTS_TAB);
        sendUpdatesAchieveTab(p);
    }
    public static void switchDailyAchieveTab(Player p) {
        swap(p, 931);
        sendUpdatesDailiesTab(p);
    }
    public static void switchEventsTab(Player p) {
        swap(p, SERVER_EVENTS_TAB);
        sendUpdatesServerEventsTab(p);
    }

    public static void swap(Player p, int interfaceId) {
        if (p.isFixedScreen())
            p.getPacketSender().sendInterface(interfaceId, 548, 68, 1);
        else if (p.getGameFrameId() == 164)
            p.getPacketSender().sendInterface(interfaceId, 164, 70, 1);
        else
            p.getPacketSender().sendInterface(interfaceId, 161, 70, 1);
    }
}