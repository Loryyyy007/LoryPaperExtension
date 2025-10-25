package me.loryyyy.loryPaperExtensions.gui.component

import me.loryyyy.loryPaperExtensions.gui.GuiState
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class MovableButton(
    override val slot: Int,
    private val itemProvider: (GuiState) -> ItemStack?, // come renderizzare il bottone
    private val onRightClick: ((Player, GuiState) -> Boolean)? = null, // per editare il bottone
    private val onPlace: ((Player, GuiState, ItemStack) -> Boolean)? = null, // movimento completato
    private val onPickup: ((Player, GuiState, ItemStack) -> Boolean)? = null
) : GuiComponent {
    
    override fun render(state: GuiState): ItemStack? = itemProvider(state)

    override fun onClick(event: InventoryClickEvent, state: GuiState): Boolean {
        val player = event.whoClicked as Player
        val cursorItem = event.cursor
        val currentItem = event.currentItem

        var result = false

        when (event.click) {
            ClickType.RIGHT -> {
                if (cursorItem.type == Material.AIR) {
                    // Empty cursor → just right click action
                    event.isCancelled = true
                    return onRightClick?.invoke(player, state) ?: false
                } else {
                    // Cursor not empty → treat as place/pickup action
                    if (cursorItem.type != Material.AIR)
                        result = onPlace?.invoke(player, state, cursorItem) ?: false

                    if (currentItem != null && currentItem.type != Material.AIR)
                        result = result or (onPickup?.invoke(player, state, currentItem) ?: false)

                    return result
                }
            }

            ClickType.LEFT -> {
                if (cursorItem.type != Material.AIR)
                    result = onPlace?.invoke(player, state, cursorItem) ?: false

                if (currentItem != null && currentItem.type != Material.AIR)
                    result = result or (onPickup?.invoke(player, state, currentItem) ?: false)

                return result
            }

            else -> return false
        }
    }

}