package io.ruin.model.combat.special.skilling;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

//Rock Knocker: Increase your Fishing level by 3. (100%)
public class InfernalHarpoon implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.equals("infernal harpoon");
    }

    @Override
    public boolean handleActivation(Player player) {
        if(!player.getCombat().useSpecialEnergy(100))
            return false;
        player.animate(2876);
        player.graphics(479);
        player.forceText("Burning!");
        player.getStats().get(StatType.Fishing).boost(3, 0);
        player.infernalAxeSpecial = 200;
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 100;
    }

}
