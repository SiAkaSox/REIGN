package io.ruin.model.combat;

import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.achievements.listeners.medium.NaturalBornKiller;
import io.ruin.model.activities.wilderness.Hotspot;
import io.ruin.model.activities.wilderness.ResourceArea;
import io.ruin.model.activities.wilderness.StaffBounty;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.inter.pvpinter.LeagueManager;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.SkillLamp;
import io.ruin.model.item.actions.impl.boxes.WildernessRewardBox;
import io.ruin.model.map.Bounds;
import io.ruin.model.stat.StatType;
import io.ruin.services.Loggers;
import io.ruin.utility.Broadcast;
import org.checkerframework.checker.units.qual.A;

public class Killer {
    public static final Bounds EDGEVILLE_FARM_SKIP_BOUNDS = new Bounds(3034, 3523, 3125, 3574, 0);

    public static int BASE_BM_REWARD = 35;

    private static long DAMAGE_RETAIN_TIME;

    public Player player;

    public int damage;
    public long lastDamage;

    public boolean expired() {
        return System.currentTimeMillis() - this.lastDamage > DAMAGE_RETAIN_TIME;
    }

    public void reward(Player pKilled, boolean bhTarget) {
        /*
         * Farming checks
         */
        long ms = System.currentTimeMillis();
//        if(player.getIpInt() == pKilled.getIpInt()) //don't reward bm for killing someone on your same ip
//            return;
        if(!pKilled.getPosition().inBounds(EDGEVILLE_FARM_SKIP_BOUNDS)) {
            boolean logged = player.bmIpLogs.contains(pKilled.getIpInt(), ms, 5L)
                    || player.bmUserLogs.contains(pKilled.getUserId(), ms, 5L);
            player.bmIpLogs.add(pKilled.getIpInt(), ms);
            player.bmUserLogs.add(pKilled.getUserId(), ms);
            if(logged)
                return;
        }

        /*
         * Increment killer kills & spree.
         */
    int killerSpree = ++player.currentKillSpree;
        if(killerSpree > player.highestKillSpree)
            player.highestKillSpree = killerSpree;
        if(killerSpree > 1) {
            player.sendMessage("You are currently on a killing spree of " + killerSpree + "!");
            if(killerSpree % 5 == 0 || killerSpree > 10) {
                String spreeMessage = player.name() + " is on a killing spree of " + killerSpree + ". Kill "
                        + (player.getAppearance().isMale() ? "him" : "her") + " for a reward of " + (BASE_BM_REWARD + bountyValue(killerSpree)) + " blood money!";
                Broadcast.WORLD.sendPlain(KillingSpree.imgTag(killerSpree) + Color.DARK_GREEN.tag() + " " + spreeMessage);
                //KillingSpreeEmbedMessage.sendDiscordMessage(spreeMessage);
            }
        }
        /*
         * Check for shutdown
         */
        int targetSpree = pKilled.currentKillSpree; //Spree is set to 0 in death method. (After this method)
        if(targetSpree >= 5) {
            String shutdownMessage = KillingSpree.shutdownMessage(player.name(), pKilled.name(), targetSpree);
            Broadcast.WORLD.sendPlain("" + Color.DARK_GREEN.tag() + shutdownMessage);
            //ShutdownEmbedMessage.sendDiscordMessage(shutdownMessage);
            if(targetSpree > player.highestShutdown)
                player.highestShutdown = targetSpree;
        }
        /*
         * Reward blood money
         */
        int bmAmount = BASE_BM_REWARD;

        /*
         * Check if we're doubling blood money for the weekend event
         */
        int bmMultiplier = World.bmMultiplier;
        if (bmMultiplier != 0) {
            bmAmount *= bmMultiplier;
        }

        bmAmount *= donatorBonus(player);

        if (bhTarget)
            bmAmount *= 1.05;
        if (player.tzrekJad || player.lilZik) {
            bmAmount *= 2.0;
        }

       if (player.getPosition().inBounds(Hotspot.ACTIVE.bounds)) {
            bmAmount *= 2.0;
            player.sendFilteredMessage("<col=6f0000>You received double blood money for killing a player in a hotspot!");
        }
        if(player.bloodmoneyBoost.isDelayed()) {
            bmAmount *= 2.0;
        }
        if (player.getPosition().inBounds(new Bounds(2993, 3523, 3124, 3597, -1))) {
            bmAmount = 55;
        }
        if (World.doubleBm) {
            bmAmount *= 2.0;
        }


        if (player.getPosition().inBounds(new Bounds(2993, 3523, 3124, 3597, -1))) {
            bmAmount += bountyValue(targetSpree) + 10;
        } else {
            bmAmount += bountyValue(targetSpree) + 10;
        }
        if (player.getPosition().inBounds(new Bounds(2993, 3523, 3124, 3597, -1))) {
            bmAmount += streakValue(killerSpree) + 10;
        } else {
            bmAmount += streakValue(killerSpree);
        }

        bmAmount += player.wildernessLevel * 1.0;


        /**
         * Check for staff bounty event
         */
        if (StaffBounty.EVENT_ACTIVE) {
            if (pKilled.isSupport()) {
                bmAmount += StaffBounty.SUPPORT_KILL_VALUE;
                StaffBounty.incrementStaffBounty("support_deaths");
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Staff Bounty", pKilled.name() + " has been slain! " + player.name() +
                        " has claimed " + StaffBounty.SUPPORT_KILL_VALUE + " for their bounty! The hunt continues...");

            }
            if (pKilled.isModerator()) {
                bmAmount += StaffBounty.MODERATOR_KILL_VALUE;
                StaffBounty.incrementStaffBounty("moderator_deaths");
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Staff Bounty", pKilled.name() + " has been slain! " + player.name() +
                        " has claimed " + StaffBounty.MODERATOR_KILL_VALUE + " for their bounty! The hunt continues...");
            }
            if (pKilled.isAdmin()) {
                bmAmount += StaffBounty.ADMINISTRATOR_KILL_VALUE;
                StaffBounty.incrementStaffBounty("administrator_deaths");
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Staff Bounty", pKilled.name() + " has been slain! " + player.name() +
                        " has claimed " + StaffBounty.ADMINISTRATOR_KILL_VALUE + " for their bounty! The hunt continues...");
            }
            Loggers.logStaffBountyKill(player, pKilled);
        }

        player.rewardBloodMoney(pKilled, bmAmount);


        /*
         * Roll for the wilderness reward box
         */
        int wildernessRewardBox = WildernessRewardBox.rollForDrop(player);
        if (wildernessRewardBox != -1) {
            player.getInventory().addOrDrop(new Item(wildernessRewardBox, 1));
        }

        if (player.insideWildernessAgilityCourse) {
            player.getStats().addXp(StatType.Agility, 25000, false);
            player.getInventory().addOrDrop(11849, 5);
            player.sendFilteredMessage("<col=6f0000> You receive 25,000 agility experience and 5 marks of grace for killing a player inside the Agility course.");
        }

        if (player.getPosition().inBounds(ResourceArea.BOUNDS)) {
            player.getInventory().addOrDrop(SkillLamp.SKILL_CAMP, 1);
            player.sendFilteredMessage("<col=6f0000> You receive a skill lamp for killing a player inside the Resource Area.");
        }

        NaturalBornKiller.complete(player);

        LeagueManager.setPvpRatingOnKill(player);
        LeagueManager.setPvpRatingOnDeath(pKilled);

        /*
         * Adjust wilderness elo
         */
       //WildernessRating.adjustEloRatings(pKilled, player);

        /*
         * Check daily
         */
//        DailyTask.checkPlayerKill(player, pKilled);
//        DailyTaskMedium.checkPlayerKill(player, pKilled);
//        DailyTaskHard.checkPlayerKill(player, pKilled);
//        DailyTaskElite.checkPlayerKill(player, pKilled);

    }

    private static double donatorBonus(Player player) {
        if (player.isGroup(PlayerGroup.ZENYTE)) {
            return 1.25;
        } else if (player.isGroup(PlayerGroup.ONYX)) {
            return 1.20;
        } else if (player.isGroup(PlayerGroup.RESPECTED_DONATOR)) {
            return 1.15;
        } else if (player.isGroup(PlayerGroup.LEGENDARY_DONATOR)) {
            return 1.10;
        } else if (player.isGroup(PlayerGroup.SUPER_DONATOR)) {
            return 1.05;
        } else if (player.isGroup(PlayerGroup.DONATOR)) {
            return 1.00;
        } else if (player.isGroup(PlayerGroup.EMERALD)) {
            return 1.00;
        } else {
            return 1.00;
        }
    }

    private static int bountyValue(int spree) {
        return (10 * spree);
    }

    private static int streakValue(int spree) {
        return 5 * spree;
    }


}