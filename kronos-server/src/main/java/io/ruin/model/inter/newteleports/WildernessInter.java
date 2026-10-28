package io.ruin.model.inter.newteleports;

import io.ruin.model.achievements.listeners.easy.TheWildernessExpansion;
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

public class WildernessInter {

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

            InterfaceHandler.register(WILDERNESS, h -> {
                h.actions[19] = (SlotAction) (player, slot) -> player.getTeleporter().openInterface(player);
                h.actions[36] = (SlotAction) (player, slot) -> DungeonInter.openInterface(player);
                h.actions[37] = (SlotAction) (player, slot) -> BossesInter.openInterface(player);
                h.actions[38] = (SlotAction) (player, slot) -> MinigameInter.openInterface(player);
                h.actions[39] = (SlotAction) (player, slot) -> SkillingInter.openInterface(player);
                h.actions[40] = (SlotAction) (player, slot) -> openInterface(player);
                h.actions[391] = (SlotAction) (player, slot) -> CityInter.openInterface(player);
                h.actions[392] = (SlotAction) (player, slot) -> MiscInter.openInterface(player);
                h.actions[400] = (SlotAction) (player, slot) -> Favorites.openInterface(player);
                h.actions[425] = (SlotAction) (player, slot) -> Teleporter.teleportPrevious(player);

                h.actions[368] = (SlotAction) (player, slot) -> selectbootybay(player);
                h.actions[369] = (SlotAction) (player, slot) -> selectchaostemple(player);
                h.actions[370] = (SlotAction) (player, slot) -> selectdemonic(player);
                h.actions[371] = (SlotAction) (player, slot) -> selectferox(player);
                h.actions[372] = (SlotAction) (player, slot) -> selectforgottencemetary(player);
                h.actions[373] = (SlotAction) (player, slot) -> selectfountain(player);
                h.actions[374] = (SlotAction) (player, slot) -> selectgalvekwild(player);
                h.actions[375] = (SlotAction) (player, slot) -> selectgraveyard(player);
                h.actions[376] = (SlotAction) (player, slot) -> selectdragseast(player);
                h.actions[377] = (SlotAction) (player, slot) -> selectdragswest(player);
                h.actions[378] = (SlotAction) (player, slot) -> selectice(player);
                h.actions[379] = (SlotAction) (player, slot) -> selectkbd(player);
                h.actions[380] = (SlotAction) (player, slot) -> selectlavadrags(player);
                h.actions[381] = (SlotAction) (player, slot) -> selectlavamze(player);
                h.actions[382] = (SlotAction) (player, slot) -> selectmb(player);
                h.actions[383] = (SlotAction) (player, slot) -> selectrevsnorth(player);
                h.actions[384] = (SlotAction) (player, slot) -> selectrevssouth(player);
                h.actions[385] = (SlotAction) (player, slot) -> selectexpansion(player);
                h.actions[428] = (SlotAction) (player, slot) -> teleport(player);

                h.actions[315] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.BOOTY_BAY, player, 315, 497);
                h.actions[316] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CHAOS_TEMPLE, player, 316, 498);
                h.actions[317] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DEMONIC_RUINS, player, 317, 499);
                h.actions[318] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FEROX_ENCLAVE, player, 318, 500);
                h.actions[319] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FORGOTTEN_CEMETARY, player, 319, 501);
                h.actions[320] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FOUNTAIN_OF_RUNE, player, 320, 502);
                h.actions[321] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GALVEK1, player, 321, 503);
                h.actions[322] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GRAVEYARD, player, 322, 504);
                h.actions[323] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GREEN_DRAGONS_EAST, player, 323, 505);
                h.actions[324] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GREEN_DRAGONS_WEST, player, 324, 506);
                h.actions[347] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ICE_PLATEAU, player, 347, 507);
                h.actions[348] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.KING_BLACK_DRAGON, player, 348, 508);
                h.actions[349] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.LAVA_DRAGONS, player, 349, 509);
                h.actions[350] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.LAVA_MAZE, player, 350, 510);
                h.actions[351] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.MAGE_BANK, player, 351, 511);
                h.actions[352] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.REVENANT_CAVE, player, 352, 512);
                h.actions[353] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.REVENANT_CAVE_TWO, player, 353, 513);
                h.actions[354] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WILDEXPANSIONBANK, player, 354, 514);

                h.actions[500] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.BOOTY_BAY, player, 315, 497);
                h.actions[501] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CHAOS_TEMPLE, player, 316, 498);
                h.actions[502] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DEMONIC_RUINS, player, 317, 499);
                h.actions[503] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FEROX_ENCLAVE, player, 318, 500);
                h.actions[504] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FORGOTTEN_CEMETARY, player, 319, 501);
                h.actions[505] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FOUNTAIN_OF_RUNE, player, 320, 502);
                h.actions[506] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GALVEK1, player, 321, 503);
                h.actions[507] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GRAVEYARD, player, 322, 504);
                h.actions[508] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GREEN_DRAGONS_EAST, player, 323, 505);
                h.actions[509] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GREEN_DRAGONS_WEST, player, 324, 506);
                h.actions[510] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ICE_PLATEAU, player, 347, 507);
                h.actions[511] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.KING_BLACK_DRAGON, player, 348, 508);
                h.actions[512] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.LAVA_DRAGONS, player, 349, 509);
                h.actions[513] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.LAVA_MAZE, player, 350, 510);
                h.actions[514] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.MAGE_BANK, player, 351, 511);
                h.actions[515] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.REVENANT_CAVE, player, 352, 512);
                h.actions[516] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.REVENANT_CAVE_TWO, player, 353, 513);
                h.actions[517] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WILDEXPANSIONBANK, player, 354, 514);

            });
    }

    public static boolean teleportSelected = false;

    //Monsters
    public static boolean bootybay, chaostemple, demonicruins, forgottencemetary, fountainofrune, galvek,
            graveyard, greendragseast, greendragswest, iceplateau, kbd, lavadragons, ferox,
            lavamaze, magebank, revcavenorth, revcavesouth, wildernessexpansionbank = false;

    public static void clearSelections() {
        bootybay = false; chaostemple = false; demonicruins = false; forgottencemetary = false; fountainofrune = false;
        galvek = false; graveyard = false; greendragseast = false; greendragswest = false; iceplateau = false; kbd = false;
        lavadragons = false; ferox = false; lavamaze = false; magebank = false; revcavenorth = false;
        revcavesouth = false; wildernessexpansionbank = false;
    }

    public static void openInterface(Player p) {
        p.openInterface(InterfaceType.MAIN, WILDERNESS);
        sendWildyTeleports(p);
        teleportSelected = false;
        sendInfo(p);
        sendSprites(p);
        sendFavSprites(p);
    }

    private static void sendInfo(Player p) {
        if (p.getTeleports().getLastTeleport() == null) {
            p.getPacketSender().sendString(WILDERNESS, 423, "None!");
        } else {
            p.getPacketSender().sendString(WILDERNESS, 423, "" +p.getTeleports().getLastTeleport().getName());
        }
    }


    private TeleportFavorites teleportFavorites;

    public WildernessInter() {
        teleportFavorites = new TeleportFavorites();
    }

    private static void handleFavButton(TeleportInterface.Teleport teleOption, Player p, int silverStar, int goldStar) {
        if (p.favorites.contains(teleOption)) {
            removeFavorite(teleOption, p, silverStar, goldStar);
        } else {
            addFavorite(teleOption, p, silverStar, goldStar);
        }
    }

    private static void addFavorite(TeleportInterface.Teleport teleportOption, Player p, int silverStar, int goldStar) {
        WildernessInter instance = new WildernessInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.addFavorite(teleportOption, p);
        sendFavSprites(p);
    }

    private static void removeFavorite(TeleportInterface.Teleport teleOption, Player p, int silverStar, int goldStar) {
        WildernessInter instance = new WildernessInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.removeFavorite(teleOption, p);
        sendFavSprites(p);
    }

    public static void sendFavSprites(Player p) {
        if (p.favorites.contains(TeleportInterface.Teleport.BOOTY_BAY)) {
            p.getPacketSender().setHidden(WILDERNESS, 315, true);
            p.getPacketSender().setHidden(WILDERNESS, 500, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 315, false);
            p.getPacketSender().setHidden(WILDERNESS, 500, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.CHAOS_TEMPLE)) {
            p.getPacketSender().setHidden(WILDERNESS, 316, true);
            p.getPacketSender().setHidden(WILDERNESS, 501, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 316, false);
            p.getPacketSender().setHidden(WILDERNESS, 501, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.DEMONIC_RUINS)) {
            p.getPacketSender().setHidden(WILDERNESS, 317, true);
            p.getPacketSender().setHidden(WILDERNESS, 502, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 317, false);
            p.getPacketSender().setHidden(WILDERNESS, 502, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FEROX_ENCLAVE)) {
            p.getPacketSender().setHidden(WILDERNESS, 318, true);
            p.getPacketSender().setHidden(WILDERNESS, 503, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 318, false);
            p.getPacketSender().setHidden(WILDERNESS, 503, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FORGOTTEN_CEMETARY)) {
            p.getPacketSender().setHidden(WILDERNESS, 319, true);
            p.getPacketSender().setHidden(WILDERNESS, 504, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 319, false);
            p.getPacketSender().setHidden(WILDERNESS, 504, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FOUNTAIN_OF_RUNE)) {
            p.getPacketSender().setHidden(WILDERNESS, 320, true);
            p.getPacketSender().setHidden(WILDERNESS, 505, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 320, false);
            p.getPacketSender().setHidden(WILDERNESS, 505, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.GALVEK1)) {
            p.getPacketSender().setHidden(WILDERNESS, 321, true);
            p.getPacketSender().setHidden(WILDERNESS, 506, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 321, false);
            p.getPacketSender().setHidden(WILDERNESS, 506, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.GRAVEYARD)) {
            p.getPacketSender().setHidden(WILDERNESS, 322, true);
            p.getPacketSender().setHidden(WILDERNESS, 507, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 322, false);
            p.getPacketSender().setHidden(WILDERNESS, 507, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.GREEN_DRAGONS_EAST)) {
            p.getPacketSender().setHidden(WILDERNESS, 323, true);
            p.getPacketSender().setHidden(WILDERNESS, 508, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 323, false);
            p.getPacketSender().setHidden(WILDERNESS, 508, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.GREEN_DRAGONS_WEST)) {
            p.getPacketSender().setHidden(WILDERNESS, 324, true);
            p.getPacketSender().setHidden(WILDERNESS, 509, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 324, false);
            p.getPacketSender().setHidden(WILDERNESS, 509, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.ICE_PLATEAU)) {
            p.getPacketSender().setHidden(WILDERNESS, 347, true);
            p.getPacketSender().setHidden(WILDERNESS, 510, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 347, false);
            p.getPacketSender().setHidden(WILDERNESS, 510, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.KING_BLACK_DRAGON)) {
            p.getPacketSender().setHidden(WILDERNESS, 348, true);
            p.getPacketSender().setHidden(WILDERNESS, 511, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 348, false);
            p.getPacketSender().setHidden(WILDERNESS, 511, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.LAVA_DRAGONS)) {
            p.getPacketSender().setHidden(WILDERNESS, 349, true);
            p.getPacketSender().setHidden(WILDERNESS, 512, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 349, false);
            p.getPacketSender().setHidden(WILDERNESS, 512, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.LAVA_MAZE)) {
            p.getPacketSender().setHidden(WILDERNESS, 350, true);
            p.getPacketSender().setHidden(WILDERNESS, 513, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 350, false);
            p.getPacketSender().setHidden(WILDERNESS, 513, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.MAGE_BANK)) {
            p.getPacketSender().setHidden(WILDERNESS, 351, true);
            p.getPacketSender().setHidden(WILDERNESS, 514, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 322, false);
            p.getPacketSender().setHidden(WILDERNESS, 514, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.REVENANT_CAVE)) {
            p.getPacketSender().setHidden(WILDERNESS, 352, true);
            p.getPacketSender().setHidden(WILDERNESS, 515, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 352, false);
            p.getPacketSender().setHidden(WILDERNESS, 515, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.REVENANT_CAVE_TWO)) {
            p.getPacketSender().setHidden(WILDERNESS, 353, true);
            p.getPacketSender().setHidden(WILDERNESS, 516, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 353, false);
            p.getPacketSender().setHidden(WILDERNESS, 516, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.WILDEXPANSIONBANK)) {
            p.getPacketSender().setHidden(WILDERNESS, 354, true);
            p.getPacketSender().setHidden(WILDERNESS, 517, false);
        } else {
            p.getPacketSender().setHidden(WILDERNESS, 354, false);
            p.getPacketSender().setHidden(WILDERNESS, 517, true);
        }
    }

    private static void sendSprites(Player p) {
        p.getPacketSender().sendItems(WILDERNESS, 461, 0, new Item(12412));
        p.getPacketSender().sendItems(WILDERNESS, 463, 0, new Item(20595));
        p.getPacketSender().sendItems(WILDERNESS, 465, 0, new Item(13501));

        p.getPacketSender().sendItems(WILDERNESS, 468, 0, new Item(26651));
        p.getPacketSender().sendItems(WILDERNESS, 470, 0, new Item(20095));
        p.getPacketSender().sendItems(WILDERNESS, 472, 0, new Item(1704));

        p.getPacketSender().sendItems(WILDERNESS, 474, 0, new Item(13510));
        p.getPacketSender().sendItems(WILDERNESS, 476, 0, new Item(7587));
        p.getPacketSender().sendItems(WILDERNESS, 478, 0, new Item(1753));

        p.getPacketSender().sendItems(WILDERNESS, 480, 0, new Item(1753));
        p.getPacketSender().sendItems(WILDERNESS, 482, 0, new Item(526));
        p.getPacketSender().sendItems(WILDERNESS, 484, 0, new Item(7980));

        p.getPacketSender().sendItems(WILDERNESS, 486, 0, new Item(30131));
        p.getPacketSender().sendItems(WILDERNESS, 488, 0, new Item(18276));
        p.getPacketSender().sendItems(WILDERNESS, 490, 0, new Item(21795));

        p.getPacketSender().sendItems(WILDERNESS, 492, 0, new Item(22545));
        p.getPacketSender().sendItems(WILDERNESS, 494, 0, new Item(22550));
        p.getPacketSender().sendItems(WILDERNESS, 496, 0, new Item(23491));
    }

    public static void teleport(Player p) {
        if (!teleportSelected) {
            p.sendMessage("You must select a teleport first!");
        }
        if (bootybay) {
            telebootybay(p);
        } else if (chaostemple) {
            telechaostemple(p);
        } else if (demonicruins) {
            teledemonic(p);
        } else if (forgottencemetary) {
            teleforgottencemetary(p);
        } else if (fountainofrune) {
            telefountain(p);
        } else if (galvek) {
            telegalvekwild(p);
        } else if (graveyard) {
            telegraveyard(p);
        } else if (greendragseast) {
            teledragseast(p);
        } else if (greendragswest) {
            teledragswest(p);
        } else if (iceplateau) {
            teleice(p);
        } else if (kbd) {
            telekbd(p);
        } else if (lavadragons) {
            telelavadrags(p);
        } else if (ferox) {
            teleferox(p);
        } else if (lavamaze) {
            telelavamaze(p);
        } else if (magebank) {
            telemb(p);
        } else if (revcavenorth) {
            telerevsnorth(p);
        } else if (revcavesouth) {
            telerevssouth(p);
        } else if (wildernessexpansionbank) {
            teleexpansion(p);
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

    public static void sendWildyTeleports(Player p) {
        p.getPacketSender().sendString(WILDERNESS, 88, "Booty Bay");
        p.getPacketSender().sendString(WILDERNESS, 89, "Chaos Temple");
        p.getPacketSender().sendString(WILDERNESS, 90, "Demonic Ruins");
        p.getPacketSender().sendString(WILDERNESS, 170, "Ferox Enclave");
        p.getPacketSender().sendString(WILDERNESS, 171, "Forgotten Cemetary");
        p.getPacketSender().sendString(WILDERNESS, 172, "Fountain of Rune");
        p.getPacketSender().sendString(WILDERNESS, 177, "Galvek");
        p.getPacketSender().sendString(WILDERNESS, 178, "Graveyard");
        p.getPacketSender().sendString(WILDERNESS, 179, "Green Dragons - East");
        p.getPacketSender().sendString(WILDERNESS, 184, "Green Dragons - West");
        p.getPacketSender().sendString(WILDERNESS, 185, "Ice Plateau");
        p.getPacketSender().sendString(WILDERNESS, 186, "King Black Dragon");
        p.getPacketSender().sendString(WILDERNESS, 191, "Lava Dragons");
        p.getPacketSender().sendString(WILDERNESS, 192, "Lava Maze");
        p.getPacketSender().sendString(WILDERNESS, 193, "Mage Bank");
        p.getPacketSender().sendString(WILDERNESS, 198, "Revenant Cave - North");
        p.getPacketSender().sendString(WILDERNESS, 199, "Revenant Cave - South");
        p.getPacketSender().sendString(WILDERNESS, 200, "Wilderness Expansion - Bank");

        p.getPacketSender().sendString(WILDERNESS, 325, "Booty bay wilderness camp, safe teleport");
        p.getPacketSender().sendString(WILDERNESS, 326, "Level 12 wilderness, home of elder chaos druids");
        p.getPacketSender().sendString(WILDERNESS, 327, "Located in level 46 wilderness");
        p.getPacketSender().sendString(WILDERNESS, 328, "The infamous ferox enclave wilderness outpost");
        p.getPacketSender().sendString(WILDERNESS, 329, "Located directly south of the chaos altar");
        p.getPacketSender().sendString(WILDERNESS, 330, "The fountain of rune, deep wilderness");
        p.getPacketSender().sendString(WILDERNESS, 331, "A very difficult dragon");
        p.getPacketSender().sendString(WILDERNESS, 332, "Located just north of ferox enclave");
        p.getPacketSender().sendString(WILDERNESS, 333, "Level 18 eastern wilderness green dragons");
        p.getPacketSender().sendString(WILDERNESS, 338, "Level 13 wilderness, near dark warriors fortress");
        p.getPacketSender().sendString(WILDERNESS, 339, "The ice plateau in deep wilderness");
        p.getPacketSender().sendString(WILDERNESS, 340, "Just south of the King Black Dragon ladder");
        p.getPacketSender().sendString(WILDERNESS, 341, "Level 43 wilderness lava dragons");
        p.getPacketSender().sendString(WILDERNESS, 342, "The center of the deep wilderness lava maze");
        p.getPacketSender().sendString(WILDERNESS, 343, "Teleports you inside of the mage bank");
        p.getPacketSender().sendString(WILDERNESS, 344, "Teleports you outside of the north revenant caves");
        p.getPacketSender().sendString(WILDERNESS, 345, "Teleports you outside of the south revenant caves");
        p.getPacketSender().sendString(WILDERNESS, 346, "Safe-zone bank in the wilderness expansion area");
    }

    public static void selectbootybay(Player p) {
        clearSelections();
        bootybay = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Booty Bay");
    }
    public static void telebootybay(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3357, 3808, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.BOOTY_BAY);
    }
    public static void selectchaostemple(Player p) {
        clearSelections();
        chaostemple = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Chaos Temple");
    }
    public static void telechaostemple(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3236, 3638, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.CHAOS_TEMPLE);
    }
    public static void selectdemonic(Player p) {
        clearSelections();
        demonicruins = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Demonic Ruins");
    }
    public static void teledemonic(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3286, 3881, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.DEMONIC_RUINS);
    }
    public static void selectferox(Player p) {
        clearSelections();
        ferox = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Ferox Enclave");
    }
    public static void teleferox(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3134, 3629, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FEROX_ENCLAVE);
    }
    public static void selectforgottencemetary(Player p) {
        clearSelections();
        forgottencemetary = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Forgotten Cemetary");
    }
    public static void teleforgottencemetary(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2980, 3765, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FORGOTTEN_CEMETARY);
    }
    public static void selectfountain(Player p) {
        clearSelections();
        fountainofrune = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Fountain of Rune");
    }
    public static void telefountain(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3357, 3872, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FOUNTAIN_OF_RUNE);
    }
    public static void selectgalvekwild(Player p) {
        clearSelections();
        galvek = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Galvek");
    }
    public static void telegalvekwild(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3374, 3643, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.GALVEK1);
    }
    public static void selectgraveyard(Player p) {
        clearSelections();
        graveyard = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Graveyard");
    }
    public static void telegraveyard(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3149, 3669, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.GRAVEYARD);
    }
    public static void selectdragseast(Player p) {
        clearSelections();
        greendragseast = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Green Dragons - East");
    }
    public static void teledragseast(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3343, 3663, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.GREEN_DRAGONS_EAST);
    }
    public static void selectdragswest(Player p) {
        clearSelections();
        greendragswest = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Green Dragons - West");
    }
    public static void teledragswest(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2994, 3624, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.GREEN_DRAGONS_WEST);
    }
    public static void selectice(Player p) {
        clearSelections();
        iceplateau = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Ice Plateau");
    }
    public static void teleice(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2970, 3880, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ICE_PLATEAU);
    }
    public static void selectkbd(Player p) {
        clearSelections();
        kbd = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "King Black Dragon");
    }
    public static void telekbd(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3024, 3833, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.KING_BLACK_DRAGON);
    }
    public static void selectlavadrags(Player p) {
        clearSelections();
        lavadragons = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Lava Dragons");
    }
    public static void telelavadrags(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3201, 3859, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.LAVA_DRAGONS);
    }
    public static void selectlavamze(Player p) {
        clearSelections();
        lavamaze = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Lava Maze");
    }

    public static void telelavamaze(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3076, 3846, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.LAVA_MAZE);
    }
    public static void selectmb(Player p) {
        clearSelections();
        magebank = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Mage Bank");
    }
    public static void telemb(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2539, 4717, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.MAGE_BANK);
    }
    public static void selectrevsnorth(Player p) {
        clearSelections();
        revcavenorth = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Rev. Cave - North");
    }
    public static void telerevsnorth(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3128, 3827, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.REVENANT_CAVE);
    }
    public static void selectrevssouth(Player p) {
        clearSelections();
        revcavesouth = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Rev. Cave - South");
    }
    public static void telerevssouth(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3075, 3651, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.REVENANT_CAVE_TWO);
    }
    public static void selectexpansion(Player p) {
        clearSelections();
        wildernessexpansionbank = true;
        teleportSelected = true;
        p.getPacketSender().sendString(WILDERNESS, 427, "Wild Expansion Bank");
    }
    public static void teleexpansion(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3166, 4055, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.WILDEXPANSIONBANK);
        TheWildernessExpansion.complete(p);
    }
}