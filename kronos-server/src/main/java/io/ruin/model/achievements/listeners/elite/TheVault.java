package io.ruin.model.achievements.listeners.elite;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.achievements.listeners.elite.EliteAchieveInter;
import io.ruin.model.entity.player.Player;

public class TheVault implements AchievementListener {
    @Override
    public String name() {
        return "The Vault";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.thevaultElite ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        rewardCoins(player, 150000);
    }

    public static void complete(Player p) {
        if (!p.thevaultElite) {
            p.thevaultElite = true;
            Achievement.THE_VAULT.update(p);
            p.eliteAchievementsCompleted++;
            p.getPacketSender().sendString(858, 313, ""+p.eliteAchievementsCompleted+"/12");
            EliteAchieveInter.updateEliteBar(p);
        }
    }
}
