package io.ruin.model.entity.player;

import com.google.gson.annotations.Expose;
import io.ruin.content.activities.lms.LastManStandingQueue;
import io.ruin.content.activities.lms.LastManStandingSession;
import io.ruin.content.activities.tournament.Tournament;
import io.ruin.content.activities.tournament.TournamentFightPair;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.activities.mimic.MimicLair;
import io.ruin.model.activities.timerift.IndividualTasks;
import io.ruin.model.activities.timerift.TeamTasks;
import io.ruin.model.activities.timerift.skillingtasks.tasks.fishing.FishingTaskType;
import io.ruin.model.activities.timerift.skillingtasks.tasks.mining.MiningTaskType;
import io.ruin.model.activities.timerift.skillingtasks.tasks.woodcutting.WoodcuttingTaskType;
import io.ruin.model.activities.unusedcontent.ActivityTimer;
import io.ruin.model.activities.minigames.JadChallenge;
import io.ruin.model.activities.barrows.BarrowsBrother;
import io.ruin.model.activities.cluescrolls.ClueSave;
import io.ruin.model.activities.minigames.fightcaves.FightCaves;
import io.ruin.model.activities.minigames.inferno.Inferno;
import io.ruin.model.activities.minigames.pestcontrol.PestControlGame;
import io.ruin.model.activities.pvminstances.PVMInstance;
import io.ruin.model.activities.wilderness.pvp.rated.Party2v2;
import io.ruin.model.activities.wilderness.pvp.rated.ones.Rated1v1;
import io.ruin.model.activities.wilderness.pvp.unrated.Arena1v1;
import io.ruin.model.activities.wilderness.pvp.unrated.Arena2v2;
import io.ruin.model.activities.wilderness.pvp.unrated.BattleGrounds;
import io.ruin.model.activities.unusedcontent.pyramidplunder.PyramidPlunderGame;
import io.ruin.model.activities.raids.xeric.party.Party;
import io.ruin.model.activities.dailytasks.DailyTask;
import io.ruin.model.activities.dailytasks.DailyTaskElite;
import io.ruin.model.activities.dailytasks.DailyTaskHard;
import io.ruin.model.activities.dailytasks.DailyTaskMedium;
import io.ruin.model.activities.wilderness.WildernessObelisk;
import io.ruin.model.combat.WildernessRating;
import io.ruin.model.content.UpgradeMachine;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.handlers.TeleportInterface;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.journal.presets.Preset;
import io.ruin.model.inter.journal.presets.PresetCustom;
import io.ruin.model.inter.journal.toggles.EdgevilleBlacklist;
import io.ruin.model.inter.teleports.TeleportList;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.enchanting.armor.EnchantArmorInfo;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.model.item.actions.impl.storage.EssencePouch;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.impl.CanoeStation;
import io.ruin.model.map.object.actions.impl.FairyRing;
import io.ruin.model.skills.birdhouses.BirdHouseHandler;
import io.ruin.model.skills.construction.House;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.model.skills.construction.seat.Seat;
import io.ruin.model.skills.hunter.traps.Trap;
import io.ruin.model.skills.magic.spells.modern.Teleother;
import io.ruin.model.skills.smithing.SmithBar;
import io.ruin.model.stat.StatType;
import io.ruin.utility.TickDelay;
import io.ruin.utility.TimedList;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Function;

public abstract class PlayerAttributes extends Entity {

    @Expose public boolean amuletOfBloodFuryEffect = true;

    @Expose public BirdHouseHandler birdHouseHandler;

    @Expose public long nexAltarDelay = System.currentTimeMillis();

    @Expose public boolean NexFrozenDoor;

    //Daily Rewards
    @Expose public int currentDailyStreak = 0;
    @Expose public int lastClaimedDay = 0;
    @Expose public int highestDailyStreak = 0;
    public int getCurrentDailyStreak() {
        return currentDailyStreak;
    }

    public void setCurrentDailyStreak(int currentDailyStreak) {
        this.currentDailyStreak = currentDailyStreak;
    }

    public int getHighestDailyStreak() {
        return highestDailyStreak;
    }

    public void setHighestDailyStreak(int highestDailyStreak) {
        this.highestDailyStreak = highestDailyStreak;
    }

    public int getLastClaimedDay() {
        return lastClaimedDay;
    }

    public void setLastClaimedDay(int lastClaimedDay) {
        this.lastClaimedDay = lastClaimedDay;
    }


    @Expose @Getter @Setter public int counters[] = new int[20], raidsDamageCounters[] = new int[20];

    @Expose public boolean escapeCrystalToggle;

    @Expose public boolean debug;
    @Expose @Getter @Setter
    public int tutorialStage;
    @Setter protected Runnable onDialogueContinued;

    @Expose public int slayerLocation = 0;
    @Expose public int taskLocation = 0;
    @Expose public boolean slayerCombatCheck;

    @Expose public boolean sacrificedFireCape;

    @Expose public int lmsPoints;

    @Expose public int castleWarsWins;
    @Expose public boolean castleWarsVoteMultiplier;

    @Expose public int dailyEdgeKills;
    @Expose public int dailyEdgeDate = 0;
    @Expose public int dailyEdgeKillsQuestTab;

    /**
     * Pet Perks
     */
    public boolean bonusChins = false;
    public boolean agilityPet = false;
    public boolean notedLogs = false;
    public boolean notedFish = false;
    public boolean notedOres = false;
    public boolean notedHerbs = false;
    @Expose public int thievingBonus = 1;
    @Expose public int clueChance = 1;
    public boolean princeBlackDragon = false;
    public boolean zulrahSnakeling = false;
    public boolean abyssalOrphan = false;
    public boolean babyMole = false;
    @Expose public int babyLavaDragBonus = 1;
    public boolean callistoCub = false;
    public boolean hellPuppy = false;
    public boolean kalphitePrincess = false;
    public boolean chaosEle = false;
    public boolean darkCore = false;
    public boolean jalNibRek = false;
    @Expose public int midnightandnoonBonus = 1;
    public boolean smokeDevilPet = false;
    public boolean tzrekZuk = false;
    public boolean tzrekJad = false;
    public boolean lilZik = false;
    public boolean riftGuardian = false;
    public boolean tangleroot = false;
    public boolean vorki = false;
    public boolean doubleCox = false;
    public boolean ikkleHydra = false;
    public boolean corporealCritter = false;
    public boolean yungllef = false;
    public boolean vanguard = false;


    @Expose public int perkPoints = 0;


    @Expose public static boolean claimedLano;

    /** barb assault high level gambles (misc colletion log entry) */
    @Expose public int barbarianAssultHighLevelGambles;

    /** Revenant Kills (for collection log entry) */
    @Expose public KillCounter revenantKills = new KillCounter();

    /** Gloughs Experiment Kills (for collection log entry) */
    @Expose public KillCounter gloughsExperimentKills = new KillCounter();

    @Expose public Journal journal = Journal.BESTIARY;

    @Expose public int brimstoneChestsOpened;

    @Expose public int pirateshipcontributedAmount;

    @Expose public int pirateshipCompletions;

    @Expose public int giantsdenMoss;
    @Expose public int giantsdenFire;
    @Expose public int giantsdenHill;

    @Expose public int conquestBonus;

    /**
     * Theatre of Blood
     */
    @Expose public int tobKC;


    /**
     * Galvek
     */
    public ActivityTimer galvekTimer;
    public ActivityTimer serenTimer;
    @Expose public long galvekBestTime;

    @Expose public int targetOverlaySetting = 0;
    public Entity targetOverlayTarget;
    public int targetOverlayResetTicks;

    @Expose public int combatTaskEasyCompleted;
    @Expose public int combatTaskMediumCompleted;
    @Expose public int combatTaskHardCompleted;
    @Expose public int combatTaskEliteCompleted;
    @Expose public int combatTaskMasterCompleted;
    @Expose public int combatTaskGrandmasterCompleted;
    @Expose public int totalTasksCompleted = combatTaskEasyCompleted + combatTaskMediumCompleted + combatTaskHardCompleted +
            combatTaskEliteCompleted + combatTaskMasterCompleted + combatTaskGrandmasterCompleted;

    @Expose public int therevenantCount;
    @Expose public int hailhydraCount;
    @Expose public int loomingThreatCount;

    //Easy tasks
    @Expose public int noxiousFoe;
    @Expose public int barrowsNovice;
    @Expose public int defencewhatDefence;
    @Expose public int bigblackandFiery;
    @Expose public int thedemonicpunchingBag;
    @Expose public int bryophytaNovice;
    @Expose public int protectionfromMoss;
    @Expose public int preparationisKey;
    @Expose public int aslowDeath;
    @Expose public int derangedarchaeologistNovice;
    @Expose public int thewalkingVolcano;
    @Expose public int intothedenofGiants;
    @Expose public int agreaterFoe;
    @Expose public int notsogreatafterAll;
    @Expose public int ademonsbestFriend;
    @Expose public int oborNovice;
    @Expose public int sleepingGiant;
    @Expose public int kingblackdragonNovice;
    @Expose public int ascaleyEncounter;
    @Expose public int shayzienProtector;
    @Expose public int giantmoleNovice;
    @Expose public int sarachnisNovice;
    @Expose public int wintertodtNovice;
    @Expose public int mummy;
    @Expose public int handyMan;
    @Expose public int cosy;
    @Expose public int aslitheryEncounter;

    //Medium tasks
    @Expose public int prayforSuccess;
    @Expose public int barrowsChampion;
    @Expose public int brutalbigblackandFiery;
    @Expose public int bryophytaChampion;
    @Expose public int sorrywhatwasThat;
    @Expose public int chaosfanaticChampion;
    @Expose public int idrathernotLearn;
    @Expose public int mageoftheRuins;
    @Expose public int crazyarchaeologistChampion;
    @Expose public int dagannothprimeChampion;
    @Expose public int afrozenKing;
    @Expose public int dagannothrexChampion;
    @Expose public int dagannothsupremeChampion;
    @Expose public int idratherbeIlliterate;
    @Expose public int mageoftheSwamp;
    @Expose public int derangedarchaeologistChampion;
    @Expose public int asmashingTime;
    @Expose public int avoidingthoselittleArms;
    @Expose public int giantmoleChampion;
    @Expose public int antifireProtection;
    @Expose public int hidePenetration;
    @Expose public int clawClipper;
    @Expose public int kingblackdragonChampion;
    @Expose public int masterofbroadWeaponry;
    @Expose public int squashingtheGiant;
    @Expose public int oborChampion;
    @Expose public int newspaperEnthusiast;
    @Expose public int sarachnisChampion;
    @Expose public int afrozenfoefromthePast;
    @Expose public int demonicWeakening;
    @Expose public int skotizoChampion;
    @Expose public int leavingnooneBehind;
    @Expose public int canwefixIt;
    @Expose public int wintertodtChampion;

    //Hard tasks
    @Expose public int dontstopMoving;
    @Expose public int dontwhipMe;
    @Expose public int theygrowuptooFast;
    @Expose public int abyssalAdept;
    @Expose public int faithlesscryptRun;
    @Expose public int justlikeThat;
    @Expose public int callistoAdept;
    @Expose public int theFlincher;
    @Expose public int hoarder;
    @Expose public int chaoselementalAdept;
    @Expose public int prayingtotheGods;
    @Expose public int chaosfanaticAdept;
    @Expose public int commanderShowdown;
    @Expose public int commanderzilyanaAdept;
    @Expose public int crazyarchaeologistAdept;
    @Expose public int dagannothprimeAdept;
    @Expose public int dagannothrexAdept;
    @Expose public int dagannothsupremeAdept;
    @Expose public int generalShowdown;
    @Expose public int ourgFreezer;
    @Expose public int generalgraardorAdept;
    @Expose public int whackaMole;
    @Expose public int whyareyouRunning;
    @Expose public int demonbaneWeaponryII;
    @Expose public int demonicShowdown;
    @Expose public int yarrnoMore;
    @Expose public int krilAdept;
    @Expose public int chitinPenetrator;
    @Expose public int kalphitequeenAdept;
    @Expose public int whoisthekingNow;
    @Expose public int krakanthurtMe;
    @Expose public int unnecessaryOptimization;
    @Expose public int krakenAdept;
    @Expose public int airborneShowdown;
    @Expose public int krearraAdept;
    @Expose public int insectRepellant;
    @Expose public int guardiansnoMore;
    @Expose public int icantreachThat;
    @Expose public int scorpiaAdept;
    @Expose public int skotizoAdept;
    @Expose public int venenatisAdept;
    @Expose public int vetionAdept;
    @Expose public int whyFletch;
    @Expose public int zulrahAdept;

    //Elite tasks
    @Expose public int perfectSire;
    @Expose public int demonicRebound;
    @Expose public int respiratoryRunner;
    @Expose public int abyssalVeteran;
    @Expose public int alchemicalVeteran;
    @Expose public int reflectingonthisEncounter;
    @Expose public int callistoVeteran;
    @Expose public int antibiteMechanics;
    @Expose public int unrequiredAntifire;
    @Expose public int ghostBuster;
    @Expose public int cerberusVeteran;
    @Expose public int killitwithFire;
    @Expose public int blizzardDodger;
    @Expose public int cryonoMore;
    @Expose public int shayzienSpecialist;
    @Expose public int undyingraidTeam;
    @Expose public int muttaDiet;
    @Expose public int togetherwellFall;
    @Expose public int perfectlyBalanced;
    @Expose public int chambersofxericVeteran;
    @Expose public int chaoselementalVeteran;
    @Expose public int reminisce;
    @Expose public int commanderzilyanaVeteran;
    @Expose public int chickenKiller;
    @Expose public int findingtheweakSpot;
    @Expose public int hotonyourFeet;
    @Expose public int corporealbeastVeteran;
    @Expose public int deathtotheseerKing;
    @Expose public int deathtothewarriorKing;
    @Expose public int deathtothearcherKing;
    @Expose public int hittingthemwhereitHurts;
    @Expose public int ifgorillascouldFly;
    @Expose public int facingjadheadOn;
    @Expose public int fightcavesVeteran;
    @Expose public int fragmentofserenspeedTrialist;
    @Expose public int galvekspeedTrialist;
    @Expose public int ourgfreezerIi;
    @Expose public int generalgraardorVeteran;
    @Expose public int hardHitter;
    @Expose public int halfwayThere;
    @Expose public int demonicDefence;
    @Expose public int thebaneofDemons;
    @Expose public int krilVeteran;
    @Expose public int prayerSmasher;
    @Expose public int insectDeflection;
    @Expose public int kalphitequeenVeteran;
    @Expose public int tenTacles;
    @Expose public int kreearraVeteran;
    @Expose public int scorpiaVeteran;
    @Expose public int upfortheChallenge;
    @Expose public int demonEvasion;
    @Expose public int hazardPrevention;
    @Expose public int thermonuclearVeteran;
    @Expose public int facingjadheadonIii;
    @Expose public int tzhaarketraksSpeedTrialist;
    @Expose public int theiijadChallenge;
    @Expose public int venenatisVeteran;
    @Expose public int vetEran;
    @Expose public int stickemwiththepointyEnd;
    @Expose public int zombieDestroyer;
    @Expose public int vorkathVeteran;
    @Expose public int zulrahspeedTrialist;
    @Expose public int snakeRebound;
    @Expose public int zulrahVeteran;

