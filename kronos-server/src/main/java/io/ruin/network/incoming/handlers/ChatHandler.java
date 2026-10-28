package io.ruin.network.incoming.handlers;

import io.ruin.network.io.InBuffer;
import io.ruin.cache.filestore.utility.Huffman;
import io.ruin.utility.IPMute;
import io.ruin.model.entity.player.GameMode;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.clanchat.ClanChat;
import io.ruin.network.incoming.Incoming;
import io.ruin.services.Loggers;
import io.ruin.services.punishment.PunishmentManager;
import io.ruin.utility.IdHolder;
import io.ruin.utility.LogType;
import io.ruin.utility.TimeUtils;

import java.util.Arrays;
import java.util.List;

@IdHolder(ids = {32})
public class ChatHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        boolean shadow = false;
        if(PunishmentManager.isMuted(player)) {
            if(!player.shadowMute) {
                if (player.mutedBy == null) {
                    player.sendMessage("You are muted. You cannot talk.");
                    return;
                }
                player.sendMessage("You were muted by: <col=ff0000>"+player.mutedBy+"</col> Reason: <col=ff0000>"+player.mutedReason+"</col> Duration: <col=ff0000>"+(player.muteEnd == -1 ? "" : TimeUtils.fromMs(player.muteEnd - System.currentTimeMillis(), false)+"</col>"));
                player.sendMessage("If you feel as though this is unjustified, please submit a ticket in our discord server ::discord");
                return;
            }
            shadow = true;
        }

        int type = in.readByte();
        int effects = in.readShort();
        String message = Huffman.decrypt(in, 100);
        if(message.isEmpty()) {
            /* how does this even happen? */
            return;
        }

        /*
         * If player mentions any of these words don't send it.
         */
        List<String> badWords = Arrays.asList("runite", "runewild", "pkhonor", "ikov", "roatz", "alora", "nigger", "nigg3r", "n1gg3r", "n1gger");
        for(String word : badWords) {
            if (message.toLowerCase().contains(word)) {
                return;
            }
        }
        int groupId = 0;
        if (player.storeAmountSpent >= 15 && player.storeAmountSpent < 50) {//sapphire
            groupId = 9;
        }
        if (player.storeAmountSpent >= 50 && player.storeAmountSpent < 100) {//emerald
            groupId = 10;
        }
        if (player.storeAmountSpent >= 100 && player.storeAmountSpent < 250) {//ruby
            groupId = 11;
        }
        if (player.storeAmountSpent >= 250 && player.storeAmountSpent < 500) {//diamond
            groupId = 12;
        }
        if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {//onyx
            groupId = 14;
        }
        if (player.youtubeRank == 1) {
            groupId = 13;
        }
        if (player.storeAmountSpent >= 1000) {//zenyte
            groupId = 15;
        }
        if (player.staffRank == 1) {//ss
            groupId = 8;
        }
        if (player.staffRank == 2) {//mod
            groupId = 1;
        }
        if (player.staffRank == 3) {//admin
            groupId = 2;
        }
        if (player.staffRank == 4) {//manager
            groupId = 7;
        }
        if (player.staffRank == 5) {//dev
            groupId = 6;
        }
        if (player.staffRank == 27) {
            groupId = 27;
        }
        if (player.getGameMode() == GameMode.IRONMAN) {
            groupId = 3;
        }
        if (player.getGameMode() == GameMode.HARDCORE_IRONMAN) {
            groupId = 5;
        }
        if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN) {
            groupId = 4;
        }
        if (player.getGameMode() == GameMode.REALISM) {
            groupId = 23;
        }
        if(type == 2) {
            message = message.substring(1, message.length());
            ClanChat.message(player, message);
            Loggers.logClanChat(player.getUserId(), player.name(), player.getIp(), message);
            return;
        }
        if(message.length() >= 3) {
            char c1 = message.charAt(0);
            char c2 = message.charAt(1);
            if((c1 == ':' && c2 == ':') || (c1 == ';' && c2 == ';')) {
                CommandHandler.handle(player, message.substring(2));
                return;
            }
            if (c1 == '!' && player.isAdmin()) {
                player.forceText(message.substring(1));
                return;
            }
        }
        player.getChatUpdate().set(shadow, effects, groupId, type, message);
        player.logger.log(LogType.PUBLIC_CHAT, " effects="+effects+",shadowMute="+shadow+",groupId="+groupId+",type="+type+",message="+message);
        Loggers.logPublicChat(player.getUserId(), player.name(), player.getIp(), message, type, effects);
    }
}