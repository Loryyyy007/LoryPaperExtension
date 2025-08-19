package me.loryyyy.loryPaperExtensions.config.models

import me.loryyyy.loryPaperExtensions.config.isNull
import org.bukkit.configuration.file.FileConfiguration

abstract class ConfigRule {

    abstract fun check(config: FileConfiguration, path: String): CheckResult

    data object NotNullRule : ConfigRule() {
        override fun check(config: FileConfiguration, path: String): CheckResult {
            return if (config.isNull(path))
                CheckResult.Problem("Value is null or missing.")
            else
                CheckResult.Success
        }
    }

    data object ValidStringList : ConfigRule() {
        override fun check(config: FileConfiguration, path: String): CheckResult {
            val stringList = config.getStringList(path)
            return if (stringList.isEmpty())
                CheckResult.Problem("String list is empty.")
            else
                CheckResult.Success
        }
    }

    data object ValidLocation : ConfigRule() {
        override fun check(config: FileConfiguration, path: String): CheckResult {
            return try {
                val location = config.getLocation(path)
                if (location == null) {
                    CheckResult.Problem("Location is null or invalid format.")
                } else {
                    CheckResult.Success
                }
            } catch (e: Exception) {
                CheckResult.Problem("Failed to parse location: ${e.message}")
            }
        }
    }

    data object ValidNumber : ConfigRule() {
        override fun check(config: FileConfiguration, path: String): CheckResult {
            return try {

                // Try to get as number - this handles int, double, float, etc.
                val value = config.get(path)
                when (value) {
                    is Number -> CheckResult.Success
                    is String -> {
                        // Try to parse string as number
                        value.toDoubleOrNull()?.let {
                            CheckResult.Success
                        } ?: CheckResult.Problem("String value '$value' cannot be parsed as a number.")
                    }

                    else -> CheckResult.Problem("Value is not a valid number type. Found: ${value?.javaClass?.simpleName}")
                }
            } catch (e: Exception) {
                CheckResult.Problem("Failed to validate number: ${e.message}")
            }
        }
    }


}

sealed class CheckResult {

    data object Success : CheckResult()

    data class Problem(val message: String) : CheckResult()

}