    //Master tasks
    @Expose public int workingOvertime;
    @Expose public int alchemicalspeedChaser;
    @Expose public int alcleanicalHydra;
    @Expose public int theflameSkipper;
    @Expose public int mixingCorrectly;
    @Expose public int dontflameMe;
    @Expose public int lightningLure;
    @Expose public int unrequiredAntipoisons;
    @Expose public int alchemicalMaster;
    @Expose public int arooonoMore;
    @Expose public int cerberusMaster;
    @Expose public int coxtriospeedChaser;
    @Expose public int coxfivescalespeedChaser;
    @Expose public int coxsolospeedChaser;
    @Expose public int blindSpot;
    @Expose public int undyingRaider;
    @Expose public int anvilnoMore;
    @Expose public int stopdropandRoll;
    @Expose public int puttingitolmtheLine;
    @Expose public int coxMaster;
    @Expose public int corporealbeastMaster;
    @Expose public int fightcavesspeedChaser;
    @Expose public int denyingtheHealers;
    @Expose public int fightcavesMaster;
    @Expose public int nibblersBegone;
    @Expose public int onehundredTentacles;
    @Expose public int swoopnoMore;
    @Expose public int precisePositioning;
    @Expose public int supplieswhoneedsEm;
    @Expose public int facingjadheadonIv;
    @Expose public int tzhaarketraksspeedChaser;
    @Expose public int theivjadChallenge;
    @Expose public int extendedEncounter;
    @Expose public int vorkathspeedChaser;
    @Expose public int vorkathMaster;
    @Expose public int zulrahspeedChaser;
    @Expose public int perfectZulrah;
    @Expose public int zulrahMaster;

    //Grandmaster tasks
    @Expose public int alchemicalspeedRunner;
    @Expose public int noPressure;
    @Expose public int coxtriospeedRunner;
    @Expose public int coxfivescalespeedRunner;
    @Expose public int coxsolospeedRunner;
    @Expose public int coxgrandMaster;
    @Expose public int peachConjurer;
    @Expose public int animalWhisperer;
    @Expose public int fightcavesspeedRunner;
    @Expose public int notimeforaDrink;
    @Expose public int denyingthehealersIi;
    @Expose public int ourgKiller;
    @Expose public int keepAway;
    @Expose public int infernospeedRunner;
    @Expose public int jadwhatareyoudoingHere;
    @Expose public int facingjadheadonIi;
    @Expose public int nibblerChaser;
    @Expose public int budgetSetup;
    @Expose public int wasntevenClose;
    @Expose public int noluckRequired;
    @Expose public int playingwithJads;
    @Expose public int thefloorisLava;
    @Expose public int infernoGrandmaster;
    @Expose public int ashCollector;
    @Expose public int demonWhisperer;
    @Expose public int featherHunter;
    @Expose public int theworstrangedWeapon;
    @Expose public int itwasntaFluke;
    @Expose public int tzhaarketraksspeedRunner;
    @Expose public int thevijadChallenge;
    @Expose public int vorkathspeedRunner;
    @Expose public int thefremennikWay;
    @Expose public int faithlessEncounter;
    @Expose public int zulrahspeedRunner;


    /**
     * Teleport interface
     */
    public int teleportSelectedCategory = -1;
    @Expose public List<Integer> favoriteTeleports = new LinkedList<>();
    public List<TeleportList.Teleport> searchTeleports;
    public boolean tpWildWarn = true;

    @Expose public List<TeleportInterface.Teleport> favorites = new ArrayList<>(10);

    @Expose public boolean breakVials;
    @Expose public boolean discardBuckets;

    @Expose public int combatXpRate = 120;

    @Expose public int beginnerClueCount;
    @Expose public KillCounter galvekKills = new KillCounter();
    @Expose public KillCounter zalcanoKills = new KillCounter();
    @Expose public KillCounter theatreOfBloodKills = new KillCounter();
    @Expose public KillCounter theFightCaveKills = new KillCounter();
    @Expose public KillCounter theGauntletKills = new KillCounter();
    @Expose public KillCounter alchemicalHydraKills = new KillCounter();
    @Expose public KillCounter grotesqueGuardianKills = new KillCounter();
    @Expose public KillCounter bryophytaKills = new KillCounter();
    @Expose public KillCounter hesporiKills = new KillCounter();
    @Expose public KillCounter sarachnisKills = new KillCounter();
    /**
     * Collection Log
     */
    @Expose @Getter @Setter public int collection_log_current_entry = 0;
    @Expose @Getter @Setter public int collection_log_current_struct = 471;//471-475
    @Expose @Getter @Setter public int collection_log_current_tab = 0;
    @Expose @Getter @Setter public int[] collection_log_current_params = player.getCollectionLog().bossParams;
    @Expose public int totalCollections;

    @Expose public int combatTasksCompleted;
    @Expose public int pvpTasksCompleted;

    /** arena pvp variables */
    @Expose public int conquestPoints;
    @Expose public int unrated1v1Wins;
    @Expose public int unrated1v1Kills;
    @Expose public int rated1v1Wins;
    @Expose public int rated1v1Losses;
    @Expose public int rated1v1Kills;
    @Expose public int seasonKills;
    @Expose public int seasonDeaths;
    @Expose public int arenathreesWins;
    @Expose public int arenathreesKills;
    @Expose public int battlegroundWins;
    @Expose public boolean claimedSeasonReward;
    @Expose public int pvpRating;
    public Party2v2 viewingParty2v2;
    public BattleGrounds battleGroundsGame;
    public Arena1v1 unratedArena1v1Game;
    public Rated1v1 ratedArena1v1Game;
    public Arena2v2 unratedArena2v2Game;
    @Expose public int unratedBattlegroundTicks;
    @Expose public int unratedBattlegroundKills;
    @Expose public int unratedBattlegroundWins;
    @Expose public int unrated1v1Ticks;
    @Expose public int rated1v1Ticks;
    @Expose public int unrated2v2Ticks;
    @Expose public int unrated2v2Kills;
    @Expose public int unrated2v2Wins;

    @Expose @Getter @Setter private String leagueName;

    @Expose public int holdthefortTicks;

    /** bounty points */
    @Expose public int bountyPoints;

    /** pk points */
    @Expose public int pkPoints;

    @Expose public XpMode xpMode = XpMode.MEDIUM;

    @Expose public boolean restored;
    @Expose public boolean beforeFuckup;
    @Expose public int wildernessSlayerPoints;
    @Expose public boolean edgeHome;

    @Expose public int theatreOfBloodCompleted;
    public int viewingTheatreSlot;
    @Expose @Setter @Getter public boolean acceptedTheatreRisk;
    @Expose @Setter @Getter public boolean acceptedTheatreCrystals;

    /**
     * Duo Slayer
     */
    @Expose @Getter @Setter public boolean duoSlayerPartner = false;
    @Expose @Getter @Setter public String duoSlayerPartnerName = null;
    @Expose @Getter @Setter public int duoSlayerPartnerUid = -1;
    @Expose public String duoSlayerTaskName = null;
    @Expose public int duoSlayerTaskRemaining = 0;
    @Expose public int duoSlayerTasksCompleted = 0;
    @Expose public int duoSlayerSpree = 0;
    @Expose public int duoSlayerLocation = 0;
    @Expose public boolean duoSlayerCombatCheck = true;
    @Expose @Getter @Setter public int duoSlayerTaskId;
    @Expose @Getter @Setter public boolean duoSlayerHost = false;

    /**
     * 2FA
     */

    public boolean tfa;

    /**
     * Agility
     */
    @Expose public int lastAgilityObjId = -1;
    @Expose public int draynorRooftopLaps;
    @Expose public int alKharidRooftopLaps;
    @Expose public int varrockRooftopLaps;
    @Expose public int canifisRooftopLaps;
    @Expose public int faladorRooftopLaps;
    @Expose public int seersRooftopLaps;
    @Expose public int rellekkaRooftopLaps;
    @Expose public int ardougneRooftopLaps;
    @Expose public int gnomeStrongholdLaps;
    @Expose public int barbarianCrouseLaps;
    @Expose public int wildernessCourseLaps;
    @Expose public int prifCourseLaps;

    /**
     * Smelting
     */
    @Expose public int smeltedBronzeBars;
    @Expose public int smeltedBluriteBars;
    @Expose public int smeltedIronBars;
    @Expose public int smeltedSilverBars;
    @Expose public int smeltedSteelBars;
    @Expose public int smeltedGoldBars;
    @Expose public int smeltedMithrilBars;
    @Expose public int smeltedAdamantBars;
    @Expose public int smeltedRuniteBars;
    @Expose public int smeltedCorruptBars;

    /**
     * Mining
     */
    @Expose public int minedClay;
    @Expose public int minedCopper;
    @Expose public int minedTin;
    @Expose public int minedIron;
    @Expose public int minedSilver;
    @Expose public int minedCoal;
    @Expose public int minedGold;
    @Expose public int minedMithril;
    @Expose public int minedLovakite;
    @Expose public int minedAdamant;
    @Expose public int minedRunite;
    @Expose public int minedAmethyst;
    @Expose public int minedRuneEssence;
    @Expose public int minedPureEssence;
    @Expose public int minedDarkEssence;
    @Expose public int minedGeode;
    @Expose public int minedSandstone;
    @Expose public int minedGranite;
    @Expose public int minedGemRock;
    @Expose public int minedCorrupt;
    @Expose public int minedVolcanicAsh;

    /**
     * Woodcutting
     */
    @Expose public int choppedRegular;
    @Expose public int choppedSapling;
    @Expose public int choppedAchey;
    @Expose public int choppedOak;
    @Expose public int choppedWillow;
    @Expose public int choppedTeak;
    @Expose public int choppedJuniper;
    @Expose public int choppedMaple;
    @Expose public int choppedYew;
    @Expose public int choppedMagic;
    @Expose public int choppedRedwood;
    @Expose public int choppedMahogany;
    @Expose public int choppedCorrupt;
    @Expose public int openedBirdsNests;
    @Expose public int acquiredBirdsNests;

    /**
     * Cooking
     */
    @Expose public int cookedFood;
    @Expose public int burntFood;
    @Expose public int jugsOfWineMade;
    @Expose public int cookedOnFire;

    /**
     * Thieving
     */
    @Expose public int vegetableStallThieves;
    @Expose public int bakerStallThieves;
    @Expose public int craftingStallThieves;
    @Expose public int monkeyFoodStallThieves;
    @Expose public int monkeyGeneralStallThieves;
    @Expose public int teaStallThieves;
    @Expose public int silkStallThieves;
    @Expose public int wineStallThieves;
    @Expose public int seedStallThieves;
    @Expose public int furStallThieves;
    @Expose public int fishStallThieves;
    @Expose public int crossbowStallThieves;
    @Expose public int silverStallThieves;
    @Expose public int spiceStallThieves;
    @Expose public int magicStallThieves;
    @Expose public int scimitarStallThieves;
    @Expose public int gemStallThieves;
    @Expose public int gemMorStallThieves;
    @Expose public int oreStallThieves;
    @Expose public int pickpocketMan;
    @Expose public int pickpocketFarmer;
    @Expose public int pickpocketWarrior;
    @Expose public int pickpocketHamMember;
    @Expose public int pickpocketRogue;
    @Expose public int pickpocketMasterFarmer;
    @Expose public int pickpocketGuard;
    @Expose public int pickpocketBandit;
    @Expose public int pickpocketKnight;
    @Expose public int pickpocketPaladin;
    @Expose public int pickpocketGnome;
    @Expose public int pickpocketHero;
    @Expose public int pickpocketElf;
    @Expose public int pickpocketTzhaarHur;
    @Expose public int wallSafesCracked;
    @Expose public int rougesCastleChests;
    @Expose public int pickedLocks;

    /**
     * Firemaking
     */
    @Expose public int normalLogsBurnt;
    @Expose public int acheyLogsBurnt;
    @Expose public int oakLogsBurnt;
    @Expose public int willowLogsBurnt;
    @Expose public int teakLogsBurnt;
    @Expose public int arcticPineLogsBurnt;
    @Expose public int mapleLogsBurnt;
    @Expose public int mahoganyLogsBurnt;
    @Expose public int yewLogsBurnt;
    @Expose public int magicLogsBurnt;
    @Expose public int redwoodLogsBurnt;
    @Expose public int redLogsBurnt;
    @Expose public int greenLogsBurnt;
    @Expose public int blueLogsBurnt;
    @Expose public int whiteLogsBurnt;
    @Expose public int purpleLogsBurnt;
    @Expose public int kindlingBurnt;

    /**
     * Prayer
     */
    @Expose public int regularBonesBuried;
    @Expose public int burntBonesBuried;
    @Expose public int batBonesBuried;
    @Expose public int wolfBonesBuried;
    @Expose public int bigBonesBuried;
    @Expose public int longBonesBuried;
    @Expose public int curvedBonesBuried;
    @Expose public int jogreBonesBuried;
    @Expose public int babydragonBonesBuried;
    @Expose public int dragonBonesBuried;
    @Expose public int zogreBonesBuried;
    @Expose public int ourgBonesBuried;
    @Expose public int monkeyBonesBuried;
    @Expose public int wyvernBonesBuried;
    @Expose public int dagannothBonesBuried;
    @Expose public int lavaDragonBonesBuried;
    @Expose public int superiorDragonBonesBuried;
    @Expose public int wyrmBonesBuried;
    @Expose public int drakeBonesBuried;
    @Expose public int hydraBonesBuried;
    @Expose public int regularBonesAltar;
    @Expose public int burntBonesAltar;
    @Expose public int batBonesAltar;
    @Expose public int wolfBonesAltar;
    @Expose public int bigBonesAltar;
    @Expose public int longBonesAltar;
    @Expose public int curvedBonesAltar;
    @Expose public int jogreBonesAltar;
    @Expose public int babydragonBonesAltar;
    @Expose public int dragonBonesAltar;
    @Expose public int zogreBonesAltar;
    @Expose public int ourgBonesAltar;
    @Expose public int monkeyBonesAltar;
    @Expose public int wyvernBonesAltar;
    @Expose public int dagannothBonesAltar;
    @Expose public int lavaDragonBonesAltar;
    @Expose public int superiorDragonBonesAltar;
    @Expose public int wyrmBonesAltar;
    @Expose public int drakeBonesAltar;
    @Expose public int hydraBonesAltar;
    public TickDelay boneBuryDelay = new TickDelay();

    /**
     * Fishing
     */
    @Expose public int totalFishCaught;


    /**
     * Construction
     */
    public int houseBuildPointX, houseBuildPointY, houseBuildPointZ;
    public Room[] houseViewerRooms;
    public Room houseViewerRoom;

    /**
     * Runecrafting
     */

    @Expose public int craftedAir;
    @Expose public int craftedMind;
    @Expose public int craftedWater;
    @Expose public int craftedEarth;
    @Expose public int craftedFire;
    @Expose public int craftedBody;
    @Expose public int craftedCosmic;
    @Expose public int craftedLaw;
    @Expose public int craftedNature;
    @Expose public int craftedChaos;
    @Expose public int craftedDeath;
    @Expose public int craftedAstral;
    @Expose public int craftedBlood;
    @Expose public int craftedSoul;

    @Expose public int tobCompleted;

    @Expose public int normalGauntlet;
    @Expose public int corruptedGauntlet;
    @Expose public int totalGauntlet;


    /**
     * Farming
     */

    @Expose public int harvestedPotato;
    @Expose public int harvestedOnion;
    @Expose public int harvestedCabbage;
    @Expose public int harvestedTomato;
    @Expose public int harvestedSweetcorn;
    @Expose public int harvestedStrawberry;
    @Expose public int harvestedWatermelon;
    @Expose public int harvestedSnapegrass;

    @Expose public int harvestedMarigolds;
    @Expose public int harvestedRosemary;
    @Expose public int harvestedNasturtium;
    @Expose public int harvestedWoad;
    @Expose public int harvestedLimpwurt;

    @Expose public int harvestedGuam;
    @Expose public int harvestedMarrentill;
    @Expose public int harvestedTarromin;
    @Expose public int harvestedHarralander;
    @Expose public int harvestedRanarr;
    @Expose public int harvestedToadflax;
    @Expose public int harvestedIrit;
    @Expose public int harvestedAvantoe;
    @Expose public int harvestedKwuarm;
    @Expose public int harvestedSnapdragon;
    @Expose public int harvestedCadantine;
    @Expose public int harvestedLantadyme;
    @Expose public int harvestedDwarfWeed;
    @Expose public int harvestedTorstol;

