package net.toshimichi.zero

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.Player

class GameModeCommand : Command("gamemode") {

    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /gamemode <gamemode>")
        }

        val gamemodeArgument = ArgumentType.String("gamemode")

        addSyntax({ sender, context ->
            val gamemode = context.get(gamemodeArgument)
            if (sender !is Player) {
                sender.sendMessage("You must be a player to use this command")
                return@addSyntax
            }
            when (gamemode) {
                "creative", "c" -> {
                    sender.gameMode = GameMode.CREATIVE
                    sender.sendMessage("You are now in creative mode")
                }

                "survival", "s" -> {
                    sender.gameMode = GameMode.SURVIVAL
                    sender.sendMessage("You are now in survival mode")
                }

                "spectator", "sp" -> {
                    sender.gameMode = GameMode.SPECTATOR
                    sender.sendMessage("You are now in spectator mode")
                }

                "adventure", "a" -> {
                    sender.gameMode = GameMode.ADVENTURE
                    sender.sendMessage("You are now in adventure mode")
                }

                else -> {
                    sender.sendMessage("Invalid gamemode")
                }
            }
        }, gamemodeArgument)
    }
}
