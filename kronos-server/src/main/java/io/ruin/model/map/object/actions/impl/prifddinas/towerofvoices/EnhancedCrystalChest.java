package io.ruin.model.map.object.actions.impl.prifddinas.towerofvoices;

import io.ruin.Server;
import io.ruin.utility.Random;
import io.ruin.model.World;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Broadcast;

public class EnhancedCrystalChest {

    private static final Item[] SUPER_RARE_LOOT = {
            /**
             * Uncut Onyx
            */
            new Item(25865, 1),//Bowfa
    };

    private static final Item[] RARE_LOOT = {
            /**
             * Dragonstone Armour
            */
            new Item(24034, 1), //helm
            new Item(24037, 1), //body
            new Item(24040, 1), //legs
            new Item(24043, 1), //boots
            new Item(24046, 1), //gauntlets
            new Item(23956, 1), //Crystal armor seed
            new Item(6571, 1), //Uncut onyx
    };

    private static final Item[][] LOOTS = {
            /**
             * Possibility 3 - Crystal key halves part 1
            */
            new Item[]{
                    new Item(995, Random.get(50000,100000)),
                    new Item(985, 1)
            },
            /**
             * Possibility 3 - Crystal key halves part 2
            */
            new Item[]{
                    new Item(995, Random.get(50000,100000)),
                    new Item(987, 1)
            },
            /**
             * Possibility 4 - Uncut gems
            */
            new Item[]{
                    new Item(1620, 13),
                    new Item(1618, 8)
            },
            /**
             * Possibility 5 - Crystal key
            */
            new Item[]{
                    new Item(989, 1)
            },
            /**
             * Possibility 6 - Coins
            */
            new Item[]{
                    new Item(995, Random.get(75000,125000)),
                    new Item(23866, 15)
            },
            /**
             * Possibility 7 - Crystal shards
            */
            new Item[]{
                    new Item(23866, 40)
            },
            /**
             * Possibility 8 - Rune platelegs or plateskirt
            */
            new Item[]{
                    new Item(1080, 2),
                    new Item(1094, 2),
                    new Item(23866, 10)
            },
            /**
             * Possibility 9 - Runes
            */
            new Item[]{
                    new Item(563, 2500),
                    new Item(562, 2500),
                    new Item(561, 2500),
                    new Item(560, 2500),
                    new Item(559, 3000),
                    new Item(558, 3000),
                    new Item(557, 3000),
                    new Item(556, 3000),
                    new Item(555, 3000),
                    new Item(554, 3000)
            },
            /**
             * Possibility 10 - Yew seed
            */
            new Item[]{
                    new Item(5315, 2)
            },
            /**
             * Possibility 11 - Raw sharks
            */
            new Item[]{
                    new Item(384, 100)
            },
            /**
             * Possibility 12 - Gold ores
            */
            new Item[]{
                    new Item(1620, 100),
                    new Item(1618, 100)
            },
            /**
             * Possibility 13 - Runite ores
            */
            new Item[]{
                    new Item(452, 10)
            },
            /**
             * Possibility 14 - Crystal acorn
            */
            new Item[]{
                    new Item(23661, 2)
            },
            /**
             * Possibility 15 - Dragon items - platelegs
            */
            new Item[]{
                    new Item(4087, 1),
            },
            /**
             * Possibility 15 - Dragon items - plateskirt
            */
            new Item[]{
                    new Item(4585, 1)
            },
            /**
             * Possibility 15 - Dragon items - shield left half
            */
            new Item[]{
                    new Item(2366, 1)
            },
    };

