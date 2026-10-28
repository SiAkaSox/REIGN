package io.ruin.model.activities.home.petperks;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;

import static io.ruin.model.activities.home.petperks.bosspets.AbyssalOrphan.sendAbyssalOrphan;
import static io.ruin.model.activities.home.petperks.bosspets.BabyMole.sendBabyMole;
import static io.ruin.model.activities.home.petperks.bosspets.CallistoCub.sendCallistoCub;
import static io.ruin.model.activities.home.petperks.bosspets.ChaosEle.sendPetChaosElemental;
import static io.ruin.model.activities.home.petperks.bosspets.CommanderZilyana.sendPetCommanderZilyana;
import static io.ruin.model.activities.home.petperks.bosspets.CorporealCritter.sendCorporealCritter;
import static io.ruin.model.activities.home.petperks.bosspets.DagPrime.sendPetDagannothPrime;
import static io.ruin.model.activities.home.petperks.bosspets.DagRex.sendPetDagannothRex;
import static io.ruin.model.activities.home.petperks.bosspets.DagSupreme.sendPetDagannothSupreme;
import static io.ruin.model.activities.home.petperks.bosspets.DarkCore.sendPetDarkCore;
import static io.ruin.model.activities.home.petperks.bosspets.GeneralGraardor.sendPetGeneralGraardor;
import static io.ruin.model.activities.home.petperks.bosspets.HellPuppy.sendHellpuppy;
import static io.ruin.model.activities.home.petperks.bosspets.IkkleHydra.sendIkkleHydra;
import static io.ruin.model.activities.home.petperks.bosspets.KalphitePrincess.sendKalphitePrincess;
import static io.ruin.model.activities.home.petperks.bosspets.Kraken.sendPetKraken;
import static io.ruin.model.activities.home.petperks.bosspets.KreeArra.sendPetKreeArra;
import static io.ruin.model.activities.home.petperks.bosspets.KrilTsutsaroth.sendPetKrilTsutsaroth;
import static io.ruin.model.activities.home.petperks.bosspets.Midnight.sendMidnight;
import static io.ruin.model.activities.home.petperks.bosspets.Noon.sendNoon;
import static io.ruin.model.activities.home.petperks.bosspets.PrinceBlackDragon.sendPrinceBlackDragon;
import static io.ruin.model.activities.home.petperks.bosspets.ScorpiasOffspring.sendScorpiasOffspring;
import static io.ruin.model.activities.home.petperks.bosspets.Skotos.sendSkotos;
import static io.ruin.model.activities.home.petperks.bosspets.SmokeDevil.sendPetSmokeDevil;
import static io.ruin.model.activities.home.petperks.bosspets.Sraracha.sendSraracha;
import static io.ruin.model.activities.home.petperks.bosspets.VenenatisSpiderling.sendVenenatisSpirderling;
import static io.ruin.model.activities.home.petperks.bosspets.VetionJr.sendVetionJr;
import static io.ruin.model.activities.home.petperks.bosspets.Vorki.sendVorki;
import static io.ruin.model.activities.home.petperks.bosspets.ZulrahSnakelings.sendPetSnakeling;
import static io.ruin.model.activities.home.petperks.minigamespets.JalNibRek.sendJalNibRek;
import static io.ruin.model.activities.home.petperks.minigamespets.TzrekJad.sendTzrekJad;
import static io.ruin.model.activities.home.petperks.minigamespets.TzrekZuk.sendTzrekZuk;
import static io.ruin.model.activities.home.petperks.miscpets.BloodHound.sendBloodhound;
import static io.ruin.model.activities.home.petperks.miscpets.LavaDragon.sendBabyLavaDragon;
import static io.ruin.model.activities.home.petperks.miscpets.Smolcano.sendSmolcano;
import static io.ruin.model.activities.home.petperks.miscpets.Youngllef.sendYounllef;
import static io.ruin.model.activities.home.petperks.raidpets.LilZik.sendLilZik;
import static io.ruin.model.activities.home.petperks.raidpets.Olmlet.sendOlmlet;
import static io.ruin.model.activities.home.petperks.raidpets.Puppadile.sendPuppadile;
import static io.ruin.model.activities.home.petperks.raidpets.Tektiny.sendTektiny;
import static io.ruin.model.activities.home.petperks.raidpets.Vanguard.sendVanguard;
import static io.ruin.model.activities.home.petperks.raidpets.Vasa.sendVasa;
import static io.ruin.model.activities.home.petperks.raidpets.Vespine.sendVespina;
import static io.ruin.model.activities.home.petperks.skillingpets.BabyChinchompas.sendBabyChinchompa;
import static io.ruin.model.activities.home.petperks.skillingpets.Beaver.sendBeaver;
import static io.ruin.model.activities.home.petperks.skillingpets.Herbi.sendHerbi;
import static io.ruin.model.activities.home.petperks.skillingpets.Heron.sendHeron;
import static io.ruin.model.activities.home.petperks.skillingpets.RiftGuardians.sendRiftGuardian;
import static io.ruin.model.activities.home.petperks.skillingpets.RockGolem.sendRockGolem;
import static io.ruin.model.activities.home.petperks.skillingpets.Rocky.sendRocky;
import static io.ruin.model.activities.home.petperks.skillingpets.Tangleroot.sendTangleroot;

public class PetPerkInter {

