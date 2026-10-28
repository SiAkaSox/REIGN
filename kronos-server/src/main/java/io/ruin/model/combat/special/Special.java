package io.ruin.model.combat.special;

import io.ruin.Server;
import io.ruin.utility.PackageLoader;
import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

import static io.ruin.Server.STARTUP_THREAD;

public interface Special extends ItemDef.ItemDefPredicate {

    default boolean handle(Player player, Entity victim, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        /* override required */
        return false;
    }

    default boolean handleActivation(Player player) {
        /* override required */
        return false;
    }

    default int getDrainAmount() {
        return 0;
    }

    static void load() throws Exception {

            try {
                for (Class c : PackageLoader.load("io.ruin.model.combat.special", Special.class)) {
                    Special special = (Special) c.newInstance();
                    ItemDef.forEach(def -> {
                        if (special.test(def))
                            def.special = special;
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}