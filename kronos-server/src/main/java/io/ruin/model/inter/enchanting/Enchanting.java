package io.ruin.model.inter.enchanting;

import io.ruin.Server;
import io.ruin.model.achievements.listeners.easy.IntroToEnchanting;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.enchanting.armor.EnchantArmorInfo;
import io.ruin.model.inter.enchanting.jewellery.EnchantJewelleryInfo;
import io.ruin.model.inter.enchanting.weapons.EnchantWeaponsInfo;
import io.ruin.model.map.object.actions.ObjectAction;

import static io.ruin.Server.STARTUP_THREAD;
import static io.ruin.model.inter.enchanting.armor.EnchantArmorData.sendArmorItemList;
import static io.ruin.model.inter.enchanting.jewellery.EnchantJewelleryData.sendJewelryItemList;
import static io.ruin.model.inter.enchanting.weapons.EnchantWeaponsData.sendWeaponItemList;

public class Enchanting {

    static {

            ObjectAction.register(31624, "Open", (player, obj) -> openArmorInter(player));
    }

    public static void clearAll() {
        EnchantWeaponsInfo.clearSelections();
        EnchantJewelleryInfo.clearSelections();
        EnchantArmorInfo.clearSelections();
    }

    public static void openArmorInter(Player p) {
        p.openInterface(InterfaceType.MAIN, 827);
        sendArmorItemList(p);
        IntroToEnchanting.complete(p);
        clearAll();
    }
    public static void openWeaponInter(Player p) {
        p.openInterface(InterfaceType.MAIN, 960);
        sendWeaponItemList(p);
        IntroToEnchanting.complete(p);
        clearAll();
    }
    public static void openJewelryInter(Player p) {
        p.openInterface(InterfaceType.MAIN, 961);
        sendJewelryItemList(p);
        IntroToEnchanting.complete(p);
        clearAll();
    }

}
