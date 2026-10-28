package io.ruin.model.activities.wilderness.pvp;

import io.ruin.utility.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import lombok.Getter;

public class SpoilsOfWar {

    static {
        ItemAction.registerInventory(25342, "Open", SpoilsOfWar::open);
    }

    @Getter
    private static final LootTable slayerCasketTable = new LootTable().addTable(1,
            new LootItem(1187, 1, 50), //dragon sq
            new LootItem(995, 100000, 50), //coins
            new LootItem(4586, 2, 45), //dragon skirt
            new LootItem(4088, 2, 45), //dragon platelegs
            new LootItem(12831, 1, 45), //blessed spirit shield
            new LootItem(11840, 1, 40), //dragon boots
            new LootItem(6920, 1, 40), //infinity boots
            new LootItem(23991, 1, 40), //crystal shield
            new LootItem(2513, 1, 35), //dragon chainbody
            new LootItem(6735, 1, 35), //warrior ring
            new LootItem(6737, 1, 35), //berserker ring
            new LootItem(6733, 1, 35), //archers ring
            new LootItem(6731, 1, 35), //seers ring
            new LootItem(6585, 1, 30), //fury
            new LootItem(12004, 1, 30), //kraken tentacle
            new LootItem(11924, 1, 25), //malediction ward
            new LootItem(11926, 1, 25), //odium ward
            new LootItem(11230, Random.get(225, 300), 25), //dragon darts
            new LootItem(11212, Random.get(225, 300), 25), //dragon arrows
            new LootItem(995, 500000, 20), //500k coins
            new LootItem(13307, 50, 20), //pkp ticket
            new LootItem(11908, 1, 20), //Uncharged trident
            new LootItem(22296, 1, 15), //SoL
            new LootItem(995, Random.get(2500000), 10), //2.5m coins
            new LootItem(12929, 1, 8), //Serp helm
            new LootItem(11832, 1, 6), //BCP
            new LootItem(11834, 1, 6), //tassets
            new LootItem(11830, 1, 6), //arma legs
            new LootItem(11828, 1, 5), //arma chest
            new LootItem(11808, 1, 5), //ZGS
            new LootItem(11806, 1, 5), //SGS
            new LootItem(11804, 1, 5), //BGS
            new LootItem(11802, 1, 3), //AGS
            new LootItem(13652, 1, 1), //dclaws
            new LootItem(12904, 1, 1) //TSOTD
    );

    private static void open(Player player, Item item) {
        player.lock();
        item.remove();
        Item reward;
        reward = getSlayerCasketTable().rollItem();
        player.getInventory().add(reward);
        player.sendMessage("You receive a "+reward.getDef().name+" from the Spoils of War!");
        player.unlock();
    }

}