package io.ruin.model.item.actions.impl.boxes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import lombok.Getter;

public class EdgePvpBoxes {

    private static int small = 25646;
    private static int medium = 25648;
    private static int large = 25650;

    static {
        ItemAction.registerInventory(small, 1, EdgePvpBoxes::openSmall);
        ItemAction.registerInventory(medium, 1, EdgePvpBoxes::openMedium);
        ItemAction.registerInventory(large, 1, EdgePvpBoxes::openLarge);
    }

    public static void openSmall(Player p, Item item) {
        p.lock();
        item.remove(1);
        Item reward;
        reward = getSmallBox().rollItem();
        p.getInventory().add(reward);
        p.sendMessage("You receive a <col=0040ff>"+reward.getDef().name+"</col> from the small pvp box!");
        p.unlock();
    }
    public static void openMedium(Player p, Item item) {
        p.lock();
        item.remove(1);
        Item reward;
        reward = getMediumBox().rollItem();
        p.getInventory().add(reward);
        p.sendMessage("You receive a <col=0040ff>"+reward.getDef().name+"</col> from the medium pvp box!");
        p.unlock();
    }
    public static void openLarge(Player p, Item item) {
        p.lock();
        item.remove(1);
        Item reward;
        reward = getLargeBox().rollItem();
        p.getInventory().add(reward);
        p.sendMessage("You receive a <col=0040ff>"+reward.getDef().name+"</col> from the large pvp box!");
        p.unlock();
    }

    @Getter
    private static final LootTable smallBox = new LootTable().addTable(1,
            new LootItem(11840, 1, 50), //d boots
            new LootItem(4087, 1, 50), //dragon legs
            new LootItem(6920, 1, 45), //Infinity boots
            new LootItem(6914, 1, 50), //Master wand
            new LootItem(6889, 1, 50), //Mage's book
            new LootItem(6737, 1, 45), //Berserker ring
            new LootItem(6733, 1, 45), //Archer ring
            new LootItem(6731, 1, 45), //Seers ring
            new LootItem(12002, 1, 35), //Occult
            new LootItem(11804, 1, 30), //Bgs
            new LootItem(11924, 1, 25), //Malediction ward
            new LootItem(11926, 1, 25), //Odium ward
            new LootItem(11791, 1, 20), //SOTD
            new LootItem(19553, 1, 20), //torture
            new LootItem(19547, 1, 20), //anguish
            new LootItem(19544, 1, 20), //tormented bracelet
            new LootItem(19550, 1, 20), //suffering
            new LootItem(11283, 1, 15), //DFS
            new LootItem(19481, 1, 10), //heavy ballista
            new LootItem(12004, 1, 15), //kraken tent
            new LootItem(22322, 1, 1), //avernic
            new LootItem(24780, 1, 5), //blood fury
            new LootItem(11824, 1, 5), //zammy spear
            new LootItem(12904, 1, 5), //TSOTD
            new LootItem(21902, 1, 20), //dragon crossbow
            new LootItem(21024, 1, 1), //ancestral skirt
            new LootItem(21021, 1, 1), //ancestral body
            new LootItem(21018, 1, 1), //ancestral hat
            new LootItem(21003, 1, 1), //Elder maul
            new LootItem(13576, 1, 1), //dwh
            new LootItem(11828, 1, 5), //arma chest
            new LootItem(11830, 1, 5), //arma legs
            new LootItem(11832, 1, 5), //bcp
            new LootItem(11834, 1, 5) //tassets
    );

