package io.ruin.model.activities.wilderness.pvp.bounty;

import io.ruin.utility.NumberUtils;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.ground.GroundItem;

public class BountyManager {

    public static void lookup(Player player) {
        player.nameInput("Enter the Players name who you wish to put a bounty on:", name -> {
            if (name == null || name.isEmpty()) {
                player.sendMessage("Invalid name! try again!");
                return;
            }
            Player target = World.getPlayerByName(name);
            if (target == null) {
                player.sendMessage(name + " isn't Online to put a bounty on.");
                return;
            }
            setBountyAmount(player, name);
        });
    }

    private static void setBountyAmount(Player player, String name) {
        player.dialogue(new OptionsDialogue("How much of a Bounty to set on " + name + "?",
                new Option("10M (Red Skull)", () -> checkBountyAndAmount(player, BountySkull.RED, name)),
                new Option("7.5M (Blue Skull)", () -> checkBountyAndAmount(player, BountySkull.BLUE, name)),
                new Option("5M (Green Skull)", () -> checkBountyAndAmount(player, BountySkull.GREEN, name)),
                new Option("2.5M (Silver Skull)", () -> checkBountyAndAmount(player, BountySkull.SILVER, name)),
                new Option("1M (Bronze Skull)", () -> checkBountyAndAmount(player, BountySkull.BRONZE, name))));
        return;
    }

    private static void checkBountyAndAmount(Player player, BountySkull skull, String name) {
        final int price = skull.requiredAmount;
        final String formattedPrice = NumberUtils.formatNumber(price);
        if (!player.getInventory().contains(995, price)) {
            player.sendMessage("You need " + formattedPrice + " GP to set a bouny on " + name);
            return;
        }
        Player target = World.getPlayerByName(name);
        if (target == null) {
            player.sendMessage(name + " is no longer Online to set a bounty on!");
            return;
        }
        final String targetName = target.name();
        if (target.getPlayerBounty() != null) {
            player.sendMessage(targetName + " already is marked with a bounty.");
            return;
        }
        if (target.wildernessLevel > 0) {
            player.sendMessage("You cannot mark players with a bounty while they are in the wilderess!");
            return;
        }
        player.getInventory().remove(995, price);
        target.setPlayerBounty(new PlayerBounty(skull));
        checkSkull(target);
        target.sendMessage(player.name()+" has set a bounty on you! watch your back!");
        player.sendMessage(targetName + " has been marked with a bounty skull. Pleasure for doing business.");
        World.sendWorldMessage("<img=" + skull.getCrownImg() + ">" + skull.getColourCode() + " A " + formattedPrice + " GP bounty has been set on " + targetName + "..\n.. Kill them to redeem it!");
    }

    public static void checkSkull(Player player) {
        PlayerBounty bounty = player.getPlayerBounty();
        if (bounty == null)
            return;
        player.getAppearance().setSkullIcon(bounty.bountySkull.getSkullId());
    }

    public static void onDeath(Player killer, Player player) {
        PlayerBounty bounty = player.getPlayerBounty();
        if (bounty == null)
            return;
        final int reward = bounty.bountySkull.requiredAmount;
        String formattedReward = NumberUtils.formatNumber(reward);
        killer.dialogue(new ItemDialogue().one(995, "You have claimed the "+formattedReward+" GP bounty of "+player.name()).hideContinue());
        killer.sendMessage("Bounty: You have claimed the bounty of "+player.name()+" for "+formattedReward+" GP");
        boolean space = killer.getInventory().freeSlot() > 0 || killer.getInventory().contains(995);
        if (space) {
            killer.getInventory().add(995, reward);
        } else {
            new GroundItem(995, reward).owner(killer).position(player.getPosition().getX(),player.getPosition().getY(),player.getPosition().getZ()).spawn();
        }
        killer.sendMessage("Your reward from the bounty has been added to "+(space ? "your inventory" : "the floor"));
        player.setPlayerBounty(null);
        player.getAppearance().setSkullIcon(-1);
        player.sendMessage("Your bounty has been claimed by "+killer.name());
        World.sendWorldMessage("Bounty: "+killer.name()+" has claimed the "+formattedReward+" GP bounty of "+player.name());
    }
}
