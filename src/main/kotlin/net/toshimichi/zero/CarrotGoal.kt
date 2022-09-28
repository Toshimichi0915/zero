package net.toshimichi.zero

import net.minestom.server.entity.EntityCreature
import net.minestom.server.entity.Player
import net.minestom.server.entity.ai.GoalSelector
import net.minestom.server.item.Material

class CarrotGoal(entityCreature: EntityCreature) : GoalSelector(entityCreature) {

    private fun doesPassengerHaveStick(): Boolean {
        return entityCreature.passengers
            .stream()
            .filter { it is Player }
            .map { it as Player }
            .anyMatch { passenger -> passenger.itemInMainHand.material() == Material.CARROT_ON_A_STICK }
    }

    override fun shouldStart(): Boolean {
        return doesPassengerHaveStick()
    }

    override fun start() {
    }

    override fun tick(time: Long) {
        val passengers = entityCreature.passengers
        if (passengers.isEmpty()) return
        entityCreature.velocity = passengers.first().position.direction()
    }

    override fun shouldEnd(): Boolean {
        return !doesPassengerHaveStick()
    }

    override fun end() {
    }
}
