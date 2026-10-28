package io.ruin.model.activities.home.petperks;

import io.ruin.model.entity.player.Player;

public class PetPerksInfo {

    public static int INT = 962;

    //Hiding the green checks until they have collected the pet
    public static void hideCollected(Player p) {
        p.getPacketSender().setHidden(INT, 214, true);
        p.getPacketSender().setHidden(INT, 215, true);
        p.getPacketSender().setHidden(INT, 216, true);
        p.getPacketSender().setHidden(INT, 217, true);
        p.getPacketSender().setHidden(INT, 218, true);
    }

    //Sending each container to the interface
    public static void sendInfoToInter(Player p) {
        sendAbyssalOrphan(p);
        sendBloodhound(p);
        sendCallistoCub(p);
        sendRocky(p);
        sendTektiny(p);
    }

    //Send pet info to interface
    public static void sendAbyssalOrphan(Player p) {
        p.getPacketSender().sendModel(INT, 169, 29631);
        p.getPacketSender().sendModelInformation(INT, 169, 800, 2033, 2047);
        p.getPacketSender().sendString(INT, 170, "Abyssal Orphan");
        p.getPacketSender().sendString(INT, 171, "");
        if (p.getCollectionLog().contains(13262, 1) || p.getInventory().contains(13262)) {
            p.getPacketSender().sendString(INT, 205, "Collected");
            p.getPacketSender().setHidden(INT, 204, true);
        }
    }
    public static void sendBloodhound(Player p) {
        p.getPacketSender().sendModel(INT, 177, 31740);
        p.getPacketSender().sendModelInformation(INT, 177, 800, 3, 213);
        p.getPacketSender().sendString(INT, 178, "Bloodhound");
        p.getPacketSender().sendString(INT, 179, "");
        if (p.getCollectionLog().contains(19730, 1) || p.getInventory().contains(19730)) {
            p.getPacketSender().sendString(INT, 207, "Collected");
            p.getPacketSender().setHidden(INT, 206, true);
        }
    }
    public static void sendCallistoCub(Player p) {
        p.getPacketSender().sendModel(INT, 185, 28298);
        p.getPacketSender().sendModelInformation(INT, 185, 800, 54, 162);
        p.getPacketSender().sendString(INT, 186, "Callisto Cub");
        p.getPacketSender().sendString(INT, 187, "");
        if (p.getCollectionLog().contains(13178, 1) || p.getInventory().contains(13178)) {
            p.getPacketSender().sendString(INT, 209, "Collected");
            p.getPacketSender().setHidden(INT, 208, true);
        }
    }
    public static void sendRocky(Player p) {
        p.getPacketSender().sendModel(INT, 193, 32203);
        p.getPacketSender().sendModelInformation(INT, 193, 800, 195, 333);
        p.getPacketSender().sendString(INT, 194, "Rocky");
        p.getPacketSender().sendString(INT, 195, "");
        if (p.getCollectionLog().contains(20663, 1) || p.getInventory().contains(20663)) {
            p.getPacketSender().sendString(INT, 211, "Collected");
            p.getPacketSender().setHidden(INT, 210, true);
        }
    }
    public static void sendTektiny(Player p) {
        p.getPacketSender().sendModel(INT, 201, 34602);
        p.getPacketSender().sendModelInformation(INT, 201, 800, 2037, 1928);
        p.getPacketSender().sendString(INT, 202, "Tektiny");
        p.getPacketSender().sendString(INT, 203, "");
        if (p.getCollectionLog().contains(20663, 1) || p.getInventory().contains(20663)) {
            p.getPacketSender().sendString(INT, 213, "Collected");
            p.getPacketSender().setHidden(INT, 212, true);
        }
    }

}