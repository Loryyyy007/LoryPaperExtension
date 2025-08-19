package me.loryyyy.loryPaperExtensions.miscelanious

import me.loryyyy.loryPaperExtensions.LoryPaperExtensions

object Logger {

    fun logInfo(msg: String) = LoryPaperExtensions.plugin.logger.info(msg)

    fun logWarning(msg: String) = LoryPaperExtensions.plugin.logger.warning(msg)

    fun logSevere(msg: String) = LoryPaperExtensions.plugin.logger.severe(msg)

}