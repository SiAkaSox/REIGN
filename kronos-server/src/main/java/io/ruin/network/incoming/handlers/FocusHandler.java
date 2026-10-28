package io.ruin.network.incoming.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.io.InBuffer;
import io.ruin.utility.IdHolder;

@IdHolder(ids = {15})
public class FocusHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {

        boolean focused = in.readByte() == 1;
        player.isClientFocused = focused;
        player.isTabbedOut = !focused;
        if (focused) {
            player.focusDuration = System.currentTimeMillis();
        }


    }
}
