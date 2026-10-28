package io.ruin.model.achievements;

import io.ruin.cache.Color;
import io.ruin.model.achievements.listeners.elite.*;
import io.ruin.model.achievements.listeners.hard.*;
import io.ruin.model.achievements.listeners.master.*;
import io.ruin.model.achievements.listeners.medium.ImbuedPower;
import io.ruin.model.achievements.listeners.oldhard.*;
import io.ruin.model.achievements.listeners.easy.*;
import io.ruin.model.achievements.listeners.oldhard.ExpertRunecrafter;
import io.ruin.model.achievements.listeners.medium.*;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.inter.journal.JournalEntry;

public enum Achievement {

    /**
     * Easy
     */
    THE_TRADING_POST(new TheTradingPost(), AchievementCategory.Easy),
    SLAYER_BEGINNER(new SlayerBeginner(), AchievementCategory.Easy),
    INTRO_TO_ENCHANTING(new IntroToEnchanting(), AchievementCategory.Easy),
    GETTING_LEARNT(new GettingLearnt(), AchievementCategory.Easy),
    IMBUED_KNOWLEDGE(new ImbuedKnowledge(), AchievementCategory.Easy),
    TELEPORTER(new Teleporter(), AchievementCategory.Easy),
    INTRO_TO_PERKS(new IntroToPerks(), AchievementCategory.Easy),
    THE_WILDERNESS_EXPANSION(new TheWildernessExpansion(), AchievementCategory.Easy),
    LOOMING_THREAT(new LoomingThreat(), AchievementCategory.Easy),
    //PRESETS(new PresetsIntro(), AchievementCategory.Introductory),

    /**
     * Medium
     */
    ARENA_NOVICE(new ArenaNovice(), AchievementCategory.Medium),
    BATTLEGROUND_NOVICE(new BattlegroundNovice(), AchievementCategory.Medium),
    IMBUED_POWER(new ImbuedPower(), AchievementCategory.Medium),
    ENCHANTER(new Enchanter(), AchievementCategory.Medium),
    GEARING_UP(new GearingUp(), AchievementCategory.Medium),
    NATURAL_BORN_KILLER(new NaturalBornKiller(), AchievementCategory.Medium),
    YOURE_A_NATURAL_AT_THIS(new YoureANaturalAtThis(), AchievementCategory.Medium),
    ARE_YOU_MAN_ENOUGH(new AreYouManEnough(), AchievementCategory.Medium),
    HOME_OWNER(new HomeOwner(), AchievementCategory.Medium),
    WORLD_EVENTS(new ReignEvents(), AchievementCategory.Medium),
    GUIDE_BOOK(new GuideBook(), AchievementCategory.Medium),
    THE_LAVA_CAPE(new TheLavaCape(), AchievementCategory.Medium),
    CYCLOPS_KILLER(new CyclopsKiller(), AchievementCategory.Medium),
    THE_BROTHERS(new TheBrothers(), AchievementCategory.Medium),
    MOLEY_MOLEY_MOLEY(new MoleyMoleyMoley(), AchievementCategory.Medium),
    KING_OF_THE_WILD(new KingOfTheWild(), AchievementCategory.Medium),

    /**
     * Hard
     */
    ARENA_CHAMPION(new ArenaChampion(), AchievementCategory.Hard),
    TIME_TRAVELER(new TimeTraveler(), AchievementCategory.Hard),
    BRIM_CHEST(new BrimChest(), AchievementCategory.Hard),
    THE_SERPANT_KING(new TheSerpantKing(), AchievementCategory.Hard),
    DOING_MY_PART(new DoingMyPart(), AchievementCategory.Hard),
    SUPERIOR_KILLER(new SuperiorKiller(), AchievementCategory.Hard),
    TREASURE_FINDER(new TreasureFinder(), AchievementCategory.Hard),
    A_REAL_CHALLENGE(new ARealChallenge(), AchievementCategory.Hard),
    THE_LAST_MAN(new TheLastMan(), AchievementCategory.Hard),
    TOURNY_TIME(new TournyTime(), AchievementCategory.Hard),

    HOW_MANY_HEADS(new HowManyHeads(), AchievementCategory.Hard),
    COLD_BLOODED(new ColdBlooded(), AchievementCategory.Hard),
    TIGHTROPE_WALKER(new TightropeWalker(), AchievementCategory.Hard),
    ON_A_CONQUEST(new OnAConquest(), AchievementCategory.Hard),
    DRAGON_SLAYER(new DragonSlayer(), AchievementCategory.Hard),
    THE_REVENANT(new TheRevenant(), AchievementCategory.Hard),
    KEY_HOLDER(new KeyHolder(), AchievementCategory.Hard),
    THE_ELITE_OF_THE_ELITE(new TheEliteoftheElite(), AchievementCategory.Hard),
    ETHEREAL(new Ethereal(), AchievementCategory.Hard),
    SACRIFICIAL(new Sacrificial(), AchievementCategory.Hard),

    /**
     * Elite
     */
    RATING(new Rating(), AchievementCategory.Elite),
    THE_LAVA_CAPE_II(new TheLavaCapeII(), AchievementCategory.Elite),
    CHAMBER_NOVICE(new ChamberNovice(), AchievementCategory.Elite),
    HAIL_HYDRA(new HailHydra(), AchievementCategory.Elite),
    MASTER_TREASURE_FINDER(new MasterTreasureFinder(), AchievementCategory.Elite),
    TOURNY_KING(new TournyKing(), AchievementCategory.Elite),
    THE_VAULT(new TheVault(), AchievementCategory.Elite),
    NO_ASSEMBLY_REQUIRED(new NoAssemblyRequired(), AchievementCategory.Elite),
    NOT_JUST_A_FRAGMENT(new NotJustAFragment(), AchievementCategory.Elite),
    FORT_HOLDER(new FortHolder(), AchievementCategory.Elite),
    LIKE_A_BOSS(new LikeABoss(), AchievementCategory.Elite),
    TAKE_ME_TO_THE_TOP(new TakeMeToTheTop(), AchievementCategory.Elite),

