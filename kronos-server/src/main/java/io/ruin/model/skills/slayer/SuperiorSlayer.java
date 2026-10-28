package io.ruin.model.skills.slayer;

import io.ruin.utility.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;

class SuperiorSlayer {
	static void trySpawn(Player player, SlayerCreature task, NPC npc) {
		if(Config.BIGGER_AND_BADDER.get(player) == 0) {
			return;
		}
		
		int superior = getSuperior(task, npc.getId());
		
		if(superior == -1 || superior == npc.getId())
			return;
		
		int odds = 100;
		
		if(Random.get(odds) == 1) {
			NPC boss = new NPC(superior);

			boss.spawn(npc.getPosition());
			
			player.sendMessage("<col=ff0000>A superior foe has appeared...</col>");
		}
	}
	
	static int getSuperior(SlayerCreature task, int npc) {
		switch(task) {
			case BASILISKS:
				return 7395;
			case COCKATRICE:
				return 7393;
			case BANSHEE:
				if(npc == 7272)
					return 7391;
				return 7390;
			case ABERRANT_SPECRES:
				if(npc == 7279)
					return 7403;
				return 7402;
			case ABYSSAL_DEMON:
				return 7410;
			case BLOODVELDS:
				if(npc == 7276)
					return 7398;
				return 7397;
			case CAVE_CRAWLER:
				return 7389;
			case CAVE_HORRORS:
				return 7401;
			case CRAWLING_HANDS:
				return 7388;
			case DUST_DEVILS:
				return 7404;
			case GARGOYLE:
				return 7407;
			case JELLIES:
				if(npc == 7277)
					return 7400;
				return 7399;
			case KURASK:
				return 7405;
			case SMOKE_DEVILS:
				return 7406;
			case ROCKSLUG:
				return 7392;
			case DARK_BEASTS:
				return 7409;
			case PYREFIEND:
				return 7394;
			default:
				return -1;
		}
	}
}
