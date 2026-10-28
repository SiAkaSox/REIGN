package io.ruin.model.activities.raids.theatre_of_blood.boss.verzik_vitur;

import io.ruin.model.World;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreOfBlood;
import io.ruin.model.activities.raids.theatre_of_blood.TheatrePartiesManager;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreParty;
import io.ruin.model.activities.raids.theatre_of_blood.boss.Passageways;
import io.ruin.model.activities.raids.theatre_of_blood.boss.sotetseg.SotetsegCombat;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;
import io.ruin.utility.Shape;

import java.util.*;

public class VerzikViturPlugin extends TheatreNPC {

    private static final int WEB_PROJECTILE = 1601, INDICATOR_PROJECTILE = 1596, SAFE_SPOT_GRAPHIC = 1595;

    private VerzikPillar[] pillars;

    private VerzikStage stage;

    public int cycle = 0;

    private boolean started;

    private Shape bounds;

    public GameObject throne;

    private List<Position> safespots;

    private boolean whirlpooled, bombed;

    private Map<Player, VerzikWhirlwindPlugin> pools = new HashMap<>();

    private Map<Integer, VerzikWebPlugin> WEBS = new HashMap<>();

    public VerzikViturPlugin(int id, Position base) {
        super(id, base);
        stage = VerzikStage.UNDISRUPTED;
        bounds = new Shape(base.translated(16, 2, 0), base.translated(-11, -22, 0));
    }

    @Override
    public VerzikCombat getCombat() {
        if (combat == null || !(combat instanceof VerzikCombat)) {
            initCombat();
        }
        return (VerzikCombat) combat;
    }

    public void initCombat() {
        combat = new VerzikCombat(this);
        combat.init(this, getDef().combatInfo);
    }

