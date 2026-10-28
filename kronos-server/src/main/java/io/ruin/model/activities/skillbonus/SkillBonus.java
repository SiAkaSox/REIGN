package io.ruin.model.activities.skillbonus;

import lombok.Getter;

public class SkillBonus {

    @Getter public String name;
    @Getter public int statId;
    @Getter public boolean allowBoost;
    public SkillBonus(String name, int statId, boolean allowBoost) {
        this.name = name;
        this.statId = statId;
        this.allowBoost = allowBoost;
    }
}
