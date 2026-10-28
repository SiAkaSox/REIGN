package io.ruin.model.inter.achievementsInter;

import io.ruin.model.achievements.listeners.hard.HardAchieveInter;
import io.ruin.model.achievements.listeners.medium.MediumAchieveInter;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.actions.SlotAction;

import static io.ruin.model.achievements.listeners.easy.EasyAchieveInter.*;
import static io.ruin.model.achievements.listeners.easy.EasyAchieveInter.loadEasyAchievements;
import static io.ruin.model.achievements.listeners.elite.EliteAchieveInter.*;
import static io.ruin.model.achievements.listeners.elite.EliteAchieveInter.loadEliteAchievements;
import static io.ruin.model.achievements.listeners.hard.HardAchieveInter.*;
import static io.ruin.model.achievements.listeners.hard.HardAchieveInter.selectSacrificial;
import static io.ruin.model.achievements.listeners.master.MasterAchieveInter.*;
import static io.ruin.model.achievements.listeners.medium.MediumAchieveInter.*;
import static io.ruin.model.achievements.listeners.medium.MediumAchieveInter.selectKingoftheWild;

public class AchievementsInter {

    public static int ACHIEVEMENTS_TAB = 858;
    public static int EASY_INTER = 900;

    static {
        InterfaceHandler.register(ACHIEVEMENTS_TAB, h ->{
            h.actions[234] = (SlotAction) (player, slot)-> loadEasyAchievements(player);
            h.actions[299] = (SlotAction) (player, slot)-> loadMediumAchievements(player);
            h.actions[310] = (SlotAction) (player, slot)-> loadHardAchievements(player);
            h.actions[321] = (SlotAction) (player, slot)-> loadEliteAchievements(player);
            h.actions[332] = (SlotAction) (player, slot)-> loadMasterAchievements(player);
        });
        InterfaceHandler.register(EASY_INTER, h ->{
            h.actions[35] = (SlotAction) (p, slot)-> loadEasyAchievements(p);
            h.actions[36] = (SlotAction) (player, slot)-> MediumAchieveInter.loadMediumAchievements(player);
            h.actions[37] = (SlotAction) (player, slot)-> HardAchieveInter.loadHardAchievements(player);
            h.actions[38] = (SlotAction) (player, slot)-> loadEliteAchievements(player);
            h.actions[39] = (SlotAction) (player, slot)-> loadMasterAchievements(player);
            h.actions[41] = (SlotAction) (p, slot)-> selectSlayerBeginner(p);
            h.actions[42] = (SlotAction) (p, slot)-> selectTheTradingPost(p);
            h.actions[43] = (SlotAction) (p, slot)-> selectIntroToEnchanting(p);
            h.actions[167] = (SlotAction) (p, slot)-> selectGettingLearnt(p);
            h.actions[168] = (SlotAction) (p, slot)-> selectImbuedKnowledge(p);
            h.actions[169] = (SlotAction) (p, slot)-> selectTeleporter(p);
            h.actions[174] = (SlotAction) (p, slot)-> selectintrotoPerks(p);
            h.actions[175] = (SlotAction) (p, slot)-> selectWildExpansion(p);
            h.actions[176] = (SlotAction) (p, slot)-> selectLoomingThreat(p);
        });
        InterfaceHandler.register(MEDIUM_INTER, h ->{
            h.actions[35] = (SlotAction) (p, slot)-> loadEasyAchievements(p);
            h.actions[36] = (SlotAction) (player, slot)-> MediumAchieveInter.loadMediumAchievements(player);
            h.actions[37] = (SlotAction) (player, slot)-> HardAchieveInter.loadHardAchievements(player);
            h.actions[38] = (SlotAction) (player, slot)-> loadEliteAchievements(player);
            h.actions[39] = (SlotAction) (player, slot)-> loadMasterAchievements(player);
            h.actions[41] = (SlotAction) (p, slot)-> selectArenaNovice(p);
            h.actions[42] = (SlotAction) (p, slot)-> selectBattlegroundNovice(p);
            h.actions[43] = (SlotAction) (p, slot)-> selectImbuedPower(p);
            h.actions[167] = (SlotAction) (p, slot)-> selectEnchanter(p);
            h.actions[168] = (SlotAction) (p, slot)-> selectGearingUp(p);
            h.actions[169] = (SlotAction) (p, slot)-> selectNaturalBornKiller(p);
            h.actions[174] = (SlotAction) (p, slot)-> selectYoureANaturalAtThis(p);
            h.actions[175] = (SlotAction) (p, slot)-> selectAreYouManEnough(p);
            h.actions[176] = (SlotAction) (p, slot)-> selectHomeOwner(p);
            h.actions[181] = (SlotAction) (p, slot)-> selectReignEvents(p);
            h.actions[182] = (SlotAction) (p, slot)-> selectGuideBook(p);
            h.actions[183] = (SlotAction) (p, slot)-> selectTheLavaCape(p);
            h.actions[188] = (SlotAction) (p, slot)-> selectCyclopsKiller(p);
            h.actions[189] = (SlotAction) (p, slot)-> selectTheBrothers(p);
            h.actions[190] = (SlotAction) (p, slot)-> selectMoleyMoleyMoley(p);
            h.actions[195] = (SlotAction) (p, slot)-> selectKingoftheWild(p);
        });
        InterfaceHandler.register(HARD_INTER, h ->{
            h.actions[35] = (SlotAction) (p, slot)-> loadEasyAchievements(p);
            h.actions[36] = (SlotAction) (player, slot)-> MediumAchieveInter.loadMediumAchievements(player);
            h.actions[37] = (SlotAction) (player, slot)-> HardAchieveInter.loadHardAchievements(player);
            h.actions[38] = (SlotAction) (player, slot)-> loadEliteAchievements(player);
            h.actions[39] = (SlotAction) (player, slot)-> loadMasterAchievements(player);
            h.actions[41] = (SlotAction) (p, slot)-> selectArenaChampion(p);
            h.actions[42] = (SlotAction) (p, slot)-> selectTimeTraveler(p);
            h.actions[43] = (SlotAction) (p, slot)-> selectBrimChest(p);
            h.actions[167] = (SlotAction) (p, slot)-> selectTheSerpantKing(p);
            h.actions[168] = (SlotAction) (p, slot)-> selectDoingMyPart(p);
            h.actions[169] = (SlotAction) (p, slot)-> selectSuperiorKiller(p);
            h.actions[174] = (SlotAction) (p, slot)-> selectTreasureFinder(p);
            h.actions[175] = (SlotAction) (p, slot)-> selectARealChallenge(p);
            h.actions[176] = (SlotAction) (p, slot)-> selectTheLastMan(p);
            h.actions[181] = (SlotAction) (p, slot)-> selectTournyTime(p);
            h.actions[182] = (SlotAction) (p, slot)-> selectHowManyHeads(p);
            h.actions[183] = (SlotAction) (p, slot)-> selectColdBlooded(p);
            h.actions[188] = (SlotAction) (p, slot)-> selectTightropeWalker(p);
            h.actions[189] = (SlotAction) (p, slot)-> selectOnAConquest(p);
            h.actions[190] = (SlotAction) (p, slot)-> selectDragonSlayer(p);
            h.actions[195] = (SlotAction) (p, slot)-> selectTheRevenant(p);
            h.actions[196] = (SlotAction) (p, slot)-> selectKeyHolder(p);
            h.actions[197] = (SlotAction) (p, slot)-> selectTheEliteoftheElite(p);
            h.actions[358] = (SlotAction) (p, slot)-> selectTheBloodDiamond(p);
            h.actions[359] = (SlotAction) (p, slot)-> selectSacrificial(p);
        });
        InterfaceHandler.register(ELITE_INTER, h ->{
            h.actions[35] = (SlotAction) (p, slot)-> loadEasyAchievements(p);
            h.actions[36] = (SlotAction) (player, slot)-> MediumAchieveInter.loadMediumAchievements(player);
            h.actions[37] = (SlotAction) (player, slot)-> HardAchieveInter.loadHardAchievements(player);
            h.actions[38] = (SlotAction) (player, slot)-> loadEliteAchievements(player);
            h.actions[39] = (SlotAction) (player, slot)-> loadMasterAchievements(player);
            h.actions[41] = (SlotAction) (p, slot)-> selectRating(p);
            h.actions[42] = (SlotAction) (p, slot)-> selectTheLavaCapeII(p);
            h.actions[43] = (SlotAction) (p, slot)-> selectChamberNovice(p);
            h.actions[167] = (SlotAction) (p, slot)-> selectHailHydra(p);
            h.actions[168] = (SlotAction) (p, slot)-> selectMasterTreasureFinder(p);
            h.actions[169] = (SlotAction) (p, slot)-> selectTournyKing(p);
            h.actions[174] = (SlotAction) (p, slot)-> selectTheVault(p);
            h.actions[175] = (SlotAction) (p, slot)-> selectNoAssemblyRequired(p);
            h.actions[176] = (SlotAction) (p, slot)-> selectNotJustAFragment(p);
            h.actions[181] = (SlotAction) (p, slot)-> selectFortHolder(p);
            h.actions[182] = (SlotAction) (p, slot)-> selectLikeABoss(p);
            h.actions[183] = (SlotAction) (p, slot)-> selectTakeMeToTheTop(p);
        });
        InterfaceHandler.register(MASTER_INTER, h ->{
            h.actions[35] = (SlotAction) (p, slot)-> loadEasyAchievements(p);
            h.actions[36] = (SlotAction) (player, slot)-> MediumAchieveInter.loadMediumAchievements(player);
            h.actions[37] = (SlotAction) (player, slot)-> HardAchieveInter.loadHardAchievements(player);
            h.actions[38] = (SlotAction) (player, slot)-> loadEliteAchievements(player);
            h.actions[39] = (SlotAction) (player, slot)-> loadMasterAchievements(player);
            h.actions[41] = (SlotAction) (p, slot)-> selectSlayerMaster(p);
            h.actions[42] = (SlotAction) (p, slot)-> selectYoureOnYourOlm(p);
            h.actions[43] = (SlotAction) (p, slot)-> selectBigOlFlex(p);
            h.actions[167] = (SlotAction) (p, slot)-> selectGladiator(p);
            h.actions[168] = (SlotAction) (p, slot)-> selectPurplePls(p);
            h.actions[169] = (SlotAction) (p, slot)-> selectCatchMeIfYouCan(p);
            h.actions[174] = (SlotAction) (p, slot)-> selectGodblessed(p);
            h.actions[175] = (SlotAction) (p, slot)-> selectChickenKiller(p);
        });
    }

}