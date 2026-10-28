package io.ruin.model.activities.timerift;

import io.ruin.utility.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;

import java.util.ArrayList;

import static io.ruin.model.activities.timerift.TeamTasks.teamShardsCollected;

public class TimeRiftEvent {

    private static final TimeRiftEvent[] HOTSPOTS = {
            new TimeRiftEvent(new Bounds(2088, 2760, 2342, 3006, -1)),
            new TimeRiftEvent(new Bounds(2088, 2760, 2342, 3006, -1))
    };

    private static final ArrayList<Player> playersInZone = new ArrayList<>();

    public static TimeRiftEvent ACTIVE = Random.get(HOTSPOTS);
    public static boolean bossDead;
    public static boolean eventActive = false;
    public static boolean eventStarted;
    public static boolean phaseOne;
    public static boolean phaseTwo;
    public final Bounds bounds;
    private static NPC northOne = null; private static final int NORTHONE = 6594;
    private static NPC northTwo = null; private static final int NORTHTWO = 6604;
    private static NPC southOne = null; private static final int SOUTHONE = 7934;
    private static NPC southTwo = null; private static final int SOUTHTWO = 7936;
    private static NPC southThree = null; private static final int SOUTHTHREE = 7937;
    private static NPC eastOne = null; private static final int EASTONE = 7657;
    private static NPC eastTwo = null; private static final int EASTTWO = 7664;
    private static NPC eastThree = null; private static final int EASTTHREE = 7657;
    private static NPC westOne = null; private static final int WESTONE = 7262;
    private static NPC westTwo = null; private static final int WESTTWO = 7259;
    private static NPC miniOne = null; private static final int MINIONE = 6118;
    private static NPC miniTwo = null; private static final int MINITWO = 600;
    private static NPC miniThree = null; private static final int MINITHREE = 5079;
    private static NPC miniFour = null; private static final int MINIFOUR = 3616;

    public static GameObject portal;
    private static final int PORTAL = 43767;

    private static final int EVENT_DURATION = 600 * 1000; //10 minutes
    private static final int BOSS_DURATION = 300 * 1000; //5 minutes
    private static long gameStartTime;
    private static long bossStartTime;

    public TimeRiftEvent(Bounds bounds) {
        this.bounds = bounds;
        MapListener.registerBounds(bounds)
                .onEnter(player -> {
                    if (bounds == ACTIVE.bounds && eventStarted && phaseOne) {
                        playersInZone.add(player);
                        TimeRift.assignTask(player);
                        updateTimeRemaining(player);
                        IndividualTasks.updateTimeRemaining(player);
                        player.openInterface(InterfaceType.TARGET_OVERLAY, 822);
                        player.closeInterface(InterfaceType.MAIN);
                        player.getPacketSender().sendString(822, 132, "Time Rift");
                        player.getPacketSender().sendString(822, 133, "Team Shards:");
                        if (player.soloTasks[0] == null && player.teamTasks[0] == null) {
                            player.getPacketSender().sendString(822, 134, "No Task");
                        }
                        player.getPacketSender().sendString(822, 135, "    "+teamShardsCollected+"/750");
                    }
                    if (phaseTwo) {
                        updateTimeRemaining(player);
                        player.sendMessage("" + Color.ORANGE_RED.wrap("<shad>A portal to an Alternate Reality has opened in the center of the island!"));
                    }
                })
                .onExit(this::onExit);
    }

    private void onExit(Player p, boolean logout) {
        if (!logout && eventStarted) {
            TimeRift.leaveArea(p);
            playersInZone.remove(p);
            p.closeInterface(InterfaceType.TARGET_OVERLAY);
        }
    }

    /*
     * Event
     */
    static {
        World.startEvent(e -> {
            if (eventActive) {
                while (true) {
                    TimeRiftEvent next = Random.get(HOTSPOTS);
                    if (next == ACTIVE) {
                        e.delay(1);
                        continue;
                    }
                    e.delay(120 * 100);
                    ACTIVE = next;
                    eventStarted = true;
                    phaseOne = true;
                    gameStartTime = System.currentTimeMillis();
                    spawnNpcs();
                    String eventMessage = "A Time Rift has started! Use ::rift to travel to the event";
                    Broadcast.WORLD.sendNews(""+eventMessage);
                    broadcastEvent(eventMessage);

                    e.delay(10 * 100);
                    phaseTwo = true;
                    playersInZone.forEach(TimeRiftEvent::updateTimeRemaining);
                    String phaseonemsg = "A portal to an Alternate Reality has opened in the center of the island!";
                    Broadcast.WORLD.sendNews(""+phaseonemsg);
                    bossStartTime = System.currentTimeMillis();
                    broadcastEvent(phaseonemsg);

                    e.delay(120 * 100);
                    String endMessage = "The Time Rift event has ended!";
                    playersInZone.forEach(TimeRiftEvent::rewards);
                    playersInZone.forEach(TimeRiftEvent::updateTimeRemaining);
                    removeNpcs();
                    teamShardsCollected = 0;
                    eventStarted = false;
                    phaseOne = false;
                    phaseTwo = false;
                    bossDead = false;
                    Broadcast.WORLD.sendNews(""+endMessage);
                    broadcastEvent(""+endMessage);
                }
            }
        });
        ObjectAction.register(41808, 1, (player, obj) -> {
            clickBossPortal(player);
        });
    }

