package io.ruin.model.inter.pvpinter;

import io.ruin.Server;
import io.ruin.model.activities.wilderness.pvp.rated.ones.Rated1v1Queue;
import io.ruin.model.activities.wilderness.pvp.unrated.Arena1v1Queue;
import io.ruin.model.activities.wilderness.pvp.unrated.BattleGroundsQueue;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;

import static io.ruin.Server.STARTUP_THREAD;

public class ArenasInter {

    public static int MAIN_INTER = 820;
    public static int UNRATED_ARENAS = 825;
    public static int UNRATED_BGS = 826;
    public static int RATED_INTER = 860;

    static {
        
            InterfaceHandler.register(MAIN_INTER, h -> {//Main inter
                h.actions[72] = (SlotAction) (player, slot) -> claimSeasonRewards(player);//Claim rewards
                h.actions[30] = (SlotAction) (player, slot) -> openUnratedArenasInter(player);//View arenas
                h.actions[38] = (SlotAction) (player, slot) -> openUnratedBattlegroundsInter(player);//View bg's
                h.actions[46] = (SlotAction) (player, slot) -> openRatedInter(player);//View rated
            });
            InterfaceHandler.register(RATED_INTER, h -> {//Rated inter
                h.actions[151] = (SlotAction) (player, slot) -> openInterface(player);//Go back
                h.actions[30] = (SlotAction) (player, slot) -> Rated1v1Queue.joinQueue(player);//Join 1v1
                h.actions[91] = (SlotAction) (player, slot) -> player.sendMessage("Cannot enter queue right now");//Enter queue
                h.actions[66] = (SlotAction) (player, slot) -> player.sendMessage("Rated 1v1 Coming soon!");//Choose 1v1
                h.actions[87] = (SlotAction) (player, slot) -> player.sendMessage("Rated 2v2 Coming soon!");//Choose 2v2
                h.actions[78] = (SlotAction) (player, slot) -> player.sendMessage("BG's Coming soon");//Choose bg's
            });
            InterfaceHandler.register(UNRATED_ARENAS, h -> {//Unrated Arenas
                h.actions[96] = (SlotAction) (player, slot) -> openInterface(player);//Go back
                h.actions[97] = (SlotAction) (player, slot) -> openInterface(player);//Go back
                h.actions[91] = (SlotAction) (player, slot) -> player.sendMessage("Cannot enter queue right now");//Enter queue
                h.actions[66] = (SlotAction) (player, slot) -> player.sendMessage("You have selected 2v2 Arenas");//Choose 1v1
                h.actions[87] = (SlotAction) (player, slot) -> player.sendMessage("You have selected 3v3 Arenas");//Choose 2v2
                h.actions[106] = (SlotAction) (player, slot) -> Arena1v1Queue.joinQueue(player);//unrated 1v1
                h.actions[143] = (SlotAction) (player, slot) -> player.sendMessage("Coming soon!");//Choose 3v3
            });
            InterfaceHandler.register(UNRATED_BGS, h -> {//Unrated BG's
                h.actions[160] = (SlotAction) (player, slot) -> openInterface(player);//Go back
                h.actions[91] = (SlotAction) (player, slot) -> player.sendMessage("Cannot enter queue right now");//Enter queue
                h.actions[66] = (SlotAction) (player, slot) -> player.sendMessage("Coming soon!");//Choose bg's
            });
            InterfaceHandler.register(822, h -> {//In Queue widget
                h.actions[136] = (SlotAction) (player, slot) -> Arena1v1Queue.leaveQueue(player);//Leave queue
            });
    }

