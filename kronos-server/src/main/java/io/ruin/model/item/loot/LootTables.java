package io.ruin.model.item.loot;


import io.ruin.cache.ItemID;

import java.util.HashMap;

public enum LootTables {
    THEATRE_OF_BLOOD_REGULAR("theatre_of_blood_regular", "Theatre of Blood"),
    THEATRE_OF_BLOOD_UNIQUES("theatre_of_blood_uniques", "Theatre of Blood (Uniques)"),

    SUPPLY_CHEST("supply_chest", "Supply Chest"),
    ROGUE_CASTLE_CHEST("rogues_castle_chest", "Rogues Castle Chest"),
    CRYSTAL_CHEST("crystal_chest", "Crystal Chest"),

    CHAMBERS_OF_XERICS_UNIQUES("chamber_of_xerics_uniques", "Chambers of Xerics (Uniques)"),
    CHAMBERS_OF_XERICS_REGULAR("chamber_of_xerics_regular", "Chambers of Xerics"),
    CHAMBERS_OF_XERICS_REGULAR_IRONMAN(
            "chamber_of_xerics_regular_ironman", "<img=2>Chambers of Xerics"),

    WILDERNESS_CASKET_SMALL_IRONMAN(
            "wilderness_slayer_casket_small_ironman", "<img=2>Wilderness Slayer casket small"),
    WILDERNESS_CASKET_MEDIUM_IRONMAN(
            "wilderness_slayer_casket_medium_ironman", "<img=2>Wilderness Slayer casket medium"),
    WILDERNESS_CASKET_LARGE_IRONMAN(
            "wilderness_slayer_casket_large_ironman", "<img=2>Wilderness Slayer casket large"),
    WILDERNESS_CASKET_GIANT_IRONMAN(
            "wilderness_slayer_casket_giant_ironman", "<img=2>Wilderness Slayer casket giant"),
    WILDERNESS_CASKET_SMALL("wilderness_slayer_casket_small", ItemID.MYSTERY_BOX),
    WILDERNESS_CASKET_MEDIUM(
            "wilderness_slayer_casket_medium", ItemID.MYSTERY_BOX),
    WILDERNESS_CASKET_LARGE("wilderness_slayer_casket_large", ItemID.MYSTERY_BOX),
    WILDERNESS_CASKET_GIANT("wilderness_slayer_casket_giant", ItemID.MYSTERY_BOX),

    WILD_BOSS_EVENT("wild_boss_event", "Wilderness Boss Event"),

    SEREN_WILD_BOSS_EVENT("seren_wild_boss_event", "Seren Boss Event"),
    SKOTIZO_WILD_BOSS_EVENT("skotizo_wild_boss_event", "Seren Boss Event"),

    CASKET_REGULAR("casket_regular", "Casket Regular"),
    CLAN_CUP("clan_cup", "Clan Cup"),
    LARRANS_SMALL_CHEST("larrans_small_chest", "Larran's Small Chest"),
    LARRANS_BIG_CHEST("larrans_big_chest", "Larran's Big Chest"),
    LARRANS_SMALL_CHEST_IRONMAN("larrans_small_chest_ironman", "<img=2>Larran's Small Chest"),
    LARRANS_BIG_CHEST_IRONMAN("larrans_big_chest ironman", "<img=2>Larran's Big Chest"),

    MYSTERY_BOX("mystery_box", ItemID.MYSTERY_BOX),
    SUPER_MYSTERY_BOX("super_mystery_box", ItemID.MYSTERY_BOX),
    VOTE_MYSTERY_BOX("vote_mystery_box", ItemID.MYSTERY_BOX),
    VOTE_MYSTERY_BOX_IRONMAN("vote_mystery_box_ironman", "<img=2>Voting Mystery Box"),
    CUSTOM_ITEM_BOX("custom_item_box", ItemID.MYSTERY_BOX),
    CUSTOM_ITEM_MYSTERY_BOX("custom_item_mystery_box", ItemID.MYSTERY_BOX),

    HALLOWEEN_EVENT("halloween_event", "Halloween Event") {
        @Override
        public boolean isEnabled() {
            return false;
        }
    },

    BARROWS_MISC("barrows_misc"),

    BARROWS_MISC_UNIQUE("barrows_misc_unique"),

    LMS_OFFENSIVE_REGULAR("last_man_standing_offensive_regular"),

    LMS_DEFENSIVE_REGULAR("last_man_standing_defensive_regular"),

    LMS_OFFENSIVE_UPGRADED("last_man_standing_offensive_upgraded"),

    REVENANT_RARE("revenant_rare"),

    REVENANT_MEDIOCRE("revenant_mediocre"),

    CHAMBER_OF_XERICS_SEEDS("chamber_of_xerics_farming_seeds"),

    BIRD_NEST_RING("bird_nest_ring"),

    BIRD_NEST_SEED("bird_nest_seed"),

    BAG_FULL_OF_GEMS("bag_full_of_gems"),

    WINTERTODT_CRATE("wintertodt_crate"),

    WALL_SAFE("wall_safe"),
    ;

    private final String fileName;

    private final String bestiaryName;

    private final int itemIdBestiary;

    private LootTable table;

    LootTables(String fileName) {
        this.fileName = fileName;
        this.bestiaryName = "";
        this.itemIdBestiary = -1;
    }

    LootTables(String fileName, String bestiaryName) {
        this.fileName = fileName;
        this.bestiaryName = bestiaryName;
        this.itemIdBestiary = -1;
    }

    LootTables(String fileName, int itemIdBestiary) {
        this.fileName = fileName;
        this.bestiaryName = "";
        this.itemIdBestiary = itemIdBestiary;
    }

    private static final HashMap<String, LootTables> tables = new HashMap<>();

    public static void initialize() {
        for (LootTables value : values()) {
            tables.put(value.fileName, value);
        }
    }

    public boolean isEnabled() {
        return true;
    }

    public String getFileName() {
        return fileName;
    }

    public String getBestiaryName() {
        return bestiaryName;
    }

    public int getItemIdBestiary() {
        return itemIdBestiary;
    }

    public LootTable getTable() {
        return table;
    }

    public static HashMap<String, LootTables> getTables() {
        return tables;
    }

    public void setTable(LootTable table) {
        this.table = table;
    }
}
