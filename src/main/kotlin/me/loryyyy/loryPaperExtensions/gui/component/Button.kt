package me.loryyyy.loryPaperExtensions.gui.component

import me.loryyyy.loryPaperExtensions.gui.Gui
import me.loryyyy.loryPaperExtensions.gui.GuiState
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

class Button(
    override val slot: Int,
    private val itemProvider: (GuiState) -> ItemStack,
    private val onLeftClick: ((Player, Gui) -> Unit)? = null,
    private val onRightClick: ((Player, Gui) -> Unit)? = null
) : GuiComponent {
    
    override fun render(state: GuiState): ItemStack = itemProvider(state)
    
    override fun onClick(player: Player, clickType: ClickType, gui: Gui): Boolean {
        return when (clickType) {
            ClickType.LEFT -> {
                onLeftClick?.invoke(player, gui)
                true
            }
            ClickType.RIGHT -> {
                onRightClick?.invoke(player, gui)
                true
            }
            else -> false
        }
    }
}