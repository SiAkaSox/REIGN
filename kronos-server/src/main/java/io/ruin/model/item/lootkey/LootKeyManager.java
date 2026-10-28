package io.ruin.model.item.lootkey;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.player.GameMode;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.handlers.IKOD;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.NumberUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static io.ruin.model.inter.handlers.IKOD.itemsSortedByProtectValue;

public class LootKeyManager {

    public static final int LOOT_KEY_ID = 26651;
    private static final int WIDGET_ID = 971;
    private static final int MAX_KEYS = 5;

    @Expose
    private LootKey[] myKeys;

    private Player player;

    public int viewingContainer;

    public LootKeyManager(Player player) {
        this.player = player;
        if (myKeys == null) {
            myKeys = new LootKey[5];
        }
    }

    static {

        ObjectAction.register(43486, 1, (p, obj) -> handleLootingKey(p));
        ItemAction.registerInventory(LOOT_KEY_ID, "check", (p, item) -> checkValue(p, item));
        ItemAction.registerInventory(LOOT_KEY_ID, "destroy", (p, item) -> destroyKey(p, item));
        InterfaceHandler.register(WIDGET_ID, h -> {
            h.actions[25] = (SimpleAction) player -> handleItemsToInventory(player);
            h.actions[32] = (SimpleAction) player -> handleItemsToBank(player);
            h.actions[9] = (SimpleAction) player -> handleDiscardItems(player);
        });
    }

    public LootKey getContainer() {
        return myKeys[viewingContainer];
    }

    private static void handleDiscardItems(Player player) {
        player.getLootKeyManager().removeKeyOnLoot(player, false, false);
    }

    private static void handleItemsToBank(Player player) {
        player.getLootKeyManager().removeKeyOnLoot(player, false, true);
    }

    private static void handleItemsToInventory(Player player) {
        player.getLootKeyManager().removeKeyOnLoot(player, true, false);
    }

    private static void handleLootingKey(Player player) {
        if (!player.getInventory().containsId(LOOT_KEY_ID, 1)) {
            player.sendMessage("You need a players' Loot Key to open this chest!");
            return;
        }
        Item key = player.getInventory().findItemExact(LOOT_KEY_ID);
        if (!key.hasAttributes()) {
            player.sendMessage("Your key holds no value. " + (player.isStaff() ? "NO ATTRIBUTES <-- dev msg" : ""));
            return;
        }
        if (player.getGameMode().isAnyIron()) {
            player.sendMessage("As an ironman, you cannot loot, lootkeys.");
            return;
        }
        List<Item> inventoryKeys = player.getInventory().nonNullStream().filter(e -> e.getId() == LOOT_KEY_ID).collect(Collectors.toList());
        Collections.sort(inventoryKeys, Comparator.comparingInt(e -> e.getAttributeInt("index")));
        if (inventoryKeys.size() > 1) {
            List<Option> dialogue = Lists.newArrayList();
            inventoryKeys.stream().forEach(e -> dialogue.add(new Option(checkValueForWidget(player, e, true), () -> openWidget(player, e))));
            player.dialogue(new OptionsDialogue(dialogue));
            return;
        }
        openWidget(player, key);
    }

    private static void openWidget(Player player, Item key) {
        player.openInterface(InterfaceType.MAIN, WIDGET_ID);
        int index = key.getAttributeInt("index");
        LootKey myKey = player.getLootKeyManager().myKeys[index];
        if (myKey == null) {
           player.getInventory().remove(key);
           return;
        }
        player.getLootKeyManager().viewingContainer = index;
        player.getPacketSender().sendString(WIDGET_ID, 6, "Value in chest: " + checkValueForWidget(player, key, false));
        int startOffset = 38;
        player.getLootKeyManager().refreshContainerSlot(player);
        for (int i = 0; i < myKey.size(); i++) {
            Item item = myKey.get(i);
            if (item == null) {
                continue;
            }
            if (item.getDef() != null && item.getDef().free) {
                //double check
                item.remove();
                continue;
            }
            player.getPacketSender().sendItems(971, startOffset, 0, item);
            startOffset += 2;
        }
    }

    private void refreshContainerSlot(Player player) {
        for (int i = 38; i < 38 + (28 * 2); i += 2) {
            player.getPacketSender().sendItems(971, i, 0, new Item(-1, -1));
        }
    }

    public static void onItemTake(Player player, Item item) {
        if (item.getId() != LOOT_KEY_ID)
            return;
        int inventoryKeys = player.getInventory().getNumberOf(item.getId());

    }

    private int freeIndex() {
        int freeIndex = -1;
        for (int containerIndex = 0; containerIndex < myKeys.length; containerIndex++) {
            ItemContainer container = myKeys[containerIndex];
            if (container == null) {
                freeIndex = containerIndex;
                break;
            }
        }
        return freeIndex;
    }

    private int getContainersSize() {
        AtomicInteger count = new AtomicInteger();
        Arrays.stream(myKeys).filter(k -> k != null).forEach(k -> count.getAndIncrement());
        return count.get();
    }

