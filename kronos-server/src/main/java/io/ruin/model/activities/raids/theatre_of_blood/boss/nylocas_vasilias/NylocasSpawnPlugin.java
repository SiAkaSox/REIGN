package io.ruin.model.activities.raids.theatre_of_blood.boss.nylocas_vasilias;

import io.ruin.model.activities.miscpvm.BasicCombat;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.activities.raids.theatre_of_blood.boss.maiden_of_sugadinti.MaidenOfSugadintiCombat;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.ClipUtils;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;

import java.util.ArrayList;
import java.util.List;

class MagicCombat extends NPCCombat {

	private final int PROJECTILE_ID = 10;

	@Override
	public void init() {

	}

	@Override
	public void follow() {

	}

	@Override
	public boolean attack() {
		npc.animate(npc.getDef().combatInfo.attack_animation);
		Projectile PROJECTILE = new Projectile(PROJECTILE_ID, 50, 50, 1, 60, 0, 15, 0);
		PROJECTILE.send(npc, target);
		final Position dest = target.getPosition().copy();
		int delay = 60;
		if (npc.getCentrePosition().distance(dest) == 1) {
			delay = 60;
		} else if (npc.getCentrePosition().distance(dest) <= 5) {
			delay = 80;
		} else if (npc.getCentrePosition().distance(dest) <= 8) {
			delay = 100;
		} else {
			delay = 120;
		}
		throttleFarcast(npc, target, delay, dest, Prayer.PROTECT_FROM_MAGIC);
		return false;
	}

	private void throttleFarcast(Entity attacker, Entity victim, int delay, final Position dest, Prayer prayer) {
		TheatreNPC n = (TheatreNPC) npc;
		npc.addEvent(e-> {
			e.delay((delay / 20) - 2);
			for (Player player : attacker.getPosition().getRegion().players) {
				int max = !target.player.getPrayer().isActive(prayer) ? (npc.getId() == 8347 ? 24 : 17) : 0;
				player.hit(new Hit().randDamage(max));
			}
		});
	}
}

class RangeCombat extends NPCCombat {

	private final int PROJECTILE_ID = 10;

	@Override
	public void init() {

	}

	@Override
	public void follow() {

	}

	@Override
	public boolean attack() {
		npc.animate(npc.getDef().combatInfo.attack_animation);
		Projectile PROJECTILE = new Projectile(PROJECTILE_ID, 50, 50, 1, 60, 0, 15, 0);
		PROJECTILE.send(npc, target);
		final Position dest = target.getPosition().copy();
		int delay = 60;
		if (npc.getCentrePosition().distance(dest) == 1) {
			delay = 60;
		} else if (npc.getCentrePosition().distance(dest) <= 5) {
			delay = 80;
		} else if (npc.getCentrePosition().distance(dest) <= 8) {
			delay = 100;
		} else {
			delay = 120;
		}
		throttleFarcast(npc, target, delay, dest, Prayer.PROTECT_FROM_MISSILES);
		return false;
	}

	private void throttleFarcast(Entity attacker, Entity victim, int delay, final Position dest, Prayer prayer) {
		TheatreNPC n = (TheatreNPC) npc;
		npc.addEvent(e-> {
			e.delay((delay / 20) - 2);
			for (Player player : attacker.getPosition().getRegion().players) {
				int max = !target.player.getPrayer().isActive(prayer) ? (npc.getId() == 8346 ? 24 : 17) : 0;
				player.hit(new Hit().randDamage(max));
			}
		});
	}
}

public class NylocasSpawnPlugin extends TheatreNPC
{
	
	private int cycles = 0;
	
	private Entity target;

	private List<Entity> faulted = new ArrayList<>();

	public boolean bossRoom, damaged;

	public NylocasSpawnPlugin(int id, Position location)
	{
		super(id, location.copy());
		combat = null;
		getRouteFinder().customClipUtils = ClipUtils.NONE;
		walkBounds = new Bounds(location, 28);
	}

