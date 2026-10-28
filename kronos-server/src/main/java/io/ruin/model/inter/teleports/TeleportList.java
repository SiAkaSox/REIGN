package io.ruin.model.inter.teleports;

import io.ruin.utility.StringUtils;
import io.ruin.model.map.Position;
import io.ruin.utility.TeleportConstants;

public class TeleportList {

    public static Category[] categories;
    public static Teleport[] teleports;

    static {
        categories = Category.values();
        teleports = Teleport.values();
    }

    public enum Category {
        FAVORITES("Favorites", 1131, 10, 10),
        CITIES("Cities", 1131, 10, 10, Teleport.AL_KHARID, Teleport.ARDOUGNE, Teleport.BRIMHAVEN,  Teleport.BURTHORPE, Teleport.CAMELOT, Teleport.CANIFIS, Teleport.CATHERBY, Teleport.DRAYNOR_VILLAGE,  Teleport.EDGEVILLE, Teleport.FALADOR, Teleport.GREAT_KOUREND, Teleport.KEBOS_LOWLANDS, Teleport.KARAMJA_DOCKS, Teleport.LUMBRIDGE, Teleport.LLEYTA, Teleport.LUNAR_ISLE, Teleport.MOS_LE_HARMLESS, Teleport.POLLNIVNEACH, Teleport.PORT_SARIM, Teleport.PRIFDDINAS,  Teleport.RELLEKKA,  Teleport.SEERS_VILLAGE, Teleport.SHILO_VILLAGE, Teleport.TREE_GNOME_STRONGHOLD, Teleport.VARROCK, Teleport.VER_SINHAZA, Teleport.YANILLE ),
        DUNGEONS("Dungeons", 1131, 10, 10, Teleport.ANCIENT_CAVERN, Teleport.BASILISK_KNIGHT_CAVE, Teleport.BRIMHAVEN_DUNGEON, Teleport.CATACOMBS_OF_KOUREND , Teleport.CRASH_SITE_CAVERN, Teleport.EDGEVILLE_DUNGEON, Teleport.FIRE_CHASM, Teleport.FORTHOS_DUNGEON, Teleport.GODWARS_DUNGEON, Teleport.LITHKREN_LABORATORY, Teleport.LIZARD_CAVERN, Teleport.LUMBRIDGE_SWAMPCAVES, Teleport.MOS_LE_HARMLESSS, Teleport.MOURNER_TUNNELS, Teleport.MOUNT_KARUULM, Teleport.RELLEKA_SLAYER_CAVE, Teleport.TAVERLY_DUNGEON, Teleport.SMOKE_DUNGEON, Teleport.STRONGHOLD_SLAYER_CAVE),
        TRAINING("Training", 1131, 10, 10, Teleport.AMMONITE_CRABS, Teleport.BANDIT_CAMP, Teleport.CHICKENS, Teleport.COWS, Teleport.EXPERIMENTS, Teleport.GOBLINS, Teleport.MORYTANIA_SLAYER_TOWER, Teleport.OGRES, Teleport.ROCK_CRABS, Teleport.SAND_CRABS, Teleport.STRONGHOLD_SECURITY, Teleport.TROILLHEIM_TROLLS, Teleport.YAKS),
        MINIGAMES("Minigames", 1131, 10, 10, Teleport.BARROWS, Teleport.CASTLE_WARS, Teleport.CHAMBERS_OF_XERIC, Teleport.DUEL_ARENA, Teleport.FIGHT_CAVE, Teleport.INFERNO, Teleport.MOTHERLODE_MINE, Teleport.PEST_CONTROL, Teleport.PURO_PURO, Teleport.WARRIORS_GUILD, Teleport.WINTERTODT),
        WILDERNESS("Wilderness", 1131, 10, 10, Teleport.CHAOS_ELEMENTAL, Teleport.EAST_DRAGONS, Teleport.GRAVEYARD_OF_SHADOWS, Teleport.GREATER_DEMONS, Teleport.ICE_PLATEAU, Teleport.LAVA_MAZE, Teleport.MAGE_BANK, Teleport.REVENANTS, Teleport.RESOURCE_AREA, Teleport.WEST_DRAGONS),
        SKILLING("Skilling", 1131, 10, 10, Teleport.AERIAL_FISHING, Teleport.BARB_FISHING, Teleport.COOKING_GUILD, Teleport.CRAFTING_GUILD, Teleport.DESERT_HUNTER, Teleport.FARMING_GUILD, Teleport.FELDIP_HILLS, Teleport.FISHING_GUILD, Teleport.GEM_MINE, Teleport.GNOME_AGILITY, Teleport.JATIZSO_MINE, Teleport.KARAMJA_DOCKS,Teleport.KOUREND_ANGLERS,Teleport.MINING_GUILD, Teleport.PURE_ESSENCE_MINING, Teleport.RANGING_GUILD, Teleport.WOODCUTTING_GUILD, Teleport.WOODLAND_HUNTER),
        BOSSES("Bosses", 1131, 10, 10, Teleport.ABYSSAL_SIRE,Teleport.ALCHEMICAL_HYDRA, Teleport.CERBERUS, Teleport.CORPOREAL_BEAST, Teleport.DAGGANOTH_LAIR, Teleport.GALVEK, Teleport.GIANT_MOLE, Teleport.GODWARS_DUNGEON, Teleport.KALPHITE_QUEEN, Teleport.KING_BLACK_DRAGON, Teleport.KRAKEN, Teleport.SMOKE_DEVIL, Teleport.VORKATH, Teleport.ZULRAH),
        MISC("Misc", 1131, 10, 10, Teleport.CHAMPIONS_GUILD, Teleport.GRAND_EXCHANGE, Teleport.MYTHS_GUILD);

