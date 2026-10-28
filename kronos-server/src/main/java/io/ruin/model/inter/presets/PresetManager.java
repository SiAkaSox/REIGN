package io.ruin.model.inter.presets;

import com.google.gson.annotations.Expose;
import io.ruin.cache.Color;
import io.ruin.content.activities.tournament.TournamentManager;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.bank.BankItem;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.stat.StatType;

import java.util.Arrays;

public class PresetManager {

    @Expose
    public Preset[] presets;

    public PresetManager() {
        this.presets = new Preset[10];
    }

    public void setPreset(int index, Preset preset) {
        presets[index] = preset;
    }

    public Preset getPreset(int index) {
        if (index < 0 || index > presets.length)
            return null;
        return presets[index];
    }

    static {
        InterfaceHandler.register(832, h -> {
            for (int i = 104; i >= 77; i -= 3) {
                int finalI = i;
                h.actions[i] = (DefaultAction) (player, child, option, slot, itemId) -> {
                    int presetId = 104 - finalI / 3 - 70;
                    if (player.attackPlayerListener != null)
                        return;
                    if (player.lmsSession != null)
                        return;
                    if (player.wildernessLevel > 0 || player.pvpAttackZone)
                        return;
                    Preset preset = player.getPresets().getPreset(presetId);
                    player.set("lastPresetId", presetId);
                    changeTitle(player, preset);
                    if (preset == null) {
                        createPreset(player, presetId);
                        return;
                    }
                    preset.display(player);
                };
            }
            h.actions[139] = (DefaultAction) (player, child, option, slot, itemId) -> {//deleting
                Integer id = player.get("lastPresetId");
                if (id == null)
                    return;
                Preset preset = player.getPresets().getPreset(id);
                if (preset == null)
                    return;
                player.dialogue(new OptionsDialogue("Delete " + preset.getName() + "?",
                        new Option("Yes, delete " + preset.getName() + ".", () -> {
                            deletePreset(player, id, preset);
                        }),
                        new Option("No, don't delete.", () -> {

                        })
                ));
            };
            h.actions[115] = (DefaultAction) (player, child, option, slot, itemId) -> {//wear
                Preset preset = player.getPresets().getPreset(player.get("lastPresetId"));
                if (preset == null)
                    return;
                player.dialogue(
                        new OptionsDialogue("Load " + preset.getName() + "?",
                                new Option("Yes, load " + preset.getName(), () -> {
                                    loadPreset(player, preset);
                                }),
                                new Option("Never mind.", () -> {

                                })
                        ));
            };
        });
    }

    private static void loadPreset(Player player, Preset preset) {
        player.getBank().deposit(player.getInventory(), true, true);
        player.getBank().deposit(player.getEquipment(), true, true);
        Item[] inventory = preset.getInventory().clone();
        Item[] equipment = preset.getEquipment().clone();
        if (player.isEasyModeWithEasyXP()) {
            for (int i = 0; i < preset.combatStats.size(); i++) {
                PresetStat stat = preset.combatStats.get(i);
                if (stat != null)
                    player.getStats().set(StatType.get(stat.getStatId()), stat.getLevel(), (int) stat.getExperience());
            }
        }
        for (int equip = 0; equip < equipment.length; equip++) {
            Item equipmentItem = equipment[equip];
            if (equipmentItem != null) {
                checkBankForEquipment(player, equipmentItem);
            }
        }
        for (int inv = 0; inv < inventory.length; inv++) {
            Item inventoryItem = inventory[inv];
            if (inventoryItem != null) {
                checkBankForInventory(player, inventoryItem, inv);
            }
        }
        preset.getSpellBook().setActive(player);
        player.sendMessage("<col=ff0000>Loaded preset " + preset.getName() + ".");
    }

    private static void deletePreset(Player player, int id, Preset preset) {
        player.getPresets().setPreset(id, null);
        resetItems(player);
        sendPresetNames(player);
        player.remove("lastPresetId");
        player.sendMessage("<col=ff0000>Delete present " + preset.getName() + ".");
    }

    private static void checkBankForEquipment(Player player, Item equipmentItem) {
        BankItem hasItem = player.getBank().findItem(equipmentItem.getId());
        if (hasItem == null) {
            player.sendMessage(Color.RED.wrap("Missing Bank Item: " + equipmentItem.getAmount() + "x " + equipmentItem.getDef().name + " (" + equipmentItem.getId() + ")"));
            return;
        }
        boolean canWield = player.getEquipment().canWield(equipmentItem);
        if (!canWield) {
            player.sendMessage(Color.RED.wrap("You cannot wield " + equipmentItem.getDef().name + " with your current stats."));
            return;
        }
        player.getBank().remove(equipmentItem);
        player.getEquipment().equip(equipmentItem);
    }

