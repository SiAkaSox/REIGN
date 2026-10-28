package io.ruin.utility;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Common {

    /**
     * Checks if player has minimum level(s)*
     *
     * @param p
     * @param minimumLevel
     * @param boostsAllowed
     * @param stat
     * @return
     */
    public static boolean hasStatRequirements(Player p, int minimumLevel, boolean boostsAllowed, StatType... stat) {
        for (StatType s : stat) {
            final int mylvl = boostsAllowed ? p.getStats().get(s).currentLevel : p.getStats().get(s).fixedLevel;
            if (mylvl < minimumLevel) {
                p.sendMessage("You need " + s.descriptiveName + " level of " + minimumLevel + " to use this.");
                return false;
            }
        }
        return true;
    }

    public static final void openDoorEffects(Player p) {
        p.privateSound(62, 1, 25);
        p.animate(4282);
    }

    // the system magic skillcape, nex, etc uses
    public static String getRemainingTime(Long attribute) {
        long ms = attribute - System.currentTimeMillis();
        long hours = TimeUnit.MILLISECONDS.toHours(ms);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);

        return (hours >= 1 ? (hours + " hour" + (hours > 1 ? "s" : "") + " and ") : "") +
                Math.max((minutes - TimeUnit.HOURS.toMinutes(hours)), 1) + " minute" +
                ((minutes - TimeUnit.HOURS.toMinutes(hours)) > 1 ? "s" : "");
    }

    public static int convertSecondsToTicksInt(int seconds) {
        return (int) convertSecondsToTicks(seconds);
    }

    public static double convertSecondsToTicks(int seconds) {
        return (double) seconds / 0.6;
    }

    public static boolean clippingIsEmpty(Position pos) {
        final Tile tile = Tile.get(pos);
        if (tile == null || tile.clipping != 0) {
            return false;
        }
        return true;
    }

    public static int countNonNullSpawnedObjects(List<GameObject> objects) {
        int count = 0;
        for (GameObject s : objects) {
            if (s != null) {
                if (!s.isRemoved()) {
                    if (s.isSpawned()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Format usernames, etc*
     *
     * @param s
     * @return
     */
    public static String formatNoun(String s) {
        if (s.length() <= 1) {
            return s.toUpperCase(); // if name is "g" or someshit, return "G".
        }
            String newStr = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            return newStr;
    }

    public static void sendTwoItemMessage(Player player, Item item, Item item2, String message) {
        player.dialogue(new ItemDialogue().two((item.getDef().isNote() ? item.getId() - 1 : item.getId()), item2.getDef().isNote() ? item2.getId() - 1 : item2.getId(), message));
    }

}


