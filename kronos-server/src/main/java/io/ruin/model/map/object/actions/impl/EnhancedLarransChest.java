package io.ruin.model.map.object.actions.impl;

import io.ruin.Server;
import io.ruin.utility.Random;
import io.ruin.model.World;
import io.ruin.model.achievements.listeners.hard.SuperiorKiller;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;

public class EnhancedLarransChest {

    private static final Item[] LOOTS = {

                    new Item(995, Random.get(100000, 200000)),
                    new Item(533, Random.get(200, 600)),
                    new Item(537, Random.get(200, 600)),
                    new Item(11237, Random.get(200, 500)),
                    new Item(1216, Random.get(2, 6)),
                    new Item(386, Random.get(100, 500)),
                    new Item(12696, Random.get(100, 300)),
                    new Item(13442, Random.get(250, 400)),
                    new Item(1514, Random.get(400, 1200)),
                    new Item(5295, Random.get(10, 30)),
                    new Item(392, Random.get(400, 800)),
                    new Item(11232, Random.get(200, 800)),
                    new Item(452, Random.get(50, 200))

    };

    private static final Item[] RARE_LOOT = {

            new Item(22613, 1),
            new Item(12904, 1),
            new Item (4151, 1),
            new Item (11806, 1),
            new Item (11920, 1),
            new Item (11926, 1),
            new Item (11924, 1),
            new Item(22616, 1),
            new Item(22619, 1),
            new Item(24780, 1)

    };


    static {

            ObjectAction.register(34875, 1, (player, obj) -> {
                Item enhancedlarranskey = player.getInventory().findItem(30138);
                if (enhancedlarranskey == null) {
                    player.sendFilteredMessage("You need an Enhanced Larran's Key to open this chest.");
                    return;
                }
                player.startEvent(event -> {
                    player.lock();
                    player.sendFilteredMessage("You unlock the chest with your key..");
                    player.sendFilteredMessage("You have been skulled and teleblocked while opening the chest!");
                    player.getCombat().skullNormal();
                    player.getCombat().teleblock();
                    enhancedlarranskey.remove(1);
                    player.privateSound(51);
                    player.animate(536);
                    SuperiorKiller.complete(player);
                    int amount = Random.get(15, 35);
                    player.getInventory().addOrDrop(13307, amount);
                    World.startEvent(e -> {
                        obj.setId(34876);
                        e.delay(2);
                        obj.setId(obj.originalId);
                    });
                    if (Random.rollPercent(5)) {
                        /**
                         * Rare loot
                         */
                        Item loot = RARE_LOOT[Random.get(RARE_LOOT.length - 1)];
                        player.getInventory().add(loot.getId(), loot.getAmount());
                        Broadcast.GLOBAL.sendLoot(player, player.name() + " has received a rare item from the Enhanced Larran's Chest!");

                    } else {
                        /**
                         * Regular loot
                         */
                        Item loot = LOOTS[Random.get(LOOTS.length - 1)];
                        player.getInventory().addOrDrop(loot.getId(), loot.getAmount());
                    }
                    event.delay(1);
                    player.unlock();
                });
            });
    }
}