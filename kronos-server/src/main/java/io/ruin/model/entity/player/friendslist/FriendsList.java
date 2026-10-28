package io.ruin.model.entity.player.friendslist;

import com.google.common.collect.Maps;
import com.google.gson.annotations.Expose;
import io.ruin.network.io.OutBuffer;
import io.ruin.utility.StringUtils;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.clanchat.ClanChat;
import io.ruin.model.entity.player.clanchat.ClanRank;

import java.util.Map;
import java.util.Objects;

public class FriendsList {

    private Player player;

    public String username;

    @Expose
    public int privacy;

    private static final int MAX_FRIENDS = 400;
    private static final int MAX_IGNORES = 400;
    @Expose
    protected int friendsCount;
    @Expose
    public Map<String, Friend> friends;
    @Expose
    protected int ignoresCount;
    @Expose
    public Map<String, Friend> ignores;

    public FriendsList() {
        friends = Maps.newConcurrentMap();
        ignores = Maps.newConcurrentMap();
    }

    public boolean addFriend(Friend friend) {
        if (this.friends == null) {
            this.friends = Maps.newHashMap();
        }
        if (this.friends.size() >= MAX_FRIENDS || this.isIgnored(friend.name) || this.isFriend(friend.name)) {
            return false;
        }
        this.friends.put(friend.name, friend);
        this.friendsCount = this.friends.size();
        update();
        return true;
    }

    public String deleteFriend(String name) {
        if (this.friends == null) {
            return "";
        }
        Friend friend = this.friends.remove(name);
        if (friend != null) {
            this.friendsCount = this.friends.size();
            return name;
        }
        return "";
    }

    public Friend getFriend(String name) {
        if (this.friends == null) {
            return null;
        }

        return this.friends.get(name);
    }

    public boolean isFriend(String name) {
        return this.getFriend(name) != null;
    }

    public void addIgnore(Friend ignore) {
        if (this.ignores == null) {
            this.ignores = Maps.newHashMap();
        }
        if (this.ignores.size() >= MAX_IGNORES || this.isIgnored(ignore.name) || this.isFriend(ignore.name)) {
            return;
        }
        this.ignores.put(ignore.name, ignore);
        this.ignoresCount = this.ignores.size();
        updateIgnores();
    }

    public String deleteIgnore(String name) {
        if (this.ignores == null) {
            return "";
        }
        Friend ignore = this.ignores.remove(name);
        if (ignore != null) {
            this.ignoresCount = this.ignores.size();
            return name;
        }
        return "";
    }

    public Friend getIgnore(String username) {
        if (this.ignores == null) {
            return null;
        }
        return this.ignores.get(username);
    }

    public boolean isIgnored(String username) {
        return this.getIgnore(username) != null;
    }

    private void add(String name, boolean ignore) {
        name = StringUtils.capitalizeName(name);
        Friend friend = new Friend(name, ignore ? null : ClanRank.FRIEND);
        if (ignore) {
            this.addIgnore(friend);
        } else {
            this.addFriend(friend);
        }
        ClanChat.updateRanks(player);
    }

    public static void handle(Player player, String name, FriendsListAction action) {
        name = StringUtils.capitalizeName(name);
        switch (action) {
            case ADD_FRIEND:
                player.getFriendsList().add(name, false);
                break;
            case DELETE_FRIEND:
                player.getFriendsList().delete(name, false);
                break;
            case ADD_IGNORE:
                player.getFriendsList().add(name, true);
                break;
            case DELETE_IGNORE:
                player.getFriendsList().delete(name, true);
                break;
        }
    }

    private void delete(String name, boolean ignore) {
        if (ignore) {
            this.deleteIgnore(name);
        } else {
            this.deleteFriend(name);
        }
        ClanChat.updateRanks(player);
    }


    public void init(Player player) {
        this.player = player;
        this.sendFriends();//unlocks
        this.sendIgnores();//unlocks
        this.sendPrivacy(this.privacy);
        this.update();//refreshes list.
        check();//login loop for friends.
    }

    private void sendPrivacy(int privacy) {
        OutBuffer out = new OutBuffer(2).sendFixedPacket(17).addByte(privacy);
        player.getPacketSender().write(out);
    }

    private void sendFriends() {
        player.getPacketSender().write(new OutBuffer(3).sendVarShortPacket(61));
    }

    private void sendIgnores() {
        player.getPacketSender().write(new OutBuffer(3).sendVarShortPacket(59));
    }

    public static void check() {
        for (Player player : World.players) {//loops world players
            if (player == null)//null player doesn't loop
                continue;
            FriendsList list = player.getFriendsList();//gets the world players friends list.
            if (list == null)
                continue;
            if (list.isIgnored(player.name))//if on ignore list then ignores
                continue;
            for (Map.Entry<String, Friend> f : list.friends.entrySet()) {//loops friends list friend object
                if (f == null)//if object is null skips
                    continue;
                Friend friend = f.getValue();//gets friend via index start - finish
                if (friend == null)//if friend is null then skips
                    continue;
                Player fri = World.getPlayer(friend.name);//If the friend is online.
                if (fri == null || Objects.equals(player, fri))//if not online then skips
                    continue;
                player.getFriendsList().update(friend);//sends player the friends list of all friends.
            }
        }
    }

    private void update(Friend friend) {
        if (player == null)
            return;
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(61);
        boolean online = World.getPlayer(friend.name) != null && World.getPlayer(friend.name).isOnline();
        out.addByte(0);//update name.
        out.addString(friend.name);
        out.addString(friend.lastName);
        out.addShort(online ? World.id : 0);//has to be 0 if offline
        out.addByte(friend.rank == null ? -1 : friend.rank.id);
        out.addByte(0);
        if (online) {
            out.skip(6);
        }
        out.skip(1);
        player.getPacketSender().write(out);
    }

    private void update() {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(61);
        for (Map.Entry<String, Friend> f : friends.entrySet()) {
            Friend friend = f.getValue();
            if (friend == null)
                continue;
            boolean online = World.getPlayer(friend.name) != null && World.getPlayer(friend.name).isOnline();
            out.addByte(0);//new name.
            out.addString(friend.name);
            out.addString(friend.lastName);
            out.addShort(online ? 1 : 0);//has to be 0 if offline
            out.addByte(friend.rank == null ? -1 : friend.rank.id);
            out.addByte(0);
            if (online) {
                out.skip(6);
            }
            out.skip(1);
        }
        player.getPacketSender().write(out);

    }

    private void updateIgnores() {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(59);
        if (ignores != null) {
            for (Map.Entry<String, Friend> f : ignores.entrySet()) {
                Friend ignore = f.getValue();
                if (ignore == null)
                    continue;
                out.addByte(0);
                out.addString(ignore.name);
                out.addString(ignore.lastName);
                out.skip(1);
            }
        }
        player.getPacketSender().write(out);
    }
}
