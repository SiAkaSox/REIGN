package io.ruin.model.activities.raids.theatre_of_blood.boss.maiden_of_sugadinti;

import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.activities.raids.theatre_of_blood.TheatrePartiesManager;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreParty;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;
import io.ruin.utility.Shape;

import java.util.*;

public class MaidenOfSugadintiPlugin extends TheatreNPC {

    public List<TheatreNPC> faction, spiderFaction;

    private Shape area;
    private int spiderState = 0;

    private Map<Player, Integer> damage = new HashMap<Player, Integer>();

    public MaidenOfSugadintiPlugin(int id, Position location) {
        super(id, location);
        this.faction = new ArrayList<>();
        this.spiderFaction = new ArrayList<>();
        Position base = location.translated(-3, -9, 0);
        area = new Shape(base.translated(27, 24, 0), base);
        deathStartListener = (entity, killer, killHit) -> onDeath();
    }

    private void onDeath() {
        GameObject chest = GameObject.spawn(37383, getPosition().translated(16, -22, 0), 10, 0);
        chest.skipReachCheck = p -> p.distance(chest.position()) < 2;
        for (Entity p : getPossibleTargets(64, true, false)) {
            TheatreParty party = TheatrePartiesManager.getParty(p.player);
            int points = 0, accumulatedDamage = damage.get(p.player) == null ? 0 : damage.get(p.player);
            if (accumulatedDamage >= 200 && accumulatedDamage < 400) {
                points = 3;
            } else if (accumulatedDamage >= 400 && accumulatedDamage < 600) {
                points = 8;
            } else if (accumulatedDamage >= 600 && accumulatedDamage < 800) {
                points = 12;
            } else if (accumulatedDamage >= 800 && accumulatedDamage < 1000) {
                points = 15;
            } else if (accumulatedDamage >= 1000 && accumulatedDamage < 1500) {
                points = 20;
            } else if (accumulatedDamage >= 1500) {
                points = 24;
            }
            if (party != null) {
                party.addPoints(p.player, points);
            }
        }
        removeAll();
    }

    @Override
    public int hit(Hit... hits) {
        int max = getCombat().getStat(StatType.Hitpoints).fixedLevel;
        boolean above70 = getCombat().getStat(StatType.Hitpoints).currentLevel > (max*0.7),
                above50 = getCombat().getStat(StatType.Hitpoints).currentLevel > (max*0.5),
                above30 = getCombat().getStat(StatType.Hitpoints).currentLevel > (max*0.3);
        Entity attacker = null;
        for(Hit hit : hits) {
            if (hit.attacker != null && attacker instanceof NylocasMatomenosPlugin) {
                hit.type = HitType.HEAL;
            }
            if (hit.attacker != null && attacker == null) {
                attacker = hit.attacker;
            }
        }
        int dmg = super.hit(hits);
        if (attacker != null && attacker.player != null) {
            int current = damage.get(attacker.player) == null ? 0 : damage.get(attacker.player);
            damage.put(attacker.player, current + dmg);
        }
        for(Hit hit : hits) {
            if (getCombat().getStat(StatType.Hitpoints).currentLevel <= dmg && hit.type != HitType.HEAL) {
                transform(8365);
            }
        }
        if (attacker != null) {
            int damage = attacker.get("maiden_damage") != null ? attacker.get("maiden_damage") : 0;
            attacker.set("maiden_damage", damage + dmg);
        }
        if (!above70 && spiderState == 0) {
            beginSpiderStage(8361);
        }
        if (!above50 && spiderState == 1) {
            beginSpiderStage(8362);
        }
        if (!above30 && spiderState == 2) {
            beginSpiderStage(8363);
        }
        return dmg;
    }

    private void beginSpiderStage(int toNpcId) {
        transform(toNpcId);
        spawnBlood();
        spawnSpiders();
        spiderState++;
    }

    @Override
    public void process() {
        super.process();
        if (spiderFaction == null || spiderFaction.size() == 0)
            return;
        spiderFaction.stream().filter(Objects::nonNull).filter(s -> !s.dead()).filter(s -> !s.getPosition().isWithinDistance(this.getCentrePosition(), 3)).forEach(s -> s.getRouteFinder().routeEntity(this));


    }

    @Override
    public ArrayList<Entity> getPossibleTargets(int ratio, boolean players, boolean npcs) {
        ArrayList<Entity> targets = super.getPossibleTargets(ratio, players, npcs);
        ArrayList<Entity> targetsClean = new ArrayList<>();
        for (Entity e : targets) {
            if (area.inside(e.getPosition())) {
                targetsClean.add(e);
            }
        }
        return targetsClean;
    }

    @Override
    public boolean isMovementBlocked(boolean message, boolean ignoreFreeze) {
        return true;
    }

    @Override
    public MaidenOfSugadintiCombat getCombat() {
        if (!(super.getCombat() instanceof MaidenOfSugadintiCombat)) {
            initCombat();
        }
        return (MaidenOfSugadintiCombat) super.getCombat();
    }

    public void initCombat() {
        combat = new MaidenOfSugadintiCombat();
        combat.init(this, getDef().combatInfo);
    }

    public void addBloodSpawn(Position location) {
        if (faction.size() > getPossibleTargets(24, true, false).size() * 2)
            return;
        BloodSpawn spawn = new BloodSpawn(location.copy(), this);
        spawn.setNodes(getNodes());
        faction.add(spawn);
        spawn.register();

    }

    public void removeAll() {
        for (TheatreNPC npc : faction) {
            npc.destroy();
        }
    }

    private void spawnSpiders() {
        Position[] spiderSpawns = new Position[] {
                getPosition().translated(13, 11, 0),
                getPosition().translated(16, 6, 0),
                getPosition().translated(19, 11, 0),
                getPosition().translated(22, 6, 0),
                getPosition().translated(22, 8, 0),
                getPosition().translated(13, 6, 0),
                getPosition().translated(16, 11, 0),
                getPosition().translated(19, 6, 0),
                getPosition().translated(22, 11, 0),
                getPosition().translated(22, 3, 0)
        };
        int count = getPossibleTargets(24, true, false).size() * 2 > spiderSpawns.length ? spiderSpawns.length : getPossibleTargets(24, true, false).size() * 2;
        for (int i = 0; i < count; i++) {
            NylocasMatomenosPlugin spider = new NylocasMatomenosPlugin(spiderSpawns[i].copy(), this);
            spider.setNodes(getNodes());
            spider.register();
            spider.getDef().ignoreOccupiedTiles = true;
            spider.setCombat();
            spider.getCombat().setTarget(this);
            System.err.println("count="+count+" adding to spider faction.. "+(spider.getCombat().getTarget() != null));
            spider.deathStartListener = (entity, killer, killHit) -> {
                spiderFaction.remove(spider);
            };
            spiderFaction.add(spider);
        }
    }

    private void spawnBlood() {
        Position[] bloodSpawns = new Position[] { getCentrePosition().translated(2, 7, 0), getCentrePosition().translated(4, 5, 0), getCentrePosition().translated(6, 3, 0),  getCentrePosition().translated(2, -9, 0), getCentrePosition().translated(4, -7, 0), getCentrePosition().translated(6, -5, 0) };
        int count = getPossibleTargets(24, true, false).size() * 2 > bloodSpawns.length ? bloodSpawns.length : getPossibleTargets(24, true, false).size() * 2;
        for (int i = 0; i < count; i++) {
            addBloodSpawn(bloodSpawns[i].copy());
        }
    }
}
