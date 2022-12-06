package de.zweistein2

import de.zweistein2.MatchOutcome.Companion.leadsTo
import de.zweistein2.Shape.Companion.against
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
private val monitoring = LoggerMonitoring()

fun main() {
    val content = InputHandler.readInput("/input.txt")

    monitoring.withTimer {
        val finalScoreMyMethod = content.split("\n")
            .map { Shape.of(it[0]) to Shape.of(it[2]) }
            .map { it to (it.first against it.second)}
            .map { it.first.second.value + it.second.value }
            .reduce { prevCal, cal -> prevCal + cal }

        val finalScoreElfMethod = content.split("\n")
            .map { Shape.of(it[0]) to MatchOutcome.of(it[2]) }
            .map { it to (it.first leadsTo it.second)}
            .map { it.first.second.value + it.second.value }
            .reduce { prevCal, cal -> prevCal + cal }

        logger.debug { "The final score after all rounds with my method is $finalScoreMyMethod" }
        logger.debug { "The final score after all rounds with the elves method is $finalScoreElfMethod" }
    }
}

enum class Shape(val value: Int) {
    ROCK(1),
    SCISSOR(3),
    PAPER(2);

    companion object {
        infix fun Shape.against(opponent: Shape): MatchOutcome {
            return if((this.ordinal + 1) % 3 == opponent.ordinal) {
                MatchOutcome.LOSS
            } else if(this.ordinal == opponent.ordinal) {
                MatchOutcome.DRAW
            } else {
                MatchOutcome.WIN
            }
        }

        fun of(value: Char): Shape {
            return when(value) {
                'A', 'X' -> ROCK
                'B', 'Y' -> PAPER
                'C', 'Z' -> SCISSOR
                else -> throw IllegalArgumentException("Must be one of 'A', 'B', 'C', 'X', 'Y' or 'Z'")
            }
        }
    }
}

enum class MatchOutcome(val value: Int) {
    WIN(6),
    LOSS(0),
    DRAW(3);

    companion object {
        infix fun Shape.leadsTo(wantedOutcome: MatchOutcome): Shape {
            return if((this.ordinal + 2) % 3 == wantedOutcome.ordinal) {
                Shape.ROCK
            } else if(this.ordinal == wantedOutcome.ordinal) {
                Shape.PAPER
            } else {
                Shape.SCISSOR
            }
        }

        fun of(value: Char): MatchOutcome {
            return when(value) {
                'X' -> LOSS
                'Y' -> DRAW
                'Z' -> WIN
                else -> throw IllegalArgumentException("Must be one of 'A', 'B', 'C', 'X', 'Y' or 'Z'")
            }
        }
    }
}