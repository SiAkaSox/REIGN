package io.ruin.model.activities.mimic;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.map.Position;

public class ThirdAgeMonster extends NPC {

    public static final int WARRIOR = 8635, RANGER = 8636, MAGE = 8637;

    public ThirdAgeMonster(int id, Position position) {
        super(id);
        this.position = position;
        this.setIgnoreMulti(true);
        this.walkRange = 10;
    }
}
