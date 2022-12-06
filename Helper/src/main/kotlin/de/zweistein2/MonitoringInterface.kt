package de.zweistein2

interface MonitoringInterface {
    fun writeError()

    fun writeTimer(duration: Long?)

    fun <R> withTimer(function: () -> R): R {
        val timer = startTimer()

        try {
            val returnValue = function.invoke()
            timer.stop()
            return returnValue
        } catch (e: Exception) {
            timer.failed()
            throw e
        }
    }

    fun startTimer(): Timer {
        val onStop = object : MonitoringConsumer<Long> {
            override fun accept(duration: Long) {
                writeTimer(duration)
            }
        }

        val onFailed = { writeError() }

        return Timer(System.currentTimeMillis(), onStop, onFailed)
    }
}