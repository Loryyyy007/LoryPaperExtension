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

        if(event.cursor.type != Material.AIR){
            return onPlace?.invoke(player, state, event.cursor) ?: false
        }
        val currentItem = event.currentItem
        if(currentItem != null){
            if(event.click == ClickType.RIGHT) {
                event.isCancelled = true
                return onRightClick?.invoke(player, state) ?: false
            }else if(event.click == ClickType.LEFT){
                onPickup?.invoke(player, state, currentItem)
            }
        }
         return false
    }

}