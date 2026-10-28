package io.ruin.model.activities.godwars.combat.impl.zaros;

enum Phase {

    SPAWN(1, -1, ""),
    SMOKE(1, -1, "Fill my soul with smoke!"),
    SMOKE_LOCKED(0.8, Fumus.ID, "Fumus, don't fail me!"),
    SHADOW(0.8, -1, "Darken my shadow!"),
    SHADOW_LOCKED(0.6, Umbra.ID, "Umbra, don't fail me!"),
    BLOOD(0.6, -1, "Flood my lungs with blood!"),
    BLOOD_LOCKED(0.4, Cruor.ID, "Cruor, don't fail me!"),
    ICE(0.4, -1, "Infuse me with the power of ice!"),
    ICE_LOCKED(0.2, Glacies.ID, "Glacies, don't fail me!"),
    ZAROS(0.2, -1, "NOW, THE POWER OF ZAROS!");

    private final double hitpointsPercent;
    private final int minionId;
    private final String phrase;

    Phase(double hitpointsPercent, int minionId, String phrase) {
        this.hitpointsPercent = hitpointsPercent;
        this.minionId = minionId;
        this.phrase = phrase;
    }

    int getMinionId() {
        return minionId;
    }

    String getPhrase() {
        return phrase;
    }

    double getHitpointsPercent() {
        return hitpointsPercent;
    }

    public int getLockedMinionId() {
        for (Phase value : values()) {
            if (this == value) {
                continue;
            }
            if (!value.name().startsWith(name())) {
                continue;
            }
            return value.getMinionId();
        }
        return -1;
    }

    public static Phase getPhase(double hpPercent, boolean locked) {
        Phase[] values = values();
        for (int i = values.length - 1; i > 0; i--) {
            Phase value = values[i];
            if (hpPercent > value.hitpointsPercent) {
                continue;
            }
            if (locked && !value.name().endsWith("LOCKED")) {
                continue;
            }
            if (!locked && value.name().endsWith("LOCKED")) {
                continue;
            }
            return value;
        }
        return SMOKE;
    }
}
