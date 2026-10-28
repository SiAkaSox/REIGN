package io.ruin.model.activities.wilderness.events;

import io.ruin.Server;
import io.ruin.utility.Misc;
import io.ruin.utility.Random;
import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Region;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;
import io.ruin.utility.TickDelay;

import java.util.ArrayList;

import static io.ruin.Server.STARTUP_THREAD;

public class PirateShipEvent {

    public static int BOX = 31572;
    public static final Region REGION = Region.get(13371);
    public static int materialsContributed;
    public static boolean eventActive = false;
    private static long spawnTicks = 0;
    private static boolean DISABLED = false;
    public static PirateShipEvent ACTIVE;
    public static GameObject box = null;
    public static boolean storagefull = false;
    private static final ArrayList<Player> contributors = new ArrayList<>();
    private static final Bounds MAIN_BOUNDS = new Bounds(3355, 3792, 3391, 3840, -1);
    public static int materialsNeeded = 500;

    public static TickDelay nextFlag = new TickDelay();

    public static boolean isCaptured;

    public static void inMainBounds(Player p) {
        if (box != null) {
            p.openInterface(InterfaceType.TARGET_OVERLAY, 908);
            p.getPacketSender().sendString(908, 135, materialsContributed + "/" + materialsNeeded);
        }
    }

    private static void exitedMain(Player player, boolean logout) {
            player.closeInterface(InterfaceType.TARGET_OVERLAY);
            player.closeInterface(InterfaceType.MAIN);
    }

    private static final PirateShipEvent[] SPAWNS = {
            new PirateShipEvent(new Position(3371, 3795, 0), "Booty Bay"),
            new PirateShipEvent(new Position(3371, 3795, 0), "Booty Bay")
    };

    public PirateShipEvent(Position spawnPosition, String positionHint) {
        this.spawnPosition = spawnPosition;
        this.positionHint = positionHint;
    }

    public final Position spawnPosition;
    public final String positionHint;

    public static void updateOverlay(Player p) {
        if (isCaptured) {
            p.getPacketSender().sendString(859, 142, "Timer: <col=27ae60>Filled!");
        } else {
            int mins = nextFlag.remainingToMins();
            String timeDisplay = nextFlag.finished() ? "<col=27ae60>Now!" : "<col=27ae60>" + mins + " min";
            p.getPacketSender().sendString(859, 142, "Timer: " + timeDisplay);
        }
    }

    private static void spawnBox() {
        eventActive = true;
        storagefull = false;
        isCaptured = false;
        box = GameObject.spawn(BOX, ACTIVE.spawnPosition.getX(), ACTIVE.spawnPosition.getY(), 0, 10, 2);
        nextFlag.finished();
    }

    public static void removeBoxWhenFull(boolean success) {
        box.setId(-1);
        box = null;
        eventActive = false;
        isCaptured = true;
        storagefull = true;
        rewardContributors();
        materialsContributed = 0;
        contributors.clear();
        String successMessage = "The Pirates Supply Depot has been filled to the top!";
        Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", successMessage);
    }

    public static void rewardContributors() {
        MAIN_BOUNDS.getRegion().players.forEach(player -> {
            if (player.pirateshipcontributedAmount > 0) {
                int amount = player.pirateshipcontributedAmount;
                player.sendMessage("You have been rewarded <col=8B0000>x"+ Misc.formatNumber(amount)+" Shards</col> for contributing to the Pirates!");
                player.getInventory().addOrDrop(25676, amount);
                player.getInventory().addOrDrop(3464, 1);
                player.pirateshipcontributedAmount = 0;
                player.pirateshipCompletions++;
                player.closeInterface(InterfaceType.TARGET_OVERLAY);
            } else {
                player.sendMessage("You did not contribute to the event, and were not rewarded!");
            }
        });
    }

    public static void removeBoxNotFilled(boolean success) {
        if (box == null) {
            eventActive = false;
            isCaptured = false;
            materialsContributed = 0;
            String failedMessage = "The Pirates Ship event has ended!";
            Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", failedMessage);
            nextFlag.delay(110 * 100);
            MAIN_BOUNDS.getRegion().players.forEach(player -> {
                player.pirateshipcontributedAmount = 0;
                player.closeInterface(InterfaceType.TARGET_OVERLAY);
            });
        } else {
            box.setId(-1);
            eventActive = false;
            isCaptured = false;
            box = null;
            String failedMessage = "The Pirates Ship event has ended!";
            Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", failedMessage);
            nextFlag.delay(110 * 100);
            MAIN_BOUNDS.getRegion().players.forEach(player -> {
                player.pirateshipcontributedAmount = 0;
                player.closeInterface(InterfaceType.TARGET_OVERLAY);
            });
        }
    }

