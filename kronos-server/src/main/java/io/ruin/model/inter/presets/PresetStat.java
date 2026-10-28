package io.ruin.model.inter.presets;

import com.google.gson.annotations.Expose;
import lombok.Getter;

@Getter
public class PresetStat {

    @Expose
    private int statId, level;
    @Expose private double experience;

    public PresetStat(int statId, int level, double experience) {
       this.statId = statId;
       this.level = level;
       this.experience = experience;
    }
}
