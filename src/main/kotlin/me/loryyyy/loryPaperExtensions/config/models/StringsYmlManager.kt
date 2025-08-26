package me.loryyyy.loryPaperExtensions.config.models

import me.loryyyy.loryPaperExtensions.debug.Logger.logInfo

abstract class StringsYmlManager(name: String) : RuledYmlManager(name) {

    protected fun getValue(path: RuledConfigPath, value: String? = null): String {
        val result = path.getAs<String>()
        return if (value == null) result else result.replace("<value>", value)
    }

    protected fun getValue(path: RuledConfigPath, values: Map<String, String?>): String {
        var result = getValue(path)
        for ((key, v) in values) {
            result = result.replace("<$key>", v!!)
        }
        return result
    }

    override fun setDefaultValues() {
        logInfo("in set default")
        for (path in configPaths) {
            logInfo(path.path)
            config[path.path] = path.getDefaultValue()
        }
        super.save()
    }
}