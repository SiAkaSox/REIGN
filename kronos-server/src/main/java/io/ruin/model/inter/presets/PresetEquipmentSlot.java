package io.ruin.model.inter.presets;

import lombok.Getter;

@Getter
public enum PresetEquipmentSlot {

    HAT(0, 147, 34),
    CAPE(1, 149, 35),
    AMULET(2, 151, 36),
    WEAPON(3, 155, 37),
    CHEST(4, 157, 38),
    SHIELD(5, 159, 39),
    LEGS(7, 161, 40),
    HANDS(9, 163, 41),
    FEET(10, 165, 42),
    RING(12, 167, 43),
    AMMO(13, 153, 44);

    private int equipId, childId, hiddenId;

    PresetEquipmentSlot(int equipId, int childId, int hiddenId) {
        this.equipId = equipId;
        this.childId = childId;
        this.hiddenId = hiddenId;
    }

}
