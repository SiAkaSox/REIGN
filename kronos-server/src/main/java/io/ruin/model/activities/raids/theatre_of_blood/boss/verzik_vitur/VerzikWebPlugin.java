package io.ruin.model.activities.raids.theatre_of_blood.boss.verzik_vitur;

import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;

import java.util.ArrayList;
import java.util.List;



public class VerzikWebPlugin extends TheatreNPC {
	
	private GameObject render;
	private List<Player> stuck = new ArrayList<Player>();
	
	private int cycle = 0;

	@Override
	public boolean isMovementBlocked(boolean message, boolean ignoreFreeze) {
		return true;
	}

	@Override
	public void process() {
		super.process();
		if (++cycle > 5 && cycle %5 == 0 && Misc.random(3) == 1) {
			destroy();
			render.remove();
			for (Player p : stuck) {
				p.remove("busy");
			}
			stuck.clear();
			return;
		}
		for (Player p : getPosition().getRegion().players) {
			if (!stuck.contains(p) && p.getPosition().equals(getPosition())) {
				p.stun(60*60, true);
				stuck.add(p);
			}
		}
	}
	
	@Override
	public int hit(Hit... hit) {
		int d = super.hit(hit);
		if (getCombat().getStat(StatType.Hitpoints).currentLevel <= d || getCombat().isDead()) {
			render.remove();
			for (Player p : stuck) {
				p.resetStun();
			}
			stuck.clear();
		}
		return d;
	}
	
	@Override
	public void destroy() {
		super.destroy();
		render.remove();
		for (Player p : stuck) {
			p.resetStun();
		}
		stuck.clear();
	}

	public VerzikWebPlugin(int id, Position location) {
		super(id, location);
		render = new GameObject(32734, location.copy(), 10, 0);
		render.spawn();
	}

}
