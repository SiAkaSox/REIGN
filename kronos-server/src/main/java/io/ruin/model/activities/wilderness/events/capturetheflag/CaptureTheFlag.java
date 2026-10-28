package io.ruin.model.activities.wilderness.events.capturetheflag;

import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;

import java.util.Objects;

public class CaptureTheFlag {

    public static String flagHolder;
    public static CaptureTheFlagLocation flagLocation;
    public static GameObject flag;

    private static final int FLAG_ID = 50005;

    public static boolean isActive() {
        return flagLocation != null;
    }

    private static void spawnFlag() {
        flag = GameObject.spawn(FLAG_ID, flagLocation.getPosition().getX(), flagLocation.getPosition().getY(), 0, 10, 0);
    }

    public static boolean isCaptured() {
        return flagHolder != null;
    }

    public static void updateOverlay(Player p) {
        if (isCaptured()) {
            p.getPacketSender().sendString(859, 138, "Timer: <col=27ae60>Captured!");
            return;
        }
        p.getPacketSender().sendString(859, 138, "Timer: " + (isActive() ? "<col=27ae60>Now!" : "<col=ff0000>Not Active"));
    }

    public static void endEvent(boolean handedIn) {
        if (flagLocation == null) {
            System.err.println("already ended..");
            return;
        }
        if (flag != null) {
            flag.remove();
        }
        /** Force deletes the flag from the player. **/
        for (Player p : World.players) {
            if (p == null)
                continue;
            if (p.getInventory().contains(FLAG_ID)) {
                p.getInventory().remove(FLAG_ID, 1);
            }
            if (p.getEquipment().getWeaponId() == FLAG_ID) {
                p.getEquipment().remove(FLAG_ID, 1);
            }
        }
        flag = null;
        flagLocation = null;
        flagHolder = null;
        if (handedIn) {
            Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", "The flag was captured and turned in to the Arena Master!");
            return;
        }
        Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", "The Capture the Flag event has ended! nobody returned the flag!");
    }

    public static void begin() {
        flagLocation = CaptureTheFlagLocation.values()[Misc.random(CaptureTheFlagLocation.values().length - 1)];
        String eventMessage = "The Capture the Flag event has started " + flagLocation.getHint() + "!";
        spawnFlag();
        //event timeout
        World.startEvent(e -> {
            e.delay(100 * 20);
            endEvent(false);
        });
    }

    static {
        ObjectAction.register(FLAG_ID, 1, (player, obj) -> captureFlag(player));
    }

    public static void captureFlag(Player player) {
        if (player.getCombat().getLevel() < 126) {
            player.dialogue(
                    new MessageDialogue("You must be 126 combat level to capture the flag!")
            );
            return;
        }
        Item flag_item = new Item(8970, 1);
        if (!player.getEquipment().canWield(flag_item)) {
            player.sendMessage("You need 2 free inventory slot to take the flag!");
            return;
        }
        if (flag == null || flag.isRemoved())
            return;
        flag.remove();
        flag = null;
        player.getEquipment().equip(flag_item);
        flagHolder = player.name();
        player.getCombat().teleblock();
        player.getCombat().skullNormal();
        player.getPrayer().deactivateAll();
        player.getStats().get(StatType.Prayer).drain(Misc.random(25, 90));
        player.getStats().get(StatType.Attack).drain(Misc.random(25, 90));
        player.getStats().get(StatType.Strength).drain(Misc.random(25, 90));
        player.getStats().get(StatType.Defence).drain(Misc.random(25, 90));
        player.getStats().get(StatType.Magic).drain(Misc.random(25, 90));
        player.getStats().get(StatType.Ranged).drain(Misc.random(25, 90));
        player.sendMessage("You have Captured the Flag! Return it to the Arena Master in Edgeville for a reward!");
        player.sendMessage("You have been high-risk skulled and teleblocked!");
        String eventMessage = player.name() + " has captured the flag " + flagLocation.getHint() + ", kill them to retrieve the flag!";
        Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", eventMessage);
    }

    public static void checkDeath(Player p) {
        if (flagHolder == null)
            return;
        if (!isFlagHolder(p))
            return;
        flag = GameObject.spawn(FLAG_ID, p.getAbsX(), p.getAbsY(), p.getHeight(), 10, 0);
        flagHolder = null;
        String eventMessage = p.name() + " has died with the flag at Level: " + p.wildernessLevel + " Wilderness, retrieve the flag!";
        Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", eventMessage);
    }

    public static boolean isFlagHolder(Player player) {
        return flagHolder != null && Objects.equals(flagHolder, player.name());
    }
}