        private final String name;
        private final Teleport[] teleports;
        private final int spriteId;

        private int sw = 24, sh = 24;

        Category(String name, int spriteId, Teleport... teleports) {
            this.name = name;
            this.spriteId = spriteId;
            this.teleports = teleports;
        }

        Category(String name, int spriteId, int sw, int sh, Teleport... teleports) {
            this.name = name;
            this.spriteId = spriteId;
            this.teleports = teleports;
            this.sw = sw;
            this.sh = sh;
        }

        public String getName() {
            return name;
        }

        public Teleport[] getTeleports() {
            return teleports;
        }

        public int getSpriteId() {
            return spriteId;
        }

        public int getSw() {
            return sw;
        }

        public int getSh() {
            return sh;
        }
    }

    public enum Teleport {
        //CITIES
        LUMBRIDGE(0, TeleportConstants.LUMBRIDGE),
        VARROCK(1, TeleportConstants.VARROCK),
        EDGEVILLE(2, TeleportConstants.EDGEVILLE),
        FALADOR(3, TeleportConstants.FALADOR),
        ARDOUGNE(4, TeleportConstants.ARDOUGNE),
        CAMELOT(5, TeleportConstants.CAMELOT),
        SEERS_VILLAGE(68, TeleportConstants.SEERS),//TODO delete
        YANILLE(6, TeleportConstants.YANILLE),
        TREE_GNOME_STRONGHOLD(7, TeleportConstants.TREE_GNOME_STRONGHOLD),
        BRIMHAVEN(8, TeleportConstants.BRIMHAVEN),
        PORT_SARIM(9, TeleportConstants.PORT_SARIM),
        AL_KHARID(10, TeleportConstants.AL_KHARID, false, false),
        CANIFIS(11, TeleportConstants.CANIFIS),
        BURTHORPE(32, TeleportConstants.BURTHORPE, false, false),
        CATHERBY(32, TeleportConstants.CATHERBY, false, false),
        DRAYNOR_VILLAGE(33, TeleportConstants.DRAYNOR_VILLAGE),
        RELLEKKA(34, TeleportConstants.RELLEKKA, false, false),
        SHILO_VILLAGE(35, TeleportConstants.SHILO_VILLAGE, false, false),
        MOS_LE_HARMLESS(66, TeleportConstants.MOS_LE_HARMLESS, false, false),
        //CANAFIS(69, TeleportConstants.CANAFIS, false, false),
        POLLNIVNEACH(70, TeleportConstants.POLLNIVNEACH, false, false),
        LUNAR_ISLE(71, TeleportConstants.LUNAR_ISLE, false, false),
        GREAT_KOUREND(73, TeleportConstants.GREAT_KOUREND, false, false),
        KEBOS_LOWLANDS(72, TeleportConstants.KEBOS_LOWLANDS, false, false),
        PRIFDDINAS(69, TeleportConstants.PRIFDDINAS, false, false),
        LLEYTA(305, TeleportConstants.LLEYTA, false, false),
        VER_SINHAZA(420, TeleportConstants.VER_SINHAZA, false, false),

