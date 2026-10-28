package io.ruin.model.map.object.actions.impl.edgeville;

import io.ruin.cache.ObjectDef;
import io.ruin.model.activities.wilderness.Ferox;
import io.ruin.model.entity.npc.actions.edgeville.Nurse;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

import java.time.Duration;

public class RejuvenationPool {

    private static void drink(Player player, GameObject obj) {
        int poolTicks = player.poolTicks;
        long seconds = Math.round(poolTicks * 0.6);
        Duration duration = Duration.ofSeconds(seconds);
        String formattedTime = formatDuration(duration);

        //boolean delayCheck = obj.id != 40004;
        if (player.poolTicks > 0) {
            player.dialogue(
                    new ItemDialogue().one(20643, "You must wait another <col=0040ff>"+formattedTime+" </col>before using the pool again.")
            );
            return;
        }
//        if(delayCheck && System.currentTimeMillis() - player.rejuvenationPool < 1000 * 60) {
//            player.dialogue(new MessageDialogue("You can only drink from the " + ObjectDef.get(obj.id).name + " once every minute."));
//            return;
//        }
        player.startEvent(event -> {
            player.lock();
            player.animate(833);
            player.resetFreeze();
            player.resetStun();
            player.getStats().restore(false);
            player.getMovement().restoreEnergy(100);
            player.cureVenom(0);
            if(player.isPoisoned()) {
                player.curePoison(0);
            }
            player.graphics(436, 48, 0);
            player.privateSound(958);
            Config.SPECIAL_ENERGY.set(player, 1000);
            player.poolTicks = resetPoolTicks(player);
            player.sendMessage("You drink from the pool and restore your stats, run energy, special attack, and cure poisons.");
            player.sendMessage("Pool ticks: "+player.poolTicks);
            //Nurse.heal(player, null);
            event.delay(1);
//            if(delayCheck)
//                player.rejuvenationPool = System.currentTimeMillis();
            player.unlock();
        });
    }

    private static String formatDuration(Duration duration) {
        long minutes = duration.toMinutes();
        long remainingSeconds = duration.minusMinutes(minutes).getSeconds();

        if (minutes > 0) {
            return String.format("%d minutes %d seconds", minutes, remainingSeconds);
        } else {
            return String.format("%d seconds", remainingSeconds);
        }
    }

    public static int resetPoolTicks(Player p) {
        if (p.storeAmountSpent >= 15 && p.storeAmountSpent < 500) {
            return 200;
        }
        if (p.storeAmountSpent >= 500) {
            return 0;
        }
        return 500;
    }

    static {
        ObjectAction.register(50004, "drink", RejuvenationPool::drink);
        ObjectAction.register(31380, "drink", RejuvenationPool::drink);
        ObjectAction.register(29241, "drink", RejuvenationPool::drink);
        ObjectAction.register(2654, "drink", RejuvenationPool::drink);
    }

}
