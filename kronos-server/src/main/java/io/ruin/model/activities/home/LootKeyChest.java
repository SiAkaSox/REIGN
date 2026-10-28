package io.ruin.model.activities.home;

import io.ruin.Server;
import io.ruin.utility.NumberUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.ArrayList;

import static io.ruin.Server.STARTUP_THREAD;
import static io.ruin.model.achievements.listeners.hard.HardAchieveInter.loadHardAchievements;
import static io.ruin.model.achievements.listeners.medium.MediumAchieveInter.loadMediumAchievements;

public class LootKeyChest extends ItemContainer {

    public static boolean keyoneempty = true;
    public static boolean selectedOne = false;
    public static boolean selectedTwo = false;
    public static boolean selectedThree = false;
    public static boolean selectedFour = false;
    public static boolean selectedFive = false;

    public static ArrayList<Item> keyoneitems = new ArrayList<>();

    static {
        ObjectAction.register(43486, 1, (p, obj) -> {
            sendDialogue(p);
            //addKeyItems(p);
        });
        InterfaceHandler.register(971, h -> {
            h.actions[25] = (SlotAction) (player, slot) -> {
                sendtoInventory(player);
            };
            h.actions[32] = (SlotAction) (player, slot) -> loadMediumAchievements(player);
            h.actions[9] = (SlotAction) (player, slot) -> loadHardAchievements(player);
        });
    }

    public static void sendtoInventory(Player p) {
        p.getInventory().remove(26651, 1);
        p.getInventory().addOrDrop(p.keyonehelmetslot, 1);
        p.getInventory().addOrDrop(p.keyonechestslot, 1);
        p.getInventory().addOrDrop(p.keyonelegsslot, 1);
        p.getInventory().addOrDrop(p.keyoneweaponslot, 1);
        p.getInventory().addOrDrop(p.keyoneshieldslot, 1);
        p.getInventory().addOrDrop(p.keyoneamuletslot, 1);
        p.getInventory().addOrDrop(p.keyonecapeslot, 1);
        p.getInventory().addOrDrop(p.keyoneammoslot, p.keyoneammoamount);
        p.getInventory().addOrDrop(p.keyoneglovesslot, 1);
        p.getInventory().addOrDrop(p.keyoneringslot, 1);
        p.getInventory().addOrDrop(p.keyonebootsslot, 1);

        p.getInventory().addOrDrop(p.keyoneinvone, p.keyoneinvoneamt);
        p.getInventory().addOrDrop(p.keyoneinvtwo, p.keyoneinvtwoamt);
        p.getInventory().addOrDrop(p.keyoneinvthree, p.keyoneinvthreeamt);
        p.getInventory().addOrDrop(p.keyoneinvfour, p.keyoneinvfouramt);
        p.getInventory().addOrDrop(p.keyoneinvfive, p.keyoneinvfiveamt);
        p.getInventory().addOrDrop(p.keyoneinvsix, p.keyoneinvsixamt);
        p.getInventory().addOrDrop(p.keyoneinvseven, p.keyoneinvsevenamt);
        p.getInventory().addOrDrop(p.keyoneinveight, p.keyoneinveightamt);
        p.getInventory().addOrDrop(p.keyoneinvnine, p.keyoneinvnineamt);
        p.getInventory().addOrDrop(p.keyoneinvten, p.keyoneinvtenamt);
        p.getInventory().addOrDrop(p.keyoneinveleven, p.keyoneinvelevenamt);
        p.getInventory().addOrDrop(p.keyoneinvtwelve, p.keyoneinvtwelveamt);
        p.getInventory().addOrDrop(p.keyoneinvthirteen, p.keyoneinvthirteenamt);
        p.getInventory().addOrDrop(p.keyoneinvfourteen, p.keyoneinvfourteenamt);
        p.getInventory().addOrDrop(p.keyoneinvfifteen, p.keyoneinvfifteenamt);
        p.getInventory().addOrDrop(p.keyoneinvsixteen, p.keyoneinvsixteenamt);
        p.getInventory().addOrDrop(p.keyoneinvseventeen, p.keyoneinvseventeenamt);
        p.getInventory().addOrDrop(p.keyoneinveighteen, p.keyoneinveighteenamt);
        p.getInventory().addOrDrop(p.keyoneinvnineteen, p.keyoneinvnineteenamt);
        p.getInventory().addOrDrop(p.keyoneinvtwenty, p.keyoneinvtwentyamt);
        p.getInventory().addOrDrop(p.keyoneinvtwentyone, p.keyoneinvtwentyoneamt);
        p.getInventory().addOrDrop(p.keyoneinvtwentytwo, p.keyoneinvtwentytwoamt);
        p.getInventory().addOrDrop(p.keyoneinvtwentythree, p.keyoneinvtwentythreeamt);
        p.getInventory().addOrDrop(p.keyoneinvtwentyfour, p.keyoneinvtwentyfouramt);
        p.getInventory().addOrDrop(p.keyoneinvtwentyfive, p.keyoneinvtwentyfiveamt);
        p.getInventory().addOrDrop(p.keyoneinvtwentysix, p.keyoneinvtwentysixamt);
        p.getInventory().addOrDrop(p.keyoneinvtwentyseven, p.keyoneinvtwentysevenamt);
    }

