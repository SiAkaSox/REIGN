package io.ruin.model.achievements.listeners.elite;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.achievements.listeners.elite.EliteAchieveInter;
import io.ruin.model.entity.player.Player;

public class HailHydra implements AchievementListener {
    @Override
    public String name() {
        return "Hail Hydra";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.hailhydraElite ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        player.getInventory().addOrDrop(23071, 1);
        player.getInventory().addOrDrop(12696, 25);
        rewardCoins(player, 250000);
    }

    public static void complete(Player p) {
        if (!p.hailhydraElite) {
            p.hailhydraElite = true;
            Achievement.HAIL_HYDRA.update(p);
            p.eliteAchievementsCompleted++;
            p.getPacketSender().sendString(858, 313, ""+p.eliteAchievementsCompleted+"/12");
            EliteAchieveInter.updateEliteBar(p);
        }
    }
}
