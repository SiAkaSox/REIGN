package io.ruin.model.content.charterships;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;

public class CharterShipNpcs {

    static {
        int[] npcIds = { 9336, 9360, 9372, };
        for (int npcId : npcIds) {
            NPCAction.register(npcId, "Talk-to", (player, npc) -> talkTo(player, npc));
            NPCAction.register(npcId, "Trade", (player, npc) -> openShop(player));
            NPCAction.register(npcId, "Charter", (player, npc) -> openCharterInter(player));
        }
        InterfaceHandler.register(997, h-> {
            h.actions[2] = (SlotAction) (player, slot)-> charterTo(player, 2142, 3122, 0, "Port Tyras", 1600); //Port tyras
            h.actions[5] = (SlotAction) (player, slot)-> charterTo(player, 3702, 3503, 0, "Port Phasmatys", 1600); //Port phasmatys
            h.actions[8] = (SlotAction) (player, slot)-> charterTo(player, 2792, 3414, 0, "Catherby", 1750); //Catherby
            h.actions[11] = (SlotAction) (player, slot)-> charterTo(player, 3001, 3032, 0, "Karamja Shipyard", 1500); //Shipyard
            h.actions[14] = (SlotAction) (player, slot)-> charterTo(player, 2954, 3158, 0, "Musa Point", 1500); //Musa point
            h.actions[17] = (SlotAction) (player, slot)-> charterTo(player, 2760, 3238, 0, "Brimhaven", 1750); //Brimhaven
            h.actions[20] = (SlotAction) (player, slot)-> charterTo(player, 2674, 3144, 0, "Port Khazard", 800); //Port Khazard
            h.actions[23] = (SlotAction) (player, slot)-> charterTo(player, 3038, 3192, 0, "Port Sarim", 1600); //Port Sarim
            h.actions[26] = (SlotAction) (player, slot)-> charterTo(player, 3671, 2931, 0, "Mos Le'Harmless", 1750); //Mos Le' Harmless
            h.actions[29] = (SlotAction) (player, slot)-> charterTo(player, 2853, 3236, 0, "Crandor", 1250); //Crandor
            h.actions[32] = (SlotAction) (player, slot)-> charterTo(player, 2589, 2851, 0, "Corsair Cove", 1600); //Corsair Cove
            h.actions[33] = (SlotAction) (player, slot)-> charterTo(player, 3181, 6082, 0, "Prifddinas", 2250); //Priff
        });
    }

    public static void talkTo(Player p, NPC npc) {
        p.dialogue(
                new NPCDialogue(npc, "How can I help you?"),
                new OptionsDialogue("Select an option",
                        new Option("Open shop", CharterShipNpcs::openShop),
                        new Option("Charter a ship", CharterShipNpcs::openCharterInter),
                        new Option("Nevermind")
                        )
        );
    }

    public static void openShop(Player player) {
        ShopManager.openIfExists(player, "4d16cff7-a4b8-4351-9aae-d69kf2f7e037");
    }

    public static void openCharterInter(Player p) {
        p.openInterface(InterfaceType.MAIN, 997);
    }

    public static void charterTo(Player p, int x, int y, int z, String destination, int cost) {
        if (!p.getInventory().contains(995, cost)) {
            p.sendMessage("You need "+cost+" coins to charter to this location!");
        } else {
            p.startEvent(e -> {
                p.closeInterfaces();
                p.lock(LockType.FULL);
                p.getPacketSender().fadeOut();
                e.delay(2);
                p.getMovement().teleport(x, y, z);
                p.getPacketSender().fadeIn();
                p.sendMessage("You pay "+cost+" coins and sail to "+destination+".");
                p.unlock();
            });
        }
    }


}