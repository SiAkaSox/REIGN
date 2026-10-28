package io.ruin.model.activities.wilderness.pvp.rated;

import io.ruin.Server;
import io.ruin.model.activities.raids.xeric.ChambersOfXeric;
import io.ruin.model.activities.raids.xeric.party.Party;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemPlayerAction;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static io.ruin.model.stat.StatType.CLIENT_ORDER;

public class Party2v2 {

    public static final int WAITING = 2;
    public static final int EXPLORING_UPPER_LEVEL = 258;
    public static final int REACHED_LOWER_LEVEL = 514;
    public static final int REACHED_BOTTOM_LEVEL = 770;
    public static final int GET_OUT = 1026;

    public static List<Player> ADVERTISED_2V2_PARTIES = new ArrayList<>(50);

    private Player leader;
    private LinkedList<Player> members;
    private ChambersOfXeric raid;
    private int points;

    public Party2v2(Player leader) {
        this.leader = leader;
        members = new LinkedList<>();
        members.add(leader);
    }

    public Player getLeader() { return leader; }

    public void setLeader(Player leader) { this.leader = leader; }

    public List<Player> getMembers() { return members; }

    public int getSize() { return members.size(); }

    public boolean addMember(Player player) { return members.add(player); }

    public void removeMember(Player player) {
        members.remove(player);
        if (members.size() > 0 && player == leader) {
            leader = members.getFirst();
        }
    }

    public ChambersOfXeric getRaid() { return raid; }

    public void setRaid(ChambersOfXeric raid) { this.raid = raid; }

    public static void createParty(Player player, boolean displayInterface) {
        Config.TWOVTWO_RATED_PARTY.set(player, player.getIndex());
        player.twovtwoRatedParty = new Party(player);
        resetPartyPreferences(player);
        player.sendMessage("Party created here");
        //updateRecruitingBoard(player);
        //player.getPacketSender().sendString(878, 15, ""+partyHost.getIndex()+"'s party - "+(Server.currentTick() - partyHost.twovtwoadvertisementStartTick));
    }
    public static void resetPartyPreferences(Player player) {
        Config.TWOVTWO_RATED_PREFERRED_PARTY_SIZE.set(player, 0);
        Config.TWOVTWO_RATED_PREFERRED_COMBAT_LEVEL.set(player, 0);
        Config.TWOVTWO_RATED_PREFERRED_SKILL_TOTAL.set(player, 0);
    }
    private static void displayParty(Player player, Party party) {
        if (party == null) {
            if (Config.TWOVTWO_RATED_PARTY.get(player) != -1) {
                Config.TWOVTWO_RATED_PARTY.set(player, -1);
            }
            return;
        }
        /* open the interface and fire the scripts! :^ ) */
        player.openInterface(InterfaceType.MAIN, 879);
        /* build the party members statistics */
        StringBuilder leaderInformation = new StringBuilder();
        /* team leader information */
        Player partyLeader = party.getLeader();
        leaderInformation.append("<col=ffffff>").append(partyLeader.name()).append("</col>").append("|");
        leaderInformation.append(partyLeader.getCombat().getLevel()).append("|");
        leaderInformation.append(partyLeader.getStats().totalLevel).append("|");
        for (StatType stat : CLIENT_ORDER)
            leaderInformation.append(partyLeader.getStats().get(stat).fixedLevel).append("|");
        player.getPacketSender().sendClientScript(1517, "is", 0, leaderInformation.toString());
        /* party members information */
        if (party.getMembers().size() != 0) {
            for (int i = 0; i < party.getMembers().size(); i++) {
                if (party.getMembers().get(i) == party.getLeader())
                    continue;
                StringBuilder memberInformation = new StringBuilder();
                memberInformation.append("<col=9f9f9f>").append(party.getMembers().get(i).name()).append("</col>").append("|");
                memberInformation.append(party.getMembers().get(i).getCombat().getLevel()).append("|");
                memberInformation.append(party.getMembers().get(i).getStats().totalLevel).append("|");
                for (StatType stat : CLIENT_ORDER)
                    memberInformation.append(party.getMembers().get(i).getStats().get(stat).fixedLevel).append("|");
                player.getPacketSender().sendClientScript(1517, "is", i + 1, memberInformation.toString());
            }
        }
        player.getPacketSender().sendClientScript(1524, "siii",
                "2v2 Team of " + partyLeader.name() + " (" + (party.getMembers().size()) + ")",
                player == partyLeader ? 2 : player.twovtwoRatedParty == party ? 1 : 0,
                player.twovtwoadvertisingParty ? 1 : 0,
                player.partyAdvertisementsRemaining);
        /* if we're not on a party, set the party we're viewing */
        if (player.twovtwoRatedParty == null)
            player.viewingParty = party;
    }

