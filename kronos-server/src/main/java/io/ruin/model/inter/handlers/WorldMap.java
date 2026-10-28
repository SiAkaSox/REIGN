package io.ruin.model.inter.handlers;

import io.ruin.Server;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;

import static io.ruin.Server.STARTUP_THREAD;

public class WorldMap {

    static {
        InterfaceHandler.register(Interface.WORLD_MAP, h -> h.actions[37] = (SimpleAction) p -> p.closeInterface(InterfaceType.WORLD_MAP));
    }
}
