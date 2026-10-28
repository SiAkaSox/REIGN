package io.ruin.model.activities.raids.theatre_of_blood.boss.verzik_vitur;


import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreOfBlood;
import io.ruin.model.activities.raids.theatre_of_blood.TheatrePartiesManager;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Position;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;

public class VerzikWhirlwindPlugin extends TheatreNPC {
	
	private Player player;
	
	private VerzikViturPlugin vitur;
	
	public VerzikWhirlwindPlugin(Position location, Player player, VerzikViturPlugin vitur) {
		super(8386, location.copy());
		this.player = player;
		this.vitur = vitur;
	}

	@Override
	public boolean isMovementBlocked(boolean message, boolean ignoreFreeze) {
		return false;
	}

	@Override
	public void process() {
		super.process();
		if (player != null) {
			if (getPosition().equals(player.getPosition())) {
				int dmg = player.getStats().get(StatType.Hitpoints).currentLevel / 2;
				TheatreOfBlood tb = TheatrePartiesManager.getRaid(getPosition());
				if (!tb.isDead(player)) {
					player.hit(new Hit().fixedDamage(dmg));
					vitur.hit(new Hit(HitType.HEAL).fixedDamage(dmg));
					destroy();
				}
			} else {
				stepAbs(player.getPosition().getX(), player.getPosition().getY(), StepType.NORMAL);
			}
			if (Misc.random(20) == 5) {
				destroy();
			}
		}
	}

}
