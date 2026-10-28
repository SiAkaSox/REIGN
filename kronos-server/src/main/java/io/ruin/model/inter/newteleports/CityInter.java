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

public class CityInter {

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
        
            InterfaceHandler.register(CITIES, h -> {
                h.actions[19] = (SlotAction) (player, slot) -> player.getTeleporter().openInterface(player);
                h.actions[36] = (SlotAction) (player, slot) -> DungeonInter.openInterface(player);
                h.actions[37] = (SlotAction) (player, slot) -> BossesInter.openInterface(player);
                h.actions[38] = (SlotAction) (player, slot) -> MinigameInter.openInterface(player);
                h.actions[39] = (SlotAction) (player, slot) -> SkillingInter.openInterface(player);
                h.actions[40] = (SlotAction) (player, slot) -> WildernessInter.openInterface(player);
                h.actions[391] = (SlotAction) (player, slot) -> openInterface(player);
                h.actions[392] = (SlotAction) (player, slot) -> MiscInter.openInterface(player);
                h.actions[400] = (SlotAction) (player, slot) -> Favorites.openInterface(player);
                h.actions[425] = (SlotAction) (player, slot) -> Teleporter.teleportPrevious(player);

                h.actions[368] = (SlotAction) (player, slot) -> selectalkharid(player);
                h.actions[369] = (SlotAction) (player, slot) -> selectardougne(player);
                h.actions[370] = (SlotAction) (player, slot) -> selectbrimhaven(player);
                h.actions[371] = (SlotAction) (player, slot) -> selectburthrope(player);
                h.actions[372] = (SlotAction) (player, slot) -> selectcamelot(player);
                h.actions[373] = (SlotAction) (player, slot) -> selectcanafis(player);
                h.actions[374] = (SlotAction) (player, slot) -> selectcatherby(player);
                h.actions[375] = (SlotAction) (player, slot) -> selectdarkmeyer(player);
                h.actions[376] = (SlotAction) (player, slot) -> selectdraynor(player);
                h.actions[377] = (SlotAction) (player, slot) -> selectedge(player);
                h.actions[378] = (SlotAction) (player, slot) -> selectfalador(player);
                h.actions[379] = (SlotAction) (player, slot) -> selectkourend(player);
                h.actions[380] = (SlotAction) (player, slot) -> selectlumb(player);
                h.actions[381] = (SlotAction) (player, slot) -> selectportkhazard(player);
                h.actions[382] = (SlotAction) (player, slot) -> selectprif(player);
                h.actions[383] = (SlotAction) (player, slot) -> selecttreegnome(player);
                h.actions[384] = (SlotAction) (player, slot) -> selectvarrock(player);
                h.actions[385] = (SlotAction) (player, slot) -> selectzanaris(player);
                h.actions[428] = (SlotAction) (player, slot) -> teleport(player);

                h.actions[315] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.AL_KHARID, player, 315, 497);
                h.actions[316] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ARDOUGNE, player, 316, 498);
                h.actions[317] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.BRIMHAVEN, player, 317, 499);
                h.actions[318] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.BURTHROPE, player, 318, 500);
                h.actions[319] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CAMELOT, player, 319, 501);
                h.actions[320] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CANAFIS, player, 320, 502);
                h.actions[321] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CATHERBY, player, 321, 503);
                h.actions[322] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DARKMEYER, player, 322, 504);
                h.actions[323] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DRAYNOR, player, 323, 505);
                h.actions[324] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.EDGEVILLE, player, 324, 506);
                h.actions[347] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FALADOR, player, 347, 507);
                h.actions[348] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.KOUREND, player, 348, 508);
                h.actions[349] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.LUMBRIDGE, player, 349, 509);
                h.actions[350] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.PORT_KHAZARD, player, 350, 510);
                h.actions[351] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.PRIFDDINAS_CITY, player, 351, 511);
                h.actions[352] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.TREE_GNOME_STRONGHOLD, player, 352, 512);
                h.actions[353] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.VARROCK, player, 353, 513);
                h.actions[354] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ZANARIS, player, 354, 514);

                h.actions[500] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.AL_KHARID, player, 315, 497);
                h.actions[501] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ARDOUGNE, player, 316, 498);
                h.actions[502] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.BRIMHAVEN, player, 317, 499);
                h.actions[503] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.BURTHROPE, player, 318, 500);
                h.actions[504] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CAMELOT, player, 319, 501);
                h.actions[505] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CANAFIS, player, 320, 502);
                h.actions[506] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CATHERBY, player, 321, 503);
                h.actions[507] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DARKMEYER, player, 322, 504);
                h.actions[508] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DRAYNOR, player, 323, 505);
                h.actions[509] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.EDGEVILLE, player, 324, 506);
                h.actions[510] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FALADOR, player, 347, 507);
                h.actions[511] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.KOUREND, player, 348, 508);
                h.actions[512] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.LUMBRIDGE, player, 349, 509);
                h.actions[513] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.PORT_KHAZARD, player, 350, 510);
                h.actions[514] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.PRIFDDINAS_CITY, player, 351, 511);
                h.actions[515] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.TREE_GNOME_STRONGHOLD, player, 352, 512);
                h.actions[516] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.VARROCK, player, 353, 513);
                h.actions[517] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ZANARIS, player, 354, 514);
            });
    }

    public static boolean teleportSelected = false;

    //Monsters
    public static boolean alkharid, ardougne, brimhaven, burthrope, camelot, canafis,
            catherby, darkmeyer, draynor, edgeville, falador, kourend, lumby,
            portkhazard, prif, treegnomestronghold, varrock, zanaris = false;

    public static void clearSelections() {
        alkharid = false; ardougne = false; brimhaven = false; burthrope = false; camelot = false;
        canafis = false; catherby = false; darkmeyer = false; draynor = false; edgeville = false; falador = false;
        kourend = false; lumby = false; portkhazard = false; prif = false; treegnomestronghold = false;
        varrock = false; zanaris = false;
    }

    public static void openInterface(Player p) {
        p.openInterface(InterfaceType.MAIN, CITIES);
        sendCityTeleports(p);
        teleportSelected = false;
        sendInfo(p);
        sendSprites(p);
        sendFavSprites(p);
    }

    private static void sendInfo(Player p) {
        if (p.getTeleports().getLastTeleport() == null) {
            p.getPacketSender().sendString(CITIES, 423, "None!");
        } else {
            p.getPacketSender().sendString(CITIES, 423, "" +p.getTeleports().getLastTeleport().getName());
        }
    }

    private TeleportFavorites teleportFavorites;

    public CityInter() {
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
        CityInter instance = new CityInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.addFavorite(teleportOption, p);
        sendFavSprites(p);
    }

    private static void removeFavorite(TeleportInterface.Teleport teleOption, Player p, int silverStar, int goldStar) {
        CityInter instance = new CityInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.removeFavorite(teleOption, p);
        sendFavSprites(p);
    }

    public static void sendFavSprites(Player p) {
        if (p.favorites.contains(TeleportInterface.Teleport.AL_KHARID)) {
            p.getPacketSender().setHidden(CITIES, 315, true);
            p.getPacketSender().setHidden(CITIES, 500, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 315, false);
            p.getPacketSender().setHidden(CITIES, 500, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.ARDOUGNE)) {
            p.getPacketSender().setHidden(CITIES, 316, true);
            p.getPacketSender().setHidden(CITIES, 501, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 316, false);
            p.getPacketSender().setHidden(CITIES, 501, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.BRIMHAVEN)) {
            p.getPacketSender().setHidden(CITIES, 317, true);
            p.getPacketSender().setHidden(CITIES, 502, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 317, false);
            p.getPacketSender().setHidden(CITIES, 502, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.BURTHROPE)) {
            p.getPacketSender().setHidden(CITIES, 318, true);
            p.getPacketSender().setHidden(CITIES, 503, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 318, false);
            p.getPacketSender().setHidden(CITIES, 503, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.CAMELOT)) {
            p.getPacketSender().setHidden(CITIES, 319, true);
            p.getPacketSender().setHidden(CITIES, 504, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 319, false);
            p.getPacketSender().setHidden(CITIES, 504, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.CANAFIS)) {
            p.getPacketSender().setHidden(CITIES, 320, true);
            p.getPacketSender().setHidden(CITIES, 505, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 320, false);
            p.getPacketSender().setHidden(CITIES, 505, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.CATHERBY)) {
            p.getPacketSender().setHidden(CITIES, 321, true);
            p.getPacketSender().setHidden(CITIES, 506, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 321, false);
            p.getPacketSender().setHidden(CITIES, 506, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.DARKMEYER)) {
            p.getPacketSender().setHidden(CITIES, 322, true);
            p.getPacketSender().setHidden(CITIES, 507, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 322, false);
            p.getPacketSender().setHidden(CITIES, 507, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.DRAYNOR)) {
            p.getPacketSender().setHidden(CITIES, 323, true);
            p.getPacketSender().setHidden(CITIES, 508, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 323, false);
            p.getPacketSender().setHidden(CITIES, 508, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.EDGEVILLE)) {
            p.getPacketSender().setHidden(CITIES, 324, true);
            p.getPacketSender().setHidden(CITIES, 509, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 324, false);
            p.getPacketSender().setHidden(CITIES, 509, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FALADOR)) {
            p.getPacketSender().setHidden(CITIES, 347, true);
            p.getPacketSender().setHidden(CITIES, 510, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 347, false);
            p.getPacketSender().setHidden(CITIES, 510, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.KOUREND)) {
            p.getPacketSender().setHidden(CITIES, 348, true);
            p.getPacketSender().setHidden(CITIES, 511, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 348, false);
            p.getPacketSender().setHidden(CITIES, 511, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.LUMBRIDGE)) {
            p.getPacketSender().setHidden(CITIES, 349, true);
            p.getPacketSender().setHidden(CITIES, 512, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 349, false);
            p.getPacketSender().setHidden(CITIES, 512, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.PORT_KHAZARD)) {
            p.getPacketSender().setHidden(CITIES, 350, true);
            p.getPacketSender().setHidden(CITIES, 513, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 350, false);
            p.getPacketSender().setHidden(CITIES, 513, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.PRIFDDINAS_CITY)) {
            p.getPacketSender().setHidden(CITIES, 351, true);
            p.getPacketSender().setHidden(CITIES, 514, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 322, false);
            p.getPacketSender().setHidden(CITIES, 514, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.TREE_GNOME_STRONGHOLD)) {
            p.getPacketSender().setHidden(CITIES, 352, true);
            p.getPacketSender().setHidden(CITIES, 515, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 352, false);
            p.getPacketSender().setHidden(CITIES, 515, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.VARROCK)) {
            p.getPacketSender().setHidden(CITIES, 353, true);
            p.getPacketSender().setHidden(CITIES, 516, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 353, false);
            p.getPacketSender().setHidden(CITIES, 516, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.ZANARIS)) {
            p.getPacketSender().setHidden(CITIES, 354, true);
            p.getPacketSender().setHidden(CITIES, 517, false);
        } else {
            p.getPacketSender().setHidden(CITIES, 354, false);
            p.getPacketSender().setHidden(CITIES, 517, true);
        }
    }

    private static void sendSprites(Player p) {
        p.getPacketSender().sendItems(CITIES, 461, 0, new Item(4591));
        p.getPacketSender().sendItems(CITIES, 463, 0, new Item(14888));
        p.getPacketSender().sendItems(CITIES, 465, 0, new Item(16162));

        p.getPacketSender().sendItems(CITIES, 468, 0, new Item(3105));
        p.getPacketSender().sendItems(CITIES, 470, 0, new Item(14887));
        p.getPacketSender().sendItems(CITIES, 472, 0, new Item(19620));

        p.getPacketSender().sendItems(CITIES, 474, 0, new Item(385));
        p.getPacketSender().sendItems(CITIES, 476, 0, new Item(24777));
        p.getPacketSender().sendItems(CITIES, 478, 0, new Item(1704));

        p.getPacketSender().sendItems(CITIES, 480, 0, new Item(19626));
        p.getPacketSender().sendItems(CITIES, 482, 0, new Item(6623));
        p.getPacketSender().sendItems(CITIES, 484, 0, new Item(20263));

        p.getPacketSender().sendItems(CITIES, 486, 0, new Item(14885));
        p.getPacketSender().sendItems(CITIES, 488, 0, new Item(1775));
        p.getPacketSender().sendItems(CITIES, 490, 0, new Item(23951));

        p.getPacketSender().sendItems(CITIES, 492, 0, new Item(3257));
        p.getPacketSender().sendItems(CITIES, 494, 0, new Item(14884));
        p.getPacketSender().sendItems(CITIES, 496, 0, new Item(772));
    }

    public static void teleport(Player p) {
        if (!teleportSelected) {
            p.sendMessage("You must select a teleport first!");
        }
        if (alkharid) {
            telealkharid(p);
        } else if (ardougne) {
            teleardougne(p);
        } else if (brimhaven) {
            telebrimhaven(p);
        } else if (burthrope) {
            teleburthrope(p);
        } else if (camelot) {
            telecamelot(p);
        } else if (canafis) {
            telecanafis(p);
        } else if (catherby) {
            telecatherby(p);
        } else if (darkmeyer) {
            teledarkmeyer(p);
        } else if (draynor) {
            teledraynor(p);
        } else if (edgeville) {
            teleedge(p);
        } else if (falador) {
            telefalador(p);
        } else if (kourend) {
           telekourend(p);
        } else if (lumby) {
            telelumb(p);
        } else if (portkhazard) {
            teleportkhazard(p);
        } else if (prif) {
            teleprif(p);
        } else if (treegnomestronghold) {
            teletreegnome(p);
        } else if (varrock) {
            televarrock(p);
        } else if (zanaris) {
            telezanaris(p);
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

    public static void sendCityTeleports(Player p) {
        p.getPacketSender().sendString(CITIES, 88, "Al-Kharid");
        p.getPacketSender().sendString(CITIES, 89, "Ardougne");
        p.getPacketSender().sendString(CITIES, 90, "Brimhaven");
        p.getPacketSender().sendString(CITIES, 170, "Burthrope");
        p.getPacketSender().sendString(CITIES, 171, "Camelot");
        p.getPacketSender().sendString(CITIES, 172, "Canafis");
        p.getPacketSender().sendString(CITIES, 177, "Catherby");
        p.getPacketSender().sendString(CITIES, 178, "Darkmeyer");
        p.getPacketSender().sendString(CITIES, 179, "Draynor Village");
        p.getPacketSender().sendString(CITIES, 184, "Edgeville");
        p.getPacketSender().sendString(CITIES, 185, "Falador");
        p.getPacketSender().sendString(CITIES, 186, "Great Kourend");
        p.getPacketSender().sendString(CITIES, 191, "Lumbridge");
        p.getPacketSender().sendString(CITIES, 192, "Port Khazard");
        p.getPacketSender().sendString(CITIES, 193, "Prifddinas");
        p.getPacketSender().sendString(CITIES, 198, "Tree Gnome Stronghold");
        p.getPacketSender().sendString(CITIES, 199, "Varrock");
        p.getPacketSender().sendString(CITIES, 200, "Zanaris");

        p.getPacketSender().sendString(CITIES, 325, "I don't really like sand..");
        p.getPacketSender().sendString(CITIES, 326, "The city of thieving, and a few other things");
        p.getPacketSender().sendString(CITIES, 327, "What can I even do here?");
        p.getPacketSender().sendString(CITIES, 328, "Near the Warrior's Guild, and some trolls");
        p.getPacketSender().sendString(CITIES, 329, "Camelot/Seers Village");
        p.getPacketSender().sendString(CITIES, 330, "Home of the Swampman");
        p.getPacketSender().sendString(CITIES, 331, "Convenient area for fishing and farming");
        p.getPacketSender().sendString(CITIES, 332, "I can probably kill Vyrewatch here");
        p.getPacketSender().sendString(CITIES, 333, "Beginner area for fishing and agility");
        p.getPacketSender().sendString(CITIES, 338, "The home area of OSPK");
        p.getPacketSender().sendString(CITIES, 339, "Home to the White Knights");
        p.getPacketSender().sendString(CITIES, 340, "Teleports you to the center of Great Kourend");
        p.getPacketSender().sendString(CITIES, 341, "The iconic city of Lumbridge");
        p.getPacketSender().sendString(CITIES, 342, "Convenient crafting area for ironmen");
        p.getPacketSender().sendString(CITIES, 343, "The magical, elven city");
        p.getPacketSender().sendString(CITIES, 344, "Convenient area for beginner agility and Nieves cave");
        p.getPacketSender().sendString(CITIES, 345, "Teleports you to the center of Varrock");
        p.getPacketSender().sendString(CITIES, 346, "Fairies, zygomites and puro puro");
    }

    public static void selectalkharid(Player p) {
        clearSelections();
        alkharid = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Al-Kharid");
    }
    public static void telealkharid(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3289, 3183, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.AL_KHARID);
    }
    public static void selectardougne(Player p) {
        clearSelections();
        ardougne = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Ardougne");
    }
    public static void teleardougne(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2660, 3304, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ARDOUGNE);
    }
    public static void selectbrimhaven(Player p) {
        clearSelections();
        brimhaven = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Brimhaven");
    }
    public static void telebrimhaven(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2772, 3219, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.BRIMHAVEN);
    }
    public static void selectburthrope(Player p) {
        clearSelections();
        burthrope = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Burthrope");
    }
    public static void teleburthrope(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2899, 3546, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.BURTHROPE);
    }
    public static void selectcamelot(Player p) {
        clearSelections();
        camelot = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Camelot");
    }
    public static void telecamelot(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2726, 3485, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.CAMELOT);
    }
    public static void selectcanafis(Player p) {
        clearSelections();
        canafis = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Canafis");
    }
    public static void telecanafis(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3505, 3483, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.CANAFIS);
    }
    public static void selectcatherby(Player p) {
        clearSelections();
        catherby = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Catherby");
    }
    public static void telecatherby(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2809, 3435, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.CATHERBY);
    }
    public static void selectdarkmeyer(Player p) {
        clearSelections();
        darkmeyer = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Darkmeyer");
    }
    public static void teledarkmeyer(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3605, 3366, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.DARKMEYER);
    }
    public static void selectdraynor(Player p) {
        clearSelections();
        draynor = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Draynor Village");
    }
    public static void teledraynor(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3105, 3251, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.DRAYNOR);
    }
    public static void selectedge(Player p) {
        clearSelections();
        edgeville = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Edgeville");
    }
    public static void teleedge(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3103, 3503, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.EDGEVILLE);
    }
    public static void selectfalador(Player p) {
        clearSelections();
        falador = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Falador");
    }
    public static void telefalador(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2965, 3379, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FALADOR);
    }
    public static void selectkourend(Player p) {
        clearSelections();
        kourend = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Great Kourend");
    }
    public static void telekourend(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1642, 3673, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.KOUREND);
    }
    public static void selectlumb(Player p) {
        clearSelections();
        lumby = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Lumbridge");
    }
    public static void telelumb(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3224, 3219, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.LUMBRIDGE);
    }
    public static void selectportkhazard(Player p) {
        clearSelections();
        portkhazard = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Port Khazard");
    }
    public static void teleportkhazard(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2661, 3160, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.PORT_KHAZARD);
    }
    public static void selectprif(Player p) {
        clearSelections();
        prif = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Prifddinas");
    }
    public static void teleprif(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3263, 6066, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.PRIFDDINAS_CITY);
    }
    public static void selecttreegnome(Player p) {
        clearSelections();
        treegnomestronghold = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Tree Gnome Stronghold");
    }
    public static void teletreegnome(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2438, 3429, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.TREE_GNOME_STRONGHOLD);
    }
    public static void selectvarrock(Player p) {
        clearSelections();
        varrock = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Varrock");
    }
    public static void televarrock(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3213, 3424, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.VARROCK);
    }
    public static void selectzanaris(Player p) {
        clearSelections();
        zanaris = true;
        teleportSelected = true;
        p.getPacketSender().sendString(CITIES, 427, "Zanaris");
    }
    public static void telezanaris(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2452, 4467, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ZANARIS);
    }
}