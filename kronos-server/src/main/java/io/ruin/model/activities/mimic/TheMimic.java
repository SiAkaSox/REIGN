package io.ruin.model.activities.mimic;

import com.google.common.collect.Lists;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.utility.Random;
import io.ruin.model.World;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.utility.Misc;

import java.util.*;

import static io.ruin.network.incoming.handlers.InterfaceOnGroundItemHandler.withinReach;

public class TheMimic extends NPCCombat {

    private final ThirdAgeMonster[] thirdAgeNPCs = new ThirdAgeMonster[3];

    @Override
    public void init() {
        npc.setIgnoreMulti(true);
        npc.deathStartListener = (entity, killer, killHit) -> {
            removeMinions();
            if (npc.get("Mimic") != null) {
                double chance = Math.random();
                double probability = 1.0 / Pet.MIMIC.dropAverage;
                Pet pet = Pet.MIMIC;
                if (chance <= probability) {
                    pet.unlock(killer.player);
                }
                if (Math.random() <= 0.25) {
                    //MimicLair.createBossInstance();
                }
            }
        };
    }

    private int getSpawnType(int id) {
        for (int index = 0; index < thirdAgeNPCs.length; index++) {
            NPC n = thirdAgeNPCs[index];
            if (n != null) {
                if (n.getId() == id) {
                    return -1;
                }
            }
        }
        return id;
    }

    private void handleMinionDeath() {
        for (int index = 0; index < thirdAgeNPCs.length; index++) {
            ThirdAgeMonster monster = thirdAgeNPCs[index];
            if (monster != null && monster.dead()) {
                thirdAgeNPCs[index] = null;
                monster.remove();
            }
        }
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        int size = possibleTargets().size();
        Player chosenTarget = possibleTargets().get(size == 1 ? 0 : Misc.random(size - 1));
        if (withinDistance(1)) {
            if (Random.rollDie(2)) {//stomp = 8306 - 8310 = death
                if (npc.get("BossMimic") != null) {
                    int hits = 0;
                    for (Player players : possibleTargets()) {
                        if (players == null || players.dead() || !npc.getCentrePosition().isWithinDistance(players.getPosition(), 3))
                            continue;
                        hits++;
                        players.hit(new Hit(npc, info.attack_style, null).randDamage(info.max_damage));
                    }
                    npc.animate(8308);
                    defendAnim();
                } else {
                    basicAttack(8308, AttackStyle.CRUSH, 20);
                }
                return true;
            }
        } else {
            follow();
        }
        boolean candyAttack = Math.random() <= 0.20;
        if (candyAttack) {
            npc.animate(8309);
            npc.addEvent(e -> {
                List<Position> possibleTiles = getPossibleTiles(chosenTarget.getPosition());
                for (int index = 0; index < possibleTiles.size(); index++) {
                    Candy candy = Candy.getCandyList(1).get(index);
                    double random = Math.random();
                    Item possibleLoot = possibleLoot(random <= 0.01 ? ClueType.MASTER : random <= 0.05 ? ClueType.ELITE : random <= 0.1 ? ClueType.HARD : random <= 0.25 ? ClueType.MEDIUM : ClueType.EASY).get(index);
                    Position targetPos = possibleTiles.get(index);
                    World.startEvent(event -> {
                        int delay = new Projectile(candy.projectile, 50, 1, 5, 75, 50, 0, 10).send(npc.getCentrePosition(), targetPos);
                        event.delay((delay * 25) / 600 - 1);
                        if (npc.get("BossMimic") != null) {
                            Item reward = possibleLoot;
                            reward.putAttribute("BossMimic", true);
                            new GroundItem(reward)
                                    .position(targetPos)
                                    .spawn();
                        }
                        World.sendGraphics(candy.gfxId, 0, 0, targetPos);
                        if (candy == Candy.PINK) {
                            for (Entity p : getPossibleTargets()) {
                                if (p == null)
                                    continue;
                                if (Objects.equals(p, targetPos)) {
                                    p.hit(new Hit(npc, null).randDamage(8, 12).delay(0));
                                }
                            }
                            return;
                        }
                        spawnThirdAgeMonster(candy == Candy.RED ? ThirdAgeMonster.WARRIOR : candy == Candy.GREEN ? ThirdAgeMonster.RANGER : ThirdAgeMonster.MAGE, targetPos);
                    });
                }
            });
        }
        return true;
    }

    private List<Position> getPossibleTiles(Position position) {
        List<Position> pos = Lists.newLinkedList();
        int attempts = 0;
        Bounds bounds = new Bounds(position.getX(), position.getY(), position.getZ(), 3);
        while (pos.size() < 6) {
            if (++attempts > 500)
                break;
            Position randomTile = bounds.randomPosition();
            int x = randomTile.getX();
            int y = randomTile.getY();
            if (!withinReach(npc.getAbsX(), npc.getAbsY(), npc.getHeight(), x, y, npc.getPosition().distance(randomTile)))
                continue;
            pos.add(randomTile);
        }
        return pos;
    }

