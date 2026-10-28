package io.ruin.model.map.object.actions.impl.prifddinas.amlodddistrict.gauntlet;

//import io.ruin.model.map.object.actions.impl.prifddinas.amlodddistrict.RoomType;

import io.ruin.cache.Color;
import io.ruin.model.achievements.listeners.medium.GearingUp;
import io.ruin.model.activities.pvminstances.InstancePrivacy;
import io.ruin.model.activities.pvminstances.InstanceType;
import io.ruin.model.activities.pvminstances.PVMInstance;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;

public class GauntletDef {

    /*START("Starting", RoomType.START, new Chunk[] {new Chunk(238, 709, 1)})//239, 710

    ;

    public String getName() {
        return name;
    }

    public RoomType getType() {
        return type;
    }

    *//**
     * all chambers are 4x4 chunks, so just use the "base" chunk, the most south western one.`
     * this should either be size 1 or 3.
     * size 1 for "dead end" rooms like the floor-ending room, and 3 for other rooms following the same logic as they are in the map:
     * [0] -> exit to the west of the entrance
     * [1] -> exit on the opposite of the entrance
     * [2] -> exit to the east of the entrance
     * (west/east directions assume the room hasn't been rotated, as is their default state in the map)
     *//*
    private Chunk[] baseChunks;

    private String name;
    private RoomType type;

    GauntletDef(String name, RoomType type, Chunk[] baseChunks) {
        this.name = name;
        this.type = type;
        this.baseChunks = baseChunks;
    }

    public Chunk getBaseChunk(int layout) {
        if (layout < 0 || layout >= baseChunks.length)
            throw new IllegalArgumentException();
        return baseChunks[layout];
    }*/

    static {
        //Gauntlet portal
        ObjectAction.register(37340, 1, (player, obj) -> enterInstance(player));
        ObjectAction.register(36083, 1, (player, obj) -> enterInstance(player));
        //Object to spawn gear
        ObjectAction.register(36074, 1, (player, obj) -> suppliesObject(player));
        ObjectAction.register(35977, 1, (player, obj) -> suppliesObject(player));
        //Hunllef Door
        final int[] BARRIER = {
                37339,//barrier
                37337//corrupt
        };
        for (int barriers : BARRIER) {
            ObjectAction.register(barriers, 1, (player, obj) -> {
                if (player.getAbsY() < obj.y)
                    teleportPlayer(player, player.getAbsX(), obj.y + 1, player.getPosition().getZ());
            });
            ObjectAction.register(barriers, 2, (player, obj) -> {
                if (player.getAbsY() < obj.y)
                    teleportPlayer(player, player.getAbsX(), obj.y + 1, player.getPosition().getZ());
            });
        }
        //Gear interface
        InterfaceHandler.register(983, (h -> {
            h.actions[62] = (SlotAction) (player, option) -> addItem(player, 23887);
            h.actions[83] = (SlotAction) (player, option) -> addItem(player, 23890);
            h.actions[87] = (SlotAction) (player, option) -> addItem(player, 23893);
            h.actions[89] = (SlotAction) (player, option) -> addItem(player, 23896);
            h.actions[91] = (SlotAction) (player, option) -> addItem(player, 23899);
            h.actions[93] = (SlotAction) (player, option) -> addItem(player, 23902);
            h.actions[95] = (SlotAction) (player, option) -> addTier3(player, 23888);
            h.actions[97] = (SlotAction) (player, option) -> addTier3(player, 23891);
            h.actions[99] = (SlotAction) (player, option) -> addTier3(player, 23894);
            h.actions[101] = (SlotAction) (player, option) -> addTier3(player, 23897);
            h.actions[103] = (SlotAction) (player, option) -> addTier3(player, 23900);
            h.actions[105] = (SlotAction) (player, option) -> addTier3(player, 23903);
            h.actions[107] = (SlotAction) (player, option) -> addItem(player, 23885);
            h.actions[109] = (SlotAction) (player, option) -> addItem(player, 23874);
        }));
    }

    private static void teleportPlayer(Player player, int x, int y, int z) {
        if (player.getCombat().checkTb())
            return;
        player.startEvent(event -> {
            player.lock();
            player.animate(4282);
            event.delay(1);
            player.getMovement().teleport(x, y, z);
            event.delay(1);
            player.unlock();
        });
    }

    public static void addItem(Player p, int id) {
        p.getInventory().addOrDrop(id, 1);
        GearingUp.complete(p);
    }

    public static void addTier3(Player p, int id) {
        if (p.storeAmountSpent < 15) {
            p.sendMessage(Color.RED.wrap("You must have a Sapphire or higher donator rank to use tier 3 items!"));
            return;
        }
        p.getInventory().addOrDrop(id, 1);
        GearingUp.complete(p);
    }

    public static void enterInstance(Player p) {
        if (Config.GAUNTLET_REWARD.get(p) > 0) {
            p.sendMessage(Color.RED.wrap("You must collect your rewards from the chest before re-entering!"));
            return;
        }
        //p.sendMessage(Color.RED.wrap("This feature is currently undergoing construction."));
        if (p.getInventory().isEmpty() && p.getEquipment().isEmpty()) {
            p.dialogue(
                    new OptionsDialogue("Select an option",
                            new Option("Normal Gauntlet", () -> normalGauntlet(p)),
                            new Option("Corrupted Gauntlet", () -> corruptedGauntlet(p)),
                            new Option("Nevermind")
                    )
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You cannot bring any items into the instance!")
            );
        }
    }

    private static void normalGauntlet(Player player) {
        PVMInstance instance = new PVMInstance(player, InstanceType.HUNLLEF, InstancePrivacy.PRIVATE, "glmate");
        instance.enter(player);
    }

    private static void corruptedGauntlet(Player player) {
        PVMInstance instance = new PVMInstance(player, InstanceType.CORRUPTED_HUNLLEF, InstancePrivacy.PRIVATE, "glmate");
        instance.enter(player);
    }

    public static void suppliesObject(Player p) {
        p.openInterface(InterfaceType.MAIN, 983);
        p.getPacketSender().sendItems(983, 63, 0, new Item(23887, 1));
        p.getPacketSender().sendItems(983, 84, 0, new Item(23890, 1));
        p.getPacketSender().sendItems(983, 88, 0, new Item(23893, 1));
        p.getPacketSender().sendItems(983, 90, 0, new Item(23896, 1));
        p.getPacketSender().sendItems(983, 92, 0, new Item(23899, 1));
        p.getPacketSender().sendItems(983, 94, 0, new Item(23902, 1));

        p.getPacketSender().sendItems(983, 96, 0, new Item(23888, 1));
        p.getPacketSender().sendItems(983, 98, 0, new Item(23891, 1));
        p.getPacketSender().sendItems(983, 100, 0, new Item(23894, 1));
        p.getPacketSender().sendItems(983, 102, 0, new Item(23897, 1));
        p.getPacketSender().sendItems(983, 104, 0, new Item(23900, 1));
        p.getPacketSender().sendItems(983, 106, 0, new Item(23903, 1));
        p.getPacketSender().sendItems(983, 108, 0, new Item(23885, 1));
        p.getPacketSender().sendItems(983, 110, 0, new Item(23874, 1));

    }
}
