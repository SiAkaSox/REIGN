package io.ruin.model.activities.wilderness;

import io.ruin.model.entity.npc.actions.edgeville.Nurse;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

import java.time.Duration;

public class Ferox {

    public static void teleport(Player player, int x, int y, int z) {
        player.resetActions(true, true, true);
        player.lock(LockType.FULL_NULLIFY_DAMAGE);
        player.startEvent(e -> {
            player.getPacketSender().fadeOut();
            e.delay(1);
            player.getMovement().teleport(x, y, z);
            player.getPacketSender().clearFade();
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

    private static void teleportPlayer(Player player, int x, int y) {
        if(player.getCombat().checkTb())
            return;
        player.startEvent(event -> {
            player.lock();
            player.animate(4282);
            event.delay(1);
            player.getMovement().teleport(x, y);
            event.delay(1);
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

    public static void usePool(Player player) {
        int poolTicks = player.poolTicks;
        long seconds = Math.round(poolTicks * 0.6);
        Duration duration = Duration.ofSeconds(seconds);
        String formattedTime = formatDuration(duration);
        if (player.poolTicks > 0) {
            player.dialogue(
                    new ItemDialogue().one(20643, "You must wait another <col=0040ff>"+formattedTime+" </col>before using the pool again.")
            );
            return;
        }
        player.animate(833);
        player.graphics(1039);
        player.getStats().restore(false);
        player.getMovement().restoreEnergy(100);
        Nurse.heal(player, null);
        player.cureVenom(0);
        player.poolTicks = resetPoolTicks(player);
        if (player.storeAmountSpent >= 50 && player.storeAmountSpent < 250) {
            player.getCombat().restore();
            player.sendMessage("You drink from the pool and restore your stats, run energy, special attack, and cure poisons.");
        }
        if (player.storeAmountSpent >= 250) {
            player.getCombat().restore();
            player.getStats().get(StatType.Attack).boost(5, 0.15);
            player.getStats().get(StatType.Strength).boost(5, 0.15);
            player.getStats().get(StatType.Defence).boost(5, 0.15);
            player.getStats().get(StatType.Ranged).boost(4, 0.10);
            player.getStats().get(StatType.Magic).boost(5, 0.0);
            player.sendMessage("You drink from the pool and restore your stats, run energy, special attack, and cure poisons.");
            player.sendMessage("Your donator rank has also boosted your stats.");
        }
        if (player.storeAmountSpent < 50) {
            player.sendMessage("You drink from the pool and restore your stats, run energy, and cure poisons.");
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
            final int[] BARRIER = {39653, 39652};
            for (int barriers : BARRIER) {
                ObjectAction.register(barriers, "pass-through", (player, obj) -> {
                    int pY = player.getAbsY();
                    int pX = player.getAbsX();
                    int objY = obj.y;
                    int objX = obj.x;
                    int direction = obj.direction;
                    if (direction == 0)
                        teleportPlayer(player, pX >= objX ? objX - 1 : objX, objY);
                    else if (direction == 1)
                        teleportPlayer(player, objX, pY <= objY ? objY + 1 : objY);
                    else if (direction == 2)
                        teleportPlayer(player, pX <= objX ? objX + 1 : objX, objY);
                    else if (direction == 3)
                        teleportPlayer(player, objX, pY >= objY ? objY - 1 : objY);
                });
            }
            ObjectAction.register(30397, "pass-through", (player, obj) -> {
                    teleportPlayer(player, obj.x, player.getAbsY() > obj.y ? obj.y : obj.y + 1);
            });
            ObjectAction.register(39651, "Drink", ((player, obj) -> usePool(player)));
            ObjectAction.register(29064, "view", (player, obj) -> {
                player.getPacketSender().sendString(395, 3, "Wins: " + player.lmsWins);
                player.getPacketSender().sendString(395, 4, "Kills: " + player.lmsKills);
                player.getPacketSender().sendString(395, 5, "Games played: " + player.lmsGamesPlayed);
                player.openInterface(InterfaceType.CHATBOX, 395);
            });
            //Cave
            ObjectAction.register(39647, "walk-down", (player, obj) -> {
                teleport(player, 3164, 10043, 0);

            });
            ObjectAction.register(39648, "exit", (player, obj) -> {
                teleport(player, 3360, 3827, 0);
            });
            ObjectAction.register(30386, "enter", (player, obj) -> {
                player.sendMessage("You cannot currently use this.");
            });
            ObjectAction.register(26642, "enter", (player, obj) -> {
                player.sendMessage("You cannot currently use this.");
            });
            ObjectAction.register(39637, "enter", (player, obj) -> {
                player.sendMessage("You cannot currently use this.");
            });
            ObjectAction.register(29087, "use", (player, obj) -> {
                player.sendMessage("You cannot currently use this.");
            });
            ObjectAction.register(39638, "chop-down", (player, obj) -> {
                player.sendMessage("You cannot currently use this.");
            });
    }
}
