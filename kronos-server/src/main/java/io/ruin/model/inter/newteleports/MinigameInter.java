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

public class MinigameInter {

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

            InterfaceHandler.register(MINIGAMES, h -> {
                h.actions[19] = (SlotAction) (player, slot) -> player.getTeleporter().openInterface(player);
                h.actions[36] = (SlotAction) (player, slot) -> DungeonInter.openInterface(player);
                h.actions[37] = (SlotAction) (player, slot) -> BossesInter.openInterface(player);
                h.actions[38] = (SlotAction) (player, slot) -> openInterface(player);
                h.actions[39] = (SlotAction) (player, slot) -> SkillingInter.openInterface(player);
                h.actions[40] = (SlotAction) (player, slot) -> WildernessInter.openInterface(player);
                h.actions[391] = (SlotAction) (player, slot) -> CityInter.openInterface(player);
                h.actions[392] = (SlotAction) (player, slot) -> MiscInter.openInterface(player);
                h.actions[400] = (SlotAction) (player, slot) -> Favorites.openInterface(player);
                h.actions[425] = (SlotAction) (player, slot) -> Teleporter.teleportPrevious(player);

                h.actions[368] = (SlotAction) (player, slot) -> selectBarrows(player);
                h.actions[369] = (SlotAction) (player, slot) -> selectCastleWars(player);
                h.actions[370] = (SlotAction) (player, slot) -> selectDuelArena(player);
                h.actions[371] = (SlotAction) (player, slot) -> selectFightCaves(player);
                h.actions[372] = (SlotAction) (player, slot) -> selectHunnlef(player);
                h.actions[373] = (SlotAction) (player, slot) -> selectInferno(player);
                h.actions[374] = (SlotAction) (player, slot) -> selectLMS(player);
                h.actions[375] = (SlotAction) (player, slot) -> selectMotherlode(player);
                h.actions[376] = (SlotAction) (player, slot) -> selectPestControl(player);
                h.actions[377] = (SlotAction) (player, slot) -> selectCox(player);
                h.actions[378] = (SlotAction) (player, slot) -> selectTob(player);
                h.actions[379] = (SlotAction) (player, slot) -> selectSixjad(player);
                h.actions[380] = (SlotAction) (player, slot) -> selectWarriorGuild(player);
                h.actions[381] = (SlotAction) (player, slot) -> selectWintertodt(player);
                h.actions[428] = (SlotAction) (player, slot) -> teleport(player);

                h.actions[315] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.BARROWS, player, 315, 497);
                h.actions[316] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CASTLE_WARS, player, 316, 498);
                h.actions[317] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DUEL_ARENA, player, 317, 499);
                h.actions[318] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FIGHT_CAVES, player, 318, 500);
                h.actions[319] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.HUNNLEF, player, 319, 501);
                h.actions[320] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.INFERNO, player, 320, 502);
                h.actions[321] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.LMS, player, 321, 503);
                h.actions[322] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.MOTHERLODE_MINE, player, 322, 504);
                h.actions[323] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.PEST_CONTROL, player, 323, 505);
                h.actions[324] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.RAIDS1, player, 324, 506);
                h.actions[347] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.RAIDS2, player, 347, 507);
                h.actions[348] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.SIX_JAD, player, 348, 508);
                h.actions[349] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WARRIORS_GUILD, player, 349, 509);
                h.actions[350] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WINTERTODT, player, 350, 510);

                h.actions[500] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.BARROWS, player, 315, 497);
                h.actions[501] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.CASTLE_WARS, player, 316, 498);
                h.actions[502] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DUEL_ARENA, player, 317, 499);
                h.actions[503] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FIGHT_CAVES, player, 318, 500);
                h.actions[504] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.HUNNLEF, player, 319, 501);
                h.actions[505] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.INFERNO, player, 320, 502);
                h.actions[506] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.LMS, player, 321, 503);
                h.actions[507] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.MOTHERLODE_MINE, player, 322, 504);
                h.actions[508] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.PEST_CONTROL, player, 323, 505);
                h.actions[509] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.RAIDS1, player, 324, 506);
                h.actions[510] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.RAIDS2, player, 347, 507);
                h.actions[511] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.SIX_JAD, player, 348, 508);
                h.actions[512] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WARRIORS_GUILD, player, 349, 509);
                h.actions[513] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.WINTERTODT, player, 350, 510);
            });
    }

    public static boolean teleportSelected = false;

    //Monsters
    public static boolean barrows, duelarena, fightcaves, inferno, lms, motherlodemine, pestcontrol, puropuro, cox,
            tob, sixjad, warriorguild, wintertodt, castleWars, hunnlef = false;

    public static void clearSelections() {
        barrows = false; duelarena = false; fightcaves = false; inferno = false; lms = false;
        motherlodemine = false; pestcontrol = false; cox = false; warriorguild = false; wintertodt = false;
        castleWars = false; tob = false; sixjad = false; hunnlef = false;
    }

    public static void openInterface(Player p) {
        p.openInterface(InterfaceType.MAIN, MINIGAMES);
        sendMinigameTeleports(p);
        teleportSelected = false;
        sendInfo(p);
        sendSprites(p);
        sendFavSprites(p);
        p.getPacketSender().setHidden(MINIGAMES, 351, true);
        p.getPacketSender().setHidden(MINIGAMES, 352, true);
        p.getPacketSender().setHidden(MINIGAMES, 353, true);
        p.getPacketSender().setHidden(MINIGAMES, 354, true);
        p.getPacketSender().setHidden(MINIGAMES, 355, true);
        p.getPacketSender().setHidden(MINIGAMES, 356, true);
        p.getPacketSender().setHidden(MINIGAMES, 357, true);
        p.getPacketSender().setHidden(MINIGAMES, 367, true);
        p.getPacketSender().setHidden(MINIGAMES, 447, true);
        p.getPacketSender().setHidden(MINIGAMES, 448, true);
    }

    private static void sendInfo(Player p) {
        if (p.getTeleports().getLastTeleport() == null) {
            p.getPacketSender().sendString(MINIGAMES, 423, "None!");
        } else {
            p.getPacketSender().sendString(MINIGAMES, 423, "" + p.getTeleports().getLastTeleport().getName());
        }
    }

    private TeleportFavorites teleportFavorites;

    public MinigameInter() {
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
        MinigameInter instance = new MinigameInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.addFavorite(teleportOption, p);
        sendFavSprites(p);
    }

    private static void removeFavorite(TeleportInterface.Teleport teleOption, Player p, int silverStar, int goldStar) {
        MinigameInter instance = new MinigameInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.removeFavorite(teleOption, p);
        sendFavSprites(p);
    }

    public static void sendFavSprites(Player p) {
        if (p.favorites.contains(TeleportInterface.Teleport.BARROWS)) {
            p.getPacketSender().setHidden(MINIGAMES, 315, true);
            p.getPacketSender().setHidden(MINIGAMES, 500, false);
        } else {
            p.getPacketSender().setHidden(MINIGAMES, 315, false);
            p.getPacketSender().setHidden(MINIGAMES, 500, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.CASTLE_WARS)) {
            p.getPacketSender().setHidden(MINIGAMES, 316, true);
            p.getPacketSender().setHidden(MINIGAMES, 501, false);
        } else {
            p.getPacketSender().setHidden(MINIGAMES, 316, false);
            p.getPacketSender().setHidden(MINIGAMES, 501, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.DUEL_ARENA)) {
            p.getPacketSender().setHidden(MINIGAMES, 317, true);
            p.getPacketSender().setHidden(MINIGAMES, 502, false);
        } else {
            p.getPacketSender().setHidden(MINIGAMES, 317, false);
            p.getPacketSender().setHidden(MINIGAMES, 502, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FIGHT_CAVES)) {
            p.getPacketSender().setHidden(MINIGAMES, 318, true);
            p.getPacketSender().setHidden(MINIGAMES, 503, false);
        } else {
            p.getPacketSender().setHidden(MINIGAMES, 318, false);
            p.getPacketSender().setHidden(MINIGAMES, 503, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.HUNNLEF)) {
            p.getPacketSender().setHidden(MINIGAMES, 319, true);
            p.getPacketSender().setHidden(MINIGAMES, 504, false);
        } else {
            p.getPacketSender().setHidden(MINIGAMES, 319, false);
            p.getPacketSender().setHidden(MINIGAMES, 504, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.INFERNO)) {
            p.getPacketSender().setHidden(MINIGAMES, 320, true);
            p.getPacketSender().setHidden(MINIGAMES, 505, false);
        } else {
            p.getPacketSender().setHidden(MINIGAMES, 320, false);
            p.getPacketSender().setHidden(MINIGAMES, 505, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.LMS)) {
            p.getPacketSender().setHidden(MINIGAMES, 321, true);
            p.getPacketSender().setHidden(MINIGAMES, 506, false);
        } else {
            p.getPacketSender().setHidden(MINIGAMES, 321, false);
            p.getPacketSender().setHidden(MINIGAMES, 506, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.MOTHERLODE_MINE)) {
            p.getPacketSender().setHidden(MINIGAMES, 322, true);
            p.getPacketSender().setHidden(MINIGAMES, 507, false);
        } else {
            p.getPacketSender().setHidden(MINIGAMES, 322, false);
            p.getPacketSender().setHidden(MINIGAMES, 507, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.PEST_CONTROL)) {
            p.getPacketSender().setHidden(MINIGAMES, 323, true);
            p.getPacketSender().setHidden(MINIGAMES, 508, false);
        } else {
            p.getPacketSender().setHidden(MINIGAMES, 323, false);
            p.getPacketSender().setHidden(MINIGAMES, 508, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.RAIDS1)) {
            p.getPacketSender().setHidden(MINIGAMES, 324, true);
            p.getPacketSender().setHidden(MINIGAMES, 509, false);
        } else {
            p.getPacketSender().setHidden(MINIGAMES, 324, false);
            p.getPacketSender().setHidden(MINIGAMES, 509, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.RAIDS2)) {
            p.getPacketSender().setHidden(MINIGAMES, 347, true);
            p.getPacketSender().setHidden(MINIGAMES, 510, false);
        } else {
            p.getPacketSender().setHidden(MINIGAMES, 347, false);
            p.getPacketSender().setHidden(MINIGAMES, 510, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.SIX_JAD)) {
            p.getPacketSender().setHidden(MINIGAMES, 348, true);
            p.getPacketSender().setHidden(MINIGAMES, 511, false);
        } else {
            p.getPacketSender().setHidden(MINIGAMES, 348, false);
            p.getPacketSender().setHidden(MINIGAMES, 511, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.WARRIORS_GUILD)) {
            p.getPacketSender().setHidden(MINIGAMES, 349, true);
            p.getPacketSender().setHidden(MINIGAMES, 512, false);
        } else {
            p.getPacketSender().setHidden(MINIGAMES, 349, false);
            p.getPacketSender().setHidden(MINIGAMES, 512, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.WINTERTODT)) {
            p.getPacketSender().setHidden(MINIGAMES, 350, true);
            p.getPacketSender().setHidden(MINIGAMES, 513, false);
        } else {
            p.getPacketSender().setHidden(MINIGAMES, 350, false);
            p.getPacketSender().setHidden(MINIGAMES, 513, true);
        }
    }

    private static void sendSprites(Player p) {
        p.getPacketSender().sendItems(MINIGAMES, 463, 0, new Item(15627));
        p.getPacketSender().sendItems(MINIGAMES, 465, 0, new Item(1215));
        p.getPacketSender().sendItems(MINIGAMES, 468, 0, new Item(6570));

        p.getPacketSender().sendItems(MINIGAMES, 472, 0, new Item(21295));
        p.getPacketSender().sendItems(MINIGAMES, 474, 0, new Item(16165));
        p.getPacketSender().sendItems(MINIGAMES, 476, 0, new Item(12013));

        p.getPacketSender().sendItems(MINIGAMES, 478, 0, new Item(11665));
        p.getPacketSender().sendItems(MINIGAMES, 484, 0, new Item(25519));
        p.getPacketSender().sendItems(MINIGAMES, 486, 0, new Item(12954));
    }

    public static void teleport(Player p) {
        if (!teleportSelected) {
            p.sendMessage("You must select a teleport first!");
        }
        if (barrows) {
            teleportBarrows(p);
        } else if (duelarena) {
            teleportDuelArena(p);
        } else if (fightcaves) {
            teleFightCaves(p);
        } else if (inferno) {
            teleInferno(p);
        } else if (lms) {
            teleLMS(p);
        } else if (motherlodemine) {
            teleportMotherlode(p);
        } else if (pestcontrol) {
            telePestControl(p);
        } else if (cox) {
            teleCox(p);
        } else if (tob) {
            teleTob(p);
        } else if (sixjad) {
            teleSixjad(p);
        } else if (warriorguild) {
            teleWarriorGuild(p);
        } else if (wintertodt) {
            teleWintertodt(p);
        } else if (castleWars) {
            teleCastleWars(p);
        } else if (hunnlef) {
            teleHunnlef(p);
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

    public static void sendMinigameTeleports(Player p) {
        p.getPacketSender().sendString(MINIGAMES, 88, "Barrows");
        p.getPacketSender().sendString(MINIGAMES, 89, "Castle Wars");
        p.getPacketSender().sendString(MINIGAMES, 90, "Duel Arena");
        p.getPacketSender().sendString(MINIGAMES, 170, "Fight Caves");
        p.getPacketSender().sendString(MINIGAMES, 171, "The Gauntlet (Hunllef Only)");
        p.getPacketSender().sendString(MINIGAMES, 172, "The Inferno");
        p.getPacketSender().sendString(MINIGAMES, 177, "Last Man Standing");
        p.getPacketSender().sendString(MINIGAMES, 178, "Motherlode Mine");
        p.getPacketSender().sendString(MINIGAMES, 179, "Pest Control");
        p.getPacketSender().sendString(MINIGAMES, 184, "Raids I: Chambers of Xeric");
        p.getPacketSender().sendString(MINIGAMES, 185, "Raids II: Theatre of Blood");
        p.getPacketSender().sendString(MINIGAMES, 186, "Six Jad Challenge");
        p.getPacketSender().sendString(MINIGAMES, 191, "Warriors Guild");
        p.getPacketSender().sendString(MINIGAMES, 192, "Wintertodt");
        p.getPacketSender().sendString(MINIGAMES, 193, "");
        p.getPacketSender().sendString(MINIGAMES, 198, "");
        p.getPacketSender().sendString(MINIGAMES, 199, "");
        p.getPacketSender().sendString(MINIGAMES, 200, "");

        p.getPacketSender().sendString(MINIGAMES, 325, "The barrows minigame located in morytania");
        p.getPacketSender().sendString(MINIGAMES, 326, "The Castle Wars minigame bank lobby");
        p.getPacketSender().sendString(MINIGAMES, 327, "World famous degenerate sand casino");
        p.getPacketSender().sendString(MINIGAMES, 328, "Home of the Fire Cape");
        p.getPacketSender().sendString(MINIGAMES, 329, "Take on the almighty Hunllef boss");
        p.getPacketSender().sendString(MINIGAMES, 330, "Defeat the last 3 waves to achieve an Infernal Cape");
        p.getPacketSender().sendString(MINIGAMES, 331, "The Last Man Standing pvp minigame");
        p.getPacketSender().sendString(MINIGAMES, 332, "Useful for training the mining skill");
        p.getPacketSender().sendString(MINIGAMES, 333, "Will you endure the grind for elite void?");
        p.getPacketSender().sendString(MINIGAMES, 338, "The full chambers of xeric raid");
        p.getPacketSender().sendString(MINIGAMES, 339, "The full theatre of blood raid");
        p.getPacketSender().sendString(MINIGAMES, 340, "Take on 6 jads at once");
        p.getPacketSender().sendString(MINIGAMES, 341, "Kill cyclops to achieve a dragon defender");
        p.getPacketSender().sendString(MINIGAMES, 342, "Ironmens favorite minigame");
        p.getPacketSender().sendString(MINIGAMES, 343, "");
        p.getPacketSender().sendString(MINIGAMES, 344, "");
        p.getPacketSender().sendString(MINIGAMES, 345, "");
        p.getPacketSender().sendString(MINIGAMES, 346, "");
    }

    public static void selectBarrows(Player p) {
        clearSelections();
        barrows = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MINIGAMES, 427, "Barrows");
    }
    public static void teleportBarrows(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3565, 3313, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.BARROWS);
    }
    public static void selectDuelArena(Player p) {
        clearSelections();
        duelarena = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MINIGAMES, 427, "Duel Arena");
    }
    public static void teleportDuelArena(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3367, 3265, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.DUEL_ARENA);
    }
    public static void selectFightCaves(Player p) {
        clearSelections();
        fightcaves = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MINIGAMES, 427, "Fight Caves");
    }
    public static void teleFightCaves(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2443, 5171, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FIGHT_CAVES);
    }
    public static void selectInferno(Player p) {
        clearSelections();
        inferno = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MINIGAMES, 427, "Inferno");
    }
    public static void teleInferno(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2496, 5116, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.INFERNO);
    }
    public static void selectLMS(Player p) {
        clearSelections();
        lms = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MINIGAMES, 427, "LMS");
    }
    public static void teleLMS(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3400, 3180, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.LMS);
    }
    public static void selectMotherlode(Player p) {
        clearSelections();
        motherlodemine = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MINIGAMES, 427, "Motherlode Mine");
    }
    public static void teleportMotherlode(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3751, 5669, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.MOTHERLODE_MINE);
    }
    public static void selectPestControl(Player p) {
        clearSelections();
        pestcontrol = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MINIGAMES, 427, "Pest Control");
    }
    public static void telePestControl(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2659, 2676, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.PEST_CONTROL);
    }
    public static void selectCox(Player p) {
        clearSelections();
        cox = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MINIGAMES, 427, "Chambers of Xeric");
    }
    public static void teleCox(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1254, 3558, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.RAIDS1);
    }
    public static void selectTob(Player p) {
        clearSelections();
        tob = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MINIGAMES, 427, "Theatre of Blood");
    }
    public static void teleTob(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3668, 3219, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.RAIDS2);
    }
    public static void selectSixjad(Player p) {
        clearSelections();
        sixjad = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MINIGAMES, 427, "Six Jad Challenge");
    }
    public static void teleSixjad(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2542, 5111, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.SIX_JAD);
    }
    public static void selectWarriorGuild(Player p) {
        clearSelections();
        warriorguild = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MINIGAMES, 427, "Warrior Guild");
    }
    public static void teleWarriorGuild(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2880, 3546, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.WARRIORS_GUILD);
    }
    public static void selectWintertodt(Player p) {
        clearSelections();
        wintertodt = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MINIGAMES, 427, "Wintertodt");
    }
    public static void teleWintertodt(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1630, 3957, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.WINTERTODT);
    }
    public static void selectCastleWars(Player p) {
        clearSelections();
        castleWars = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MINIGAMES, 427, "Castle Wars");
    }
    public static void teleCastleWars(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2439, 3091, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.CASTLE_WARS);
    }
    public static void selectHunnlef(Player p) {
        clearSelections();
        hunnlef = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MINIGAMES, 427, "The Gauntlet");
    }
    public static void teleHunnlef(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3030, 6126, 1);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.HUNNLEF);
    }
}