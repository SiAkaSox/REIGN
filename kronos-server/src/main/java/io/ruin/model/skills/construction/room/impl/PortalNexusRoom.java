package io.ruin.model.skills.construction.room.impl;

import io.ruin.model.inter.teleports.TeleportInterface;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.room.Room;

import static io.ruin.model.skills.construction.Hotspot.PORTALS_NEXUS;

public class PortalNexusRoom extends Room {

    static {
        for (Buildable nexus_portal: PORTALS_NEXUS.getBuildables()) {
            int unlit = nexus_portal.getBuiltObjects()[0];
            ObjectAction.register(unlit, "teleport menu", (player, obj) -> {
                TeleportInterface.open(player);
            });
        }
    }

}
