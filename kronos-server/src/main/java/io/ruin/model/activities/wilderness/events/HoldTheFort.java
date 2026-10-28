package io.ruin.model.activities.wilderness.events;

import io.ruin.Server;
import io.ruin.utility.Random;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.achievements.listeners.elite.FortHolder;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;
import io.ruin.utility.TickDelay;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static io.ruin.Server.STARTUP_THREAD;

public class HoldTheFort {

    public static final int REGION_ID = 13117;
    public static TickDelay nextEvent = new TickDelay();

    private static final HoldTheFort[] HOTSPOTS = {
            new HoldTheFort("Rogues Castle1", new Bounds(3274, 3922, 3298, 3947, -1)),
            new HoldTheFort("Rogues Castle2", new Bounds(3274, 3922, 3298, 3947, -1))
    };

    public static HoldTheFort ACTIVE = Random.get(HOTSPOTS);
    private static long spawnTicks = 0;
    private static long timeRemaining;
    private static GameObject orb;
    private static final int ORB = 33441;
    public static boolean orbClaimed;
    private static final ArrayList<String> orbClaimedBy = new ArrayList<>();
    public static boolean eventActive;

    public static void updateOverlay(Player p) {
        if (orbClaimed) {
            p.getPacketSender().sendString(859, 168, "Timer: <col=27ae60>Captured!");
        } else {
            int mins = nextEvent.remainingToMins();
            String timeDisplay = nextEvent.finished() ? "<col=27ae60>Now!" : "<col=27ae60>" + mins + " min";
            p.getPacketSender().sendString(859, 168, "Timer: " + timeDisplay);
        }
    }

    /**
     * Separator
     */

    public final String name;

    public final Bounds bounds;

    private static boolean allowPrayer(Player player) {
        return player.getPosition().regionId() != REGION_ID;
    }

    public HoldTheFort(String name, Bounds bounds) {
        this.name = name;
        this.bounds = bounds;
        MapListener.registerBounds(bounds)
                .onEnter(player -> {
                    if (bounds == ACTIVE.bounds && eventActive) {
                            disablePrayers(player);
                    }
                })
                .onExit((player, logout) -> {
                    if (!logout && bounds == ACTIVE.bounds && eventActive)
                        player.sendMessage("<img=123>" + Color.ORANGE_RED.wrap("You have left the event area."));
                });
    }

    public static void disablePrayers(Player p) {
        p.sendMessage("<img=123>" + Color.ORANGE_RED.wrap("Your prayers have been disabled while in the Fort!"));
        p.allowPrayerListener = HoldTheFort::allowPrayer;
    }

