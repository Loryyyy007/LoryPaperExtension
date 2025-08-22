package me.loryyyy.loryPaperExtensions.gui.component

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

interface GuiComponent {

    val state: MutableMap<String, Any>

    fun render(): ItemStack
    fun onClick(e: InventoryClickEvent): Boolean
}