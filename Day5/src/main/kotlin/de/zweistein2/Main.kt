package de.zweistein2

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
private val monitoring = LoggerMonitoring()

private const val EMPTY_CRATE = "[ ]"
private const val MAX_ROWS = 55

fun main() {
    val content = InputHandler.readInput("/input.txt")

    monitoring.withTimer {
        val crateInput = content.substring(0, content.indexOf(" 1 ")).split("\n")
        val cratesAsList = crateInput.filter { it.isNotEmpty() }
            .map { it.padEnd(crateInput.last { inputLine -> inputLine.isNotEmpty() }.length, ' ') }
            .map { it.chunked(4).map { crate -> crate.trim().ifEmpty { EMPTY_CRATE }}.toTypedArray() }
            .toMutableList()

        for (i in cratesAsList.size until MAX_ROWS) {
            val emptyArray = arrayOfNulls<String>(cratesAsList.last().size)
            emptyArray.fill(EMPTY_CRATE)
            cratesAsList.add(0, emptyArray.requireNoNulls())
        }

        val crates = cratesAsList.toTypedArray()
        val cratesCopied = crates.map { it.clone() }.toTypedArray()

        val instructions = content.substring(content.indexOf("move")).split("\n")
            .mapNotNull { Regex("move (\\d+) from (\\d+) to (\\d+)").matchEntire(it)?.groupValues }

        moveCratesWithCraneMover9000(instructions, crates)
        moveCratesWithCraneMover9001(instructions, cratesCopied)

        logger.debug { "the crates on top of each stack after the rearrangement procedure with CraneMover9000 are: ${getTopCrates(crates)}" }
        logger.debug { "the crates on top of each stack after the rearrangement procedure with CraneMover9001 are: ${getTopCrates(cratesCopied)}" }
    }
}

private fun moveCratesWithCraneMover9000(
    instructions: List<List<String>>,
    crates: Array<Array<String>>
) {
    for (instruction in instructions) {
        val amount = instruction[1].toInt()
        val source = instruction[2].toInt()
        val target = instruction[3].toInt()

        var temp = EMPTY_CRATE

        for (i in 0 until amount) {
            for (row in crates.indices) {
                if (crates[row][source - 1] !== EMPTY_CRATE) {
                    temp = crates[row][source - 1]
                    crates[row][source - 1] = EMPTY_CRATE

                    break
                }
            }

            for (row in crates.indices) {
                if (crates[crates.size - 1][target - 1] === EMPTY_CRATE) {
                    crates[crates.size - 1][target - 1] = temp
                    temp = EMPTY_CRATE

                    break
                } else if (crates[row][target - 1] !== EMPTY_CRATE) {
                    crates[row - 1][target - 1] = temp
                    temp = EMPTY_CRATE

                    break
                }
            }
        }
    }
}

private fun moveCratesWithCraneMover9001(
    instructions: List<List<String>>,
    crates: Array<Array<String>>
) {
    for (instruction in instructions) {
        val amount = instruction[1].toInt()
        val source = instruction[2].toInt()
        val target = instruction[3].toInt()

        val temp = mutableListOf<String>()

        for (row in crates.indices) {
            if (crates[row][source - 1] !== EMPTY_CRATE) {
                for (i in 0 until amount) {
                    temp.add(crates[row + i][source - 1])
                    crates[row + i][source - 1] = EMPTY_CRATE
                }

                break
            }
        }

        temp.reverse()

        for (tempCrate in temp) {
            for (row in crates.indices) {
                if (crates[crates.size - 1][target - 1] === EMPTY_CRATE) {
                    crates[crates.size - 1][target - 1] = tempCrate

                    break
                } else if (crates[row][target - 1] !== EMPTY_CRATE) {
                    crates[row - 1][target - 1] = tempCrate

                    break
                }
            }
        }
    }
}

private fun getTopCrates(crates: Array<Array<String>>): String {
    var topCrates = ""

    for (row in crates[0].indices) {
        for (cell in crates.indices) {
            if (crates[cell][row] !== EMPTY_CRATE) {
                topCrates += crates[cell][row]

                break
            }
        }
    }

    return topCrates.replace(Regex("[\\[\\]]"), "")
}