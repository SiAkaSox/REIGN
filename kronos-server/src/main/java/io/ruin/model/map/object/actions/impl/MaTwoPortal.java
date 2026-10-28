package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;

public class MaTwoPortal {

    static {
        ObjectAction.register(43747, 1, (player, obj) -> { justiciarExitPortal(player); });
        ObjectAction.register(43745, 1, (player, obj) -> { derwenExitPortal(player); });
        ObjectAction.register(43749, 1, (player, obj) -> { porazdirExitPortal(player); });
    }

    public static void openDialogue(Player p) {
        p.dialogue(
                new OptionsDialogue("Select a boss to fight",
                        new Option("Derwen", () -> selectDerwen(p)),
                        new Option("Porazdir", () -> selectPorazdir(p)),
                        new Option("Justiciar Zachariah", () -> selectJusticiar(p)),
                        new Option("Nevermind"))
        );
    }

    public static void selectJusticiar(Player p) {
        if (Config.GWD_SARADOMIN_KC.get(p) >= 20) {
            p.addEvent(event -> {
                Config.GWD_SARADOMIN_KC.set(p, 0);
                p.getPacketSender().fadeOut();
                event.delay(2);
                p.getMovement().teleport(3341, 4042, 0);
                event.delay(2);
                p.getPacketSender().fadeIn();
                p.sendMessage("<col=FF0000>You enter the portal, and arrive in Justiciar Zachariah's lair..");
            });
        } else {
            p.sendMessage("You need <col=FF0000>20 Saradomin </col>killcount to enter Justiciar Zachariah's lair.");
            p.sendMessage("Your killcount is currently: <col=FF0000>"+Config.GWD_SARADOMIN_KC.get(p));
        }
    }
    public static void selectDerwen(Player p) {
        if (Config.GWD_BANDOS_KC.get(p) >= 20) {
            p.addEvent(event -> {
                Config.GWD_BANDOS_KC.set(p, 0);
                p.getPacketSender().fadeOut();
                event.delay(2);
                p.getMovement().teleport(3375, 4042, 0);
                event.delay(2);
                p.getPacketSender().fadeIn();
                p.sendMessage("<col=FF0000>You enter the portal, and arrive in Derwen's lair..");
            });
        } else {
            p.sendMessage("You need <col=FF0000>20 Bandos </col>killcount to enter Derwen's lair.");
            p.sendMessage("Your killcount is currently: <col=FF0000>"+Config.GWD_BANDOS_KC.get(p));
        }
    }
    public static void selectPorazdir(Player p) {
        if (Config.GWD_ZAMORAK_KC.get(p) >= 20) {
            p.addEvent(event -> {
                Config.GWD_ZAMORAK_KC.set(p, 0);
                p.getPacketSender().fadeOut();
                event.delay(2);
                p.getMovement().teleport(3344, 4079, 0);
                event.delay(2);
                p.getPacketSender().fadeIn();
                p.sendMessage("<col=FF0000>You enter the portal, and arrive in Porazdir's lair..");
            });
        } else {
            p.sendMessage("You need <col=FF0000>20 Zamorak </col>killcount to enter Porazdir's lair.");
            p.sendMessage("Your killcount is currently: <col=FF0000>"+Config.GWD_ZAMORAK_KC.get(p));
        }
    }

    public static void justiciarExitPortal(Player p) {
        p.addEvent(event -> {
            p.getPacketSender().fadeOut();
            event.delay(2);
            p.getMovement().teleport(3308, 4057, 0);
            event.delay(2);
            p.getPacketSender().fadeIn();
            p.sendMessage("<col=FF0000>You exit Justiciar Zachariah's lair");
        });
    }
    public static void derwenExitPortal(Player p) {
        p.addEvent(event -> {
            p.getPacketSender().fadeOut();
            event.delay(2);
            p.getMovement().teleport(3308, 4057, 0);
            event.delay(2);
            p.getPacketSender().fadeIn();
            p.sendMessage("<col=FF0000>You exit Derwen's lair");
        });
    }
    public static void porazdirExitPortal(Player p) {
        p.addEvent(event -> {
            p.getPacketSender().fadeOut();
            event.delay(2);
            p.getMovement().teleport(3308, 4057, 0);
            event.delay(2);
            p.getPacketSender().fadeIn();
            p.sendMessage("<col=FF0000>You exit Porazdir's lair");
        });
    }
}