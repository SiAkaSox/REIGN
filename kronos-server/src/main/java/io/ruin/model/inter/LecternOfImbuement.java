package io.ruin.model.inter;

import io.ruin.Server;
import io.ruin.model.achievements.listeners.easy.ImbuedKnowledge;
import io.ruin.model.achievements.listeners.medium.ImbuedPower;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;

import static io.ruin.Server.STARTUP_THREAD;
import static io.ruin.cache.ItemID.COINS_995;

public class LecternOfImbuement {

    static {
        
            ObjectAction.register(28802, 1, (player, object) ->
                    openInterface(player));
            InterfaceHandler.register(724, h -> {//Main interface
                h.actions[33] = (SlotAction) (player, slot) -> selectSaradominCape(player);
                h.actions[35] = (SlotAction) (player, slot) -> selectZamorakCape(player);
                h.actions[43] = (SlotAction) (player, slot) -> selectGuthixCape(player);
                h.actions[44] = (SlotAction) (player, slot) -> selectBerserkerRing(player);
                h.actions[46] = (SlotAction) (player, slot) -> selectSeersRing(player);
                h.actions[48] = (SlotAction) (player, slot) -> selectArcherRing(player);
                h.actions[50] = (SlotAction) (player, slot) -> selectWarriorRIng(player);
                h.actions[52] = (SlotAction) (player, slot) -> selectSuffering(player);
                h.actions[54] = (SlotAction) (player, slot) -> selectGods(player);
                h.actions[56] = (SlotAction) (player, slot) -> selectGraniteRing(player);
                h.actions[58] = (SlotAction) (player, slot) -> selectTreasonous(player);
                h.actions[60] = (SlotAction) (player, slot) -> selectTyrannical(player);
                h.actions[62] = (SlotAction) (player, slot) -> selectBlackMask(player);
                h.actions[64] = (SlotAction) (player, slot) -> selectSlayerHelmet(player);
                h.actions[66] = (SlotAction) (player, slot) -> selectBlackSlayerHelmet(player);
                h.actions[68] = (SlotAction) (player, slot) -> selectGreenSlayerHelmet(player);
                h.actions[70] = (SlotAction) (player, slot) -> selectPurpleSlayerHelmet(player);
                h.actions[72] = (SlotAction) (player, slot) -> selectRedSlayerHelmet(player);
                h.actions[74] = (SlotAction) (player, slot) -> selectTurquoiseSlayerHelmet(player);
                h.actions[76] = (SlotAction) (player, slot) -> selectHydraSlayerHelmet(player);
                h.actions[96] = (SlotAction) (player, slot) -> pressImbueButton(player);
            });
            ItemObjectAction.register(24370, 28802, (player, item, obj) -> {
                imbueTwistedDialogue(player);
            });
            ItemObjectAction.register(25904, 28802, (player, item, obj) -> {
                imbueVampyricDialogue(player);
            });
            ItemObjectAction.register(25898, 28802, (player, item, obj) -> {
                imbueJadDialogue(player);
            });
            ItemObjectAction.register(25910, 28802, (player, item, obj) -> {
                imbueZukDialogue(player);
            });
    }

    public static boolean selectedSaradominCape, selectedZamorakCape, selectedGuthixCape, selectedBerserkerRing,
            selectedSeersRing, selectedArchersRing, selectedWarriorRing, selectedSuffering, selectedRingOfGods, selectedGraniteRing,
            selectedTreasonous, selectedTyrannical, selectedBlackMask, selectedSlayerHelmet, selectedBlackSlayerHelmet,
            selectedGreenSlayerHelmet, selectedPurpleSlayerHelmet, selectedRedSlayerHelmet, selectedTurquoiseSlayerHelmet,
            selectedCrystalBow;

    public static int test = 0;