    @Expose public int harvestedBittercap;

    @Expose public int grownApple;
    @Expose public int grownBanana;
    @Expose public int grownOrange;
    @Expose public int grownCurry;
    @Expose public int grownPineapple;
    @Expose public int grownPapaya;
    @Expose public int grownPalm;

    @Expose public int grownOak;
    @Expose public int grownWillow;
    @Expose public int grownMaple;
    @Expose public int grownYew;
    @Expose public int grownMagic;

    @Expose public int grownRedberry;
    @Expose public int grownCadavaberry;
    @Expose public int grownDwellberry;
    @Expose public int grownJangerberry;
    @Expose public int grownWhiteberry;
    @Expose public int grownPoisonIvy;

    @Expose public int harvestedBarley;
    @Expose public int harvestedHammerstone;
    @Expose public int harvestedAsgarnian;
    @Expose public int harvestedJute;
    @Expose public int harvestedYanillian;
    @Expose public int harvestedKrandorian;
    @Expose public int harvestedWildblood;

    @Expose public int grownCalquat;
    @Expose public int grownCactus;

    @Expose public int grownSpiritTree;

    /**
     * Clue scrolls
     */

    @Expose public ClueSave easyClue;
    @Expose public ClueSave medClue;
    @Expose public ClueSave hardClue;
    @Expose public ClueSave eliteClue;
    @Expose public ClueSave masterClue;

    @Expose public int easyClueCount;
    @Expose public int medClueCount;
    @Expose public int hardClueCount;
    @Expose public int eliteClueCount;
    @Expose public int masterClueCount;


    /**
     * Motherlode mine
     */

    @Expose public int minedPaydirt;
    @Expose public int cleanedPaydirt;

    @Expose public int paydirtInWater;

    @Expose public boolean motherlodeRestrictedMineUnlocked;
    @Expose public boolean motherlodeBiggerSackUnlocked;

    /**
     * Misc
     */

    @Expose public int poolTicks;

    @Expose public int gameModeTicks;

    @Expose public int afkzoneTicks;

    @Expose public long playTime;

    @Expose public int staminaTicks;

    public int specialRestoreTicks;

    public Config selectedKeybindConfig;

    public TickDelay eatDelay = new TickDelay();

    public TickDelay karamDelay = new TickDelay();

    public TickDelay potDelay = new TickDelay();

    public SmithBar smithBar;

    @Expose public int darkEssFragments;

    @Expose public int baggedCoal;

    @Expose public boolean miningGuildMinerals;

    @Expose public int gluttony;

    @Expose public int yesEmote;

    @Expose public int noEmote;

    public TickDelay yesDelay = new TickDelay();

    public TickDelay noDelay = new TickDelay();

    public TickDelay emoteDelay = new TickDelay();

    @Expose public int teleportTabsBroken;

    @Expose public boolean beginnerParkourEnergyBoost;

    public Teleother teleotherActive;


    @Expose public boolean kylieMinnowDialogueStarted;

    @Expose public boolean fairyAerykaDialogueStarted;

    @Expose public boolean elnockInquisitorDialogueStarted;

    @Expose public boolean elnockInquisitorGivenEquipment;

    public NPC examineMonster;

    @Expose public int runForestRun;

    @Expose public boolean runForestRunUnlockable;

    @Expose public BarrowsBrother barrowsChestBrother;

    @Expose public int barrowsChestsOpened;

    public TickDelay magicImbueEffect = new TickDelay();

    public NPC teleportsWizard;

    @Expose public int teleportCategoryIndex, teleportSubcategoryIndex;

    @Expose public boolean canEnterMorUlRek;

    @Expose public FairyRing[] unlockedFairyRingTeleports = new FairyRing[FairyRing.values().length];

    public GameObject fairyRing;

    public CanoeStation canoeStation;

    public boolean npcTarget; //just so we can check if a player has a target or not.

    @Expose public boolean edgevilleLeverWarning = true;

    @Expose public int mageArenaPoints;

    public boolean teleportsInterface;

    @Expose public int previousTeleportX = -1, previousTeleportY, previousTeleportZ;

    @Expose public int previousTeleportPrice;

    public TickDelay imbueHeartCooldown = new TickDelay();

    @Expose public boolean newPlayer = true;

    public boolean inTutorial = false;

    @Expose public boolean krakenWarning = true;

    @Expose public boolean smokeBossWarning = true;

    @Expose public boolean wyvernWarning = true;

    @Expose public int dragSetting = 5;

    @Expose public int switchGrading;

    @Expose public boolean showTimers = true;

    @Expose public boolean colorValuedGroundItems = false;

    @Expose public boolean swapRangePrayerPosition;

    @Expose public boolean swapMagePrayerPosition;

    @Expose public long yellDelay;

    @Expose public boolean yellFilter;

    @Expose public boolean hideTitles = false;

    /**
     * PVP
     */

    public boolean pvpAttackZone;

    public int pvpCombatLevel;

    @Expose public int recoilDamageRemaining = 40;

    @Expose public int sufferingCharges;

    @Expose public ArrayList<Item[]> presetOneInv = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetTwoInv = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetThreeInv = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetFourInv = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetFiveInv = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetSixInv = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetSevenInv = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetEightInv = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetNineInv = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetTenInv = new ArrayList<>();

    @Expose public ArrayList<Item[]> presetOneEq = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetTwoEq = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetThreeEq = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetFourEq = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetFiveEq = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetSixEq = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetSevenEq = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetEightEq = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetNineEq = new ArrayList<>();
    @Expose public ArrayList<Item[]> presetTenEq = new ArrayList<>();

    @Expose public int presetOneHelm = -1;
    @Expose public int presetOneChest = -1;
    @Expose public int presetOneLegs = -1;
    @Expose public int presetOneCape = -1;
    @Expose public int presetOneWeapon = -1;
    @Expose public int presetOneShield = -1;
    @Expose public int presetOneGloves = -1;
    @Expose public int presetOneBoots = -1;
    @Expose public int presetOneNeck = -1;
    @Expose public int presetOneRing = -1;
    @Expose public int presetOneAmmo = -1;
    @Expose public int presetOneAmmoAmount = -1;

    @Expose public int presetOneSpellbook = -1;

    @Expose public int presetOneInvOne = -1;
    @Expose public int presetOneInvTwo = -1;
    @Expose public int presetOneInvThree = -1;
    @Expose public int presetOneInvFour = -1;
    @Expose public int presetOneInvFive = -1;

    @Expose public int presetOneInvSix = -1;
    @Expose public int presetOneInvSeven = -1;
    @Expose public int presetOneInvEight = -1;
    @Expose public int presetOneInvNine = -1;
    @Expose public int presetOneInvTen = -1;

    @Expose public int presetOneInvEleven = -1;
    @Expose public int presetOneInvTwelve = -1;
    @Expose public int presetOneInvThirteen = -1;
    @Expose public int presetOneInvFourteen = -1;
    @Expose public int presetOneInvFifteen = -1;

    @Expose public int presetOneInvSixteen = -1;
    @Expose public int presetOneInvSeventeen = -1;
    @Expose public int presetOneInvEighteen = -1;
    @Expose public int presetOneInvNineteen = -1;
    @Expose public int presetOneInvTwnety = -1;

    @Expose public int presetOneInvTwentyOne = -1;
    @Expose public int presetOneInvTwentyTwo = -1;
    @Expose public int presetOneInvTwnetyThree = -1;
    @Expose public int presetOneInvTwentyFour = -1;
    @Expose public int presetOneInvTwentyFive = -1;

    @Expose public int presetOneInvTwnetySix = -1;
    @Expose public int presetOneInvTwentySeven = -1;
    @Expose public int presetOneInvTwentyEight = -1;

    @Expose public int presetOneInvOneAmount = -1;
    @Expose public int presetOneInvTwoAmount = -1;
    @Expose public int presetOneInvThreeAmount = -1;
    @Expose public int presetOneInvFourAmount = -1;
    @Expose public int presetOneInvFiveAmount = -1;

    @Expose public int presetOneInvSixAmount = -1;
    @Expose public int presetOneInvSevenAmount = -1;
    @Expose public int presetOneInvEightAmount = -1;
    @Expose public int presetOneInvNineAmount = -1;
    @Expose public int presetOneInvTenAmount = -1;

    @Expose public int presetOneInvElevenAmount = -1;
    @Expose public int presetOneInvTwelveAmount = -1;
    @Expose public int presetOneInvThirteenAmount = -1;
    @Expose public int presetOneInvFourteenAmount = -1;
    @Expose public int presetOneInvFifteenAmount = -1;

    @Expose public int presetOneInvSixteenAmount = -1;
    @Expose public int presetOneInvSeventeenAmount = -1;
    @Expose public int presetOneInvEighteenAmount = -1;
    @Expose public int presetOneInvNineteenAmount = -1;
    @Expose public int presetOneInvTwnetyAmount = -1;

    @Expose public int presetOneInvTwentyOneAmount = -1;
    @Expose public int presetOneInvTwentyTwoAmount = -1;
    @Expose public int presetOneInvTwnetyThreeAmount = -1;
    @Expose public int presetOneInvTwentyFourAmount = -1;
    @Expose public int presetOneInvTwentyFiveAmount = -1;

    @Expose public int presetOneInvTwnetySixAmount = -1;
    @Expose public int presetOneInvTwentySevenAmount = -1;
    @Expose public int presetOneInvTwentyEightAmount = -1;


    @Expose public int presetTwoHelm = -1;
    @Expose public int presetTwoChest = -1;
    @Expose public int presetTwoLegs = -1;
    @Expose public int presetTwoCape = -1;
    @Expose public int presetTwoWeapon = -1;
    @Expose public int presetTwoShield = -1;
    @Expose public int presetTwoGloves = -1;
    @Expose public int presetTwoBoots = -1;
    @Expose public int presetTwoNeck = -1;
    @Expose public int presetTwoRing = -1;
    @Expose public int presetTwoAmmo = -1;
    @Expose public int presetTwoAmmoAmount = -1;

    @Expose public int presetTwoSpellbook = -1;

    @Expose public int presetTwoInvOne = -1;
    @Expose public int presetTwoInvTwo = -1;
    @Expose public int presetTwoInvThree = -1;
    @Expose public int presetTwoInvFour = -1;
    @Expose public int presetTwoInvFive = -1;

    @Expose public int presetTwoInvSix = -1;
    @Expose public int presetTwoInvSeven = -1;
    @Expose public int presetTwoInvEight = -1;
    @Expose public int presetTwoInvNine = -1;
    @Expose public int presetTwoInvTen = -1;

    @Expose public int presetTwoInvEleven = -1;
    @Expose public int presetTwoInvTwelve = -1;
    @Expose public int presetTwoInvThirteen = -1;
    @Expose public int presetTwoInvFourteen = -1;
    @Expose public int presetTwoInvFifteen = -1;

    @Expose public int presetTwoInvSixteen = -1;
    @Expose public int presetTwoInvSeventeen = -1;
    @Expose public int presetTwoInvEighteen = -1;
    @Expose public int presetTwoInvNineteen = -1;
    @Expose public int presetTwoInvTwnety = -1;

    @Expose public int presetTwoInvTwentyOne = -1;
    @Expose public int presetTwoInvTwentyTwo = -1;
    @Expose public int presetTwoInvTwnetyThree = -1;
    @Expose public int presetTwoInvTwentyFour = -1;
    @Expose public int presetTwoInvTwentyFive = -1;

    @Expose public int presetTwoInvTwnetySix = -1;
    @Expose public int presetTwoInvTwentySeven = -1;
    @Expose public int presetTwoInvTwentyEight = -1;

    @Expose public int presetTwoInvOneAmount = -1;
    @Expose public int presetTwoInvTwoAmount = -1;
    @Expose public int presetTwoInvThreeAmount = -1;
    @Expose public int presetTwoInvFourAmount = -1;
    @Expose public int presetTwoInvFiveAmount = -1;

    @Expose public int presetTwoInvSixAmount = -1;
    @Expose public int presetTwoInvSevenAmount = -1;
    @Expose public int presetTwoInvEightAmount = -1;
    @Expose public int presetTwoInvNineAmount = -1;
    @Expose public int presetTwoInvTenAmount = -1;

    @Expose public int presetTwoInvElevenAmount = -1;
    @Expose public int presetTwoInvTwelveAmount = -1;
    @Expose public int presetTwoInvThirteenAmount = -1;
    @Expose public int presetTwoInvFourteenAmount = -1;
    @Expose public int presetTwoInvFifteenAmount = -1;

    @Expose public int presetTwoInvSixteenAmount = -1;
    @Expose public int presetTwoInvSeventeenAmount = -1;
    @Expose public int presetTwoInvEighteenAmount = -1;
    @Expose public int presetTwoInvNineteenAmount = -1;
    @Expose public int presetTwoInvTwnetyAmount = -1;

    @Expose public int presetTwoInvTwentyOneAmount = -1;
    @Expose public int presetTwoInvTwentyTwoAmount = -1;
    @Expose public int presetTwoInvTwnetyThreeAmount = -1;
    @Expose public int presetTwoInvTwentyFourAmount = -1;
    @Expose public int presetTwoInvTwentyFiveAmount = -1;

    @Expose public int presetTwoInvTwnetySixAmount = -1;
    @Expose public int presetTwoInvTwentySevenAmount = -1;
    @Expose public int presetTwoInvTwentyEightAmount = -1;


    @Expose public int presetThreeHelm = -1;
    @Expose public int presetThreeChest = -1;
    @Expose public int presetThreeLegs = -1;
    @Expose public int presetThreeCape = -1;
    @Expose public int presetThreeWeapon = -1;
    @Expose public int presetThreeShield = -1;
    @Expose public int presetThreeGloves = -1;
    @Expose public int presetThreeBoots = -1;
    @Expose public int presetThreeNeck = -1;
    @Expose public int presetThreeRing = -1;
    @Expose public int presetThreeAmmo = -1;
    @Expose public int presetThreeAmmoAmount = -1;

    @Expose public int presetThreeSpellbook = -1;

    @Expose public int presetThreeInvOne = -1;
    @Expose public int presetThreeInvTwo = -1;
    @Expose public int presetThreeInvThree = -1;
    @Expose public int presetThreeInvFour = -1;
    @Expose public int presetThreeInvFive = -1;

    @Expose public int presetThreeInvSix = -1;
    @Expose public int presetThreeInvSeven = -1;
    @Expose public int presetThreeInvEight = -1;
    @Expose public int presetThreeInvNine = -1;
    @Expose public int presetThreeInvTen = -1;

    @Expose public int presetThreeInvEleven = -1;
    @Expose public int presetThreeInvTwelve = -1;
    @Expose public int presetThreeInvThirteen = -1;
    @Expose public int presetThreeInvFourteen = -1;
    @Expose public int presetThreeInvFifteen = -1;

    @Expose public int presetThreeInvSixteen = -1;
    @Expose public int presetThreeInvSeventeen = -1;
    @Expose public int presetThreeInvEighteen = -1;
    @Expose public int presetThreeInvNineteen = -1;
    @Expose public int presetThreeInvTwnety = -1;

    @Expose public int presetThreeInvTwentyOne = -1;
    @Expose public int presetThreeInvTwentyTwo = -1;
    @Expose public int presetThreeInvTwnetyThree = -1;
    @Expose public int presetThreeInvTwentyFour = -1;
    @Expose public int presetThreeInvTwentyFive = -1;

    @Expose public int presetThreeInvTwnetySix = -1;
    @Expose public int presetThreeInvTwentySeven = -1;
    @Expose public int presetThreeInvTwentyEight = -1;

    @Expose public int presetThreeInvOneAmount = -1;
    @Expose public int presetThreeInvTwoAmount = -1;
    @Expose public int presetThreeInvThreeAmount = -1;
    @Expose public int presetThreeInvFourAmount = -1;
    @Expose public int presetThreeInvFiveAmount = -1;

