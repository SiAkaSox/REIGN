package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.impl.ItemSet;
import io.ruin.model.item.actions.impl.tradepost.TradePost;
import io.ruin.model.shop.ShopManager;

public class HomeShops {

    public static void ironshopDialogue(Player p) {
        p.dialogue(
                new OptionsDialogue("Which shop would you like to open?",
                        new Option("Ironman Combat Supplies", () -> openNewIronShop(p)),
                        new Option("Ironman Armor Store", () -> openIronArmor(p)),
                        new Option("Ironman Skilling Supplies", () -> openIronShop(p)),
                        new Option("Nevermind"))
        );
    }

    public static void openNewIronShop(Player p) {
        if (p.getGameMode().isAnyIron() || p.isAdmin()) {
            ShopManager.openIfExists(p, "12dae699-41d6-4d0a-8c05-5b3c721b0849");
        } else {
            p.sendMessage("This shop is only for Ironman accounts.");
        }
    }

    public static void openIronArmor(Player p) {
        if (p.getGameMode().isAnyIron() || p.isAdmin()) {
            ShopManager.openIfExists(p, "12dke699-41d6-4d0a-8c05-5b3c721b0849");
        } else {
            p.sendMessage("This shop is only for Ironman accounts.");
        }
    }


    public static void openIronShop(Player p) {
        if (p.getGameMode().isAnyIron() || p.isAdmin()) {
            ShopManager.openIfExists(p, "11ddf699-41d6-4d0a-8c05-5b3c721b0849");
        } else {
            p.sendMessage("This shop is only for Ironman accounts.");
        }
    }

    public static void talkSigmund(Player p) {
        ShopManager.openIfExists(p, "5464df07-5091-4ddc-8c5a-436db5ae5193");
    }

