package io.ruin.model.inter.combattasks;

import static io.ruin.model.inter.combattasks.CombatTaskDifficulty.*;

public enum CombatTaskList {

    //27 Easy Tasks
    NOXIOUS_FOE(EASY, "Noxious Foe", "Kill an Aberrant Spectre.", 1),
    BARROWS_NOVICE(EASY, "Barrows Novice", "Open the Barrows chest 10 times.", 10),
    DEFENCE_WHAT_DEFENCE(EASY, "Defence? What Defence?", "Kill any barrows brother using only magical damage.", 1),
    BIG_BLACK_AND_FIERY(EASY, "Big, Black and Fiery", "Kill a Black Dragon.", 1),
    THE_DEMONIC_PUNCHING_BAG(EASY, "The Demonic Punching Bag", "Kill a Bloodveld.", 1),
    BRYOPHYTA_NOVICE(EASY, "Bryophyta Novice", "Kill Bryophyta once.", 1),
    PROTECTION_FROM_MOSS(EASY, "Protection from Moss", "Kill Bryophyta with the Protect from Magic prayer active.", 1),
    PREPARATION_IS_KEY(EASY, "Preparation is Key", "Kill Bryophyta without suffering any poison damage.", 1),
    A_SLOW_DEATH(EASY, "A Slow Death", "Kill Bryophyta with either poison or venom being the final source of damage.", 1),
    DERANGED_ARCHAEOLOGIST_NOVICE(EASY, "Deranged Archaeologist Novice", "Kill the Deranged Archaeologist 10 times.", 1),

    THE_WALKING_VOLCANO(EASY, "The Walking Volcano", "Kill a fire giant.", 1),
    INTO_THE_DEN_OF_GIANTS(EASY, "Into the Den of Giants", "Kill a Hill Giant, Moss Giant and Fire Giant in the Giant Cave within the Shayzien region.", 1),
    A_GREATER_FOE(EASY, "A Greater Foe", "Kill a Greater Demon.", 1),
    NOT_SO_GREAT_AFTER_ALL(EASY, "Not So Greater After All", "Finish off a Greater Demon with a demonbane weapon.", 1),
    A_DEMONS_BEST_FRIEND(EASY, "A Demons Best Friend", "Kill a Hellhound.", 1),
    OBOR_NOVICE(EASY, "Obor Novice", "Kill Obor once.", 1),
    SLEEPING_GIANT(EASY, "Sleeping Giant", "Kill Obor whilst he is immobilized.", 1),
    KING_BLACK_DRAGON_NOVICE(EASY, "King Black Dragon Novice", "Kill the King Black Dragon 10 times.", 1),
    A_SCALEY_ENCOUNTER(EASY, "A Scaley Encounter", "Kill a Lizardman Shaman.", 1),
    SHAYZIEN_PROTECTOR(EASY, "Shayzien Protector", "Kill a Lizardman Shaman in Molch which has not dealt damage to anyone.", 1),

    GIANT_MOLE_NOVICE(EASY, "Giant Mole Novice", "Kill the Giant Mole 10 times.", 1),
    SARACHNIS_NOVICE(EASY, "Sarachnis Novice", "Kill Sarachnis 10 times.", 1),
    WINTERTODT_NOVICE(EASY, "Wintertodt Novice", "Subdue the Wintertodt 5 times.", 1),
    MUMMY(EASY, "Mummy!", "Heal a Pyromancer at Wintertodt after they have fallen.", 1),
    HANDYMAN(EASY, "Handyman", "Repair a brazier which has been destroyed by the Wintertodt.", 1),
    COSY(EASY, "Cosy", "Subdue the Wintertodt with four pieces of warm equipment equipped.", 1),
    A_SLITHERY_ENCOUNTER(EASY, "A Slithery Encounter", "Kill a Wyrm.", 1),

    //34 Medium Tasks
    PRAY_FOR_SUCCESS(MEDIUM, "Pray For Success", "Kill all six Barrows Brothers and loot the Barrows chest without taking any damage.", 1),
    BARROWS_CHAMPION(MEDIUM, "Barrows Champion", "Open the Barrows chest 25 times.", 25),
    BRUTAL_BIG_BLACK_AND_FIERY(MEDIUM, "Brutal, Big, Black and Fiery", "Kill a Brutal Black Dragon.", 1),
    BRYOPHYTA_CHAMPION(MEDIUM, "Bryophyta Champion", "Kill Bryophyta 5 times.", 5),
    SORRY_WHAT_WAS_THAT(MEDIUM, "Sorry, What Was That?", "Kill the Chaos Fanatic without anyone being hit by his explosion attack.", 1),
    CHAOS_FANATIC_CHAMPION(MEDIUM, "Chaos Fanatic Champion", "Kill the Chaos Fanatic 10 times.", 10),
    ID_RATHER_NOT_LEARN(MEDIUM, "I'd Rather Not Learn", "Kill the Crazy Archaeologist without being hit by his Rain of Knowledge attack.", 1),
    MAGE_OF_THE_RUINS(MEDIUM, "Mage of the Ruins", "Kill the Crazy Archaeologist with only magical attacks.", 1),
    CRAZY_ARCHAEOLOGIST_CHAMPION(MEDIUM, "Crazy Archaeologist Champion", "Kill the Crazy Archaeologist 10 times.", 10),
    DAGANNOTH_PRIME_CHAMPION(MEDIUM, "Dagannoth Prime Champion", "Kill Dagannoth Prime 10 times.", 10),

