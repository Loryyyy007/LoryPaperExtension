package me.loryyyy.loryPaperExtensions.config.models

import me.loryyyy.betterArsenal.core.domain.config.UnspecifiedWorldLocation
import org.bukkit.configuration.file.FileConfiguration

interface ConfigPath {

    val path: String
    val ymlManager: YmlManager

    fun getNullableValue(
        parentPath: String = "",
        getter: (config: FileConfiguration, path: String) -> Any? = { config, path -> config.get(path) }
    ): Any? {
        val completePath = if (parentPath.isEmpty()) path else "${parentPath}.$path"
        return getter(ymlManager.config, completePath)
    }

    fun setValue(
        newValue: Any?,
        parentPath: String = "",
        setter: (config: FileConfiguration, path: String, value: Any?) -> Unit = { config, path, value ->
            config.set(
                path,
                value
            )
        }
    ) {
        val completePath = if (parentPath.isEmpty()) path else "${parentPath}.$path"
        setter(ymlManager.config, completePath, newValue)
        ymlManager.save()
    }

}

fun ConfigPath.getStringValue(parentPath: String = ""): String? {
    val completePath = if (parentPath.isEmpty()) path else "${parentPath}.$path"
    return ymlManager.config.getString(completePath)
}
fun ConfigPath.getIntValue(parentPath: String = ""): Int? {
    val completePath = if (parentPath.isEmpty()) path else "${parentPath}.$path"
    return ymlManager.config.getInt(completePath)
}
fun ConfigPath.getDoubleValue(parentPath: String = ""): Double? {
    val completePath = if (parentPath.isEmpty()) path else "${parentPath}.$path"
    return ymlManager.config.getDouble(completePath)
}
fun ConfigPath.getBooleanValue(parentPath: String = ""): Boolean? {
    val completePath = if (parentPath.isEmpty()) path else "${parentPath}.$path"
    return ymlManager.config.getBoolean(completePath)
}
fun ConfigPath.getLongValue(parentPath: String = ""): Long? {
    val completePath = if (parentPath.isEmpty()) path else "${parentPath}.$path"
    return ymlManager.config.getLong(completePath)
}

inline fun <reified T> ConfigPath.getAs(
    parentPath: String = "",
    noinline getter: (config: FileConfiguration, path: String) -> T? = { config, path -> config.get(path) as T? }
): T? =
    this.getNullableValue(
        parentPath = parentPath,
        getter = getter
    ) as? T

