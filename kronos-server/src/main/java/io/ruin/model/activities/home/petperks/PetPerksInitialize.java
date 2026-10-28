package io.ruin.model.activities.home.petperks;

import io.ruin.utility.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

public class PetPerksInitialize {

    public static void initialize(Player p, int id) {
        switch (id) {
                //Baby Lava Dragon
            case 15017:
                p.babyLavaDragBonus = 2;
                break;
                //Abyssal Orphan
            case 5884:
                p.abyssalOrphan = true;
                break;
                //Baby Mole
            case 6635:
                p.babyMole = true;
                break;
                //Callisto Cub
            case 5558:
                p.callistoCub = true;
                break;
                //Hellpuppy
            case 3099:
                p.hellPuppy = true;
                break;
                //Kalphite Princess
            case 6637:
            case 6638:
                p.kalphitePrincess = true;
                break;
                //Chaos Elemental
            case 2055:
                p.chaosEle = true;
                break;
                //Dagannoth Kings
            case 6628:
                p.getStats().get(StatType.Defence).boost(5, 0.15);
            case 6629:
                p.getStats().get(StatType.Strength).boost(5, 0.15);
                break;
            case 6630:
                p.getStats().get(StatType.Attack).boost(5, 0.15);
                break;
                //Dark Core
            case 318:
                p.darkCore = true;
                break;
                //General Graardor
            case 6632:
                p.getStats().get(StatType.Strength).boost(8, 0.15);
                break;
                //Jal Nib Rek
            case 7675:
                p.jalNibRek = true;
                break;
                //Kril Tsutsaroth
            case 6634:
                p.getStats().get(StatType.Magic).boost(8, 0.15);
                break;
                //Kraken
            case 6640:
                if (p.getCombat().isDefending(25) && Random.rollPercent(5)) {
                    p.incrementHp(p.getStats().get(StatType.Hitpoints).fixedLevel / 10);
                    p.graphics(1811, 0, 0);
                }
                break;
                //Kree arra
            case 6631:
                p.getStats().get(StatType.Ranged).boost(8, 0.15);
                break;
                //Midnight
            case 7893:
                //Noon
            case 7892:
                p.midnightandnoonBonus = 2;
                break;
            //Smoke Devil
            case 6639:
                p.smokeDevilPet = true;
                break;
                //Zulrah Snakelings
            case 2130:
            case 2131:
            case 2132:
                p.zulrahSnakeling = true;
                break;
                //Commander Zilyana
            case 6633:
                p.getStats().get(StatType.Magic).boost(8, 0.15);
                break;
                //Prince Black Dragon - gives the player anti-fire immunity
            case 6636:
                p.princeBlackDragon = true;
                break;
                //Scorpias Offspring - gives the player poison immunity
            case 5561:
                p.poisonImmunity = 120;
                break;
                //Tzrek-Jad
            case 5893:
                p.tzrekJad = true;
                break;
                //Venenatis Spiderling
            case 5557:
                p.babyLavaDragBonus = 2;
                break;
                //Vetion Jr
            case 5559:
            case 5560:
                p.babyLavaDragBonus = 2;
                break;
                //Skotos
            case 7671:
                if (p.getCombat().getTarget() != null && p.getCombat().getTarget().npc.getDef().demon) {
                    p.getStats().get(StatType.Strength).boost(10, 0.25);
                    p.getStats().get(StatType.Attack).boost(10, 0.25);
                }
                break;
                //Vorki
            case 8029:
                p.vorki = true;
                break;
                //Lil zik
            case 8337:
                p.lilZik = true;
                break;
                //Tzrek-Zuk
            case 8009:
                p.tzrekZuk = true;
                break;
                //Corporeal Critter
            case 8010:
                p.corporealCritter = true;
                break;
                //Hydra
            case 8492:
            case 8493:
            case 8494:
                p.ikkleHydra = true;
                break;
                //Sraracha
            case 2143:

                break;
//                //Abyssal Sire
//            case 15006:
//
//                break;
//                //Cerberus
//            case 15007:
//
//                break;
                //Chinchompa - Gives the player +3 additional chinchompas whilst hunting them
            case 6756:
            case 6757:
            case 6758:
            case 6759:
                p.bonusChins = true;
                break;
                //Beaver - Gives the player noted wood while woodcutting
            case 6724:
                p.notedLogs = true;
                break;
                //Heron - Gives the player noted fish while fishing
            case 6722:
                p.notedFish = true;
                break;
                //Rock Golem - Gives the player noted ore while mining
            case 7451:
                p.notedOres = true;
                break;
                //Tangleroot
            case 7352:
                p.tangleroot = true;
                break;
                //Rocky - gives the player x2 money while thieving
            case 7353:
                p.thievingBonus = 2;
                break;
                //Rift Guardian
            case 7354:
            case 7355:
            case 7356:
            case 7357:
            case 7358:
            case 7359:
            case 7360:
            case 7361:
            case 7362:
            case 7363:
            case 7364:
            case 7365:
            case 7366:
            case 7367:
                p.riftGuardian = true;
                break;
                //Herbi - Gives the player noted herbs while farming
            case 7760:
                p.notedHerbs = true;
                break;
                //Bloodhound - Gives the player x2 loot from clue scrolls
            case 7232:
                p.clueChance = 2;
                break;
                //Olmlet
            case 7520:
                p.doubleCox = true;
                break;
                //Puppadile
            case 8201:
                p.doubleCox = true;
                break;
                //Tektiny
            case 8202:
                p.doubleCox = true;
                break;
                //Vanguard
            case 8203:
                p.doubleCox = true;
                break;
                //Vasa
            case 8204:
                p.doubleCox = true;
                break;
                //Vespine
            case 8205:
                p.doubleCox = true;
                break;
            case 8729:
                p.yungllef = true;
                break;
        }
    }

}