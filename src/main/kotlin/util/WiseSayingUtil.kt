package com.util

import com.entity.WiseSaying

fun extractIdFromCommand(command: String): Int? {
    return command.substringAfter("id=", "").toIntOrNull()
}

fun readUserInput(prompt: String): String {
    print(prompt)
    return readlnOrNull()?.trim() ?: ""
}

fun formatReadWiseSaying(id: Int, author: String, content: String): String =
    "$id / $author / $content"

fun toJson(wiseSaying: WiseSaying): String {
    return "{" +
            "\"id\":${wiseSaying.id}," +
            "\"content\":\"${wiseSaying.content}\"," +
            "\"author\":\"${wiseSaying.author}\"" +
            "}"
}

fun fromJson(json: String): WiseSaying? {
    // 공백 제거 후 양쪽 중괄호를 제거
    val trimmedJson = json.trim().removePrefix("{").removeSuffix("}")
    val map = trimmedJson.split(",").mapNotNull { part ->
        val tokens = part.split(":", limit = 2).map { it.trim() }
        if (tokens.size == 2)
            tokens[0].removeSurrounding("\"") to tokens[1]
        else null
    }.toMap()

    var id = map["id"]?.toIntOrNull() ?: return null
    var content = map["content"]?.removeSurrounding("\"") ?: return null
    var author = map["author"]?.removeSurrounding("\"") ?: return null


    return WiseSaying(id, content, author)
}
