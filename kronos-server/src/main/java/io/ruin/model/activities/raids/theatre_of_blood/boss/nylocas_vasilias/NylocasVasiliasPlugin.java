package io.ruin.model.activities.raids.theatre_of_blood.boss.nylocas_vasilias;

import io.ruin.model.World;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.activities.raids.theatre_of_blood.TheatrePartiesManager;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreParty;
import io.ruin.model.activities.raids.theatre_of_blood.boss.maiden_of_sugadinti.MaidenOfSugadintiCombat;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NylocasVasiliasPlugin extends TheatreNPC {

    protected static final int MELEE = 8355, MAGIC = 8356, RANGE = 8357;

    private int waveTicks = 0, spawnOffset = 0;

    private Position[] locations;

    private boolean altSpawn;

    private SupportPillar[] pillars;

    private int[][] pillarData = new int[][] { { -5, -5, 2 }, { -5, 6, 3 }, { 6, 6, 0 }, {6, -5, 1} };

    private List<TheatreNPC> minions;

    private Map<Player, Integer> damage = new HashMap<Player, Integer>();

    public NylocasVasiliasPlugin(int id, Position base) {
        super(id, base);
        locations = new Position[] { base.translated(-13, 1, 0), base.translated(1, -13, 0), base.translated(16, 1, 0) };
        pillars = new SupportPillar[] { new SupportPillar(8358, base.translated(-2, -3, 0)), new SupportPillar(8358, base.translated(-4, 5, 0)), new SupportPillar(8358, base.translated(5, 5, 0)), new SupportPillar(8358, base.translated(5, -3, 0)) };
        for (int i = 0; i < pillars.length; i++) {
            Position dest = base.translated(pillarData[i][0], pillarData[1][0], 0);
            pillars[i].setDestroyData(new int[] { dest.getX(), dest.getY(), pillarData[1][2] });
        }
        minions = new ArrayList<TheatreNPC>();
        deathStartListener = (entity, killer, killHit) -> onDeath();
    }

    private void onDeath() {
        for (Entity p : getPossibleTargets(64, true, false)) {
            if (p.player == null) continue;
            GameObject chest = new GameObject(37383, getPosition().translated(9, 30, 0), 10, 2);
            chest.skipReachCheck = p2 -> p2.distance(chest.position()) < 2;
            chest.spawn();
        }
        for (Entity p : getPossibleTargets(64, true, false)) {
            if (p.player == null) continue;
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
            party.addPoints(p.player, points);
        }
    }

    @Override
    public boolean isMovementBlocked(boolean message, boolean ignoreFreeze) {
        return true;
    }

    @Override
    public NylocasVasiliasCombat getCombat() {
        if (combat == null || !(combat instanceof NylocasVasiliasCombat)) {
            initCombat();
        }
        return (NylocasVasiliasCombat) combat;
    }

    public void initCombat() {
        combat = new NylocasVasiliasCombat(this);
        combat.init(this, getDef().combatInfo);
    }

    @Override
    public void process()
    {
        super.process();
        boolean inReach = false;
        for (Entity m : getPossibleTargets()) {
            if (m.getPosition().distance(getCentrePosition()) < 7) {
                inReach = true;
                break;
            }
        }
        if (inReach) {
            List<TheatreNPC> alive = new ArrayList<TheatreNPC>();
            try {
                for (TheatreNPC m : minions) {
                    if (m == null) continue;
                    if (World.npcs.equals(m)) {
                        alive.add(m);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (alive.size() > 10) return;
            if (++waveTicks < 150 && waveTicks % 5 == 0) {
                spawnMinions();
            }
            if (waveTicks < 150 && waveTicks % 20 == 0) {
                spawnLargeMinion();
            }
            if (waveTicks >= 150 && getId() == 8358 && alive.isEmpty()) {
                for (Entity n : getPossibleTargets(64, false, true)) {
                    if (n.npc == null || !(n instanceof TheatreNPC)) continue;
                    ((TheatreNPC) n).destroy();
                }
                minions.clear();
                getCombat().attackDelayTicks = 4;
                state(8354);
                animate(8075);
            }
            if (!getCombat().hasAttackDelay() && getId() == 8354) {
                state(MELEE);
            }
            if (waveTicks >= 240 && waveTicks %10 == 0) {
                if (getCombat().attackDelayTicks > 3) {
                    waveTicks -= 3;
                } else {
                    state(getId() == MELEE ? RANGE : getId() == MAGIC ? MELEE : MAGIC);
                }
            }
        }
    }

    @Override
    public int hit(Hit... hits) {
        AttackStyle st = hits[0].attackStyle == null ? AttackStyle.CRUSH : hits[0].attackStyle;
        Entity attacker = hits[0].attacker;
        AttackStyle type = getId() == 8354 || getId() == 8355 ? AttackStyle.STAB : getId() == MAGIC ? AttackStyle.MAGIC : AttackStyle.RANGED;
        AttackStyle current = st.isMagic() ?  AttackStyle.MAGIC : st.isRanged() ? AttackStyle.RANGED : AttackStyle.STAB;
        if (attacker.player != null) {
            if (type != current) {
                attacker.hit(hits);
                return 0;
            }
        }
        int dmg = super.hit(hits);
        if (attacker != null && attacker.player != null) {
            int c = damage.get(attacker.player) == null ? 0 : damage.get(attacker.player);
            damage.put(attacker.player, c + dmg);
        }
        return dmg;
    }

    @Override
    public void setNodes(List<TheatreNPC> nodes)
    {
        super.setNodes(nodes);
        for (SupportPillar p : pillars) {
            p.setNodes(getNodes());
            p.register();
        }
    }

    private void state(int state) {
        getCombat().delayAttack(1);
        transform(state);
    }

    private void spawnLargeMinion()
    {
        Position loc = locations[spawnOffset];
        NylocasSpawnPlugin spawn = new NylocasSpawnPlugin(8345 + spawnOffset, loc);
        List<Entity> tgt = getPossibleTargets(24, true, false);
        Entity target = Misc.random(5) == 3 && tgt.size() > 0 ? tgt.get(0) : closestPillar(loc);
        spawn.setNodes(getNodes());
//        spawn.loadCombatDefinition();
        spawn.register();
        spawn.setTarget(target);
        spawn.getCombat().setTarget(target);
        spawn.face(target);
        spawn.getCombat().attack();
        minions.add(spawn);
    }

    private void spawnMinions()
    {
        altSpawn = !altSpawn;
        List<Integer> ids = new ArrayList<Integer>();
        switch(spawnOffset) {
            case 0:
                ids.add(8342);
                ids.add(8343);
                ids.add(8344);
                break;
            case 1:
                ids.add(8343);
                ids.add(8344);
                ids.add(8342);
            case 2:
                ids.add(8344);
                ids.add(8342);
                ids.add(8343);
                break;
        }
        for (int i = 0; i < locations.length; i++) {
            Position loc = locations[i].translated(i == 1 ? altSpawn ? 1 : 0 : 0, i != 1 ? altSpawn ? 1 : 0 : 0, 0);
            NylocasSpawnPlugin spawn = new NylocasSpawnPlugin(ids.get(i), loc);
            spawn.setNodes(getNodes());
            List<Entity> tgt = getPossibleTargets(24, true, false);
            Entity target = Misc.random(5) == 0 && tgt.size() > 0 ? tgt.get(0) : closestPillar(loc);
//            spawn.loadCombatDefinition();
            spawn.register();
            spawn.setTarget(target);
            spawn.face(target);
            minions.add(spawn);
            minions.add(spawn);
            spawn.getCombat().attack();
        }
        spawnOffset++;
        if (spawnOffset == 3) {
            spawnOffset = 0;
        }
    }

    private Entity closestPillar(Position l)
    {
        int pillar = -1;
        int hp = 0;
        for (int i = 0; i < pillars.length; i++) {
            SupportPillar p = pillars[i];
            if (p.getCombat().isDead() || p.getCombat().getStat(StatType.Hitpoints).currentLevel <= 0) {
                continue;
            }
            if (p.getCombat().getStat(StatType.Hitpoints).currentLevel > hp) {
                hp = p.getCombat().getStat(StatType.Hitpoints).currentLevel;
                pillar = i;
            }
        }
        if (pillar == -1) return pillars[0];
        return pillars[pillar];
    }

}
