package io.ruin.model.achievements.listeners.medium;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class BattlegroundNovice implements AchievementListener {
    @Override
    public String name() {
        return "Dead Man Walkin'";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.battlegroundnovicemedium ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        player.getInventory().addOrDrop(12696, 10);
        rewardBm(player, 100);
        rewardCoins(player, 50000);
    }

    public static void complete(Player p) {
        if (!p.battlegroundnovicemedium) {
            p.battlegroundnovicemedium = true;
            Achievement.BATTLEGROUND_NOVICE.update(p);
            p.mediumAchievementsCompleted++;
            p.getPacketSender().sendString(858, 291, ""+p.mediumAchievementsCompleted+"/16");
            MediumAchieveInter.updateMediumBar(p);
        }
    }
}
