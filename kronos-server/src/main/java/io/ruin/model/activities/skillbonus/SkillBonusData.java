package io.ruin.model.activities.skillbonus;

public enum SkillBonusData {

    ATTACK(false),
    DEFENSE(false),
    STRENGTH(false),
    HITPOINTS(false),
    RANGED(false),
    PRAYER(false),
    MAGIC(false),
    COOKING(true),
    WOODCUTTING(true),
    FLETCHING(true),
    FISHING(true),
    FIREMAKING(true),
    CRAFTING(true),
    SMITHING(true),
    MINING(true),
    HERBLORE(true),
    AGILITY(true),
    THIEVING(true),
    SLAYER(true),
    FARMING(true),
    RUNECRAFTING(true)

    ;
    public boolean allowBoost;
    SkillBonusData(boolean allowBoost) {
        this.allowBoost = allowBoost;
    }
}
