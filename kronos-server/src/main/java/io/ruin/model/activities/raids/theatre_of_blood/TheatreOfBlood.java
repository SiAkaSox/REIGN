package io.ruin.model.activities.raids.theatre_of_blood;

import io.ruin.Server;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.activities.raids.theatre_of_blood.boss.Passageways;
import io.ruin.model.activities.raids.theatre_of_blood.boss.maiden_of_sugadinti.MaidenOfSugadintiPlugin;
import io.ruin.model.activities.raids.theatre_of_blood.boss.nylocas_vasilias.NylocasVasiliasPlugin;
import io.ruin.model.activities.raids.theatre_of_blood.boss.pestilent_bloat.PestilentBloatPlugin;
import io.ruin.model.activities.raids.theatre_of_blood.boss.sotetseg.SotetsegPlugin;
import io.ruin.model.activities.raids.theatre_of_blood.boss.verzik_vitur.VerzikStage;
import io.ruin.model.activities.raids.theatre_of_blood.boss.verzik_vitur.VerzikViturPlugin;
import io.ruin.model.activities.raids.theatre_of_blood.boss.xarpus.XarpusPlugin;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceAction;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.map.*;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;
import io.ruin.utility.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.ruin.Server.STARTUP_THREAD;


public class TheatreOfBlood {

    private DynamicMap map[];

    private Position base[];

    private Shape[] rooms;

    private TheatreParty party;

    private List<TheatreNPC> nodes = new ArrayList<TheatreNPC>();

    public MaidenOfSugadintiPlugin maiden;

    public PestilentBloatPlugin bloat;

    public NylocasVasiliasPlugin nylocas;

    public SotetsegPlugin sotetseg;

    public XarpusPlugin xarpus;

    public VerzikViturPlugin verzik;

    public Position[] CHEST_SPOTS;

    public GameObject[] CHESTS;

    private Position[] death_tiles;

    public boolean[] deaths;

    private long[] delta;

    private boolean started;

    public TheatreOfBlood(TheatreParty party) {
        this.party = party;
        this.map = new DynamicMap[4];
        this.base = new Position[4];
        deaths = new boolean[party.members.size()];
        constructMap();
        rooms = new Shape[]{new Shape(getBase()[0].translated(128, 64, 0), getBase()[0].copy()),
                new Shape(getBase()[1].translated(64, 64, 0), getBase()[1].copy()),
                new Shape(getBase()[1].translated(128, 64, 0), getBase()[1].translated(64, 0, 0)),
                new Shape(getBase()[2].translated(64, 64, 0), getBase()[2].copy()),
                new Shape(getBase()[2].translated(128, 64, 1), getBase()[2].translated(64, 0, 1)),
                new Shape(getBase()[3].translated(64, 64, 0), getBase()[3].copy())};
        death_tiles = new Position[]{getBase()[0].translated(23, 15, 0), getBase()[1].translated(30, 18, 0),
                getBase()[1].translated(94, 43, 0), getBase()[2].translated(15, 7, 0),
                getBase()[2].translated(97, 23, 1),
                getBase()[3].translated(23, 13, 0)};
        deaths = new boolean[party.members.size()];
        party.getApplicants().clear();
        party.setTheatre(this);
        party.setDeaths(new int[party.members.size()]);
        delta = new long[7];
    }

