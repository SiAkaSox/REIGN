package io.ruin.model.map.object.actions.impl.prifddinas.amlodddistrict.gauntlet;

import io.ruin.model.content.accountperks.Perk;
import io.ruin.model.content.accountperks.PerkManager;
import io.ruin.utility.Random;
import io.ruin.cache.Color;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;

import java.text.NumberFormat;

public class NormalChestRewards {

    private static final Item[] SUPER_RARE_LOOT = {
            new Item(25859, 1), //Enhanced seed
            new Item(23757, 1), //Youngllef pet
    };

    private static final Item[] RARE_LOOT = {
            new Item(23956, 1), //Crystal armour seed
            new Item(4207, 1), //Crystal weapon seed
    };

    private static final Item[][] LOOTS = {

            new Item[]{
                    new Item(12073, 1) //Elite clue scroll
            },
            new Item[]{
                    new Item(995, Random.get(20000, 80000) * 2) //coins
            },
            new Item[]{
                    new Item(1392, Random.get(6, 10) * 2) //Battlestaff
            },
            new Item[]{
                    new Item(1618, Random.get(7, 12) * 2) //Uncut diamond
            },
            new Item[]{
                    new Item(1620, Random.get(12, 30) * 2) //Uncut ruby
            },
            new Item[]{
                    new Item(1622, Random.get(20, 60) * 2) //Uncut emerald
            },
            new Item[]{
                    new Item(1624, Random.get(30, 70) * 2) //Uncut sapphire
            },
            new Item[]{
                    new Item(11212, Random.get(50, 115) * 2) //Dragon arrow
            },
            new Item[]{
                    new Item(892, Random.get(250, 350) * 2) //Rune arrow
            },
            new Item[]{
                    new Item(890, Random.get(500, 700) * 2) //Adamant arrow
            },
            new Item[]{
                    new Item(888, Random.get(900, 1300) * 2) //Mithril arrow
            },
            new Item[]{
                    new Item(565, Random.get(100, 160) * 2) //Blood rune
            },
            new Item[]{
                    new Item(560, Random.get(120, 180) * 2) //Death rune
            },
            new Item[]{
                    new Item(562, Random.get(210, 340) * 2) //Chaos rune
            },
            new Item[]{
                    new Item(563, Random.get(120, 180) * 2) //Law rune
            },
            new Item[]{
                    new Item(561, Random.get(140, 180) * 2) //Nature rune
            },
            new Item[]{
                    new Item(564, Random.get(200, 280) * 2) //Cosmic rune
            },
            new Item[]{
                    new Item(3204, Random.get(1, 2)) //Dragon halberd
            },
            new Item[]{
                    new Item(1275, Random.get(2, 4)) //Rune pickaxe
            },
            new Item[]{
                    new Item(3202, Random.get(2, 4)) //Rune Halberd
            },
            new Item[]{
                    new Item(1093, Random.get(2, 4)) //Rune plateskirt
            },
            new Item[]{
                    new Item(1113, Random.get(2, 4)) //Rune chainbody
            },
            new Item[]{
                    new Item(1163, Random.get(4, 8)) //Rune full helm
            },
            new Item[]{
                    new Item(1149, Random.get(2, 4)) //Dragon med helm
            },
            new Item[]{
                    new Item(1305, Random.get(2, 4)) //Dragon longsword
            },
            new Item[]{
                    new Item(4087, Random.get(2, 4)) //Dragon platelegs
            },
            new Item[]{
                    new Item(4585, Random.get(2, 4)) //Dragon plateskirt
            },
    };

