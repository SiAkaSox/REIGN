package io.ruin.model.activities.wilderness.pvp.rated;

import io.ruin.Server;
import io.ruin.model.activities.raids.xeric.party.Party;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class RatedArena2v2 {

    public static List<Player> ADVERTISED_PARTIES = new ArrayList<>(50);

    private static void displayRecruitingBoard(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.RAIDING_RECRUITING_BOARD);
        for (int i = 0; i < 40; i++)
            if (i < ADVERTISED_PARTIES.size() && ADVERTISED_PARTIES.get(i) != null) {
                Player partyHost = ADVERTISED_PARTIES.get(i);
                String partyInformation = "<col=ffffff>" + partyHost.name() + "</col>" + "|" +
                        (partyHost.raidsParty.getMembers().size() + 1) + "|" +
                        Config.RAIDS_PREFERRED_PARTY_SIZE.get(partyHost) + "|" +
                        Config.RAIDS_PREFERRED_COMBAT_LEVEL.get(partyHost) + "|" +
                        Config.RAIDS_PREFERRED_SKILL_TOTAL.get(partyHost) + "|" +
                        (Server.currentTick() - partyHost.advertisementStartTick) + "|";
                player.getPacketSender().sendClientScript(1566, "is", i, partyInformation);
            } else {
                player.getPacketSender().sendClientScript(1566, "is", i, "");
            }
    }

    public static void createParty(Player player, boolean displayInterface) {

    }

    public static void invite(Player player, Player target) {

    }

    public static void joinParty( Player player, Party2v2 party) {

    }

    public static void disbandParty(Player player) {

    }

    public static void leaveParty(Player player) {

    }

    private static void advertiseParty(Player player, boolean delist) {

    }

    private static void displayParty(Player player, Party party) {

    }

    static {
        //ObjectAction.register(31622, "Enter", (player, obj) -> displayRecruitingBoard(player));
        InterfaceHandler.register(Interface.RAIDING_RECRUITING_BOARD, h -> {
            h.actions[3] = (DefaultAction) (player, child, option, slot, itemId) -> {
                if (slot == 0)
                    displayRecruitingBoard(player);
                if (slot == 1)
                    if (Config.RAIDS_PARTY.get(player) == -1)
                        createParty(player, true);
                    else
                        displayParty(player, player.raidsParty);
            };
            h.actions[14] = (DefaultAction) (player, child, option, slot, itemId) -> {
                if (slot < ADVERTISED_PARTIES.size() && ADVERTISED_PARTIES.get(slot) != null) {
                    Player partyLeader = ADVERTISED_PARTIES.get(slot);
                    displayParty(player, partyLeader.raidsParty);
                }
            };
        });
        InterfaceHandler.register(Interface.RAIDING_PARTY, h -> {
            h.actions[3] = (DefaultAction) (player, child, option, slot, itemId) -> {
                if (slot == 0)
                    if (player.raidsParty == null)
                        joinParty(player, player.viewingParty2v2);
                    else
                        leaveParty(player);
                if (slot == 1)
                    advertiseParty(player, player.advertisingParty);
                if (slot == 2)
                    disbandParty(player);
                if (slot == 3) {
                    displayParty(player, player.raidsParty);
                }
                if (slot == 4)
                    displayRecruitingBoard(player);
                if (slot == 5)
                    setPreferredSetting(player, "party size", Config.RAIDS_PREFERRED_PARTY_SIZE, 1, 100);
                if (slot == 6)
                    setPreferredSetting(player, "combat level", Config.RAIDS_PREFERRED_COMBAT_LEVEL, 4, 126);
                if (slot == 7)
                    setPreferredSetting(player, "skill total", Config.RAIDS_PREFERRED_SKILL_TOTAL, 33, 2277);
                if (slot == 8)
                    player.sendMessage("Challenge mode is not currently available.");
            };
        });
    }

    private static void copyPartyPreferences(Player player, Party party) {
        Config.RAIDS_PREFERRED_PARTY_SIZE.set(player, Config.RAIDS_PREFERRED_PARTY_SIZE.get(party.getLeader()));
        Config.RAIDS_PREFERRED_COMBAT_LEVEL.set(player, Config.RAIDS_PREFERRED_COMBAT_LEVEL.get(party.getLeader()));
        Config.RAIDS_PREFERRED_SKILL_TOTAL.set(player, Config.RAIDS_PREFERRED_SKILL_TOTAL.get(party.getLeader()));
    }

    public static void resetPartyPreferences(Player player) {
        Config.RAIDS_PREFERRED_PARTY_SIZE.set(player, 0);
        Config.RAIDS_PREFERRED_COMBAT_LEVEL.set(player, 0);
        Config.RAIDS_PREFERRED_SKILL_TOTAL.set(player, 0);
    }

    private static void setPreferredSetting(Player player, String setting, Config config, int minValue, int maxValue) {
        player.closeInterface(InterfaceType.MAIN);
        player.integerInput("Set a preferred " + setting + " (or 0 to clear it):", amt -> {
            config.set(player, minValue > amt ? 0 : Math.min(maxValue, amt));
            player.raidsParty.getMembers().forEach(member -> config.set(member, minValue > amt ? 0 : Math.min(maxValue, amt)));
            displayParty(player, player.raidsParty);
        });
    }
}