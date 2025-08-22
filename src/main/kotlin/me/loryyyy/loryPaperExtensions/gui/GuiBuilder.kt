package me.loryyyy.loryPaperExtensions.gui

import me.loryyyy.loryPaperExtensions.gui.component.Button
import me.loryyyy.loryPaperExtensions.gui.component.GuiComponent
import me.loryyyy.loryPaperExtensions.gui.component.MovableButton
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class GuiBuilder(
    private val id: String
) {
    var size: Int = 27
    var title: String = ""
    private val components = mutableListOf<GuiComponent>()
    
    fun button(
        slot: Int,
        item: (GuiState) -> ItemStack,
        onLeftClick: ((Player, GuiState) -> Boolean)? = null,
        onRightClick: ((Player, GuiState) -> Boolean)? = null
    ) {
        components.add(Button(slot, item, onLeftClick, onRightClick))
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
        val gui = Gui(id, size, title)
        components.forEach { gui.addComponent(it) }
        return gui
    }
}

fun gui(id: String, builder: GuiBuilder.() -> Unit): Gui {
    val guiBuilder = GuiBuilder(id)
    guiBuilder.builder()
    return guiBuilder.build()
}