    A_FROZEN_KING(MEDIUM, "A Frozen King", "Kill Dagannoth Rex whilst he is immoblized.", 1),
    DAGANNOTH_REX_CHAMPION(MEDIUM, "Dagannoth Rex Champion", "Kill Dagannoth Rex 10 times.", 1),
    DAGANNOTH_SUPREME_CHAMPION(MEDIUM, "Dagannoth Supreme Champion", "Kill Dagannoth Supreme 10 times.", 1),
    ID_RATHER_BE_ILLITERATE(MEDIUM, "I'd Rather Be Illiterate", "Kill the Deranged Archaeologist without anyone being hit by his Learn to Read attack.", 1),
    MAGE_OF_THE_SWAMP(MEDIUM, "Mage of the Swamp", "Kill the Deranged Archaeologist with only magical attacks.", 1),
    DERANGED_ARCHAEOLOGIST_CHAMPION(MEDIUM, "Deranged Archaeologist Champion", "Kill the Deranged Archaeologist 25 times.", 25),
    A_SMASHING_TIME(MEDIUM, "A Smashing Time", "Kill a Gargoyle.", 1),
    AVOIDING_THOSE_LITTLE_ARMS(MEDIUM, "Avoiding Those Little Arms", "Kill the Giant Mole without her damaging anyone.", 1),
    GIANT_MOLE_CHAMPION(MEDIUM, "Giant Mole Champion", "Kill the Giant mole 25 times.", 25),
    ANTIFIRE_PROTECTION(MEDIUM, "Antifire Protection", "Kill the King Black Dragon with an antifire potion active and an antidragon shield equipped.", 1),

    HIDE_PENETRATION(MEDIUM, "Hide Penetration", "Kill the King Black Dragon with a stab weapon.", 1),
    CLAW_CLIPPER(MEDIUM, "Claw Clipper", "Kill the King Black Dragon with the Protect from Melee prayer activated.", 1),
    KING_BLACK_DRAGON_CHAMPION(MEDIUM, "King Black Dragon Champion", "Kill the King Black Dragon 25 times.", 1),
    MASTER_OF_BROAD_WEAPONRY(MEDIUM, "Master of Broad Weaponry", "Kill a Kurask.", 1),
    SQUASHING_THE_GIANT(MEDIUM, "Squashing the Giant", "Kill Obor without taking any damage off prayer.", 1),
    OBOR_CHAMPION(MEDIUM, "Obor Champion", "Kill Obor 5 times.", 5),
    NEWSPAPER_ENTHUSIAST(MEDIUM, "Newspaper Enthusiast", "Kill Sarachnis with a crush weapon.", 1),
    SARACHNIS_CHAMPION(MEDIUM, "Sarachnis Champion", "Kill Sarachnis 25 times.", 25),
    A_FROZEN_FOE_FROM_THE_PAST(MEDIUM, "A Frozen Foe From the Past", "Kill a Skeletal Wyvern", 1),
    DEMONIC_WEAKENING(MEDIUM, "Demonic Weakening", "Kill Skotizo with no altars active.", 1),

    SKOTIZO_CHAMPION(MEDIUM, "Skotizo Champion", "Kill Skotizo once.", 1),
    LEAVING_NO_ONE_BEHIND(MEDIUM, "Leaving No One Behind", "Subdue the Wintertodt without any of the Pyromancers falling.", 1),
    CAN_WE_FIX_IT(MEDIUM, "Can We Fix It?", "Subdue the Wintertodt without allowing all 4 braziers to be broken at the same time.", 1),
    WINTERTODT_CHAMPION(MEDIUM, "Wintertodt Champion", "Subdue the Wintertodt 10 times.", 10),

