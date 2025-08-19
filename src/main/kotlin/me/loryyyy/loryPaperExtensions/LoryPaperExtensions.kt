package me.loryyyy.loryPaperExtensions

import me.loryyyy.loryPaperExtensions.item_actions.ItemActionListener
import org.bukkit.plugin.java.JavaPlugin

object LoryPaperExtensions {

    fun init(plugin: JavaPlugin) {
        this.plugin = plugin

        this.plugin.server.pluginManager.registerEvents(ItemActionListener, this.plugin)
    }

    lateinit var plugin: JavaPlugin
        private set

}