    @Override
    public void process()
    {
        super.process();
        if (get("transitioning") != null) {
            return;
        }
        List<Player> targets = getPlayers();
        if (targets.size() > 0 && pillars == null) {
            pillars = new VerzikPillar[] {
                    new VerzikPillar(base.translated(-5, -5, 0), 0),
                    new VerzikPillar(base.translated(-5, -11, 0), 0),
                    new VerzikPillar(base.translated(-5, -17, 0), 0),
                    new VerzikPillar(base.translated(7, -5, 0), 1),
                    new VerzikPillar(base.translated(7, -11, 0), 1),
                    new VerzikPillar(base.translated(7, -17, 0), 1) };
            for (VerzikPillar p : pillars) {
                p.register();
            }
        }
        if (getStage().ordinal() > 0) {
            cycle++;
            switch(stage) {
                case FIGHT_SHIELD:
                    if (cycle % 15 == 0) {
                        if (targets.size() > 0) {
                            combat.setTarget(targets.get(0));
                            combat.attack();
                        }
                    }
                    break;
                case FIGHT_MUTABLE:
                    if (targets.size() == 0) return;
                    if (getId() == stage.getMesh() && !getCombat().hasAttackDelay() && targets.size() > 0) {
                        combat.setTarget(targets.get(0));
                        combat.attack();
                    }
                    break;
                case FIGHT_SPIDER:
                    if (targets.size() == 0) return;
                    if (getId() != stage.getMesh()) {
                        return;
                    }
                    if (get("positioning") != null) {
                        if (getPosition().equals(getSpawnPosition().translated(0, -10, 0))) {
                            remove("positioning");
                        } else {
                            walkTo = getSpawnPosition().translated(0, -10, 0);
                            return;
                        }
                    }
                    if (get("special_attack") == null) {
                        if (!getCombat().hasAttackDelay() && targets.size() > 0) {
                            combat.setTarget(targets.get(0));
                            combat.attack();
                        }
                    }
//                    if (getCombat().attackDelayTicks > 0) return;
                    if (get("special_attack") != null) {
                        boolean webWhirlwind = get("special_attack");
                        int count = get("attack_delta") != null ? get("attack_delta") : 0;
                        if (webWhirlwind) {
                            if (count > 30) {
                                animate(-1);
                                remove("special_attack");
                                remove("attack_delta");
                                return;
                            }
                            switch (count) {
                                case 1:
                                   animate(8127);
                                    break;
                                default:
                                    web(targets.get(Misc.random(targets.size() - 1)));
                                    break;
                            }
                        } else {
                            if (count > 10) {
                                List<Position> usedSafespots = new ArrayList<Position>();
                                l : for (Player p : targets) {
                                    for (Position pos : safespots) {
                                        if (usedSafespots.contains(pos)) {
                                            continue;
                                        }
                                        if (p.getPosition().equals(pos)) {
                                            p.player.sendMessage("The power resonating here protects you from the blast.");
                                            usedSafespots.add(pos);
                                            continue l;
                                        }
                                    }
                                    p.hit(new Hit().fixedDamage(Math.min(80, p.player.getStats().get(StatType.Hitpoints).currentLevel)));
                                }
                                animate(-1);
                                remove("special_attack");
                                remove("attack_delta");
                                safespots.clear();
                                return;
                            }
                            if (safespots == null || safespots.isEmpty()) {
                                safespots = new ArrayList<>();
                                for (int i = 0; i < getPosition().getRegion().players.size(); i++) {
                                    safespots.add(base.translated(-11, -18, 0).translated(Misc.random(14), Misc.random(14), 0));
                                }
                            }
                            for (Position safespot : safespots) {
                                for (Player p : targets) {
                                    p.getPacketSender().sendGraphics(SAFE_SPOT_GRAPHIC, 0, 0, safespot.getX(), safespot.getY(), safespot.getZ());
                                }
                            }
                            switch (count) {
                                case 1:
                                   animate(8126);
                                    break;
                                case 5:
                                    break;
                                case 8:
                                    for (Player p : targets) {
                                        Projectile PROJECTILE = new Projectile(INDICATOR_PROJECTILE, 110, 0, 0, 60, 0, 15, 0);
                                        PROJECTILE.send(this, p);
                                    }
                                    break;
                            }
                        }
                        set("attack_delta", ++count);
                        return;
                    }
                    combat.setTarget(targets.get(0));
                    combat.attack();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int hit(Hit... hit)
    {
        Entity attacker = hit[0].attacker;
        if (attacker != null && attacker.npc != null) {
            hit[0].type = HitType.HEAL;
            return super.hit(hit);
        }
        if (get("transitioning") != null) {
            return 0;
        }
        int dmg = super.hit(hit);

        if (stage == VerzikStage.FIGHT_SPIDER) {
            if (!whirlpooled && getCombat().getStat(StatType.Hitpoints).currentLevel <= 800) {
                whirlpooled = true;
                for (Entity target : getPossibleTargets(48, true, false)) {
                    if (target.player == null || pools.get(target.player) != null) continue;
                    VerzikWhirlwindPlugin whirlwind = new VerzikWhirlwindPlugin(target.getPosition().translated(1, -1, 0), target.player, this) {
                        @Override
                        public int hit(Hit... hits) {
                            int dmg = super.hit(hits);
                            if (getCombat().getStat(StatType.Hitpoints).currentLevel <= 0 || getCombat().isDead()) {
                                pools.remove(target.player);
                            }
                            return dmg;
                        }
                    };
                    whirlwind.register();
                    pools.put(target.player, whirlwind);
                }
            } else if (getCombat().getStat(StatType.Hitpoints).currentLevel < 600 && !bombed && getPlayers().size() > 0) {
                Player target = getPlayers().get(0);
                Position dest = target.getPosition();
                Projectile PROJECTILE = new Projectile(VerzikCombat.GREEN_BOMB, 110, 0, 12, 60, 0, 15, 0);
                PROJECTILE.send(this, target);
                int delay = 120;
                if (getCentrePosition().distance(dest) == 1) {
                    delay = 120;
                } else if (getCentrePosition().distance(dest) <= 5) {
                    delay = 100;
                } else if (getCentrePosition().distance(dest) <= 8) {
                    delay = 60;
                } else {
                    delay = 80;
                }
                final int gdmg = Misc.random(50);
                final int del = (delay / 20) - 2;
                World.startEvent(e -> {
                    e.delay(del);
                    target.graphics(VerzikCombat.GREEN_BOMB_IMPACT);
                    target.hit(new Hit().randDamage(gdmg));
                });
                getCombat().delayAttack(4);
                bombed = true;
            }
        }
        if (attacker != null && attacker.npc != null) {
            return dmg;
        }
        if (attacker != null && attacker.isPlayer() && dmg > 0) {
            TheatreParty party = TheatrePartiesManager.getParty(attacker.player);
            if (party != null) {
                party.addRewardPoints(attacker.player, dmg);
            }
        }
        if (stage != VerzikStage.FLEEING && (attacker != null && attacker.isPlayer() && (dmg >= getCombat().getStat(StatType.Hitpoints).currentLevel)) || getCombat().getStat(StatType.Hitpoints).currentLevel <= 200) {
            getCombat().reset();
            getCombat().getStat(StatType.Hitpoints).restore();
            setStage(VerzikStage.values()[stage.ordinal() + 1]);
            cycle = 0;
            if (getStage() == VerzikStage.FLEEING) {
                TheatreOfBlood theatre = TheatrePartiesManager.getRaid(getPosition());
                if (theatre != null) {
                    theatre.getDelta()[6] = System.currentTimeMillis();
                }
            }
            return 0;
        }
        return dmg;
    }

    @Override
    public ArrayList<Entity> getPossibleTargets(int ratio, boolean players, boolean npcs) {
        ArrayList<Entity> possibleTargets = new ArrayList<Entity>();
        if (players) {
            for (Player player : World.players) {
                if (player == null) {
                    continue;
                }
                if (player == null || (TheatrePartiesManager.getRaid(player.getPosition()) != null && TheatrePartiesManager.getRaid(player.getPosition()).isDead(player)) || player.getCombat().isDead() || player.getPosition().distance(this.getCentrePosition()) > ratio) {
                    continue;
                }
                possibleTargets.add(player);
            }
        }
        if (npcs) {
            for (NPC npc : World.npcs) {
                if (npc == null || npc == this || npc.getCombat() == null || npc.getCombat().isDead() || npc.getCentrePosition().distance(this.getCentrePosition()) > ratio) {
                    continue;
                }
                possibleTargets.add(npc);
            }
        }
        return possibleTargets;
    }

    private void web(Player target) {
        int count = Misc.random(2, 6);
        face(target);
        Position[] Positions = new Position[count];
        Positions[0] = target.getPosition().copy();
        Position base = target.getPosition().translated(-3, -3, 0);
        for (int i = 1; i < Positions.length; i++) {
            Positions[i] = base.translated(Misc.random(6), Misc.random(6), 0);
        }
        for (Position dest : Positions) {
            if (WEBS.get(dest.tileHash()) != null) continue;
            Projectile PROJECTILE = new Projectile(WEB_PROJECTILE, 110, 0, 0, 60, 0, 15, 0);
            PROJECTILE.send(getCentrePosition().getX(), getCentrePosition().getY(), dest.getX(), dest.getY());
            int delay = 120;
            if (getCentrePosition().distance(dest) == 1) {
                delay = 120;
            } else if (getPosition().distance(dest) <= 5) {
                delay = 100;
            } else if (getPosition().distance(dest) <= 8) {
                delay = 60;
            } else {
                delay = 80;
            }
            final int del = (delay / 20) - 2;
            World.startEvent(e -> {
                e.delay(del);
                VerzikWebPlugin web = new VerzikWebPlugin(8376, dest);
                web.register();
                WEBS.put(dest.tileHash(), web);
                e.delay(10);
                web.destroy();
            });
        }
    }

    public VerzikPillar closestPillar(Entity target) {
        double closest = 64;
        int idx = -1;
        for (int i = 0; i < pillars.length; i++) {
            VerzikPillar p = pillars[i];
            if (p.getCombat().isDead()) {
                continue;
            }
            double dist = p.getCentrePosition().distance(target.getPosition());
            if (dist < 3 && closest > dist) {
                closest = p.getCentrePosition().distance(target.getPosition());
                idx = i;
            }
        }
        if (idx >= 0) {
            return pillars[idx];
        }
        return null;
    }

    public VerzikStage getStage()
    {
        return stage;
    }

    public void setStage(VerzikStage stage)
    {
        set("transitioning", true);
        this.stage = stage;
        getCombat().delayAttack(10);
        stage.animate(this);
        for (Entity m : getPossibleTargets(48, true, false)) {
            m.getCombat().reset();
        }
    }

    public boolean isStarted()
    {
        return started;
    }

    public void setStarted(boolean started)
    {
        this.started = started;
    }

    public VerzikPillar[] getPillars()
    {
        return pillars;
    }

    @Override
    public boolean isMovementBlocked(boolean message, boolean ignoreFreeze) {
        if (get("transitioning") != null || stage.ordinal() < 2) {
            return true;
        }
        return super.isMovementBlocked(message, ignoreFreeze);
    }

    public List<Player> getPlayers() {
        List<Entity> allTargets = getPossibleTargets(48, true, false);
        List<Player> targets = new ArrayList<Player>();
        for (Entity m : allTargets) {
            if (bounds.inside(m.getPosition())) {
                targets.add(m.player);
            }
        }
        Collections.shuffle(targets);
        return targets;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

}
