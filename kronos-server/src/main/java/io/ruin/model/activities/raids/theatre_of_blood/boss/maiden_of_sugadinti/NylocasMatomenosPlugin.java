package io.ruin.model.activities.raids.theatre_of_blood.boss.maiden_of_sugadinti;

import io.ruin.model.activities.raids.theatre_of_blood.TheatreNPC;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreOfBlood;
import io.ruin.model.activities.raids.theatre_of_blood.TheatrePartiesManager;
import io.ruin.model.activities.raids.theatre_of_blood.TheatreParty;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.ClipUtils;
import io.ruin.model.map.Position;
import io.ruin.model.map.Region;
import io.ruin.model.map.Tile;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.route.RouteFinder;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NylocasMatomenosPlugin extends TheatreNPC {

    private TheatreNPC maiden;

    public NylocasMatomenosPlugin(Position base, TheatreNPC maiden) {
        super(8385, base);
        this.maiden = maiden;
        getRouteFinder().customClipUtils = ClipUtils.NONE;
        animate(8098);

    }

    @Override
    public NylocasMatomenosCombat getCombat() {
        if (combat == null || !(combat instanceof NylocasMatomenosCombat)) {
            combat = new NylocasMatomenosCombat(this);
            combat.init(this, getDef().combatInfo);
            combat.retaliating = false;
        }
        return (NylocasMatomenosCombat) combat;
    }


    @Override
    public void process() {
        super.process();
        if (maiden.getCentrePosition().distance(getPosition()) <= 3 && !this.dead()) {
            int currentHP = getHp();
            maiden.hit(new Hit(HitType.HEAL).fixedDamage(currentHP));
            this.hit(new Hit().fixedDamage(currentHP));
        }
    }

    /**
     * @return the maiden
     */
    public TheatreNPC getMaiden() {
        return maiden;
    }
}
