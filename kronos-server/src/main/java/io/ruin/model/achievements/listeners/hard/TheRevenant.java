package io.ruin.model.achievements.listeners.hard;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class TheRevenant implements AchievementListener {
    @Override
    public String name() {
        return "The Revenant";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.therevenantHard ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        rewardCoins(player, 100000);
    }

    public static void complete(Player p) {
        if (!p.therevenantHard) {
            p.therevenantHard = true;
            Achievement.THE_REVENANT.update(p);
            p.hardAchievementsCompleted++;
            p.getPacketSender().sendString(858, 302, ""+p.hardAchievementsCompleted+"/20");
            HardAchieveInter.updateHardBar(p);
        }
    }
}
