package me.loryyyy.loryPaperExtensions.gui

import me.loryyyy.loryPaperExtensions.gui.component.BasicButton
import me.loryyyy.loryPaperExtensions.gui.component.AdvancedButton
import me.loryyyy.loryPaperExtensions.gui.component.Button
import me.loryyyy.loryPaperExtensions.gui.component.GuiComponent
import me.loryyyy.loryPaperExtensions.gui.component.MovableButton
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class GuiBuilder(
    private val title: String,
    private val rows: Int,
) {

    private val components = mutableListOf<GuiComponent>()

    var startingState: GuiState = GuiState()
    var isBase: Boolean = false

    fun basicButton(
        slot: Int,
        item: ItemStack,
        onClick: ((Player) -> Unit)? = null,
    ){
        components.add(BasicButton(slot, item, onClick))
    }
    fun button(
        slot: Int,
        item: ItemStack,
        onClick: ((Player, GuiState) -> Boolean)? = null,
    ){
        components.add(Button(slot, item, onClick))
    }

    fun advancedButton(
        slot: Int,
        itemProvider: (GuiState) -> ItemStack,
        onClick: ((Player, GuiState) -> Boolean)? = null,
        onLeftClick: ((Player, GuiState) -> Boolean)? = null,
        onRightClick: ((Player, GuiState) -> Boolean)? = null
    ) {
        components.add(AdvancedButton(slot, itemProvider, onClick, onLeftClick, onRightClick))
    }
    
    fun movableButton(
        slot: Int,
        itemProvider: (GuiState) -> ItemStack?,
        onRightClick: ((Player, GuiState) -> Boolean)? = null,
        onPlace: ((Player, GuiState, ItemStack) -> Boolean)? = null,
        onPickup: ((Player, GuiState, ItemStack) -> Boolean)? = null
    ) {
        components.add(MovableButton(slot, itemProvider, onRightClick, onPlace, onPickup))

    }
    
    internal fun build(): Gui {
        val gui = Gui(rows, title, isBase, startingState)
        components.forEach { gui.addComponent(it) }
        return gui
    }
}


fun makeItem(material: Material, displayName: String, lore: List<String> = emptyList()): ItemStack{
    val item = ItemStack(material)
    val meta = item.itemMeta
    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
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