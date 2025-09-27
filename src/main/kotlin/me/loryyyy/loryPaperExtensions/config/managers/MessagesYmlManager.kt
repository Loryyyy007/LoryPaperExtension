package me.loryyyy.loryPaperExtensions.config.managers

import me.loryyyy.loryPaperExtensions.config.models.RuledConfigPath
import me.loryyyy.loryPaperExtensions.config.models.StringsYmlManager

object MessagesYmlManager : StringsYmlManager("Messages", "ymls") {

    fun getMessage(path: RuledConfigPath, value: String? = null): String {
        return super.getValue(path, value)
    }

    fun getMessage(path: RuledConfigPath, values: Map<String, String?>): String {
        return super.getValue(path, values)
    }

}