package me.loryyyy.loryPaperExtensions.config.managers

import me.loryyyy.loryPaperExtensions.config.models.RuledConfigPath
import me.loryyyy.loryPaperExtensions.config.models.RuledYmlManager
import me.loryyyy.loryPaperExtensions.config.models.getAs

object MessagesYmlManager : RuledYmlManager("Messages") {

    fun getMessage(path: RuledConfigPath, value: String? = null): String {
        val message = (path.getAs<String>())

        return if (value == null) message
        else message.replace("<value>", value)
    }

    fun getMessage(path: RuledConfigPath, values: Map<String, String?>): String {
        var message = getMessage(path)

        for ((key, value) in values) {
            val placeholder = "<$key>"
            message = message.replace(placeholder, value!!)
        }

        return message
    }

    override fun setDefaultValues() {

        for (path in configPaths) {
            config[path.path] = path.getDefaultValue()
        }

        super.save()
    }

}