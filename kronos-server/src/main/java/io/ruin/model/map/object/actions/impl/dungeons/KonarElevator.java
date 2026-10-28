package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.teleports.TeleportInterface;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public class KonarElevator {

    private static void moveDownElevator(Player player, Object obj) {
        if (player.getPosition().getY() == 3807) {
            player.startEvent(e -> {
                player.face(Direction.WEST);
                e.delay(1);
                player.animate(2140);
                e.delay(1);
                TeleportInterface.teleport(player, new Position(1311, 10188, 0));
            });
        } else if (player.getPosition().getY() != 3807) {
            player.startEvent(e -> {
                player.stepAbs(1311, 3807, StepType.FORCE_WALK);
                e.delay(1);
                player.face(Direction.WEST);
                e.delay(1);
                player.animate(2140);
                e.delay(1);
                TeleportInterface.teleport(player, new Position(1311, 10188, 0));
            });
        }
    }

    private static void moveDownLever(Player player, Object obj) {
        player.startEvent(e -> {
            player.animate(2140);
            e.delay(1);
            TeleportInterface.teleport(player, new Position(1311, 10188, 0));
        });
    }

    static{
        ObjectAction.register(34359, "activate", KonarElevator::moveDownElevator);
        ObjectAction.register(34513, "activate", KonarElevator::moveDownLever);
        ObjectAction.register(34514, "exit", (player, obj) -> TeleportInterface.teleport(player, new Position(1311, 3809, 0 )));
    }

}