package io.ruin.model.inter.handlers;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.entity.shared.listeners.LoginListener;

public class TabAccountManagement {

    private static final int OSPVP_CREDITS = 13190;
    private static final String VOTE_URL = World.type.getWebsiteUrl() + "/voting";
    private static final String HISCORES = World.type.getWebsiteUrl() + "/hiscores";

    static {
        /**
         * Send the interface when our player logs in
         */
        LoginListener.register(player -> player.getPacketSender().sendAccountManagement(getDonatorRank(player), getUsername(player), player.getUnreadPMs()));

        /**
         * Interface buttons
         */
        /*InterfaceHandler.register(Interface.ACCOUNT_MANAGEMENT, h -> {
            h.actions[61] = (SlotAction) (player, slot)-> TogglesInter.loadAll(player);
        });*/
    }

    public static String getUsername(Player player) {
        PlayerGroup clientGroup = player.getClientGroup();
        if (clientGroup == null)
            return player.name();
        return (clientGroup.clientImgId != -1 ? clientGroup.tag() + " " : "") + player.name();
    }

    public static String getDonatorRank(Player player) {
        if (player.isGroup(PlayerGroup.ZENYTE)) {
            return PlayerGroup.ZENYTE.tag() + " Godlike";
        } else if (player.isGroup(PlayerGroup.PLATINUM_DONATOR)) {
            return PlayerGroup.ONYX.tag() + " Platinum Donator";
        } else if (player.isGroup(PlayerGroup.RESPECTED_DONATOR)) {
            return PlayerGroup.RESPECTED_DONATOR.tag() + " Respected Donator";
        } else if (player.isGroup(PlayerGroup.LEGENDARY_DONATOR)) {
            return PlayerGroup.LEGENDARY_DONATOR.tag() + " Legendary Donator";
        } else if (player.isGroup(PlayerGroup.SUPER_DONATOR)) {
            return PlayerGroup.SUPER_DONATOR.tag() + " Super Donator";
        } else if (player.isGroup(PlayerGroup.DONATOR)) {
            return PlayerGroup.DONATOR.tag() + " Donator";
        } else {
            return "";
        }
    }

}
