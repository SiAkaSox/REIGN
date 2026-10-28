package io.ruin.model.entity.player;

import com.google.gson.*;
import io.ruin.Server;
import io.ruin.model.entity.player.clanchat.ClanChat;
import io.ruin.process.ProcessFactory;
import io.ruin.utility.FileUtils;
import io.ruin.utility.JsonUtils;
import io.ruin.utility.ListUtils;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.skills.construction.RoomDefinition;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.utility.OfflineMode;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerFile {

    private static final ExecutorService SAVE_EXECUTOR = Executors.newSingleThreadExecutor(new ProcessFactory("save-worker", Thread.NORM_PRIORITY - 1));
    private static final Gson GSON_LOADER = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(Room.class, new JsonDeserializer<Room>() {
        @Override
        public Room deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject obj = jsonElement.getAsJsonObject();
            RoomDefinition definition = RoomDefinition.valueOf(obj.get("definition").getAsString());
            return jsonDeserializationContext.deserialize(jsonElement, definition.getHandler());
        }
    }).create();

    public static void save(Player player, int logoutAttempt) {
        SAVE_EXECUTOR.execute(() -> {
            Config.save(player);
            String json;
            try {
                json = JsonUtils.GSON_EXPOSE_PRETTY.toJson(player);
            } catch (Exception e) {
                Server.logError("", e);
                return;
            }
            OfflineMode.savePlayer(player, logoutAttempt, json);
        });
    }

    public static boolean saveExists(String name) {
        return FileUtils.get(OfflineMode.path + "/" + name.toLowerCase().replaceAll(" ", "_") + ".json").exists();
    }

    public static Player load(PlayerLogin login) {
        try {
            Player player;
            if (login.info.saved == null || login.info.saved.isEmpty())
                player = new Player();
            else
                player = GSON_LOADER.fromJson(login.info.saved, Player.class);
            if (player.getPrimaryGroup() == null)
                player.setGroups(ListUtils.toList(PlayerGroup.REGISTERED.id));
            else
                player.setGroups(ListUtils.toList(player.getPrimaryGroup().id));

            Config.load(player);
            return player;
        } catch (Throwable t) {
            Server.logError("", t);
            return null;
        }
    }

    public static Player loadOffline(String name) {
        try {
            File folder = FileUtils.get(OfflineMode.path);
            name = name.replaceAll(" ", "_").toLowerCase();
            return new Gson().fromJson(new FileReader(folder.getPath() + "/" + name + ".json"), Player.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Player test() {
        try {
            return new Gson().fromJson(new FileReader("data.json"), Player.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}