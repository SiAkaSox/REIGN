package io.ruin.network.login;

import io.ruin.network.login.protocol.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class LoginRequest {

    public final LoginInfo info;

    public LoginRequest(LoginInfo info) {
        this.info = info;
    }

    public void deny(Response response) {
        if(info.channel != null)
            response.send(info.channel);
        log.info("Denied Login for: "+info.name+","+info.ipAddress+","+info.macAddress+","+info.uuid+" Response="+response.name());
    }

    public abstract void success();

}