    public static void openUnratedArenasInter(Player p) {
        p.openInterface(InterfaceType.MAIN, UNRATED_ARENAS);
        p.getPacketSender().sendString(UNRATED_ARENAS, 167, "1v1 Wins: <col=0fd60f>"+ p.unrated1v1Wins);
        p.getPacketSender().sendString(UNRATED_ARENAS, 181, "1v1 Kills: <col=0fd60f>"+p.unrated1v1Kills);
        p.getPacketSender().sendString(UNRATED_ARENAS, 182, "2v2 Wins: <col=0fd60f>"+p.arenathreesWins);
        p.getPacketSender().sendString(UNRATED_ARENAS, 183, "2v2 Kills: <col=0fd60f>"+p.arenathreesKills);
        p.getPacketSender().sendString(UNRATED_ARENAS, 186, "Arena Rating: <col=0fd60f>"+p.pvpRating);
        p.getPacketSender().sendString(UNRATED_ARENAS, 187, "");
        p.getPacketSender().sendString(UNRATED_ARENAS, 204, p.pvpRating+" / 5,000 PvP Rating");
        if (p.pvpRating <= 0) {
            int[] childIds = {194, 195, 196, 197, 198, 199, 200, 201, 202, 203};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(UNRATED_ARENAS, childId, true);
            }
        }
        if (p.pvpRating > 0 && p.pvpRating < 500) {
            int[] childIds = {195, 196, 197, 198, 199, 200, 201, 202, 203};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(UNRATED_ARENAS, childId, true);
            }
        }
        if (p.pvpRating >= 500 && p.pvpRating < 1000) {
            int[] childIds = {196, 197, 198, 199, 200, 201, 202, 203};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(UNRATED_ARENAS, childId, true);
            }
        }
        if (p.pvpRating >= 1000 && p.pvpRating < 1500) {
            int[] childIds = {197, 198, 199, 200, 201, 202, 203};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(UNRATED_ARENAS, childId, true);
            }
        }
        if (p.pvpRating >= 1500 && p.pvpRating < 2000) {
            int[] childIds = {198, 199, 200, 201, 202, 203};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(UNRATED_ARENAS, childId, true);
            }
        }
        if (p.pvpRating >= 2000 && p.pvpRating < 2500) {
            int[] childIds = {199, 200, 201, 202, 203};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(UNRATED_ARENAS, childId, true);
            }
        }
        if (p.pvpRating >= 2500 && p.pvpRating < 3000) {
            int[] childIds = {200, 201, 202, 203};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(UNRATED_ARENAS, childId, true);
            }
        }
        if (p.pvpRating >= 3000 && p.pvpRating < 3500) {
            int[] childIds = {201, 202, 203};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(UNRATED_ARENAS, childId, true);
            }
        }
        if (p.pvpRating >= 3500 && p.pvpRating < 4000) {
            int[] childIds = {202, 203};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(UNRATED_ARENAS, childId, true);
            }
        }
        if (p.pvpRating >= 4000 && p.pvpRating < 4500) {
            p.getPacketSender().setHidden(UNRATED_ARENAS, 203, true);
        }
        if (p.pvpRating >= 4500) {
            int[] childIds = {194, 195, 196, 197, 198, 199, 200, 201, 202, 203};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(UNRATED_ARENAS, childId, false);
            }
        }
    }

    public static void openUnratedBattlegroundsInter(Player p) {
        p.dialogue(
                new MessageDialogue("Battlegrounds are disabled until the start of Season 1.")
        );
//        p.openInterface(InterfaceType.MAIN, UNRATED_BGS);
//        p.getPacketSender().sendString(UNRATED_BGS, 143, "Battleground Wins: <col=0fd60f>"+p.unratedBattlegroundWins);
//        p.getPacketSender().sendString(UNRATED_BGS, 157, "Battleground Kills: <col=0fd60f>"+p.unratedBattlegroundKills);
//        p.getPacketSender().sendString(UNRATED_BGS, 158, "Conquest Points: <col=0fd60f>"+p.conquestPoints);
//        p.getPacketSender().sendString(UNRATED_BGS, 177, p.pvpRating+" / 5,000 PvP Rating");
//        if (p.pvpRating <= 0) {
//            int[] childIds = {167, 168, 169, 170, 171, 172, 173, 174, 175, 176};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(UNRATED_BGS, childId, true);
//            }
//        }
//        if (p.pvpRating >= 0 && p.pvpRating < 500) {
//            int[] childIds = {168, 169, 170, 171, 172, 173, 174, 175, 176};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(UNRATED_BGS, childId, true);
//            }
//        }
//        if (p.pvpRating >= 500 && p.pvpRating < 1000) {
//            int[] childIds = {169, 170, 171, 172, 173, 174, 175, 176};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(UNRATED_BGS, childId, true);
//            }
//        }
//        if (p.pvpRating >= 1000 && p.pvpRating < 1500) {
//            int[] childIds = {170, 171, 172, 173, 174, 175, 176};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(UNRATED_BGS, childId, true);
//            }
//        }
//        if (p.pvpRating >= 1500 && p.pvpRating < 2000) {
//            int[] childIds = {171, 172, 173, 174, 175, 176};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(UNRATED_BGS, childId, true);
//            }
//        }
//        if (p.pvpRating >= 2000 && p.pvpRating < 2500) {
//            int[] childIds = {172, 173, 174, 175, 176};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(UNRATED_BGS, childId, true);
//            }
//        }
//        if (p.pvpRating >= 2500 && p.pvpRating < 3000) {
//            int[] childIds = {173, 174, 175, 176};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(UNRATED_BGS, childId, true);
//            }
//        }
//        if (p.pvpRating >= 3000 && p.pvpRating < 3500) {
//            int[] childIds = {174, 175, 176};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(UNRATED_BGS, childId, true);
//            }
//        }
//        if (p.pvpRating >= 3500 && p.pvpRating < 4000) {
//            int[] childIds = {175, 176};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(UNRATED_BGS, childId, true);
//            }
//        }
//        if (p.pvpRating >= 4000 && p.pvpRating < 4500) {
//            p.getPacketSender().setHidden(UNRATED_BGS, 176, true);
//        }
//        if (p.pvpRating >= 4500) {
//            int[] childIds = {167, 168, 169, 170, 171, 172, 173, 174, 175, 176};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(UNRATED_BGS, childId, false);
//            }
//        }
    }

    public static void openInterface(Player p) {
        if (p.wildernessLevel > 0) {
            p.sendMessage("You cannot open this from inside the wilderness!");
            return;
        }
        p.openInterface(InterfaceType.MAIN, 820);
        p.getPacketSender().sendItems(820, 174, 0, new Item(13652));
        p.getPacketSender().sendItems(820, 176, 0, new Item(11802));
        p.getPacketSender().sendString(820, 164, p.pvpRating+" / 5,000 PvP Rating");
        if (p.pvpRating <= 0) {
            int[] childIds = {154, 155, 156, 157, 158, 159, 160, 161, 162, 163};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(820, childId, true);
            }
        }
        if (p.pvpRating >= 0 && p.pvpRating < 500) {
            int[] childIds = {155, 156, 157, 158, 159, 160, 161, 162, 163};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(820, childId, true);
            }
        }
        if (p.pvpRating >= 500 && p.pvpRating < 1000) {
            int[] childIds = {156, 157, 158, 159, 160, 161, 162, 163};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(820, childId, true);
            }
        }
        if (p.pvpRating >= 1000 && p.pvpRating < 1500) {
            int[] childIds = {157, 158, 159, 160, 161, 162, 163};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(820, childId, true);
            }
        }
        if (p.pvpRating >= 1500 && p.pvpRating < 2000) {
            int[] childIds = {158, 159, 160, 161, 162, 163};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(820, childId, true);
            }
        }
        if (p.pvpRating >= 2000 && p.pvpRating < 2500) {
            int[] childIds = {159, 160, 161, 162, 163};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(820, childId, true);
            }
        }
        if (p.pvpRating >= 2500 && p.pvpRating < 3000) {
            int[] childIds = {160, 161, 162, 163};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(820, childId, true);
            }
        }
        if (p.pvpRating >= 3000 && p.pvpRating < 3500) {
            int[] childIds = {161, 162, 163};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(820, childId, true);
            }
        }
        if (p.pvpRating >= 3500 && p.pvpRating < 4000) {
            int[] childIds = {162, 163};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(820, childId, true);
            }
        }
        if (p.pvpRating >= 4000 && p.pvpRating < 4500) {
            p.getPacketSender().setHidden(820, 163, true);
        }
        if (p.pvpRating >= 4500) {
            int[] childIds = {154, 155, 156, 157, 158, 159, 160, 161, 162, 163};
            for (int childId : childIds) {
                p.getPacketSender().setHidden(820, childId, false);
            }
        }
    }

    public static void openRatedInter(Player p) {
        p.dialogue(
                new MessageDialogue("Rated arenas are disabled until the start of Season 1.")
        );
//        p.openInterface(InterfaceType.MAIN, RATED_INTER);
//        p.getPacketSender().sendString(RATED_INTER, 174, p.pvpRating+" / 5,000 PvP Rating");
//        if (p.pvpRating <= 0) {
//            int[] childIds = {24, 165, 166, 167, 168, 169, 170, 171, 172, 173};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(RATED_INTER, childId, true);
//            }
//        }
//        if (p.pvpRating >= 0 && p.pvpRating < 500) {
//            int[] childIds = {165, 166, 167, 168, 169, 170, 171, 172, 173};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(RATED_INTER, childId, true);
//            }
//        }
//        if (p.pvpRating >= 500 && p.pvpRating < 1000) {
//            int[] childIds = {166, 167, 168, 169, 170, 171, 172, 173};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(RATED_INTER, childId, true);
//            }
//        }
//        if (p.pvpRating >= 1000 && p.pvpRating < 1500) {
//            int[] childIds = {167, 168, 169, 170, 171, 172, 173};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(RATED_INTER, childId, true);
//            }
//        }
//        if (p.pvpRating >= 1500 && p.pvpRating < 2000) {
//            int[] childIds = {168, 169, 170, 171, 172, 173};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(RATED_INTER, childId, true);
//            }
//        }
//        if (p.pvpRating >= 2000 && p.pvpRating < 2500) {
//            int[] childIds = {169, 170, 171, 172, 173};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(RATED_INTER, childId, true);
//            }
//        }
//        if (p.pvpRating >= 2500 && p.pvpRating < 3000) {
//            int[] childIds = {170, 171, 172, 173};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(RATED_INTER, childId, true);
//            }
//        }
//        if (p.pvpRating >= 3000 && p.pvpRating < 3500) {
//            int[] childIds = {171, 172, 173};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(RATED_INTER, childId, true);
//            }
//        }
//        if (p.pvpRating >= 3500 && p.pvpRating < 4000) {
//            int[] childIds = {172, 173};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(RATED_INTER, childId, true);
//            }
//        }
//        if (p.pvpRating >= 4000 && p.pvpRating < 4500) {
//            p.getPacketSender().setHidden(RATED_INTER, 173, true);
//        }
//        if (p.pvpRating >= 4500) {
//            int[] childIds = {24, 165, 166, 167, 168, 169, 170, 171, 172, 173};
//            for (int childId : childIds) {
//                p.getPacketSender().setHidden(RATED_INTER, childId, false);
//            }
//        }
    }

    private static void leaveQueueWidget(Player player){
        player.dialogue(
                new OptionsDialogue("Leave Queue?",
                        new Option("Yes", () -> BattleGroundsQueue.leaveQueue(player)),
                        new Option("No"))
        );
    }

    private static void claimSeasonRewards(Player player) {
        if (player.conquestPoints < 50000) {
            player.dialogue(
                    new MessageDialogue("You must reach level 5 conquest to claim the season rewards!")
            );
        }
        if (player.claimedSeasonReward) {
            player.dialogue(
                    new MessageDialogue("You have already claimed this seasons reward!")
            );
        }
        if (player.conquestPoints >= 50000 && !player.claimedSeasonReward) {
            //Add title & pet reward here
            player.claimedSeasonReward = true;
            player.dialogue(
                    new MessageDialogue("Congratulations, you have claimed this seasons Conquest rewards!")
            );
        }
    }

}