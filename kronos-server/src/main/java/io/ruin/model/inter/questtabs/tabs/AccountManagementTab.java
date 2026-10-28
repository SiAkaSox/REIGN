package io.ruin.model.inter.questtabs.tabs;

import io.ruin.model.inter.presets.PresetManager;
import io.ruin.utility.NumberUtils;
import io.ruin.model.activities.wilderness.Hotspot;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.QuickGearInter;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.utils.Config;

public class AccountManagementTab {

    public static int ACCOUNT_MANAGEMENT = 920;

    static {
        InterfaceHandler.register(ACCOUNT_MANAGEMENT, h -> {
            h.actions[134] = (SlotAction) (player, slot) ->
                    player.sendMessage("Arenas aren't active until Week 2!");
            //ArenasInter.openInterface(player);
            h.actions[135] = (SlotAction) (player, slot) -> QuickGearInter.openInterface(player);
            h.actions[163] = (SlotAction) (player, slot) -> PresetManager.openInterface(player);
        });
    }

    private static String toRatio(Player p) {
        return NumberUtils.formatTwoPlaces(Config.PVP_KILLS.get(p) / Math.max(1D, Config.PVP_DEATHS.get(p)));
    }

    public static void sendUpdatesAccManageTab(Player p) {
        int bmamount = p.getInventory().getAmount(13307) + p.getBank().getAmount(13307);
        int kills = Config.PVP_KILLS.get(p);
        int deaths = Config.PVP_DEATHS.get(p);
        p.getPacketSender().sendString(ACCOUNT_MANAGEMENT, 189, "Kills: <col=0fd60f>" + kills);
        p.getPacketSender().sendString(ACCOUNT_MANAGEMENT, 190, "Deaths: <col=0fd60f>" + deaths);
        p.getPacketSender().sendString(ACCOUNT_MANAGEMENT, 191, "KDR: <col=0fd60f>" + toRatio(p));
        p.getPacketSender().sendString(ACCOUNT_MANAGEMENT, 192, "Streak: <col=0fd60f>" + p.currentKillSpree);
        p.getPacketSender().sendString(ACCOUNT_MANAGEMENT, 193, "Blood Money: <col=0fd60f>" + bmamount);
        p.getPacketSender().sendString(ACCOUNT_MANAGEMENT, 197, "Pvp Rating: <col=0fd60f>" + p.pvpRating);
        p.getPacketSender().sendString(ACCOUNT_MANAGEMENT, 200, "Hotspot: <col=0fd60f>" + Hotspot.ACTIVE.name);
    }

}