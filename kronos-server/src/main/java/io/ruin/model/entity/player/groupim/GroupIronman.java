package io.ruin.model.entity.player.groupim;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;

public class GroupIronman {

    public static void handleKickMember(Player player) {
        // TODO: wrap up the kicking/leaving would like to do this via interface
    }

    public static void invitePlayer(Player player, Player target) {
        if(player.getGameMode().isAnyIron()) {
            if(target.getStats().totalXp == 1054 && target.getGameMode().isAnyIron()) {
                inviteMember(player, target);
                return;
            }
        } else {
            player.sendMessage("You can't invite "+target.name()+" because they either have gained experience, or this player is not an regular Ironman!");
            return;
        }
    }

    public static void kickMember(Player player, Player member) {

    }

    public static void voteKickLeader(Player player, Player member) {

    }

    public static void leaveGroup(Player player) {

    }

    private static void reject(Player player, Player target) {
        target.closeDialogue();
        player.sendMessage(""+target.name()+" has declined your invitation.");
    }

    private static void accept(Player player, Player target) {

    }

    public static boolean checkIfMember(Player player, Player target) {
        return true;
    }

    public static void inviteMember(Player player, Player target) {
        player.sendMessage("Inviting "+target.name()+" to be in your group.");
        /**
         * Invite Dialogue
         */
        target.dialogue(new OptionsDialogue(""+player.name()+" has invited you to be apart of their group.",
                new Option("Accept invitation", () -> accept(player, target)),
                new Option("Decline invitation", () -> reject(player, target))));
    }


}
