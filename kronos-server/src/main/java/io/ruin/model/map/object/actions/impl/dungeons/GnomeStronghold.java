package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.Server;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.actions.ObjectAction;

public class GnomeStronghold {

    static {

            /**
             * Entrance/exit
             */
            ObjectAction.register(26709, 2428, 3423, 0, "enter", (player, obj) -> player.startEvent(event -> {
                player.lock();
                player.animate(2796);
                event.delay(2);
                player.resetAnimation();
                player.getMovement().teleport(2429, 9824, 0);
                player.unlock();
            }));
            ObjectAction.register(27257, 2430, 9824, 0, "use", (player, obj) -> player.startEvent(event -> {
                player.lock();
                player.animate(2796);
                event.delay(2);
                player.resetAnimation();
                player.getMovement().teleport(2430, 3424, 0);
                player.unlock();
            }));
            ObjectAction.register(30174, "Enter", (player, obj) -> {
                if (obj.x == 2430 && obj.y == 9806)
                    tunnel(player, 2435, 9806);
                else
                    tunnel(player, 2429, 9806);
            });
            ObjectAction.register(30175, "Enter", (player, obj) -> {
                if (obj.x == 2430 && obj.y == 9807)
                    tunnel(player, 2435, 9807);
                else
                    tunnel(player, 2429, 9807);
            });
    }

    public static void tunnel(Player player, int x, int y) {
        player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.getMovement().teleport(x, y);
            player.resetAnimation();
            player.unlock();
        });
    }
}
