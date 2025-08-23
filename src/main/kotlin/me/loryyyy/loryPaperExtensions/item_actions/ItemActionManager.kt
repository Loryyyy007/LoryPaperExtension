package me.loryyyy.loryPaperExtensions.item_actions

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

object ItemActionManager : Listener {

    fun init(plugin: JavaPlugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

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
    @EventHandler
    fun on(event: InventoryClickEvent){
        val item = event.currentItem
        if(ItemActionsRegistry.hasItemAction(item)) event.isCancelled = true
    }
}