    static {
        //Vote shop
        NPCAction.register(13, "talk-to", (player, npc) -> ShopManager.openIfExists(player, "aecef817-39c1-4a22-9ee9-2815da9dd3df"));

        //Daily task shop
        NPCAction.register(3984, "open-shop", (player, npc) -> ShopManager.openIfExists(player, "cba68c44-a106-4815-9b07-eaebe7fca6bf"));

        //Sams skill shop
        NPCAction.register(4721, 1, (player, npc) ->
                player.dialogue(
                        new NPCDialogue(4721, "How can I help you today?"),
                        new OptionsDialogue(
                                new Option("I'd like to view your wares", () -> ShopManager.openIfExists(player, "9c9c490e-61b9-4aec-b4be-0321105e2d5b")),
                                new Option("Nevermind")
                        )
                ));
        NPCAction.register(4721, "trade", (player, npc) -> ShopManager.openIfExists(player, "9c9c490e-61b9-4aec-b4be-0321105e2d5b"));

        //Melee Shop
        NPCAction.register(3216, 1, (player, npc) ->
                player.dialogue(
                        new NPCDialogue(3216, "How can I help you today?"),
                        new OptionsDialogue(
                                new Option("View melee armour shop", () -> ShopManager.openIfExists(player, "81c7cb3a-f03f-4862-9d3e-3d63ddb64a8a")),
                                new Option("View melee weapons shop", () -> ShopManager.openIfExists(player, "d8644890-1eb6-47c6-a074-ccb2833f8ea6")),
                                new Option("Nevermind")
                        )
                ));
        //Melee armour shop
        //NPCAction.register(3216, "armour", (player, npc) -> ShopManager.openIfExists(player, "81c7cb3a-f03f-4862-9d3e-3d63ddb64a8a"));

        //Melee weapon shop
        //NPCAction.register(3216, "weapons", (player, npc) -> ShopManager.openIfExists(player, "d8644890-1eb6-47c6-a074-ccb2833f8ea6"));

        //Range shop
        NPCAction.register(3217, 1, (player, npc) ->
                player.dialogue(
                        new NPCDialogue(3217, "How can I help you today?"),
                        new OptionsDialogue(
                                new Option("View range armour shop", () -> ShopManager.openIfExists(player, "4643a9b8-4ccb-4180-bfbc-ad340319fef21")),
                                new Option("View range weapons shop", () -> ShopManager.openIfExists(player, "c1acaaeb-7e71-492d-97f1-ba2d38c90648")),
                                new Option("Nevermind")
                        )
                ));
        NPCAction.register(3217, 2, (player, npc) ->
                player.dialogue(
                        new NPCDialogue(3217, "How can I help you today?"),
                        new OptionsDialogue(
                                new Option("View range armour shop", () -> ShopManager.openIfExists(player, "4643a9b8-4ccb-4180-bfbc-ad340319fef21")),
                                new Option("View range weapons shop", () -> ShopManager.openIfExists(player, "c1acaaeb-7e71-492d-97f1-ba2d38c90648")),
                                new Option("Nevermind")
                        )
                ));
        //Range armour shop
        NPCAction.register(2883, "Armour", (player, npc) -> ShopManager.openIfExists(player, "4643a9b8-4ccb-4180-bfbc-ad340319fef21"));
        //Range weapon shop
        NPCAction.register(2883, "Weapons", (player, npc) -> ShopManager.openIfExists(player, "c1acaaeb-7e71-492d-97f1-ba2d38c90648"));

        //Consumables
        NPCAction.register(687, "trade", (player, npc) -> ShopManager.openIfExists(player, "347da8b3-dd23-4a46-bf05-3a452e5e1f3d"));

        //Olivias herbs
        NPCAction.register(500, 1, (player, npc) ->
                player.dialogue(
                        new NPCDialogue(500, "How can I help you today?"),
                        new OptionsDialogue(
                                new Option("I'd like to view your wares", () -> ShopManager.openIfExists(player, "25a37451-f6f4-4d6d-972a-de517e78df99")),
                                new Option("Nevermind")
                        )
                ));
        final int[] AUBURY = {2886, 11434};
        for (int aubury : AUBURY) {
            NPCAction.register(aubury, 1, (player, npc) ->
                    player.dialogue(
                            new NPCDialogue(2886, "How can I help you today?"),
                            new OptionsDialogue(
                                    new Option("I'd like to view your wares", () -> ShopManager.openIfExists(player, "jV40FSgmtyni78az1Sc4pKk8DcJIYa")),
                                    new Option("Nevermind")
                            )
                    ));
            NPCAction.register(aubury, 3, (player, npc) ->
                    player.dialogue(
                            new NPCDialogue(2886, "How can I help you today?"),
                            new OptionsDialogue(
                                    new Option("I'd like to view your wares", () -> ShopManager.openIfExists(player, "jV40FSgmtyni78az1Sc4pKk8DcJIYa")),
                                    new Option("Nevermind")
                            )
                    ));
        }
        NPCAction.register(500, "trade", (player, npc) -> ShopManager.openIfExists(player, "1f68200d-0ba3-4a2e-859d-ec1888679034"));

        //Mage shop
        NPCAction.register(3218, 1, (player, npc) ->
                player.dialogue(
                        new NPCDialogue(3218, "How can I help you today?"),
                        new OptionsDialogue(
                                new Option("View magic armour shop", () -> ShopManager.openIfExists(player, "3a66d8c1-dd6f-444e-a91e-24ea1f0135c0")),
                                new Option("View magic weapons shop", () -> ShopManager.openIfExists(player, "64473b09-f91f-44b6-93be-4c36b4d19133")),
                                new Option("Nevermind")
                        )
                ));
        //Mage armour shop
        NPCAction.register(3218, "Armour", (player, npc) -> ShopManager.openIfExists(player, "3a66d8c1-dd6f-444e-a91e-24ea1f0135c0"));
        //Mage weapon shop
        NPCAction.register(3218, "Weapons", (player, npc) -> ShopManager.openIfExists(player, "64473b09-f91f-44b6-93be-4c36b4d19133"));

        //Ironman supplies
        NPCAction.register(2814, "Talk-to", (player, npc) -> openIronShop(player));
        NPCAction.register(2814, "trade", (player, npc) -> openIronShop(player));

        //Sigmund
        NPCAction.register(3894, 1, (player, npc) ->
                player.dialogue(
                        new NPCDialogue(3894, "How can I help you today?"),
                        new OptionsDialogue(
                                new Option("I'd like to view your wares", () -> talkSigmund(player)),
                                new Option("Nevermind")
                        )
                ));
        NPCAction.register(3894, "Trade", (player, npc) ->
                ShopManager.openIfExists(player, "5464df07-5091-4ddc-8c5a-436db5ae5193"));
        NPCAction.register(3894, 4, (player, npc) -> ItemSet.open(player));

        //General store
        NPCAction.register(2821, 1, (player, npc) ->
                player.dialogue(
                        new NPCDialogue(2821, "How can I help you today?"),
                        new OptionsDialogue(
                                new Option("I'd like to view your wares", () -> ShopManager.openIfExists(player, "e522b145-fbaa-4746-8d71-6ad9808d2338")),
                                new Option("Nevermind")
                        )
                ));
        NPCAction.register(2821, "trade", (player, npc) -> ShopManager.openIfExists(player, "e522b145-fbaa-4746-8d71-6ad9808d2338"));

        //Adam
        NPCAction.register(311, "armour", (player, npc) -> Adam.pickStatus(player));

        //GE Clerks
        NPCAction.register(2148, 1, (player, npc) ->
                player.dialogue(
                        new NPCDialogue(2148, "How can I help you today?"),
                        new OptionsDialogue(
                                new Option("Open the Trading Post", () -> player.getTradePost().openViewOffers()),
                                new Option("Open the bank", () -> player.getBank().open()),
                                new Option("Collect from coffer", () -> TradePost.openCoffer(player)),
                                new Option("Nevermind")
                        )
                ));
        NPCAction.register(2149, 1, (player, npc) ->
                player.dialogue(
                        new NPCDialogue(2149, "How can I help you today?"),
                        new OptionsDialogue(
                                new Option("Open the Trading Post", () -> player.getTradePost().openViewOffers()),
                                new Option("Open the bank", () -> player.getBank().open()),
                                new Option("Collect from coffer", () -> TradePost.openCoffer(player)),
                                new Option("Nevermind")
                        )
                ));

    }
}
