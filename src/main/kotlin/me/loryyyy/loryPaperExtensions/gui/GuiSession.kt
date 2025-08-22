package me.loryyyy.loryPaperExtensions.gui

import org.bukkit.entity.Player
import java.util.Stack

class GuiSession(val player: Player) {
    private val guiStack = Stack<GuiStackFrame>()
    var currentGui: Gui? = null
        private set
    
    fun pushGui(gui: Gui) {
        // Salva la GUI corrente nello stack se esiste
        currentGui?.let { current ->
            guiStack.push(GuiStackFrame(current, current.state.copy()))
        }
        
        currentGui = gui
        gui.open(player)
    }
    
    fun popGui(): Boolean {
        if (guiStack.isEmpty()) return false
        
        currentGui?.close()
        
        val frame = guiStack.pop()
        currentGui = frame.gui
        // Ripristina lo state precedente
        //TODO boh
        /*frame.gui.state.data.clear()
        frame.gui.state.data.putAll(frame.state.data)*/
        
        frame.gui.open(player)
        return true
    }
    
    fun closeAll() {
        currentGui?.close()
        currentGui = null
        guiStack.clear()
    }
    
    fun handleClick(slot: Int, clickType: org.bukkit.event.inventory.ClickType): Boolean {
        return currentGui?.handleClick(player, slot, clickType) ?: false
    }
}