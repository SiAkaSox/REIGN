package io.ruin.model.map.object.actions.impl;

import io.ruin.model.activities.bosses.galvek.Galvek;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public class BootyBayShip {

    public static final Position KNIGHTS_GUILD = new Position(3282, 4094, 0);

    static {
        ObjectAction.register(27777, 1, ((player, obj) -> { displayOptions(player); }));
    }

    public static void displayOptions(Player p) {
        p.dialogue(
                new OptionsDialogue("Where do you want to go?",
                        new Option("Wilderness Expansion", () -> selectKnightsGuild(p)),
                        new Option("Galvek", () -> selectGalvek(p)),
                        new Option("Nevermind")
                )
        );
    }
    public static void selectKnightsGuild (Player p) {
        if (p.pirateshipCompletions >= 1) {
            teleport(p, KNIGHTS_GUILD);
        } else {
            p.sendMessage("You must complete the Pirates Ship event at least once to use this boat.");
        }
    }
    public static void selectGalvek(Player p) {
        if (p.pirateshipCompletions >= 1) {
            Galvek.enter(p);
        } else {
            p.sendMessage("You must complete the Pirates Ship event at least once to use this boat.");
        }
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