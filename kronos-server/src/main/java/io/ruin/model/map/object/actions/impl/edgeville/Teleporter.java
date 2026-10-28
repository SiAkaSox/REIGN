package io.ruin.model.map.object.actions.impl.edgeville;

import io.ruin.data.impl.teleports;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.actions.ObjectAction;

public class Teleporter {

    static {
        ObjectAction.register(37446, "use", (p, object) -> openTeleporter(p));
        ObjectAction.register(37446, "previous", (p, object) -> p.sendMessage("This feature is under construction."));
        ObjectAction.register(35965, "use", (p, object) -> openTeleporter(p));
        ObjectAction.register(35965, "previous", (p, object) -> teleportPrevious(p));
    }

    public static void openTeleporter(Player p) {
        p.getTeleporter().openInterface(p);
        io.ruin.model.achievements.listeners.easy.Teleporter.complete(p);
    }

    public static void teleportPrevious(Player player) {
        if (player.getTeleports().getLastTeleport() != null) {
            teleports.teleport(player, player.getTeleports().getLastTeleport().getPosition());
        } else {
            player.sendMessage("You haven't teleported previously.");
        }
    }

}