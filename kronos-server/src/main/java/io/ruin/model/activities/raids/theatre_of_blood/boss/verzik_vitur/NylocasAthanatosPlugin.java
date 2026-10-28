package io.ruin.model.activities.raids.theatre_of_blood.boss.verzik_vitur;

import io.ruin.model.World;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.activities.raids.theatre_of_blood.boss.nylocas_vasilias.NylocasVasiliasCombat;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;

import java.util.List;

public class NylocasAthanatosPlugin extends TheatreNPC {
	
	public VerzikViturPlugin v;

	private int cycles = 0;
	
	public NylocasAthanatosPlugin(VerzikViturPlugin v, Position spawn) {
		super(8384, spawn.copy());
		this.v = v;
		getCombat().delayAttack(3);
	}

	@Override
	public NylocasAthanatosCombat getCombat() {
		if (combat == null || !(combat instanceof NylocasAthanatosCombat)) {
			combat = new NylocasAthanatosCombat(this);
			combat.init(this, getDef().combatInfo);
		}
		return (NylocasAthanatosCombat) combat;
	}

	@Override
	public void register() {
		super.register();
		animate(8079);
	}
	
	@Override
	public void process() {
		super.process();
		if (++cycles >= 5 && cycles %5 == 0) {
			int roll = Misc.random(5);
			if (roll == 3) {
				explode();
				return;
			}
			if (v != null && !getCombat().hasAttackDelay() && !getCombat().isDead()) {
				int projectile = Misc.random(10) > 5 ? 1587 : 1588;
				animate(getCombat().getInfo().attack_animation);
				Projectile PROJECTILE = new Projectile(projectile, 110, 0, 0, 60, 0, 15, 0);
				PROJECTILE.send(this, v);
				World.startEvent(e->{
					Hit h = new Hit(HitType.HEAL).randDamage(22);
					h.attacker = this.npc = this;
					v.hit(h);
				});
			}
		}
	}

	@Override
	public int hit(Hit... hits) {
		int damage = super.hit(hits);
		Entity attacker = null;
		for (Hit h : hits) {
			if (h.attacker != null) {
				attacker = h.attacker;
			}
		}
		boolean poisoned = false;
		if (attacker != null && attacker.player != null && attacker.player.getEquipment().get(Equipment.SLOT_WEAPON) !=  null) {
			int[] poison = new int[] { 12931, 12006, 12926, 12899, 12904, 5698,	5680, 1232, 13267, 13269 };
			for (int w : poison) {
				if (attacker.player.getEquipment().get(Equipment.SLOT_WEAPON).getId() == w) {
					poisoned = true;
				}
			}
		}
		if (poisoned) {
			v.hit(new Hit().fixedDamage(73));
			hit(new Hit().fixedDamage(getCombat().getStat(StatType.Hitpoints).currentLevel));
			return damage;
		}
		if (attacker != null) {
			getCombat().setTarget(attacker);
		}
		if (getCombat().getStat(StatType.Hitpoints).currentLevel <= 0) {
			destroy();
		}
		return damage;
	}

	private void explode() {
		animate(getCombat().getInfo().death_animation);
		List<Entity> targets = getPossibleTargets(3, true, false);
		if (targets.size() > 0) {
			for (Entity t : targets) {
				if (t.npc != null) continue;
				Hit h = new Hit().fixedDamage(Misc.random(63));
				h.attackStyle = getId() == 8342 || getId() == 8345 ? AttackStyle.STAB : getId() == 8342 || getId() == 8345 ? AttackStyle.MAGIC : AttackStyle.RANGED;
				t.hit(h);
			}
		}
		startEvent(e -> {
			e.delay(3);
			destroy();
		});
	}

}
