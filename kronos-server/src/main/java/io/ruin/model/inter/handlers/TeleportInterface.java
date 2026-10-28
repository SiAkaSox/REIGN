package io.ruin.model.inter.handlers;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import io.ruin.cache.Color;
import io.ruin.data.impl.teleports;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.map.Position;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author ReverendDread on 5/19/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class TeleportInterface {

    //The last catagory opened.
    @Expose @Setter @Getter private Catagory lastCatagory;
    //The last teleport used
    @Expose @Getter @Setter private Teleport lastTeleport;
    @Expose @Getter @Setter private Teleport lastTeleport1;
    @Expose @Getter @Setter private Teleport lastTeleport2;
    @Expose @Getter @Setter private Teleport lastTeleport3;
    @Expose @Getter @Setter private Teleport lastTeleport4;
    //The last teleport selected
    @Expose @Getter @Setter private Teleport selectedTeleport;
    @Expose @Getter @Setter private Teleport selectedTeleportFavorite;
    //The player assosiated with the interface.
    @Setter @Getter private static transient Player player;

    //Doing it this way incase we add more buttons in the future
    private static final int[] TEXT_COMPONENTS = {
            88, 89, 90, 170, 171, 172, 177, 178, 179, 184, 185, 186, 191, 192, 193, 198, 199, 200, 361, 362, 363
    };
    private static final int[] INFO_COMPONENTS = {
            325, 326, 327, 328, 329, 330, 331, 332, 333, 338, 339, 340, 341, 342, 343, 344, 345, 346, 364, 365, 366
    };
    private static final int[] BUTTON_COMPONENTS = {
            368, 369, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388
    };
    private static final int[] TELEPORT_INFO = {
            157, 158, 325, 326, 327, 328, 329, 330, 331, 332, 333, 338, 339, 340, 341, 342, 343, 344, 345, 346, 364, 365, 366
    };
    private static final int[] FAVORITE_BUTTONS = {
            315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 347, 348, 349, 350, 351, 352, 353, 354
    };
    //Teleport button component
    private static final int TELEPORT_BUTTON = 428;
    private static final int PREVIOUS_BUTTON = 425;

    /**
     * Holds data about teleports.
     */
    @RequiredArgsConstructor @Getter
    public enum Teleport {
        /*
         * Monster Teles
         */
        AMMONITE_CRABS(0, 18, Catagory.MONSTERS, "Ammonite Crabs", "Ammonite crabs on fossil island",Position.of(3722, 3840, 0), Difficulty.HARD, false, true),
        BANDIT_CAMP(1, 6, Catagory.MONSTERS, "Bandit Camp", "Experienced/afk training location",Position.of(3170, 2982, 0), Difficulty.HARD, false, true),
        BRINE_RAT(2, 7, Catagory.MONSTERS, "Brine Rat Cavern","Home of ulfric and some giant rats", Position.of(2696, 10121, 0), Difficulty.HARD, false, true),
        CAVE_HORRORS(3, 19, Catagory.MONSTERS, "Cave Horrors", "Mos le harmless cave horrors",Position.of(3748, 9374, 0), Difficulty.HARD, false, true),
        CHASM_OF_FIRE(4, 8, Catagory.MONSTERS, "Chasm of Fire","Seems to be full of Demons", Position.of(1435, 3671, 0), Difficulty.HARD, false, true),
        CRASH_SITE(5, 9, Catagory.MONSTERS, "Crash Site Cavern", "Demonic gorillas can be found here",Position.of(2026, 5610, 0), Difficulty.HARD, false, true),
        COWS(6, 6, Catagory.MONSTERS, "Cows", "Beginner training location",Position.of(3246, 3274, 0), Difficulty.BEGINNER, false, true),
        DAGANNOTHS(7, 17, Catagory.MONSTERS, "Dagannoths", "Mid level slayer task",Position.of(2442, 10146, 0), Difficulty.HARD, false, true),
        EXPERIMENTS(8, 5, Catagory.MONSTERS, "Experiments","Experienced training location", Position.of(3550, 9932, 0), Difficulty.MEDIUM, false, true),
        GOBLIN_VILLAGE(9, 4, Catagory.MONSTERS, "Goblin Village","Beginner training location", Position.of(2956, 3501, 0), Difficulty.EASY, false, true),
        JUNGLE_DEMONS(10, 10, Catagory.MONSTERS, "Jungle Demons", "Mid level training located",Position.of(2715, 9211, 1), Difficulty.HARD, false, true),
        KALPHITES(11, 20, Catagory.MONSTERS, "Kalphites", "Entrance to the kalphite lair",Position.of(3483, 9509, 0), Difficulty.HARD, false, true),
        KRAKEN_COVE(12, 11, Catagory.MONSTERS, "Kraken Cove", "Release the kraken!",Position.of(2278, 3609, 0), Difficulty.HARD, false, true),
        MOURENER(13, 12, Catagory.MONSTERS, "Mourner Tunnels", "Dark beasts and runite ore located here",Position.of(2029, 4636, 0), Difficulty.HARD, false, true),
        ROCK_CRABS(14, 0, Catagory.MONSTERS, "Rock Crabs", "Ideal spot for beginners combat training",Position.of(2676, 3711, 0), Difficulty.BEGINNER, false, true),
        SAND_CRABS(15, 1, Catagory.MONSTERS, "Sand Crabs", "Good location to afk combat train",Position.of(1723, 3464, 0), Difficulty.BEGINNER, false, true),
        SLAYER_TOWER(16, 13, Catagory.MONSTERS, "Slayer Tower", "Most common area to train slayer",Position.of(3428, 3534, 0), Difficulty.HARD, false, true),
        STRONGHOLD_SLAYER(17, 14, Catagory.MONSTERS, "Stronghold Slayer Cave", "Nieve's slayer task cave",Position.of(2431, 3424, 0), Difficulty.HARD, false, true),
        SUQAH(18, 16, Catagory.MONSTERS, "Suqahs", "Mid level slayer task",Position.of(2135, 3866, 0), Difficulty.HARD, false, true),
        TROLLPIT(19, 15, Catagory.MONSTERS, "Trolls", "Death plateau trolls, mid level slayer task",Position.of(2884, 3588, 0), Difficulty.HARD, false, true),
        ELVEN_CAMP(20, 2, Catagory.MONSTERS, "Elven Camp", "Popular mid level slayer task area",Position.of(2192, 3240, 0), Difficulty.BEGINNER, false, true),

        /*
         * Dungeon Teles
         */
        ANCIENT_CAVERN(0, 24, Catagory.DUNGEONS, "Ancient Cavern", "Home to mithril dragons and waterfiends",Position.of(1762, 5361, 0), Difficulty.HARD, false, false),
        ANCIENT_WYVERN_CAVERN(1, 37, Catagory.DUNGEONS, "Ancient Wyvern Cavern", "Contains all four wyvern types",Position.of(3604, 10231, 0), Difficulty.HARD, false, true),
        ASGARNIAN_ICE_DUNGEON(2, 25, Catagory.DUNGEONS, "Asgarnian Ice Dungeon", "Ice giants and ice warriors",Position.of(3006, 9550, 0), Difficulty.HARD, false, false),
        BRIM_DUNG(3, 26, Catagory.DUNGEONS, "Brimhaven Dungeon", "Moss giants, greater demons, iron/steel dragons",Position.of(2710, 9564, 0), Difficulty.HARD, false, false),
        CATACOMBS(4, 27, Catagory.DUNGEONS, "Catacombs of Kourend", "Higher level slayer task monsters",Position.of(1665, 10049, 0), Difficulty.HARD, false, false),
        CHASM_OF_FIRE1(5, 38, Catagory.DUNGEONS, "Chasm of Fire", "Home to lesser, greater and black Demons",Position.of(1435, 3671, 0), Difficulty.HARD, false, true),
        FORTHOS_DUNGEON(6, 125, Catagory.DUNGEONS, "Forthos Dungeon", "Home to sarachnis, red dragons and spiders",Position.of(1800, 9967, 0), Difficulty.HARD, false, true),
        FREMMY_DUNG(7, 28, Catagory.DUNGEONS, "Fremennik Slayer Dungeon", "Mid level slayer tasks located here",Position.of(2805, 10001, 0), Difficulty.HARD, false, false),
        GIANTS_DEN(8, 23, Catagory.DUNGEONS, "Giants' Den", "Houses fire, hill and moss giants",Position.of(1432, 9912, 0), Difficulty.MEDIUM, false, false),
        GODWARS_DUNGEON(9, 39, Catagory.DUNGEONS, "Godwars Dungeon", "Teleports you to the center of godwars dungeon",Position.of(2915, 3746, 0), Difficulty.HARD, false, true),
        JORMUNGANDS_PRISON(10, 40, Catagory.DUNGEONS, "Jormungand's Prison", "Contains basilisks and basilisk knights",Position.of(2448, 10402, 0), Difficulty.HARD, false, true),
        KALPHITE_CAVE(11, 36, Catagory.DUNGEONS, "Kalphite Lair", "Travel to the entrance of the kalphite lair",Position.of(3483, 9509, 0), Difficulty.HARD, false, true),
        KARUULM(12, 29, Catagory.DUNGEONS, "Karuulm Dungeon", "High level slayer tasks located here",Position.of(1311, 10188, 0), Difficulty.HARD, false, false),
        LUMBY_CAVES(13, 21, Catagory.DUNGEONS, "Lumbridge Swamp Caves", "Low level slayer tasks",Position.of(3168, 9572, 0), Difficulty.EASY, false, false),
        MOSLEHARMLESS(14, 30, Catagory.DUNGEONS, "Mos Le Harmless", "Cave and jungle horrors",Position.of(3746, 9373, 0), Difficulty.MEDIUM, false, false),
        SMOKE_DEVIL_DUNGEON(15, 32, Catagory.DUNGEONS, "Smoke Devil Dungeon", "Smoke devils and thermonuclear smoke devil",Position.of(2412, 3060, 0), Difficulty.HARD, false, false),
        STRONGHOLD_OF_SECURITY(16, 22, Catagory.DUNGEONS,"Stronghold Of Security", "Low level monsters",Position.of(3081, 3421, 0), Difficulty.MEDIUM, false, false),
        SMOKE_DUNGEON(17, 31, Catagory.DUNGEONS, "Smoke Dungeon", "Fire giants, dust devils, pyrefiends, fire elementals",Position.of(3203, 9379, 0), Difficulty.HARD, false, false),
        TAVERLY(18, 33, Catagory.DUNGEONS, "Taverly Dungeon", "Home to blue/black dragons and black demons",Position.of(2884, 9799, 0), Difficulty.HARD, false, false),
        WATERBIRTH(19, 34, Catagory.DUNGEONS, "Waterbirth Dungeon", "Contains dagannoths and dagannoth kings",Position.of(2443, 10146, 0), Difficulty.HARD, false, false),
        WATERFALL(20, 35, Catagory.DUNGEONS, "Waterfall Dungeon", "Home to fire giants, skeletons and spiders",Position.of(2573, 9866, 0), Difficulty.HARD, false, false),

        /*
         * Boss Teles
         */
        ABYSSAL_SIRE(0, 41, Catagory.BOSSES, "Abyssal Sire", "Can be killed on abyssal demon slayer task",Position.of(3038, 4771, 0), Difficulty.HARD, false, false),
        RUNE_DRAGS(13, 42, Catagory.BOSSES, "Rune/Adamant Dragons", "Home to rune and adamant dragons",Position.of(1568, 5075, 0), Difficulty.HARD, false, false),
        KRAKEN(11, 43, Catagory.BOSSES, "Kraken", "Teleports you to the kraken cove",Position.of(2292, 10008, 0), Difficulty.HARD, false, false),
        CERB(2, 44, Catagory.BOSSES, "Cerberus", "Can be killed on hellhound slayer task",Position.of(1310, 1252, 0), Difficulty.HARD, false, false),
        CORP(3, 45, Catagory.BOSSES, "Corporeal Beast", "The corporeal beast boss",Position.of(3157, 3670, 0), Difficulty.HARD, false, false),
        DAG_KINGS(4, 46, Catagory.BOSSES, "Dagannoth Kings", "Dagannoth rex, prime and supreme",Position.of(1912, 4367, 0), Difficulty.HARD, false, false),
        NEX(5, 47, Catagory.BOSSES, "Nex", "The fifth general: Nex",Position.of(2906, 5203, 0), Difficulty.HARD, false, false),
        DERANGED_ARCHAEOLOGIST(6, 48, Catagory.BOSSES, "Deranged Archaeologist", "Located on fossil island",Position.of(3677, 3804, 0), Difficulty.HARD, false, false),
        GIANT_MOLE(8, 49, Catagory.BOSSES, "Giant Mole", "The giant mole lair underneath falador park",Position.of(1764, 5179, 0), Difficulty.HARD, false, false),
        GOD_WARS(9, 50, Catagory.BOSSES, "Godwars", "Takes you to the center of godwars dungeon",Position.of(2916, 3746, 0), Difficulty.HARD, false, false),
        KALPHITE_QUEEN(10, 51, Catagory.BOSSES, "Kalphite Queen", "The kalphite queen lair",Position.of(3505, 9493, 0), Difficulty.HARD, false, false),
        LIZARDMAN(12, 52, Catagory.BOSSES, "Lizardman Shammans", "Lizardmans and lizardman shamans",Position.of(1445, 3699, 0), Difficulty.HARD, false, false),
        THE_NIGHTMARE(16, 53, Catagory.BOSSES, "The Nightmare", "The Nightmare of Ashihama",Position.of(3808, 9747, 1), Difficulty.HARD, false, false),
        THERMO(17, 54, Catagory.BOSSES, "Thermonuclear Smoke Devils", "Can be killed on smoke devil slayer tasks",Position.of(2412, 3060, 0), Difficulty.HARD, false, false),
        VORKATH(18, 55, Catagory.BOSSES, "Vorkath", "Vorkaths icy lair",Position.of(2272, 4051, 0), Difficulty.HARD, false, false),
        ZULRAH(20, 56, Catagory.BOSSES, "Zulrah", "The shrine of zulrah",Position.of(2205, 3057, 0), Difficulty.HARD, false, false),
        THE_FRAGMENT_OF_SEREN(15, 57, Catagory.BOSSES, "The Fragment of Seren", "The lair of the fragment of seren world boss",Position.of(2206, 4573, 0), Difficulty.EXPERT, false, true),
        ALCHEMICAL_HYDRA(1, 58, Catagory.BOSSES, "Alchemical Hydra", "Can be killed on hydra slayer tasks",Position.of(1352, 10258, 0), Difficulty.HARD, false, true),
        SARACHNIS(7, 59, Catagory.BOSSES, "Sarachnis", "A formidable foe inside the Forthos Dungeon",Position.of(1842, 9928, 0), Difficulty.HARD, false, true),
        SKOTIZO(14, 60, Catagory.BOSSES, "Skotizo", "Skotizos lair inside the catacombs",Position.of(1663, 10051, 0), Difficulty.HARD, false, true),
        ZALCANO(19, 61, Catagory.BOSSES, "Zalcano", "The skilling boss in prifddinas",Position.of(3034, 6067, 0), Difficulty.HARD, false, true),

        /*
         * Minigame Teles
         */
        LMS(4, 62, Catagory.MINIGAMES, "Last Man Standing", "The last man standing minigame",Position.of(3400, 3180, 0), Difficulty.NA, false, false),
        BARROWS(0, 63, Catagory.MINIGAMES, "Barrows", "The barrows minigame located in morytania",Position.of(3565, 3313, 0), Difficulty.NA, false, false),
        DUEL_ARENA(1, 64, Catagory.MINIGAMES, "Duel Arena", "The sand casino for degenerates",Position.of(3367, 3265, 0), Difficulty.NA, false, false),
        FIGHT_CAVES(2, 65, Catagory.MINIGAMES, "Fight Caves", "Home of the fire cape",Position.of(2443, 5171, 0), Difficulty.NA, false, false),
        MOTHERLODE_MINE(5, 66, Catagory.MINIGAMES, "Motherload Mine", "Useful for training mining",Position.of(3751, 5669, 0), Difficulty.NA, false, false),
        PURO_PURO(7, 67, Catagory.MINIGAMES, "Hunter - Puro Puro", "Hunter training area for catching implings",Position.of(2426, 4443, 0), Difficulty.NA, false, false),
        WARRIORS_GUILD(9, 68, Catagory.MINIGAMES, "Warriors Guild", "Kill cyclops for defenders here",Position.of(2880, 3546, 0), Difficulty.NA, false, false),
        WINTERTODT(10, 69, Catagory.MINIGAMES, "Wintertodt", "The firemaking minigame",Position.of(1630, 3957, 0), Difficulty.NA, false, false),
        RAIDS1(8, 70, Catagory.MINIGAMES, "Chambers of Xeric", "The chambers of xeric full raid",Position.of(1254, 3558, 0), Difficulty.EXPERT, false, false),
        PEST_CONTROL(6, 71, Catagory.MINIGAMES, "Pest Control", "Come and get your full void set",Position.of(2659, 2676, 0), Difficulty.NA, false, false),
        INFERNO(3, 72, Catagory.MINIGAMES, "Inferno", "Fight the last 3 waves for an infernal cape",Position.of(2496, 5116, 0), Difficulty.IMPOSSIBLE, false, false),
        ZALCANO_MINIGAME(11, 73, Catagory.MINIGAMES, "Zalcano", "The skilling boss in prifddinas",Position.of(3034, 6067, 0), Difficulty.HARD, false, true),
        CASTLE_WARS(12, 74, Catagory.MINIGAMES, "Castle Wars", "The Castle Wars minigame",Position.of(2439, 3091, 0), Difficulty.HARD, false, true),
        HUNNLEF(13, 75, Catagory.MINIGAMES, "The Gauntlet", "Take on the almighty Hunnlef",Position.of(3030, 6126, 1), Difficulty.HARD, false, true),
        RAIDS2(14, 76, Catagory.MINIGAMES, "Theatre of Blood", "The theatre of blood full raid", Position.of(3668, 3219, 0), Difficulty.HARD, false, true),
        SIX_JAD(15, 77, Catagory.MINIGAMES, "Six Jad Challenge", "Take on 6 Jads at once.",Position.of(2542, 5111, 0), Difficulty.HARD, false, true),
        FOSSIL_ISLAND_BANK(16, 78, Catagory.MINIGAMES, "Fossil Island Bank", "Quick access to the fossil island bank",Position.of(3737, 3804, 0), Difficulty.HARD, false, true),
        ICE_MOUNTAIN(17, 79, Catagory.MINIGAMES, "Ice Mountain", "Because who actually wants to walk there",Position.of(3025, 3473, 0), Difficulty.HARD, false, true),
        MYTHS_GUILD(18, 80, Catagory.MINIGAMES, "Myths Guild", "Teleports you inside of the Myths Guild",Position.of(2457, 2851, 0), Difficulty.HARD, false, true),
        TREE_GNOME_VILLAGE(19, 81, Catagory.MINIGAMES, "Tree Gnome Village", "Quick access to a fruit tree patch",Position.of(2501, 3191, 0), Difficulty.HARD, false, true),
        EMPTY25(20, 82, Catagory.MINIGAMES, "", "",Position.of(2884, 3588, 0), Difficulty.HARD, false, true),

        /*
         * Skilling Teles
         */
        PISCATORIS(13, 83, Catagory.SKILLING, "Hunter - Piscatoris", "Low-medium level hunter area",Position.of(2332, 3608, 0), Difficulty.NA, false, false),
        CRAFTING_GUILD(6, 84, Catagory.SKILLING, "Crafting Guild", "A resourceful area with a nearby bank",Position.of(2933, 3291, 0), Difficulty.NA, false, false),
        FISHING_GUILD(10, 85, Catagory.SKILLING, "Fishing Guild", "Requires level 68 fishing to enter",Position.of(2611, 3390, 0), Difficulty.NA, false, false),
        MINING_GUILD(12, 86, Catagory.SKILLING, "Mining Guild", "Requires level 60 mining to enter",Position.of(3022, 3337, 0), Difficulty.NA, false, false),
        WOODCUT_GUILD(20, 87, Catagory.SKILLING, "Woodcutting Guild", "Train woodcutting and cut redwoods",Position.of(1661, 3505, 0), Difficulty.NA, false, false),
        GNOME_COURSE(11, 88, Catagory.SKILLING, "Gnome Stronghold Agility", "Requires level 1 agility",Position.of(2471, 3437, 0), Difficulty.NA, false, false),
        ESSENCE_MINE(8,126, Catagory.SKILLING, "Rune Essence Mine", "Used for mining pure essence",Position.of(2911, 4832, 0), Difficulty.NA, false, true),
        CATHERBY_FISHING(5, 102, Catagory.SKILLING, "Fishing - Catherby", "Great for medium-high level fishing",Position.of(2838, 3435, 0), Difficulty.NA, false, false),
        AERIAL_FISHING(0, 100, Catagory.SKILLING, "Aerial Fishing", "Trains your hunter and fishing skills",Position.of(1368, 3633, 0), Difficulty.NA, false, false),
        PRIFDDINAS(15, 98, Catagory.SKILLING, "Prifddinas", "The elven city of Prifddinas",Position.of(3263, 6066, 0), Difficulty.NA, false, false),
        WOODLAND_HUNTER(19, 127, Catagory.SKILLING, "Kouren Woodland", "Useful for training hunter and fishing",Position.of(2341, 3597, 0), Difficulty.NA, false, true),
        DRAYNOR_COURSE(7, 89, Catagory.SKILLING, "Draynor Rooftop Course", "Requires level 10 agility",Position.of(3105, 3272, 0), Difficulty.NA, false, false),
        ALKHARID_COURSE(1, 90, Catagory.SKILLING, "Al-Kharid Rooftop Course", "Requires level 20 agility",Position.of(3281, 3197, 0), Difficulty.NA, false, false),
        VARROCK_COURSE(18, 91, Catagory.SKILLING, "Varrock Rooftop Course", "Requires level 30 agility",Position.of(3224, 3415, 0), Difficulty.NA, false, false),
        PRIF_COURSE(3, 92, Catagory.SKILLING, "Prifddinas Rooftop Course", "Requires level 75 agility",Position.of(3251, 6109, 0), Difficulty.NA, false, false),
        CANNIFIS_COURSE(4, 93, Catagory.SKILLING, "Canifis Rooftop Course", "Requires level 40 agility",Position.of(3505, 3486, 0), Difficulty.NA, false, false),
        FALADOR_COURSE(9, 94, Catagory.SKILLING, "Falador Rooftop Course", "Requires level 50 agility",Position.of(3037, 3339, 0), Difficulty.NA, false, false),
        SEERS_COURSE(17, 95, Catagory.SKILLING, "Seers Rooftop Course", "Requires level 60 agility",Position.of(2728, 3485, 0), Difficulty.NA, false, false),
        ARDOUGNE_COURSE(2, 97, Catagory.SKILLING, "Ardougne Rooftop Course", "Requires level 90 agility",Position.of(2673, 3296, 0), Difficulty.NA, false, false),
        RELLEKKA_COURSE(16, 96, Catagory.SKILLING, "Rellekka Rooftop Course", "Requires level 80 agility",Position.of(2629, 3677, 0), Difficulty.NA, false, false),
        PISCATORIS_FISHING(14, 128, Catagory.SKILLING, "Piscatoris Fishing", "Medium-high level fishing spots",Position.of(2341, 3666, 0), Difficulty.NA, false, true),
        WILD_COURSE(16, 99, Catagory.SKILLING, "Wilderness Course", "Requires level 52 agility",Position.of(2998, 3914, 0), Difficulty.NA, true, false),
        BARB_VILLAGE_FISH(18, 101, Catagory.SKILLING, "Barbarian Village", "15 fishing and higher",Position.of(3105, 3432, 0), Difficulty.NA, false, false),
        DRAYNOR_WOODCUTTING(20, 103, Catagory.SKILLING, "Woodcutting - Draynor", "Level 30 woodcutting area",Position.of(3091, 3225, 0), Difficulty.NA, false, false),
        KOUREND_FISHING(21, 200,Catagory.SKILLING, "Kourend Anglers", "82 fishing and higher",Position.of(1829, 3782, 0), Difficulty.NA, false, false),
        KARAMJA_FISHING(22, 201, Catagory.SKILLING, "Karamja Docks", "",Position.of(2925, 3177, 0), Difficulty.NA, false, false),
        LUMB_FISHING(23, 202, Catagory.SKILLING, "Lumbridge River", "Level 1 fishing spots",Position.of(3243, 3157, 0), Difficulty.NA, false, false),
        BARB_FISHING(24, 203, Catagory.SKILLING, "Barbarian Fishing", "",Position.of(2503, 3488, 0), Difficulty.NA, false, false),
        HUNTER_BIRDHOUSES(26, 204, Catagory.SKILLING, "Hunter - Birdhouses", "Travel to the House on the Hill",Position.of(3763, 3870, 1), Difficulty.NA, false, false),
        MINING_ALKHARID(28, 205, Catagory.SKILLING, "Mining - Al-Kharid", "Low-medium level mining spots",Position.of(3290, 3276, 0), Difficulty.NA, false, false),
        FELDIP_HILLS(29, 206, Catagory.SKILLING, "Hunter - Feldip Hills", "Low level hunter area",Position.of(2603, 2932, 0), Difficulty.NA, false, false),
        ABYSS(30, 207, Catagory.SKILLING, "Runecrafting - Abyss", "Used for traveling to runecrafting altars",Position.of(3016, 4838, 0), Difficulty.NA, false, false),
        WOODCUT_SEERS(31, 208, Catagory.SKILLING, "Woodcutting - Seers", "Level 45 woodcutting area",Position.of(2730, 3500, 0), Difficulty.NA, true, false),
        MINING_VARROCK(32, 209, Catagory.SKILLING, "Mining - Varrock", "Level 1-15 mining area",Position.of(3285, 3372, 0), Difficulty.NA, false, false),
        SNOW_HUNTER(33, 210, Catagory.SKILLING, "Snow Hunter", "",Position.of(2723, 3771, 0), Difficulty.NA, false, false),
        DWARVEN_MINE(35, 211, Catagory.SKILLING, "Dwarven Mine", "",Position.of(3043, 9817, 0), Difficulty.NA, false, false),
        DENSE_ESSENCE(37, 212, Catagory.SKILLING, "Dense Essence", "",Position.of(1761, 3851, 0), Difficulty.NA, false, false),
        GEM_MINE(38, 213, Catagory.SKILLING, "Gem Mine", "",Position.of(2825, 2997, 0), Difficulty.NA, false, false),
        JATIZO_MINE(39, 214, Catagory.SKILLING, "Jatizo Mine", "",Position.of(2406, 10190, 0), Difficulty.NA, false, false),
        ARDY_MARKET(40, 215, Catagory.SKILLING, "Ardougne Market", "",Position.of(2661, 3305, 0), Difficulty.NA, false, false),
        FARMING_GUILD(41, 216, Catagory.SKILLING, "Farming Guild", "Requires level 45 farming to enter",Position.of(1248, 3718, 0), Difficulty.NA, false, false),
        FARMING_ARDY(42, 217, Catagory.SKILLING, "Farming - Ardougne", "Travel to the Ardougne farming patches",Position.of(2673, 3375, 0), Difficulty.NA, false, false),
        FARMING_CANAFIS(43, 217, Catagory.SKILLING, "Farming - Canafis", "Travel to the Canafis farming patches",Position.of(3606, 3533, 0), Difficulty.NA, false, false),
        FARMING_CATH(43, 217, Catagory.SKILLING, "Farming - Catherby", "Travel to the Catherby farming patches",Position.of(2816, 3464, 0), Difficulty.NA, false, false),
        FARMING_FALLY(43, 217, Catagory.SKILLING, "Farming - Falador", "Travel to the Falador farm patches",Position.of(3061, 3309, 0), Difficulty.NA, false, false),
        FARMING_TROLL(43, 217, Catagory.SKILLING, "Farming - Trollheim", "Travel to the Trollheim herb patch",Position.of(2823, 3695, 0), Difficulty.NA, false, false),
        FARMING_ZEAH(43, 217, Catagory.SKILLING, "Farming - Zeah", "Travel to the Zeah farming patches",Position.of(1742, 3551, 0), Difficulty.NA, false, false),

        /*
         * Wilderness Teles
         */
        MAGE_BANK(15, 104, Catagory.WILDERNESS, "Mage Bank", "Teleports you inside of the mage bank",Position.of(2539, 4717, 0), Difficulty.NA, true, false),
        GREEN_DRAGONS_WEST(9, 105, Catagory.WILDERNESS, "Green Dragons - West", "Level 13 wilderness, near dark warriors fortress", Position.of(2994, 3624, 0), Difficulty.NA, true, false),
        CHAOS_TEMPLE(2, 106, Catagory.WILDERNESS, "Chaos Temple", "Level 12 wilderness, home of elder chaos druids",Position.of(3236, 3638, 0), Difficulty.NA, true, false),
        REVENANT_CAVE(16, 107, Catagory.WILDERNESS, "Revenant Caves - North", "Teleports you outside of the north revenant caves",Position.of(3128, 3827, 0), Difficulty.NA, true, false),
        GREEN_DRAGONS_EAST(8, 108, Catagory.WILDERNESS, "Green Dragons - East", "Level 18 wilderness",Position.of(3343, 3663, 0), Difficulty.NA, true, false),
        GRAVEYARD(7, 109, Catagory.WILDERNESS, "Graveyard", "Located south of the chaos altar",Position.of(3149, 3669, 0), Difficulty.NA, true, false),
        FORGOTTEN_CEMETARY(4, 110, Catagory.WILDERNESS, "Forgotten Cemetary", "Located directly north of ferox enclave",Position.of(2980, 3765, 0), Difficulty.NA, true, false),
        DEMONIC_RUINS(3, 111, Catagory.WILDERNESS, "Demonic Ruins", "Located in level 46 wilderness",Position.of(3286, 3881, 0), Difficulty.NA, true, false),
        BLACK_CHINS(0, 117, Catagory.WILDERNESS, "Black Chinchompas", "Level 32 wilderness",Position.of(3145, 3771, 0), Difficulty.NA, true, true),
        FEROX_ENCLAVE(4, 118, Catagory.WILDERNESS, "Ferox Enclave", "The infamous ferox enclave",Position.of(3134, 3629, 0), Difficulty.NA, false, true),
        ICE_PLATEAU(10, 112, Catagory.WILDERNESS, "Ice Plateau", "The ice plateau in deep wilderness",Position.of(2970, 3880, 0), Difficulty.NA, true, false),
        LAVA_MAZE(13, 113, Catagory.WILDERNESS, "Lava Maze", "The center of the deep wilderness lava maze",Position.of(3076, 3846, 0), Difficulty.NA, true, false),
        LAVA_DRAGONS(12, 114, Catagory.WILDERNESS, "Lava Dragons", "Level 43 wilderness lava dragons",Position.of(3201, 3859, 0), Difficulty.NA, true, false),
        FOUNTAIN_OF_RUNE(5, 115, Catagory.WILDERNESS, "Fountain of Rune", "The fountain of rune, deep wilderness",Position.of(3357, 3872, 0), Difficulty.NA, true, false),
        BOOTY_BAY(1, 116, Catagory.WILDERNESS, "Booty Bay", "Booty bay wilderness camp, safe teleport", Position.of(3357, 3808, 0), Difficulty.NA, false, false),
        GALVEK1(6, 59, Catagory.WILDERNESS, "Galvek", "A very difficult dragon",Position.of(3374, 3643, 0), Difficulty.HARD, true, true),
        WILDEXPANSIONBANK(18, 120, Catagory.WILDERNESS, "Wild Expansion Bank", "The king of Scorpions",Position.of(3166, 4055, 0), Difficulty.NA, true, true),
        LEVEL_ONE_DEF_WILD(14, 121, Catagory.WILDERNESS, "Level 1 Defence Only", "Only those with level 1 Defence can travel here",Position.of(3103, 3503, 0), Difficulty.NA, true, true),
        REVENANT_CAVE_TWO(17, 122, Catagory.WILDERNESS, "Revenant Caves - South", "Teleports you outside of the south revenant caves",Position.of(3075, 3651, 0), Difficulty.NA, true, true),
        KING_BLACK_DRAGON(11, 125, Catagory.WILDERNESS, "King Black Dragon", "Just south of the King Black Dragon ladder",Position.of(3024, 3833, 0), Difficulty.NA, true, true),
        EMPTY32(19, 123, Catagory.WILDERNESS, "", "",Position.of(2884, 3588, 0), Difficulty.NA, true, true),
        EMPTY33(20, 124, Catagory.WILDERNESS, "", "",Position.of(2884, 3588, 0), Difficulty.NA, true, true),

        /*
         * Cities Teles
         */
        AL_KHARID(0, 104, Catagory.CITIES, "Al-Kharid", "I don't really like sand..",Position.of(3289, 3183, 0), Difficulty.NA, false, false),
        ARDOUGNE(1, 105, Catagory.CITIES, "Ardougne", "The city of thieving, and a few other things", Position.of(2660, 3304, 0), Difficulty.NA, false, false),
        BRIMHAVEN(2, 106, Catagory.CITIES, "Brimhaven", "What can I even do here?",Position.of(2713, 9564, 0), Difficulty.NA, false, false),
        BURTHROPE(3, 107, Catagory.CITIES, "Burthorpe", "Near the Warrior's Guild, and some trolls",Position.of(2899, 3546, 0), Difficulty.NA, false, false),
        CAMELOT(5, 108, Catagory.CITIES, "Camelot", "Camelot/Seers Village",Position.of(2726, 3485, 0), Difficulty.NA, false, false),
        CANAFIS(6, 109, Catagory.CITIES, "Canafis", "Home of the Swampman",Position.of(3501, 3483, 0), Difficulty.NA, false, false),
        CATHERBY(7, 110, Catagory.CITIES, "Catherby", "Convenient area for fishing and farming",Position.of(2809, 3435, 0), Difficulty.NA, false, false),
        DARKMEYER(8, 111, Catagory.CITIES, "Darkmeyer", "I can probably kill Vyrewatch here",Position.of(3605, 3367, 0), Difficulty.NA, false, false),
        DRAYNOR(9, 117, Catagory.CITIES, "Draynor Village", "Beginner area for fishing and agility",Position.of(3105, 3251, 0), Difficulty.NA, false, true),
        EDGEVILLE(10, 118, Catagory.CITIES, "Edgeville", "The home area of OSPK",Position.of(3103, 3503, 0), Difficulty.NA, false, true),
        FALADOR(11, 112, Catagory.CITIES, "Falador", "Home to the White Knights",Position.of(2965, 3379, 0), Difficulty.NA, false, false),
        KOUREND(12, 113, Catagory.CITIES, "Great Kourend", "Teleports you to the center of Great Kourend",Position.of(1644, 3674, 0), Difficulty.NA, false, false),
        LUMBRIDGE(13, 114, Catagory.CITIES, "Lumbridge", "The iconic city of Lumbridge",Position.of(3224, 3219, 0), Difficulty.NA, false, false),
        PORT_KHAZARD(14, 115, Catagory.CITIES, "Port Khazard", "Convenient crafting area for ironmen",Position.of(2661, 3160, 0), Difficulty.NA, false, false),
        PRIFDDINAS_CITY(15, 116, Catagory.CITIES, "Prifddinas", "The magical, elven city", Position.of(3263, 6066, 0), Difficulty.NA, false, false),
        //RELLEKKA(16, 59, Catagory.CITIES, "Rellekka", "Home to a few drunk sailors and some rock crabs",Position.of(2643, 3679, 0), Difficulty.HARD, false, true),
        TREE_GNOME_STRONGHOLD(17, 120, Catagory.CITIES, "Tree Gnome Stronghold", "Convenient area for beginner agility and Nieves cave",Position.of(2438, 3429, 0), Difficulty.NA, false, true),
        VARROCK(18, 121, Catagory.CITIES, "Varrock - West", "Teleports you to the western bank of Varrock",Position.of(3213, 3424, 0), Difficulty.NA, false, true),
        VARROCK_WEST(4, 122, Catagory.CITIES, "Smithing - Varrock", "One of the closest anvils to a bank",Position.of(3187, 3426, 0), Difficulty.NA, false, true),
        //YANILLE(19, 123, Catagory.CITIES, "Yanille", "Home to the Wizards guild and the Nightmare Zone",Position.of(2605, 3092, 0), Difficulty.NA, false, true),
        ZANARIS(20, 124, Catagory.CITIES, "Zanaris", "Fairies, zygomites and puro puro",Position.of(2452, 4467, 0), Difficulty.NA, false, true),
        DONATOR_ZONE_DUNGEON(0, 130, Catagory.DONATOR, "Donator Zone: Dungeon", "Teleports you to the Donator Zone Dungeon",Position.of(1250, 4301, 0), Difficulty.NA, false, true),
        DONATOR_ZONE(0, 130, Catagory.DONATOR, "Donator Zone", "Teleports you to the Donator Zone",Position.of(2078, 4264, 0), Difficulty.NA, false, true),
        BARBARIAN_OUTPOST(0, 131, Catagory.EVENTS, "Barbarian Outpost", "The Barbarian Outpost area", Position.of(2520,3570, 0), Difficulty.EASY, false, false),
        SEREN(0, 131, Catagory.EVENTS, "Fragment of Seren", "Teleport to the world boss Fragment of Seren",Position.of(2206, 4573, 0), Difficulty.NA, false, true),
        PIRATESSHIP(0, 131, Catagory.EVENTS, "Pirates Ship", "Teleport to Booty Bay for the Pirates Ship event",Position.of(3357, 3808, 0), Difficulty.NA, false, true),
        TIMERIFT(0, 131, Catagory.EVENTS, "Time Rift", "Travel back in time to the Time Rift event!",Position.of(2287, 2918, 0), Difficulty.NA, false, true),

        ;

        private final int slot; //0-29
        private final int favoriteSlot;
        private final Catagory catagory;
        private final String name;
        private final String info;
        private final Position position;
        private final Difficulty difficulty;
        private final boolean wilderness;
        private final boolean showStar;

        /**
         * Gets all of the teleports retaining to a certain catagory.
         * @param catagory  The catagory.
         * @return  A list of teleports that match the catagory provided, or an empty list if none are found.
         */
        public static ArrayList<Teleport> forCatagory(Catagory catagory) {
            ArrayList<Teleport> teleports = Lists.newArrayList();
            for (Teleport teleport : Teleport.values()) {
                if (teleport.catagory.equals(catagory)) {
                    teleports.add(teleport);
                }
            }
            return teleports;
        }

    }

    /**
     * Catagory for teleports.
     */
    public enum Catagory {
        MONSTERS,
        DUNGEONS,
        BOSSES,
        MINIGAMES,
        SKILLING,
        WILDERNESS,
        CITIES,
        DONATOR,
        EVENTS
    }

    private enum Difficulty {
        NA,
        BEGINNER,
        EASY,
        MEDIUM,
        HARD,
        EXPERT,
        IMPOSSIBLE
    }

    /**
     * Opens the teleport interface with the desired catagory.
     * @param catagory  The catagory.
     */
    /*public void sendInterface(Catagory catagory) {
        player.openInterface(InterfaceType.MAIN, Interface.TELEPORTS);
        player.getTeleports().setLastCatagory(catagory);
        player.getTeleports().setSelectedTeleport(null);
        sendTeleports();
        sendInfo(true);
    }*/

    /**
     * Opens the teleports interface with the last catagory used.
     */
    public void sendInterface() {
        player.openInterface(InterfaceType.MAIN, Interface.TELEPORTS);
        player.getTeleports().setLastCatagory(Catagory.MONSTERS);
        //player.getTeleports().setSelectedTeleport(selectedTeleport);
        sendTeleports();
        sendInfo(true);
        if (player.getTeleports().getLastTeleport() == null) {
            player.getPacketSender().sendString(Interface.TELEPORTS, 423, "None!");
        } else {
            player.getPacketSender().sendString(Interface.TELEPORTS, 423, "" + player.getTeleports().getLastTeleport().getName());
        }
    }

    public void sendInterfaceWildyTab() {
        player.openInterface(InterfaceType.MAIN, Interface.TELEPORTS);
        player.getTeleports().setLastCatagory(Catagory.WILDERNESS);
        player.getTeleports().setSelectedTeleport(selectedTeleport);
        sendTeleports();
        sendInfo(selectedTeleport == null);
    }

    static void handleFavorite(Player player){
       /* if(player.favoriteTeleports.contains(player.getTeleports().getSelectedTeleport().getName())){
            player.favoriteTeleports.remove((Object)player.getTeleports().getSelectedTeleport().slot);
            player.sendMessage("You have removed " + player.getTeleports().getSelectedTeleport().getName() + " from your favorite teleports.");
        } else {*/
        player.favoriteTeleports.add(player.getTeleports().getSelectedTeleport().favoriteSlot);
        player.sendMessage("You have added " + player.getTeleports().getSelectedTeleport().getName() + " to your favorite teleports.");
            /*for (int i = 0; i < player.favoriteTeleports.size(); i++) {
                TeleportList.Teleport teleport = TeleportList.getTeleportForId(player.favoriteTeleports.get(i));
                player.getPacketSender().sendString(Interface.TELEPORTS, 279, ""+teleport.getName());
            }*/
        // }
    }


    /**
     * Sends the component information such as text and sets action listeners.
     */
    private void sendTeleports() {
        List<Teleport> teleports = Teleport.forCatagory(player.getTeleports().getLastCatagory());
        IntStream.of(TEXT_COMPONENTS).forEach(i -> {
            boolean hide = teleports.stream().noneMatch(teleport -> i == TEXT_COMPONENTS[teleport.getSlot()]);
            if (hide) {
                player.getPacketSender().sendString(Interface.TELEPORTS, i, "");
            }
        });
        IntStream.of(BUTTON_COMPONENTS).forEach(i -> {
            boolean hide = teleports.stream().noneMatch(teleport -> i == BUTTON_COMPONENTS[teleport.getSlot()]);
            player.getPacketSender().setHidden(Interface.TELEPORTS, i, hide);
        });
        teleports.forEach(t -> {
            player.getPacketSender().sendString(Interface.TELEPORTS, TEXT_COMPONENTS[t.getSlot()], t.getName());
            player.getPacketSender().sendString(Interface.TELEPORTS, INFO_COMPONENTS[t.getSlot()], t.getInfo());
            player.getPacketSender().setHidden(Interface.TELEPORTS, BUTTON_COMPONENTS[t.getSlot()], false);
        });
    }

    /**
     * Selects a catagory and sends the interface information.
     * @param index     The catagory ordinal
     */
    private void selectCatagory(int index) {
        Catagory catagory = Catagory.values()[index - 28];
        player.getTeleports().setLastCatagory(catagory);
        player.getTeleports().setSelectedTeleport(null);
        sendTeleports();
        player.getPacketSender().sendString(Interface.TELEPORTS, 423, "" + player.getTeleports().getLastTeleport().getName());
        if (catagory == Catagory.MINIGAMES) {
            player.getPacketSender().setHidden(877, 349, true);
            player.getPacketSender().setHidden(877, 350, true);
            player.getPacketSender().setHidden(877, 351, true);
            player.getPacketSender().setHidden(877, 352, true);
            player.getPacketSender().setHidden(877, 353, true);
            player.getPacketSender().setHidden(877, 354, true);
            player.getPacketSender().setHidden(877, 355, true);
            player.getPacketSender().setHidden(877, 356, true);
            player.getPacketSender().setHidden(877, 357, true);
            player.getPacketSender().setHidden(877, 367, true);
            player.getPacketSender().setHidden(877, 447, true);
            player.getPacketSender().setHidden(877, 448, true);
        }
        if (catagory == Catagory.BOSSES) {
            player.getPacketSender().setHidden(877, 315, false);
            player.getPacketSender().setHidden(877, 316, false);
            player.getPacketSender().setHidden(877, 317, false);
            player.getPacketSender().setHidden(877, 318, false);
            player.getPacketSender().setHidden(877, 319, false);
            player.getPacketSender().setHidden(877, 320, false);
            player.getPacketSender().setHidden(877, 321, false);
            player.getPacketSender().setHidden(877, 322, false);
            player.getPacketSender().setHidden(877, 323, false);
            player.getPacketSender().setHidden(877, 324, false);
            player.getPacketSender().setHidden(877, 347, false);
            player.getPacketSender().setHidden(877, 348, false);
            player.getPacketSender().setHidden(877, 349, false);
            player.getPacketSender().setHidden(877, 350, false);
            player.getPacketSender().setHidden(877, 351, false);
            player.getPacketSender().setHidden(877, 352, false);
            player.getPacketSender().setHidden(877, 353, false);
            player.getPacketSender().setHidden(877, 354, false);
            player.getPacketSender().setHidden(877, 355, false);
            player.getPacketSender().setHidden(877, 366, false);
        }
        if (catagory == Catagory.MONSTERS) {
            player.getPacketSender().setHidden(877, 315, false);
            player.getPacketSender().setHidden(877, 316, false);
            player.getPacketSender().setHidden(877, 317, false);
            player.getPacketSender().setHidden(877, 318, false);
            player.getPacketSender().setHidden(877, 319, false);
            player.getPacketSender().setHidden(877, 320, false);
            player.getPacketSender().setHidden(877, 321, false);
            player.getPacketSender().setHidden(877, 322, false);
            player.getPacketSender().setHidden(877, 323, false);
            player.getPacketSender().setHidden(877, 324, false);
            player.getPacketSender().setHidden(877, 347, false);
            player.getPacketSender().setHidden(877, 348, false);
            player.getPacketSender().setHidden(877, 349, false);
            player.getPacketSender().setHidden(877, 350, false);
            player.getPacketSender().setHidden(877, 351, false);
            player.getPacketSender().setHidden(877, 352, false);
            player.getPacketSender().setHidden(877, 353, false);
            player.getPacketSender().setHidden(877, 354, false);
            player.getPacketSender().setHidden(877, 355, false);
            player.getPacketSender().setHidden(877, 366, false);
        }
        if (catagory == Catagory.DUNGEONS) {
            player.getPacketSender().setHidden(877, 315, false);
            player.getPacketSender().setHidden(877, 316, false);
            player.getPacketSender().setHidden(877, 317, false);
            player.getPacketSender().setHidden(877, 318, false);
            player.getPacketSender().setHidden(877, 319, false);
            player.getPacketSender().setHidden(877, 320, false);
            player.getPacketSender().setHidden(877, 321, false);
            player.getPacketSender().setHidden(877, 322, false);
            player.getPacketSender().setHidden(877, 323, false);
            player.getPacketSender().setHidden(877, 324, false);
            player.getPacketSender().setHidden(877, 347, false);
            player.getPacketSender().setHidden(877, 348, false);
            player.getPacketSender().setHidden(877, 349, false);
            player.getPacketSender().setHidden(877, 350, false);
            player.getPacketSender().setHidden(877, 351, false);
            player.getPacketSender().setHidden(877, 352, false);
            player.getPacketSender().setHidden(877, 353, false);
            player.getPacketSender().setHidden(877, 354, false);
            player.getPacketSender().setHidden(877, 355, false);
            player.getPacketSender().setHidden(877, 366, false);
        }
        if (catagory == Catagory.SKILLING) {
            player.getPacketSender().setHidden(877, 315, false);
            player.getPacketSender().setHidden(877, 316, false);
            player.getPacketSender().setHidden(877, 317, false);
            player.getPacketSender().setHidden(877, 318, false);
            player.getPacketSender().setHidden(877, 319, false);
            player.getPacketSender().setHidden(877, 320, false);
            player.getPacketSender().setHidden(877, 321, false);
            player.getPacketSender().setHidden(877, 322, false);
            player.getPacketSender().setHidden(877, 323, false);
            player.getPacketSender().setHidden(877, 324, false);
            player.getPacketSender().setHidden(877, 347, false);
            player.getPacketSender().setHidden(877, 348, false);
            player.getPacketSender().setHidden(877, 349, false);
            player.getPacketSender().setHidden(877, 350, false);
            player.getPacketSender().setHidden(877, 351, false);
            player.getPacketSender().setHidden(877, 352, false);
            player.getPacketSender().setHidden(877, 353, false);
            player.getPacketSender().setHidden(877, 354, false);
            player.getPacketSender().setHidden(877, 355, false);
            player.getPacketSender().setHidden(877, 366, false);
        }
    }

    /**
     * Sends teleport information above the teleport button.
     * @param clear     If the info should be cleared.
     */
    private void sendInfo(boolean clear) {
        player.getPacketSender().sendString(Interface.TELEPORTS, 427, clear ? "" : "" + player.getTeleports().getSelectedTeleport().getName());
        player.getPacketSender().sendString(Interface.TELEPORTS, 423, clear ? "" : "" + player.getTeleports().getLastTeleport().getName());
    }

    /**
     * Selects a teleport.
     * @param component     The component id.
     */
    private void clickButton(int component) {
        List<Teleport> teleportList = Teleport.forCatagory(player.getTeleports().getLastCatagory());
        for (Teleport teleport : teleportList) {
            int button = BUTTON_COMPONENTS[teleport.getSlot()];
            if (button == component) {
                player.getTeleports().setSelectedTeleport(teleport);
                sendInfo(false);
            }
        }
    }

    /**
     * Teleports the player to the last selected teleport.
     */
    private void teleport() {
        if (player.getTeleports().getSelectedTeleport() == null) {
            player.sendMessage("You must select a teleport first!");
        }
        teleports.teleport(player, player.getTeleports().getSelectedTeleport().getPosition());
        player.closeInterface(InterfaceType.MAIN);
        player.getTeleports().setLastTeleport(player.getTeleports().getSelectedTeleport());
    }

    public static void teleportPrevious() {
        Teleport previousTeleport = player.getTeleports().getLastTeleport();
        if (previousTeleport != null) {
            teleports.teleport(player, player.getTeleports().getLastTeleport().getPosition());
        } else {
            player.sendMessage("You haven't teleported previously.");
        }
    }

    /**
     * Formats selected teleport into color wrapped varient.
     * @return
     */
    private String getWilderness() {
        Teleport selectedTeleport = player.getTeleports().getSelectedTeleport();
        boolean wilderness = selectedTeleport != null && selectedTeleport.wilderness;
        return wilderness ? Color.RED.wrap("Wilderness") : Color.GREEN.wrap("<col=0fd60f>Non-wilderness");
    }

   /* static {
        InterfaceHandler.register(Interface.TELEPORTS, h-> {
            h.actions[315] = (SlotAction) (player, slot)-> handleFavorite(player);
        });
        InterfaceHandler.register(Interface.TELEPORTS, (interfaceHandler -> {
            for (int component = 35; component < 41; component++) {
                int finalComponent = component;
                interfaceHandler.actions[component] = (SimpleAction) player -> player.getTeleports().selectCatagory(finalComponent);
                //interfaceHandler.actions[391] = (SimpleAction) player -> player.getTeleports().selectCatagory(7);
                interfaceHandler.actions[392] = (SimpleAction) player -> player.sendMessage("Work in progress!");
                interfaceHandler.actions[400] = (SimpleAction) player -> player.sendMessage("Work in progress!");
            }
            for (int component : BUTTON_COMPONENTS) {
                interfaceHandler.actions[component] = (SimpleAction) player -> player.getTeleports().clickButton(component);
            }
            for (int component : FAVORITE_BUTTONS) {
                int finalComponent = component;
                interfaceHandler.actions[component] = (SimpleAction) player -> player.getTeleports().clickFavorite(finalComponent);
            }
            interfaceHandler.actions[TELEPORT_BUTTON] = (SimpleAction) player -> player.getTeleports().teleport();
            interfaceHandler.actions[PREVIOUS_BUTTON] = (SimpleAction) player -> teleportPrevious();
        }));
    }*/

}
