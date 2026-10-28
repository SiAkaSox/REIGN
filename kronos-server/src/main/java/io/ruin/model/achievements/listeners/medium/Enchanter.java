package io.ruin.model.achievements.listeners.medium;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class Enchanter implements AchievementListener {
    @Override
    public String name() {
        return "Enchanter";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.enchanterMedium ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        player.getInventory().addOrDrop(989, 3);
        player.getInventory().addOrDrop(1464, 3);
        rewardCoins(player, 100000);
    }

    public static void complete(Player p) {
        if (!p.enchanterMedium) {
            p.enchanterMedium = true;
            Achievement.ENCHANTER.update(p);
            p.mediumAchievementsCompleted++;
            p.getPacketSender().sendString(858, 291, ""+p.mediumAchievementsCompleted+"/16");
            MediumAchieveInter.updateMediumBar(p);
        }
    }
}
