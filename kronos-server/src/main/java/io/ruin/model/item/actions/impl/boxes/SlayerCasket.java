package io.ruin.model.item.actions.impl.boxes;

import io.ruin.utility.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import lombok.Getter;

public class SlayerCasket {

    static {
        ItemAction.registerInventory(23071, "Open", SlayerCasket::open);
    }

    @Getter
    private static final LootTable slayerCasketTable = new LootTable().addTable(1,
            new LootItem(995, Random.get(35000, 50000), 50),
            new LootItem(13442, Random.get(25, 50), 32), //Anglerfish
            new LootItem(990, 2, 26), //2 crystal key
            new LootItem(12696, Random.get(8), 45), //Super combats
            new LootItem(11230, Random.get(30, 60), 31), //dragon darts
            new LootItem(11212, Random.get(30, 60), 32), //dragon arrows
            new LootItem(8901, 1, 15), //black mask
            new LootItem(995, Random.get(25000, 75000), 27),
            new LootItem(12696, Random.get(5, 12), 36), //Super combats
            new LootItem(23951, 1, 25), //Enhanced crystal key
            new LootItem(786, 1, 35), //Skip slayer task scroll
            new LootItem(11840, 1, 14), //Dragon boots
            new LootItem(6920, 1, 22), //infinity boots
            new LootItem(6585, 1, 10), //Fury
            new LootItem(11235, 1, 30), //Dark bow
            new LootItem(4151, 1, 8), //Whip
            new LootItem(12004, 1, 1), //Kraken Tentacle
            new LootItem(4716, 1, 5), //Dharok's helm
            new LootItem(4720, 1, 5), //Dharok's platebody
            new LootItem(4722, 1, 5), //Dharok's platelegs
            new LootItem(4718, 1, 5), //Dharok's greataxe
            new LootItem(4732, 1, 5), //Karil's helm
            new LootItem(4734, 1, 5), //Karil's platebody
            new LootItem(4736, 1, 5), //Karil's platelegs
            new LootItem(4738, 1, 5), //Karil's greataxe
            new LootItem(4708, 1, 5), //Ahrim's helm
            new LootItem(4710, 1, 5), //Ahrim's platebody
            new LootItem(4712, 1, 5), //Ahrim's platelegs
            new LootItem(4714, 1, 5) //Ahrim's greataxe
    );

    private static void open(Player player, Item item) {
        player.lock();
        item.remove();
        Item reward;
        reward = getSlayerCasketTable().rollItem();
        player.getInventory().add(reward);
        player.sendMessage("You receive a "+reward.getDef().name+" from the slayer casket!");
        player.dialogue(
                new ItemDialogue().one(reward.getId(), "You receive a "+reward.getDef().name+" from the slayer casket!")
        );
        player.unlock();
    }
}
