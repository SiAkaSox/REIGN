package io.ruin.utility;

import io.ruin.Server;
import io.ruin.network.login.protocol.Protocol;
import io.ruin.network.login.protocol.Response;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerLogin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class OfflineMode {

    public static boolean enabled = true;

    public static String path;

    static {
        path = World.getSavePath("players").getPath();
    }

    /**
     * Players
     */

    private static final Object userIdLock = new Object();

    private static final HashMap<String, Integer> tempUserIds = new HashMap<>();

    private static int userIdOffset;

    public static boolean savePlayer(Player player, int logoutAttempt, String json) {
        try {
            File folder = FileUtils.get(path);
            if (!folder.exists() && !folder.mkdir())
                throw new IOException("Failed to create offline players folder!");
            Files.write(FileUtils.get(folder.getPath() + "/" +player.name().toLowerCase() + ".json").toPath(), json.getBytes());
        } catch (IOException e) {
            Server.logError("", e);
        } finally {
            if (logoutAttempt != -1)
                player.finishLogout(logoutAttempt);
        }
        return true;
    }

    public static boolean loadPlayer(PlayerLogin login) {
        if (Protocol.method360(login.info.name) == null) {
            login.deny(Response.CHANGE_DISPLAY_NAME);
            return true;
        }
        Integer userId;
        synchronized (userIdLock) {
            String key = login.info.name.toLowerCase();
            userId = tempUserIds.computeIfAbsent(key, k -> ++userIdOffset);
        }
        String saved = "";
        try {
            File savedFile = FileUtils.get(path+"/"+login.info.name.toLowerCase() + ".json");
            if (savedFile.exists())
                saved = new String(Files.readAllBytes(savedFile.toPath()));
        } catch (Exception e) {
            Server.logError("", e);
        }
        login.info.update(userId, login.info.name, saved, 0);
        login.success();
        return true;
    }

}