package io.ruin.model.skills.slayer;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.skills.slayer.master.Duradel;
import io.ruin.model.skills.slayer.master.Konar;
import io.ruin.model.skills.slayer.master.Nieve;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public enum SlayerCreature {
	MONKEYS(1, 1, 1, "monkey"),
	GOBLINS(2, 1, 1, "goblin"),
	RATS(3, 1, 1, "rat"),
	SPIDERS(4, 1, 1, "spider"),
	BIRDS(5, 1, 1, "bird"),
	COWS(6, 1, 5, "cow"),
	SCORPIONS(7, 1, 7, "scorpion"),
	BATS(8, 1, 5, "bat"),
	WOLVES(9, 1, 20, "wolf"),
	ZOMBIES(10, 1, 10, "zombie"),
	SKELETONS(11, 1, 15, "skeleton"),
	GHOSTS(12, 1, 13, "ghost"),
	BEARS(13, 1, 13, "bear"),
	HILL_GIANTS(14, 1, 25, "hillgiant"),
	ICE_GIANT(15, 1, 50, "icegiant"),
	FIRE_GIANTS(16, 1, 65, "firegiants"),
	MOSS_GIANTS(17, 1, 40, "mossgiant"),
	TROLLS(18, 1, 60, "trolls"),
	ICE_WARRIOR(19, 1, 45,  "icewarrior"),
	OGRES(20, 1, 40, "ogre"),
	HOBGOBLIN(21, 1, 20, "hobgoblin"),
	DOG(22, 1, 15, "dog"),
	GHOUL(23, 1, 25, "ghoul"),
	GREEN_DRAGONS(24, 1, 52, "greendragon"),
	BLUE_DRAGONS(25, 1, 65, "bluedragons"),
	RED_DRAGON(26, 1, 68, "reddragons"),
	BLACK_DRAGONS(27, 1, 80, "blackdragons"),
	LESSER_DEMONS(28, 1, 60, "lesserdemon"),
	GREATER_DEMONS(29, 1, 75, "greaterdemons"),
	BLACK_DEMONS(30, 1, 80, "blackdemons"),
	HELLHOUNDS(31, 1, 75, "hellhounds"),
	SHADOW_WARRIOR(32, 1, 60, "shadowwarrior"),
	WEREWOLVES(33, 1, 60, "werewolf"),
	VAMPYRE(34, 1, 35, "vampyre"),
	DAGANNOTH(35, 1, 75, "dagannoth"),
	TUROTH(36, 55, 60, "turoth"),
	CAVE_CRAWLER(37, 10, 10, "cavecrawler"),
	BANSHEE(38,15,20, "banshee"),
	CRAWLING_HANDS(39, 5, 10, "crawlinghand"),
	INFERNAL_MAGE(40, 45, 40, "infernalmage"),
	ABERRANT_SPECRES(41, 60, 65, "aberrantspectres"),
	ABYSSAL_DEMON(42, 85, 85, "abyssaldemons"),
	BASILISKS(43, 40, 40, "basilisk", (p, s) -> s.getNpcId() == Konar.KONAR || s.getNpcId() == Duradel.DURADEL || s.getNpcId() == Nieve.NIEVE),
	COCKATRICE(44, 25, 25, "cockatrice"),
	KURASK(45, 70, 65, "kurask"),
	GARGOYLE(46, 75, 80, "gargoyles"),
	PYREFIEND(47, 30, 25, "pyrefiend"),
	BLOODVELDS(48, 50, 50, "bloodveld"),
	DUST_DEVILS(49, 65, 70, "dustdevils"),
	JELLIES(50, 52, 57, "jelly"),
	ROCKSLUG(51, 20, 20, "rockslug"),
	NECHRYAEL(52, 80, 85, "nechryael"),
	KALPHITE(53, 1, 15, "kalphite"),
	EARTH_WARRIORS(54, 1, 35, "earthwarrior"),
	OTHERWORLD_BEINGS(55, 1, 40, "otherworldbeings"),
	ELVES(56, 1, 70, "elves"),
	DWARVES(57, 1, 6, "dwarf"),
	BRONZE_DRAGONS(58, 1, 75, "bronzedragon"),
	IRON_DRAGONS(59, 1, 80, "irondragons"),
	STEEL_DRAGONS(60, 1, 85, "steeldragons"),
	WALL_BEAST(61, 35, 30, "wallbeast"),
	CAVE_SLIME(62, 17, 15, "caveslime"),
	CAVE_BUG(63, 7, 1, "cavebug"),
	SHADE(64, 1, 30, "shade"),
	CROCODILE(65, 1, 50, "crocodile"),
	DARK_BEASTS(66, 90, 90, "darkbeasts"),
	MOGRE(67, 32, 30, "mogre"),
	LIZARD(68, 22, 1, "lizard"),
	FEVER_SPIDER(69, 42, 40, "feverspider"),
	HARPIE_BUG_SWARM(70, 33, 45, "harpiebugswarm"),
	SEA_SNAKE(71, 40, 50, "sea nake"),
	WYVERN(72, 72, 70, "skeletalwyvern"),
	KILLERWATT(73, 37, 50, "Killerwatt"),
	MUTATED_ZYGOMITES(74, 57, 60, "mutatedzygomites"),
	ICEFIENDS(75, 1, 20, "icefiend"),
	MINOTAURS(76, 1, 7, "minotaur"),
	FLESH_CRAWLER(77, 1, 15, "fleshcrawler"),
	CATABLEPON(78, 34, 1, "catablepon"),
	ANKOUS(79, 1, 40, "ankou"),
	CAVE_HORRORS(80, 58, 85, "cavehorrors"),
	JUNGLE_HORROR(81, 1, 65, "junglehorror"),
	SUQAH(83, 1, 1, "suqahs"),
	BRINE_RATE(84, 47, 45, "brinerats"),
	MINIONS_OF_SCABARAS(85, 1, 85, "minionsofscabaras"),
	TERROR_DOGS(86, 40, 60, "terrordog"),
	MOLANISK(87, 39, 50, "molanisk"),
	WATERFIEND(88, 1, 75, "waterfiend"),
	SPIRITUAL_CREATURES(89, 63, 60, "spiritualcreatures"),
	LIZARDMEN(90, 1, 45, "lizardmen", (p, s) -> Config.REPTILE_GOT_RIPPED.get(p) != 0),
	MAGIC_AXES(91, 1, 1, "magicaxe"),
	CAVE_KRAKEN(92, 87, 80, "cavekraken"),
	MITHRIL_DRAGON(93, 1, 85, "mithrildragons", (p, s) -> Config.I_HOPE_YOU_MITH_ME.get(p) != 0),
	AVIANSIES(94, 1, 60, "aviansies", (p, s) -> Config.WATCH_THE_BIRDIE.get(p) != 0),
	SMOKE_DEVILS(95, 93, 85, "smokedevils"),
	TZHARR(96, 1, 1, "tzhaar", (p, s) -> Config.HOT_STUFF.get(p) != 0),
	BOSSES(98, 1, 1, "bosses", (p, s) -> Config.LIKE_A_BOSS.get(p) != 0),
	MAMMOTHS(99, 1, 1, "mammoth"),
	ROGUES(100, 1, 1, "rogue"),
	ENTS(101, 1, 1, "ents"),
	BANDITS(102, 1, 1, "bandit"),
	DARK_WARRIORS(103, 1, 1, "darkwarrior"),
	LAVA_DRAGONS(104, 1, 1, "lavadragon"),
	FOSSIL_ISLAND_WYVERNS(106, 66, 60, "fossilislandwyvern"),
	REVENANTS(107, 1, 1, "revenant"),
	ADAMANT_DRAGONS(108, 1, 1, "adamantdragons"),
	RUNE_DRAGONS(109, 1, 1, "runedragons"),
	CHAOS_DRUID(110, 1, 1, "chaosdruid"),
	WYRMS(111, 62, 1, "wyrms"),
	DRAKES(112, 84, 1, "drake"),
	HYDRAS(113, 95, 1, "hydras"),

	;
	
	private final int uid;
	private final int req;
	private final int cbreq;
	private final String category;

	public final BiFunction<Player, SlayerMaster, Boolean> canAssign;

	private static Map<Integer, SlayerCreature> lookup = null;

	SlayerCreature(int uid, int req, int cbreq, String category) {
		this.uid = uid;
		this.req = req;
		this.cbreq = cbreq;
		this.category = category;
		this.canAssign = null;
	}

	SlayerCreature(int uid, int req, int cbreq, String category, BiFunction<Player, SlayerMaster, Boolean> canAssign) {
		this.uid = uid;
		this.req = req;
		this.cbreq = cbreq;
		this.category = category;
		this.canAssign = canAssign;
	}

	public static SlayerCreature lookup(int uid) {
		if(lookup == null) {
			Map<Integer, SlayerCreature> temp = new HashMap<>();
			
			for(SlayerCreature sc : values()) {
				temp.put(sc.uid, sc);
			}
			
			lookup = temp;
		}
		
		return lookup.get(uid);
	}

	public boolean contains(NPC npc) {
		if(npc.getDef().combatInfo == null || npc.getDef().combatInfo.slayer_tasks == null)
			return false;

		for(String s : npc.getDef().combatInfo.slayer_tasks){
			if(category.toLowerCase().contains(s.toLowerCase()))
				return true;
		}

		return false;
	}
	
	public int getUid() {
		return uid;
	}

	public int getReq() {
		return req;
	}

	public int getCbreq() {
		return cbreq;
	}
}
