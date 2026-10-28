package io.ruin.model.item.actions.impl.boxes.mystery;

import io.ruin.cache.Icon;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.utility.Broadcast;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.model.item.actions.impl.boxes.mystery.VoteMysteryBox.VIEW_REWARDS_WIDGET;
import static io.ruin.model.item.actions.impl.boxes.mystery.VoteMysteryBox.updateRewards;

public class SuperMysteryBox extends ItemContainer {

    private static final int SUPER_MYSTERY_BOX = 290;

    private static final int EASTER_EGG = 21227;

    private static final LootTable ECO_MYSTERY_BOX_TABLE = new LootTable().addTable(1,

            new LootItem(12006, 1, 80), //tent
            new LootItem(21902, 1, 70), //dcbow
            new LootItem(12929, 1, 65), //serp
            new LootItem(11283, 1, 60), //dfs
            new LootItem(11804, 1, 55), // BGS
            new LootItem(11806, 1, 55), // SGS
            new LootItem(11808, 1, 55), // ZGS
            new LootItem(12002, 1, 50), // Occult
            new LootItem(19547, 1, 50), // Anguish
            new LootItem(19553, 1, 50), // Torture
            new LootItem(19550, 1, 50), // Ring of Suffering
            new LootItem(19544, 1, 50), // Tormented Bracelet
            new LootItem(13239, 1, 45), // Prims
            new LootItem(13237, 1, 45), // Pegs
            new LootItem(13235, 1, 45), // Eternals
            new LootItem(22636, 150, 40), // Morrigans Jav
            new LootItem(11828, 1, 45), // Arma Chest
            new LootItem(11785, 1, 35), // Arma Cbow
            new LootItem(11830, 1, 45), // Arma Skirt
            new LootItem(11826, 1, 45), // Arma helm
            new LootItem(11832, 1, 45), // BCP
            new LootItem(11834, 1, 45), // Tassy
            new LootItem(22634, 150, 40), // Morrigans Throw axe
            new LootItem(24780, 1, 40), // Staff of Light
            new LootItem(13271, 1, 40), // Abby Dag p++
            new LootItem(22975, 1, 40), // Brimstone Ring
            new LootItem(24271, 1, 35), // Neit Faceguard
            new LootItem(24780, 1, 30), // Blood Fury
            new LootItem(13576, 1, 30), // DWH
            new LootItem(12904, 1, 30), // Toxic Staff of the Dead
            new LootItem(21295, 1, 5).broadcast(Broadcast.WORLD), // Infernal cape
            new LootItem(22552, 1, 30), // Tham Sceptre (u)
            new LootItem(11802, 1, 30).broadcast(Broadcast.WORLD), // AGS
            new LootItem(22638, 1, 25), // Morrigans Coif
            new LootItem(22641, 1, 25), // Morrigans Body
            new LootItem(22644, 1, 25), // Morrigans Chaps
            new LootItem(22625, 1, 25), // Stat Helm
            new LootItem(22628, 1, 25), // Stat Plate
            new LootItem(22631, 1, 25), // Stat legs
            new LootItem(22616, 1, 25), // Vesta Body
            new LootItem(22619, 1, 25), // Vesta Skirt
            new LootItem(22622, 1, 20), // Stat Warhammer
            new LootItem(22613, 1, 20), // Vesta Long
            new LootItem(22323, 1, 9).broadcast(Broadcast.WORLD) // Sang Staff
    );

    public static void open(Player player, Item item) {
        InterfaceHandler.register(Interface.SUPER_MYSTERY_BOX, h -> {
            h.actions[7] = (SimpleAction) SuperMysteryBox::spin;
            h.actions[19] = (SimpleAction) SuperMysteryBox::claimReward;
            h.actions[21] = (SimpleAction) SuperMysteryBox::discardReward;
        });

        if(player.isVisibleInterface(Interface.SUPER_MYSTERY_BOX)) {
            player.sendMessage("You need to claim or discard your reward before doing this!");
            return;
        }

        player.easterEgg = item.getId() == EASTER_EGG;
        if(player.claimedBox) {
            player.getBox().clear();
            generateReward(player);
            player.getBox().sendUpdates();
            player.openInterface(InterfaceType.MAIN, 702);
            player.getPacketSender().sendClientScript(10034, "ssii", "Super Mystery Box", "Click the \"spin\" button when you're ready to test your luck.<br><br>May the odds be ever in your favor!", 15, 0);
        } else {
            player.getBox().sendUpdates();
            player.openInterface(InterfaceType.MAIN, 702);
            player.getPacketSender().sendClientScript(10034, "ssii", "Super Mystery Box", "Click the \"spin\" button when you're ready to test your luck!<br><br>May the odds be ever in your favor!", 15, 1);
        }
    }

    private static void generateReward(Player player) {
        LootTable table = ECO_MYSTERY_BOX_TABLE;
        for(int i = 0; i < 24; i ++)
            player.getBox().add(table.rollItem());
        Item reward = player.getBox().get(15);
        if(reward == null)
            generateReward(player);
    }

    private static void spin(Player player) {
        player.lock(LockType.FULL);
        if(player.easterEgg) {
            Item easterEgg = player.getInventory().findItem(EASTER_EGG);
            if(easterEgg == null) {
                player.closeInterface(InterfaceType.MAIN);
                return;
            }
            player.claimedBox = false;
            easterEgg.remove();
        } else {
            Item superMysteryBox = player.getInventory().findItem(SUPER_MYSTERY_BOX);
            player.getPacketSender().setHidden(702, 30, true);
            if(superMysteryBox == null) {
                player.closeInterface(InterfaceType.MAIN);
                return;
            }
            player.claimedBox = false;
            superMysteryBox.remove();
        }
    }

    private static void claimReward(Player player) {
        player.unlock();
        Item reward = player.getBox().get(15);
        player.getInventory().add(reward);
        player.claimedBox = true;
        player.sendMessage("You get " + reward.getDef().descriptiveName + " from the " + (player.easterEgg ? "Easter egg." : "Super Mystery Box."));

        if (ECO_MYSTERY_BOX_TABLE.getWeight(reward) < 5 && !player.easterEgg) {
            Broadcast.WORLD.sendNews("</shad>" + player.name() + " just received <col=8B0000>" + reward.getDef().descriptiveName + "</col> from a Super Mystery Box!");
        }
        player.getBox().clear();
        if(player.isVisibleInterface(Interface.SUPER_MYSTERY_BOX))
            player.closeInterface(InterfaceType.MAIN);
    }

    private static void discardReward(Player player) {
        player.closeInterface(InterfaceType.MAIN);
        player.claimedBox = true;
        player.sendMessage("You discard your Super Mystery Box reward.");
        player.getBox().clear();
        player.unlock();
    }

    public static void openRewards(Player player) {
        player.openInterface(InterfaceType.MAIN, VIEW_REWARDS_WIDGET);
        player.closeInterface(InterfaceType.INVENTORY);
        updateRewards(player, ECO_MYSTERY_BOX_TABLE);
    }

    static {
        ItemAction.registerInventory(SUPER_MYSTERY_BOX, "open", SuperMysteryBox::open);
    }
}
