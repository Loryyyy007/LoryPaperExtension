package me.loryyyy.loryPaperExtensions.config.models

import me.loryyyy.loryPaperExtensions.LoryPaperExtensions
import me.loryyyy.loryPaperExtensions.debug.Logger.logInfo
import me.loryyyy.loryPaperExtensions.debug.Logger.logSevere
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

abstract class YmlManager(
    val name: String,
    val path: String
) {

    companion object {
        val managersRegistry = mutableMapOf<String, YmlManager>()
    }

    private lateinit var file: File
    lateinit var config: FileConfiguration
        protected set
    var defaultConfig: FileConfiguration? = null
        protected set

    open fun initializeConfig() {
        file = File("${LoryPaperExtensions.plugin.dataFolder.path}/$path", "$name.yml")

        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                logSevere("Failed to create the $name.yml file.")
                throw RuntimeException(e)
            }
        }

        config = YamlConfiguration.loadConfiguration(file)

        try {
            config.save(file)
        } catch (_: IOException) {
            logSevere("Failed to save the $name file.")
        }
        if (config.getKeys(false).isEmpty()) {
            setDefaultValues()
        }
    }

    fun setup() {

        initializeConfig()

        val resourceStream = LoryPaperExtensions.plugin.getResource("$name.yml")

        if (resourceStream != null) defaultConfig =
            YamlConfiguration.loadConfiguration(InputStreamReader(resourceStream))

        managersRegistry.put(name.lowercase(), this)
    }

    open fun save() {
        try {
            config.save(file)
        } catch (_: IOException) {
            logSevere("Failed to save the $name file.")
        }
    }

    open fun reload() {
        config = YamlConfiguration.loadConfiguration(file)
    }

    protected open fun setDefaultValues() {}

}
