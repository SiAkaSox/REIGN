package io.ruin.model.inter;

import io.ruin.Server;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.impl.PrayerAltar;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.stat.StatType;

import static io.ruin.Server.STARTUP_THREAD;

public class QuickGearInter {

    static {
        
            InterfaceHandler.register(923, h -> {
                h.actions[386] = (SlotAction) (player, slot) -> sendMaxHybrid(player);
                h.actions[387] = (SlotAction) (player, slot) -> sendMaxTribrid(player);
                h.actions[388] = (SlotAction) (player, slot) -> sendMaxMelee(player);
                h.actions[389] = (SlotAction) (player, slot) -> sendZerkHybrid(player);
                h.actions[390] = (SlotAction) (player, slot) -> sendZerkTribrid(player);
                h.actions[391] = (SlotAction) (player, slot) -> sendZerkMelee(player);
                h.actions[392] = (SlotAction) (player, slot) -> sendPureHybrid(player);
                h.actions[393] = (SlotAction) (player, slot) -> sendPureTribrid(player);
                h.actions[394] = (SlotAction) (player, slot) -> sendPureMelee(player);
                h.actions[395] = (SlotAction) (player, slot) -> sendRangeTank(player);
                h.actions[422] = (SlotAction) (player, slot) -> player.sendMessage("Work in progress. Coming soon!");
            });
    }

    public static boolean selectedMaxHybrid, selectedMaxTribrid, selectedMaxMelee, selectedZerkHybrid,
    selectedZerktribrid, selectedZerkMelee, selectedPureHybrid, selectedPureTribrid, selectedPureMelee,
    selectedRangeTank = false;
    public static boolean noneSelected = true;

    public static void sendscrollSprites(Player p) {
        p.getPacketSender().sendClientScript(917, "ii", -1, -1);
        p.getPacketSender().sendItems(923, 397, 0, new Item(3842, 1));
        p.getPacketSender().sendItems(923, 399, 0, new Item(4675, 1));
        p.getPacketSender().sendItems(923, 401, 0, new Item(4587, 1));
        p.getPacketSender().sendItems(923, 403, 0, new Item(3751, 1));
        p.getPacketSender().sendItems(923, 405, 0, new Item(4675, 1));
        p.getPacketSender().sendItems(923, 407, 0, new Item(4587, 1));
        p.getPacketSender().sendItems(923, 409, 0, new Item(6109, 1));
        p.getPacketSender().sendItems(923, 411, 0, new Item(4675, 1));
        p.getPacketSender().sendItems(923, 413, 0, new Item(4587, 1));
        p.getPacketSender().sendItems(923, 415, 0, new Item(9185, 1));
    }

    public static void openInterface (Player p) {
        if (p.isRealism) {
            p.sendMessage("Realism mode accounts may not use this feature.");
            return;
        }
        if (!p.isEasyModeWithEasyXP()) {
            p.sendMessage("Only standard easy-xp mode players can use Quick Gear!");
            return;
        }
        if (Config.IRONMAN_MODE.get(p) == 0 && p.isEasyModeWithEasyXP()) {
            if (p.getInventory().hasFreeSlots(28) && p.getEquipment().isEmpty()) {
                if (p.getPosition().regionId() == 12342) {
                    p.getMovement().reset();
                    p.getEquipment().sendUpdates();
                    sendMaxHybrid(p);
                    p.openInterface(InterfaceType.MAIN, 923);
                    sendscrollSprites(p);
                } else {
                    p.sendMessage("You can only use this feature while in Edgeville!");
                }
            } else {
                p.sendMessage("You must have an empty inventory, and no items equipped to use this feature");
            }
        } else {
            p.sendMessage("Only Normal Mode players may access this feature");
        }
    }

