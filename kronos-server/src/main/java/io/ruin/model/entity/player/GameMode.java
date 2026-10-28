package io.ruin.model.entity.player;

import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.npc.actions.edgeville.StarterGuide;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.utility.Broadcast;
import io.ruin.utility.NumberUtils;
import io.ruin.utility.XenPost;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public enum GameMode {
    STANDARD(-1),
    IRONMAN(20),
    ULTIMATE_IRONMAN(21),
    HARDCORE_IRONMAN(22),
    REALISM(23);

    public final int groupId;

    GameMode(int groupId) {
        this.groupId = groupId;
    }

    public boolean isStandard() { return this == STANDARD; }
    public boolean isRealism() {
        return this == REALISM;
    }

    public boolean isAnyIron() {
        return this != STANDARD;
    }
    public boolean isRegularIronman() { return this == IRONMAN; }
    public boolean isUltimateIronman() {
        return this == ULTIMATE_IRONMAN;
    }
    public boolean isHardcoreIronman() {
        return this == HARDCORE_IRONMAN;
    }

    public static void hardcoreDeath(Player player, Hit killHit) {
        Config.IRONMAN_MODE.set(player, 1);
        player.sendMessage(Color.RED.wrap("You have fallen as a Hardcore Ironman, your Hardcore status has been revoked."));
        if (player.getStats().totalLevel >= 100) {
            String overall = NumberUtils.formatNumber(player.getStats().totalLevel);
            if (killHit == null) {
                Broadcast.GLOBAL.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.name() + " has died as a Hardcore Ironman with a total level of " + overall + "!"));
            } else if (killHit.attacker != null) {
                if (killHit.attacker instanceof Player) {
                    Broadcast.GLOBAL.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.name() + " has died as a Hardcore Ironman with a total level of " + overall + ", losing a fight to " + killHit.attacker.player.name() + "!"));
                } else {
                    Broadcast.GLOBAL.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.name() + " has died as a Hardcore Ironman with a total level of " + overall + ", brutally executed by " + killHit.attacker.npc.getDef().descriptiveName + "!"));
                }
            } else {
                if (killHit.type == HitType.POISON) {
                    Broadcast.GLOBAL.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.name() + " has been poisoned to death as a Hardcore Ironman with a total level of " + overall + "!"));
                } else if (killHit.type == HitType.VENOM) {
                    Broadcast.GLOBAL.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.name() + " has succumbed to venom as a Hardcore Ironman with a total level of " + overall + "!"));
                } else { // not sure if this can happen? can't think of anything
                    Broadcast.GLOBAL.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.name() + " has died as a Hardcore Ironman with a total level of " + overall + "!"));
                }
            }
        }
    }

    public static void hardcoreDeathByNPC(Player player, String npcName) {
        Config.IRONMAN_MODE.set(player, 1);
        changeForumsGroup(player, IRONMAN.groupId);
        player.sendMessage(Color.RED.wrap("You have fallen as a Hardcore Ironman, your Hardcore status has been revoked."));
        if (player.getStats().totalLevel >= 1) { // hmm
            String overall = NumberUtils.formatNumber(player.getStats().totalLevel);
            Broadcast.GLOBAL.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.name() + " has died as a Hardcore Ironman with a total level of " + overall + ", brutally executed by " + npcName + "!"));
        }
    }

    public static void openSelection(Player player) {
       handleNormalInter(player);
    }

    public static int INTERFACE_ID = 929;

    public static boolean selectedEasyxp;
    public static boolean selectedMediumxp;
    public static boolean selectedHardxp;
    public static boolean selectedRealismXp;
    public static boolean selectedNormalMode;
    public static boolean selectedRealismMode;
    public static boolean selectedIronmanMode;
    public static boolean selectedHcimMode;
    public static boolean selectedUimMode;

    public static void resetModeSelections(Player p) {
        selectedNormalMode = false;selectedRealismMode= false;
        selectedIronmanMode= false;selectedHcimMode= false;selectedUimMode = false;
        p.getPacketSender().setHidden(INTERFACE_ID, 509, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 510, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 484, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 483, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 423, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 514, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 515, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 516, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 517, true);
        p.getPacketSender().sendString(INTERFACE_ID, 452, "500/25 (0% DR Boost");
        p.getPacketSender().sendString(INTERFACE_ID, 453, "125/25 (3% DR Boost");
        p.getPacketSender().sendString(INTERFACE_ID, 454, "40/25 (5% DR Boost");
    }
    public static void resetXpSelections(Player p) {
        selectedEasyxp = false;selectedMediumxp = false;selectedHardxp = false;
        p.getPacketSender().setHidden(INTERFACE_ID, 507, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 509, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 510, true);
    }

    public static void addDelayTicks(Player p) {
        p.gameModeTicks += 3;
        p.gameModeTicks--;
    }

    public static void handleNormalInter(Player p) {
        if (p.gameModeTicks > 0) {
            return;
        }
        addDelayTicks(p);
        resetModeSelections(p);
        resetXpSelections(p);
        selectedNormalMode = true;
        p.getPacketSender().setHidden(INTERFACE_ID, 513, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 423, false);
        p.openInterface(InterfaceType.MAIN, INTERFACE_ID);
        p.getPacketSender().sendClientScript(INTERFACE_ID, "ii", -1, -1);
        p.getPacketSender().sendString(INTERFACE_ID, 508, ""+p.name());
        p.getPacketSender().sendString(INTERFACE_ID, 493, "Normal Mode");
        p.getPacketSender().sendString(INTERFACE_ID, 494, "Normal game mode with the ability to spawn basic pking items and change combat stats.");
        p.getPacketSender().sendString(INTERFACE_ID, 503, "Set combat stats (ONLY the 500/50 xp mode can set combat stats. Other xp modes cannot.)");
        p.getPacketSender().sendString(INTERFACE_ID, 504, "");
        p.getPacketSender().sendString(INTERFACE_ID, 505, "");
        p.getPacketSender().setHidden(INTERFACE_ID, 498, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 499, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 500, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 501, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 518, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 519, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 520, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 521, true);

        Item helm = new Item(1153);
        Item chest = new Item(1115);
        Item legs = new Item(1067);
        Item sword = new Item(1323);
        Item shield = new Item(1191);
        Item boots = new Item(3105);
        Item cape = new Item(4413);
        Item amulet = new Item(1712);
        p.getEquipment().clear();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, sword);
        p.getEquipment().set(5, shield);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getMovement().reset();
        p.getEquipment().sendUpdates();
    }
    public static void handleRealismInter(Player p) {
        if (p.gameModeTicks > 0) {
            return;
        }
        addDelayTicks(p);
        resetModeSelections(p);
        resetXpSelections(p);
        selectedRealismMode = true;
        p.openInterface(InterfaceType.MAIN, INTERFACE_ID);
        p.getPacketSender().sendString(INTERFACE_ID, 452, "5/5 (10% DR Boost)");
        p.getPacketSender().sendString(INTERFACE_ID, 453, "");
        p.getPacketSender().sendString(INTERFACE_ID, 454, "");
        p.getPacketSender().setHidden(INTERFACE_ID, 513, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 514, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 515, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 516, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 517, true);
        //p.sendMessage("This mode is currently a work in progress.");
        p.getPacketSender().sendClientScript(INTERFACE_ID, "ii", -1, -1);
        p.getPacketSender().setHidden(INTERFACE_ID, 507, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 509, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 510, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 484, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 483, true);
        p.getPacketSender().sendString(INTERFACE_ID, 508, ""+p.name());
        p.getPacketSender().sendString(INTERFACE_ID, 493, "Realism Mode");
        p.getPacketSender().sendString(INTERFACE_ID, 494, "Challenge yourself by earning only x5 XP rate and only trading other realism players.");
        p.getPacketSender().sendString(INTERFACE_ID, 503, "Highest drop rate boost");
        p.getPacketSender().sendString(INTERFACE_ID, 504, "Trade/loot normal accounts");
        p.getPacketSender().sendString(INTERFACE_ID, 505, "Gamble/duel normal accounts");
        p.getPacketSender().setHidden(INTERFACE_ID, 498, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 499, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 500, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 501, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 518, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 519, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 520, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 521, false);
        Item helm = new Item(1139);
        Item chest = new Item(1103);
        Item legs = new Item(1087);
        Item sword = new Item(1277);
        Item shield = new Item(1171);
        Item boots = new Item(9006);
        Item cape = new Item(4393);
        Item amulet = new Item(1478);
        p.getEquipment().clear();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, sword);
        p.getEquipment().set(5, shield);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getMovement().reset();
        p.getEquipment().sendUpdates();
    }
    public static void handleIronmanInter(Player p) {
        if (p.gameModeTicks > 0) {
            return;
        }
        addDelayTicks(p);
        resetModeSelections(p);
        resetXpSelections(p);
        selectedIronmanMode = true;
        p.openInterface(InterfaceType.MAIN, INTERFACE_ID);
        p.getPacketSender().setHidden(INTERFACE_ID, 513, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 514, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 515, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 516, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 517, true);
        p.getPacketSender().sendClientScript(INTERFACE_ID, "ii", -1, -1);
        p.getPacketSender().sendString(INTERFACE_ID, 508, ""+p.name());
        p.getPacketSender().sendString(INTERFACE_ID, 493, "Ironman Mode");
        p.getPacketSender().sendString(INTERFACE_ID, 494, "Ironman game mode with normal ironman restrictions. Speak to Adam to revert to Normal.");
        p.getPacketSender().sendString(INTERFACE_ID, 503, "Set combat stats");
        p.getPacketSender().sendString(INTERFACE_ID, 504, "Trade/loot other accounts");
        p.getPacketSender().sendString(INTERFACE_ID, 505, "Gamble/duel other accounts");
        p.getPacketSender().setHidden(INTERFACE_ID, 498, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 499, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 500, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 501, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 518, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 519, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 520, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 521, false);
        Item helm = new Item(12810);
        Item chest = new Item(12811);
        Item legs = new Item(12812);
        Item sword = new Item(9703);
        Item shield = new Item(9704);
        Item boots = new Item(1061);
        Item cape = new Item(4413);
        Item amulet = new Item(1478);
        p.getEquipment().clear();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, sword);
        p.getEquipment().set(5, shield);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getMovement().reset();
        p.getEquipment().sendUpdates();
    }
    public static void handleHCIMInter(Player p) {
        if (p.gameModeTicks > 0) {
            return;
        }
        addDelayTicks(p);
        resetModeSelections(p);
        resetXpSelections(p);
        selectedHcimMode = true;
        p.openInterface(InterfaceType.MAIN, INTERFACE_ID);
        p.getPacketSender().setHidden(INTERFACE_ID, 513, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 514, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 515, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 516, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 517, true);
        p.getPacketSender().sendClientScript(INTERFACE_ID, "ii", -1, -1);
        p.getPacketSender().sendString(INTERFACE_ID, 508, ""+p.name());
        p.getPacketSender().sendString(INTERFACE_ID, 493, "HCIM Mode");
        p.getPacketSender().sendString(INTERFACE_ID, 494, "Hardcore Ironman game mode with ironman restrictions, and only one life!");
        p.getPacketSender().sendString(INTERFACE_ID, 503, "Set combat stats");
        p.getPacketSender().sendString(INTERFACE_ID, 504, "Trade/loot other accounts");
        p.getPacketSender().sendString(INTERFACE_ID, 505, "Gamble/duel other accounts");
        p.getPacketSender().setHidden(INTERFACE_ID, 498, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 499, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 500, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 501, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 518, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 519, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 520, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 521, false);
        Item helm = new Item(20792);
        Item chest = new Item(20794);
        Item legs = new Item(20796);
        Item sword = new Item(9703);
        Item shield = new Item(9704);
        Item boots = new Item(1061);
        Item cape = new Item(4413);
        Item amulet = new Item(1478);
        p.getEquipment().clear();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, sword);
        p.getEquipment().set(5, shield);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getMovement().reset();
        p.getEquipment().sendUpdates();
    }
    public static void handleUIMInter(Player p) {
        if (p.gameModeTicks > 0) {
            return;
        }
        addDelayTicks(p);
        resetModeSelections(p);
        resetXpSelections(p);
        selectedUimMode = true;
        p.openInterface(InterfaceType.MAIN, INTERFACE_ID);
        p.getPacketSender().setHidden(INTERFACE_ID, 513, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 514, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 515, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 516, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 517, false);
        p.getPacketSender().sendClientScript(INTERFACE_ID, "ii", -1, -1);
        p.getPacketSender().sendString(INTERFACE_ID, 508, ""+p.name());
        p.getPacketSender().sendString(INTERFACE_ID, 493, "UIM Mode");
        p.getPacketSender().sendString(INTERFACE_ID, 494, "UIM game mode with normal ironman restrictions, plus no use of banking/storing items.");
        p.getPacketSender().sendString(INTERFACE_ID, 503, "Set combat stats");
        p.getPacketSender().sendString(INTERFACE_ID, 504, "Trade/loot other accounts");
        p.getPacketSender().sendString(INTERFACE_ID, 505, "Gamble/duel other accounts");
        p.getPacketSender().setHidden(INTERFACE_ID, 498, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 499, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 500, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 501, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 518, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 519, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 520, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 521, false);
        Item helm = new Item(12813);
        Item chest = new Item(12814);
        Item legs = new Item(12815);
        Item sword = new Item(9703);
        Item shield = new Item(9704);
        Item boots = new Item(1061);
        Item cape = new Item(4413);
        Item amulet = new Item(1478);
        p.getEquipment().clear();
        p.getEquipment().set(0, helm);
        p.getEquipment().set(4, chest);
        p.getEquipment().set(7, legs);
        p.getEquipment().set(3, sword);
        p.getEquipment().set(5, shield);
        p.getEquipment().set(10, boots);
        p.getEquipment().set(1, cape);
        p.getEquipment().set(2, amulet);
        p.getMovement().reset();
        p.getEquipment().sendUpdates();
    }


    //Xp Mode Selections
    public static void handleEasyXp(Player p) {
        p.getPacketSender().setHidden(INTERFACE_ID, 507, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 509, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 510, true);
        if (selectedRealismMode) {
            selectedEasyxp = false;
            selectedMediumxp = false;
            selectedHardxp = false;
            selectedRealismXp = true;
        }
        selectedEasyxp = true;
        selectedMediumxp = false;
        selectedHardxp = false;
    }
    public static void handleMediumXp(Player p) {
        p.getPacketSender().setHidden(INTERFACE_ID, 507, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 509, false);
        p.getPacketSender().setHidden(INTERFACE_ID, 510, true);
        selectedEasyxp = false;
        selectedMediumxp = true;
        selectedHardxp = false;
    }
    public static void handleHardXp(Player p) {
        p.getPacketSender().setHidden(INTERFACE_ID, 507, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 509, true);
        p.getPacketSender().setHidden(INTERFACE_ID, 510, false);
        selectedEasyxp = false;
        selectedMediumxp = false;
        selectedHardxp = true;
    }

    public static void createAccountButton(Player p) {
        if (selectedNormalMode) {
            if (!selectedHardxp && !selectedMediumxp && !selectedEasyxp) {
                p.sendMessage("<col=d22222>You must select an XP mode before continuing!");
                return;
            }
            if (selectedEasyxp) {
                XpMode.setXpMode(p, XpMode.EASY);
            }
            if (selectedMediumxp) {
                XpMode.setXpMode(p, XpMode.MEDIUM);
            }
            if (selectedHardxp) {
                XpMode.setXpMode(p, XpMode.HARD);
            }
            Config.IRONMAN_MODE.set(p, 0);
            StarterGuide.continueTut(p);
            p.ispvpMode = false;
            p.isRealism = false;
        }
        if (selectedRealismMode) {

            XpMode.setXpMode(p, XpMode.REALISM);

            Config.IRONMAN_MODE.set(p, 0);
            StarterGuide.continueTut(p);
            p.ispvpMode = false;
            p.isRealism = true;
        }
        if (selectedIronmanMode) {
            if (!selectedHardxp && !selectedMediumxp && !selectedEasyxp) {
                p.sendMessage("<col=d22222>You must select an XP mode before continuing!");
                return;
            }
            if (selectedEasyxp) {
                XpMode.setXpMode(p, XpMode.EASY);
            }
            if (selectedMediumxp) {
                XpMode.setXpMode(p, XpMode.MEDIUM);
            }
            if (selectedHardxp) {
                XpMode.setXpMode(p, XpMode.HARD);
            }
            Config.IRONMAN_MODE.set(p, 1);
            StarterGuide.continueTut(p);
            p.ispvpMode = false;
            p.isRealism = false;
        }
        if (selectedHcimMode) {
            if (!selectedHardxp && !selectedMediumxp && !selectedEasyxp) {
                p.sendMessage("<col=d22222>You must select an XP mode before continuing!");
                return;
            }
            if (selectedEasyxp) {
                XpMode.setXpMode(p, XpMode.EASY);
            }
            if (selectedMediumxp) {
                XpMode.setXpMode(p, XpMode.MEDIUM);
            }
            if (selectedHardxp) {
                XpMode.setXpMode(p, XpMode.HARD);
            }
            Config.IRONMAN_MODE.set(p, 3);
            StarterGuide.continueTut(p);
            p.ispvpMode = false;
            p.isRealism = false;
        }
        if (selectedUimMode) {
            if (!selectedHardxp && !selectedMediumxp && !selectedEasyxp) {
                p.sendMessage("<col=d22222>You must select an XP mode before continuing!");
                return;
            }
            if (selectedEasyxp) {
                XpMode.setXpMode(p, XpMode.EASY);
            }
            if (selectedMediumxp) {
                XpMode.setXpMode(p, XpMode.MEDIUM);
            }
            if (selectedHardxp) {
                XpMode.setXpMode(p, XpMode.HARD);
            }
            Config.IRONMAN_MODE.set(p, 2);
            StarterGuide.continueTut(p);
            p.ispvpMode = false;
            p.isRealism = false;
        }
    }

    public static void changeForumsGroup(Player player, int mode) {
        CompletableFuture.runAsync(() -> {
            Map<Object, Object> map = new HashMap<>();
            map.put("userId", player.getUserId());
            map.put("type", "ironman_mode");
            map.put("groupId", mode);
            XenPost.post("add_group", map);
        });
    }

    static {

            InterfaceHandler.register(INTERFACE_ID, h -> {
                h.actions[388] = (SimpleAction) GameMode::handleNormalInter;
                h.actions[513] = (SimpleAction) GameMode::handleNormalInter;
                h.actions[389] = (SimpleAction) GameMode::handleRealismInter;
                h.actions[390] = (SimpleAction) GameMode::handleIronmanInter;
                h.actions[515] = (SimpleAction) GameMode::handleIronmanInter;
                h.actions[391] = (SimpleAction) GameMode::handleHCIMInter;
                h.actions[392] = (SimpleAction) GameMode::handleUIMInter;
                h.actions[517] = (SimpleAction) GameMode::handleUIMInter;
                h.actions[423] = (SimpleAction) GameMode::handleNormalInter;
                h.actions[424] = (SimpleAction) GameMode::handleRealismInter;
                h.actions[425] = (SimpleAction) GameMode::handleIronmanInter;
                h.actions[426] = (SimpleAction) GameMode::handleHCIMInter;
                h.actions[427] = (SimpleAction) GameMode::handleUIMInter;

                h.actions[482] = (SimpleAction) GameMode::handleEasyXp;
                h.actions[452] = (SimpleAction) GameMode::handleEasyXp;
                h.actions[483] = (SimpleAction) GameMode::handleMediumXp;
                h.actions[453] = (SimpleAction) GameMode::handleMediumXp;
                h.actions[484] = (SimpleAction) GameMode::handleHardXp;
                h.actions[454] = (SimpleAction) GameMode::handleHardXp;

                h.actions[495] = (SimpleAction) GameMode::createAccountButton;
            });

            InterfaceHandler.register(215, h -> {
                h.actions[10] = (SimpleAction) p -> Config.IRONMAN_MODE.set(p, 0);
                h.actions[11] = (SimpleAction) p -> {
                    Config.IRONMAN_MODE.set(p, 1);
                    changeForumsGroup(p, IRONMAN.groupId);
                };
                h.actions[12] = (SimpleAction) p -> {
                    Config.IRONMAN_MODE.set(p, 3);
                    changeForumsGroup(p, HARDCORE_IRONMAN.groupId);
                };
                h.actions[13] = (SimpleAction) p -> {
                    Config.IRONMAN_MODE.set(p, 2);
                    changeForumsGroup(p, ULTIMATE_IRONMAN.groupId);
                };

            });
    }
}
