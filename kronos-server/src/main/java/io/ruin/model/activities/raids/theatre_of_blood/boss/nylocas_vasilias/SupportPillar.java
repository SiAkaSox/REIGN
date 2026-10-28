package io.ruin.model.activities.raids.theatre_of_blood.boss.nylocas_vasilias;


import io.ruin.model.World;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;

public class SupportPillar extends TheatreNPC
{
	
	private int[] destroyData;
	
	public SupportPillar(int id, Position location)
	{
		super(id, location);
	}
	
	@Override
	public int hit(Hit... hit) {
		int damage = super.hit(hit);
		boolean dead = getCombat().getStat(StatType.Hitpoints).currentLevel <= 10 || getCombat().isDead();
		if (dead) {
			GameObject replace = GameObject.spawn(32863, new Position(destroyData[0], destroyData[1], 0), 10,destroyData[2]);
			World.startEvent(e -> {
				e.delay(2);
				if (replace.getDef() != null)
				replace.animate(replace.getDef().animationId);
				for (Entity p : getPossibleTargets(64, true, false)) {
					if (!p.isPlayer()) continue;
					p.hit(new Hit().fixedDamage(35));
				}
			});
		}
		return damage;
	}

	public int[] getDestroyData() {
		return destroyData;
	}

	public void setDestroyData(int[] destroyData) {
		this.destroyData = destroyData;
	}

}
