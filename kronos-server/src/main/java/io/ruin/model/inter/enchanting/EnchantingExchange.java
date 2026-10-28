package io.ruin.model.inter.enchanting;

import io.ruin.Server;
import io.ruin.cache.ItemDef;
import io.ruin.model.achievements.listeners.hard.OnAConquest;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;

import static io.ruin.Server.STARTUP_THREAD;
import static io.ruin.cache.ItemID.COINS_995;

public class EnchantingExchange {

    public static int ENCHANTING_SHARDS = 25676;

    static {

            ObjectAction.register(33523, "Exchange", (player, obj) -> {
                player.sendScroll("<col=8B0000>Enchanting Item Exchange</col>",
                        "Welcome to the Enchanting item exchange! Here, you can find ",
                        "information about which items are eligible to be offered in ",
                        "exchange for Enchanting Shards.",
                        "<br>",
                        "Once ready to offer an item to the Exchange, simply use the ",
                        "item on the altar and it will prompt you to accept the amount",
                        "of shards given. Below, you can find the shard amounts offered",
                        "per eligible item. If an item is not listed below, it is not",
                        "eligible to be exchanged.",
                        "<br>",
                        "<br>",
                        "Abyssal Whip - <col=8B0000>2,500",
                        "Amulet of Fury - <col=8B0000>2,500",
                        "Amulet of Torture - <col=8B0000>12,500",
                        "Arcane Spirit Shield - <col=8B0000>35,000",
                        "Armadyl Chestplate - <col=8B0000>10,000",
                        "Armadyl Chainskirt - <col=8B0000>10,000",
                        "Armadyl Crossbow - <col=8B0000>17,500",
                        "Armadyl Helmet - <col=8B0000>8,000",
                        "Armadyl Godsword - <col=8B0000>15,000",
                        "Bandos Chestplate - <col=8B0000>10,000",
                        "Bandos Godsword - <col=8B0000>12,000",
                        "Bandos Tassets - <col=8B0000>10,000",
                        "Bow of Faerdhinen - <col=8B0000>22,500",
                        "Dragon Claws - <col=8B0000>15,000",
                        "Dragon Crossbow - <col=8B0000>3,000",
                        "Elder Maul - <col=8B0000>20,000",
                        "Elysian Spirit Shield - <col=8B0000>35,000",
                        "Eternal Boots - <col=8B0000>15,000",
                        "Infernal Cape - <col=8B0000>17,500",
                        "Necklace of Anguish - <col=8B0000>12,500",
                        "Occult Necklace - <col=8B0000>3,000",
                        "Pegasian Boots - <col=8B0000>15,000",
                        "Primordial Boots - <col=8B0000>15,000",
                        "Ring of Suffering - <col=8B0000>12,500",
                        "Saradomin Godsword - <col=8B0000>12,000",
                        "Spectral Spirit Shield - <col=8B0000>35,000",
                        "Staff of Light - <col=8B0000>5,000",
                        "Tormented Bracelet - <col=8B0000>12,500",
                        "Toxic Blowpipe (e) - <col=8B0000>12,500",
                        "Toxic Staff of the Dead - <col=8B0000>10,000",
                        "Vesta's Longsword - <col=8B0000>15,000",
                        "Zamorak Godsword - <col=8B0000>12,000");
            });

            int[][] ancientArtifacts = {
                    {11802, 15000},   //ags
                    {11830, 10000},  //arma legs
                    {11832, 10000},  //bcp
                    {11828, 10000},  //arma chest
                    {11826, 8000},  //arma helm
                    {11834, 10000},  //tassets

                    {13652, 15000}, {22296, 5000}, {12904, 10000}, {19553, 12500}, {19547, 12500},
                    {6585, 2500}, {4151, 2500}, {21902, 3000}, {11804, 12000}, {11806, 12000},

                    {11808, 12000}, {25865, 22500}, {12002, 3000}, {19544, 12500}, {19550, 12500},
                    {11785, 17500}, {21003, 20000}, {21295, 17500}, {12817, 35000}, {12821, 35000},

                    {12825, 35000}, {12924, 12500}, {22613, 15000}, {13239, 15000}, {13237, 15000},
                    {13235, 15000}

            };
            for (int[] artifact : ancientArtifacts) {
                int id = artifact[0];
                ItemDef.get(id).sigmundBuyPrice = artifact[1];
                ItemObjectAction.register(id, 33523, (player, item, npc) ->
                        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Exchange your " + ItemDef.get(id).name +
                                " for " + artifact[1] + " enchanting shards?", item, () -> {
                            item.remove();
                            player.getInventory().add(ENCHANTING_SHARDS, artifact[1]);
                        }
                        ))
                );
            }
    }

}