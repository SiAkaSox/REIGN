package io.ruin.network.incoming.handlers;

import io.ruin.network.io.InBuffer;
import io.ruin.cache.filestore.utility.Huffman;
import io.ruin.network.login.protocol.Protocol;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.clanchat.ClanChat;
import io.ruin.model.entity.player.friendslist.FriendsList;
import io.ruin.model.entity.player.friendslist.FriendsListAction;
import io.ruin.network.PacketSender;
import io.ruin.network.incoming.Incoming;
import io.ruin.services.Loggers;
import io.ruin.services.punishment.PunishmentManager;
import io.ruin.utility.IdHolder;

@IdHolder(ids = {84, 80, 48, 56, 93, 67})
public class FriendsHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        String name;
        if(opcode == 67) {
            /**
             * Rank friend
             */
            int rank = in.readByte();
            name = in.readString();
            if (name.length() > 12 || rank > 15) {
                System.err.println("invalid name length / rank for "+player.getUsername()+" "+player.getUUID());
                return;
            }
            ClanChat.assignRank(player, rank, name);
            return;
        }
        name = in.readString().toLowerCase();
        if (name.length() > 15) {
            System.err.println(player.name()+" "+player.getUUID()+" sent invalid name..?");
            return;
        }
        if(opcode == 80) {
            /**
             * Add friend
             */
            FriendsList.handle(player, name, FriendsListAction.ADD_FRIEND);
            return;
        }
        if(opcode == 48) {
            /**
             * Delete friend
             */
            FriendsList.handle(player, name, FriendsListAction.DELETE_FRIEND);
            return;
        }
        if(opcode == 84) {
            /**
             * Add ignore
             */
            FriendsList.handle(player, name, FriendsListAction.ADD_IGNORE);
            return;
        }
        if(opcode == 56) {
            /**
             * Delete ignore
             */
            FriendsList.handle(player, name, FriendsListAction.DELETE_IGNORE);
            return;
        }
        if(opcode == 93) {
            /**
             * Private message
             */
            String message = Huffman.decrypt(in, 100);
            if (message.length() > 60) {
                message.substring(60, message.length());
            }
            if(PunishmentManager.isMuted(player)) {
                if(player.shadowMute)
                    player.getPacketSender().write(Protocol.outgoingPm(name, message));
                else
                    player.sendMessage("You're muted and can't talk.");
                return;
            }
            PacketSender.sendPrivateMessage(player, name, message);
            Loggers.logPrivateChat(player.getUserId(), player.name(), player.getIp(), name, message);
            return;
        }
    }

}