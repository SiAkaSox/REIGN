package io.ruin.model.activities.raids.theatre_of_blood;

import io.ruin.model.achievements.listeners.master.PurplePls;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.utility.Misc;

import java.util.ArrayList;
import java.util.Collections;

public enum Rewards {

    ELITE_CLUE(12073, 1, 1, 16.0),
    TORN_PRAYER_SCROLL(21047, 1, 1, 16.0),
    ARCANE_PRAYER_SCROLL(21079, 1, 1, 2.2),
    DEXTEROUS_PRAYER_SCROLL(21034, 1, 1, 2.2),
    DEATH_RUNE(560, 500, 600, 30.0),
    BLOOD_RUNE(565, 500, 600, 30.0),
    SWAMP_TAR(1939, 500, 600, 30.0),
    COAL(454, 500, 600, 30.0),
    GOLD_ORE(445, 300, 360, 30.0),
    MOLTEN_GLASS(1776, 200, 240, 30.0),
    ADAMANTITE_ORE(450, 130, 156, 30.0),
    RUNITE_ORE(452, 60, 72, 30.0),
    GRIMY_TOADFLAX(3050, 25, 60, 16.0),
    GRIMY_IRIT_LEAF(210, 30, 60, 68.0),
    GRIMY_AVANTOE(212, 25, 60, 68.0),
    GRIMY_KWUARM(214, 20, 60, 68.0),
    GRIMY_SNAPDRAGON(3052, 28, 60, 68.0),
    GRIMY_CADANTINE(216, 22, 70, 68.0),
    GRIMY_LANTADYME(2486, 18, 50, 68.0),
    GRIMY_DWARF_WEED(218, 20, 60, 68.0),
    GRIMY_TORSTOL(220, 25, 50, 68.0),
    BATTLESTAFF(1392, 50, 150, 68.0),
    MAHOGANY_SEED(21488, 15, 30, 68.0),
    RUNE_BATTLEAXE(1374, 40, 100, 68.0),
    RUNE_PLATEBODY(1128, 40, 100, 68.0),
    RUNE_CHAINBODY(1114, 40, 100, 68.0),
    TEAK_PLANK(8781, 100, 200, 16.0),
    MAHOGANY_PLANKS(8783, 100, 200, 16.0),

    /*
     * Raids 2
     */
    PET(22473, 1, 1, 2.5),
    SCYTHE_OF_VITUR(22325, 1, 1, 0.6),
    SANGUINESTI_STAFF(22323, 1, 1, 1.5),
    JUSTICAR_FACEGUARD(22326, 1, 1, 3.5),
    JUSTICAR_CHESTGUARD(22327, 1, 1, 3.5),
    JUSTICAR_LEGGUARDS(22328, 1, 1, 3.5),
    AVERNIC_DEFENDER(22322, 1, 1, 5.5),
    GHRAZI_RAPIER(22324, 1, 1, 2.5),
    ELITE_CLUE2(12073, 1, 1, 0.1),
    TORN_PRAYER_SCROLL2(21047, 1, 1, 0.1),
    ARCANE_PRAYER_SCROLL2(21079, 1, 1, 0.1),
    DEXTEROUS_PRAYER_SCROLL2(21034, 1, 1, 0.1),
    DEATH_RUNE2(560, 500, 600, 0.1),
    BLOOD_RUNE2(565, 500, 600, 0.1),
    SWAMP_TAR2(1939, 500, 600, 0.1),
    COAL2(454, 500, 600, 0.1),
    GOLD_ORE2(445, 300, 360, 0.1),
    MOLTEN_GLASS2(1776, 200, 240, 0.1),
    ADAMANTITE_ORE2(450, 130, 156, 0.1),
    RUNITE_ORE2(452, 60, 72, 0.1),
    GRIMY_TOADFLAX2(3050, 25, 60, 0.1),
    GRIMY_IRIT_LEAF2(210, 30, 60, 0.1),
    GRIMY_AVANTOE2(212, 25, 60, 0.1),
    GRIMY_KWUARM2(214, 20, 60, 0.1),
    GRIMY_SNAPDRAGON2(3052, 28, 60, 0.1),
    GRIMY_CADANTINE2(216, 22, 70, 0.1),
    GRIMY_LANTADYME2(2486, 18, 50, 0.1),
    GRIMY_DWARF_WEED2(218, 20, 60, 0.1),
    GRIMY_TORSTOL2(220, 25, 50, 0.1),
    BATTLESTAFF2(1392, 50, 150, 0.1),
    MAHOGANY_SEED2(21488, 15, 30, 0.1),
    RUNE_BATTLEAXE2(1374, 40, 100, 0.1),
    RUNE_PLATEBODY2(1128, 40, 100, 0.1),
    RUNE_CHAINBODY2(1114, 40, 100, 0.1),
    TEAK_PLANK2(8781, 100, 200, 0.1),
    MAHOGANY_PLANKS2(8783, 100, 200, 0.1),
    RUNITE_ORE3(452, 60, 72, 0.1),
    GRIMY_TOADFLAX3(3050, 25, 60, 0.1),
    GRIMY_IRIT_LEAF3(210, 30, 60, 0.1),
    GRIMY_AVANTOE3(212, 25, 60, 0.1),
    GRIMY_KWUARM3(214, 20, 60, 0.1),
    GRIMY_SNAPDRAGON3(3052, 28, 60, 0.1),
    GRIMY_CADANTINE3(216, 22, 70, 0.1),
    GRIMY_LANTADYME3(2486, 18, 50, 0.1),
    GRIMY_DWARF_WEED3(218, 20, 60, 0.1),
    GRIMY_TORSTOL3(220, 25, 50, 0.1),
    BATTLESTAFF3(1392, 50, 150, 0.1),
    MAHOGANY_SEED3(21488, 15, 30, 0.1),
    RUNE_BATTLEAXE3(1374, 40, 100, 0.1),
    RUNE_PLATEBODY3(1128, 40, 100, 0.1),
    RUNE_CHAINBODY3(1114, 40, 100, 0.1),
    TEAK_PLANK3(8781, 100, 200, 0.1),
    MAHOGANY_PLANKS3(8783, 100, 200, 0.1),

