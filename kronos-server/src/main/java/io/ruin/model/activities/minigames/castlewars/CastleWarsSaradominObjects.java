package io.ruin.model.activities.minigames.castlewars;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;

import static io.ruin.model.map.object.actions.impl.Ladder.climb;

public class CastleWarsSaradominObjects {

    static {
        //2nd floor barriers
        ObjectAction.register(4470, "Pass", (player, obj) -> {
            boolean atObjX = obj.x == player.getAbsX();
            boolean atObjY = obj.y == player.getAbsY();
            if (obj.direction == 0 && atObjX)
                teleportPlayer(player, player.getAbsX() - 1, player.getAbsY(), obj.z);
            else if (obj.direction == 1 && atObjY)
                teleportPlayer(player, obj.x, obj.y + 1, obj.z);
            else if (obj.direction == 2 && atObjX)
                teleportPlayer(player, obj.x + 1, obj.y, obj.z);
            else if (obj.direction == 3 && atObjY)
                teleportPlayer(player, obj.x, obj.y - 1, obj.z);
            else
                teleportPlayer(player, obj.x, obj.y, obj.z);
        });
        //1st floor barricade table
        ObjectAction.register(4461, "Take-from", (player, obj) -> {
            player.startEvent(e -> {
                player.lock();
                player.animate(832);
                e.delay(1);
                player.getInventory().addOrDrop(4053, 1);
                player.unlock();
            });
        });
        ObjectAction.register(4461, "Take-5", (player, obj) -> {
            player.startEvent(e -> {
                player.lock();
                player.animate(832);
                e.delay(1);
                player.getInventory().addOrDrop(4053, 5);
                player.unlock();
            });
        });
        //2nd floor bandage table
        ObjectAction.register(4458, "Take-from", (player, obj) -> {
            player.startEvent(e -> {
                player.lock();
                player.animate(832);
                e.delay(1);
                player.getInventory().addOrDrop(4049, 1);
                player.unlock();
            });
        });
        ObjectAction.register(4458, "Take-5", (player, obj) -> {
            player.startEvent(e -> {
                player.lock();
                player.animate(832);
                e.delay(1);
                player.getInventory().addOrDrop(4049, 5);
                player.unlock();
            });
        });
        //4415 stairs
        ObjectAction.register(4415, "Climb", (player, obj) -> {
            if (obj.z == 1)
                climb(player, 2419, 3077, 0, false, true, true);
            if (obj.z == 2)
                climb(player, 2427, 3081, 1, false, true, true);
            if (obj.z == 3)
                climb(player, 2425, 3077, 2, true, true, true);
        });
        //4417 stairs
        ObjectAction.register(4417, "Climb", (player, obj) -> {
            if (obj.z == 0)
                climb(player, 2420, 3080, 1, true, true, true);
            if (obj.z == 1)
                climb(player, 2430, 3080, 2, true, true, true);
            if (obj.z == 2)
                climb(player, 2426, 3074, 3, true, true, true);
        });
        //1st floor stairs going up
        ObjectAction.register(4419, "Climb", (player, obj) -> {
            if (player.getAbsX() == 2417 && player.getAbsY() == 3077)
                climb(player, 2416, 3074, 0, true, true, false);
        });
        //1st floor stairs going down
        ObjectAction.register(4419, "Climb", (player, obj) -> {
            if (player.getAbsX() == 2416 && player.getAbsY() == 3074)
                climb(player, 2417, 3077, 0, false, true, false);
        });
        //Capture flag
        ObjectAction.register(4902, "Capture", (player, obj) -> {
            //fill this in
        });
        //Exit portal
        ObjectAction.register(4406, "Leave", (player, obj) -> {
            player.dialogue(
                    new OptionsDialogue("Leave the game?",
                            new Option("Yes, leave the game.", () -> {
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

    private static void teleportPlayer(Player player, int x, int y, int z) {
        if(player.getCombat().checkTb())
            return;
        player.startEvent(event -> {
            player.lock();
            player.animate(4282);
            event.delay(1);
            player.getMovement().teleport(x, y, z);
            event.delay(1);
            player.unlock();
        });
    }

}