package io.ruin.model.activities.skillbonus;

import io.ruin.utility.Random;
import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.utility.Broadcast;

public class BiHourlyBonusMinigame {

    private static final BiHourlyBonusMinigame[] MINIGAMES = {
            new BiHourlyBonusMinigame("Barrows", "a 10% higher chance of receiving an item!", "Barrows",1, 1.10),
            new BiHourlyBonusMinigame("Chambers of Xeric", "a 5% increased chance of rare loot!", "CoX",2, 1.05),
            new BiHourlyBonusMinigame("Last Man Standing", "double LMS points for winning!", "LMS", 3, 2.0),
            new BiHourlyBonusMinigame("Pest Control", "double Pest Control points!", "Pest Control", 4, 2.0),
            new BiHourlyBonusMinigame("Theatre of Blood", "a 5% increased chance of rare loot!", "ToB", 5, 1.05),
            new BiHourlyBonusMinigame("Wintertodt", "a 15% increased chance of rare items!", "Wintertodt", 6, 1.15)
    };

    public final String name;
    public final String description;
    public final String shortName;
    public final int eventNumber;
    public final double bonus;

    public BiHourlyBonusMinigame(String name, String description, String shortName, int eventNumber, double bonus) {
        this.name = name;
        this.description = description;
        this.shortName = shortName;
        this.eventNumber = eventNumber;
        this.bonus = bonus;
    }

    public static boolean active = false;

    public static BiHourlyBonusMinigame ACTIVE = Random.get(MINIGAMES);

    public static String MINIGAME;
    public static long currentTime;

    static {
        if (active) {
            int swapTicks = 120 * 100; //2 hours
            World.startEvent(e -> {
                while (true) {
                    BiHourlyBonusMinigame next = Random.get(MINIGAMES);
                    if (next == ACTIVE) {
                        e.delay(1);
                        continue;
                    }
                    ACTIVE = next;
                    String eventMsg = "</col></shad>The bonus minigame has changed to: <col=8b04fb>"+ACTIVE.name+"!</col> Participate for "+ACTIVE.description;
                    Broadcast.WORLD.sendWorldBoss(Icon.WILDERNESS, "", eventMsg);
                    e.delay(swapTicks);
                }
            });
        }
    }

    public static void currentMinigameSelection(Player player) {
        player.sendMessage("<img=34> The current bonus minigame is: <col=1E90FF>" + ACTIVE.name + "");
    }

    public static String sendtoEvents() {
        return "";
    }
}