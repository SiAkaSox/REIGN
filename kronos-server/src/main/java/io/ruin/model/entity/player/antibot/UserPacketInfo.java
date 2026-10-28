package io.ruin.model.entity.player.antibot;

import io.ruin.model.entity.player.Player;

public class UserPacketInfo {

    private Player player;

    public long lastClick;
    public int screenX;
    public int screenY;
    public int cameraX;
    public int cameraY;
    public long focusDuration;

    public UserPacketInfo(Player player) {
        this.player = player;
    }

    public UserPacketInfo(int screenX, int screenY, long lastClick, int cameraX, int cameraY, long focusDuration) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.lastClick = lastClick;
        this.cameraX = cameraX;
        this.cameraY = cameraY;
        this.focusDuration = focusDuration;
    }
}
