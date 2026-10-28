package io.ruin.model.achievements.listeners.master;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class SlayerMasterAchieve implements AchievementListener {

    @Override
    public String name() {
        return "Slayer Master";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.slayermasterMaster ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        player.getInventory().addOrDrop(23071, 5);
        player.getInventory().addOrDrop(2528, 2);
        player.getInventory().addOrDrop(995, 500000);
    }

    public static void complete(Player p) {
        if (!p.slayermasterMaster) {
            p.slayermasterMaster = true;
            Achievement.SLAYER_MASTER.update(p);
            p.masterAchievementsCompleted++;
            p.getPacketSender().sendString(858, 324, ""+p.masterAchievementsCompleted+"/8");
            MasterAchieveInter.updateMasterBar(p);
        }
    }
}