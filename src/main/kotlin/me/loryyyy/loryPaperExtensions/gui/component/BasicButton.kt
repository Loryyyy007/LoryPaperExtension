package me.loryyyy.loryPaperExtensions.gui.component

import me.loryyyy.loryPaperExtensions.gui.GuiState
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class BasicButton(
    override val slot: Int,
    private val item: ItemStack,
    private val onClick: ((Player) -> Unit)? = null,
) : GuiComponent {

    override fun render(state: GuiState): ItemStack = item

    override fun onClick(event: InventoryClickEvent, state: GuiState): Boolean {
        event.isCancelled = true
        val player = event.whoClicked as Player
        onClick?.invoke(player)
        return false
    }
}