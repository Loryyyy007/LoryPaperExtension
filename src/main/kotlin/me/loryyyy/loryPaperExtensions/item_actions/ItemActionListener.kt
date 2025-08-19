package me.loryyyy.loryPaperExtensions.item_actions

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

object ItemActionListener : Listener {

    @EventHandler
    fun on(event: PlayerInteractEvent) {
        val item = event.item ?: return
        ItemActionsRegistry.handleInteraction(item, event)
    }

    @EventHandler
    fun on(event: PlayerDropItemEvent) {
        val item = event.itemDrop.itemStack
        ItemActionsRegistry.handleInteraction(item, event)
    }

    @EventHandler
    fun on(event: PlayerInteractAtEntityEvent) {
        val item = event.player.inventory.itemInMainHand
        ItemActionsRegistry.handleInteraction(item, event)
    }
}