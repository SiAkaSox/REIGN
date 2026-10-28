package io.ruin.model.activities.dailytasks;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

import java.util.function.Predicate;

public enum NPCKillTaskListener implements TaskListener {
    ZULRAH(n -> n.getDef().name.equalsIgnoreCase("Zulrah")),
    ABYSSAL_SIRE(n -> n.getDef().name.equalsIgnoreCase("Abyssal Sire")),
    KRAKEN(494),
    DAGANNOTH_KINGS(n -> n.getId() >= 2265 && n.getId() <= 2267),
    GODWARS_GENERALS(n -> n.getId() == 2205 || n.getId() == 2215 || n.getId() == 3162 || n.getId() == 3129),
    COMMANDER_ZILYANA(2205),
    GENERAL_GRAARDOR(2215),
    KREE_ARRA(3162),
    KRIL_TSUTSAROTH(3129),
    KALPHITE_QUEEN(n -> n.getDef().name.equalsIgnoreCase("Kalphite Queen")),
    THERMONUCLEAR_SMOKE_DEVIL(499),
    LIZARDMAN_SHAMAN(n -> n.getDef().name.equalsIgnoreCase("Lizardman Shaman")),
    CERBERUS(n -> n.getDef().name.equalsIgnoreCase("Cerberus")),
    BARROWS_BROTHERS(n -> n.getId() == 1672 || n.getId() == 1677 || n.getId() == 1675 || n.getId() == 1673 || n.getId() == 1674 || n.getId() == 1676),
    CALLISTO(6609),
    VETION(n -> n.getDef().name.contains("Vet'ion")),
    VENENATIS(6610),
    SCORPIA(6615),
    REVENANTS(n -> n.getId() == 7931 || n.getId() == 7881 || n.getId() == 7934 || n.getId() == 7936 || n.getId() == 7935 || n.getId() == 7937 || n.getId() == 7932 || n.getId() == 7939 || n.getId() == 7940 || n.getId() == 7938 || n.getId() == 9047 || n.getId() == 9045 || n.getId() == 9041 || n.getId() == 9048 || n.getId() == 9043),


    //Combat Task List
    ABERRANT_SPECTRE(2),
    BLACK_DRAGON(252),
    BLOODVELD(484),
    YAK(5816),
    ENT(6594),
    ELDER_CHAOS(6607),
    SAND_CRABS(n -> n.getId() == 7206 || n.getId() == 5935),
    ROCK_CRABS(n -> n.getId() == 102 || n.getId() == 100),
    BRYOPHYTA(8195),
    DERANGED_ARCHAEOLOGIST(7806),
    FIRE_GIANT(2075),
    GREATER_DEMON(n -> n.getDef().name.equalsIgnoreCase("Greater Demon")),
    HELLHOUND(104),
    OBOR(7416),
    KBD(239),
    GIANT_MOLE(5779),
    SARACHNIS(8713),
    WYRM(8611),
    ;

    NPCKillTaskListener(int npcId) {
        this(n -> n.getId() == npcId);
    }

    NPCKillTaskListener(Predicate<NPC> isTaskNPC) {
        this.isTaskNPC = isTaskNPC;
    }

    private Predicate<NPC> isTaskNPC;

    @Override
    public int onPlayerKill(Player player, Player killed) {
        return 0;
    }

    @Override
    public int onNPCKill(Player player, NPC killed) {
        return isTaskNPC.test(killed) ? 1 : 0;
    }

}
