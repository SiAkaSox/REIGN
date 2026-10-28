package io.ruin.model.activities.godwars;

import io.ruin.model.content.accountperks.PerkManager;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

import static io.ruin.model.content.accountperks.Perk.ANCIENT_ONES_BLESSING;

public enum GodwarsAltars {

    BANDOS(26366, new Position(2862, 5354, 2)),
    ZAMORAK(26363, new Position(2925, 5333, 2)),
    SARADOMIN(26364, new Position(2909, 5265, 0)),
    ARMADYL(26365, new Position(2839, 5294, 2));

    public final int altarId;
    public final Position teleportPosition;

    GodwarsAltars(int altarId, Position altarPosition) {
        this.altarId = altarId;
        this.teleportPosition = altarPosition;
    }

    static {
        for (GodwarsAltars altar : values()) {
            ObjectAction.register(altar.altarId, "pray", (player, obj) -> {
                if (player.getCombat().isDefending(5) | player.getCombat().isAttacking(5)) {
                    player.sendFilteredMessage("You cannot use this altar while under combat.");
                    return;
                }

                if (player.godwarsAltarCooldown.isDelayed()) {
                    player.sendFilteredMessage("You cannot use this altar yet!");
                    return;
                }

                player.animate(645);
                int altarDelay = 1000;
                player.sendFilteredMessage("You pray to the gods...");
                if (PerkManager.isActive(player, ANCIENT_ONES_BLESSING))
                    altarDelay /= 2;

                player.godwarsAltarCooldown.delay(altarDelay);
                player.getStats().get(StatType.Prayer).restore();
            });
            ObjectAction.register(altar.altarId, "teleport", (player, obj) -> {
                player.getMovement().teleport(altar.teleportPosition);
            });
        }
    }
}
