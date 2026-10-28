package io.ruin.model.achievements.listeners.easy;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class IntroToPerks implements AchievementListener {
    @Override
    public String name() {
        return "Intro to Perks";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.introtoPerks ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        if (!p.introtoPerks) {
            p.introtoPerks = true;
            Achievement.INTRO_TO_PERKS.update(p);
            p.introAchievementsCompleted++;
            p.getPacketSender().sendString(858, 236, ""+p.introAchievementsCompleted+"/8");
            EasyAchieveInter.updateEasyBar(p);
        }
    }
}
