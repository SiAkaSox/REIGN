package io.ruin.model.inter;

import io.ruin.Server;
import io.ruin.model.entity.npc.actions.edgeville.StarterGuide;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.stat.StatType;

import static io.ruin.Server.STARTUP_THREAD;

public class GameModeSelectionInter {

    static {

            InterfaceHandler.register(888, h -> {
                h.actions[152] = (SlotAction) (p, slot) -> openStandardInfo(p);
                h.actions[154] = (SlotAction) (p, slot) -> openIronmanModesScreen(p);
                h.actions[178] = (SlotAction) (p, slot) -> openPvPModeScreen(p);
            });
            InterfaceHandler.register(889, h -> {
                h.actions[152] = (SlotAction) (p, slot) -> openIronmanInfo(p);
                h.actions[178] = (SlotAction) (p, slot) -> openHardcoreIronmanInfo(p);
                h.actions[154] = (SlotAction) (p, slot) -> openUltimateIronmanInfo(p);
                h.actions[181] = (SlotAction) (p, slot) -> openGameModeScreen(p);
            });
            InterfaceHandler.register(918, h -> {
                h.actions[152] = (SlotAction) (p, slot) -> confirmpickPvPModeScreen(p);
                h.actions[250] = (SlotAction) (p, slot) -> openGameModeScreen(p);
            });
            InterfaceHandler.register(919, h -> {
                h.actions[283] = (SlotAction) (p, slot) -> setpvpMode(p);
                h.actions[284] = (SlotAction) (p, slot) -> openPvPModeScreen(p);
            });
            InterfaceHandler.register(891, h -> {
                h.actions[152] = (SlotAction) (p, slot) -> confirmPickStandardScreen(p);
                h.actions[250] = (SlotAction) (p, slot) -> openGameModeScreen(p);
            });
            InterfaceHandler.register(892, h -> {
                h.actions[283] = (SlotAction) (p, slot) -> setStandardMode(p);
                h.actions[284] = (SlotAction) (p, slot) -> openStandardInfo(p);
            });
            InterfaceHandler.register(893, h -> {
                h.actions[152] = (SlotAction) (p, slot) -> confirmPickIronmanScreen(p);
                h.actions[250] = (SlotAction) (p, slot) -> openIronmanModesScreen(p);
            });
            InterfaceHandler.register(894, h -> {
                h.actions[283] = (SlotAction) (p, slot) -> setIronmanMode(p);
                h.actions[284] = (SlotAction) (p, slot) -> openIronmanInfo(p);
            });
            InterfaceHandler.register(895, h -> {
                h.actions[152] = (SlotAction) (p, slot) -> confirmPickHardcoreIronmanScreen(p);
                h.actions[252] = (SlotAction) (p, slot) -> openIronmanModesScreen(p);
            });
            InterfaceHandler.register(896, h -> {
                h.actions[283] = (SlotAction) (p, slot) -> setHCIMMode(p);
                h.actions[284] = (SlotAction) (p, slot) -> openHardcoreIronmanInfo(p);
            });
            InterfaceHandler.register(897, h -> {
                h.actions[152] = (SlotAction) (p, slot) -> confirmPickUltimateIronmanScreen(p);
                h.actions[252] = (SlotAction) (p, slot) -> openIronmanModesScreen(p);
            });
            InterfaceHandler.register(898, h -> {
                h.actions[283] = (SlotAction) (p, slot) -> setUIMMode(p);
                h.actions[284] = (SlotAction) (p, slot) -> openUltimateIronmanInfo(p);
            });
    }

    public static void openGameModeScreen(Player p) {
        p.openInterface(InterfaceType.MAIN, 888);
        p.getPacketSender().sendItem(888, 181, 5698, 1);
    }
    public static void openIronmanModesScreen(Player p) {
        p.openInterface(InterfaceType.MAIN, 889);
    }

    public static void setStandardMode(Player p) {
        p.ispvpMode = false;
        Config.IRONMAN_MODE.set(p, 0);
        StarterGuide.continueTut(p);
    }
    public static void setIronmanMode(Player p) {
        p.ispvpMode = false;
        Config.IRONMAN_MODE.set(p, 1);
        StarterGuide.continueTut(p);
    }
    public static void setHCIMMode(Player p) {
        p.ispvpMode = false;
        Config.IRONMAN_MODE.set(p, 3);
        StarterGuide.continueTut(p);
    }
    public static void setUIMMode(Player p) {
        p.ispvpMode = false;
        Config.IRONMAN_MODE.set(p, 2);
        StarterGuide.continueTut(p);
    }
    public static void setpvpMode(Player p) {
        p.ispvpMode = true;
        Config.IRONMAN_MODE.set(p, 0);
        StarterGuide.continueTut(p);
    }

