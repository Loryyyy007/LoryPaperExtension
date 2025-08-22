package me.loryyyy.loryPaperExtensions

import me.loryyyy.loryPaperExtensions.gui.GuiManager
import me.loryyyy.loryPaperExtensions.item_actions.ItemActionManager
import org.bukkit.plugin.java.JavaPlugin

object LoryPaperExtensions {

    fun init(plugin: JavaPlugin) {
        this.plugin = plugin

        ItemActionManager.init(plugin)
        GuiManager.init(plugin)
    }

    lateinit var plugin: JavaPlugin
        private set

}