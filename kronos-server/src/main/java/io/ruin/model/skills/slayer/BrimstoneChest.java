package io.ruin.model.skills.slayer;

import io.ruin.utility.Random;
import io.ruin.model.achievements.listeners.hard.BrimChest;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BrimstoneChest {

    private static final int BRIMSTONE_KEY = 23083;

    private static final Item[] LOOTS = {
            new Item(1618, 25 + Random.get(10)),
            new Item(1620, 25 + Random.get(10)),
            new Item(454, 300 + Random.get(200)),
            new Item(995, 50000 + Random.get(100000)),
            new Item(445, 100 + Random.get(100)),
            new Item(11237, 50 + Random.get(150)),
            new Item(441, 350 + Random.get(150)),
            new Item(1164, 1 + Random.get(3)),
            new Item(1128, 1 + Random.get(2)),
            new Item(1080, 1 + Random.get(6)),
            new Item(360, 100 + Random.get(250)),
            new Item(378, 100 + Random.get(250)),
            new Item(372, 100 + Random.get(200)),
            new Item(7945, 100 + Random.get(200)),
            new Item(384, 100 + Random.get(150)),
            new Item(396, 80 + Random.get(80)),
            new Item(390, 80 + Random.get(80)),
            new Item(452, 9 + Random.get(6)),
            new Item(2354, 300 + Random.get(200)),
            new Item(1514, 120 + Random.get(40)),
            new Item(11232, 40 + Random.get(120)),
            new Item(5289, 1 + Random.get(3)),
            new Item(5316, 1 + Random.get(2)),
            new Item(22869, 1 + Random.get(3)),
            new Item(22877, 1 + Random.get(3)),
            new Item(22871, 1),
            new Item(5304, 2 + Random.get(3)),
            new Item(5300, 2 + Random.get(3)),
            new Item(5295, 2 + Random.get(3)),
            new Item(7937, 3000 + Random.get(3000))
    };

    public static void openChest(Player player){
        int keyCount = player.getInventory().getAmount(BRIMSTONE_KEY);
        if (!player.getInventory().contains(BRIMSTONE_KEY, 1)){
            player.sendMessage("You need a <col=0040ff>Brimstone Key</col> to open this chest!");
        }
        if(keyCount > 0){
            if(player.getInventory().getFreeSlots() == 0 && keyCount > 1){
                player.sendMessage("You don't have enough free slots to do that!");
                return;
            }
            if(player.getInventory().contains(BRIMSTONE_KEY, 1)) {
                Item loot = LOOTS[Random.get(LOOTS.length - 1)];
                player.startEvent(event -> {
                    player.lock();
                    player.animate(536);
                    player.getInventory().addOrDrop(loot);
                    player.getInventory().remove(BRIMSTONE_KEY, 1);
                    player.sendFilteredMessage("You receive <col=8B0000>"+loot.getAmount()+"x " + loot.getDef().name + "</col> from the chest.");
                    BrimChest.complete(player);
                    event.delay(1);
                    player.unlock();
                    player.brimstoneChestsOpened++;
                });
            }
        }
    }

    static {
        ObjectAction.register(34660, 1, (player, obj) -> {
            openChest(player);
        });
        ObjectAction.register(34662, 1, (player, obj) -> {
            openChest(player);
        });
        ObjectAction.register(34660, 2, (player, obj) -> {
            player.sendMessage("You have opened the brimstone chest <col=0040ff>"+player.brimstoneChestsOpened+"</col> times.");
        });
        ObjectAction.register(34662, 2, (player, obj) -> {
            player.sendMessage("You have opened the brimstone chest <col=0040ff>"+player.brimstoneChestsOpened+"</col> times.");
        });
    }

}