    public static void joinParty(Player player, Party party) {
        /*if (!ADVERTISED_2V2_PARTIES.contains(party.getLeader())) {
            player.dialogue(new MessageDialogue("This party is no longer recruiting."));
            return;
        }*/
        player.closeInterface(InterfaceType.MAIN);
        player.dialogue(new MessageDialogue("Requesting..").hideContinue());
        party.getLeader().dialogue(
                new OptionsDialogue(player.name() + " wishes to join your party.",
                new Option("Accept", () -> {
                    Player leader = party.getLeader();
                    if (player.twovtwoRatedParty != null) {
                        leader.dialogue(new MessageDialogue(player.name() + " is already in a party."));
                        return;
                    }
                    party.addMember(player);
                    player.twovtwoRatedParty = party;
                    copyPartyPreferences(player, party);
                    if (leader.isVisibleInterface(879))
                        displayParty(leader, party);
                    displayParty(player, player.twovtwoRatedParty);
                    Config.TWOVTWO_RATED_PARTY.set(player, leader.getIndex());
                    player.sendMessage("You've joined " + leader.name() + "'s 2v2 rated team.");
                    party.getLeader().sendMessage(player.name() + " has joined your 2v2 rated team.");
                }),
                new Option("Decline", () -> {
                    player.dialogue(new MessageDialogue(party.getLeader().name() + " has declined your request to join their 2v2 team."));
                    party.getLeader().sendMessage("You decline " + player.name() + "'s request to join your 2v2 team.");
                })
        ));
    }

    private static void updateRecruitingBoard(Player player) {
        player.openInterface(InterfaceType.MAIN, 878);
        player.getPacketSender().sendString(878, 15, ""+player.twovtwoRatedParty.getLeader().name()+"'s party - "+(Server.currentTick() - player.twovtwoRatedParty.getLeader().twovtwoadvertisementStartTick));
        /*for (int i = 0; i < 40; i++)
            if (i < ADVERTISED_2V2_PARTIES.size() && ADVERTISED_2V2_PARTIES.get(i) != null) {
                Player partyHost = ADVERTISED_2V2_PARTIES.get(i);
                player.getPacketSender().sendString(878, 15, ""+player.twovtwoRatedParty.getLeader().getName()+"'s party - "+(Server.currentTick() - player.twovtwoRatedParty.getLeader().twovtwoadvertisementStartTick));
            }*/
    }

    private static void displayRecruitingBoard(Player player) {
        player.openInterface(InterfaceType.MAIN, 878);
//        player.getPacketSender().sendString(878, 15, ""+player.twovtwoRatedParty.getLeader().getName()+"'s party");
        for (int i = 0; i < 40; i++)
            if (i < ADVERTISED_2V2_PARTIES.size() && ADVERTISED_2V2_PARTIES.get(i) != null) {
                Player partyHost = ADVERTISED_2V2_PARTIES.get(i);
                String partyInformation = "<col=ffffff>" + partyHost.name() + "</col>" + "|" +
                        (partyHost.twovtwoRatedParty.getMembers().size() + 1) + "|" +
                        Config.TWOVTWO_RATED_PREFERRED_PARTY_SIZE.get(partyHost) + "|" +
                        Config.TWOVTWO_RATED_PREFERRED_COMBAT_LEVEL.get(partyHost) + "|" +
                        Config.TWOVTWO_RATED_PREFERRED_SKILL_TOTAL.get(partyHost) + "|" +
                        (Server.currentTick() - partyHost.twovtwoadvertisementStartTick) + "|";
                //player.getPacketSender().sendString(878, 15, ""+player.twovtwoRatedParty.getLeader().getName()+"'s party");
                player.getPacketSender().sendString(878, 15, ""+partyHost.getIndex()+"'s party - "+(Server.currentTick() - partyHost.twovtwoadvertisementStartTick));
                //player.getPacketSender().sendString(878, 15, ""+(Server.currentTick() - partyHost.twovtwoadvertisementStartTick));
            }
    }

