package io.ruin.model.inter.enchanting.weapons;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

public class EnchantWeaponsData {

    public static int WEAPON_INTER = 960;

    public static void sendWeaponItemList(Player p) {
        //Clear side box sprites
        p.getPacketSender().sendItems(WEAPON_INTER, 196, 0, new Item(-1));
        p.getPacketSender().sendItems(WEAPON_INTER, 210, 0, new Item(-1));
        p.getPacketSender().sendItems(WEAPON_INTER, 212, 0, new Item(-1));
        p.getPacketSender().sendItems(WEAPON_INTER, 214, 0, new Item(-1));
        //Clear item name and description
        p.getPacketSender().sendString(WEAPON_INTER, 92, "Select an item");
        p.getPacketSender().sendString(WEAPON_INTER, 162, "");
        //Send item names and sprites
        sendNameList(p);
        sendSprites(p);
    }
    public static void sendNameList(Player p) {
        p.getPacketSender().sendString(WEAPON_INTER, 168, "Abyssal Bludgeon");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 177, "Abyssal Dagger (p++)");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 184, "Abyssal Whip");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 221, "Bow of Faerdhinen");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 230, "Armadyl Crossbow");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 239, "Blade of Saeldor");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 248, "Dragon Claws");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 257, "Ghrazi Rapier");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 266, "Kodai Wand");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 275, "Sanguinesti Staff");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 284, "Staff of Light");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 293, "Toxic Blowpipe");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 302, "Scythe of Vitur");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 311, "Trident of the Seas");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 320, "Dragon Hunter CB");//Item name goes here
        p.getPacketSender().sendString(WEAPON_INTER, 329, "Dragon Hunter Lance");//Item name goes here
    }
    public static void sendSprites(Player p) {
        p.getPacketSender().sendItems(WEAPON_INTER, 170, 0, new Item(13263, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 186, 0, new Item(13271, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 188, 0, new Item(4151, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 223, 0, new Item(25865, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 232, 0, new Item(11785, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 241, 0, new Item(23995, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 250, 0, new Item(13652, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 259, 0, new Item(22324, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 268, 0, new Item(21006, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 277, 0, new Item(22323, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 286, 0, new Item(22296, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 295, 0, new Item(12924, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 304, 0, new Item(22325, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 313, 0, new Item(11908, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 322, 0, new Item(21012, 1));
        p.getPacketSender().sendItems(WEAPON_INTER, 331, 0, new Item(22978, 1));
    }

}