package io.ruin.model.activities.wilderness.bosses.custom.yama;

import io.ruin.model.map.Direction;
import lombok.Data;
import lombok.Getter;

@Getter
public enum FireStratergy {

    SOUTH(3161, 4115, 17, Direction.SOUTH),
    NORTH(3161, 4126, 17, Direction.NORTH),
    EAST(3177, 4114, 14, Direction.EAST),
    WEST(3161, 4114, 14, Direction.WEST);

    private int startX, startY, max;
    private Direction direction;

    FireStratergy(int startX, int startY, int max, Direction direction) {
        this.startX = startX;
        this.startY = startY;
        this.max = max;
        this.direction = direction;
    }
}
