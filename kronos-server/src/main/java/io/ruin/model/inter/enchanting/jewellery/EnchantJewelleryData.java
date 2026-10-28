package io.ruin.model.inter.enchanting.jewellery;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

public class EnchantJewelleryData {

    public static int JEWELLERY_INTER = 961;

    public static void sendJewelryItemList(Player p) {
        //Clear side box sprites
        p.getPacketSender().sendItems(JEWELLERY_INTER, 196, 0, new Item(-1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 210, 0, new Item(-1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 212, 0, new Item(-1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 214, 0, new Item(-1));
        //Clear item name and description
        p.getPacketSender().sendString(JEWELLERY_INTER, 92, "Select an item");
        p.getPacketSender().sendString(JEWELLERY_INTER, 162, "");
        //Send item names and sprites
        sendNameList(p);
        sendSprites(p);
    }

    public static void sendNameList(Player p) {
        p.getPacketSender().sendString(JEWELLERY_INTER, 168, "Amulet of Fury");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 177, "Amulet of Torture");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 184, "Amulet of the Damned");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 221, "Archers Ring");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 230, "Berserker Ring");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 239, "Brimstone Ring");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 248, "Granite Ring");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 257, "Necklace of Anguish");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 266, "Ring of Recoil");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 275, "Ring of Suffering");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 284, "Ring of the Gods");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 293, "Seers Ring");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 302, "Tormented Bracelet");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 311, "Treasonous Ring");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 320, "Amulet of Blood Fury");//Item name goes here
        p.getPacketSender().sendString(JEWELLERY_INTER, 329, "Occult Necklace");//Item name goes here
    }

    public static void sendSprites(Player p) {
        p.getPacketSender().sendItems(JEWELLERY_INTER, 170, 0, new Item(6585, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 186, 0, new Item(19553, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 188, 0, new Item(12853, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 223, 0, new Item(6733, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 232, 0, new Item(6737, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 241, 0, new Item(22975, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 250, 0, new Item(21739, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 259, 0, new Item(19547, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 268, 0, new Item(2550, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 277, 0, new Item(19550, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 286, 0, new Item(12601, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 295, 0, new Item(6731, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 304, 0, new Item(19544, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 313, 0, new Item(12605, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 322, 0, new Item(24780, 1));
        p.getPacketSender().sendItems(JEWELLERY_INTER, 331, 0, new Item(12002, 1));
    }
}