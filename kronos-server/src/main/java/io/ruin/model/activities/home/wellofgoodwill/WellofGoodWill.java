package io.ruin.model.activities.home.wellofgoodwill;

import io.ruin.Server;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;
import io.ruin.utility.TickDelay;
import lombok.Getter;
import lombok.Setter;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class WellofGoodWill {

    private static final int WELL_ID = 884;

    @Getter
    @Setter
    private static long WELL_START_TIMESTAMP = Integer.MAX_VALUE;
    public static Map<String, String> donationsMap;
    @Getter
    private static boolean bonusxpactivated = false;
    @Getter
    private static boolean bonusslayeractivated = false;
    @Getter
    private static boolean bonusdropsactivated = false;
    @Getter
    private static boolean bonusvotesactivated = false;
    @Getter
    private static boolean bonusraidsactivated = false;
    @Getter
    private static boolean bonuspestactivated = false;
    @Getter
    private static boolean bonusbmactivated = false;
    private static int CURRENT_DONATION_ID = 0;
    @Getter
    @Setter
    private static int XP_MAX_AMT = 200000000; //200M
    @Getter
    @Setter
    private static int XP_AMT_NEEDED = (XP_MAX_AMT);
    @Getter
    @Setter
    private static int CURRENT_XP_AMT = 0;
    @Getter
    @Setter
    private static int XP_CURRENT_REMAINING = XP_AMT_NEEDED; //XP_AMOUNT_NEEDED - XP_CURRENT_AMOUNT;

    @Getter
    @Setter
    private static int SLAY_MAX_AMT = 200000000; //200M
    @Getter
    @Setter
    private static int SLAY_AMT_NEEDED = (SLAY_MAX_AMT);
    @Getter
    @Setter
    private static int CURRENT_SLAY_AMT = 0;
    @Getter
    @Setter
    private static int SLAY_CURRENT_REMAINING = SLAY_AMT_NEEDED; //SLAY_AMOUNT_NEEDED - SLAY_CURRENT_AMOUNT;

    @Getter
    @Setter
    private static int DROPS_MAX_AMT = 350000000; //350M
    @Getter
    @Setter
    private static int DROPS_AMT_NEEDED = (DROPS_MAX_AMT);
    @Getter
    @Setter
    private static int CURRENT_DROPS_AMT = 0;
    @Getter
    @Setter
    private static int DROPS_CURRENT_REMAINING = DROPS_AMT_NEEDED; //DROPS_AMOUNT_NEEDED - DROPS_CURRENT_AMOUNT;

    @Getter
    @Setter
    private static int VOTE_MAX_AMT = 500000000; //500M
    @Getter
    @Setter
    private static int VOTE_AMT_NEEDED = (VOTE_MAX_AMT);
    @Getter
    @Setter
    private static int CURRENT_VOTE_AMT = 0;
    @Getter
    @Setter
    private static int VOTE_CURRENT_REMAINING = VOTE_AMT_NEEDED; //VOTE_AMOUNT_NEEDED - VOTE_CURRENT_AMOUNT;

    @Getter
    @Setter
    private static int RAIDS_MAX_AMT = 350000000; //350M
    @Getter
    @Setter
    private static int RAIDS_AMT_NEEDED = (RAIDS_MAX_AMT);
    @Getter
    @Setter
    private static int CURRENT_RAIDS_AMT = 0;
    @Getter
    @Setter
    private static int RAIDS_CURRENT_REMAINING = RAIDS_AMT_NEEDED; //RAIDS_AMOUNT_NEEDED - RAIDS_CURRENT_AMOUNT;

    @Getter
    @Setter
    private static int PEST_MAX_AMT = 50000000; //50M
    @Getter
    @Setter
    private static int PEST_AMT_NEEDED = (PEST_MAX_AMT);
    @Getter
    @Setter
    private static int CURRENT_PEST_AMT = 0;
    @Getter
    @Setter
    private static int PEST_CURRENT_REMAINING = PEST_AMT_NEEDED; //PEST_AMOUNT_NEEDED - PEST_CURRENT_AMOUNT;

    @Getter
    @Setter
    private static int BM_MAX_AMT = 250000000; //250M
    @Getter
    @Setter
    private static int BM_AMT_NEEDED = (BM_MAX_AMT);
    @Getter
    @Setter
    private static int CURRENT_BM_AMT = 0;
    @Getter
    @Setter
    private static int BM_CURRENT_REMAINING = BM_AMT_NEEDED; //BM_AMOUNT_NEEDED - BM_CURRENT_AMOUNT;

    public static TickDelay doubleXPTimer = new TickDelay();
    public static TickDelay doubleSlayerTimer = new TickDelay();
    public static TickDelay doubleDropsTimer = new TickDelay();
    public static TickDelay doubleVotesTimer = new TickDelay();
    public static TickDelay bonusRaidsTimer = new TickDelay();
    public static TickDelay doublePestTImer = new TickDelay();
    public static TickDelay doubleBMTimer = new TickDelay();

    @Getter
    @Setter
    public static int doubleSlayerAmt;
    @Getter
    @Setter
    public static int doubleSlayerAmtTotal;

    @Getter
    @Setter
    public static int doubleDropsAmt;
    @Getter
    @Setter
    public static int doubleDropsAmtTotal;

    @Getter
    @Setter
    public static int doubleVoteAmt;
    @Getter
    @Setter
    public static int doubleVoteAmtTotal;

    @Getter
    @Setter
    public static int bonusRaidsAmt;
    @Getter
    @Setter
    public static int bonusRaidsAmtTotal;

    @Getter
    @Setter
    public static int doublePestAmt;
    @Getter
    @Setter
    public static int doublePestAmtTotal;

    @Getter
    @Setter
    public static int doubleBmAmt;
    @Getter
    @Setter
    public static int doubleBmAmtTotal;

    static {
        donationsMap = new HashMap<>();
    }

    static {
        InterfaceHandler.register(985, h -> {
            h.actions[214] = (SlotAction) (p, slot) -> donateBonusXP(p);
            h.actions[239] = (SlotAction) (p, slot) -> donateBonusSlayer(p);
            h.actions[263] = (SlotAction) (p, slot) -> donateBonusDrops(p);
            h.actions[287] = (SlotAction) (p, slot) -> donateBonusVote(p);
            h.actions[311] = (SlotAction) (p, slot) -> donateBonusRaids(p);
            h.actions[336] = (SlotAction) (p, slot) -> donateBonusPest(p);
            h.actions[360] = (SlotAction) (p, slot) -> donateBonusBm(p);
        });
        ObjectAction.register(WELL_ID, 3, (player, object) -> {
            if (WellofGoodWill.isBonusxpactivated()) {
                player.dialogue(
                        new MessageDialogue("The well is already active for another " + getRemainingTime()));
                return;
            }
            player.dialogue(new ItemDialogue().one(995, "The well currently contains " + String.format("%,.0f", (double) CURRENT_XP_AMT) + " coins out of the required " + String.format("%,.0f", (double) XP_AMT_NEEDED)));
        });
        ObjectAction.register(WELL_ID, 1, (player, object) -> {
            player.openInterface(InterfaceType.MAIN, 985);
            sendInterfaceUpdates(player);
        });
        ObjectAction.register(WELL_ID, 4, (player, object) -> {
            if (WellofGoodWill.isBonusxpactivated()) {
                player.dialogue(
                        new MessageDialogue("Sorry, but the well is already active for another " + getRemainingTime()));
                return;
            }
        });
    }

    public static void sendProgressBarUpdates(Player p) {
        sendXpProgressBar(p);
        sendSlayProgressBar(p);
        sendDropsProgressBar(p);
        sendVotesProgressBar(p);
        sendRaidsProgressBar(p);
        sendPestProgressBar(p);
        sendBmProgressBar(p);
    }

    public static void sendXpProgressBar(Player p) {
        if (CURRENT_XP_AMT < 50000000) {
            //p.getPacketSender().setHidden(985, 219, true);
            p.getPacketSender().setHidden(985, 220, false);
            p.getPacketSender().setHidden(985, 221, false);
            p.getPacketSender().setHidden(985, 222, false);
            p.getPacketSender().setHidden(985, 223, false);
        }
        if (CURRENT_XP_AMT >= 50000000 && CURRENT_XP_AMT < 100000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 220, true);
            p.getPacketSender().setHidden(985, 221, false);
            p.getPacketSender().setHidden(985, 222, false);
            p.getPacketSender().setHidden(985, 223, false);
        }
        if (CURRENT_XP_AMT >= 100000000 && CURRENT_XP_AMT < 150000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 220, true);
            p.getPacketSender().setHidden(985, 221, true);
            p.getPacketSender().setHidden(985, 222, false);
            p.getPacketSender().setHidden(985, 223, false);
        }
        if (CURRENT_XP_AMT >= 150000000 && CURRENT_XP_AMT < 200000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 220, true);
            p.getPacketSender().setHidden(985, 221, true);
            p.getPacketSender().setHidden(985, 222, true);
            p.getPacketSender().setHidden(985, 223, false);
        }
        if (CURRENT_XP_AMT >= 200000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 220, true);
            p.getPacketSender().setHidden(985, 221, true);
            p.getPacketSender().setHidden(985, 222, true);
            p.getPacketSender().setHidden(985, 223, true);
        }
    }

    public static void sendSlayProgressBar(Player p) {
        if (CURRENT_SLAY_AMT < 50000000) {
            //p.getPacketSender().setHidden(985, 219, true);
            p.getPacketSender().setHidden(985, 244, false);
            p.getPacketSender().setHidden(985, 245, false);
            p.getPacketSender().setHidden(985, 246, false);
            p.getPacketSender().setHidden(985, 247, false);
        }
        if (CURRENT_SLAY_AMT >= 50000000 && CURRENT_SLAY_AMT < 100000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 244, true);
            p.getPacketSender().setHidden(985, 245, false);
            p.getPacketSender().setHidden(985, 246, false);
            p.getPacketSender().setHidden(985, 247, false);
        }
        if (CURRENT_SLAY_AMT >= 100000000 && CURRENT_SLAY_AMT < 150000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 244, true);
            p.getPacketSender().setHidden(985, 245, true);
            p.getPacketSender().setHidden(985, 246, false);
            p.getPacketSender().setHidden(985, 247, false);
        }
        if (CURRENT_SLAY_AMT >= 150000000 && CURRENT_SLAY_AMT < 200000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 244, true);
            p.getPacketSender().setHidden(985, 245, true);
            p.getPacketSender().setHidden(985, 246, true);
            p.getPacketSender().setHidden(985, 247, false);
        }
        if (CURRENT_SLAY_AMT >= 200000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 244, true);
            p.getPacketSender().setHidden(985, 245, true);
            p.getPacketSender().setHidden(985, 246, true);
            p.getPacketSender().setHidden(985, 247, true);
        }
    }

    public static void sendDropsProgressBar(Player p) {
        if (CURRENT_DROPS_AMT < 87000000) {
            //p.getPacketSender().setHidden(985, 219, true);
            p.getPacketSender().setHidden(985, 268, false);
            p.getPacketSender().setHidden(985, 269, false);
            p.getPacketSender().setHidden(985, 270, false);
            p.getPacketSender().setHidden(985, 270, false);
        }
        if (CURRENT_DROPS_AMT >= 87000000 && CURRENT_DROPS_AMT < 175000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 268, true);
            p.getPacketSender().setHidden(985, 269, false);
            p.getPacketSender().setHidden(985, 270, false);
            p.getPacketSender().setHidden(985, 271, false);
        }
        if (CURRENT_DROPS_AMT >= 175000000 && CURRENT_DROPS_AMT < 262000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 268, true);
            p.getPacketSender().setHidden(985, 269, true);
            p.getPacketSender().setHidden(985, 270, false);
            p.getPacketSender().setHidden(985, 271, false);
        }
        if (CURRENT_DROPS_AMT >= 262000000 && CURRENT_DROPS_AMT < 350000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 268, true);
            p.getPacketSender().setHidden(985, 269, true);
            p.getPacketSender().setHidden(985, 270, true);
            p.getPacketSender().setHidden(985, 271, false);
        }
        if (CURRENT_DROPS_AMT >= 350000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 268, true);
            p.getPacketSender().setHidden(985, 269, true);
            p.getPacketSender().setHidden(985, 270, true);
            p.getPacketSender().setHidden(985, 271, true);
        }
    }

    public static void sendVotesProgressBar(Player p) {
        if (CURRENT_VOTE_AMT < 125000000) {
            //p.getPacketSender().setHidden(985, 219, true);
            p.getPacketSender().setHidden(985, 292, false);
            p.getPacketSender().setHidden(985, 293, false);
            p.getPacketSender().setHidden(985, 294, false);
            p.getPacketSender().setHidden(985, 295, false);
        }
        if (CURRENT_VOTE_AMT >= 125000000 && CURRENT_VOTE_AMT < 250000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 292, true);
            p.getPacketSender().setHidden(985, 293, false);
            p.getPacketSender().setHidden(985, 294, false);
            p.getPacketSender().setHidden(985, 295, false);
        }
        if (CURRENT_VOTE_AMT >= 250000000 && CURRENT_VOTE_AMT < 375000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 292, true);
            p.getPacketSender().setHidden(985, 293, true);
            p.getPacketSender().setHidden(985, 294, false);
            p.getPacketSender().setHidden(985, 295, false);
        }
        if (CURRENT_VOTE_AMT >= 375000000 && CURRENT_VOTE_AMT < 500000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 292, true);
            p.getPacketSender().setHidden(985, 293, true);
            p.getPacketSender().setHidden(985, 294, true);
            p.getPacketSender().setHidden(985, 295, false);
        }
        if (CURRENT_VOTE_AMT >= 500000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 292, true);
            p.getPacketSender().setHidden(985, 293, true);
            p.getPacketSender().setHidden(985, 294, true);
            p.getPacketSender().setHidden(985, 295, true);
        }
    }

    public static void sendRaidsProgressBar(Player p) {
        if (CURRENT_RAIDS_AMT < 87000000) {
            //p.getPacketSender().setHidden(985, 219, true);
            p.getPacketSender().setHidden(985, 316, false);
            p.getPacketSender().setHidden(985, 317, false);
            p.getPacketSender().setHidden(985, 318, false);
            p.getPacketSender().setHidden(985, 319, false);
        }
        if (CURRENT_RAIDS_AMT >= 87000000 && CURRENT_RAIDS_AMT < 175000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 316, true);
            p.getPacketSender().setHidden(985, 317, false);
            p.getPacketSender().setHidden(985, 318, false);
            p.getPacketSender().setHidden(985, 319, false);
        }
        if (CURRENT_RAIDS_AMT >= 175000000 && CURRENT_RAIDS_AMT < 262000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 316, true);
            p.getPacketSender().setHidden(985, 317, true);
            p.getPacketSender().setHidden(985, 318, false);
            p.getPacketSender().setHidden(985, 319, false);
        }
        if (CURRENT_RAIDS_AMT >= 262000000 && CURRENT_RAIDS_AMT < 350000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 316, true);
            p.getPacketSender().setHidden(985, 317, true);
            p.getPacketSender().setHidden(985, 318, true);
            p.getPacketSender().setHidden(985, 319, false);
        }
        if (CURRENT_RAIDS_AMT >= 350000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 316, true);
            p.getPacketSender().setHidden(985, 317, true);
            p.getPacketSender().setHidden(985, 318, true);
            p.getPacketSender().setHidden(985, 319, true);
        }
    }

    public static void sendPestProgressBar(Player p) {
        if (CURRENT_PEST_AMT < 13000000) {
            //p.getPacketSender().setHidden(985, 219, true);
            p.getPacketSender().setHidden(985, 341, false);
            p.getPacketSender().setHidden(985, 342, false);
            p.getPacketSender().setHidden(985, 343, false);
            p.getPacketSender().setHidden(985, 344, false);
        }
        if (CURRENT_PEST_AMT >= 13000000 && CURRENT_PEST_AMT < 26000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 341, true);
            p.getPacketSender().setHidden(985, 342, false);
            p.getPacketSender().setHidden(985, 343, false);
            p.getPacketSender().setHidden(985, 344, false);
        }
        if (CURRENT_PEST_AMT >= 26000000 && CURRENT_PEST_AMT < 39000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 341, true);
            p.getPacketSender().setHidden(985, 342, true);
            p.getPacketSender().setHidden(985, 343, false);
            p.getPacketSender().setHidden(985, 344, false);
        }
        if (CURRENT_PEST_AMT >= 39000000 && CURRENT_PEST_AMT < 50000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 341, true);
            p.getPacketSender().setHidden(985, 342, true);
            p.getPacketSender().setHidden(985, 343, true);
            p.getPacketSender().setHidden(985, 344, false);
        }
        if (CURRENT_PEST_AMT >= 50000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 341, true);
            p.getPacketSender().setHidden(985, 342, true);
            p.getPacketSender().setHidden(985, 343, true);
            p.getPacketSender().setHidden(985, 344, true);
        }
    }

    public static void sendBmProgressBar(Player p) {
        if (CURRENT_BM_AMT < 63000000) {
            //p.getPacketSender().setHidden(985, 219, true);
            p.getPacketSender().setHidden(985, 365, false);
            p.getPacketSender().setHidden(985, 366, false);
            p.getPacketSender().setHidden(985, 367, false);
            p.getPacketSender().setHidden(985, 368, false);
        }
        if (CURRENT_BM_AMT >= 63000000 && CURRENT_BM_AMT < 125000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 365, true);
            p.getPacketSender().setHidden(985, 366, false);
            p.getPacketSender().setHidden(985, 367, false);
            p.getPacketSender().setHidden(985, 368, false);
        }
        if (CURRENT_BM_AMT >= 125000000 && CURRENT_BM_AMT < 188000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 365, true);
            p.getPacketSender().setHidden(985, 366, true);
            p.getPacketSender().setHidden(985, 367, false);
            p.getPacketSender().setHidden(985, 368, false);
        }
        if (CURRENT_BM_AMT >= 188000000 && CURRENT_BM_AMT < 250000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 365, true);
            p.getPacketSender().setHidden(985, 366, true);
            p.getPacketSender().setHidden(985, 367, true);
            p.getPacketSender().setHidden(985, 368, false);
        }
        if (CURRENT_BM_AMT >= 250000000) {
            //p.getPacketSender().setHidden(985, 219, false);
            p.getPacketSender().setHidden(985, 365, true);
            p.getPacketSender().setHidden(985, 366, true);
            p.getPacketSender().setHidden(985, 367, true);
            p.getPacketSender().setHidden(985, 368, true);
        }
    }

    public static void sendInterfaceUpdates(Player p) {
        p.getPacketSender().sendString(985, 229, doubleXpString()); //Double XP
        p.getPacketSender().sendString(985, 254, doubleSlayerString()); //Double slayer
        p.getPacketSender().sendString(985, 278, doubleDropsString()); //Double drops
        p.getPacketSender().sendString(985, 302, doubleVotesString()); //Double votes
        p.getPacketSender().sendString(985, 326, bonusRaidsString()); //Bonus raids
        p.getPacketSender().sendString(985, 351, doublePestString()); //Double pest
        p.getPacketSender().sendString(985, 375, doubleBmString()); //Double BM
        sendProgressBarUpdates(p);
    }

    public static String doubleXpString() {
        String bonusString = "" + CURRENT_XP_AMT;
        int bonusInt = Integer.parseInt(bonusString);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedNumber = numberFormat.format(bonusInt);
        if (!bonusxpactivated) {
            return "" + formattedNumber + "/200,000,000";
        }
        int mins = doubleXPTimer.remainingToMins();
        String timeDisplay = CURRENT_XP_AMT < XP_MAX_AMT ? "<col=27ae60>" + formattedNumber + "/200,000,000" : "<col=0fd60f>" + mins + " minutes remaining";
        return "" + timeDisplay;
    }

    public static String doubleSlayerString() {
        String bonusString = "" + CURRENT_SLAY_AMT;
        int bonusInt = Integer.parseInt(bonusString);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedNumber = numberFormat.format(bonusInt);
        if (!bonusslayeractivated) {
            return "" + formattedNumber + "/200,000,000";
        }
        int mins = doubleSlayerTimer.remainingToMins();
        String timeDisplay = CURRENT_SLAY_AMT < SLAY_MAX_AMT ? "<col=27ae60>" + formattedNumber + "/200,000,000" : "<col=0fd60f>" + mins + " minutes remaining";
        return "" + timeDisplay;
    }

    public static String doubleDropsString() {
        String bonusString = "" + CURRENT_DROPS_AMT;
        int bonusInt = Integer.parseInt(bonusString);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedNumber = numberFormat.format(bonusInt);
        if (!bonusdropsactivated) {
            return "" + formattedNumber + "/350,000,000";
        }
        int mins = doubleDropsTimer.remainingToMins();
        String timeDisplay = CURRENT_DROPS_AMT < DROPS_MAX_AMT ? "<col=27ae60>" + formattedNumber + "/350,000,000" : "<col=0fd60f>" + mins + " minutes remaining";
        return "" + timeDisplay;
    }

    public static String doubleVotesString() {
        String bonusString = "" + CURRENT_VOTE_AMT;
        int bonusInt = Integer.parseInt(bonusString);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedNumber = numberFormat.format(bonusInt);
        if (!bonusvotesactivated) {
            return "" + formattedNumber + "/500,000,000";
        }
        int mins = doubleVotesTimer.remainingToMins();
        String timeDisplay = CURRENT_VOTE_AMT < VOTE_MAX_AMT ? "<col=27ae60>" + formattedNumber + "/500,000,000" : "<col=0fd60f>" + mins + " minutes remaining";
        return "" + timeDisplay;
    }

    public static String bonusRaidsString() {
        String bonusString = "" + CURRENT_RAIDS_AMT;
        int bonusInt = Integer.parseInt(bonusString);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedNumber = numberFormat.format(bonusInt);
        if (!bonusraidsactivated) {
            return "" + formattedNumber + "/350,000,000";
        }
        int mins = bonusRaidsTimer.remainingToMins();
        String timeDisplay = CURRENT_RAIDS_AMT < RAIDS_MAX_AMT ? "<col=27ae60>" + formattedNumber + "/350,000,000" : "<col=0fd60f>" + mins + " minutes remaining";
        return "" + timeDisplay;
    }

    public static String doublePestString() {
        String bonusString = "" + CURRENT_PEST_AMT;
        int bonusInt = Integer.parseInt(bonusString);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedNumber = numberFormat.format(bonusInt);
        if (!bonuspestactivated) {
            return "" + formattedNumber + "/50,000,000";
        }
        int mins = doublePestTImer.remainingToMins();
        String timeDisplay = CURRENT_PEST_AMT < PEST_MAX_AMT ? "<col=27ae60>" + formattedNumber + "/50,000,000" : "<col=0fd60f>" + mins + " minutes remaining";
        return "" + timeDisplay;
    }

    public static String doubleBmString() {
        String bonusString = "" + CURRENT_BM_AMT;
        int bonusInt = Integer.parseInt(bonusString);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedNumber = numberFormat.format(bonusInt);
        if (!bonusbmactivated) {
            return "" + formattedNumber + "/250,000,000";
        }
        int mins = doubleBMTimer.remainingToMins();
        String timeDisplay = CURRENT_BM_AMT < BM_MAX_AMT ? "<col=27ae60>" + formattedNumber + "/250,000,000" : "<col=0fd60f>" + mins + " minutes remaining";
        return "" + timeDisplay;
    }

    private static String getRemainingTime() {
        long ms = WELL_START_TIMESTAMP - System.currentTimeMillis();
        long hours = TimeUnit.MILLISECONDS.toHours(ms);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);
        return (hours <= 2 ? (hours + " hour" + (hours > 1 ? "s" : "") + " and ") : "") +
                Math.max((minutes - TimeUnit.HOURS.toMinutes(hours)), 1) + " minute" +
                ((minutes - TimeUnit.HOURS.toMinutes(hours)) > 1 ? "s" : "");
    }

    public static void donateBonusXP(Player p) {
        if (WellofGoodWill.isBonusxpactivated()) {
            p.dialogue(
                    new MessageDialogue("Bonus XP is already active for another " + getRemainingTime()));
            return;
        }
        p.integerInput("Enter the amount of coins you'd like to donate", amount -> {

            String formattedAmount = String.format("%,.0f", (double) amount);

            if (amount > XP_MAX_AMT) {
                amount = XP_MAX_AMT;
            }

            if (amount < 500000 || p.getInventory().getAmount(995) < 500000) {
                p.retryIntegerInput("The minimum donation allowed is at least 500,000 coins.");

            } else if (p.getInventory().getAmount(995) >= amount) {
                int finalAmount = amount;
                p.dialogue(
                        new OptionsDialogue("Are you sure you want to donate " + formattedAmount + " coins?",
                                new Option("Yes, I want to donate " + Color.DARK_RED.wrap(formattedAmount) + " coins.", () -> {

                                    CURRENT_DONATION_ID++;
                                    donationsMap.put(p.name() + "___" + CURRENT_DONATION_ID, formattedAmount);

                                    //TODO log well donations - no repeats of the alerion rune boots incident pls :)
                                    if (finalAmount > 10000000) {
                                        Broadcast.WORLD.sendNews(p.name() + " has donated " + formattedAmount + " coins to the Well of Goodwill for 25% Bonus XP!");
                                    }

                                    if (finalAmount >= XP_CURRENT_REMAINING) {
                                        Broadcast.WORLD.sendNews(p.name() + " has filled the Well of Goodwill for 25% Bonus XP!");
                                        // Broadcast.WORLD.sendNews("It is now granting double xp for the next two hours!");
                                        activateBonusXp(true, p);
                                        p.getInventory().remove(995, finalAmount);
                                        CURRENT_XP_AMT += finalAmount;
                                        XP_CURRENT_REMAINING = XP_AMT_NEEDED - CURRENT_XP_AMT;
                                        return;
                                    }

                                    if (finalAmount >= XP_AMT_NEEDED) {
                                        Broadcast.WORLD.sendNews(p.name() + " has filled the Well of Goodwill for 25% Bonus XP!");
                                        // Broadcast.WORLD.sendNews("It is now granting double xp for the next two hours!");
                                        activateBonusXp(true, p);
                                        p.getInventory().remove(995, finalAmount);
                                        CURRENT_XP_AMT += finalAmount;
                                        XP_CURRENT_REMAINING = XP_AMT_NEEDED - CURRENT_XP_AMT;
                                        return;
                                    }

                                    p.getInventory().remove(995, finalAmount);
                                    CURRENT_XP_AMT += finalAmount;
                                    XP_CURRENT_REMAINING = XP_AMT_NEEDED - CURRENT_XP_AMT;
                                }),
                                new Option("No, I've changed my mind.")
                        )
                );
            }
        });
    }

    public static void donateBonusSlayer(Player p) {
        if (WellofGoodWill.isBonusslayeractivated()) {
            p.dialogue(
                    new MessageDialogue("Bonus Slayer Points are already active for another " + getRemainingTime()));
            return;
        }
        p.integerInput("Enter the amount of coins you'd like to donate", amount -> {

            String formattedAmount = String.format("%,.0f", (double) amount);

            if (amount > SLAY_MAX_AMT) {
                amount = SLAY_MAX_AMT;
            }

            if (amount < 500000 || p.getInventory().getAmount(995) < 500000) {
                p.retryIntegerInput("The minimum donation allowed is at least 500,000 coins.");
                return;
            } else if (p.getInventory().getAmount(995) >= amount) {
                int finalAmount = amount;
                p.dialogue(
                        new OptionsDialogue("Are you sure you want to donate " + formattedAmount + " coins?",
                                new Option("Yes, I want to donate " + Color.DARK_RED.wrap(formattedAmount) + " coins.", () -> {

                                    CURRENT_DONATION_ID++;
                                    donationsMap.put(p.name() + "___" + CURRENT_DONATION_ID, formattedAmount);

                                    //TODO log well donations - no repeats of the alerion rune boots incident pls :)
                                    if (finalAmount > 10000000) {
                                        Broadcast.WORLD.sendNews(p.name() + " has donated " + formattedAmount + " coins to the Well of Goodwill for x2 Slayer Points!");
                                    }

                                    if (finalAmount >= SLAY_CURRENT_REMAINING) {
                                        Broadcast.WORLD.sendNews(p.name() + " has filled the Well of Goodwill for x2 Slayer Points!");
                                        // Broadcast.WORLD.sendNews("It is now granting double xp for the next two hours!");
                                        activateDoubleSlayer(true, p);
                                        p.getInventory().remove(995, finalAmount);
                                        CURRENT_SLAY_AMT += finalAmount;
                                        SLAY_CURRENT_REMAINING = SLAY_AMT_NEEDED - CURRENT_SLAY_AMT;
                                        return;
                                    }

                                    if (finalAmount >= SLAY_AMT_NEEDED) {
                                        Broadcast.WORLD.sendNews(p.name() + " has filled the Well of Goodwill for x2 Slayer Points!");
                                        // Broadcast.WORLD.sendNews("It is now granting double xp for the next two hours!");
                                        activateDoubleSlayer(true, p);
                                        p.getInventory().remove(995, finalAmount);
                                        CURRENT_SLAY_AMT += finalAmount;
                                        SLAY_CURRENT_REMAINING = SLAY_AMT_NEEDED - CURRENT_SLAY_AMT;
                                        return;
                                    }

                                    p.getInventory().remove(995, finalAmount);
                                    CURRENT_SLAY_AMT += finalAmount;
                                    SLAY_CURRENT_REMAINING = SLAY_AMT_NEEDED - CURRENT_SLAY_AMT;
                                }),
                                new Option("No, I've changed my mind.")
                        )
                );
            }
        });
    }

    public static void donateBonusDrops(Player p) {
        if (WellofGoodWill.isBonusdropsactivated()) {
            p.dialogue(
                    new MessageDialogue("Bonus Drop Rates are already active for another " + getRemainingTime()));
            return;
        }
        p.integerInput("Enter the amount of coins you'd like to donate", amount -> {

            String formattedAmount = String.format("%,.0f", (double) amount);

            if (amount > DROPS_MAX_AMT) {
                amount = DROPS_MAX_AMT;
            }

            if (amount < 500000 || p.getInventory().getAmount(995) < 500000) {
                p.retryIntegerInput("The minimum donation allowed is at least 500,000 coins.");
                return;
            } else if (p.getInventory().getAmount(995) >= amount) {
                int finalAmount = amount;
                p.dialogue(
                        new OptionsDialogue("Are you sure you want to donate " + formattedAmount + " coins?",
                                new Option("Yes, I want to donate " + Color.DARK_RED.wrap(formattedAmount) + " coins.", () -> {

                                    CURRENT_DONATION_ID++;
                                    donationsMap.put(p.name() + "___" + CURRENT_DONATION_ID, formattedAmount);

                                    //TODO log well donations - no repeats of the alerion rune boots incident pls :)
                                    if (finalAmount > 10000000) {
                                        Broadcast.WORLD.sendNews(p.name() + " has donated " + formattedAmount + " coins to the Well of Goodwill for x2 Rare Drop Chance!");
                                    }

                                    if (finalAmount >= DROPS_CURRENT_REMAINING) {
                                        Broadcast.WORLD.sendNews(p.name() + " has filled the Well of Goodwill for x2 Rare Drop Chance!");
                                        // Broadcast.WORLD.sendNews("It is now granting double xp for the next two hours!");
                                        activateDoubleDropRate(true, p);
                                        p.getInventory().remove(995, finalAmount);
                                        CURRENT_DROPS_AMT += finalAmount;
                                        DROPS_CURRENT_REMAINING = DROPS_AMT_NEEDED - CURRENT_DROPS_AMT;
                                        return;
                                    }

                                    if (finalAmount >= DROPS_AMT_NEEDED) {
                                        Broadcast.WORLD.sendNews(p.name() + " has filled the Well of Goodwill for x2 Rare Drop Chance!");
                                        // Broadcast.WORLD.sendNews("It is now granting double XP for the next two hours!");
                                        activateDoubleDropRate(true, p);
                                        p.getInventory().remove(995, finalAmount);
                                        CURRENT_DROPS_AMT += finalAmount;
                                        DROPS_CURRENT_REMAINING = DROPS_AMT_NEEDED - CURRENT_DROPS_AMT;
                                        return;
                                    }

                                    p.getInventory().remove(995, finalAmount);
                                    CURRENT_DROPS_AMT += finalAmount;
                                    DROPS_CURRENT_REMAINING = DROPS_AMT_NEEDED - CURRENT_DROPS_AMT;
                                }),
                                new Option("No, I've changed my mind.")
                        )
                );
            }
        });
    }

    public static void donateBonusVote(Player p) {
        if (WellofGoodWill.isBonusvotesactivated()) {
            p.dialogue(
                    new MessageDialogue("Bonus Vote Tickets are already active for another " + getRemainingTime()));
            return;
        }
        p.integerInput("Enter the amount of coins you'd like to donate", amount -> {

            String formattedAmount = String.format("%,.0f", (double) amount);

            if (amount > VOTE_MAX_AMT) {
                amount = VOTE_MAX_AMT;
            }

            if (amount < 500000 || p.getInventory().getAmount(995) < 500000) {
                p.retryIntegerInput("The minimum donation allowed is at least 500,000 coins.");
                return;
            } else if (p.getInventory().getAmount(995) >= amount) {
                int finalAmount = amount;
                p.dialogue(
                        new OptionsDialogue("Are you sure you want to donate " + formattedAmount + " coins?",
                                new Option("Yes, I want to donate " + Color.DARK_RED.wrap(formattedAmount) + " coins.", () -> {

                                    CURRENT_DONATION_ID++;
                                    donationsMap.put(p.name() + "___" + CURRENT_DONATION_ID, formattedAmount);

                                    //TODO log well donations - no repeats of the alerion rune boots incident pls :)
                                    if (finalAmount > 10000000) {
                                        Broadcast.WORLD.sendNews(p.name() + " has donated " + formattedAmount + " coins to the Well of Goodwill for x2 Vote Tickets!");
                                    }

                                    if (finalAmount >= VOTE_CURRENT_REMAINING) {
                                        Broadcast.WORLD.sendNews(p.name() + " has filled the Well of Goodwill for x2 Vote Tickets!");
                                        // Broadcast.WORLD.sendNews("It is now granting double xp for the next two hours!");
                                        activateDoubleVotes(true, p);
                                        p.getInventory().remove(995, finalAmount);
                                        CURRENT_VOTE_AMT += finalAmount;
                                        VOTE_CURRENT_REMAINING = VOTE_AMT_NEEDED - CURRENT_VOTE_AMT;
                                        return;
                                    }

                                    if (finalAmount >= VOTE_AMT_NEEDED) {
                                        Broadcast.WORLD.sendNews(p.name() + " has filled the Well of Goodwill for x2 Vote Tickets!");
                                        // Broadcast.WORLD.sendNews("It is now granting double xp for the next two hours!");
                                        activateDoubleVotes(true, p);
                                        p.getInventory().remove(995, finalAmount);
                                        CURRENT_VOTE_AMT += finalAmount;
                                        VOTE_CURRENT_REMAINING = VOTE_AMT_NEEDED - CURRENT_VOTE_AMT;
                                        return;
                                    }

                                    p.getInventory().remove(995, finalAmount);
                                    CURRENT_VOTE_AMT += finalAmount;
                                    VOTE_CURRENT_REMAINING = VOTE_AMT_NEEDED - CURRENT_VOTE_AMT;
                                }),
                                new Option("No, I've changed my mind.")
                        )
                );
            }
        });
    }

    public static void donateBonusRaids(Player p) {
        if (WellofGoodWill.isBonusraidsactivated()) {
            p.dialogue(
                    new MessageDialogue("Bonus Raids Points are already active for another " + getRemainingTime()));
            return;
        }
        p.integerInput("Enter the amount of coins you'd like to donate", amount -> {

            String formattedAmount = String.format("%,.0f", (double) amount);

            if (amount > RAIDS_MAX_AMT) {
                amount = RAIDS_MAX_AMT;
            }

            if (amount < 500000 || p.getInventory().getAmount(995) < 500000) {
                p.retryIntegerInput("The minimum donation allowed is at least 500,000 coins.");
                return;
            } else if (p.getInventory().getAmount(995) >= amount) {
                int finalAmount = amount;
                p.dialogue(
                        new OptionsDialogue("Are you sure you want to donate " + formattedAmount + " coins?",
                                new Option("Yes, I want to donate " + Color.DARK_RED.wrap(formattedAmount) + " coins.", () -> {

                                    CURRENT_DONATION_ID++;
                                    donationsMap.put(p.name() + "___" + CURRENT_DONATION_ID, formattedAmount);

                                    //TODO log well donations - no repeats of the alerion rune boots incident pls :)
                                    if (finalAmount > 10000000) {
                                        Broadcast.WORLD.sendNews(p.name() + " has donated " + formattedAmount + " coins to the Well of Goodwill for 15% more Raid Points!");
                                    }

                                    if (finalAmount >= RAIDS_CURRENT_REMAINING) {
                                        Broadcast.WORLD.sendNews(p.name() + " has filled the Well of Goodwill for 15% more Raid Points!");
                                        // Broadcast.WORLD.sendNews("It is now granting double xp for the next two hours!");
                                        activateBonusRaidPts(true, p);
                                        p.getInventory().remove(995, finalAmount);
                                        CURRENT_RAIDS_AMT += finalAmount;
                                        RAIDS_CURRENT_REMAINING = RAIDS_AMT_NEEDED - CURRENT_RAIDS_AMT;
                                        return;
                                    }

                                    if (finalAmount >= RAIDS_AMT_NEEDED) {
                                        Broadcast.WORLD.sendNews(p.name() + " has filled the Well of Goodwill for 15% more Raid Points!");
                                        // Broadcast.WORLD.sendNews("It is now granting double RAIDS for the next two hours!");
                                        activateBonusRaidPts(true, p);
                                        p.getInventory().remove(995, finalAmount);
                                        CURRENT_RAIDS_AMT += finalAmount;
                                        RAIDS_CURRENT_REMAINING = RAIDS_AMT_NEEDED - CURRENT_RAIDS_AMT;
                                        return;
                                    }

                                    p.getInventory().remove(995, finalAmount);
                                    CURRENT_RAIDS_AMT += finalAmount;
                                    RAIDS_CURRENT_REMAINING = RAIDS_AMT_NEEDED - CURRENT_RAIDS_AMT;
                                }),
                                new Option("No, I've changed my mind.")
                        )
                );
            }
        });
    }

    public static void donateBonusPest(Player p) {
        if (WellofGoodWill.isBonuspestactivated()) {
            p.dialogue(
                    new MessageDialogue("Bonus Pest Control Points are already active for another " + getRemainingTime()));
            return;
        }
        p.integerInput("Enter the amount of coins you'd like to donate", amount -> {

            String formattedAmount = String.format("%,.0f", (double) amount);

            if (amount > PEST_MAX_AMT) {
                amount = PEST_MAX_AMT;
            }

            if (amount < 500000 || p.getInventory().getAmount(995) < 500000) {
                p.retryIntegerInput("The minimum donation allowed is at least 500,000 coins.");
                return;
            } else if (p.getInventory().getAmount(995) >= amount) {
                int finalAmount = amount;
                p.dialogue(
                        new OptionsDialogue("Are you sure you want to donate " + formattedAmount + " coins?",
                                new Option("Yes, I want to donate " + Color.DARK_RED.wrap(formattedAmount) + " coins.", () -> {

                                    CURRENT_DONATION_ID++;
                                    donationsMap.put(p.name() + "___" + CURRENT_DONATION_ID, formattedAmount);

                                    //TODO log well donations - no repeats of the alerion rune boots incident pls :)
                                    if (finalAmount > 10000000) {
                                        Broadcast.WORLD.sendNews(p.name() + " has donated " + formattedAmount + " coins to the Well of Goodwill for x2 Pest Control Points!");
                                    }

                                    if (finalAmount >= PEST_CURRENT_REMAINING) {
                                        Broadcast.WORLD.sendNews(p.name() + " has filled the Well of Goodwill for x2 Pest Control Points!");
                                        // Broadcast.WORLD.sendNews("It is now granting double xp for the next two hours!");
                                        activateDoublePest(true, p);
                                        p.getInventory().remove(995, finalAmount);
                                        CURRENT_PEST_AMT += finalAmount;
                                        PEST_CURRENT_REMAINING = PEST_AMT_NEEDED - CURRENT_PEST_AMT;
                                        return;
                                    }

                                    if (finalAmount >= PEST_AMT_NEEDED) {
                                        Broadcast.WORLD.sendNews(p.name() + " has filled the Well of Goodwill for x2 Pest Control Points!");
                                        // Broadcast.WORLD.sendNews("It is now granting double xp for the next two hours!");
                                        activateDoublePest(true, p);
                                        p.getInventory().remove(995, finalAmount);
                                        CURRENT_PEST_AMT += finalAmount;
                                        PEST_CURRENT_REMAINING = PEST_AMT_NEEDED - CURRENT_PEST_AMT;
                                        return;
                                    }

                                    p.getInventory().remove(995, finalAmount);
                                    CURRENT_PEST_AMT += finalAmount;
                                    PEST_CURRENT_REMAINING = PEST_AMT_NEEDED - CURRENT_PEST_AMT;
                                }),
                                new Option("No, I've changed my mind.")
                        )
                );
            }
        });
    }

    public static void donateBonusBm(Player p) {
        if (WellofGoodWill.isBonusbmactivated()) {
            p.dialogue(
                    new MessageDialogue("Bonus Blood Money is already active for another " + getRemainingTime()));
            return;
        }
        p.integerInput("Enter the amount of coins you'd like to donate", amount -> {

            String formattedAmount = String.format("%,.0f", (double) amount);

            if (amount > BM_MAX_AMT) {
                amount = BM_MAX_AMT;
            }

            if (amount < 500000 || p.getInventory().getAmount(995) < 500000) {
                p.retryIntegerInput("The minimum donation allowed is at least 500,000 coins.");
                return;
            } else if (p.getInventory().getAmount(995) >= amount) {
                int finalAmount = amount;
                p.dialogue(
                        new OptionsDialogue("Are you sure you want to donate " + formattedAmount + " coins?",
                                new Option("Yes, I want to donate " + Color.DARK_RED.wrap(formattedAmount) + " coins.", () -> {

                                    CURRENT_DONATION_ID++;
                                    donationsMap.put(p.name() + "___" + CURRENT_DONATION_ID, formattedAmount);

                                    //TODO log well donations - no repeats of the alerion rune boots incident pls :)
                                    if (finalAmount > 10000000) {
                                        Broadcast.WORLD.sendNews(p.name() + " has donated " + formattedAmount + " coins to the Well of Goodwill for x2 Blood Money (pvp only)!");
                                    }

                                    if (finalAmount >= BM_CURRENT_REMAINING) {
                                        Broadcast.WORLD.sendNews(p.name() + " has filled the Well of Goodwill for x2 Blood Money (pvp only)!");
                                        // Broadcast.WORLD.sendNews("It is now granting double xp for the next two hours!");
                                        activateDoubleBm(true, p);
                                        p.getInventory().remove(995, finalAmount);
                                        CURRENT_BM_AMT += finalAmount;
                                        BM_CURRENT_REMAINING = BM_AMT_NEEDED - CURRENT_BM_AMT;
                                        return;
                                    }

                                    if (finalAmount >= BM_AMT_NEEDED) {
                                        Broadcast.WORLD.sendNews(p.name() + " has filled the Well of Goodwill for x2 Blood Money (pvp only)!");
                                        // Broadcast.WORLD.sendNews("It is now granting double BM for the next two hours!");
                                        activateDoubleBm(true, p);
                                        p.getInventory().remove(995, finalAmount);
                                        CURRENT_BM_AMT += finalAmount;
                                        BM_CURRENT_REMAINING = BM_AMT_NEEDED - CURRENT_BM_AMT;
                                        return;
                                    }

                                    p.getInventory().remove(995, finalAmount);
                                    CURRENT_BM_AMT += finalAmount;
                                    BM_CURRENT_REMAINING = BM_AMT_NEEDED - CURRENT_BM_AMT;
                                }),
                                new Option("No, I've changed my mind.")
                        )
                );
            }
        });
    }

    public static void activateBonusXp(boolean activated, Player p) {
        if (activated) {
            bonusxpactivated = true;
            doubleXPTimer.delay(120 * 100); //2 Hours
            World.toggleWeekendExpBoost();
            World.startEvent(e -> {
               e.delay(120 * 100);
                bonusxpactivated = false;
                doubleXPTimer.finished();
                CURRENT_XP_AMT = 0;
                p.getPacketSender().setHidden(985, 220, false);
                p.getPacketSender().setHidden(985, 221, false);
                p.getPacketSender().setHidden(985, 222, false);
                p.getPacketSender().setHidden(985, 223, false);
                World.toggleWeekendExpBoost();
            });
        }
    }

    public static void activateDoubleSlayer(boolean activated, Player p) {
        if (activated) {
            bonusslayeractivated = true;
            doubleSlayerTimer.delay(120 * 100); //2 Hours
            World.toggleDoubleSlayer();
            World.startEvent(e -> {
                e.delay(120 * 100);
                doubleSlayerTimer.finished();
                bonusslayeractivated = false;
                CURRENT_SLAY_AMT = 0;
                p.getPacketSender().setHidden(985, 244, false);
                p.getPacketSender().setHidden(985, 245, false);
                p.getPacketSender().setHidden(985, 246, false);
                p.getPacketSender().setHidden(985, 247, false);
                World.toggleDoubleSlayer();
            });
        }
    }

    public static void activateDoubleDropRate(boolean activated, Player p) {
        if (activated) {
            bonusdropsactivated = true;
            doubleDropsTimer.delay(60 * 100); //1 Hour
            World.toggleDoubleDrops();
            World.startEvent(e -> {
               e.delay(60 * 100);
                doubleDropsTimer.finished();
                bonusdropsactivated = false;
                CURRENT_DROPS_AMT = 0;
                p.getPacketSender().setHidden(985, 268, false);
                p.getPacketSender().setHidden(985, 269, false);
                p.getPacketSender().setHidden(985, 270, false);
                p.getPacketSender().setHidden(985, 270, false);
                World.toggleDoubleDrops();
            });
        }
    }

    public static void activateDoubleVotes(boolean activated, Player p) {
        if (activated) {
            bonusvotesactivated = true;
            doubleVotesTimer.delay(60 * 100); //1 Hour
            World.toggleDoubleVotes();
            World.startEvent(e-> {
                e.delay(60 * 100);
                doubleVotesTimer.finished();
                bonusvotesactivated = false;
                CURRENT_VOTE_AMT = 0;
                p.getPacketSender().setHidden(985, 292, false);
                p.getPacketSender().setHidden(985, 293, false);
                p.getPacketSender().setHidden(985, 294, false);
                p.getPacketSender().setHidden(985, 295, false);
                World.toggleDoubleVotes();
            });
        }
    }

    public static void activateBonusRaidPts(boolean activated, Player p) {
        if (activated) {
            bonusraidsactivated = true;
            bonusRaidsTimer.delay(60 * 100); //1 Hour
            World.toggleBonusRaidPts();
            World.startEvent(e -> {
                e.delay(60 * 100);
                bonusRaidsTimer.finished();
                bonusraidsactivated = false;
                CURRENT_RAIDS_AMT = 0;
                p.getPacketSender().setHidden(985, 316, false);
                p.getPacketSender().setHidden(985, 317, false);
                p.getPacketSender().setHidden(985, 318, false);
                p.getPacketSender().setHidden(985, 319, false);
                World.toggleBonusRaidPts();
            });
        }
    }

    public static void activateDoublePest(boolean activated, Player p) {
        if (activated) {
            bonuspestactivated = true;
            doublePestTImer.delay(120 * 100); //2 Hours
            World.toggleDoublePest();
            World.startEvent(e -> {
                e.delay(120 * 100);
                doublePestTImer.finished();
                bonuspestactivated = false;
                CURRENT_PEST_AMT = 0;
                p.getPacketSender().setHidden(985, 341, false);
                p.getPacketSender().setHidden(985, 342, false);
                p.getPacketSender().setHidden(985, 343, false);
                p.getPacketSender().setHidden(985, 344, false);
                World.toggleDoublePest();
            });
        }
    }

    public static void activateDoubleBm(boolean activated, Player p) {
        if (activated) {
            bonusbmactivated = true;
            doubleBMTimer.delay(60 * 100); //1 Hour
            World.toggledoubleBloodMoney();
            World.startEvent(e -> {
                e.delay(60 * 100);
                doubleBMTimer.finished();
                bonusbmactivated = false;
                CURRENT_BM_AMT = 0;
                p.getPacketSender().setHidden(985, 365, false);
                p.getPacketSender().setHidden(985, 366, false);
                p.getPacketSender().setHidden(985, 367, false);
                p.getPacketSender().setHidden(985, 368, false);
                World.toggledoubleBloodMoney();
            });
        }
    }


}

