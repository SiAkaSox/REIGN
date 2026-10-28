package io.ruin.model.inter.enchanting.armor;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

public class EnchantArmorData {

    public static int ARMOR_INTER = 827;

    public static void sendArmorItemList(Player p) {
        //Clear side box sprites
        p.getPacketSender().sendItems(ARMOR_INTER, 196, 0, new Item(-1));
        p.getPacketSender().sendItems(ARMOR_INTER, 210, 0, new Item(-1));
        p.getPacketSender().sendItems(ARMOR_INTER, 212, 0, new Item(-1));
        p.getPacketSender().sendItems(ARMOR_INTER, 214, 0, new Item(-1));
        //Clear item name and description
        p.getPacketSender().sendString(ARMOR_INTER, 92, "Select an item");
        p.getPacketSender().sendString(ARMOR_INTER, 162, "");
        //Send item names and sprites
        sendNameList(p);
        sendSprites(p);
    }

    public static void sendNameList(Player p) {
        p.getPacketSender().sendString(ARMOR_INTER, 168, "Crystal Helm");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 177, "Crystal Body");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 184, "Crystal Legs");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 221, "Ancestral Hat");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 230, "Ancestral Top");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 239, "Ancestral Bottoms");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 248, "Serpentine Helm");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 257, "Neitiznot Faceguard");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 266, "Dragonfire Shield");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 275, "Elite Void Top");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 284, "Elite Void Bottoms");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 293, "Bandos Chestplate");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 302, "Bandos Tassets");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 311, "Armadyl Helmet");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 320, "Armadyl Chestplate");//Item name goes here
        p.getPacketSender().sendString(ARMOR_INTER, 329, "Armadyl Chainskirt");//Item name goes here
    }
    public static void sendSprites(Player p) {
        p.getPacketSender().sendItems(ARMOR_INTER, 170, 0, new Item(23971, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 186, 0, new Item(23975, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 188, 0, new Item(23979, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 223, 0, new Item(21018, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 232, 0, new Item(21021, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 241, 0, new Item(21024, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 250, 0, new Item(12929, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 259, 0, new Item(24271, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 268, 0, new Item(11283, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 277, 0, new Item(13072, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 286, 0, new Item(13073, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 295, 0, new Item(11832, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 304, 0, new Item(11834, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 313, 0, new Item(11826, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 322, 0, new Item(11828, 1));
        p.getPacketSender().sendItems(ARMOR_INTER, 331, 0, new Item(11830, 1));
    }
}