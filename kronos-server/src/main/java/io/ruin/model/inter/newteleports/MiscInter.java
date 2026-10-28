package io.ruin.model.inter.newteleports;

import io.ruin.cache.Color;
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

public class MiscInter {

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
        
            InterfaceHandler.register(MISC, h -> {
                h.actions[19] = (SlotAction) (player, slot) -> player.getTeleporter().openInterface(player);
                h.actions[36] = (SlotAction) (player, slot) -> DungeonInter.openInterface(player);
                h.actions[37] = (SlotAction) (player, slot) -> BossesInter.openInterface(player);
                h.actions[38] = (SlotAction) (player, slot) -> MinigameInter.openInterface(player);
                h.actions[39] = (SlotAction) (player, slot) -> SkillingInter.openInterface(player);
                h.actions[40] = (SlotAction) (player, slot) -> WildernessInter.openInterface(player);
                h.actions[391] = (SlotAction) (player, slot) -> CityInter.openInterface(player);
                h.actions[392] = (SlotAction) (player, slot) -> openInterface(player);
                h.actions[400] = (SlotAction) (player, slot) -> Favorites.openInterface(player);
                h.actions[425] = (SlotAction) (player, slot) -> Teleporter.teleportPrevious(player);

                h.actions[368] = (SlotAction) (player, slot) -> selectBarb(player);
                h.actions[369] = (SlotAction) (player, slot) -> selectdz(player);
                h.actions[370] = (SlotAction) (player, slot) -> selectdztwo(player);
                h.actions[371] = (SlotAction) (player, slot) -> selectfossil(player);
                h.actions[372] = (SlotAction) (player, slot) -> selectgambling(player);
                h.actions[373] = (SlotAction) (player, slot) -> selectice(player);
                h.actions[374] = (SlotAction) (player, slot) -> selectmyths(player);
                h.actions[375] = (SlotAction) (player, slot) -> selectree(player);
                h.actions[428] = (SlotAction) (player, slot) -> teleport(player);

                h.actions[315] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.BARBARIAN_OUTPOST, player, 315, 497);
                h.actions[316] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DONATOR_ZONE, player, 316, 498);
                h.actions[317] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DONATOR_ZONE_DUNGEON, player, 317, 499);
                h.actions[318] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FOSSIL_ISLAND_BANK, player, 318, 500);
                //h.actions[319] = (SlotAction) (player, slot)-> handleFavButton(TeleportInterface.Teleport.GAMBLING, player, 319, 501);
                h.actions[320] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ICE_MOUNTAIN, player, 320, 502);
                h.actions[321] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.MYTHS_GUILD, player, 321, 503);
                h.actions[322] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.TREE_GNOME_VILLAGE, player, 322, 504);

                h.actions[500] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.BARBARIAN_OUTPOST, player, 315, 497);
                h.actions[501] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DONATOR_ZONE, player, 316, 498);
                h.actions[502] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.DONATOR_ZONE_DUNGEON, player, 317, 499);
                h.actions[503] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.FOSSIL_ISLAND_BANK, player, 318, 500);
                //h.actions[504] = (SlotAction) (player, slot)-> handleFavButton(TeleportInterface.Teleport.GAMBLING, player, 319, 501);
                h.actions[505] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.ICE_MOUNTAIN, player, 320, 502);
                h.actions[506] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.MYTHS_GUILD, player, 321, 503);
                h.actions[507] = (SlotAction) (player, slot) -> handleFavButton(TeleportInterface.Teleport.TREE_GNOME_VILLAGE, player, 322, 504);
            });
    }

    public static boolean teleportSelected = false;

    //Monsters
    public static boolean dz, dz2, barbarian, gambling, fossil, icemtn, myths, treegnome = false;

    public static void clearSelections() {
        dz = false; dz2 = false; barbarian = false; gambling = false; fossil = false; icemtn = false; myths = false;
        treegnome = false;
    }

    public static void openInterface(Player p) {
        p.openInterface(InterfaceType.MAIN, MISC);
        sendDonatorTeles(p);
        teleportSelected = false;
        sendInfo(p);
        sendSprites(p);
        sendFavSprites(p);
        p.getPacketSender().setHidden(MISC, 323, true);
        p.getPacketSender().setHidden(MISC, 324, true);
        p.getPacketSender().setHidden(MISC, 347, true);
        p.getPacketSender().setHidden(MISC, 348, true);
        p.getPacketSender().setHidden(MISC, 349, true);
        p.getPacketSender().setHidden(MISC, 350, true);
        p.getPacketSender().setHidden(MISC, 351, true);
        p.getPacketSender().setHidden(MISC, 352, true);
        p.getPacketSender().setHidden(MISC, 353, true);
        p.getPacketSender().setHidden(MISC, 354, true);
        p.getPacketSender().setHidden(MISC, 355, true);
        p.getPacketSender().setHidden(MISC, 356, true);
        p.getPacketSender().setHidden(MISC, 357, true);
        p.getPacketSender().setHidden(MISC, 367, true);
        p.getPacketSender().setHidden(MISC, 447, true);
        p.getPacketSender().setHidden(MISC, 448, true);
    }

    private static void sendInfo(Player p) {
        if (p.getTeleports().getLastTeleport() == null) {
            p.getPacketSender().sendString(MISC, 423, "None!");
        } else {
            p.getPacketSender().sendString(MISC, 423, "" +p.getTeleports().getLastTeleport().getName());
        }
    }

    private TeleportFavorites teleportFavorites;

    public MiscInter() {
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
        MiscInter instance = new MiscInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.addFavorite(teleportOption, p);
        sendFavSprites(p);
    }

    private static void removeFavorite(TeleportInterface.Teleport teleOption, Player p, int silverStar, int goldStar) {
        MiscInter instance = new MiscInter();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.removeFavorite(teleOption, p);
        sendFavSprites(p);
    }

    public static void sendFavSprites(Player p) {
        if (p.favorites.contains(TeleportInterface.Teleport.BARBARIAN_OUTPOST)) {
            p.getPacketSender().setHidden(MISC, 315, true);
            p.getPacketSender().setHidden(MISC, 500, false);
        } else {
            p.getPacketSender().setHidden(MISC, 315, false);
            p.getPacketSender().setHidden(MISC, 500, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.DONATOR_ZONE)) {
            p.getPacketSender().setHidden(MISC, 316, true);
            p.getPacketSender().setHidden(MISC, 501, false);
        } else {
            p.getPacketSender().setHidden(MISC, 316, false);
            p.getPacketSender().setHidden(MISC, 501, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.DONATOR_ZONE_DUNGEON)) {
            p.getPacketSender().setHidden(MISC, 317, true);
            p.getPacketSender().setHidden(MISC, 502, false);
        } else {
            p.getPacketSender().setHidden(MISC, 317, false);
            p.getPacketSender().setHidden(MISC, 502, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.FOSSIL_ISLAND_BANK)) {
            p.getPacketSender().setHidden(MISC, 318, true);
            p.getPacketSender().setHidden(MISC, 503, false);
        } else {
            p.getPacketSender().setHidden(MISC, 318, false);
            p.getPacketSender().setHidden(MISC, 503, true);
        }
//        if (p.favorites.contains(TeleportInterface.Teleport.GAMBLING)) {
//            p.getPacketSender().setHidden(MISC, 319, true);
//            p.getPacketSender().setHidden(MISC, 504, false);
//        } else {
//            p.getPacketSender().setHidden(MISC, 319, false);
//            p.getPacketSender().setHidden(MISC, 504, true);
//        }
        p.getPacketSender().setHidden(MISC, 319, false);
        p.getPacketSender().setHidden(MISC, 504, true);
        if (p.favorites.contains(TeleportInterface.Teleport.ICE_MOUNTAIN)) {
            p.getPacketSender().setHidden(MISC, 320, true);
            p.getPacketSender().setHidden(MISC, 505, false);
        } else {
            p.getPacketSender().setHidden(MISC, 320, false);
            p.getPacketSender().setHidden(MISC, 505, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.MYTHS_GUILD)) {
            p.getPacketSender().setHidden(MISC, 321, true);
            p.getPacketSender().setHidden(MISC, 506, false);
        } else {
            p.getPacketSender().setHidden(MISC, 321, false);
            p.getPacketSender().setHidden(MISC, 506, true);
        }
        if (p.favorites.contains(TeleportInterface.Teleport.TREE_GNOME_VILLAGE)) {
            p.getPacketSender().setHidden(MISC, 322, true);
            p.getPacketSender().setHidden(MISC, 507, false);
        } else {
            p.getPacketSender().setHidden(MISC, 322, false);
            p.getPacketSender().setHidden(MISC, 507, true);
        }
    }

    private static void sendSprites(Player p) {
        p.getPacketSender().sendItems(MISC, 461, 0, new Item(10551));
        p.getPacketSender().sendItems(MISC, 463, 0, new Item(30288));
        p.getPacketSender().sendItems(MISC, 465, 0, new Item(30282));

        p.getPacketSender().sendItems(MISC, 468, 0, new Item(21608));
        p.getPacketSender().sendItems(MISC, 470, 0, new Item(16979));
        p.getPacketSender().sendItems(MISC, 472, 0, new Item(10));

        p.getPacketSender().sendItems(MISC, 474, 0, new Item(22114));
        p.getPacketSender().sendItems(MISC, 476, 0, new Item(17647));
        p.getPacketSender().sendItems(MISC, 478, 0, new Item(-1));

        p.getPacketSender().sendItems(MISC, 480, 0, new Item(-1));
        p.getPacketSender().sendItems(MISC, 482, 0, new Item(-1));
        p.getPacketSender().sendItems(MISC, 484, 0, new Item(-1));

        p.getPacketSender().sendItems(MISC, 486, 0, new Item(-1));
        p.getPacketSender().sendItems(MISC, 488, 0, new Item(-1));
        p.getPacketSender().sendItems(MISC, 490, 0, new Item(-1));

        p.getPacketSender().sendItems(MISC, 492, 0, new Item(-1));
        p.getPacketSender().sendItems(MISC, 494, 0, new Item(-1));
        p.getPacketSender().sendItems(MISC, 496, 0, new Item(-1));
    }

    public static void teleport(Player p) {
        if (!teleportSelected) {
            p.sendMessage("You must select a teleport first!");
        }
        if (dz) {
            teledz(p);
        } else if (dz2) {
            teledztwo(p);
        } else if (fossil) {
            telefossil(p);
        } else if (barbarian) {
            telebarb(p);
        } else if (gambling) {
            telegambling(p);
        } else if (icemtn) {
            teleice(p);
        } else if (myths) {
            telemyths(p);
        } else if (treegnome) {
            teletree(p);
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

    public static void sendDonatorTeles(Player p) {
        p.getPacketSender().sendString(MISC, 88, "Barbarian Outpost");
        p.getPacketSender().sendString(MISC, 89, "Donator Zone");
        p.getPacketSender().sendString(MISC, 90, "Donator Zone: Dungeon");
        p.getPacketSender().sendString(MISC, 170, "Fossil Island Bank");
        p.getPacketSender().sendString(MISC, 171, "Gambling");
        p.getPacketSender().sendString(MISC, 172, "Ice Mountain");
        p.getPacketSender().sendString(MISC, 177, "Myths Guild");
        p.getPacketSender().sendString(MISC, 178, "Tree Gnome Village");
        p.getPacketSender().sendString(MISC, 179, "");
        p.getPacketSender().sendString(MISC, 184, "");
        p.getPacketSender().sendString(MISC, 185, "");
        p.getPacketSender().sendString(MISC, 186, "");
        p.getPacketSender().sendString(MISC, 191, "");
        p.getPacketSender().sendString(MISC, 192, "");
        p.getPacketSender().sendString(MISC, 193, "");
        p.getPacketSender().sendString(MISC, 198, "");
        p.getPacketSender().sendString(MISC, 199, "");
        p.getPacketSender().sendString(MISC, 200, "");

        p.getPacketSender().sendString(MISC, 325, "The Barbarian Outpost area");
        p.getPacketSender().sendString(MISC, 326, "Teleports you to the Donator Zone");
        p.getPacketSender().sendString(MISC, 327, "Teleports you to the Donator Zone Dungeon");
        p.getPacketSender().sendString(MISC, 328, "Quick access to the fossil island bank");
        p.getPacketSender().sendString(MISC, 329, "Gambling addiction? Call 888-236-4848");
        p.getPacketSender().sendString(MISC, 330, "Because who actually wants to walk to here");
        p.getPacketSender().sendString(MISC, 331, "Teleports you inside of the Myths Guild");
        p.getPacketSender().sendString(MISC, 332, "Quick access to a fruit tree patch");
        p.getPacketSender().sendString(MISC, 333, "");
        p.getPacketSender().sendString(MISC, 338, "");
        p.getPacketSender().sendString(MISC, 339, "");
        p.getPacketSender().sendString(MISC, 340, "");
        p.getPacketSender().sendString(MISC, 341, "");
        p.getPacketSender().sendString(MISC, 342, "");
        p.getPacketSender().sendString(MISC, 343, "");
        p.getPacketSender().sendString(MISC, 344, "");
        p.getPacketSender().sendString(MISC, 345, "");
        p.getPacketSender().sendString(MISC, 346, "");
    }

    public static void selectBarb(Player p) {
        clearSelections();
        barbarian = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MISC, 427, "Barbarian Outpost");
    }
    public static void telebarb(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2520,3570, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.BARBARIAN_OUTPOST);
    }
    public static void selectdz(Player p) {
        clearSelections();
        dz = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MISC, 427, "Donator Zone");
    }
    public static void teledz(Player p) {
        if (p.storeAmountSpent < 15) {
            p.sendMessage(Color.RED.wrap("You must have a donator rank to use this teleport!"));
            return;
        }
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2078,4264, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.DONATOR_ZONE);
    }
    public static void selectdztwo(Player p) {
        clearSelections();
        dz2 = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MISC, 427, "Donator Zone: Dungeon");
    }
    public static void teledztwo(Player p) {
        if (p.storeAmountSpent < 100) {
            p.sendMessage(Color.RED.wrap("You must have a Ruby donator rank to use this teleport!"));
            return;
        }
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 1250, 4301, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.DONATOR_ZONE_DUNGEON);
    }
    public static void selectfossil(Player p) {
        clearSelections();
        fossil = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MISC, 427, "Fossil Island Bank");
    }
    public static void telefossil(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3737, 3804, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FOSSIL_ISLAND_BANK);
    }
    public static void selectgambling(Player p) {
        clearSelections();
        gambling = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MISC, 427, "Gambling");
    }
    public static void telegambling(Player p) {
        p.sendMessage("Work in progress");
//        p.closeInterface(InterfaceType.MAIN);
//        teleport(p, 3737, 3804, 0);
//        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.FOSSIL_ISLAND_BANK);
    }
    public static void selectice(Player p) {
        clearSelections();
        icemtn = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MISC, 427, "Ice Mountain");
    }
    public static void teleice(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 3025, 3473, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.ICE_MOUNTAIN);
    }
    public static void selectmyths(Player p) {
        clearSelections();
        myths = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MISC, 427, "Myths Guild");
    }
    public static void telemyths(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2457, 2851, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.MYTHS_GUILD);
    }
    public static void selectree(Player p) {
        clearSelections();
        treegnome = true;
        teleportSelected = true;
        p.getPacketSender().sendString(MISC, 427, "Tree Gnome Village");
    }
    public static void teletree(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, 2501, 3191, 0);
        p.getTeleports().setLastTeleport(TeleportInterface.Teleport.TREE_GNOME_VILLAGE);
    }
}