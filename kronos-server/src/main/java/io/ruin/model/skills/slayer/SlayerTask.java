package io.ruin.model.skills.slayer;

import io.ruin.model.item.Item;
import io.ruin.model.map.Position;

public class SlayerTask {
    private String taskName;
    private Item[] requiredItems;
    private int[] npcIds;
    private Position location;
    private int combatRequirement;
    private int slayerRequirement;
    private int minAmount;
    private int maxAmount;
    private int taskId;

    private int amountLeft;

    SlayerTask(int taskId, String taskName, Position location, Item[] requiredItems, int[] npcIds, int combatRequirement, int slayerRequirement, int minAmount, int maxAmount) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.requiredItems = requiredItems;
        this.npcIds = npcIds;
        this.location = location;
        this.combatRequirement = combatRequirement;
        this.slayerRequirement = slayerRequirement;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.amountLeft = 0;
    }

    public Item[] getRequiredItems() {
        return requiredItems;
    }

    public int[] getNpcIds() {
        return npcIds;
    }

    public Position getLocation() {
        return location;
    }

    public int getCombatRequirement() {
        return combatRequirement;
    }

    public int getSlayerRequirement() {
        return slayerRequirement;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setRequiredItems(Item[] requiredItems) {
        this.requiredItems = requiredItems;
    }

    public void setLocation(Position location) {
        this.location = location;
    }

    public int getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(int amountLeft) {
        this.amountLeft = amountLeft;
    }

    public String getTaskName() {
        return "<col=cc0000>" + taskName + "</col>";
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
