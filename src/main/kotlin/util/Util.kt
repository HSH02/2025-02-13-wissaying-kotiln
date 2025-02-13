package com.util

fun extractIdFromCommand(command: String): Int? {
    return command.substringAfter("id=", "").toIntOrNull()
}