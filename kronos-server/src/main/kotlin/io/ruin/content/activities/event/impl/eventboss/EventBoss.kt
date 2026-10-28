package io.ruin.content.activities.event.impl.eventboss

/**
 *
 * @project Kronos
 * @author ReverendDread on 5/12/2020
 * https://www.rune-server.ee/members/reverenddread/
 */
//class EventBoss(val boss: EventBossType) : TimedEventImpl {

    //val npc = NPC(boss.id)

   /* override fun onEventStart() {
        npc.hp = boss.hitpoints
        val random = Random.get(boss.positions.size - 1);
        npc.spawn(boss.positions[random])
        npc.setIgnoreMulti(true)
        npc.combat.setAllowRespawn(false)
        globalEvent { Broadcast.WORLD.worldBoss(boss.message[random]) }
        if (!OfflineMode.enabled) {
            EventBossEmbedMessage.sendDiscordMessage(boss, boss.message[random])
        }
        npc.deathEndListener = DeathListener { entity, _, killHit ->
            entity.combat.killers.forEach { (_, killer) ->
                if (killer.damage >= 100) {
                    boss.lootTable.guaranteed.forEach { GroundItem(it.id, Random.get(it.min, it.max), it.attributes).position(npc.position).owner(killer.player).spawn() }
                    repeat(boss.rolls) {
                        val rolled = boss.lootTable.rollItem()
                        GroundItem(rolled.id, rolled.amount, rolled.copyOfAttributes()).position(npc.position).owner(killer.player).spawn()
                    }
                    if (Random.rollDie(boss.petChance)) {
                        boss.pet.unlock(killer.player)
                    }
                }
            }
            globalEvent { Broadcast.WORLD.worldBoss("<col=7F00FF>${npc.def.name} has been defeated!") }
        }
    }*/

    /*override fun onEventStopped() {
        npc.remove()
        globalEvent { Broadcast.WORLD.worldBoss("<col=7F00FF>${npc.def.name} has retreated. Defeat him next time!") }
    }*/

    /*override fun tick() {
        if (!npc.isRemoved && !npc.dead()) {
            if (npc.localPlayers().isEmpty()) {
                npc.hit(Hit(HitType.HEAL).fixedDamage(50))
            }
       }
    }*/

    /*companion object {
        lateinit var npc: Any
    }

}*/