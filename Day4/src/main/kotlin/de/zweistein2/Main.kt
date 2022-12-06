package de.zweistein2

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
private val monitoring = LoggerMonitoring()

fun main() {
    val content = InputHandler.readInput("/input.txt")

    monitoring.withTimer {
        val fullyContainedAssignments = content.split("\n")
            .map { it.split(",")
                .map { assignments -> assignments.split("-") }
                .map { assignment -> assignment[0].toInt() .. assignment[1].toInt() }}
            .map { it[0] to it[1] }
            .count { it.first isFullyOverlapping it.second || it.second isFullyOverlapping it.first }

        val partlyContainedAssignments = content.split("\n")
            .map { it.split(",")
                .map { assignments -> assignments.split("-") }
                .map { assignment -> assignment[0].toInt() .. assignment[1].toInt() }}
            .map { it[0] to it[1] }
            .count { it.first isPartlyOverlapping it.second || it.second isPartlyOverlapping it.first }

        logger.debug { "number of fully contained assignment-pairs: $fullyContainedAssignments" }
        logger.debug { "number of partly contained assignment-pairs: $partlyContainedAssignments" }
    }
}

private infix fun ClosedRange<Int>.isFullyOverlapping(range: ClosedRange<Int>): Boolean {
    return this.contains(range.start) && this.contains(range.endInclusive)
}

private infix fun ClosedRange<Int>.isPartlyOverlapping(range: ClosedRange<Int>): Boolean {
    return this.contains(range.start) || this.contains(range.endInclusive)
}