    public boolean canReceive(Killer killer, Player slayed) {
        if (killer == null)
            return false;

        if (killer.player != null) {
            if (player.isRealism || player.getGameMode().isAnyIron()) {
                return false;
            }
        }

        Player player = killer.player;

        List<Item> items = itemsSortedByProtectValue(slayed);
        if (items.isEmpty())
            return false;

        int price = 0;
        for (Item item : items) {
            if (item == null)
                continue;
            if (!item.getDef().free) {
                price += item.getDef().value;
            }
            if (item.getId() == LOOT_KEY_ID) {
                price -= item.getDef().value;
                new GroundItem(item).owner(player).position(slayed.getPosition()).spawnPrivate();
            }
        }
        if (price == 0) {
            return false;
        }


        int freeIndex = freeIndex();

        if (getContainersSize() >= MAX_KEYS || freeIndex == -1) {
            player.sendMessage("You have reached the limit of " + MAX_KEYS + " loot keys.");
            return false;
        }
        LootKey container = new LootKey();//Creates the Item Container
        container.init(player, 42, WIDGET_ID, 3, 0, false);
        IKOD.forLostItem(slayed, killer, (item -> container.addCheck(item, killer.player, player)));//Adds dropped items to the new container
        Item key = new Item(LOOT_KEY_ID, 1);//adds the loot key to the floor
        key.putAttribute("index", freeIndex);//sets the attribute of the loot key container index
        myKeys[freeIndex] = container;//sets your stored key loot with the uid of the container.
        if (player.getInventory().freeSlot() > 0) {
            player.getInventory().add(key);
        } else
            new GroundItem(key).owner(killer.player).position(slayed.getPosition().getX(), slayed.getPosition().getY(), slayed.getPosition().getZ()).spawn();

        checkSkull();
        return true;
    }

    public void checkSkull() {
        int keyAmount = player.getInventory().count(LOOT_KEY_ID, false, true);
        if (keyAmount >= 1 && keyAmount <= 5) {
            player.getAppearance().setSkullIcon(14 + keyAmount);
        }
        if (keyAmount == 0) {
            player.getAppearance().setSkullIcon(-1);
        }
    }

    public boolean hasSkull() {
        return player.getAppearance().getSkullIcon() >= 15 && player.getAppearance().getSkullIcon() <= 19;
    }

    public static void checkValue(Player player, Item item) {
        Integer uid = item.getAttributeInt("index");
        String errorMessage = "This key holds no value.";
        if (uid == -1) {
            player.sendMessage(errorMessage);
            return;
        }
        ItemContainer container = player.getLootKeyManager().myKeys[uid];
        if (container == null) {
            player.sendMessage(errorMessage);
            return;
        }
        player.sendMessage("Your loot key contains items that are worth approximately " + NumberUtils.formatNumber(container.getContainerValue(false)) + "gp");
    }

    public static String checkValueForWidget(Player player, Item item, boolean dialogue) {
        Integer uid = item.getAttributeInt("index");
        String errorMessage = "This key holds no value.";
        if (uid == -1) {
            player.sendMessage(errorMessage);
            return "N/A";
        }
        ItemContainer container = player.getLootKeyManager().myKeys[uid];
        if (container == null) {
            return "N/A";
        }
        if (dialogue) {
            int id = uid += 1;
            return "Key #" + id + " Value: " + NumberUtils.formatNumber(container.getContainerValue(false));
        }
        return NumberUtils.formatNumber(container.getContainerValue(false)) + "gp";
    }

    public void calculateSkull() {

    }

    private static void destroyKey(Player player, Item item) {
        player.dialogue(new YesNoDialogue("Are you sure you want to destroy the item?",
                "The contents of the inside will be destroyed with it.", item, () -> removeKey(player, item)));
    }

    private static void removeKey(Player player, Item item) {
        item.remove();
        Integer uid = item.getAttributeInt("index");
        if (uid == -1)
            return;
        player.getLootKeyManager().myKeys[uid] = null;
    }

    private void removeKeyOnLoot(Player player, boolean toInventory, boolean toBank) {
        LootKey loot = getContainer();
        if (loot == null) {
            System.err.println("loot is null..");
            return;
        }
        int index = player.getLootKeyManager().viewingContainer;

        if (index == -1) {
            return;
        }

        Item key = findLootKey(index);

        if (key == null) {
            return;
        }

        key.remove();
        player.getLootKeyManager().myKeys[index] = null;
        player.getLootKeyManager().viewingContainer = -1;

        if (toInventory) {
            loot.nonNullStream().forEach(e -> player.getInventory().addOrDrop(e));
        }
        if (toBank) {
            loot.nonNullStream().forEach(e -> player.getBank().add(e.getId(), e.getAmount()));
        }
        checkSkull();
        player.closeInterfaces();

    }

    private Item findLootKey(int index) {
        for (Item item : player.getInventory().getItems()) {
            if (item != null && item.getAttributeInt("index") != -1 && item.getAttributeInt("index") == index && (item.getId() == LOOT_KEY_ID))
                return item;
        }
        return null;
    }

    public void init(Player player) {
        this.player = player;
    }
}