package io.ruin.model.entity.npc.actions;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.map.Direction;

public class Durial321NPC extends NPC {

    public Durial321NPC(int id, Player target) {
        super(id);
        this.spawn(target.getAbsX(), target.getAbsY(), target.getHeight(), Direction.SOUTH, 1000);
        this.setCombat();
        this.getCombat().setTarget(target);
        this.targetPlayer(target, false);
        this.walkRange = 1000;
    }

    private static int ID = 11480;

    static {
        NPCAction.register(ID, "Follow", (player, npc) -> {
            player.face(npc);//TODO
           // player.getMovement().following = npc;
        });
        NPCAction.register(ID, "Trade", (player, npc) -> player.getPacketSender().sendMessage("Sending trade offer...", null, 102));
    }
}
