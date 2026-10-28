package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.achievements.listeners.easy.TheTradingPost;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.item.actions.impl.ItemSet;

public class HomePortal {

    static {

        for(int trader : new int[]{2149, 2148}) {
           /* SpawnListener.register(trader, npc -> npc.startEvent(event -> {
                while (true) {
                    npc.forceText("Come check out the trading post!");
                    event.delay(150);
                }
            }));*/
            NPCAction.register(trader, "sets", (player, npc) -> ItemSet.open(player));
            NPCAction.register(trader, "exchange", ((player, npc) -> {
                if (player.getGameMode().isAnyIron()) {
                    TheTradingPost.complete(player);
                    player.sendMessage("Your gamemode prevents you from accessing the trading post!");
                    return;
                }
                player.getTradePost().openViewOffers();
                TheTradingPost.complete(player);
            }));
        }
    }
}
