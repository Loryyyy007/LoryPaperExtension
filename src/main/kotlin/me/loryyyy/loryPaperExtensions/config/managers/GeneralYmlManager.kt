package me.loryyyy.loryPaperExtensions.config.managers

import me.loryyyy.loryPaperExtensions.LoryPaperExtensions
import me.loryyyy.loryPaperExtensions.config.models.RuledYmlManager

object GeneralYmlManager : RuledYmlManager("config") {

    val plugin = LoryPaperExtensions.plugin

    override fun reload() {
        plugin.reloadConfig()
        super.config = plugin.config
    }

    override fun initializeConfig() {
        plugin.saveDefaultConfig()
        plugin.saveConfig()
        super.config = plugin.config
    }

    override fun save() {
        plugin.saveConfig()
        checkForProblems()
    }

}