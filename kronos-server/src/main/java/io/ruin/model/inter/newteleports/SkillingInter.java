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
import io.ruin.model.stat.StatType;
import lombok.Getter;
import lombok.Setter;

public class SkillingInter {

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

            InterfaceHandler.register(SKILLING, h -> {
                h.actions[19] = (SlotAction) (player, slot) -> player.getTeleporter().openInterface(player);
                h.actions[36] = (SlotAction) (player, slot) -> DungeonInter.openInterface(player);
                h.actions[37] = (SlotAction) (player, slot) -> BossesInter.openInterface(player);
                h.actions[38] = (SlotAction) (player, slot) -> MinigameInter.openInterface(player);
                h.actions[39] = (SlotAction) (player, slot) -> openInterface(player);
                h.actions[40] = (SlotAction) (player, slot) -> WildernessInter.openInterface(player);
                h.actions[391] = (SlotAction) (player, slot) -> CityInter.openInterface(player);
                h.actions[392] = (SlotAction) (player, slot) -> MiscInter.openInterface(player);
                h.actions[400] = (SlotAction) (player, slot) -> Favorites.openInterface(player);
                h.actions[425] = (SlotAction) (player, slot) -> Teleporter.teleportPrevious(player);

                h.actions[368] = (SlotAction) (player, slot) -> selectgnome(player);
                h.actions[369] = (SlotAction) (player, slot) -> selectdraynor(player);
                h.actions[370] = (SlotAction) (player, slot) -> selectalkharid(player);
                h.actions[371] = (SlotAction) (player, slot) -> selectvarrock(player);
                h.actions[372] = (SlotAction) (player, slot) -> selectcanafis(player);
                h.actions[373] = (SlotAction) (player, slot) -> selectfalador(player);
                h.actions[374] = (SlotAction) (player, slot) -> selectagilWild(player);
                h.actions[375] = (SlotAction) (player, slot) -> selectseers(player);
                h.actions[376] = (SlotAction) (player, slot) -> selectprif(player);
                h.actions[377] = (SlotAction) (player, slot) -> selectrellekka(player);
                h.actions[378] = (SlotAction) (player, slot) -> selectardougne(player);
                h.actions[379] = (SlotAction) (player, slot) -> selectcraftingguild(player);
                h.actions[380] = (SlotAction) (player, slot) -> selectfarmguild(player);
                h.actions[381] = (SlotAction) (player, slot) -> selectfarmardy(player);
                h.actions[382] = (SlotAction) (player, slot) -> selectfarmcanafis(player);
                h.actions[383] = (SlotAction) (player, slot) -> selectfarmcath(player);
                h.actions[384] = (SlotAction) (player, slot) -> selectfarmfally(player);
                h.actions[385] = (SlotAction) (player, slot) -> selectfarmtroll(player);
                h.actions[510] = (SlotAction) (player, slot) -> selectfarmzeah(player);
                h.actions[511] = (SlotAction) (player, slot) -> selectfishingguild(player);
                h.actions[512] = (SlotAction) (player, slot) -> selectfishingangler(player);
                h.actions[529] = (SlotAction) (player, slot) -> selectfishingbarb(player);
                h.actions[530] = (SlotAction) (player, slot) -> selectfishcath(player);
                h.actions[531] = (SlotAction) (player, slot) -> selectfishlumb(player);
                h.actions[548] = (SlotAction) (player, slot) -> selectfishpisc(player);
                h.actions[549] = (SlotAction) (player, slot) -> selecthuntbird(player);
                h.actions[550] = (SlotAction) (player, slot) -> selecthuntfeld(player);
                h.actions[567] = (SlotAction) (player, slot) -> selecthuntpisc(player);
                h.actions[568] = (SlotAction) (player, slot) -> selectpuro(player);
                h.actions[569] = (SlotAction) (player, slot) -> selectminingguild(player);
                h.actions[586] = (SlotAction) (player, slot) -> selectminingalkharid(player);
                h.actions[587] = (SlotAction) (player, slot) -> selectessence(player);
                h.actions[588] = (SlotAction) (player, slot) -> selectminingvarrock(player);
                h.actions[605] = (SlotAction) (player, slot) -> selectabyss(player);
                h.actions[606] = (SlotAction) (player, slot) -> selectsmith(player);
                h.actions[607] = (SlotAction) (player, slot) -> selectwoodcuttinguild(player);
                h.actions[624] = (SlotAction) (player, slot) -> selectwoodcutdraynor(player);
                h.actions[625] = (SlotAction) (player, slot) -> selectwoodcutseers(player);

                h.actions[315] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GNOME_COURSE, player, 315, 497);
                h.actions[316] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DRAYNOR_COURSE, player, 316, 498);
                h.actions[317] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ALKHARID_COURSE, player, 317, 499);
                h.actions[318] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.VARROCK_COURSE, player, 318, 500);
                h.actions[319] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CANNIFIS_COURSE, player, 319, 501);
                h.actions[320] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FALADOR_COURSE, player, 320, 502);
                h.actions[321] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WILD_COURSE, player, 321, 503);
                h.actions[322] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.SEERS_COURSE, player, 322, 504);
                h.actions[323] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.PRIF_COURSE, player, 323, 505);
                h.actions[324] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.RELLEKKA_COURSE, player, 324, 506);
                h.actions[347] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ARDOUGNE_COURSE, player, 347, 507);
                h.actions[348] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CRAFTING_GUILD, player, 348, 508);
                h.actions[349] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FARMING_GUILD, player, 349, 509);
                h.actions[350] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FARMING_ARDY, player, 350, 510);
                h.actions[351] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FARMING_CANAFIS, player, 351, 511);
                h.actions[352] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FARMING_CATH, player, 352, 512);
                h.actions[353] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FARMING_FALLY, player, 353, 513);
                h.actions[354] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FARMING_TROLL, player, 354, 514);
                h.actions[686] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FARMING_ZEAH, player, 354, 514);
                h.actions[687] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FISHING_GUILD, player, 354, 514);
                h.actions[688] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.KOUREND_FISHING, player, 354, 514);
                h.actions[689] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.BARB_VILLAGE_FISH, player, 354, 514);
                h.actions[690] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CATHERBY_FISHING, player, 354, 514);
                h.actions[633] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.LUMB_FISHING, player, 354, 514);
                h.actions[634] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.PISCATORIS_FISHING, player, 354, 514);
                h.actions[635] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.HUNTER_BIRDHOUSES, player, 354, 514);
                h.actions[636] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FELDIP_HILLS, player, 354, 514);
                h.actions[637] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.PISCATORIS, player, 354, 514);
                h.actions[638] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.PURO_PURO, player, 354, 514);
                h.actions[639] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.MINING_GUILD, player, 354, 514);
                h.actions[640] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.MINING_ALKHARID, player, 354, 514);
                h.actions[641] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ESSENCE_MINE, player, 354, 514);
                h.actions[642] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.MINING_VARROCK, player, 354, 514);
                h.actions[643] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ABYSS, player, 354, 514);
                h.actions[644] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.VARROCK_WEST, player, 354, 514);
                h.actions[645] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WOODCUT_GUILD, player, 354, 514);
                h.actions[646] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DRAYNOR_WOODCUTTING, player, 354, 514);
                h.actions[647] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WOODCUT_SEERS, player, 354, 514);

                h.actions[648] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.GNOME_COURSE, player, 315, 497);
                h.actions[649] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DRAYNOR_COURSE, player, 316, 498);
                h.actions[650] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ALKHARID_COURSE, player, 317, 499);
                h.actions[651] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.VARROCK_COURSE, player, 318, 500);
                h.actions[652] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CANNIFIS_COURSE, player, 319, 501);
                h.actions[653] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FALADOR_COURSE, player, 320, 502);
                h.actions[654] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WILD_COURSE, player, 321, 503);
                h.actions[655] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.SEERS_COURSE, player, 322, 504);
                h.actions[656] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.PRIF_COURSE, player, 323, 505);
                h.actions[657] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.RELLEKKA_COURSE, player, 324, 506);
                h.actions[658] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ARDOUGNE_COURSE, player, 347, 507);
                h.actions[659] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CRAFTING_GUILD, player, 348, 508);
                h.actions[660] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FARMING_GUILD, player, 349, 509);
                h.actions[661] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FARMING_ARDY, player, 350, 510);
                h.actions[662] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FARMING_CANAFIS, player, 351, 511);
                h.actions[663] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FARMING_CATH, player, 352, 512);
                h.actions[664] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FARMING_FALLY, player, 353, 513);
                h.actions[665] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FARMING_TROLL, player, 354, 514);
                h.actions[666] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FARMING_ZEAH, player, 354, 514);
                h.actions[667] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FISHING_GUILD, player, 354, 514);
                h.actions[668] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.KOUREND_FISHING, player, 354, 514);
                h.actions[669] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.BARB_VILLAGE_FISH, player, 354, 514);
                h.actions[670] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CATHERBY_FISHING, player, 354, 514);
                h.actions[671] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.LUMB_FISHING, player, 354, 514);
                h.actions[672] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.PISCATORIS_FISHING, player, 354, 514);
                h.actions[673] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.HUNTER_BIRDHOUSES, player, 354, 514);
                h.actions[674] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FELDIP_HILLS, player, 354, 514);
                h.actions[675] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.PISCATORIS, player, 354, 514);
                h.actions[676] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.PURO_PURO, player, 354, 514);
                h.actions[677] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.MINING_GUILD, player, 354, 514);
                h.actions[678] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.MINING_ALKHARID, player, 354, 514);
                h.actions[679] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ESSENCE_MINE, player, 354, 514);
                h.actions[680] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.MINING_VARROCK, player, 354, 514);
                h.actions[681] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ABYSS, player, 354, 514);
                h.actions[682] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.VARROCK_WEST, player, 354, 514);
                h.actions[683] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WOODCUT_GUILD, player, 354, 514);
                h.actions[684] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DRAYNOR_WOODCUTTING, player, 354, 514);
                h.actions[685] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WOODCUT_SEERS, player, 354, 514);

                h.actions[428] = (SlotAction) (player, slot) -> teleport(player);
            });
    }

    public static boolean teleportSelected = false;

    //Monsters
    public static boolean prifcourse,wildcourse,farmguild,farmardy,farmcanafis,farmcath,farmfally,farmtroll,farmzeah,
            fishangler,fishbarb,fishcath,fishlumb,fishpisc,huntbirdhouse,huntfeld,huntpist,puropuro,miningalkharid,
            miningvarrock,runecraft,smithing,woodcutdraynor,woodcutseers,
            alkharidcourse, ardougnecourse, barbcourse, canafiscourse, catherbyfishing, craftinguild,
            draynorcourse, essencemine, faladorcourse, fishingguild, gnomeagility, miningguild, piscatorisfishing,
            piscatoriswoodland, rellekkacourse, seerscourse, varrockcourse, woodcuttingguild = false;

    public static void clearSelections() {
        prifcourse = false; wildcourse = false; farmguild = false; farmardy = false; farmcanafis = false; farmcath = false;
        farmfally = false; farmtroll = false; farmzeah = false; fishangler = false; fishbarb = false; fishcath = false;
        fishlumb = false; fishpisc = false; huntbirdhouse = false; huntfeld = false; huntpist = false; puropuro = false;
        miningalkharid = false; miningvarrock = false; runecraft = false; smithing = false; woodcutdraynor = false;
        alkharidcourse = false; ardougnecourse = false; barbcourse = false; canafiscourse = false; catherbyfishing = false;
        craftinguild = false; draynorcourse = false; essencemine = false; faladorcourse = false; fishingguild = false; gnomeagility = false;
        miningguild = false; piscatorisfishing = false; piscatoriswoodland = false; rellekkacourse = false; seerscourse = false;
        varrockcourse = false; woodcuttingguild = false; woodcutseers = false;
    }

    public static void openInterface(Player p) {
        p.openInterface(InterfaceType.MAIN, SKILLING);
        sendSkillingTeles(p);
        teleportSelected = false;
        sendInfo(p);
        sendSprites(p);
        sendFavSprites(p);
    }

    private static void sendInfo(Player p) {
        if (p.getTeleports().getLastTeleport() == null) {
            p.getPacketSender().sendString(SKILLING, 423, "None!");
        } else {
            p.getPacketSender().sendString(SKILLING, 423, "" +p.getTeleports().getLastTeleport().getName());
        }
    }

    private TeleportFavorites teleportFavorites;

    public SkillingInter() {
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
        SkillingInter instance = new SkillingInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.addFavorite(teleportOption, p);
        sendFavSprites(p);
    }

    private static void removeFavorite(TeleportInterface.Teleport teleOption, Player p, int silverStar, int goldStar) {
        SkillingInter instance = new SkillingInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.removeFavorite(teleOption, p);
        sendFavSprites(p);
    }

    public static void sendFavSprites(Player p) {
        if (p.favorites.contains(TeleportInterface.Teleport.GNOME_COURSE)) {
            p.getPacketSender().setHidden(SKILLING, 315, true);
            p.getPacketSender().setHidden(SKILLING, 648, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 315, false);
            p.getPacketSender().setHidden(SKILLING, 648, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.DRAYNOR_COURSE)) {
            p.getPacketSender().setHidden(SKILLING, 316, true);
            p.getPacketSender().setHidden(SKILLING, 649, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 316, false);
            p.getPacketSender().setHidden(SKILLING, 649, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.ALKHARID_COURSE)) {
            p.getPacketSender().setHidden(SKILLING, 317, true);
            p.getPacketSender().setHidden(SKILLING, 650, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 317, false);
            p.getPacketSender().setHidden(SKILLING, 650, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.VARROCK_COURSE)) {
            p.getPacketSender().setHidden(SKILLING, 318, true);
            p.getPacketSender().setHidden(SKILLING, 651, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 318, false);
            p.getPacketSender().setHidden(SKILLING, 651, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.CANNIFIS_COURSE)) {
            p.getPacketSender().setHidden(SKILLING, 319, true);
            p.getPacketSender().setHidden(SKILLING, 652, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 319, false);
            p.getPacketSender().setHidden(SKILLING, 652, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FALADOR_COURSE)) {
            p.getPacketSender().setHidden(SKILLING, 320, true);
            p.getPacketSender().setHidden(SKILLING, 653, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 320, false);
            p.getPacketSender().setHidden(SKILLING, 653, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.WILD_COURSE)) {
            p.getPacketSender().setHidden(SKILLING, 321, true);
            p.getPacketSender().setHidden(SKILLING, 654, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 321, false);
            p.getPacketSender().setHidden(SKILLING, 654, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.SEERS_COURSE)) {
            p.getPacketSender().setHidden(SKILLING, 322, true);
            p.getPacketSender().setHidden(SKILLING, 655, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 322, false);
            p.getPacketSender().setHidden(SKILLING, 655, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.PRIF_COURSE)) {
            p.getPacketSender().setHidden(SKILLING, 323, true);
            p.getPacketSender().setHidden(SKILLING, 656, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 323, false);
            p.getPacketSender().setHidden(SKILLING, 656, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.RELLEKKA_COURSE)) {
            p.getPacketSender().setHidden(SKILLING, 324, true);
            p.getPacketSender().setHidden(SKILLING, 657, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 324, false);
            p.getPacketSender().setHidden(SKILLING, 657, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.ARDOUGNE_COURSE)) {
            p.getPacketSender().setHidden(SKILLING, 347, true);
            p.getPacketSender().setHidden(SKILLING, 658, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 347, false);
            p.getPacketSender().setHidden(SKILLING, 658, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.CRAFTING_GUILD)) {
            p.getPacketSender().setHidden(SKILLING, 348, true);
            p.getPacketSender().setHidden(SKILLING, 659, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 348, false);
            p.getPacketSender().setHidden(SKILLING, 659, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FARMING_GUILD)) {
            p.getPacketSender().setHidden(SKILLING, 349, true);
            p.getPacketSender().setHidden(SKILLING, 660, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 349, false);
            p.getPacketSender().setHidden(SKILLING, 660, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FARMING_ARDY)) {
            p.getPacketSender().setHidden(SKILLING, 350, true);
            p.getPacketSender().setHidden(SKILLING, 661, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 350, false);
            p.getPacketSender().setHidden(SKILLING, 661, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FARMING_CANAFIS)) {
            p.getPacketSender().setHidden(SKILLING, 351, true);
            p.getPacketSender().setHidden(SKILLING, 662, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 322, false);
            p.getPacketSender().setHidden(SKILLING, 662, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FARMING_CATH)) {
            p.getPacketSender().setHidden(SKILLING, 352, true);
            p.getPacketSender().setHidden(SKILLING, 663, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 352, false);
            p.getPacketSender().setHidden(SKILLING, 663, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FARMING_FALLY)) {
            p.getPacketSender().setHidden(SKILLING, 353, true);
            p.getPacketSender().setHidden(SKILLING, 664, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 353, false);
            p.getPacketSender().setHidden(SKILLING, 664, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FARMING_TROLL)) {
            p.getPacketSender().setHidden(SKILLING, 354, true);
            p.getPacketSender().setHidden(SKILLING, 665, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 354, false);
            p.getPacketSender().setHidden(SKILLING, 665, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FARMING_ZEAH)) {
            p.getPacketSender().setHidden(SKILLING, 686, true);
            p.getPacketSender().setHidden(SKILLING, 666, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 686, false);
            p.getPacketSender().setHidden(SKILLING, 666, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FISHING_GUILD)) {
            p.getPacketSender().setHidden(SKILLING, 687, true);
            p.getPacketSender().setHidden(SKILLING, 667, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 687, false);
            p.getPacketSender().setHidden(SKILLING, 667, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.KOUREND_FISHING)) {
            p.getPacketSender().setHidden(SKILLING, 688, true);
            p.getPacketSender().setHidden(SKILLING, 668, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 688, false);
            p.getPacketSender().setHidden(SKILLING, 668, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.BARB_VILLAGE_FISH)) {
            p.getPacketSender().setHidden(SKILLING, 689, true);
            p.getPacketSender().setHidden(SKILLING, 669, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 689, false);
            p.getPacketSender().setHidden(SKILLING, 669, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.CATHERBY_FISHING)) {
            p.getPacketSender().setHidden(SKILLING, 690, true);
            p.getPacketSender().setHidden(SKILLING, 670, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 690, false);
            p.getPacketSender().setHidden(SKILLING, 670, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.LUMB_FISHING)) {
            p.getPacketSender().setHidden(SKILLING, 633, true);
            p.getPacketSender().setHidden(SKILLING, 671, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 633, false);
            p.getPacketSender().setHidden(SKILLING, 671, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.PISCATORIS_FISHING)) {
            p.getPacketSender().setHidden(SKILLING, 634, true);
            p.getPacketSender().setHidden(SKILLING, 672, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 634, false);
            p.getPacketSender().setHidden(SKILLING, 672, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.HUNTER_BIRDHOUSES)) {
            p.getPacketSender().setHidden(SKILLING, 635, true);
            p.getPacketSender().setHidden(SKILLING, 673, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 635, false);
            p.getPacketSender().setHidden(SKILLING, 673, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FELDIP_HILLS)) {
            p.getPacketSender().setHidden(SKILLING, 636, true);
            p.getPacketSender().setHidden(SKILLING, 674, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 636, false);
            p.getPacketSender().setHidden(SKILLING, 674, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.PISCATORIS)) {
            p.getPacketSender().setHidden(SKILLING, 637, true);
            p.getPacketSender().setHidden(SKILLING, 675, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 637, false);
            p.getPacketSender().setHidden(SKILLING, 675, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.PURO_PURO)) {
            p.getPacketSender().setHidden(SKILLING, 638, true);
            p.getPacketSender().setHidden(SKILLING, 676, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 638, false);
            p.getPacketSender().setHidden(SKILLING, 676, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.MINING_GUILD)) {
            p.getPacketSender().setHidden(SKILLING, 639, true);
            p.getPacketSender().setHidden(SKILLING, 677, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 639, false);
            p.getPacketSender().setHidden(SKILLING, 677, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.MINING_ALKHARID)) {
            p.getPacketSender().setHidden(SKILLING, 640, true);
            p.getPacketSender().setHidden(SKILLING, 678, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 640, false);
            p.getPacketSender().setHidden(SKILLING, 678, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.ESSENCE_MINE)) {
            p.getPacketSender().setHidden(SKILLING, 641, true);
            p.getPacketSender().setHidden(SKILLING, 679, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 641, false);
            p.getPacketSender().setHidden(SKILLING, 679, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.MINING_VARROCK)) {
            p.getPacketSender().setHidden(SKILLING, 642, true);
            p.getPacketSender().setHidden(SKILLING, 680, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 642, false);
            p.getPacketSender().setHidden(SKILLING, 680, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.ABYSS)) {
            p.getPacketSender().setHidden(SKILLING, 643, true);
            p.getPacketSender().setHidden(SKILLING, 681, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 643, false);
            p.getPacketSender().setHidden(SKILLING, 681, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.VARROCK_WEST)) {
            p.getPacketSender().setHidden(SKILLING, 644, true);
            p.getPacketSender().setHidden(SKILLING, 682, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 644, false);
            p.getPacketSender().setHidden(SKILLING, 682, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.WOODCUT_GUILD)) {
            p.getPacketSender().setHidden(SKILLING, 645, true);
            p.getPacketSender().setHidden(SKILLING, 683, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 645, false);
            p.getPacketSender().setHidden(SKILLING, 683, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.DRAYNOR_WOODCUTTING)) {
            p.getPacketSender().setHidden(SKILLING, 646, true);
            p.getPacketSender().setHidden(SKILLING, 684, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 646, false);
            p.getPacketSender().setHidden(SKILLING, 684, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.WOODCUT_SEERS)) {
            p.getPacketSender().setHidden(SKILLING, 647, true);
            p.getPacketSender().setHidden(SKILLING, 685, false);
        } else {
            p.getPacketSender().setHidden(SKILLING, 647, false);
            p.getPacketSender().setHidden(SKILLING, 685, true);
        }
    }

    private static void sendSprites(Player p) {
        p.getPacketSender().sendItems(SKILLING, 461, 0, new Item(3257));
        p.getPacketSender().sendItems(SKILLING, 463, 0, new Item(18207));
        p.getPacketSender().sendItems(SKILLING, 465, 0, new Item(18207));

        p.getPacketSender().sendItems(SKILLING, 468, 0, new Item(18207));
        p.getPacketSender().sendItems(SKILLING, 470, 0, new Item(18207));
        p.getPacketSender().sendItems(SKILLING, 472, 0, new Item(18207));

        p.getPacketSender().sendItems(SKILLING, 474, 0, new Item(964));
        p.getPacketSender().sendItems(SKILLING, 476, 0, new Item(18207));
        p.getPacketSender().sendItems(SKILLING, 478, 0, new Item(18207));

        p.getPacketSender().sendItems(SKILLING, 480, 0, new Item(18207));
        p.getPacketSender().sendItems(SKILLING, 482, 0, new Item(18207));
        p.getPacketSender().sendItems(SKILLING, 484, 0, new Item(1755));

        p.getPacketSender().sendItems(SKILLING, 486, 0, new Item(7409));
        p.getPacketSender().sendItems(SKILLING, 488, 0, new Item(207));
        p.getPacketSender().sendItems(SKILLING, 490, 0, new Item(207));

        p.getPacketSender().sendItems(SKILLING, 492, 0, new Item(207));
        p.getPacketSender().sendItems(SKILLING, 494, 0, new Item(207));
        p.getPacketSender().sendItems(SKILLING, 496, 0, new Item(207));


        p.getPacketSender().sendItems(SKILLING, 514, 0, new Item(207));
        p.getPacketSender().sendItems(SKILLING, 516, 0, new Item(9799));
        p.getPacketSender().sendItems(SKILLING, 518, 0, new Item(13439));

        p.getPacketSender().sendItems(SKILLING, 533, 0, new Item(309));
        p.getPacketSender().sendItems(SKILLING, 535, 0, new Item(383));
        p.getPacketSender().sendItems(SKILLING, 537, 0, new Item(303));

        p.getPacketSender().sendItems(SKILLING, 552, 0, new Item(7944));
        p.getPacketSender().sendItems(SKILLING, 554, 0, new Item(22201));
        p.getPacketSender().sendItems(SKILLING, 556, 0, new Item(15385));

        p.getPacketSender().sendItems(SKILLING, 571, 0, new Item(9976));
        p.getPacketSender().sendItems(SKILLING, 573, 0, new Item(11256));
        p.getPacketSender().sendItems(SKILLING, 575, 0, new Item(9793));

        p.getPacketSender().sendItems(SKILLING, 590, 0, new Item(444));
        p.getPacketSender().sendItems(SKILLING, 592, 0, new Item(7936));
        p.getPacketSender().sendItems(SKILLING, 594, 0, new Item(436));

        p.getPacketSender().sendItems(SKILLING, 609, 0, new Item(19240));
        p.getPacketSender().sendItems(SKILLING, 611, 0, new Item(2353));
        p.getPacketSender().sendItems(SKILLING, 613, 0, new Item(6739));

        p.getPacketSender().sendItems(SKILLING, 628, 0, new Item(1519));
        p.getPacketSender().sendItems(SKILLING, 630, 0, new Item(1517));
    }

    public static void teleport(Player p) {
        if (!teleportSelected) {
            p.sendMessage("You must select a teleport first!");
        }
        if (gnomeagility) {
            telegnome(p);
        } else if (draynorcourse) {
            teledraynor(p);
        } else if (alkharidcourse) {
            telealkharid(p);
        } else if (varrockcourse) {
            televarrock(p);
        } else if (canafiscourse) {
            telecanafis(p);
        } else if (faladorcourse) {
            telefalador(p);
        } else if (wildcourse) {
            teleagilWild(p);
        } else if (seerscourse) {
            teleseers(p);
        } else if (prifcourse) {
            teleprif(p);
        } else if (rellekkacourse) {
            telerellekka(p);
        } else if (ardougnecourse) {
            teleardougne(p);
        } else if (craftinguild) {
            telecraftingguild(p);
        } else if (farmguild) {
            telefarmguild(p);
        } else if (farmardy) {
            telefarmardy(p);
        } else if (farmcanafis) {
            telefarmcanafis(p);
        } else if (farmcath) {
            telefarmcath(p);
        } else if (farmfally) {
            telefarmfally(p);
        } else if (farmtroll) {
            telefarmtroll(p);
        } else if (farmzeah) {
            telefarmzeah(p);
        } else if (fishingguild) {
            telefishingguild(p);
        } else if (fishangler) {
            telefishingangler(p);
        } else if (fishbarb) {
            telefishingbarb(p);
        } else if (fishcath) {
            telefishcath(p);
        } else if (fishlumb) {
            telefishlumb(p);
        } else if (piscatorisfishing) {
            telefishpisc(p);
        } else if (huntbirdhouse) {
            telehuntbird(p);
        } else if (huntfeld) {
            telehuntfeld(p);
        } else if (piscatoriswoodland) {
            telehuntpisc(p);
        } else if (puropuro) {
            telepuro(p);
        } else if (miningguild) {
            teleminingguild(p);
        } else if (miningalkharid) {
            teleminingalkharid(p);
        } else if (essencemine) {
            teleessence(p);
        } else if (miningvarrock) {
            teleminingvarrock(p);
        } else if (runecraft) {
            teleabyss(p);
        } else if (smithing) {
            telesmith(p);
        } else if (woodcuttingguild) {
            telewoodcuttingguild(p);
        } else if (woodcutdraynor) {
            telewoodcutdraynor(p);
        } else if (woodcutseers) {
            telewoodcutseers(p);
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

    public static void sendSkillingTeles(Player p) {
        p.getPacketSender().sendString(SKILLING, 88, "Agility - Gnome Course");
        p.getPacketSender().sendString(SKILLING, 89, "Agility - Draynor Rooftop");
        p.getPacketSender().sendString(SKILLING, 90, "Agility - Al-Kharid Rooftop");
        p.getPacketSender().sendString(SKILLING, 170, "Agility - Varrock Rooftop");
        p.getPacketSender().sendString(SKILLING, 171, "Agility - Canafis Rooftop");
        p.getPacketSender().sendString(SKILLING, 172, "Agility - Falador Rooftop");
        p.getPacketSender().sendString(SKILLING, 177, "Agility - Wilderness Rooftop");
        p.getPacketSender().sendString(SKILLING, 178, "Agility - Seers Rooftop");
        p.getPacketSender().sendString(SKILLING, 179, "Agility - Prifddinas Rooftop");
        p.getPacketSender().sendString(SKILLING, 184, "Agility - Rellekka Rooftop");
        p.getPacketSender().sendString(SKILLING, 185, "Agility - Ardougne Rooftop");
        p.getPacketSender().sendString(SKILLING, 186, "Crafting Guild");
        p.getPacketSender().sendString(SKILLING, 191, "Farming Guild");
        p.getPacketSender().sendString(SKILLING, 192, "Farming - Ardougne Patches");
        p.getPacketSender().sendString(SKILLING, 193, "Farming - Canafis Patches");
        p.getPacketSender().sendString(SKILLING, 198, "Farming - Catherby Patches");
        p.getPacketSender().sendString(SKILLING, 199, "Farming - Falador Farm Patches");
        p.getPacketSender().sendString(SKILLING, 200, "Farming - Trollheim Herb Patch");

        p.getPacketSender().sendString(SKILLING, 504, "Farming - Zeah Patches");
        p.getPacketSender().sendString(SKILLING, 505, "Fishing Guild");
        p.getPacketSender().sendString(SKILLING, 506, "Fishing - Anglerfish");
        p.getPacketSender().sendString(SKILLING, 523, "Fishing - Barbarian Village");
        p.getPacketSender().sendString(SKILLING, 524, "Fishing - Catherby");
        p.getPacketSender().sendString(SKILLING, 525, "Fishing - Lumbridge Swamp");
        p.getPacketSender().sendString(SKILLING, 542, "Fishing - Piscatoris");
        p.getPacketSender().sendString(SKILLING, 543, "Hunter - Birdhouses");
        p.getPacketSender().sendString(SKILLING, 544, "Hunter - Feldip Hills");
        p.getPacketSender().sendString(SKILLING, 561, "Hunter - Piscatoris");
        p.getPacketSender().sendString(SKILLING, 562, "Hunter - Puro Puro");
        p.getPacketSender().sendString(SKILLING, 563, "Mining Guild");
        p.getPacketSender().sendString(SKILLING, 580, "Mining - Al-Kharid");
        p.getPacketSender().sendString(SKILLING, 581, "Mining - Essence Mine");
        p.getPacketSender().sendString(SKILLING, 582, "Mining - Varrock");
        p.getPacketSender().sendString(SKILLING, 599, "Runecrafting - The Abyss");
        p.getPacketSender().sendString(SKILLING, 600, "Smithing - Varrock West Bank");
        p.getPacketSender().sendString(SKILLING, 601, "Woodcutting Guild");
        p.getPacketSender().sendString(SKILLING, 618, "Woodcutting - Draynor Bank");
        p.getPacketSender().sendString(SKILLING, 619, "Woodcutting - Seers Bank");
        p.getPacketSender().sendString(SKILLING, 620, "");

        p.getPacketSender().sendString(SKILLING, 325, "Requires level 1 agility");
        p.getPacketSender().sendString(SKILLING, 326, "Requires level 10 agility");
        p.getPacketSender().sendString(SKILLING, 327, "Requires level 20 agility");
        p.getPacketSender().sendString(SKILLING, 328, "Requires level 30 agility");
        p.getPacketSender().sendString(SKILLING, 329, "Requires level 40 agility");
        p.getPacketSender().sendString(SKILLING, 330, "Requires level 50 agility");
        p.getPacketSender().sendString(SKILLING, 331, "Requires level 52 agility");
        p.getPacketSender().sendString(SKILLING, 332, "Requires level 60 agility");
        p.getPacketSender().sendString(SKILLING, 333, "Requires level 75 agility");
        p.getPacketSender().sendString(SKILLING, 338, "Requires level 80 agility");
        p.getPacketSender().sendString(SKILLING, 339, "Requires level 90 agility");
        p.getPacketSender().sendString(SKILLING, 340, "A resourceful area with a nearby bank");
        p.getPacketSender().sendString(SKILLING, 341, "Requires level 45 farming to enter");
        p.getPacketSender().sendString(SKILLING, 342, "Travel to the Ardougne farming patches");
        p.getPacketSender().sendString(SKILLING, 343, "Travel to the Canafis farming patches");
        p.getPacketSender().sendString(SKILLING, 344, "Travel to the Catherby farming patches");
        p.getPacketSender().sendString(SKILLING, 345, "Travel to the Falador farm patches");
        p.getPacketSender().sendString(SKILLING, 346, "Travel to the Trollheim herb patch");

        p.getPacketSender().sendString(SKILLING, 507, "Travel to the Zeah farming patches");
        p.getPacketSender().sendString(SKILLING, 508, "Requires level 68 fishing to enter");
        p.getPacketSender().sendString(SKILLING, 509, "82 fishing and higher");
        p.getPacketSender().sendString(SKILLING, 526, "15 fishing and higher");
        p.getPacketSender().sendString(SKILLING, 527, "Great for medium-high level fishing");
        p.getPacketSender().sendString(SKILLING, 528, "Level 1 fishing spots");
        p.getPacketSender().sendString(SKILLING, 545, "Medium-high level fishing spots");
        p.getPacketSender().sendString(SKILLING, 546, "Travel to the House on the Hill");
        p.getPacketSender().sendString(SKILLING, 547, "Low level hunter area");
        p.getPacketSender().sendString(SKILLING, 564, "Low-medium level hunter area");
        p.getPacketSender().sendString(SKILLING, 565, "Hunter training area for catching implings");
        p.getPacketSender().sendString(SKILLING, 566, "Requires level 60 mining to enter");
        p.getPacketSender().sendString(SKILLING, 583, "Low-medium level mining spots");
        p.getPacketSender().sendString(SKILLING, 584, "Used for mining pure essence");
        p.getPacketSender().sendString(SKILLING, 585, "Level 1-15 mining area");
        p.getPacketSender().sendString(SKILLING, 602, "Used for traveling to runecrafting altars");
        p.getPacketSender().sendString(SKILLING, 603, "One of the closest anvils to a bank");
        p.getPacketSender().sendString(SKILLING, 604, "Train woodcutting and cut redwoods");
        p.getPacketSender().sendString(SKILLING, 621, "Level 30 woodcutting area");
        p.getPacketSender().sendString(SKILLING, 622, "Level 45 woodcutting area");

        p.getPacketSender().sendString(SKILLING, 623, "");
    }

    public static void selectgnome(Player p) {
        clearSelections();
        gnomeagility = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Agility - Gnome Course");
    }
    public static void telegnome(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2471, 3437, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.GNOME_COURSE);
    }
    public static void selectdraynor(Player p) {
        clearSelections();
        draynorcourse = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Draynor Rooftop");
    }
    public static void teledraynor(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3105, 3272, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.DRAYNOR_COURSE);
    }
    public static void selectalkharid(Player p) {
        clearSelections();
        alkharidcourse = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Al-Kharid Rooftop");
    }
    public static void telealkharid(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3281, 3197, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ALKHARID_COURSE);
    }
    public static void selectvarrock(Player p) {
        clearSelections();
        varrockcourse = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Varrock Rooftop");
    }
    public static void televarrock(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3224, 3415, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.VARROCK_COURSE);
    }
    public static void selectcanafis(Player p) {
        clearSelections();
        canafiscourse = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Canafis Rooftop");
    }
    public static void telecanafis(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3505, 3486, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.CANNIFIS_COURSE);
    }
    public static void selectfalador(Player p) {
        clearSelections();
        faladorcourse = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Falador Rooftop");
    }
    public static void telefalador(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3037, 3339, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FALADOR_COURSE);
    }
    public static void selectagilWild(Player p) {
        clearSelections();
        wildcourse = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Wilderness Course");
    }
    public static void teleagilWild(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2998, 3914, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.WILD_COURSE);
    }
    public static void selectseers(Player p) {
        clearSelections();
        seerscourse = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Seers Rooftop");
    }
    public static void teleseers(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2728, 3485, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.SEERS_COURSE);
    }
    public static void selectardougne(Player p) {
        clearSelections();
        ardougnecourse = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Ardougne Rooftop");
    }
    public static void teleardougne(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2673, 3296, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ARDOUGNE_COURSE);
    }
    public static void selectprif(Player p) {
        clearSelections();
        prifcourse = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Prifddinas Rooftop");
    }
    public static void teleprif(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3251, 6109, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.PRIF_COURSE);
    }
    public static void selectwoodcutseers(Player p) {
        clearSelections();
        woodcutseers = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Woodcutting - Seers");
    }
    public static void telewoodcutseers(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2730, 3500, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.WOODCUT_SEERS);
    }
    public static void selectcraftingguild(Player p) {
        clearSelections();
        craftinguild = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Crafting Guild");
    }
    public static void telecraftingguild(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2933, 3291, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.CRAFTING_GUILD);
    }
    public static void selectessence(Player p) {
        clearSelections();
        essencemine = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Essence Mine");
    }
    public static void teleessence(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2911, 4832, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ESSENCE_MINE);
    }
    public static void selectfishingguild(Player p) {
        clearSelections();
        fishingguild = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Fishing Guild");
    }
    public static void telefishingguild(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2611, 3390, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FISHING_GUILD);
    }
    public static void selectminingguild(Player p) {
        clearSelections();
        miningguild = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Mining Guild");
    }
    public static void teleminingguild(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3022, 3337, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.MINING_GUILD);
    }
    public static void selectwoodcutdraynor(Player p) {
        clearSelections();
        woodcutdraynor = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Woodcutting - Draynor");
    }
    public static void telewoodcutdraynor(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3091, 3225, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.DRAYNOR_WOODCUTTING);
    }
    public static void selectsmith(Player p) {
        clearSelections();
        smithing = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Smithing - Varrock");
    }
    public static void telesmith(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3187, 3426, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.VARROCK_WEST);
    }
    public static void selectrellekka(Player p) {
        clearSelections();
        rellekkacourse = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Rellekka Rooftop");
    }
    public static void telerellekka(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2629, 3677, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.RELLEKKA_COURSE);
    }
    public static void selectwoodcuttinguild(Player p) {
        clearSelections();
        woodcuttingguild = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Woodcutting Guild");
    }
    public static void telewoodcuttingguild(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1661, 3505, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.WOODCUT_GUILD);
    }




    public static void selectfarmguild(Player p) {
        clearSelections();
        farmguild = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Farming Guild");
    }

    public static void telefarmguild(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        if (p.getStats().get(StatType.Farming).fixedLevel > 45) {
            teleport(p, 1248, 3725, 0);
        } else
            teleport(p, 1248, 3720, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FARMING_GUILD);
    }

    public static void selectfarmardy(Player p) {
        clearSelections();
        farmardy = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Farming - Ardougne");
    }
    public static void telefarmardy(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2673, 3375, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FARMING_ARDY);
    }
    public static void selectfarmcanafis(Player p) {
        clearSelections();
        farmcanafis = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Farming - Canafis");
    }
    public static void telefarmcanafis(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3606, 3533, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FARMING_CANAFIS);
    }
    public static void selectfarmcath(Player p) {
        clearSelections();
        farmcath = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Farming - Catherby");
    }
    public static void telefarmcath(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2816, 3464, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FARMING_CATH);
    }
    public static void selectfarmfally(Player p) {
        clearSelections();
        farmfally = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Farming - Falador");
    }
    public static void telefarmfally(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3061, 3309, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FARMING_FALLY);
    }
    public static void selectfarmtroll(Player p) {
        clearSelections();
        farmtroll = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Farming - Trollheim");
    }
    public static void telefarmtroll(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2823, 3695, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FARMING_TROLL);
    }
    public static void selectfarmzeah(Player p) {
        clearSelections();
        farmzeah = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Farming - Zeah");
    }
    public static void telefarmzeah(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1742, 3551, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FARMING_ZEAH);
    }
    public static void selectfishingangler(Player p) {
        clearSelections();
        fishangler = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Fishing - Anglerfish");
    }
    public static void telefishingangler(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1829, 3782, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.KOUREND_FISHING);
    }
    public static void selectfishingbarb(Player p) {
        clearSelections();
        fishbarb = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Fishing - Barb Village");
    }
    public static void telefishingbarb(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3105, 3432, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.BARB_VILLAGE_FISH);
    }
    public static void selectfishcath(Player p) {
        clearSelections();
        fishcath = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Fishing - Catherby");
    }
    public static void telefishcath(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2838, 3435, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.CATHERBY_FISHING);
    }
    public static void selectfishlumb(Player p) {
        clearSelections();
        fishlumb = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Fishing - Lumb Swamp");
    }
    public static void telefishlumb(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3243, 3157, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.LUMB_FISHING);
    }
    public static void selectfishpisc(Player p) {
        clearSelections();
        piscatorisfishing = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Fishing - Piscatoris");
    }
    public static void telefishpisc(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2341, 3666, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.PISCATORIS_FISHING);
    }
    public static void selecthuntbird(Player p) {
        clearSelections();
        huntbirdhouse = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Hunter - Birdhouses");
    }
    public static void telehuntbird(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3763, 3870, 1);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.HUNTER_BIRDHOUSES);
    }
    public static void selecthuntfeld(Player p) {
        clearSelections();
        huntfeld = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Hunter - Feldip Hills");
    }
    public static void telehuntfeld(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2603, 2932, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FELDIP_HILLS);
    }
    public static void selecthuntpisc(Player p) {
        clearSelections();
        huntpist = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Hunter - Piscatoris");
    }
    public static void telehuntpisc(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2332, 3608, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.PISCATORIS);
    }
    public static void selectpuro(Player p) {
        clearSelections();
        puropuro = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Hunter - Puro Puro");
    }
    public static void telepuro(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2426, 4443, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.PURO_PURO);
    }
    public static void selectminingalkharid(Player p) {
        clearSelections();
        miningalkharid = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Mining - Al-Kharid");
    }
    public static void teleminingalkharid(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3290, 3276, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.MINING_ALKHARID);
    }
    public static void selectminingvarrock(Player p) {
        clearSelections();
        miningvarrock = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Mining - Varrock");
    }
    public static void teleminingvarrock(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3285, 3372, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.MINING_VARROCK);
    }
    public static void selectabyss(Player p) {
        clearSelections();
        runecraft = true;
        teleportSelected = true;
        p.getPacketSender().sendString(SKILLING, 427, "Runecrafting - Abyss");
    }
    public static void teleabyss(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3016, 4838, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ABYSS);
    }
}