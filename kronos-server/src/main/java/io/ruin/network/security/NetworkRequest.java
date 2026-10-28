package io.ruin.network.security;

import lombok.Getter;

@Getter
public class NetworkRequest {

    public String uuid, mac, ip, username;
    public long requestedAt;

    public NetworkRequest(String uuid, String mac, String ip, String username) {
        this.uuid = uuid;
        this.mac = mac;
        this.ip = ip;
        this.username = username;
        this.requestedAt = System.currentTimeMillis();
    }
}
