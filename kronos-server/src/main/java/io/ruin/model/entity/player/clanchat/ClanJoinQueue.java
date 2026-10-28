package io.ruin.model.entity.player.clanchat;

import io.ruin.model.entity.player.Player;

public class ClanJoinQueue {

    public Player player;
    public String name;
    public boolean login, newPlayer;

    public ClanJoinQueue(Player player, String name, boolean login, boolean newPlayer) {
        this.player = player;
        this.name = name;
        this.login = login;
        this.newPlayer = newPlayer;
    }
}
