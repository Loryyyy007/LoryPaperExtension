package me.loryyyy.loryPaperExtensions.gui

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

object GuiManager : Listener {
    private val sessions = mutableMapOf<UUID, GuiSession>()

    fun init(plugin: JavaPlugin){
        plugin.server.pluginManager.registerEvents(this, plugin)
    }
    
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
    
    fun handleInventoryClick(player: Player, slot: Int, clickType: ClickType): Boolean {
        return sessions[player.uniqueId]?.handleClick(slot, clickType) ?: false
    }

    @EventHandler
    private fun on(e: InventoryClickEvent){
        handleInventoryClick(
            player = e.whoClicked as Player,
            slot = e.slot,
            clickType = e.click
        )
    }
}