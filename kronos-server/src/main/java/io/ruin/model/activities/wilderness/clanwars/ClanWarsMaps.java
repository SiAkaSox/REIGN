package io.ruin.model.activities.wilderness.clanwars;

import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;

import java.util.List;


public enum ClanWarsMaps {

    WASTELAND("Wasteland", new Bounds(3264, 4992, 3327, 5119, 0), 60, new Position(1752, 5236, 0), new Position(2985, 3316, 0)),
    PLATEAU("Plateau", 13133, 60, new Position(2899, 4449, 0), new Position(1912, 4367, 0)),
    SYLVAN_GLADE("Sylvan Glade", 13646, 60, new Position(2280, 10022, 0), new Position(2280, 10016, 0)),
    FORSAKEN_QUARRY("Forsaken Quarry", 13647, 60, new Position(2271, 4680, 0),  new Position(3067, 10253, 0)),
    TURRETS("Turrets", new Bounds(3136, 4992, 3199, 5111, 0), 60, new Position(2376, 9452, 0), new Position(2379, 9452, 0)),
    CLAN_CUP_ARENA("Clan Cup Arena", 12621, 60, new Position(1304, 1290, 0), new Position(1310, 1274, 0)),
    SOGGY_SWAMP("Soggy Swamp", 13645, 60, new Position(2974, 4384, 2), new Position(2970, 4384, 2)),
    CLASSIC("Classic", new Bounds(3392, 4672, 3455, 4799, 0), 60, new Position(3507, 9494, 0), new Position(3509, 9496, 2)),
    LUMBRIDGE_CASTLE("Lumbridge Castle", 13643, 60, new Position(2864, 5354, 2), new Position(2862, 5354, 2)),
    FALADOR_PARK("Falador Park", new Bounds(3456, 4736, 3527, 4783, 0), 60, new Position(2925, 5331, 2), new Position(2925, 5333, 2)),
    GHASTLY_SWAMP("Ghastly Swamp", 13645, 60, new Position(2839, 5296, 2), new Position(2839, 5294, 2)),
    NORTHLEACH_QUELL("Northleach Quell", 13644, 60, new Position(2907, 5265, 0), new Position(2909, 5265, 0)),
    GRIDLOCK("Gridlock", new Bounds(3456, 4800, 3583, 4927, 0), 60, new Position(2907, 5265, 0), new Position(2909, 5265, 0)),
    ETHEREAL("Ethereal", 13390, 60, new Position(2907, 5265, 0), new Position(2909, 5265, 0))
    ;

    ClanWarsMaps(String name, Bounds bounds, int duration, Position entryPosition, Position exitPosition) {
        this.name = name;
        this.bounds = bounds;
        this.duration = duration * 100;
        this.entryPosition = entryPosition;
        this.exitPosition = exitPosition;
    }

    ClanWarsMaps(String name, int regionId, int duration, Position entryPosition, Position exitPosition) {
        this(name, Bounds.fromRegion(regionId), duration, entryPosition, exitPosition);
    }

    private String name;

    private Position exitPosition;

    private Position entryPosition;

    private int duration;

    private Bounds bounds;


    private List<ClanWarsMaps.Spawn> spawns;

    public Position getExitPosition() {
        return exitPosition;
    }

    public Position getEntryPosition() {
        return entryPosition;
    }

    public int getDuration() {
        return duration;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public String getName() {
        return name;
    }

    public List<ClanWarsMaps.Spawn> getSpawns() {
        return spawns;
    }

    static class Spawn {
        int id;
        int x;
        int y;
        int z;
        Direction direction;
        int walkRange;

        Spawn(int id, int x, int y, int z, Direction direction, int walkRange) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.z = z;
            this.direction = direction;
            this.walkRange = walkRange;
        }
    }

}