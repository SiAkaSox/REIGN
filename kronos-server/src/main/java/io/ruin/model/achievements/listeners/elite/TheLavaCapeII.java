package io.ruin.model.achievements.listeners.elite;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.achievements.listeners.elite.EliteAchieveInter;
import io.ruin.model.entity.player.Player;

public class TheLavaCapeII implements AchievementListener {
    @Override
    public String name() {
        return "The Lava Cape II";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.thelavacapeiiElite ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[] {
                Achievement.slashIf("", finished),
                Achievement.slashIf("", finished),
                Achievement.slashIf("", finished),
                "",
                Achievement.slashIf("", finished),
                Achievement.slashIf("", finished),
        };
    }

    @Override
    public void started(Player player) {

    }

    @Override
    public void finished(Player player) {
        rewardCoins(player, 250000);
        player.getInventory().addOrDrop(608, 1);
        player.getInventory().addOrDrop(23071, 3);
    }

    public static void complete(Player p) {
        if (!p.thelavacapeiiElite) {
            p.thelavacapeiiElite = true;
            Achievement.THE_LAVA_CAPE_II.update(p);
            p.eliteAchievementsCompleted++;
            p.getPacketSender().sendString(858, 313, ""+p.eliteAchievementsCompleted+"/12");
            EliteAchieveInter.updateEliteBar(p);
        }
    }
}
