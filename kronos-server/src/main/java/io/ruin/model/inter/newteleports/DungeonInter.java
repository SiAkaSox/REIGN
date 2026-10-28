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

public class DungeonInter {

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

            InterfaceHandler.register(DUNGEONS, h -> {
                h.actions[19] = (SlotAction) (player, slot) -> player.getTeleporter().openInterface(player);
                h.actions[36] = (SlotAction) (player, slot) -> openInterface(player);
                h.actions[37] = (SlotAction) (player, slot) -> BossesInter.openInterface(player);
                h.actions[38] = (SlotAction) (player, slot) -> MinigameInter.openInterface(player);
                h.actions[39] = (SlotAction) (player, slot) -> SkillingInter.openInterface(player);
                h.actions[40] = (SlotAction) (player, slot) -> WildernessInter.openInterface(player);
                h.actions[391] = (SlotAction) (player, slot) -> CityInter.openInterface(player);
                h.actions[392] = (SlotAction) (player, slot) -> MiscInter.openInterface(player);
                h.actions[400] = (SlotAction) (player, slot) -> Favorites.openInterface(player);
                h.actions[425] = (SlotAction) (player, slot) -> Teleporter.teleportPrevious(player);

                h.actions[368] = (SlotAction) (player, slot) -> selectAncientCavern(player);
                h.actions[369] = (SlotAction) (player, slot) -> selectAncientWyvernCavern(player);
                h.actions[370] = (SlotAction) (player, slot) -> selectAsgarnianIceDungeon(player);
                h.actions[371] = (SlotAction) (player, slot) -> selectBrimhaven(player);
                h.actions[372] = (SlotAction) (player, slot) -> selectCatacombs(player);
                h.actions[373] = (SlotAction) (player, slot) -> selectChasm(player);
                h.actions[374] = (SlotAction) (player, slot) -> selectForthos(player);
                h.actions[375] = (SlotAction) (player, slot) -> selectFremmy(player);
                h.actions[376] = (SlotAction) (player, slot) -> selectGiantsDen(player);
                h.actions[377] = (SlotAction) (player, slot) -> selectGodwars(player);
                h.actions[378] = (SlotAction) (player, slot) -> selectJormungands(player);
                h.actions[379] = (SlotAction) (player, slot) -> selectKaruulm(player);
                h.actions[380] = (SlotAction) (player, slot) -> selectLumby(player);
                h.actions[381] = (SlotAction) (player, slot) -> selectSmoke(player);
                h.actions[382] = (SlotAction) (player, slot) -> selectStronghold(player);
                h.actions[383] = (SlotAction) (player, slot) -> selectTaverly(player);
                h.actions[384] = (SlotAction) (player, slot) -> selectWaterbirth(player);
                h.actions[385] = (SlotAction) (player, slot) -> selectWaterfall(player);
                h.actions[428] = (SlotAction) (player, slot) -> teleport(player);

                h.actions[315] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ANCIENT_CAVERN, player, 315, 497);
                h.actions[316] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ANCIENT_WYVERN_CAVERN, player, 316, 498);
                h.actions[317] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ASGARNIAN_ICE_DUNGEON, player, 317, 499);
                h.actions[318] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.BRIMHAVEN, player, 318, 500);
                h.actions[319] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CATACOMBS, player, 319, 501);
                h.actions[320] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CHASM_OF_FIRE, player, 320, 502);
                h.actions[321] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FORTHOS_DUNGEON, player, 321, 503);
                h.actions[322] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FREMMY_DUNG, player, 322, 504);
                h.actions[323] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GIANTS_DEN, player, 323, 505);
                h.actions[324] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GODWARS_DUNGEON, player, 324, 506);
                h.actions[347] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.JORMUNGANDS_PRISON, player, 347, 507);
                h.actions[348] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.KARUULM, player, 348, 508);
                h.actions[349] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.LUMBY_CAVES, player, 349, 509);
                h.actions[350] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.SMOKE_DEVIL_DUNGEON, player, 350, 510);
                h.actions[351] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.STRONGHOLD_OF_SECURITY, player, 351, 511);
                h.actions[352] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.TAVERLY, player, 352, 512);
                h.actions[353] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WATERBIRTH, player, 353, 513);
                h.actions[354] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WATERFALL, player, 354, 514);

                h.actions[499] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ANCIENT_CAVERN, player, 317, 499);
                h.actions[500] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ANCIENT_WYVERN_CAVERN, player, 318, 500);
                h.actions[501] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ASGARNIAN_ICE_DUNGEON, player, 319, 501);
                h.actions[502] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.BRIMHAVEN, player, 320, 502);
                h.actions[503] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CATACOMBS, player, 321, 503);
                h.actions[504] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CHASM_OF_FIRE, player, 322, 504);
                h.actions[505] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FORTHOS_DUNGEON, player, 323, 505);
                h.actions[506] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FREMMY_DUNG, player, 324, 506);
                h.actions[507] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GIANTS_DEN, player, 347, 507);
                h.actions[508] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GODWARS_DUNGEON, player, 348, 508);
                h.actions[509] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.JORMUNGANDS_PRISON, player, 349, 509);
                h.actions[510] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.KARUULM, player, 350, 510);
                h.actions[511] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.LUMBY_CAVES, player, 351, 511);
                h.actions[512] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.SMOKE_DEVIL_DUNGEON, player, 352, 512);
                h.actions[513] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.STRONGHOLD_OF_SECURITY, player, 353, 513);
                h.actions[514] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.TAVERLY, player, 354, 514);
                h.actions[515] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WATERBIRTH, player, 354, 514);
                h.actions[516] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WATERFALL, player, 354, 514);
            });
    }

    private TeleportFavorites teleportFavorites;

    public DungeonInter() {
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
        DungeonInter instance = new DungeonInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.addFavorite(teleportOption, p);
        sendFavSprites(p);
    }

    private static void removeFavorite(TeleportInterface.Teleport teleOption, Player p, int silverStar, int goldStar) {
        DungeonInter instance = new DungeonInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.removeFavorite(teleOption, p);
        sendFavSprites(p);
    }

    public static void sendFavSprites(Player p) {
        if (p.favorites.contains(TeleportInterface.Teleport.ANCIENT_CAVERN)) {
            p.getPacketSender().setHidden(DUNGEONS, 315, true);
            p.getPacketSender().setHidden(DUNGEONS, 499, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 315, false);
            p.getPacketSender().setHidden(DUNGEONS, 499, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.ANCIENT_WYVERN_CAVERN)) {
            p.getPacketSender().setHidden(DUNGEONS, 316, true);
            p.getPacketSender().setHidden(DUNGEONS, 500, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 316, false);
            p.getPacketSender().setHidden(DUNGEONS, 500, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.ASGARNIAN_ICE_DUNGEON)) {
            p.getPacketSender().setHidden(DUNGEONS, 317, true);
            p.getPacketSender().setHidden(DUNGEONS, 501, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 317, false);
            p.getPacketSender().setHidden(DUNGEONS, 501, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.BRIMHAVEN)) {
            p.getPacketSender().setHidden(DUNGEONS, 318, true);
            p.getPacketSender().setHidden(DUNGEONS, 502, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 318, false);
            p.getPacketSender().setHidden(DUNGEONS, 502, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.CATACOMBS)) {
            p.getPacketSender().setHidden(DUNGEONS, 319, true);
            p.getPacketSender().setHidden(DUNGEONS, 503, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 319, false);
            p.getPacketSender().setHidden(DUNGEONS, 503, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.CHASM_OF_FIRE)) {
            p.getPacketSender().setHidden(DUNGEONS, 320, true);
            p.getPacketSender().setHidden(DUNGEONS, 504, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 320, false);
            p.getPacketSender().setHidden(DUNGEONS, 504, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FORTHOS_DUNGEON)) {
            p.getPacketSender().setHidden(DUNGEONS, 321, true);
            p.getPacketSender().setHidden(DUNGEONS, 505, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 321, false);
            p.getPacketSender().setHidden(DUNGEONS, 505, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FREMMY_DUNG)) {
            p.getPacketSender().setHidden(DUNGEONS, 322, true);
            p.getPacketSender().setHidden(DUNGEONS, 506, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 322, false);
            p.getPacketSender().setHidden(DUNGEONS, 506, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.GIANTS_DEN)) {
            p.getPacketSender().setHidden(DUNGEONS, 323, true);
            p.getPacketSender().setHidden(DUNGEONS, 507, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 323, false);
            p.getPacketSender().setHidden(DUNGEONS, 507, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.GODWARS_DUNGEON)) {
            p.getPacketSender().setHidden(DUNGEONS, 324, true);
            p.getPacketSender().setHidden(DUNGEONS, 508, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 324, false);
            p.getPacketSender().setHidden(DUNGEONS, 508, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.JORMUNGANDS_PRISON)) {
            p.getPacketSender().setHidden(DUNGEONS, 347, true);
            p.getPacketSender().setHidden(DUNGEONS, 509, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 347, false);
            p.getPacketSender().setHidden(DUNGEONS, 509, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.KARUULM)) {
            p.getPacketSender().setHidden(DUNGEONS, 348, true);
            p.getPacketSender().setHidden(DUNGEONS, 510, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 348, false);
            p.getPacketSender().setHidden(DUNGEONS, 510, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.LUMBY_CAVES)) {
            p.getPacketSender().setHidden(DUNGEONS, 349, true);
            p.getPacketSender().setHidden(DUNGEONS, 511, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 349, false);
            p.getPacketSender().setHidden(DUNGEONS, 511, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.SMOKE_DEVIL_DUNGEON)) {
            p.getPacketSender().setHidden(DUNGEONS, 350, true);
            p.getPacketSender().setHidden(DUNGEONS, 512, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 350, false);
            p.getPacketSender().setHidden(DUNGEONS, 512, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.STRONGHOLD_OF_SECURITY)) {
            p.getPacketSender().setHidden(DUNGEONS, 351, true);
            p.getPacketSender().setHidden(DUNGEONS, 513, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 322, false);
            p.getPacketSender().setHidden(DUNGEONS, 513, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.TAVERLY)) {
            p.getPacketSender().setHidden(DUNGEONS, 352, true);
            p.getPacketSender().setHidden(DUNGEONS, 514, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 352, false);
            p.getPacketSender().setHidden(DUNGEONS, 514, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.WATERBIRTH)) {
            p.getPacketSender().setHidden(DUNGEONS, 353, true);
            p.getPacketSender().setHidden(DUNGEONS, 515, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 353, false);
            p.getPacketSender().setHidden(DUNGEONS, 515, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.WATERFALL)) {
            p.getPacketSender().setHidden(DUNGEONS, 354, true);
            p.getPacketSender().setHidden(DUNGEONS, 516, false);
        } else {
            p.getPacketSender().setHidden(DUNGEONS, 354, false);
            p.getPacketSender().setHidden(DUNGEONS, 516, true);
        }
    }

    public static boolean teleportSelected = false;

    //Monsters
    public static boolean ancientCavern, ancientwyvernCavern, asgarnianiceDungeon, brimDungeon, catacombs, chasmofFire,
            forthos, fremmyDungeon, giantsDen, godwarsDungeon, jormungandsPrison, karuulm, lumbyCaves,
            smokeDevil, strongholdofSecurity, taverly, waterbirth, waterfall = false;

    public static void clearSelections() {
        ancientCavern = false; ancientwyvernCavern = false; asgarnianiceDungeon = false; brimDungeon = false; catacombs = false;
        chasmofFire = false; forthos= false; fremmyDungeon= false; giantsDen = false; godwarsDungeon = false; jormungandsPrison= false;
        karuulm= false; lumbyCaves= false; smokeDevil = false; strongholdofSecurity = false; taverly = false;
        waterbirth = false; waterfall = false;
    }

    public static void openInterface(Player p) {
        p.openInterface(InterfaceType.MAIN, DUNGEONS);
        sendDungeonTeleports(p);
        teleportSelected = false;
        sendInfo(p);
        sendSprites(p);
        sendFavSprites(p);
    }

    private static void sendInfo(Player p) {
        if (p.getTeleports().getLastTeleport() == null) {
            p.getPacketSender().sendString(DUNGEONS, 423, "None!");
        } else {
            p.getPacketSender().sendString(DUNGEONS, 423, "" +p.getTeleports().getLastTeleport().getName());
        }
    }

    private static void sendSprites(Player p) {
        p.getPacketSender().sendItems(DUNGEONS, 461, 0, new Item(2359));
        p.getPacketSender().sendItems(DUNGEONS, 463, 0, new Item(6812));
        p.getPacketSender().sendItems(DUNGEONS, 465, 0, new Item(16472));

        p.getPacketSender().sendItems(DUNGEONS, 468, 0, new Item(12365));
        p.getPacketSender().sendItems(DUNGEONS, 470, 0, new Item(19685));
        p.getPacketSender().sendItems(DUNGEONS, 472, 0, new Item(20026));

        p.getPacketSender().sendItems(DUNGEONS, 474, 0, new Item(23499));
        p.getPacketSender().sendItems(DUNGEONS, 476, 0, new Item(20727));
        p.getPacketSender().sendItems(DUNGEONS, 478, 0, new Item(13474));

        p.getPacketSender().sendItems(DUNGEONS, 480, 0, new Item(12650));
        p.getPacketSender().sendItems(DUNGEONS, 482, 0, new Item(24268));
        p.getPacketSender().sendItems(DUNGEONS, 484, 0, new Item(22966));

        p.getPacketSender().sendItems(DUNGEONS, 486, 0, new Item(15748));
        p.getPacketSender().sendItems(DUNGEONS, 488, 0, new Item(12002));
        p.getPacketSender().sendItems(DUNGEONS, 490, 0, new Item(9005));

        p.getPacketSender().sendItems(DUNGEONS, 492, 0, new Item(1751));
        p.getPacketSender().sendItems(DUNGEONS, 494, 0, new Item(8141));
        p.getPacketSender().sendItems(DUNGEONS, 496, 0, new Item(1333));
    }

    public static void teleport(Player p) {
        if (!teleportSelected) {
            p.sendMessage("You must select a teleport first!");
        }
        if (ancientCavern) {
            teleportAncientCavern(p);
        } else if (ancientwyvernCavern) {
            teleportAncientWyvernCavern(p);
        } else if (asgarnianiceDungeon) {
            teleportAsgarnianIceDungeon(p);
        } else if (brimDungeon) {
            teleportBrimhaven(p);
        } else if (catacombs) {
            teleportCatacombs(p);
        } else if (chasmofFire) {
            teleportChasm(p);
        } else if (forthos) {
            teleportForthos(p);
        } else if (fremmyDungeon) {
            teleportFremmy(p);
        } else if (giantsDen) {
            teleportGiantsDen(p);
        } else if (godwarsDungeon) {
            teleportGodwars(p);
        } else if (jormungandsPrison) {
            teleportJormungands(p);
        } else if (karuulm) {
            teleportKaruulm(p);
        } else if (lumbyCaves) {
            teleportLumby(p);
        } else if (smokeDevil) {
            teleportSmoke(p);
        } else if (strongholdofSecurity) {
            teleportStronghold(p);
        } else if (taverly) {
            teleportTaverly(p);
        } else if (waterbirth) {
            teleportWaterbirth(p);
        } else if (waterfall) {
            teleportWaterfall(p);
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

    public static void sendDungeonTeleports(Player p) {
        p.getPacketSender().sendString(DUNGEONS, 88, "Ancient Cavern");
        p.getPacketSender().sendString(DUNGEONS, 89, "Ancient Wyvern Cavern");
        p.getPacketSender().sendString(DUNGEONS, 90, "Asgarnian Ice Dungeon");
        p.getPacketSender().sendString(DUNGEONS, 170, "Brimhaven Dungeon");
        p.getPacketSender().sendString(DUNGEONS, 171, "Catacombs of Kourend");
        p.getPacketSender().sendString(DUNGEONS, 172, "Chasm of Fire");
        p.getPacketSender().sendString(DUNGEONS, 177, "Forthos Dungeon");
        p.getPacketSender().sendString(DUNGEONS, 178, "Fremennik Slayer Dungeon");
        p.getPacketSender().sendString(DUNGEONS, 179, "Giants' Den");
        p.getPacketSender().sendString(DUNGEONS, 184, "Godwars Dungeon");
        p.getPacketSender().sendString(DUNGEONS, 185, "Jormungand's Prison");
        p.getPacketSender().sendString(DUNGEONS, 186, "Karuulm Dungeon");
        p.getPacketSender().sendString(DUNGEONS, 191, "Lumbridge Swamp Caves");
        p.getPacketSender().sendString(DUNGEONS, 192, "Smoke Devil Dungeon");
        p.getPacketSender().sendString(DUNGEONS, 193, "Stronghold Of Security");
        p.getPacketSender().sendString(DUNGEONS, 198, "Taverly Dungeon");
        p.getPacketSender().sendString(DUNGEONS, 199, "Waterbirth Dungeon");
        p.getPacketSender().sendString(DUNGEONS, 200, "Waterfall Dungeon");

        p.getPacketSender().sendString(DUNGEONS, 325, "Home to mithril dragons and waterfiends");
        p.getPacketSender().sendString(DUNGEONS, 326, "Contains all four wyvern types");
        p.getPacketSender().sendString(DUNGEONS, 327, "Ice giants and ice warriors");
        p.getPacketSender().sendString(DUNGEONS, 328, "Moss giants, greater demons, iron/steel dragons");
        p.getPacketSender().sendString(DUNGEONS, 329, "Higher level slayer task monsters");
        p.getPacketSender().sendString(DUNGEONS, 330, "Home to lesser, greater and black Demons");
        p.getPacketSender().sendString(DUNGEONS, 331, "Home to sarachnis, red dragons and spiders");
        p.getPacketSender().sendString(DUNGEONS, 332, "Mid level slayer tasks located here");
        p.getPacketSender().sendString(DUNGEONS, 333, "Houses fire, hill and moss giants");
        p.getPacketSender().sendString(DUNGEONS, 338, "Teleports you to the center of godwars dungeon");
        p.getPacketSender().sendString(DUNGEONS, 339, "Contains basilisks and basilisk knights");
        p.getPacketSender().sendString(DUNGEONS, 340, "High level slayer tasks located here");
        p.getPacketSender().sendString(DUNGEONS, 341, "Low level slayer tasks");
        p.getPacketSender().sendString(DUNGEONS, 342, "Smoke devils and thermonuclear smoke devil");
        p.getPacketSender().sendString(DUNGEONS, 343, "Low level monsters");
        p.getPacketSender().sendString(DUNGEONS, 344, "Home to blue/black dragons and black demons");
        p.getPacketSender().sendString(DUNGEONS, 345, "Contains dagannoths and dagannoth kings");
        p.getPacketSender().sendString(DUNGEONS, 346, "Home to fire giants, skeletons and spiders");
    }

    public static void selectAncientCavern(Player p) {
        clearSelections();
        ancientCavern = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Ancient Cavern");
    }
    public static void teleportAncientCavern(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1762, 5361, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ANCIENT_CAVERN);
    }
    public static void selectAncientWyvernCavern(Player p) {
        clearSelections();
        ancientwyvernCavern = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Ancient Wyvern Cavern");
    }
    public static void teleportAncientWyvernCavern(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3604, 10231, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ANCIENT_WYVERN_CAVERN);
    }
    public static void selectAsgarnianIceDungeon(Player p) {
        clearSelections();
        asgarnianiceDungeon = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Asgarnian Ice Dungeon");
    }
    public static void teleportAsgarnianIceDungeon(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3006, 9550, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ASGARNIAN_ICE_DUNGEON);
    }
    public static void selectBrimhaven(Player p) {
        clearSelections();
        brimDungeon = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Brimhaven Dungeon");
    }
    public static void teleportBrimhaven(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2710, 9564, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.BRIMHAVEN);
    }
    public static void selectCatacombs(Player p) {
        clearSelections();
        catacombs = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Catacombs of Kourend");
    }
    public static void teleportCatacombs(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1665, 10049, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.CATACOMBS);
    }
    public static void selectChasm(Player p) {
        clearSelections();
        chasmofFire = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Chasm of Fire");
    }
    public static void teleportChasm(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1435, 3671, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.CHASM_OF_FIRE1);
    }
    public static void selectForthos(Player p) {
        clearSelections();
        forthos = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Forthos Dungeon");
    }
    public static void teleportForthos(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1800, 9967, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FORTHOS_DUNGEON);
    }
    public static void selectFremmy(Player p) {
        clearSelections();
        fremmyDungeon = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Fremennik Slayer Dungeon");
    }
    public static void teleportFremmy(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2805, 10001, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FREMMY_DUNG);
    }
    public static void selectGiantsDen(Player p) {
        clearSelections();
        giantsDen = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Giants' Den");
    }
    public static void teleportGiantsDen(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1432, 9912, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.GIANTS_DEN);
    }
    public static void selectGodwars(Player p) {
        clearSelections();
        godwarsDungeon = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Godwars Dungeon");
    }
    public static void teleportGodwars(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2915, 3746, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.GODWARS_DUNGEON);
    }
    public static void selectJormungands(Player p) {
        clearSelections();
        jormungandsPrison = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Jormungand's Prison");
    }
    public static void teleportJormungands(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2448, 10402, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.JORMUNGANDS_PRISON);
    }
    public static void selectKaruulm(Player p) {
        clearSelections();
        karuulm = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Mount Karuulm Dungeon");
    }
    public static void teleportKaruulm(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1311, 10188, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.KARUULM);
    }
    public static void selectLumby(Player p) {
        clearSelections();
        lumbyCaves = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Lumbridge Swamp Caves");
    }
    public static void teleportLumby(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3168, 9572, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.LUMBY_CAVES);
    }
    public static void selectSmoke(Player p) {
        clearSelections();
        smokeDevil = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Smoke Devil Dungeon");
    }
    public static void teleportSmoke(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2412, 3060, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.SMOKE_DEVIL_DUNGEON);
    }
    public static void selectStronghold(Player p) {
        clearSelections();
        strongholdofSecurity = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Stronghold Of Security");
    }
    public static void teleportStronghold(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3081, 3421, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.STRONGHOLD_OF_SECURITY);
    }
    public static void selectTaverly(Player p) {
        clearSelections();
        taverly = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Taverly Dungeon");
    }
    public static void teleportTaverly(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2884, 9799, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.TAVERLY);
    }
    public static void selectWaterbirth(Player p) {
        clearSelections();
        waterbirth = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Waterbirth Dungeon");
    }
    public static void teleportWaterbirth(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2443, 10146, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.WATERBIRTH);
    }
    public static void selectWaterfall(Player p) {
        clearSelections();
        waterfall = true;
        teleportSelected = true;
        p.getPacketSender().sendString(DUNGEONS, 427, "Waterfall Dungeon");
    }
    public static void teleportWaterfall(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2573, 9866, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.WATERFALL);
    }
}