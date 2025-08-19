package me.loryyyy.loryPaperExtensions.config.configurable

import java.util.*

data class ValueRange(
    val min: Int,
    val max: Int
) {

    val r: Random = Random()

    init {
        require(min <= max) { "Minimum value must be less than maximum value." }
    }

    companion object {
        fun of(range: String): ValueRange {
            var range = range
            range = range.replace(" ", "")
            require(range.contains("-")) { "There must be a '-' in a string range representation." }
            val n1 = range.substring(0, range.indexOf("-"))
            val n2 = range.substring(range.indexOf("-") + 1)

            return ValueRange(n1.toInt(), n2.toInt())
        }
    }

    fun random(): Int {
        return r.nextInt(max - min + 1) + min
    }
}
