package me.loryyyy.loryPaperExtensions.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
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
    
    fun goBack(player: Player) {
        sessions[player.uniqueId]?.popGui()
    }

    @EventHandler
    private fun on(e: InventoryClickEvent){
        val player = e.whoClicked as Player
        sessions[player.uniqueId]?.let {
            if(e.isShiftClick) e.isCancelled = true
            else if(e.cursor.type != Material.AIR && e.clickedInventory == player.openInventory.bottomInventory) e.isCancelled = true
            else if(!it.handleClick(e)) e.isCancelled = true
        }
    }
    @EventHandler
    private fun on(e: InventoryDragEvent){
        val player = e.whoClicked as Player
        sessions[player.uniqueId]?.let {
            e.isCancelled = true
        }
    }
    @EventHandler
    private fun on(e: InventoryCloseEvent){
        val player = e.player as Player
        player.setItemOnCursor(null)
        if(player.openInventory.type == InventoryType.CRAFTING){
            removeSession(player)
        }
    }
}