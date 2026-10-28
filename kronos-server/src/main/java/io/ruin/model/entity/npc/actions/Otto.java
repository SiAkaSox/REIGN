package io.ruin.model.entity.npc.actions;


import io.ruin.model.achievements.listeners.master.Godblessed;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;

public class Otto {

    public static final int OTTO = 2914;

    static {
        NPCAction.register(OTTO, "talk-to", Otto::ottoDialogue);
    }

    private static void ottoDialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "For a 500k fee, I can convert your Zamorakian Spear into a Hasta, and vice versa. Would you like me to do this?"),
                new OptionsDialogue("Convert Zamorakian Spear?",
                        new Option("Convert spear into hasta", () -> convertSpear(player, npc)),
                        new Option("Convert hasta into spear", () -> convertHasta(player, npc)),
                        new Option("Nevermind"))
        );
    }

    public static void convertSpear(Player player, NPC npc) {
        if (player.getInventory().contains(995, 500000) && player.getInventory().contains(11824, 1)) {
            player.getInventory().remove(995, 500000);
            player.getInventory().remove(11824, 1);
            player.getInventory().add(11889, 1);
            Godblessed.complete(player);
            player.dialogue(
                    new MessageDialogue("Otto converts your Zamorakian Spear into a Zamorakian Hasta.")
            );
        } else {
            player.sendMessage("You do not have the required items to do this.");
        }
    }
    public static void convertHasta(Player player, NPC npc) {
        if (player.getInventory().contains(995, 500000) && player.getInventory().contains(11889, 1)) {
            player.getInventory().remove(995, 500000);
            player.getInventory().remove(11889, 1);
            player.getInventory().add(11824, 1);
            Godblessed.complete(player);
            player.dialogue(
                    new MessageDialogue("Otto converts your Zamorakian Hasta back into a Zamorakian Spear.")
            );
        } else {
            player.sendMessage("You do not have the required items to do this.");
        }
    }


}