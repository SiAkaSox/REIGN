package io.ruin.model.activities.raids.theatre_of_blood.boss.maiden_of_sugadinti;

import io.ruin.model.map.Position;

import java.util.concurrent.TimeUnit;

public class BloodSplatterFloor {

    public Position position;

    public long duration;

    public BloodSplatterFloor(Position position) {
        this.position = position;
        this.duration = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5);
    }
}
