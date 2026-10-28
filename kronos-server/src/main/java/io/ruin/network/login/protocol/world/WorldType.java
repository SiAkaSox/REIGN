package io.ruin.network.login.protocol.world;

import lombok.Getter;

@Getter
public enum WorldType {

    LIVE("OSPK", "http://ospk.net", 13302, 1),
    BETA("OSPK - BETA", "http://ospk.net", 13302, 4),
    DEV("OSPK - DEV", "http://127.0.0.1", 13302, 2);

    WorldType(String worldName, String websiteUrl, int port, int id) {
        this.worldName = worldName;
        this.websiteUrl = websiteUrl;
        this.port = port;
        this.id = id;
    }

    private String worldName, websiteUrl;
    private int port, id;
}