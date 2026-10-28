package io.ruin.model.activities.raids.theatre_of_blood;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.utility.Shape;

import java.util.HashMap;
import java.util.Map;

public class TheatrePartiesManager {

    private static Map<Player, TheatreParty> PARTIES = new HashMap<Player, TheatreParty>();

    public static Map<Player, TheatreParty> getParties() {
        return PARTIES;
    }

    public static TheatreParty createParty(Player player) {
        TheatreParty party = new TheatreParty(player);
        return register(player, party);
    }


    public static TheatreParty register(Player player, TheatreParty party) {
        PARTIES.put(player, party);
        return PARTIES.get(player);
    }

    public static void remove(Player player) {
        PARTIES.remove(player);
    }


    public static TheatreOfBlood getRaid(Position location) {
        for (TheatreParty party : TheatrePartiesManager.getParties().values()) {
            if (party == null || party.getTheatre() == null) {
                continue;
            }
            if (party.inside(location)) {
                return party.getTheatre();
            }
        }
        return null;
    }

    public static TheatreParty getParty(Player player) {
        for (TheatreParty party : TheatrePartiesManager.getParties().values()) {
            if (party == null) {
                continue;
            }
            if (party.members.contains(player))
                return party;
            if (party.getLeaderName().equalsIgnoreCase(player.name())) {
                if (party.getLeader() != player) {
                    TheatrePartiesManager.getParties().remove(party.getLeader());
                    party.setLeader(player);
                    TheatrePartiesManager.getParties().put(player, party);
                }
                return party;
            }
        }
        return null;
    }

    public static TheatreParty getMyParty(Player player) {
        try {
            for (TheatreParty party : TheatrePartiesManager.getParties().values()) {
                if (party == null) {
                    continue;
                }
                if (party.getLeaderName().equalsIgnoreCase(player.name())) {
                    if (party.getLeader() != player) {
                        TheatrePartiesManager.getParties().remove(party.getLeader());
                        for (Player p : party.members) {
                            if (p.name().equalsIgnoreCase(player.name())) {
                                party.members.remove(p);
                            }
                        }
                        party.setLeader(player);
                        TheatrePartiesManager.getParties().put(player, party);
                    }
                    return party;
                }
            }
        } catch(Exception e) {

        }
        return null;
    }

}