    @Expose public int presetThreeInvSixAmount = -1;
    @Expose public int presetThreeInvSevenAmount = -1;
    @Expose public int presetThreeInvEightAmount = -1;
    @Expose public int presetThreeInvNineAmount = -1;
    @Expose public int presetThreeInvTenAmount = -1;

    @Expose public int presetThreeInvElevenAmount = -1;
    @Expose public int presetThreeInvTwelveAmount = -1;
    @Expose public int presetThreeInvThirteenAmount = -1;
    @Expose public int presetThreeInvFourteenAmount = -1;
    @Expose public int presetThreeInvFifteenAmount = -1;

    @Expose public int presetThreeInvSixteenAmount = -1;
    @Expose public int presetThreeInvSeventeenAmount = -1;
    @Expose public int presetThreeInvEighteenAmount = -1;
    @Expose public int presetThreeInvNineteenAmount = -1;
    @Expose public int presetThreeInvTwnetyAmount = -1;

    @Expose public int presetThreeInvTwentyOneAmount = -1;
    @Expose public int presetThreeInvTwentyTwoAmount = -1;
    @Expose public int presetThreeInvTwnetyThreeAmount = -1;
    @Expose public int presetThreeInvTwentyFourAmount = -1;
    @Expose public int presetThreeInvTwentyFiveAmount = -1;

    @Expose public int presetThreeInvTwnetySixAmount = -1;
    @Expose public int presetThreeInvTwentySevenAmount = -1;
    @Expose public int presetThreeInvTwentyEightAmount = -1;


    @Expose public int presetFourHelm = -1;
    @Expose public int presetFourChest = -1;
    @Expose public int presetFourLegs = -1;
    @Expose public int presetFourCape = -1;
    @Expose public int presetFourWeapon = -1;
    @Expose public int presetFourShield = -1;
    @Expose public int presetFourGloves = -1;
    @Expose public int presetFourBoots = -1;
    @Expose public int presetFourNeck = -1;
    @Expose public int presetFourRing = -1;
    @Expose public int presetFourAmmo = -1;
    @Expose public int presetFourAmmoAmount = -1;

    @Expose public int presetFourSpellbook = -1;

    @Expose public int presetFourInvOne = -1;
    @Expose public int presetFourInvTwo = -1;
    @Expose public int presetFourInvThree = -1;
    @Expose public int presetFourInvFour = -1;
    @Expose public int presetFourInvFive = -1;

    @Expose public int presetFourInvSix = -1;
    @Expose public int presetFourInvSeven = -1;
    @Expose public int presetFourInvEight = -1;
    @Expose public int presetFourInvNine = -1;
    @Expose public int presetFourInvTen = -1;

    @Expose public int presetFourInvEleven = -1;
    @Expose public int presetFourInvTwelve = -1;
    @Expose public int presetFourInvThirteen = -1;
    @Expose public int presetFourInvFourteen = -1;
    @Expose public int presetFourInvFifteen = -1;

    @Expose public int presetFourInvSixteen = -1;
    @Expose public int presetFourInvSeventeen = -1;
    @Expose public int presetFourInvEighteen = -1;
    @Expose public int presetFourInvNineteen = -1;
    @Expose public int presetFourInvTwnety = -1;

    @Expose public int presetFourInvTwentyOne = -1;
    @Expose public int presetFourInvTwentyTwo = -1;
    @Expose public int presetFourInvTwnetyThree = -1;
    @Expose public int presetFourInvTwentyFour = -1;
    @Expose public int presetFourInvTwentyFive = -1;

    @Expose public int presetFourInvTwnetySix = -1;
    @Expose public int presetFourInvTwentySeven = -1;
    @Expose public int presetFourInvTwentyEight = -1;

    @Expose public int presetFourInvOneAmount = -1;
    @Expose public int presetFourInvTwoAmount = -1;
    @Expose public int presetFourInvThreeAmount = -1;
    @Expose public int presetFourInvFourAmount = -1;
    @Expose public int presetFourInvFiveAmount = -1;

    @Expose public int presetFourInvSixAmount = -1;
    @Expose public int presetFourInvSevenAmount = -1;
    @Expose public int presetFourInvEightAmount = -1;
    @Expose public int presetFourInvNineAmount = -1;
    @Expose public int presetFourInvTenAmount = -1;

    @Expose public int presetFourInvElevenAmount = -1;
    @Expose public int presetFourInvTwelveAmount = -1;
    @Expose public int presetFourInvThirteenAmount = -1;
    @Expose public int presetFourInvFourteenAmount = -1;
    @Expose public int presetFourInvFifteenAmount = -1;

    @Expose public int presetFourInvSixteenAmount = -1;
    @Expose public int presetFourInvSeventeenAmount = -1;
    @Expose public int presetFourInvEighteenAmount = -1;
    @Expose public int presetFourInvNineteenAmount = -1;
    @Expose public int presetFourInvTwnetyAmount = -1;

    @Expose public int presetFourInvTwentyOneAmount = -1;
    @Expose public int presetFourInvTwentyTwoAmount = -1;
    @Expose public int presetFourInvTwnetyThreeAmount = -1;
    @Expose public int presetFourInvTwentyFourAmount = -1;
    @Expose public int presetFourInvTwentyFiveAmount = -1;

    @Expose public int presetFourInvTwnetySixAmount = -1;
    @Expose public int presetFourInvTwentySevenAmount = -1;
    @Expose public int presetFourInvTwentyEightAmount = -1;

    @Expose public int presetFiveSpellbook = -1;


    @Expose public int presetFiveHelm = -1;
    @Expose public int presetFiveChest = -1;
    @Expose public int presetFiveLegs = -1;
    @Expose public int presetFiveCape = -1;
    @Expose public int presetFiveWeapon = -1;
    @Expose public int presetFiveShield = -1;
    @Expose public int presetFiveGloves = -1;
    @Expose public int presetFiveBoots = -1;
    @Expose public int presetFiveNeck = -1;
    @Expose public int presetFiveRing = -1;
    @Expose public int presetFiveAmmo = -1;
    @Expose public int presetFiveAmmoAmount = -1;

    @Expose public int presetFiveInvOne = -1;
    @Expose public int presetFiveInvTwo = -1;
    @Expose public int presetFiveInvThree = -1;
    @Expose public int presetFiveInvFour = -1;
    @Expose public int presetFiveInvFive = -1;

    @Expose public int presetFiveInvSix = -1;
    @Expose public int presetFiveInvSeven = -1;
    @Expose public int presetFiveInvEight = -1;
    @Expose public int presetFiveInvNine = -1;
    @Expose public int presetFiveInvTen = -1;

    @Expose public int presetFiveInvEleven = -1;
    @Expose public int presetFiveInvTwelve = -1;
    @Expose public int presetFiveInvThirteen = -1;
    @Expose public int presetFiveInvFourteen = -1;
    @Expose public int presetFiveInvFifteen = -1;

    @Expose public int presetFiveInvSixteen = -1;
    @Expose public int presetFiveInvSeventeen = -1;
    @Expose public int presetFiveInvEighteen = -1;
    @Expose public int presetFiveInvNineteen = -1;
    @Expose public int presetFiveInvTwnety = -1;

    @Expose public int presetFiveInvTwentyOne = -1;
    @Expose public int presetFiveInvTwentyTwo = -1;
    @Expose public int presetFiveInvTwnetyThree = -1;
    @Expose public int presetFiveInvTwentyFour = -1;
    @Expose public int presetFiveInvTwentyFive = -1;

    @Expose public int presetFiveInvTwnetySix = -1;
    @Expose public int presetFiveInvTwentySeven = -1;
    @Expose public int presetFiveInvTwentyEight = -1;

    @Expose public int presetFiveInvOneAmount = -1;
    @Expose public int presetFiveInvTwoAmount = -1;
    @Expose public int presetFiveInvThreeAmount = -1;
    @Expose public int presetFiveInvFourAmount = -1;
    @Expose public int presetFiveInvFiveAmount = -1;

    @Expose public int presetFiveInvSixAmount = -1;
    @Expose public int presetFiveInvSevenAmount = -1;
    @Expose public int presetFiveInvEightAmount = -1;
    @Expose public int presetFiveInvNineAmount = -1;
    @Expose public int presetFiveInvTenAmount = -1;

    @Expose public int presetFiveInvElevenAmount = -1;
    @Expose public int presetFiveInvTwelveAmount = -1;
    @Expose public int presetFiveInvThirteenAmount = -1;
    @Expose public int presetFiveInvFourteenAmount = -1;
    @Expose public int presetFiveInvFifteenAmount = -1;

    @Expose public int presetFiveInvSixteenAmount = -1;
    @Expose public int presetFiveInvSeventeenAmount = -1;
    @Expose public int presetFiveInvEighteenAmount = -1;
    @Expose public int presetFiveInvNineteenAmount = -1;
    @Expose public int presetFiveInvTwnetyAmount = -1;

    @Expose public int presetFiveInvTwentyOneAmount = -1;
    @Expose public int presetFiveInvTwentyTwoAmount = -1;
    @Expose public int presetFiveInvTwnetyThreeAmount = -1;
    @Expose public int presetFiveInvTwentyFourAmount = -1;
    @Expose public int presetFiveInvTwentyFiveAmount = -1;

    @Expose public int presetFiveInvTwnetySixAmount = -1;
    @Expose public int presetFiveInvTwentySevenAmount = -1;
    @Expose public int presetFiveInvTwentyEightAmount = -1;


    @Expose public int presetSixHelm = -1;
    @Expose public int presetSixChest = -1;
    @Expose public int presetSixLegs = -1;
    @Expose public int presetSixCape = -1;
    @Expose public int presetSixWeapon = -1;
    @Expose public int presetSixShield = -1;
    @Expose public int presetSixGloves = -1;
    @Expose public int presetSixBoots = -1;
    @Expose public int presetSixNeck = -1;
    @Expose public int presetSixRing = -1;
    @Expose public int presetSixAmmo = -1;
    @Expose public int presetSixAmmoAmount = -1;

    @Expose public int presetSixSpellbook = -1;

    @Expose public int presetSixInvOne = -1;
    @Expose public int presetSixInvTwo = -1;
    @Expose public int presetSixInvThree = -1;
    @Expose public int presetSixInvFour = -1;
    @Expose public int presetSixInvFive = -1;

    @Expose public int presetSixInvSix = -1;
    @Expose public int presetSixInvSeven = -1;
    @Expose public int presetSixInvEight = -1;
    @Expose public int presetSixInvNine = -1;
    @Expose public int presetSixInvTen = -1;

    @Expose public int presetSixInvEleven = -1;
    @Expose public int presetSixInvTwelve = -1;
    @Expose public int presetSixInvThirteen = -1;
    @Expose public int presetSixInvFourteen = -1;
    @Expose public int presetSixInvFifteen = -1;

    @Expose public int presetSixInvSixteen = -1;
    @Expose public int presetSixInvSeventeen = -1;
    @Expose public int presetSixInvEighteen = -1;
    @Expose public int presetSixInvNineteen = -1;
    @Expose public int presetSixInvTwnety = -1;

    @Expose public int presetSixInvTwentyOne = -1;
    @Expose public int presetSixInvTwentyTwo = -1;
    @Expose public int presetSixInvTwnetyThree = -1;
    @Expose public int presetSixInvTwentyFour = -1;
    @Expose public int presetSixInvTwentyFive = -1;

    @Expose public int presetSixInvTwnetySix = -1;
    @Expose public int presetSixInvTwentySeven = -1;
    @Expose public int presetSixInvTwentyEight = -1;

    @Expose public int presetSixInvOneAmount = -1;
    @Expose public int presetSixInvTwoAmount = -1;
    @Expose public int presetSixInvThreeAmount = -1;
    @Expose public int presetSixInvFourAmount = -1;
    @Expose public int presetSixInvFiveAmount = -1;

    @Expose public int presetSixInvSixAmount = -1;
    @Expose public int presetSixInvSevenAmount = -1;
    @Expose public int presetSixInvEightAmount = -1;
    @Expose public int presetSixInvNineAmount = -1;
    @Expose public int presetSixInvTenAmount = -1;

    @Expose public int presetSixInvElevenAmount = -1;
    @Expose public int presetSixInvTwelveAmount = -1;
    @Expose public int presetSixInvThirteenAmount = -1;
    @Expose public int presetSixInvFourteenAmount = -1;
    @Expose public int presetSixInvFifteenAmount = -1;

    @Expose public int presetSixInvSixteenAmount = -1;
    @Expose public int presetSixInvSeventeenAmount = -1;
    @Expose public int presetSixInvEighteenAmount = -1;
    @Expose public int presetSixInvNineteenAmount = -1;
    @Expose public int presetSixInvTwnetyAmount = -1;

    @Expose public int presetSixInvTwentyOneAmount = -1;
    @Expose public int presetSixInvTwentyTwoAmount = -1;
    @Expose public int presetSixInvTwnetyThreeAmount = -1;
    @Expose public int presetSixInvTwentyFourAmount = -1;
    @Expose public int presetSixInvTwentyFiveAmount = -1;

    @Expose public int presetSixInvTwnetySixAmount = -1;
    @Expose public int presetSixInvTwentySevenAmount = -1;
    @Expose public int presetSixInvTwentyEightAmount = -1;


    @Expose public int presetSevenHelm = -1;
    @Expose public int presetSevenChest = -1;
    @Expose public int presetSevenLegs = -1;
    @Expose public int presetSevenCape = -1;
    @Expose public int presetSevenWeapon = -1;
    @Expose public int presetSevenShield = -1;
    @Expose public int presetSevenGloves = -1;
    @Expose public int presetSevenBoots = -1;
    @Expose public int presetSevenNeck = -1;
    @Expose public int presetSevenRing = -1;
    @Expose public int presetSevenAmmo = -1;
    @Expose public int presetSevenAmmoAmount = -1;

    @Expose public int presetSevenSpellbook = -1;

    @Expose public int presetSevenInvOne = -1;
    @Expose public int presetSevenInvTwo = -1;
    @Expose public int presetSevenInvThree = -1;
    @Expose public int presetSevenInvFour = -1;
    @Expose public int presetSevenInvFive = -1;

    @Expose public int presetSevenInvSix = -1;
    @Expose public int presetSevenInvSeven = -1;
    @Expose public int presetSevenInvEight = -1;
    @Expose public int presetSevenInvNine = -1;
    @Expose public int presetSevenInvTen = -1;

    @Expose public int presetSevenInvEleven = -1;
    @Expose public int presetSevenInvTwelve = -1;
    @Expose public int presetSevenInvThirteen = -1;
    @Expose public int presetSevenInvFourteen = -1;
    @Expose public int presetSevenInvFifteen = -1;

    @Expose public int presetSevenInvSixteen = -1;
    @Expose public int presetSevenInvSeventeen = -1;
    @Expose public int presetSevenInvEighteen = -1;
    @Expose public int presetSevenInvNineteen = -1;
    @Expose public int presetSevenInvTwnety = -1;

    @Expose public int presetSevenInvTwentyOne = -1;
    @Expose public int presetSevenInvTwentyTwo = -1;
    @Expose public int presetSevenInvTwnetyThree = -1;
    @Expose public int presetSevenInvTwentyFour = -1;
    @Expose public int presetSevenInvTwentyFive = -1;

    @Expose public int presetSevenInvTwnetySix = -1;
    @Expose public int presetSevenInvTwentySeven = -1;
    @Expose public int presetSevenInvTwentyEight = -1;

    @Expose public int presetSevenInvOneAmount = -1;
    @Expose public int presetSevenInvTwoAmount = -1;
    @Expose public int presetSevenInvThreeAmount = -1;
    @Expose public int presetSevenInvFourAmount = -1;
    @Expose public int presetSevenInvFiveAmount = -1;