    public static int INT = 974;

    public static void openInterface(Player p) {
        p.openInterface(InterfaceType.MAIN, INT);
        hideCollected(p);
        sendInfoToInter(p);
    }
    //Hiding the green checks until they have collected the pet
    public static void hideCollected(Player p) {
        p.getPacketSender().setHidden(INT, 80, true);
        p.getPacketSender().setHidden(INT, 90, true);
        p.getPacketSender().setHidden(INT, 100, true);
        p.getPacketSender().setHidden(INT, 110, true);
        p.getPacketSender().setHidden(INT, 120, true);
        p.getPacketSender().setHidden(INT, 130, true);
        p.getPacketSender().setHidden(INT, 142, true);
        p.getPacketSender().setHidden(INT, 152, true);
        p.getPacketSender().setHidden(INT, 162, true);
        p.getPacketSender().setHidden(INT, 172, true);
        p.getPacketSender().setHidden(INT, 182, true);
        p.getPacketSender().setHidden(INT, 192, true);
        p.getPacketSender().setHidden(INT, 202, true);
        p.getPacketSender().setHidden(INT, 212, true);
        p.getPacketSender().setHidden(INT, 222, true);
        p.getPacketSender().setHidden(INT, 232, true);
        p.getPacketSender().setHidden(INT, 242, true);
        p.getPacketSender().setHidden(INT, 252, true);
        p.getPacketSender().setHidden(INT, 262, true);
        p.getPacketSender().setHidden(INT, 272, true);
        p.getPacketSender().setHidden(INT, 282, true);
        p.getPacketSender().setHidden(INT, 292, true);
        p.getPacketSender().setHidden(INT, 302, true);
        p.getPacketSender().setHidden(INT, 312, true);
        p.getPacketSender().setHidden(INT, 322, true);
        p.getPacketSender().setHidden(INT, 332, true);
        p.getPacketSender().setHidden(INT, 342, true);
        p.getPacketSender().setHidden(INT, 352, true);
        p.getPacketSender().setHidden(INT, 362, true);
        p.getPacketSender().setHidden(INT, 372, true);
        p.getPacketSender().setHidden(INT, 382, true);
        p.getPacketSender().setHidden(INT, 392, true);
        p.getPacketSender().setHidden(INT, 402, true);
        p.getPacketSender().setHidden(INT, 412, true);
        p.getPacketSender().setHidden(INT, 422, true);
        p.getPacketSender().setHidden(INT, 432, true);
        p.getPacketSender().setHidden(INT, 442, true);
        p.getPacketSender().setHidden(INT, 452, true);
        p.getPacketSender().setHidden(INT, 462, true);
        p.getPacketSender().setHidden(INT, 472, true);
        p.getPacketSender().setHidden(INT, 482, true);
        p.getPacketSender().setHidden(INT, 492, true);
        p.getPacketSender().setHidden(INT, 502, true);
        p.getPacketSender().setHidden(INT, 512, true);
        p.getPacketSender().setHidden(INT, 522, true);
        p.getPacketSender().setHidden(INT, 532, true);
        p.getPacketSender().setHidden(INT, 542, true);
        p.getPacketSender().setHidden(INT, 552, true);
        p.getPacketSender().setHidden(INT, 562, true);
        p.getPacketSender().setHidden(INT, 572, true);
    }
    //Sends the models to the interface
    public static void sendModel(Player p, int childId, int modelId, int zoom, int rotX, int rotY) {
        p.getPacketSender().sendModel(INT, childId, modelId);
        p.getPacketSender().sendModelInformation(INT, childId, zoom, rotX, rotY);
    }
    //Sending each container to the interface
    public static void sendInfoToInter(Player p) {
        sendAbyssalOrphan(p);
        sendBabyChinchompa(p);
        sendBabyLavaDragon(p);
        sendBabyMole(p);
        sendBeaver(p);
        sendBloodhound(p);
        sendCallistoCub(p);
        sendCorporealCritter(p);
        sendHellpuppy(p);
        sendHerbi(p);
        sendHeron(p);
        sendIkkleHydra(p);
        sendJalNibRek(p);
        sendKalphitePrincess(p);
        sendLilZik(p);
        sendMidnight(p);
        sendNoon(p);
        sendOlmlet(p);
        sendPetChaosElemental(p);
        sendPetCommanderZilyana(p);
        sendPetDagannothPrime(p);
        sendPetDagannothRex(p);
        sendPetDagannothSupreme(p);
        sendPetDarkCore(p);
        sendPetGeneralGraardor(p);
        sendPetKraken(p);
        sendPetKreeArra(p);
        sendPetKrilTsutsaroth(p);
        sendPetSmokeDevil(p);
        sendPetSnakeling(p);
        sendPrinceBlackDragon(p);
        sendPuppadile(p);
        sendRiftGuardian(p);
        sendRockGolem(p);
        sendRocky(p);
        sendScorpiasOffspring(p);
        sendSkotos(p);
        sendSmolcano(p);
        sendSraracha(p);
        sendTangleroot(p);
        sendTektiny(p);
        sendVanguard(p);
        sendVasa(p);
        sendVespina(p);
        sendVenenatisSpirderling(p);
        sendVetionJr(p);
        sendVorki(p);
        sendYounllef(p);
        sendTzrekJad(p);
        sendTzrekZuk(p);

        //Component 263

    }

}