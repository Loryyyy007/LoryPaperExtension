package me.loryyyy.loryPaperExtensions.gui

import me.loryyyy.loryPaperExtensions.gui.component.GuiComponent
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.PlayerInventory

class Gui(
    val rows: Int,
    val title: String,
    val isBase: Boolean = false,
    val state: GuiState = GuiState()
) {
    private val components = mutableMapOf<Int, GuiComponent>()
    private var inventory: Inventory? = null

    fun addComponent(component: GuiComponent) {
        components[component.slot] = component
    }

    fun open(player: Player) {
        inventory = Bukkit.createInventory(player, rows * 9, Component.text(title))
        refresh(player)
        player.openInventory(inventory!!)
    }

    fun refresh(player: Player) {
        inventory?.let { inv ->
            inv.clear()
            player.setItemOnCursor(null)
            components.forEach { (slot, component) ->
                val item = component.render(state)
                if (item != null) {
                    inv.setItem(slot, item)
                }
            }
        }
    }

    fun handleClick(event: InventoryClickEvent): Boolean {
        val player = event.whoClicked as Player
        components[event.slot]?.let {
            val refresh = it.onClick(event, this.state)
            if (refresh) refresh(player)
            return true
        }
        return false
    }

    fun close() {
        inventory = null
    }
}