    public static void sendInterface(Player p) {
        p.openInterface(InterfaceType.MAIN, 971);
        p.getPacketSender().sendString(971, 6, "Value in chest: " + NumberUtils.formatNumber(sendKeyValue(p)));
        sendkeyoneinterfaceItems(p);
    }

    public static void sendDialogue(Player p) {
        if (p.getInventory().getAmount(26651) == 1) {
            p.dialogue(
                    new OptionsDialogue(
                            new Option("Key 1 - " + NumberUtils.formatNumber(p.keyonevalue) + "gp", () -> {
                                selectedOne = true;
                                sendInterface(p);
                            })
                    )
            );
        }
        if (p.getInventory().getAmount(26651) == 2) {
            p.dialogue(
                    new OptionsDialogue(
                            new Option("Key 1 - " + NumberUtils.formatNumber(p.keyonevalue) + "gp", () -> {
                                selectedOne = true;
                                sendInterface(p);
                            }),
                            new Option("Key 2 - " + NumberUtils.formatNumber(p.keytwovalue) + "gp", () -> {
                                selectedTwo = true;
                                sendInterface(p);
                            })
                    )
            );
        }
        if (p.getInventory().getAmount(26651) == 3) {
            p.dialogue(
                    new OptionsDialogue(
                            new Option("Key 1 - " + NumberUtils.formatNumber(p.keyonevalue) + "gp", () -> {
                                selectedOne = true;
                                sendInterface(p);
                            }),
                            new Option("Key 2 - " + NumberUtils.formatNumber(p.keytwovalue) + "gp", () -> {
                                selectedTwo = true;
                                sendInterface(p);
                            }),
                            new Option("Key 3 - " + NumberUtils.formatNumber(p.keythreevalue) + "gp", () -> {
                                selectedThree = true;
                                sendInterface(p);
                            })
                    )
            );
        }
        if (p.getInventory().getAmount(26651) == 4) {
            p.dialogue(
                    new OptionsDialogue(
                            new Option("Key 1 - " + NumberUtils.formatNumber(p.keyonevalue) + "gp", () -> {
                                selectedOne = true;
                                sendInterface(p);
                            }),
                            new Option("Key 2 - " + NumberUtils.formatNumber(p.keytwovalue) + "gp", () -> {
                                selectedTwo = true;
                                sendInterface(p);
                            }),
                            new Option("Key 3 - " + NumberUtils.formatNumber(p.keythreevalue) + "gp", () -> {
                                selectedThree = true;
                                sendInterface(p);
                            }),
                            new Option("Key 4 - " + NumberUtils.formatNumber(p.keyfourvalue) + "gp", () -> {
                                selectedFour = true;
                                sendInterface(p);
                            })
                    )
            );
        }
        if (p.getInventory().getAmount(26651) == 5) {
            p.dialogue(
                    new OptionsDialogue(
                            new Option("Key 1 - " + NumberUtils.formatNumber(p.keyonevalue) + "gp", () -> {
                                selectedOne = true;
                                sendInterface(p);
                            }),
                            new Option("Key 2 - " + NumberUtils.formatNumber(p.keytwovalue) + "gp", () -> {
                                selectedTwo = true;
                                sendInterface(p);
                            }),
                            new Option("Key 3 - " + NumberUtils.formatNumber(p.keythreevalue) + "gp", () -> {
                                selectedThree = true;
                                sendInterface(p);
                            }),
                            new Option("Key 4 - " + NumberUtils.formatNumber(p.keyfourvalue) + "gp", () -> {
                                selectedFour = true;
                                sendInterface(p);
                            }),
                            new Option("Key 5 - " + NumberUtils.formatNumber(p.keyfivevalue) + "gp", () -> {
                                selectedFive = true;
                                sendInterface(p);
                            })
                    )
            );
        }
    }

