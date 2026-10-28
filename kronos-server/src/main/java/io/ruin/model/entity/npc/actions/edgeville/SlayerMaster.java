//package io.ruin.model.entity.npc.actions.edgeville;
//
//import io.ruin.cache.Color;
//import io.ruin.model.entity.npc.NPC;
//import io.ruin.model.entity.npc.NPCAction;
//import io.ruin.model.entity.player.Player;
//import io.ruin.model.inter.dialogue.ActionDialogue;
//import io.ruin.model.inter.dialogue.NPCDialogue;
//import io.ruin.model.inter.dialogue.OptionsDialogue;
//import io.ruin.model.inter.dialogue.PlayerDialogue;
//import io.ruin.model.inter.utils.Option;
//import io.ruin.model.shop.ShopManager;
//import io.ruin.model.skills.slayer.Slayer;
//import io.ruin.model.skills.slayer.SlayerTask;
//import io.ruin.model.skills.slayer.SlayerUnlock;
//import io.ruin.model.stat.StatType;
//
//import static io.ruin.cache.NpcID.NIEVE;
//import static io.ruin.model.skills.slayer.Slayer.reset;
//import static io.ruin.model.skills.slayer.Slayer.set;
//
//public class SlayerMaster {
//
//    public static final int[] IDS = {6797, 8623, 401, 6798, 405};
//
//    public static final int KRYSTILIA = 7663;
//
//    static {
//        NPCAction.register(KRYSTILIA, "Talk-to", (player, npc) -> player.dialogue(
//                new NPCDialogue(KRYSTILIA, "Hello, adventurer. I am only able to assign you slayer tasks inside of" +
//                        " the wilderness, however, all of my tasks are assigned regardless of your combat level. How can I" +
//                        " assist you?"),
//                new OptionsDialogue(
//                        player.slayerTask == null ?
//                                new Option("I want a Slayer assignment.", () -> {
//                                    player.dialogue(
//                                            new PlayerDialogue("I want a Slayer assignment."),
//                                            new ActionDialogue(() -> assignment(player, npc))
//                                    );
//                                })
//                                :
//                                new Option("My task is too difficult", () -> {
//                                    player.dialogue(
//                                            new PlayerDialogue("My task is too difficult.."),
//                                            new ActionDialogue(() -> assignment(player, npc))
//                                    );
//                                }),
//                        new Option("I wish to view your shop", () -> {
//                            player.dialogue(
//                                    new PlayerDialogue("I wish to view your shop!"),
//                                    new ActionDialogue(() -> ShopManager.openIfExists(player, "44f369bf-6369-48c5-9952-b9b50011e89b"))
//                            );
//                        }),
//                        new Option("View slayer rewards & unlocks", () -> {
//                            player.dialogue(
//                                    new PlayerDialogue("I would like to view slayer rewards & unlocks."),
//                                    new ActionDialogue(() -> rewards(player))
//                            );
//                        }),
//                        new Option("Nevermind", () ->
//                                player.dialogue(new PlayerDialogue("Oh, nevermind!").animate(588)))
//                )
//        ));
//        for(int ID : IDS) {
//            NPCAction.register(ID, "Talk-to", (player, npc) -> player.dialogue(
//                    new NPCDialogue(npc, "Hello, adventurer. I am the only non-wilderness slayer master available," +
//                            " however, I can assign you tasks that are suitable to your slayer level, as well as offer" +
//                            " you access to the plethora of rewards in my shop. How can I assist you?"),
//                    new OptionsDialogue(
//                            player.slayerTask == null ?
//                                    new Option("I want a Slayer assignment.", () -> {
//                                        player.dialogue(
//                                                new PlayerDialogue("I want a Slayer assignment."),
//                                                new ActionDialogue(() -> assignment(player, npc))
//                                        );
//                                    })
//                                    :
//                                    new Option("My task is too difficult", () -> {
//                                        player.dialogue(
//                                                new PlayerDialogue("My task is too difficult.."),
//                                                new ActionDialogue(() -> assignment(player, npc))
//                                        );
//                                    }),
//                            new Option("I wish to view your shop", () -> {
//                                player.dialogue(
//                                        new PlayerDialogue("I wish to view your shop!"),
//                                        new ActionDialogue(() -> ShopManager.openIfExists(player, "44f369bf-6369-48c5-9952-b9b50011e89b"))
//                                );
//                            }),
//                            new Option("View slayer rewards & unlocks", () -> {
//                                player.dialogue(
//                                        new PlayerDialogue("I would like to view slayer rewards & unlocks."),
//                                        new ActionDialogue(() -> rewards(player))
//                                );
//                            }),
//                            new Option("Nevermind", () ->
//                                    player.dialogue(new PlayerDialogue("Oh, nevermind!").animate(588)))
//                    )
//            ));
//            NPCAction.register(KRYSTILIA, "Assignment", SlayerMaster::assignment);
//            NPCAction.register(KRYSTILIA, "Trade", (player, npc) -> tradeDialogue(player));
//            NPCAction.register(KRYSTILIA, "Rewards", (player, npc) -> rewards(player));
//            NPCAction.register(ID, "Assignment", SlayerMaster::assignment);
//            NPCAction.register(ID, "Trade", (player, npc) -> tradeDialogue(player));
//            NPCAction.register(ID, "Rewards", (player, npc) -> rewards(player));
//        }
//    }
//
//    public static void tradeDialogue(Player p) {
//        p.dialogue(
//                new OptionsDialogue("What would you like to do?",
//                        new Option("Open Slayer Rewards Shop", () -> ShopManager.openIfExists(p, "06bf1d5a-e4d8-49f5-9aac-6223a965b98a")),
//                        new Option("Open Slayer Equipment Shop", () -> ShopManager.openIfExists(p, "16bf1d5b-e4d8-49f5-9aac-6229a965b98a"))
//                )
//        );
//    }
//
//    /**
//     * Assignment
//     */
//
//    private static void assignment(Player p, NPC npc) {
//        if (npc.getId() == NIEVE) {
//        if (p.slayerTaskRemaining > 0) {
//            p.dialogue(new NPCDialogue(npc, "Would you like me to reset your current task? You will be assigned an " + Color.CRIMSON.wrap("easy") + " task."),
//                    new OptionsDialogue(
//                            new Option("Yes please.", () -> p.dialogue(
//                                    new PlayerDialogue("Yes please."),
//                                    new ActionDialogue(() -> {
//                                        reset(p);
//                                        set(p, SlayerTask.Type.EASY);
//                                        p.dialogue(new NPCDialogue(npc, "Your new task is to kill " + p.slayerTaskRemaining + " " + p.slayerTaskName + ".<br>Good luck!").lineHeight(28));
//                                    }))),
//                            new Option("No, thanks.")
//                    )
//            );
//        } else {
//            if (p.getStats().get(StatType.Slayer).currentLevel <= 30 && Slayer.getTask(p) == null) {
//                assign(p, npc, SlayerTask.Type.EASY);
//            }
//            if (p.getStats().get(StatType.Slayer).currentLevel > 30 && p.getStats().get(StatType.Slayer).currentLevel <= 60
//                    && Slayer.getTask(p) == null) {
//                assign(p, npc, SlayerTask.Type.MEDIUM);
//            }
//            if (p.getStats().get(StatType.Slayer).currentLevel > 60 && Slayer.getTask(p) == null) {
//                assign(p, npc, SlayerTask.Type.HARD);
//            }
//        }
//    }
//
////        if (npc.getId() == TURAEL) {
////            if (p.slayerTaskRemaining > 0) {
////                p.dialogue(new NPCDialogue(npc, "Would you like me to reset your current task? You will be assigned an " + Color.CRIMSON.wrap("easy") + " task."),
////                        new OptionsDialogue(
////                                new Option("Yes please.", () -> p.dialogue(
////                                        new PlayerDialogue("Yes please."),
////                                        new ActionDialogue(() -> {
////                                            reset(p);
////                                            set(p, SlayerTask.Type.EASY);
////                                            p.dialogue(new NPCDialogue(npc, "Your new task is to kill " + p.slayerTaskRemaining + " " + p.slayerTaskName + ".<br>Good luck!").lineHeight(28));
////                                        }))),
////                                new Option("No, thanks.")
////                        )
////                );
////            } else if (Slayer.getTask(p) == null) {
////                assign(p, npc, SlayerTask.Type.EASY);
////            }
////        }
////        if (npc.getId() == STEVE) {
////            if (p.slayerTaskRemaining > 0) {
////                p.dialogue(new NPCDialogue(npc, "Would you like me to reset your current task? You will be assigned an " + Color.CRIMSON.wrap("easy") + " task."),
////                        new OptionsDialogue(
////                                new Option("Yes please.", () -> p.dialogue(
////                                        new PlayerDialogue("Yes please."),
////                                        new ActionDialogue(() -> {
////                                            reset(p);
////                                            set(p, SlayerTask.Type.EASY);
////                                            p.dialogue(new NPCDialogue(npc, "Your new task is to kill " + p.slayerTaskRemaining + " " + p.slayerTaskName + ".<br>Good luck!").lineHeight(28));
////                                        }))),
////                                new Option("No, thanks.")
////                        )
////                );
////            } else if (Slayer.getTask(p) == null) {
////                if (Config.LIKE_A_BOSS.get(p) == 1) {
////                    new OptionsDialogue(
////                            new Option("I want a normal task.", () ->
////                                    assign(p, npc, SlayerTask.Type.MEDIUM)),
////                            new Option("I want a boss task.", () ->
////                                    assign(p, npc, SlayerTask.Type.BOSS))
////                    );
////                } else {
////                    assign(p, npc, SlayerTask.Type.MEDIUM);
////                }
////            }
////        }
////        if (npc.getId() == DURADEL) {
////            if (p.slayerTaskRemaining > 0) {
////                p.dialogue(new NPCDialogue(npc, "Would you like me to reset your current task? You will be assigned an " + Color.CRIMSON.wrap("easy") + " task."),
////                        new OptionsDialogue(
////                                new Option("Yes please.", () -> p.dialogue(
////                                        new PlayerDialogue("Yes please."),
////                                        new ActionDialogue(() -> {
////                                            reset(p);
////                                            set(p, SlayerTask.Type.EASY);
////                                            p.dialogue(new NPCDialogue(npc, "Your new task is to kill " + p.slayerTaskRemaining + " " + p.slayerTaskName + ".<br>Good luck!").lineHeight(28));
////                                        }))),
////                                new Option("No, thanks.")
////                        )
////                );
////            } else if (Slayer.getTask(p) == null) {
////                assign(p, npc, SlayerTask.Type.HARD);
////            }
////        }
//        if (npc.getId() == KRYSTILIA) {
//            if (p.slayerTaskRemaining > 0) {
//                p.dialogue(new NPCDialogue(npc, "Would you like me to reset your current task? You will be assigned an " + Color.CRIMSON.wrap("easy") + " task."),
//                        new OptionsDialogue(
//                                new Option("Yes please.", () -> p.dialogue(
//                                        new PlayerDialogue("Yes please."),
//                                        new ActionDialogue(() -> {
//                                            reset(p);
//                                            set(p, SlayerTask.Type.EASY);
//                                            p.dialogue(new NPCDialogue(npc, "Your new task is to kill " + p.slayerTaskRemaining + " " + p.slayerTaskName + ".<br>Good luck!").lineHeight(28));
//                                        }))),
//                                new Option("No, thanks.")
//                        )
//                );
//            } else if (Slayer.getTask(p) == null) {
//                assign(p, npc, SlayerTask.Type.WILDERNESS);
//            }
//        }
//        if (p.slayerTaskRemaining == -1) {
//            requestAmount(p, npc);
//            return;
//        }
////        if(Slayer.getTask(p) == null) {
////            if (PlayerCounter.SLAYER_TASKS_COMPLETED.get(p) == 0) {
////                set(p, SlayerTask.Type.EASY);
////                p.dialogue(new NPCDialogue(npc, "Your new task is to kill " + p.slayerTaskRemaining + " " + p.slayerTaskName + ".<br>Good luck!").lineHeight(28));
////                return;
////            }
////            return;
////        }
//        //p.dialogue(new NPCDialogue(npc, "You're currently assigned to kill " + p.slayerTaskName + "; only " + p.slayerTaskRemaining + " more to go.")/* Your reward point tally is " + tally + ".")*/.lineHeight(28));
//    }
//
//    private static void assign(Player player, NPC npc, SlayerTask.Type type) {
//        if (type == SlayerTask.Type.BOSS) {
//            requestAmount(player, npc);
//        }
//        if(player.getCombat().getLevel() < type.minimumCombatLevel) {
//            player.dialogue(
//                    new NPCDialogue(npc, "You must have combat level of " + type.minimumCombatLevel + " or higher for this type of task.").lineHeight(28)
//            );
//        }
//        set(player, type);
//        player.dialogue(
//            new NPCDialogue(npc, "Your new task is to kill " + player.slayerTaskRemaining + " " + player.slayerTaskName + ".<br>Good luck!").lineHeight(28)
//        );
//    }
//
//    private static Option assign(String message, Player player, NPC npc, SlayerTask.Type type) {
//        return new Option(message, () -> {
//           set(player, type);
//           if(player.slayerTaskRemaining == -1) {
//               player.dialogue(
//                       new PlayerDialogue(message),
//                       new ActionDialogue(() -> requestAmount(player, npc))
//               );
//           } else {
//               player.dialogue(
//                       new PlayerDialogue(message),
//                       new NPCDialogue(npc, "Your new task is to kill " + player.slayerTaskRemaining + " " + player.slayerTaskName + ".<br>Good luck!").lineHeight(28)
//               );
//           }
//        });
//    }
//
//    private static void requestAmount(Player player, NPC npc) {
//        SlayerTask task = Slayer.getTask(player);
//        if(task == null) { //literally should NEVER happen lol.
//            reset(player);
//            player.dialogue(new NPCDialogue(npc, "Oh my!"));
//        } else {
//            player.dialogue(
//                    new NPCDialogue(npc, "Your new task is to kill " + player.slayerTaskName + ". How many would you like to slay?").lineHeight(28),
//                    new ActionDialogue(() -> {
//                        player.closeDialogue();
//                        player.integerInput("Enter amount: (" + task.min + "-" + task.max + ")", amt -> {
//                            if(amt < task.min || amt > task.max) {
//                                player.retryIntegerInput("Invalid amount, try again: (" + task.min + "-" + task.max + ")");
//                                return;
//                            }
//                            set(player, SlayerTask.Type.BOSS);
//                            player.slayerTaskRemaining = amt;
//                            player.dialogue(new NPCDialogue(npc, "Your new task is to kill " + player.slayerTaskRemaining + " " + player.slayerTaskName + ".<br>Good luck!").lineHeight(28));
//                        });
//                    })
//            );
//        }
//    }
//
//    /**
//     * Rewards
//     */
//
//    private static void rewards(Player player) {
//        SlayerUnlock.openRewards(player);
//    }
//
//}