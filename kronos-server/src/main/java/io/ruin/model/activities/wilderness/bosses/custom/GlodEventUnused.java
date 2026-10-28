package io.ruin.model.activities.wilderness.bosses.custom;

import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.utility.Broadcast;

public class GlodEventUnused {

    private static final GlodEventUnused[] SPAWNS = {
            new GlodEventUnused(new Position(3029, 3631, 0), "at the center of the Dark Warriors' Fortress!"),
            new GlodEventUnused(new Position(3079, 3871, 0), "inside the Lava Maze!"),
            new GlodEventUnused(new Position(3345, 3871, 0), "at the Fountain of Rune!"),
    };
    private static boolean DISABLED = true;
    private static final int GLOD = 5129;
    private static NPC glod = null;
    public final Position spawnPosition;
    public final String positionHint;
    public static GlodEventUnused ACTIVE;
    private static long spawnTicks = 0;
    private static long timeRemaining;
    public static boolean eventActive = false;
    public GlodEventUnused(Position spawnPosition, String positionHint) {
        this.spawnPosition = spawnPosition;
        this.positionHint = positionHint;
    }

    static {
        /*
         * Event
         */
        /* event runs every 45 minutes */
       /* World.startEvent(e -> {
            e.delay(90 * 100);
            while (true) {
                spawnTicks = Server.getEnd(60 * 100);
                GlodEvent next = Random.get(SPAWNS);
                if (next == ACTIVE) {
                    e.delay(1);
                    continue;
                }
                ACTIVE = next;
                String eventMessage = "Glod has been located "+ACTIVE.positionHint;
                Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", eventMessage);
                broadcastEvent(eventMessage);
                spawnGlod();
                e.delay(20 * 100); // 15 mins
                despawnGlod();
                e.delay(60 * 100);
            }
        });*/
    }


    private static void spawnGlod() {
        eventActive = true;
        glod = new NPC(GLOD).spawn(ACTIVE.spawnPosition);
    }

    private static void despawnGlod() {
        if (glod != null) {
            glod.remove();
            eventActive = false;
            glod = null;
            String despawnMessage = "Glod has retreated, for now. He won't be gone for long!";
            Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", despawnMessage);
        }
    }

    private static void broadcastEvent(String eventMessage) {
        for(Player p : World.players) {
            if(p.broadcastGlod) {
                p.getPacketSender().sendMessage(eventMessage, "", 14);
            }
        }
        Broadcast.WORLD.sendNews(Icon.WILDERNESS, "", eventMessage);
    }

}