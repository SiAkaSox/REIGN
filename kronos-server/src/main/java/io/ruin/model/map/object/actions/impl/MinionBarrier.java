package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Region;
import io.ruin.model.map.object.actions.ObjectAction;

public class MinionBarrier {

    private static Bounds bossBounds;
    private static int playerCount;

    static {
        ObjectAction.register(42939, 1, (player, obj) -> { checkKey(player); });
        ObjectAction.register(42939, 3, (player, obj) -> { peekInside(player); });
        ObjectAction.register(42940, 1, (player, obj) -> { exitBarrier(player); });
       // MapListener.registerBounds(bossBounds).onEnter(player -> playerCount++).onExit((player, logout) -> playerCount--);

    }

    public static void checkKey(Player p) {
        p.dialogue(
                new OptionsDialogue("Pass through the barrier?",
                        new Option("Yes, I'm not afraid!", () -> {
                            enterBarrier(p);
                        }),
                        new Option("No, nevermind."))
        );
    }

    public static void enterBarrier(Player p) {
        if (p.getCombat().tbTicks > 0) {
            p.sendMessage("You cannot use this while teleblocked!");
            return;
        }
        p.addEvent(event -> {
            p.getInventory().remove(26356, 1);
            p.getPacketSender().fadeOut();
            event.delay(2);
            p.getMovement().teleport(3169, 4110, 0);
            event.delay(2);
            p.getPacketSender().fadeIn();
            p.sendMessage("<col=FF0000>You pass the barrier, and arrive in the courtyard.");
        });
    }

    public static void peekInside(Player p) {
        if(playerCount == 0)
            p.sendFilteredMessage("<col=FF0000>You look inside the crevice and see no adventurers in the courtyard.");
        else
            p.sendFilteredMessage("You look inside the crevice and see <col=FF0000>" + playerCount + " adventurer" + (playerCount == 1 ? "" : "s") + "</col> in the courtyard.");

    }

    public static void exitBarrier(Player p) {
        if (p.getCombat().tbTicks > 0) {
            p.sendMessage("You cannot use this while teleblocked!");
            return;
        }
        p.addEvent(event -> {
            p.getPacketSender().fadeOut();
            event.delay(2);
            p.getMovement().teleport(3169, 4102, 0);
            event.delay(2);
            p.getPacketSender().fadeIn();
            p.sendMessage("<col=FF0000>You pass the barrier, and leave the courtyard.");
        });
    }

}