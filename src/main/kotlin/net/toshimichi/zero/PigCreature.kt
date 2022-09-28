package net.toshimichi.zero

import net.minestom.server.entity.EntityCreature
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.ai.EntityAIGroupBuilder
import net.minestom.server.entity.ai.goal.RandomLookAroundGoal

class PigCreature : EntityCreature(EntityType.PIG) {
    init {
        addAIGroup(
            EntityAIGroupBuilder()
                .addGoalSelector(RandomLookAroundGoal(this, 20))
                .addGoalSelector(CarrotGoal(this))
                .build()
        )
    }
}
