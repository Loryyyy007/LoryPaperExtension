package me.loryyyy.loryPaperExtensions.gui

import me.loryyyy.loryPaperExtensions.debug.Logger.logInfo
import me.loryyyy.loryPaperExtensions.gui.gui.Gui
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

object GuiManager : Listener {
    private val sessions = mutableMapOf<UUID, ArrayDeque<GuiSession>>()

    fun init(plugin: JavaPlugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    fun open(player: Player, gui: Gui) {
        val stack = sessions.getOrPut(player.uniqueId) { ArrayDeque() }
        stack.push(GuiSession(player.uniqueId, gui, state))
        gui.render(player)
    }

    fun back(player: Player) {
        val stack = sessions[player.uniqueId] ?: return
        if (stack.size > 1) {
            stack.pop() // chiudi la GUI corrente
            val parent = stack.peek()
            parent?.gui?.render(player) // riapri la GUI madre
        }
    }

    fun current(player: Player): GuiSession<*>? {
        return sessions[player.uniqueId]?.peek()
    }

    fun close(player: Player) {
        sessions.remove(player.uniqueId)
    }

    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        val session = current(e.whoClicked as Player) ?: return
        if (session.gui.handleClick(e, session.state)) {
            e.isCancelled = true
        }
    }
    @EventHandler
    fun onDrag(e: InventoryDragEvent){
        current(e.whoClicked as Player) ?: return
        logInfo("drag")
        e.isCancelled = true
    }

    @EventHandler
    fun onClose(e: InventoryCloseEvent  ) {
        val session = current(e.player as Player) ?: return
        session.gui.onClose(e.player as Player, session.state)
    }
}