    public static int sendKeyValue(Player p) {
        if (selectedOne)
            return p.keyonevalue;
        if (selectedTwo)
            return p.keytwovalue;
        if (selectedThree)
            return p.keythreevalue;
        if (selectedFour)
            return p.keyfourvalue;
        if (selectedFive)
            return p.keyfivevalue;
        return 0;
    }

    public static void addKeyItems(Player p) {
        if (!p.getInventory().contains(26651)) {
            p.dialogue(
                    new MessageDialogue("You do not have any loot keys to open!")
            );
        }
        keyoneEmpty(p);
        if (!keyoneempty) {
            p.getInventory().remove(26651, 1);
            if (p.keyonehelmetslot != -1) {
                p.getInventory().addOrDrop(p.keyonehelmetslot, 1);
            }
            if (p.keyonecapeslot != -1) {
                p.getInventory().addOrDrop(p.keyonecapeslot, 1);
            }
            if (p.keyoneamuletslot != -1) {
                p.getInventory().addOrDrop(p.keyoneamuletslot, 1);
            }
            if (p.keyoneammoslot != -1) {
                p.getInventory().addOrDrop(p.keyoneammoslot, p.keyoneammoamount);
            }
            if (p.keyoneweaponslot != -1) {
                p.getInventory().addOrDrop(p.keyoneweaponslot, 1);
            }
            if (p.keyonechestslot != -1) {
                p.getInventory().addOrDrop(p.keyonechestslot, 1);
            }
            if (p.keyoneshieldslot != -1) {
                p.getInventory().addOrDrop(p.keyoneshieldslot, 1);
            }
            if (p.keyonelegsslot != -1) {
                p.getInventory().addOrDrop(p.keyonelegsslot, 1);
            }
            if (p.keyoneglovesslot != -1) {
                p.getInventory().addOrDrop(p.keyoneglovesslot, 1);
            }
            if (p.keyonebootsslot != -1) {
                p.getInventory().addOrDrop(p.keyonebootsslot, 1);
            }
            if (p.keyoneringslot != -1) {
                p.getInventory().addOrDrop(p.keyoneringslot, 1);
            }
            resetKeyOneEquipment(p);
        }
    }

    public static void keyoneEmpty(Player p) {
        if (p.keyonehelmetslot == -1 && p.keyonechestslot == -1 && p.keyonelegsslot == -1
                && p.keyoneweaponslot == -1 && p.keyoneshieldslot == -1 && p.keyoneamuletslot == -1
                && p.keyoneringslot == -1 && p.keyoneammoslot == -1 && p.keyoneglovesslot == -1
                && p.keyonebootsslot == -1 && p.keyonecapeslot == -1) {
            keyoneempty = true;
        } else {
            keyoneempty = false;
        }
    }

    public static void resetKeyOneEquipment(Player p) {
        p.keyonehelmetslot = -1;
        p.keyonecapeslot = -1;
        p.keyoneamuletslot = -1;
        p.keyoneammoslot = -1;
        p.keyoneammoamount = -1;
        p.keyoneweaponslot = -1;
        p.keyonechestslot = -1;
        p.keyoneshieldslot = -1;
        p.keyonelegsslot = -1;
        p.keyoneglovesslot = -1;
        p.keyonebootsslot = -1;
        p.keyoneringslot = -1;
    }

