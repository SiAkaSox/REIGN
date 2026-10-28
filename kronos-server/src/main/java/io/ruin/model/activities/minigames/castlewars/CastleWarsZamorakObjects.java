package io.ruin.model.activities.minigames.castlewars;

import io.ruin.Server;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;

import static io.ruin.Server.STARTUP_THREAD;

public class CastleWarsZamorakObjects {

    static {
        //2nd floor barriers
        ObjectAction.register(4469, "Pass", (player, obj) -> {
            boolean atObjX = obj.x == player.getAbsX();
            boolean atObjY = obj.y == player.getAbsY();
            if (obj.direction == 0 && atObjX)
                teleportPlayer(player, player.getAbsX() - 1, player.getAbsY());
            else if (obj.direction == 1 && atObjY)
                teleportPlayer(player, obj.x, obj.y + 1);
            else if (obj.direction == 2 && atObjX)
                teleportPlayer(player, obj.x + 1, obj.y);
            else if (obj.direction == 3 && atObjY)
                teleportPlayer(player, obj.x, obj.y - 1);
            else
                teleportPlayer(player, obj.x, obj.y);
        });
        //Exit portal
        ObjectAction.register(4407, "Leave", (player, obj) -> {
            player.dialogue(
                    new OptionsDialogue("Leave the game?",
                            new Option("Yes, leave", () -> {
                                CastleWars castleWars = new CastleWars(player);
                                castleWars.removePlayerFromTeam(player);
                                player.getMovement().teleport(2439, 3089, 0);
                                player.sendMessage("You have left the game and forfeited any potential rewards.");
                            }),
                            new Option("Nevermind")
                    )
            );
        });
    }

    private static void teleportPlayer(Player player, int x, int y) {
        if (player.getCombat().checkTb())
            return;
        player.startEvent(event -> {
            player.lock();
            player.animate(4282);
            event.delay(1);
            player.getMovement().teleport(x, y);
            event.delay(1);
            player.unlock();
        });
    }

}