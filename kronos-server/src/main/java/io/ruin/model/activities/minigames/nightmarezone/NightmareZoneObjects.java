package io.ruin.model.activities.minigames.nightmarezone;

import io.ruin.Server;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.map.object.actions.ObjectAction;

import static io.ruin.Server.STARTUP_THREAD;

public class NightmareZoneObjects {

    static {
        /**
         * Rewards chest
         */
        
            ObjectAction.register(26273, "search", (player, obj) -> {
                player.openInterface(InterfaceType.MAIN, Interface.NIGHTMARE_ZONE_REWARDS);
            });
    }
}
