package io.ruin.model.achievements.listeners.elite;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.achievements.listeners.elite.EliteAchieveInter;
import io.ruin.model.entity.player.Player;

public class NotJustAFragment implements AchievementListener {
    @Override
    public String name() {
        return "Not Just A Fragment";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.notjustafragmentElite ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        player.getInventory().addOrDrop(30276, 1);
        rewardCoins(player, 250000);
    }

    public static void complete(Player p) {
        if (!p.notjustafragmentElite) {
            p.notjustafragmentElite = true;
            Achievement.NOT_JUST_A_FRAGMENT.update(p);
            p.eliteAchievementsCompleted++;
            p.getPacketSender().sendString(858, 313, ""+p.eliteAchievementsCompleted+"/12");
            EliteAchieveInter.updateEliteBar(p);
        }
    }
}
