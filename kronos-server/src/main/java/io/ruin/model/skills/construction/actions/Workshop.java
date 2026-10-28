package io.ruin.model.skills.construction.actions;

import io.ruin.utility.NumberUtils;
import io.ruin.cache.ItemID;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.actions.impl.ItemBreaking;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Flatpack;
import io.ruin.model.stat.StatType;

import static io.ruin.cache.ItemID.COINS_995;

public class Workshop {

    static {
        ItemObjectAction.register(Buildable.ARMOUR_STAND.getBuiltObjects()[0], (player, item, obj) -> {
            ItemBreaking broken = item.getDef().brokenFrom;
            if (broken == null) {
                player.dialogue(new MessageDialogue("You can't repair that."));
                return;
            }
            int price = (int) ((1d - (player.getStats().get(StatType.Smithing).currentLevel / 200d)) * broken.coinRepairCost);
            player.dialogue(new YesNoDialogue("Repair the item?", "Repairing this item will cost " + NumberUtils.formatNumber(price) + " coins. Continue?", broken.fixedId, 1, () -> {
                if (!player.getInventory().contains(COINS_995, price)) {
                    player.dialogue(new MessageDialogue("Not enough coins in your inventory."));
                    return;
                }
                player.getInventory().remove(COINS_995, price);
                item.setId(broken.fixedId);
            }));
        });

        //Tool stores
        ItemDispenser.register(6786, Tool.SAW, Tool.HAMMER, Tool.CHISEL, Tool.SHEARS);
        ItemDispenser.register(6787, Tool.EMPTY_BUCKET, Tool.KNIFE, Tool.SPADE, Tool.TINDER_BOX);
        ItemDispenser.register(6788, 1757, Tool.GLASSBLOWING_PIPE, Tool.NEEDLE);
        ItemDispenser.register(6789, 1592, 1597, 1595, 11065, 5523, 1599);
        ItemDispenser.register(6790, Tool.RAKE, Tool.SPADE, Tool.TROWEL, Tool.SEED_DIBBER, Tool.WATERING_CAN);

        //Workbenches
        ObjectAction.register(Buildable.WOODEN_WORKBENCH.getBuiltObjects()[0], 1, (player, obj) -> Flatpack.openFlatpackCategories(player, 20));
        ObjectAction.register(Buildable.OAK_WORKBENCH.getBuiltObjects()[0], 1, (player, obj) -> Flatpack.openFlatpackCategories(player, 40));
        ObjectAction.register(Buildable.STEEL_FRAMED_WORKBENCH.getBuiltObjects()[0], 1, (player, obj) -> Flatpack.openFlatpackCategories(player, 60));
        ObjectAction.register(Buildable.BENCH_WITH_VICE.getBuiltObjects()[0], 1, (player, obj) -> Flatpack.openFlatpackCategories(player, 80));
        ObjectAction.register(Buildable.BENCH_WITH_LATHE.getBuiltObjects()[0], 1, (player, obj) -> Flatpack.openFlatpackCategories(player, 99));

        ObjectAction.register(Buildable.CRAFTING_TABLE_2.getBuiltObjects()[0], "Craft", (player, obj) -> makeClockworks(player));
        ObjectAction.register(Buildable.CRAFTING_TABLE_3.getBuiltObjects()[0], "Craft", (player, obj) -> makeClockworks(player));
        ObjectAction.register(Buildable.CRAFTING_TABLE_4.getBuiltObjects()[0], "Craft", (player, obj) -> makeClockworks(player));

    }

    public static void makeClockworks(Player p) {
        if (!p.getInventory().contains(ItemID.STEEL_BAR)) {
            p.sendMessage("You don't have the required materials to make a clockwork!");
            return;
        } else {
            p.startEvent(event -> {
                p.lock();
                p.animate(4109);
                event.delay(2);
                p.getInventory().remove(ItemID.STEEL_BAR, 1);
                p.getInventory().addOrDrop(ItemID.CLOCKWORK, 1);
                p.dialogue(
                        new ItemDialogue().one(8792, "You carefully craft the steel bar into a Clockwork.")
                );
                p.animate(-1);
                p.unlock();
            });
        }
    }


}
