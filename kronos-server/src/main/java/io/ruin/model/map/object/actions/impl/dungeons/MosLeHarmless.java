package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.Server;
import io.ruin.model.map.object.actions.ObjectAction;

public class MosLeHarmless {

    static {

            ObjectAction.register(5269, 1, (player, obj) -> {
                if (obj.x == 3814) {
                    player.lock();
                    player.getPacketSender().fadeOut();
                    player.getMovement().teleport(3816, 3062, 0);
                    player.getPacketSender().fadeIn();
                    player.unlock();
                }
                if (obj.x == 3829) {
                    player.lock();
                    player.getPacketSender().fadeOut();
                    player.getMovement().teleport(3831, 3062, 0);
                    player.getPacketSender().fadeIn();
                    player.unlock();
                }
            });
            ObjectAction.register(5270, 1, (player, obj) -> {
                if (obj.x == 3814) {
                    player.lock();
                    player.getPacketSender().fadeOut();
                    player.getMovement().teleport(3814, 9461, 0);
                    player.getPacketSender().fadeIn();
                    player.unlock();
                }
                if (obj.x == 3829) {
                    player.lock();
                    player.getPacketSender().fadeOut();
                    player.getMovement().teleport(3829, 9461, 0);
                    player.getPacketSender().fadeIn();
                    player.unlock();
                }
            });
    }

}