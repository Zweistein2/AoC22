package de.zweistein2

import java.io.InputStream

object InputHandler {
    fun readInput(filename: String): String {
        return String((javaClass.getResourceAsStream(filename) ?: InputStream.nullInputStream()).readAllBytes())
    }
}