package de.zweistein2

import mu.KotlinLogging

class LoggerMonitoring: MonitoringInterface {
    private val logger = KotlinLogging.logger {}

    override fun writeError() {
        logger.error { "error" }
    }

    override fun writeTimer(duration: Long?) {
        logger.info { "duration: ${duration}ms" }
    }
}