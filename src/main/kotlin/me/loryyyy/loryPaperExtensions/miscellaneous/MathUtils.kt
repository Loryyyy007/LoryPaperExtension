package me.loryyyy.loryPaperExtensions.miscellaneous

import java.util.*

fun probability(per: Double): Boolean {
    val r = Random()
    val res = (r.nextInt(1000) + 1) / 10.0
    return res <= per
}

fun probability(vararg percents: Double): Int {
    val r = Random()
    val res = (r.nextInt(1000) + 1) / 10.0
    var sum = 0.0
    for (i in percents.indices) {
        sum += percents[i]
        if (res <= sum) return i
    }

    return -1
}

fun isIn(min: Double, max: Double, number: Double): Boolean {
    return min <= number && number <= max
}

