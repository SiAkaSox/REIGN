package io.ruin.network.incoming.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.antibot.UserPacketInfo;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.io.InBuffer;
import io.ruin.utility.IdHolder;

@IdHolder(ids = {26})
public class MouseHandler implements Incoming {

    public long lastClick = System.currentTimeMillis();

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        int buttonClickType = in.readShort();//button click type
        int lastButton = buttonClickType == 2 ? 1 : 0;
        int test2 = in.readShort() >> 1;
        int test = lastButton + test2;//time
        int screenX = in.readShort();//correct
        int screenY = in.readShort();//correct
        if (player.antiBotManager != null)
            player.getAntiBotManager().submit(new UserPacketInfo(screenX, screenY, test, player.cameraX, player.cameraY, player.focusDuration));
    }
}
