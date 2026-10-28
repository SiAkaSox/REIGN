package io.ruin.model.inter.worldeventsinter;

import io.ruin.Server;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.item.Item;

import static io.ruin.Server.STARTUP_THREAD;
import static io.ruin.model.inter.worldeventsinter.CtfInter.sendCtf;
import static io.ruin.model.inter.worldeventsinter.DeadmanInter.sendDeadman;
import static io.ruin.model.inter.worldeventsinter.FragmentInter.sendFragment;
import static io.ruin.model.inter.worldeventsinter.GlodInter.sendGlod;
import static io.ruin.model.inter.worldeventsinter.HtfInter.sendHtf;
import static io.ruin.model.inter.worldeventsinter.PiratesInter.sendPirates;

public class WorldEventsInter {

    static {
        
            InterfaceHandler.register(964, h -> {//Main inter
                h.actions[21] = (SlotAction) (player, slot) -> sendCtf(player);
                h.actions[25] = (SlotAction) (player, slot) -> sendDeadman(player);
                h.actions[29] = (SlotAction) (player, slot) -> sendFragment(player);
                h.actions[33] = (SlotAction) (player, slot) -> sendGlod(player);
                h.actions[37] = (SlotAction) (player, slot) -> sendHtf(player);
                h.actions[41] = (SlotAction) (player, slot) -> sendPirates(player);
            });
            InterfaceHandler.register(965, h -> {//Main inter
                h.actions[21] = (SlotAction) (player, slot) -> sendCtf(player);
                h.actions[25] = (SlotAction) (player, slot) -> sendDeadman(player);
                h.actions[29] = (SlotAction) (player, slot) -> sendFragment(player);
                h.actions[33] = (SlotAction) (player, slot) -> sendGlod(player);
                h.actions[37] = (SlotAction) (player, slot) -> sendHtf(player);
                h.actions[41] = (SlotAction) (player, slot) -> sendPirates(player);
            });
            InterfaceHandler.register(966, h -> {//Main inter
                h.actions[21] = (SlotAction) (player, slot) -> sendCtf(player);
                h.actions[25] = (SlotAction) (player, slot) -> sendDeadman(player);
                h.actions[29] = (SlotAction) (player, slot) -> sendFragment(player);
                h.actions[33] = (SlotAction) (player, slot) -> sendGlod(player);
                h.actions[37] = (SlotAction) (player, slot) -> sendHtf(player);
                h.actions[41] = (SlotAction) (player, slot) -> sendPirates(player);
            });
            InterfaceHandler.register(967, h -> {//Main inter
                h.actions[21] = (SlotAction) (player, slot) -> sendCtf(player);
                h.actions[25] = (SlotAction) (player, slot) -> sendDeadman(player);
                h.actions[29] = (SlotAction) (player, slot) -> sendFragment(player);
                h.actions[33] = (SlotAction) (player, slot) -> sendGlod(player);
                h.actions[37] = (SlotAction) (player, slot) -> sendHtf(player);
                h.actions[41] = (SlotAction) (player, slot) -> sendPirates(player);
            });
            InterfaceHandler.register(968, h -> {//Main inter
                h.actions[21] = (SlotAction) (player, slot) -> sendCtf(player);
                h.actions[25] = (SlotAction) (player, slot) -> sendDeadman(player);
                h.actions[29] = (SlotAction) (player, slot) -> sendFragment(player);
                h.actions[33] = (SlotAction) (player, slot) -> sendGlod(player);
                h.actions[37] = (SlotAction) (player, slot) -> sendHtf(player);
                h.actions[41] = (SlotAction) (player, slot) -> sendPirates(player);
            });
            InterfaceHandler.register(969, h -> {//Main inter
                h.actions[21] = (SlotAction) (player, slot) -> sendCtf(player);
                h.actions[25] = (SlotAction) (player, slot) -> sendDeadman(player);
                h.actions[29] = (SlotAction) (player, slot) -> sendFragment(player);
                h.actions[33] = (SlotAction) (player, slot) -> sendGlod(player);
                h.actions[37] = (SlotAction) (player, slot) -> sendHtf(player);
                h.actions[41] = (SlotAction) (player, slot) -> sendPirates(player);
            });
    }

    public static void sendTitles(Player p, int inter) {
        p.getPacketSender().sendString(inter, 22, "Capture the Flag");
        p.getPacketSender().sendString(inter, 26, "Deadmans Chest");
        p.getPacketSender().sendString(inter, 30, "Fragment of Seren");
        p.getPacketSender().sendString(inter, 34, "Glod");
        p.getPacketSender().sendString(inter, 38, "Hold the Fort");
        p.getPacketSender().sendString(inter, 42, "Pirates Ship");
    }
    public static void sendModel(Player p, int inter, int child, int model, int zoom, int rotX, int rotY) {
        p.getPacketSender().sendModel(inter, child, model);
        p.getPacketSender().sendModelInformation(inter, child, zoom, rotX, rotY);
    }
    public static void sendRewards(Player p, int inter, int child, int container, int reward, int amount) {
        p.getPacketSender().sendItems(inter, child, container, new Item(reward, amount));
    }

}