package io.ruin.model.content.vote;

import com.google.api.client.util.Lists;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.model.map.Bounds;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;
import io.ruin.utility.Random;

import java.util.Arrays;
import java.util.List;

public class VoteManager {

    public static int totalVotes;

    public static NPC voteBoss;

    public static void check() {
        totalVotes++;
        if (totalVotes == 100) {
            voteBoss = new NPC(6330);
            voteBoss.walkBounds = new Bounds(2904, 3604, 2919, 3519, 0);
            voteBoss.spawn(2910, 3612, 0);
            voteBoss.getDef().ignoreOccupiedTiles = true;
            totalVotes = 0;
        }
    }

    static {
        ItemDef.get(7162).tradeable = true;//vote book
        ItemDef.get(26920).tradeable = true;//vote mbox
        ItemAction.registerInventory(26920, 1, (player, item) -> {
            if (!player.getInventory().hasId(26920))
                return;
            player.getInventory().remove(item);
            double chance = Math.random();
            double rare = 1.0 / 150;
            double super_rare = 1.0 / 250;
            List<Item> possibleLoot = Lists.newArrayList();
            boolean announce = false;
            if (chance <= super_rare) {
                List<Item> superrares = Arrays.asList(
                        new Item(30282, 1),
                        new Item(607, 1),
                        new Item(608, 1),
                        new Item(26500, 1),
                        new Item(20238, 1),
                        new Item(25089, 1));
                announce = true;
                possibleLoot.addAll(superrares);
            } else if (chance <= rare) {
                List<Item> rares = Arrays.asList(
                        new Item(30276, 1),
                        new Item(6199, 1),
                        new Item(4151, 1),
                        new Item(6920, 1));
                announce = true;
                possibleLoot.addAll(rares);
            } else {
                List<Item> commons = Arrays.asList(
                        new Item(995, Misc.random(75_000, 300_000)),
                        new Item(4708, 1),
                        new Item(4710, 1),
                        new Item(4712, 1),
                        new Item(4714, 1),
                        new Item(4716, 1),
                        new Item(4718, 1),
                        new Item(4720, 1),
                        new Item(25676, Misc.random(500, 2000)),
                        new Item(4722, 1),
                        new Item(4724, 1),
                        new Item(4726, 1),
                        new Item(4728, 1),
                        new Item(4730, 1),
                        new Item(4732, 1),
                        new Item(4736, 1),
                        new Item(4738, 1),
                        new Item(4734, 1),
                        new Item(4745, 1),
                        new Item(4747, 1),
                        new Item(4749, 1),
                        new Item(4751, 1),
                        new Item(4708, 1),
                        new Item(13307, Misc.random(10, 50)),//blood money
                        new Item(7162, 1),//vote book
                        new Item(786, 3)//skippable scroll ting
                );
                possibleLoot.addAll(commons);
            }

            Item selectedReward = Random.get(possibleLoot);

            if (selectedReward != null) {
                player.getInventory().addOrDrop(selectedReward);
                player.dialogue(new ItemDialogue().one(selectedReward.getId(), "As you open the Vote Mystery Box<br>you see a.. " + selectedReward.getDef().name + " at the bottom.."));
                if (announce)
                    Broadcast.GLOBAL.sendLoot(player, player.name()+" received <col=8B0000>"+selectedReward.getAmount()+"x "+ selectedReward.getDef().name + "</col> <col=000000>from a <col=8B0000>Vote Mystery Box!");
            }

            Pet pet = Pet.DAD;
            if (pet != null) {
                double odds = 1.0 / pet.dropAverage;
                if (Math.random() <= odds) {
                    pet.unlock(player);
                }
            }
        });

        ItemAction.registerInventory(7162, 1, (player, item) -> {
            if (!player.getInventory().hasId(item.getId()))
                return;
            player.getInventory().remove(item);
            player.rewardsDialogue("Thank you for voting on all sites!<br>Enjoy 30 minutes of double pkp and xp!");
            player.sendMessage("<img=33> <col=800080>Thank you for voting on all sites! Enjoy 30 minutes of double pkp and xp!");
            player.expBonus.delaySeconds(30 * 60);
            player.bloodmoneyBoost.delaySeconds(30 * 60);
        });
    }
}