    //45 Hard Tasks
    DONT_STOP_MOVING(HARD, "Don't Stop Moving", "Kill the Abyssal Sire without taking damage from any miasma pools.", 1),
    DONT_WHIP_ME(HARD, "Don't Whip Me", "Kill the Abyssal Sire without being hit by any external tentacles.", 1),
    THEY_GROW_UP_TOO_FAST(HARD, "They Grow Up Too Fast", "Kill the Abyssal Sire without letting any Scion mature.", 1),
    ABYSSAL_ADEPT(HARD, "Abyssal Adept", "Kill the Abyssal Sire 20 times.", 20),
    FAITHLESS_CRYPT_RUN(HARD, "Faithless Crypt Run", "Kill all six Barrows Brothers and loot the chest without ever having more than 0 prayer pts.", 1),
    JUST_LIKE_THAT(HARD, "Just Like That", "Kill Karil using only damage dealt by special attacks.", 1),
    CALLISTO_ADEPT(HARD, "Callisto Adept", "Kill Callisto 10 times.", 10),
    THE_FLINCHER(HARD, "The Flincher", "Kill the Chaos Elemental without taking any damage from it's attacks.", 1),
    HOARDER(HARD, "Hoarder", "Kill the Chaos Elemental without it unequipping any of your items.", 1),
    CHAOS_ELEMENTAL_ADEPT(HARD, "Chaos Elemental Adept", "Kill the Chaos Elemental 10 times.", 10),

    PRAYING_TO_THE_GODS(HARD, "Praying to the Gods", "Kill the Chaos Fanatic 10 times without leaving the Wilderness.", 10),
    CHAOS_FANATIC_ADEPT(HARD, "Chaos Fanatic Adept", "Kill the Chaos Fanatic 25 times.", 25),
    COMMANDER_SHOWDOWN(HARD, "Commander Showdown", "Finish off Commander Zilyana while all of her bodyguards are dead.", 1),
    COMMAND_ZILYANA_ADEPT(HARD, "Commander Zilyana Adept", "Kill Commander Zilyana 50 times.", 50),
    CRAZY_ARCHAEOLOGIST_ADEPT(HARD, "Crazy Archaeologist Adept", "Kill the Crazy Archaeologist 25 times.", 25),
    DAGANNOTH_PRIME_ADEPT(HARD, "Dagannoth Prime Adept", "Kill Dagannoth Prime 25 times.", 25),
    DAGANNOTH_REX_ADEPT(HARD, "Dagannoth Rex Adept", "Kill Dagannoth Rex 25 times.", 25),
    DAGANNOTH_SUPREME_ADEPT(HARD, "Dagannoth Supreme Adept", "Kill Dagannoth Supreme 25 times.", 25),
    GENERAL_SHOWDOWN(HARD, "General Showdown", "Finish off General Graardor whilst all of his bodyguards are dead.", 1),
    OURG_FREEZER(HARD, "Ourg Freezer", "Kill General Graardor whilst he is immobilized.", 1),

    GENERAL_GRAARDOR_ADEPT(HARD, "General Graardor Adept", "Kill General Graardor 50 times.", 50),
    WHACK_A_MOLE(HARD, "Whack-a-Mole", "Kill the Giant Mole within 10 seconds of her resurfacing.", 1),
    WHY_ARE_YOU_RUNNING(HARD, "Why Are You Running?", "Kill the Giant Mole without her burrowing more than 2 times.", 1),
    DEMONBANE_WEAPONRY_II(HARD, "Demonbane Weaponry II", "Finish off K'ril Tsutsaroth with a demonbane weapon.", 1),
    DEMONIC_SHOWDOWN(HARD, "Demonic Showdown", "Finish off K'ril Tsutsaroth whilst all of his bodyguards are dead.", 1),
    YARR_NO_MORE(HARD, "Yarr No More", "Receive kill-credit for K'ril Tsutsaroth without him using his special attack.", 1),
    KRIL_ADEPT(HARD, "K'ril Tsutsaroth Adept", "Kill K'ril Tsutsaroth 50 times.", 50),
    CHITIN_PENETRATOR(HARD, "Chitin Penetrator", "Kill the Kalphite Queen while her defence was last lowered by you.", 1),
    KALPHITE_QUEEN_ADEPT(HARD, "Kalphite Queen Adept", "Kill the Kalphite Queen 25 times.", 25),
    WHO_IS_THE_KING_NOW(HARD, "Who Is the King Now?", "Kill The King Black Dragon 10 times in a privately rented instance without leaving the instance.", 10),

    KRAKANT_HURT_ME(HARD, "Krakan't Hurt Me", "Kill the Kraken 25 times in a privately rented instance without leaving the room.", 25),
    UNNECESSARY_OPTIMIZATION(HARD, "Unnecessary Optimization", "Kill the Kraken after killing all four tentacles.", 1),
    KRAKEN_ADEPT(HARD, "Kraken Adept", "Kill the Kraken 20 times.", 20),
    AIRBORNE_SHOWDOWN(HARD, "Airborne Showdown", "Finish off Kree'arra whilst all of his bodyguards are dead.", 1),
    KREARRA_ADEPT(HARD, "Kree'arra Adept", "Kill Kree'arra 50 times.", 50),
    INSPECT_REPELLANT(HARD, "Inspect Repellent", "Kill Sarachnis without her dealing damage to anyone.", 1),
    GUARDIANS_NO_MORE(HARD, "Guardians No More", "Kill Scorpia without killing her guardians.", 1),
    I_CANT_REACH_THAT(HARD, "I Can't Reach That", "Kill Scorpia without taking any damage from her.", 1),
    SCORPIA_ADEPT(HARD, "Scorpia Adept", "Kill Scorpia 10 times.", 10),

