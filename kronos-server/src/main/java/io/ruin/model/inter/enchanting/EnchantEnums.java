package io.ruin.model.inter.enchanting;

import lombok.Getter;

@Getter
public enum EnchantEnums {

    /*
     * Weapons
     */
    CRYSTAL_HELM("Crystal Helm", "Does more damage against dragons.",  30.0, true, EquipSlot.WEAPON),
    CRYSTAL_BODY("Crystal Body", "Increases your chances of getting a rare drop.",  5.0, true, EquipSlot.WEAPON, EquipSlot.RING),
    CRYSTAL_LEGS("Crystal Legs", "Does fire damage to your target over time.",  15.0, true, EquipSlot.WEAPON),
    ANCESTRAL_HAT("Ancestral Hat", "Reflects some damage back at your target.",  20.0, true, EquipSlot.WEAPON),
    ANCESTRAL_BODY("Ancestral Robe Top", "Does more damage against spiders.",  30.0, true, EquipSlot.WEAPON),
    ANCESTRAL_LEGS("Ancestral Robe Bottoms", "Randomly heals you when you deal damage.",  12.0, true, EquipSlot.WEAPON),
    VOID_MAGE_HELM("Void Mage Helm", "You will deal more damage the lower your health is.",  5.0, true, EquipSlot.WEAPON),
    VOID_MELEE_HELM("Void Melee Helm", "Reduced damage dealt", 20.0, false, EquipSlot.WEAPON),
    VOID_RANGE_HELM("Void Ranger Helm", "Deals additional damage, but also damages the user.", 10.0, false, EquipSlot.WEAPON),
    ELITE_VOID_TOP("Elite Void Top", "10% chance to inflict poison", 20.0, true, EquipSlot.WEAPON),
    ELITE_VOID_LEGS("Elite Void Robe", "10% chance to inflict poison", 20.0, true, EquipSlot.WEAPON),

    ;

    private String name, description;
    private EquipSlot[] slots;
    private double weight;
    private boolean positive;

    EnchantEnums(String name, String description, double weight, boolean positive, EquipSlot... slots) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.positive = positive;
        this.slots = slots;
    }

    enum EquipSlot {
        HAT, CAPE, AMULET, WEAPON, CHEST, SHIELD, BLANK_1, LEGS, BLANK_2, HANDS, FEET, BLANK_3, RING, AMMO
    }

}
