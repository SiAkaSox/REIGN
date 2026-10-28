package io.ruin.model.achievements.listeners.hard;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class TightropeWalker implements AchievementListener {
    @Override
    public String name() {
        return "Tightrope Walker";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.tightropewalkerHard ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        player.getInventory().addOrDrop(11849, 10);
        player.getInventory().addOrDrop(995, 150000);
    }

    public static void complete(Player p) {
        if (!p.tightropewalkerHard) {
            p.tightropewalkerHard = true;
            Achievement.TIGHTROPE_WALKER.update(p);
            p.hardAchievementsCompleted++;
            p.getPacketSender().sendString(858, 302, ""+p.hardAchievementsCompleted+"/20");
            HardAchieveInter.updateHardBar(p);
        }
    }
}