    SKOTIZO_ADEPT(HARD, "Skotizo Adept", "Kill Skotizo 5 times.", 5),
    VENENATIS_ADEPT(HARD, "Venenatis Adept", "Kill Venenatis 10 times.", 10),
    VETION_ADEPT(HARD, "Vet'ion Adept", "Kill Vet'ion 10 times.", 10),
    WHY_FLETCH(HARD, "Why Fletch?", "Subdue the Wintertodt after earning 3000 or more points.", 1),
    ZULRAH_ADEPT(HARD, "Zulrah Adept", "Kill Zulrah 25 times.", 25),

    //64 Elite Tasks
    PERFECT_SIRE(ELITE, "Perfect Sire", "Kill the Abyssal Sire without taking damage from the tentacles, miasma pools, explosion or the Sire.", 1),
    DEMONIC_REBOUND(ELITE, "Demonic Rebound", "Use the Vengeance spell to reflect the damage from the Abyssal Sire's explosion back to him.", 1),
    RESPIRATORY_RUNNER(ELITE, "Respiratory Runner", "Kill the Abyssal Sire after only stunning him once.", 1),
    ABYSSAL_VETERAN(ELITE, "Abyssal Veteran", "Kill the Abyssal Sire 50 times.", 50),
    ALCHEMICAL_VETERAN(ELITE, "Alchemical Veteran", "Kill the Alchemical Hydra 75 times.", 75),
    REFLECTING_ON_THIS_ENCOUNTER(ELITE, "Reflecting on This Encounter", "Kill a Basilisk Knight.", 1),
    CALLISTO_VETERAN(ELITE, "Callisto Veteran", "Kill Callisto 20 times.", 20),
    ANTI_BITE_MECHANICS(ELITE, "Anti-Bite Mechanics", "Kill Cerberus without taking any melee damage.", 1),
    UNREQUIRED_ANTIFIRE(ELITE, "Unrequired Antifire", "Kill Cerberus without taking damage from any lava pools.", 1),
    GHOST_BUSTER(ELITE, "Ghost Buster", "Kill Cerberus after successfully negating 6 or more attacks from Summoned Souls.", 1),

    CERBERUS_VETERAN(ELITE, "Cerberus Veteran", "Kill Cerberus 75 times.", 75),
    KILL_IT_WITH_FIRE(ELITE, "Kill It with Fire", "Finish off the Ice Demon with a fire spell.", 1),
    BLIZZARD_DODGER(ELITE, "Blizzard Dodger", "Receive kill-credit for the Ice Demon without activating the Protect from Range prayer.", 1),
    CRYO_NO_MORE(ELITE, "Cryo No More", "Receive kill-credit for the Ice Demon without taking any damage.", 1),
    SHAYZIEN_SPECIALIST(ELITE, "Shayzien Specialist", "Receive kill-credit for a Lizardman Shaman without taking damage from any shamans in the room.", 1),
    UNDYING_RAID_TEAM(ELITE, "Undying Raid Team", "Complete a Chambers of Xeric raid without anyone dying.", 1),
    MUTTA_DIET(ELITE, "Mutta-diet", "Kill the Muttadile without letting her or her baby recover hitpoints from the meat tree.", 1),
    TOGETHER_WELL_FALL(ELITE, "Together We'll Fall", "Kill the Vanguards within 10 seconds of the first one dying.", 1),
    PERFECTLY_BALANCED(ELITE, "Perfectly Balanced", "Kill the Vanguards without them resetting their health.", 1),
    CHAMBERS_OF_XERIC_VETERAN(ELITE, "Chambers of Xeric Veteran", "Complete the Chambers of Xeric 25 times.", 25),