    private static void spawnNpcs() {
        bossDead = false;
        portal = GameObject.spawn(PORTAL, 2199, 2964, 0, 10, 0); //North
        portal = GameObject.spawn(PORTAL, 2211, 2824, 0, 10, 0); //South
        portal = GameObject.spawn(PORTAL, 2287, 2898, 0, 10, 0); //East
        portal = GameObject.spawn(PORTAL, 2127, 2893, 0, 10, 0); //West

        northOne = new NPC(NORTHONE).spawn(2195, 2962, 0, 1);
        northTwo = new NPC(NORTHTWO).spawn(2202, 2964, 0, 1);

//        for (NPC npc4 : Arrays.asList(northOne, northTwo)) {
//            npc4.getDef().combatInfo.respawn_ticks = 25;
//        }

        southOne = new NPC(SOUTHONE).spawn(2209, 2822, 0, 1);
        southTwo = new NPC(SOUTHTWO).spawn(2215, 2821, 0, 1);
        southThree = new NPC(SOUTHTHREE).spawn(2207, 2819, 0, 1);

//        for (NPC npc3 : Arrays.asList(southOne, southTwo, southThree)) {
//            npc3.getDef().combatInfo.respawn_ticks = 25;
//        }

        eastOne = new NPC(EASTONE).spawn(2289, 2899, 0, 1);
        eastTwo = new NPC(EASTTWO).spawn(2286, 2896, 0, 1);
        eastThree = new NPC(EASTTHREE).spawn(2285, 2902, 0, 1);

//        for (NPC npc2 : Arrays.asList(eastOne, eastTwo, eastThree)) {
//            npc2.getDef().combatInfo.respawn_ticks = 25;
//        }

        westOne = new NPC(WESTONE).spawn(2124, 2888, 0, 1);
        westTwo = new NPC(WESTTWO).spawn(2127, 2895, 0, 1);

//        for (NPC npc1 : Arrays.asList(westOne, westTwo)) {
//            npc1.getDef().combatInfo.respawn_ticks = 25;
//        }

        miniOne = new NPC(MINIONE).spawn(2194, 2842, 0);
        miniTwo = new NPC(MINITWO).spawn(2226, 2842, 0);
        miniThree = new NPC(MINITHREE).spawn(2287, 2931, 0);
        miniFour = new NPC(MINIFOUR).spawn(2127, 2921, 0);

//        for (NPC npc : Arrays.asList(miniOne, miniTwo, miniThree, miniFour))
//            npc.getDef().combatInfo.respawn_ticks = 50;

    }

    private static void removeNpcs() {
        northOne.remove();
        northTwo.remove();

        southOne.remove();
        southTwo.remove();
        southThree.remove();

        eastOne.remove();
        eastTwo.remove();
        eastThree.remove();

        westOne.remove();
        westTwo.remove();

        miniOne.remove();
        miniTwo.remove();
        miniThree.remove();
        miniFour.remove();

    }

    public static void clickBossPortal(Player p) {
        if (!eventActive || !phaseTwo) {
            p.sendMessage("This portal is not currently active.");
        } else {
            if (bossDead) {
                p.dialogue(
                        new MessageDialogue("The boss has already been defeated!")
                );
            }
            p.dialogue(
                    new MessageDialogue("Defeat the boss before time runs out!")
            );
            p.getMovement().teleport(2836, 4581, 0);
            p.openInterface(InterfaceType.TARGET_OVERLAY, 822);
            p.closeInterface(InterfaceType.MAIN);
            p.getPacketSender().sendString(822, 132, "Time Rift");
            p.getPacketSender().sendString(822, 133, "Team Shards:");
            if (p.soloTasks[0] == null && p.teamTasks[0] == null) {
                p.getPacketSender().sendString(822, 134, "No Task");
            }
            p.getPacketSender().sendString(822, 135, "    "+teamShardsCollected+"/750");

        }
    }

    private static void broadcastEvent(String eventMessage) {
        for(Player p : World.players) {
            if(p.broadcastHoldTheFort)
                p.getPacketSender().sendMessage(eventMessage, "", 14);
        }
    }

    public static long getElapsedTime() {
        if (phaseTwo) {
            return System.currentTimeMillis() - bossStartTime;
        }
        return System.currentTimeMillis() - gameStartTime;
    }

    public static int getTimeRemaining() {
        long elapsedTime = getElapsedTime();
        long remainingTime;
        if (phaseTwo) {
            remainingTime = BOSS_DURATION - elapsedTime;
        } else {
            remainingTime = EVENT_DURATION - elapsedTime;
        }
        return (int) (remainingTime / 1000);
    }


    public static void updateTimeRemaining(Player p) {
        int timeRemaining = getTimeRemaining();
        if (p.getPosition().inBounds(ACTIVE.bounds) && eventStarted) {
            p.getPacketSender().sendWidget(Widget.TIME_RIFT, timeRemaining);
        }
        if (phaseTwo && eventStarted) {
            p.getPacketSender().sendWidget(Widget.TIME_RIFT, timeRemaining);
            p.getPacketSender().sendHintIcon(2206, 2911);
        }
    }

    public static void rewards(Player p) {
        if (bossDead) {
            if (teamShardsCollected >= 250 && teamShardsCollected < 500) {
                p.getInventory().addOrDrop(6199, 1);
            }
            if (teamShardsCollected >= 500 && teamShardsCollected < 750) {
                p.getInventory().addOrDrop(4151, 1);
            }
            if (teamShardsCollected >= 750) {
                p.getInventory().addOrDrop(25865, 1);
            }
        }
    }

}
