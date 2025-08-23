package me.loryyyy.loryPaperExtensions.gui.component

import me.loryyyy.loryPaperExtensions.gui.GuiState
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class Button(
    override val slot: Int,
    private val itemProvider: (GuiState) -> ItemStack,
    private val onLeftClick: ((Player, GuiState) -> Boolean)? = null,
    private val onRightClick: ((Player, GuiState) -> Boolean)? = null
) : GuiComponent {
    
    override fun render(state: GuiState): ItemStack = itemProvider(state)
    
    override fun onClick(event: InventoryClickEvent, state: GuiState): Boolean {
        event.isCancelled = true
        val player = event.whoClicked as Player
        return when (event.click) {
            ClickType.LEFT -> onLeftClick?.invoke(player, state) ?: false
            ClickType.RIGHT -> onRightClick?.invoke(player, state) ?: false
            else -> false
        }
    }
}