    //spawnIfAbsent(tile, candy == Candy.RED ? ThirdAgeMonster.WARRIOR : candy == Candy.GREEN ? ThirdAgeMonster.RANGER : ThirdAgeMonster.MAGE);
    private void spawnThirdAgeMonster(int id, Position tile) {
        if (npc.dead()) {
            return;
        }
        id = getSpawnType(id);
        if (id == -1)
            return;
        /** Loops to gran empty index **/
        for (int index = 0; index < thirdAgeNPCs.length; index++) {
            ThirdAgeMonster monster = thirdAgeNPCs[index];
            if (monster == null && !npc.dead()) {
                monster = thirdAgeNPCs[index] = new ThirdAgeMonster(id, tile);
                monster.set("Mimic", true);
                monster.spawn(tile.getX(), tile.getY(), tile.getZ(), Direction.SOUTH, 10);
                monster.getDef().ignoreOccupiedTiles = true;
                if (target instanceof Player) {
                    monster.targetPlayer((Player) target, false);
                }
                monster.attackTargetPlayer();
                monster.deathEndListener = (DeathListener.Simple) this::handleMinionDeath;
                break;
            }
        }
    }

    private void removeMinions() {
        System.err.println("destroying minions...");
        Arrays.stream(thirdAgeNPCs).filter(m -> m != null).forEach(m -> m.hit(new Hit(null, null).fixedDamage(m.getHp())));
    }

    private List<Item> possibleLoot(ClueType type) {
        ArrayList<LootItem> list = new ArrayList<>();
        LootItem[] baseLoots = {
                new LootItem(3827, 1, 10),          //Saradomin page 1
                new LootItem(3828, 1, 10),          //Saradomin page 2
                new LootItem(3829, 1, 10),          //Saradomin page 3
                new LootItem(3830, 1, 10),          //Saradomin page 4
                new LootItem(3835, 1, 10),          //Guthix page 1
                new LootItem(3836, 1, 10),          //Guthix page 2
                new LootItem(3837, 1, 10),          //Guthix page 3
                new LootItem(3838, 1, 10),          //Guthix page 4
                new LootItem(3831, 1, 10),          //Zamorak page 1
                new LootItem(3832, 1, 10),          //Zamorak page 2
                new LootItem(3833, 1, 10),          //Zamorak page 3
                new LootItem(3834, 1, 10),          //Zamorak page 4
                new LootItem(12617, 1, 10),         //Armadyl page 1
                new LootItem(12618, 1, 10),         //Armadyl page 2
                new LootItem(12619, 1, 10),         //Armadyl page 3
                new LootItem(12620, 1, 10),         //Armadyl page 4
                new LootItem(12621, 1, 10),         //Ancient page 1
                new LootItem(12622, 1, 10),         //Ancient page 2
                new LootItem(12623, 1, 10),         //Ancient page 3
                new LootItem(12624, 1, 10),         //Ancient page 4
                new LootItem(12613, 1, 10),         //Bandos page 1
                new LootItem(12614, 1, 10),         //Bandos page 2
                new LootItem(12615, 1, 10),         //Bandos page 3
                new LootItem(12616, 1, 10),         //Bandos page 4
                new LootItem(10326, 1, 10),         //Purple firelighter
                new LootItem(7329, 1, 10),          //Red firelighter
                new LootItem(7331, 1, 10),          //Blue firelighter
                new LootItem(7330, 1, 10),          //Green firelighter
                new LootItem(10327, 1, 10),         //White firelighter
                new LootItem(10476, 10, 50, 10),    //Purple sweets
                new LootItem(554, 10, 50, 10),      //Fire rune
                new LootItem(555, 10, 50, 10),      //Water rune
                new LootItem(556, 10, 50, 10),      //Air rune
                new LootItem(557, 10, 50, 10),      //Earth rune
                new LootItem(558, 10, 50, 10),      //Mind rune
                new LootItem(560, 10, 50, 10),      //Death rune
                new LootItem(561, 10, 50, 10),      //Nature rune
                new LootItem(562, 10, 50, 10),      //Chaos rune
                new LootItem(563, 10, 50, 10),      //Law rune
                new LootItem(564, 10, 50, 10),      //Cosmic rune
                new LootItem(565, 10, 50, 10),      //Blood rune
                new LootItem(1381, 1, 10),          //Staff of air
                new LootItem(1383, 1, 10),          //Staff of water
                new LootItem(1385, 1, 10),          //Staff of earth
                new LootItem(1387, 1, 10),          //Staff of fire
                new LootItem(1694, 1, 10),          //Sapphire amulet
                new LootItem(1696, 1, 10),          //Emerald amulet
                new LootItem(1698, 1, 10),          //Ruby amulet
                new LootItem(1700, 1, 10),          //Diamond amulet
        };
        LootItem[] loots = type.loots;
        Collections.addAll(list, baseLoots);
        Collections.addAll(list, loots);
        List<Item> possibleLoot = Lists.newArrayList();
        LootTable table = new LootTable().addTable(1, loots);
        for(int i = 0; i < 6; i++) {
            Item item = table.rollItem();
            possibleLoot.add(item);
        }
        return possibleLoot;
    }

    private List<Player> possibleTargets() {
        return npc.getPosition().getRegion().players;
    }
}
