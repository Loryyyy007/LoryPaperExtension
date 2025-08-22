package me.loryyyy.loryPaperExtensions.gui.component

import me.loryyyy.loryPaperExtensions.gui.GuiState
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

interface GuiComponent {
    val slot: Int
    fun render(state: GuiState): ItemStack?
    fun onClick(event: InventoryClickEvent, state: GuiState): Boolean // return true se l'evento è stato gestito
}