package me.loryyyy.loryPaperExtensions.gui.component

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ButtonComponent(
    private val item: ItemStack,
    private val onLeftClick: (InventoryClickEvent) -> Unit,
    private val onRightClick: (InventoryClickEvent) -> Unit
) : GuiComponent {

    override fun render() = item

    override fun onClick(e: InventoryClickEvent): Boolean {
        when (e.click.isLeftClick) {
            true -> onLeftClick(e)
            false -> if (e.click.isRightClick) onRightClick(e)
        }
        return true // cancella l'evento
    }
}