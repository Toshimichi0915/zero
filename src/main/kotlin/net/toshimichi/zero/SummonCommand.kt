package net.toshimichi.zero

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player

class SummonCommand : Command("summon") {

    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /summon <entity>")
        }

        val entityArgument = ArgumentType.EntityType("entity")
        addSyntax({ sender, context ->
            val entity = context.get(entityArgument)
            if (sender !is Player) {
                sender.sendMessage("You must be a player to use this command")
                return@addSyntax
            }
            val instance = sender.instance
            if (instance == null) {
                sender.sendMessage("You must be in a world to use this command")
                return@addSyntax
            }
            val position = sender.position
            val entityInstance = Entity(entity)
            entityInstance.setInstance(instance, position)
        }, entityArgument)
    }
}
