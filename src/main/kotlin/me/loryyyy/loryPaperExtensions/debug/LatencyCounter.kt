package me.loryyyy.loryPaperExtensions.debug

class LatencyCounter {
    private var previousTime: Long

    init {
        this.previousTime = System.nanoTime()
    }

    fun start() {
        previousTime = System.nanoTime()
    }

    @JvmOverloads
    fun inform(message: String = "Latency:", stop: Boolean = false) {
        if (previousTime == 0L) {
            println("LatencyCounter not started.")
            return
        }

        val currentTime = System.nanoTime()
        val resultNs = currentTime - previousTime

        println("$message ${resultNs}ns")
        println("$message ${resultNs / 1_000_000.0}ms")
        println("$message ${resultNs / 1_000_000_000.0}s")

        if (stop) previousTime = currentTime
    }
}