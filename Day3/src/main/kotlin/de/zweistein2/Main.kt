package de.zweistein2

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
private val monitoring = LoggerMonitoring()

fun main() {
    val content = InputHandler.readInput("/input.txt")

    monitoring.withTimer {
        val prioritySum = content.split("\n")
            .map { it.toCharArray(0, it.length / 2) to it.toCharArray(it.length / 2) }
            .map { it.first.intersect(it.second.toSet()) }
            .map { getCasePriority(it.first()) }
            .reduce { prevCal, cal -> prevCal + cal }

        val badgesSum = content.split("\n").asSequence()
            .chunked(3)
            .map { it.map { backpack -> backpack.toCharArray() }}
            .map { it.reduce { prevBackpack, backpack -> prevBackpack.intersect(backpack.toSet()).toCharArray() }}
            .map { getCasePriority(it.asList().first()) }
            .reduce { prevCal, cal -> prevCal + cal }

        logger.debug { "the sum of the priorities of the wrong item types is: $prioritySum" }
        logger.debug { "the sum of the priorities of the badges is: $badgesSum" }
    }
}

private fun getCasePriority(char: Char): Int {
    return if(char.isLowerCase()) {
        char.code - 'a'.code + 1
    } else {
        char.code - 'A'.code + 27
    }
}