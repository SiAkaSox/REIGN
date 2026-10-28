package io.ruin.model.achievements.listeners.easy;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class LoomingThreat implements AchievementListener {
    @Override
    public String name() {
        return "Looming Threat";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.loomingThreatIntro ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
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
        rewardCoins(player, 15000);
    }

    public static void complete(Player p) {
        if (!p.loomingThreatIntro) {
            p.loomingThreatIntro = true;
            Achievement.LOOMING_THREAT.update(p);
            p.introAchievementsCompleted++;
            p.getPacketSender().sendString(858, 236, ""+p.introAchievementsCompleted+"/9");
            EasyAchieveInter.updateEasyBar(p);
        }
    }
}
