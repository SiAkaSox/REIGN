package io.ruin.model.entity.shared.listeners

import io.ruin.model.entity.npc.NPC
import java.util.function.BiFunction

/**
 * @author Shadowrs tardisfan121@gmail.com
 */
object SpawnListeners {

    fun runFor(npc: NPC) {
        triggers.get(npc.id)?.forEach { it.handle(npc) }
        globals.forEach { it.handle(npc) }
    }

    fun register(id: Int, action: SpawnListener) {
        triggers.compute(id, BiFunction { t, u ->
            if (u == null)
                mutableListOf(action)
            else {
                u.add(action)
                u
            }
        })
    }

    val globals = mutableListOf<SpawnListener>()

    val triggers = HashMap<Int, MutableList<SpawnListener>>()
}