package io.ruin.model.activities.miscpvm.slayer.superiors;

import io.ruin.utility.Random;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.DeathListener;

public class ChasmCrawler extends NPCCombat {


    @Override
        public void init() {
        npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            npc.remove();
        };
    }

    private void onDeath() {
        npc.remove();
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        basicAttack();
        npc.incrementHp(1);
        if (Random.rollDie(4, 1))
            target.poison(3);
        return true;
    }
}
