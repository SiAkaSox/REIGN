package io.ruin.model.activities.timerift;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

import java.util.function.Predicate;

public enum NPCTaskListener implements TaskListener {
    NORTH_MOBS(n -> n.getId() == 6594 || n.getId() == 6604),
    SOUTH_MOBS(n -> n.getId() == 7934 || n.getId() == 7936 || n.getId() == 7937),
    EAST_MOBS(n -> n.getId() == 7657 || n.getId() == 7664),
    WEST_MOBS(n -> n.getId() == 7262 || n.getId() == 7259),

    MINI_BOSS_ONE(n -> n.getId() == 8633),
    MINI_BOSS_TWO(n -> n.getId() == 600),
    MINI_BOSS_THREE(n -> n.getId() == 5079),
    MINI_BOSS_FOUR(n -> n.getId() == 3616)
    ;

    NPCTaskListener(int npcId) { this(n -> n.getId() == npcId); }

    NPCTaskListener(Predicate<NPC> isTaskNPC) {
        this.isTaskNPC = isTaskNPC;
    }

    @Override
    public int onNPCKill(Player player, NPC killed) {
            return isTaskNPC.test(killed) ? 1 : 0;
    }

    private Predicate<NPC> isTaskNPC;
}