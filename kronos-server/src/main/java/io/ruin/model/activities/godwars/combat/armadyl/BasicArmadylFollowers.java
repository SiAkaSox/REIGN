package io.ruin.model.activities.godwars.combat.armadyl;

import io.ruin.model.entity.npc.NPCCombat;

public class BasicArmadylFollowers  extends NPCCombat {

    @Override
    public void init() {
//        npc.npcAttacksNPCListener = (attacker, victim) -> {
//            if (attacker.getDef().combatHandlerClass != victim.getDef().combatHandlerClass) {
//                if (victim.getDef().combatHandlerClass != null) {
//                    return !(
//                            victim.getDef().combatHandlerClass.getName().contains("armadyl") ||
//                                    victim.getDef().combatHandlerClass.getName().contains("aviansie") ||
//                                    victim.getDef().combatHandlerClass.getName().contains("spiritual")
//                    );
//                }
//                return true;
//            }
//            return false;
//        };
    }

    @Override
    public void follow() {
        follow(getAttackStyle().isMelee() ? 1 : 8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(getAttackStyle().isMelee() ? 1 : 8))
            return false;
        basicAttack();
        return true;
    }

}