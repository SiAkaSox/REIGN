package io.ruin.model.item.actions.impl.boxes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import lombok.Getter;

public class CrystalJar {

    static {
        ItemAction.registerInventory(23768, "Loot", CrystalJar::open);
    }

    @Getter
    private static final LootTable crystalJar = new LootTable().addTable(1,
            new LootItem(4093, 1, 3),         //Mystic robe bottom
            new LootItem(1705, 3, 3),         //Amulet of glory
            new LootItem(1703, 2, 3),         //Dragonstone amulet
            new LootItem(11212, 100, 250, 3), //Dragon arrow
            new LootItem(9341, 10, 40, 3),    //Dragonstone bolts
            new LootItem(1305, 1, 3),         //Dragon longsword
            new LootItem(5698, 1, 3),         //Dragon dagger(p++)
            new LootItem(11230, 100, 250, 3), //Dragon dart
            new LootItem(9193, 10, 30, 3),    //Dragonstone bolt tips
            new LootItem(1616, 3, 3),         //Dragonstone
            new LootItem(11232, 100, 350, 3), //Dragon dart tip
            new LootItem(11237, 100, 350, 3), //Dragon arrowtips
            new LootItem(19582, 25, 35, 3),   //Dragon javelin heads
            new LootItem(535, 100, 300, 3),   //Babydragon bones
            new LootItem(537, 50, 100, 3),    //Dragon bones
            new LootItem(5316, 1, 3),         //Magic seed
            new LootItem(5300, 6, 3),         //Snapdragon seed
            new LootItem(7219, 15, 3),        //Summer pie
            new LootItem(23956, 1, 1),        //Crystal armour seed
            new LootItem(4207, 1, 1),        //Crystal weapon seed
            new LootItem(25859, 1, 1),        //Enhanced crystal weapon seed
            new LootItem(4093, 1, 3)          //Clue scroll (elite)
    );

    private static void open(Player player, Item item) {
        player.lock();
        item.remove();
        Item reward;
        reward = CrystalJar.getCrystalJar().rollItem();
        player.getInventory().add(reward);
        player.unlock();
    }
}
