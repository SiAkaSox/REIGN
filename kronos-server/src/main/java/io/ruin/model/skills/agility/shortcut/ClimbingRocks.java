package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author ReverendDread on 3/16/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class ClimbingRocks {

    static {
        ObjectAction.register(34397, "Climb", ClimbingRocks::climb);
        ObjectAction.register(34396, "Climb", ClimbingRocks::climb);
    }

    public static void climb(Player player, GameObject object) {
        player.startEvent(e -> {
            e.waitForMovement(player);
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(4435);
            if (player.getAbsY() < object.y)
                player.getMovement().force(0, 8, 0, 0, 0, 120, Direction.NORTH);
            else
                player.getMovement().force(0, -8, 0, 0, 0, 120, Direction.SOUTH);
            e.delay(5);
            player.animate(-1);
            player.unlock();
        });
    }

}
