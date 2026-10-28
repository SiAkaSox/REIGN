package io.ruin.model.entity.player;

import io.ruin.utility.StringUtils;
import io.ruin.utility.XenPost;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Descending order from highest priority group
 */
public enum PlayerGroup {
    OWNER(7, 100, 100),
    DEVELOPER(4, 6, 16),
    COMMUNITY_MANAGER(8, 7, 17),
    ADMINISTRATOR(13, 2, 1),
    MODERATOR(6, 1, 0),
    FORUM_MODERATOR(8, 0, 0),
    SUPPORT(9, 8, 18),
    YOUTUBER(10, 13, 16),
    BETA_TESTER(6, 0, -1),

    ZENYTE(17, 15, 25),
    ONYX(16, 14, 24),
    PLATINUM_DONATOR(15, 40, 19),//Platinum
    RESPECTED_DONATOR(14, 12, 22),//Black
    LEGENDARY_DONATOR(19, 11, 23),//Purple
    SUPER_DONATOR(12, 10, 20),//Green
    EMERALD(23, 2, 2),
    DONATOR(11, 9, 21),//Red

    PVP_MODE(20, 16, 11),
    REGISTERED(2, 0, 0),
    BANNED(18, 0, -1);

    public final int id;

    public final int clientId;

    public final int clientImgId;

    public String title;

    PlayerGroup(int id, int clientId, int clientImgId, String title) {
        this.id = id;
        this.clientId = clientId;
        this.clientImgId = clientImgId;
        this.title = title;
    }
    PlayerGroup(int id, int clientId, int clientImgId, int doubleDropChance) {
        this.id = id;
        this.clientId = clientId;
        this.clientImgId = clientImgId;
        this.title = StringUtils.getFormattedEnumName(name());
    }

    PlayerGroup(int id, int clientId, int clientImgId) {
        this(id, clientId, clientImgId, "");
    }

    public void sync(Player player, String type) {
        sync(player, type, null);
    }

    public void sync(Player player, String type, Runnable successAction) {
        CompletableFuture.runAsync(() -> {
            Map<Object, Object> map = new HashMap<>();
            map.put("userId", player.getUserId());
            map.put("type", type);
            map.put("groupId", id);
            String result = XenPost.post("add_group", map);
            if(successAction != null && "1".equals(result))
                successAction.run();
        });
    }

    public void removePKMode(Player player, String type) {
        removePKMode(player, type, null);
    }

    public void removePKMode(Player player, String type, Runnable successAction) {
        CompletableFuture.runAsync(() -> {
            Map<Object, Object> map = new HashMap<>();
            map.put("userId", player.getUserId());
            map.put("type", type);
            String result = XenPost.post("remove_group", map);
            if(successAction != null && "1".equals(result))
                successAction.run();
        });
    }

    public String tag() {
        return "<img=" + clientImgId + ">";
    }

    public static final PlayerGroup[] GROUPS_BY_ID;

    public String toString() {
        return name().replaceAll("_", " ");
    }

    static {
        int highestGroupId = 0;
        for(PlayerGroup group : values()) {
            if(group.id > highestGroupId)
                highestGroupId = group.id;
        }
        GROUPS_BY_ID = new PlayerGroup[highestGroupId + 1];
        for(PlayerGroup group : values())
            GROUPS_BY_ID[group.id] = group;
    }

}