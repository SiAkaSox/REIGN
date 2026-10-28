package io.ruin.model.achievements.listeners.medium;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class ImbuedPower implements AchievementListener {
    @Override
    public String name() {
        return "Imbued Power";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.imbuedpowerMedium ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        player.getInventory().addOrDrop(1464, 3);
    }

    public static void complete(Player p) {
        if (!p.imbuedpowerMedium) {
            p.imbuedpowerMedium = true;
            Achievement.IMBUED_POWER.update(p);
            p.mediumAchievementsCompleted++;
            p.getPacketSender().sendString(858, 291, ""+p.mediumAchievementsCompleted+"/16");
            MediumAchieveInter.updateMediumBar(p);
        }
    }
}
