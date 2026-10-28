package io.ruin.model.activities.wilderness.pvp.unrated;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.interfaceutils.InterfaceType;

import java.util.ArrayList;

public class Arena2v2 {

    private boolean ended;

    private Arena2v2Settings settings;

    private final ArrayList<Player> players = new ArrayList<>();

    Arena2v2(Arena2v2Settings settings) {
        this.settings = settings;
    }

    public boolean ended() {
        return ended;
    }

    public void start(ArrayList<Player> participants) {
        players.addAll(participants);
        players.forEach(player -> {
            player.unratedArena2v2Game = this;
            player.getMovement().teleport(3100, 3504, 0);
            player.teleportListener = p -> {
                p.sendMessage("Spells of this kind are not allowed within the Arena.");
                return false;
            };
            player.logoutListener = new LogoutListener().onLogout(this::leave);
            player.sendMessage("Successfully entered an Arena 3v3 game here");
        });
    }

    public void leave(Player player) {
        clearActivityOf(player);
        players.remove(player);
        player.getMovement().teleport(3100, 3500, 0);
    }

    private void clearActivityOf(Player player) {
        player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
        player.unratedArena2v2Game = null;
        player.teleportListener = null;
        player.deathEndListener = null;
        player.logoutListener = null;
        player.getCombat().restore();
    }

}