    private static void checkBankForInventory(Player player, Item inventory, int slot) {
        BankItem hasItem = player.getBank().findItem(inventory.getId(), true);
        if (hasItem == null) {
            player.sendMessage(Color.RED.wrap("Missing Bank Item: " + inventory.getAmount() + "x " + inventory.getDef().name + " (" + inventory.getId() + ")"));
            return;
        }
        int requiredAmount = inventory.getAmount();
        int hasAmount = hasItem.getAmount();
        boolean needsNoted = inventory.getDef().isNote();
        if (hasAmount < requiredAmount) {
            int todo = requiredAmount - hasAmount;
            player.sendMessage(Color.RED.wrap("Missing Bank Item: " + todo + "x " + inventory.getDef().name + " (" + inventory.getId() + ")"));
        }
        player.getBank().withdrawPresetItem(hasItem, requiredAmount, needsNoted, slot);
    }

    private static void createPreset(Player player, int presetId) {
        showDefaultPreset(player);
        if (!player.getEquipment().isNotEmpty() && !player.getInventory().isNotEmpty()) {
            player.sendMessage("<col=ff0000>Surely, It would be wise to only use this when you have items?..");
            return;
        }
        player.dialogue(new OptionsDialogue("Create Present #" + presetId + "?",
                new Option("Yes, create a preset in slot #" + presetId, () -> {
                    player.nameInput("Enter a Name for your #" + presetId + " preset slot", (presetName) -> {
                        if (presetName == null || presetName.isEmpty()) {
                            player.sendMessage("You must enter a name for your slot.");
                            return;
                        }
                        player.getPresets().setPreset(presetId, new Preset(presetId, player, presetName, getSpellBook(player), false));
                        reloadPreset(player, presetId);
                        player.sendMessage("Created new present in slot #" + presetId + " with name (" + presetName + ")");
                    });
                }),
                new Option("Never mind", () -> {

                })
        ));
    }

    private static SpellBook getSpellBook(Player player) {
        return SpellBook.ANCIENT.isActive(player) ? SpellBook.ANCIENT : SpellBook.LUNAR.isActive(player) ? SpellBook.LUNAR : SpellBook.MODERN;
    }

    private static void reloadPreset(Player player, int presetId) {
        sendPresetNames(player);
    }

    public static void openInterface(Player p) {
        if (p.attackPlayerListener != null)
            return;
        if (p.lmsSession != null)
            return;
        if (p.wildernessLevel > 0 || p.pvpAttackZone)
            return;
        if (TournamentManager.Lobby.INSTANCE != null && TournamentManager.Lobby.INSTANCE.getPlayers().contains(p))
            return;
        if (p.tournament != null)
            return;
        p.openInterface(InterfaceType.MAIN, 832);
        changeTitle(p, null);
        resetItems(p);
        sendPresetNames(p);
    }

    private static void changeTitle(Player player, Preset preset) {
        //122
        player.getPacketSender().sendString(832, 122, preset == null ? "Preset Manager" : "Viewing Preset: "+preset.getName());
    }

    public static void resetItems(Player p) {
        /** Resets data **/
        for (int i = 147; i <= 167; i += 2) {
            p.getPacketSender().sendItems(832, i, 0, new Item(-1));
        }
        for (int i = 169; i <= 223; i += 2) {
            p.getPacketSender().sendItems(832, i, 0, new Item(-1));
        }
        for (int i = 34; i < 45; i++) {
            p.getPacketSender().setHidden(832, i, false);
        }
    }

    private static void sendPresetNames(Player p) {
        int childNameStart = 78;
        for (int i = p.getPresets().presets.length - 1; i > 0; i--) {
            Preset preset = p.getPresets().getPreset(i);
            p.getPacketSender().sendString(832, childNameStart, "" + (preset == null ? "Create new..." : preset.getName()));
            childNameStart += 3;
        }
    }

    public static void showDefaultPreset(Player player) {
        resetItems(player);
        Arrays.stream(PresetEquipmentSlot.values()).forEach(e -> {
            final Item equipId = player.getEquipment().get(e.getEquipId());
            if (equipId != null) {
                player.getPacketSender().sendItems(832, e.getChildId(), 0, equipId);
                player.getPacketSender().setHidden(832, e.getHiddenId(), true);
            }
        });
        int countId = 0;
        for (int i = 169; i <= 223; i += 2) {
            final Item inventoryItem = player.getInventory().get(countId);
            if (inventoryItem != null) {
                player.getPacketSender().sendItems(832, i, 0, inventoryItem);
            }
            countId++;
        }
        player.getPacketSender().sendClientScript(10193);
    }
}
