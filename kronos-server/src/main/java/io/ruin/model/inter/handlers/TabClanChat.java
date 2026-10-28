package io.ruin.model.inter.handlers;

import io.ruin.model.entity.player.clanchat.ClanChat;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;

public class TabClanChat {

    static {
        InterfaceHandler.register(Interface.CLAN_CHAT, h -> {
            h.actions[24] = (SimpleAction) p -> {
                p.openInterface(InterfaceType.MAIN, Interface.CLAN_CHAT_SETTINGS);
                ClanChat.sendSettings(p);
            };
        });

        InterfaceHandler.register(Interface.CLAN_CHAT_SETTINGS, h -> {

            h.actions[10] = (OptionAction) (player, option) -> {
                if (option == 1) {
                    player.nameInput("Enter chat prefix:", name -> {
                        name = name.replaceAll("[^a-zA-Z0-9\\s]", "");
                        name = name.substring(0, Math.min(name.length(), 12));
                        if (name.isEmpty()) {
                            player.retryStringInput("Invalid chat prefix, try again:");
                            return;
                        }
                        ClanChat.setName(player, name);
                    });
                    return;
                }
            };
            h.actions[13] = (OptionAction) (player, option) -> ClanChat.changeEnterSettings(player, option - 2);
             h.actions[16] = (OptionAction) (player, option) -> ClanChat.changeTalkSettings(player, option - 2);
             h.actions[19] = (OptionAction) (player, option) -> ClanChat.changeKickSettings(player, option - 2);

        });
    }
}