package io.ruin.model.activities.raids.theatre_of_blood;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.utility.Shape;

import java.util.*;

public class TheatreParty {

    private Player leader;

    public List<Player> members = new ArrayList<Player>(), applicants = new ArrayList<Player>();
    public TheatreOfBlood theatre;
    private Map<String, Integer> points = new HashMap<>();
    private Map<String, Integer> rewardPoints = new HashMap<>();
    public int[] deaths;
    public long age;

    public TheatreOfBlood getTheatre() {
        return theatre;
    }


    public void setTheatre(TheatreOfBlood theatre) {
        this.theatre = theatre;
        resetPoints();
    }


    public TheatreParty(Player leader) {
        this.leader = leader;
        members = new LinkedList<>();
        members.add(leader);
        points = new HashMap<>();
        rewardPoints = new HashMap<>();
        this.age = System.currentTimeMillis();
    }

    public void resetPoints() {
        points = new HashMap<>();
        rewardPoints = new HashMap<>();
    }

    public int[] getDeaths() {
        return deaths;
    }

    public void setDeaths(int[] deaths) {
        this.deaths = deaths;
    }

    public Player getLeader() {
        return leader;
    }
    public String getLeaderName() {
        return leader.name();
    }

    public void setLeader(Player player) {
        this.leader = player;
    }

    public int getPoints(Player player) {
        int current = points.get(player.name().toLowerCase()) == null ? 0 : points.get(player.name().toLowerCase());
        return current;
    }

    public void addPoints(Player player, int i) {
        int current = points.get(player.name().toLowerCase()) == null ? 0 : points.get(player.name().toLowerCase());
        points.put(player.name().toLowerCase(), current + i);
    }

    public void resetPoints(Player player) {
        points.put(player.name().toLowerCase(), 0);
    }

    public void removePoints(Player player, int i) {
        int current = points.get(player.name().toLowerCase()) == null ? 0 : points.get(player.name().toLowerCase());
        points.put(player.name().toLowerCase(), current - i);
    }

    public int getRewardPoints(Player player) {
        int current = rewardPoints.get(player.name().toLowerCase()) == null ? 0 : rewardPoints.get(player.name().toLowerCase());
        return current;
    }

    public void addRewardPoints(Player player, int i) {
        int current = rewardPoints.get(player.name().toLowerCase()) == null ? 0 : rewardPoints.get(player.name().toLowerCase());
        rewardPoints.put(player.name().toLowerCase(), current + i);
    }

    public void removeRewardPoints(Player player, int i) {
        int current = rewardPoints.get(player.name().toLowerCase()) == null ? 0 : rewardPoints.get(player.name().toLowerCase());
        rewardPoints.put(player.name().toLowerCase(), current - i);
    }

    public List<Player> getApplicants() {
        return applicants;
    }


    public void setApplicants(List<Player> applicants) {
        this.applicants = applicants;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return (int) (System.currentTimeMillis() - this.age) / 1000;
    }

    public boolean inside(Position location) {
        for (Position p : theatre.getBase()) {
            Shape shape = new Shape(p.translated(128,64,0), p.copy());
            if (shape.inside(location)) {
                return true;
            }
        }
        return false;
    }
}