    /*
     * Event
     */
    static {

            ObjectAction.register(ORB, 1, (player, obj) -> claimOrb(player));
            int swapTicks = 45 * 100; //2 minutes
            World.startEvent(e -> {
                while (true) {
                    spawnTicks = Server.getEnd(45 * 100);
                    HoldTheFort next = Random.get(HOTSPOTS);
                    if (next == ACTIVE) {
                        e.delay(1);
                        continue;
                    }
                    timeRemaining = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2);
                    // e.delay(45 * 100);
                    orbClaimed = false;
                    ACTIVE = next;
                    spawnOrb();
                    String eventMessage = "Hold The Fort is now active! Travel to Rogues Castle to compete!";
                    Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", eventMessage);
                   broadcastEvent(eventMessage);
                    e.delay(swapTicks);
                    String endMessage = "The Hold The Fort event has ended.";
                    Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", endMessage);
                    orbClaimedBy.clear();
                    orbClaimed = false;
                    eventActive = false;
                    removeOrb();
                    e.delay(swapTicks);
                }
            });
    }

    private static void spawnOrb() {
        eventActive = true;
        orb = GameObject.spawn(ORB, 3288, 3932, 1, 10, 0);
        nextEvent.finished();
    }

    private static void removeOrb() {
        eventActive = false;
        if (orb == null) {
            return;
        }
        orb.remove();
        orb = null;
        nextEvent.delay(45 * 100);
    }

    public static void claimOrb(Player p) {
        if (orbClaimedBy.contains(p.name())) {
            p.dialogue(
                    new MessageDialogue("You are already Holding the Fort!")
            );
            return;
        }
        if (p.getCombat().getLevel() < 126) {
            p.dialogue(
                    new MessageDialogue("You must be 126 combat level to hold the fort!")
            );
            return;
        }
        if (ACTIVE == null) {
            p.sendMessage("The event is not currently active.");
        }
        if (orbClaimedBy.isEmpty()) {
            orbClaimedBy.add(p.name());
            if (orbClaimedBy.contains(p.name())) {
                startGameTimer(p);
                p.dialogue(
                        new MessageDialogue("You are now Holding the Fort! Defend the orb for 60 seconds to win!")
                );
                p.sendMessage("<img=123>" + Color.ORANGE_RED.wrap("You are now Holding the Fort! Defend the orb for 60 seconds to win!"));
            }
            orbClaimed = true;
            String eventMessage = orbClaimedBy.get(0) + " is currently Holding the Fort!";
            broadcastEvent(eventMessage);
            Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", eventMessage);
        }
        if (!orbClaimedBy.isEmpty() && !orbClaimedBy.contains(p.name())) {
            if (orbClaimedBy.contains(p.name())) {
                p.holdthefortTicks = -1;
                p.closeInterfaces();
            }
            orbClaimedBy.clear();
            p.closeInterface(InterfaceType.TARGET_OVERLAY);
            orbClaimedBy.add(p.name());
            if (orbClaimedBy.contains(p.name())) {
                startGameTimer(p);
                p.holdthefortTicks--;
                FortHolder.complete(p);
                p.sendMessage("<img=123>" + Color.ORANGE_RED.wrap("You are now Holding the Fort! Defend the orb for 60 seconds to win!"));
            }
            String newOrbHolderMessage = orbClaimedBy.get(0) + " has taken control of the Fort! You have 60 seconds to take control!";
            broadcastEvent(newOrbHolderMessage);
            Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", newOrbHolderMessage);
        }
        p.teleportListener = player -> {
            if (orbClaimedBy.contains(p.name())) {
                orbClaimedBy.clear();
                orbClaimed = false;
                return true;
            }
            return true;
        };
        p.logoutListener = new LogoutListener().onLogout(player -> {
            if (orbClaimedBy.contains(p.name())) {
                orbClaimedBy.clear();
                orbClaimed = false;
                String deathMessage = p.name() + " has died and lost control of the fort!";
                broadcastEvent(deathMessage);
                p.holdthefortTicks = -1;
            }
        });
        p.deathStartListener = (DeathListener.Simple) () -> {
            if (orbClaimedBy.contains(p.name())) {
                orbClaimedBy.clear();
                orbClaimed = false;
                String deathMessage = p.name() + " has died and lost control of the fort!";
                Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", deathMessage);
                broadcastEvent(deathMessage);
                p.holdthefortTicks = -1;
            }
        };
    }

    public static void startGameTimer(Player player) {
        player.holdthefortTicks += 100;
        player.getPacketSender().sendWidget(Widget.HOLD_THE_FORT, (int) (100 * 0.6));
    }

    private static void broadcastEvent(String eventMessage) {
        for(Player p : World.players) {
            if(p.broadcastHoldTheFort)
                p.getPacketSender().sendMessage(eventMessage, "", 14);
        }
    }

    public static void winEvent(Player p) {
        nextEvent.delay(45 * 100);
        eventActive = false;
        if (orbClaimedBy.contains(p.name())) {
            p.sendMessage("You have won the Hold The Fort event! You have been rewarded with a <col=8B0000>Great Vault Key.");
            p.getInventory().addOrDrop(3460, 1);
            String eventMessage = "" + p.name() + " was successful at Holding The Fort!";
            FortHolder.complete(p);
            Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", eventMessage);
            broadcastEvent(eventMessage);
            orbClaimedBy.clear();
            orbClaimed = false;
            removeOrb();
        } else if (p.holdthefortTicks >= 0 && !orbClaimedBy.contains(p.name())) {
            return;
        }
    }

}
