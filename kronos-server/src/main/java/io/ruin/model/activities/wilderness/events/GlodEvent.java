package io.ruin.model.activities.wilderness.events;

import io.ruin.Server;
import io.ruin.utility.Random;
import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.utility.Broadcast;
import io.ruin.utility.TickDelay;

public class GlodEvent {

    private static final GlodEvent[] SPAWNS = {
            new GlodEvent(new Position(3307, 3663, 0), "north of the hill giants"),
            new GlodEvent(new Position(3158, 3848, 0), "east of lava maze"),
            new GlodEvent(new Position(3034, 3682, 0), "inside the bandit camp"),
            new GlodEvent(new Position(3151, 3886, 0), "west of giant spider hill"),
            new GlodEvent(new Position(3261, 3786, 0), "south east of lava dragon isle"),
    };

    private static boolean DISABLED = false;
    private static final int GLOD = 5129;
    private static NPC glod = null;
    public final Position spawnPosition;
    public final String positionHint;
    public static GlodEvent ACTIVE;
    public static long timeRemaining;
    public static boolean eventActive = false;

    public GlodEvent(Position spawnPosition, String positionHint) {
        this.spawnPosition = spawnPosition;
        this.positionHint = positionHint;
    }

    public static boolean isCaptured;

    public static boolean wasKilled;

    public static TickDelay nextFlag = new TickDelay();

    public static void updateOverlay(Player p) {
        int mins = nextFlag.remainingToMins();
        String timeDisplay = nextFlag.finished() ? "<col=27ae60>Now!" : "<col=27ae60>" + mins + " min";
        p.getPacketSender().sendString(859, 187, "Timer: " + timeDisplay);
        if (wasKilled && !isCaptured) {
            p.getPacketSender().sendString(859, 187, "Timer: <col=27ae60>Killed!");
        }
    }

    static {

            World.startEvent(e -> {
                while (true) {
                    GlodEvent next = Random.get(SPAWNS);
                    if (next == ACTIVE) {
                        e.delay(1);
                        continue;
                    }
                    ACTIVE = next;
                    String eventMessage = "Glod has spawned " + ACTIVE.positionHint;
                   Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", eventMessage);
                    spawnGlod();
                    e.delay(30 * 100); // 60 mins
                    despawnGlod();
                    e.delay(90 * 100);
                }
            });
    }

    private static void spawnGlod() {
        eventActive = true;
        isCaptured = false;
        nextFlag.finished();
        glod = new NPC(GLOD).spawn(ACTIVE.spawnPosition);
        wasKilled = false;
    }

    public static void glodDied() {
        wasKilled = true;
        String despawnMessage = "Glod was successfully slain before time expired!";
        Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", despawnMessage);
    }

    public static void despawnGlod() {
            glod.remove();
            glod = null;
            String despawnMessage = "The Glod world event has ended, and will return in 90 minutes.";
            Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", despawnMessage);
            eventActive = false;
            isCaptured = true;
            nextFlag.delay(90 * 100);
            wasKilled = false;
    }

}