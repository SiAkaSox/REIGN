package io.ruin.model.entity.npc.actions.guild.warrior;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.stat.StatType;

public class Ghommal {

    static {
        NPCAction.register(2457, "talk-to", (p, npc) -> {
            p.dialogue(
                    new NPCDialogue(2457, "Can I help you?"),
                    new OptionsDialogue(
                            new Option("What are you doing here?", () -> whatareyoudoingHere(p)),
                            new Option("I'm here to talk about Combat Achievements.", () -> askaboutcombatTasks(p)),
                            new Option("No, I'm alright thanks.")
                    )
            );
        });
    }

    public static void whatareyoudoingHere(Player p) {
        p.dialogue(
                new PlayerDialogue("What are you doing here?"),
                new NPCDialogue(2457, "This here is the Warrior's Guild, a place where the strong may train. I'm the one who decides if you are allowed in or not."),
                new PlayerDialogue("Am I qualified to enter?"),
                new ActionDialogue(() -> statChecker(p))
        );
    }
    public static void statChecker(Player p) {
        int combinedLevel = p.getStats().get(StatType.Attack).fixedLevel + p.getStats().get(StatType.Strength).fixedLevel;
        if (combinedLevel < 130) {
            p.dialogue(
                    new NPCDialogue(2457, "You may not enter the Warrior's Guild as you are right now."),
                    new PlayerDialogue("What? But I'm a warrior!").animate(571),
                    new NPCDialogue(2457, "While you may consider yourself a warrior, I do not."),
                    new PlayerDialogue("Go on, let me in, you know you want to. I could...<br>make it worth your while...").animate(593),
                    new NPCDialogue(2457, "Go away and train small one. I do not take bribes.").animate(615),
                    new PlayerDialogue("Why not?"),
                    new NPCDialogue(2457, "The Warrior's Guild has a code of honour, a code that I respect. Move along.").animate(589)
            );
        } else {
            p.dialogue(
                    new NPCDialogue(2457, "Yes you are. Welcome to the Warrior's Guild!"),
                    new PlayerDialogue("Thank you!")
            );
        }
    }

    public static void askaboutcombatTasks(Player p) {
        p.dialogue(
                new PlayerDialogue("I'm here to talk about Combat Achievements."),
                new OptionsDialogue(
                        new Option("Could you explain what they are?", () -> explainCombatTasks(p)),
                        new Option("I have to go.")
                )
        );
    }
    public static void explainCombatTasks(Player p) {
        p.dialogue(
                new PlayerDialogue("Could you explain what they are?"),
                new NPCDialogue(2457, "Combat Achievements are a set of tasks for all adventurers to try and accomplish. They range from simple missions to kill various creatures, to challenges that will push you to your limits."),
                new NPCDialogue(2457, "The purpose of these tasks is for you to grow and improve, experience all that Gielinor has to offer, and become stronger in the process."),
                new NPCDialogue(2457, "Is this something that sounds interesting to you?"),
                new OptionsDialogue(
                        new Option("Yes, tell me about Combat Achievements.", () -> explaincombattasksFurther(p)),
                        new Option("Not at the moment, no.")
                )
        );
    }
    public static void explaincombattasksFurther(Player p) {
        p.dialogue(
                new PlayerDialogue("Yes, tell me about Combat Achievements."),
                new NPCDialogue(2547,"Combat Achievements are broken down into six tiers: Easy, Medium, Hard, Elite, Master and Grandmaster."),
                new NPCDialogue(2547, "Each of these tiers is progressively harder than the last, and to earn any rewards from the latter tiers, the prior tiers must be completed."),
                new NPCDialogue(2547, "To complete a tier you will need to conquer every task within it."),
                new NPCDialogue(2547, "I'd recommend you go kill some monsters. Come back to me when you've completed a tier or two. I'll reward you for your efforts."),
                new PlayerDialogue("Thanks a lot, I'll get going.")
        );
    }

    public static boolean claimedEasy;
    public static boolean claimedMedium;
    public static boolean claimedHard;
    public static boolean claimedElite;
    public static boolean claimedMaster;
    public static boolean claimedGrandmaster;

    public static boolean dhcblackunlocked = false;
    public static boolean dhcturquoiseunlocked = false;
    public static boolean tztokhelmunlocked = false;
    public static boolean vampyrichelmunlocked = false;
    public static boolean tzkalhelmunlocked = false;

