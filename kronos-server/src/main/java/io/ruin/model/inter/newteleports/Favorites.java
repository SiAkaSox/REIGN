package io.ruin.model.inter.newteleports;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.handlers.TeleportInterface;
import io.ruin.model.map.object.actions.impl.edgeville.Teleporter;

public class Favorites {

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

        InterfaceHandler.register(EVENTS, h-> {
            h.actions[19] = (SlotAction) (player, slot) -> player.getTeleporter().openInterface(player);
            h.actions[36] = (SlotAction) (player, slot) -> DungeonInter.openInterface(player);
            h.actions[37] = (SlotAction) (player, slot) -> BossesInter.openInterface(player);
            h.actions[38] = (SlotAction) (player, slot) -> MinigameInter.openInterface(player);
            h.actions[39] = (SlotAction) (player, slot) -> SkillingInter.openInterface(player);
            h.actions[40] = (SlotAction) (player, slot) -> WildernessInter.openInterface(player);
            h.actions[391] = (SlotAction) (player, slot) -> CityInter.openInterface(player);
            h.actions[392] = (SlotAction) (player, slot) -> MiscInter.openInterface(player);
            h.actions[400] = (SlotAction) (player, slot) -> openInterface(player);
            h.actions[425] = (SlotAction) (player, slot) -> Teleporter.teleportPrevious(player);

            h.actions[368] = (SlotAction) (player, slot) -> selectFavOne(player);
            h.actions[369] = (SlotAction) (player, slot) -> selectFavTwo(player);
            h.actions[370] = (SlotAction) (player, slot) -> selectFavThree(player);
            h.actions[371] = (SlotAction) (player, slot) -> selectFavFour(player);
            h.actions[372] = (SlotAction) (player, slot) -> selectFavFive(player);
            h.actions[373] = (SlotAction) (player, slot) -> selectFavSix(player);
            h.actions[374] = (SlotAction) (player, slot) -> selectFavSeven(player);
            h.actions[375] = (SlotAction) (player, slot) -> selectFavEight(player);
            h.actions[376] = (SlotAction) (player, slot) -> selectFavNine(player);
            h.actions[377] = (SlotAction) (player, slot) -> selectFavTen(player);
            h.actions[428] = (SlotAction) (player, slot) -> teleport(player);

            h.actions[461] = (SlotAction) (player, slot) -> handleFavButton(player.favorites.get(0), player, 315, 497);
            h.actions[463] = (SlotAction) (player, slot) -> handleFavButton(player.favorites.get(1), player, 316, 498);
            h.actions[465] = (SlotAction) (player, slot) -> handleFavButton(player.favorites.get(2), player, 317, 499);
            h.actions[468] = (SlotAction) (player, slot) -> handleFavButton(player.favorites.get(3), player, 318, 500);
            h.actions[470] = (SlotAction) (player, slot) -> handleFavButton(player.favorites.get(4), player, 319, 501);
            h.actions[472] = (SlotAction) (player, slot) -> handleFavButton(player.favorites.get(5), player, 320, 502);
            h.actions[474] = (SlotAction) (player, slot) -> handleFavButton(player.favorites.get(6), player, 321, 503);
            h.actions[476] = (SlotAction) (player, slot) -> handleFavButton(player.favorites.get(7), player, 322, 504);
            h.actions[478] = (SlotAction) (player, slot) -> handleFavButton(player.favorites.get(8), player, 323, 505);
            h.actions[480] = (SlotAction) (player, slot) -> handleFavButton(player.favorites.get(9), player, 324, 506);
        });
    }

    public static boolean teleportSelected = false;

    //Monsters
    public static boolean favOne, faveTwo, favThree, favFour, favFive, favSix, favSeven, favEight, favNine, favTen = false;

    public static void clearSelections() {
        favOne = false; faveTwo = false; favThree = false; favFour = false; favFive = false; favSix = false; favSeven = false;
        favEight = false; favNine = false; favTen = false;
    }

    private TeleportFavorites teleportFavorites;

    public Favorites() {
        teleportFavorites = new TeleportFavorites();
    }

    public static void openInterface(Player p) {
        p.openInterface(InterfaceType.MAIN, EVENTS);
        sendFavoritesList(p);
        teleportSelected = false;
        sendInfo(p);
    }

    private static void sendInfo(Player p) {
        if (p.getTeleports().getLastTeleport() == null) {
            p.getPacketSender().sendString(EVENTS, 423, "None!");
        } else {
            p.getPacketSender().sendString(EVENTS, 423, "" +p.getTeleports().getLastTeleport().getName());
        }
    }

    private static void handleFavButton(TeleportInterface.Teleport teleOption, Player p, int silverStar, int goldStar) {
        removeFavorite(teleOption, p, silverStar, goldStar);
    }

    private static void removeFavorite(TeleportInterface.Teleport teleOption, Player p, int silverStar, int goldStar) {
        Favorites instance = new Favorites();
        instance.teleportFavorites = new TeleportFavorites();
        instance.teleportFavorites.removeFavorite(teleOption, p);
        System.out.println(teleOption.getName()+" removed from favorites");
        int size = p.favorites.size();
        System.out.println("There are currently x"+size+" favorites");
        sendFavoritesList(p);
    }

    public static void teleport(Player p) {
        if (!teleportSelected) {
            p.sendMessage("You must select a teleport first!");
        }
        if (favOne) {
            teleFavOne(p);
        } else if (faveTwo) {
            teleFavTwo(p);
        } else if (favThree) {
            teleFavThree(p);
        } else if (favFour) {
            teleFavFour(p);
        } else if (favFive) {
            teleFavFive(p);
        } else if (favSix) {
            teleFavSix(p);
        } else if (favSeven) {
            teleFavSeven(p);
        } else if (favEight) {
            teleFavEight(p);
        } else if (favNine) {
            teleFavNine(p);
        } else if (favTen) {
            teleFavTen(p);
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

    public static void sendFavStars(Player p) {
        int[] childIds = {461, 463, 465, 468, 470, 472, 474, 476, 478, 480};
        for (int i = 0; i < childIds.length; i++) {
            if (i < p.favorites.size() && p.favorites.get(i) != null) {
                p.getPacketSender().setHidden(EVENTS, childIds[i], false);
            } else {
                p.getPacketSender().setHidden(EVENTS, childIds[i], true);

            }
        }
    }

    public static void sendFavoritesString(Player p) {
        int[] childIds = {88, 89, 90, 170, 171, 172, 177, 178, 179, 184};
        for (int i = 0; i < childIds.length; i++) {
            if (i < p.favorites.size() && p.favorites.get(i) != null) {
                p.getPacketSender().sendString(EVENTS, childIds[i], p.favorites.get(i).getName());
            } else {
                p.getPacketSender().sendString(EVENTS, childIds[i], "");
            }
        }
    }

    public static void sendDescriptionString(Player p) {
        int[] childIds = {325, 326, 327, 328, 329, 330, 331, 332, 333, 338};
        for (int i = 0; i < childIds.length; i++) {
            if (i < p.favorites.size() && p.favorites.get(i) != null) {
                p.getPacketSender().sendString(EVENTS, childIds[i], p.favorites.get(i).getInfo());
            } else {
                p.getPacketSender().sendString(EVENTS, childIds[i], "");
            }
        }
    }

    public static void sendFavoritesList(Player p) {
        sendFavoritesString(p);
        sendDescriptionString(p);
        sendFavStars(p);
    }

    public static void useSendNameMethod(Player player, int id) {
        if (player.favorites.get(id) != null) {
            player.getPacketSender().sendString(EVENTS, 427, player.favorites.get(id).getName());
        } else {
            player.getPacketSender().sendString(EVENTS, 427, "");
        }
    }

    public static void selectFavOne(Player p) {
        clearSelections();
        favOne = true;
        teleportSelected = true;
        useSendNameMethod(p, 0);
    }
    public static void teleFavOne(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, p.favorites.get(0).getPosition().getX(), p.favorites.get(0).getPosition().getY(), p.favorites.get(0).getPosition().getZ());
        p.getTeleports().setLastTeleport(p.favorites.get(0));
    }
    public static void selectFavTwo(Player p) {
        clearSelections();
        faveTwo = true;
        teleportSelected = true;
        useSendNameMethod(p, 1);
    }
    public static void teleFavTwo(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, p.favorites.get(1).getPosition().getX(), p.favorites.get(1).getPosition().getY(), p.favorites.get(1).getPosition().getZ());
        p.getTeleports().setLastTeleport(p.favorites.get(1));
    }
    public static void selectFavThree(Player p) {
        clearSelections();
        favThree = true;
        teleportSelected = true;
        useSendNameMethod(p, 2);
    }
    public static void teleFavThree(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, p.favorites.get(2).getPosition().getX(), p.favorites.get(2).getPosition().getY(), p.favorites.get(2).getPosition().getZ());
        p.getTeleports().setLastTeleport(p.favorites.get(2));
    }
    public static void selectFavFour(Player p) {
        clearSelections();
        favFour = true;
        teleportSelected = true;
        useSendNameMethod(p, 3);
    }
    public static void teleFavFour(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, p.favorites.get(3).getPosition().getX(), p.favorites.get(3).getPosition().getY(), p.favorites.get(3).getPosition().getZ());
        p.getTeleports().setLastTeleport(p.favorites.get(3));
    }
    public static void selectFavFive(Player p) {
        clearSelections();
        favFive = true;
        teleportSelected = true;
        useSendNameMethod(p, 4);
    }
    public static void teleFavFive(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, p.favorites.get(4).getPosition().getX(), p.favorites.get(4).getPosition().getY(), p.favorites.get(4).getPosition().getZ());
        p.getTeleports().setLastTeleport(p.favorites.get(4));
    }
    public static void selectFavSix(Player p) {
        clearSelections();
        favSix = true;
        teleportSelected = true;
        useSendNameMethod(p, 5);
    }
    public static void teleFavSix(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, p.favorites.get(5).getPosition().getX(), p.favorites.get(5).getPosition().getY(), p.favorites.get(5).getPosition().getZ());
        p.getTeleports().setLastTeleport(p.favorites.get(5));
    }
    public static void selectFavSeven(Player p) {
        clearSelections();
        favSeven = true;
        teleportSelected = true;
        useSendNameMethod(p, 6);
    }
    public static void teleFavSeven(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, p.favorites.get(6).getPosition().getX(), p.favorites.get(6).getPosition().getY(), p.favorites.get(6).getPosition().getZ());
        p.getTeleports().setLastTeleport(p.favorites.get(6));
    }
    public static void selectFavEight(Player p) {
        clearSelections();
        favEight = true;
        teleportSelected = true;
        useSendNameMethod(p, 7);
    }
    public static void teleFavEight(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, p.favorites.get(7).getPosition().getX(), p.favorites.get(7).getPosition().getY(), p.favorites.get(7).getPosition().getZ());
        p.getTeleports().setLastTeleport(p.favorites.get(7));
    }
    public static void selectFavNine(Player p) {
        clearSelections();
        favNine = true;
        teleportSelected = true;
        useSendNameMethod(p, 8);
    }
    public static void teleFavNine(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, p.favorites.get(8).getPosition().getX(), p.favorites.get(8).getPosition().getY(), p.favorites.get(8).getPosition().getZ());
        p.getTeleports().setLastTeleport(p.favorites.get(8));
    }
    public static void selectFavTen(Player p) {
        clearSelections();
        favTen = true;
        teleportSelected = true;
        useSendNameMethod(p, 9);
    }
    public static void teleFavTen(Player p) {
        p.closeInterface(InterfaceType.MAIN);
        teleport(p, p.favorites.get(9).getPosition().getX(), p.favorites.get(9).getPosition().getY(), p.favorites.get(9).getPosition().getZ());
        p.getTeleports().setLastTeleport(p.favorites.get(9));
    }
}