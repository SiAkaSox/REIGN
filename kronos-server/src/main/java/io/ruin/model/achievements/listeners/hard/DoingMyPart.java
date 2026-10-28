package io.ruin.model.achievements.listeners.hard;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class DoingMyPart implements AchievementListener {
    @Override
    public String name() {
        return "Doing My Part!";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.doingmypartHard ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        player.getInventory().addOrDrop(20545, 1);
        player.getInventory().addOrDrop(1464, 1);
        player.getInventory().addOrDrop(995, 100000);
    }

    public static void complete(Player p) {
        if (!p.doingmypartHard) {
            p.doingmypartHard = true;
            Achievement.DOING_MY_PART.update(p);
            p.hardAchievementsCompleted++;
            p.getPacketSender().sendString(858, 302, ""+p.hardAchievementsCompleted+"/20");
            HardAchieveInter.updateHardBar(p);
        }
    }
}
