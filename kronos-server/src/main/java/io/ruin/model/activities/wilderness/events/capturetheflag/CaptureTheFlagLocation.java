package io.ruin.model.activities.wilderness.events.capturetheflag;

import io.ruin.model.map.Position;
import lombok.Getter;

@Getter
public enum CaptureTheFlagLocation {

    WILDERNESS_COURSE(new Position(2991, 3940, 0), "inside the wilderness agility course"),

    VALCANO(new Position(3365, 3959, 0), "at the wilderness volcano"),

    LAVA_DRAGON_ENCAMPMENT(new Position(3224, 3829, 0), "inside the lava dragon encampment");

    private Position position;
    private String hint;

    CaptureTheFlagLocation(Position position, String hint) {
        this.position = position;
        this.hint = hint;
    }

}
