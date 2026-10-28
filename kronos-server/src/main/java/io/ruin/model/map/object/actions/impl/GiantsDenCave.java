package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public class GiantsDenCave {

    static {
        ObjectAction.register(42247, 1, (p, obj) -> leaveCave(p));
        ObjectAction.register(42248, 1, (p, obj) -> enterfromOutside(p));
    }

    public static final Position INSIDE = new Position(1432, 9912, 0);
    public static final Position OUTSIDE = new Position(1420, 3589, 0);

    public static void enterfromOutside(Player p) {
        teleport(p, INSIDE);
    }
    public static void leaveCave(Player p) {
        teleport(p, OUTSIDE);
    }

    public static void teleport(Player player, Position position) {
        player.resetActions(true, true, true);
        player.lock(LockType.FULL_NULLIFY_DAMAGE); //keep lock outside of event!
        player.startEvent(e -> {
            player.getPacketSender().fadeOut();
            e.delay(1);
            player.getMovement().teleport(position);
            player.getPacketSender().clearFade();
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

}