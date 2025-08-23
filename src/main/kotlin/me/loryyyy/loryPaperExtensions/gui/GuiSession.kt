package me.loryyyy.loryPaperExtensions.gui

import me.loryyyy.loryPaperExtensions.debug.Logger.logInfo
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
    fun clearAndPushGui(gui: Gui){
        closeAll()
        pushGui(gui)
    }
    
    fun popGui() {
        if (guiStack.size < 2) {
            closeAll()
            return
        }
        
        currentGui?.close()
        guiStack.pop()

        currentGui = guiStack.peek()

        logInfo(currentGui.toString())

        currentGui?.open(player)
    }
    
    fun closeAll() {
        currentGui?.close()
        currentGui = null
        guiStack.clear()
    }
    
    fun handleClick(event: InventoryClickEvent) : Boolean {
        return currentGui?.handleClick(event) ?: false
    }
}