    public static void sendMaxHybrid (Player p) {
        Item helm = new Item(6109);
        Item chest = new Item(4101);
        Item legs = new Item(4103);
        Item weapon = new Item(4675);
        Item shield = new Item(3842);
        Item boots = new Item(3105);
        Item cape = new Item(2412);
        Item amulet = new Item(1712);
        Item gloves = new Item(7461);
        Item ring = new Item(2550);
        p.getEquipment().clear();
        p.getEquipment().sendUpdates();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, weapon);
        p.getEquipment().set(5, shield);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getEquipment().set(9, gloves);
        p.getEquipment().set(12, ring);
        sendscrollSprites(p);
        giveMaxHybrid(p);
        selectedMaxHybrid = true;
        noneSelected = false;
        p.openInterface(InterfaceType.MAIN, 923);
        p.getPacketSender().sendString(923, 15, "Quick Gear - 126 Hybrid");

        p.getPacketSender().sendItems(923, 216, 0, new Item(2503, 1));
        p.getPacketSender().sendItems(923, 218, 0, new Item(4587, 1));
        p.getPacketSender().sendItems(923, 220, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 227, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 229, 0, new Item(1079, 1));
        p.getPacketSender().sendItems(923, 231, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 233, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 235, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 237, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 239, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 241, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 243, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 245, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 247, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 309, 0, new Item(2440, 1));
        p.getPacketSender().sendItems(923, 311, 0, new Item(2436, 1));

