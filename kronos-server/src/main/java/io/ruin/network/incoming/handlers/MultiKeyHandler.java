package io.ruin.network.incoming.handlers;

import io.ruin.content.activities.tournament.TournamentManager;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.actions.impl.edgeville.Teleporter;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.io.InBuffer;
import io.ruin.utility.IdHolder;

import static io.ruin.model.activities.wilderness.pvp.unrated.Arena1v1Queue.inArena;

@IdHolder(ids = {101})
public class MultiKeyHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        int keyId = in.readByte();

        switch (keyId) {
            case 0://teleport
                boolean requirements = player.storeAmountSpent >= 250 && player.wildernessLevel < 1;
                if (!player.isAdminAbove()) {
                    if (!requirements) {
                        player.sendMessage("You must be a <img=22> <shad=0000000><col=FFFFFF>Diamond Donator</col></shad> or above to use this command");
                        return;
                    }
                    if (TournamentManager.Lobby.INSTANCE != null && TournamentManager.Lobby.INSTANCE.getPlayers().contains(player)) {
                        return;
                    }
                    if (player.lmsSession != null) {
                        player.sendMessage("You cannot use this shortcut teleport feature in LMS.");
                        return;
                    }
                    if (player.tournament != null)
                        return;
                    if (inArena) {
                        player.sendMessage("You cannot use this shortcut teleport feature in the Arena.");
                        return;
                    }
                    if (player.attackPlayerListener != null || player.attackNpcListener != null) {
                        player.sendMessage("You cannot use this shortcut teleport feature here.");
                        return;
                    }
                }
                Teleporter.openTeleporter(player);
                break;
            case 1://bank
                boolean requirements2 = player.storeAmountSpent >= 250 && player.wildernessLevel < 1;
                if (!player.isAdminAbove()) {
                    if (!requirements2) {
                        player.sendMessage("You must be a <img=22> <shad=0000000><col=FFFFFF>Diamond Donator</col></shad> or above to use this command");
                        return;
                    }
                    if (player.tournament != null)
                        return;
                    if (TournamentManager.Lobby.INSTANCE != null && TournamentManager.Lobby.INSTANCE.getPlayers().contains(player)) {
                        return;
                    }
                    if (player.lmsSession != null) {
                        player.sendMessage("You cannot use this shortcut bank feature in LMS.");
                        return;
                    }
                    if (inArena) {
                        player.sendMessage("You cannot use this shortcut bank feature in the Arena.");
                        return;
                    }
                    if (player.attackPlayerListener != null || player.attackNpcListener != null) {
                        player.sendMessage("You cannot use this shortcut bank feature here.");
                        return;
                    }
                }
                player.getBank().open();
                break;
            default:
                break;
        }
    }
}