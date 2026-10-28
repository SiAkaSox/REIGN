package io.ruin.model.inter.greatvaultrewards;

import io.ruin.utility.Random;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.utility.Broadcast;

public class GlodChestRewards {

    private static final LootTable LOW_COUNT_REWARDS = new LootTable().addTable(1,
            new LootItem(995, 125000, 50),//125k coins
            new LootItem(990, 2, 40),//crystal key
            new LootItem(995, 250000, 35),//250k coins
            new LootItem(11840, 1, 32),//d boots
            new LootItem(4088, 2, 32),//noted dragon platelegs
            new LootItem(13442, Random.get(50, 75), 30),//noted anglers
            new LootItem(22450, Random.get(10, 22), 30),//battlemage potions
            new LootItem(22462, Random.get(10, 22), 30),//bastion potions
            new LootItem(12696, Random.get(10, 22), 30),//super combats
            new LootItem(13307, 75, 25),//pkp tickets
            new LootItem(23389, 1, 25),//spied manacles
            new LootItem(4716, 1, 24),//dh helm
            new LootItem(4720, 1, 24),//dh body
            new LootItem(4722, 1, 24),//dh legs
            new LootItem(4718, 1, 24),//dh axe
            new LootItem(4712, 1, 24),//ahrims top
            new LootItem(4714, 1, 24),//ahrims skirt
            new LootItem(4732, 1, 24),//karils coif
            new LootItem(4736, 1, 24),//karils top
            new LootItem(4738, 1, 24),//karils skirt
            new LootItem(11235, 1, 22),//dark bow
            new LootItem(20849, Random.get(15, 25), 20),//dragon thrownaxe
            new LootItem(22804, Random.get(15, 25), 20),//dragon knife
            new LootItem(13307, Random.get(35, 60), 19),//blood money
            new LootItem(13307, Random.get(45, 70), 18),//blood money
            new LootItem(6889, 1, 18),//mage's book
            new LootItem(6914, 1, 17),//master wand
            new LootItem(12883, 1, 17),//karils set
            new LootItem(12881, 1, 17),//ahrims set
            new LootItem(12877, 1, 17),//dh set
            new LootItem(6585, 1, 16),//fury
            new LootItem(6920, 1, 16),//inf boots
            new LootItem(19481, 1, 16),//heavy ballista
            new LootItem(25087, 1, 15), //Rev boost scroll
            new LootItem(607, 1, 14), //Rare drop scroll
            new LootItem(608, 1, 14), //Pet drop scroll
            new LootItem(6758, 1, 13),//bonus xp scrolls
            new LootItem(1464, 4, 12),//vote tickets
            new LootItem(20716, 1, 11),//tome of fire (empty)
            new LootItem(11908, 1, 11),//uncharged trident
            new LootItem(12004, 1, 10),//Kraken tent
            new LootItem(995, 5000000, 9),//5m coins
            new LootItem(13307, 300, 8).broadcast(Broadcast.WORLD),//10 pkp tickets
            new LootItem(11283, 1, 7).broadcast(Broadcast.WORLD),//dfs
            new LootItem(12002, 1, 6).broadcast(Broadcast.WORLD),//occult
            new LootItem(12846, 1, 5).broadcast(Broadcast.WORLD),//target teleport scroll
            new LootItem(12904, 1, 4).broadcast(Broadcast.WORLD),//tsotd
            new LootItem(12929, 1, 3).broadcast(Broadcast.WORLD),//serp helm
            new LootItem(11802, 1, 2).broadcast(Broadcast.WORLD),//AGS
            new LootItem(30276, 1, 2).broadcast(Broadcast.WORLD),//$5 bond
            new LootItem(19493, 1, 1).broadcast(Broadcast.WORLD),//zenyte
            new LootItem(13652, 1, 1).broadcast(Broadcast.WORLD)//d claws
    );

