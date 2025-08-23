package me.loryyyy.loryPaperExtensions.gui

import me.loryyyy.loryPaperExtensions.gui.component.Button
import me.loryyyy.loryPaperExtensions.gui.component.GuiComponent
import me.loryyyy.loryPaperExtensions.gui.component.MovableButton
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class GuiBuilder(
    private val title: String,
    private val rows: Int
) {

    private val components = mutableListOf<GuiComponent>()
    
    fun button(
        slot: Int,
        itemProvider: (GuiState) -> ItemStack,
        onLeftClick: ((Player, GuiState) -> Boolean)? = null,
        onRightClick: ((Player, GuiState) -> Boolean)? = null
    ) {
        components.add(Button(slot, itemProvider, onLeftClick, onRightClick))
    }
    
    fun movableButton(
        slot: Int,
        itemProvider: (GuiState) -> ItemStack?,
        onRightClick: ((Player, GuiState) -> Boolean)? = null,
        onMove: ((Player, GuiState, ItemStack) -> Boolean)? = null
    ) {
        components.add(MovableButton(slot, itemProvider, onRightClick, onMove))
    }
    
    internal fun build(): Gui {
        val gui = Gui(rows, title)
        components.forEach { gui.addComponent(it) }
        return gui
    }
}


fun makeItem(material: Material, displayName: String, lore: List<String>): ItemStack{
    val item = ItemStack(material)
    val meta = item.itemMeta
    meta.displayName(Component.text(displayName))
    meta.lore(lore.map { it -> Component.text(it) })
    item.setItemMeta(meta)
    return item
}

fun gui(title: String, rows: Int, builder: GuiBuilder.() -> Unit): Gui {
    val guiBuilder = GuiBuilder(title, rows)
    guiBuilder.builder()
    return guiBuilder.build()
}