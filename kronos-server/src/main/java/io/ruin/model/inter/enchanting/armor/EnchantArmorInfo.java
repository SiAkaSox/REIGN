package io.ruin.model.inter.enchanting.armor;

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

import static io.ruin.model.inter.enchanting.armor.EnchantArmorData.sendNameList;

public class EnchantArmorInfo {

    public static int ARMOR_INTER = 827;

    public static int SHARDS = 25676;
    public static int GP = 995;

    public static boolean selectedCrystalHelm, selectedCrystalBody, selectedCrystalLegs, selectedAncestralHat,
     selectedAncestralTop, selectedAncestralBottom, selectedArmaHelm, selectedArmaTop, selectedArmaBottom,
     selectedBandosTop, selectedBandosBottom, selectedDfs, selectedEliteVoidTop, selectedEliteVoidBottom,
     selectedNeitiznotFaceguard, selectedSerpentineHelm;

    public static void clearSelections() {
        selectedCrystalBody = false; selectedCrystalLegs = false; selectedAncestralHat = false; selectedAncestralTop= false;
        selectedAncestralBottom= false; selectedArmaHelm= false; selectedArmaTop= false; selectedArmaBottom= false;
        selectedBandosTop= false; selectedBandosBottom= false; selectedDfs= false; selectedEliteVoidTop= false;
        selectedEliteVoidBottom= false; selectedNeitiznotFaceguard= false; selectedSerpentineHelm= false; selectedCrystalHelm = false;
    }
    public static void refreshInter(Player p) {
        p.openInterface(InterfaceType.MAIN, ARMOR_INTER);
        sendNameList(p);
    }
    public static void sendonSelect(Player p, String newItemName, String description, String highlitedName, int highlitedChild, int newItem, int oldItem, int shardAmount, int gpAmount) {
        p.getPacketSender().sendString(ARMOR_INTER, 92, ""+newItemName);//new item name
        p.getPacketSender().sendString(ARMOR_INTER, 162, ""+description);//description
        p.getPacketSender().sendString(ARMOR_INTER, highlitedChild, "<col=FFFFFF>"+highlitedName);//selected name
        p.getPacketSender().sendItems(ARMOR_INTER, 196, 0, new Item(newItem));
        p.getPacketSender().sendItems(ARMOR_INTER, 210, 0, new Item(oldItem));
        p.getPacketSender().sendItems(ARMOR_INTER, 212, 0, new Item(SHARDS, shardAmount));
        p.getPacketSender().sendItems(ARMOR_INTER, 214, 0, new Item(GP, gpAmount));
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
    public static void sendCrystalHelm(Player p) {
        clearSelections();
        selectedCrystalHelm = true;
        refreshInter(p);
        sendonSelect(p, "Corrupted Helm", "Full set does 5% more damage and has a 5% chance of siphoning health from opponent", "Crystal Helm", 168,23842, 23971, 175000, 125000000);
    }
    public static void upgradecrystalHelm(Player p) {
        if (p.getInventory().contains(23971, 1)
                && p.getInventory().contains(SHARDS, 175000)
                && p.getInventory().contains(GP, 125000000)) {
            removeandReplace(p, 23971, 175000, 125000000, 23842);
            successMessage(p, 23842,"Crystal Helm");
        } else {
            failMessage(p);
        }
    }
    public static void sendCrystalBody(Player p) {
        clearSelections();
        selectedCrystalBody = true;
        refreshInter(p);
        sendonSelect(p, "Corrupted Body", "Full set does 5% more damage and has a 5% chance of siphoning health from opponent", "Crystal Body", 177,23845, 23975, 175000, 125000000);
    }
    public static void upgradecrystalBody(Player p) {
        if (p.getInventory().contains(23975, 1)
                && p.getInventory().contains(SHARDS, 175000)
                && p.getInventory().contains(GP, 125000000)) {
            removeandReplace(p, 23975, 175000, 125000000, 23845);
            successMessage(p, 23845, "Crystal Body");
        } else {
            failMessage(p);
        }
    }
    public static void sendCrystalLegs(Player p) {
        clearSelections();
        selectedCrystalLegs = true;
        refreshInter(p);
        sendonSelect(p, "Corrupted Legs", "Full set does 5% more damage and has a 5% chance of siphoning health from opponent", "Crystal Legs", 184, 23848, 23979, 175000, 125000000);
    }
    public static void upgradecrystalLegs(Player p) {
        if (p.getInventory().contains(23979, 1)
                && p.getInventory().contains(SHARDS, 175000)
                && p.getInventory().contains(GP, 125000000)) {
            removeandReplace(p, 23979, 175000, 125000000, 23848);
            successMessage(p, 23848, "Crystal Legs");
        } else {
           failMessage(p);
        }
    }
    public static void sendAncestralHat(Player p) {
        clearSelections();
        selectedAncestralHat = true;
        refreshInter(p);
        sendonSelect(p, "Dark Ancestral Hat", "Full set does 5% more damage and has a 5% chance of siphoning health from opponent", "Ancestral Hat", 221,30175, 21018, 225000, 200000000);
    }
    public static void upgradeAncestralHat(Player p) {
        if (p.getInventory().contains(21018, 1)
                && p.getInventory().contains(SHARDS, 225000)
                && p.getInventory().contains(GP, 200000000)) {
            removeandReplace(p, 21018, 225000, 200000000, 30175);
            successMessage(p, 30175, "Ancestral Hat");
        } else {
            failMessage(p);
        }
    }
    public static void sendAncestralTop(Player p) {
        clearSelections();
        selectedAncestralTop = true;
        refreshInter(p);
        sendonSelect(p, "Dark Ancestral Top", "Full set does 5% more damage and has a 5% chance of siphoning health from opponent", "Ancestral Top", 230,30177, 21021, 225000, 200000000);
    }
    public static void upgradeAncestralTop(Player p) {
        if (p.getInventory().contains(21021, 1)
                && p.getInventory().contains(SHARDS, 225000)
                && p.getInventory().contains(GP, 200000000)) {
            removeandReplace(p, 21021, 225000, 200000000, 30177);
            successMessage(p, 30177, "Ancestral Top");
        } else {
            failMessage(p);
        }
    }
    public static void sendAncestralBottom(Player p) {
        clearSelections();
        selectedAncestralBottom = true;
        refreshInter(p);
        sendonSelect(p, "Dark Ancestral Bottoms", "Full set does 5% more damage and has a 5% chance of siphoning health from opponent", "Ancestral Bottoms", 239,30179, 21024, 225000, 200000000);
    }
    public static void upgradeAncestralBottom(Player p) {
        if (p.getInventory().contains(21024, 1)
                && p.getInventory().contains(SHARDS, 225000)
                && p.getInventory().contains(GP, 200000000)) {
            removeandReplace(p, 21024, 225000, 200000000, 30179);
            successMessage(p, 30179, "Ancestral Bottoms");
        } else {
           failMessage(p);
        }
    }
    public static void sendSerpentineHelm(Player p) {
        clearSelections();
        selectedSerpentineHelm = true;
        refreshInter(p);
        sendonSelect(p, "Serpentine Helm (e)", "Provides a +2 prayer bonus to the helmet", "Serpentine Helm", 248,31018, 12929, 175000, 50000000);
    }
    public static void upgradeSerpentine(Player p) {
        if (p.getInventory().contains(12929, 1)
                && p.getInventory().contains(SHARDS, 175000)
                && p.getInventory().contains(GP, 50000000)) {
            removeandReplace(p, 12929, 175000, 50000000, 31018);
           successMessage(p, 31018, "Serpentine Helm");
        } else {
            failMessage(p);
        }
    }
    public static void sendNeitiznotFaceguard(Player p) {
        clearSelections();
        selectedNeitiznotFaceguard = true;
        refreshInter(p);
        sendonSelect(p, "Neitiznot Faceguard (e)", "Provides an additional +3 strength bonus, the highest of any helm slot", "Neitiznot Faceguard", 257,31020, 24271, 175000, 50000000);
    }
    public static void upgradeNeitiznot(Player p) {
        if (p.getInventory().contains(24271, 1)
                && p.getInventory().contains(SHARDS, 175000)
                && p.getInventory().contains(GP, 50000000)) {
            removeandReplace(p, 24271, 175000, 50000000, 31020);
            successMessage(p, 31020, "Neitiznot Faceguard");
        } else {
            failMessage(p);
        }
    }
    public static void sendDragonfireShield(Player p) {
        clearSelections();
        selectedDfs = true;
        refreshInter(p);
        sendonSelect(p, "Dragonfire Shield (e)", "Upgrades the Dragonfire Shield to mitigate 95% of dragon fire damage", "Dragonfire Shield", 266,31011, 11283, 175000, 90000000);
    }
    public static void upgradeDfs(Player p) {
        if (p.getInventory().contains(11283, 1)
                && p.getInventory().contains(SHARDS, 175000)
                && p.getInventory().contains(GP, 90000000)) {
            removeandReplace(p, 11283, 175000, 90000000, 31011);
            successMessage(p, 31011, "Dragonfire Shield");
        } else {
            failMessage(p);
        }
    }
    public static void sendArmaHelm(Player p) {
        clearSelections();
        selectedArmaHelm = true;
        refreshInter(p);
        sendonSelect(p, "Armadyl Helmet (e)", "Enchanted Armadyl armor set gives a 3% chance of gaining +12 ranged max hit temporarily", "Armadyl Helmet", 311,31004, 11826, 175000, 75000000);
    }
    public static void upgradeArmaHelm(Player p) {
        if (p.getInventory().contains(11826, 1)
                && p.getInventory().contains(SHARDS, 175000)
                && p.getInventory().contains(GP, 75000000)) {
            removeandReplace(p, 11826, 175000, 75000000, 31004);
            successMessage(p, 31004, "Armadyl Helmet");
        } else {
            failMessage(p);
        }
    }
    public static void sendArmaTop(Player p) {
        clearSelections();
        selectedArmaTop = true;
        refreshInter(p);
        sendonSelect(p, "Armadyl Chestplate (e)", "Enchanted Armadyl armor set gives a 3% chance of gaining +12 ranged max hit temporarily", "Armadyl Chestplate", 320,31005, 11828, 175000, 75000000);
    }
    public static void upgradeArmaTop(Player p) {
        if (p.getInventory().contains(11828, 1)
                && p.getInventory().contains(SHARDS, 175000)
                && p.getInventory().contains(GP, 75000000)) {
            removeandReplace(p, 11828, 175000, 75000000, 31005);
            successMessage(p, 31005, "Armadyl Chestplate");
        } else {
            failMessage(p);
        }
    }
    public static void sendArmaBottom(Player p) {
        clearSelections();
        selectedArmaBottom = true;
        refreshInter(p);
        sendonSelect(p, "Armadyl Chainskirt (e)", "Enchanted Armadyl armor set gives a 3% chance of gaining +12 ranged max hit temporarily", "Armadyl Chainskirt", 329,31006, 11830, 175000, 75000000);
    }
    public static void upgradeArmaBottom(Player p) {
        if (p.getInventory().contains(11830, 1)
                && p.getInventory().contains(SHARDS, 175000)
                && p.getInventory().contains(GP, 75000000)) {
            removeandReplace(p, 11830, 175000, 75000000, 31006);
            successMessage(p, 31006, "Armadyl Chainskirt");
        } else {
            failMessage(p);
        }
    }
    public static void sendBcp(Player p) {
        clearSelections();
        selectedBandosTop = true;
        refreshInter(p);
        sendonSelect(p, "Bandos Chestplate (e)", "Enchanted BCP and Tassets set gives a 3% chance of gaining +12 melee max hit temporarily", "Bandos Chestplate", 293,31007, 11832, 175000, 75000000);
    }
    public static void upgradeBcp(Player p) {
        if (p.getInventory().contains(11832, 1)
                && p.getInventory().contains(SHARDS, 175000)
                && p.getInventory().contains(GP, 75000000)) {
            removeandReplace(p, 11832, 175000, 75000000, 31007);
            successMessage(p, 31007, "Bandos Chestplate");
        } else {
            failMessage(p);
        }
    }
    public static void sendTassets(Player p) {
        clearSelections();
        selectedBandosBottom = true;
        refreshInter(p);
        sendonSelect(p, "Bandos Tassets (e)", "Enchanted BCP and Tassets set gives a 3% chance of gaining +12 melee max hit temporarily", "Bandos Tassets", 302,31008, 11834, 175000, 75000000);
    }
    public static void upgradeTassets(Player p) {
        if (p.getInventory().contains(11834, 1)
                && p.getInventory().contains(SHARDS, 175000)
                && p.getInventory().contains(GP, 75000000)) {
            removeandReplace(p, 11834, 175000, 75000000, 31008);
            successMessage(p, 31008, "Bandos Tassets");
        } else {
            failMessage(p);
        }
    }
    public static void sendEliteVoidTop(Player p) {
        clearSelections();
        selectedEliteVoidTop = true;
        refreshInter(p);
        sendonSelect(p, "Elite Void Top (e)", "Provides a 5% damage boost while wearing the full set", "Elite Void Top", 275,26469, 13072, 150000, 50000000);
    }
    public static void upgradeEliteVoidTop(Player p) {
        if (p.getInventory().contains(13072, 1)
                && p.getInventory().contains(SHARDS, 150000)
                && p.getInventory().contains(GP, 50000000)) {
            removeandReplace(p, 13072, 150000, 50000000, 26469);
            successMessage(p, 26469, "Elite Void Top");
        } else {
            failMessage(p);
        }
    }
    public static void sendEliteVoidBottom(Player p) {
        clearSelections();
        selectedEliteVoidBottom = true;
        refreshInter(p);
        sendonSelect(p, "Elite Void Bottoms (e)", "Provides a 5% damage boost while wearing the full set", "Elite Void Bottoms", 284,26471, 13073, 150000, 50000000);
    }
    public static void upgradeEliteVoidBottom(Player p) {
        if (p.getInventory().contains(13073, 1)
                && p.getInventory().contains(SHARDS, 150000)
                && p.getInventory().contains(GP, 50000000)) {
            removeandReplace(p, 13073, 150000, 50000000, 26471);
            successMessage(p, 26471, "Elite Void Bottoms");
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required materials to enchant this item.")
            );
        }
    }
    static {
        InterfaceHandler.register(ARMOR_INTER, h -> {
            h.actions[96] = (SlotAction) (player, slot)-> EnchantButton.enchantButton(player);
            h.actions[110] = (SlotAction) (player, slot)-> Enchanting.openArmorInter(player);
            h.actions[111] = (SlotAction) (player, slot)-> Enchanting.openWeaponInter(player);
            h.actions[112] = (SlotAction) (player, slot)-> Enchanting.openJewelryInter(player);
            h.actions[168] = (SlotAction) (player, slot)-> sendCrystalHelm(player);
            h.actions[177] = (SlotAction) (player, slot)-> sendCrystalBody(player);
            h.actions[184] = (SlotAction) (player, slot)-> sendCrystalLegs(player);
            h.actions[221] = (SlotAction) (player, slot)-> sendAncestralHat(player);
            h.actions[230] = (SlotAction) (player, slot)-> sendAncestralTop(player);
            h.actions[239] = (SlotAction) (player, slot)-> sendAncestralBottom(player);
            h.actions[248] = (SlotAction) (player, slot)-> sendSerpentineHelm(player);
            h.actions[257] = (SlotAction) (player, slot)-> sendNeitiznotFaceguard(player);
            h.actions[266] = (SlotAction) (player, slot)-> sendDragonfireShield(player);
            h.actions[275] = (SlotAction) (player, slot)-> sendEliteVoidTop(player);
            h.actions[284] = (SlotAction) (player, slot)-> sendEliteVoidBottom(player);
            h.actions[293] = (SlotAction) (player, slot)-> sendBcp(player);
            h.actions[302] = (SlotAction) (player, slot)-> sendTassets(player);
            h.actions[311] = (SlotAction) (player, slot)-> sendArmaHelm(player);
            h.actions[320] = (SlotAction) (player, slot)-> sendArmaTop(player);
            h.actions[329] = (SlotAction) (player, slot)-> sendArmaBottom(player);
        });
    }
}