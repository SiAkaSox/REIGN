package io.ruin.model.achievements.listeners.hard;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class TheSerpantKing implements AchievementListener {
    @Override
    public String name() {
        return "The Serpent King";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.theserpantkingHard ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        player.getInventory().addOrDrop(995, 150000);
        player.getInventory().addOrDrop(23071, 1);
        player.getInventory().addOrDrop(12934, 1000);
    }

    public static void complete(Player p) {
        if (!p.theserpantkingHard) {
            p.theserpantkingHard = true;
            Achievement.THE_SERPANT_KING.update(p);
            p.hardAchievementsCompleted++;
            p.getPacketSender().sendString(858, 302, ""+p.hardAchievementsCompleted+"/20");
            HardAchieveInter.updateHardBar(p);
        }
    }
}