    public static void sendItems(Player p, int helm, int chest, int legs, int sword, int shield, int boots, int cape, int amulet) {
        p.getEquipment().clear();
        Item helm1 = new Item(helm);
        Item chest1 = new Item(chest);
        Item legs1 = new Item(legs);
        Item sword1 = new Item(sword);
        Item shield1 = new Item(shield);
        Item boots1 = new Item(boots);
        Item cape1 = new Item(cape);
        Item amulet1 = new Item(amulet);
        p.getEquipment().set(0, helm1);
        p.getEquipment().set(4, chest1);
        p.getEquipment().set(7, legs1);
        p.getEquipment().set(3, sword1);
        p.getEquipment().set(5, shield1);
        p.getEquipment().set(10, boots1);
        p.getEquipment().set(1, cape1);
        p.getEquipment().set(2, amulet1);
        p.getEquipment().sendUpdates();
    }

    public static void openStandardInfo(Player p) {
        p.getPacketSender().sendItems(891, 216, 0, new Item(995, 250000));
        p.getPacketSender().sendItems(891, 218, 0, new Item(558, 500));
        p.getPacketSender().sendItems(891, 220, 0, new Item(556, 1500));
        p.getPacketSender().sendItems(891, 227, 0, new Item(554, 1500));
        p.getPacketSender().sendItems(891, 229, 0, new Item(555, 1500));
        p.getPacketSender().sendItems(891, 231, 0, new Item(557, 1500));
        p.getPacketSender().sendItems(891, 233, 0, new Item(13385));
        p.getPacketSender().sendItems(891, 235, 0, new Item(13387));
        p.getPacketSender().sendItems(891, 237, 0, new Item(1169));
        p.getPacketSender().sendItems(891, 239, 0, new Item(1129));
        p.getPacketSender().sendItems(891, 241, 0, new Item(1095));
        p.getPacketSender().sendItems(891, 243, 0, new Item(863, 1000));
        p.getPacketSender().sendItems(891, 245, 0, new Item(386, 100));
        p.getPacketSender().sendItems(891, 247, 0, new Item(1333));
        p.getPacketSender().sendItems(891, 249, 0, new Item(8013, 50));
        p.getMovement().reset();
        p.getPacketSender().sendClientScript(917, "ii", -1, -1);
        p.openInterface(InterfaceType.MAIN, 891);
        sendItems(p, 1153, 1115, 1067, 1323, 1191, 3105, 4413, 1725);
    }
    public static void confirmPickStandardScreen(Player p) {
        p.getPacketSender().sendItems(892, 216, 0, new Item(995, 250000));
        p.getPacketSender().sendItems(892, 218, 0, new Item(558, 500));
        p.getPacketSender().sendItems(892, 220, 0, new Item(556, 1500));
        p.getPacketSender().sendItems(892, 227, 0, new Item(554, 1500));
        p.getPacketSender().sendItems(892, 229, 0, new Item(555, 1500));
        p.getPacketSender().sendItems(892, 231, 0, new Item(557, 1500));
        p.getPacketSender().sendItems(892, 233, 0, new Item(13385));
        p.getPacketSender().sendItems(892, 235, 0, new Item(13387));
        p.getPacketSender().sendItems(892, 237, 0, new Item(1169));
        p.getPacketSender().sendItems(892, 239, 0, new Item(1129));
        p.getPacketSender().sendItems(892, 241, 0, new Item(1095));
        p.getPacketSender().sendItems(892, 243, 0, new Item(863, 1000));
        p.getPacketSender().sendItems(892, 245, 0, new Item(386, 100));
        p.getPacketSender().sendItems(892, 247, 0, new Item(1333));
        p.getPacketSender().sendItems(892, 249, 0, new Item(8013, 50));
        p.getMovement().reset();
        p.getPacketSender().sendClientScript(917, "ii", -1, -1);
        p.openInterface(InterfaceType.MAIN, 892);
        sendItems(p, 1153, 1115, 1067, 1323, 1191, 3105, 4413, 1725);
    }
    public static void openPvPModeScreen(Player p) {
        p.getStats().set(StatType.Attack, 99);
        p.getStats().set(StatType.Strength, 99);
        p.getStats().set(StatType.Defence, 99);
        p.getStats().set(StatType.Magic, 99);
        p.getStats().set(StatType.Ranged, 99);
        p.getStats().set(StatType.Prayer, 99);
        p.getStats().set(StatType.Hitpoints, 99);
        p.getCombat().updateLevel();
        p.getPacketSender().sendItem(918, 251, 5698, 1);
        p.getPacketSender().sendItems(918, 216, 0, new Item(3751, 50));
        p.getPacketSender().sendItems(918, 218, 0, new Item(1127, 50));
        p.getPacketSender().sendItems(918, 220, 0, new Item(1079, 50));
        p.getPacketSender().sendItems(918, 227, 0, new Item(4587, 50));
        p.getPacketSender().sendItems(918, 229, 0, new Item(1201, 50));
        p.getPacketSender().sendItems(918, 231, 0, new Item(3105, 50));
        p.getPacketSender().sendItems(918, 233, 0, new Item(10499, 50));
        p.getPacketSender().sendItems(918, 235, 0, new Item(1712, 50));
        p.getPacketSender().sendItems(918, 237, 0, new Item(385, 100000));
        p.getPacketSender().sendItems(918, 239, 0, new Item(565, 100000));
        p.getPacketSender().sendItems(918, 241, 0, new Item(560, 100000));
        p.getPacketSender().sendItems(918, 243, 0, new Item(555, 100000));
        p.getPacketSender().sendItems(918, 245, 0, new Item(892, 100000));
        p.getPacketSender().sendItems(918, 247, 0, new Item(868, 100000));
        p.getMovement().reset();
        p.getPacketSender().sendClientScript(917, "ii", -1, -1);
        p.openInterface(InterfaceType.MAIN, 918);
        sendItems(p, 3751, 1127, 1079, 4587, 1201, 3105, 10499, 1712);
    }
    public static void confirmpickPvPModeScreen(Player p) {
        p.getStats().set(StatType.Attack, 99);
        p.getStats().set(StatType.Strength, 99);
        p.getStats().set(StatType.Defence, 99);
        p.getStats().set(StatType.Magic, 99);
        p.getStats().set(StatType.Ranged, 99);
        p.getStats().set(StatType.Prayer, 99);
        p.getStats().set(StatType.Hitpoints, 99);
        p.getPacketSender().sendItems(919, 216, 0, new Item(3751, 50));
        p.getPacketSender().sendItems(919, 218, 0, new Item(1127, 50));
        p.getPacketSender().sendItems(919, 220, 0, new Item(1079, 50));
        p.getPacketSender().sendItems(919, 227, 0, new Item(4587, 50));
        p.getPacketSender().sendItems(919, 229, 0, new Item(1201, 50));
        p.getPacketSender().sendItems(919, 231, 0, new Item(3105, 50));
        p.getPacketSender().sendItems(919, 233, 0, new Item(10499, 50));
        p.getPacketSender().sendItems(919, 235, 0, new Item(1712, 50));
        p.getPacketSender().sendItems(919, 237, 0, new Item(385, 100000));
        p.getPacketSender().sendItems(919, 239, 0, new Item(565, 100000));
        p.getPacketSender().sendItems(919, 241, 0, new Item(560, 100000));
        p.getPacketSender().sendItems(919, 243, 0, new Item(555, 100000));
        p.getPacketSender().sendItems(919, 245, 0, new Item(892, 100000));
        p.getPacketSender().sendItems(919, 247, 0, new Item(868, 100000));
        p.getMovement().reset();
        p.getPacketSender().sendClientScript(917, "ii", -1, -1);
        p.openInterface(InterfaceType.MAIN, 919);
        sendItems(p, 3751, 1127, 1079, 4587, 1201, 3105, 10499, 1712);
    }
    public static void openIronmanInfo(Player p) {
        p.getPacketSender().sendItems(893, 216, 0, new Item(995, 50000));
        p.getPacketSender().sendItems(893, 218, 0, new Item(558, 100));
        p.getPacketSender().sendItems(893, 220, 0, new Item(556, 500));
        p.getPacketSender().sendItems(893, 227, 0, new Item(554, 500));
        p.getPacketSender().sendItems(893, 229, 0, new Item(555, 500));
        p.getPacketSender().sendItems(893, 231, 0, new Item(557, 500));
        p.getPacketSender().sendItems(893, 233, 0, new Item(13385));
        p.getPacketSender().sendItems(893, 235, 0, new Item(13387));
        p.getPacketSender().sendItems(893, 237, 0, new Item(1169));
        p.getPacketSender().sendItems(893, 239, 0, new Item(1129));
        p.getPacketSender().sendItems(893, 241, 0, new Item(1095));
        p.getPacketSender().sendItems(893, 243, 0, new Item(9706, 200));
        p.getPacketSender().sendItems(893, 245, 0, new Item(386, 100));
        p.getPacketSender().sendItems(893, 247, 0, new Item(9705));
        p.getPacketSender().sendItems(893, 249, 0, new Item(8013, 50));
        p.getMovement().reset();
        p.getPacketSender().sendClientScript(917, "ii", -1, -1);
        p.openInterface(InterfaceType.MAIN, 893);
        sendItems(p, 12810, 12811, 12812, 9703, 9704, 1061, 4413, 1478);
    }
    public static void confirmPickIronmanScreen(Player p) {
        p.getPacketSender().sendItems(894, 216, 0, new Item(995, 50000));
        p.getPacketSender().sendItems(894, 218, 0, new Item(558, 100));
        p.getPacketSender().sendItems(894, 220, 0, new Item(556, 500));
        p.getPacketSender().sendItems(894, 227, 0, new Item(554, 500));
        p.getPacketSender().sendItems(894, 229, 0, new Item(555, 500));
        p.getPacketSender().sendItems(894, 231, 0, new Item(557, 500));
        p.getPacketSender().sendItems(894, 233, 0, new Item(13385));
        p.getPacketSender().sendItems(894, 235, 0, new Item(13387));
        p.getPacketSender().sendItems(894, 237, 0, new Item(1169));
        p.getPacketSender().sendItems(894, 239, 0, new Item(1129));
        p.getPacketSender().sendItems(894, 241, 0, new Item(1095));
        p.getPacketSender().sendItems(894, 243, 0, new Item(9706, 200));
        p.getPacketSender().sendItems(894, 245, 0, new Item(386, 100));
        p.getPacketSender().sendItems(894, 247, 0, new Item(9705));
        p.getPacketSender().sendItems(894, 249, 0, new Item(8013, 50));
        p.getMovement().reset();
        p.getPacketSender().sendClientScript(917, "ii", -1, -1);
        p.openInterface(InterfaceType.MAIN, 894);
        sendItems(p, 12810, 12811, 12812, 9703, 9704, 1061, 4413, 1478);
    }
    public static void openHardcoreIronmanInfo(Player p) {
        p.getPacketSender().sendItems(895, 216, 0, new Item(995, 50000));
        p.getPacketSender().sendItems(895, 218, 0, new Item(558, 100));
        p.getPacketSender().sendItems(895, 220, 0, new Item(556, 500));
        p.getPacketSender().sendItems(895, 227, 0, new Item(554, 500));
        p.getPacketSender().sendItems(895, 229, 0, new Item(555, 500));
        p.getPacketSender().sendItems(895, 231, 0, new Item(557, 500));
        p.getPacketSender().sendItems(895, 233, 0, new Item(13385));
        p.getPacketSender().sendItems(895, 235, 0, new Item(13387));
        p.getPacketSender().sendItems(895, 237, 0, new Item(1169));
        p.getPacketSender().sendItems(895, 239, 0, new Item(1129));
        p.getPacketSender().sendItems(895, 241, 0, new Item(1095));
        p.getPacketSender().sendItems(895, 243, 0, new Item(9706, 200));
        p.getPacketSender().sendItems(895, 245, 0, new Item(386, 100));
        p.getPacketSender().sendItems(895, 247, 0, new Item(9705));
        p.getPacketSender().sendItems(895, 249, 0, new Item(8013, 50));
        p.getMovement().reset();
        p.getPacketSender().sendClientScript(917, "ii", -1, -1);
        p.openInterface(InterfaceType.MAIN, 895);
        sendItems(p, 20792, 20794, 20796, 9703, 9704, 1061, 4413, 1478);
    }
    public static void confirmPickHardcoreIronmanScreen(Player p) {
        p.getPacketSender().sendItems(896, 216, 0, new Item(995, 50000));
        p.getPacketSender().sendItems(896, 218, 0, new Item(558, 100));
        p.getPacketSender().sendItems(896, 220, 0, new Item(556, 500));
        p.getPacketSender().sendItems(896, 227, 0, new Item(554, 500));
        p.getPacketSender().sendItems(896, 229, 0, new Item(555, 500));
        p.getPacketSender().sendItems(896, 231, 0, new Item(557, 500));
        p.getPacketSender().sendItems(896, 233, 0, new Item(13385));
        p.getPacketSender().sendItems(896, 235, 0, new Item(13387));
        p.getPacketSender().sendItems(896, 237, 0, new Item(1169));
        p.getPacketSender().sendItems(896, 239, 0, new Item(1129));
        p.getPacketSender().sendItems(896, 241, 0, new Item(1095));
        p.getPacketSender().sendItems(896, 243, 0, new Item(9706, 200));
        p.getPacketSender().sendItems(896, 245, 0, new Item(386, 100));
        p.getPacketSender().sendItems(896, 247, 0, new Item(9705));
        p.getPacketSender().sendItems(896, 249, 0, new Item(8013, 50));
        p.getMovement().reset();
        p.getPacketSender().sendClientScript(917, "ii", -1, -1);
        p.openInterface(InterfaceType.MAIN, 896);
        sendItems(p, 20792, 20794, 20796, 9703, 9704, 1061, 4413, 1478);
    }
    public static void openUltimateIronmanInfo(Player p) {
        p.getPacketSender().sendItems(897, 216, 0, new Item(995, 50000));
        p.getPacketSender().sendItems(897, 218, 0, new Item(558, 100));
        p.getPacketSender().sendItems(897, 220, 0, new Item(556, 500));
        p.getPacketSender().sendItems(897, 227, 0, new Item(554, 500));
        p.getPacketSender().sendItems(897, 229, 0, new Item(555, 500));
        p.getPacketSender().sendItems(897, 231, 0, new Item(557, 500));
        p.getPacketSender().sendItems(897, 233, 0, new Item(13385));
        p.getPacketSender().sendItems(897, 235, 0, new Item(13387));
        p.getPacketSender().sendItems(897, 237, 0, new Item(1169));
        p.getPacketSender().sendItems(897, 239, 0, new Item(1129));
        p.getPacketSender().sendItems(897, 241, 0, new Item(1095));
        p.getPacketSender().sendItems(897, 243, 0, new Item(9706, 200));
        p.getPacketSender().sendItems(897, 245, 0, new Item(386, 100));
        p.getPacketSender().sendItems(897, 247, 0, new Item(9705));
        p.getPacketSender().sendItems(897, 249, 0, new Item(8013, 50));
        p.getMovement().reset();
        p.getPacketSender().sendClientScript(917, "ii", -1, -1);
        p.openInterface(InterfaceType.MAIN, 897);
        sendItems(p, 12813, 12814, 12815, 9703, 9704, 1061, 4413, 1478);
    }
    public static void confirmPickUltimateIronmanScreen(Player p) {
        p.getPacketSender().sendItems(898, 216, 0, new Item(995, 50000));
        p.getPacketSender().sendItems(898, 218, 0, new Item(558, 100));
        p.getPacketSender().sendItems(898, 220, 0, new Item(556, 500));
        p.getPacketSender().sendItems(898, 227, 0, new Item(554, 500));
        p.getPacketSender().sendItems(898, 229, 0, new Item(555, 500));
        p.getPacketSender().sendItems(898, 231, 0, new Item(557, 500));
        p.getPacketSender().sendItems(898, 233, 0, new Item(13385));
        p.getPacketSender().sendItems(898, 235, 0, new Item(13387));
        p.getPacketSender().sendItems(898, 237, 0, new Item(1169));
        p.getPacketSender().sendItems(898, 239, 0, new Item(1129));
        p.getPacketSender().sendItems(898, 241, 0, new Item(1095));
        p.getPacketSender().sendItems(898, 243, 0, new Item(9706, 200));
        p.getPacketSender().sendItems(898, 245, 0, new Item(386, 100));
        p.getPacketSender().sendItems(898, 247, 0, new Item(9705));
        p.getPacketSender().sendItems(898, 249, 0, new Item(8013, 50));
        p.getMovement().reset();
        p.getPacketSender().sendClientScript(917, "ii", -1, -1);
        p.openInterface(InterfaceType.MAIN, 898);
        sendItems(p, 12813, 12814, 12815, 9703, 9704, 1061, 4413, 1478);
    }

}