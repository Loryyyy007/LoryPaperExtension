package me.loryyyy.loryPaperExtensions.gui

import me.loryyyy.loryPaperExtensions.gui.component.GuiComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
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
    
    fun removeComponent(slot: Int) {
        components.remove(slot)
    }
    
    fun getComponent(slot: Int): GuiComponent? = components[slot]
    
    fun getComponents(): Map<Int, GuiComponent> = components.toMap()
    
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
    
    fun handleClick(player: Player, slot: Int, clickType: org.bukkit.event.inventory.ClickType): Boolean {
        return components[slot]?.onClick(player, clickType, this) ?: false
    }
    
    fun close() {
        inventory = null
    }
}