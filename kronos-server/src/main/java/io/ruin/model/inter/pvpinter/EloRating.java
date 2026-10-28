package io.ruin.model.inter.pvpinter;

import lombok.Getter;

public class EloRating {

    @Getter public String name;
    @Getter public int rating, kills, deaths;

    public EloRating(String name, int kills, int deaths, int rating) {
        this.name = name;
        this.rating = rating;
        this.kills = kills;
        this.deaths = deaths;
    }
}
