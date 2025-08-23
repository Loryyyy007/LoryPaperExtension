package me.loryyyy.loryPaperExtensions.gui.component

import me.loryyyy.loryPaperExtensions.gui.GuiState
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class Button(
    override val slot: Int,
    private val item: ItemStack,
    private val onClick: ((Player, GuiState) -> Boolean)? = null,
) : GuiComponent {

    override fun render(state: GuiState): ItemStack = item

    override fun onClick(event: InventoryClickEvent, state: GuiState): Boolean {
        event.isCancelled = true
        val player = event.whoClicked as Player
        return onClick?.invoke(player, state) ?: false
    }
}