    @Expose public int presetSevenInvSixAmount = -1;
    @Expose public int presetSevenInvSevenAmount = -1;
    @Expose public int presetSevenInvEightAmount = -1;
    @Expose public int presetSevenInvNineAmount = -1;
    @Expose public int presetSevenInvTenAmount = -1;

    @Expose public int presetSevenInvElevenAmount = -1;
    @Expose public int presetSevenInvTwelveAmount = -1;
    @Expose public int presetSevenInvThirteenAmount = -1;
    @Expose public int presetSevenInvFourteenAmount = -1;
    @Expose public int presetSevenInvFifteenAmount = -1;

    @Expose public int presetSevenInvSixteenAmount = -1;
    @Expose public int presetSevenInvSeventeenAmount = -1;
    @Expose public int presetSevenInvEighteenAmount = -1;
    @Expose public int presetSevenInvNineteenAmount = -1;
    @Expose public int presetSevenInvTwnetyAmount = -1;

    @Expose public int presetSevenInvTwentyOneAmount = -1;
    @Expose public int presetSevenInvTwentyTwoAmount = -1;
    @Expose public int presetSevenInvTwnetyThreeAmount = -1;
    @Expose public int presetSevenInvTwentyFourAmount = -1;
    @Expose public int presetSevenInvTwentyFiveAmount = -1;

    @Expose public int presetSevenInvTwnetySixAmount = -1;
    @Expose public int presetSevenInvTwentySevenAmount = -1;
    @Expose public int presetSevenInvTwentyEightAmount = -1;


    @Expose public int presetEightHelm = -1;
    @Expose public int presetEightChest = -1;
    @Expose public int presetEightLegs = -1;
    @Expose public int presetEightCape = -1;
    @Expose public int presetEightWeapon = -1;
    @Expose public int presetEightShield = -1;
    @Expose public int presetEightGloves = -1;
    @Expose public int presetEightBoots = -1;
    @Expose public int presetEightNeck = -1;
    @Expose public int presetEightRing = -1;
    @Expose public int presetEightAmmo = -1;
    @Expose public int presetEightAmmoAmount = -1;

    @Expose public int presetEightSpellbook = -1;

    @Expose public int presetEightInvOne = -1;
    @Expose public int presetEightInvTwo = -1;
    @Expose public int presetEightInvThree = -1;
    @Expose public int presetEightInvFour = -1;
    @Expose public int presetEightInvFive = -1;

    @Expose public int presetEightInvSix = -1;
    @Expose public int presetEightInvSeven = -1;
    @Expose public int presetEightInvEight = -1;
    @Expose public int presetEightInvNine = -1;
    @Expose public int presetEightInvTen = -1;

    @Expose public int presetEightInvEleven = -1;
    @Expose public int presetEightInvTwelve = -1;
    @Expose public int presetEightInvThirteen = -1;
    @Expose public int presetEightInvFourteen = -1;
    @Expose public int presetEightInvFifteen = -1;

    @Expose public int presetEightInvSixteen = -1;
    @Expose public int presetEightInvSeventeen = -1;
    @Expose public int presetEightInvEighteen = -1;
    @Expose public int presetEightInvNineteen = -1;
    @Expose public int presetEightInvTwnety = -1;

    @Expose public int presetEightInvTwentyOne = -1;
    @Expose public int presetEightInvTwentyTwo = -1;
    @Expose public int presetEightInvTwnetyThree = -1;
    @Expose public int presetEightInvTwentyFour = -1;
    @Expose public int presetEightInvTwentyFive = -1;

    @Expose public int presetEightInvTwnetySix = -1;
    @Expose public int presetEightInvTwentySeven = -1;
    @Expose public int presetEightInvTwentyEight = -1;

    @Expose public int presetEightInvOneAmount = -1;
    @Expose public int presetEightInvTwoAmount = -1;
    @Expose public int presetEightInvThreeAmount = -1;
    @Expose public int presetEightInvFourAmount = -1;
    @Expose public int presetEightInvFiveAmount = -1;

    @Expose public int presetEightInvSixAmount = -1;
    @Expose public int presetEightInvSevenAmount = -1;
    @Expose public int presetEightInvEightAmount = -1;
    @Expose public int presetEightInvNineAmount = -1;
    @Expose public int presetEightInvTenAmount = -1;

    @Expose public int presetEightInvElevenAmount = -1;
    @Expose public int presetEightInvTwelveAmount = -1;
    @Expose public int presetEightInvThirteenAmount = -1;
    @Expose public int presetEightInvFourteenAmount = -1;
    @Expose public int presetEightInvFifteenAmount = -1;

    @Expose public int presetEightInvSixteenAmount = -1;
    @Expose public int presetEightInvSeventeenAmount = -1;
    @Expose public int presetEightInvEighteenAmount = -1;
    @Expose public int presetEightInvNineteenAmount = -1;
    @Expose public int presetEightInvTwnetyAmount = -1;

    @Expose public int presetEightInvTwentyOneAmount = -1;
    @Expose public int presetEightInvTwentyTwoAmount = -1;
    @Expose public int presetEightInvTwnetyThreeAmount = -1;
    @Expose public int presetEightInvTwentyFourAmount = -1;
    @Expose public int presetEightInvTwentyFiveAmount = -1;

    @Expose public int presetEightInvTwnetySixAmount = -1;
    @Expose public int presetEightInvTwentySevenAmount = -1;
    @Expose public int presetEightInvTwentyEightAmount = -1;


    @Expose public int presetNineHelm = -1;
    @Expose public int presetNineChest = -1;
    @Expose public int presetNineLegs = -1;
    @Expose public int presetNineCape = -1;
    @Expose public int presetNineWeapon = -1;
    @Expose public int presetNineShield = -1;
    @Expose public int presetNineGloves = -1;
    @Expose public int presetNineBoots = -1;
    @Expose public int presetNineNeck = -1;
    @Expose public int presetNineRing = -1;
    @Expose public int presetNineAmmo = -1;
    @Expose public int presetNineAmmoAmount = -1;

    @Expose public int presetNineSpellbook = -1;

    @Expose public int presetNineInvOne = -1;
    @Expose public int presetNineInvTwo = -1;
    @Expose public int presetNineInvThree = -1;
    @Expose public int presetNineInvFour = -1;
    @Expose public int presetNineInvFive = -1;

    @Expose public int presetNineInvSix = -1;
    @Expose public int presetNineInvSeven = -1;
    @Expose public int presetNineInvEight = -1;
    @Expose public int presetNineInvNine = -1;
    @Expose public int presetNineInvTen = -1;

    @Expose public int presetNineInvEleven = -1;
    @Expose public int presetNineInvTwelve = -1;
    @Expose public int presetNineInvThirteen = -1;
    @Expose public int presetNineInvFourteen = -1;
    @Expose public int presetNineInvFifteen = -1;

    @Expose public int presetNineInvSixteen = -1;
    @Expose public int presetNineInvSeventeen = -1;
    @Expose public int presetNineInvEighteen = -1;
    @Expose public int presetNineInvNineteen = -1;
    @Expose public int presetNineInvTwnety = -1;

    @Expose public int presetNineInvTwentyOne = -1;
    @Expose public int presetNineInvTwentyTwo = -1;
    @Expose public int presetNineInvTwnetyThree = -1;
    @Expose public int presetNineInvTwentyFour = -1;
    @Expose public int presetNineInvTwentyFive = -1;

    @Expose public int presetNineInvTwnetySix = -1;
    @Expose public int presetNineInvTwentySeven = -1;
    @Expose public int presetNineInvTwentyEight = -1;

    @Expose public int presetNineInvOneAmount = -1;
    @Expose public int presetNineInvTwoAmount = -1;
    @Expose public int presetNineInvThreeAmount = -1;
    @Expose public int presetNineInvFourAmount = -1;
    @Expose public int presetNineInvFiveAmount = -1;

    @Expose public int presetNineInvSixAmount = -1;
    @Expose public int presetNineInvSevenAmount = -1;
    @Expose public int presetNineInvEightAmount = -1;
    @Expose public int presetNineInvNineAmount = -1;
    @Expose public int presetNineInvTenAmount = -1;

    @Expose public int presetNineInvElevenAmount = -1;
    @Expose public int presetNineInvTwelveAmount = -1;
    @Expose public int presetNineInvThirteenAmount = -1;
    @Expose public int presetNineInvFourteenAmount = -1;
    @Expose public int presetNineInvFifteenAmount = -1;

    @Expose public int presetNineInvSixteenAmount = -1;
    @Expose public int presetNineInvSeventeenAmount = -1;
    @Expose public int presetNineInvEighteenAmount = -1;
    @Expose public int presetNineInvNineteenAmount = -1;
    @Expose public int presetNineInvTwnetyAmount = -1;

    @Expose public int presetNineInvTwentyOneAmount = -1;
    @Expose public int presetNineInvTwentyTwoAmount = -1;
    @Expose public int presetNineInvTwnetyThreeAmount = -1;
    @Expose public int presetNineInvTwentyFourAmount = -1;
    @Expose public int presetNineInvTwentyFiveAmount = -1;

    @Expose public int presetNineInvTwnetySixAmount = -1;
    @Expose public int presetNineInvTwentySevenAmount = -1;
    @Expose public int presetNineInvTwentyEightAmount = -1;


    @Expose public int presetTenHelm = -1;
    @Expose public int presetTenChest = -1;
    @Expose public int presetTenLegs = -1;
    @Expose public int presetTenCape = -1;
    @Expose public int presetTenWeapon = -1;
    @Expose public int presetTenShield = -1;
    @Expose public int presetTenGloves = -1;
    @Expose public int presetTenBoots = -1;
    @Expose public int presetTenNeck = -1;
    @Expose public int presetTenRing = -1;
    @Expose public int presetTenAmmo = -1;
    @Expose public int presetTenAmmoAmount = -1;

    @Expose public int presetTenSpellbook = -1;

    @Expose public int presetTenInvOne = -1;
    @Expose public int presetTenInvTwo = -1;
    @Expose public int presetTenInvThree = -1;
    @Expose public int presetTenInvFour = -1;
    @Expose public int presetTenInvFive = -1;

    @Expose public int presetTenInvSix = -1;
    @Expose public int presetTenInvSeven = -1;
    @Expose public int presetTenInvEight = -1;
    @Expose public int presetTenInvNine = -1;
    @Expose public int presetTenInvTen = -1;

    @Expose public int presetTenInvEleven = -1;
    @Expose public int presetTenInvTwelve = -1;
    @Expose public int presetTenInvThirteen = -1;
    @Expose public int presetTenInvFourteen = -1;
    @Expose public int presetTenInvFifteen = -1;

    @Expose public int presetTenInvSixteen = -1;
    @Expose public int presetTenInvSeventeen = -1;
    @Expose public int presetTenInvEighteen = -1;
    @Expose public int presetTenInvNineteen = -1;
    @Expose public int presetTenInvTwnety = -1;

    @Expose public int presetTenInvTwentyOne = -1;
    @Expose public int presetTenInvTwentyTwo = -1;
    @Expose public int presetTenInvTwnetyThree = -1;
    @Expose public int presetTenInvTwentyFour = -1;
    @Expose public int presetTenInvTwentyFive = -1;

    @Expose public int presetTenInvTwnetySix = -1;
    @Expose public int presetTenInvTwentySeven = -1;
    @Expose public int presetTenInvTwentyEight = -1;

    @Expose public int presetTenInvOneAmount = -1;
    @Expose public int presetTenInvTwoAmount = -1;
    @Expose public int presetTenInvThreeAmount = -1;
    @Expose public int presetTenInvFourAmount = -1;
    @Expose public int presetTenInvFiveAmount = -1;

    @Expose public int presetTenInvSixAmount = -1;
    @Expose public int presetTenInvSevenAmount = -1;
    @Expose public int presetTenInvEightAmount = -1;
    @Expose public int presetTenInvNineAmount = -1;
    @Expose public int presetTenInvTenAmount = -1;

    @Expose public int presetTenInvElevenAmount = -1;
    @Expose public int presetTenInvTwelveAmount = -1;
    @Expose public int presetTenInvThirteenAmount = -1;
    @Expose public int presetTenInvFourteenAmount = -1;
    @Expose public int presetTenInvFifteenAmount = -1;

    @Expose public int presetTenInvSixteenAmount = -1;
    @Expose public int presetTenInvSeventeenAmount = -1;
    @Expose public int presetTenInvEighteenAmount = -1;
    @Expose public int presetTenInvNineteenAmount = -1;
    @Expose public int presetTenInvTwnetyAmount = -1;

    @Expose public int presetTenInvTwentyOneAmount = -1;
    @Expose public int presetTenInvTwentyTwoAmount = -1;
    @Expose public int presetTenInvTwnetyThreeAmount = -1;
    @Expose public int presetTenInvTwentyFourAmount = -1;
    @Expose public int presetTenInvTwentyFiveAmount = -1;

    @Expose public int presetTenInvTwnetySixAmount = -1;
    @Expose public int presetTenInvTwentySevenAmount = -1;
    @Expose public int presetTenInvTwentyEightAmount = -1;


    @Expose public int presetElevenHelm = -1;
    @Expose public int presetElevenChest = -1;
    @Expose public int presetElevenLegs = -1;
    @Expose public int presetElevenCape = -1;
    @Expose public int presetElevenWeapon = -1;
    @Expose public int presetElevenShield = -1;
    @Expose public int presetElevenGloves = -1;
    @Expose public int presetElevenBoots = -1;
    @Expose public int presetElevenNeck = -1;
    @Expose public int presetElevenRing = -1;
    @Expose public int presetElevenAmmo = -1;
    @Expose public int presetElevenAmmoAmount = -1;

    @Expose public int presetElevenSpellbook = -1;

    @Expose public int presetElevenInvOne = -1;
    @Expose public int presetElevenInvTwo = -1;
    @Expose public int presetElevenInvThree = -1;
    @Expose public int presetElevenInvFour = -1;
    @Expose public int presetElevenInvFive = -1;

    @Expose public int presetElevenInvSix = -1;
    @Expose public int presetElevenInvSeven = -1;
    @Expose public int presetElevenInvEight = -1;
    @Expose public int presetElevenInvNine = -1;
    @Expose public int presetElevenInvTen = -1;

    @Expose public int presetElevenInvEleven = -1;
    @Expose public int presetElevenInvTwelve = -1;
    @Expose public int presetElevenInvThirteen = -1;
    @Expose public int presetElevenInvFourteen = -1;
    @Expose public int presetElevenInvFifteen = -1;

    @Expose public int presetElevenInvSixteen = -1;
    @Expose public int presetElevenInvSeventeen = -1;
    @Expose public int presetElevenInvEighteen = -1;
    @Expose public int presetElevenInvNineteen = -1;
    @Expose public int presetElevenInvTwnety = -1;

    @Expose public int presetElevenInvTwentyOne = -1;
    @Expose public int presetElevenInvTwentyTwo = -1;
    @Expose public int presetElevenInvTwnetyThree = -1;
    @Expose public int presetElevenInvTwentyFour = -1;
    @Expose public int presetElevenInvTwentyFive = -1;

    @Expose public int presetElevenInvTwnetySix = -1;
    @Expose public int presetElevenInvTwentySeven = -1;
    @Expose public int presetElevenInvTwentyEight = -1;

    @Expose public int presetElevenInvOneAmount = -1;
    @Expose public int presetElevenInvTwoAmount = -1;
    @Expose public int presetElevenInvThreeAmount = -1;
    @Expose public int presetElevenInvFourAmount = -1;
    @Expose public int presetElevenInvFiveAmount = -1;

    @Expose public int presetElevenInvSixAmount = -1;
    @Expose public int presetElevenInvSevenAmount = -1;
    @Expose public int presetElevenInvEightAmount = -1;
    @Expose public int presetElevenInvNineAmount = -1;
    @Expose public int presetElevenInvTenAmount = -1;

