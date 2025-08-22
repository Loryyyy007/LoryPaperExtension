package me.loryyyy.loryPaperExtensions.gui.gui

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.AnvilInventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.view.AnvilView

class AnvilInputGui(
    title: String,
    private val placeholder: ItemStack,
    private val onInput: (Player, String) -> Unit
) : Gui(title, 1) {

    override fun render(player: Player) {
        val inv = Bukkit.createInventory(player, 3, Component.text(title)) as AnvilInventory
        inv.setItem(0, placeholder)
        player.openInventory(inv)
    }

    override fun <T: Any> handleClick(e: InventoryClickEvent, state: T): Boolean {
        if (e.inventory is AnvilInventory && e.slot == 2) {
            val text = (e.view as AnvilView).renameText ?: return true
            onInput(e.whoClicked as Player, text)
            return true
        }
        return false
    }
}