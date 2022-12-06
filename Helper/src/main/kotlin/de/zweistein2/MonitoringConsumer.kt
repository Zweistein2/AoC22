package de.zweistein2

interface MonitoringConsumer<R> {
    fun accept(duration: R)
}