    @Expose public int presetElevenInvElevenAmount = -1;
    @Expose public int presetElevenInvTwelveAmount = -1;
    @Expose public int presetElevenInvThirteenAmount = -1;
    @Expose public int presetElevenInvFourteenAmount = -1;
    @Expose public int presetElevenInvFifteenAmount = -1;

    @Expose public int presetElevenInvSixteenAmount = -1;
    @Expose public int presetElevenInvSeventeenAmount = -1;
    @Expose public int presetElevenInvEighteenAmount = -1;
    @Expose public int presetElevenInvNineteenAmount = -1;
    @Expose public int presetElevenInvTwnetyAmount = -1;

    @Expose public int presetElevenInvTwentyOneAmount = -1;
    @Expose public int presetElevenInvTwentyTwoAmount = -1;
    @Expose public int presetElevenInvTwnetyThreeAmount = -1;
    @Expose public int presetElevenInvTwentyFourAmount = -1;
    @Expose public int presetElevenInvTwentyFiveAmount = -1;

    @Expose public int presetElevenInvTwnetySixAmount = -1;
    @Expose public int presetElevenInvTwentySevenAmount = -1;
    @Expose public int presetElevenInvTwentyEightAmount = -1;


    @Expose public int presetTwelveHelm = -1;
    @Expose public int presetTwelveChest = -1;
    @Expose public int presetTwelveLegs = -1;
    @Expose public int presetTwelveCape = -1;
    @Expose public int presetTwelveWeapon = -1;
    @Expose public int presetTwelveShield = -1;
    @Expose public int presetTwelveGloves = -1;
    @Expose public int presetTwelveBoots = -1;
    @Expose public int presetTwelveNeck = -1;
    @Expose public int presetTwelveRing = -1;
    @Expose public int presetTwelveAmmo = -1;
    @Expose public int presetTwelveAmmoAmount = -1;

    @Expose public int presetTwelveSpellbook = -1;

    @Expose public int presetTwelveInvOne = -1;
    @Expose public int presetTwelveInvTwo = -1;
    @Expose public int presetTwelveInvThree = -1;
    @Expose public int presetTwelveInvFour = -1;
    @Expose public int presetTwelveInvFive = -1;

    @Expose public int presetTwelveInvSix = -1;
    @Expose public int presetTwelveInvSeven = -1;
    @Expose public int presetTwelveInvEight = -1;
    @Expose public int presetTwelveInvNine = -1;
    @Expose public int presetTwelveInvTen = -1;

    @Expose public int presetTwelveInvEleven = -1;
    @Expose public int presetTwelveInvTwelve = -1;
    @Expose public int presetTwelveInvThirteen = -1;
    @Expose public int presetTwelveInvFourteen = -1;
    @Expose public int presetTwelveInvFifteen = -1;

    @Expose public int presetTwelveInvSixteen = -1;
    @Expose public int presetTwelveInvSeventeen = -1;
    @Expose public int presetTwelveInvEighteen = -1;
    @Expose public int presetTwelveInvNineteen = -1;
    @Expose public int presetTwelveInvTwnety = -1;

    @Expose public int presetTwelveInvTwentyOne = -1;
    @Expose public int presetTwelveInvTwentyTwo = -1;
    @Expose public int presetTwelveInvTwnetyThree = -1;
    @Expose public int presetTwelveInvTwentyFour = -1;
    @Expose public int presetTwelveInvTwentyFive = -1;

    @Expose public int presetTwelveInvTwnetySix = -1;
    @Expose public int presetTwelveInvTwentySeven = -1;
    @Expose public int presetTwelveInvTwentyEight = -1;

    @Expose public int presetTwelveInvOneAmount = -1;
    @Expose public int presetTwelveInvTwoAmount = -1;
    @Expose public int presetTwelveInvThreeAmount = -1;
    @Expose public int presetTwelveInvFourAmount = -1;
    @Expose public int presetTwelveInvFiveAmount = -1;

    @Expose public int presetTwelveInvSixAmount = -1;
    @Expose public int presetTwelveInvSevenAmount = -1;
    @Expose public int presetTwelveInvEightAmount = -1;
    @Expose public int presetTwelveInvNineAmount = -1;
    @Expose public int presetTwelveInvTenAmount = -1;

    @Expose public int presetTwelveInvElevenAmount = -1;
    @Expose public int presetTwelveInvTwelveAmount = -1;
    @Expose public int presetTwelveInvThirteenAmount = -1;
    @Expose public int presetTwelveInvFourteenAmount = -1;
    @Expose public int presetTwelveInvFifteenAmount = -1;

    @Expose public int presetTwelveInvSixteenAmount = -1;
    @Expose public int presetTwelveInvSeventeenAmount = -1;
    @Expose public int presetTwelveInvEighteenAmount = -1;
    @Expose public int presetTwelveInvNineteenAmount = -1;
    @Expose public int presetTwelveInvTwnetyAmount = -1;

    @Expose public int presetTwelveInvTwentyOneAmount = -1;
    @Expose public int presetTwelveInvTwentyTwoAmount = -1;
    @Expose public int presetTwelveInvTwnetyThreeAmount = -1;
    @Expose public int presetTwelveInvTwentyFourAmount = -1;
    @Expose public int presetTwelveInvTwentyFiveAmount = -1;

    @Expose public int presetTwelveInvTwnetySixAmount = -1;
    @Expose public int presetTwelveInvTwentySevenAmount = -1;
    @Expose public int presetTwelveInvTwentyEightAmount = -1;

    /**
     * Dueling stuff
     */

    @Expose public int presetDuelVarp, lastDuelVarp;

    @Expose public int duelWins, duelLosses;

    public TickDelay acceptDelay = new TickDelay();

    public int totalStaked = 0;

    public int bloodMoneyStaked = 0;

    public int bloodyTokensStaked = 0;

    @Expose public boolean experienceLock;

    /**
     * Time Rift
     */

    @Expose public TeamTasks[] teamTasks = new TeamTasks[1];
    @Expose public int[] teamTaskProgress = new int[1];
    @Expose public IndividualTasks[] soloTasks = new IndividualTasks[1];
    @Expose public int[] soloTaskProgress = new int[1];

    @Expose public int riftShards;

    @Expose public boolean hasAcceptedWoodcuttingTask;
    @Expose public boolean hasAcceptedMiningTask;
    @Expose public boolean hasAcceptedFishingTask;

    @Expose public boolean hasCompletedWoodcuttingTask;
    @Expose public boolean hasCompletedMiningTask;
    @Expose public boolean hasCompletedFishingTask;

    @Expose public int woodcuttingTaskAmount;
    @Expose public int fishingTaskAmount;
    @Expose public int miningTaskAmount;

    @Expose @Getter public WoodcuttingTaskType woodcuttingTaskType;
    @Expose @Getter public FishingTaskType fishingTaskType;
    @Expose @Getter public MiningTaskType miningTaskType;


    /**
     * Loot Keys
     */

    @Expose public int keyonevalue = 0;
    @Expose public int keytwovalue = 0;
    @Expose public int keythreevalue = 0;
    @Expose public int keyfourvalue = 0;
    @Expose public int keyfivevalue = 0;

    //Key 1
    @Expose public int keyonehelmetslot, keyonecapeslot, keyoneamuletslot, keyoneammoslot, keyoneammoamount,
            keyoneweaponslot, keyonechestslot, keyoneshieldslot, keyonelegsslot, keyoneglovesslot,
            keyonebootsslot, keyoneringslot = -1;
    @Expose public int keyoneinvone, keyoneinvtwo, keyoneinvthree, keyoneinvfour, keyoneinvfive, keyoneinvsix
            , keyoneinvseven, keyoneinveight, keyoneinvnine, keyoneinvten, keyoneinveleven, keyoneinvtwelve
            , keyoneinvthirteen, keyoneinvfourteen, keyoneinvfifteen, keyoneinvsixteen, keyoneinvseventeen
            , keyoneinveighteen, keyoneinvnineteen, keyoneinvtwenty, keyoneinvtwentyone, keyoneinvtwentytwo
            , keyoneinvtwentythree, keyoneinvtwentyfour, keyoneinvtwentyfive, keyoneinvtwentysix,
            keyoneinvtwentyseven, keyoneinvtwentyeight = -1;
    @Expose public int keyoneinvoneamt, keyoneinvtwoamt, keyoneinvthreeamt, keyoneinvfouramt, keyoneinvfiveamt,
            keyoneinvsixamt, keyoneinvsevenamt, keyoneinveightamt, keyoneinvnineamt, keyoneinvtenamt, keyoneinvelevenamt,
            keyoneinvtwelveamt, keyoneinvthirteenamt, keyoneinvfourteenamt, keyoneinvfifteenamt, keyoneinvsixteenamt,
            keyoneinvseventeenamt, keyoneinveighteenamt, keyoneinvnineteenamt, keyoneinvtwentyamt, keyoneinvtwentyoneamt,
            keyoneinvtwentytwoamt, keyoneinvtwentythreeamt, keyoneinvtwentyfouramt, keyoneinvtwentyfiveamt,
            keyoneinvtwentysixamt, keyoneinvtwentysevenamt, keyoneinvtwentyeightamt;
    //Key 2
    @Expose public int keytwohelmetslot, keytwocapeslot, keytwoamuletslot, keytwoammoslot,
            keytwoweaponslot, keytwochestslot, keytwoshieldslot, keytwolegsslot, keytwoglovesslot,
            keytwobootsslot, keytworingslot = -1;
    @Expose public int keytwoinvone, keytwoinvtwo, keytwoinvthree, keytwoinvfour, keytwoinvfive, keytwoinvsix
            , keytwoinvseven, keytwoinveight, keytwoinvnine, keytwoinvten, keytwoinveleven, keytwoinvtwelve
            , keytwoinvthirteen, keytwoinvfourteen, keytwoinvfifteen, keytwoinvsixteen, keytwoinvseventeen
            , keytwoinveighteen, keytwoinvnineteen, keytwoinvtwenty, keytwoinvtwentyone, keytwoinvtwentytwo
            , keytwoinvtwentythree, keytwoinvtwentyfour, keytwoinvtwentyfive, keytwoinvtwentysix,
            keytwoinvtwentyseven, keytwoinvtwentyeight = -1;
    @Expose public int keytwoinvoneamt, keytwoinvtwoamt, keytwoinvthreeamt, keytwoinvfouramt, keytwoinvfiveamt,
            keytwoinvsixamt, keytwoinvsevenamt, keytwoinveightamt, keytwoinvnineamt, keytwoinvtenamt, keytwoinvelevenamt,
            keytwoinvtwelveamt, keytwoinvthirteenamt, keytwoinvfourteenamt, keytwoinvfifteenamt, keytwoinvsixteenamt,
            keytwoinvseventeenamt, keytwoinveighteenamt, keytwoinvnineteenamt, keytwoinvtwentyamt, keytwoinvtwentyoneamt,
            keytwoinvtwentytwoamt, keytwoinvtwentythreeamt, keytwoinvtwentyfouramt, keytwoinvtwentyfiveamt,
            keytwoinvtwentysixamt, keytwoinvtwentyseventamt, keytwoinvtwentyeightamt;
    //Key 3
    @Expose public int keythreehelmetslot, keythreecapeslot, keythreeamuletslot, keythreeammoslot,
            keythreeweaponslot, keythreechestslot, keythreeshieldslot, keythreelegsslot, keythreeglovesslot,
            keythreebootsslot, keythreeringslot = -1;
    @Expose public int keythreeinvone, keythreeinvtwo, keythreeinvthree, keythreeinvfour, keythreeinvfive, keythreeinvsix
            , keythreeinvseven, keythreeinveight, keythreeinvnine, keythreeinvten, keythreeinveleven, keythreeinvtwelve
            , keythreeinvthirteen, keythreeinvfourteen, keythreeinvfifteen, keythreeinvsixteen, keythreeinvseventeen
            , keythreeinveighteen, keythreeinvnineteen, keythreeinvtwenty, keythreeinvtwentyone, keythreeinvtwentytwo
            , keythreeinvtwentythree, keythreeinvtwentyfour, keythreeinvtwentyfive, keythreeinvtwentysix,
            keythreeinvtwentyseven, keythreeinvtwentyeight = -1;
    @Expose public int keythreeinvoneamt, keythreeinvtwoamt, keythreeinvthreeamt, keythreeinvfouramt, keythreeinvfiveamt,
            keythreeinvsixamt, keythreeinvsevenamt, keythreeinveightamt, keythreeinvnineamt, keythreeinvtenamt, keythreeinvelevenamt,
            keythreeinvtwelveamt, keythreeinvthirteenamt, keythreeinvfourteenamt, keythreeinvfifteenamt, keythreeinvsixteenamt,
            keythreeinvseventeenamt, keythreeinveighteenamt, keythreeinvnineteenamt, keythreeinvtwentyamt, keythreeinvtwentyoneamt,
            keythreeinvtwentytwoamt, keythreeinvtwentythreeamt, keythreeinvtwentyfouramt, keythreeinvtwentyfiveamt,
            keythreeinvtwentysixamt, keythreeinvtwentyseventamt, keythreeinvtwentyeightamt;
    //Key 4
    @Expose public int keyfourhelmetslot, keyfourcapeslot, keyfouramuletslot, keyfourammoslot,
            keyfourweaponslot, keyfourchestslot, keyfourshieldslot, keyfourlegsslot, keyfourglovesslot,
            keyfourbootsslot, keyfourringslot = -1;
    @Expose public int keyfourinvone, keyfourinvtwo, keyfourinvthree, keyfourinvfour, keyfourinvfive, keyfourinvsix
            , keyfourinvseven, keyfourinveight, keyfourinvnine, keyfourinvten, keyfourinveleven, keyfourinvtwelve
            , keyfourinvthirteen, keyfourinvfourteen, keyfourinvfifteen, keyfourinvsixteen, keyfourinvseventeen
            , keyfourinveighteen, keyfourinvnineteen, keyfourinvtwenty, keyfourinvtwentyone, keyfourinvtwentytwo
            , keyfourinvtwentythree, keyfourinvtwentyfour, keyfourinvtwentyfive, keyfourinvtwentysix,
            keyfourinvtwentyseven, keyfourinvtwentyeight = -1;
    @Expose public int keyfourinvoneamt, keyfourinvtwoamt, keyfourinvthreeamt, keyfourinvfouramt, keyfourinvfiveamt,
            keyfourinvsixamt, keyfourinvsevenamt, keyfourinveightamt, keyfourinvnineamt, keyfourinvtenamt, keyfourinvelevenamt,
            keyfourinvtwelveamt, keyfourinvthirteenamt, keyfourinvfourteenamt, keyfourinvfifteenamt, keyfourinvsixteenamt,
            keyfourinvseventeenamt, keyfourinveighteenamt, keyfourinvnineteenamt, keyfourinvtwentyamt, keyfourinvtwentyoneamt,
            keyfourinvtwentytwoamt, keyfourinvtwentythreeamt, keyfourinvtwentyfouramt, keyfourinvtwentyfiveamt,
            keyfourinvtwentysixamt, keyfourinvtwentyseventamt, keyfourinvtwentyeightamt;
    //Key 5
    @Expose public int keyfivehelmetslot, keyfivecapeslot, keyfiveamuletslot, keyfiveammoslot,
            keyfiveweaponslot, keyfivechestslot, keyfiveshieldslot, keyfivelegsslot, keyfiveglovesslot,
            keyfivebootsslot, keyfiveringslot = -1;
    @Expose public int keyfiveinvone, keyfiveinvtwo, keyfiveinvthree, keyfiveinvfour, keyfiveinvfive, keyfiveinvsix
            , keyfiveinvseven, keyfiveinveight, keyfiveinvnine, keyfiveinvten, keyfiveinveleven, keyfiveinvtwelve
            , keyfiveinvthirteen, keyfiveinvfourteen, keyfiveinvfifteen, keyfiveinvsixteen, keyfiveinvseventeen
            , keyfiveinveighteen, keyfiveinvnineteen, keyfiveinvtwenty, keyfiveinvtwentyone, keyfiveinvtwentytwo
            , keyfiveinvtwentythree, keyfiveinvtwentyfour, keyfiveinvtwentyfive, keyfiveinvtwentysix,
            keyfiveinvtwentyseven, keyfiveinvtwentyeight = -1;
    @Expose public int keyfiveinvoneamt, keyfiveinvtwoamt, keyfiveinvthreeamt, keyfiveinvfouramt, keyfiveinvfiveamt,
            keyfiveinvsixamt, keyfiveinvsevenamt, keyfiveinveightamt, keyfiveinvnineamt, keyfiveinvtenamt, keyfiveinvelevenamt,
            keyfiveinvtwelveamt, keyfiveinvthirteenamt, keyfiveinvfourteenamt, keyfiveinvfifteenamt, keyfiveinvsixteenamt,
            keyfiveinvseventeenamt, keyfiveinveighteenamt, keyfiveinvnineteenamt, keyfiveinvtwentyamt, keyfiveinvtwentyoneamt,
            keyfiveinvtwentytwoamt, keyfiveinvtwentythreeamt, keyfiveinvtwentyfouramt, keyfiveinvtwentyfiveamt,
            keyfiveinvtwentysixamt, keyfiveinvtwentyseventamt, keyfiveinvtwentyeightamt;

