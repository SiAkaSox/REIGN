package io.ruin.model.inter.questtabs.tabs;

import io.ruin.Server;
import io.ruin.model.achievements.listeners.easy.EasyAchieveInter;
import io.ruin.model.achievements.listeners.elite.EliteAchieveInter;
import io.ruin.model.achievements.listeners.hard.HardAchieveInter;
import io.ruin.model.achievements.listeners.master.MasterAchieveInter;
import io.ruin.model.achievements.listeners.medium.MediumAchieveInter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;

import static io.ruin.Server.STARTUP_THREAD;
import static io.ruin.model.achievements.listeners.easy.EasyAchieveInter.loadEasyAchievements;
import static io.ruin.model.achievements.listeners.elite.EliteAchieveInter.loadEliteAchievements;
import static io.ruin.model.achievements.listeners.master.MasterAchieveInter.loadMasterAchievements;
import static io.ruin.model.inter.questtabs.QuestTab.*;

public class AchievementsTab {

    public static int ACHIEVEMENTS_TAB = 858;

    static {
        
            InterfaceHandler.register(ACHIEVEMENTS_TAB, h -> {
                h.actions[1] = (SlotAction) (p, slot) -> switchMain(p);
                h.actions[61] = (SlotAction) (p, slot) -> switchServerTab(p);
                h.actions[65] = (SlotAction) (p, slot) -> switchAchieveTab(p);
                h.actions[69] = (SlotAction) (p, slot) -> switchEventsTab(p);
                h.actions[234] = (SlotAction) (p, slot) -> loadEasyAchievements(p);
                h.actions[289] = (SlotAction) (p, slot) -> MediumAchieveInter.loadMediumAchievements(p);
                h.actions[300] = (SlotAction) (p, slot) -> HardAchieveInter.loadHardAchievements(p);
                h.actions[311] = (SlotAction) (p, slot) -> loadEliteAchievements(p);
                h.actions[322] = (SlotAction) (p, slot) -> loadMasterAchievements(p);
                h.actions[407] = (SlotAction) (p, slot) ->
                        p.sendMessage("This feature is currently under construction");
                //switchDailyAchieveTab(p);
            });
    }

    public static void sendUpdatesAchieveTab(Player p) {
        p.getPacketSender().sendString(ACHIEVEMENTS_TAB, 120, "Achievements");
        EasyAchieveInter.updateEasyBar(p);
        MediumAchieveInter.updateMediumBar(p);
        HardAchieveInter.updateHardBar(p);
        EliteAchieveInter.updateEliteBar(p);
        MasterAchieveInter.updateMasterBar(p);
    }

}