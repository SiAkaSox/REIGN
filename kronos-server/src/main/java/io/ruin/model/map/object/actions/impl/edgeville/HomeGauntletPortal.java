package io.ruin.model.map.object.actions.impl.edgeville;

import io.ruin.Server;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.object.actions.ObjectAction;

public class HomeGauntletPortal {

    static {
        
            ObjectAction.register(36081, 1, (player, obj) -> usePortal(player));
    }

    public static void usePortal(Player player) {
        player.resetActions(true, true, true);
        player.lock(LockType.FULL_NULLIFY_DAMAGE); //keep lock outside of event!
        player.startEvent(e -> {
            player.getPacketSender().fadeOut();
            e.delay(2);
            player.getMovement().teleport(3035, 6124, 1);
            player.getPacketSender().clearFade();
            PlayerCounter.TELEPORT_PORTAL_USES.increment(player, 1);
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

}