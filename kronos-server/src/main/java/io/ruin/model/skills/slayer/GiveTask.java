package io.ruin.model.skills.slayer;

import io.ruin.utility.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.stat.StatType;
import kotlin.Pair;

import static io.ruin.model.skills.slayer.master.Duradel.DURADEL;
import static io.ruin.model.skills.slayer.master.Konar.KONAR;

public class GiveTask {

    public static void giveTask(Player player, int master, int masterId, int combatLevel) {
        if ((master == DURADEL && player.getStats().get(StatType.Slayer).fixedLevel < 50) && player.getCombat().getLevel() < combatLevel) {
            player.dialogue(new NPCDialogue(master, "Sorry, but you're not strong enough to be taught by<br>me. Your best trainer would be " + SlayerMaster.bestMaster(player) + "."));
            return;
        }
        int left = Config.SLAYER_TASK_AMOUNT.get(player);

        if (left > 0) {
            String text = SlayerMaster.getTaskText(player, left);
            player.dialogue(new NPCDialogue(master, text));
            return;
        }

//        if(player.getRelicsHandler().hasRelic(Relics.DESTINY_IS_MINE)) {
//            player.getChooseSlayerTask().openInterface(master, masterId);
//            return;
//        } else {
        assignTask(player, master, masterId);
//        }

        SlayerCreature task = SlayerCreature.lookup(Config.SLAYER_TASK.get(player));
        left = Config.SLAYER_TASK_AMOUNT.get(player);

        if (task.equals(SlayerCreature.BOSSES)) {

            player.dialogue(
                    new NPCDialogue(master, "Your new task is to kill " + SlayerUnlock.taskName(player, task.getUid()) +
                            " in the wilderness. How many would you like to slay?").action(() -> {
                        player.integerInput("How many would you like to slay? (3-35)", (i) -> {
                            if (i < 3)
                                i = 3;

                            if (i > 35)
                                i = 35;

                            Config.SLAYER_TASK_AMOUNT.set(player, i);
                            player.slayerTaskRemaining = i;

                            player.dialogue(
                                    new NPCDialogue(master, "Excellent. You're now assigned to kill " + SlayerUnlock.taskName(player, task.getUid()) + " boss " + i + " times in the wilderness."),
                                    new OptionsDialogue(new Option("Got any tips for me?", () -> player.dialogue(
                                            new PlayerDialogue("Got any tips for me?"),
                                            new NPCDialogue(master, SlayerUnlock.tipForWilderness(task)),
                                            new PlayerDialogue("Great, thanks!"))), new Option("Great, thanks!", () -> {
                                        player.dialogue(new PlayerDialogue("Okay, great!"));
                                    }))
                            );
                        });
                    }));
            if (Config.SLAYER_TASK_AMOUNT.get(player) <= 0) {
                int value = Random.get(3, 35);
                Config.SLAYER_TASK_AMOUNT.set(player, value);

                player.slayerTaskRemaining = value;
            }



        } else {
            player.dialogue(
                    new NPCDialogue(master, "Excellent, you're doing great. Your new task is to kill " + left + " " + SlayerUnlock.taskName(player, task.getUid()) + " in the wilderness."),
                    new OptionsDialogue(new Option("Got any tips for me?", () -> player.dialogue(
                            new PlayerDialogue("Got any tips for me?"),
                            new NPCDialogue(master, SlayerUnlock.tipForWilderness(task)),
                            new PlayerDialogue("Great, thanks!"))), new Option("Great, thanks!", () -> {
                        player.dialogue(new PlayerDialogue("Okay, great!"));
                    }))
            );
        }
    }

    public static void assignTask(Player player, int master, int masterId) {
        SlayerMaster slayerMaster = SlayerMaster.master(master);

        if(slayerMaster == null)
            return;

        Config.SLAYER_MASTER.set(player, masterId);
        SlayerTask def = slayerMaster.randomTask(player);
        int taskId = def.getTaskId();
        Config.SLAYER_TASK.set(player, taskId);

        if (master == KONAR) {
            KonarData.assignLocation(player, taskId);
        }

        int min = def.getMinAmount();
        int max = def.getMaxAmount();

        for(Pair<Integer, Config> creature : SlayerUnlock.multipliable) {
            if(creature.getFirst() == taskId){
                if(creature.getSecond().get(player) != 0) {
                    min = (int) (def.getMinAmount() * 1.75);
                    max = (int) (def.getMaxAmount() * 1.75);

                    break;
                }
            }
        }

        int task_amt = Random.get(min, max);

        Config.SLAYER_TASK_AMOUNT.set(player, task_amt);
        player.slayerTaskRemaining = task_amt;
    }

}