        //ENTRANA(29,TeleportConstants.ENTRANA),

        //DUNGEONS
        TAVERLY_DUNGEON(12, TeleportConstants.TAVERLY_DUNGEON, "blue dragons", "black dragons", "chaos druids", "greater demons", "hellhounds"),
        BRIMHAVEN_DUNGEON(13, TeleportConstants.BRIMHAVEN_DUNGEON, "greater demons", "red dragons", "iron dragons", "steel dragons"),
        GODWARS_DUNGEON(14, TeleportConstants.GODWARS_DUNGEON, false, false, "bandos", "saradomin", "zamorak", "armadyl", "gwd"),
        EDGEVILLE_DUNGEON(15, TeleportConstants.EDGEVILLE_DUNGEON, "hill giants", "black demons", "earth warriors", "chaos druids"),
        STRONGHOLD_SLAYER_CAVE(52, TeleportConstants.STRONGHOLD_SLAYER_CAVE),
        RELLEKA_SLAYER_CAVE(54, TeleportConstants.RELLEKA_SLAYER_CAVE),
        CATACOMBS_OF_KOUREND(54, TeleportConstants.CATACOMBS, false, false),
        MOUNT_KARUULM(200, TeleportConstants.MOUNT_KARUULM),
        LITHKREN_LABORATORY(200, TeleportConstants.LITHKREN_LABORATORY),
        CRASH_SITE_CAVERN(201, TeleportConstants.CRASH_SITE_CAVERN, "demonic gorillas"),
        MOS_LE_HARMLESSS(66, TeleportConstants.MOS_LE_HARMLESS, false, false),
        BASILISK_KNIGHT_CAVE(66, TeleportConstants.BASILISK_KNIGHT_CAVE, false, false),
        FIRE_CHASM(306, TeleportConstants.FIRE_CHASM, false, false),
        MOURNER_TUNNELS(307, TeleportConstants.MOURNER_TUNNELS, false, false),
        SMOKE_DUNGEON(308, TeleportConstants.SMOKE_DUNGEON, false, false),
        LIZARD_CAVERN(309, TeleportConstants.LIZARD_CAVERN, false, false),
        LUMBRIDGE_SWAMPCAVES(310, TeleportConstants.LUMBRIDGE_SWAMPCAVES, false, false),
        ASGANIAN_ICEDUNGEON(311, TeleportConstants.ASGANIAN_ICEDUNGEON, false, false),
        ANCIENT_CAVERN(313, TeleportConstants.ANCIENT_CAVERN, false, false),
        FORTHOS_DUNGEON(315, TeleportConstants.FORTHOS_DUNGEON, false, false),



