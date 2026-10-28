package io.ruin.model.inter;

import io.ruin.cache.ItemDef;
import io.ruin.model.achievements.listeners.easy.GettingLearnt;
import io.ruin.model.achievements.listeners.hard.OnAConquest;
import io.ruin.model.achievements.listeners.master.CatchMeIfYouCan;
import io.ruin.model.activities.wilderness.events.capturetheflag.CaptureTheFlag;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.handlers.shopinterface.CustomShop;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.pvpinter.ArenasInter;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.actions.impl.boxes.EdgePvpBoxes;
import io.ruin.model.shop.ShopManager;

public class ConquestShop {

    static {

        NPCAction.register(7632, "Trade", (player, obj) -> CustomShop.openBMShop(player));
        NPCAction.register(7632, "Talk-to", (player, obj) -> arenaMasterDialogue(player));
        InterfaceHandler.register(725, h -> {//Main interface
            h.actions[272] = (SlotAction) (player, slot) -> purchaseButton(player);
            h.actions[81] = (SlotAction) (player, slot) -> selectTwistedBow(player);
            h.actions[473] = (SlotAction) (player, slot) -> selectDragonClaws(player);
            h.actions[485] = (SlotAction) (player, slot) -> selectGhraziRapier(player);
            h.actions[497] = (SlotAction) (player, slot) -> selectScytheofVitur(player);
            h.actions[509] = (SlotAction) (player, slot) -> selectSangStaff(player);
            h.actions[521] = (SlotAction) (player, slot) -> selectVestaTop(player);
            h.actions[533] = (SlotAction) (player, slot) -> selectVestaLegs(player);
            h.actions[545] = (SlotAction) (player, slot) -> selectVLS(player);
            h.actions[557] = (SlotAction) (player, slot) -> selectStatHelm(player);
            h.actions[569] = (SlotAction) (player, slot) -> selectStatTop(player);
            h.actions[581] = (SlotAction) (player, slot) -> selectStatLegs(player);
            h.actions[593] = (SlotAction) (player, slot) -> selectStatHammer(player);
            h.actions[605] = (SlotAction) (player, slot) -> selectZurielHood(player);
            h.actions[617] = (SlotAction) (player, slot) -> selectZurielTop(player);
            h.actions[629] = (SlotAction) (player, slot) -> selectZurielBottom(player);
            h.actions[643] = (SlotAction) (player, slot) -> selectZurielStaff(player);
            h.actions[655] = (SlotAction) (player, slot) -> selectMorrigansCoif(player);
            h.actions[667] = (SlotAction) (player, slot) -> selectMorrigansTop(player);
            h.actions[679] = (SlotAction) (player, slot) -> selectMorrigansBottom(player);
            h.actions[691] = (SlotAction) (player, slot) -> selectAncestralHat(player);
            h.actions[703] = (SlotAction) (player, slot) -> selectAncestralTop(player);
            h.actions[715] = (SlotAction) (player, slot) -> selectAncestralBottom(player);
            h.actions[727] = (SlotAction) (player, slot) -> selectJusticiarHelm(player);
            h.actions[739] = (SlotAction) (player, slot) -> selectJusticiarTop(player);
            h.actions[751] = (SlotAction) (player, slot) -> selectJusticiarBottom(player);
            h.actions[763] = (SlotAction) (player, slot) -> selectKodaiWand(player);
            h.actions[775] = (SlotAction) (player, slot) -> selectAvernic(player);
            h.actions[787] = (SlotAction) (player, slot) -> selectInferno(player);
            h.actions[799] = (SlotAction) (player, slot) -> selectNeitz(player);
            h.actions[811] = (SlotAction) (player, slot) -> selectDragonWarhammer(player);
            h.actions[823] = (SlotAction) (player, slot) -> selectDivine(player);
            h.actions[835] = (SlotAction) (player, slot) -> selectArcane(player);
            h.actions[847] = (SlotAction) (player, slot) -> selectEly(player);
            h.actions[859] = (SlotAction) (player, slot) -> selectSpectral(player);
        });
        int[][] vouchers = {
                {5020, 100},
                {5021, 500},
                {5022, 1000},
                {5023, 2000}
        };
        for (int[] voucher : vouchers) {
            int id = voucher[0];
            ItemDef.get(id).sigmundBuyPrice = voucher[1];
            ItemNPCAction.register(id, 7632, (player, item, npc) -> player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Trade your " + ItemDef.get(id).name +
                    " for " + voucher[1] + " pkp?", item, () -> {
                item.remove();
                player.pkPoints += voucher[1];
                player.dialogue(new NPCDialogue(npc, "Excellent find, " + player.name() + "! If you have anymore vouchers, you know where to find me!"));
            })));
        }
        ItemNPCAction.register(25646, 7632, (player, item, npc) ->
                player.dialogue(
                        new OptionsDialogue("Exchange your small caskets?",
                                new Option("Exchange 10 small caskets for 1 large casket", () -> EdgePvpBoxes.swapSmallsForLarge(player)),
                                new Option("Exchange 3 small caskets for 1 medium casket", () -> EdgePvpBoxes.swapSmallsForMeds(player)),
                                new Option("Nevermind"))
                )
        );
        ItemNPCAction.register(25648, 7632, (player, item, npc) ->
                player.dialogue(
                        new OptionsDialogue("Exchange your medium caskets?",
                                new Option("Exchange 5 medium caskets for 1 large casket", () -> EdgePvpBoxes.swapMedsForLarge(player)),
                                new Option("Nevermind"))
                )
        );
    }


    public static boolean selectedTbow, selectedDclaws, selectedGhraziRapier, selectedScythe, selectedSang,
            selectedVestaTop, selectedVestaLegs, selectedVls, selectedStatHelm, selectedStatBody, selectedStatLegs,
            selectedStatHammer, selectedZurielHood, selectedZurielTop, selectedZurielBottom, selectedZurielStaff,
            selectedMorrigansCoif, selectedMorrigansTop, selectedMorrigansBottom, selectedAncestralHat,
            selectedAncestralTop, selectedAncestralBottom, selectedJusticarHelm, selectedJusticarTop,
            selectedJusticarBottom, selectedKodaiWand, selectedAvernic, selectedInferno, selectedNeitzFaceguard,
            selectedDragonWarhammer, selectedDivine, selectedArcane, selectedEly, selectedSpectral = false;

    public static void arenaMasterDialogue(Player player) {
        if (player.getInventory().contains(8970) || player.getEquipment().contains(8970)) {
            player.dialogue(
                    new PlayerDialogue("I have retrieved the Deadman's Flag! I am here to claim my reward."),
                    new NPCDialogue(7632, "Wonderful job, adventurer. I have your reward here somewhere..." +
                            " Ah, here it is!"),
                    new ActionDialogue(() -> {
                        giveCaptureFlagReward(player);
                    })
            );
        } else {
            player.dialogue(
                    new NPCDialogue(7632, "Greetings, adventurer. What can I do for you?"),
                    new OptionsDialogue(
                            new Option("What are you doing here?", () -> whatAreYouDoingHere(player)),
                            new Option("Tell me more about Arenas/Battlegrounds", () -> tellMeMore(player)),
                            new Option("I'd like to view your shop", () -> viewShop(player)),
                            new Option("I want to enter an Arena/Battleground", () -> enter(player)),
                            new Option("Nevermind")
                    )
            );
        }
    }

    public static void giveCaptureFlagReward(Player p) {
        if (!p.getEquipment().contains(8970))
            return;
        p.dialogue(new ItemDialogue().one(3461, "You receive a Great Vault Key for successfully Capturing the Flag!"));
        CatchMeIfYouCan.complete(p);
        p.getInventory().addOrDrop(3461, 1);
        p.getEquipment().remove(8970, 1);
        CaptureTheFlag.endEvent(true);
    }

    public static void whatAreYouDoingHere(Player p) {
        p.dialogue(
                new NPCDialogue(7632, "I am the Master of OSPK's Arenas and Battlegrounds." +
                        " Through me, you can learn more about these PvP minigames, enter an Arena or Battleground, or view my shop."),
                new ActionDialogue(() -> {
                    GettingLearnt.complete(p);
                })
        );
    }

    public static void tellMeMore(Player p) {

    }

    public static void viewShop(Player p) {
        ShopManager.openIfExists(p, "183ba085-4bec-44e0-96b5-50752b4a7ec9");
    }

    public static void enter(Player p) {
        ArenasInter.openInterface(p);
    }

    public static void purchaseButton(Player p) {
        if (selectedTbow = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Twisted Bow for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseTbow(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedDclaws = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Dragon Claws for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseDclaws(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedGhraziRapier = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Ghrazi Rapier for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseGhrazi(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedScythe = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Scythe of Vitur for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseScythe(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedSang = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Sanguinesti Staff for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseSang(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedVestaTop = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Vesta's Chainbody for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseVestaTop(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedVestaLegs = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Vesta's Chainskirt for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseVestaLegs(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedVls = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Vesta's Longsword for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseVls(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedStatHelm = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Statius's Full Helm for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseStatHelm(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedStatBody = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Statius's Platebody for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseStatBody(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedStatLegs = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Statius's Platelegs for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseStatLegs(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedStatHammer = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Statius's Warhammer for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseStatHammer(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedZurielHood = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Zuriel's Hood for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseZurielsHood(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedZurielTop = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Zuriel's Robe Top for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseZurielsTop(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedZurielBottom = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Zuriel's Robe Bottom for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseZurielsBottom(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedZurielStaff = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Zuriel's Staff for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseZurielsStaff(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedMorrigansCoif = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Morrigan's Coif for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseMorrigansCoif(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedMorrigansTop = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Morrigan's Leather Body for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseMorrigansTop(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedMorrigansBottom = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Morrigan's Leather Chaps for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseMorrigansBottom(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedAncestralHat = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Ancestral Hat for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseAncestralHat(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedAncestralTop = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Ancestral Robe Top for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseAncestralTop(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedAncestralBottom = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Ancestral Robe Bottom for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseAncestralBottom(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedJusticarHelm = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Justiciar Faceguard for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseJusticarHelm(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedJusticarTop = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Justiciar Chestguard for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseJusticarTop(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedJusticarBottom = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Justiciar Legguards for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseJusticarBottom(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedKodaiWand = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Kodai Wand for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseKodaiWand(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedAvernic = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Avernic Defender for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseAvernic(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedInferno = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Infernal Cape for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseInferno(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedNeitzFaceguard = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Neitiznot Faceguard for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseNeitiznot(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedDragonWarhammer = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Dragon Warhammer for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseDragonWarhammer(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedDivine = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Divine Spirit Shield for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseDivine(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedArcane = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Arcane Spirit Shield for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseArcane(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedEly = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Elysian Spirit Shield for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseEly(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
        if (selectedSpectral = true && p.pvpRating >= 2200) {
            p.dialogue(
                    new OptionsDialogue("You have selected to purchase the Spectral Spirit Shield for 17,000 conquest points." +
                            " Do you wish to continue?",
                            new Option("Yes, I want to purchase this item.", () -> purchaseSpectral(p)),
                            new Option("No, nevermind."))
            );
        } else if (p.pvpRating < 2200) {
            p.dialogue(
                    new MessageDialogue("You must have a PvP Rating of atleast 2,200 or higher to purchase this item.")
            );
        }
    }

    public static void itemPrices(Player p) {
        p.getPacketSender().sendString(725, 278, "17,000");//Tbow
        p.getPacketSender().sendString(725, 475, "12,000");//D Claws
        p.getPacketSender().sendString(725, 487, "19,000");//Ghrazi Rapier

        p.getPacketSender().sendString(725, 640, "42069");//Conquest Points
    }

    public static void openInter(Player p) {
        p.openInterface(InterfaceType.MAIN, 725);
        itemPrices(p);
        p.getPacketSender().sendString(725, 273, "Select an item");//Item name goes here
        p.getPacketSender().sendString(725, 274, "");//Item purchase reqs here
        p.getPacketSender().sendString(725, 640, "42069");//Conquest Points
    }

    public static void purchaseTbow(Player p) {
        int cost = 17000;
        if (p.conquestPoints >= 17000) {
            p.conquestPoints -= cost;
            p.getInventory().addOrDrop(20997, 1);
            OnAConquest.complete(p);
            p.dialogue(
                    new ItemDialogue().one(20997, "Congratulations, you have purchased a Twisted Bow!")
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseDclaws(Player p) {
        int cost = 17000;
        if (p.conquestPoints >= 17000) {
            p.conquestPoints -= cost;
            p.getInventory().addOrDrop(20784, 1);
            OnAConquest.complete(p);
            p.dialogue(
                    new ItemDialogue().one(20784, "Congratulations, you have purchased Dragon Claws!")
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseGhrazi(Player p) {
        int cost = 17000;
        if (p.conquestPoints >= 17000) {
            p.conquestPoints -= cost;
            p.getInventory().addOrDrop(22324, 1);
            OnAConquest.complete(p);
            p.dialogue(
                    new ItemDialogue().one(22324, "Congratulations, you have purchased a Ghrazi Rapier!")
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseScythe(Player p) {
        int cost = 17000;
        if (p.conquestPoints >= 17000) {
            p.conquestPoints -= cost;
            p.getInventory().addOrDrop(22325, 1);
            OnAConquest.complete(p);
            p.dialogue(
                    new ItemDialogue().one(22325, "Congratulations, you have purchased a Scythe of Vitur!")
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseSang(Player p) {
        int cost = 17000;
        if (p.conquestPoints >= 17000) {
            p.conquestPoints -= cost;
            p.getInventory().addOrDrop(22323, 1);
            OnAConquest.complete(p);
            p.dialogue(
                    new ItemDialogue().one(22323, "Congratulations, you have purchased a Sanguinesti Staff!")
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseVestaTop(Player p) {
        int cost = 17000;
        if (p.conquestPoints >= 17000) {
            p.conquestPoints -= cost;
            p.getInventory().addOrDrop(22616, 1);
            OnAConquest.complete(p);
            p.dialogue(
                    new ItemDialogue().one(22616, "Congratulations, you have purchased a Vesta's Chainbody!")
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseVestaLegs(Player p) {
        int cost = 17000;
        if (p.conquestPoints >= 17000) {
            p.conquestPoints -= cost;
            p.getInventory().addOrDrop(22619, 1);
            OnAConquest.complete(p);
            p.dialogue(
                    new ItemDialogue().one(22619, "Congratulations, you have purchased a Vesta's Chainskirt!")
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseVls(Player p) {
        int cost = 17000;
        if (p.conquestPoints >= 17000) {
            p.conquestPoints -= cost;
            p.getInventory().addOrDrop(22613, 1);
            OnAConquest.complete(p);
            p.dialogue(
                    new ItemDialogue().one(22613, "Congratulations, you have purchased a Vesta's Longsword!")
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseStatHelm(Player p) {
        int cost = 17000;
        if (p.conquestPoints >= 17000) {
            p.conquestPoints -= cost;
            p.getInventory().addOrDrop(22625, 1);
            OnAConquest.complete(p);
            p.dialogue(
                    new ItemDialogue().one(22625, "Congratulations, you have purchased a Statius's Full Helm!")
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseStatBody(Player p) {
        int cost = 17000;
        if (p.conquestPoints >= 17000) {
            p.conquestPoints -= cost;
            p.getInventory().addOrDrop(22628, 1);
            OnAConquest.complete(p);
            p.dialogue(
                    new ItemDialogue().one(22628, "Congratulations, you have purchased a Statius's Platebody!")
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseStatLegs(Player p) {
        int cost = 17000;
        if (p.conquestPoints >= 17000) {
            p.conquestPoints -= cost;
            p.getInventory().addOrDrop(22631, 1);
            OnAConquest.complete(p);
            p.dialogue(
                    new ItemDialogue().one(22631, "Congratulations, you have purchased a Statius's Platelegs!")
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseStatHammer(Player p) {
        int cost = 17000;
        if (p.conquestPoints >= 17000) {
            p.conquestPoints -= cost;
            p.getInventory().addOrDrop(23620, 1);
            OnAConquest.complete(p);
            p.dialogue(
                    new ItemDialogue().one(23620, "Congratulations, you have purchased a Statius's Warhammer!")
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseZurielsHood(Player p) {
        int cost = 17000;
        if (p.conquestPoints >= 17000) {
            p.conquestPoints -= cost;
            p.getInventory().addOrDrop(22650, 1);
            OnAConquest.complete(p);
            p.dialogue(
                    new ItemDialogue().one(22650, "Congratulations, you have purchased a Zuriel's Hood!")
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseZurielsTop(Player p) {
        int cost = 17000;
        if (p.conquestPoints >= 17000) {
            p.conquestPoints -= cost;
            p.getInventory().addOrDrop(22653, 1);
            OnAConquest.complete(p);
            p.dialogue(
                    new ItemDialogue().one(22653, "Congratulations, you have purchased a Zuriel's Robe Top!")
            );
        } else {
            p.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseZurielsBottom(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(22656, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(22656, "Congratulations, you have purchased a Zuriel's Robe Bottom!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseZurielsStaff(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(22647, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(22647, "Congratulations, you have purchased a Zuriel's Staff!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseMorrigansCoif(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(22638, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(22638, "Congratulations, you have purchased a Morrigan's Coif!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseMorrigansTop(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(22641, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(22641, "Congratulations, you have purchased a Morrigan's Leather Body!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseMorrigansBottom(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(22644, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(22644, "Congratulations, you have purchased a Morrigan's Leather Chaps!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseAncestralHat(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(21018, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(21018, "Congratulations, you have purchased a Ancestral Hat!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseAncestralTop(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(21021, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(21021, "Congratulations, you have purchased a Ancestral Robe Top!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseAncestralBottom(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(21024, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(21024, "Congratulations, you have purchased a Ancestral Robe Bottom!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseJusticarHelm(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(22326, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(22326, "Congratulations, you have purchased a Justiciar Faceguard!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseJusticarTop(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(22327, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(22327, "Congratulations, you have purchased a Justiciar Chestguard!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseJusticarBottom(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(22328, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(22328, "Congratulations, you have purchased a Justiciar Legguards!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseKodaiWand(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(21006, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(21006, "Congratulations, you have purchased a Kodai Wand!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseAvernic(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(22322, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(22322, "Congratulations, you have purchased a Avernic Defender!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseInferno(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(21295, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(21295, "Congratulations, you have purchased a Infernal Cape!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseNeitiznot(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(24271, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(24271, "Congratulations, you have purchased a Neitiznot Faceguard!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseDragonWarhammer(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(13576, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(13576, "Congratulations, you have purchased a Dragon Warhammer!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseDivine(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(30191, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(30191, "Congratulations, you have purchased a Divine Spirit Shield!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseArcane(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(12825, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(12825, "Congratulations, you have purchased a Arcane Spirit Shield!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseEly(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(12817, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(12817, "Congratulations, you have purchased a Elysian Spirit Shield!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }

    public static void purchaseSpectral(Player player) {
        int cost = 17000;
        if (player.conquestPoints >= 17000) {
            player.conquestPoints -= cost;
            player.getInventory().addOrDrop(12821, 1);
            OnAConquest.complete(player);
            player.dialogue(
                    new ItemDialogue().one(12821, "Congratulations, you have purchased a Spectral Spirit Shield!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required Conquest Points to purchase this item.")
            );
        }
    }


    public static void selectTwistedBow(Player player) {
        selectedTbow = true;
        player.getPacketSender().sendString(725, 273, "Twisted Bow");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectDragonClaws(Player player) {
        selectedDclaws = true;
        player.getPacketSender().sendString(725, 273, "Dragon Claws");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectGhraziRapier(Player player) {
        selectedGhraziRapier = true;
        player.getPacketSender().sendString(725, 273, "Ghrazi Rapier");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectScytheofVitur(Player player) {
        selectedScythe = true;
        player.getPacketSender().sendString(725, 273, "Scythe of Vitur");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectSangStaff(Player player) {
        selectedSang = true;
        player.getPacketSender().sendString(725, 273, "Sanguinesti Staff");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectVestaTop(Player player) {
        selectedVestaTop = true;
        player.getPacketSender().sendString(725, 273, "Vesta's Chainbody");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectVestaLegs(Player player) {
        selectedVestaLegs = true;
        player.getPacketSender().sendString(725, 273, "Vesta's Chainskirt");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectVLS(Player player) {
        selectedVls = true;
        player.getPacketSender().sendString(725, 273, "Vesta's Longsword");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectStatHelm(Player player) {
        selectedStatHelm = true;
        player.getPacketSender().sendString(725, 273, "Statius's Full Helm");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectStatTop(Player player) {
        selectedStatBody = true;
        player.getPacketSender().sendString(725, 273, "Statius's Platebody");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectStatLegs(Player player) {
        selectedStatLegs = true;
        player.getPacketSender().sendString(725, 273, "Statius's Platelegs");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectStatHammer(Player player) {
        selectedStatHammer = true;
        player.getPacketSender().sendString(725, 273, "Statius's Warhammer");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectZurielHood(Player player) {
        selectedZurielHood = true;
        player.getPacketSender().sendString(725, 273, "Zuriel's Hood");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectZurielTop(Player player) {
        selectedZurielTop = true;
        player.getPacketSender().sendString(725, 273, "Zuriel's Robe Top");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectZurielBottom(Player player) {
        selectedZurielBottom = true;
        player.getPacketSender().sendString(725, 273, "Zuriel's Robe Bottom");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectZurielStaff(Player player) {
        selectedZurielStaff = true;
        player.getPacketSender().sendString(725, 273, "Zuriel's Staff");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectMorrigansCoif(Player player) {
        selectedMorrigansCoif = true;
        player.getPacketSender().sendString(725, 273, "Morrigan's Coif");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectMorrigansTop(Player player) {
        selectedMorrigansTop = true;
        player.getPacketSender().sendString(725, 273, "Morrigan's Leather Body");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectMorrigansBottom(Player player) {
        selectedMorrigansBottom = true;
        player.getPacketSender().sendString(725, 273, "Morrigan's Leather Chaps");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectAncestralHat(Player player) {
        selectedAncestralHat = true;
        player.getPacketSender().sendString(725, 273, "Ancestral Hat");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectAncestralTop(Player player) {
        selectedAncestralTop = true;
        player.getPacketSender().sendString(725, 273, "Ancestral Robe Top");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectAncestralBottom(Player player) {
        selectedAncestralBottom = true;
        player.getPacketSender().sendString(725, 273, "Ancestral Robe Bottom");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectJusticiarHelm(Player player) {
        selectedJusticarHelm = true;
        player.getPacketSender().sendString(725, 273, "Justiciar Faceguard");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectJusticiarTop(Player player) {
        selectedJusticarTop = true;
        player.getPacketSender().sendString(725, 273, "Justiciar Chestguard");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectJusticiarBottom(Player player) {
        selectedJusticarBottom = true;
        player.getPacketSender().sendString(725, 273, "Justiciar Legguards");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectKodaiWand(Player player) {
        selectedKodaiWand = true;
        player.getPacketSender().sendString(725, 273, "Kodai Wand");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectAvernic(Player player) {
        selectedAvernic = true;
        player.getPacketSender().sendString(725, 273, "Avernic Defender");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectInferno(Player player) {
        selectedInferno = true;
        player.getPacketSender().sendString(725, 273, "Infernal Cape");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectNeitz(Player player) {
        selectedNeitzFaceguard = true;
        player.getPacketSender().sendString(725, 273, "Neitiznot Faceguard");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectDragonWarhammer(Player player) {
        selectedDragonWarhammer = true;
        player.getPacketSender().sendString(725, 273, "Dragon Warhammer");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectDivine(Player player) {
        selectedDivine = true;
        player.getPacketSender().sendString(725, 273, "Divine Spirit Shield");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectArcane(Player player) {
        selectedArcane = true;
        player.getPacketSender().sendString(725, 273, "Arcane Spirit Shield");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectEly(Player player) {
        selectedEly = true;
        player.getPacketSender().sendString(725, 273, "Elysian Spirit Shield");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

    public static void selectSpectral(Player player) {
        selectedSpectral = true;
        player.getPacketSender().sendString(725, 273, "Spectral Spirit Shield");//Item name goes here
        player.getPacketSender().sendString(725, 274, "Requires 2,200 arena rating to purchase");//Item purchase reqs here
    }

}