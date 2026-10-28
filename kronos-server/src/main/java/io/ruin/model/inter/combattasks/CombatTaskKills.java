package io.ruin.model.inter.combattasks;


import io.ruin.model.achievements.listeners.elite.HailHydra;
import io.ruin.model.achievements.listeners.hard.*;
import io.ruin.model.achievements.listeners.master.ChickenKiller;
import io.ruin.model.achievements.listeners.medium.KingOfTheWild;
import io.ruin.model.achievements.listeners.medium.MoleyMoleyMoley;
import io.ruin.model.activities.bosses.GiantMole;
import io.ruin.model.activities.bosses.hydra.AlchemicalHydra;
import io.ruin.model.activities.godwars.combat.impl.zamorak.KrilTsutsaroth;
import io.ruin.model.activities.pvminstances.InstanceType;
import io.ruin.model.activities.raids.xeric.chamber.combat.LargeMuttadile;
import io.ruin.model.activities.raids.xeric.chamber.combat.VasaNistirio;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.player.Player;
import io.ruin.model.skills.magic.spells.modern.CrumbleUndead;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class CombatTaskKills {

    public static int[] aberrantSpectre = {2, 4, 5};

    public static void kills(Player p, int npcId) {
        switch (npcId) {
            case 8615:
            case 8616:
            case 8617:
            case 8618:
            case 8619:
            case 8620:
            case 8621:
            case 8622:
                p.hailhydraCount++;
                if (p.hailhydraCount == 25) {
                    HailHydra.complete(p);
                }
                if (p.alchemicalVeteran < 75) {
                    p.alchemicalVeteran++;
                }
                if (p.alchemicalVeteran == 75) {
                    CombatTaskList.ALCHEMICAL_VETERAN.isComplete(75);
                    p.combatTaskEliteCompleted++;
                    p.alchemicalVeteran++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Alchemical Veteran (elite)!");
                }
//                if (p.alchemicalVeteran >= 75 && p.workingOvertime < 15 && AlchemicalHydra.workingOvertimeCount == 1) {
//                    p.workingOvertime++;
//                }
                if (p.workingOvertime == 15) {
                    CombatTaskList.WORKING_OVERTIME.isComplete(15);
                    p.combatTaskMasterCompleted++;
                    p.workingOvertime++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Working Overtime (master)!");
                }
                if (p.alchemicalBestTime < 2 * 100 && p.alchemicalspeedChaser == 0) {
                    p.alchemicalspeedChaser++;
                    CombatTaskList.ALCHEMICAL_SPEED_CHASER.isComplete(1);
                    p.combatTaskMasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Alchemical Speed-Chaser (master)!");
                }
                if (p.alchemicalBestTime < 1.2 * 100 && p.alchemicalspeedRunner == 0) {
                    p.alchemicalspeedRunner++;
                    CombatTaskList.ALCHEMICAL_SPEED_RUNNER.isComplete(1);
                    p.combatTaskGrandmasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Alchemical Speed-Runner (grandmaster)!");
                }
                if (p.alcleanicalHydra == 0 && p.hitsUpdate.curHp == p.hitsUpdate.maxHp) {
                    p.alcleanicalHydra++;
                    CombatTaskList.ALCLEANICAL_HYDRA.isComplete(1);
                    p.combatTaskMasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Alcleanical Hydra (master)!");
                }
                if (p.lightningLure == 0 && !AlchemicalHydra.shockHit) {
                    p.lightningLure++;
                    CombatTaskList.LIGHTNING_LURE.isComplete(1);
                    p.combatTaskMasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Lightning Lure (master)!");
                }
                if (p.unrequiredAntipoisons == 0 && !AlchemicalHydra.acidHit) {
                    p.unrequiredAntipoisons++;
                    CombatTaskList.UNREQUIRED_ANTIPOISONS.isComplete(1);
                    p.combatTaskMasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Unrequired Antipoisons (master)!");
                }
                if (p.alchemicalVeteran >= 75 && p.alchemicalMaster < 150) {
                    p.alchemicalMaster++;
                }
                if (p.alchemicalMaster == 150) {
                    CombatTaskList.ALCHEMICAL_MASTER.isComplete(1);
                    p.combatTaskMasterCompleted++;
                    p.alchemicalMaster++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Alchemical Master (master)!");
                }
                if (p.noPressure == 0) {
                    if (p.hitsUpdate.curHp <= 10 &&
                            p.getEquipment().contains(4718)) {
                        p.noPressure++;
                        CombatTaskList.NO_PRESSURE.isComplete(1);
                        p.combatTaskGrandmasterCompleted++;
                        p.totalTasksCompleted++;
                        p.sendMessage("You have completed a combat task: No Pressure (grandmaster)!");
                    }
                }
                break;
            case 9293:
                if (p.reflectingonthisEncounter == 0) {
                    p.reflectingonthisEncounter++;
                    CombatTaskList.REFLECTING_ON_THIS_ENCOUNTER.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Reflecting on This Encounter (elite)!");
                }
                break;
            case 2266:
                if (p.dagannothprimeChampion < 10) {
                    p.dagannothprimeChampion++;
                }
                if (p.dagannothprimeChampion == 10) {
                    CombatTaskList.DAGANNOTH_PRIME_CHAMPION.isComplete(10);
                    p.combatTaskMediumCompleted++;
                    p.dagannothprimeChampion++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Dagannoth Prime Champion (medium)!");
                }
                if (p.dagannothprimeChampion >= 10 && p.dagannothprimeAdept < 25) {
                    p.dagannothprimeAdept++;
                }
                if (p.dagannothprimeAdept == 25) {
                    CombatTaskList.DAGANNOTH_PRIME_ADEPT.isComplete(10);
                    p.combatTaskHardCompleted++;
                    p.dagannothprimeAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Dagannoth Prime Adept (hard)!");
                }
                if (p.deathtotheseerKing == 0 && !p.localNpcs().isEmpty()) {
                    p.deathtotheseerKing++;
                    CombatTaskList.DEAD_TO_THE_SEER_KING.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Death to the Seer King (elite)!");
                }
                break;
            case 2265:
                if (p.dagannothsupremeChampion < 10) {
                    p.dagannothsupremeChampion++;
                }
                if (p.dagannothsupremeChampion == 10) {
                    CombatTaskList.DAGANNOTH_SUPREME_CHAMPION.isComplete(10);
                    p.combatTaskMediumCompleted++;
                    p.dagannothsupremeChampion++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Dagannoth Supreme Champion (medium)!");
                }
                if (p.dagannothsupremeChampion >= 10 && p.dagannothsupremeAdept < 25) {
                    p.dagannothsupremeAdept++;
                }
                if (p.dagannothsupremeAdept == 25) {
                    CombatTaskList.DAGANNOTH_SUPREME_ADEPT.isComplete(25);
                    p.combatTaskHardCompleted++;
                    p.dagannothsupremeAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Dagannoth Supreme Adept (hard)!");
                }
                if (p.deathtothearcherKing == 0 && !p.localNpcs().isEmpty()) {
                    p.deathtotheseerKing++;
                    CombatTaskList.DEAD_TO_THE_ARCHER_KING.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Death to the Archer King (elite)!");
                }
                break;
            case 2267:
                if (p.dagannothrexChampion < 10) {
                    p.dagannothrexChampion++;
                }
                if (p.dagannothrexChampion == 10) {
                    CombatTaskList.DAGANNOTH_REX_CHAMPION.isComplete(10);
                    p.combatTaskMediumCompleted++;
                    p.dagannothrexChampion++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Dagannoth Rex Champion (medium)!");
                }
                if (p.dagannothrexChampion >= 10 && p.dagannothrexAdept < 25) {
                    p.dagannothrexAdept++;
                }
                if (p.dagannothrexAdept == 25) {
                    CombatTaskList.DAGANNOTH_REX_ADEPT.isComplete(25);
                    p.combatTaskHardCompleted++;
                    p.dagannothrexAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Dagannoth Rex Adept (hard)!");
                }
                if (p.getCombat().getTarget().isFrozen() || p.getCombat().getTarget().isStunned()) {
                    p.afrozenKing++;
                    CombatTaskList.A_FROZEN_KING.isComplete(1);
                    p.combatTaskMediumCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: A Frozen King (medium)!");
                }
                if (p.deathtothewarriorKing == 0 && !p.localNpcs().isEmpty()) {
                    p.deathtotheseerKing++;
                    CombatTaskList.DEAD_TO_THE_WARRIOR_KING.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Death to the Warrior King (elite)!");
                }
                break;
            case 2042:
            case 2043:
            case 2044:
                TheSerpantKing.complete(p);
                if (p.zulrahAdept < 25) {
                    p.zulrahAdept++;
                }
                if (p.zulrahAdept == 25) {
                    CombatTaskList.ZULRAH_ADEPT.isComplete(25);
                    p.combatTaskHardCompleted++;
                    p.zulrahAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Zulrah Adept (hard)!");
                }
                if (p.zulrahAdept >= 25 && p.zulrahVeteran < 75) {
                    p.zulrahVeteran++;
                }
                if (p.zulrahVeteran == 75) {
                    CombatTaskList.ZULRAH_VETERAN.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.zulrahVeteran++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Zulrah Veteran (elite)!");
                }
                if (p.zulrahspeedChaser == 0  && p.zulrahBestTime < 1 && p.slayerTaskName == null) {
                    p.zulrahspeedChaser++;
                    CombatTaskList.ZULRAH_SPEED_CHASER.isComplete(1);
                    p.combatTaskMasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Zulrah Speed-Chaser (master)!");
                }
                if (p.zulrahspeedChaser == 1 && p.zulrahspeedRunner == 0 && p.zulrahBestTime < 1 && p.slayerTaskName == null) {
                    p.zulrahspeedRunner++;
                    CombatTaskList.ZULRAH_SPEED_RUNNER.isComplete(1);
                    p.combatTaskGrandmasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Zulrah Speed-Runner (grandmaster)!");
                }
                if (p.perfectZulrah == 0 && p.hitsUpdate.curHp == p.hitsUpdate.maxHp) {
                    p.perfectZulrah++;
                    CombatTaskList.PERFECT_ZULRAH.isComplete(1);
                    p.combatTaskMasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Perfect Zulrah (master)!");
                }
                if (p.zulrahVeteran == 75 && p.zulrahMaster < 150) {
                    p.zulrahMaster++;
                }
                if (p.zulrahMaster == 150) {
                    CombatTaskList.ZULRAH_MASTER.isComplete(1);
                    p.combatTaskMasterCompleted++;
                    p.zulrahMaster++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Zulrah Master (master)!");
                }
                break;
            case 5862:
                HowManyHeads.complete(p);
                if (p.antibiteMechanics >= 1) {
                    CombatTaskList.ANTI_BITE_MECHANICS.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Anti-Bite Mechanics (elite)!");
                }
                if (p.unrequiredAntifire >= 1) {
                    CombatTaskList.UNREQUIRED_ANTIFIRE.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Unrequired Antifire (elite)!");
                }
                if (p.ghostBuster == 0) {
                    p.ghostBuster++;
                    CombatTaskList.GHOST_BUSTER.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Ghost Buster (elite)!");
                }
                if (p.cerberusVeteran < 75) {
                    p.cerberusVeteran++;
                }
                if (p.cerberusVeteran == 75) {
                    CombatTaskList.CERBERUS_VETERAN.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.cerberusVeteran++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Cerberus Veteran (elite)!");
                }
//                if (p.arooonoMore == 0 && !Cerberus.soulsSpawned == false) {
//                    p.arooonoMore++;
//                    CombatTaskList.AROOO_NO_MORE.isComplete(1);
//                    p.combatTaskMasterCompleted++;
//                    p.totalTasksCompleted++;
//                    p.sendMessage("You have completed a combat task: Arooo No More (master)!");
//                }
                if (p.cerberusVeteran >= 75 && p.cerberusMaster < 150) {
                    p.cerberusMaster++;
                }
                if (p.cerberusMaster == 150) {
                    CombatTaskList.CERBERUS_MASTER.isComplete(150);
                    p.combatTaskMasterCompleted++;
                    p.cerberusMaster++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Cerberus Master (master)!");
                }
                break;
            case 7540:
            case 7541:
            case 7542:
            case 7545:
                /*Tekton.walktoanvilcount = 0;
                if (p.blindSpot == 0 && p.hitsUpdate.curHp == p.hitsUpdate.maxHp) {
                    p.blindSpot++;
                    CombatTaskList.BLIND_SPOT.isComplete(150);
                    p.combatTaskMasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Blind Spot (master)!");
                }
                if (p.anvilnoMore == 0 && Tekton.walktoanvilcount < 2) {
                    p.anvilnoMore++;
                    CombatTaskList.ANVIL_NO_MORE.isComplete(1);
                    p.combatTaskMasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Anvil No More (master)!");
                }*/
                break;
            case 7565:
            case 7566:
            case 7567:
                if (p.stopdropandRoll == 0 && VasaNistirio.teleportattackcount < 2) {
                    p.stopdropandRoll++;
                    CombatTaskList.STOP_DROP_AND_ROLL.isComplete(1);
                    p.combatTaskMasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Stop Drop and Roll (master)!");
                }
                break;
            case 7585:
                if (p.killitwithFire == 0 && p.getCombat().getAttackStyle().isMagic()) {
                    p.killitwithFire++;
                    CombatTaskList.KILL_IT_WITH_FIRE.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Kill It With Fire (elite)!");
                }
                if (p.blizzardDodger == 0 && !p.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES)) {
                    p.blizzardDodger++;
                    CombatTaskList.BLIZZARD_DODGER.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Blizzard Dodger (elite)!");
                }
                if (p.cryonoMore == 0 && p.hitsUpdate.curHp == p.hitsUpdate.maxHp) {
                    p.cryonoMore++;
                    CombatTaskList.CRYO_NO_MORE.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Cryo No More (elite)!");
                }
                break;
            case 8061:
            case 8060:
                DragonSlayer.complete(p);
                if (p.stickemwiththepointyEnd == 0 && p.getCombat().getAttackStyle().isMelee()) {
                    p.stickemwiththepointyEnd++;
                    CombatTaskList.STICK_EM_WITH_THE_POINTY_END.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Stick 'em With the Pointy End (elite)!");
                }
                if (p.vorkathVeteran < 50) {
                    p.vorkathVeteran++;
                }
                if (p.vorkathVeteran == 50) {
                    CombatTaskList.VORKATH_VETERAN.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.vorkathVeteran++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Vorkath Veteran (elite)!");
                }
                if (p.extendedEncounter < 10 && p.vorkathVeteran >= 50) {
                    p.extendedEncounter++;
                }
                if (p.extendedEncounter == 10) {
                    CombatTaskList.EXTENDED_ENCOUNTER.isComplete(10);
                    p.combatTaskMasterCompleted++;
                    p.extendedEncounter++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Extended Encounter (master)!");
                }
                if (p.vorkathspeedChaser == 0 && p.vorkathBestTime < 2 * 100) {
                    p.vorkathspeedChaser++;
                    CombatTaskList.VORKATH_SPEED_CHASER.isComplete(10);
                    p.combatTaskMasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Vorkath Speed-Chaser (master)!");
                }
                if (p.vorkathVeteran >= 50 && p.vorkathMaster < 100) {
                    p.vorkathMaster++;
                }
                if (p.vorkathMaster == 100) {
                    CombatTaskList.VORKATH_MASTER.isComplete(100);
                    p.combatTaskMasterCompleted++;
                    p.vorkathMaster++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Vorkath Master (master)!");
                }
                if (p.vorkathMaster >= 100 && p.vorkathspeedChaser == 1 && p.vorkathspeedRunner == 0 &&
                        p.vorkathBestTime < 1) {
                    p.vorkathspeedRunner++;
                    CombatTaskList.VORKATH_SPEED_RUNNER.isComplete(1);
                    p.combatTaskGrandmasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Vorkath Speed-Runner (grandmaster)!");
                }
                if (p.vorkathMaster >= 100 && p.thefremennikWay == 0 && p.getEquipment().get(3) == null) {
                    p.thefremennikWay++;
                    CombatTaskList.THE_FREMENNIK_WAY.isComplete(1);
                    p.combatTaskGrandmasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: The Fremennik Way (grandmaster)!");
                }
                Stat prayer = p.getStats().get(StatType.Prayer);
                int currentLevel = prayer.currentLevel;
                if (p.vorkathMaster >= 100 && p.faithlessEncounter == 0 && currentLevel == p.getStats().get(7).fixedLevel) {
                    p.faithlessEncounter++;
                    CombatTaskList.FAITHLESS_ENCOUNTER.isComplete(1);
                    p.combatTaskGrandmasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Faithless Encounter (grandmaster)!");
                }
                break;
            case 8062:
            case 8063:
                if (!p.getCombat().queuedSpell.equals(CrumbleUndead.INSTANCE)) {
                    p.zombieDestroyer++;
                    CombatTaskList.ZOMBIE_DESTROYER.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Zombie Destroyer (elite)!");
                }
                break;
            case 7881:
            case 7931:
            case 7932:
            case 7933:
            case 7934:
            case 7935:
            case 7936:
            case 7937:
            case 7938:
            case 7939:
            case 7940:
                p.therevenantCount++;
                if (p.therevenantCount == 25) {
                    TheRevenant.complete(p);
                }
                break;
            case 7388:
            case 7389:
            case 7390:
            case 7391:
            case 7392:
            case 7393:
            case 7394:
            case 7395:
            case 7396:
            case 7397:
            case 7398:
            case 7399:
            case 7400:
            case 7401:
            case 7402:
            case 7403:
            case 7404:
            case 7405:
            case 7406:
            case 7407:
            case 7408:
            case 7409:
            case 7410:
            case 7411:
                SuperiorKiller.complete(p);
                break;
            case 2://Aberrant Spectre
            case 4:
            case 5:
                if (p.noxiousFoe == 0) {
                    p.noxiousFoe++;
                    CombatTaskList.NOXIOUS_FOE.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Noxious Foe (easy)!");
                }
            break;
            case 252://Black Dragon
            case 253:
            case 254:
            case 255:
            case 256:
            case 257:
            case 258:
            case 259:
            case 7861:
            case 7862:
            case 7863:
            case 8084:
                if (p.bigblackandFiery == 0) {
                    p.bigblackandFiery++;
                    CombatTaskList.BIG_BLACK_AND_FIERY.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Big, Black and Fiery (easy)!");
                }
                break;
            case 239://KBD
                if (p.kingblackdragonNovice == 5) {
                    KingOfTheWild.complete(p);
                }
                if (p.kingblackdragonNovice < 10) {
                    p.kingblackdragonNovice++;
                }
                if (p.kingblackdragonNovice == 10) {
                    CombatTaskList.KING_BLACK_DRAGON_NOVICE.isComplete(10);
                    p.combatTaskEasyCompleted++;
                    p.kingblackdragonNovice++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: King Black Dragon Novice (easy)!");
                }
                if (p.kingblackdragonNovice >= 10 && p.antifireProtection == 0 && p.getEquipment().contains(1540)
                && p.antifireTicks > 0) {
                    p.antifireProtection++;
                    CombatTaskList.ANTIFIRE_PROTECTION.isComplete(1);
                    p.combatTaskMediumCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Antifire Protection (medium)!");
                }
                if (p.kingblackdragonNovice >= 10 && p.clawClipper == 0 && p.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)) {
                    p.clawClipper++;
                    CombatTaskList.CLAW_CLIPPER.isComplete(1);
                    p.combatTaskMediumCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Claw Clipper (medium)!");
                }
                if (p.kingblackdragonNovice >= 10 && p.getCombat().getAttackStyle().equals(AttackStyle.STAB)) {
                    p.hidePenetration++;
                    CombatTaskList.HIDE_PENETRATION.isComplete(1);
                    p.combatTaskMediumCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Hide Penetration (medium)!");
                }
                if (p.kingblackdragonChampion < 25 && p.kingblackdragonNovice >= 10) {
                    p.kingblackdragonChampion++;
                }
                if (p.kingblackdragonChampion == 25) {
                    CombatTaskList.KING_BLACK_DRAGON_CHAMPION.isComplete(25);
                    p.combatTaskMediumCompleted++;
                    p.kingblackdragonChampion++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: King Black Dragon Champion (medium)!");
                }
                if (p.kingblackdragonNovice >= 10 && p.kingblackdragonChampion >= 25 && p.currentInstance.getType() == InstanceType.KBD) {
                    p.whoisthekingNow++;
                }
                if (p.whoisthekingNow == 10) {
                    CombatTaskList.WHO_IS_THE_KING_NOW.isComplete(25);
                    p.combatTaskHardCompleted++;
                    p.whoisthekingNow++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Who is the King Now? (hard)!");
                }
                break;
            case 494:
                if (p.tenTacles == 0 && p.currentInstance.getType().equals(InstanceType.KRAKEN)) {
                    p.tenTacles++;
                }
                if (p.tenTacles == 50) {
                    CombatTaskList.TEN_TACLES.isComplete(25);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.tenTacles++;
                    p.sendMessage("You have completed a combat task: Ten-tacles (elite)!");
                }
                if (p.krakanthurtMe < 25 && p.currentInstance.getType() == InstanceType.KRAKEN) {
                    p.krakanthurtMe++;
                }
                if (p.krakanthurtMe == 25) {
                    CombatTaskList.KRAKANT_HURT_ME.isComplete(25);
                    p.combatTaskHardCompleted++;
                    p.krakanthurtMe++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Krakan't Hurt Me (hard)!");
                }
                if (p.unnecessaryOptimization == 0 && !p.localNpcs().contains(5534)) {
                    p.unnecessaryOptimization++;
                    CombatTaskList.KRAKANT_HURT_ME.isComplete(25);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Krakan't Hurt Me (hard)!");
                }
                if (p.krakenAdept < 20) {
                    p.krakenAdept++;
                }
                if (p.krakenAdept == 20) {
                    CombatTaskList.KRAKEN_ADEPT.isComplete(20);
                    p.combatTaskHardCompleted++;
                    p.krakenAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Kraken Adept (hard)!");
                }
                if (p.onehundredTentacles < 100 && p.currentInstance.getType().equals(InstanceType.KRAKEN)) {
                    p.onehundredTentacles++;
                }
                if (p.onehundredTentacles == 100) {
                    CombatTaskList.ONE_HUNDRED_TENTACLES.isComplete(100);
                    p.combatTaskMasterCompleted++;
                    p.onehundredTentacles++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: One Hundred Tentacles (master)!");
                }
                break;
            case 3162:
                if (p.airborneShowdown == 0 && p.localNpcs().isEmpty()) {
                    p.airborneShowdown++;
                    CombatTaskList.AIRBORNE_SHOWDOWN.isComplete(20);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Airborne Showdown (hard)!");
                }
                if (p.krearraAdept < 50) {
                    p.krearraAdept++;
                }
                if (p.krearraAdept == 50) {
                    CombatTaskList.KREARRA_ADEPT.isComplete(20);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.krearraAdept++;
                    p.sendMessage("You have completed a combat task: Kree'arra Adept (hard)!");
                }
                if (p.krearraAdept >= 50 && p.kreearraVeteran < 100) {
                    p.kreearraVeteran++;
                }
                if (p.kreearraVeteran == 100) {
                    CombatTaskList.KREEARRA_VETERAN.isComplete(100);
                    p.combatTaskEliteCompleted++;
                    p.kreearraVeteran++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Kree'arra Veteran (elite)!");
                }
                if (p.swoopnoMore == 0) {
                    if (p.currentInstance.getType().equals(InstanceType.ARMADYL_GWD) &&
                            p.hitsUpdate.curHp == p.hitsUpdate.maxHp) {
                        p.swoopnoMore++;
                        CombatTaskList.SWOOP_NO_MORE.isComplete(1);
                        p.combatTaskMasterCompleted++;
                        p.totalTasksCompleted++;
                        p.sendMessage("You have completed a combat task: Swoop No More (master)!");
                    }
                }
                if (p.kreearraVeteran == 100 && p.featherHunter < 30 & p.currentInstance.getType().equals(InstanceType.ARMADYL_GWD)) {
                    p.featherHunter++;
                }
                if (p.featherHunter == 30) {
                    CombatTaskList.FEATHER_HUNTER.isComplete(1);
                    p.combatTaskGrandmasterCompleted++;
                    p.featherHunter++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Feather Hunter (grandmaster)!");
                }
                if (p.theworstrangedWeapon == 0 && p.getEquipment().contains(10146) || p.getEquipment().contains(10147)
                    || p.getEquipment().contains(10148)) {
                    p.theworstrangedWeapon++;
                    CombatTaskList.THE_WORST_RANGED_WEAPON.isComplete(1);
                    p.combatTaskGrandmasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: The Worst Ranged Weapon (grandmaster)!");
                }
                break;
            case 410:
            case 411:
                    if (p.masterofbroadWeaponry == 0) {
                        p.masterofbroadWeaponry++;
                        CombatTaskList.MASTER_OF_BROAD_WEAPONRY.isComplete(1);
                        p.combatTaskMediumCompleted++;
                        p.totalTasksCompleted++;
                        p.sendMessage("You have completed a combat task: Master of Broad Weaponry (medium)!");
                    }
                break;
            case 6611:
                if (p.vetionAdept < 10) {
                    p.vetionAdept++;
                }
                if (p.vetionAdept == 10) {
                    CombatTaskList.VETION_ADEPT.isComplete(10);
                    p.combatTaskHardCompleted++;
                    p.vetionAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Vet'ion Adept (hard)!");
                }
                if (p.vetionAdept >= 10 && p.vetEran < 20) {
                    p.vetEran++;
                }
                if (p.vetEran == 20) {
                    CombatTaskList.VET_ERAN.isComplete(20);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.vetEran++;
                    p.sendMessage("You have completed a combat task: Vet'eran (elite)!");
                }
                break;
            case 6504:
            case 6610:
                if (p.venenatisAdept <10) {
                    p.venenatisAdept++;
                }
                if (p.venenatisAdept == 10) {
                    CombatTaskList.VENENATIS_ADEPT.isComplete(10);
                    p.combatTaskHardCompleted++;
                    p.venenatisAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Venenatis Adept (hard)!");
                }
                if (p.venenatisAdept >= 10 && p.venenatisVeteran < 20) {
                    p.venenatisVeteran++;
                }
                if (p.venenatisVeteran == 20) {
                    CombatTaskList.VENENATIS_VETERAN.isComplete(20);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.venenatisVeteran++;
                    p.sendMessage("You have completed a combat task: Venenatis Veteran (elite)!");
                }
                break;
            case 7286:
                if (p.skotizoChampion == 0) {
                    p.skotizoChampion++;
                    CombatTaskList.SKOTIZO_CHAMPION.isComplete(1);
                    p.combatTaskMediumCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Skotizo Champion (medium)!");
                }
                if (p.skotizoChampion >= 1 && p.skotizoAdept < 5) {
                    p.skotizoAdept++;
                }
                if (p.skotizoAdept == 5) {
                    CombatTaskList.SKOTIZO_ADEPT.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.skotizoAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Skotizo Adept (hard)!");
                }
                if (p.skotizoAdept >= 5 && p.upfortheChallenge == 0 && !p.getEquipment().contains(2402) || p.getEquipment().contains(6746)
                        || p.getEquipment().contains(19675)) {
                    p.upfortheChallenge++;
                    CombatTaskList.UP_FOR_THE_CHALLENGE.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Up For the Challenge (elite)!");
                }
                if (p.skotizoAdept >= 5 && p.demonEvasion == 0 && p.hitsUpdate.curHp == p.hitsUpdate.maxHp) {
                    p.demonEvasion++;
                    CombatTaskList.DEMON_EVASION.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Demon Evasion (elite)!");
                }
                if (p.precisePositioning == 0 && p.getEquipment().contains(9976) || p.getEquipment().contains(9977)
                    || p.getEquipment().contains(10034) || p.getEquipment().contains(11959)) {
                    p.precisePositioning++;
                    CombatTaskList.PRECISE_POSITIONING.isComplete(1);
                    p.combatTaskMasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Precise Positioning (master)!");
                }
                break;
            case 1672://Barrows brothers
            case 1673:
            case 1674:
            case 1675:
            case 1676:
            case 1677:
                if (p.defencewhatDefence == 0 && p.getCombat().getAttackStyle().isMagic()) {
                    p.defencewhatDefence++;
                    CombatTaskList.DEFENCE_WHAT_DEFENCE.isComplete(1);
                    p.combatTaskEasyCompleted++;

                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Defence? What Defence? (easy)!");
                }
                Stat stat = p.getStats().get(StatType.Hitpoints);
                if ((p.barrowsNovice >= 10) && stat.currentLevel >= stat.fixedLevel) {
                    p.prayforSuccess++;
                }
                if (p.justlikeThat == 0 && p.specialRestoreTicks == 0) {
                    p.justlikeThat++;
                    CombatTaskList.JUST_LIKE_THAT.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Just Like That (hard)!");
                }
                break;
            case 2054:
                Stat stat2 = p.getStats().get(StatType.Hitpoints);
                if (p.theFlincher == 0 &&  stat2.currentLevel >= stat2.fixedLevel) {
                    p.theFlincher++;
                    CombatTaskList.THE_FLINCHER.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: The Flincher (hard)!");
                }
                if (p.chaoselementalAdept < 10) {
                    p.chaoselementalAdept++;
                }
                if (p.chaoselementalAdept == 10) {
                    CombatTaskList.CHAOS_ELEMENTAL_ADEPT.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.chaoselementalAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Chaos Elemental Adept (hard)!");
                }
                if (p.chaoselementalAdept >= 10 && p.chaoselementalVeteran < 25) {
                    p.chaoselementalVeteran++;
                }
                if (p.chaoselementalVeteran == 25) {
                    CombatTaskList.CHAOS_ELEMENTAL_VETERAN.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Chaos Elemental Veteran (elite)!");
                }
                break;
            case 6609:
                if (p.callistoAdept < 10) {
                    p.callistoAdept++;
                }
                if (p.callistoAdept == 10) {
                    CombatTaskList.CALLISTO_ADEPT.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.callistoAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Callisto Adept (hard)!");
                }
                if (p.callistoAdept >= 10 && p.callistoVeteran < 20) {
                    p.callistoVeteran++;
                }
                if (p.callistoVeteran == 20) {
                    CombatTaskList.CALLISTO_VETERAN.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.callistoVeteran++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Callisto Veteran (elite)!");
                }
                break;
            case 5886:
            case 5887:
            case 5888:
            case 5889:
            case 5890:
            case 5891:
            case 5908:
                if (p.abyssalAdept < 20) {
                    p.abyssalAdept++;
                }
                if (p.abyssalAdept == 20) {
                    CombatTaskList.ABYSSAL_ADEPT.isComplete(20);
                    p.combatTaskHardCompleted++;
                    p.abyssalAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Abyssal Adept (hard)!");
                }
                Stat stat5 = p.getStats().get(StatType.Hitpoints);
                if (p.perfectSire == 0 && stat5.currentLevel >= stat5.fixedLevel) {
                    CombatTaskList.PERFECT_SIRE.isComplete(1);
                    p.combatTaskEliteCompleted++;

                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Perfect Sire (elite)!");
                }
                if (p.abyssalAdept >= 20 && p.abyssalVeteran < 50) {
                    p.abyssalVeteran++;
                }
                if (p.abyssalVeteran == 50) {
                    CombatTaskList.ABYSSAL_VETERAN.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.abyssalVeteran++;
                    p.sendMessage("You have completed a combat task: Abyssal Veteran (elite)!");
                }
                break;
            case 7275:
                if (p.brutalbigblackandFiery == 0) {
                    p.brutalbigblackandFiery++;
                }
                if (p.brutalbigblackandFiery == 1) {
                    CombatTaskList.BRUTAL_BIG_BLACK_AND_FIERY.isComplete(1);
                    p.combatTaskMediumCompleted++;
                    p.brutalbigblackandFiery++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Brutal, Big, Black and Fiery (medium)!");
                }
                break;
            case 484://Bloodveld
            case 485:
            case 486:
            case 487:
            case 3138:
                if (p.thedemonicpunchingBag == 0) {
                    p.thedemonicpunchingBag++;
                    CombatTaskList.THE_DEMONIC_PUNCHING_BAG.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: The Demonic Punching Bag (easy)!");
                }
                break;
            case 7262:
                if (p.giantsdenMoss == 0) {
                    p.giantsdenMoss++;
                }
                if (p.giantsdenMoss >= 1 && p.giantsdenHill >= 1 && p.giantsdenFire >= 1) {
                    CombatTaskList.INTO_THE_DEN_OF_GIANTS.isComplete(1);
                    p.intothedenofGiants++;
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Into the Den of Giants (easy)!");
                }
                break;
            case 7261:
                if (p.giantsdenHill == 0) {
                    p.giantsdenHill++;
                }
                if (p.giantsdenMoss >= 1 && p.giantsdenHill >= 1 && p.giantsdenFire >= 1) {
                    CombatTaskList.INTO_THE_DEN_OF_GIANTS.isComplete(1);
                    p.intothedenofGiants++;
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Into the Den of Giants (easy)!");
                }
                break;
            case 7251:
                if (p.giantsdenFire == 0) {
                    p.giantsdenFire++;
                }
                if (p.giantsdenMoss >= 1 && p.giantsdenHill >= 1 && p.giantsdenFire >= 1) {
                    CombatTaskList.INTO_THE_DEN_OF_GIANTS.isComplete(1);
                    p.intothedenofGiants++;
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Into the Den of Giants (easy)!");
                }
                break;
            case 8195://Bryophyta
                if (p.poisonTicks == 0) {
                    p.preparationisKey++;
                    CombatTaskList.PREPARATION_IS_KEY.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Preparation is Key (easy)!");
                }
                if (p.bryophytaNovice == 0) {
                    p.bryophytaNovice++;
                    CombatTaskList.BRYOPHYTA_NOVICE.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Bryophyta Novice (easy)!");
                }
                if (p.bryophytaNovice >= 1) {
                    p.bryophytaChampion++;
                }
                if (p.bryophytaChampion == 5) {
                    CombatTaskList.BRYOPHYTA_CHAMPION.isComplete(5);
                    p.combatTaskMediumCompleted++;
                    p.bryophytaChampion++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Bryophyta Champion (medium)!");
                }
                if (p.protectionfromMoss == 0 && p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC)) {
                    p.protectionfromMoss++;
                    CombatTaskList.PROTECTION_FROM_MOSS.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Protection From Moss (easy)!");
                }
                if (p.aslowDeath == 0 && p.getCombat().getTarget().isPoisoned()) {
                    p.aslowDeath++;
                    CombatTaskList.A_SLOW_DEATH.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: A Slow Death (easy)!");
                }
                break;
            case 7806://Deranged Archaeologist
                if (p.derangedarchaeologistNovice < 10) {
                    p.derangedarchaeologistNovice++;
                }
                if (p.derangedarchaeologistNovice == 10) {
                    CombatTaskList.DERANGED_ARCHAEOLOGIST_NOVICE.isComplete(10);
                    p.derangedarchaeologistNovice++;

                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Deranged Archaeologist Novice (easy)!");
                }
                if (p.derangedarchaeologistChampion < 25) {
                    p.derangedarchaeologistChampion++;
                }
                if (p.derangedarchaeologistChampion == 25) {
                    CombatTaskList.DERANGED_ARCHAEOLOGIST_CHAMPION.isComplete(25);
                    p.combatTaskMediumCompleted++;
                    p.derangedarchaeologistChampion++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Deranged Archaeologist Champion (medium)!");
                }
                break;
            case 412:
            case 413:
                if (p.asmashingTime == 0) {
                    p.asmashingTime++;
                    CombatTaskList.A_SMASHING_TIME.isComplete(1);
                    p.combatTaskMediumCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: A Smashing Time (medium)!");
                }
                break;
            case 2075://Fire Giant
            case 2076:
            case 2077:
            case 2078:
            case 2079:
            case 2080:
            case 2081:
            case 2082:
            case 2083:
            case 2084:
                if (p.thewalkingVolcano == 0) {
                    p.thewalkingVolcano++;
                    CombatTaskList.THE_WALKING_VOLCANO.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: The Walking Volcano (easy)!");
                }
                break;
            case 2025://Greater Demon
            case 2026:
            case 2027:
            case 2028:
            case 2029:
            case 2030:
            case 2031:
            case 2032:
            case 7244:
            case 7245:
            case 7246:
            case 7871:
            case 7872:
            case 7873:
                if (p.agreaterFoe == 0) {
                    p.agreaterFoe++;
                    CombatTaskList.A_GREATER_FOE.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: A Greater Foe (easy)!");
                }
                if (p.npcTarget && p.getEquipment().contains(2402) || p.getEquipment().contains(6746)
                 || p.getEquipment().contains(19675)) {
                    p.notsogreatafterAll++;
                    CombatTaskList.NOT_SO_GREAT_AFTER_ALL.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Not So Great After All (easy)!");
                }
                break;
            case 3127:
            case 6506:
                if (p.getCombat().getAttackStyle().isMelee() && p.facingjadheadOn == 0) {
                    p.facingjadheadOn++;
                    CombatTaskList.FACING_JAD_HEAD_ON.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Not So Great After All (elite)!");
                }
                break;
            case 7144:
            case 7145:
            case 7146:
            case 7147:
            case 7148:
            case 7149:
                if (p.hittingthemwhereitHurts == 0 && p.getEquipment().contains(2402) || p.getEquipment().contains(6746)
                        || p.getEquipment().contains(19675)) {
                    p.hittingthemwhereitHurts++;
                    CombatTaskList.HITTING_THEM_WHERE_IT_HURTS.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Hitting Them Where It Hurts (elite)!");
                }
                if (p.ifgorillascouldFly == 0) {
                    p.ifgorillascouldFly++;
                    CombatTaskList.IF_GORILLAS_COULD_FLY.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: If Gorillas Could Fly (elite)!");
                }
                break;
            case 3129:
                KrilTsutsaroth.attackTotal = 0;
                if (p.demonbaneWeaponryII == 0 && p.getEquipment().contains(2402) || p.getEquipment().contains(6746)
                        || p.getEquipment().contains(19675)) {
                    p.demonbaneWeaponryII++;
                    CombatTaskList.DEMONBANE_WEAPONRY_II.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Demonbane Weaponry II (hard)!");
                }
                if (p.demonicShowdown == 0 && p.localNpcs().isEmpty()) {
                    p.demonicShowdown++;
                    CombatTaskList.DEMONIC_SHOWDOWN.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Demonic Showdown (hard)!");
                }
                if (p.yarrnoMore == 0 && KrilTsutsaroth.specialKril == 0) {
                    p.yarrnoMore++;
                    CombatTaskList.YARR_NO_MORE.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Yarr No More (hard)!");
                }
                if (p.krilAdept < 50) {
                    p.krilAdept++;
                }
                if (p.krilAdept == 50) {
                    CombatTaskList.KRIL_ADEPT.isComplete(50);
                    p.combatTaskHardCompleted++;
                    p.krilAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: K'ril Tsutsaroth Adept (hard)!");
                }
                if (p.demonicDefence == 0 && KrilTsutsaroth.attackTotal == 0 && p.currentInstance.getType().equals(InstanceType.ZAMORAK_GWD)) {
                    p.demonicDefence++;
                    CombatTaskList.DEMONIC_DEFENCE.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Demonic Defence (elite)!");
                }
                if (p.thebaneofDemons == 0 && p.getEquipment().contains(2402) || p.getEquipment().contains(6746)
                        || p.getEquipment().contains(19675) && p.currentInstance.getType().equals(InstanceType.ZAMORAK_GWD)) {
                    p.thebaneofDemons++;
                    CombatTaskList.THE_BANE_OF_DEMONS.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: The Bane of Demons (elite)!");
                }
                if (p.krilAdept >= 50 && p.krilVeteran < 100) {
                    p.krilVeteran++;
                }
                if (p.krilVeteran == 100) {
                    CombatTaskList.KRIL_VETERAN.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.krilVeteran++;
                    p.sendMessage("You have completed a combat task: K'ril Tsutsaroth Veteran (elite)!");
                }
                if (p.krilVeteran >= 100 && p.ashCollector < 20 && p.currentInstance.getType().equals(InstanceType.ZAMORAK_GWD)) {
                    p.ashCollector++;
                }
                if (p.ashCollector == 20) {
                    CombatTaskList.ASH_COLLECTOR.isComplete(20);
                    p.combatTaskGrandmasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Ash Collector (grandmaster)!");
                }
                if (p.krilVeteran >= 100 && p.demonWhisperer == 0 && p.currentInstance.getType().equals(InstanceType.ZAMORAK_GWD)
                    && p.hitsUpdate.curHp == p.hitsUpdate.maxHp) {
                    p.demonWhisperer++;
                    CombatTaskList.DEMON_WHISPERER.isComplete(1);
                    p.combatTaskGrandmasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Demon Whisperer (grandmaster)!");
                }
                break;
            case 963:
            case 965:
                if (p.kalphitequeenAdept < 25) {
                    p.kalphitequeenAdept++;
                }
                if (p.kalphitequeenAdept == 25) {
                    CombatTaskList.KALPHITE_QUEEN_ADEPT.isComplete(25);
                    p.combatTaskHardCompleted++;
                    p.kalphitequeenAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Kalphite Queen Adept (hard)!");
                }
                if (p.kalphitequeenAdept >= 25 && p.prayerSmasher == 0 && p.getEquipment().contains(4755) ||
                p.getEquipment().contains(4982) || p.getEquipment().contains(4983) || p.getEquipment().contains(4984) ||
                p.getEquipment().contains(4985)) {
                    p.prayerSmasher++;
                    CombatTaskList.PRAYER_SMASHER.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Prayer Smasher (elite)!");
                }
                if (p.insectDeflection == 0 && p.vengeanceActive) {
                    p.insectDeflection++;
                    CombatTaskList.INSECT_DEFLECTION.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Insect Deflection (elite)!");
                }
                if (p.kalphitequeenAdept == 25 && p.kalphitequeenVeteran < 50) {
                    p.kalphitequeenVeteran++;
                }
                if (p.kalphitequeenVeteran == 50) {
                    CombatTaskList.KALPHITE_QUEEN_VETERAN.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.kalphitequeenVeteran++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Kalphite Queen Veteran (elite)!");
                }
                break;
            case 104://Hellhound
            case 105:
            case 135:
            case 3133:
            case 7256:
            case 7877:
                if (p.ademonsbestFriend == 0) {
                    p.ademonsbestFriend++;
                    CombatTaskList.A_DEMONS_BEST_FRIEND.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: A Demons Best Friend (easy)!");
                }
                break;
            case 7416://Obor
                if (p.oborNovice == 0) {
                    p.oborNovice++;
                    CombatTaskList.OBOR_NOVICE.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Obor Novice (easy)!");
                }
                if (p.getCombat().getTarget() != p.getNpc() && p.getCombat().getTarget().isStunned()
                        || p.getCombat().getTarget().isFrozen()) {
                    p.sleepingGiant++;
                    CombatTaskList.SLEEPING_GIANT.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Sleeping Giant (easy)!");
                }
                if (p.sleepingGiant >= 1 && p.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE) || p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC)
                || p.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES)) {
                    p.squashingtheGiant++;
                    CombatTaskList.SQUASHING_THE_GIANT.isComplete(1);
                    p.combatTaskMediumCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Squashing the Giant (medium)!");
                }
                if (p.sleepingGiant >= 1 && p.oborChampion < 5) {
                    p.oborChampion++;
                }
                if (p.oborChampion == 5) {
                    CombatTaskList.OBOR_CHAMPION.isComplete(5);
                    p.combatTaskMediumCompleted++;
                    p.totalTasksCompleted++;
                    p.oborChampion++;
                    p.sendMessage("You have completed a combat task: Obor Champion (medium)!");
                }
                break;
            case 6615:
                if (p.guardiansnoMore == 0 && !p.localNpcs().isEmpty()) {
                    p.guardiansnoMore++;
                    CombatTaskList.GUARDIANS_NO_MORE.isComplete(5);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Guardians No More (hard)!");
                }
                Stat stat4 = p.getStats().get(StatType.Hitpoints);
                if (p.icantreachThat == 0 && stat4.currentLevel >= stat4.fixedLevel) {
                    p.icantreachThat++;
                    CombatTaskList.I_CANT_REACH_THAT.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: I Can't Reach That (hard)!");
                }
                if (p.scorpiaAdept < 10) {
                    p.scorpiaAdept++;
                }
                if (p.scorpiaAdept == 10) {
                    CombatTaskList.SCORPIA_ADEPT.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.scorpiaAdept++;
                    p.sendMessage("You have completed a combat task: Scorpia Adept (hard)!");
                }
                if (p.scorpiaAdept >= 10 && p.scorpiaVeteran < 25) {
                    p.scorpiaVeteran++;
                }
                if (p.scorpiaVeteran == 25) {
                    CombatTaskList.SCORPIA_VETERAN.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.scorpiaVeteran++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Scorpia Veteran (elite)!");
                }
                break;
            case 6618:
                if (p.crazyarchaeologistChampion < 10) {
                    p.crazyarchaeologistChampion++;
                }
                if (p.crazyarchaeologistChampion == 10) {
                    CombatTaskList.CRAZY_ARCHAEOLOGIST_CHAMPION.isComplete(10);
                    p.combatTaskMediumCompleted++;
                    p.totalTasksCompleted++;
                    p.crazyarchaeologistChampion++;
                    p.sendMessage("You have completed a combat task: Crazy Archaeologist Champion (medium)!");
                }
                if (p.mageoftheRuins == 0 && p.getCombat().getAttackStyle().isMagic()) {
                    p.mageoftheRuins++;
                    CombatTaskList.MAGE_OF_THE_RUINS.isComplete(1);
                    p.combatTaskMediumCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Mage of the Ruins (medium)!");
                }
                if (p.crazyarchaeologistChampion == 10 && p.crazyarchaeologistAdept < 25) {
                    p.crazyarchaeologistAdept++;
                }
                if (p.crazyarchaeologistAdept == 25) {
                    CombatTaskList.CRAZY_ARCHAEOLOGIST_ADEPT.isComplete(25);
                    p.combatTaskHardCompleted++;
                    p.crazyarchaeologistAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Crazy Archaeologist Adept (hard)!");
                }
            break;
            case 6619:
                if (p.chaosfanaticChampion < 10) {
                    p.chaosfanaticChampion++;
                }
                if (p.chaosfanaticChampion == 10) {
                    CombatTaskList.CHAOS_FANATIC_CHAMPION.isComplete(10);
                    p.combatTaskMediumCompleted++;
                    p.totalTasksCompleted++;
                    p.chaosfanaticChampion++;
                    p.sendMessage("You have completed a combat task: Chaos Fanatic Champion (medium)!");
                }
                if (p.chaosfanaticChampion >= 10 && p.prayingtotheGods < 10 && p.getPosition().regionId() == 11836
                 || p.getPosition().regionId() == 11835) {
                    p.prayingtotheGods++;
                }
                if (p.prayingtotheGods == 10) {
                    CombatTaskList.PRAYING_TO_THE_GODS.isComplete(10);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Praying to the Gods (hard)!");
                }
                if (p.chaosfanaticAdept < 25 && p.chaosfanaticChampion == 10) {
                    p.chaosfanaticAdept++;
                }
                if (p.chaosfanaticAdept == 25) {
                    CombatTaskList.CHAOS_FANATIC_ADEPT.isComplete(10);
                    p.combatTaskHardCompleted++;
                    p.chaosfanaticAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Chaos Fanatic Adept (hard)!");
                }
                break;
            case 2215:
                if (p.generalShowdown == 0 && p.localNpcs().isEmpty()) {
                    p.generalShowdown++;
                    CombatTaskList.GENERAL_SHOWDOWN.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: General Showdown (hard)!");
                }
                if (p.ourgFreezer == 0 && p.getCombat().getTarget().isFrozen() ||
                        p.getCombat().getTarget().isStunned()) {
                    p.ourgFreezer++;
                    CombatTaskList.OURG_FREEZER.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Ourg Freezer (hard)!");
                }
                if (p.generalgraardorAdept < 50) {
                    p.generalgraardorAdept++;
                }
                if (p.generalgraardorAdept == 50) {
                    CombatTaskList.GENERAL_GRAARDOR_ADEPT.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.generalgraardorAdept++;
                    p.sendMessage("You have completed a combat task: General Graardor Adept (hard)!");
                }

                if (p.generalgraardorAdept >= 50 && p.generalgraardorVeteran < 100) {
                    p.generalgraardorVeteran++;
                }
                if (p.generalgraardorVeteran == 100) {
                    CombatTaskList.GENERAL_GRAARDOR_VETERAN.isComplete(100);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;

                    p.generalgraardorVeteran++;
                    p.sendMessage("You have completed a combat task: General Graardor Veteran (elite)!");
                }
                if (p.generalgraardorVeteran >= 100 && p.ourgKiller < 15 && p.currentInstance.getType().equals(InstanceType.BANDOS_GWD)) {
                    p.ourgKiller++;
                }
                if (p.ourgKiller == 15) {
                    CombatTaskList.OURG_KILLER.isComplete(15);
                    p.combatTaskGrandmasterCompleted++;
                    p.ourgKiller++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Ourg Killer (grandmaster)!");
                }
                if (p.generalgraardorVeteran == 100 && p.currentInstance.getType().equals(InstanceType.BANDOS_GWD) &&
                    p.keepAway == 0 && p.hitsUpdate.curHp == p.hitsUpdate.maxHp) {
                    p.keepAway++;
                    CombatTaskList.KEEP_AWAY.isComplete(15);
                    p.combatTaskGrandmasterCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Keep Away (grandmaster)!");
                }
                break;
            case 2205:
                if (p.commanderShowdown == 0 && p.localNpcs().isEmpty()) {
                    p.commanderShowdown++;
                    CombatTaskList.COMMANDER_SHOWDOWN.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Commander Showdown (hard)!");
                }
                if (p.commanderzilyanaAdept < 50) {
                    p.commanderzilyanaAdept++;
                }
                if (p.commanderzilyanaAdept == 50) {
                    CombatTaskList.COMMAND_ZILYANA_ADEPT.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.commanderzilyanaAdept++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Commander Zilyana Adept (hard)!");
                }
                if (p.commanderzilyanaAdept >= 50 && p.currentInstance.getType().equals(InstanceType.SARADOMIN_GWD)
                    && p.getCombat().getAttackStyle().isMelee()) {
                    p.reminisce++;
                    CombatTaskList.REMINISCE.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Reminisce (elite)!");
                }
                if (p.commanderzilyanaAdept >= 50 && p.commanderzilyanaVeteran < 100) {
                    p.commanderzilyanaVeteran++;
                    CombatTaskList.COMMANDER_ZILYANA_VETERAN.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Commander Zilyana Veteran (elite)!");
                }
                if (p.commanderzilyanaVeteran >= 100 && p.peachConjurer < 50 && p.currentInstance.getType().equals(InstanceType.SARADOMIN_GWD)) {
                    p.peachConjurer++;
                }
                if (p.peachConjurer == 50) {
                    CombatTaskList.PEACH_CONJURER.isComplete(1);
                    p.combatTaskGrandmasterCompleted++;
                    p.totalTasksCompleted++;
                    p.peachConjurer++;
                    p.sendMessage("You have completed a combat task: Peach Conjurer (grandmaster)!");
                }
                if (p.animalWhisperer == 0) {
                    if (p.currentInstance.getType().equals(InstanceType.SARADOMIN_GWD)
                            && p.hitsUpdate.curHp == p.hitsUpdate.maxHp) {
                        p.animalWhisperer++;
                        CombatTaskList.ANIMAL_WHISPERER.isComplete(1);
                        p.combatTaskGrandmasterCompleted++;
                        p.totalTasksCompleted++;
                        p.sendMessage("You have completed a combat task: Animal Whisperer (grandmaster)!");
                    }
                }
                break;
            case 319:
                if (p.chickenKiller == 0) {
                    p.chickenKiller++;
                    CombatTaskList.CHICKEN_KILLER.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Chicken Killer (elite)!");
                    ChickenKiller.complete(p);
                }
                if (p.findingtheweakSpot == 0
                        && p.getEquipment().contains(16893) || p.getEquipment().contains(13092)
                        || p.getEquipment().contains(13021)) {
                    p.findingtheweakSpot++;
                    CombatTaskList.FINDING_THE_WEAK_SPOT.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Finding the Weak Spot (elite)!");
                }
                if (p.hotonyourFeet == 0 && !p.localNpcs().isEmpty()) {
                    p.hotonyourFeet++;
                    CombatTaskList.HOT_ON_YOUR_FEET.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Hot on Your Feet (elite)!");
                }
                if (p.corporealbeastVeteran < 25) {
                    p.corporealbeastVeteran++;
                }
                if (p.corporealbeastVeteran == 25) {
                    CombatTaskList.CORPOREAL_BEAST_VETERAN.isComplete(25);
                    p.combatTaskEliteCompleted++;
                    p.corporealbeastVeteran++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Corporeal Beast Veteran (elite)!");
                }
                if (p.corporealbeastVeteran >= 25 && p.corporealbeastMaster < 50) {
                    p.corporealbeastMaster++;
                }
                if (p.corporealbeastMaster == 50) {
                    CombatTaskList.CORPOREAL_BEAST_MASTER.isComplete(50);
                    p.combatTaskEliteCompleted++;
                    p.corporealbeastMaster++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Corporeal Beast Master (elite)!");
                }
                break;
            case 6766://Lizardman shaman
            case 6767:
            case 7573:
            case 7574:
            case 7744:
            case 7745:
            case 8565:
                if (p.ascaleyEncounter == 0) {
                    p.ascaleyEncounter++;
                    CombatTaskList.A_SCALEY_ENCOUNTER.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: A Scaley Encounter (easy)!");
                }
                if (p.shayzienProtector == 0 && p.getRegions().equals(5177)) {
                    p.shayzienProtector++;
                    CombatTaskList.SHAYZIEN_PROTECTOR.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Shayzien Protector (easy)!");
                }
                if (npcId == 7573 || npcId == 7574 && p.shayzienSpecialist == 0 && p.hitsUpdate.curHp == p.hitsUpdate.maxHp) {
                    p.shayzienSpecialist++;
                    CombatTaskList.SHAYZIEN_SPECIALIST.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Shayzien Specialist (elite)!");
                }
                break;
            case 7527:
            case 7528:
            case 7529:
                if (p.togetherwellFall == 0) {
                    p.togetherwellFall++;
                    CombatTaskList.TOGETHER_WELL_FALL.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Together We'll Fall (elite)!");
                }
                break;
            case 7563:
                LargeMuttadile.healCounter = 0;
                if (LargeMuttadile.healCounter == 0 && p.muttaDiet == 0) {
                    p.muttaDiet++;
                    CombatTaskList.MUTTA_DIET.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Mutta-Diet (elite)!");
                }
                break;
            case 7700:
                    if (p.facingjadheadonIii == 0 && p.getCombat().getAttackStyle().isMelee()) {
                        p.facingjadheadonIii++;
                        CombatTaskList.FACING_JAD_HEAD_ON_III.isComplete(1);
                        p.combatTaskEliteCompleted++;
                        p.totalTasksCompleted++;
                        p.sendMessage("You have completed a combat task: Facing Jad Head On III (elite)!");
                    }
                break;
            case 499:
                if (p.hazardPrevention == 0 && p.hitsUpdate.curHp == p.hitsUpdate.maxHp) {
                    p.hazardPrevention++;
                    CombatTaskList.HAZARD_PREVENTION.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Hazard Prevention (elite)!");
                }
                if (p.thermonuclearVeteran < 20) {
                    p.thermonuclearVeteran++;
                }
                if (p.thermonuclearVeteran == 20) {
                    CombatTaskList.THERMONUCLEAR_VETERAN.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.thermonuclearVeteran++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Thermonuclear Veteran (elite)!");
                }
                break;
            case 5779://Giant mole
            case 6499:
                GiantMole.attackCounter = 0;
                if (p.giantmoleNovice == 5) {
                    MoleyMoleyMoley.complete(p);
                }
                if (p.giantmoleNovice < 10) {
                    p.giantmoleNovice++;
                }
                if (p.giantmoleNovice == 10) {
                    CombatTaskList.GIANT_MOLE_NOVICE.isComplete(10);
                    p.combatTaskEasyCompleted++;
                    p.giantmoleNovice++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Giant Mole Novice (easy)!");
                }
                if (p.giantmoleChampion < 25 && p.giantmoleNovice >= 10) {
                    p.giantmoleChampion++;
                }
                if (p.giantmoleChampion == 25) {
                    CombatTaskList.GIANT_MOLE_CHAMPION.isComplete(10);
                    p.combatTaskMediumCompleted++;
                    p.giantmoleChampion++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Giant Mole Champion (medium)!");
                }
                Stat stat1 = p.getStats().get(StatType.Hitpoints);
                if ((p.avoidingthoselittleArms == 0) && stat1.currentLevel >= stat1.fixedLevel) {
                    p.avoidingthoselittleArms++;
                    CombatTaskList.AVOIDING_THOSE_LITTLE_ARMS.isComplete(1);
                    p.combatTaskMediumCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Avoiding Those Little Arms (medium)!");
                }
                if (GiantMole.attackCounter <= 4 && p.hardHitter == 0) {
                    p.hardHitter++;
                    CombatTaskList.HARD_HITTER.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Hard Hitter (elite)!");
                }
                break;
            case 7699:
            case 7703:
                if (p.halfwayThere == 0) {
                    p.halfwayThere++;
                    CombatTaskList.HALF_WAY_THERE.isComplete(1);
                    p.combatTaskEliteCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Half-Way There (elite)!");
                }
                break;
            case 8713://Sarachnis
                if (p.sarachnisNovice < 10) {
                    p.sarachnisNovice++;
                }
                if (p.sarachnisNovice == 10) {
                    CombatTaskList.SARACHNIS_NOVICE.isComplete(10);
                    p.combatTaskEasyCompleted++;
                    p.sarachnisNovice++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Sarachnis Novice (easy)!");
                }
                if (p.sarachnisNovice >= 10 && p.newspaperEnthusiast == 0 && p.getCombat().getAttackStyle().equals(AttackStyle.CRUSH)) {
                    p.newspaperEnthusiast++;
                    CombatTaskList.NEWSPAPER_ENTHUSIAST.isComplete(1);
                    p.combatTaskMediumCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Newspaper Enthusiast (medium)!");
                }
                if (p.sarachnisNovice >= 10 && p.sarachnisChampion < 25) {
                    p.sarachnisChampion++;
                }
                if (p.sarachnisChampion == 25) {
                    CombatTaskList.SARACHNIS_CHAMPION.isComplete(25);
                    p.combatTaskMediumCompleted++;
                    p.totalTasksCompleted++;
                    p.sarachnisChampion++;
                    p.sendMessage("You have completed a combat task: Sarachnis Champion (medium)!");
                }
                Stat stat3 = p.getStats().get(StatType.Hitpoints);
                if (p.sarachnisChampion >= 25 && p.insectRepellant == 0 && stat3.currentLevel >= stat3.fixedLevel) {
                    p.insectRepellant++;
                    CombatTaskList.INSPECT_REPELLANT.isComplete(1);
                    p.combatTaskHardCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: Inspect Repellent (hard)!");
                }
                break;
            case 8610:
            case 8611:
                if (p.aslitheryEncounter == 0) {
                    p.aslitheryEncounter++;
                    CombatTaskList.A_SLITHERY_ENCOUNTER.isComplete(1);
                    p.combatTaskEasyCompleted++;
                    p.totalTasksCompleted++;
                    p.sendMessage("You have completed a combat task: A Slithery Encounter (easy)!");
                }
                break;
        }
    }

}