        //TRAINING
        ROCK_CRABS(16, TeleportConstants.ROCK_CRABS, false, false),
        COWS(16, TeleportConstants.COWS, false, false),
        EXPERIMENTS(17, TeleportConstants.EXPERIMENTS),
        SAND_CRABS(18, TeleportConstants.SAND_CRABS, false, false, "crabs", "sand", "crab"),
        AMMONITE_CRABS(47, TeleportConstants.AMMONITE_CRABS, false, false, "crabs", "ammy", "crab"),
        BANDIT_CAMP(48, TeleportConstants.BANDIT_CAMP, false, false),
        CHICKENS(49, TeleportConstants.CHICKENS, false, false),
        GOBLINS(50, TeleportConstants.GOBLINS, false, false),
        OGRES(51, TeleportConstants.OGRES, false, false),
        MORYTANIA_SLAYER_TOWER(53, TeleportConstants.MORYTANIA_SLAYER_TOWER, false, false),
        YAKS(53, TeleportConstants.YAKS, false, false),
        STRONGHOLD_SECURITY(322, TeleportConstants.STRONGHOLD_SECURITY, false, false),
        TROILLHEIM_TROLLS(323, TeleportConstants.TROILLHEIM_TROLLS, false, false),


        //MINIGAMES
        DUEL_ARENA(28, TeleportConstants.DUEL_ARENA, false, false, "duel", "battle", "fight"),
        MOTHERLODE_MINE(29, TeleportConstants.MOTHERLODE_MINE, false, false, "mining", "motherload", "mother", "motherlode"),
        PURO_PURO(30, TeleportConstants.PURO_PURO, false, false, "puro", "puro puro", "implings", "imp", "hunter"),
        WARRIORS_GUILD(43, TeleportConstants.WARRIORS_GUILD, false, false, "defender"),
        PEST_CONTROL(44, TeleportConstants.PEST_CONTROL, false, false, "pc", "pest"),
        BARROWS(45, TeleportConstants.BARROWS, false, false, "barrow"),
        CHAMBERS_OF_XERIC(46, TeleportConstants.CHAMBERS_OF_XERIC, false, false, "cox"),
        WINTERTODT(56, TeleportConstants.WINTERTODT, false, false, "fm", "firemaking"),
        CASTLE_WARS(57, TeleportConstants.CASTLE_WARS, false, false, "castle", "wars"),
        FIGHT_CAVE(72, TeleportConstants.FIGHT_CAVE, false, false, "fire", "fire cape"),
        INFERNO(73, TeleportConstants.INFERNO, false, false, "fire", "infernal cape"),


        //WILDERNESS
        AGILITY_COURSE(18, TeleportConstants.WILD_AGILITY, true, false),
        RESOURCE_AREA(18, TeleportConstants.RESOURCE_AREA, true, false),
        EAST_DRAGONS(19, TeleportConstants.EAST_DRAGONS, true, false, "easts", "green dragons"),
        REVENANTS(20, TeleportConstants.REVENANTS, true, false, "revs"),
        MAGE_BANK(21, TeleportConstants.MAGE_BANK, true, false, "mb"),
        GREATER_DEMONS(22, TeleportConstants.GREATER_DEMONS, true, false, "gdz"),
        ICE_PLATEAU(23, TeleportConstants.ICE_PLATEAU, true, false),
        GRAVEYARD_OF_SHADOWS(24, TeleportConstants.GRAVEYARD_OF_SHADOWS, true, false),
        LAVA_MAZE(25, TeleportConstants.LAVA_MAZE, true, false),
        CHAOS_ELEMENTAL(26, TeleportConstants.CHAOS_ELEMENTAL, true, false, "rogues"),
        WEST_DRAGONS(67, TeleportConstants.WEST_DRAGONS, true, false, "wests", "green dragons"),


