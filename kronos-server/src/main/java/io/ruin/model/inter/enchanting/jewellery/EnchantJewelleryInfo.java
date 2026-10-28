package io.ruin.model.inter.enchanting.jewellery;

import io.ruin.Server;
import io.ruin.model.achievements.listeners.medium.Enchanter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.enchanting.EnchantButton;
import io.ruin.model.inter.enchanting.Enchanting;
import io.ruin.model.item.Item;

import static io.ruin.Server.STARTUP_THREAD;
import static io.ruin.model.inter.enchanting.jewellery.EnchantJewelleryData.sendNameList;

public class EnchantJewelleryInfo {

    public static int JEWELLERY_INTER = 961;

    public static int SHARDS = 25676;
    public static int GP = 995;

    public static boolean selectedFury, selectedTorture, selectedDamned, selectedArchers, selectedBerserker,
    selectedBrimstone, selectedGranite, selectedAnguish, selectedRecoil, selectedSuffering, selectedGods,
    selectedSeers, selectedTormented, selectedTreasonous, selectedBloodFury, selectedOccult = false;

    public static void clearSelections() {
        selectedTorture=false; selectedDamned=false; selectedArchers=false; selectedBerserker=false;
        selectedBrimstone=false; selectedGranite=false; selectedAnguish=false; selectedRecoil=false; selectedSuffering=false;
        selectedGods=false; selectedSeers=false; selectedTormented=false; selectedTreasonous=false;
        selectedBloodFury=false; selectedOccult = false; selectedFury = false;
    }
    public static void refreshInter(Player p) {
        p.openInterface(InterfaceType.MAIN, JEWELLERY_INTER);
        sendNameList(p);
    }
    public static void sendonSelect(Player p, String newItemName, String description, String highlitedName, int highlightedChild, int newItem, int oldItem, int shardAmount, int gpAmount) {
        p.getPacketSender().sendString(JEWELLERY_INTER, 92, ""+newItemName);//new item name
        p.getPacketSender().sendString(JEWELLERY_INTER, 162, ""+description);//description
        p.getPacketSender().sendString(JEWELLERY_INTER, highlightedChild, "<col=FFFFFF>"+highlitedName);//selected name
        p.getPacketSender().sendItems(JEWELLERY_INTER, 196, 0, new Item(newItem));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 210, 0, new Item(oldItem));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 212, 0, new Item(SHARDS, shardAmount));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 214, 0, new Item(GP, gpAmount));
    }
    public static void removeandReplace(Player p, int item, int shards, int coins, int newItem) {
        p.getInventory().remove(item, 1);
        p.getInventory().remove(SHARDS, shards);
        p.getInventory().remove(GP, coins);
        p.getInventory().addOrDrop(newItem, 1);
    }
    public static void successMessage(Player p, int item, String itemName) {
        p.dialogue(new ItemDialogue().one(item, "Congratulations, you've enchanted your "+itemName+"!"));
        Enchanter.complete(p);
    }
    public static void failMessage(Player p) {
        p.dialogue(new MessageDialogue("You do not have the required materials to enchant this item."));
    }
    public static void sendFury(Player p) {
        clearSelections();
        selectedFury = true;
        refreshInter(p);
        sendonSelect(p, "Amulet of Fury (e)", "Provides a 3% chance on each hit to restore up to 12 prayer points", "Amulet of Fury", 168, 31029, 6585, 115000, 40000000);
    }
    public static void upgradeFury(Player p) {
        if (p.getInventory().contains(6585, 1)
                && p.getInventory().contains(SHARDS, 115000)
                && p.getInventory().contains(GP, 40000000)) {
           removeandReplace(p, 6585, 115000, 40000000, 31029);
           successMessage(p, 31029, "Amulet of Fury");
        } else {
            failMessage(p);
        }
    }
    public static void sendTorture(Player p) {
        clearSelections();
        selectedTorture = true;
        refreshInter(p);
        sendonSelect(p, "Amulet of Torture (e)", "Upgrades the amulet to gain an additional +2 prayer bonus", "Amulet of Torture", 177, 31030, 19553, 125000, 50000000);
    }
    public static void upgradeTorture(Player p) {
        if (p.getInventory().contains(19553, 1)
                && p.getInventory().contains(SHARDS, 125000)
                && p.getInventory().contains(GP, 50000000)) {
            removeandReplace(p, 19553, 125000, 50000000, 31030);
            successMessage(p, 31030, "Amulet of Torture");
        } else {
            failMessage(p);
        }
    }
    public static void sendDamned(Player p) {
        clearSelections();
        selectedDamned = true;
        refreshInter(p);
        sendonSelect(p, "Amulet of the Damned (e)", "Provides a 15% chance of receiving 2x the loot from barrows chests", "Amulet of the Damned", 184, 31031, 12853, 125000, 50000000);
    }
    public static void upgradeDamned(Player p) {
        if (p.getInventory().contains(12853, 1)
                && p.getInventory().contains(SHARDS, 125000)
                && p.getInventory().contains(GP, 50000000)) {
            removeandReplace(p, 12853, 125000, 50000000, 31031);
            successMessage(p, 31031, "Amulet of the Damned");
        } else {
            failMessage(p);
        }
    }
    public static void sendArchers(Player p) {
        clearSelections();
        selectedArchers = true;
        refreshInter(p);
        sendonSelect(p, "Archers Ring (e)", "Increases the rings range attack bonus by +2", "Archers Ring", 221, 31032, 6733, 150000, 75000000);
    }
    public static void upgradeArchers(Player p) {
        if (p.getInventory().contains(6733, 1)
                && p.getInventory().contains(SHARDS, 150000)
                && p.getInventory().contains(GP, 75000000)) {
            removeandReplace(p, 6733, 150000, 75000000, 31032);
            successMessage(p, 31032, "Archers Ring");
        } else {
            failMessage(p);
        }
    }
    public static void sendBerserker(Player p) {
        clearSelections();
        selectedBerserker = true;
        refreshInter(p);
        sendonSelect(p, "Berserker Ring (e)", "Upgrades the rings melee strength bonus by +2", "Berserker Ring", 230, 31033, 6737, 150000, 75000000);
    }
    public static void upgradeBerserker(Player p) {
        if (p.getInventory().contains(6737, 1)
                && p.getInventory().contains(SHARDS, 150000)
                && p.getInventory().contains(GP, 75000000)) {
            removeandReplace(p, 6737, 150000, 75000000, 31033);
            successMessage(p, 31033, "Berserker Ring");
        } else {
            failMessage(p);
        }
    }
    public static void sendBrimstone(Player p) {
        clearSelections();
        selectedBrimstone = true;
        refreshInter(p);
        sendonSelect(p, "Brimstone Ring (e)", "Upgrades the ring to gain a +2 prayer bonus", "Brimstone Ring", 239, 31034, 22975, 215000, 200000000);
    }
    public static void upgradeBrimstone(Player p) {
        if (p.getInventory().contains(22975, 1)
                && p.getInventory().contains(SHARDS, 215000)
                && p.getInventory().contains(GP, 200000000)) {
            removeandReplace(p, 22975, 215000, 200000000, 31034);
            successMessage(p, 31034, "Brimstone Ring");
        } else {
            failMessage(p);
        }
    }
    public static void sendGranite(Player p) {
        clearSelections();
        selectedGranite = true;
        refreshInter(p);
        sendonSelect(p, "Granite Ring (e)", "Upgrades the ring to have no stat requirements to equip", "Granite Ring", 248, 31035, 21739, 60000, 15000000);
    }
    public static void upgradeGranite(Player p) {
        if (p.getInventory().contains(21739, 1)
                && p.getInventory().contains(SHARDS, 60000)
                && p.getInventory().contains(GP, 15000000)) {
            removeandReplace(p, 21739, 60000, 15000000, 31035);
            successMessage(p, 31035, "Granite Ring");
        } else {
            failMessage(p);
        }
    }
    public static void sendAnguish(Player p) {
        clearSelections();
        selectedAnguish = true;
        refreshInter(p);
        sendonSelect(p, "Necklace of Anguish (e)", "Upgrades the necklace to gain an additional +2 prayer bonus", "Necklace of Anguish", 257, 31036, 19547, 125000, 50000000);
    }
    public static void upgradeAnguish(Player p) {
        if (p.getInventory().contains(19547, 1)
                && p.getInventory().contains(SHARDS, 125000)
                && p.getInventory().contains(GP, 50000000)) {
            removeandReplace(p, 19547, 125000, 50000000, 31036);
            successMessage(p, 31036, "Necklace of Anguish");
        } else {
            failMessage(p);
        }
    }
    public static void sendRecoil(Player p) {
        clearSelections();
        selectedRecoil = true;
        refreshInter(p);
        sendonSelect(p, "Ring of Recoil (e)", "Upgrades the ring to recoil 13% of damage, rather than 10%", "Ring of Recoil", 266, 31037, 2550, 5000, 2500000);
    }
    public static void upgradeRecoil(Player p) {
        if (p.getInventory().contains(2550, 1)
                && p.getInventory().contains(SHARDS, 5000)
                && p.getInventory().contains(GP, 2500000)) {
            removeandReplace(p, 2550, 5000, 2500000, 31037);
            successMessage(p, 31037, "Ring of Recoil");
        } else {
            failMessage(p);
        }
    }
    public static void sendSuffering(Player p) {
        clearSelections();
        selectedSuffering = true;
        refreshInter(p);
        sendonSelect(p, "Ring of Suffering (e)", "Upgrades the ring to gain an additional +2 prayer bonus", "Ring of Suffering", 275, 31038, 19550, 125000, 50000000);
    }
    public static void upgradeSuffering(Player p) {
        if (p.getInventory().contains(19550, 1)
                && p.getInventory().contains(SHARDS, 125000)
                && p.getInventory().contains(GP, 50000000)) {
            removeandReplace(p, 19550, 125000, 50000000, 31038);
            successMessage(p, 31038, "Ring of Suffering");
        } else {
            failMessage(p);
        }
    }
    public static void sendGods(Player p) {
        clearSelections();
        selectedGods = true;
        refreshInter(p);
        sendonSelect(p, "Ring of the Gods (e)", "Upgrades the ring to gain an additional +2 prayer bonus", "Ring of the Gods", 284, 31039, 12601, 90000, 75000000);
    }
    public static void upgradeGods(Player p) {
        if (p.getInventory().contains(12601, 1)
                && p.getInventory().contains(SHARDS, 90000)
                && p.getInventory().contains(GP, 75000000)) {
            removeandReplace(p, 12601, 90000, 75000000, 31039);
            successMessage(p, 31039, "Ring of the Gods");
        } else {
            failMessage(p);
        }
    }
    public static void sendSeers(Player p) {
        clearSelections();
        selectedSeers = true;
        refreshInter(p);
        sendonSelect(p, "Seers Ring (e)", "Upgrades the rings magic attack bonus by +2", "Seers Ring", 293, 31040, 6731, 150000, 75000000);
    }
    public static void upgradeSeers(Player p) {
        if (p.getInventory().contains(6731, 1)
                && p.getInventory().contains(SHARDS, 150000)
                && p.getInventory().contains(GP, 75000000)) {
            removeandReplace(p, 6731, 150000, 75000000, 31040);
            successMessage(p, 31040, "Seers Ring");
        } else {
            failMessage(p);
        }
    }
    public static void sendTormented(Player p) {
        clearSelections();
        selectedTormented = true;
        refreshInter(p);
        sendonSelect(p, "Tormented Bracelet (e)", "Upgrades the rings magic damage bonus by +2", "Tormented Bracelet", 302, 31041, 19544, 215000, 200000000);
    }
    public static void upgradeTormented(Player p) {
        if (p.getInventory().contains(19544, 1)
                && p.getInventory().contains(SHARDS, 215000)
                && p.getInventory().contains(GP, 200000000)) {
            removeandReplace(p, 19544, 215000, 200000000, 31041);
            successMessage(p, 31041, "Tormented Bracelet");
        } else {
            failMessage(p);
        }
    }
    public static void sendTreasonous(Player p) {
        clearSelections();
        selectedTreasonous = true;
        refreshInter(p);
        sendonSelect(p, "Treasonous Ring (e)", "Upgrades the rings stab attack bonus by +2", "Treasonous Ring", 311, 31042, 12605, 60000, 30000000);
    }
    public static void upgradeTreasonous(Player p) {
        if (p.getInventory().contains(12605, 1)
                && p.getInventory().contains(SHARDS, 60000)
                && p.getInventory().contains(GP, 30000000)) {
            removeandReplace(p, 12605, 60000, 30000000, 31042);
            successMessage(p, 31042, "Treasonous Ring");
        } else {
            failMessage(p);
        }
    }
    public static void sendBloodFury(Player p) {
        clearSelections();
        selectedBloodFury = true;
        refreshInter(p);
        sendonSelect(p, "Amulet of Blood Fury (e)", "Upon every hit to your enemy, the amulet has a 3% chance of providing a heal over time effect", "Amulet of Blood Fury", 320, 31045, 24780, 325000, 400000000);
    }
    public static void upgradeBloodFury(Player p) {
        if (p.getInventory().contains(24780, 1)
                && p.getInventory().contains(SHARDS, 325000)
                && p.getInventory().contains(GP, 400000000)) {
            removeandReplace(p, 24780, 325000, 400000000, 31045);
            successMessage(p, 31045, "Amulet of Blood Fury");
        } else {
            failMessage(p);
        }
    }
    public static void sendOccult(Player p) {
        clearSelections();
        selectedOccult = true;
        refreshInter(p);
        sendonSelect(p, "Occult Necklace (e)", "Provides a 2% chance on every hit to freeze your enemy (if enemy is freezable)", "Occult Necklace", 329, 31046, 12002, 225000, 150000000);
    }
    public static void upgradeOccult(Player p) {
        if (p.getInventory().contains(12002, 1)
                && p.getInventory().contains(SHARDS, 225000)
                && p.getInventory().contains(GP, 150000000)) {
            removeandReplace(p, 12002, 225000, 150000000, 31046);
            successMessage(p, 31046, "Occult Necklace");
        } else {
            failMessage(p);
        }
    }
    static {

            InterfaceHandler.register(JEWELLERY_INTER, h -> {
                h.actions[96] = (SlotAction) (player, slot) -> EnchantButton.enchantButton(player);
                h.actions[110] = (SlotAction) (player, slot) -> Enchanting.openArmorInter(player);
                h.actions[111] = (SlotAction) (player, slot) -> Enchanting.openWeaponInter(player);
                h.actions[112] = (SlotAction) (player, slot) -> Enchanting.openJewelryInter(player);
                h.actions[168] = (SlotAction) (player, slot) -> sendFury(player);
                h.actions[177] = (SlotAction) (player, slot) -> sendTorture(player);
                h.actions[184] = (SlotAction) (player, slot) -> sendDamned(player);
                h.actions[221] = (SlotAction) (player, slot) -> sendArchers(player);
                h.actions[230] = (SlotAction) (player, slot) -> sendBerserker(player);
                h.actions[239] = (SlotAction) (player, slot) -> sendBrimstone(player);
                h.actions[248] = (SlotAction) (player, slot) -> sendGranite(player);
                h.actions[257] = (SlotAction) (player, slot) -> sendAnguish(player);
                h.actions[266] = (SlotAction) (player, slot) -> sendRecoil(player);
                h.actions[275] = (SlotAction) (player, slot) -> sendSuffering(player);
                h.actions[284] = (SlotAction) (player, slot) -> sendGods(player);
                h.actions[293] = (SlotAction) (player, slot) -> sendSeers(player);
                h.actions[302] = (SlotAction) (player, slot) -> sendTormented(player);
                h.actions[311] = (SlotAction) (player, slot) -> sendTreasonous(player);
                h.actions[320] = (SlotAction) (player, slot) -> sendBloodFury(player);
                h.actions[329] = (SlotAction) (player, slot) -> sendOccult(player);
            });
    }
}