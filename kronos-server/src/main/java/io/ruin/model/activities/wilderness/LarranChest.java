package io.ruin.model.activities.wilderness;

import io.ruin.utility.Random;
import io.ruin.model.achievements.listeners.hard.KeyHolder;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Adam Ali ("Kal-El") https://www.rune-server.ee/members/kal+el/
 */
public class LarranChest {

    private static final int CHEST = 34829;

    private static final int LARRAN_KEY_ID = 23490;

    static {
        ObjectAction.register(CHEST, 1, ((player, obj) -> openChest(player))); //TODO: Add options to object def
    }

    private static void openChest(Player player) {
        Item larrenKey = player.getInventory().findItem(LARRAN_KEY_ID);

        if (larrenKey == null) {
            player.sendFilteredMessage("You need a key to open this chest");
            return;
        }

        KeyHolder.complete(player);
        player.startEvent(event -> {
            player.lock();
            player.sendFilteredMessage("You unlock the chest with your key");
            larrenKey.remove(1);
            player.animate(536);
            Item loot = new LarranChestLoot().rollItem();
            player.getInventory().addOrDrop(loot);
            int amount = Random.get(10, 25);
            player.getInventory().addOrDrop(13307, amount);
            event.delay(1);
            player.unlock();
        });
    }
}
