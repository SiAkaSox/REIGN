package io.ruin.model.inter.newteleports;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.handlers.TeleportInterface;
import io.ruin.model.item.Item;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.object.actions.impl.edgeville.Teleporter;

/**
 * this needs a fkin rewrite
 */
public class MonsterInter {

    public static int MONSTERS = 877;
    public static int DUNGEONS = 986;
    public static int BOSSES = 987;
    public static int MINIGAMES = 988;
    public static int SKILLING = 989;
    public static int WILDERNESS = 990;
    public static int CITIES = 991;
    public static int MISC = 992;
    public static int EVENTS = 993;

    static {
        InterfaceHandler.register(MONSTERS, h -> {
            h.actions[19] = (SlotAction) (player, slot) -> player.getTeleporter().openInterface(player);
            h.actions[36] = (SlotAction) (player, slot) -> DungeonInter.openInterface(player);
            h.actions[37] = (SlotAction) (player, slot) -> BossesInter.openInterface(player);
            h.actions[38] = (SlotAction) (player, slot) -> MinigameInter.openInterface(player);
            h.actions[39] = (SlotAction) (player, slot) -> SkillingInter.openInterface(player);
            h.actions[40] = (SlotAction) (player, slot) -> WildernessInter.openInterface(player);
            h.actions[391] = (SlotAction) (player, slot) -> CityInter.openInterface(player);
            h.actions[392] = (SlotAction) (player, slot) -> MiscInter.openInterface(player);
            h.actions[400] = (SlotAction) (player, slot) -> Favorites.openInterface(player);
            h.actions[425] = (SlotAction) (player, slot) -> Teleporter.teleportPrevious(player);

            h.actions[368] = (SlotAction) (player, slot) -> player.getTeleporter().selectAmmoniteCrabs(player);
            h.actions[369] = (SlotAction) (player, slot) -> player.getTeleporter().selectBanditCamp(player);
            h.actions[370] = (SlotAction) (player, slot) -> player.getTeleporter().selectBrineRatCavern(player);
            h.actions[371] = (SlotAction) (player, slot) -> player.getTeleporter().selectCaveHorrors(player);
            h.actions[372] = (SlotAction) (player, slot) -> player.getTeleporter().selectCows(player);
            h.actions[373] = (SlotAction) (player, slot) -> player.getTeleporter().selectCrashSite(player);
            h.actions[374] = (SlotAction) (player, slot) -> player.getTeleporter().selectDagannoths(player);
            h.actions[375] = (SlotAction) (player, slot) -> player.getTeleporter().selectElves(player);
            h.actions[376] = (SlotAction) (player, slot) -> player.getTeleporter().selectExperiments(player);
            h.actions[377] = (SlotAction) (player, slot) -> player.getTeleporter().selectJungleDemons(player);
            h.actions[378] = (SlotAction) (player, slot) -> player.getTeleporter().selectKalphites(player);
            h.actions[379] = (SlotAction) (player, slot) -> player.getTeleporter().selectMournerTunnels(player);
            h.actions[380] = (SlotAction) (player, slot) -> player.getTeleporter().selectRockCrabs(player);
            h.actions[381] = (SlotAction) (player, slot) -> player.getTeleporter().selectSandCrabs(player);
            h.actions[382] = (SlotAction) (player, slot) -> player.getTeleporter().selectSlayerTower(player);
            h.actions[383] = (SlotAction) (player, slot) -> player.getTeleporter().selectStrongholdSlayerCave(player);
            h.actions[384] = (SlotAction) (player, slot) -> player.getTeleporter().selectSuqahs(player);
            h.actions[385] = (SlotAction) (player, slot) -> player.getTeleporter().selectTrolls(player);
            h.actions[428] = (SlotAction) (player, slot) -> player.getTeleporter().teleport(player);

            h.actions[315] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.AMMONITE_CRABS, player, 315, 497);
            h.actions[316] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.BANDIT_CAMP, player, 316, 498);
            h.actions[317] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.BRINE_RAT, player, 317, 499);
            h.actions[318] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.CAVE_HORRORS, player, 318, 500);
            h.actions[319] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.COWS, player, 319, 501);
            h.actions[320] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.CRASH_SITE, player, 320, 502);
            h.actions[321] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.DAGANNOTHS, player, 321, 503);
            h.actions[322] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.ELVEN_CAMP, player, 322, 504);
            h.actions[323] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.EXPERIMENTS, player, 323, 505);
            h.actions[324] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.JUNGLE_DEMONS, player, 324, 506);
            h.actions[347] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.KALPHITES, player, 347, 507);
            h.actions[348] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.MOURENER, player, 348, 508);
            h.actions[349] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.ROCK_CRABS, player, 349, 509);
            h.actions[350] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.SAND_CRABS, player, 350, 510);
            h.actions[351] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.SLAYER_TOWER, player, 351, 511);
            h.actions[352] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.STRONGHOLD_SLAYER, player, 352, 512);
            h.actions[353] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.SUQAH, player, 353, 513);
            h.actions[354] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.TROLLPIT, player, 354, 514);

            h.actions[497] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.AMMONITE_CRABS, player, 315, 497);
            h.actions[498] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.BANDIT_CAMP, player, 316, 498);
            h.actions[499] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.BRINE_RAT, player, 317, 499);
            h.actions[500] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.CAVE_HORRORS, player, 318, 500);
            h.actions[501] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.COWS, player, 319, 501);
            h.actions[502] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.CRASH_SITE, player, 320, 502);
            h.actions[503] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.DAGANNOTHS, player, 321, 503);
            h.actions[504] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.ELVEN_CAMP, player, 322, 504);
            h.actions[505] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.EXPERIMENTS, player, 323, 505);
            h.actions[506] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.JUNGLE_DEMONS, player, 324, 506);
            h.actions[507] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.KALPHITES, player, 347, 507);
            h.actions[508] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.MOURENER, player, 348, 508);
            h.actions[509] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.ROCK_CRABS, player, 349, 509);
            h.actions[510] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.SAND_CRABS, player, 350, 510);
            h.actions[511] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.SLAYER_TOWER, player, 351, 511);
            h.actions[512] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.STRONGHOLD_SLAYER, player, 352, 512);
            h.actions[513] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.SUQAH, player, 353, 513);
            h.actions[514] = (SlotAction) (player, slot) -> player.getTeleporter().handleFavButton(TeleportInterface.Teleport.TROLLPIT, player, 354, 514);

        });
    }

    public boolean teleportSelected = false;

    //Monsters
    public boolean ammoniteCrabs, banditCamp, brineratCavern, caveHorrors, cows, crashSite, dagannoths, experiements,
            jungleDemons, kalphites, mournerTunnels, rockCrabs, sandCrabs, slayerTower,
            strongholdslayerCave, suqahs, elves, trolls, yaks = false;

    public void clearSelections() {
        experiements = false;
        elves = false;
        ammoniteCrabs = false;
        banditCamp = false;
        brineratCavern = false;
        caveHorrors = false;
        cows = false;
        crashSite = false;
        dagannoths = false;
        jungleDemons = false;
        kalphites = false;
        mournerTunnels = false;
        rockCrabs = false;
        sandCrabs = false;
        slayerTower = false;
        strongholdslayerCave = false;
        suqahs = false;
        trolls = false;
        yaks = false;
    }

    static Bounds bossBounds = new Bounds(3160, 4114, 3177, 4126, 0);

    public void openInterface(Player p) {
        if (p.wildernessLevel > 0 || p.getPosition().inBounds(bossBounds)) {
            p.sendMessage("You cannot use this while in the wilderness.");
            return;
        }
        p.openInterface(InterfaceType.MAIN, Interface.TELEPORTS);
        sendMonsterTeleports(p);
        teleportSelected = false;
        sendInfo(p);
        sendSprites(p);
        sendFavSprites(p);
    }

    private void sendSprites(Player p) {
        p.getPacketSender().sendItems(MONSTERS, 461, 0, new Item(21566));
        p.getPacketSender().sendItems(MONSTERS, 463, 0, new Item(4595));
        p.getPacketSender().sendItems(MONSTERS, 465, 0, new Item(11037));
        p.getPacketSender().sendItems(MONSTERS, 468, 0, new Item(8901));
        p.getPacketSender().sendItems(MONSTERS, 470, 0, new Item(1739));
        p.getPacketSender().sendItems(MONSTERS, 472, 0, new Item(19529));
        p.getPacketSender().sendItems(MONSTERS, 474, 0, new Item(8141));
        p.getPacketSender().sendItems(MONSTERS, 476, 0, new Item(4212));
        p.getPacketSender().sendItems(MONSTERS, 478, 0, new Item(7893));
        p.getPacketSender().sendItems(MONSTERS, 480, 0, new Item(20032));
        p.getPacketSender().sendItems(MONSTERS, 482, 0, new Item(13489));
        p.getPacketSender().sendItems(MONSTERS, 484, 0, new Item(11235));
        p.getPacketSender().sendItems(MONSTERS, 486, 0, new Item(22191));
        p.getPacketSender().sendItems(MONSTERS, 488, 0, new Item(401));
        p.getPacketSender().sendItems(MONSTERS, 490, 0, new Item(11864));
        p.getPacketSender().sendItems(MONSTERS, 492, 0, new Item(12817));
        p.getPacketSender().sendItems(MONSTERS, 494, 0, new Item(9105));
        p.getPacketSender().sendItems(MONSTERS, 496, 0, new Item(8136));
    }

    public void sendFavSprites(Player p) {
        if (p.favorites.contains(TeleportInterface.Teleport.AMMONITE_CRABS)) {
            p.getPacketSender().setHidden(MONSTERS, 315, true);
            p.getPacketSender().setHidden(MONSTERS, 497, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 315, false);
            p.getPacketSender().setHidden(MONSTERS, 497, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.BANDIT_CAMP)) {
            p.getPacketSender().setHidden(MONSTERS, 316, true);
            p.getPacketSender().setHidden(MONSTERS, 498, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 316, false);
            p.getPacketSender().setHidden(MONSTERS, 498, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.BRINE_RAT)) {
            p.getPacketSender().setHidden(MONSTERS, 317, true);
            p.getPacketSender().setHidden(MONSTERS, 499, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 317, false);
            p.getPacketSender().setHidden(MONSTERS, 499, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.CAVE_HORRORS)) {
            p.getPacketSender().setHidden(MONSTERS, 318, true);
            p.getPacketSender().setHidden(MONSTERS, 500, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 318, false);
            p.getPacketSender().setHidden(MONSTERS, 500, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.COWS)) {
            p.getPacketSender().setHidden(MONSTERS, 319, true);
            p.getPacketSender().setHidden(MONSTERS, 501, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 319, false);
            p.getPacketSender().setHidden(MONSTERS, 501, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.CRASH_SITE)) {
            p.getPacketSender().setHidden(MONSTERS, 320, true);
            p.getPacketSender().setHidden(MONSTERS, 502, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 320, false);
            p.getPacketSender().setHidden(MONSTERS, 502, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.DAGANNOTHS)) {
            p.getPacketSender().setHidden(MONSTERS, 321, true);
            p.getPacketSender().setHidden(MONSTERS, 503, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 321, false);
            p.getPacketSender().setHidden(MONSTERS, 503, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.ELVEN_CAMP)) {
            p.getPacketSender().setHidden(MONSTERS, 322, true);
            p.getPacketSender().setHidden(MONSTERS, 504, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 322, false);
            p.getPacketSender().setHidden(MONSTERS, 504, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.EXPERIMENTS)) {
            p.getPacketSender().setHidden(MONSTERS, 323, true);
            p.getPacketSender().setHidden(MONSTERS, 505, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 323, false);
            p.getPacketSender().setHidden(MONSTERS, 505, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.JUNGLE_DEMONS)) {
            p.getPacketSender().setHidden(MONSTERS, 324, true);
            p.getPacketSender().setHidden(MONSTERS, 506, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 324, false);
            p.getPacketSender().setHidden(MONSTERS, 506, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.KALPHITES)) {
            p.getPacketSender().setHidden(MONSTERS, 347, true);
            p.getPacketSender().setHidden(MONSTERS, 507, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 347, false);
            p.getPacketSender().setHidden(MONSTERS, 507, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.MOURENER)) {
            p.getPacketSender().setHidden(MONSTERS, 348, true);
            p.getPacketSender().setHidden(MONSTERS, 508, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 348, false);
            p.getPacketSender().setHidden(MONSTERS, 508, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.ROCK_CRABS)) {
            p.getPacketSender().setHidden(MONSTERS, 349, true);
            p.getPacketSender().setHidden(MONSTERS, 509, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 349, false);
            p.getPacketSender().setHidden(MONSTERS, 509, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.SAND_CRABS)) {
            p.getPacketSender().setHidden(MONSTERS, 350, true);
            p.getPacketSender().setHidden(MONSTERS, 510, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 350, false);
            p.getPacketSender().setHidden(MONSTERS, 510, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.SLAYER_TOWER)) {
            p.getPacketSender().setHidden(MONSTERS, 351, true);
            p.getPacketSender().setHidden(MONSTERS, 511, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 322, false);
            p.getPacketSender().setHidden(MONSTERS, 511, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.STRONGHOLD_SLAYER)) {
            p.getPacketSender().setHidden(MONSTERS, 352, true);
            p.getPacketSender().setHidden(MONSTERS, 512, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 352, false);
            p.getPacketSender().setHidden(MONSTERS, 512, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.SUQAH)) {
            p.getPacketSender().setHidden(MONSTERS, 353, true);
            p.getPacketSender().setHidden(MONSTERS, 513, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 353, false);
            p.getPacketSender().setHidden(MONSTERS, 513, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.TROLLPIT)) {
            p.getPacketSender().setHidden(MONSTERS, 354, true);
            p.getPacketSender().setHidden(MONSTERS, 514, false);
        } else {
            p.getPacketSender().setHidden(MONSTERS, 354, false);
            p.getPacketSender().setHidden(MONSTERS, 514, true);
        }
    }

    private void sendInfo(Player p) {
        if (p.getTeleports().getLastTeleport() == null) {
            p.getPacketSender().sendString(Interface.TELEPORTS, 423, "None!");
        } else {
            p.getPacketSender().sendString(Interface.TELEPORTS, 423, "" + p.getTeleports().getLastTeleport().getName());
        }
    }

    private TeleportFavorites teleportFavorites;

    public MonsterInter() {
        teleportFavorites = new TeleportFavorites();
    }

    private void handleFavButton(TeleportInterface.Teleport teleOption, Player p, int silverStar, int goldStar) {
        if (p.favorites.contains(teleOption)) {
            removeFavorite(teleOption, p, silverStar, goldStar);
        } else {
            addFavorite(teleOption, p, silverStar, goldStar);
        }
    }

    private void addFavorite(TeleportInterface.Teleport teleportOption, Player p, int silverStar, int goldStar) {
        MonsterInter instance = new MonsterInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.addFavorite(teleportOption, p);
        sendFavSprites(p);
    }

    private void removeFavorite(TeleportInterface.Teleport teleOption, Player p, int silverStar, int goldStar) {
        MonsterInter instance = new MonsterInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.removeFavorite(teleOption, p);
        sendFavSprites(p);
    }

    public void teleport(Player p) {
        if (!teleportSelected) {
            p.sendMessage("You must select a teleport first!");
        }
        if (ammoniteCrabs) {
            teleportAmmoniteCrabs(p);
        } else if (banditCamp) {
            teleportBanditCamp(p);
        } else if (brineratCavern) {
            teleportBrineRatCavern(p);
        } else if (caveHorrors) {
            teleportCaveHorrors(p);
        } else if (cows) {
            teleportCows(p);
        } else if (crashSite) {
            teleportCrashSite(p);
        } else if (dagannoths) {
            teleportDagannoths(p);
        } else if (experiements) {
            teleportExperiments(p);
        } else if (jungleDemons) {
            teleportJungleDemons(p);
        } else if (kalphites) {
            teleportKalphites(p);
        } else if (mournerTunnels) {
            teleportMournerTunnels(p);
        } else if (rockCrabs) {
            teleportRockCrabs(p);
        } else if (sandCrabs) {
            teleportSandCrabs(p);
        } else if (slayerTower) {
            teleportSlayerTower(p);
        } else if (strongholdslayerCave) {
            teleportStrongholdSlayerCave(p);
        } else if (suqahs) {
            teleportSuqahs(p);
        } else if (trolls) {
            teleportTrolls(p);
        } else if (elves) {
            teleportElves(p);
        }

    }

    public void teleport(Player player, int x, int y, int z) {
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

    public void sendMonsterTeleports(Player p) {
        p.getPacketSender().sendString(MONSTERS, 88, "Ammonite Crabs");
        p.getPacketSender().sendString(MONSTERS, 89, "Bandit Camp");
        p.getPacketSender().sendString(MONSTERS, 90, "Brine Rat Cavern");
        p.getPacketSender().sendString(MONSTERS, 170, "Cave Horrors");
        p.getPacketSender().sendString(MONSTERS, 171, "Cows");
        p.getPacketSender().sendString(MONSTERS, 172, "Crash Site Cavern");
        p.getPacketSender().sendString(MONSTERS, 177, "Dagannoths");
        p.getPacketSender().sendString(MONSTERS, 178, "Elven Camp");
        p.getPacketSender().sendString(MONSTERS, 179, "Experiments");
        p.getPacketSender().sendString(MONSTERS, 184, "Jungle Demons");
        p.getPacketSender().sendString(MONSTERS, 185, "Kalphites");
        p.getPacketSender().sendString(MONSTERS, 186, "Mourner Tunnels");
        p.getPacketSender().sendString(MONSTERS, 191, "Rock Crabs");
        p.getPacketSender().sendString(MONSTERS, 192, "Sand Crabs");
        p.getPacketSender().sendString(MONSTERS, 193, "Slayer Tower");
        p.getPacketSender().sendString(MONSTERS, 198, "Stronghold Slayer Cave");
        p.getPacketSender().sendString(MONSTERS, 199, "Suqahs");
        p.getPacketSender().sendString(MONSTERS, 200, "Trolls");

        p.getPacketSender().sendString(MONSTERS, 325, "Ammonite crabs on fossil island");
        p.getPacketSender().sendString(MONSTERS, 326, "Experienced/afk training location");
        p.getPacketSender().sendString(MONSTERS, 327, "Home of ulfric and some giant rats");
        p.getPacketSender().sendString(MONSTERS, 328, "Mos le harmless cave horrors");
        p.getPacketSender().sendString(MONSTERS, 329, "Beginner training location");
        p.getPacketSender().sendString(MONSTERS, 330, "Demonic gorillas can be found here");
        p.getPacketSender().sendString(MONSTERS, 331, "Mid level slayer task");
        p.getPacketSender().sendString(MONSTERS, 332, "Popular mid level slayer task area");
        p.getPacketSender().sendString(MONSTERS, 333, "Experienced training location");
        p.getPacketSender().sendString(MONSTERS, 338, "Mid level training located");
        p.getPacketSender().sendString(MONSTERS, 339, "Entrance to the kalphite lair");
        p.getPacketSender().sendString(MONSTERS, 340, "Dark beasts and runite ore are located here");
        p.getPacketSender().sendString(MONSTERS, 341, "Ideal spot for beginners combat training");
        p.getPacketSender().sendString(MONSTERS, 342, "Good location to afk combat train");
        p.getPacketSender().sendString(MONSTERS, 343, "Most common area to train slayer");
        p.getPacketSender().sendString(MONSTERS, 344, "Stronghold Slayer Cave");
        p.getPacketSender().sendString(MONSTERS, 345, "Mid level slayer task");
        p.getPacketSender().sendString(MONSTERS, 346, "Death plateau trolls, mid level slayer task");
    }

    public void selectAmmoniteCrabs(Player p) {
        clearSelections();
        ammoniteCrabs = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Ammonite Crabs");
    }

    public void teleportAmmoniteCrabs(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3722, 3840, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.AMMONITE_CRABS);

    }

    public void selectBanditCamp(Player p) {
        clearSelections();
        banditCamp = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Bandit Camp");
    }

    public void teleportBanditCamp(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3170, 2982, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.BANDIT_CAMP);
    }

    public void selectBrineRatCavern(Player p) {
        clearSelections();
        brineratCavern = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Brine Rat Cavern");
    }

    public void teleportBrineRatCavern(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2696, 10121, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.BRINE_RAT);
    }

    public void selectCaveHorrors(Player p) {
        clearSelections();
        caveHorrors = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Cave Horrors");
    }

    public void teleportCaveHorrors(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3748, 9374, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.CAVE_HORRORS);
    }

    public void selectCows(Player p) {
        clearSelections();
        cows = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Cows");
    }

    public void teleportCows(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3246, 3274, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.COWS);
    }

    public void selectCrashSite(Player p) {
        clearSelections();
        crashSite = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Crash Site Cavern");
    }

    public void teleportCrashSite(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2026, 5610, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.CRASH_SITE);
    }

    public void selectDagannoths(Player p) {
        clearSelections();
        dagannoths = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Dagannoths");
    }

    public void teleportDagannoths(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2442, 10146, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.DAGANNOTHS);
    }

    public void selectElves(Player p) {
        clearSelections();
        elves = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Elven Camp");
    }

    public void teleportElves(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2192, 3240, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ELVEN_CAMP);
    }

    public void selectExperiments(Player p) {
        clearSelections();
        experiements = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Experiments");
    }

    public void teleportExperiments(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3550, 9932, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.EXPERIMENTS);
    }

    public void selectJungleDemons(Player p) {
        clearSelections();
        jungleDemons = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Jungle Demons");
    }

    public void teleportJungleDemons(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2715, 9211, 1);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.JUNGLE_DEMONS);
    }

    public void selectKalphites(Player p) {
        clearSelections();
        kalphites = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Kalphites");
    }

    public void teleportKalphites(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3483, 9509, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.KALPHITES);
    }

    public void selectMournerTunnels(Player p) {
        clearSelections();
        mournerTunnels = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Mourner Tunnels");
    }

    public void teleportMournerTunnels(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2029, 4636, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.MOURENER);
    }

    public void selectRockCrabs(Player p) {
        clearSelections();
        rockCrabs = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Rock Crabs");
    }

    public void teleportRockCrabs(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2676, 3711, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ROCK_CRABS);
    }

    public void selectSandCrabs(Player p) {
        clearSelections();
        sandCrabs = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Sand Crabs");
    }

    public void teleportSandCrabs(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1723, 3464, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.SAND_CRABS);
    }

    public void selectSlayerTower(Player p) {
        clearSelections();
        slayerTower = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Slayer Tower");
    }

    public void teleportSlayerTower(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3428, 3534, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.SLAYER_TOWER);
    }

    public void selectStrongholdSlayerCave(Player p) {
        clearSelections();
        strongholdslayerCave = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Stronghold Slayer Cave");
    }

    public void teleportStrongholdSlayerCave(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2431, 3424, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.STRONGHOLD_SLAYER);
    }

    public void selectSuqahs(Player p) {
        clearSelections();
        suqahs = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Suqahs");
    }

    public void teleportSuqahs(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2135, 3866, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.SUQAH);
    }

    public void selectTrolls(Player p) {
        clearSelections();
        trolls = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Trolls");
    }

    public void teleportTrolls(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2884, 3588, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.TROLLPIT);
    }

    public void selectYaks(Player p) {
        clearSelections();
        yaks = true;
        teleportSelected = true;
        p.getPacketSender().sendString(Interface.TELEPORTS, 427, "Yaks");
    }

    public void teleportYaks(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2326, 3804, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ELVEN_CAMP);
    }
}