    public static void invitePlayer(Player player) {
        if (player.twovtwoRatedParty == null)
            joinParty(player, player.twovtwoviewingParty);
        /*int boxId = 6199;
        player.stringInput("Enter player's display name:", name -> {
            name = name.replaceAll("[^a-zA-Z0-9\\s]", "");
            name = name.substring(0, Math.min(name.length(), 12));
            if (name.isEmpty()) {
                player.retryStringInput("Invalid username, try again:");
                return;
            }
            if (name.equalsIgnoreCase(player.getName())) {
                player.retryStringInput("Cannot invite yourself, try again:");
                return;
            }
            Player target = World.getPlayer(name);
            if (target == null) {
                player.retryStringInput("Player cannot be found, try again:");
                return;
            }

        });*/
    }

    static {
        InterfaceHandler.register(878, h -> {
            h.actions[56] = (SlotAction) (player, slot)-> createParty(player, false);
        });
        //ObjectAction.register(28802, 1, (player, obj) -> displayRecruitingBoard(player));
        ItemPlayerAction.register(5021, (player, item, other) -> {
                    invitePlayer(player);

                });
       /* InterfaceHandler.register(878, h -> {
            h.actions[3] = (DefaultAction) (player, option, slot, itemId) -> {
                if (slot == 0)
                    displayRecruitingBoard(player);
                if (slot == 1)
                    if (Config.TWOVTWO_RATED_PARTY.get(player) == -1)
                        createParty(player, true);
                    else
                        displayParty(player, player.twovtwoRatedParty);
            };
            h.actions[14] = (DefaultAction) (player, option, slot, itemId) -> {
                if (slot < ADVERTISED_2V2_PARTIES.size() && ADVERTISED_2V2_PARTIES.get(slot) != null) {
                    Player partyLeader = ADVERTISED_2V2_PARTIES.get(slot);
                    displayParty(player, partyLeader.twovtwoRatedParty);
                }
            };
        });*/
        InterfaceHandler.register(879, h -> {
            h.actions[3] = (DefaultAction) (player, child, option, slot, itemId) -> {
                if (slot == 0)
                    if (player.twovtwoRatedParty == null)
                        joinParty(player, player.twovtwoviewingParty);
                    else
                        leaveParty(player);
                if (slot == 1)
                    advertiseParty(player, player.twovtwoadvertisingParty);
                if (slot == 2)
                    disbandParty(player);
                if (slot == 3) {
                    displayParty(player, player.twovtwoRatedParty);
                }
                if (slot == 4)
                    displayRecruitingBoard(player);
                if (slot == 5)
                    setPreferredSetting(player, "party size", Config.TWOVTWO_RATED_PREFERRED_PARTY_SIZE, 1, 100);
                if (slot == 6)
                    setPreferredSetting(player, "combat level", Config.TWOVTWO_RATED_PREFERRED_COMBAT_LEVEL, 4, 126);
                if (slot == 7)
                    setPreferredSetting(player, "skill total", Config.TWOVTWO_RATED_PREFERRED_SKILL_TOTAL, 33, 2277);
                if (slot == 8)
                    player.sendMessage("Challenge mode is not currently available.");
            };
        });
    }

    private static void advertiseParty(Player player, boolean delist) {
        if (delist)
            ADVERTISED_2V2_PARTIES.remove(player);
        else
            ADVERTISED_2V2_PARTIES.add(player);
        player.twovtwoadvertisingParty = !player.twovtwoadvertisingParty;
        player.twovtwoadvertisementStartTick = delist ? 0L : Server.currentTick();
        displayParty(player, player.twovtwoRatedParty);
    }

    private static void setPreferredSetting(Player player, String setting, Config config, int minValue, int maxValue) {
        player.closeInterface(InterfaceType.MAIN);
        player.integerInput("Set a preferred " + setting + " (or 0 to clear it):", amt -> {
            config.set(player, minValue > amt ? 0 : Math.min(maxValue, amt));
            player.twovtwoRatedParty.getMembers().forEach(member -> config.set(member, minValue > amt ? 0 : Math.min(maxValue, amt)));
            displayParty(player, player.twovtwoRatedParty);
        });
    }

