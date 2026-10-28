package io.ruin.model.item.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Position;
import io.ruin.model.skills.magic.spells.HomeTeleport;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

public class EscapeCrystal {

    static {
        ItemAction.registerInventory(22517, "Activate", (player, item) -> {
            player.getMovement().startTeleport(30, event -> {
                if (player.getCombat().tbTicks > 0) {
                    return;
                }
                player.lock(LockType.FULL_NULLIFY_DAMAGE);
                player.animate(4069, 16);
                player.privateSound(965, 1, 15);
                event.delay(2);
                item.remove(1);
                player.animate(4071);
                player.graphics(678);
                event.delay(2);
                player.getMovement().teleport(World.HOME);
                player.resetAnimation();
                player.unlock();
            });
        });
    }

    public static void toggle(Player p) {
        if (!p.escapeCrystalToggle) {
            p.dialogue(
                    new ItemDialogue().one(22517, "Toggling this feature on will automatically teleport you home if you go below 10% of your HP level."),
                    new OptionsDialogue("Toggle on?",
                            new Option("Yes, turn this feature on", () -> p.escapeCrystalToggle = true),
                            new Option("Nevermind")
                    )
            );
        } else {
            p.dialogue(
                    new OptionsDialogue("Toggle off?",
                            new Option("Yes, toggle off", () -> p.escapeCrystalToggle = false),
                            new Option("Nevermind")
                    )
            );
        }
    }

    public static void check(Player player) {
        if(player.getDuel().stage >= 4)
            return;
        if (player.getHp() <= player.getMaxHp() * 0.10 && !player.getCombat().isDead()) {
            Item ring = player.getEquipment().get(Equipment.SLOT_RING);
            if (ring == null || ring.getId() != 2570)
                return;
            if(ModernTeleport.teleport(player, World.HOME)) {
                ring.remove();
                player.sendFilteredMessage("Your ring of life crumbles to dust.");
            }
        }
    }

}