package io.ruin.utility;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;

import java.util.ArrayList;
import java.util.List;

public class Shape {

    private Position[] areas;

    public Shape() {

    }
    public Shape(Position northEast, Position southWest) {
        areas(new Position[] { northEast, southWest });
    }

    public Position[] areas() {
        return areas;
    }

    public Shape areas(Position[] areas) {
        this.areas = areas;
        return this;
    }

    public boolean inside(Position location) {

        if (areas()[0].getX() <= location.getX() || areas()[1].getX() >= location.getX())
            return false;

        if (areas()[0].getY() <= location.getY() || areas()[1].getY() >= location.getY())
            return false;

        return true;
    }

    public int getNumberOfPlayers() {
        return getPlayers().size();
    }

    public List<Player> getPlayers() {
        List<Player> count = new ArrayList<>();
        for (Player player : World.players) {
            if (inside(player.getPosition())) {
                count.add(player);
            }
        }
        return count;
    }
}
