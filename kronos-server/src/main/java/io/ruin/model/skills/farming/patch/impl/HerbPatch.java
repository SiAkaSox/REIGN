package io.ruin.model.skills.farming.patch.impl;


import io.ruin.model.entity.player.Player;
import io.ruin.model.skills.farming.Farming;
import io.ruin.utility.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.item.Item;
import io.ruin.model.skills.CapePerks;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.HerbCrop;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.stat.StatType;

public class HerbPatch extends Patch {

    @Override
    public int getCropVarpbitValue() {
        int index = 0;
        if (isDiseased()) {
            index = (getStage() - 1) | (1 << 7);
        } else if (isDead()) {
            index = 170 + Math.min(getStage() - 1, 2);
        } else {
            index = getPlantedCrop().getContainerIndex() + getStage();
        }
        return index;
    }

    @Override
    public void cropInteract() {
        if (isDead()) {
            clear();
            return;
        }
        if (getProduceCount() == 0) {
            reset(false);
            return;
        }
        player.startEvent(event -> {
            while (true) {
                if (player.getInventory().getFreeSlots() == 0) {
                    player.sendMessage("Not enough space in your inventory.");
                    return;
                }
                if (getProduceCount() == 0) {
                    reset(false);
                    player.sendMessage("You've picked all the herbs from this patch.");
                    return;
                }
                int amount = 1;
                if (player.tangleroot) {
                    amount = 2;
                }
                player.animate(2282);
                event.delay(2);
                player.collectResource(new Item(getPlantedCrop().getProduceId(), amount));
                player.getInventory().add(getPlantedCrop().getProduceId(), 1);
                double xp = getPlantedCrop().getHarvestXP();
                double additional = xp * (Farming.farmersBonus(player) / 100);
                player.getStats().addXp(StatType.Farming, xp + additional, true);
                player.sendFilteredMessage("You pick a " + ItemDef.get(getPlantedCrop().getProduceId()).name + ".");
                getPlantedCrop().getCounter().increment(player, 1);
                removeProduce();
                event.delay(1);
            }
        });
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof HerbCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return getObjectId() == 18816; // trollheim patch no disease
    }

    @Override
    public int calculateProduceAmount() {
        int amount = Random.get(8, 10) + getDonatorBonus(player);
        if (getCompost() == 2) { // supercompost bonus
            amount += Random.get(3, 5);
        }
        if (CapePerks.wearsFarmingCape(player)) {
            amount += Random.get(1, 2);
        }
        return amount;
    }

    private int getDonatorBonus(Player player) {
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            return 9;
        } else if (player.storeAmountSpent >= 250 && player.storeAmountSpent < 500) {
            return 9;
        } else if (player.storeAmountSpent >= 100 && player.storeAmountSpent < 250) {
            return 7;
        } else if (player.storeAmountSpent >= 50 && player.storeAmountSpent < 100) {
            return 5;
        } else if (player.storeAmountSpent >= 15 && player.storeAmountSpent < 50) {
            return 3;
        }
        return 0;
    }

    @Override
    public boolean requiresCure() {
        return true;
    }

    @Override
    public String getPatchName() {
        return "a herb";
    }

}

