package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.GameMode;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;

/**
 * @author Danny
 */
public class Adam {
    /**
     * for shops add in shop file;  "ironman": "true", if you want the ironman to have access to it
     */

    private static final int ADAM = 311;

    static {
        NPCAction.register(ADAM, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc, "Hello, my name is Adam and I can give you ironman armor or assist with removing your ironman status."), Adam.options(player, npc)
            );
        });
        //NPCAction.register(ADAM, "armour", (player, npc) -> pickStatus(player));
    }

    private static Dialogue options(Player player, NPC npc) {
        return new OptionsDialogue(
                new Option("Get Armour", () -> pickStatus(player)),
                new Option("Remove ironman status.", () -> confirm(player, npc)));
    }

    private static void removeStatus(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Wise choice, " + player.name())
        );
    }

    public static void pickStatus(Player player) {
        if (player.getGameMode().equals(GameMode.IRONMAN)) {
            player.dialogue(new MessageDialogue("Adam hands you a set of Ironman Armour"));
            player.getInventory().addOrDrop(12810, 1);
            player.getInventory().addOrDrop(12811, 1);
            player.getInventory().addOrDrop(12812, 1);
        } else if (player.getGameMode().equals(GameMode.HARDCORE_IRONMAN)) {
            player.dialogue(new MessageDialogue("Adam hands you a set of Hardcore Ironman Armour"));
            player.getInventory().addOrDrop(20792, 1);
            player.getInventory().addOrDrop(20794, 1);
            player.getInventory().addOrDrop(20796, 1);
        } else if (player.getGameMode().equals(GameMode.ULTIMATE_IRONMAN)) {
            player.dialogue(new MessageDialogue("Adam hands you a set of Ultimate Ironman Armour"));
            player.getInventory().addOrDrop(12813, 1);
            player.getInventory().addOrDrop(12814, 1);
            player.getInventory().addOrDrop(12815, 1);
        } else {
            player.sendMessage("Adam isn't interested in giving armour to non ironman mode players.");
        }
    }

    private static void confirm(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Are you sure you want to continue..."), options1(player, npc)
        );
    }

    private static Dialogue options1(Player player, NPC npc) {
        return new OptionsDialogue(
                new Option("No, I don't want my ironman status removed", () -> removeStatus(player, npc)),
                new Option("Remove ironman status.", () -> removedStatus(player)));
    }

    private static void removedStatus(Player player) {
        Config.IRONMAN_MODE.set(player, 0);
        player.sendFilteredMessage("Your ironman status has been removed.");
    }

}
