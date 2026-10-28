package io.ruin.model.item;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum EquipmentSound {

    BODY(2239, "platebody", "chainbody"),
    WEP(2248, "sword", "scimitar", "2h"),
    RANGE(2244, "bow", "arrow"),
    BOLT(2235, "bolt"),
    WEP_2(2246, "mace", "flail"),
    LEGS(2442, "plateleg", "plateskirt"),
    WEP_3(2247, "staff", "spear"),
    BOOT(2237, "boot"),
    WEP_4(2249, "whip"),
    HELMET(2440, "helmet"),
    RANGED_ARM(2241, "chaps", "vambrace", "d'hide", "leather body"),
    MISC(2243, "maul"),
    MISC_2(2232, "pickaxe", "battleaxe", "greataxe"),
    MISC_3(2238, "cape", "ring", "hood")
    ;

    public int soundId;
    public List<String> triggers;

    EquipmentSound(int soundId, String... triggers) {
        this.soundId = soundId;
        this.triggers = Arrays.asList(triggers);
    }
}
