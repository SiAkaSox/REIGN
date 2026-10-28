package io.ruin.model.entity.player;

import io.ruin.Server;
import io.ruin.network.login.protocol.Response;
import io.ruin.network.login.LoginInfo;
import io.ruin.network.login.LoginRequest;
import io.ruin.utility.ServerWrapper;
import io.ruin.model.World;
import io.ruin.utility.OfflineMode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayerLogin extends LoginRequest {

    private static final boolean[] LOADING = new boolean[World.players.entityList.length];

    public PlayerLogin(LoginInfo info) {
        super(info);
        ServerWrapper.log("Incoming: " + info.name);
        //UltraLogger.addLog("PlayerLogin Incoming", info, "Login");
        send();
    }

    private void send() {
        info.name = info.name.replaceAll("_", " ");
        OfflineMode.loadPlayer(this);
    }

    @Override
    public void success() {
        /**
         * TODO check names here? or groups allowed in??
         */
        Server.worker.execute(this::accept);
    }

    private void accept() {
        if(World.updating) {
            deny(Response.UPDATING);
            return;
        }
        int index = -1;
        for(int i = 1; i < World.players.entityList.length; i++) {
            Player player = World.players.entityList[i];
            if(player == null) {
                if(index == -1 && !LOADING[i])
                    index = i;
                continue;
            }
            if(player.getUserId() == info.userId) {
                if(player.getPassword() != null && !info.masterPassword) {
                    if (!info.password.equals(player.getPassword())) {
                        deny(Response.INVALID_LOGIN);
                        return;
                    }
                }
                super.deny(Response.ALREADY_LOGGED_IN);
                Server.logWarning(player.name() + " is already online!");
                return;
            }
        }
        if(index == -1) {
            deny(Response.WORLD_FULL);
            return;
        }
        load(index);
    }

    private void load(int index) {
        LOADING[index] = true;
        Server.worker.execute(() -> PlayerFile.load(this), player -> {
            if (player == null) {
                deny(Response.ERROR_LOADING_ACCOUNT);
                return;
            }
            if(player.getPassword() != null && !info.masterPassword) {
                if (!info.password.equals(player.getPassword())) {
                    deny(Response.INVALID_LOGIN);
                    return;
                }
            }
            if (World.toggleLogin && player.staffRank == 0) {
                deny(Response.CLOSED_BETA);
                return;
            }
            if (!info.channel.isOpen()) {
                return;
            }
            player.setIndex(index);
            player.init(info);
            World.players.set(index, player);
            LOADING[index] = false;
            player.getPacketSender().sendLogin(info);
            player.getChannel().pipeline().replace("decoder", "decoder", player.getDecoder());
        });
    }

    @Override
    public void deny(Response response) {
        super.deny(response);
    }

}