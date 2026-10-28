package io.ruin.model.item.actions.impl.chargable;

import io.ruin.cache.ItemDef;
import io.ruin.model.content.accountperks.Perk;
import io.ruin.model.content.accountperks.PerkManager;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.utility.NumberUtils;

public class ChargedTorture {

    private static final int TORTURE = 19553;
    private static final int BLOOD_SHARD = 24777;
    private static final int CHARGED_TORTURE = 31101;
    private static final int MAX_CHARGES = 30000;

    private static void makeBloodFury(Player player, Item primary, Item secondary, int result) {
        if (!PerkManager.isActive(player, Perk.MASTER_JEWELER)) {
            player.sendMessage("You must unlock the Master Jeweler perk before you can charge this item.");
            return;
        }
        player.dialogue(
                new ItemDialogue().one(19553, "<col=7f0000>Warning!</col><br>Charging the Amulet of Torture will consume your Blood Shard. Do you wish to continue?"),
                new YesNoDialogue("Are you sure you want to do this?", "Selecting yes will consume your Blood Shard.", result, 1, () -> {
                    primary.setId(CHARGED_TORTURE);
                    secondary.remove();
                    primary.putAttribute(AttributeTypes.CHARGES, 10000);
                })
        );
    }

    private static void charge(Player p, Item amulet, Item shard) {
        int currentCharges = AttributeExtensions.getCharges(amulet);
        int allowedAmt = MAX_CHARGES - currentCharges;
        if (allowedAmt == 0) {
            p.sendMessage("Your amulet of torture cannot hold anymore charges.");
            return;
        }
        int addAmount = Math.min(allowedAmt, 10000);
        AttributeExtensions.addCharges(amulet, addAmount);
        shard.remove(1);
        p.dialogue(new ItemDialogue().one(24780, "Your Amulet of Torture now holds " + NumberUtils.formatNumber(amulet.getAttributeInt(AttributeTypes.CHARGES)) + " charges."));
    }

    private static void check(Player player, Item item) {
        int charges = AttributeExtensions.getCharges(item);
        player.sendFilteredMessage("<col=007f00>Your amulet currently has "
                + NumberUtils.formatNumber(charges) + " healing effect" + (charges > 1 ? "s" : "")
                + " remaining. The healing effect is currently "
                + (player.amuletOfBloodFuryEffect ? "enabled" : "disabled") + ".</col>");
    }

    static {
        ItemAction.registerInventory(CHARGED_TORTURE, "check", ChargedTorture::check);
        ItemAction.registerEquipment(CHARGED_TORTURE, "check", ChargedTorture::check);
        ItemItemAction.register(31101, 24777, ChargedTorture::charge);
        ItemItemAction.register(TORTURE, BLOOD_SHARD, (player, primary, secondary) -> makeBloodFury(player, primary, secondary, 31101));

        ItemDef.get(CHARGED_TORTURE).addPostTargetDefendListener((player, item, hit, target) -> {
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
}