    /**
     * Master
     */
    SLAYER_MASTER(new SlayerMasterAchieve(), AchievementCategory.Master),
    YOURE_ON_YOUR_OLM(new YoureOnYourOlm(), AchievementCategory.Master),
    BIG_OL_FLEX(new BigOlFlex(), AchievementCategory.Master),
    GLADIATOR(new Gladiator(), AchievementCategory.Master),
    PURPLE_PLS(new PurplePls(), AchievementCategory.Master),
    CATCH_ME_IF_YOU_CAN(new CatchMeIfYouCan(), AchievementCategory.Master),
    GODBLESSED(new Godblessed(), AchievementCategory.Master),
    CHICKEN_KILLER(new ChickenKiller(), AchievementCategory.Master),


    /**
     * Unused
     */
    DOWN_IN_THE_DIRT(new DownInTheDirt(), AchievementCategory.Hard),
    ESSENCE_EXTRACTOR(new EssenceExtractor(), AchievementCategory.Hard),
    GOLDEN_TOUCH(new GoldenTouch(), AchievementCategory.Hard),
    NATURES_TOUCH(new NaturesTouch(), AchievementCategory.Hard),
    ABYSSAL_DISTURBANCE(new AbyssalDisturbance(), AchievementCategory.Hard),
    PRACTICE_MAKES_PERFECT(new PracticeMakesPerfect(), AchievementCategory.Hard),
    QUICK_HANDS(new QuickHands(), AchievementCategory.Hard),
    MY_ARMS_PATCH(new MyArmsPatch(), AchievementCategory.Hard),
    WELCOME_TO_THE_JUNGLE(new WelcomeToTheJungle(), AchievementCategory.Hard),
    DEMON_SLAYER(new DemonSlayer(), AchievementCategory.Hard),
    INTO_THE_ABYSS(new IntoTheAbyss(), AchievementCategory.Medium),
    MORYTANIA_FARMING(new MorytaniaFarming(), AchievementCategory.Medium),
    LIGHTNESS(new Lightness(), AchievementCategory.Medium),
    IMPLING_HUNTER(new ImplingHunter(), AchievementCategory.Medium),
    EXPERT_RUNECRAFTER(new ExpertRunecrafter(), AchievementCategory.Master);

    private final AchievementListener listener;
    private final AchievementCategory category;

    private JournalEntry entry;

    Achievement(AchievementListener listener, AchievementCategory category) {
        this.listener = listener;
        this.category = category;
    }

    public void update(Player player) {
        if (entry == null) {
            //never displayed on this world
            return;
        }
        AchievementStage oldStage = player.achievementStages[ordinal()];
        entry.send(player);
        AchievementStage newStage = player.achievementStages[ordinal()];
        if (newStage != oldStage) {
            if (newStage == AchievementStage.STARTED) {
                player.sendMessage("<col=000080>You have started the achievement: <col=800000>" + getListener().name());
                getListener().started(player);
            } else if (newStage == AchievementStage.FINISHED) {
                player.sendMessage("<col=000080>You have completed the achievement: <col=800000>" + getListener().name());
                getListener().finished(player);
           }
        }
    }

    public JournalEntry toEntry() {
        return entry = new JournalEntry() {
            @Override
            public void send(Player player) {
                AchievementStage stage = player.achievementStages[ordinal()] = getListener().stage(player);
                if (player.journal != Journal.ACHIEVEMENTS) {
                    return;
                }
                if (stage == AchievementStage.FINISHED)
                    send(player, getListener().name(), Color.GREEN);
                else if (stage == AchievementStage.STARTED)
                    send(player, getListener().name(), Color.YELLOW);
                else
                    send(player, getListener().name(), Color.RED);
            }

            @Override
            public void select(Player player) {
                player.sendScroll("<col=800000>" + getListener().name(), getListener().lines(player, isFinished(player)));
            }
        };
    }

    public boolean isStarted(Player player) {
        return player.achievementStages[ordinal()] != AchievementStage.NOT_STARTED;
    }

    public boolean isFinished(Player player) {
        return player.achievementStages[ordinal()] == AchievementStage.FINISHED;
    }

    /**
     * Misc
     */

    public static String slashIf(String string, boolean slash) {
        return slash ? ("<str>" + string + "</str>") : string;
    }

    public AchievementListener getListener() {
        return listener;
    }

    public AchievementCategory getCategory() {
        return category;
    }

    public static AchievementStage counterStage(int current, int start, int finish) {
        if (current >= finish)
            return AchievementStage.FINISHED;
        else if (current <= start)
            return AchievementStage.NOT_STARTED;
        return AchievementStage.STARTED;
    }

    static {
            for (Achievement achievement : values()) {
                if (achievement.listener != null)
                    achievement.listener.init();
            }
            LoginListener.register(player -> {
                for (Achievement achievement : values()) {
                    player.achievementStages[achievement.ordinal()] = achievement.getListener().stage(player); // forces achievements to have the correct state on login
                }
            });
    }
}