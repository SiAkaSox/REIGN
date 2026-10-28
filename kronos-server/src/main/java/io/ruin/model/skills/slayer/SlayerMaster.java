package io.ruin.model.skills.slayer;

import io.ruin.utility.Random;
import io.ruin.cache.Color;
import io.ruin.cache.ItemID;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.achievements.listeners.easy.SlayerBeginner;
import io.ruin.model.achievements.listeners.medium.AreYouManEnough;
import io.ruin.model.achievements.listeners.medium.YoureANaturalAtThis;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.content.accountperks.Perk;
import io.ruin.model.content.accountperks.PerkManager;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.KillCounter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SlayerMaster {
    private final int npcId;
    public static SlayerMaster[] masters;
    public static final int TURAEL_ID = 1;
    public static final int MAZCHNA_ID = 2;
    public static final int VANNAKA_ID = 3;
    public static final int CHAELDAR_ID = 4;
    public static final int NIEVE_ID = 5;
    public static final int DURADEL_ID = 6;
    public static final int KRYSTILIA_ID = 7;
    public static final int KONAR_ID = 8;

    public static final int SLAYER_HELM = 11864;
    public static final int SLAYER_HELM_IMBUE = 11865;

    public static final int RED_SLAYER_HELM = 19647;
    public static final int RED_HELM_IMBUE = 19649;
    public static final int GREEN_SLAYER_HELM = 19643;
    public static final int GREEN_HELM_IMBUE = 19645;
    public static final int BLACK_SLAYER_HELM = 19639;
    public static final int BLACK_HELM_IMBUE = 19641;
    public static final int PURPLE_SLAYER_HELM = 21264;
    public static final int PURPLE_HELM_IMBUE = 21266;
    public static final int TURQUOISE_SLAYER_HELM = 21888;
    public static final int TURQUOISE_HELM_IMBUE = 21890;
    public static final int HYDRA_SLAYER_HELM = ItemID.HYDRA_SLAYER_HELMET;
    public static final int HYDRA_HELM_IMBUE = ItemID.HYDRA_SLAYER_HELMET_I;

    public SlayerMaster(int npcId) {
        this.npcId = npcId;
    }

    public SlayerTask randomTask(Player player) {
        int last = Config.SLAYER_TASK.get(player);
        List<SlayerTask> tasks = Slayer.getTasksForMaster(Config.SLAYER_MASTER.get(player));

        List<SlayerTask> filteredTasks = new ArrayList<>();

        for (SlayerTask task : tasks) {
            if (task != null && task.getTaskId() != last && player.getStats().get(StatType.Slayer).fixedLevel >= task.getSlayerRequirement() && !SlayerUnlock.blocked(player, task.getTaskId())) {

                if (!player.slayerCombatCheck && player.getCombat().getLevel() < task.getCombatRequirement()) {
                    continue;
                }

                filteredTasks.add(task);
            }
        }

        SlayerTask def = null;

        if (Config.LIKE_A_BOSS.get(player) != 0 && filteredTasks.stream().filter(task -> task.getTaskId() == 98).count() > 0) {
            if (Random.get(0, 100) <= 20) {
                for(SlayerTask slayerTask : filteredTasks) {
                    if(slayerTask.getTaskId() == 98 && player.getStats().get(StatType.Slayer).fixedLevel >= slayerTask.getSlayerRequirement()) {
                        def = slayerTask;
                    }
                }
            }
        }

        if(def == null) {
            def = filteredTasks.get(Random.get(filteredTasks.size() - 1));
        }

        if (def.getTaskId() == 98) {
            if(npcId == KRYSTILIA_ID) {
                List<SlayerBoss> wildyBossTasks = Arrays.stream(SlayerBoss.values())
                        .filter(task -> task.wilderness).collect(Collectors.toList());
                SlayerBoss bossTask = wildyBossTasks.get(Random.get(wildyBossTasks.size()));
                int configId = bossTask.configId;
                Config.BOSS_TASK.set(player, configId);
            } else {
                List<SlayerBoss> nonWildyBossTasks = Arrays.stream(SlayerBoss.values())
                        .filter(task -> !task.wilderness).collect(Collectors.toList());
                SlayerBoss bossTask = Random.get(nonWildyBossTasks);
                int configId = bossTask.configId;
                Config.BOSS_TASK.set(player, configId);
            }
        } else {
            Config.BOSS_TASK.set(player, 0);
        }

        return def;
    }

    public static SlayerMaster master(int npc) {
        for (SlayerMaster master : masters) {
            if (master.npcId == npc)
                return master;
        }

        return null;
    }

    public static String bestMaster(Player player) {
        final int cmb = player.getCombat().getLevel();

        if (cmb < 20)
            return "Turael in Burthorpe";

        if (cmb < 40)
            return "Mazchna in Canifis";

        if (cmb < 70)
            return "Vannaka in Edgeville<br>Dungeon";

        if (cmb < 75)
            return "Chaeldar in Zanaris.";

        if (cmb < 85)
            return "Konar on Mount<br>Karuulm";

        if (cmb < 100)
            return "Steve in the Gnome<br>Stronghold";

        return "Duradel in Shilo Village";
    }

    public static boolean isTask(Player player, NPC npc) {
        final int task = Config.SLAYER_TASK.get(player);
        int am = Config.SLAYER_TASK_AMOUNT.get(player);
        final int master = Config.SLAYER_MASTER.get(player);

        SlayerCreature creature = SlayerCreature.lookup(task);
        return task > 0 && am > 0 && master > 0 && creature != null && creature.contains(npc);
    }

    public static Item findHighestEmblem(Player player) {
        Item highest = null;
        for (Item item : player.getInventory().getItems()) {
            if (item != null && (item.getId() == 12746 || (item.getId() >= 12748 && item.getId() <= 12756))) {
                if (highest == null || item.getId() > highest.getId())
                    highest = item;
            }
        }
        return highest;
    }

    public static void handle(Player player, NPC npc) {
        //player.getSlayerLog().update(npc.getId());TODO
        final int task = Config.SLAYER_TASK.get(player);
        int am = Config.SLAYER_TASK_AMOUNT.get(player);
        final int master = Config.SLAYER_MASTER.get(player);

        SlayerCreature creature = SlayerCreature.lookup(task);

        if (task > 0 && am > 0 && master > 0 && creature != null && (creature.contains(npc))) {
            int combat = npc.getDef().combatLevel;
            if (master == KRYSTILIA_ID && (player.wildernessLevel <= 0 && !player.getPosition().inBounds(
                    new Bounds(2256, 4680, 2287, 4711)))) {
                player.sendMessage("<col=FF0000>You must kill your slayer assignment within the wilderness to "
                        + "receive experience!");
                return;
            } else if (master == KRYSTILIA_ID) {
                if (Wilderness.getLevel(npc.getPosition()) > 55) {
                    npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
                            if (Random.rollDie((int) Math.floor(npc.getDef().combatInfo.hitpoints / 2.0))) {
                                new GroundItem(24565, 1).owner(killer.player).position(npc.getPosition()).spawn(); //Drops the first emblem
                            }
                            int chance = Random.get(1, 50);
                            if (player.storeAmountSpent >= 100 && player.storeAmountSpent < 250) {
                                chance = Random.get(1, 45);
                            }
                            if (player.storeAmountSpent >= 250 && player.storeAmountSpent < 500) {
                                chance = Random.get(1, 42);
                            }
                            if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
                                chance = Random.get(1, 40);
                            }
                            if (player.storeAmountSpent >= 1000) {
                                chance = Random.get(1, 37);
                            }
                            if (player.storeAmountSpent < 50) {
                                chance = Random.get(1, 50);
                            }
                            if (chance == 1) {
                                killer.player.getInventory().addOrDrop(30138, 1);
                            }
                    };
                }
                int chance = (int) (155 - Math.floor(npc.getDef().combatInfo.hitpoints / 2.0));

                //Archaic emblem upgrade chance
                Item emblem = findHighestEmblem(player);
                if (emblem != null && emblem.getId() != 12756 && Random.get(100) < 3) {
                    emblem.setId(emblem.getId() == 12746 ? 12748 : emblem.getId() + 1);
                    player.sendMessage(Color.RED.wrap("Your Archaic Emblem has been upgraded by one tier!"));
                }

                if (Random.get(chance) == 1) {
                    new GroundItem(12746, 1).owner(player).position(npc.getPosition()).spawn();
                } else {
                    if (combat <= 80) {
                        chance = (int) (100 + ((3 / 10) * Math.pow(80 - combat, 2)));
                    } else {
                        chance = 50 + (350 - combat) / (269) * 49;
                    }
                    if (Random.get(20) == 1) {
                        int amount = Random.get(12, 25);
                        if (player.bloodmoneyBoostTimeLeft > 0) {
                            amount = amount * 2;
                        }
                        new GroundItem(13307, amount).owner(player).position(npc.getPosition()).spawn();
                        player.sendMessage("You notice <col=FF0000>x"+amount+" Blood Money</col> fall to the ground.");
                    }

                    if (Random.get(chance) == 1) {
                        if (player.abyssalOrphan) {
                            new GroundItem(23490, 2).owner(player).position(npc.getPosition()).spawn();
                            player.sendMessage("<col=ff0000>Untradable drop: x2 Larran's key");
                        } else {
                            new GroundItem(23490, 1).owner(player).position(npc.getPosition()).spawn();
                            player.sendMessage("<col=ff0000>Untradable drop: Larran's key");
                        }

                    } else {
                        chance = (int) (320 - Math.floor(npc.getDef().combatInfo.hitpoints * 0.8));

                        if (combat > 200)
                            chance = 30;

                        if (Random.get(chance) == 1) {
                            new GroundItem(786, 1).owner(player).position(npc.getPosition()).spawn();
                        }
                    }
                }
            } else if (master == KONAR_ID && !KonarData.TaskLocation.values()[player.slayerLocation].inside(npc.getPosition().copy())) {
                String location = KonarData.TaskLocation.values()[player.slayerLocation].getName();
                player.sendMessage("<col=FF0000>You must kill your slayer assignment at the " + location + " to receive experience!");
                return;
            } else if (master == KONAR_ID) {
                int chance;

                if (combat < 100) {
                    chance = (int) (0.2 * Math.pow(combat - 100, 2) + 100);
                } else {
                    chance = (int) (-0.2 * combat + 120);

                    if (chance > 100)
                        chance = 100;

                    if (chance < 50)
                        chance = 50;
                }

                if (Random.get(chance) == 1) {
                    new GroundItem(23083, 1).owner(player).position(player.getPosition()).spawn();
                    player.sendMessage("<col=ff0000>Untradable drop: Brimstone key");
                }
            }

            int npcCombat = npc.getDef().combatLevel;

            int coinBoxChance = npcCombat > 100 ? 25 : npcCombat > 75 ? 40 : npcCombat > 50 ? 60 : 100;

            if (Random.get(70) == 1) {
                new GroundItem(23071, 1).owner(player).position(player.getPosition()).spawn();
                player.sendMessage("<col=ff0000>You've received a slayer casket drop!");
            }
            if (player.storeAmountSpent >= 10 && Random.get(60) == 1) {
                new GroundItem(23071, 1).owner(player).position(player.getPosition()).spawn();
                player.sendMessage("<col=ff0000>You've received a slayer casket drop!");
            }
            if (player.storeAmountSpent >= 50 && Random.get(50) == 1) {
                new GroundItem(23071, 1).owner(player).position(player.getPosition()).spawn();
                player.sendMessage("<col=ff0000>You've received a slayer casket drop!");
            }
            if (player.storeAmountSpent >= 200 && Random.get(45) == 1) {
                new GroundItem(23071, 1).owner(player).position(player.getPosition()).spawn();
                player.sendMessage("<col=ff0000>You've received a slayer casket drop!");
            }
            if (player.storeAmountSpent >= 500 && Random.get(40) == 1) {
                new GroundItem(23071, 1).owner(player).position(player.getPosition()).spawn();
                player.sendMessage("<col=ff0000>You've received a slayer casket drop!");
            }
            if (player.storeAmountSpent >= 1000 && Random.get(40) == 1) {
                new GroundItem(23071, 1).owner(player).position(player.getPosition()).spawn();
                player.sendMessage("<col=ff0000>You've received a slayer casket drop!");
            }

            final int hp = NPCDef.get(npc.getId()).combatInfo.hitpoints;
            final boolean superior = SuperiorSlayer.getSuperior(creature, npc.getId()) != -1 && SuperiorSlayer.getSuperior(creature, npc.getId()) == npc.getId();
            final int xp = superior ? hp * 10 : hp;

            player.getStats().addXp(StatType.Slayer, xp, true);
            Config.SLAYER_TASK_AMOUNT.set(player, Config.SLAYER_TASK_AMOUNT.get(player) - 1);

            am -= 1;

            if (am == 0) {
                final int spree = ++player.slayerSpree;
                int basePoints = basePoints(master) * (World.doubleSlayer
                        && !player.name().equalsIgnoreCase("normal") ? 2 : 1);
                int total = points(master, spree) + basePoints;
                int current = Config.SLAYER_POINTS.get(player);

                if (PerkManager.isActive(player, Perk.SLAYER_BOOSTER)) {
                    total += player.getStats().get(18).fixedLevel / 9;
                    player.sendFilteredMessage("Your Slayer Booster perk helps boost your slayer points earned!");
                }

                if (master == 7) {
                    player.getInventory().addOrDrop(13307, Random.get(75, 250));
                    player.sendMessage("You receive some <col=FF0000>blood money</col> for completing a wilderness slayer task.");
                    AreYouManEnough.complete(player);
                }

                if (player.slayerSpree == 5) {
                    YoureANaturalAtThis.complete(player);
                }
                player.slayerTasksCompleted++;
                player.sendMessage("<col=7F00FF>You've completed " + spree + " tasks in a row and received " + (total) + " points. Your total is now "+ (current + total) +" points. Return to a Slayer Master for a new task.");
                Config.SLAYER_POINTS.set(player, current + total);
                SlayerBeginner.complete(player);
            } else {
                SuperiorSlayer.trySpawn(player, creature, npc);
            }
        } else if (Config.SLAYER_TASK.get(player) == 98) {
            SlayerBoss slayerBoss = SlayerBoss.getSlayerBoss(Config.BOSS_TASK.get(player));
            if(slayerBoss != null && slayerBoss.isTask(npc.getId())) {

                final int hp = NPCDef.get(npc.getId()).combatInfo.hitpoints;
                player.getStats().addXp(StatType.Slayer, hp, true);
                Config.SLAYER_TASK_AMOUNT.set(player, Config.SLAYER_TASK_AMOUNT.get(player) - 1);

                am -= 1;

                if (am == 0) {
                    final int spree = ++player.slayerSpree;
                    int basePoints = basePoints(master) * (World.doubleSlayer ? 2 : 1);
                    final int total = points(master, spree) + basePoints;
                    final int current = Config.SLAYER_POINTS.get(player);

                    SlayerBeginner.complete(player);

                    player.sendMessage("<col=7F00FF>You've completed " + spree + " tasks in a row and received "
                            + (total) + " points. Return to a Slayer Master for a new task.");
                    Config.SLAYER_POINTS.set(player, current + total);
                    SlayerBeginner.complete(player);
                }
            }
        }
    }

    private static int basePoints(int master) {
        switch (master) {
            case MAZCHNA_ID:
            case TURAEL_ID:
                return 3;
            case VANNAKA_ID:
                return 5;
            case CHAELDAR_ID:
                return 10;
            case NIEVE_ID:
            case DURADEL_ID:
            case KONAR_ID:
                return 30;
            case KRYSTILIA_ID:
                return 50;
            default:
                return 0;
        }
    }

    private static int points(int master, int spree) {
        if (spree % 1000 == 0) {
            switch (master) {
                case MAZCHNA_ID:
                    return 100;
                case VANNAKA_ID:
                    return 200;
                case CHAELDAR_ID:
                    return 500;
                case NIEVE_ID:
                case DURADEL_ID:
                case KONAR_ID:
                    return 750;
                case KRYSTILIA_ID:
                    return 1250;
                default:
                    return 0;
            }
        }

        if (spree % 250 == 0) {
            switch (master) {
                case MAZCHNA_ID:
                    return 70;
                case VANNAKA_ID:
                    return 140;
                case CHAELDAR_ID:
                    return 350;
                case NIEVE_ID:
                case DURADEL_ID:
                case KONAR_ID:
                    return 525;
                case KRYSTILIA_ID:
                    return 875;
                default:
                    return 0;
            }
        }

        if (spree % 100 == 0) {
            switch (master) {
                case MAZCHNA_ID:
                    return 50;
                case VANNAKA_ID:
                    return 100;
                case CHAELDAR_ID:
                    return 250;
                case NIEVE_ID:
                case DURADEL_ID:
                case KONAR_ID:
                    return 375;
                case KRYSTILIA_ID:
                    return 625;
                default:
                    return 0;
            }
        }

        if (spree % 50 == 0) {
            switch (master) {
                case MAZCHNA_ID:
                    return 15;
                case VANNAKA_ID:
                    return 60;
                case CHAELDAR_ID:
                    return 150;
                case NIEVE_ID:
                case DURADEL_ID:
                case KONAR_ID:
                    return 225;
                case KRYSTILIA_ID:
                    return 375;
                default:
                    return 0;
            }
        }

        if (spree % 10 == 0) {
            switch (master) {
                case MAZCHNA_ID:
                    return 5;
                case VANNAKA_ID:
                    return 20;
                case CHAELDAR_ID:
                    return 50;
                case NIEVE_ID:
                case DURADEL_ID:
                case KONAR_ID:
                    return 75;
                case KRYSTILIA_ID:
                    return 125;
                default:
                    return 0;
            }
        }
        return 0;
    }

    public int getNpcId() {
        return npcId;
    }

    public static String getTaskText(Player player, int left) {
        String text = "You're still hunting " + SlayerUnlock.taskName(player, Config.SLAYER_TASK.get(player)) + ", with " + left + " to go.<br>Come back when you're finished.";

        int master = Config.SLAYER_MASTER.get(player);

        if (master == KRYSTILIA_ID) {
            text = "You're still meant to be slaying " + SlayerUnlock.taskName(player, Config.SLAYER_TASK.get(player)) + " in<br>the Wilderness, you have " + left + " to go. Come back when<br>you've finished your task.";
        } else if (master == KONAR_ID) {
            text = "You're still bringing balance to " + SlayerUnlock.taskName(player, Config.SLAYER_TASK.get(player)) + " at the " + KonarData.TaskLocation.values()[player.slayerLocation].getName() + ", with " + left + " to go.<br>Come back when you're finished.";
        }

        return text;
    }

    public static void check(Player player) {
        int amount = Config.SLAYER_TASK_AMOUNT.get(player);

        if (amount > 0) {
            String name = SlayerUnlock.taskName(player, Config.SLAYER_TASK.get(player));
            String location = "";
            int master = Config.SLAYER_MASTER.get(player);

            if (master == KRYSTILIA_ID) {
                location = " in the wilderness";
            } else if (master == KONAR_ID) {
                location = " at the " + KonarData.TaskLocation.values()[player.slayerLocation].getName();
            }

            player.sendMessage("You're assigned to kill " + name + "" + location + ", only " + amount + " to go.");
        } else {
            player.sendMessage("You need something new to hunt.");
        }

    }

    public SlayerTask specificTask(Player player, int taskId) {
        int last = Config.SLAYER_TASK.get(player);
        List<SlayerTask> tasks = Slayer.getTasksForMaster(Config.SLAYER_MASTER.get(player));

        List<SlayerTask> filteredTasks = new ArrayList<>();

        for (SlayerTask task : tasks) {
            if (task != null && task.getTaskId() != last && player.getStats().get(StatType.Slayer).fixedLevel >= task.getSlayerRequirement() && !SlayerUnlock.blocked(player, task.getTaskId())) {

                if (!player.slayerCombatCheck && player.getCombat().getLevel() < task.getCombatRequirement()) {
                    continue;
                }

                filteredTasks.add(task);
            }
        }

        SlayerTask def = null;

        for(SlayerTask task : filteredTasks) {
            if(task.getTaskId() == taskId) {
                def = task;
            }
        }
        Config.BOSS_TASK.set(player, 0);

        return def;
    }

    static {
        for (int helm : Arrays.asList(SLAYER_HELM, SLAYER_HELM_IMBUE, RED_HELM_IMBUE, RED_SLAYER_HELM, GREEN_SLAYER_HELM, GREEN_HELM_IMBUE, BLACK_HELM_IMBUE, BLACK_SLAYER_HELM, PURPLE_HELM_IMBUE, PURPLE_SLAYER_HELM, TURQUOISE_SLAYER_HELM, TURQUOISE_HELM_IMBUE , HYDRA_SLAYER_HELM, HYDRA_HELM_IMBUE)) {
            ItemAction.registerEquipment(helm, 4, (player, item) -> check(player));
            ItemAction.registerEquipment(helm, "log", (player, item) -> KillCounter.openOwnSlayer(player));
            ItemAction.registerInventory(helm, "check", (player, item) -> check(player));
            ItemAction.registerInventory(ItemID.ENCHANTED_GEM, "check", (player, item) -> check(player));
            ItemAction.registerInventory(ItemID.ENCHANTED_GEM, "activate", (player, item) -> check(player));
            ItemAction.registerInventory(ItemID.ENCHANTED_GEM, "log", (player, item) -> KillCounter.openOwnSlayer(player));
        }
    }
}
