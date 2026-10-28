package io.ruin.model.achievements.listeners.master;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class BigOlFlex implements AchievementListener {

    @Override
    public String name() {
        return "Big Ol Flex";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.bigolflexMaster ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        player.getInventory().addOrDrop(608, 1);
        player.getInventory().addOrDrop(13222, 1);
        player.getInventory().addOrDrop(995, 500000);
    }

    public static void complete(Player p) {
        if (!p.bigolflexMaster) {
            p.bigolflexMaster = true;
            Achievement.BIG_OL_FLEX.update(p);
            p.masterAchievementsCompleted++;
            p.getPacketSender().sendString(858, 324, ""+p.masterAchievementsCompleted+"/8");
            MasterAchieveInter.updateMasterBar(p);
        }
    }
}