package io.ruin.model.item.actions.impl.boxes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;

public class UpdateBox {

    //26915

    static {
        ItemAction.registerInventory(26915, 1, (player, item) -> open(player, item));
    }

    public static void open(Player player, Item item) {
        if (!player.getInventory().contains(item))
            return;
        player.getInventory().remove(item);
        Item reward = roll(player);
        if (reward != null) {
            int dialogueId = reward.getId();
            if (reward.getDef().isNote())
                dialogueId = reward.getDef().notedId;
            player.dialogue(new ItemDialogue().one(dialogueId, "As you open the box..<br>.. you find "+reward.getAmount()+"x "+reward.getDef().name+""));
            player.getInventory().addOrDrop(reward);
        }
    }

    private static Item roll(Player player) {
        double chance = Math.random();
        Item reward = null;
        boolean announce = false;
        Item[] rares = new Item[] {
                new Item(1464, Misc.random(1, 6)),//vote tickets

        };
        Item[] commons = new Item[] {
                new Item(995, Misc.random(50_000, 250_000)),//coins
                new Item(1333, 1),//rune scimitar
                new Item(12696, Misc.random(5, 35)),//super combat potion noted
                new Item(3025, Misc.random(10, 30)),//super restore
                new Item(6686, Misc.random(10, 35)),//saradomin brew
                new Item(2435, Misc.random(10, 30)),//prayer potion
                new Item(13442, Misc.random(30, 90)),//angler

        };

        if (chance <= .02) {//1/50
            announce = true;
            reward = rares[Misc.random(rares.length - 1)];
        } else {
            reward = commons[Misc.random(commons.length - 1)];
        }

        double chance2 = Math.random();

        if (chance2 <= 0.004) {//1/250
            player.sendMessage("You hit the very rare loot table and receive..");
            int reward_loot = Misc.random(1);
            switch (reward_loot) {
                case 0: {
                    int creditAmount = Misc.random(3, 7);
                    player.storeAmountSpent += creditAmount;
                    player.sendMessage("... "+creditAmount+"$ towards your donator rank!");
                    break;
                }
                case 1: {
                    player.getInventory().addOrDrop(new Item(6199, 1));
                    player.sendMessage("... A mystery box!");
                    break;
                }
            }
        }

        if (announce) {
            Broadcast.GLOBAL.sendLoot(player, player.name() + " has received "+Misc.formatNumber(reward.getAmount())+"x "+reward.getDef().name+" (Rare) from an Update Rewards Box.");
        }

        return reward;
    }
}
