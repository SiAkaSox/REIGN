package io.ruin.model.entity.player.friendslist;

import com.google.gson.annotations.Expose;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.clanchat.ClanRank;

import java.util.Objects;

public class Friend {

    @Expose
    public String name;
    @ Expose public String lastName;
    public boolean newName;
    @ Expose public ClanRank rank;

    public Friend(String username, ClanRank rank) {
        this.name = username;
        this.lastName = "";
        this.rank = rank;
    }
}