	@Override
	public NPCCombat getCombat() {
		if (combat == null) {
			initCombat();
		}
		return combat;
	}

	public void initCombat() {
		combat = id == 8342 || id == 8345 ? new BasicCombat().init(this, getDef().combatInfo) : id == 8343 || id == 8346 ? new MagicCombat().init(this, getDef().combatInfo) : new RangeCombat().init(this, getDef().combatInfo);
		combat.init(this, getDef().combatInfo);
	}

	@Override
	public void process() {
		super.process();
		if (target != null && walkTo == null && !getCombat().hasAttackDelay()) {
			getCombat().setTarget(target);
		}
		if (++cycles >= 15 && cycles %5 == 0) {
			int roll = Misc.random(5);
			if ((roll >= 3 && !bossRoom) || (roll == 2)) {
				explode();
				Hit h = new Hit().fixedDamage(getCombat().getStat(StatType.Hitpoints).currentLevel);
				h.attackStyle = getId() == 8342 || getId() == 8345 ? AttackStyle.STAB : getId() == 8344 || getId() == 8347 ? AttackStyle.MAGIC : AttackStyle.RANGED;
				hit(h);
			}
		}
	}

	@Override
	public int hit(Hit... hit) {
		AttackStyle st = hit[0].attackStyle == null ? AttackStyle.CRUSH : hit[0].attackStyle;
		AttackStyle type = getId() == 8342 || getId() == 8345 ? AttackStyle.STAB : getId() == 8344 || getId() == 8347 ? AttackStyle.MAGIC : AttackStyle.RANGED;
		AttackStyle current = st.isMagic() ?  AttackStyle.MAGIC : st.isRanged() ? AttackStyle.RANGED : AttackStyle.STAB;
		for (Hit h : hit) {
			if (faulted.contains(h.attacker)) {
				return 0;
			}
		}
		if (type != current) {
			for (Hit h : hit) {
				if (h.attacker != null && !faulted.contains(h.attacker)) {
					faulted.add(h.attacker);
				}
			}
			return 0;
		}
		int damage = super.hit(hit);
		if (getCombat().getStat(StatType.Hitpoints).currentLevel <= 0) {
			damaged = true;
			if (hit[0].attacker != null && hit[0].attacker.player != null) {
				explode();
			}
			if (hit[0].attacker != null && hit[0].attacker.player != null && getId() >= 8345) {
				NylocasSpawnPlugin spawn = new NylocasSpawnPlugin(8342 + (getId() - 8345), getCentrePosition().copy());
				spawn.setNodes(getNodes());
				spawn.register();

				NylocasSpawnPlugin spawn1 = new NylocasSpawnPlugin(8342 + (getId() - 8345), getPosition().copy());
				spawn1.setNodes(getNodes());
				spawn1.register();

			}
		}
		return damage;
	}

	private void explode() {
		if (damaged) {
			return;
		}
		int radius = getId() < 8345 && !bossRoom ? 1 : 4;
		List<Entity> targets = getPossibleTargets(radius, true, true);
		if (targets.size() > 0) {
			for (Entity t : targets) {
				if (t.npc != null) continue;
				Hit h = new Hit().fixedDamage(bossRoom ? Misc.random(63) :  Misc.random(t.player.getPrayer().isActive(getId() == 8342 || getId() == 8345 ? Prayer.PROTECT_FROM_MELEE : getId() == 8344 || getId() == 8347 ? Prayer.PROTECT_FROM_MAGIC : Prayer.PROTECT_FROM_MISSILES) ? 0 : 18));
				h.attackStyle = getId() == 8342 || getId() == 8345 ? AttackStyle.STAB : getId() == 8344 || getId() == 8347 ? AttackStyle.MAGIC : AttackStyle.RANGED;
				t.hit(h);
			}
		}
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
		getRouteFinder().routeEntity(target);
	}

}