    static {

            ItemAction.registerInventory(23946, "lletya", (player, item) -> {
                Position position = new Position(2343, 3172, 0);
                player.resetActions(true, true, true);
                player.lock(LockType.FULL_NULLIFY_DAMAGE); //keep lock outside of event!
                player.startEvent(e -> {
                    player.getPacketSender().fadeOut();
                    e.delay(1);
                    player.getMovement().teleport(position);
                    player.getPacketSender().clearFade();

                    PlayerCounter.TELEPORT_PORTAL_USES.increment(player, 1);
                    player.getPacketSender().fadeIn();
                    player.unlock();
                });
            });
            ItemAction.registerInventory(23946, "prifddinas", (player, item) -> {
                Position position = new Position(3263, 6077, 0);
                player.resetActions(true, true, true);
                player.lock(LockType.FULL_NULLIFY_DAMAGE); //keep lock outside of event!
                player.startEvent(e -> {
                    player.getPacketSender().fadeOut();
                    e.delay(1);
                    player.getMovement().teleport(position);
                    player.getPacketSender().clearFade();

                    PlayerCounter.TELEPORT_PORTAL_USES.increment(player, 1);
                    player.getPacketSender().fadeIn();
                    player.unlock();
                });
            });
            ItemItemAction.register(989, 23866, (player, primary, secondary) -> {
                if (!player.getInventory().contains(23866, 10)) {
                    player.sendMessage("You need at least 10 crystal shards to do this!");
                    return;
                }
                if (player.isLocked() || player.isStunned())
                    return;
                if (player.getStats().get(StatType.Smithing).currentLevel < 80 && player.getStats().get(StatType.Crafting).currentLevel < 80) {
                    player.sendFilteredMessage("You need to be level 80 in both smithing and crafting to do this.");
                    return;
                }
                player.startEvent(e -> {
                    player.lock();
                    e.delay(1);
                    player.getStats().addXp(StatType.Smithing, 500, true);
                    player.getStats().addXp(StatType.Crafting, 500, true);
                    player.getInventory().remove(23866, 10);
                    player.getInventory().remove(989, 1);
                    player.getInventory().addOrDrop(23951, 1);
                    player.sendFilteredMessage("You combine the crystal key and 10 crystal shards to create an enhanced key.");
                    player.unlock();
                });
            });
            ObjectAction.register(36582, 1, (player, obj) -> {
                Item enchancedKey = player.getInventory().findItem(23951);
                if (!player.getInventory().contains(23951)) {
                    player.sendFilteredMessage("You need an <col=0040ff>Enhanced Crystal Key</col> to open this chest.");
                    return;
                }
                if (enchancedKey != null) {
                    player.startEvent(event -> {
                        player.lock();
                        player.sendFilteredMessage("You unlock the chest with your key.");
                        enchancedKey.remove();
                        if (player.storeAmountSpent >= 100 && player.storeAmountSpent < 250 && Random.rollPercent(25)) {
                            player.getInventory().addOrDrop(23951, 1);
                            player.sendMessage("Your donator rank allowed you to keep your enhanced crystal key!");
                        }
                        if (player.storeAmountSpent >= 250 && player.storeAmountSpent < 500 && Random.rollPercent(30)) {
                            player.getInventory().addOrDrop(23951, 1);
                            player.sendMessage("Your donator rank allowed you to keep your enhanced crystal key!");
                        }
                        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000 && Random.rollPercent(35)) {
                            player.getInventory().addOrDrop(23951, 1);
                            player.sendMessage("Your donator rank allowed you to keep your enhanced crystal key!");
                        }
                        if (player.storeAmountSpent >= 1000 && Random.rollPercent(40)) {
                            player.getInventory().addOrDrop(23951, 1);
                            player.sendMessage("Your donator rank allowed you to keep your enhanced crystal key!");
                        }
                        player.privateSound(51);
                        player.animate(536);
                        World.startEvent(e -> {
                            obj.setId(36581);
                            e.delay(2);
                            obj.setId(obj.originalId);
                        });
                        if (Random.get() <= 0.004) { //1/250
                            /**
                             * Super Rare loot
                             */
                            Item loot = SUPER_RARE_LOOT[Random.get(SUPER_RARE_LOOT.length - 1)];
                            player.getInventory().add(loot.getId(), loot.getAmount());
                            player.getCollectionLog().checkItemReceived(loot);
                            Broadcast.GLOBAL.sendNews(player.name() + " just received " + loot.getDef().descriptiveName + " from the Elven Crystal Chest!");
                        } else if (Random.get() <= 0.025) { //idk even know how this one works lol.
                            /**
                             * Rare loot
                             */
                            Item loot = RARE_LOOT[Random.get(RARE_LOOT.length - 1)];
                            player.getInventory().add(loot.getId(), loot.getAmount());
                            player.getCollectionLog().checkItemReceived(loot);
                            Broadcast.GLOBAL.sendNews(player.name() + " just received " + loot.getDef().descriptiveName + " from the Elven Crystal Chest!");
                        } else {
                            /**
                             * Regular loot
                             */
                            Item[] loot = LOOTS[Random.get(LOOTS.length - 1)];
                            for (Item item : loot) {
                                player.getInventory().addOrDrop(item.getId(), item.getAmount());
                                player.getCollectionLog().checkItemReceived(item);
                            }
                        }
                        event.delay(1);
                        player.unlock();
                    });
                }
            });
            ItemObjectAction.register(23951, 36582, (player, item, obj) -> {
                Item enchancedKey = player.getInventory().findItem(23951);
                if (!player.getInventory().contains(23951)) {
                    player.sendFilteredMessage("You need an <col=0040ff>Enhanced Crystal Key</col> to open this chest.");
                    return;
                }
                if (enchancedKey != null) {
                    player.startEvent(event -> {
                        player.lock();
                        player.sendFilteredMessage("You unlock the chest with your key.");
                        enchancedKey.remove();
                        if (player.storeAmountSpent >= 100 && player.storeAmountSpent < 250 && Random.rollPercent(25)) {
                            player.getInventory().addOrDrop(23951, 1);
                            player.sendMessage("Your donator rank allowed you to keep your enhanced crystal key!");
                        }
                        if (player.storeAmountSpent >= 250 && player.storeAmountSpent < 500 && Random.rollPercent(30)) {
                            player.getInventory().addOrDrop(23951, 1);
                            player.sendMessage("Your donator rank allowed you to keep your enhanced crystal key!");
                        }
                        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000 && Random.rollPercent(35)) {
                            player.getInventory().addOrDrop(23951, 1);
                            player.sendMessage("Your donator rank allowed you to keep your enhanced crystal key!");
                        }
                        if (player.storeAmountSpent >= 1000 && Random.rollPercent(40)) {
                            player.getInventory().addOrDrop(23951, 1);
                            player.sendMessage("Your donator rank allowed you to keep your enhanced crystal key!");
                        }
                        player.privateSound(51);
                        player.animate(536);
                        World.startEvent(e -> {
                            obj.setId(36581);
                            e.delay(2);
                            obj.setId(obj.originalId);
                        });
                        if (Random.get() <= 0.004) { //1/250
                            /**
                             * Super Rare loot
                             */
                            Item loot = SUPER_RARE_LOOT[Random.get(SUPER_RARE_LOOT.length - 1)];
                            player.getInventory().add(loot.getId(), loot.getAmount());
                            Broadcast.GLOBAL.sendNews(player.name() + " just received " + loot.getDef().descriptiveName + " from the Elven Crystal Chest!");
                        } else if (Random.get() <= 0.025) { //idk even know how this one works lol.
                            /**
                             * Rare loot
                             */
                            Item loot = RARE_LOOT[Random.get(RARE_LOOT.length - 1)];
                            player.getInventory().add(loot.getId(), loot.getAmount());
                            Broadcast.GLOBAL.sendNews(player.name() + " just received " + loot.getDef().descriptiveName + " from the Elven Crystal Chest!");
                        } else {
                            /**
                             * Regular loot
                             */
                            Item[] loot = LOOTS[Random.get(LOOTS.length - 1)];
                            for (Item newitem : loot)
                                player.getInventory().addOrDrop(newitem.getId(), newitem.getAmount());
                        }
                        event.delay(1);
                        player.unlock();
                    });
                }
            });

            ObjectAction.register(37342, 1, (player, obj) -> {
                Item enchancedKey = player.getInventory().findItem(23951);
                if (enchancedKey == null) {
                    player.sendFilteredMessage("You need a Enhanced Crystal key to open this chest.");
                    return;
                }
                player.startEvent(event -> {
                    player.lock();
                    player.sendFilteredMessage("You unlock the chest with your key.");
                    enchancedKey.remove();
                    player.privateSound(51);
                    player.animate(536);
                    World.startEvent(e -> {
                        obj.setId(36581);
                        e.delay(2);
                        obj.setId(obj.originalId);
                    });
                    enchancedKey.setId(1631);
                    if (Random.get() <= 0.004) { //1/250
                        /**
                         * Super Rare loot
                         */
                        Item loot = SUPER_RARE_LOOT[Random.get(SUPER_RARE_LOOT.length - 1)];
                        player.getInventory().add(loot.getId(), loot.getAmount());
                        player.getCollectionLog().checkItemReceived(loot);
                        Broadcast.GLOBAL.sendNews(player.name() + " just received " + loot.getDef().descriptiveName + " from the Elven Crystal Chest!");
                    } else if (Random.get() <= 0.025) { //idk even know how this one works lol.
                        /**
                         * Rare loot
                         */
                        Item loot = RARE_LOOT[Random.get(RARE_LOOT.length - 1)];
                        player.getInventory().add(loot.getId(), loot.getAmount());
                        player.getCollectionLog().checkItemReceived(loot);
                        Broadcast.GLOBAL.sendNews(player.name() + " just received " + loot.getDef().descriptiveName + " from the Elven Crystal Chest!");
                    } else {
                        /**
                         * Regular loot
                         */
                        Item[] loot = LOOTS[Random.get(LOOTS.length - 1)];
                        for (Item item : loot) {
                            player.getInventory().addOrDrop(item.getId(), item.getAmount());
                            player.getCollectionLog().checkItemReceived(item);
                        }
                    }
                    event.delay(1);
                    player.unlock();
                });
            });
    }
}
