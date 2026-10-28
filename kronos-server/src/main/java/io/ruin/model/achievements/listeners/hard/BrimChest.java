package io.ruin.model.achievements.listeners.hard;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class BrimChest implements AchievementListener {
    @Override
    public String name() {
        return "The Brimstone Chest";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.brimchestHard ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        player.getInventory().addOrDrop(995, 100000);
        player.getInventory().addOrDrop(989, 2);
    }

    public static void complete(Player p) {
        if (!p.brimchestHard) {
            p.brimchestHard = true;
            Achievement.BRIM_CHEST.update(p);
            p.hardAchievementsCompleted++;
            p.getPacketSender().sendString(858, 302, ""+p.hardAchievementsCompleted+"/20");
            HardAchieveInter.updateHardBar(p);
        }
    }
}
