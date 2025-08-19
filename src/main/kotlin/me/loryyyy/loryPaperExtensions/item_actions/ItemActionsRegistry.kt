package me.loryyyy.loryPaperExtensions.item_actions

import me.loryyyy.loryPaperExtensions.LoryPaperExtensions
import org.bukkit.NamespacedKey
import org.bukkit.event.Cancellable
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object ItemActionsRegistry {

    private val itemActions = mutableMapOf<String, ItemAction>()
    val ITEM_TAG_KEY = NamespacedKey(LoryPaperExtensions.plugin, "arsenal_item_tag")

    fun register(tag: String, action: ItemAction) {
        itemActions[tag] = action
    }

    fun handleInteraction(item: ItemStack?, event: PlayerEvent) {
        val meta = item?.itemMeta ?: return
        val tag = meta.persistentDataContainer.get(ITEM_TAG_KEY, PersistentDataType.STRING) ?: return

        val action = itemActions[tag] ?: return
        if (event is Cancellable) event.isCancelled = true
        when (event) {
            is PlayerInteractEvent -> action.onInteract(event)
            is PlayerDropItemEvent -> action.onDrop(event)
            is PlayerInteractAtEntityEvent -> action.onInteractAtEntity(event)
        }
    }
}