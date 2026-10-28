package io.ruin.network.incoming.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.io.InBuffer;
import io.ruin.utility.IdHolder;

import java.util.List;

@IdHolder(ids = {25})
public class CameraHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        int cameraAngleX = in.readUnsignedLEShortA();
        int cameraAngleY = in.readUnsignedLEShort();

        player.cameraX = cameraAngleX;
        player.cameraY = cameraAngleY;

        if (player.spectators == null)
            return;

        List<Player> spectators = player.spectators;
        for (Player sp : spectators) {
            if (sp == null) {
                continue;
            }
            sp.getPacketSender().sendClientScript(143, "ii", cameraAngleX, cameraAngleY);
        }
    }
}
