package io.ruin.network.incoming.handlers;

import io.ruin.network.io.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.clanchat.ClanChat;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

@IdHolder(ids = {53, 22})
public class ClanHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        String username = in.readString().toLowerCase();
        if (username == null || username.length() > 15) {
            System.err.println("invalid username for "+player.getUsername()+" "+player.getUUID());
            return;
        }
        if (opcode == 53) {
            /**
             * Join / Leave
             */
            if (username.isEmpty()) {
                ClanChat.leave(player, false);
                return;
            }
            ClanChat.join(player, username, false, false, true);
        } else if (opcode == 22) {
            /**
             * Kick
             */
            ClanChat.kick(player, username);
        }
    }

}