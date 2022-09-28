package net.toshimichi.zero

import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.metadata.animal.PigMeta
import net.minestom.server.event.player.PlayerEntityInteractEvent
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.event.player.PlayerPacketEvent
import net.minestom.server.instance.block.Block
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.network.packet.client.play.ClientSteerVehiclePacket

fun main(args: Array<String>) {
    val server = MinecraftServer.init()
    val instanceManager = MinecraftServer.getInstanceManager()
    val instanceContainer = instanceManager.createInstanceContainer()
    val globalEventHandler = MinecraftServer.getGlobalEventHandler()
    val commandManager = MinecraftServer.getCommandManager()

    instanceContainer.setGenerator { it.modifier().fillHeight(0, 40, Block.STONE) }

    // spawn
    globalEventHandler.addListener(PlayerLoginEvent::class.java) {
        it.setSpawningInstance(instanceContainer)
        it.player.respawnPoint = Pos(0.0, 42.0, 0.0)
    }

    // saddle equipment & mount
    globalEventHandler.addListener(PlayerEntityInteractEvent::class.java) {
        val meta = it.target.entityMeta
        if (meta !is PigMeta) return@addListener

        if (it.player.itemInMainHand.material() == Material.SADDLE) {
            it.player.itemInMainHand = ItemStack.AIR
            meta.isHasSaddle = true
        } else {
            if (!meta.isHasSaddle) return@addListener
            it.target.addPassenger(it.player)
        }
    }

    // dismount
    globalEventHandler.addListener(PlayerPacketEvent::class.java) {
        val packet = it.packet
        if (packet !is ClientSteerVehiclePacket) return@addListener
        if (packet.flags() != 2.toByte()) return@addListener
        val vehicle = it.player.vehicle ?: return@addListener
        vehicle.removePassenger(it.player)
    }

    commandManager.register(GameModeCommand())
    commandManager.register(GiveCommand())
    commandManager.register(KickCommand())
    commandManager.register(StopCommand())
    commandManager.register(SummonCommand())
    server.start("0.0.0.0", 25565)
}
