package me.loryyyy.loryPaperExtensions.gui.gui

import me.loryyyy.loryPaperExtensions.gui.component.GuiComponent
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import kotlin.collections.iterator

abstract class Gui(
    val title: String,
    val rows: Int
) {
    private val components = mutableMapOf<Int, GuiComponent>()

    fun setComponent(slot: Int, component: GuiComponent) {
        components[slot] = component
    }
    fun moveComponent(from: Int, to: Int) {
        val comp = components.remove(from) ?: return
        components[to] = comp
    }

    open fun render(player: Player) {
        val inv = Bukkit.createInventory(player, rows * 9, Component.text(title))
        for ((slot, comp) in components) {
            inv.setItem(slot, comp.render())
        }
        player.openInventory(inv)
    }

    open fun handleClick(e: InventoryClickEvent): Boolean {
        val comp = components[e.slot] ?: return false
        return comp.onClick(e)
    }

    open fun <T: Any> onClose(player: Player, state: T) {}
}