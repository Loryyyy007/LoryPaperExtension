package me.loryyyy.loryPaperExtensions.gui

import org.bukkit.entity.Player
import java.util.UUID

object GuiManager {
    private val sessions = mutableMapOf<UUID, GuiSession>()
    
    fun getOrCreateSession(player: Player): GuiSession {
        return sessions.getOrPut(player.uniqueId) { GuiSession(player) }
    }
    
    fun removeSession(player: Player) {
        sessions[player.uniqueId]?.closeAll()
        sessions.remove(player.uniqueId)
    }
    
    fun openGui(player: Player, gui: Gui) {
        val session = getOrCreateSession(player)
        session.pushGui(gui)
    }
    
    fun goBack(player: Player): Boolean {
        return sessions[player.uniqueId]?.popGui() ?: false
    }
    
    fun handleInventoryClick(player: Player, slot: Int, clickType: org.bukkit.event.inventory.ClickType): Boolean {
        return sessions[player.uniqueId]?.handleClick(slot, clickType) ?: false
    }
}