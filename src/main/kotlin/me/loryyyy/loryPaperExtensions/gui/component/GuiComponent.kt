package me.loryyyy.loryPaperExtensions.gui.component

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

interface GuiComponent {
    fun render(): ItemStack
    fun <T: Any> onClick(e: InventoryClickEvent, state: T): Boolean
}