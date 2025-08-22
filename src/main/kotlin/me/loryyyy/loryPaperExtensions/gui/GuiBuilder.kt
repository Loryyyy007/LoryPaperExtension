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
        onLeftClick: ((Player, Gui) -> Unit)? = null,
        onRightClick: ((Player, Gui) -> Unit)? = null
    ) {
        components.add(Button(slot, item, onLeftClick, onRightClick))
    }
    
    fun movableButton(
        slot: Int,
        itemKey: String,
        validSlots: Set<Int>,
        itemProvider: (itemKey: String, GuiState) -> ItemStack?,
        onRightClick: ((itemKey: String, Player, Gui) -> Unit)? = null,
        onMove: ((itemKey: String, from: Int, to: Int, Player, Gui) -> Unit)? = null
    ) {
        components.add(MovableButton(slot, itemKey, validSlots, itemProvider, onRightClick, onMove))
    }
    
    // Shortcut per creare una griglia di slot che possono contenere bottoni movibili
    fun movableGrid(
        slots: IntRange,
        validItemKeys: Set<String>,
        itemProvider: (itemKey: String, GuiState) -> ItemStack?,
        onRightClick: ((itemKey: String, Player, Gui) -> Unit)? = null,
        onMove: ((itemKey: String, from: Int, to: Int, Player, Gui) -> Unit)? = null
    ) {
        val slotSet = slots.toSet()
        validItemKeys.forEach { itemKey ->
            slots.forEach { slot ->
                movableButton(slot, itemKey, slotSet, itemProvider, onRightClick, onMove)
            }
        }
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