package net.toshimichi.zero

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player

class GiveCommand : Command("give") {

    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /give <item> [amount]")
        }

        val itemArgument = ArgumentType.ItemStack("item")
        val numberArgument = ArgumentType.Integer("amount").setDefaultValue(1)

        addSyntax({ sender, context ->
            val item = context.get(itemArgument)
            val amount = context.get(numberArgument)
            if (sender !is Player) {
                sender.sendMessage("You must be a player to use this command")
                return@addSyntax
            }
            sender.inventory.addItemStack(item.withAmount(amount))
            sender.sendMessage("You got ${item.material()} x$amount")
        }, itemArgument, numberArgument)
    }
}