    static {

            if (!DISABLED) {
                World.startEvent(e -> {
                    while (true) {
                        spawnTicks = Server.getEnd(110 * 100);
                        PirateShipEvent next = Random.get(SPAWNS);
                        if (next == ACTIVE) {
                            e.delay(1);
                            continue;
                        }
                        ACTIVE = next;
                        String eventMessage = "The Pirates of " + ACTIVE.positionHint + " need your help fixing their ship!";
                        Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", eventMessage);
                        materialsContributed = 0;
                        spawnBox();
                        e.delay(80 * 100);
                        removeBoxNotFilled(false);
                        e.delay(110 * 100);
                    }
                });
            }
            MapListener.registerBounds(MAIN_BOUNDS)
                    .onEnter(PirateShipEvent::inMainBounds)
                    .onExit(PirateShipEvent::exitedMain);
            ObjectAction.register(BOX, 1, (p, obj) -> {
                addtoStorage(p);
            });
    }

    public static void pulse(Player p) {
        if (eventActive) {
            if (materialsContributed >= materialsNeeded) {
                removeBoxWhenFull(true);
            }
            p.getPacketSender().sendString(908, 135, materialsContributed+"/"+materialsNeeded);
        }
    }

    public static void addtoStorage(Player p) {
        if (p.getInventory().contains(1539)) {
            p.startEvent(e -> {
                inMainBounds(p);
                p.lock(LockType.FULL);
                int amount = p.getInventory().getAmount(1539);
                p.pirateshipcontributedAmount += amount;
                materialsContributed += amount;
                p.animate(881);
                e.delay(1);
                p.getPacketSender().sendString(908, 135, materialsContributed+"/"+materialsNeeded);
                p.sendMessage("You contributed <col=8B0000>x"+amount+" Steel Nails</col> to the Pirates Supply Depot!");
                e.delay(1);
                p.getInventory().remove(1539, amount);
                p.unlock();
                if (!contributors.contains(p)) {
                    contributors.add(p);
                }
            });
        }
        if (p.getInventory().contains(960)) {
            p.startEvent(e -> {
                inMainBounds(p);
                p.lock(LockType.FULL);
                int amount = p.getInventory().getAmount(960);
                materialsContributed += amount;
                p.pirateshipcontributedAmount += amount;
                p.animate(881);
                e.delay(1);
                p.sendMessage("You contributed <col=8B0000>x"+amount+" Plank</col> to the Pirates Supply Depot!");
                p.getPacketSender().sendString(908, 135, materialsContributed+"/"+materialsNeeded);
                e.delay(1);
                p.getInventory().remove(960, amount);
                if (!contributors.contains(p)) {
                    contributors.add(p);
                }
                p.unlock();
            });
        }
        if (p.getInventory().contains(8778)) {
            p.startEvent(e -> {
                inMainBounds(p);
                p.lock(LockType.FULL);
                int amount = p.getInventory().getAmount(8778);
                materialsContributed += amount;
                p.pirateshipcontributedAmount += amount;
                p.animate(881);
                e.delay(1);
                p.sendMessage("You contributed <col=8B0000>x"+amount+" Oak Plank</col> to the Pirates Supply Depot!");
                p.getPacketSender().sendString(908, 135, materialsContributed+"/"+materialsNeeded);
                e.delay(1);
                p.getInventory().remove(8778, amount);
                if (!contributors.contains(p)) {
                    contributors.add(p);
                }
                p.unlock();
            });
        }
        if (p.getInventory().contains(8780)) {
            p.startEvent(e -> {
                inMainBounds(p);
                p.lock(LockType.FULL);
                int amount = p.getInventory().getAmount(8780);
                materialsContributed += amount;
                p.pirateshipcontributedAmount += amount;
                p.animate(881);
                e.delay(1);
                p.sendMessage("You contributed <col=8B0000>x"+amount+" Teak Plank</col> to the Pirates Supply Depot!");
                p.getPacketSender().sendString(908, 135, materialsContributed+"/"+materialsNeeded);
                e.delay(1);
                p.getInventory().remove(8780, amount);
                if (!contributors.contains(p)) {
                    contributors.add(p);
                }
                p.unlock();
            });
        }
        if (p.getInventory().contains(8782)) {
            p.startEvent(e -> {
                inMainBounds(p);
                p.lock(LockType.FULL);
                int amount = p.getInventory().getAmount(8782);
                materialsContributed += amount;
                p.pirateshipcontributedAmount += amount;
                p.animate(881);
                e.delay(1);
                p.sendMessage("You contributed <col=8B0000>x"+amount+" Mahogany Plank</col> to the Pirates Supply Depot!");
                p.getPacketSender().sendString(908, 135, materialsContributed+"/"+materialsNeeded);
                e.delay(1);
                p.getInventory().remove(8782, amount);
                    if (!contributors.contains(p)) {
                    contributors.add(p);
                }
                    p.unlock();
            });
        }
        if (p.getInventory().contains(950)) {
            p.startEvent(e -> {
                inMainBounds(p);
                p.lock(LockType.FULL);
                int amount = p.getInventory().getAmount(950);
                materialsContributed += amount;
                p.pirateshipcontributedAmount += amount;
                p.animate(881);
                e.delay(1);
                p.sendMessage("You contributed <col=8B0000>x"+amount+" Silk</col> to the Pirates Supply Depot!");
                p.getPacketSender().sendString(908, 135, materialsContributed+"/"+materialsNeeded);
                e.delay(1);
                p.getInventory().remove(950, amount);
                if (!contributors.contains(p)) {
                    contributors.add(p);
                }
                p.unlock();
            });
        }
        if (p.getInventory().contains(453)) {
            p.startEvent(e -> {
                inMainBounds(p);
                p.lock(LockType.FULL);
                int amount = p.getInventory().getAmount(453);
                materialsContributed += amount;
                p.pirateshipcontributedAmount += amount;
                p.animate(881);
                e.delay(1);
                p.sendMessage("You contributed <col=8B0000>x"+amount+" Coal</col> to the Pirates Supply Depot!");
                p.getPacketSender().sendString(908, 135, materialsContributed+"/"+materialsNeeded);
                e.delay(1);
                p.getInventory().remove(453, amount);
                if (!contributors.contains(p)) {
                    contributors.add(p);
                }
                p.unlock();
            });
        }
        if (p.getInventory().contains(1325)) {
            p.startEvent(e -> {
                inMainBounds(p);
                p.lock(LockType.FULL);
                int amount = p.getInventory().getAmount(1325);
                materialsContributed += amount;
                p.pirateshipcontributedAmount += amount;
                p.animate(881);
                e.delay(1);
                p.sendMessage("You contributed <col=8B0000>x"+amount+" Steel Scimitar</col> to the Pirates Supply Depot!");
                p.getPacketSender().sendString(908, 135, materialsContributed+"/"+materialsNeeded);
                e.delay(1);
                p.getInventory().remove(1325, amount);
                if (!contributors.contains(p)) {
                    contributors.add(p);
                }
                p.unlock();
            });
        }
        if (p.getInventory().contains(1329)) {
            p.startEvent(e -> {
                inMainBounds(p);
                p.lock(LockType.FULL);
                int amount = p.getInventory().getAmount(1329);
                materialsContributed += amount;
                p.pirateshipcontributedAmount += amount;
                p.animate(881);
                e.delay(1);
                p.sendMessage("You contributed <col=8B0000>x"+amount+" Mithril Scimitar</col> to the Pirates Supply Depot!");
                p.getPacketSender().sendString(908, 135, materialsContributed+"/"+materialsNeeded);
                e.delay(1);
                p.getInventory().remove(1329, amount);
                if (!contributors.contains(p)) {
                    contributors.add(p);
                }
                p.unlock();
            });
        }
        if (p.getInventory().contains(379)) {
            p.startEvent(e -> {
                inMainBounds(p);
                p.lock(LockType.FULL);
                int amount = p.getInventory().getAmount(379);
                materialsContributed += amount;
                p.pirateshipcontributedAmount += amount;
                p.animate(881);
                e.delay(1);
                p.sendMessage("You contributed <col=8B0000>x"+amount+" Lobster</col> to the Pirates Supply Depot!");
                p.getPacketSender().sendString(908, 135, materialsContributed+"/"+materialsNeeded);
                e.delay(1);
                p.getInventory().remove(379, amount);
                if (!contributors.contains(p)) {
                    contributors.add(p);
                }
                p.unlock();
            });
        }
    }

}