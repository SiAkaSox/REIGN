package io.ruin.model.inter.newteleports;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.handlers.TeleportInterface;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.impl.edgeville.Teleporter;
import lombok.Getter;
import lombok.Setter;

public class BossesInter {

    public static int MONSTERS = 877;
    public static int DUNGEONS = 986;
    public static int BOSSES = 987;
    public static int MINIGAMES = 988;
    public static int SKILLING = 989;
    public static int WILDERNESS = 990;
    public static int CITIES = 991;
    public static int MISC = 992;
    public static int EVENTS = 993;

    @Setter @Getter private static transient Player p;

    static {

            InterfaceHandler.register(BOSSES, h -> {
                h.actions[19] = (SlotAction) (player, slot) -> player.getTeleporter().openInterface(player);
                h.actions[36] = (SlotAction) (player, slot) -> DungeonInter.openInterface(player);
                h.actions[37] = (SlotAction) (player, slot) -> openInterface(player);
                h.actions[38] = (SlotAction) (player, slot) -> MinigameInter.openInterface(player);
                h.actions[39] = (SlotAction) (player, slot) -> SkillingInter.openInterface(player);
                h.actions[40] = (SlotAction) (player, slot) -> WildernessInter.openInterface(player);
                h.actions[391] = (SlotAction) (player, slot) -> CityInter.openInterface(player);
                h.actions[392] = (SlotAction) (player, slot) -> MiscInter.openInterface(player);
                h.actions[400] = (SlotAction) (player, slot) -> Favorites.openInterface(player);
                h.actions[425] = (SlotAction) (player, slot) -> Teleporter.teleportPrevious(player);

                h.actions[368] = (SlotAction) (player, slot) -> selectAbyssalSire(player);
                h.actions[369] = (SlotAction) (player, slot) -> selectAlchemical(player);
                h.actions[370] = (SlotAction) (player, slot) -> selectCaveKraken(player);
                h.actions[371] = (SlotAction) (player, slot) -> selectCerb(player);
                h.actions[372] = (SlotAction) (player, slot) -> selectCorp(player);
                h.actions[373] = (SlotAction) (player, slot) -> selectDagKings(player);
                h.actions[374] = (SlotAction) (player, slot) -> selectGiantMole(player);
                h.actions[375] = (SlotAction) (player, slot) -> selectGodwars(player);
                h.actions[376] = (SlotAction) (player, slot) -> selectKalphiteQueen(player);
                h.actions[377] = (SlotAction) (player, slot) -> selectLizardman(player);
                h.actions[378] = (SlotAction) (player, slot) -> selectNex(player);
                h.actions[379] = (SlotAction) (player, slot) -> selectNightmare(player);
                h.actions[380] = (SlotAction) (player, slot) -> selectRune(player);
                h.actions[381] = (SlotAction) (player, slot) -> selectSarachnis(player);
                h.actions[382] = (SlotAction) (player, slot) -> selectSkotizo(player);
                h.actions[383] = (SlotAction) (player, slot) -> selectThermo(player);
                h.actions[384] = (SlotAction) (player, slot) -> selectVorkath(player);
                h.actions[385] = (SlotAction) (player, slot) -> selectZulrah(player);
                h.actions[428] = (SlotAction) (player, slot) -> teleport(player);

                h.actions[315] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ABYSSAL_SIRE, player, 315, 497);
                h.actions[316] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ALCHEMICAL_HYDRA, player, 316, 498);
                h.actions[317] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.KRAKEN, player, 317, 499);
                h.actions[318] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CERB, player, 318, 500);
                h.actions[319] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CORP, player, 319, 501);
                h.actions[320] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DAG_KINGS, player, 320, 502);
                h.actions[321] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GIANT_MOLE, player, 321, 503);
                h.actions[322] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GOD_WARS, player, 322, 504);
                h.actions[323] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.KALPHITE_QUEEN, player, 323, 505);
                h.actions[324] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.LIZARDMAN, player, 324, 506);
                h.actions[347] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.NEX, player, 347, 507);
                h.actions[348] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.THE_NIGHTMARE, player, 348, 508);
                h.actions[349] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.RUNE_DRAGS, player, 349, 509);
                h.actions[350] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.SARACHNIS, player, 350, 510);
                h.actions[351] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.SKOTIZO, player, 351, 511);
                h.actions[352] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.THERMO, player, 352, 512);
                h.actions[353] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.VORKATH, player, 353, 513);
                h.actions[354] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ZULRAH, player, 354, 514);

                h.actions[500] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ABYSSAL_SIRE, player, 315, 497);
                h.actions[501] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ALCHEMICAL_HYDRA, player, 316, 498);
                h.actions[502] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.KRAKEN, player, 317, 499);
                h.actions[503] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CERB, player, 318, 500);
                h.actions[504] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CORP, player, 319, 501);
                h.actions[505] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DAG_KINGS, player, 320, 502);
                h.actions[506] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GIANT_MOLE, player, 321, 503);
                h.actions[507] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GOD_WARS, player, 322, 504);
                h.actions[508] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.KALPHITE_QUEEN, player, 323, 505);
                h.actions[509] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.LIZARDMAN, player, 324, 506);
                h.actions[510] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.NEX, player, 347, 507);
                h.actions[511] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.THE_NIGHTMARE, player, 348, 508);
                h.actions[512] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.RUNE_DRAGS, player, 349, 509);
                h.actions[513] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.SARACHNIS, player, 350, 510);
                h.actions[514] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.SKOTIZO, player, 351, 511);
                h.actions[515] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.THERMO, player, 352, 512);
                h.actions[516] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.VORKATH, player, 353, 513);
                h.actions[517] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ZULRAH, player, 354, 514);

            });
    }

    public static boolean teleportSelected = false;

    //Monsters
    public static boolean abyssalSire, alchemicalHydra, cerb, corp, dagKings, nex, sarachnis, giantMole, godWars,
        kalphiteQueen, kraken, lizardMen, runeDrags, skotizo, thenightmare,  thermo, vorkath, zulrah = false;

    public static void clearSelections() {
        abyssalSire = false; alchemicalHydra = false; cerb = false; corp = false; dagKings = false;
        nex = false; sarachnis = false; giantMole= false; godWars = false; kalphiteQueen = false; kraken= false;
        lizardMen= false; runeDrags= false; skotizo = false; thenightmare = false; thermo = false; vorkath = false;
        zulrah = false;
    }

    public static void openInterface(Player p) {
        p.openInterface(InterfaceType.MAIN, BOSSES);
        sendBossTeleports(p);
        teleportSelected = false;
        sendInfo(p);
        sendSprites(p);
        sendFavSprites(p);
    }

    private static void sendInfo(Player p) {
        if (p.getTeleports().getLastTeleport() == null) {
            p.getPacketSender().sendString(BOSSES, 423, "None!");
        } else {
            p.getPacketSender().sendString(BOSSES, 423, "" +p.getTeleports().getLastTeleport().getName());
        }
    }

    private TeleportFavorites teleportFavorites;

    public BossesInter() {
        teleportFavorites = new TeleportFavorites();
    }

    private static void sendSprites(Player p) {
        p.getPacketSender().sendItems(BOSSES, 480, 0, new Item(13576));
        p.getPacketSender().sendItems(BOSSES, 486, 0, new Item(2363));
    }

    private static void handleFavButton(TeleportInterface.Teleport teleOption, Player p, int silverStar, int goldStar) {
        if (p.favorites.contains(teleOption)) {
            removeFavorite(teleOption, p, silverStar, goldStar);
        } else {
            addFavorite(teleOption, p, silverStar, goldStar);
        }
    }

    private static void addFavorite(TeleportInterface.Teleport teleportOption, Player p, int silverStar, int goldStar) {
        BossesInter instance = new BossesInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.addFavorite(teleportOption, p);
        sendFavSprites(p);
    }

    private static void removeFavorite(TeleportInterface.Teleport teleOption, Player p, int silverStar, int goldStar) {
        BossesInter instance = new BossesInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.removeFavorite(teleOption, p);
        sendFavSprites(p);
    }

    public static void sendFavSprites(Player p) {
        if (p.favorites.contains(TeleportInterface.Teleport.ABYSSAL_SIRE)) {
            p.getPacketSender().setHidden(BOSSES, 315, true);
            p.getPacketSender().setHidden(BOSSES, 500, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 315, false);
            p.getPacketSender().setHidden(BOSSES, 500, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.ALCHEMICAL_HYDRA)) {
            p.getPacketSender().setHidden(BOSSES, 316, true);
            p.getPacketSender().setHidden(BOSSES, 501, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 316, false);
            p.getPacketSender().setHidden(BOSSES, 501, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.KRAKEN)) {
            p.getPacketSender().setHidden(BOSSES, 317, true);
            p.getPacketSender().setHidden(BOSSES, 502, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 317, false);
            p.getPacketSender().setHidden(BOSSES, 502, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.CERB)) {
            p.getPacketSender().setHidden(BOSSES, 318, true);
            p.getPacketSender().setHidden(BOSSES, 503, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 318, false);
            p.getPacketSender().setHidden(BOSSES, 503, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.CORP)) {
            p.getPacketSender().setHidden(BOSSES, 319, true);
            p.getPacketSender().setHidden(BOSSES, 504, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 319, false);
            p.getPacketSender().setHidden(BOSSES, 504, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.DAG_KINGS)) {
            p.getPacketSender().setHidden(BOSSES, 320, true);
            p.getPacketSender().setHidden(BOSSES, 505, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 320, false);
            p.getPacketSender().setHidden(BOSSES, 505, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.GIANT_MOLE)) {
            p.getPacketSender().setHidden(BOSSES, 321, true);
            p.getPacketSender().setHidden(BOSSES, 506, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 321, false);
            p.getPacketSender().setHidden(BOSSES, 506, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.GOD_WARS)) {
            p.getPacketSender().setHidden(BOSSES, 322, true);
            p.getPacketSender().setHidden(BOSSES, 507, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 322, false);
            p.getPacketSender().setHidden(BOSSES, 507, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.KALPHITE_QUEEN)) {
            p.getPacketSender().setHidden(BOSSES, 323, true);
            p.getPacketSender().setHidden(BOSSES, 508, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 323, false);
            p.getPacketSender().setHidden(BOSSES, 508, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.LIZARDMAN)) {
            p.getPacketSender().setHidden(BOSSES, 324, true);
            p.getPacketSender().setHidden(BOSSES, 509, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 324, false);
            p.getPacketSender().setHidden(BOSSES, 509, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.NEX)) {
            p.getPacketSender().setHidden(BOSSES, 347, true);
            p.getPacketSender().setHidden(BOSSES, 510, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 347, false);
            p.getPacketSender().setHidden(BOSSES, 510, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.THE_NIGHTMARE)) {
            p.getPacketSender().setHidden(BOSSES, 348, true);
            p.getPacketSender().setHidden(BOSSES, 511, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 348, false);
            p.getPacketSender().setHidden(BOSSES, 511, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.RUNE_DRAGS)) {
            p.getPacketSender().setHidden(BOSSES, 349, true);
            p.getPacketSender().setHidden(BOSSES, 512, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 349, false);
            p.getPacketSender().setHidden(BOSSES, 512, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.SARACHNIS)) {
            p.getPacketSender().setHidden(BOSSES, 350, true);
            p.getPacketSender().setHidden(BOSSES, 513, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 350, false);
            p.getPacketSender().setHidden(BOSSES, 513, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.SKOTIZO)) {
            p.getPacketSender().setHidden(BOSSES, 351, true);
            p.getPacketSender().setHidden(BOSSES, 514, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 322, false);
            p.getPacketSender().setHidden(BOSSES, 514, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.THERMO)) {
            p.getPacketSender().setHidden(BOSSES, 352, true);
            p.getPacketSender().setHidden(BOSSES, 515, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 352, false);
            p.getPacketSender().setHidden(BOSSES, 515, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.VORKATH)) {
            p.getPacketSender().setHidden(BOSSES, 353, true);
            p.getPacketSender().setHidden(BOSSES, 516, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 353, false);
            p.getPacketSender().setHidden(BOSSES, 516, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.ZULRAH)) {
            p.getPacketSender().setHidden(BOSSES, 354, true);
            p.getPacketSender().setHidden(BOSSES, 517, false);
        } else {
            p.getPacketSender().setHidden(BOSSES, 354, false);
            p.getPacketSender().setHidden(BOSSES, 517, true);
        }
    }

    public static void teleport(Player p) {
        if (!teleportSelected) {
            p.sendMessage("You must select a teleport first!");
        }
        if (abyssalSire) {
            teleportAbyssalSire(p);
        } else if (alchemicalHydra) {
            teleportAlchemical(p);
        } else if (cerb) {
            teleportCerb(p);
        } else if (corp) {
            teleportCorp(p);
        } else if (dagKings) {
            teleportDagKings(p);
        } else if (nex) {
            teleportNex(p);
        } else if (sarachnis) {
            teleportSarachnis(p);
        } else if (giantMole) {
            teleportGiantMole(p);
        } else if (godWars) {
            teleportGodwars(p);
        } else if (kalphiteQueen) {
            teleportKalphiteQueen(p);
        } else if (kraken) {
            teleportCaveKraken(p);
        } else if (lizardMen) {
            teleportLizardman(p);
        } else if (runeDrags) {
            teleportRune(p);
        } else if (skotizo) {
            teleportSkotizo(p);
        } else if (thenightmare) {
            teleportNightmare(p);
        } else if (thermo) {
            teleportThermo(p);
        } else if (vorkath) {
            teleportVorkath(p);
        } else if (zulrah) {
            teleportZulrah(p);
        }

    }
    public static void teleport(Player player, int x, int y, int z) {
        player.resetActions(true, true, true);
        player.startEvent(e -> {
            player.lock(LockType.FULL);
            player.animate(9131);
            player.graphics(1945);
            e.delay(4);
            player.animate(9133);
            player.graphics(1946);
            player.graphics(1946);
            e.delay(1);
            player.animate(-1);
            player.getMovement().teleport(x, y, z);
            player.unlock();
            PlayerCounter.TELEPORT_PORTAL_USES.increment(player, 1);
        });
    }

    public static void sendBossTeleports(Player p) {
        p.getPacketSender().sendString(BOSSES, 88, "Abyssal Sire");
        p.getPacketSender().sendString(BOSSES, 89, "Alchemical Hydra");
        p.getPacketSender().sendString(BOSSES, 90, "Cave Kraken");
        p.getPacketSender().sendString(BOSSES, 170, "Cerberus");
        p.getPacketSender().sendString(BOSSES, 171, "Corporeal Beast <col=FF0000>(Wilderness)</col>");
        p.getPacketSender().sendString(BOSSES, 172, "Dagannoth Kings");
        p.getPacketSender().sendString(BOSSES, 177, "Giant Mole");
        p.getPacketSender().sendString(BOSSES, 178, "Godwars Dungeon");
        p.getPacketSender().sendString(BOSSES, 179, "Kalphite Queen");
        p.getPacketSender().sendString(BOSSES, 184, "Lizardman Shaman");
        p.getPacketSender().sendString(BOSSES, 185, "Nex");
        p.getPacketSender().sendString(BOSSES, 186, "The Nightmare");
        p.getPacketSender().sendString(BOSSES, 191, "Rune/Adamant Dragons");
        p.getPacketSender().sendString(BOSSES, 192, "Sarachnis");
        p.getPacketSender().sendString(BOSSES, 193, "Skotizo");
        p.getPacketSender().sendString(BOSSES, 198, "Thermonuclear Smoke Devil");
        p.getPacketSender().sendString(BOSSES, 199, "Vorkath");
        p.getPacketSender().sendString(BOSSES, 200, "Zulrah");

        p.getPacketSender().sendString(BOSSES, 325, "The daddy of all abyssal creatures");
        p.getPacketSender().sendString(BOSSES, 326, "It has that many heads?!");
        p.getPacketSender().sendString(BOSSES, 327, "Release the kraken!");
        p.getPacketSender().sendString(BOSSES, 328, "Come for the primordial, leave with a smouldering");
        p.getPacketSender().sendString(BOSSES, 329, "The corporeal beast boss");
        p.getPacketSender().sendString(BOSSES, 330, "Dagannoth rex, prime and supreme");
        p.getPacketSender().sendString(BOSSES, 331, "The giant mole lair underneath falador park");
        p.getPacketSender().sendString(BOSSES, 332, "Takes you to the center of godwars dungeon");
        p.getPacketSender().sendString(BOSSES, 333, "The kalphite queen lair");
        p.getPacketSender().sendString(BOSSES, 338, "Lizardmans and lizardman shamans");
        p.getPacketSender().sendString(BOSSES, 339, "The fifth general: Nex");
        p.getPacketSender().sendString(BOSSES, 340, "The Nightmare of Ashihama");
        p.getPacketSender().sendString(BOSSES, 341, "Home to the most annoying dragons in the game");
        p.getPacketSender().sendString(BOSSES, 342, "A formidable foe within Forthos Dungeon");
        p.getPacketSender().sendString(BOSSES, 343, "Skotizos lair inside the catacombs");
        p.getPacketSender().sendString(BOSSES, 344, "Can be killed on smoke devil slayer tasks");
        p.getPacketSender().sendString(BOSSES, 345, "Vorkaths icy lair");
        p.getPacketSender().sendString(BOSSES, 346, "The shrine of zulrah, many hcim perish here");
    }

    public static void selectAbyssalSire(Player p) {
        clearSelections();
        abyssalSire = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Abyssal Sire");
    }
    public static void teleportAbyssalSire(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3038, 4771, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ABYSSAL_SIRE);
    }
    public static void selectAlchemical(Player p) {
        clearSelections();
        alchemicalHydra = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Alchemical Hydra");
    }
    public static void teleportAlchemical(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1352, 10258, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ALCHEMICAL_HYDRA);
    }
    public static void selectCaveKraken(Player p) {
        clearSelections();
        kraken = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Kraken");
    }
    public static void teleportCaveKraken(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2292, 10008, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.KRAKEN);
    }
    public static void selectCerb(Player p) {
        clearSelections();
        cerb = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Cerberus");
    }
    public static void teleportCerb(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1310, 1252, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.CERB);
    }
    public static void selectCorp(Player p) {
        clearSelections();
        corp = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Corporeal Beast <col=FF0000>(Wild)</col>");
    }
    public static void teleportCorp(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3157, 3670, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.CORP);
    }
    public static void selectDagKings(Player p) {
        clearSelections();
        dagKings = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Dagannoth Kings");
    }
    public static void teleportDagKings(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1912, 4367, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.DAG_KINGS);
    }
    public static void selectGiantMole(Player p) {
        clearSelections();
        giantMole = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Giant Mole");
    }
    public static void teleportGiantMole(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1764, 5179, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.GIANT_MOLE);
    }
    public static void selectGodwars(Player p) {
        clearSelections();
        godWars = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Godwars Dungeon");
    }
    public static void teleportGodwars(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2916, 3746, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.GOD_WARS);
    }
    public static void selectKalphiteQueen(Player p) {
        clearSelections();
        kalphiteQueen = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Kalphite Queen");
    }
    public static void teleportKalphiteQueen(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3505, 9493, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.KALPHITE_QUEEN);
    }
    public static void selectLizardman(Player p) {
        clearSelections();
        lizardMen = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Lizardman Shaman");
    }
    public static void teleportLizardman(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1445, 3699, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.LIZARDMAN);
    }
    public static void selectNex(Player p) {
        clearSelections();
        nex = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Nex");
    }
    public static void teleportNex(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2906, 5203, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.NEX);
    }
    public static void selectNightmare(Player p) {
        clearSelections();
        thenightmare = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "The Nightmare");
    }
    public static void teleportNightmare(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3808, 9747, 1);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.THE_NIGHTMARE);
    }
    public static void selectRune(Player p) {
        clearSelections();
        runeDrags = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Rune/Adamant Dragons");
    }
    public static void teleportRune(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1568, 5075, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.RUNE_DRAGS);
    }
    public static void selectSarachnis(Player p) {
        clearSelections();
        sarachnis = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Sarachnis");
    }
    public static void teleportSarachnis(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1842, 9928, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.SARACHNIS);
    }
    public static void selectSkotizo(Player p) {
        clearSelections();
        skotizo = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Skotizo");
    }
    public static void teleportSkotizo(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1663, 10051, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.SKOTIZO);
    }
    public static void selectThermo(Player p) {
        clearSelections();
        thermo = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Thermo. Smoke Devil");
    }
    public static void teleportThermo(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2412, 3060, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.THERMO);
    }
    public static void selectVorkath(Player p) {
        clearSelections();
        vorkath = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Vorkath");
    }
    public static void teleportVorkath(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2272, 4051, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.VORKATH);
    }
    public static void selectZulrah(Player p) {
        clearSelections();
        zulrah = true;
        teleportSelected = true;
        p.getPacketSender().sendString(BOSSES, 427, "Zulrah");
    }
    public static void teleportZulrah(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2205, 3057, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ZULRAH);
    }
}