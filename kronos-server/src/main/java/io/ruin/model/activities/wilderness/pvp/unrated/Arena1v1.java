package io.ruin.model.activities.wilderness.pvp.unrated;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.interfaceutils.InterfaceType;

import java.util.ArrayList;

public class Arena1v1 {

    private boolean ended;

    private Arena1v1Settings settings;

    private final ArrayList<Player> players = new ArrayList<>();

    public Arena1v1(Arena1v1Settings settings) {
        this.settings = settings;
    }

    public boolean ended() {
        return ended;
    }

    public void start(ArrayList<Player> participants) {
        players.addAll(participants);
        players.forEach(player -> {
            player.unratedArena1v1Game = this;
            player.getMovement().teleport(3100, 3504, 0);
            player.teleportListener = p -> {
                p.sendMessage("Spells of this kind are not allowed within the arena.");
                return false;
            };
            player.logoutListener = new LogoutListener().onLogout(this::leave);
            player.sendMessage("Successfully entered a arena game here");
        });
    }

    public void leave(Player player) {
        clearActivityOf(player);
        players.remove(player);
        player.getMovement().teleport(3100, 3500, 0);
    }

    private void clearActivityOf(Player player) {
        player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
        player.battleGroundsGame = null;
        player.teleportListener = null;
        player.deathEndListener = null;
        player.logoutListener = null;
        player.getCombat().restore();
    }

}