        p.getPacketSender().sendItems(923, 313, 0, new Item(5698, 1));
        p.getPacketSender().sendItems(923, 315, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 317, 0, new Item(6685, 1));
        p.getPacketSender().sendItems(923, 319, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 321, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 323, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 325, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 327, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 329, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 331, 0, new Item(565, 2000));
        p.getPacketSender().sendItems(923, 333, 0, new Item(560, 4000));
        p.getPacketSender().sendItems(923, 335, 0, new Item(555, 6000));
    }

    public static void giveMaxHybrid(Player p) {
        PrayerAltar.switchBook(p, SpellBook.ANCIENT, false);
        p.getInventory().clear();
        p.getInventory().add(2503, 1);
        p.getInventory().add(4587, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(1079, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(2440, 1);
        p.getInventory().add(2436, 1);
        p.getInventory().add(5698, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(6685, 1);
        p.getInventory().add(6685, 1);
        p.getInventory().add(3144, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);
        p.getInventory().add(3144, 1);
        p.getInventory().add(565, 2000);
        p.getInventory().add(560, 4000);
        p.getInventory().add(555, 6000);
        p.getStats().get(StatType.Attack).set(99);
        p.getStats().get(StatType.Strength).set(99);
        p.getStats().get(StatType.Defence).set(99);
        p.getStats().get(StatType.Hitpoints).set(99);
        p.getStats().get(StatType.Magic).set(99);
        p.getStats().get(StatType.Ranged).set(99);
        p.getStats().get(StatType.Prayer).set(99);
        p.getCombat().updateLevel();
        p.getPrayer().deactivateAll();
        p.getCombat().updateLevel();
    }

    public static void sendMaxTribrid (Player p) {
        sendscrollSprites(p);
        giveMaxTribrid(p);
        p.openInterface(InterfaceType.MAIN, 923);
        Item helm = new Item(6109);
        Item chest = new Item(4101);
        Item legs = new Item(4103);
        Item weapon = new Item(4675);
        Item shield = new Item(3842);
        Item boots = new Item(3105);
        Item cape = new Item(2412);
        Item amulet = new Item(1712);
        Item gloves = new Item(7461);
        Item ring = new Item(2550);
        Item ammo = new Item(9244, 200);
        p.getEquipment().clear();
        p.getEquipment().sendUpdates();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, weapon);
        p.getEquipment().set(5, shield);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getEquipment().set(9, gloves);
        p.getEquipment().set(13, ammo);
        p.getEquipment().set(12, ring);
        p.getPacketSender().sendString(923, 15, "Quick Gear - 126 Tribrid");

        p.getPacketSender().sendItems(923, 216, 0, new Item(2503, 1));
        p.getPacketSender().sendItems(923, 218, 0, new Item(9185, 1));
        p.getPacketSender().sendItems(923, 220, 0, new Item(4587, 1));
        p.getPacketSender().sendItems(923, 227, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 229, 0, new Item(1079, 1));
        p.getPacketSender().sendItems(923, 231, 0, new Item(1201, 1));
        p.getPacketSender().sendItems(923, 233, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 235, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 237, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 239, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 241, 0, new Item(2440, 1));
        p.getPacketSender().sendItems(923, 243, 0, new Item(2444, 1));

        p.getPacketSender().sendItems(923, 245, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 247, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 309, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 311, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 313, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 315, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 317, 0, new Item(6685, 1));
        p.getPacketSender().sendItems(923, 319, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 321, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 323, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 325, 0, new Item(6685, 1));
        p.getPacketSender().sendItems(923, 327, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 329, 0, new Item(5698, 1));
        p.getPacketSender().sendItems(923, 331, 0, new Item(565, 2000));
        p.getPacketSender().sendItems(923, 333, 0, new Item(560, 4000));
        p.getPacketSender().sendItems(923, 335, 0, new Item(555, 6000));
    }

    public static void giveMaxTribrid(Player p) {
        PrayerAltar.switchBook(p, SpellBook.ANCIENT, false);
        p.getInventory().clear();
        p.getInventory().add(2503, 1);
        p.getInventory().add(9185, 1);
        p.getInventory().add(4587, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(1079, 1);
        p.getInventory().add(1201, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(2440, 1);
        p.getInventory().add(2436, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(5698, 1);
        p.getInventory().add(565, 2000);
        p.getInventory().add(560, 4000);
        p.getInventory().add(555, 6000);
        p.getStats().get(StatType.Attack).set(99);
        p.getStats().get(StatType.Strength).set(99);
        p.getStats().get(StatType.Defence).set(99);
        p.getStats().get(StatType.Hitpoints).set(99);
        p.getStats().get(StatType.Magic).set(99);
        p.getStats().get(StatType.Ranged).set(99);
        p.getStats().get(StatType.Prayer).set(99);
        p.getCombat().updateLevel();
        p.getPrayer().deactivateAll();
        p.getCombat().updateLevel();
    }

    public static void sendMaxMelee (Player p) {
        sendscrollSprites(p);
        giveMaxMelee(p);
        p.openInterface(InterfaceType.MAIN, 923);
        Item helm = new Item(10828);
        Item chest = new Item(1127);
        Item legs = new Item(1079);
        Item weapon = new Item(4587);
        Item shield = new Item(3842);
        Item boots = new Item(3105);
        Item cape = new Item(4393);
        Item amulet = new Item(1712);
        Item gloves = new Item(7461);
        Item ring = new Item(2550);
        p.getEquipment().clear();
        p.getEquipment().sendUpdates();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, weapon);
        p.getEquipment().set(5, shield);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getEquipment().set(9, gloves);
        p.getEquipment().set(12, ring);


        p.getPacketSender().sendString(923, 15, "Quick Gear - 126 Melee");

        p.getPacketSender().sendItems(923, 216, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 218, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 220, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 227, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 229, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 231, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 233, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 235, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 237, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 239, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 241, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 243, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 245, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 247, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 309, 0, new Item(2440, 1));
        p.getPacketSender().sendItems(923, 311, 0, new Item(2436, 1));

        p.getPacketSender().sendItems(923, 313, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 315, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 317, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 319, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 321, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 323, 0, new Item(5698, 1));
        p.getPacketSender().sendItems(923, 325, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 327, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 329, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 331, 0, new Item(557, 1000));
        p.getPacketSender().sendItems(923, 333, 0, new Item(560, 200));
        p.getPacketSender().sendItems(923, 335, 0, new Item(9075, 400));
    }

    public static void giveMaxMelee(Player p) {
        p.getInventory().clear();
        PrayerAltar.switchBook(p, SpellBook.LUNAR, false);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(2440, 1);
        p.getInventory().add(2436, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(3144, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(5698, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(557, 1000);
        p.getInventory().add(560, 200);
        p.getInventory().add(9075, 400);
        p.getStats().get(StatType.Attack).set(99);
        p.getStats().get(StatType.Strength).set(99);
        p.getStats().get(StatType.Defence).set(99);
        p.getStats().get(StatType.Hitpoints).set(99);
        p.getStats().get(StatType.Magic).set(99);
        p.getStats().get(StatType.Ranged).set(99);
        p.getStats().get(StatType.Prayer).set(99);
        p.getCombat().updateLevel();
        p.getPrayer().deactivateAll();
        p.getCombat().updateLevel();
    }

    public static void sendZerkHybrid (Player p) {
        sendscrollSprites(p);
        giveZerkHybrid(p);
        p.openInterface(InterfaceType.MAIN, 923);
        Item helm = new Item(3751);
        Item chest = new Item(6107);
        Item legs = new Item(6108);
        Item weapon = new Item(4675);
        Item shield = new Item(3842);
        Item boots = new Item(3105);
        Item cape = new Item(2412);
        Item amulet = new Item(1712);
        Item gloves = new Item(7460);
        Item ring = new Item(2550);
        p.getEquipment().clear();
        p.getEquipment().sendUpdates();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, weapon);
        p.getEquipment().set(5, shield);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getEquipment().set(9, gloves);
        p.getEquipment().set(12, ring);

        p.getPacketSender().sendString(923, 15, "Quick Gear - Zerk. Hybrid");

        p.getPacketSender().sendItems(923, 216, 0, new Item(2503, 1));
        p.getPacketSender().sendItems(923, 218, 0, new Item(4587, 1));
        p.getPacketSender().sendItems(923, 220, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 227, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 229, 0, new Item(1079, 1));
        p.getPacketSender().sendItems(923, 231, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 233, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 235, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 237, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 239, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 241, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 243, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 245, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 247, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 309, 0, new Item(2440, 1));
        p.getPacketSender().sendItems(923, 311, 0, new Item(2436, 1));

        p.getPacketSender().sendItems(923, 313, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 315, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 317, 0, new Item(6685, 1));
        p.getPacketSender().sendItems(923, 319, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 321, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 323, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 325, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 327, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 329, 0, new Item(5698, 1));
        p.getPacketSender().sendItems(923, 331, 0, new Item(565, 2000));
        p.getPacketSender().sendItems(923, 333, 0, new Item(560, 4000));
        p.getPacketSender().sendItems(923, 335, 0, new Item(555, 6000));
    }

    public static void giveZerkHybrid(Player p) {
        p.getInventory().clear();
        PrayerAltar.switchBook(p, SpellBook.ANCIENT, false);
        p.getInventory().add(2503, 1);
        p.getInventory().add(4587, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(1079, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(2440, 1);
        p.getInventory().add(2436, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(3144, 1);
        p.getInventory().add(6685, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(5698, 1);
        p.getInventory().add(565, 2000);
        p.getInventory().add(560, 4000);
        p.getInventory().add(555, 6000);
        p.getStats().get(StatType.Attack).set(99);
        p.getStats().get(StatType.Strength).set(99);
        p.getStats().get(StatType.Defence).set(45);
        p.getStats().get(StatType.Hitpoints).set(99);
        p.getStats().get(StatType.Magic).set(99);
        p.getStats().get(StatType.Ranged).set(99);
        p.getStats().get(StatType.Prayer).set(99);
        p.getCombat().updateLevel();
        p.getPrayer().deactivateAll();
        p.getCombat().updateLevel();
    }

    public static void sendZerkTribrid (Player p) {
        sendscrollSprites(p);
        giveZerkerTribrid(p);
        p.openInterface(InterfaceType.MAIN, 923);
        Item helm = new Item(3751);
        Item chest = new Item(6107);
        Item legs = new Item(6108);
        Item weapon = new Item(4675);
        Item shield = new Item(3842);
        Item boots = new Item(3105);
        Item cape = new Item(2412);
        Item amulet = new Item(1712);
        Item gloves = new Item(7460);
        Item ring = new Item(2550);
        Item ammo = new Item(9244, 200);
        p.getEquipment().clear();
        p.getEquipment().sendUpdates();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, weapon);
        p.getEquipment().set(5, shield);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getEquipment().set(9, gloves);
        p.getEquipment().set(13, ammo);
        p.getEquipment().set(12, ring);

        p.getPacketSender().sendString(923, 15, "Quick Gear - Zerk. Tribrid");

        p.getPacketSender().sendItems(923, 216, 0, new Item(2503, 1));
        p.getPacketSender().sendItems(923, 218, 0, new Item(9185, 1));
        p.getPacketSender().sendItems(923, 220, 0, new Item(4587, 1));
        p.getPacketSender().sendItems(923, 227, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 229, 0, new Item(1079, 1));
        p.getPacketSender().sendItems(923, 231, 0, new Item(1201, 1));
        p.getPacketSender().sendItems(923, 233, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 235, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 237, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 239, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 241, 0, new Item(2440, 1));
        p.getPacketSender().sendItems(923, 243, 0, new Item(2444, 1));

        p.getPacketSender().sendItems(923, 245, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 247, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 309, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 311, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 313, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 315, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 317, 0, new Item(6685, 1));
        p.getPacketSender().sendItems(923, 319, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 321, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 323, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 325, 0, new Item(6685, 1));
        p.getPacketSender().sendItems(923, 327, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 329, 0, new Item(5698, 1));
        p.getPacketSender().sendItems(923, 331, 0, new Item(565, 2000));
        p.getPacketSender().sendItems(923, 333, 0, new Item(560, 4000));
        p.getPacketSender().sendItems(923, 335, 0, new Item(555, 6000));
    }

    public static void giveZerkerTribrid(Player p) {
        p.getInventory().clear();
        PrayerAltar.switchBook(p, SpellBook.ANCIENT, false);
        p.getInventory().add(2503, 1);
        p.getInventory().add(9185, 1);
        p.getInventory().add(4587, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(1079, 1);
        p.getInventory().add(1201, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(2440, 1);
        p.getInventory().add(2436, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(5698, 1);
        p.getInventory().add(565, 2000);
        p.getInventory().add(560, 4000);
        p.getInventory().add(555, 6000);
        p.getStats().get(StatType.Attack).set(99);
        p.getStats().get(StatType.Strength).set(99);
        p.getStats().get(StatType.Defence).set(45);
        p.getStats().get(StatType.Hitpoints).set(99);
        p.getStats().get(StatType.Magic).set(99);
        p.getStats().get(StatType.Ranged).set(99);
        p.getStats().get(StatType.Prayer).set(99);
        p.getCombat().updateLevel();
        p.getPrayer().deactivateAll();
        p.getCombat().updateLevel();
    }

    public static void sendZerkMelee (Player p) {
        sendscrollSprites(p);
        giveZerkerMelee(p);
        p.openInterface(InterfaceType.MAIN, 923);
        Item helm = new Item(3751);
        Item chest = new Item(1127);
        Item legs = new Item(1079);
        Item weapon = new Item(4587);
        Item shield = new Item(3842);
        Item boots = new Item(3105);
        Item cape = new Item(4393);
        Item amulet = new Item(1725);
        Item gloves = new Item(7460);
        Item ring = new Item(2550);
        p.getEquipment().clear();
        p.getEquipment().sendUpdates();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, weapon);
        p.getEquipment().set(5, shield);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getEquipment().set(9, gloves);
        p.getEquipment().set(12, ring);

        p.getPacketSender().sendString(923, 15, "Quick Gear - Zerk. Melee");

        p.getPacketSender().sendItems(923, 216, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 218, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 220, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 227, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 229, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 231, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 233, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 235, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 237, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 239, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 241, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 243, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 245, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 247, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 309, 0, new Item(2440, 1));
        p.getPacketSender().sendItems(923, 311, 0, new Item(2436, 1));

        p.getPacketSender().sendItems(923, 313, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 315, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 317, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 319, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 321, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 323, 0, new Item(5698, 1));
        p.getPacketSender().sendItems(923, 325, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 327, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 329, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 331, 0, new Item(557, 1000));
        p.getPacketSender().sendItems(923, 333, 0, new Item(560, 200));
        p.getPacketSender().sendItems(923, 335, 0, new Item(9075, 400));
    }

    public static void giveZerkerMelee(Player p) {
        p.getInventory().clear();
        PrayerAltar.switchBook(p, SpellBook.LUNAR, false);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(2440, 1);
        p.getInventory().add(2436, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(3144, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(5698, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(557, 1000);
        p.getInventory().add(560, 200);
        p.getInventory().add(9075, 400);
        p.getStats().get(StatType.Attack).set(99);
        p.getStats().get(StatType.Strength).set(99);
        p.getStats().get(StatType.Defence).set(45);
        p.getStats().get(StatType.Hitpoints).set(99);
        p.getStats().get(StatType.Magic).set(99);
        p.getStats().get(StatType.Ranged).set(99);
        p.getStats().get(StatType.Prayer).set(99);
        p.getCombat().updateLevel();
        p.getPrayer().deactivateAll();
        p.getCombat().updateLevel();
    }

    public static void sendPureHybrid (Player p) {
        sendscrollSprites(p);
        givePureHybrid(p);
        p.openInterface(InterfaceType.MAIN, 923);
        Item helm = new Item(6109);
        Item chest = new Item(6107);
        Item legs = new Item(6108);
        Item weapon = new Item(4675);
        Item shield = new Item(3842);
        Item boots = new Item(3105);
        Item cape = new Item(2414);
        Item amulet = new Item(1712);
        Item gloves = new Item(7458);
        Item ring = new Item(2550);
        p.getEquipment().clear();
        p.getEquipment().sendUpdates();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, weapon);
        p.getEquipment().set(5, shield);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getEquipment().set(9, gloves);
        p.getEquipment().set(12, ring);

        p.getPacketSender().sendString(923, 15, "Quick Gear - Pure Hybrid");

        p.getPacketSender().sendItems(923, 216, 0, new Item(4587, 1));
        p.getPacketSender().sendItems(923, 218, 0, new Item(5698, 1));
        p.getPacketSender().sendItems(923, 220, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 227, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 229, 0, new Item(2497, 1));
        p.getPacketSender().sendItems(923, 231, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 233, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 235, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 237, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 239, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 241, 0, new Item(2440, 1));
        p.getPacketSender().sendItems(923, 243, 0, new Item(2436, 1));

        p.getPacketSender().sendItems(923, 245, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 247, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 309, 0, new Item(6685, 1));
        p.getPacketSender().sendItems(923, 311, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 313, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 315, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 317, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 319, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 321, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 323, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 325, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 327, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 329, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 331, 0, new Item(565, 2000));
        p.getPacketSender().sendItems(923, 333, 0, new Item(560, 4000));
        p.getPacketSender().sendItems(923, 335, 0, new Item(555, 6000));
    }

    public static void givePureHybrid(Player p) {
        p.getInventory().clear();
        PrayerAltar.switchBook(p, SpellBook.ANCIENT, false);
        p.getInventory().add(4587, 1);
        p.getInventory().add(5698, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(2497, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(2440, 1);
        p.getInventory().add(2436, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(6685, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(3144, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(565, 2000);
        p.getInventory().add(560, 4000);
        p.getInventory().add(555, 6000);
        p.getStats().get(StatType.Attack).set(99);
        p.getStats().get(StatType.Strength).set(99);
        p.getStats().get(StatType.Defence).set(1);
        p.getStats().get(StatType.Hitpoints).set(99);
        p.getStats().get(StatType.Magic).set(99);
        p.getStats().get(StatType.Ranged).set(99);
        p.getStats().get(StatType.Prayer).set(99);
        p.getCombat().updateLevel();
        p.getPrayer().deactivateAll();
        p.getCombat().updateLevel();
    }

    public static void sendPureTribrid (Player p) {
        sendscrollSprites(p);
        givePureTribrid(p);
        p.openInterface(InterfaceType.MAIN, 923);
        Item helm = new Item(6109);
        Item chest = new Item(6107);
        Item legs = new Item(6108);
        Item weapon = new Item(4675);
        Item shield = new Item(3842);
        Item boots = new Item(3105);
        Item cape = new Item(2414);
        Item amulet = new Item(1712);
        Item gloves = new Item(7458);
        Item ring = new Item(2550);
        Item ammo = new Item(9244, 200);
        p.getEquipment().clear();
        p.getEquipment().sendUpdates();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, weapon);
        p.getEquipment().set(5, shield);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getEquipment().set(9, gloves);
        p.getEquipment().set(13, ammo);
        p.getEquipment().set(12, ring);

        p.getPacketSender().sendString(923, 15, "Quick Gear - Pure Tribrid");

        p.getPacketSender().sendItems(923, 216, 0, new Item(2497, 1));
        p.getPacketSender().sendItems(923, 218, 0, new Item(10499, 1));
        p.getPacketSender().sendItems(923, 220, 0, new Item(9185, 1));
        p.getPacketSender().sendItems(923, 227, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 229, 0, new Item(4587, 1));
        p.getPacketSender().sendItems(923, 231, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 233, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 235, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 237, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 239, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 241, 0, new Item(2440, 1));
        p.getPacketSender().sendItems(923, 243, 0, new Item(2436, 1));

        p.getPacketSender().sendItems(923, 245, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 247, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 309, 0, new Item(6685, 1));
        p.getPacketSender().sendItems(923, 311, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 313, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 315, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 317, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 319, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 321, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 323, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 325, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 327, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 329, 0, new Item(5698, 1));
        p.getPacketSender().sendItems(923, 331, 0, new Item(565, 2000));
        p.getPacketSender().sendItems(923, 333, 0, new Item(560, 4000));
        p.getPacketSender().sendItems(923, 335, 0, new Item(555, 6000));
    }

    public static void givePureTribrid(Player p) {
        p.getInventory().clear();
        PrayerAltar.switchBook(p, SpellBook.ANCIENT, false);
        p.getInventory().add(4587, 1);
        p.getInventory().add(10499, 1);
        p.getInventory().add(9185, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(2497, 1);
        p.getInventory().add(5698, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(2440, 1);
        p.getInventory().add(2436, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(6685, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(3144, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(565, 2000);
        p.getInventory().add(560, 4000);
        p.getInventory().add(555, 6000);
        p.getStats().get(StatType.Attack).set(99);
        p.getStats().get(StatType.Strength).set(99);
        p.getStats().get(StatType.Defence).set(1);
        p.getStats().get(StatType.Hitpoints).set(99);
        p.getStats().get(StatType.Magic).set(99);
        p.getStats().get(StatType.Ranged).set(99);
        p.getStats().get(StatType.Prayer).set(99);
        p.getCombat().updateLevel();
        p.getPrayer().deactivateAll();
        p.getCombat().updateLevel();
    }

    public static void sendPureMelee (Player p) {
        sendscrollSprites(p);
        givePureMelee(p);
        p.openInterface(InterfaceType.MAIN, 923);
        Item helm = new Item(4502);
        Item chest = new Item(544);
        Item legs = new Item(2497);
        Item weapon = new Item(4587);
        Item shield = new Item(3842);
        Item boots = new Item(3105);
        Item cape = new Item(4393);
        Item amulet = new Item(1725);
        Item gloves = new Item(7458);
        Item ring = new Item(2550);
        p.getEquipment().clear();
        p.getEquipment().sendUpdates();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, weapon);
        p.getEquipment().set(5, shield);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getEquipment().set(9, gloves);
        p.getEquipment().set(12, ring);

        p.getPacketSender().sendString(923, 15, "Quick Gear - Pure Melee");

        p.getPacketSender().sendItems(923, 216, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 218, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 220, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 227, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 229, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 231, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 233, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 235, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 237, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 239, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 241, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 243, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 245, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 247, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 309, 0, new Item(2440, 1));
        p.getPacketSender().sendItems(923, 311, 0, new Item(2436, 1));

        p.getPacketSender().sendItems(923, 313, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 315, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 317, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 319, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 321, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 323, 0, new Item(5698, 1));
        p.getPacketSender().sendItems(923, 325, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 327, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 329, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 331, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 333, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 335, 0, new Item(385, 1));
    }

    public static void givePureMelee(Player p) {
        p.getInventory().clear();
        PrayerAltar.switchBook(p, SpellBook.LUNAR, false);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(2440, 1);
        p.getInventory().add(2436, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(3144, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(5698, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getStats().get(StatType.Attack).set(99);
        p.getStats().get(StatType.Strength).set(99);
        p.getStats().get(StatType.Defence).set(1);
        p.getStats().get(StatType.Hitpoints).set(99);
        p.getStats().get(StatType.Magic).set(99);
        p.getStats().get(StatType.Ranged).set(99);
        p.getStats().get(StatType.Prayer).set(99);
        p.getCombat().updateLevel();
        p.getPrayer().deactivateAll();
        p.getCombat().updateLevel();
    }

    public static void sendRangeTank (Player p) {
        sendscrollSprites(p);
        giveRangeTank(p);
        p.openInterface(InterfaceType.MAIN, 923);
        Item helm = new Item(10828);
        Item chest = new Item(2503);
        Item legs = new Item(1079);
        Item weapon = new Item(861);
        Item boots = new Item(6328);
        Item cape = new Item(10499);
        Item amulet = new Item(1712);
        Item gloves = new Item(7461);
        Item ring = new Item(2550);
        Item ammo = new Item (892, 200);
        p.getEquipment().clear();
        p.getEquipment().sendUpdates();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, weapon);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getEquipment().set(9, gloves);
        p.getEquipment().set(13, ammo);
        p.getEquipment().set(12, ring);

        p.getPacketSender().sendString(923, 15, "Quick Gear - Range Tank");

        p.getPacketSender().sendItems(923, 216, 0, new Item(9185, 1));
        p.getPacketSender().sendItems(923, 218, 0, new Item(1201, 1));
        p.getPacketSender().sendItems(923, 220, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 227, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 229, 0, new Item(9244, 200));
        p.getPacketSender().sendItems(923, 231, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 233, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 235, 0, new Item(385, 1));

        p.getPacketSender().sendItems(923, 237, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 239, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 241, 0, new Item(2444, 1));
        p.getPacketSender().sendItems(923, 243, 0, new Item(2440, 1));

        p.getPacketSender().sendItems(923, 245, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 247, 0, new Item(385, 1));
        p.getPacketSender().sendItems(923, 309, 0, new Item(2436, 1));
        p.getPacketSender().sendItems(923, 311, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 313, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 315, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 317, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 319, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 321, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 323, 0, new Item(5698, 1));
        p.getPacketSender().sendItems(923, 325, 0, new Item(3024, 1));
        p.getPacketSender().sendItems(923, 327, 0, new Item(6685, 1));

        p.getPacketSender().sendItems(923, 329, 0, new Item(3144, 1));
        p.getPacketSender().sendItems(923, 331, 0, new Item(557, 1000));
        p.getPacketSender().sendItems(923, 333, 0, new Item(560, 200));
        p.getPacketSender().sendItems(923, 335, 0, new Item(9075, 400));
    }

    public static void giveRangeTank(Player p) {
        p.getInventory().clear();
        PrayerAltar.switchBook(p, SpellBook.LUNAR, false);
        p.getInventory().add(9185, 1);
        p.getInventory().add(1201, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(9244, 200);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(2444, 1);
        p.getInventory().add(2440, 1);

        p.getInventory().add(385, 1);
        p.getInventory().add(385, 1);
        p.getInventory().add(2436, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(3144, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(5698, 1);
        p.getInventory().add(3024, 1);
        p.getInventory().add(6685, 1);

        p.getInventory().add(3144, 1);
        p.getInventory().add(557, 1000);
        p.getInventory().add(560, 200);
        p.getInventory().add(9075, 400);
        p.getStats().get(StatType.Attack).set(99);
        p.getStats().get(StatType.Strength).set(99);
        p.getStats().get(StatType.Defence).set(99);
        p.getStats().get(StatType.Hitpoints).set(99);
        p.getStats().get(StatType.Magic).set(99);
        p.getStats().get(StatType.Ranged).set(99);
        p.getStats().get(StatType.Prayer).set(99);
        p.getCombat().updateLevel();
        p.getPrayer().deactivateAll();
        p.getCombat().updateLevel();
    }

}