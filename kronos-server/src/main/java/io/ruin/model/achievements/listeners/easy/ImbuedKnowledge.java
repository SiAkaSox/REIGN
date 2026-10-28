package io.ruin.model.achievements.listeners.easy;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;

public class ImbuedKnowledge implements AchievementListener {
    @Override
    public String name() {
        return "Imbued Knowledge";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.imbuedknowledgeIntro ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        rewardCoins(player, 10000);
    }

    public static void complete(Player p) {
        if (!p.imbuedknowledgeIntro) {
            p.imbuedknowledgeIntro = true;
            Achievement.IMBUED_KNOWLEDGE.update(p);
            p.introAchievementsCompleted++;
            p.getPacketSender().sendString(858, 236, ""+p.introAchievementsCompleted+"/8");
            EasyAchieveInter.updateEasyBar(p);
        }
    }
}
