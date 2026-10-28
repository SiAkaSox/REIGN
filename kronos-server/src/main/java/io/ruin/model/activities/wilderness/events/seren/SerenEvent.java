package io.ruin.model.activities.wilderness.events.seren;

import io.ruin.utility.Random;
import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.utility.Broadcast;
import io.ruin.utility.TickDelay;

public class SerenEvent {

    private static long SEREN_START_TIMESTAMP = Integer.MAX_VALUE;

    private static final SerenEvent[] SPAWNS = {
            new SerenEvent(new Position(2206, 4581, 0), "inside her lair! Use ::seren to travel there."),
            new SerenEvent(new Position(2206, 4581, 0), "inside her lair! Use ::seren to travel there."),
    };

    private static boolean DISABLED = false;
    private static final int SEREN = 8918;
    private static NPC seren = null;
    public final Position spawnPosition;
    public final String positionHint;
    public static SerenEvent ACTIVE;
    private static long spawnTicks = 0;
    public static long timeRemaining;
    public static boolean eventActive = false;

    public SerenEvent(Position spawnPosition, String positionHint) {
        this.spawnPosition = spawnPosition;
        this.positionHint = positionHint;
    }

    public static boolean isCaptured;

    public static boolean wasKilled;

    public static TickDelay nextFlag = new TickDelay();

    public static void updateOverlay(Player p) {
        int mins = nextFlag.remainingToMins();
        String timeDisplay = nextFlag.finished() ? "<col=27ae60>Now!" : "<col=27ae60>" + mins + " min";
        p.getPacketSender().sendString(859, 144, "Timer: " + timeDisplay);
        if (wasKilled && !isCaptured) {
            p.getPacketSender().sendString(859, 144, "Timer: <col=27ae60>Killed!");
        }
    }

    static {

            /*
             * Event
             */
            /* event runs every 45 minutes */
            World.startEvent(e -> {
                while (true) {
                    SerenEvent next = Random.get(SPAWNS);
                    if (next == ACTIVE) {
                        e.delay(1);
                        continue;
                    }
                    ACTIVE = next;
                    String eventMessage = "The Fragment of Seren has spawned " + ACTIVE.positionHint;
                   Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", eventMessage);
                    spawnSeren();
                    e.delay(30 * 100); // 30 mins
                    despawnSeren();
                    e.delay(180 * 100);
                }
            });
    }

    private static void spawnSeren() {
        eventActive = true;
        isCaptured = false;
        nextFlag.finished();
        seren = new NPC(SEREN).spawn(ACTIVE.spawnPosition);
    }

    public static void serenDied() {
        wasKilled = true;
        String despawnMessage = "The Fragment of Seren was successfully slain before time expired!";
        Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", despawnMessage);
    }

    private static void despawnSeren() {
            seren.remove();
            seren = null;
            String despawnMessage = "The Fragment of Seren event has ended!";
            Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", despawnMessage);
            eventActive = false;
            isCaptured = true;
            nextFlag.delay(180 * 100);
            wasKilled = false;
        }
}