    public static void disbandParty(Player player) {
        Config.TWOVTWO_RATED_PARTY.set(player, -1);
        player.dialogue(
                new MessageDialogue("Your party has been disbanded."),
                new ActionDialogue(() -> {
                    if (player.getAbsX() == 1246 && player.getAbsY() == 3562)
                        displayRecruitingBoard(player);
                }));

        /* reset the player's party preferences */
        resetPartyPreferences(player);

        /* terminate the party */
        if (player.twovtwoRatedParty.getLeader() == player) {

            /* remove all the players from the party */
            player.twovtwoRatedParty.getMembers().forEach(member -> {
                player.twovtwoRatedParty.removeMember(member);
                member.sendMessage("<col=ef20ff>" + player.name() + " has debanded the party.");
                member.twovtwoRatedParty = null;
                Config.TWOVTWO_RATED_PARTY.set(member, -1);
                if (member.isVisibleInterface(879))
                    displayRecruitingBoard(member);
            });

            /* if the party is advertised, remove it from the list */
            player.twovtwoadvertisingParty = false;
            player.twovtwoadvertisementStartTick = 0L;
            player.twovtwoRatedParty = null;
            if (ADVERTISED_2V2_PARTIES.contains(player))
                ADVERTISED_2V2_PARTIES.remove(player);
        }
    }

    private static void copyPartyPreferences(Player player, Party party) {
        Config.TWOVTWO_RATED_PREFERRED_PARTY_SIZE.set(player, Config.TWOVTWO_RATED_PREFERRED_PARTY_SIZE.get(party.getLeader()));
        Config.TWOVTWO_RATED_PREFERRED_COMBAT_LEVEL.set(player, Config.TWOVTWO_RATED_PREFERRED_COMBAT_LEVEL.get(party.getLeader()));
        Config.TWOVTWO_RATED_PREFERRED_SKILL_TOTAL.set(player, Config.TWOVTWO_RATED_PREFERRED_SKILL_TOTAL.get(party.getLeader()));
    }

    public static void leaveParty(Player player) {
        Player partyLeader = player.twovtwoRatedParty.getLeader();
        if (partyLeader == player) {
            disbandParty(player);
            return;
        }
        player.sendMessage("<col=ef20ff> You leave " + player.twovtwoRatedParty.getLeader().name() + "'s party.");
        player.twovtwoRatedParty.removeMember(player);
        player.twovtwoRatedParty = null;
        resetPartyPreferences(player);
        Config.TWOVTWO_RATED_PARTY.set(player, -1);
        if (player.isVisibleInterface(879))
            displayRecruitingBoard(player);
        if (partyLeader.isVisibleInterface(879))
            displayParty(partyLeader, partyLeader.twovtwoRatedParty);
    }

    public static void sendRaidJournal(Player player, Party party) {

        player.getPacketSender().sendClientScript(1547, "i", party.getMembers().size());
        /* party members information */
        int index = 0;
        if (party.getMembers().size() != 0) {
            for (Player member : party.getMembers()) {
                String memberInformation = "<col=" + (member == party.getLeader() ? "ffffff" : "9f9f9f") + ">" + member.name() + "</col>" + "|" +
                        member.getCombat().getLevel() + "|" +
                        member.getStats().totalLevel;
                player.getPacketSender().sendClientScript(1548, "is", index++, memberInformation);
            }
        }
        player.getPacketSender().sendClientScript(1553, "i", index - 1);
    }

    public static void updatePartyStage(Player player, int stage) {
        if(player.twovtwoRatedParty == null)
            return;
        if(player.twovtwoRatedParty.getSize() > 1)
            stage += player.twovtwoRatedParty.getMembers().size() * 2;
        Player partyLeader = player.twovtwoRatedParty.getLeader();
        if (partyLeader == player)
            stage += 1;
        Config.RAIDS_STAGE.set(player, stage);
    }

    public void forPlayers(Consumer<Player> action) {
        members.forEach(action);
    }

    /*public void refreshAll() {
        forPlayers(p -> sendRaidJournal(p, this));
    }*/

    public void destroy() {
        raid = null;
        members = null;
        leader = null;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPartyCombatLevel() {
        //used to use average for this but was changed in one of the raid updates to just use highest combat instead
        return members.stream().mapToInt(p -> p.getCombat().getLevel()).max().orElse(126);
    }

}