    public static void sendkeyoneinterfaceItems(Player p) {
        p.getPacketSender().sendItems(971, 38, 0, new Item(p.keyonehelmetslot, 1));
        p.getPacketSender().sendItems(971, 40, 0, new Item(p.keyonechestslot, 1));
        p.getPacketSender().sendItems(971, 42, 0, new Item(p.keyonelegsslot, 1));
        p.getPacketSender().sendItems(971, 44, 0, new Item(p.keyoneweaponslot, 1));
        p.getPacketSender().sendItems(971, 46, 0, new Item(p.keyoneshieldslot, 1));
        p.getPacketSender().sendItems(971, 48, 0, new Item(p.keyoneglovesslot, 1));
        p.getPacketSender().sendItems(971, 50, 0, new Item(p.keyonebootsslot, 1));
        p.getPacketSender().sendItems(971, 52, 0, new Item(p.keyoneamuletslot, 1));
        p.getPacketSender().sendItems(971, 54, 0, new Item(p.keyoneringslot, 1));
        p.getPacketSender().sendItems(971, 56, 0, new Item(p.keyonecapeslot, 1));
        p.getPacketSender().sendItems(971, 58, 0, new Item(p.keyoneammoslot, p.keyoneammoamount));
        p.getPacketSender().sendItems(971, 60, 0, new Item(p.keyoneinvone, p.keyoneinvoneamt));
        p.getPacketSender().sendItems(971, 62, 0, new Item(p.keyoneinvtwo, p.keyoneinvtwoamt));
        p.getPacketSender().sendItems(971, 64, 0, new Item(p.keyoneinvthree, p.keyoneinvthreeamt));
        p.getPacketSender().sendItems(971, 66, 0, new Item(p.keyoneinvfour, p.keyoneinvfouramt));
        p.getPacketSender().sendItems(971, 68, 0, new Item(p.keyoneinvfive, p.keyoneinvfiveamt));
        p.getPacketSender().sendItems(971, 70, 0, new Item(p.keyoneinvsix, p.keyoneinvsixamt));
        p.getPacketSender().sendItems(971, 72, 0, new Item(p.keyoneinvseven, p.keyoneinvsevenamt));
        p.getPacketSender().sendItems(971, 74, 0, new Item(p.keyoneinveight, p.keyoneinveightamt));
        p.getPacketSender().sendItems(971, 76, 0, new Item(p.keyoneinvnine, p.keyoneinvnineamt));
        p.getPacketSender().sendItems(971, 78, 0, new Item(p.keyoneinvten, p.keyoneinvtenamt));
        p.getPacketSender().sendItems(971, 80, 0, new Item(p.keyoneinveleven, p.keyoneinvelevenamt));
        p.getPacketSender().sendItems(971, 82, 0, new Item(p.keyoneinvtwelve, p.keyoneinvtwelveamt));
        p.getPacketSender().sendItems(971, 84, 0, new Item(p.keyoneinvthirteen, p.keyoneinvthirteenamt));
        p.getPacketSender().sendItems(971, 86, 0, new Item(p.keyoneinvfourteen, p.keyoneinvfourteenamt));
        p.getPacketSender().sendItems(971, 88, 0, new Item(p.keyoneinvfifteen, p.keyoneinvfifteenamt));
        p.getPacketSender().sendItems(971, 90, 0, new Item(p.keyoneinvsixteen, p.keyoneinvsixteenamt));
        p.getPacketSender().sendItems(971, 92, 0, new Item(p.keyoneinvseventeen, p.keyoneinvseventeenamt));
    }

    public static ArrayList<Item> getItems(Player player) {
        int count = player.getInventory().getCount() + player.getEquipment().getCount();
        ArrayList<Item> list = new ArrayList<>(count);
        if (count > 0) {
            for (Item item : player.getInventory().getItems()) {
                if (item != null)
                    list.add(item.copy());
            }
            for (Item item : player.getEquipment().getItems()) {
                if (item != null)
                    list.add(item.copy());
            }
            list.sort((i1, i2) -> Integer.compare(i2.getDef().protectValue, i1.getDef().protectValue));
        }
        return list;
    }

}