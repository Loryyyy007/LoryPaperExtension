package me.loryyyy.loryPaperExtensions.gui

import me.loryyyy.loryPaperExtensions.gui.component.GuiComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

class Gui(
    val id: String,
    val size: Int,
    val title: String,
    val state: GuiState = GuiState()
) {
    private val components = mutableMapOf<Int, GuiComponent>()
    private var inventory: Inventory? = null
    
    fun addComponent(component: GuiComponent) {
        components[component.slot] = component
    }
    
    fun open(player: Player) {
        inventory = Bukkit.createInventory(null, size, title)
        refresh()
        player.openInventory(inventory!!)
    }
    
    fun refresh() {
        inventory?.let { inv ->
            inv.clear()
            components.forEach { (slot, component) ->
                val item = component.render(state)
                if (item != null) {
                    inv.setItem(slot, item)
                }
            }
        }
    }
    
    fun handleClick(event: InventoryClickEvent) {
        val refresh = components[event.slot]?.onClick(event, this.state) ?: false
        if(refresh) refresh()
    }
    
    fun close() {
        inventory = null
    }
}