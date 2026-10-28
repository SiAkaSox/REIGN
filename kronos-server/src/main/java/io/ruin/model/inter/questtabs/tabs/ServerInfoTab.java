package io.ruin.model.inter.questtabs.tabs;

import io.ruin.Server;
import io.ruin.model.inter.newteleports.MonsterInter;
import io.ruin.utility.TimeUtils;
import io.ruin.model.World;
import io.ruin.model.activities.home.DailyRewards.DailyRewardsInter;
import io.ruin.model.activities.home.petperks.PetPerkInter;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.Title;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.handlers.DropViewer;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.skills.slayer.SlayerUnlock;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static io.ruin.Server.STARTUP_THREAD;
import static io.ruin.model.inter.questtabs.QuestTab.*;

public class ServerInfoTab {

    public static int SERVER_INFO_TAB = 857;

    static {

            InterfaceHandler.register(SERVER_INFO_TAB, h -> {
                h.actions[1] = (SlotAction) (p, slot) -> switchMain(p);
                h.actions[61] = (SlotAction) (p, slot) -> switchServerTab(p);
                h.actions[65] = (SlotAction) (p, slot) -> switchAchieveTab(p);
                h.actions[69] = (SlotAction) (p, slot) -> switchEventsTab(p);
                h.actions[208] = (SlotAction) (p, slot) -> PetPerkInter.openInterface(p);
                h.actions[209] = (SlotAction) (p, slot) -> DropViewer.open(p);
                h.actions[242] = (SlotAction) (p, slot) -> Title.openSelection(p, true);
                h.actions[243] = (SlotAction) (p, slot) -> p.getTeleporter().openInterface(p);
            });
    }

    private static String toTime(Player p) {
        return TimeUtils.fromMs(p.playTime * Server.tickMs(), false);
    }

    private static String formatTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " EST";
    }

    public static void sendUpdatesServerInfoTab(Player p) {
        int bmamount = p.getInventory().getAmount(13307) + p.getBank().getAmount(13307);
        LocalTime currentTime = LocalTime.now(ZoneId.of("America/New_York"));
        String formattedTime = formatTime(currentTime);

        p.getPacketSender().sendString(SERVER_INFO_TAB, 120, "Server Information");
        p.getPacketSender().sendString(SERVER_INFO_TAB, 243, "- Teleports");
        p.getPacketSender().sendString(SERVER_INFO_TAB, 246, ""+formattedTime);
        p.getPacketSender().sendString(SERVER_INFO_TAB, 244, ""+ World.players.count());
        p.getPacketSender().sendString(SERVER_INFO_TAB, 245, "Players in Wild: <col=0fd60f>"+ Wilderness.players.size());
        p.getPacketSender().sendString(SERVER_INFO_TAB, 209, "- View Drop Tables");
        p.getPacketSender().sendString(SERVER_INFO_TAB, 208, "- View Pet Perks");
        p.getPacketSender().sendString(SERVER_INFO_TAB, 193, "PvP Summary");
        p.getPacketSender().sendString(SERVER_INFO_TAB, 194, "Total Kills: <col=0fd60f>"+ Config.PVP_KILLS.get(p));
        p.getPacketSender().sendString(SERVER_INFO_TAB, 195, "Total Deaths: <col=0fd60f>"+Config.PVP_DEATHS.get(p));
        p.getPacketSender().sendString(SERVER_INFO_TAB, 196, "PvP Rating: <col=0fd60f>"+p.pvpRating);
        p.getPacketSender().sendString(SERVER_INFO_TAB, 197, "Tournament Wins: <col=0fd60f>"+p.tournamentWins);
        p.getPacketSender().sendString(SERVER_INFO_TAB, 198, "PvP Rating: <col=0fd60f>"+p.pvpRating);
        p.getPacketSender().sendString(SERVER_INFO_TAB, 199, "Blood Money: <col=0fd60f>"+bmamount);
        p.getPacketSender().sendString(SERVER_INFO_TAB, 200, "Highest Kill Streak: <col=0fd60f>"+p.highestKillSpree);
        p.getPacketSender().sendString(SERVER_INFO_TAB, 201, "Player Summary");
        p.getPacketSender().sendString(SERVER_INFO_TAB, 202, "Time Played: <col=0fd60f>"+toTime(p));
        p.getPacketSender().sendString(SERVER_INFO_TAB, 203, "Donated Amount: <col=0fd60f>$"+p.storeAmountSpent);
        String name = SlayerUnlock.taskName(p, Config.SLAYER_TASK.get(p));
        int amount = Config.SLAYER_TASK_AMOUNT.get(p);
        if (name == null || amount == 0) {
            p.getPacketSender().sendString(SERVER_INFO_TAB, 205, "Slayer Task: <col=0fd60f>None!");
        } else {
            p.getPacketSender().sendString(SERVER_INFO_TAB, 205, "Slayer Task: <col=e6994d>(<col=0fd60f>" + amount + "<col=e6994d>) <col=0fd60f>" + name);
        }
        p.updateRank();
    }

}