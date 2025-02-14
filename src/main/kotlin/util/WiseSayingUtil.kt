package com.util

fun extractIdFromCommand(command: String): Int? {
    return command.substringAfter("id=", "").toIntOrNull()
}

fun readUserInput(prompt: String): String {
    print(prompt)
    return readlnOrNull()?.trim() ?: ""
}

fun formatReadWiseSaying(id: Int, author: String, content: String): String {
    return "$id / $author / $content"
}