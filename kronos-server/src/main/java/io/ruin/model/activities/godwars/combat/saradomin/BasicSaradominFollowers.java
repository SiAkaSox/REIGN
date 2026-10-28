package io.ruin.model.activities.godwars.combat.saradomin;

import io.ruin.model.entity.npc.NPCCombat;

public class BasicSaradominFollowers extends NPCCombat {

    private int attackStyleRange;

    @Override
    public void init() {
        attackStyleRange = getAttackStyle().isMelee() ? 1 : getAttackStyle().isMagicalMelee() ? 1 : 8;

//        npc.npcAttacksNPCListener = (attacker, victim) -> {
//            if (attacker.getDef().combatHandlerClass != victim.getDef().combatHandlerClass) {
//                if (victim.getDef().combatHandlerClass != null) {
//                    if (victim.getDef().combatHandlerClass.getCanonicalName().contains("saradomin")
//                        || victim.getDef().combatHandlerClass.getCanonicalName().contains("armadyl")) {
//                        return false;
//                    }
//                }
//                return true;
//            }
//            return false;
//        };
    }

    @Override
    public void follow() {
        follow(attackStyleRange);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(attackStyleRange))
            return false;
        basicAttack();
        return true;
    }

}
