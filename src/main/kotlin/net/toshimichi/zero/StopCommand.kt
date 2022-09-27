package net.toshimichi.zero

import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType

class StopCommand : Command("stop") {

    init {
        val reasonArgument = ArgumentType.StringArray("reason").setDefaultValue(arrayOf("Server closed"))

        addSyntax({ sender, context ->
            val reason = context.get(reasonArgument)
            sender.sendMessage("Stopping the server...")
            for (player in MinecraftServer.getConnectionManager().onlinePlayers) {
                player.kick(reason.joinToString(" "))
            }

            MinecraftServer.getSchedulerManager().scheduleNextTick {
                MinecraftServer.stopCleanly()
            }
        }, reasonArgument)
    }
}
