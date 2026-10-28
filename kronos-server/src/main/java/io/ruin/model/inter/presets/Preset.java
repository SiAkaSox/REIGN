package io.ruin.model.inter.presets;

import com.google.api.client.util.Lists;
import com.google.gson.annotations.Expose;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.stat.Stat;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.ruin.model.inter.presets.PresetManager.resetItems;

@Data
public class Preset {

    @Expose private final int presetId;
    @Expose public Item[] equipment;
    @Expose private Item[] inventory;
    @Expose private String name;
    @Expose private SpellBook spellBook;
    @Expose private boolean locked;
    @Expose public List<PresetStat> combatStats;

    public Preset(int presetId, Player player, String name, SpellBook spellBook, boolean locked) {
        this.presetId = presetId;
        Item[] equip = new Item[14];
        for (int slot = 0; slot < player.getEquipment().getItems().length; slot++) {
            Item item = player.getEquipment().get(slot);
            if (item != null) {
                equip[slot] = item.copy();
            }
        }
        this.setEquipment(equip);
        Item[] inventory = new Item[28];
        for (int slot = 0; slot < player.getInventory().getItems().length; slot++) {
            Item item = player.getInventory().get(slot);
            if (item != null) {
                inventory[slot] = item.copy();
            }
        }
        this.setInventory(inventory);
        this.spellBook = spellBook;
        if (player.isEasyModeWithEasyXP()) {
            this.combatStats = Lists.newArrayList();
            for (int i = 0; i < 7; i++) {
                Stat stat = player.getStats().get(i);
                combatStats.add(new PresetStat(i, stat.fixedLevel, stat.experience));
            }
        }
        this.name = name;
        this.spellBook = spellBook;
        this.locked = locked;
    }

    @Override
    public String toString(){
        return name != null ? name : "Preset #"+presetId;
    }

    public String getName() {
        return name != null ? name : "Preset #"+presetId;
    }

    public void display(Player player) {
        resetItems(player);
        Arrays.stream(PresetEquipmentSlot.values()).forEach(e -> {
            final Item equipId = getEquipment()[e.getEquipId()];
            if (equipId != null) {
                player.getPacketSender().sendItems(832, e.getChildId(), 0, equipId);
                player.getPacketSender().setHidden(832, e.getHiddenId(), true);
            }
        });

        int count = 0;
        for (int i = 169; i <= 223; i += 2) {
            final Item inventoryItem = getInventory()[count];
            if (inventoryItem != null) {
                player.getPacketSender().sendItems(832, i, 0, inventoryItem);
            }
            count++;
        }
        player.getPacketSender().sendClientScript(10193);
    }
}