    @Getter
    private static final LootTable mediumBox = new LootTable().addTable(1,
            new LootItem(11840, 1, 20), //d boots
            new LootItem(4087, 1, 20), //dragon legs
            new LootItem(6585, 1, 50), //fury
            new LootItem(6920, 1, 20), //Infinity boots
            new LootItem(6914, 1, 25), //Master wand
            new LootItem(6889, 1, 25), //Mage's book
            new LootItem(6737, 1, 25), //Berserker ring
            new LootItem(6733, 1, 25), //Archer ring
            new LootItem(6731, 1, 25), //Seers ring
            new LootItem(12002, 1, 15), //Occult
            new LootItem(11804, 1, 50), //Bgs
            new LootItem(11924, 1, 5), //Malediction ward
            new LootItem(11824, 1, 20), //zammy spear
            new LootItem(11926, 1, 5), //Odium ward
            new LootItem(11791, 1, 15), //SOTD
            new LootItem(12904, 1, 20), //TSOTD
            new LootItem(11283, 1, 50), //DFS
            new LootItem(19481, 1, 35), //heavy ballista
            new LootItem(12004, 1, 30), //kraken tent
            new LootItem(21024, 1, 10), //ancestral skirt
            new LootItem(21021, 1, 10), //ancestral body
            new LootItem(21018, 1, 10), //ancestral hat
            new LootItem(21003, 1, 10), //Elder maul
            new LootItem(22322, 1, 10), //avernic
            new LootItem(19553, 1, 30), //torture
            new LootItem(19547, 1, 30), //anguish
            new LootItem(19544, 1, 30), //tormented bracelet
            new LootItem(19550, 1, 30), //suffering
            new LootItem(11828, 1, 30), //arma chest
            new LootItem(11830, 1, 30), //arma legs
            new LootItem(11832, 1, 30), //bcp
            new LootItem(13239, 1, 15), //prims
            new LootItem(13235, 1, 15), //eternals
            new LootItem(13237, 1, 15), //pegs
            new LootItem(13652, 1, 1), //claws
            new LootItem(13576, 1, 10), //dwh
            new LootItem(11785, 1, 1), //acb
            new LootItem(11834, 1, 30) //tassets
    );

    @Getter
    private static final LootTable largeBox = new LootTable().addTable(1,
            new LootItem(11804, 1, 5), //Bgs
            new LootItem(12817, 1, 1), //Ely
            new LootItem(21295, 1, 1), //infernal
            new LootItem(12825, 1, 1), //arcane
            new LootItem(11926, 1, 5), //Odium ward
            new LootItem(11791, 1, 10), //SOTD
            new LootItem(11283, 1, 5), //DFS
            new LootItem(21024, 1, 30), //ancestral skirt
            new LootItem(11824, 1, 35), //zammy spear
            new LootItem(19553, 1, 45), //torture
            new LootItem(19547, 1, 45), //anguish
            new LootItem(19544, 1, 45), //tormented bracelet
            new LootItem(19550, 1, 20), //suffering
            new LootItem(13239, 1, 35), //prims
            new LootItem(13235, 1, 35), //eternals
            new LootItem(13237, 1, 35), //pegs
            new LootItem(21021, 1, 30), //ancestral body
            new LootItem(21018, 1, 30), //ancestral hat
            new LootItem(11828, 1, 50), //arma chest
            new LootItem(11830, 1, 50), //arma legs
            new LootItem(11832, 1, 50), //bcp
            new LootItem(13576, 1, 25), //dwh
            new LootItem(26233, 1, 15), //ancient gs
            new LootItem(11802, 1, 15), //ags
            new LootItem(13652, 1, 20), //claws
            new LootItem(11785, 1, 20), //acb
            new LootItem(11834, 1, 50) //tassets
    );

    public static void swapSmallsForMeds(Player p) {
        if (!p.getInventory().contains(25646, 3)) {
            p.sendMessage("You must have (3) small pvp caskets to do this!");
        } else {
            p.getInventory().remove(25646, 3);
            p.getInventory().addOrDrop(25648, 1);
            p.sendMessage("You have successfully swapped (3) small caskets for (1) medium casket");
        }
    }
    public static void swapSmallsForLarge(Player p) {
        if (!p.getInventory().contains(25646, 10)) {
            p.sendMessage("You must have (10) small pvp caskets to do this!");
        } else {
            p.getInventory().remove(25646, 10);
            p.getInventory().addOrDrop(25650, 1);
            p.sendMessage("You have successfully swapped (10) small caskets for (1) large casket");
        }
    }
    public static void swapMedsForLarge(Player p) {
        if (!p.getInventory().contains(25648, 5)) {
            p.sendMessage("You must have (5) medium pvp caskets to do this!");
        } else {
            p.getInventory().remove(25648, 5);
            p.getInventory().addOrDrop(25650, 1);
            p.sendMessage("You have successfully swapped (5) medium caskets for (1) large casket");
        }
    }
}