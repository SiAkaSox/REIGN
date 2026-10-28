package io.ruin.model.activities.home;

import io.ruin.Server;
import io.ruin.cache.ItemDef;
import io.ruin.cache.ItemID;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Unlock;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Material;
import io.ruin.model.stat.StatType;

import java.util.Map;

import static io.ruin.Server.STARTUP_THREAD;

public class UimStorage extends ItemContainer {

    static {
        ObjectAction.register(11236, 1, (p, obj) -> openPrivateStorage(p));
        InterfaceHandler.register(Interface.UIM_STORAGE, h -> {
            h.actions[9] = (SimpleAction) p -> {
                for (Item item : p.getUimStorage().getItems()) {
                    if (item == null)
                        continue;
                    if (!withdrawFromStorage(p, item.getSlot(), item.getAmount()))
                        return;
                }
            };
            h.actions[10] = (SimpleAction) p -> {
                for (Item item : p.getInventory().getItems()) {
                    if (item == null)
                        continue;
                    if (item.move(item.getId(), item.getAmount(), p.getUimStorage()) == 0) {
                        p.sendMessage("Not enough space in the storage unit.");
                        return;
                    }
                }
                p.getUimStorage().sendUpdates();
            };
            h.actions[6] = (DefaultAction) (p, child, option, slot, id) -> {
                switch (option) {
                    case 1:
                        withdrawFromStorage(p, slot, 1);
                        return;
                    case 2:
                        withdrawFromStorage(p, slot, 5);
                        return;
                    case 3:
                        withdrawFromStorage(p, slot, 10);
                        return;
                    case 4:
                        withdrawFromStorage(p, slot, Integer.MAX_VALUE);
                        return;
                    case 5:
                        p.integerInput("Enter amount:", amt -> withdrawFromStorage(p, slot, amt));
                        return;
                }
            };
        });

        InterfaceHandler.register(Interface.RAID_STORAGE_INVENTORY, h -> {
            h.actions[8] = (SimpleAction) p -> Config.RAIDS_STORAGE_WARNING_DISMISSED.set(p, 1);
            h.actions[1] = (DefaultAction) (p, child, option, slot, id) -> {
                switch (option) {
                    case 1:
                        depositToStorage(p, slot, 1);
                        return;
                    case 2:
                        depositToStorage(p, slot, 5);
                        return;
                    case 3:
                        depositToStorage(p, slot, 10);
                        return;
                    case 4:
                        depositToStorage(p, slot, Integer.MAX_VALUE);
                        return;
                    case 5:
                        p.integerInput("Enter amount:", amt -> depositToStorage(p, slot, amt));
                        return;
                }
            };
        });
    }

    private static boolean depositToStorage(Player p, int slot, int amount) {
        Item item = p.getInventory().get(slot);
        if (item == null)
            return false;
        int moved = item.move(item.getId(), amount, p.getUimStorage());
        p.getUimStorage().sendUpdates();
        if (moved == 0) {
            p.sendMessage("Not enough space in the storage unit.");
            return false;
        }
        return true;
    }


    public static boolean withdrawFromStorage(Player p, int slot, int amount) {
        Item item = p.getUimStorage().get(slot);
        if (item == null)
            return false;
        int moved = item.move(item.getId(), amount, p.getInventory());
        p.getUimStorage().sendUpdates();
        if (moved == 0) {
            p.sendMessage("Not enough space in your inventory.");
            return false;
        }
        return true;
    }

    public UimStorage() {
        init(null, 250, -1, -1, 32768 + 582, false);
    }

    public void setSize(int size) {
        if (size != items.length)
            init(null, size, -1, -1, 32768 + 582, false);
    }

    @Override
    public boolean sendUpdates(ItemContainerG mirrorContainer) {
        if (updatedCount == 0 && !sendAll)
            return false;
        if (sendAll) {
            if (player != null) {
                player.getPacketSender().sendItems(interfaceHash, containerId, items, items.length);
            }
        } else {
            if (player != null) {
                player.getPacketSender().updateItems(interfaceHash, containerId, items, updatedSlots, updatedCount);
            }
        }
        for (int i = 0; i < updatedSlots.length; i++)
            updatedSlots[i] = false;
        updatedCount = 0;
        sendAll = false;
        return true;
    }

    public void send(Player player) {
        player.getPacketSender().sendItems(interfaceHash, containerId, items, items.length);
    }

    @Override
    public int add(int id, int amount, Map<String, String> attributes) {
        ItemDef def = ItemDef.get(id);
        if (def == null || !def.coxItem)
            return 0;
        return super.add(id, amount, attributes);
    }

    public void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.RAID_SHARED_STORAGE);
        player.openInterface(InterfaceType.INVENTORY, Interface.RAID_STORAGE_INVENTORY);
        Config.RAIDS_STORAGE_PRIVATE_INVENTORY.set(player, 0);
        player.getPacketSender().sendString(Interface.RAID_SHARED_STORAGE, 4, String.valueOf(items.length));
        new Unlock(551, 1, 0, 27).unlockMultiple(player, 0, 1, 2, 3, 4, 9);
        new Unlock(550, 7, 0, 600).unlockMultiple(player, 0, 1, 2, 3, 4, 9);
    }

    public static void openPrivateStorage(Player p) {
        Config.RAIDS_STORAGE_WARNING_DISMISSED.set(p, 1);
        p.openInterface(InterfaceType.MAIN, Interface.UIM_STORAGE);
        p.openInterface(InterfaceType.INVENTORY, Interface.RAID_STORAGE_INVENTORY);
        Config.RAIDS_STORAGE_PRIVATE_INVENTORY.set(p, 1);


        p.getPacketSender().sendString(Interface.UIM_STORAGE, 4, "28");
        p.getPacketSender().sendString(Interface.UIM_STORAGE, 11, "Players with the <img=21><col=FF0000><shad=0000000>Ruby</col></shad> donator rank are granted 10 extra storage slots!");
        new Unlock(551, 1, 0, 27).unlockMultiple(p, 0, 1, 2, 3, 4, 9);
        new Unlock(975, 6, 0, 600).unlockMultiple(p, 0, 1, 2, 3, 4, 9);
        //p.getUimStorage().sendUpdates();
    }
}
