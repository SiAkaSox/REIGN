package io.ruin.model.activities.timerift;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

public interface TaskListener {

    int onNPCKill(Player player, NPC killed);
}
