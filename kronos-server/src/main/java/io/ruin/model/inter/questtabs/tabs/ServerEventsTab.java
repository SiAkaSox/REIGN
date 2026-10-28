package io.ruin.model.inter.questtabs.tabs;

import io.ruin.model.activities.skillbonus.BiHourlyBonusMinigame;
import io.ruin.model.activities.skillbonus.BiHourlyBonusSkill;
import io.ruin.model.activities.wilderness.events.*;
import io.ruin.model.activities.wilderness.events.capturetheflag.CaptureTheFlag;
import io.ruin.model.activities.wilderness.events.seren.SerenEvent;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;

import static io.ruin.model.inter.questtabs.QuestTab.*;
import static io.ruin.model.inter.worldeventsinter.CtfInter.sendCtf;
import static io.ruin.model.inter.worldeventsinter.DeadmanInter.sendDeadman;
import static io.ruin.model.inter.worldeventsinter.FragmentInter.sendFragment;
import static io.ruin.model.inter.worldeventsinter.GlodInter.sendGlod;
import static io.ruin.model.inter.worldeventsinter.HtfInter.sendHtf;
import static io.ruin.model.inter.worldeventsinter.PiratesInter.sendPirates;

public class ServerEventsTab {

    public static int SERVER_EVENTS_TAB = 859;

    static {
        
            InterfaceHandler.register(SERVER_EVENTS_TAB, h -> {
                h.actions[1] = (SlotAction) (p, slot) -> switchMain(p);
                h.actions[61] = (SlotAction) (p, slot) -> switchServerTab(p);
                h.actions[65] = (SlotAction) (p, slot) -> switchAchieveTab(p);
                h.actions[69] = (SlotAction) (p, slot) -> switchEventsTab(p);
                h.actions[174] = (SlotAction) (p, slot) -> sendCtf(p);
                h.actions[175] = (SlotAction) (p, slot) -> sendDeadman(p);
                h.actions[176] = (SlotAction) (p, slot) -> sendPirates(p);
                h.actions[177] = (SlotAction) (p, slot) -> sendFragment(p);
                h.actions[178] = (SlotAction) (p, slot) -> sendHtf(p);
                h.actions[189] = (SlotAction) (p, slot) -> sendGlod(p);
            });
    }

    public static void sendUpdatesServerEventsTab(Player p) {
        CaptureTheFlag.updateOverlay(p);
        DeadmanChestEvent.updateOverlay(p);
        PirateShipEvent.updateOverlay(p);
        GlodEvent.updateOverlay(p);
        SerenEvent.updateOverlay(p);
        HoldTheFort.updateOverlay(p);
        p.getPacketSender().sendString(SERVER_EVENTS_TAB, 120, "Server Events");
        p.getPacketSender().sendString(SERVER_EVENTS_TAB, 200, "<img=79> Bonus Skill: <col=1E90FF>"+ BiHourlyBonusSkill.sendtoEvents());
        p.getPacketSender().sendString(SERVER_EVENTS_TAB, 211, "<img=79> Bonus Minigame: <col=1E90FF>"+ BiHourlyBonusMinigame.ACTIVE.shortName);
        p.getPacketSender().sendModel(SERVER_EVENTS_TAB, 136, 38605);
        p.getPacketSender().sendModelInformation(SERVER_EVENTS_TAB, 136, 3800, 2012, 0);
    }

}