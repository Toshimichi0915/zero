package net.toshimichi.zero

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType

class KickCommand : Command("kick") {

    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /kick <player>")
        }

        val playerArgument = ArgumentType.Entity("player").onlyPlayers(true)
        val reasonArgument = ArgumentType.StringArray("reason").setDefaultValue(arrayOf("You have been kicked"))

        addSyntax({ sender, context ->
            val finder = context.get(playerArgument)
            val reason = context.get(reasonArgument)
            val player = finder.findFirstPlayer(sender)
            if (player == null) {
                sender.sendMessage("Player not found")
                return@addSyntax
            }

            player.kick(reason.joinToString(" "))
        }, playerArgument, reasonArgument)
    }
}
