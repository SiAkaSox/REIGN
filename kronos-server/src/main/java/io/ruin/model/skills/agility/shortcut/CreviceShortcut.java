package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.skills.slayer.SlayerTask;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CreviceShortcut {

    ZANARIS(66, 1, Position.of(2409, 4402), Position.of(2409, 4400)),
    FALADOR(1, 1, Position.of(3028, 9806), Position.of(3035, 9806)),
    BRIMHAVEN(83, 100, Position.of(2697, 9436), Position.of(2684, 9436)),
    SDZ(50, 1, Position.of(1967, 8986, 1), Position.of(1969, 8986, 1));
    //1969 8986 1
    private int levelReq, xp;
    private Position startPosition, endPosition;

    public void squeeze(Player player, GameObject obj) {
        if (obj.id == 30198
            && Config.SLAYER_TASK.get(player) != 58 ||
                    Config.SLAYER_TASK.get(player) != 59 ||
                    Config.SLAYER_TASK.get(player) != 60) {
                player.dialogue(
                        new NPCDialogue(7667, "You haven't got an assignment to slay these dragons. Go and train somewhere else.")
                );
                return;
        }
        player.startEvent( e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(2594);
            //player.getAppearance().setCustomRenders(2594);
            Position target = player.getPosition().equals(startPosition) ? endPosition : startPosition;
            int distance = startPosition.distance(endPosition);
            player.stepAbs(target.getX(), target.getY(), StepType.FORCE_WALK);
            e.delay(distance + 1);

            player.animate(2595);
            if(World.isEco())
                player.getStats().addXp(StatType.Agility, xp, true);
            player.unlock();
        })
        ;}
}