    private void constructMap() {
        map[0] = new DynamicMap().buildSw(12613, 3).buildSe(12869, 3);
        map[1] = new DynamicMap().buildSw(13125, 3).buildSe(13122, 3);
        map[2] = new DynamicMap().buildSw(13123, 3).buildSe(12612, 3);
        map[3] = new DynamicMap().buildSw(12611, 3).buildSe(12867, 3);
        for (int i = 0; i < map.length; i++) {
            MultiZone.add(((map[i].swRegion.id >> 8) << 6), ((map[i].swRegion.id & 0xFF) << 6), 0);
            base[i] = new Position(((map[i].swRegion.id >> 8) << 6), ((map[i].swRegion.id & 0xFF) << 6));
        }
        Position spawn = base[0].translated(26, 28, 0);
        for (int x = spawn.getX(); x < spawn.getX() + 6; x++) {
            for (int y = spawn.getY(); y < spawn.getY() + 6; y++) {
                Tile.get(x, y, 0, true).flagUnmovable();
            }
        }
        maiden = new MaidenOfSugadintiPlugin(8360, spawn) {
            @Override
            public int hit(Hit... hits) {
                int dmg = super.hit(hits);
                if (getCombat().getStat(StatType.Hitpoints).currentLevel <= 0) {
                    delta[1] = System.currentTimeMillis();
                    for (GameObject o : getPosition().getRegion().getAllObjects()) {
                        if (o.component1() >= 32760 && o.component1() <= 32762) {
                            if (o.position().distance(this.base) < 20) {
                                o.remove();
                            }
                        }
                    }
                }
                return dmg;
            }
        };
        maiden.setNodes(nodes);
        maiden.register();
        bloat = new PestilentBloatPlugin(8359, base[1].translated(23, 36, 0)) {
            @Override
            public int hit(Hit... hits) {
                int dmg = super.hit(hits);
                if (getCombat().getStat(StatType.Hitpoints).currentLevel <= 0) {
                    delta[2] = System.currentTimeMillis();
                    for (GameObject o : getPosition().getRegion().getAllObjects()) {
                        if (o.component1() >= 32760 && o.component1() <= 32762) {
                            if (o.position().distance(this.base) < 20) {
                                o.remove();
                            }
                        }
                    }
                }
                return dmg;

            }
        };
        bloat.setNodes(nodes);
        bloat.register();
        nylocas = new NylocasVasiliasPlugin(8358, base[1].translated(94, 23, 0)) {
            @Override
            public int hit(Hit... hits) {
                int dmg = super.hit(hits);
                if (getCombat().getStat(StatType.Hitpoints).currentLevel <= 0) {
                    delta[3] = System.currentTimeMillis();
                    for (GameObject o : getPosition().getRegion().getAllObjects()) {
                        if (o.component1() >= 32760 && o.component1() <= 32762) {
                            if (o.position().distance(this.base) < 20) {
                                o.remove();
                            }
                        }
                    }
                }
                return dmg;
            }
        };
        nylocas.setNodes(nodes);
        nylocas.register();
        nylocas.startEvent(e -> {
            e.delay(3);
            for (GameObject o : nylocas.getPosition().getRegion().getAllObjects()) {
                if (o.id == 4909 && o.type == 2) {
                    o.remove();
                }
            }
        });
        sotetseg = new SotetsegPlugin(8388, base[2].translated(13, 38, 0)) {
            @Override
            public int hit(Hit... hits) {
                int dmg = super.hit(hits);
                if (getCombat().getStat(StatType.Hitpoints).currentLevel <= 0) {
                    delta[4] = System.currentTimeMillis();
                    for (GameObject o : getPosition().getRegion().getAllObjects()) {
                        if (o.component1() >= 32760 && o.component1() <= 32762) {
                            if (o.position().distance(this.base) < 20) {
                                o.remove();
                            }
                        }
                    }
                }
                return dmg;
            }
        };
        Position to = sotetseg.base.translated(0, -5, 0);
        sotetseg.face(to.getX(), to.getY());
        sotetseg.setNodes(nodes);
        sotetseg.register();
        xarpus = new XarpusPlugin(8338, base[2].translated(97, 34, 1)) {
            @Override
            public int hit(Hit... hits) {
                int dmg = super.hit(hits);
                if (getCombat().getStat(StatType.Hitpoints).currentLevel <= 0) {
                    delta[5] = System.currentTimeMillis();
                    for (GameObject o : getPosition().getRegion().getAllObjects()) {
                        if (o.component1() >= 32760 && o.component1() <= 32762) {
                            if (o.position().distance(this.base) < 20) {
                                o.remove();
                            }
                        }
                    }
                }
                return dmg;
            }
        };
        xarpus.setNodes(nodes);
        xarpus.register();
        verzik = new VerzikViturPlugin(8369, base[3].translated(30, 35, 0));
        verzik.setNodes(nodes);
        verzik.register();
        int[] chest_rotations = {0, 3, 1, 3, 1};
        CHEST_SPOTS = new Position[]{
                base[3].translated(97, 42, 0),
                base[3].translated(90, 37, 0),
                base[3].translated(105, 37, 0),
                base[3].translated(90, 34, 0),
                base[2].translated(105, 34, 0)
        };
        CHESTS = new GameObject[party.members.size()];
        for (int i = 0; i < CHESTS.length; i++) {
            CHESTS[i] = new GameObject(32990, CHEST_SPOTS[i], 10, chest_rotations[i]).spawn();
        }
        party.members.stream().forEach(player -> {
            player.startEvent(event -> {
                player.lock();
                event.delay(2);
                entered(player);
                assignListener(player);
//                player.getPacketSender().sendVarp(1042, 36672);
                player.getCombat().restore();
                player.getPacketSender().sendClientScript(2306, "iis", 255, 0, "The Theatre awaits...");
//                player.getPacketSender().sendClientScript(2306, "sii", "The Theatre awaits...", 255, 0);
                player.openInterface(InterfaceType.PRIMARY_OVERLAY, Interface.THEATRE_OF_BLOOD_LOBBY);
//                player.getPacketSender().sendVarp(1740, 6910101);
                event.delay(2);
//                player.getPacketSender().sendVarp(1745, 5);
//                player.getPacketSender().sendVarp(1745, 6);
//                player.getPacketSender().sendVarp(1745, 870);
//                player.getPacketSender().sendVarp(1021, 544);
//                player.getPacketSender().sendClientScript(2306, "sii", "The Maiden of Sugadinti", 0, 255);
//                player.getPacketSender().sendClientScript(2301, "sssss", "", "", "", "", "Vitarah");
                player.getMovement().teleport(base[0].translated(82, 40, 0));
                event.delay(2);
                player.unlock();
                if (!started) {
                    delta[0] = System.currentTimeMillis();
                    started = true;
                }
            });
        });

    }

    private void assignListener(Player player) {
        for (DynamicMap l : map) {
            l.assignListener(player).onEnter(this::entered).onExit(this::exited);
        }
    }

    private void entered(Player player) {
        player.deathEndListener = (entity, killer, killHit) -> handleDeath(player);
        player.teleportListener = TheatreOfBlood::allowTeleport;
        player.getNpcUpdater().setMaxDistance(32);
        player.setIgnoreMulti(true);
    }

    private void handleDeath(Player player) {
        player.getMovement().teleport(getDeathLocation(player));
        for (int i = 0; i < party.members.size(); i++) {
            if (player == party.members.get(i)) {
                deaths[i] = true;
                party.getDeaths()[i] = party.getDeaths()[i] + 1;
                break;
            }
        }
        boolean all = true;
        for (int i = 0; i < party.members.size(); i++) {
            if (!deaths[i]) {
                all = false;
            }
        }
        if (all) {
            for (Player p : party.members) {
                exited(p, false);
            }
        }
    }

