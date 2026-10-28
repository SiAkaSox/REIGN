package io.ruin.model.skills.magic.spells;

public class AutoCast {

    public int index;

    public TargetSpell spell;
    public AutoCast(int index, TargetSpell spell) {
        this.index = index;
        this.spell = spell;
    }
}
