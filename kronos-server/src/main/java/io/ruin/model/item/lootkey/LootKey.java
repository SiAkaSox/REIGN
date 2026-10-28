package io.ruin.model.item.lootkey;

import io.ruin.model.combat.Killer;
import io.ruin.model.entity.player.GameMode;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.IKOD;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static io.ruin.model.item.lootkey.LootKeyManager.LOOT_KEY_ID;

public class LootKey extends ItemContainer {


    public int addCheck(Item item, Player pKiller, Player p) {
        if (item == null || item.getDef() == null) {
            return 0;
        }
        if (pKiller != null) {
            if (pKiller.isEasyModeWithEasyXP() && item.getDef().free) {
                new GroundItem(item).owner(pKiller).position(p.getPosition()).spawn();
                return 0;
            }
            if (item.getId() == LOOT_KEY_ID) {
                item.remove();
                return 0;
            }
            if (item.getDef().consumable && item.getDef().value > 2000) {
                new GroundItem(item).owner(pKiller).position(p.getPosition()).spawn();
                return 0;
            }
        }
        return add(item.getId(), item.getAmount(), item.copyOfAttributes());
    }
}