    private Position getDeathLocation(Player player) {
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].inside(player.getPosition())) {
                return death_tiles[i];
            }
        }
        return base[0].translated(21, 6, 0);
    }

    private static boolean allowTeleport(Player player) {
        return false;
    }

    private void exited(Player player, boolean logout) {
        if (!started) return;
        player.getMovement().teleport(3676, 3219, 0);
        for (int i = 0; i < party.members.size(); i++) {
            if (player == party.members.get(i)) {
                deaths[i] = true;
                break;
            }
        }
        boolean all = true;
        for (int i = 0; i < party.members.size(); i++) {
            if (!deaths[i]) {
                all = false;
            }
        }
        player.getInventory().remove(22516, 28);
        player.getEquipment().remove(22516, 28);
        if (all) {
            for (Player p : World.players) {
                if (TheatrePartiesManager.getRaid(p.getPosition()) != null && TheatrePartiesManager.getRaid(p.getPosition()) == this) {
                    player.getMovement().teleport(3676, 3219, 0);
                }
            }
            try {
                for (DynamicMap m : map) {
                    m.destroy();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            party.setTheatre(null);
        }
    }

    static {

        MapListener.registerRegion(14642).onEnter(p -> {
            open(p, Interface.THEATRE_OF_BLOOD_LOBBY);
            p.getPacketSender().sendClientScript(2296);
            p.getPacketSender().sendClientScript(2306, "iis", 200, 0, "");
            p.logoutListener = new LogoutListener().onLogout(o -> {
                if (TheatrePartiesManager.getParty(p) != null) {
                    TheatreParty party = TheatrePartiesManager.getParty(p);
                    if (party.getLeader() == p) {
                        for (Player p2 : party.members) {
                            TheatrePartiesManager.getParties().remove(p2);
                            if (party.getTheatre() != null) {
                                party.getTheatre().exited(p2, false);
                            }
                            p2.getNpcUpdater().setMaxDistance(14);
                        }
                        party.members.clear();
                    } else {
                        if (party.getTheatre() != null) {
                            party.getTheatre().exited(p, false);
                        }
                        TheatrePartiesManager.getParties().remove(p);
                        party.members.remove(p);
                        for (Player p2 : party.members) {
                            updateLobby(p2);
                        }
                        updateLobby(p);
                    }
                }
            });
        }).onExit((p, p2) -> {
            if (TheatrePartiesManager.getParty(p) != null) {
                TheatreParty party = TheatrePartiesManager.getParty(p);
                if (party.getTheatre() != null && !party.getTheatre().withinBounds(p)) {
                    p.logoutListener = null;
                }
            } else {
                p.logoutListener = null;
            }
        });
        NPCAction.register(8369, "talk-to", new NPCAction() {

            @Override
            public void handle(Player player, NPC npc) {
                TheatreOfBlood theatre = TheatrePartiesManager.getRaid(player.getPosition());
                if (theatre != null) {
                    if (npc == theatre.verzik) {
                        if (theatre.verzik.getStage() == VerzikStage.UNDISRUPTED) {
                            player.dialogue(new NPCDialogue(theatre.verzik.getId(), "Now that was quite the show! I haven't been that entertained in a long time."),
                                    new NPCDialogue(theatre.verzik.getId(), "Of course, you know I can't let you leave here alive. Time for your final performance..."),
                                    new OptionsDialogue("Is your party ready to fight?",
                                            new Option("Yes, let's begin.", () -> {
                                                player.closeDialogue();
                                                if (theatre.delta[5] < 1) {
                                                    theatre.delta[5] = System.currentTimeMillis();
                                                }
                                                theatre.verzik.setStage(VerzikStage.FIGHT_SHIELD);
                                            }),
                                            new Option("No, don't start yet.", player::closeDialogue))
                            );
                        }
                    }
                }
            }
        });
        //TODO - Uncomment this to unlock entering the raid
        ObjectAction.register(32653, "enter", (player, obj) -> player.sendMessage("Theatre of Blood will be released in Week 2!"));
//        ObjectAction.register(32653, "enter", new ObjectAction() {
//            @Override
//            public void handle(Player player, GameObject object) {
//                TheatreParty party = TheatrePartiesManager.getParty(player);
//                if (party == null) {
//                    player.dialogue(new MessageDialogue("You are not in a party."));
//                    return;
//                }
//                TheatreParty finalParty = party;
//                player.dialogue(new OptionsDialogue("Select an Option",
//                        new Option("Start Raid.", () -> {
//                            if (!finalParty.getLeaderName().equalsIgnoreCase(player.name())) {
//                                player.dialogue(new MessageDialogue("Only the party leader can do this."));
//                                return;
//                            }
//                            for (Player p : finalParty.members) {
//                                if (Region.get(p.getPosition().getX(), p.getPosition().getY()) != Region.get(player.getPosition().getX(), player.getPosition().getY())) {
//                                    player.dialogue(new MessageDialogue("One or more of your party members has left the area."));
//                                    return;
//                                }
//                            }
//                            TheatreOfBlood theatre = new TheatreOfBlood(finalParty);
//                        }),
//                        new Option("Cancel.", player::closeDialogue))
//                );
//            }
//        });
        ObjectAction.register(32655, "read", new ObjectAction() {
            @Override
            public void handle(Player player, GameObject object) {
                open(player, Interface.THEATRE_OF_BLOOD_PARTIES);
            }
        });
        ObjectAction.register(37383, "use", new ObjectAction() {
            @Override
            public void handle(Player player, GameObject object) {
                open(player, Interface.THEATRE_RESOURCE_SHOP);
            }
        });
        ObjectAction.register(33113, "enter", new ObjectAction() {
            @Override
            public void handle(Player player, GameObject object) {
                TheatreOfBlood theatre = TheatrePartiesManager.getRaid(player.getPosition());
                if (theatre != null) {
                    TheatreParty party = theatre.party;
                    for (int i = 0; i < Passageways.values().length; i++) {
                        Passageways p = Passageways.values()[i];
                        if (object.position().distance(theatre.getBase()[p.getRoom()].translated(p.getLocDelta()[0], p.getLocDelta()[1], 0)) < 5) {
                            switch (Passageways.values()[i + 1]) {
                                case NYLOCAS_VASILIAS:
                                    if (!theatre.bloat.getCombat().isDead()) {
                                        player.dialogue(new MessageDialogue("The Pestilent Bloat must be slain before you may pass."));
                                        return;
                                    }
                                    break;
                                case PESTILENT_BLOAT:
                                    if (!theatre.maiden.getCombat().isDead()) {
                                        player.dialogue(new MessageDialogue("The Maiden must be slain before you may pass."));
                                        return;
                                    }
                                    break;
                                case SOTETSEG:
                                    if (!theatre.nylocas.getCombat().isDead()) {
                                        player.dialogue(new MessageDialogue("The Nylocas must be defeated before you may pass."));
                                        return;
                                    }
                                    break;
                                case XARPUS:
                                    if (!theatre.sotetseg.getCombat().isDead()) {
                                        player.dialogue(new MessageDialogue("Sotetseg must be slain before you may pass."));
                                        return;
                                    }
                                    break;
                                default:
                                    break;
                            }
                            for (int j = 0; j < party.members.size(); j++) {
                                if (theatre.deaths[j]) {
                                    if (party.members.get(j) != null) {
                                        party.members.get(j).getMovement().teleport(player.getPosition().copy());
                                        party.members.get(j).getCombat().restore();
                                    }
                                    theatre.deaths[j] = false;
                                }
                            }
                            theatre.party.resetPoints(player);
                            theatre.delta[i + 1] = System.currentTimeMillis();
                            player.getMovement().teleport(theatre.base[p.getBoss()].translated(p.getDestDelta()[0], p.getDestDelta()[1], p.getDestDelta()[2]));
                        }
                    }
                }
            }
        });
        ObjectAction.register(32996, "use", new ObjectAction() {
            @Override
            public void handle(Player player, GameObject object) {
                TheatreOfBlood theatre = TheatrePartiesManager.getRaid(player.getPosition());
                if (theatre != null) {
                    theatre.exited(player, false);
                }
            }
        });
        ObjectAction.register(32990, "open", new ObjectAction() {
            @Override
            public void handle(Player player, GameObject object) {
                TheatreParty party = TheatrePartiesManager.getParty(player);
                if (party != null) {
                    if (party.getRewardPoints(player) <= 0) {
                        player.dialogue(new MessageDialogue("You do not have any rewards to claim."));
                        return;
                    }
                    open(player, Interface.THEATRE_OF_BLOOD_REWARDS);
                }
            }
        });
        ObjectAction.register(12351, "Pass-through", new ObjectAction() {
            @Override
            public void handle(Player player, GameObject object) {
                boolean died = false;
                TheatreParty party = TheatrePartiesManager.getParty(player);
                if (party != null) {
                    TheatreOfBlood theatre = TheatrePartiesManager.getRaid(player.getPosition());
                    if (theatre != null) {
                        for (int i = 0; i < party.members.size(); i++) {
                            if (player == party.members.get(i)) {
                                if (theatre.deaths[i]) {
                                    died = true;
                                }
                                break;
                            }
                        }
                    }
                    if (died) {
                        player.dialogue(new MessageDialogue("You cannot do this as you are dead."));
                        return;
                    }
                    int xDiff = 0, yDiff = 0;
                    switch (object.direction) {
                        case 1:
                            yDiff = object.position().getY() < player.getPosition().getY() ? +2 : 2;
                            break;
                        case 0:
                            xDiff = object.position().getX() < player.getPosition().getX() ? -2 : 2;
                            break;
                        default:
                            yDiff = object.position().getY() < player.getPosition().getY() ? -2 : 2;
                            break;
                    }
                    Position dest = player.getPosition().translated(xDiff, yDiff, 0);
                    player.getMovement().teleport(dest.getX(), dest.getY(), dest.getZ());
                }
            }
        });
        ObjectAction.register(32738, "enter", new ObjectAction() {
            @Override
            public void handle(Player player, GameObject object) {
                TheatreOfBlood theatre = TheatrePartiesManager.getRaid(player.getPosition());
                if (theatre != null) {
                    player.getMovement().teleport(theatre.base[3].translated(101, 19, 0));
                    player.startEvent(event -> {
                        event.delay(2);
                        player.getPacketSender().sendClientScript(2306, "sii", "Verzik Vitur's Vault", 0, 355);
                        for (int i = 0; i < theatre.party.members.size(); i++) {
                            if (theatre.party.members.get(i) == player) {
                                int[] chest_rotations = {0, 3, 1, 3, 1};
                                player.getPacketSender().sendCreateObject(32992, theatre.CHEST_SPOTS[i].getX(), theatre.CHEST_SPOTS[i].getY(), 0, 10, chest_rotations[i]);
                            }
                        }
                    });
                }

            }
        });
        ObjectAction.register(33014, "read", new ObjectAction() {
            @Override
            public void handle(Player player, GameObject object) {
                open(player, Interface.THEATRE_OF_BLOOD_PERFORMANCE_DETAILS);
            }
        });
        ObjectAction.register(32741, "search", new ObjectAction() {
            @Override
            public void handle(Player player, GameObject object) {
                if (player.getInventory().hasFreeSlots(1) && TheatrePartiesManager.getRaid(player.getPosition()) != null) {
                    player.getInventory().add(22516, 1);
                    object.setId(32742);
                }
            }
        });
        ObjectAction.register(32751, "enter", new ObjectAction() {
            @Override
            public void handle(Player player, GameObject object) {
                TheatreOfBlood theatre = TheatrePartiesManager.getRaid(player.getPosition());
                if (theatre != null) {
                    if (!theatre.xarpus.getCombat().isDead()) {
                        player.dialogue(new MessageDialogue("Xarpus must be slain before you may pass."));
                        return;
                    }
                    player.dialogue(new OptionsDialogue("Are you ready to proceed?",
                            new Option("Yes, I'm ready.", () -> {
                                Passageways p = Passageways.XARPUS;
                                player.getMovement().teleport(theatre.base[3].translated(p.getDestDelta()[0], p.getDestDelta()[1], p.getDestDelta()[2]));
                                theatre.verzik.setStarted(true);
                                player.startEvent(e -> {
                                    e.delay(1);
                                    player.lock();
                                    player.animate(819);
                                    player.getMovement().force(0, 5, 0, 0, 10, 30, Direction.NORTH);
                                    e.delay(6);
                                    player.unlock();
                                });
                            }),
                            new Option("No - Stay here.", player::closeDialogue))
                    );
                }
            }
        });
        InterfaceHandler.register(Interface.THEATRE_OF_BLOOD_PARTY, c -> {
            c.actions[0] = new InterfaceAction() {

                @Override
                public void handleClick(Player player, int child, int option, int slot, int itemId) {
                    switch (slot) {
                        case 0:
                            player.closeInterface(InterfaceType.MAIN);
                            open(player, Interface.THEATRE_OF_BLOOD_PARTIES);
                            break;
                        case 1:
                            player.closeInterface(InterfaceType.MAIN);
                            open(player, Interface.THEATRE_OF_BLOOD_PARTY);
                            break;
                        case 2: {
                            TheatreParty party = player.get("viewing_party") != null ? player.remove("viewing_party") : TheatrePartiesManager.getParty(player);
                            if (party == null) {
                                return;
                            }
                            if (party.getLeaderName() != player.name()) {
                                player.dialogue(new MessageDialogue("Only the leader can do this."));
                                return;
                            }
                            player.dialogue(new MessageDialogue("There no bans issues at this time."));
                            break;
                        }
                        case 3: {
                            TheatreParty party = player.get("viewing_party") != null ? player.remove("viewing_party") : TheatrePartiesManager.getParty(player);
                            if (party == null) {
                                return;
                            }
                            if (party.getLeaderName() != player.name()) {
                                player.dialogue(new MessageDialogue("Only the leader can do this."));
                                return;
                            }
                            Config config = Config.THEATRE_PREFERRED_PARTY_SIZE;
                            player.integerInput("Set a preferred size (or 0 to clear it):", amt -> {
                                config.set(player, 0 > amt ? 0 : Math.min(5, amt));
                                party.members.forEach(member -> config.set(member, 0 > amt ? 0 : Math.min(5, amt)));
                            });
                            break;
                        }
                        case 4: {
                            TheatreParty party = player.get("viewing_party") != null ? player.remove("viewing_party") : TheatrePartiesManager.getParty(player);
                            if (party == null) {
                                return;
                            }
                            if (party.getLeaderName() != player.name()) {
                                player.dialogue(new MessageDialogue("Only the leader can do this."));
                                return;
                            }
                            Config config = Config.THEATRE_PREFERRED_TOTAL_LEVEL;
                            player.integerInput("Set a preferred total level (or 0 to clear it):", amt -> {
                                config.set(player, 0 > amt ? 0 : Math.min(2277, amt));
                                party.members.forEach(member -> config.set(member, 0 > amt ? 0 : Math.min(2277, amt)));
                            });
                            break;
                        }
                        case 5: {
                            TheatreParty party = player.get("viewing_party") != null ? player.remove("viewing_party") : TheatrePartiesManager.getParty(player);
                            if (party == null) {
                                return;
                            }
                            if (party.getTheatre() != null && party.getTheatre().getPlayersCount() > 0) {
                                player.dialogue(new MessageDialogue("This party has already started raiding."));
                                return;
                            }
                            if (!party.members.contains(player)) {
                                if (TheatrePartiesManager.getMyParty(player) != null) {
                                    player.dialogue(new MessageDialogue("You are already in your own party."));
                                    return;
                                }
                                if (!party.getApplicants().contains(player)) {
                                    party.getApplicants().add(player);
                                    player.dialogue(new MessageDialogue("Your application was successful."));
                                    for (Player p : party.members) {
                                        open(p, Interface.THEATRE_OF_BLOOD_PARTY);
                                    }
                                    return;
                                } else {
                                    player.dialogue(new MessageDialogue("You have already applied to be a part of this team."));
                                }
                            } else if (party.getLeaderName().equalsIgnoreCase(player.name())) {
                                player.dialogue(new OptionsDialogue("Are you sure you wish to quit?",
                                        new Option("Yes, disband my party.", () -> {
                                            player.closeDialogue();
                                            for (Player p : party.members) {
                                                TheatrePartiesManager.getParties().remove(p);
                                                if (party.getTheatre() != null) {
                                                    party.getTheatre().exited(p, false);
                                                }
                                            }
                                            List<Player> m = new ArrayList<>();
                                            Collections.copy(m, party.members);
                                            party.members.clear();
                                            for (Player p : m) {
                                                updateLobby(p);
                                            }
                                        }),
                                        new Option("Nevermind.", player::closeDialogue)));
                            } else {
                                player.dialogue(new OptionsDialogue("Are you sure you wish to leave?",
                                        new Option("Yes, leave this party.", () -> {
                                            player.closeDialogue();
                                            TheatrePartiesManager.getParties().remove(player);
                                            party.members.remove(player);
                                            for (Player p : party.members) {
                                                updateLobby(p);
                                            }
                                            updateLobby(player);
                                        }),
                                        new Option("Nevermind.", player::closeDialogue)));
                            }
                            break;
                        }
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10: {
                            TheatreParty party = TheatrePartiesManager.getParty(player);
                            if (party != null) {
                                if (party.getLeaderName() != player.name()) {
                                    player.dialogue(new MessageDialogue("Only the leader can do this."));
                                    return;
                                }
                                if (slot - 6 == 0) {
                                    player.dialogue(new OptionsDialogue("Are you sure you wish to quit?",
                                            new Option("Yes, disband my party.", () -> {
                                                for (Player p : party.members) {
                                                    TheatrePartiesManager.getParties().remove(p);
                                                    if (party.getTheatre() != null) {
                                                        party.getTheatre().exited(p, false);
                                                    }
                                                }
                                                party.members.clear();
                                                player.closeInterface(InterfaceType.MAIN);
                                            }),
                                            new Option("Nevermind.", player::closeDialogue)));
                                } else {
                                    Player target = party.members.get(slot - 6);
                                    if (target != null) {
                                        player.dialogue(new MessageDialogue("You have kicked " + target.name() + " from your party."));
                                        party.members.remove(target);
                                        player.closeInterface(InterfaceType.MAIN);
                                        TheatreOfBlood.open(player, Interface.THEATRE_OF_BLOOD_PARTY);
                                    }
                                    for (Player p : party.members) {
                                        updateLobby(p);
                                    }
                                    updateLobby(target);
                                }
                            }
                            break;
                        }
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16: {
                            TheatreParty party = TheatrePartiesManager.getMyParty(player);
                            if (party != null) {
                                if (party.getLeaderName().equalsIgnoreCase(player.name())) {
                                    boolean accept = option == 1;
                                    Player applicant = party.getApplicants().get(slot - 12);
                                    party.getApplicants().remove(applicant);
                                    if (TheatrePartiesManager.getMyParty(applicant) != null) {
                                        player.dialogue(new MessageDialogue(applicant.name() + " has their own party, application unsuccessful."));
                                        return;
                                    }
                                    if (accept) {
                                        party.members.add(applicant);
                                    }
                                    for (Player p : party.members) {
                                        if (p.isVisibleInterface(Interface.THEATRE_OF_BLOOD_PARTY)) {
                                            p.closeInterface(InterfaceType.MAIN);
                                            open(p, Interface.THEATRE_OF_BLOOD_PARTY);
                                        }
                                        updateLobby(p);
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            };
        });
        InterfaceHandler.register(Interface.THEATRE_OF_BLOOD_PARTIES, c -> {
            for (int i = 16; i <= 62; i++) {
                int finalI = i;
                c.actions[i] = new InterfaceAction() {
                    @Override
                    public void handleClick(Player player, int child, int option, int slot, int itemId) {
                        player.set("viewing_party", getParties()[slot]);
                        open(player, Interface.THEATRE_OF_BLOOD_PARTY);
                    }
                };
            }
            c.actions[3] = new InterfaceAction() {
                @Override
                public void handleClick(Player player, int child, int option, int slot, int itemId) {
                    if (slot == 1) {
                        TheatreParty party = TheatrePartiesManager.getParty(player);
                        if (party == null) {
                            TheatrePartiesManager.createParty(player);
                        }
                        open(player, Interface.THEATRE_OF_BLOOD_PARTY);
                    } else {
                        open(player, Interface.THEATRE_OF_BLOOD_PARTIES);
                        TheatreParty party = TheatrePartiesManager.getParty(player);
                        if (party == null) {
                            player.sendMessage("You don't have a party.");
                            return;
                        }
                    }

                }
            };
            c.actions[63] = new InterfaceAction() {
                @Override
                public void handleClick(Player player, int child, int option, int slot, int itemId) {
                    open(player, Interface.THEATRE_OF_BLOOD_PARTIES);
                }
            };
        });
        InterfaceHandler.register(Interface.THEATRE_RESOURCE_SHOP, c -> {

            c.actions[8] = new InterfaceAction() {
                @Override
                public void handleClick(Player player, int child, int option, int slot, int itemId) {
                    Resources resource = Resources.values()[slot];
                    switch (option) {
                        case 1:
                            player.sendMessage(ItemDef.get(resource.model).name + " costs " + resource.price + " points.");
                            break;
                        case 2:
                        case 3: {
                            TheatreParty party = TheatrePartiesManager.getParty(player);
                            int available = party == null || party.getPoints(player) <= 0 ? 0 : party.getPoints(player) / resource.price, amount = option == 2 ? 1 : available;
                            if (available == 0) {
                                player.sendMessage("You don't have enough points.");
                                return;
                            }
                            party.removePoints(player, amount * resource.price);
                            Config.THEATRE_RESOURCE_POINTS.set(player, party.getPoints(player));
                            player.getInventory().addOrDrop(new Item(resource.model, amount));
                            break;
                        }
                        case 4: {
                            player.integerInput("How many would you like to buy?", amt -> {
                                TheatreParty party = TheatrePartiesManager.getParty(player);
                                int available = party == null || party.getPoints(player) <= 0 ? 0 : party.getPoints(player) / resource.price, amount = amt > available ? available : amt;
                                if (available == 0 || amount == 0 || party.getPoints(player) < amt * resource.price) {
                                    player.sendMessage("You don't have enough points.");
                                    return;
                                }
                                party.removePoints(player, amount * resource.price);
                                Config.THEATRE_RESOURCE_POINTS.set(player, party.getPoints(player));
                                player.getInventory().addOrDrop(new Item(resource.model, amount));
                            });
                            break;
                        }
                        case 10:
                            player.sendMessage(ItemDef.get(resource.model).examine);
                            break;
                    }
                }
            };
        });
    }

    private int getPlayersCount() {
        int count = 0;
        for (Player p : World.players) {
            if (withinBounds(p)) count++;
        }
        return count;
    }

    private boolean withinBounds(Player p) {
        for (Shape s : rooms) if (s.inside(p.getPosition())) return true;
        return false;
    }

    public static void open(Player player, int inter) {
        switch (inter) {
            case Interface.THEATRE_RESOURCE_SHOP: {
                player.openInterface(InterfaceType.MAIN, inter);
                player.getPacketSender().sendAccessMask(26542088 >> 16, 26542088 & 0xffff, 0, 7, 1054);
                player.getPacketSender().sendAccessMask(26542083 >> 16, 26542083 & 0xffff, -1, -1, 1026);
                TheatreParty party = TheatrePartiesManager.getParty(player);
                if (party != null) {
                    Config.THEATRE_RESOURCE_POINTS.set(player, party.getPoints(player));
                }
                break;
            }
            case Interface.THEATRE_OF_BLOOD_PARTIES: {
                player.openInterface(InterfaceType.MAIN, inter);
                player.getPacketSender().sendClientScript(2524, "ii", -1, -1);
                for (int i = 0; i < 45; i++) {
                    TheatreParty p = getParties()[i];
                    if (p == null) {
                        player.getPacketSender().sendClientScript(2340, "si", "", i);
                    } else {
                        player.getPacketSender().sendClientScript(2340, "si", "<col=ffffff>" + p.getLeaderName() + "</col>|" + p.members.size() + "|0|0|" + p.getAge(), i);
                    }
                }
                player.getPacketSender().sendAccessMask(Interface.THEATRE_OF_BLOOD_PARTIES, 3, 0, 1, 1);
                player.getPacketSender().sendAccessMask(Interface.THEATRE_OF_BLOOD_PARTIES, 16, 0, 44, 1);
                break;
            }
            case Interface.THEATRE_OF_BLOOD_PARTY: {
                player.openInterface(InterfaceType.MAIN, inter);
                TheatreParty party = player.get("viewing_party") != null ? player.get("viewing_party") : TheatrePartiesManager.getParty(player);
                if (party == null) {
                    return;
                }
                for (Player p : party.members) {
                    updateLobby(p);
                }
                for (int i = 0; i < 5; i++) {
                    Player p = party.members.size() > i ? party.members.get(i) : null;
                    if (p == null) {
                        player.getPacketSender().sendClientScript(2317, "si", "", 2);
                    } else {
                        player.getPacketSender().sendClientScript(2317, "si", p.name() + "|" + p.getCombat().getLevel() + "|" + player.getStats().get(StatType.Attack).fixedLevel + "|" + p.getStats().get(StatType.Strength).fixedLevel + "|" + p.getStats().get(StatType.Ranged).fixedLevel + "|" + p.getStats().get(StatType.Magic).fixedLevel + "|" + p.getStats().get(StatType.Defence).fixedLevel + "|" + p.getStats().get(StatType.Hitpoints).fixedLevel + "|" + p.getStats().get(StatType.Prayer).fixedLevel + "|0", 2);
                    }
                }
                for (Player p : party.getApplicants()) {
                    player.getPacketSender().sendClientScript(2321, "s", p.name() + "|121|98|99|98|96|93|99|77|0");
                }
                player.getPacketSender().sendClientScript(2323, "iii", !party.members.contains(player) ? 0 : party.getLeaderName().equalsIgnoreCase(player.name()) ? 2 : 1, 0, 0);
                player.getPacketSender().sendAccessMask(Interface.THEATRE_OF_BLOOD_PARTY, 0, 0, 30, 1);

                break;
            }
            case Interface.THEATRE_OF_BLOOD_LOBBY: {
                player.openInterface(InterfaceType.PRIMARY_OVERLAY, inter);
                player.getPacketSender().sendClientScript(2306, "sii", "Welcome.. to the Theatre", 200, 0);
                updateLobby(player);
                break;
            }
            case Interface.THEATRE_OF_BLOOD_PERFORMANCE_DETAILS: {
                player.openInterface(InterfaceType.MAIN, inter);
                TheatreParty party = TheatrePartiesManager.getParty(player);
                if (party == null || true) {
                    return;
                }
                player.getPacketSender().sendString(inter, 3, "Death Counts");
                int idx = 0, totalDeaths = 0;
                Player mvp = null;
                int maxPoints = 0;
                for (Player p : party.members) {
                    if (party.getPoints(p) > maxPoints) {
                        mvp = player;
                        maxPoints = party.getPoints(p);
                    }
                    totalDeaths += party.getDeaths()[idx];
                    player.getPacketSender().sendString(inter, 22 + (idx * 2), p.name());
                    player.getPacketSender().sendString(inter, 23 + (idx * 2), party.getDeaths()[idx] + "");
                    ++idx;
                }
                player.getPacketSender().sendString(inter, 32, "Total Deaths");
                player.getPacketSender().sendString(inter, 33, totalDeaths + "");
                player.getPacketSender().sendString(inter, 13, "MVP");
                player.getPacketSender().sendString(inter, 14, mvp == null ? "-" : mvp.name());

                TheatreOfBlood theatre = party.getTheatre();
                player.getPacketSender().sendString(inter, 10, "Challenge Time");
                player.getPacketSender().sendString(inter, 44, "The Maiden of Sugadinti");
                player.getPacketSender().sendString(inter, 46, "Pestilent Bloat");
                player.getPacketSender().sendString(inter, 48, "The Nylocas");
                player.getPacketSender().sendString(inter, 50, "Sotetseg");
                player.getPacketSender().sendString(inter, 52, "Xarpus");
                player.getPacketSender().sendString(inter, 54, "The Final Challenge");
                if (theatre != null) {
                    player.getPacketSender().sendString(inter, 11, formatTime(theatre.getDelta()[6] - theatre.getDelta()[5]));
                    player.getPacketSender().sendString(inter, 36, formatTime(theatre.getDelta()[6] - theatre.getDelta()[0]));
                    player.getPacketSender().sendString(inter, 45, formatTime(theatre.getDelta()[1] - theatre.getDelta()[0]));
                    player.getPacketSender().sendString(inter, 47, formatTime(theatre.getDelta()[2] - theatre.getDelta()[1]));
                    player.getPacketSender().sendString(inter, 49, formatTime(theatre.getDelta()[3] - theatre.getDelta()[2]));
                    player.getPacketSender().sendString(inter, 51, formatTime(theatre.getDelta()[4] - theatre.getDelta()[3]));
                    player.getPacketSender().sendString(inter, 53, formatTime(theatre.getDelta()[5] - theatre.getDelta()[4]));
                    player.getPacketSender().sendString(inter, 55, formatTime(theatre.getDelta()[6] - theatre.getDelta()[5]));
                    player.getPacketSender().sendString(inter, 536, formatTime(theatre.getDelta()[0] - theatre.getDelta()[6]));
                }
                player.getPacketSender().sendString(inter, 35, "Overall Time");
                break;
            }

            case Interface.THEATRE_OF_BLOOD_REWARDS: {
                player.openInterface(InterfaceType.MAIN, inter);

                TheatreParty party = TheatrePartiesManager.getParty(player);
                if (party == null) {
                    player.sendMessage("Unable to determine points, your party was not found!");
                    return;
                }
                ItemContainer rewards = Rewards.getRewards(player);
                for (Item item : rewards.getItems()) {
                    if (item == null) {
                        continue;
                    }
                    player.getInventory().addOrDrop(item);
                    player.getCollectionLog().checkItemReceived(item);
                }
                party.removeRewardPoints(player, party.getRewardPoints(player));
                player.sendMessage("Your Theatre Of Blood count is: <col=c4260e>" + (++player.tobKC) + "</col>.");
                break;
            }
        }
    }

    private static String formatTime(long time) {
        long seconds = time / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        seconds = seconds % 60;
        minutes = minutes % 60;
        hours = hours % 24;

        StringBuilder string = new StringBuilder();
        string.append(days > 9 ? days : ("0" + days));
        string.append(":" + (hours > 9 ? hours : ("0" + hours)));
        string.append(":" + (minutes > 9 ? minutes : ("0" + minutes)));
        string.append(":" + (seconds > 9 ? seconds : ("0" + seconds)));
        return string.toString();
    }

    private static void updateLobby(Player player) {
        TheatreParty party = TheatrePartiesManager.getParty(player);
        if (party == null) {
            Config.THEATRE_LOBBY_IN_PARTY.set(player, 0); //NO PARTY
            player.getPacketSender().sendString(Interface.THEATRE_OF_BLOOD_LOBBY, 9, "-<br>-<br>-<br>-<br>-");
            return;
        }
        StringBuilder names = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            Player p = party.members.size() > i ? party.members.get(i) : null;
            names.append((p == null ? "-" : p.name()) + "<br>");
        }
        player.getPacketSender().sendString(Interface.THEATRE_OF_BLOOD_LOBBY, 9, names.toString());
        Config.THEATRE_LOBBY_IN_PARTY.set(player, 1); //PARTY
        if (party.members.size() == 5) {
            Config.THEATRE_LOBBY_IN_PARTY.set(player, 2); //STEP INSIDE NOW
        }
    }

    private static TheatreParty[] getParties() {
        TheatreParty[] parties = new TheatreParty[45];
        int idx = 0;
        List<TheatreParty> pL = new ArrayList<>();
        for (TheatreParty party : TheatrePartiesManager.getParties().values()) {
            if (!pL.contains(party)) {
                pL.add(party);
            }
        }
        for (TheatreParty party : pL) {
            parties[idx] = party;
            idx++;
        }
        return parties;
    }

    public Position[] getBase() {
        return base;
    }

    public long[] getDelta() {
        return delta;
    }

    public Shape[] getRooms() {
        return rooms;
    }

    public TheatreParty getParty() {
        return party;
    }

    public boolean isDead(Player player) {
        for (int i = 0; i < party.members.size(); i++) {
            if (party.members.get(i) == player) {
                return deaths[i];
            }
        }
        return false;
    }
}
