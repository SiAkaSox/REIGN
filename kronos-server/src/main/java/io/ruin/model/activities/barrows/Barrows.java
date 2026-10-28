package io.ruin.model.activities.barrows;

import io.ruin.Server;
import io.ruin.model.map.object.actions.impl.Trapdoor;
import io.ruin.utility.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.impl.Spade;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.dungeons.DonatorDungeon;

public class Barrows {

    /**
     * Main area
     */

    private static final Bounds MAIN_BOUNDS = new Bounds(3546, 3267, 3583, 3308, -1);

    private static void enteredMain(Player player) {
        player.openInterface(InterfaceType.PRIMARY_OVERLAY, Interface.BARROWS);
    }

    private static void exitedMain(Player player, boolean logout) {
        if(!logout)
            player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
    }

    static {
        MapListener.registerBounds(MAIN_BOUNDS)
                .onEnter(Barrows::enteredMain)
                .onExit(Barrows::exitedMain);
    }

    /**
     * Crypts area
     */

    private static final Bounds CRYPT_BOUNDS = new Bounds(3520, 9664, 3583, 9727, -1);

    private static void enteredCrypts(Player player) {
        player.openInterface(InterfaceType.PRIMARY_OVERLAY, Interface.BARROWS);

        player.getPacketSender().sendMapState(2);
        player.addEvent(e -> {
            while(player.getPosition().inBounds(CRYPT_BOUNDS)) {
                player.getPrayer().drain(1);
                e.delay(Random.get(5, 10));
            }
        });
    }

    private static void exitedCrypts(Player player, boolean logout) {
        if(!logout) {
            player.getPacketSender().resetCamera();
            player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
            player.getPacketSender().sendMapState(0);
        }
    }

    static {
        MapListener.registerBounds(CRYPT_BOUNDS)
                .onEnter(Barrows::enteredCrypts)
                .onExit(Barrows::exitedCrypts);
    }

    /**
     * Actions
     */

    private static void register(BarrowsBrother brother, int digX, int digY, int cryptX, int cryptY, int stairsId, int sarcophagusId) {
            Spade.registerDig(new Bounds(digX, digY, 0, 3), player -> player.getMovement().teleport(cryptX, cryptY, 3));
            ObjectAction.register(stairsId, "climb-up", (player, obj) -> player.getMovement().teleport(digX, digY, 0));
            ObjectAction.register(sarcophagusId, "search", (player, obj) -> {
                        if (player.getPosition().getRegion().id == 4931) {
                            if (obj.id == 20770) {
                                DonatorDungeon.spawnAhrims(player);
                            }
                            if (obj.id == 20720) {
                                DonatorDungeon.spawnDharok(player);
                            }
                            if (obj.id == 20722) {
                                DonatorDungeon.spawnGuthans(player);
                            }
                            if (obj.id == 20721) {
                                DonatorDungeon.spawnTorag(player);
                            }
                            if (obj.id == 20771) {
                                DonatorDungeon.spawnKaril(player);
                            }
                            if (obj.id == 20772) {
                                DonatorDungeon.spawnVerac(player);
                            }
                        } else {
                            search(player, brother);
                        }
                    }
            );
    }

    private static void search(Player player, BarrowsBrother brother) {
            if (player.barrowsChestBrother == null)
                player.barrowsChestBrother = Random.get(BarrowsBrother.values());
            if (player.barrowsChestBrother == brother) {
                player.dialogue(
                        new MessageDialogue("You've found a hidden tunnel, do you want to enter?"),
                        new OptionsDialogue(
                                new Option("Yeah I'm fearless!", () -> player.getMovement().teleport(3552, 9690, 0)),
                                new Option("No way, that looks scary!", player::closeDialogue)
                        )
                );
                return;
            }
            if (brother.isKilled(player)) {
                player.sendMessage("This sarcophagus appears to be empty.");
                return;
            }
            brother.spawn(player);
    }

    private static void loot(Player p) {
        if(p.barrowsChestBrother == null) {
            p.getPacketSender().resetCamera();
            p.getMovement().teleport(3565, 3313, 0);
            return;
        }
        if(!p.barrowsChestBrother.isKilled(p)) {
            p.barrowsChestBrother.spawn(p);
            return;
        }
        if(Config.BARROWS_CHEST.get(p) == 0) {
            toggleChest(p, true);
            return;
        }
        p.startEvent(e -> {
            p.lock();
            p.animate(535);
            p.getPacketSender().shakeCamera(0,3);
            ItemContainer loot = BarrowsRewards.loot(p);
            p.getPacketSender().sendClientScript(917, "ii", -1, -1);
            p.openInterface(InterfaceType.MAIN, Interface.BARROWS_LOOT);
            p.getPacketSender().sendAccessMask(Interface.BARROWS_LOOT, 3, 0, 8, 1024);
            loot.sendUpdates();
            for(Item item : loot.getItems()) {
                if(item != null) {
                    p.getInventory().addOrDrop(item.getId(), item.getAmount());
                    p.getCollectionLog().checkItemReceived(item);
                }
            }
            p.sendMessage("Your Barrows chest count is: <col=FF0000>" + (++p.barrowsChestsOpened) + "</col>.");
            for(BarrowsBrother brother : BarrowsBrother.values())
                brother.config.set(p, 0);
            Config.BARROWS_CHEST.set(p, 0);
            p.barrowsChestBrother = null;
            p.unlock();
        });
    }

    private static void toggleChest(Player player, boolean open) {
        player.animate(535);
        Config.BARROWS_CHEST.set(player, open ? 1 : 0);
    }

    static {
            register(BarrowsBrother.AHRIM, 3565, 3289, 3557, 9703, 20667, 20770);
            register(BarrowsBrother.DHAROK, 3576, 3298, 3556, 9718, 20668, 20720);
            register(BarrowsBrother.GUTHAN, 3578, 3283, 3534, 9704, 20669, 20722);
            register(BarrowsBrother.KARIL, 3566, 3276, 3546, 9684, 20670, 20771);
            register(BarrowsBrother.TORAG, 3554, 3283, 3568, 9683, 20671, 20721);
            register(BarrowsBrother.VERAC, 3557, 3298, 3578, 9706, 20672, 20772);
            ObjectAction.register(20973, 1, (p, obj) -> loot(p));
            ObjectAction.register(20973, 2, (p, obj) -> toggleChest(p, false));
    }

}
