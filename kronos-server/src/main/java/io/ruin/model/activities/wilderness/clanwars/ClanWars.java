package io.ruin.model.activities.wilderness.clanwars;

import io.ruin.utility.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.dynamic.DynamicMap;

import java.util.ArrayList;

public class ClanWars {

    public static Player player;

    public ClanWars targetCw;

    private static DynamicMap map;

    public static Bounds outsidePortal = new Bounds(3135, 3619, 3142, 3627, 0);

    private static final ArrayList<Player> playersTeamOne = new ArrayList<>();
    private static final ArrayList<Player> playersTeamTwo = new ArrayList<>();

    public static int teamoneKills;
    public static int teamtwoKills;

    static {
        MapListener.registerBounds(outsidePortal)
                .onEnter(player -> { player.setAction(1, PlayerAction.WAR); })
                .onExit((player, logout) -> { player.setAction(1, null); });
        playersTeamOne.forEach(player -> {
            player.teleportListener = p -> {
                p.sendMessage("Spells of this kind are not allowed within clan wars.");
                return false;
            };
            player.deathEndListener = (entity, killer, hit) -> {
                if (entity instanceof Player) {
                    player.sendMessage("Oh dear, you have died!");
                    teamoneKills++;
                    player.getMovement().teleport(map.convertX(2656) + Random.get(3), map.convertY(2610) + Random.get(4));
                }
            };
        });
        playersTeamTwo.forEach(player -> {
            player.teleportListener = p -> {
                p.sendMessage("Spells of this kind are not allowed within clan wars.");
                return false;
            };
            player.deathEndListener = (entity, killer, hit) -> {
                if (entity instanceof Player) {
                    player.sendMessage("Oh dear, you have died!");
                    teamtwoKills++;
                    player.getMovement().teleport(map.convertX(2656) + Random.get(3), map.convertY(2610) + Random.get(4));
                }
            };
        });
    }

    public static void normalize(Player p) {
        p.attackPlayerListener = null;
        p.teleportListener = null;
        p.logoutListener = null;
        p.deathEndListener = null;
        p.setAction(1, null);
        p.getCombat().restore();
        p.getCombat().reset();
        p.getCombat().setDead(false);
    }

    public static void request(Player target) {
            if (!target.getPosition().inBounds(outsidePortal)) {
                player.sendMessage("You can't challenge this player from here");
                return;
            }
            if (!player.getPosition().inBounds(outsidePortal)) {
                player.sendMessage("You can't challenge this player from here");
                return;
            }
            if (target.isLocked()) {
                player.sendMessage("This player is currently busy.");
                return;
            }
        target.getPacketSender().sendMessage(player.name() + " wishes to challenge your chat-channel to a Clan War.", player.name(), 103);
        player.getPacketSender().sendMessage("Sending challenge to" + target.name() + " on behalf of your chat channel...", null, 102);
    }


}