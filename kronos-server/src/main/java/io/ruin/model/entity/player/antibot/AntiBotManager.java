package io.ruin.model.entity.player.antibot;

import com.google.api.client.util.Lists;
import io.ruin.model.entity.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AntiBotManager {

    private Player player;

    public List<UserPacketInfo> sessionInfo = Lists.newArrayList();

    public void submit(UserPacketInfo userPacketInfo) {
        sessionInfo.add(userPacketInfo);
    }

    public void init(Player player) {
        this.player = player;
    }

    private void start() {

    }

}
