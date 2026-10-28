package io.ruin.model.inter.newteleports;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.TeleportInterface;

import java.util.ArrayList;
import java.util.List;

import static io.ruin.utility.ServerWrapper.println;

public class TeleportFavorites {
    private final ArrayList<TeleportInterface.Teleport> favorites = new ArrayList<>();


    public void addFavorite(TeleportInterface.Teleport teleportOption, Player p) {
        if (!p.favorites.contains(teleportOption)) {
            p.favorites.add(teleportOption);
            println(teleportOption.getName()+" added to favorites");
        }
    }

    public void removeFavorite(TeleportInterface.Teleport teleportOption, Player p) {
        p.favorites.remove(teleportOption);
    }

    // Check if a teleport option is a favorite
    public boolean isFavorite(TeleportInterface.Teleport teleportOption) {
        return favorites.contains(teleportOption);
    }

    public List<TeleportInterface.Teleport> getFavorites() {
        return favorites;
    }

    public void sendName(Player p, int id) {
        String name = favorites.get(id).getName();
        p.getPacketSender().sendString(993, 427, name);
    }

    public void sendName1(Player p, int id, int child) {
        String name = favorites.get(id).getName();
        p.getPacketSender().sendString(993, child, name);
    }
}
