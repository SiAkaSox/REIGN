package io.ruin.model.entity.player;

import io.ruin.utility.XenPost;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Descending order from highest priority group
 */
public class PlayerGroupHelpers {

    public static void sync(PlayerGroup group, Player player, String type) {
        sync(group, player, type, null);
    }

    public static void sync(PlayerGroup group, Player player, String type, Runnable successAction) {
        CompletableFuture.runAsync(() -> {
            Map<Object, Object> map = new HashMap<>();
            map.put("userId", player.getUserId());
            map.put("type", type);
            map.put("groupId", group.id);
            String result = XenPost.post("add_group", map);
            if(successAction != null && "1".equals(result))
                successAction.run();
        });
    }

    public static void removePKMode(Player player, String type) {
        removePKMode(player, type, null);
    }

    public static void removePKMode(Player player, String type, Runnable successAction) {
        CompletableFuture.runAsync(() -> {
            Map<Object, Object> map = new HashMap<>();
            map.put("userId", player.getUserId());
            map.put("type", type);
            String result = XenPost.post("remove_group", map);
            if(successAction != null && "1".equals(result))
                successAction.run();
        });
    }


}