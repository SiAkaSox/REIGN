package io.ruin.model.map.object.actions.impl;

import io.ruin.model.map.object.actions.ObjectAction;

public class DagKingsLadder {
    static {
        //Dagannoth Kings Ladder
        //ObjectAction.register(40417, 1911, 4367, 0, 1, (player, obj) -> Ladder.climb(player, 2900, 4385, 0, false, true, false));
        ObjectAction.register(3831, 1, (player, obj) -> Ladder.climb(player, 2900, 4385, 0, false, true, false));
    }
}