package io.ruin.utility;

public enum LogType {

    DROP_ITEM("dropped_items.txt"),
    SPAWN_ITEM("spawned_items.txt"),
    COMMAND("commands.txt"),
    PICK_UP("pickup.txt"),
    TRADE("trades.txt"),
    SHOP_BUY("shop_buy.txt"),
    SHOP_SELL("shop_sell.txt"),
    TRADING_POST_OFFER("trading_post_offer.txt"),
    TRADING_POST_ABORTED("trading_post_aborted.txt"),
    TRADING_POST_BOUGHT("trading_post_bought.txt"),
    TRADING_POST_SOLD("trading_post_sold.txt"),
    DUEL_DRAW("duel_draws.txt"),
    DUEL_WIN("duel_wins.txt"),
    DUEL_LOSS("duel_loss.txt"),

    CLAN_CHAT_MESSAGE("clan_chat_message.txt"),
    CLAN_CHAT_JOIN("clan_chat_join.txt"),
    CLAN_CHAT_LEAVE("clan_chat_leave.txt"),

    PUBLIC_CHAT("public_chat.txt"),
    
    PMS_SENT("pms_sent.txt"),
    PMS_RECIEVED("pms_received.txt"),

    WILD_KILLS("wilderness_kills.txt"),
    WILD_DEATHS("wilderness_deaths.txt"),
    
    
    ;

    public String directory;

    LogType(String directory) {
        this.directory = directory;
    }

}
