package io.ruin.model.activities.skillbonus;

import com.google.common.collect.Lists;
import io.ruin.utility.StringUtils;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.utility.Misc;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BiHourlyBonusSkill {

    public static SkillBonus skillBonus;

    public static boolean isActive() {
        return skillBonus != null;
    }

    static {
        World.startTask(e -> {
            selectRandomSkill();
            e.sleep(TimeUnit.HOURS.toMillis(2));
       });
    }

    public static SkillBonus selectRandomSkill() {
        List<SkillBonusData> list = Lists.newArrayList();
        for (SkillBonusData bonus : SkillBonusData.values()) {
            if (!bonus.allowBoost)
                continue;
            list.add(bonus);
        }
        SkillBonusData skill = list.get(Misc.random(list.size() - 1));
        skillBonus = new SkillBonus(StringUtils.capitalizeFirst(skill.name().toLowerCase()), skill.ordinal(), skill.allowBoost);
        World.players.nonNullStream().filter(p -> p.isOnline()).forEach(p -> p.sendMessage("<img=34> The current 25% bonus skill is: <col=1E90FF>" +skillBonus.name));
        return skillBonus;
    }

    public static void skillOfTheDayMessage(Player player) {
        if (!isActive())
            return;
        player.sendMessage("<img=34> The current 25% bonus skill is: <col=1E90FF>" +skillBonus.getName());
    }

    public static String sendtoEvents() {
        if (skillBonus == null)
            return "N/A";
        return skillBonus.getName();
    }
}