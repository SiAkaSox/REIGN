package io.ruin.model.activities.wilderness.events;

import io.ruin.Server;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Random;
import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.achievements.listeners.medium.BattlegroundNovice;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;
import io.ruin.utility.TickDelay;

import java.util.concurrent.TimeUnit;

public class DeadmanChestEvent {

    private static final DeadmanChestEvent[] SPAWNS = {
            new DeadmanChestEvent(new Position(3017, 3809, 0), "south of the KBD entrance"),
            new DeadmanChestEvent(new Position(3139, 3858, 0), "north-east of the Revenant Cave"),
            new DeadmanChestEvent(new Position(3354, 3758, 0), "east of Venenatis"),
    };

    public DeadmanChestEvent(Position spawnPosition, String positionHint) {
        this.spawnPosition = spawnPosition;
        this.positionHint = positionHint;
    }

    public final Position spawnPosition;
    public final String positionHint;

    private static long timeRemaining;
    private static long spawnTicks = 0;
    private static boolean DISABLED = false;
    public static DeadmanChestEvent ACTIVE;
    public static GameObject chest = null;
    private static final int CHEST = 33117;
    public static boolean opened = false;
    public static boolean eventActive = false;

    public static TickDelay nextChest= new TickDelay();

    public static void updateOverlay(Player p) {
        if (isCaptured) {
            p.getPacketSender().sendString(859, 140, "Timer: <col=27ae60>Looted!");
        } else {
            int mins = nextChest.remainingToMins();
            String timeDisplay = eventActive ? "<col=27ae60>Now!" : "<col=27ae60>" + mins + " min";
            p.getPacketSender().sendString(859, 140, "Timer: " + timeDisplay);
        }
    }

    private static void spawnChest() {
        opened = false;
        eventActive = true;
        isCaptured = false;
        chest = GameObject.spawn(CHEST, ACTIVE.spawnPosition.getX(), ACTIVE.spawnPosition.getY(), 0, 10, 0);
        nextChest.finished();
    }

    public static void removeChestOpened(boolean success) {
        chest = null;
        opened = true;
        eventActive = false;
        String successMessage = "The Deadman Chest was opened and looted!";
        Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", successMessage);
        nextChest.delay(45 * 100);
    }

    public static void removeChestNotOpened(boolean success) {
        if (chest == null) {
            eventActive = false;
            isCaptured = false;
            String failedMessage = "The Deadman Chest event has ended!";
            Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", failedMessage);
            nextChest.delay(45 * 100);
        } else {
            chest.setId(-1);
            eventActive = false;
            isCaptured = false;
            chest = null;
            String failedMessage = "The Deadman Chest event has ended!";
            Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", failedMessage);
            nextChest.delay(45 * 100);
        }
    }

    public static boolean isCaptured;

    static {

            if (!DISABLED) {
                World.startEvent(e -> {
                    while (true) {
                        spawnTicks = Server.getEnd(3 * 100);
                        DeadmanChestEvent next = Random.get(SPAWNS);
                        if (next == ACTIVE) {
                            e.delay(1);
                            continue;
                        }
                        ACTIVE = next;
                        String eventMessage = "A Deadman Chest has been spotted " + ACTIVE.positionHint + "!";
                        Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", eventMessage);
                        spawnChest();
                        timeRemaining = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15);
                        e.delay(45 * 100);
                        removeChestNotOpened(false);
                        e.delay(45 * 100);
                    }
                });
            }
            ObjectAction.register(CHEST, 1, (player, obj) -> openChest(player));
    }

    public static void openChest(Player p) {
        if (isCaptured) {
            p.sendMessage("The chest has already been captured!");
        }
        if (p.getCombat().getLevel() < 126) {
            p.dialogue(
                    new MessageDialogue("You must be 126 combat level to loot the chest!")
            );
            return;
        }
        isCaptured = true;
        p.animate(881);
        chest.setId(-1);
        chest = null;
        p.getCombat().skullNormal();
        p.getCombat().teleblock();
        p.supplyChestRestricted = true;
        p.dialogue(new ItemDialogue().one(3463, "You open the Deadman Chest and receive a Great Vault key!"));
        p.sendMessage("You loot the chest, and receive a <col=8B0000>Great Vault Key!");
        p.sendMessage("You have been skulled and teleblocked.");
        p.getInventory().addOrDrop(3463, 1);
        p.getStats().get(StatType.Prayer).drain(99.0);
        p.getStats().get(StatType.Attack).drain(99.0);
        p.getStats().get(StatType.Strength).drain(99.0);
        p.getStats().get(StatType.Defence).drain(99.0);
        p.getStats().get(StatType.Magic).drain(99.0);
        p.getStats().get(StatType.Ranged).drain(99.0);
        BattlegroundNovice.complete(p);
        String eventMessage = p.name()+" has looted the Deadman Chest "+ACTIVE.positionHint+"!";
        Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", eventMessage);
    }

}