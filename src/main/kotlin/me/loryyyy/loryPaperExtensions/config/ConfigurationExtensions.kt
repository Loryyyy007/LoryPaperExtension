package me.loryyyy.loryPaperExtensions.config

import me.loryyyy.betterArsenal.core.domain.config.SoundData
import me.loryyyy.betterArsenal.core.domain.config.UnspecifiedWorldLocation
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection

fun ConfigurationSection.isNull(path: String): Boolean = get(path) == null

fun ConfigurationSection.getSoundData(path: String): SoundData? {
    val section = getConfigurationSection(path)
    return section?.let { SoundData.of(it) }
}

fun ConfigurationSection.getMaterial(path: String): Material? {
    val name = getString(path)?.uppercase()
    return name?.let { Material.valueOf(it) }
}

fun ConfigurationSection.getUnspecifiedWorldLocation(path: String): UnspecifiedWorldLocation? {
    val section = this.getConfigurationSection(path) ?: return null
    return try {
        val x = section.getDouble("x")
        val y = section.getDouble("y")
        val z = section.getDouble("z")
        val pitch = section.getDouble("pitch").toFloat()
        val yaw = section.getDouble("yaw").toFloat()

        UnspecifiedWorldLocation(x, y, z, yaw, pitch)
    } catch (_: Exception) {
        null
    }
}

fun <T : Enum<T>> ConfigurationSection.getEnumList(path: String, enumClass: Class<T>): List<T> =
    getStringList(path).mapNotNull { name ->
        try {
            java.lang.Enum.valueOf(enumClass, name.uppercase())
        } catch (_: IllegalArgumentException) {
            null
        }
    }