    CHAOS_ELEMENTAL_VETERAN(ELITE, "Chaos Elemental Veteran", "Kill the Chaos Elemental 25 times.", 25),
    REMINISCE(ELITE, "Reminisce", "Kill Commander Zilyana in a privately rented instance with melee only.", 1),
    COMMANDER_ZILYANA_VETERAN(ELITE, "Commander Zilyana Veteran", "Kill Commander Zilyana 100 times.", 100),
    CHICKEN_KILLER(ELITE, "Chicken Killer", "Kill the Corporeal Beast solo.", 1),
    FINDING_THE_WEAK_SPOT(ELITE, "Finding the Weak Spot", "Finish off the Corporeal Beast with a Crystal Halberd special attack.", 1),
    HOT_ON_YOUR_FEET(ELITE, "Hot on Your Feet", "Kill the Corporeal Beast without anyone killing the dark core or taking damage from the dark core.", 1),
    CORPOREAL_BEAST_VETERAN(ELITE, "Corporeal Beast Veteran", "Kill the Corporeal Beast 25 times.", 25),
    DEAD_TO_THE_SEER_KING(ELITE, "Death to the Seer King", "Kill Dagannoth Prime whilst under attack by Dagannoth Supreme and Dagannoth Rex.", 1),
    DEAD_TO_THE_WARRIOR_KING(ELITE, "Death to the Warrior King", "Kill Dagannoth Rex whilst under attack by Dagannoth Supreme and Dagannoth Prime.", 1),
    DEAD_TO_THE_ARCHER_KING(ELITE, "Death to the Archer King", "Kill Dagannoth Supreme whilst under attack by Dagannoth Prime and Dagannoth Rex.", 1),

    HITTING_THEM_WHERE_IT_HURTS(ELITE, "Hitting Them Where It Hurts", "Finish off a Demonic Gorilla with a demonbane weapon.", 1),
    IF_GORILLAS_COULD_FLY(ELITE, "If Gorillas Could Fly", "Kill a Demonic Gorilla.", 1),
    FACING_JAD_HEAD_ON(ELITE, "Facing Jad Head-on", "Complete the Fight Caves with only melee.", 1),
    FIGHT_CAVES_VETERAN(ELITE, "Fight Caves Veteran", "Complete the Fight Caves once.", 1),
    FRAGMENT_OF_SEREN_SPEED_TRIALIST(ELITE, "Fragment of Seren Speed-Trialist", "Kill The Fragment of Seren in less than 4 minutes.", 1),
    GALVEK_SPEED_TRIALIST(ELITE, "Galvek Speed-Trialist", "Kill Galvek in less than 3 minutes.", 1),
    OURG_FREEZER_II(ELITE, "Ourg Freezer II", "Kill General Graardor without him attacking any players.", 1),
    GENERAL_GRAARDOR_VETERAN(ELITE, "General Graardor Veteran", "Kill General Graardor 100 times.", 100),
    HARD_HITTER(ELITE, "Hard Hitter", "Kill the Giant Mole with 4 or fewer instances of damage.", 1),
    HALF_WAY_THERE(ELITE, "Half-Way There", "Kill a Jal-Zek within the Inferno.", 1),

    DEMONIC_DEFENCE(ELITE, "Demonic Defence", "Kill K'ril Tsutsaroth in a privately rented instance without taking any of his melee hits.", 1),
    THE_BANE_OF_DEMONS(ELITE, "The Bane of Demons", "Defeat K'ril Tsutsaroth in a privately rented instance using only demonbane weapons.", 1),
    KRIL_VETERAN(ELITE, "K'ril Tsutsaroth Veteran", "Kill K'ril Tsutsaroth 100 times.", 100),
    PRAYER_SMASHER(ELITE, "Prayer Smasher", "Kill the Kalphite Queen using only the Verac's Flail as a weapon.", 1),
    INSECT_DEFLECTION(ELITE, "Insect Deflection", "Kill the Kalphite Queen by using the Vengeance spell as the finishing blow.", 1),
    KALPHITE_QUEEN_VETERAN(ELITE, "Kalphite Queen Veteran", "Kill the Kalphite Queen 50 times.", 50),
    TEN_TACLES(ELITE, "Ten-tacles", "Kill the Kraken 50 times in a privately rented instance without leaving the room.", 50),
    KREEARRA_VETERAN(ELITE, "Kree'arra Veteran", "Kill Kree'arra 100 times.", 100),
    SCORPIA_VETERAN(ELITE, "Scorpia Veteran", "Kill Scorpia 25 times.", 25),
    UP_FOR_THE_CHALLENGE(ELITE, "Up for the Challenge", "Kill Skotizo without equipping a demonbane weapon.", 1),

