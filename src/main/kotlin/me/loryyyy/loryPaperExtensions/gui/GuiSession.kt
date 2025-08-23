package me.loryyyy.loryPaperExtensions.gui

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.Stack

class GuiSession(val player: Player) {
    private val guiStack = Stack<Gui>()
    var currentGui: Gui? = null
        private set
    
    fun pushGui(gui: Gui) {
        // Salva la GUI corrente nello stack se esiste
        currentGui?.let { current ->
            guiStack.push(current)
        }
        
        currentGui = gui
        gui.open(player)
    }
    
    fun popGui() {
        if (guiStack.isEmpty()) return
        
        currentGui?.close()
        guiStack.pop()

        currentGui = guiStack.peek()

        currentGui?.open(player)
    }
    
    fun closeAll() {
        currentGui?.close()
        currentGui = null
        guiStack.clear()
        player.closeInventory()
    }
    
    fun handleClick(event: InventoryClickEvent) {
        currentGui?.handleClick(event) ?: false
    }
}