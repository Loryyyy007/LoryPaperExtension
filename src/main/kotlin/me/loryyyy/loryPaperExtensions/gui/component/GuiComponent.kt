package me.loryyyy.loryPaperExtensions.gui.component

import me.loryyyy.loryPaperExtensions.gui.Gui
import me.loryyyy.loryPaperExtensions.gui.GuiState
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

interface GuiComponent {
    val slot: Int
    fun render(state: GuiState): ItemStack?
    fun onClick(player: Player, clickType: ClickType, gui: Gui): Boolean // return true se l'evento è stato gestito
}