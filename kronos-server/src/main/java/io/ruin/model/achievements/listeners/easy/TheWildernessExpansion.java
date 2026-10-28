package io.ruin.model.achievements.listeners.easy;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class TheWildernessExpansion implements AchievementListener {
    @Override
    public String name() {
        return "The Wilderness Expansion";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.theknightsguildIntro ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        rewardCoins(player, 10000);
    }

    public static void complete(Player p) {
        if (!p.theknightsguildIntro) {
            p.theknightsguildIntro = true;
            Achievement.THE_WILDERNESS_EXPANSION.update(p);
            p.introAchievementsCompleted++;
            p.getPacketSender().sendString(858, 236, ""+p.introAchievementsCompleted+"/8");
            EasyAchieveInter.updateEasyBar(p);
        }
    }
}
