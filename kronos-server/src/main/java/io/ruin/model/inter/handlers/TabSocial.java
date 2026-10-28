package io.ruin.model.inter.handlers;

import io.ruin.Server;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;

import static io.ruin.Server.STARTUP_THREAD;

public class TabSocial {

    static {

            InterfaceHandler.register(Interface.FRIENDS_LIST, h -> h.actions[1] = (SimpleAction) p -> {
                p.openInterface(InterfaceType.SOCIAL_TAB, Interface.IGNORE_LIST);
                Config.FRIENDS_AND_IGNORE_TOGGLE.set(p, 1);
            });

            InterfaceHandler.register(Interface.IGNORE_LIST, h -> h.actions[1] = (SimpleAction) p -> {
                p.openInterface(InterfaceType.SOCIAL_TAB, Interface.FRIENDS_LIST);
                Config.FRIENDS_AND_IGNORE_TOGGLE.set(p, 0);
            });
    }

}