    SUPER_COMBAT_POTION(12696, 10, 18, 0.1),
    SUPER_RESTORE(3025, 20, 24, 0.1),
    SARADOMIN_BREW(6686, 20, 22, 0.1),
    CRUSHED_NEST(6694, 10, 14, 0.1),
    SUPERIOR_DRAGON_BONES(22125, 28, 42, 0.1),
    RUNE_FULL_HELM(1164, 45, 80, 0.1),
    RUNE_KITESHIELD(1202, 18, 72, 0.1),
    RUNE_PLATELEGS(1080, 20, 60, 0.1),
    MAGIC_LOGS(1514, 70, 92, 0.1),
    DRAGON_BONES(537, 20, 25, 0.1),
    ONYX_BOLT_TIPS(9194, 50, 58, 0.1),
    BLACK_DRAGONHIDE(1748, 100, 120, 0.1),
    DRAGON_PLATELEGS(4088, 2, 4, 0.1),
    BRACELET_OF_ETHEREUM(21818, 2, 3, 0.1),
    DRAGON_DAGGER(1216, 50, 64, 0.1),
    DRAGON_LONGSWORD(1306, 5, 17, 0.1),
    MYSTIC_ROBE_TOP(23050, 1, 1, 0.1),
    MYSTIC_ROBE_BOTTOM(23053, 1, 1, 0.1),


    ;

    public int itemId, minimum_amount, maximum_amount;
    public double chance;

    Rewards(int itemId, int min, int max, double chance) {
        this.itemId = itemId;
        this.minimum_amount = min;
        this.maximum_amount = max;
        this.chance = chance;
    }

    public static Rewards forItem(int itemId) {
        for (Rewards reward : Rewards.values()) {
            if (reward.itemId == itemId) {
                return reward;
            }
        }
        return null;
    }

    public static final double UNIQUE = 0, UNCOMMON = 16.0, COMMON = 30, RARE = 11, VERY_RARE = 0.4, ALWAYS = 100;
    
    public static ItemContainer getRewards(Player player) {
        final ItemContainer rewards = new ItemContainer();
        rewards.init(player, 28, -1, 63761, 541, false);
        rewards.sendAll = false;
        int damage = TheatrePartiesManager.getParty(player) == null ? 0 : TheatrePartiesManager.getParty(player).getRewardPoints(player);
        int count = damage == 0 ? 0 : Misc.random(1, 3);
        ArrayList<Rewards> applicable_rewards = new ArrayList<>();
        boolean rared = false;
        for (Rewards reward : Rewards.values()) {
            double chance = damage >= 5000 ? UNIQUE : damage >= 3800 ? RARE : damage >= 2500 ? UNCOMMON : COMMON;
            if (reward.chance >= chance) {
                if (reward.chance < 10) {
                    if (Misc.random(3) != 1) {
                        applicable_rewards.add(reward);
                    }
                } else {
                    applicable_rewards.add(reward);
                }
            }
        }
        Collections.shuffle(applicable_rewards);
        for (int i = 0; i < count; i++) {
            Rewards reward = applicable_rewards.get(Misc.random(applicable_rewards.size() - 1));
            if ((rared && reward.chance < 10)) {
                continue;
            }
            if (reward.chance < 10) {
                rared = true;
            }
            rewards.add(new Item(reward.itemId, reward.maximum_amount));
            PurplePls.complete(player);
        }
        return rewards;
    }
}