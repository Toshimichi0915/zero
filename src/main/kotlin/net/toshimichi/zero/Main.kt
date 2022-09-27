package net.toshimichi.zero

import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.instance.block.Block

fun main(args: Array<String>) {
    val server = MinecraftServer.init()
    val instanceManager = MinecraftServer.getInstanceManager()
    val instanceContainer = instanceManager.createInstanceContainer()
    val globalEventHandler = MinecraftServer.getGlobalEventHandler()
    val commandManager = MinecraftServer.getCommandManager()

    instanceContainer.setGenerator { it.modifier().fillHeight(0, 40, Block.STONE) }
    globalEventHandler.addListener(PlayerLoginEvent::class.java) {
        it.setSpawningInstance(instanceContainer)
        it.player.respawnPoint = Pos(0.0, 42.0, 0.0)
    }

    commandManager.register(GameModeCommand())
    commandManager.register(GiveCommand())
    commandManager.register(KickCommand())
    commandManager.register(StopCommand())
    commandManager.register(SummonCommand())
    server.start("0.0.0.0", 25565)
}
