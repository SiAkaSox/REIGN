
package io.ruin.model.entity;

public enum HitpointsBar {
    GREEN_RED_30(30),
    ORANGE_RED_100(100),
    BAR_2(30),
    BLUE_DARK_BLUE_30(30),
    BLUE_DARK_BLUE_50(50),
    BLUE_DARK_BLUE_60(60),
    BLUE_DARK_BLUE_80(80),
    BLUE_GREEN(100),
    YELLOW_RED(120),
    BLUE_DARK_BLUE_100(100),
    BLUE_DARK_BLUE_120(120),
    BLUE_DARK_BLUE_140(140),
    CYAN_BLUE(120),
    BLUE_DARK_BLUE_160(160),
    BLUE_DARK_BLUE_180(180),
    ORANGE_RED_30(30),
    GREEN_RED_50(50),
    GREEN_RED_60(60),
    GREEN_RED_80(80),
    GREEN_RED_100(100),
    GREEN_RED_120(120),
    GREEN_RED_140(140),
    GREEN_RED_160(160);

    private int size;

    private HitpointsBar(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
