package io.ruin.model.item.actions.impl;

import io.ruin.utility.NumberUtils;
import io.ruin.utility.ServerWrapper;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class MysteriousEmblem {

    static {
        int[][] emblems = {
                {12746, 25},    //Tier 1
                {12748, 40},    //Tier 2
                {12749, 60},    //Tier 3
                {12750, 85},   //Tier 4
                {12751, 115},   //Tier 5
                {12752, 150},   //Tier 6
                {12753, 190},   //Tier 7
                {12754, 235},   //Tier 8
                {12755, 285},   //Tier 9
                {12756, 340},  //Tier 10
        };
        for(int[] emblem : emblems) {
            ItemDef def = ItemDef.get(emblem[0]);
            def.sigmundBuyPrice = emblem[1];
            ItemAction.registerInventory(def.id, "info", (player, item) -> player.sendScroll("<col=800000>About Archaic Emblems",
                    "Archaic Emblems are items dropped from killing",
                    " wilderness slayer monsters. While on task in the wilderness,",
                    " your emblem has a chance of being automatically upgraded",
                    " upon killing your slayer task target.",
                    "",
                    "You can right click and redeem emblems in exchange",
                    "for blood money. Blood Money can be spent",
                    "by trading the Emblem Trader. Here are the emblem values:",
                    "",
                    Color.DARK_RED.wrap("Tier 1 ") + "           25 blood money",
                    Color.DARK_RED.wrap("Tier 2 ") + "           40 blood money",
                    Color.DARK_RED.wrap("Tier 3 ") + "           60 blood money",
                    Color.DARK_RED.wrap("   Tier 4 ") + "         85 blood money",
                    Color.DARK_RED.wrap("   Tier 5 ") + "         115 blood money",
                    Color.DARK_RED.wrap("   Tier 6 ") + "         150 blood money",
                    Color.DARK_RED.wrap("   Tier 7 ") + "         190 blood money",
                    Color.DARK_RED.wrap("   Tier 8 ") + "         235 blood money",
                    Color.DARK_RED.wrap("   Tier 9 ") + "         285 blood money",
                    Color.DARK_RED.wrap("   Tier 10 ") + "        340 blood money"
            ));

            ItemAction.registerInventory(def.id, "redeem", (player, item) -> {
                int emblemValue = emblem[1];
                String emblemName = item.getDef().name;
                if(player.wildernessLevel > 0 || player.pvpAttackZone) {
                    player.sendMessage("You can't redeem a mysterious emblem inside the wilderness.");
                    return;
                }
                player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Redeem your " + emblemName +
                        " for " + NumberUtils.formatNumber(emblemValue) + " blood money?", item, () -> {
                    item.remove();
                    player.getInventory().add(new Item(13307, emblemValue));
                    String format = String.format("Emblem:[Player:[%s] IPAddress:[%s] Value:[%d]]", player.name(), player.getIp(), emblemValue);
                    ServerWrapper.log(format);
                    player.dialogue(new ItemDialogue().one(13307, "You redeem your " + emblemName + " for " +
                            Color.DARK_RED.wrap(NumberUtils.formatNumber(emblemValue)) + " blood money."));
                }));
            });
        }
    }

}