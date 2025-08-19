package me.loryyyy.loryPaperExtensions.item_actions

import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

abstract class ItemAction(val key: String) {

    init {
        ItemActionsRegistry.register(key, this)
    }

    open fun onInteract(event: PlayerInteractEvent) {

    }

    open fun onDrop(event: PlayerDropItemEvent) {

    }

    open fun onInteractAtEntity(event: PlayerInteractAtEntityEvent) {

    }
}