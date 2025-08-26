package me.loryyyy.loryPaperExtensions.config.models

abstract class RuledYmlManager(
    name: String,
    path: String = ""
) : YmlManager(name, path) {

    var configPaths: List<RuledConfigPath> = emptyList()

    fun setup(configPaths: List<RuledConfigPath>) {
        this.configPaths = configPaths
        super.setup()
    }

    open fun checkForProblems(): Boolean {
        var hasProblems = false
        configPaths.forEach { if (!it.checkRules()) hasProblems = true }
        return hasProblems
    }
    fun saveAndCheck(){
        super.save()
        checkForProblems()
    }

}