    private static final LootTable HIGH_COUNT_REWARDS = new LootTable().addTable(1,
            new LootItem(995, 125000, 50),//125k coins
            new LootItem(990, 2, 40),//crystal key
            new LootItem(995, 250000, 35),//250k coins
            new LootItem(11840, 1, 32),//d boots
            new LootItem(4088, 2, 32),//noted dragon platelegs
            new LootItem(13442, Random.get(50, 75), 30),//noted anglers
            new LootItem(22450, Random.get(10, 22), 30),//battlemage potions
            new LootItem(22462, Random.get(10, 22), 30),//bastion potions
            new LootItem(12696, Random.get(10, 22), 30),//super combats
            new LootItem(13307, 75, 25),//pkp tickets
            new LootItem(23389, 1, 25),//spied manacles
            new LootItem(4716, 1, 24),//dh helm
            new LootItem(4720, 1, 24),//dh body
            new LootItem(4722, 1, 24),//dh legs
            new LootItem(4718, 1, 24),//dh axe
            new LootItem(4712, 1, 24),//ahrims top
            new LootItem(4714, 1, 24),//ahrims skirt
            new LootItem(4732, 1, 24),//karils coif
            new LootItem(4736, 1, 24),//karils top
            new LootItem(4738, 1, 24),//karils skirt
            new LootItem(11235, 1, 22),//dark bow
            new LootItem(20849, Random.get(15, 25), 20),//dragon thrownaxe
            new LootItem(22804, Random.get(15, 25), 20),//dragon knife
            new LootItem(13307, Random.get(35, 60), 19),//blood money
            new LootItem(13307, Random.get(45, 70), 18),//blood money
            new LootItem(6889, 1, 18),//mage's book
            new LootItem(6914, 1, 17),//master wand
            new LootItem(12883, 1, 17),//karils set
            new LootItem(12881, 1, 17),//ahrims set
            new LootItem(12877, 1, 17),//dh set
            new LootItem(6585, 1, 16),//fury
            new LootItem(6920, 1, 16),//inf boots
            new LootItem(19481, 1, 16),//heavy ballista
            new LootItem(25087, 1, 15), //Rev boost scroll
            new LootItem(607, 1, 14), //Rare drop scroll
            new LootItem(608, 1, 14), //Pet drop scroll
            new LootItem(6758, 1, 13),//bonus xp scrolls
            new LootItem(1464, 4, 12),//vote tickets
            new LootItem(20716, 1, 11),//tome of fire (empty)
            new LootItem(11908, 1, 11),//uncharged trident
            new LootItem(12004, 1, 10),//Kraken tent
            new LootItem(995, 5000000, 9),//5m coins
            new LootItem(13307, 300, 8).broadcast(Broadcast.WORLD),//10 pkp tickets
            new LootItem(11283, 1, 7).broadcast(Broadcast.WORLD),//dfs
            new LootItem(12002, 1, 6).broadcast(Broadcast.WORLD),//occult
            new LootItem(12846, 1, 5).broadcast(Broadcast.WORLD),//target teleport scroll
            new LootItem(12904, 1, 4).broadcast(Broadcast.WORLD),//tsotd
            new LootItem(12929, 1, 3).broadcast(Broadcast.WORLD),//serp helm
            new LootItem(11802, 1, 2).broadcast(Broadcast.WORLD),//AGS
            new LootItem(30276, 1, 2).broadcast(Broadcast.WORLD),//$5 bond
            new LootItem(19493, 1, 1).broadcast(Broadcast.WORLD),//zenyte
            new LootItem(13652, 1, 1).broadcast(Broadcast.WORLD)//d claws
    );

    public static void claimReward(Player player) {
        player.setGlodDamageCounter(0);
        Item reward;
        if (World.players.count() < 50) {
            reward = LOW_COUNT_REWARDS.rollItem();
        } else {
            reward = HIGH_COUNT_REWARDS.rollItem();
        }
        player.getInventory().addOrDrop(reward);
        int shardAmt = Random.get(500, 2000);
        player.getInventory().addOrDrop(25676, shardAmt);
        if (reward.getDef().isNote()) {
            player.dialogue(
                    new ItemDialogue().one(reward.getId() - 1, "You unlock The Great Vault and receive <col=8B0000>" + reward.getAmount() + "x " + reward.getDef().name + "</col>!")
            );
        } else {
            player.dialogue(
                    new ItemDialogue().one(reward.getId(), "You unlock The Great Vault and receive <col=8B0000>" + reward.getAmount() + "x " + reward.getDef().name + "</col>!")
            );
        }
        player.sendFilteredMessage("You unlock The Great Vault and receive <col=8B0000>"+reward.getAmount()+"x " + reward.getDef().name + "</col>!");
        if (reward.getId() == 4151 || reward.getId() == 6585 || reward.getId() == 11832 || reward.getId() == 11834
                || reward.getId() == 11828 || reward.getId() == 11830 || reward.getId() == 11802 ||reward.getId() == 13239
                || reward.getId() == 19547 || reward.getId() == 6199) {
            String eventMessage = ""+ player.name() +" has received <col=8B0000>"+reward.getAmount()+"x "+reward.getDef().name+"</col> from The Great Vault!";
            Broadcast.WORLD.sendLoot( player, eventMessage);
        }
    }
    public static void greatvaultBoostReward(Player player) {
        player.greatvaultBoost--;
        player.setGlodDamageCounter(0);
        Item reward;
        if (World.players.count() < 50) {
            reward = LOW_COUNT_REWARDS.rollItem();
        } else {
            reward = HIGH_COUNT_REWARDS.rollItem();
        }
        player.getInventory().addOrDrop(reward);
        int shardAmt = Random.get(500, 2000);
        player.getInventory().addOrDrop(25676, shardAmt);
        if (reward.getDef().isNote()) {
            player.dialogue(
                    new ItemDialogue().one(reward.getId() - 1, "You unlock The Great Vault and receive <col=8B0000>" + reward.getAmount() + "x " + reward.getDef().name + "</col>!")
            );
        } else {
            player.dialogue(
                    new ItemDialogue().one(reward.getId(), "You unlock The Great Vault and receive <col=8B0000>" + reward.getAmount() + "x " + reward.getDef().name + "</col>!")
            );
        }
        player.sendFilteredMessage("You unlock The Great Vault and receive <col=8B0000>"+reward.getAmount()+"x " + reward.getDef().name + "</col>!");
        if (reward.getId() == 4151 || reward.getId() == 6585 || reward.getId() == 11832 || reward.getId() == 11834
                || reward.getId() == 11828 || reward.getId() == 11830 || reward.getId() == 11802 ||reward.getId() == 13239
                || reward.getId() == 19547 || reward.getId() == 6199) {
            String eventMessage = ""+ player.name() +" has received <col=8B0000>"+reward.getAmount()+"x "+reward.getDef().name+"</col> from The Great Vault!";
            Broadcast.WORLD.sendLoot( player, eventMessage);
        }
    }
}