    public static void pressImbueButton(Player player) {
        if (selectedSaradominCape) {
            if (player.getInventory().contains(2412) && player.getInventory().getAmount(COINS_995) >= 8000000) {
                player.dialogue(
                        new OptionsDialogue("Imbue your Saradomin Cape?" +
                                " This cannot be reversed!",
                                new Option("Yes", () -> imbueSaradominCape(player)),
                                new Option("No"))
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedZamorakCape) {
            if (player.getInventory().contains(2414) && player.getInventory().getAmount(COINS_995) >= 8000000) {
                player.dialogue(
                        new OptionsDialogue("Imbue your Zamorak Cape?" +
                                " This cannot be reversed!",
                                new Option("Yes", () -> imbueZamorakCape(player)),
                                new Option("No"))
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedGuthixCape) {
            if (player.getInventory().contains(2413) && player.getInventory().getAmount(COINS_995) >= 8000000) {
                player.dialogue(
                        new OptionsDialogue("Imbue your Guthix Cape?" +
                                " This cannot be reversed!",
                                new Option("Yes", () -> imbueGuthixCape(player)),
                                new Option("No"))
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedBerserkerRing) {
            if (player.getInventory().contains(6737) && player.getInventory().getAmount(COINS_995) >= 15000000) {
                player.dialogue(
                        new OptionsDialogue("Imbue your Berserker Ring?" +
                                " This cannot be reversed!",
                                new Option("Yes", () -> imbueBerserkerRing(player)),
                                new Option("No"))
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedSeersRing) {
                if (player.getInventory().contains(6731) && player.getInventory().getAmount(COINS_995) >= 12000000) {
            player.dialogue(
                    new OptionsDialogue("Imbue your Seers Ring?" +
                            " This cannot be reversed!",
                            new Option("Yes", () -> imbueSeersRing(player)),
                            new Option("No"))
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required items in your inventory to do this.")
            );
        }
    }
        if (selectedArchersRing) {
                if (player.getInventory().contains(6733) && player.getInventory().getAmount(COINS_995) >= 15000000) {
            player.dialogue(
                    new OptionsDialogue("Imbue your Archer Ring?" +
                            " This cannot be reversed!",
                            new Option("Yes", () -> imbueArcherRing(player)),
                            new Option("No"))
            );
        } else {
                    player.dialogue(
                            new MessageDialogue("You do not have the required items in your inventory to do this.")
                    );
                }
        }
        if (selectedWarriorRing) {
                if (player.getInventory().contains(6735) && player.getInventory().getAmount(COINS_995) >= 7000000) {
            player.dialogue(
                    new OptionsDialogue("Imbue your Warrior Ring?" +
                            " This cannot be reversed!",
                            new Option("Yes", () -> imbueWarriorRing(player)),
                            new Option("No"))
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
       if (selectedSuffering) {
                if (player.getInventory().contains(19550) && player.getInventory().getAmount(COINS_995) >= 24000000) {
            player.dialogue(
                    new OptionsDialogue("Imbue your Ring of Suffering?" +
                            " This cannot be reversed!",
                            new Option("Yes", () -> imbueSuffering(player)),
                            new Option("No"))
            );

        } else {
           player.dialogue(
                   new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedRingOfGods) {
            if (player.getInventory().contains(12601) && player.getInventory().getAmount(COINS_995) >= 22000000) {
                player.dialogue(
                        new OptionsDialogue("Imbue your Ring of the Gods?" +
                                " This cannot be reversed!",
                                new Option("Yes", () -> imbueGods(player)),
                                new Option("No"))
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedGraniteRing) {
                if (player.getInventory().contains(21739) && player.getInventory().getAmount(COINS_995) >= 12000000) {
            player.dialogue(
                    new OptionsDialogue("Imbue your Granite Ring?" +
                            " This cannot be reversed!",
                            new Option("Yes", () -> imbueGraniteRing(player)),
                            new Option("No"))
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedTreasonous) {
            if (player.getInventory().contains(12605) && player.getInventory().getAmount(COINS_995) >= 17000000) {
                player.dialogue(
                        new OptionsDialogue("Imbue your Treasonous Ring?" +
                                " This cannot be reversed!",
                                new Option("Yes", () -> imbueTreasonous(player)),
                                new Option("No"))
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedTyrannical) {
            if (player.getInventory().contains(12603) && player.getInventory().getAmount(COINS_995) >= 22000000) {
                player.dialogue(
                        new OptionsDialogue("Imbue your Tyrannical Ring?" +
                                " This cannot be reversed!",
                                new Option("Yes", () -> imbueTyrannical(player)),
                                new Option("No"))
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedBlackMask) {
            if (player.getInventory().contains(8901) && player.getInventory().getAmount(COINS_995) >= 12000000) {
                player.dialogue(
                        new OptionsDialogue("Imbue your Black Mask?" +
                                " This cannot be reversed!",
                                new Option("Yes", () -> imbueBlackMask(player)),
                                new Option("No"))
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedSlayerHelmet) {
                if (player.getInventory().contains(11864) && player.getInventory().getAmount(COINS_995) >= 30000000) {
            player.dialogue(
                    new OptionsDialogue("Imbue your Slayer Helmet?" +
                            " This cannot be reversed!",
                            new Option("Yes", () -> imbueSlayerHelmet(player)),
                            new Option("No"))
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedBlackSlayerHelmet) {
            if (player.getInventory().contains(19639) && player.getInventory().getAmount(COINS_995) >= 35000000) {
                player.dialogue(
                        new OptionsDialogue("Imbue your Black Slayer Helmet?" +
                                " This cannot be reversed!",
                                new Option("Yes", () -> imbueBlackSlayerHelmet(player)),
                                new Option("No"))
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedGreenSlayerHelmet) {
            if (player.getInventory().contains(19643) && player.getInventory().getAmount(COINS_995) >= 35000000) {
                player.dialogue(
                        new OptionsDialogue("Imbue your Green Slayer Helmet?" +
                                " This cannot be reversed!",
                                new Option("Yes", () -> imbueGreenSlayerHelmet(player)),
                                new Option("No"))
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedPurpleSlayerHelmet) {
            if (player.getInventory().contains(21264) && player.getInventory().getAmount(COINS_995) >= 35000000) {
                player.dialogue(
                        new OptionsDialogue("Imbue your Purple Slayer Helmet?" +
                                " This cannot be reversed!",
                                new Option("Yes", () -> imbuePurpleSlayerHelmet(player)),
                                new Option("No"))
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedRedSlayerHelmet) {
            if (player.getInventory().contains(19647) && player.getInventory().getAmount(COINS_995) >= 35000000) {
                player.dialogue(
                        new OptionsDialogue("Imbue your Red Slayer Helmet?" +
                                " This cannot be reversed!",
                                new Option("Yes", () -> imbueRedSlayerHelmet(player)),
                                new Option("No"))
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
        if (selectedTurquoiseSlayerHelmet) {
                if (player.getInventory().contains(21888) && player.getInventory().getAmount(COINS_995) >= 35000000) {
                    player.dialogue(
                            new OptionsDialogue("Imbue your Turquoise Slayer Helmet?" +
                                    " This cannot be reversed!",
                                    new Option("Yes", () -> imbueTurquoiseSlayerHelmet(player)),
                                    new Option("No"))
                    );
                } else{
                    player.dialogue(
                            new MessageDialogue("You do not have the required items in your inventory to do this.")
                    );
                }
            }
        if (selectedCrystalBow) {
                if (player.getInventory().contains(23073) && player.getInventory().getAmount(COINS_995) >= 35000000) {
            player.dialogue(
                    new OptionsDialogue("Imbue your Hydra slayer helm?" +
                            " This cannot be reversed!",
                            new Option("Yes", () -> imbueHydraSlayerHelmet(player)),
                            new Option("No"))
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required items in your inventory to do this.")
                );
            }
        }
    }

    public static void resetBooleans() {
        selectedSaradominCape = false;selectedZamorakCape = false;
        selectedGuthixCape = false;selectedBerserkerRing = false;selectedSeersRing = false;
        selectedArchersRing = false;selectedWarriorRing = false;selectedSuffering = false;
        selectedRingOfGods = false;selectedGraniteRing = false;selectedTreasonous = false;
        selectedTyrannical = false;selectedBlackMask = false;selectedSlayerHelmet = false;
        selectedBlackSlayerHelmet = false;selectedGreenSlayerHelmet = false;selectedRedSlayerHelmet = false;
        selectedPurpleSlayerHelmet = false;selectedTurquoiseSlayerHelmet =false;selectedCrystalBow = false;
    }

    public static void openInterface(Player p) {
        p.openInterface(InterfaceType.MAIN, 724);
        p.getPacketSender().sendString(724, 90, "Select An Item");
        p.getPacketSender().sendString(724, 93, "");
        p.getPacketSender().sendString(724, 95, "");
        p.getPacketSender().sendString(724, 77, "Hydra Slayer Helmet");
        ImbuedKnowledge.complete(p);
    }

    public static void selectSaradominCape(Player p) {
        resetBooleans();
        selectedSaradominCape = true;
        p.openInterface(InterfaceType.MAIN, 724);
        p.getPacketSender().sendString(724, 90, "Saradomin Cape (i)");
        p.getPacketSender().sendString(724, 93, "8m Coins");
        p.getPacketSender().sendModel(724, 87, 34261);
        p.getPacketSender().sendModelInformation(724, 87, 800, 424, 528);
    }
    public static void imbueSaradominCape(Player player) {
        int cost = 8000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 6000000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 4000000;
        }
        if (player.getInventory().hasItem(2412, 1) && player.getInventory().hasItem(995, 8000000)) {
            player.getInventory().remove(2412, 1);
            player.getInventory().remove(995, cost);
            player.animate(4462);
            player.graphics(759);
            player.getInventory().add(21791, 1);
            player.sendMessage("You have successfully imbued your Saradomin Cape.");
            ImbuedPower.complete(player);
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required items in your inventory to do this.")
            );
        }
    }
    public static void selectZamorakCape(Player player) {
        resetBooleans();
        selectedZamorakCape = true;
        player.getPacketSender().sendString(724, 90, "Zamorak Cape (i)");
        player.getPacketSender().sendString(724, 93, "8m Coins");
        player.getPacketSender().sendModel(724, 87, 34166);
        player.getPacketSender().sendModelInformation(724, 87, 800, 424, 528);
    }
    public static void imbueZamorakCape(Player player) {
        int cost = 8000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 6000000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 4000000;
        }
        player.getInventory().remove(2414, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(21795, 1);
        player.sendMessage("You have successfully imbued your Zamorak Cape.");
        ImbuedPower.complete(player);
    }
    public static void selectGuthixCape(Player player) {
        resetBooleans();
        selectedGuthixCape = true;
        player.getPacketSender().sendString(724, 90, "Guthix Cape (i)");
        player.getPacketSender().sendString(724, 93, "8m Coins");
        player.getPacketSender().sendModel(724, 87, 34167);
        player.getPacketSender().sendModelInformation(724, 87, 800, 424, 528);
    }
    public static void imbueGuthixCape(Player player) {
        int cost = 8000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 6000000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 4000000;
        }
        player.getInventory().remove(2413, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(21793, 1);
        player.sendMessage("You have successfully imbued your Guthix Cape.");
        ImbuedPower.complete(player);
    }
    public static void selectBerserkerRing(Player player) {
        resetBooleans();
        selectedBerserkerRing = true;
        player.getPacketSender().sendString(724, 90, "Berserker Ring (i)");
        player.getPacketSender().sendString(724, 93, "15m Coins");
        player.getPacketSender().sendModel(724, 87, 21847);
        player.getPacketSender().sendModelInformation(724, 87, 350, 324, 1916);
    }
    public static void imbueBerserkerRing(Player player) {
        int cost = 15000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 11500000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 7500000;
        }
        player.getInventory().remove(6737, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(11773, 1);
        player.sendMessage("You have successfully imbued your Berserker Ring.");
        ImbuedPower.complete(player);
    }
    public static void selectSeersRing(Player player) {
        resetBooleans();
        selectedSeersRing = true;
        player.getPacketSender().sendString(724, 90, "Seers Ring (i)");
        player.getPacketSender().sendString(724, 93, "12m Coins");
        player.getPacketSender().sendModel(724, 87, 21850);
        player.getPacketSender().sendModelInformation(724, 87, 350, 340, 1940);
    }
    public static void imbueSeersRing(Player player) {
        int cost = 12000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 9000000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 6000000;
        }
        player.getInventory().remove(6731, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(11770, 1);
        player.sendMessage("You have successfully imbued your Seers Ring.");
        ImbuedPower.complete(player);
    }
    public static void selectArcherRing(Player player) {
        resetBooleans();
        selectedArchersRing = true;
        player.getPacketSender().sendString(724, 90, "Archer Ring (i)");
        player.getPacketSender().sendString(724, 93, "15m Coins");
        player.getPacketSender().sendModel(724, 87, 21848);
        player.getPacketSender().sendModelInformation(724, 87, 350, 332, 1904);
    }
    public static void imbueArcherRing(Player player) {
        int cost = 15000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 11500000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 7500000;
        }
        player.getInventory().remove(6733, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(11771, 1);
        player.sendMessage("You have successfully imbued your Archer Ring.");
        ImbuedPower.complete(player);
    }
    public static void selectWarriorRIng(Player player) {
        resetBooleans();
        selectedWarriorRing = true;
        player.getPacketSender().sendString(724, 90, "Warrior Ring (i)");
        player.getPacketSender().sendString(724, 93, "7m Coins");
        player.getPacketSender().sendModel(724, 87, 21849);
        player.getPacketSender().sendModelInformation(724, 87, 350, 348, 1776);
    }
    public static void imbueWarriorRing(Player player) {
        int cost = 15000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 5250000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 3500000;
        }
        player.getInventory().remove(6735, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(11772, 1);
        player.sendMessage("You have successfully imbued your Warrior Ring.");
        ImbuedPower.complete(player);
    }
    public static void selectGraniteRing(Player player) {
        resetBooleans();
        selectedGraniteRing = true;
        player.getPacketSender().sendString(724, 90, "Granite Ring (i)");
        player.getPacketSender().sendString(724, 93, "12m Coins");
        player.getPacketSender().sendModel(724, 87, 34249);
        player.getPacketSender().sendModelInformation(724, 87, 450, 377, 1936);
    }
    public static void imbueGraniteRing(Player player) {
        int cost = 15000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 9000000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 6000000;
        }
        player.getInventory().remove(21739, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(21752, 1);
        player.sendMessage("You have successfully imbued your Granite Ring.");
        ImbuedPower.complete(player);
    }
    public static void selectSuffering(Player player) {
        resetBooleans();
        selectedSuffering = true;
        player.getPacketSender().sendString(724, 90, "Ring of Suffering (i)");
        player.getPacketSender().sendString(724, 93, "24m Coins");
        player.getPacketSender().sendModel(724, 87, 31519);
        player.getPacketSender().sendModelInformation(724, 87, 350, 268, 180);
    }
    public static void imbueSuffering(Player player) {
        int cost = 24000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 18000000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 12000000;
        }
        player.getInventory().remove(19550, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(19710, 1);
        player.sendMessage("You have successfully imbued your Ring of Suffering.");
        ImbuedPower.complete(player);
    }
    public static void selectTreasonous(Player player) {
        resetBooleans();
        selectedTreasonous = true;
        player.getPacketSender().sendString(724, 90, "Treasonous Ring (i)");
        player.getPacketSender().sendString(724, 93, "17m Coins");
        player.getPacketSender().sendModel(724, 87, 21853);
        player.getPacketSender().sendModelInformation(724, 87, 350, 342, 252);
    }
    public static void imbueTreasonous(Player player) {
        int cost = 17000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 12750000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 8500000;
        }
        player.getInventory().remove(12605, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(12692, 1);
        player.sendMessage("You have successfully imbued your Treasonous Ring.");
        ImbuedPower.complete(player);
    }
    public static void selectGods(Player player) {
        resetBooleans();
        selectedRingOfGods = true;
        player.getPacketSender().sendString(724, 90, "Ring of Gods (i)");
        player.getPacketSender().sendString(724, 93, "22m Coins");
        player.getPacketSender().sendModel(724, 87, 29222);
        player.getPacketSender().sendModelInformation(724, 87, 450, 393, 1589);
    }
    public static void imbueGods(Player player) {
        int cost = 22000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 16500000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 11000000;
        }
        player.getInventory().remove(12601, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(13202, 1);
        player.sendMessage("You have successfully imbued your Ring of Gods.");
        ImbuedPower.complete(player);
    }
    public static void selectTyrannical(Player player) {
        resetBooleans();
        selectedTyrannical = true;
        player.getPacketSender().sendString(724, 90, "Tyrannical Ring (i)");
        player.getPacketSender().sendString(724, 93, "22m Coins");
        player.getPacketSender().sendModel(724, 87, 23907);
        player.getPacketSender().sendModelInformation(724, 87, 350, 285, 1163);
    }
    public static void imbueTyrannical(Player player) {
        int cost = 22000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 16500000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 11000000;
        }
        player.getInventory().remove(12603, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(12691, 1);
        player.sendMessage("You have successfully imbued your Tyrannical Ring.");
        ImbuedPower.complete(player);
    }
    public static void selectBlackMask(Player player) {
        resetBooleans();
        selectedBlackMask = true;
        player.getPacketSender().sendString(724, 90, "Black Mask (i)");
        player.getPacketSender().sendString(724, 93, "12m Coins");
        player.getPacketSender().sendModel(724, 87, 15615);
        player.getPacketSender().sendModelInformation(724, 87, 400, 387, 1892);
    }
    public static void imbueBlackMask(Player player) {
        int cost = 12000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 9000000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 6000000;
        }
        player.getInventory().remove(8901, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(11774, 1);
        player.sendMessage("You have successfully imbued your Black Mask.");
        ImbuedPower.complete(player);
    }
    public static void selectSlayerHelmet(Player player) {
        resetBooleans();
        selectedSlayerHelmet = true;
        player.getPacketSender().sendString(724, 90, "Slayer Helmet (i)");
        player.getPacketSender().sendString(724, 93, "30m Coins");
        player.getPacketSender().sendModel(724, 87, 36005);
        player.getPacketSender().sendModelInformation(724, 87, 500, 30, 1791);
    }
    public static void imbueSlayerHelmet(Player player) {
        int cost = 30000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 22500000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 15000000;
        }
        player.getInventory().remove(11864, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(11865, 1);
        player.sendMessage("You have successfully imbued your Slayer Helmet.");
        ImbuedPower.complete(player);
    }
    public static void selectRedSlayerHelmet(Player player) {
        resetBooleans();
        selectedRedSlayerHelmet = true;
        player.getPacketSender().sendString(724, 90, "Red Slayer Helmet (i)");
        player.getPacketSender().sendString(724, 93, "35m Coins");
        player.getPacketSender().sendModel(724, 87, 31547);
        player.getPacketSender().sendModelInformation(724, 87, 500, 30, 1791);
    }
    public static void imbueRedSlayerHelmet(Player player) {
        int cost = 35000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 26250000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 17500000;
        }
        player.getInventory().remove(19647, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(19649, 1);
        player.sendMessage("You have successfully imbued your Red Slayer Helmet.");
        ImbuedPower.complete(player);
    }
    public static void selectGreenSlayerHelmet(Player player) {
        resetBooleans();
        selectedGreenSlayerHelmet = true;
        player.getPacketSender().sendString(724, 90, "Green Slayer Helmet (i)");
        player.getPacketSender().sendString(724, 93, "35m Coins");
        player.getPacketSender().sendModel(724, 87, 31546);
        player.getPacketSender().sendModelInformation(724, 87, 500, 30, 1791);
    }
    public static void imbueGreenSlayerHelmet(Player player) {
        int cost = 35000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 26250000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 17500000;
        }
        player.getInventory().remove(19643, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(19645, 1);
        player.sendMessage("You have successfully imbued your Green Slayer Helmet.");
        ImbuedPower.complete(player);
    }
    public static void selectPurpleSlayerHelmet(Player player) {
        resetBooleans();
        selectedPurpleSlayerHelmet = true;
        player.getPacketSender().sendString(724, 90, "Purp. Slayer Helmet (i)");
        player.getPacketSender().sendString(724, 93, "35m Coins");
        player.getPacketSender().sendModel(724, 87, 32991);
        player.getPacketSender().sendModelInformation(724, 87, 500, 30, 1791);
    }
    public static void imbuePurpleSlayerHelmet(Player player) {
        int cost = 35000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 26250000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 17500000;
        }
        player.getInventory().remove(21264, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(21266, 1);
        player.sendMessage("You have successfully imbued your Purple Slayer Helmet.");
        ImbuedPower.complete(player);
    }
    public static void selectBlackSlayerHelmet(Player player) {
        resetBooleans();
        selectedBlackSlayerHelmet = true;
        player.getPacketSender().sendString(724, 90, "Black Slayer Helmet (i)");
        player.getPacketSender().sendString(724, 93, "35m Coins");
        player.getPacketSender().sendModel(724, 87, 31545);
        player.getPacketSender().sendModelInformation(724, 87, 500, 30, 1791);
    }
    public static void imbueBlackSlayerHelmet(Player player) {
        int cost = 35000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 26250000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 17500000;
        }
        player.getInventory().remove(19639, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(19641, 1);
        player.sendMessage("You have successfully imbued your Black Slayer Helmet.");
        ImbuedPower.complete(player);
    }
    public static void selectTurquoiseSlayerHelmet(Player player) {
        resetBooleans();
        selectedTurquoiseSlayerHelmet =true;
        player.getPacketSender().sendString(724, 90, "Turq. Slayer Helmet (i)");
        player.getPacketSender().sendString(724, 93, "35m Coins");
        player.getPacketSender().sendModel(724, 87, 34619);
        player.getPacketSender().sendModelInformation(724, 87, 500, 30, 1791);
    }
    public static void imbueTurquoiseSlayerHelmet(Player player) {
        int cost = 35000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 26250000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 17500000;
        }
        player.getInventory().remove(21888, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(21890, 1);
        player.sendMessage("You have successfully imbued your Turquoise Black Slayer Helmet.");
        ImbuedPower.complete(player);
    }
    public static void selectHydraSlayerHelmet(Player player) {
        resetBooleans();
        selectedCrystalBow = true;
        player.getPacketSender().sendString(724, 77, "Hydra Slayer Helmet");
        player.getPacketSender().sendString(724, 90, "Hydra Slayer Helmet (i)");
        player.getPacketSender().sendString(724, 93, "35m Coins");
        player.getPacketSender().sendModel(724, 87, 42703);
        player.getPacketSender().sendModelInformation(724, 87, 500, 30, 1791);
    }
    public static void imbueHydraSlayerHelmet(Player player) {
        int cost = 35000000;
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
            cost = 26250000;
        }
        if (player.storeAmountSpent > 1000) {
            cost = 17500000;
        }
        player.getInventory().remove(23073, 1);
        player.getInventory().remove(995, cost);
        player.animate(4462);
        player.graphics(759);
        player.getInventory().add(23075, 1);
        player.sendMessage("You have successfully imbued your Hydra Slayer Helmet.");
        ImbuedPower.complete(player);
    }

    public static void imbueTwistedDialogue(Player p) {
        p.dialogue(
                new ItemDialogue().one(24370, "Do you wish to imbue your Twisted Slayer Helmet? This will cost 35m coins."),
                new OptionsDialogue("Imbue your helmet?",
                        new Option("Yes, imbue it.", () -> imbueNewSlayHelms(p, 24370, 24444)),
                        new Option("Nevermind")
                )
        );
    }
    public static void imbueVampyricDialogue(Player p) {
        p.dialogue(
                new ItemDialogue().one(25904, "Do you wish to imbue your Vampyric Slayer Helmet? This will cost 35m coins."),
                new OptionsDialogue("Imbue your helmet?",
                        new Option("Yes, imbue it.", () -> imbueNewSlayHelms(p, 25904,  25906)),
                        new Option("Nevermind")
                )
        );
    }
    public static void imbueJadDialogue(Player p) {
        p.dialogue(
                new ItemDialogue().one(25898, "Do you wish to imbue your Tztok Slayer Helmet? This will cost 35m coins."),
                new OptionsDialogue("Imbue your helmet?",
                        new Option("Yes, imbue it.", () -> imbueNewSlayHelms(p, 25898,  25900)),
                        new Option("Nevermind")
                )
        );
    }
    public static void imbueZukDialogue(Player p) {
        p.dialogue(
                new ItemDialogue().one(25910, "Do you wish to imbue your Tzkal Slayer Helmet? This will cost 35m coins."),
                new OptionsDialogue("Imbue your helmet?",
                        new Option("Yes, imbue it.", () -> imbueNewSlayHelms(p, 25910,  25912)),
                        new Option("Nevermind")
                )
        );
    }
    public static void imbueNewSlayHelms(Player p, int oldId, int newId) {
        int amount = 35000000;
        if (p.storeAmountSpent >= 500 && p.storeAmountSpent < 1000) {
            amount = 26250000;
        }
        if (p.storeAmountSpent > 1000) {
            amount = 17500000;
        }
        boolean hasItems = p.getInventory().contains(oldId, 1) && p.getInventory().contains(995, amount);
        if (!hasItems) {
            p.sendMessage("You do not have the correct items to imbue your helmet.");
        } else {
            p.getInventory().remove(oldId, 1);
            p.getInventory().remove(995, amount);
            p.getInventory().add(newId, 1);
            p.animate(4462);
            p.graphics(759);
            p.dialogue(
                    new ItemDialogue().one(newId, "Congratulations! You have imbued your slayer helmet.")
            );
        }
    }
}