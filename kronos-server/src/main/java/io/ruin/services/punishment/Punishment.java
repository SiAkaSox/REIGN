package io.ruin.services.punishment;

import io.ruin.model.entity.player.Player;

public class Punishment {

    public String punishedBy;
    public String punishReason;
    public PunishmentType punishmentType;
    public long punishmentDuration;

    public String punisherIP;
    public String punisherMAC;
    public String punisherUUID;
    public int staffLevel;

    public int requiredOres;

    public Punishment() {

    }

    public Punishment(Player player, String punishReason, PunishmentType punishmentType, long punishmentDuration) {
        this.punishedBy = player.name();
        this.punishReason = punishReason;
        this.punishmentType = punishmentType;
        this.punishmentDuration = punishmentDuration;
        this.punisherIP = player.getIp();
        this.punisherMAC = player.getMACAddress();
        this.punisherUUID = player.getUUID();
        this.staffLevel = player.staffRank;
    }

    public Punishment(Player player, String punishReason, int requiredOres) {
        this.punishedBy = player.name();
        this.punishReason = punishReason;
        this.punishmentType = PunishmentType.JAIL;
        this.requiredOres = requiredOres;
        this.punisherIP = player.getIp();
        this.punisherMAC = player.getMACAddress();
        this.punisherUUID = player.getUUID();
        this.staffLevel = player.staffRank;
    }
}
