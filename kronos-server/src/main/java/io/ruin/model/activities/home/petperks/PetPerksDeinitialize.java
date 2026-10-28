package io.ruin.model.activities.home.petperks;

import io.ruin.model.entity.player.Player;

public class PetPerksDeinitialize {

    public static void deinitialize(Player p, int id) {
        switch (id) {
            //Baby Lava Dragon
            case 15017:
                p.babyLavaDragBonus = 1;
                break;
            //Abyssal Orphan
            case 5884:
                p.abyssalOrphan = false;
                break;
            //Baby Mole
            case 6635:
                p.babyMole = false;
                break;
            //Callisto Cub
            case 5558:
                p.callistoCub = false;
                break;
            //Hellpuppy
            case 3099:
                p.hellPuppy = false;
                break;
            //Kalphite Princess
            case 6637:
            case 6638:
                p.kalphitePrincess = false;
                break;
            //Chaos Elemental
            case 2055:
                p.chaosEle = false;
                break;
            //Dagannoth Kings
            case 6628:
                p.getCombat().restore();
            case 6629:
                p.getCombat().restore();
                break;
            case 6630:
                p.getCombat().restore();
                break;
            //Dark Core
            case 318:
                p.darkCore = false;
                break;
            //General Graardor
            case 6632:
                p.getCombat().restore();
                break;
            //Jal Nib Rek
            case 7675:
                p.jalNibRek = false;
                break;
            //Kril Tsutsaroth
            case 6634:
                p.getCombat().restore();
                break;
            //Kraken
            case 6640:
                p.getCombat().restore();
                break;
            //Kree arra
            case 6631:
                p.getCombat().restore();
                break;
            //Midnight
            case 7893:
                //Noon
            case 7892:
                p.midnightandnoonBonus = 1;
                break;
            //Smoke Devil
            case 6639:
                p.smokeDevilPet = false;
                break;
            //Zulrah Snakelings
            case 2130:
            case 2131:
            case 2132:
                p.zulrahSnakeling = false;
                break;
            //Commander Zilyana
            case 6633:
                p.getCombat().restore();
                break;
            //Prince Black Dragon - gives the player anti-fire immunity
            case 6636:
                p.princeBlackDragon = false;
                break;
            //Scorpias Offspring - gives the player poison immunity
            case 5561:
                p.poisonImmunity = 0;
                break;
            //Tzrek-Jad
            case 5893:
                p.tzrekJad = false;
                break;
            //Venenatis Spiderling
            case 5557:
                p.babyLavaDragBonus = 1;
                break;
            //Vetion Jr
            case 5559:
            case 5560:
                p.babyLavaDragBonus = 1;
                break;
            //Skotos
            case 7671:
                p.getCombat().restore();
                break;
            //Vorki
            case 8029:
                p.vorki = false;
                break;
            //Lil zik
            case 8337:
                p.lilZik = false;
                break;
            //Tzrek-Zuk
            case 8009:
                p.tzrekZuk = false;
                break;
            //Corporeal Critter
            case 8010:
                p.corporealCritter = false;
                break;
            //Hydra
            case 8492:
            case 8493:
            case 8494:
                p.ikkleHydra = false;
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
                p.bonusChins = false;
                break;
            //Beaver - Gives the player noted wood while woodcutting
            case 6724:
                p.notedLogs = false;
                break;
            //Heron - Gives the player noted fish while fishing
            case 6722:
                p.notedFish = false;
                break;
            //Rock Golem - Gives the player noted ore while mining
            case 7451:
                p.notedOres = false;
                break;
            //Tangleroot
            case 7352:
                p.tangleroot = false;
                break;
            //Rocky - gives the player x2 money while thieving
            case 7353:
                p.thievingBonus = 1;
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
                p.riftGuardian = false;
                break;
            //Herbi - Gives the player noted herbs while farming
            case 7760:
                p.notedHerbs = false;
                break;
            //Bloodhound - Gives the player x2 loot from clue scrolls
            case 7232:
                p.clueChance = 1;
                break;
            //Olmlet
            case 7520:
                p.doubleCox = false;
                break;
            //Puppadile
            case 8201:
                p.doubleCox = false;
                break;
            //Tektiny
            case 8202:
                p.doubleCox = false;
                break;
            //Vanguard
            case 8203:
                p.doubleCox = false;
                break;
            //Vasa
            case 8204:
                p.doubleCox = false;
                break;
            //Vespine
            case 8205:
                p.doubleCox = false;
                break;
            case 8729:
                p.yungllef = false;
                break;
        }

    }

}