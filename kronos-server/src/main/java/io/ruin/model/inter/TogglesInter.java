package io.ruin.model.inter;

import io.ruin.model.activities.wilderness.BountyHunter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.utils.Option;

public class TogglesInter {

    public static int childId;

    public static void loadAll(Player p) {
        p.openInterface(InterfaceType.ACCOUNT_MANAGEMENT, 921);
        // p.getPacketSender().sendInterface(921, 165, 16, 1);
        showbhTargeting(p);
        p.sendMessage("This worked here");
    }

    static String key;
    //27ae60 green
    //FF0000 red
    public static void showbhTargeting(Player p) {
        BountyHunter.Targeting targeting = p.getBountyHunter().targeting;
        key = "Testinggggg: <col=27ae60>"+targeting.name;
        childId = 190;
        send(p, key);
    }
    public void select(Player player) {
        if(player.wildernessLevel > 0) {
            player.sendMessage("You can't toggle bounty hunter targeting inside the wilderness.");
            return;
        }
        player.dialogue(
                new OptionsDialogue(
                        new Option("All Wilderness", () -> set(player, BountyHunter.Targeting.ALL)),
                        new Option("Disable Targeting", () -> set(player, BountyHunter.Targeting.DISABLED)),
                        new Option("Edgeville Only", () -> set(player, BountyHunter.Targeting.EDGEVILLE))
                )
        );
    }
    private void set(Player player, BountyHunter.Targeting targeting) {
        player.getBountyHunter().targeting = targeting;
        showbhTargeting(player);
    }

    protected static final void send(Player player, String key) {
        player.getPacketSender().sendString(921, childId, (key));
    }

    public static void swap(Player p) {
        if (p.isFixedScreen())
            p.getPacketSender().sendInterface(921, 165, 16, 1);
        else if (p.getGameFrameId() == 164)
            p.getPacketSender().sendInterface(921, 165, 16, 1);
        else
            p.getPacketSender().sendInterface(921, 165, 16, 1);
    }

}