package io.ruin.model.inter.greatvaultrewards;

import io.ruin.Server;
import io.ruin.utility.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SerenChestRewards {

    private static final int SEREN_KEY = 3462;

    private static final LootTable REWARDS = new LootTable().addTable(1,
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
            new LootItem(12002, 1, 10).broadcast(Broadcast.WORLD),
            new LootItem(2528, 3, 9).broadcast(Broadcast.WORLD),
            new LootItem(23951, 5, 8).broadcast(Broadcast.WORLD),
            new LootItem(24460, 1, 7).broadcast(Broadcast.WORLD),
            new LootItem(30276, 1, 6).broadcast(Broadcast.WORLD),
            new LootItem(11283, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(11804, 1, 4).broadcast(Broadcast.WORLD),
            new LootItem(11824, 1, 3).broadcast(Broadcast.WORLD),
            new LootItem(11830, 1, 2).broadcast(Broadcast.WORLD),
            new LootItem(11832, 1, 2).broadcast(Broadcast.WORLD),
            new LootItem(19493, 1, 1).broadcast(Broadcast.WORLD),
            new LootItem(995, 5000000, 5).broadcast(Broadcast.WORLD)
    );

    public static void openChest(Player p){
        int keyCount = p.getInventory().getAmount(SEREN_KEY);
        if (!p.getInventory().contains(SEREN_KEY, 1)){
            p.sendMessage("You need a <col=0040ff>Seren Key</col> to open this chest!");
        }
        if(keyCount > 0){
            if(p.getInventory().getFreeSlots() == 0 && keyCount > 1){
                p.sendMessage("You don't have enough free slots to do that!");
                return;
            }
            if(p.getInventory().contains(SEREN_KEY, 1)) {
                Item reward;
                reward = REWARDS.rollItem();
                p.startEvent(event -> {
                    p.setSerenDamageCounter(0);
                    p.lock(LockType.FULL_NULLIFY_DAMAGE);
                    p.dialogue(new MessageDialogue("Opening The Great Vault, searching for a reward..").hideContinue());
                    event.delay(1);
                    p.animate(881);
                    event.delay(2);
                    p.unlock();
                    p.getInventory().addOrDrop(reward);
                    int shardAmt = Random.get(500, 2000);
                    p.getInventory().addOrDrop(25676, shardAmt);
                    p.getInventory().remove(SEREN_KEY, 1);
                    if (p.greatvaultBoost > 0) {
                        boostReRoll(p);
                    }
                    if (reward.getDef().isNote()) {
                        p.dialogue(
                                new ItemDialogue().one(reward.getId() - 1, "You unlock The Great Vault and receive <col=8B0000>" + reward.getAmount() + "x " + reward.getDef().name + "</col>!")
                        );
                    } else {
                        p.dialogue(
                                new ItemDialogue().one(reward.getId(), "You unlock The Great Vault and receive <col=8B0000>" + reward.getAmount() + "x " + reward.getDef().name + "</col>!")
                        );
                    }
                    if (reward.getId() == 12904 || reward.getId() == 19547 || reward.getId() == 11802 || reward.getId() == 20784
                     || reward.getId() == 30191 || reward.getId() == 22613 || reward.getId() == 24424
                     || reward.getId() == 26233 || reward.getId() == 24780 || reward.getId() == 6585) {
                        String eventMessage = ""+ p.name() +" has received <col=8B0000>"+reward.getAmount()+"x "+reward.getDef().name+"</col> from Seren's Chest!";
                        Broadcast.WORLD.sendLoot(p, ""+eventMessage);
                     }
                    event.delay(1);
                   });
            }
        }
    }

    public static void boostReRoll(Player p) {
        p.greatvaultBoost--;
        Item reward;
        reward = REWARDS.rollItem();
        p.startEvent(event -> {
            p.setSerenDamageCounter(0);
            p.lock(LockType.FULL_NULLIFY_DAMAGE);
            p.dialogue(new MessageDialogue("Your boost allows you to open the chest again!").hideContinue());
            event.delay(1);
            p.animate(881);
            event.delay(2);
            p.unlock();
            p.getInventory().addOrDrop(reward);
            int shardAmt = Random.get(500, 2000);
            p.getInventory().addOrDrop(25676, shardAmt);
            if (reward.getDef().isNote()) {
                p.dialogue(
                        new ItemDialogue().one(reward.getId() - 1, "You unlock The Great Vault and receive <col=8B0000>" + reward.getAmount() + "x " + reward.getDef().name + "</col>!")
                );
            } else {
                p.dialogue(
                        new ItemDialogue().one(reward.getId(), "You unlock The Great Vault and receive <col=8B0000>" + reward.getAmount() + "x " + reward.getDef().name + "</col>!")
                );
            }
            if (reward.getId() == 12904 || reward.getId() == 19547 || reward.getId() == 11802 || reward.getId() == 20784
                    || reward.getId() == 30191 || reward.getId() == 22613 || reward.getId() == 24424
                    || reward.getId() == 26233 || reward.getId() == 24780 || reward.getId() == 6585) {
                String eventMessage = ""+ p.name() +" has received <col=8B0000>"+reward.getAmount()+"x "+reward.getDef().name+"</col> from Seren's Chest!";
                Broadcast.WORLD.sendLoot(p, ""+eventMessage);
            }
            event.delay(1);
        });
    }

    static {
            ObjectAction.register(35988, 1, (player, obj) -> {
                openChest(player);
            });
    }

}