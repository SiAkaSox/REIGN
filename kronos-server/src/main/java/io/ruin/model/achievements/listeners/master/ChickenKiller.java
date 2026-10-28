package io.ruin.model.achievements.listeners.master;

import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

public class ChickenKiller implements AchievementListener {

    @Override
    public String name() {
        return "Chicken Killer";
    }

    @Override
    public AchievementStage stage(Player player) {
        return player.chickenkillermaster ? AchievementStage.FINISHED : AchievementStage.NOT_STARTED;
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
        if (!p.chickenkillermaster) {
            p.chickenkillermaster = true;
            Achievement.CHICKEN_KILLER.update(p);
            p.masterAchievementsCompleted++;
            p.getPacketSender().sendString(858, 324, ""+p.masterAchievementsCompleted+"/8");
            MasterAchieveInter.updateMasterBar(p);
        }
    }

    public static void check(Player player, NPC killed) {
        if (killed.getDef().id == 319)
            ChickenKiller.complete(player);
    }
}