    /**
     * Wilderness stuff
     */

    public int wildernessLevel = -1;

    @Expose public boolean mageArena, resourceArea;

    @Expose public boolean obeliskRedirectionScroll;

    @Expose public WildernessObelisk obeliskDestination;

    /**
     * Achievements
     */

    public AchievementStage[] achievementStages = new AchievementStage[Achievement.values().length];

    /**
     * Gem bag
     */
    @Expose public int gemBagDiamond;
    @Expose public int gemBagRuby;
    @Expose public int gemBagEmerald;
    @Expose public int gemBagSapphire;
    @Expose public int gemBagJade;
    @Expose public int gemBagOpal;
    @Expose public int gemBagTopaz;
    @Expose public int gemBagDragonstone;
    @Expose public int gemBagOnyx;
    @Expose public int gemBagZenyte;

    /**
     * Herb sack
     */
    @Expose public int herbSackGuamLeaf;
    @Expose public int herbSackMarrentill;
    @Expose public int herbSackTarromin;
    @Expose public int herbSackHarralander;
    @Expose public int herbSackRanarrWeed;
    @Expose public int herbSackToadflax;
    @Expose public int herbSackIritLeaf;
    @Expose public int herbSackAvantoe;
    @Expose public int herbSackKwuarm;
    @Expose public int herbSackSnapdragon;
    @Expose public int herbSackCadantine;
    @Expose public int herbSackLantadyme;
    @Expose public int herbSackDwarfWeed;
    @Expose public int herbSackTorstol;

    /**
     * Fight caves
     */
    @Expose public FightCaves fightCaves;
    @Expose public long fightCavesBestTime;

    /**
     * Six Jad Challenge
     */
    @Expose public boolean Challenge2 = false;
    @Expose public boolean Challenge3 = false;
    @Expose public boolean Challenge4 = false;
    @Expose public boolean Challenge5 = false;
    @Expose public boolean Challenge6 = false;
    @Expose public JadChallenge sixjad;
    @Expose public long sixjadBestTime;

    /**
     * Inferno
     */
    @Expose public boolean talkedToTzHaarKetKeh;
    @Expose public Inferno inferno;
    @Expose public long infernoBestTime;

    public ActivityTimer corpTimer;
    @Expose public long corpBestTime;

    public ActivityTimer nightmareTimer;
    @Expose public long nightmareBestTime;

    public ActivityTimer vorkathTimer;
    @Expose public long vorkathBestTime;

    @Expose public long serenBestTime;

    /**
     * Warriors guild
     */
    public TickDelay tokenEvent = new TickDelay();
    public int nextDefenderId = -1;

    /**
     * Wintertodt
     */
    public int wintertodtPoints = 0;
    @Expose public int lifetimeWintertodtPoints;
    @Expose public int wintertodtSubdued;
    @Expose public int wintertodtHighscore;
    @Expose public boolean talkedToIgnisia;

    public MimicLair mimicLair;
    /**
     * Hunter
     */
    public ArrayList<Trap> traps = new ArrayList<>(5);
    @Expose public int caughtSwift, caughtWarbler, caughtLongtail, caughtTwitch, caughtWagtail;
    @Expose public int caughtGreyChinchompa, caughtRedChinchompa, caughtBlackChinchompa;
    @Expose public int caughtSwampLizard, caughtOrangeSalamander, caughtRedSalamander, caughtBlackSalamander;

    /**
     * Godwars
     */
    public TickDelay godwarsAltarCooldown = new TickDelay();

    /**
     * Slayer
     */
    @Expose public String slayerTaskName;
    @Expose public int slayerTaskRemaining;
    @Expose public int slayerTaskAmountAssigned;
    @Expose public List<String> slayerBlockedTasks;
    @Expose public int slayerTasksCompleted;
    @Expose public boolean slayerTaskDangerous = false;
    @Expose public int wildernessTasksCompleted;
    @Expose public int slayerSpree = 0;

    public TickDelay blackChinchompaBoost = new TickDelay();
    @Expose public int blackChinchompaBoostTimeLeft;
    public TickDelay darkCrabBoost = new TickDelay();
    @Expose public int darkCrabBoostTimeLeft;

    /**
     * Antifire
     */
    @Expose public int antifireTicks;
    @Expose public int superAntifireTicks;

    /**
     * Alch
     */
    public TickDelay alchDelay = new TickDelay();


    /**
     * Vengeance
     */

    @Expose public boolean vengeanceActive;

    /**
     * Superheat delay
     */
    public TickDelay superheatDelay = new TickDelay();

    /**
     * Runecrafting pouch
     */
    @Expose public Map<EssencePouch, Integer> runeEssencePouches = new HashMap<>();

    /**
     * Mute
     */

    @Expose public long muteEnd;
    @Expose public String mutedBy;
    @Expose public String mutedReason;

    @Expose public boolean shadowMute;

    /**
     * Jail
     */

    @Expose public String jailerName;
    @Expose public String jailerReason;
    @Expose public int jailOresAssigned, jailOresCollected;
    @Expose public int punishedByRank;

    /**
     * Kill spree/shutdown
     */
    @Expose public int currentKillSpree;
    @Expose public int highestKillSpree;
    @Expose public int highestShutdown;
    @Expose public long nextWildernessBonus;

    /**
     * Farm prevention
     */
    @Expose public TimedList bmIpLogs = new TimedList();
    @Expose public TimedList bmUserLogs = new TimedList();

    /**
     * Runecrafting achievement thingies
     */
    @Expose public boolean enteredAbyss;
    @Expose public int abyssCreaturesKilled;

    /**
     * Spec effect
     */
    @Expose public int dragonAxeSpecial;
    @Expose public int infernalAxeSpecial;
    @Expose public int dragonPickaxeSpecial;
    @Expose public int infernalPickaxeSpecial;

    @Expose public boolean morytaniaFarmAchievement;

    public OptionScroll optionScroll;

    /**
     * Kill counter interface handler stuff (I dont like this)
     */
    public List<Function<Player, KillCounter>> activeKillLogList = null;
    public int activeKillLogSlot = -1;


    /**
     * Boss kill counters
     */
    @Expose public KillCounter kreeArraKills = new KillCounter();
    @Expose public KillCounter commanderZilyanaKills = new KillCounter();
    @Expose public KillCounter generalGraardorKills = new KillCounter();
    @Expose public KillCounter krilTsutsarothKills = new KillCounter();
    @Expose public KillCounter dagannothRexKills = new KillCounter();
    @Expose public KillCounter dagannothPrimeKills = new KillCounter();
    @Expose public KillCounter dagannothSupremeKills = new KillCounter();
    @Expose public KillCounter giantMoleKills = new KillCounter();
    @Expose public KillCounter kalphiteQueenKills = new KillCounter();
    @Expose public KillCounter kingBlackDragonKills = new KillCounter();
    @Expose public KillCounter callistoKills = new KillCounter();
    @Expose public KillCounter venenatisKills = new KillCounter();
    @Expose public KillCounter vetionKills = new KillCounter();
    @Expose public KillCounter chaosElementalKills = new KillCounter();
    @Expose public KillCounter chaosFanaticKills = new KillCounter();
    @Expose public KillCounter crazyArchaeologistKills = new KillCounter();
    @Expose public KillCounter scorpiaKills = new KillCounter();
    @Expose public KillCounter barrowsChestsLooted = new KillCounter();
    @Expose public KillCounter corporealBeastKills = new KillCounter();
    @Expose public KillCounter zulrahKills = new KillCounter();
    @Expose public KillCounter nexKills = new KillCounter();
    @Expose public KillCounter jadCounter = new KillCounter();
    @Expose public KillCounter zukKills = new KillCounter();
    @Expose public KillCounter krakenKills = new KillCounter();
    @Expose public KillCounter thermonuclearSmokeDevilKills = new KillCounter();
    @Expose public KillCounter cerberusKills = new KillCounter();
    @Expose public KillCounter abyssalSireKills = new KillCounter();
    @Expose public KillCounter skotizoKills = new KillCounter();
    @Expose public KillCounter wintertodtKills = new KillCounter();
    @Expose public KillCounter oborKills = new KillCounter();
    @Expose public KillCounter chambersofXericKills = new KillCounter();
    @Expose public KillCounter derangedArchaeologistKills = new KillCounter();
    @Expose public KillCounter elvargKills = new KillCounter();
    @Expose public KillCounter vorkathKills = new KillCounter();
    @Expose public KillCounter nightmareKills = new KillCounter();

    @Expose public KillCounter serenKills = new KillCounter();

    /**
     * Slayer kill counters
     */
    @Expose public KillCounter crawlingHandKills = new KillCounter();
    @Expose public KillCounter caveBugKills = new KillCounter();
    @Expose public KillCounter caveCrawlerKills = new KillCounter();
    @Expose public KillCounter bansheeKills = new KillCounter();
    @Expose public KillCounter caveSlimeKills = new KillCounter();
    @Expose public KillCounter rockslugKills = new KillCounter();
    @Expose public KillCounter desertLizardKills = new KillCounter();
    @Expose public KillCounter cockatriceKills = new KillCounter();
    @Expose public KillCounter pyrefiendKills = new KillCounter();
    @Expose public KillCounter mogreKills = new KillCounter();
    @Expose public KillCounter harpieBugSwarmKills = new KillCounter();
    @Expose public KillCounter wallBeastKills = new KillCounter();
    @Expose public KillCounter killerwattKills = new KillCounter();
    @Expose public KillCounter molaniskKills = new KillCounter();
    @Expose public KillCounter basiliskKills = new KillCounter();
    @Expose public KillCounter seaSnakeKills = new KillCounter();
    @Expose public KillCounter terrorDogKills = new KillCounter();
    @Expose public KillCounter feverSpiderKills = new KillCounter();
    @Expose public KillCounter infernalMageKills = new KillCounter();
    @Expose public KillCounter brineRatKills = new KillCounter();
    @Expose public KillCounter bloodveldKills = new KillCounter();
    @Expose public KillCounter jellyKills = new KillCounter();
    @Expose public KillCounter turothKills = new KillCounter();
    @Expose public KillCounter mutatedZygomiteKills = new KillCounter();
    @Expose public KillCounter caveHorrorKills = new KillCounter();
    @Expose public KillCounter aberrantSpectreKills = new KillCounter();
    @Expose public KillCounter spiritualRangerKills = new KillCounter();
    @Expose public KillCounter dustDevilKills = new KillCounter();
    @Expose public KillCounter spiritualWarriorKills = new KillCounter();
    @Expose public KillCounter kuraskKills = new KillCounter();
    @Expose public KillCounter skeletalWyvernKills = new KillCounter();
    @Expose public KillCounter gargoyleKills = new KillCounter();
    @Expose public KillCounter nechryaelKills = new KillCounter();
    @Expose public KillCounter spiritualMageKills = new KillCounter();
    @Expose public KillCounter abyssalDemonKills = new KillCounter();
    @Expose public KillCounter caveKrakenKills = new KillCounter();
    @Expose public KillCounter darkBeastKills = new KillCounter();
    @Expose public KillCounter smokeDevilKills = new KillCounter();
    @Expose public KillCounter superiorCreatureKills = new KillCounter();
    @Expose public KillCounter brutalBlackDragonKills = new KillCounter();
    @Expose public KillCounter fossilIslandWyvernsKills = new KillCounter();

    /**
     * Misc kill counters
     */
    @Expose public KillCounter demonicGorillaKills = new KillCounter();
    @Expose public KillCounter adamantDragonKills = new KillCounter();
    @Expose public KillCounter runeDragonKills = new KillCounter();
    @Expose public KillCounter jungleDemonKills = new KillCounter();


    public ActivityTimer zulrahTimer;
    @Expose public long zulrahBestTime;

    public ActivityTimer alchemicalTimer;
    @Expose public long alchemicalBestTime;

    public ActivityTimer coxTimer;
    @Expose public long coxBestTime;



    /**
     * Staff of the dead
     */
    public TickDelay sotdDelay = new TickDelay();

    /**
     * Rock cake
     */
    public TickDelay rockCakeDelay = new TickDelay();

    /**
     * new Make-X interface last chosen amount
     */
    @Expose public int lastMakeXAmount;

    /**
     * Hide donator icons
     */
    @Expose public boolean hidePlayerIcon = false;

    /**
     * Pet
     */

    @Expose public Pet pet;

    public boolean callPet;

    public boolean hidePet;

    public boolean showPet;


    public boolean thrallActive = false;


    /**
     * Presets
     */

    @Expose public PresetCustom[] customPresets = new PresetCustom[PresetCustom.ENTRIES.length];

    /**
     * Elo rating
     */
    @Expose public int pkRating = WildernessRating.DEFAULT_RATING;

    /**
     * PVP Instance position
     */
    @Expose public Position pvpInstancePosition;

    /**
     * Loyalty Chest
     */
    @Expose public long loyaltyTimer = System.currentTimeMillis();
    @Expose public long loyaltySpreeTimer = System.currentTimeMillis();
    @Expose public int loyaltyChestReward = 1;
    @Expose public int loyaltyChestCount;
    @Expose public int loyaltyChestSpree;
    @Expose public int highestLoyaltyChestSpree;

    @Expose public long pvpVaultTimer = System.currentTimeMillis();
    @Expose public int pvpVaultActivities;

    /*
     * first3 stuff
     */
    public TickDelay first3 = new TickDelay();
    @Expose public int first3TimeLeft;

    /*
     * Custom Points
     */
    @Expose public int PvmPoints;

    /**
     * Store
     */

    @Expose public int storeAmountSpent;
    @Expose public List<Item> claimedStoreItems;


    @Expose public int staffRank;
    @Expose public int youtubeRank;


    @Expose public boolean ispvpMode = false;
    @Expose public boolean isRealism = false;


    /**
     * Resting
     */
    @Expose public boolean resting;

    /**
     * Elder chaos druid teleport
     */
    public TickDelay elderChaosDruidTeleport = new TickDelay();

    /**
     * Tournament system attributes
     */
    public boolean tempUseRaidPrayers = false;
    @Expose public boolean joinedTournament = false; // Save this in case the server disconnects/restarts during the waiting lobby
    public Position viewingOrbLocation;
    public boolean usingTournamentOrb = false;
    public boolean usingTournamentOrbFromHome = false;
    public int cachedRunePouchTypes;
    public int cachedRunePouchAmounts;
    public boolean tournamentPouch = false;
    public boolean tournamentAugury = false;
    public boolean tournamentRigour = false;
    public boolean tournamentPreserve = false;
    @Expose public int tournamentWins = 0;
    @Expose public int[] preTournyAttack, preTournyStrength, preTournyDefence, preTournyRanged, preTournyPrayer, preTournyMagic, preTournyHitpoints;
    @Expose public int preTournySpellbook;

