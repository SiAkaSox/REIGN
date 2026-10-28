package io.ruin.model.content.accountperks;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import io.ruin.model.achievements.listeners.easy.IntroToPerks;
import io.ruin.model.achievements.listeners.hard.Ethereal;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Misc;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PerkManager {

    private Player player;

    private static final int WIDGET_ID = 996;

    private PerkType pageIndex = PerkType.COMBAT;

    //97 Unlock - PERK NAME
    //90 - Available Points: 9,500

    static {

            InterfaceHandler.register(WIDGET_ID, widget -> {
                widget.actions[110] = (SimpleAction) player -> player.getPerkManager().sendPage(PerkType.COMBAT);
                widget.actions[111] = (SimpleAction) player -> player.getPerkManager().sendPage(PerkType.SKILLING);
                widget.actions[112] = (SimpleAction) player -> player.getPerkManager().sendPage(PerkType.MISC);
                widget.actions[251] = (SimpleAction) (player) -> openSacrificeList(player);
                widget.actions[96] = (SimpleAction) player -> player.getPerkManager().handlePurchase();
                for (int child = 257; child <= 417; child += 10) {
                    widget.actions[child] = (DefaultAction) (player, child1, option, slot, itemId) -> {
                        player.getPerkManager().selectPerk(child1);
                    };
                }
            });
            InterfaceHandler.register(998, i -> {
                i.actions[449] = (SimpleAction) PerkManager::open;
            });
            ObjectAction.register(33321, 1, (player, obj) -> {
                open(player);
            });
            ObjectAction.register(33321, 2, (player, obj) -> {
                openSacrificeList(player);
            });
    }

    private void handlePurchase() {
        Perk perks = player.get("lastPerkSelected");
        if (perks == null)
            return;
        if (actives.contains(perks)) {
            player.dialogue(new MessageDialogue("You already own this Perk."));
            return;
        }
        player.dialogue(new ItemDialogue().one(perks.itemId, perks.title + "<br>" + perks.description + "<br>Price: " + Misc.formatNumber(perks.requiredPoints)),
                new OptionsDialogue("Purchase " + perks.title + " for " + Misc.formatNumber(perks.requiredPoints) + " points?",
                        new Option("Yes", () -> {
                            if (player.perkPoints < perks.requiredPoints) {
                                player.sendMessage("You don't have enough points to purchase " + perks.title);
                                return;
                            }
                            player.perkPoints -= perks.requiredPoints;
                            actives.add(perks);
                            String message = "You have successfully purchased " + (perks.title == null ? "" : perks.title) + ".<br>Active: " + (perks.description == null ? "" : perks.description) + (perks.type == PerkType.COMBAT ? "<br>Please be aware. This perk will only work against monsters." : "") + "<br>Perks are automatically active upon purchasing.";
                            player.dialogue(new MessageDialogue(message));
                            player.sendMessage(message);
                            Ethereal.complete(player);
                        }),
                        new Option("No")));
    }

    private void selectPerk(int child) {
        Optional<Perk> selected = Arrays.stream(Perk.values()).filter(e -> e.type == pageIndex && e.child == child).findFirst();
        if (!selected.isPresent())
            return;
        final Perk perk = selected.get();
        toggleBuy(player, false);
        player.getPacketSender().sendString(WIDGET_ID, 97, getActives().contains(perk) ? "<col=4fff33>OWNED" : "<col=ff981f>Unlock - " + perk.title);
        player.set("lastPerkSelected", perk);
    }

    @Expose
    @Getter
    private List<Perk> actives = Lists.newArrayList();

    public static boolean isActive(Player player, Perk perk) {
        if (player.lastRegion != null && player.lastRegion.id == 11576)
            return false;
        return player.getPerkManager() != null && player.getPerkManager().getActives().contains(perk);
    }

    public static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, WIDGET_ID);
        player.getPacketSender().sendString(WIDGET_ID, 90, "Available Points: " + Misc.formatNumber(player.perkPoints));
        player.getPerkManager().sendPage(PerkType.COMBAT);
        IntroToPerks.complete(player);
    }

    public static void openSacrificeList(Player p) {
        p.openInterface(InterfaceType.MAIN, 998);
        SacrificeData.SacrificeList.sendEnumItems(p);
    }

    private static void toggleBuy(Player player, boolean off) {
        player.getPacketSender().setHidden(WIDGET_ID, 96, off);
        player.getPacketSender().setHidden(WIDGET_ID, 97, off);
    }

    private void sendPage(PerkType type) {
        pageIndex = type;
        toggleBuy(player, true);
        List<Perk> page = Arrays.stream(Perk.values()).filter(e -> e.type == type).collect(Collectors.toList());//262 for bought
        int titleId = 258;
        int description = 259;
        int itemId = 261;
        int unlocked = 262;
        for (int index = 0; index < page.size(); index++) {
            Perk perk = page.get(index);
            if (perk == null)
                continue;
            player.getPacketSender().sendString(WIDGET_ID, titleId, perk.title);
            player.getPacketSender().sendString(WIDGET_ID, description, perk.description + " " + ("<col=ff0000>(" + Misc.formatNumber(perk.requiredPoints)) + " points)");
            player.getPacketSender().sendItems(WIDGET_ID, itemId, 0, new Item(perk.itemId, -1));
            player.getPacketSender().setHidden(WIDGET_ID, unlocked, actives.contains(perk));
            titleId += 10;
            description += 10;
            itemId += 10;
            unlocked += 10;
        }
    }

    public void init(Player player) {
        this.player = player;
    }
}
