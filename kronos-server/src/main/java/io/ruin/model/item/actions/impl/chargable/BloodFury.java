package io.ruin.model.item.actions.impl.chargable;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.utility.NumberUtils;

public class BloodFury {

    private static final int FURY = 6585;
    private static final int BLOOD_SHARD = 24777;
    private static final int BLOOD_FURY = 24780;
    private static final int MAX_CHARGES = 30000;

    private static void makeBloodFury(Player player, Item primary, Item secondary, int result) {
        player.dialogue(
                new ItemDialogue().one(6585, "<col=7f0000>Warning!</col><br>Creating the Amulet of Blood Fury will consume your Amulet of Fury. Do you wish to continue?"),
                new YesNoDialogue("Are you sure you want to do this?", "Selecting yes will consume your Amulet of Fury.", result, 1, () -> {
                    primary.setId(BLOOD_FURY);
                    secondary.remove();
                    primary.putAttribute(AttributeTypes.CHARGES, 10000);
                })
        );
    }

    private static void charge(Player p, Item amulet, Item shard) {
        int currentCharges = AttributeExtensions.getCharges(amulet);
        int allowedAmt = MAX_CHARGES - currentCharges;
        if (allowedAmt == 0) {
            p.sendMessage("Your amulet of blood fury cannot hold anymore charges.");
            return;
        }
        int addAmount = Math.min(allowedAmt, 10000);
        AttributeExtensions.addCharges(amulet, addAmount);
        shard.remove(1);
        p.dialogue(new ItemDialogue().one(24780, "Your Amulet of Blood Fury now holds " + NumberUtils.formatNumber(amulet.getAttributeInt(AttributeTypes.CHARGES)) + " charges."));
    }

    private static void revertBloodFury(Player player, Item primary) {
        player.dialogue(
                new ItemDialogue().one(24780, "Reverting the amulet does NOT return the amulet of fury. Do you wish to continue?"),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, you will only receive a blood shard.", BLOOD_FURY, 1, () -> {
                    primary.setId(BLOOD_SHARD);
                    primary.setUniqueValue(0);
                })
        );
    }

    private static void check(Player player, Item item) {
        int charges = AttributeExtensions.getCharges(item);
        player.sendFilteredMessage("<col=007f00>Your amulet currently has "
                + NumberUtils.formatNumber(charges) + " healing effect" + (charges > 1 ? "s" : "")
                + " remaining. The healing effect is currently "
                + (player.amuletOfBloodFuryEffect ? "enabled" : "disabled") + ".</col>");
    }

    static {
        ItemAction.registerInventory(BLOOD_FURY, "check", BloodFury::check);
        ItemAction.registerEquipment(BLOOD_FURY, "check", BloodFury::check);
        ItemAction.registerInventory(BLOOD_FURY, "revert", BloodFury::revertBloodFury);
        ItemItemAction.register(24780, 24777, BloodFury::charge);
        ItemAction.registerEquipment(BLOOD_FURY, "effect settings", (player, item) -> effectSettings(player));
        ItemAction.registerInventory(BLOOD_FURY, "effect settings", (player, item) -> effectSettings(player));
        ItemItemAction.register(FURY, BLOOD_SHARD, (player, primary, secondary) -> makeBloodFury(player, primary, secondary, 24780));

        ItemDef.get(BLOOD_FURY).addPostTargetDefendListener((player, item, hit, target) -> {
            int charges = item.getUniqueValue();
            if(charges == 0)
                charges = 10000;
            if(--charges <= 0) {
                item.remove();
                player.getInventory().addOrDrop(BLOOD_SHARD, 1);
            }
            item.setUniqueValue(charges);
        });
    }


    private static void effectSettings(Player player) {
        player.dialogue(new OptionsDialogue(
                new Option("Toggle healing effect", () -> {
                    player.amuletOfBloodFuryEffect = !player.amuletOfBloodFuryEffect;
                    player.sendFilteredMessage("You " + (player.amuletOfBloodFuryEffect ? "enable" : "disable") + " the healing effect of your amulet.");
                }),
                new Option("Cancel", player::closeDialogue)
        ));
    }


}


