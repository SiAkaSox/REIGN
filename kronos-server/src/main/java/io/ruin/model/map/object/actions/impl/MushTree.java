package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.map.object.actions.ObjectAction;

public class MushTree {
    static {
        InterfaceHandler.register(608, h -> {
            ObjectAction.register(30924, "use", (player, object) -> MushTree.open(player));
            ObjectAction.register(30920, "use", (player, object) -> MushTree.open(player));
            h.actions [4] = (SimpleAction) MushTree::houseonthehill;
            h.actions [8] = (SimpleAction) MushTree::verdantvalley;
            h.actions [12] = (SimpleAction) MushTree::stickyswamp;
            h.actions [16] = (SimpleAction) MushTree::mushroomeadow;
        });
    }

    public static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, 608);
        player.getPacketSender().sendString(608, 5,"1. House on the Hill");
        player.getPacketSender().sendString(608, 9,"2. Verdant Valley");
        player.getPacketSender().sendString(608, 13,"3. Sticky Swamp");
        player.getPacketSender().sendString(608, 17,"4. Mushroom Meadow");
    }

    public static void houseonthehill(Player player) {
        player.startEvent(e -> {
            player.lock();
            player.closeInterface(InterfaceType.MAIN);
            player.getPacketSender().sendClientScript(101, "i", 11);
            player.getPacketSender().fadeOut();
            e.delay(2);
            player.getMovement().teleport(3764, 3879, 1);
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

    public static void verdantvalley(Player player) {
        player.startEvent(e -> {
            player.lock();
            player.closeInterface(InterfaceType.MAIN);
            player.getPacketSender().sendClientScript(101, "i", 11);
            player.getPacketSender().fadeOut();
            e.delay(2);
            player.getMovement().teleport(3757, 3757, 0);
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

    public static void stickyswamp(Player player) {
        player.startEvent(e -> {
            player.lock();
            player.closeInterface(InterfaceType.MAIN);
            player.getPacketSender().sendClientScript(101, "i", 11);
            player.getPacketSender().fadeOut();
            e.delay(2);
            player.getMovement().teleport(3676, 3755, 0);
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

    public static void mushroomeadow(Player player) {
        player.startEvent(e -> {
            player.lock();
            player.closeInterface(InterfaceType.MAIN);
            player.getPacketSender().sendClientScript(101, "i", 11);
            player.getPacketSender().fadeOut();
            e.delay(2);
            player.getMovement().teleport(3676, 3871, 0);
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

}
