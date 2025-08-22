package me.loryyyy.loryPaperExtensions.gui

import me.loryyyy.loryPaperExtensions.gui.component.ButtonComponent
import me.loryyyy.loryPaperExtensions.gui.component.GuiComponent
import me.loryyyy.loryPaperExtensions.gui.gui.Gui
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class GuiBuilder(
    private val title: String,
    private val rows: Int,
    private val state: MutableMap<String, Any>
) {
    private val components = mutableListOf<Pair<Int, GuiComponent>>()

    fun slot(index: Int, component: GuiComponent) {
        components.add(index to component)
    }

    fun button(
        material: Material,
        name: String,
        description: List<String> = emptyList(),
        onLeftClick: (org.bukkit.event.inventory.InventoryClickEvent) -> Unit = {},
        onRightClick: (org.bukkit.event.inventory.InventoryClickEvent) -> Unit = {}
    ): ButtonComponent {
        return ButtonComponent(makeItem(material, name, description), state, onLeftClick, onRightClick)
    }

    fun separatorRow(row: Int, material: Material = Material.GRAY_STAINED_GLASS_PANE) {
        val start = row * 9
        for (i in start until start + 9) {
            slot(i, button(material, " "))
        }
    }

    fun build(): Gui {
        return object : Gui(title, rows) {
            init {
                components.forEach { (slot, comp) -> setComponent(slot, comp) }
            }
        }
    }

    private fun makeItem(material: Material, name: String, description: List<String> = emptyList()): ItemStack {
        val item = ItemStack(material)
        val meta: ItemMeta = item.itemMeta
        meta.displayName(Component.text(name))
        val lore = description.map { it -> Component.text(it) }
        meta.lore(lore)
        item.itemMeta = meta
        return item
    }
}