        //SKILLING
        COOKING_GUILD(36, TeleportConstants.COOKING_GUILD, false, false),
        WOODCUTTING_GUILD(37, TeleportConstants.WOODCUTTING_GUILD, false, false),
        FISHING_GUILD(38, TeleportConstants.FISHING_GUILD, false, false),
        CRAFTING_GUILD(39, TeleportConstants.CRAFTING_GUILD, false, false),
        RANGING_GUILD(40, TeleportConstants.RANGING_GUILD, false, false),
        MINING_GUILD(41, TeleportConstants.MINING_GUILD, false, false),
        FARMING_GUILD(41, TeleportConstants.FARMING_GUILD, false, false, "farming"),
        PURE_ESSENCE_MINING(42, TeleportConstants.PURE_ESSENCE_MINING, false, false),
        AERIAL_FISHING(300,TeleportConstants.AERIAL_FISHING,false, false),
        KOUREND_ANGLERS(302,TeleportConstants.KOUREND_ANGLERS,false, false),
        BARB_FISHING(303, TeleportConstants.BARB_FISHING, false, false),
        GEM_MINE(312, TeleportConstants.GEM_MINE, false, false),
        GNOME_AGILITY(313, TeleportConstants.GNOME_AGILITY, false, false),
        FELDIP_HILLS(316, TeleportConstants.FELDIP_HILLS, false, false),
        KARAMJA_DOCKS(318, TeleportConstants.KARAMJA_DOCKS, false, false),
        WOODLAND_HUNTER(319, TeleportConstants.WOODLAND_HUNTER, false, false),
        DESERT_HUNTER(320, TeleportConstants.DESERT_HUNTER, false, false),
        JATIZSO_MINE(321, TeleportConstants.JATIZSO_MINE, false, false),


        //BOSSES
        VORKATH(27, TeleportConstants.VORKATH, false, false),
        CORPOREAL_BEAST(55, TeleportConstants.CORPOREAL_BEAST, false, false),
        CERBERUS(58, TeleportConstants.CERBERUS, false, false),
        KRAKEN(59, TeleportConstants.KRAKEN, false, false),
        KING_BLACK_DRAGON(56, TeleportConstants.KING_BLACK_DRAGON, true, false),
        ZULRAH(60, TeleportConstants.ZULRAH, false, false),
        GIANT_MOLE(62, TeleportConstants.GIANT_MOLE, false, false),
        KALPHITE_QUEEN(63, TeleportConstants.KALPHITE_QUEEN, false, false),
        SMOKE_DEVIL(64, TeleportConstants.SMOKE_DEVIL, false, false),
        ALCHEMICAL_HYDRA(65, TeleportConstants.ALCHEMICAL_HYDRA, false, false),
        DAGGANOTH_LAIR(301, TeleportConstants.DAGGANOTH_LAIR, false, false),
        GALVEK(81, TeleportConstants.GALVEK, true, false),
        LIZARD_CANYON(314,TeleportConstants.LIZARD_CANYON, false, false),
        ABYSSAL_SIRE(324,TeleportConstants.ABYSSAL_SIRE, false, false),

        //MISC
        GRAND_EXCHANGE(31, TeleportConstants.GRAND_EXCHANGE, false, false, "exchange", "grand", "grand exchange"),
        CHAMPIONS_GUILD(36, TeleportConstants.CHAMPIONS_GUILD, false, false),
        MYTHS_GUILD(317, TeleportConstants.MYTHS_GUILD, false, false),

        ;

        private final int id;
        private final String name;
        private final boolean wild;
        private final boolean multi;
        private final Position position;
        private String[] tags;

        Teleport(int id, Position position, String... tags) {
            this.id = id;
            this.name = StringUtils.fixCaps(name().replace("_", " ").toLowerCase());
            wild = false;
            multi = false;
            this.tags = tags;
            this.position = position;
        }

        Teleport(int id, Position position, boolean wild, boolean multi, String... tags) {
            this.id = id;
            this.name = StringUtils.fixCaps(name().replace("_", " ").toLowerCase());
            this.wild = wild;
            this.multi = multi;
            this.tags = tags;
            this.position = position;
        }

        Teleport(int id, String name, Position position, boolean wild, boolean multi, String... tags) {
            this.id = id;
            this.name = name;
            this.wild = wild;
            this.multi = multi;
            this.tags = tags;
            this.position = position;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public boolean isWild() {
            return wild;
        }

        public boolean isMulti() {
            return multi;
        }

        public String[] getTags() {
            return tags;
        }

        public Position getPosition() {
            return position;
        }
    }

    public static Teleport getTeleportForId(int id) {
        for (Teleport tp : teleports) {
            if (tp.id == id)
                return tp;
        }

        return null;
    }
}