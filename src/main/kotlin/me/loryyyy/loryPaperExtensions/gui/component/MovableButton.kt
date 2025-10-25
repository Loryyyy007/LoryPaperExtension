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
    private val onPickup: ((Player, GuiState, ItemStack) -> Boolean)? = null,
    private val onSwitchWithCursor: ((Player, GuiState, ItemStack, ItemStack) -> Boolean)? = null
) : GuiComponent {
    
    override fun render(state: GuiState): ItemStack? = itemProvider(state)

    override fun onClick(event: InventoryClickEvent, state: GuiState): Boolean {
        val player = event.whoClicked as Player
        val cursorItem = event.cursor
        val currentItem = event.currentItem

        fun managePickupAndPlace() : Boolean{
            val runOnPlace = cursorItem.type != Material.AIR
            val runOnPickup = currentItem != null && currentItem.type != Material.AIR
            if(runOnPlace && runOnPickup)
                return onSwitchWithCursor?.invoke(player, state, currentItem, cursorItem) ?: false

            if (runOnPlace)
                return onPlace?.invoke(player, state, cursorItem) ?: false

            if (runOnPickup)
                return onPickup?.invoke(player, state, currentItem) ?: false
            return false
        }

        when (event.click) {
            ClickType.RIGHT -> {
                if (cursorItem.type == Material.AIR) {
                    // Empty cursor → just right click action
                    event.isCancelled = true
                    return onRightClick?.invoke(player, state) ?: false
                } else {
                    return managePickupAndPlace()
                }
            }

            ClickType.LEFT -> {
                return managePickupAndPlace()
            }

            else -> return false
        }
    }

}