package de.zweistein2

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
private val monitoring = LoggerMonitoring()

fun main() {
    val content = InputHandler.readInput("/input.txt")

    monitoring.withTimer {
        val elves = content.split("\n\n")
        val caloriesPerElf = elves
            .map { it.split("\n") }
            .map { it.map { calories -> calories.toInt() }}
            .map { it.reduce { prevCal, cal -> prevCal + cal }}
            .sorted().reversed()

        val totalCaloriesTopThree = caloriesPerElf.take(3).reduce { prevCal, cal -> prevCal + cal }

        logger.debug { "most calories on one elf: ${caloriesPerElf[0]}" }
        logger.debug { "most calories on the top three elves: $totalCaloriesTopThree" }
    }
}