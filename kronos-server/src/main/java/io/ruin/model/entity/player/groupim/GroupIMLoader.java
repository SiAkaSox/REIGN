package io.ruin.model.entity.player.groupim;

import com.google.gson.annotations.Expose;
import io.ruin.utility.JsonUtils;
import io.ruin.data.DataFile;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerLogin;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class GroupIMLoader extends DataFile {

    private static final Map<String, Set> LOADED = new HashMap<>();

    @Override
    public String path() {
        return "groups/ " + (World.isEco() ? "/eco/" : "/pvp/") + "/*.json";
    }

    @Override
    public Object fromJson(String fileName, String json) {
        Set set = JsonUtils.fromJson(json, Set.class);
        LOADED.put(fileName, set);
        return set;
    }

    private static final class Set {

        @Expose
        public String[] group_members;

        private boolean isAllowed(PlayerLogin login) {
            if(group_members == null || Stream.of(group_members).anyMatch(name -> name.equalsIgnoreCase(login.info.name)))
                return true;
            return false;
        }

    }

    /**
     * Active
     */

    private static Set ACTIVE_SET;

    public static void setActive(Player player, String name) {
        if(player != null) {
            LOADED.clear();
            DataFile.reload(player, GroupIMLoader.class);
        }
        if((name = name.trim()).isEmpty())
            name = World.getName().toLowerCase();
        if((ACTIVE_SET = LOADED.get(name)) != null) {
            if(player != null)
                player.sendMessage("Login set \"" + name + "\" is now active.");
        } else {
            if(player != null)
                player.sendMessage("Login set \"" + name + "\" does not exist!");
            else
                System.err.println("Login set \"" + name + "\" does not exist!");
        }
    }

}
