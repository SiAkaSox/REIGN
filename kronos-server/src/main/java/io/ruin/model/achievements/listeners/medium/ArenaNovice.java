package io.ruin.model.achievements.listeners.medium;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class ArenaNovice implements AchievementListener {
    @Override
    public String name() {
        return "Arena Novice";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.arenanovicemedium ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        player.getInventory().addOrDrop(13442, 50);
        player.getInventory().addOrDrop(12696, 10);
        rewardCoins(player, 100000);
    }

    public static void complete(Player p) {
        if (!p.arenanovicemedium) {
            p.arenanovicemedium = true;
            Achievement.ARENA_NOVICE.update(p);
            p.mediumAchievementsCompleted++;
            p.getPacketSender().sendString(858, 291, ""+p.mediumAchievementsCompleted+"/16");
            MediumAchieveInter.updateMediumBar(p);
        }
    }
}
