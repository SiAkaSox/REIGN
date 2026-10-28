package io.ruin.model.activities.wilderness;

import io.ruin.Server;
import io.ruin.utility.NumberUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

import static io.ruin.cache.ItemID.COINS_995;

public class ResourceArea2 {

    public static final Bounds BOUNDS = new Bounds(3213, 3996, 3228, 4018, 0);

    public static int playerCount;

    private static void gateOpen(Player player, GameObject door) {
        if (player.getAbsY() >= 4019) {
            /*
             * Donator Benefit: [Free resource area entry]
             */
            if (player.isSapphire()) {
                enter(player, door);
            }
            Item currency = player.getInventory().findItem(getCurrency());
            ItemDef wepDef = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
            boolean hasWildernessSword = wepDef != null && wepDef.id == 13111;
            if ((currency == null || currency.getAmount() < getEntryFee()) && !hasWildernessSword) {
                player.sendMessage("You need " + NumberUtils.formatNumber(getEntryFee()) + " " + getCurrencyName() + " to enter the Arena.");
                return;
            }
            if (hasWildernessSword)
                enter(player, currency, door, true);
            else
                player.dialogue(
                        new YesNoDialogue("Are you sure you want to do this?", "Pay " + NumberUtils.formatNumber(getEntryFee()) + " " + getCurrencyName() + " to enter the resource arena?", getCurrency(), getEntryFee(), () -> {
                            enter(player, currency, door, false);
                        })
                );
        } else {
            player.startEvent(event -> {
                if (door.id == -1)
                    return;
                player.lock();
                if (player.getAbsX() != 3223 || player.getAbsY() != 4018) {
                    player.stepAbs(3223, 4018, StepType.FORCE_WALK);
                    event.delay(1);
                }
                openGateEvent(door);
                player.step(0, 1, StepType.FORCE_WALK);
                player.unlock();
            });
        }
    }

    private static void enter(Player player, GameObject door) {
        player.startEvent(event -> {
            if (door.id == -1)
                return;
            player.lock();
            if (player.getAbsX() != 3223 || player.getAbsY() != 4018) {
                player.stepAbs(3223, 4018, StepType.FORCE_WALK);
                event.delay(1);
            }
            player.sendMessage("Your donator rank grants you free access to the resource area.");
            openGateEvent(door);
            player.step(0, -1, StepType.FORCE_WALK);
            player.unlock();
        });
    }

    private static void enter(Player player, Item currency, GameObject door, boolean wildernessSword) {
        player.startEvent(event -> {
            if (door.id == -1)
                return;
            player.lock();
            if (player.getAbsX() != 3223 || player.getAbsY() != 4018) {
                player.stepAbs(3223, 4018, StepType.FORCE_WALK);
                event.delay(1);
            }
            if (wildernessSword) {
                player.sendMessage("Your wilderness sword grants you free access to the resource area.");
            } else {
                currency.remove(getEntryFee());
                player.sendMessage("You pay " + NumberUtils.formatNumber(getEntryFee()) + " " + getCurrencyName() + " and enter the resource arena.");
            }
            openGateEvent(door);
            player.step(0, -1, StepType.FORCE_WALK);
            player.unlock();
        });
    }

    private static int getCurrency() {
        return COINS_995;
    }

    private static int getEntryFee() {
        return 200000;
    }

    private static String getCurrencyName() {
        return "gold coins";
    }


    private static void openGateEvent(GameObject door) {
        World.startEvent(event -> {
            GameObject opened = GameObject.spawn(1548, 3223, 4018, 0, door.type, 4);
            door.skipClipping(true).remove();
            event.delay(2);
            opened.remove();
            door.restore().skipClipping(false);
        });
    }

    private static void gatePeek(Player player) {
        if (player.getAbsY() == 4017) {
            player.sendMessage("All you see is the barren wasteland of the Wilderness.");
            return;
        }
        if (playerCount == 0)
            player.dialogue(new MessageDialogue("You peek inside the gate and see no adventurers inside the area."));
        else
            player.dialogue(new MessageDialogue("You peek inside the gate and see " + playerCount + " adventurers inside the area."));
    }

    private static void entered(Player player) {
        player.resourceArea = true;
        ResourceArea2.playerCount++;
    }

    private static void exited(Player player, boolean logout) {
        player.resourceArea = false;
        ResourceArea2.playerCount--;
    }

    static {
        ObjectAction.register(26760, 3223, 4019, 0, "open", ResourceArea2::gateOpen);
        ObjectAction.register(26760, 3223, 4019, 0, "peek", (player, obj) -> gatePeek(player));
        MapListener.registerBounds(BOUNDS)
                .onEnter(ResourceArea2::entered)
                .onExit(ResourceArea2::exited);
    }

}