    /**
     * Login date
     */
    public long sessionStart = System.currentTimeMillis();

    /**
     * Bestiary
     */
    public List<JournalEntry> bestiarySearchResults;

    /**
     * Active valcano
     */
    public int bloodyFragments;

    /**
     * Raids party settings
     */
    @Expose public int partyAdvertisementsRemaining = 15;
    public boolean advertisingParty = false;
    public long advertisementStartTick = 0L;
    public Party raidsParty;
    public Party viewingParty;
    @Expose public boolean raidsEntranceWarning = false;

    public Party twovtwoRatedParty;
    public boolean twovtwoadvertisingParty = false;
    public Party twovtwoviewingParty;
    public long twovtwoadvertisementStartTick = 0L;
    /**
     * Ring of suffering
     */
    @Expose public boolean ringOfSufferingEffect = true;

    /**
     * Buy Credits
     */
    public int selectedCreditPackage, selectedPaymentMethod;

    /**
     *
     */
    @Expose public long rejuvenationPool = 0;

    /**
     * Title
     */
    @Expose public int titleId = -1;
    public Title title;
    @Expose public String customTitle;
    @Expose public boolean hasCustomTitle;

    /*
     * Summer Event
     */
    @Expose public long lastSacrifice;

    /**
     * Intro achievements fields
     */
    @Expose public boolean slayerBeginner = false;
    @Expose public boolean thetradingpostIntro = false;
    @Expose public boolean introtoenchantingIntro = false;
    @Expose public boolean gettinglearntIntro = false;
    @Expose public boolean imbuedknowledgeIntro = false;
    @Expose public boolean teleporterIntro = false;
    @Expose public boolean introtoPerks = false;
    @Expose public boolean theknightsguildIntro = false;
    @Expose public boolean loomingThreatIntro = false;

    @Expose public boolean arenanovicemedium = false;
    @Expose public boolean battlegroundnovicemedium = false;
    @Expose public boolean imbuedpowerMedium = false;
    @Expose public boolean enchanterMedium = false;
    @Expose public boolean gearingupMedium = false;
    @Expose public boolean naturalbornkillerMedium = false;
    @Expose public boolean youreanaturalatthisMedium = false;
    @Expose public boolean areyoumanenoughMedium = false;
    @Expose public boolean homeownerMedium = false;
    @Expose public boolean reigneventsMedium = false;
    @Expose public boolean guidebookMedium = false;
    @Expose public boolean thelavacapeMedium = false;
    @Expose public boolean cyclopskillerMedium = false;
    @Expose public boolean thebrothersMedium = false;
    @Expose public boolean moleymoleymoleyMedium = false;
    @Expose public boolean kingofthewildMedium = false;

    @Expose public boolean arenachampionHard = false;
    @Expose public boolean timetravelerHard = false;
    @Expose public boolean brimchestHard = false;
    @Expose public boolean theserpantkingHard = false;
    @Expose public boolean doingmypartHard = false;
    @Expose public boolean superiorkillerHard = false;
    @Expose public boolean treasurefinderHard = false;
    @Expose public boolean realchallengeHard = false;
    @Expose public boolean thelastmanHard = false;
    @Expose public boolean tournytimeHard = false;
    @Expose public boolean howmanyheadsHard = false;
    @Expose public boolean coldbloodedHard = false;
    @Expose public boolean tightropewalkerHard = false;
    @Expose public boolean onaconquestHard = false;
    @Expose public boolean dragonslayerHard = false;
    @Expose public boolean therevenantHard = false;
    @Expose public boolean keyholderHard = false;
    @Expose public boolean theeliteoftheeliteHard = false;
    @Expose public boolean etherealHard = false;
    @Expose public boolean sacrificialHard = false;


    @Expose public boolean chambernoviceElite = false;
    @Expose public boolean fortholderElite = false;
    @Expose public boolean hailhydraElite = false;
    @Expose public boolean likeabossElite = false;
    @Expose public boolean mastertreasurefinderElite = false;
    @Expose public boolean noassemblyrequiredElite = false;
    @Expose public boolean notjustafragmentElite = false;
    @Expose public boolean ratingElite = false;
    @Expose public boolean takemetothetopElite = false;
    @Expose public boolean thelavacapeiiElite = false;
    @Expose public boolean thevaultElite = false;
    @Expose public boolean tournykingElite = false;

    @Expose public boolean slayermasterMaster = false;
    @Expose public boolean youreonyourolmMaster = false;
    @Expose public boolean bigolflexMaster = false;
    @Expose public boolean gladiatorMaster = false;
    @Expose public boolean purpleplsMaster = false;
    @Expose public boolean catchmeifyoucanMaster = false;
    @Expose public boolean godblessedMaster = false;
    @Expose public boolean chickenkillermaster = false;

    @Expose public int teleportPortalUses = 0;
    @Expose public int presetsLoaded = 0;
    @Expose public int introAchievementsCompleted;
    @Expose public int mediumAchievementsCompleted;
    @Expose public int hardAchievementsCompleted;
    @Expose public int eliteAchievementsCompleted;
    @Expose public int masterAchievementsCompleted;

    /**
     * New con code
     */
    @Expose
    public House house = null;

    public Seat seat;

    /**
     * Boost scrolls
     */
    public TickDelay expBonus = new TickDelay();
    @Expose public int expBonusTimeLeft;
    public TickDelay petDropBonus = new TickDelay();
    @Expose public int petDropBonusTimeLeft;
    public TickDelay rareDropBonus = new TickDelay();
    @Expose public int rareDropBonusTimeLeft;
    public TickDelay pkpBonus = new TickDelay();
    @Expose public int pkpBonusTimeLeft;

    public TickDelay bloodmoneyBoost = new TickDelay();
    @Expose public int bloodmoneyBoostTimeLeft;
    public TickDelay revenantBoost = new TickDelay();
    @Expose public int revenantBoostTimeLeft;
    @Expose public int greatvaultBoost;
    public TickDelay wildernessImmunity = new TickDelay();
    @Expose public int wildernessImmunityTimeLeft;
    public TickDelay cluekeyBoost = new TickDelay();
    @Expose public int cluekeyboostTimeLeft;


    /**
     * Wilderness points
     */
    @Expose public int wildernessPoints;

    /**
     * Toggle for exp counter to show hit
     */
    @Expose public boolean showHitAsExperience = false;

    /**
     * Mystery box incentives
     */
    @Expose public boolean firstMysteryBoxReward = true;
    @Expose public int guaranteedMysteryBoxLoot = 1;

    /**
     * Equip timer to prevent animations
     */
    public TickDelay recentlyEquipped = new TickDelay();

    /**
     * Nurse special attack refill cooldown
     */
    public TickDelay nurseSpecialRefillCooldown = new TickDelay();

    /**
     * Used when a player has a bounty target to ensure he doesn't switch presets
     */
    public Preset lastPresetUsed;

    /**
     * Edgeville blacklisted users
     */
    @Expose public EdgevilleBlacklist[] edgevilleBlacklistedUsers = new EdgevilleBlacklist[EdgevilleBlacklist.ENTRIES.length];

    /**
     * Used to determine if a player is an official dice host or not (set when the player claims the dice bag)
     */
    @Expose public boolean diceHost = false;


    /**
     * Daily reset
     */
    @Expose public String lastLoginDate; // would have used a LocalDate object for this but it doesn't serialize properly with the @Expose annotation

    @Expose public DailyTask[] dailyTasks = new DailyTask[3];
    @Expose public DailyTaskMedium[] dailyTasksMedium = new DailyTaskMedium[3];
    @Expose public DailyTaskHard[] dailyTasksHard = new DailyTaskHard[3];
    @Expose public DailyTaskElite[] dailyTasksElite = new DailyTaskElite[3];
    @Expose public int[] dailyTaskProgress = new int[3];
    @Expose public int[] dailyTaskMediumProgress = new int[3];
    @Expose public int[] dailyTaskHardProgress = new int[3];
    @Expose public int[] dailyTaskEliteProgress = new int[3];
    @Expose public int easydailyTasksCompleted;
    @Expose public int mediumdailyTasksCompleted;
    @Expose public int harddailyTasksCompleted;
    @Expose public int elitedailyTasksCompleted;
    @Expose public int dailyTaskPoints;
    @Expose public long dailyTaskDate = 0;
    @Expose public int totalDailyDone = 0;
    @Expose public int totalDailyMediumDone = 0;
    @Expose public int totalDailyHardDone = 0;
    @Expose public int dailyTaskCompletePoints = 0;
    @Expose public int dailyEasyTask = -1;
    @Expose public int dailyMediumTask = -1;
    @Expose public int dailyHardTask = -1;
    @Expose public int pvmSelected;
    @Expose public int pvpSelected;

    /**
     * PvP weapon specials
     */
    public TickDelay vestasSpearSpecial = new TickDelay();
    public TickDelay morrigansAxeSpecial = new TickDelay();

    /**
     * Supply chest wilderness event
     */
    public boolean supplyChestRestricted = false;
    @Expose public boolean supplyChestWarning = true;

    /**
     * Blood money key wilderness event
     */
    public boolean bloodyKeyRestricted = false;
    @Expose public boolean bloodyKeyWarning = true;

    /**
     * Attribute used to hide free items on the PVP world
     */
    @Expose public boolean hideFreeItems = true;

    /**
     * Used for the dragonfire shield special attack
     */
    public boolean dragonfireShieldSpecial = false;
    public TickDelay dragonfireShieldCooldown = new TickDelay();

    @Expose public ArrayList<Item> unlockedIronmanItems = new ArrayList<>();

    /**
     * Pvp Rating Interface
     */
    @Expose public ArrayList<Player> recentActivityPlayerNames = new ArrayList<>();
    @Expose public ArrayList<Player> recentActivityWins = new ArrayList<>();
    @Expose public ArrayList<Player> recentActivityLosses = new ArrayList<>();


    /**
     * Ring of wealth attributes for features
     */
    @Expose public boolean ROWAutoCollectBloodMoney = false;

    @Expose public boolean ROWAutoCollectEther = false;

    @Expose public boolean ROWAutoCollectGold = false;

    public boolean insideWildernessAgilityCourse = false;

    public TickDelay presetDelay = new TickDelay();

    public StatType selectedSkillLampSkill;

    public StatType bigSelectedSkillLampSkill;

    /**
     * Risk protection toggle
     */
    @Expose public int riskProtectionTier = 10;
    @Expose public long riskedBloodMoney = 0;
    public TickDelay riskProtectionExpirationDelay = new TickDelay();

    /**
     * Magic skillcape attributes
     */
    @Expose public long mageSkillcapeSpecial = System.currentTimeMillis();
    @Expose public int magicSkillcapeUses = 0;

    @Expose public long dailytaskReset = System.currentTimeMillis();

    /**
     * PvM Instances
     */
    public PVMInstance currentInstance;

    public boolean claimedBox = true;
    public boolean easterEgg = false;

    /**
     * Idle timer
     */
    public int idleTicks;
    public boolean isIdle = false;

    /**
     * Christmas 2018
     */
    @Expose public boolean gotChristmasPresent = false;
    @Expose public boolean snowballPeltOption = false;
    @Expose public int snowballPoints = 0;
    public TickDelay snowballCooldown = new TickDelay();

    /**
     * Giveaway
     */
    @Expose public int giveawayId = -1;
    @Expose public int giveawayEntries = 0;

    public TickDelay specTeleportDelay = new TickDelay();

    @Expose public boolean bloodyMerchantTradeWarning = true;

    @Expose public boolean broadcastBossEvent = true;
    @Expose public boolean broadcastActiveVolcano = true;
    @Expose public boolean broadcastHotspot = true;
    @Expose public boolean broadcastBonusMinigameEvent = true;
    @Expose public boolean broadcastSupplyChest = true;
    @Expose public boolean broadcastBloodyMechant = true;
    @Expose public boolean broadcastAnnouncements = true;
    @Expose public boolean broadcastTournaments = true;
    @Expose public boolean broadcastHoldTheFort = true;
    @Expose public boolean broadcastPirateShip = true;
    @Expose public boolean broadcastCapturetheFlag = true;
    @Expose public boolean broadcastGlod = true;
    @Expose public boolean broadcastSeren = true;


    @Expose public boolean bootsOfLightnessTaken = false;
    @Expose public int demonKills = 0;

    /**
     * Nest boxes
     */
    @Expose public int nestBoxSeeds;
    @Expose public int nestBoxRings;

    public TickDelay edgevilleStallCooldown = new TickDelay();

    @Expose public double chaosAltarBoneChance = 50;

    /**
     * Appreciation points
     */
    @Expose public int appreciationTicks = 0;
    @Expose public int appreciationPoints = 0;

    @Expose public double energyUnits = 10000;


    @Expose public int refundedCredits = 0;


    public NPC botPreventionNPC;
    public boolean dismissBotPreventionNPC;
    public TickDelay botPreventionJailDelay = new TickDelay();
    public TickDelay botPreventionNpcShoutDelay = new TickDelay();

    @Expose public int claimedVotes;

    @Expose public int voteMysteryBoxReward;

    @Expose public int implingCaught = 0;

    @Expose public boolean startedEggHunt = false;

    @Expose public boolean preventSkippingCourse = false;

    // Skill cape dailies
    @Expose public long lastAgilityCapeBoost;

    @Expose public int timesKilledDonatorBoss;
    @Expose public long lastTimeKilledDonatorBoss;

    public boolean overloadBoostActive = false;
    public boolean prayerEnhanceBoostActive = false;
    public boolean divineAttackBoostActive = false;
    public boolean divineStrBoostActive = false;
    public boolean divineDefBoostActive = false;
    public boolean divineSuperCmbBoostActive = false;
    public boolean divineMagicBoostActive = false;
    public boolean divineRangingBoostActive = false;

    @Expose public boolean obtained50KCVorkathHead = false;

    @Expose public boolean obtained25KCSeren = false;

    public PestControlGame pestGame;
    @Expose public int pestPoints;
    @Expose public int pestNoviceWins;
    @Expose public int pestIntermediateWins;
    @Expose public int pestVeteranWins;
    public int pestActivityScore;
    public int selectedWidgetId;

    @Expose public boolean autoCollectEther = false;

    @Expose public boolean unlockedGreenSkin = true;
    @Expose public boolean unlockedBlueSkin = true;
    @Expose public boolean unlockedPurpleSkin = true;
    @Expose public boolean unlockedWhiteSkin = true;
    @Expose public boolean unlockedBlackSkin = true;

    public LastManStandingSession lmsSession;
    public LastManStandingQueue lmsQueue;
    public int lmsSessionKills;
    @Expose public int lmsKills;
    @Expose public int lmsWins;
    @Expose public int lmsGamesPlayed;
    @Expose public int lmsRank;

    public Tournament tournament;
    public TournamentFightPair tournamentFight;

    @Expose public int mysteriousStrangerVarp;
    public boolean rigging = false;

    @Expose public Position cannonPosition;
    @Expose public int cannonBallsLoaded;

    public int pkModeTutorialOp;

    @Expose public int bossPoints;

    @Expose public int totalBossKills;
    @Expose public int totalSkillingBossKills;

    @Expose public boolean bountyHunterOverlay = true;

    public PyramidPlunderGame pyramidPlunderGame;

    @Expose @Getter
    public TeleportInterface teleports;

    @Expose @Getter
    public EnchantArmorInfo itemlist;

    @Expose @Getter
    public UpgradeMachine upgradeMachine;

    @Expose public boolean cerberusMetamorphisis;
    @Expose public boolean infernalJadMetamorphisis;
    @Expose public boolean abyssalSireMetamorphisis;
    @Expose public boolean olmletMetamorphisis;

}