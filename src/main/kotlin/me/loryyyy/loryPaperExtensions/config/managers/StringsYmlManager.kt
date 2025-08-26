package me.loryyyy.loryPaperExtensions.config.managers

import me.loryyyy.loryPaperExtensions.config.models.RuledConfigPath
import me.loryyyy.loryPaperExtensions.config.models.StringsYmlManager

object StringsYmlManager : StringsYmlManager("Strings", "ymls") {

    fun getString(path: RuledConfigPath, value: String? = null): String {
        return super.getValue(path, value)
    }

    fun getString(path: RuledConfigPath, values: Map<String, String?>): String {
       return super.getValue(path, values)
    }

}