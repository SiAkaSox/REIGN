package io.ruin.model.achievements.listeners.master;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class YoureOnYourOlm implements AchievementListener {

    @Override
    public String name() {
        return "You're On Your Olm";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.youreonyourolmMaster ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        player.getInventory().addOrDrop(11158, 1);
        player.getInventory().addOrDrop(995, 500000);
    }

    public static void complete(Player p) {
        if (!p.youreonyourolmMaster) {
            p.youreonyourolmMaster = true;
            Achievement.YOURE_ON_YOUR_OLM.update(p);
            p.masterAchievementsCompleted++;
            p.getPacketSender().sendString(858, 324, ""+p.masterAchievementsCompleted+"/8");
            MasterAchieveInter.updateMasterBar(p);
        }
    }
}