    static {
        
            final int[] CHEST = {36087, 37341};
            for (int chest : CHEST) {
                ObjectAction.register(chest, 1, (player, obj) -> {
                    if (Config.GAUNTLET_REWARD.get(player) == 0) {
                        player.sendFilteredMessage("You have no rewards to collect.");
                        return;
                    }
                    player.startEvent(event -> {
                        player.lock();
                        player.privateSound(51);
                        player.animate(536);
                        Config.GAUNTLET_REWARD.set(player, 0);
                        if (Random.get() <= 0.0015) { //1/750
                            /**
                             * Super Rare loot
                             */
                            Item loot = SUPER_RARE_LOOT[Random.get(SUPER_RARE_LOOT.length - 1)];
                            Item[] normLoot = LOOTS[Random.get(LOOTS.length - 1)];
                            player.getInventory().add(loot.getId(), loot.getAmount());
                            for (Item item : normLoot) {
                                player.getInventory().add(item.getId(), item.getAmount());
                                int lootAmount = item.getAmount() * item.getDef().value;
                                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                                String formattedNumber = numberFormat.format(lootAmount);
                                player.sendMessage(Color.RED.wrap("Valuable drop: " + item.getAmount() + "x " + item.getDef().name + " (" + formattedNumber) + " <col=FF0000>coins)");
                            }
                            int shardamt = PerkManager.isActive(player, Perk.SHINY_ENTHUSIAST) ? Random.get(20, 30) : Random.get(10, 15);
                            player.getInventory().add(23866, shardamt);
                            int lootAmount = loot.getAmount() * loot.getDef().value;
                            NumberFormat numberFormat = NumberFormat.getNumberInstance();
                            String formattedNumber = numberFormat.format(lootAmount);
                            player.sendMessage(Color.RED.wrap("Valuable drop: " + loot.getAmount() + "x " + loot.getDef().name + " (" + formattedNumber) + " <col=FF0000>coins)");
                            Broadcast.GLOBAL.sendNews(player.name() + " just received " + loot.getDef().descriptiveName + " from the Gauntlet Chest!");
                        } else if (Random.get() <= 0.018) {
                            /**
                             * Rare loot
                             */
                            Item loot = RARE_LOOT[Random.get(RARE_LOOT.length - 1)];
                            player.getInventory().add(loot.getId(), loot.getAmount());
                            Item[] normLoot = LOOTS[Random.get(LOOTS.length - 1)];
                            for (Item item : normLoot) {
                                player.getInventory().add(item.getId(), item.getAmount());
                                int lootAmount = item.getAmount() * item.getDef().value;
                                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                                String formattedNumber = numberFormat.format(lootAmount);
                                player.sendMessage(Color.RED.wrap("Valuable drop: " + item.getAmount() + "x " + item.getDef().name + " (" + formattedNumber) + " <col=FF0000>coins)");
                            }
                            int shardamt = PerkManager.isActive(player, Perk.SHINY_ENTHUSIAST) ? Random.get(20, 30) : Random.get(10, 15);
                            player.getInventory().add(23866, shardamt);
                            int lootAmount = loot.getAmount() * loot.getDef().value;
                            NumberFormat numberFormat = NumberFormat.getNumberInstance();
                            String formattedNumber = numberFormat.format(lootAmount);
                            player.sendMessage(Color.RED.wrap("Valuable drop: " + loot.getAmount() + "x " + loot.getDef().name + " (" + formattedNumber) + " <col=FF0000>coins)");
                            Broadcast.GLOBAL.sendNews(player.name() + " just received " + loot.getDef().descriptiveName + " from the Gauntlet Chest!");
                        } else {
                            /**
                             * Regular loot
                             */
                            Item[] loot = LOOTS[Random.get(LOOTS.length - 1)];
                            for (Item item : loot) {
                                player.getInventory().addOrDrop(item.getId(), item.getAmount());
                                int shardamt = PerkManager.isActive(player, Perk.SHINY_ENTHUSIAST) ? Random.get(20, 30) : Random.get(10, 15);
                                player.getInventory().add(23866, shardamt);
                                int lootAmount = item.getAmount() * item.getDef().value;
                                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                                String formattedNumber = numberFormat.format(lootAmount);
                                player.sendMessage(Color.RED.wrap("Valuable drop: " + item.getAmount() + "x " + item.getDef().name + " (" + formattedNumber) + " <col=FF0000>coins)");
                            }
                            Item[] loot2 = LOOTS[Random.get(LOOTS.length - 1)];
                            if (Misc.random(2) == 1) {
                                for (Item item2 : loot2) {
                                    player.getInventory().addOrDrop(item2.getId(), item2.getAmount());
                                    int lootAmount = item2.getAmount() * item2.getDef().value;
                                    NumberFormat numberFormat = NumberFormat.getNumberInstance();
                                    String formattedNumber = numberFormat.format(lootAmount);
                                    player.sendMessage(Color.RED.wrap("Valuable drop: " + item2.getAmount() + "x " + item2.getDef().name + " (" + formattedNumber) + " <col=FF0000>coins)");
                                }
                            }
                        }
                        event.delay(1);
                        player.unlock();
                    });
                });
            }
    }

}