    DEMON_EVASION(ELITE, "Demon Evasion", "Kill Skotizo without taking any damage.", 10),
    HAZARD_PREVENTION(ELITE, "Hazard Prevention", "Kill the Thermonuclear Smoke Devil without it hitting anyone.", 1),
    THERMONUCLEAR_VETERAN(ELITE, "Thermonuclear Veteran", "Kill the Thermonuclear Smoke Devil 20 times.", 20),
    FACING_JAD_HEAD_ON_III(ELITE, "Facing Jad Head-on III", "Complete TzHaar-Ket-Rak's second challenge with only melee.", 1),
    TZHAAR_KET_RAKS_SPEED_TRIALIST(ELITE, "TzHaar-Ket-Rak's Speed-Trialist", "Complete TzHaar-Ket-Rak's first challenge in less than 45 seconds.", 1),
    THE_II_JAD_CHALLENGE(ELITE, "The II Jad Challenge", "Complete TzHaar-Ket-Rak's second challenge.", 1),
    VENENATIS_VETERAN(ELITE, "Venenatis Veteran", "Kill Venenatis 20 times.", 20),
    VET_ERAN(ELITE, "Vet'eran", "Kill Vet'ion 20 times.", 20),
    STICK_EM_WITH_THE_POINTY_END(ELITE, "Stick 'em With the Pointy End", "Kill Vorkath using melee weapons only.", 1),
    ZOMBIE_DESTROYER(ELITE, "Zombie Destroyer", "Kill Vorkath's zombified spawn without using crumble undead.", 1),

    VORKATH_VETERAN(ELITE, "Vorkath Veteran", "Kill Vorkath 50 times.", 50),
    ZULRAH_SPEED_TRIALIST(ELITE, "Zulrah Speed-Trialist", "Kill Zulrah in less than 1 minute 20 seconds, without a slayer task.", 1),
    SNAKE_REBOUND(ELITE, "Snake Rebound", "Kill Zulrah by using the Vengeance spell as the finishing blow.", 1),
    ZULRAH_VETERAN(ELITE, "Zulrah Veteran", "Kill Zulrah 75 times.", 75),

    //38 Master tasks
    WORKING_OVERTIME(MASTER, "Working Overtime", "Kill the Alchemical Hydra 15 times without leaving the room.", 15),
    ALCHEMICAL_SPEED_CHASER(MASTER, "Alchemical Speed-Chaser", "Kill the Alchemical Hydra in less than 1 minute 45 seconds.", 1),
    ALCLEANICAL_HYDRA(MASTER, "Alcleanical Hydra", "Kill the Alchemical Hydra without taking any damage.", 1),
    THE_FLAME_SKIPPER(MASTER, "The Flame Skipper", "Kill the Alchemical Hydra without letting it spawn a flame wall attack.", 1),
    MIXING_CORRECTLY(MASTER, "Mixing Correctly", "Kill the Alchemical Hydra without empowering it.", 1),
    DONT_FLAME_ME(MASTER, "Don't Flame Me", "Kill the Alchemical Hydra without being hit by the flame wall attack.", 1),
    LIGHTNING_LURE(MASTER, "Lightning Lure", "Kill the Alchemical Hydra without being hit by the lightning attack.", 1),
    UNREQUIRED_ANTIPOISONS(MASTER, "Unrequired Antipoisons", "Kill the Alchemical Hydra without being hit by the acid pool attack.", 1),
    ALCHEMICAL_MASTER(MASTER, "Alchemical Master", "Kill the Alchemical Hydra 150 times.", 150),
    AROOO_NO_MORE(MASTER, "Arooo No More", "Kill Cerberus without any of the Summoned Souls being spawned.", 1),

    CERBERUS_MASTER(MASTER, "Cerberus Master", "Kill Cerberus 150 times.", 150),
    COX_TRIO_SPEED_CHASER(MASTER, "Chambers of Xeric (Trio) Speed-Chaser", "Complete a Chambers of Xeric (Trio) in less than 16 minutes and 30 seconds.", 1),
    COX_FIVE_SCALE_SPEED_CHASER(MASTER, "Chambers of Xeric (5-Scale) Speed-Chaser", "Complete a Chambers of Xeric (5-scale) in less than 15 minutes.", 1),
    COX_SOLO_SPEED_CHASER(MASTER, "Chambers of Xeric (Solo) Speed-Chaser", "Complete a Chambers of Xeric (Solo) in less than 21 minutes.", 1),
    BLIND_SPOT(MASTER, "Blind Spot", "Kill Tekton without taking any damage.", 1),
    UNDYING_RAIDER(MASTER, "Undying Raider", "Complete a Chambers of Xeric solo raid without dying.", 1),
    ANVIL_NO_MORE(MASTER, "Anvil No More", "Kill Tekton before he returns to his anvil for a second time after the fight begins.", 1),
    STOP_DROP_AND_ROLL(MASTER, "Stop Drop and Roll", "Kill Vasa Nistirio before he performs his teleport attack for the second time.", 1),
    PUTTING_IT_OLM_THE_LINE(MASTER, "Putting It Olm the Line", "Complete a Chambers of Xeric solo raid with more than 40,000 points.", 1),
    COX_MASTER(MASTER, "Chambers of Xeric Master", "Complete the Chambers of Xeric 75 times.", 75),

