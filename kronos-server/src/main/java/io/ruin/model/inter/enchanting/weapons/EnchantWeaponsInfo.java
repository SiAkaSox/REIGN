package io.ruin.model.inter.enchanting.weapons;

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
import static io.ruin.model.inter.enchanting.weapons.EnchantWeaponsData.sendNameList;

public class EnchantWeaponsInfo {

    public static int SHARDS = 25676;
    public static int GP = 995;

    public static int WEAPON_INTER = 960;

    public static boolean selectedAbyssalBludgeon, selectedAbyssalDagger, selectedAbyssalWhip, selectedBofa,
            selectedDragonClaws, selectedGhraziRapier, selectedKodaiWand,
            selectedSanguinesti, selectedToxicBlowpipe, selectedBladeofSaeldor, selectedDHCB, selectedDHLance,
            selectedScythe, selectedSOL, selectedTrident, selectedVolatile, selectedHarmonised, selectedAcb = false;

    public static void clearSelections() {
        selectedAbyssalBludgeon = false; selectedAbyssalDagger=false; selectedAbyssalWhip=false; selectedBofa =false;
        selectedDragonClaws=false; selectedGhraziRapier=false;selectedKodaiWand=false;selectedSanguinesti=false; selectedToxicBlowpipe=false; selectedBladeofSaeldor=false;
        selectedDHCB=false; selectedDHLance=false; selectedScythe=false; selectedSOL=false;selectedTrident=false; selectedVolatile=false; selectedHarmonised=false; selectedAcb = false;
    }
    public static void refreshInter(Player p) {
        p.openInterface(InterfaceType.MAIN, WEAPON_INTER);
        sendNameList(p);
    }
    public static void sendonSelect(Player p, String newItemName, String description, String highlitedName, int highlightedChild, int newItem, int oldItem, int shardAmount, int gpAmount) {
        p.getPacketSender().sendString(WEAPON_INTER, 92, ""+newItemName);//new item name
        p.getPacketSender().sendString(WEAPON_INTER, 162, ""+description);//description
        p.getPacketSender().sendString(WEAPON_INTER, highlightedChild, "<col=FFFFFF>"+highlitedName);//selected name
        p.getPacketSender().sendItems(WEAPON_INTER, 196, 0, new Item(newItem));
        p.getPacketSender().sendItems(WEAPON_INTER, 210, 0, new Item(oldItem));
        p.getPacketSender().sendItems(WEAPON_INTER, 212, 0, new Item(SHARDS, shardAmount));
        p.getPacketSender().sendItems(WEAPON_INTER, 214, 0, new Item(GP, gpAmount));
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
    public static void sendAbyssalBludgeon(Player p) {
        clearSelections();
        selectedAbyssalBludgeon = true;
        refreshInter(p);
        sendonSelect(p, "Abyssal Bludgeon (e)", "Upgrades the special attack to only consume 30% special attack energy, rather than 50%", "Abyssal Bludgeon", 168, 31047, 13263, 215000, 160000000);
    }
    public static void upgradeAbyssalBludgeon(Player p) {
        if (p.getInventory().contains(13263, 1)
                && p.getInventory().contains(SHARDS, 215000)
                && p.getInventory().contains(GP, 160000000)) {
            removeandReplace(p, 13263, 215000, 160000000, 31047);
            successMessage(p, 31047, "Abyssal Bludgeon");
        } else {
            failMessage(p);
        }
    }
    public static void sendAbyssalDagger(Player p) {
        clearSelections();
        selectedAbyssalDagger = true;
        refreshInter(p);
        sendonSelect(p, "Abyssal Dagger (e)", "Upgrades the special attack to only consume 30% special attack energy, rather than 50%", "Abyssal Dagger (p++)", 177, 31048, 13271, 215000, 160000000);
    }
    public static void upgradeAbyssalDagger(Player p) {
        if (p.getInventory().contains(13271, 1)
                && p.getInventory().contains(SHARDS, 215000)
                && p.getInventory().contains(GP, 160000000)) {
            removeandReplace(p, 13271, 215000, 160000000, 31048);
            successMessage(p, 31048, "Abyssal Dagger (p++)");
        } else {
            failMessage(p);
        }
    }
    public static void sendAbyssalWhip(Player p) {
        clearSelections();
        selectedAbyssalWhip = true;
        refreshInter(p);
        sendonSelect(p, "Abyssal Whip (e)", "Upgrades the abyssal whip to attack 1 tick faster", "Abyssal Whip", 184, 31049, 4151, 260000, 220000000);
    }
    public static void upgradeAbyssalWhip(Player p) {
        if (p.getInventory().contains(4151, 1)
                && p.getInventory().contains(SHARDS, 260000)
                && p.getInventory().contains(GP, 100000000)) {
            removeandReplace(p, 4151, 260000, 100000000, 31049);
            successMessage(p, 31049, "Abyssal Whip");
        } else {
            failMessage(p);
        }
    }
    public static void sendBofa(Player p) {
        clearSelections();
        selectedBofa = true;
        refreshInter(p);
        sendonSelect(p, "Bow of Faerdhinen (c)", "Full set does 5% more damage and has a 5% chance of siphoning health from opponent", "Bow of Faerdhinen", 221, 25867, 25865, 500000, 400000000);
    }
    public static void upgradeBofa(Player p) {
        if (p.getInventory().contains(25865, 1)
                && p.getInventory().contains(SHARDS, 500000)
                && p.getInventory().contains(GP, 400000000)) {
            removeandReplace(p, 25865, 500000, 400000000, 25867);
            successMessage(p, 25867, "Bow of Faerdhinen");
        } else {
            failMessage(p);
        }
    }
    public static void sendArmadylCrossbow(Player p) {
        clearSelections();
        selectedAcb = true;
        refreshInter(p);
        sendonSelect(p, "Armadyl Crossbow (e)", "Special attack will also freeze your enemy for 15 seconds (if enemy is freezable)", "Armadyl Crossbow", 230, 31051, 11785, 300000, 150000000);
    }
    public static void upgradeAcb(Player p) {
        if (p.getInventory().contains(11785, 1)
                && p.getInventory().contains(SHARDS, 300000)
                && p.getInventory().contains(GP, 150000000)) {
            removeandReplace(p, 11785, 300000, 150000000, 31051);
            successMessage(p, 31051, "Armadyl Crossbow");
        } else {
            failMessage(p);
        }
    }
    public static void sendBladeofSaeldor(Player p) {
        clearSelections();
        selectedBladeofSaeldor = true;
        refreshInter(p);
        sendonSelect(p, "Blade of Saeldor (c)", "Full set does 5% more damage and has a 5% chance of siphoning health from opponent", "Blade of Saeldor", 239, 24551, 23995, 400000, 375000000);
    }
    public static void upgradeSaeldor(Player p) {
        if (p.getInventory().contains(23995, 1)
                && p.getInventory().contains(SHARDS, 400000)
                && p.getInventory().contains(GP, 275000000)) {
            removeandReplace(p, 23995, 400000, 275000000, 24551);
            successMessage(p, 24551, "Blade of Saeldor");
        } else {
            failMessage(p);
        }
    }
    public static void sendDragonClaws(Player p) {
        clearSelections();
        selectedDragonClaws = true;
        refreshInter(p);
        sendonSelect(p, "Dragon Claws (e)", "Has a 10% chance of only using 25% special attack energy, rather than 50%", "Dragon Claws", 248, 31055, 13652, 300000, 180000000);
    }
    public static void upgradeClaws(Player p) {
        if (p.getInventory().contains(13652, 1)
                && p.getInventory().contains(SHARDS, 300000)
                && p.getInventory().contains(GP, 180000000)) {
            removeandReplace(p, 13652, 300000, 180000000, 31055);
            successMessage(p, 31055, "Dragon Claws");
        } else {
            failMessage(p);
        }
    }
    public static void sendDHCB(Player p) {
        clearSelections();
        selectedDHCB = true;
        refreshInter(p);
        sendonSelect(p, "Dragon Hunter Crossbow (b)", "Upgrades the weapon to also count as protection against dragon fire", "Dragon Hunter CB", 320, 25918, 21012, 400000, 275000000);
    }
    public static void upgradeDhcb(Player p) {
        if (p.getInventory().contains(21012, 1)
                && p.getInventory().contains(SHARDS, 400000)
                && p.getInventory().contains(GP, 275000000)) {
            removeandReplace(p, 21012, 400000, 275000000, 25918);
            successMessage(p, 25918, "Dragon Hunter Crossbow");
        } else {
            failMessage(p);
        }
    }
    public static void sendDHLance(Player p) {
        clearSelections();
        selectedDHLance = true;
        refreshInter(p);
        sendonSelect(p, "Dragon Hunter Lance (e)", "Upgrades the weapon to also count as protection against dragon fire", "Dragon Hunter Lance", 329, 31057, 22978, 400000, 275000000);
    }
    public static void upgradeLance(Player p) {
        if (p.getInventory().contains(22978, 1)
                && p.getInventory().contains(SHARDS, 400000)
                && p.getInventory().contains(GP, 275000000)) {
            removeandReplace(p, 22978, 400000, 275000000, 31057);
            successMessage(p, 31057, "Dragon Hunter Lance");
        } else {
            failMessage(p);
        }
    }
    public static void sendGhraziRapier(Player p) {
        clearSelections();
        selectedGhraziRapier = true;
        refreshInter(p);
        sendonSelect(p, "Ghrazi Rapier (e)", "Provides a 5% chance of hitting for an additional 50% of the current hit", "Ghrazi Rapier", 257, 25734, 22324, 500000, 400000000);
    }
    public static void upgradeRapier(Player p) {
        if (p.getInventory().contains(22324, 1)
                && p.getInventory().contains(SHARDS, 500000)
                && p.getInventory().contains(GP, 400000000)) {
            removeandReplace(p, 22324, 500000, 400000000, 25734);
            successMessage(p, 25734, "Ghrazi Rapier");
        } else {
            failMessage(p);
        }
    }
    public static void sendKodaiWand(Player p) {
        clearSelections();
        selectedKodaiWand = true;
        refreshInter(p);
        sendonSelect(p, "Kodai Wand (e)", "Upgrades the weapon to deal higher damage inside of CoX", "Kodai Wand", 266, 30181, 21006, 500000, 400000000);
    }
    public static void upgradeKodai(Player p) {
        if (p.getInventory().contains(21006, 1)
                && p.getInventory().contains(SHARDS, 500000)
                && p.getInventory().contains(GP, 400000000)) {
            removeandReplace(p, 21006, 500000, 400000000, 30181);
            successMessage(p, 30181, "Kodai Wand");
        } else {
            failMessage(p);
        }
    }
    public static void sendSanguinesti(Player p) {
        clearSelections();
        selectedSanguinesti = true;
        refreshInter(p);
        sendonSelect(p, "Sanguinesti Staff (e)", "Provides a 5% chance of hitting for an additional 50% of the current hit", "Sanguinesti Staff", 275, 25731, 22323, 350000, 225000000);
    }
    public static void upgradeSanguinesti(Player p) {
        if (p.getInventory().contains(22323, 1)
                && p.getInventory().contains(SHARDS, 350000)
                && p.getInventory().contains(GP, 225000000)) {
            removeandReplace(p, 22323, 350000, 225000000, 25731);
            successMessage(p, 25731, "Sanguinesti Staff");
        } else {
            failMessage(p);
        }
    }
    public static void sendScythe(Player p) {
        clearSelections();
        selectedScythe = true;
        refreshInter(p);
        sendonSelect(p, "Sanguine Scythe of Vitur", "Provides a 5% chance of hitting for an additional 50% of the current highest hit", "Scythe of Vitur", 302, 25739, 22325, 500000, 400000000);
    }
    public static void upgradeScythe(Player p) {
        if (p.getInventory().contains(22325, 1)
                && p.getInventory().contains(SHARDS, 500000)
                && p.getInventory().contains(GP, 400000000)) {
            removeandReplace(p, 22325, 500000, 400000000, 25739);
            successMessage(p, 25739, "Scythe of Vitur");
        } else {
            failMessage(p);
        }
    }
    public static void sendStaffofLight(Player p) {
        clearSelections();
        selectedSOL = true;
        refreshInter(p);
        sendonSelect(p, "Staff of Light (e)", "Provides a +8 magic attack bonus over the regular SoL", "Staff of Light", 284, 31065, 22296, 250000, 125000000);
    }
    public static void upgradeSOL(Player p) {
        if (p.getInventory().contains(22296, 1)
                && p.getInventory().contains(SHARDS, 250000)
                && p.getInventory().contains(GP, 125000000)) {
            removeandReplace(p, 22296, 250000, 125000000, 31065);
            successMessage(p, 31065, "Staff of Light");
        } else {
            failMessage(p);
        }
    }
    public static void sendBlowpipe(Player p) {
        clearSelections();
        selectedToxicBlowpipe = true;
        refreshInter(p);
        sendonSelect(p, "Toxic Blowpipe (e)", "Special attack now heals you for 100% of the damage done, rather than 50%", "Toxic Blowpipe", 293, 30049, 12924, 275000, 150000000);
    }
    public static void upgradeBlowpipe(Player p) {
        if (p.getInventory().contains(12924, 1)
                && p.getInventory().contains(SHARDS, 275000)
                && p.getInventory().contains(GP, 150000000)) {
            removeandReplace(p, 12924, 275000, 150000000, 30049);
            successMessage(p, 30049, "Toxic Blowpipe");
        } else {
            failMessage(p);
        }
    }
    public static void sendTrident(Player p) {
        clearSelections();
        selectedTrident = true;
        refreshInter(p);
        sendonSelect(p, "Trident of the Seas (e)", "Increases the weapons magic attack bonus by +2", "Trident of the Seas", 311, 31068, 11907, 175000, 50000000);
    }
    public static void upgradeTrident(Player p) {
        if (p.getInventory().contains(11907, 1)
                && p.getInventory().contains(SHARDS, 175000)
                && p.getInventory().contains(GP, 50000000)) {
            removeandReplace(p, 11907, 175000, 50000000, 31068);
            successMessage(p, 31068, "Trident of the Seas");
        } else {
            failMessage(p);
        }
    }
    static {

            InterfaceHandler.register(WEAPON_INTER, h -> {
                h.actions[96] = (SlotAction) (player, slot) -> EnchantButton.enchantButton(player);
                h.actions[110] = (SlotAction) (player, slot) -> Enchanting.openArmorInter(player);
                h.actions[111] = (SlotAction) (player, slot) -> Enchanting.openWeaponInter(player);
                h.actions[112] = (SlotAction) (player, slot) -> Enchanting.openJewelryInter(player);
                h.actions[168] = (SlotAction) (player, slot) -> sendAbyssalBludgeon(player);
                h.actions[177] = (SlotAction) (player, slot) -> sendAbyssalDagger(player);
                h.actions[184] = (SlotAction) (player, slot) -> sendAbyssalWhip(player);
                h.actions[221] = (SlotAction) (player, slot) -> sendBofa(player);
                h.actions[230] = (SlotAction) (player, slot) -> sendArmadylCrossbow(player);
                h.actions[239] = (SlotAction) (player, slot) -> sendBladeofSaeldor(player);
                h.actions[248] = (SlotAction) (player, slot) -> sendDragonClaws(player);
                h.actions[257] = (SlotAction) (player, slot) -> sendGhraziRapier(player);
                h.actions[266] = (SlotAction) (player, slot) -> sendKodaiWand(player);
                h.actions[275] = (SlotAction) (player, slot) -> sendSanguinesti(player);
                h.actions[284] = (SlotAction) (player, slot) -> sendStaffofLight(player);
                h.actions[293] = (SlotAction) (player, slot) -> sendBlowpipe(player);
                h.actions[302] = (SlotAction) (player, slot) -> sendScythe(player);
                h.actions[311] = (SlotAction) (player, slot) -> sendTrident(player);
                h.actions[320] = (SlotAction) (player, slot) -> sendDHCB(player);
                h.actions[329] = (SlotAction) (player, slot) -> sendDHLance(player);
            });
    }
}