package io.ruin.model.achievements.listeners.hard;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.achievements.listeners.master.ChickenKiller;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

public class ARealChallenge implements AchievementListener {
    @Override
    public String name() {
        return "A Real Challenge Awaits..";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.realchallengeHard ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        player.getInventory().addOrDrop(995, 250000);
        player.getInventory().addOrDrop(11158 , 1);
    }

    public static void complete(Player p) {
        if (!p.realchallengeHard) {
            p.realchallengeHard = true;
            Achievement.A_REAL_CHALLENGE.update(p);
            p.hardAchievementsCompleted++;
            p.getPacketSender().sendString(858, 302, ""+p.hardAchievementsCompleted+"/20");
            HardAchieveInter.updateHardBar(p);
        }
    }

    public static void check(Player player, NPC killed) {
        if (killed.getDef().id == 8918)
            complete(player);
    }
}
