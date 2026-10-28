package io.ruin.model.achievements.listeners.easy;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class SlayerBeginner implements AchievementListener {
    @Override
    public String name() {
        return "Slayer Beginner";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.slayerTasksCompleted >= 1 ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("Completing Slayer tasks is a great way to quickly train your stats", finished),
                Achievement.slashIf("and start building your bank up!", finished),
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: Complete a Slayer task.", finished),
                Achievement.slashIf("<col=000080>Reward</col>: 10,000 coins.", finished),
        };
    }

    @Override
    public void started(Player player) {

    }

    @Override
    public void finished(Player p) {
        rewardCoins(p, 25000);
    }

    public static void complete(Player p) {
        if (!p.slayerBeginner) {
            p.slayerBeginner = true;
            Achievement.SLAYER_BEGINNER.update(p);
            p.introAchievementsCompleted++;
            p.getPacketSender().sendString(858, 236, "" + p.introAchievementsCompleted + "/8");
            EasyAchieveInter.updateEasyBar(p);
        }
    }

}
