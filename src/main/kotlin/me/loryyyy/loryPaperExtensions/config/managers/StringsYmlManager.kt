package me.loryyyy.loryPaperExtensions.config.managers

import me.loryyyy.loryPaperExtensions.config.models.RuledConfigPath
import me.loryyyy.loryPaperExtensions.config.models.RuledYmlManager
import me.loryyyy.loryPaperExtensions.config.models.StringsYmlManager
import me.loryyyy.loryPaperExtensions.config.models.getAs

object StringsYmlManager : StringsYmlManager("Strings") {

    fun getString(path: RuledConfigPath, value: String? = null): String {
        return getValue(path, value)
    }

    fun getString(path: RuledConfigPath, values: Map<String, String?>): String {
       return getValue(path, values)
    }

}