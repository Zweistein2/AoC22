package de.zweistein2

open class Timer(val startMillis: Long, val onStop: MonitoringConsumer<Long>, val onFailed: Runnable) {
    fun stop() {
        onStop.accept(System.currentTimeMillis() - startMillis)
    }

    fun failed() {
        onFailed.run()
    }
}