    public static void rightclickRewards(Player p) {
        if (!claimedEasy && p.combatTaskEasyCompleted < 27) {
            p.dialogue(
                    new MessageDialogue("You must complete all Easy tasks before claiming a reward.")
            );
        }
        if (claimedEasy && p.combatTaskMediumCompleted < 34) {
            p.dialogue(
                    new MessageDialogue("You must complete all tasks of your current tier to claim the rewards.")
            );
        }
        if (claimedEasy && claimedMedium && p.combatTaskHardCompleted < 45) {
            p.dialogue(
                    new MessageDialogue("You must complete all tasks of your current tier to claim the rewards.")
            );
        }
        if (claimedEasy && claimedMedium && claimedHard && p.combatTaskEliteCompleted < 64) {
            p.dialogue(
                    new MessageDialogue("You must complete all tasks of your current tier to claim the rewards.")
            );
        }
        if (claimedEasy && claimedMedium && claimedHard && claimedElite && p.combatTaskMasterCompleted < 38) {
            p.dialogue(
                    new MessageDialogue("You must complete all tasks of your current tier to claim the rewards.")
            );
        }
        if (claimedEasy && claimedMedium && claimedHard && claimedElite && claimedMaster && p.combatTaskGrandmasterCompleted < 34) {
            p.dialogue(
                    new MessageDialogue("You must complete all tasks of your current tier to claim the rewards.")
            );
        }
        if (!claimedEasy && p.combatTaskEasyCompleted == 27) {
            p.dialogue(
                    new PlayerDialogue("I think I've completed a combat tier!"),
                    new NPCDialogue(2547, "Oh, which tier have you done?"),
                    new PlayerDialogue("I've completed all the Easy Combat Achievement tasks!"),
                    new NPCDialogue(2547, "I can see that. Nice job. You'll be expecting some rewards?"),
                    new PlayerDialogue("Yes please!"),
                    new ActionDialogue(() -> {
                        giveeasyRewards(p);
                    })
            );
        }
        if (claimedEasy && p.combatTaskMediumCompleted == 34) {
            p.dialogue(
                    new PlayerDialogue("I think I've completed a combat tier!"),
                    new NPCDialogue(2547, "Oh, which tier have you done?"),
                    new PlayerDialogue("I've completed all the Medium Combat Achievement tasks!"),
                    new NPCDialogue(2547, "I can see that. Nice job. You'll be expecting some rewards?"),
                    new PlayerDialogue("Yes please!"),
                    new ActionDialogue(() -> {
                        givemediumRewards(p);
                    })
            );
        }
        if (claimedEasy && claimedMedium && p.combatTaskHardCompleted == 45) {
            p.dialogue(
                    new PlayerDialogue("I think I've completed a combat tier!"),
                    new NPCDialogue(2547, "Oh, which tier have you done?"),
                    new PlayerDialogue("I've completed all the Hard Combat Achievement tasks!"),
                    new NPCDialogue(2547, "I can see that. Nice job. You'll be expecting some rewards?"),
                    new PlayerDialogue("Yes please!"),
                    new ActionDialogue(() -> {
                        givehardRewards(p);
                    })
            );
        }
        if(claimedEasy && claimedMedium && claimedHard && p.combatTaskEliteCompleted == 64) {
            p.dialogue(
                    new PlayerDialogue("I think I've completed a combat tier!"),
                    new NPCDialogue(2547, "Oh, which tier have you done?"),
                    new PlayerDialogue("I've completed all the Elite Combat Achievement tasks!"),
                    new NPCDialogue(2547, "I can see that. Nice job. You'll be expecting some rewards?"),
                    new PlayerDialogue("Yes please!"),
                    new ActionDialogue(() -> {
                        giveeliteRewards(p);
                    })
            );
        }
        if (claimedEasy && claimedMedium && claimedHard && claimedElite && p.combatTaskMasterCompleted == 38) {
            p.dialogue(
                    new PlayerDialogue("I think I've completed a combat tier!"),
                    new NPCDialogue(2547, "Oh, which tier have you done?"),
                    new PlayerDialogue("I've completed all the Master Combat Achievement tasks!"),
                    new NPCDialogue(2547, "I can see that. Nice job. You'll be expecting some rewards?"),
                    new PlayerDialogue("Yes please!"),
                    new ActionDialogue(() -> {
                        givemasterRewards(p);
                    })
            );
        }
        if (claimedEasy && claimedMedium && claimedHard && claimedElite && claimedMaster && p.combatTaskGrandmasterCompleted == 34) {
            p.dialogue(
                    new PlayerDialogue("I think I've completed a combat tier!"),
                    new NPCDialogue(2547, "Oh, which tier have you done?"),
                    new PlayerDialogue("I've completed all the Grandmaster Combat Achievement tasks!"),
                    new NPCDialogue(2547, "I can see that. Nice job. You'll be expecting some rewards?"),
                    new PlayerDialogue("Yes please!"),
                    new ActionDialogue(() -> {
                        givegrandmasterRewards(p);
                    })
            );
        }
    }
    public static void giveeasyRewards(Player p) {
        p.getInventory().addOrDrop(25926, 1);
        p.getInventory().addOrDrop(2528, 1);
        p.conquestBonus += 2;
        claimedEasy = true;
    }
    public static void givemediumRewards(Player p) {
        p.getInventory().addOrDrop(25928, 1);
        p.getInventory().addOrDrop(2528, 2);
        p.conquestBonus += 3;
        claimedMedium = true;
    }
    public static void givehardRewards(Player p) {
        p.getInventory().addOrDrop(25930, 1);
        p.getInventory().addOrDrop(2528, 3);
        p.conquestBonus += 2;
        claimedHard = true;
        dhcblackunlocked = true;
    }
    public static void giveeliteRewards(Player p) {
        p.getInventory().addOrDrop(25932, 1);
        p.getInventory().addOrDrop(2528, 4);
        p.conquestBonus += 3;
        claimedElite = true;
        dhcturquoiseunlocked = true;
        tztokhelmunlocked = true;
    }
    public static void givemasterRewards(Player p) {
        p.getInventory().addOrDrop(25934, 1);
        p.getInventory().addOrDrop(2528, 5);
        p.conquestBonus += 5;
        claimedMaster = true;
        vampyrichelmunlocked = true;
    }
    public static void givegrandmasterRewards(Player p) {
        p.getInventory().addOrDrop(25936, 1);
        p.getInventory().addOrDrop(2528, 6);
        p.conquestBonus += 10;
        claimedGrandmaster = true;
        tzkalhelmunlocked = true;
    }
}
