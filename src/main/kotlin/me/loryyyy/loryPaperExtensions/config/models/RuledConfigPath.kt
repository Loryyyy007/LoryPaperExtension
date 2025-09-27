package me.loryyyy.loryPaperExtensions.config.models

import me.loryyyy.loryPaperExtensions.debug.Logger.logWarning
import org.bukkit.configuration.file.FileConfiguration

interface RuledConfigPath : ConfigPath {

    val rules: List<ConfigRule>

    fun getValue(
        parentPath: String = "",
        getter: (config: FileConfiguration, path: String) -> Any = { config, path -> config.get(path)!! }
    ): Any {
        val completePath = if (parentPath.isEmpty()) path else "${parentPath}.$path"
        return getter(ymlManager.config, completePath)
    }

    fun checkRules(parentPath: String = ""): Boolean {
        for (rule in listOf(ConfigRule.NotNullRule) + rules) {
            val result = rule.check(ymlManager.config, "${parentPath}${path}")
            if (result is CheckResult.Problem) {

                logWarning("Problem found in ${ymlManager.name}.yml at path: ${parentPath}${path}")
                logWarning(result.message)

                onError(parentPath)

                return false
            }
        }
        return true
    }

    fun getDefaultValue(parentPath: String = ""): Any? {
        return ymlManager.defaultConfig?.get("${parentPath}${path}")
    }

    fun onError(parentPath: String = "") {
        switchToDefault(parentPath)
    }

    fun switchToDefault(parentPath: String) {
        logWarning("Switching to default value: ${getDefaultValue(parentPath)}")

        ymlManager.config.set("${parentPath}.${path}", getDefaultValue(parentPath))
    }


}

fun RuledConfigPath.getStringValue(parentPath: String = ""): String {
    val completePath = if (parentPath.isEmpty()) path else "${parentPath}.$path"
    return ymlManager.config.getString(completePath)!!
}
fun RuledConfigPath.getIntValue(parentPath: String = ""): Int {
    val completePath = if (parentPath.isEmpty()) path else "${parentPath}.$path"
    return ymlManager.config.getInt(completePath)
}
fun RuledConfigPath.getDoubleValue(parentPath: String = ""): Double {
    val completePath = if (parentPath.isEmpty()) path else "${parentPath}.$path"
    return ymlManager.config.getDouble(completePath)
}
fun RuledConfigPath.getBooleanValue(parentPath: String = ""): Boolean {
    val completePath = if (parentPath.isEmpty()) path else "${parentPath}.$path"
    return ymlManager.config.getBoolean(completePath)
}
fun RuledConfigPath.getLongValue(parentPath: String = ""): Long {
    val completePath = if (parentPath.isEmpty()) path else "${parentPath}.$path"
    return ymlManager.config.getLong(completePath)
}

inline fun <reified T> RuledConfigPath.getAs(
    parentPath: String = "",
    noinline getter: (config: FileConfiguration, path: String) -> Any = { config, path -> config.get(path)!! }
): T =
    this.getValue(
        parentPath = parentPath,
        getter = getter
    ) as T