    CORPOREAL_BEAST_MASTER(MASTER, "Corporeal Beast Master", "Kill the Corporeal Beast 50 times.", 50),
    FIGHT_CAVES_SPEED_CHASER(MASTER, "Fight Caves Speed-Chaser", "Complete the Fight Caves in less than 30 minutes.", 1),
    DENYING_THE_HEALERS(MASTER, "Denying the Healers", "Complete the Fight caves without letting any of the Yt-MejKot heal.", 1),
    FIGHT_CAVES_MASTER(MASTER, "Fight Caves Master", "Complete the Fight Caves 5 times.", 5),
    NIBBLERS_BEGONE(MASTER, "Nibblers, Begone!", "Kill Tzkal-Zuk without letting a pillar fall before wave 67.", 1),
    ONE_HUNDRED_TENTACLES(MASTER, "One Hundred Tentacles", "Kill the Kraken 100 times in a private instance without leaving the room.", 100),
    SWOOP_NO_MORE(MASTER, "Swoop No More", "Kill Kree'arra in a private instance without taking any melee damage from the boss or bodyguards.", 1),
    PRECISE_POSITIONING(MASTER, "Precise Positioning", "Kill Skotizo with the final source of damage being a Chinchompa explosion.", 1),
    SUPPLIES_WHO_NEEDS_EM(MASTER, "Supplies? Who Needs 'em?", "Complete TzHaar-Ket-Rak's third challenge without having anything in your inventory.", 1),
    FACING_JAD_HEAD_ON_IV(MASTER, "Facing Jad Head-on IV", "Complete TzHaar-Ket-Rak's fourth challenge with only melee.", 1),

    TZHAAR_KET_RAKS_SPEED_CHASER(MASTER, "TzHaar-Ket-Rak's Speed-Chaser", "Complete TzHaar-Ket-Rak's third challenge in less than 3 minutes.", 1),
    THE_IV_JAD_CHALLENGE(MASTER, "The IV Jad Challenge", "Complete TzHaar-Ket-Rak's fourth challenge.", 1),
    EXTENDED_ENCOUNTER(MASTER, "Extended Encounter", "Kill Vorkath 10 times without leaving his area.", 10),
    VORKATH_SPEED_CHASER(MASTER, "Vorkath Speed-Chaser", "Kill Vorkath in less than 1 minute and 15 seconds.", 1),
    VORKATH_MASTER(MASTER, "Vorkath Master", "Kill Vorkath 100 times.", 100),
    ZULRAH_SPEED_CHASER(MASTER, "Zulrah Speed-Chaser", "Kill Zulrah in less than 1 minute, without a slayer task.", 1),
    PERFECT_ZULRAH(MASTER, "Perfect Zulrah", "Kill Zulrah whilst taking no damage from the following: Snakelings, Clouds, Green or Crimson phase.", 1),
    ZULRAH_MASTER(MASTER, "Zulrah Master", "Kill Zulrah 150 times.", 150),

    //34 Grandmaster tasks
    ALCHEMICAL_SPEED_RUNNER(GRANDMASTER, "Alchemical Speed-Runner", "Kill the Alchemical Hydra in less than 1 minute 20 seconds.", 1),
    NO_PRESSURE(GRANDMASTER, "No Pressure", "Kill the Alchemical Hydra using only Dharok's Greataxe as a weapon with no more than 10 HP.", 1),
    COX_TRIO_SPEED_RUNNER(GRANDMASTER, "Chambers of Xeric (Trio) Speed-Runner", "Complete a Chambers of Xeric (Trio) in less than 14 minutes and 30 seconds.", 1),
    COX_FIVE_SCALE_SPEED_RUNNER(GRANDMASTER, "Chambers of Xeric (5-Scale) Speed-Runner", "Complete a Chambers of Xeric (5-scale) in less than 12 minutes and 30 seconds.", 1),
    COX_SOLO_SPEED_RUNNER(GRANDMASTER, "Chambers of Xeric (Solo) Speed-Runner", "Complete a Chambers of Xeric (Solo) in less than 17 minutes.", 1),
    COX_GRANDMASTER(GRANDMASTER, "Chambers of Xeric Grandmaster", "Complete the Chambers of Xeric 150 times.", 150),
    PEACH_CONJURER(GRANDMASTER, "Peach Conjurer", "Kill Commander Zilyana 50 times in a privately rented instance without leaving the room.", 50),
    ANIMAL_WHISPERER(GRANDMASTER, "Animal Whisperer", "Kill Commander Zilyana in a privately rented instance without taking any damage.", 1),
    FIGHT_CAVES_SPEED_RUNNER(GRANDMASTER, "Fight Caves Speed-Runner", "Complete the Fight Caves in less than 26 minutes and 30 seconds.", 1),
    NO_TIME_FOR_A_DRINK(GRANDMASTER, "No Time for a Drink", "Complete the Fight Caves without losing any prayer points.", 1),

