package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.Server;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public class WildernessSlayerDungeon {

    public static int NORTH_EXIT = 40391;
    public static final Position NORTH_EXIT_POS = new Position(3293, 3749, 0);
    public static int SOUTH_EXIT = 40389;
    public static final Position SOUTH_EXIT_POS = new Position(3260, 3663, 0);
    public static int NORTH_ENTRANCE = 40390;
    public static final Position NORTH_ENTRANCE_POS = new Position(3406, 10145, 0);
    public static int SOUTH_ENTRANCE = 40388;
    public static final Position SOUTH_ENTRANCE_POS = new Position(3385, 10052, 0);

    static {
        
            ObjectAction.register(SOUTH_EXIT, 3384, 10050, 0, "exit", (player, obj) -> teleport(player, SOUTH_EXIT_POS));
            ObjectAction.register(NORTH_EXIT, 3405, 10146, 0, "exit", (player, obj) -> teleport(player, NORTH_EXIT_POS));
            ObjectAction.register(NORTH_ENTRANCE, 3293, 3746, 0, "walk-down", (player, obj) -> teleport(player, NORTH_ENTRANCE_POS));
            ObjectAction.register(SOUTH_ENTRANCE, 3259, 3664, 0, "walk-down", (player, obj) -> teleport(player, SOUTH_ENTRANCE_POS));
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