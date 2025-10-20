package me.loryyyy.loryPaperExtensions.config.models

abstract class StringsYmlManager(name: String, path: String = "") : RuledYmlManager(name, path) {

    protected fun getValue(path: RuledConfigPath, value: String? = null): String {
        val result = path.getStringValue()
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
        for (path in configPaths) {
            config[path.path] = path.getDefaultValue()
        }
        super.save()
    }
}