package io.ruin.services.punishment;

public enum MuteDuration {

    FIFTH("24 Hours", 60_000 * 60 * 24),
    FOURH("12 Hours",60_000 * 60 * 12),
    THIRD("6 Hours",60_000 * 60 * 6),
    SECOND("2 Hour",60_000 * 60 * 2),
    FIRST("1 Hour",60_000 * 60)

        ;

    public String setting;
    public long duration;

    MuteDuration(String setting, long duration) {
        this.setting = setting;
        this.duration = duration;
    }
}