    DENYING_THE_HEALERS_II(GRANDMASTER, "Denying the Healers II", "Complete the Fight Caves without TzTok-Jad being healed by a Yt-HurKot.", 1),
    OURG_KILLER(GRANDMASTER, "Ourg Killer", "Kill General Graardor 15 times in a privately rented instance without leaving the room.", 15),
    KEEP_AWAY(GRANDMASTER, "Keep Away", "Kill General Graardor in a privately rented instance without taking any damage.", 1),
    INFERNO_SPEED_RUNNER(GRANDMASTER, "Inferno Speed-Runner", "Complete the Inferno in less than 65 minutes.", 1),
    JAD_WHAT_ARE_YOU_DOING_HERE(GRANDMASTER, "Jad? What Are You Doing Here?", "Kill Tzkal-Zuk without killing the JalTok-Jad which spawns during wave 69.", 1),
    FACING_JAD_HEAD_ON_II(GRANDMASTER, "Facing Jad Head-on II", "Kill Tzkal-Zuk without equipping any range or mage weapons before wave 69.", 1),
    NIBBLER_CHASER(GRANDMASTER, "Nibbler Chaser", "Kill Tzkal-Zuk without using any magic spells during any wave in the Inferno.", 1),
    BUDGET_SETUP(GRANDMASTER, "Budget Setup", "Kill Tzkal-Zuk without equipping a Twisted Bow within the Inferno.", 1),
    WASNT_EVEN_CLOSE(GRANDMASTER, "Wasn't Even Close", "Kill Tzkal-Zuk without letting your hitpoints fall below 50 during any wave in the Inferno.", 1),
    NO_LUCK_REQUIRED(GRANDMASTER, "No Luck Required", "Kill Tzkal-Zuk without being attacked by TzKal-Zuk and without taking damage from a JalTok-Jad.", 1),

    PLAYING_WITH_JADS(GRANDMASTER, "Playing with Jads", "Complete wave 68 of the Inferno within 30 seconds of the first JalTok-Jad dying.", 1),
    THE_FLOOR_IS_LAVA(GRANDMASTER, "The Floor Is Lava", "Kill Tzkal-Zuk without letting Jal-ImKot dig during any wave in the Inferno.", 1),
    INFERNO_GRANDMASTER(GRANDMASTER, "Inferno Grandmaster", "Complete the Inferno 5 times.", 5),
    ASH_COLLECTOR(GRANDMASTER, "Ash Collector", "Kill K'ril Tsutsaroth 20 times in a privately rented instance without leaving the room.", 20),
    DEMON_WHISPERER(GRANDMASTER, "Demon Whisperer", "Kill K'ril Tsutsaroth in a privately rented instance without ever being hit by his bodyguards.", 1),
    FEATHER_HUNTER(GRANDMASTER, "Feather Hunter", "Kill Kree'arra 30 times in a privately rented instance without leaving the room.", 30),
    THE_WORST_RANGED_WEAPON(GRANDMASTER, "The Worst Ranged Weapon", "Kill Kree'arra by only dealing damage to him with a salamander.", 1),
    IT_WASNT_A_FLUKE(GRANDMASTER, "It Wasn't a Fluke", "Complete TzHaar-Ket-Rak's fifth and sixth challenges back to back without failing.", 1),
    TZHAAR_KET_RAKS_SPEED_RUNNER(GRANDMASTER, "TzHaar-Ket-Rak's Speed-Runner", "Complete TzHaar-Ket-Rak's fifth challenge in less than 5 minutes.", 1),
    THE_VI_JAD_CHALLENGE(GRANDMASTER, "The VI Jad Challenge", "Complete TzHaar-Ket-Rak's sixth challenge.", 1),

    VORKATH_SPEED_RUNNER(GRANDMASTER, "Vorkath Speed-Runner", "Kill Vorkath in less than 54 seconds.", 1),
    THE_FREMENNIK_WAY(GRANDMASTER, "The Fremennik Way", "Kill Vorkath with only your fists.", 1),
    FAITHLESS_ENCOUNTER(GRANDMASTER, "Faithless Encounter", "Kill Vorkath without losing any prayer points.", 1),
    ZULRAH_SPEED_RUNNER(GRANDMASTER, "Zulrah Speed-Runner", "Kill Zulrah in less than 54 seconds, without a slayer task.", 1);


    CombatTaskList(CombatTaskDifficulty difficulty, String title, String description, int amountToKill) {
        this.difficulty = difficulty;
        this.title = title;
        this.description = description;
        this.amountToKill = amountToKill;
    }

    private final CombatTaskDifficulty difficulty;
    private final String title;
    private final String description;
    private final int amountToKill;

    public CombatTaskDifficulty difficulty() {
        return difficulty;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public boolean isComplete(int currentProgress) {
        return currentProgress >= amountToKill;
    }

}