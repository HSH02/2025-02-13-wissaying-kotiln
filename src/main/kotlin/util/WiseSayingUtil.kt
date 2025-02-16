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

fun WiseSaying.toJson(space : String = ""): String {
    return  "${space}{\n" +
            "${space}  \"id\":${this.id},\n" +
            "${space}  \"content\":\"${this.content}\",\n" +
            "${space}  \"author\":\"${this.author}\"\n" +
            "${space}}"
}

fun String.fromJson(): WiseSaying? {
    // 공백 제거 후 양쪽 중괄호를 제거
    val trimmedJson = this.trim().removePrefix("{").removeSuffix("}")
    val map = trimmedJson.split(",").mapNotNull { part ->
        val tokens = part.split(":", limit = 2).map { it.trim() }
        if (tokens.size == 2)
            tokens[0].removeSurrounding("\"") to tokens[1]
        else null
    }.toMap()

    val id = map["id"]?.toIntOrNull() ?: return null
    val content = map["content"]?.removeSurrounding("\"") ?: return null
    val author = map["author"]?.removeSurrounding("\"") ?: return null


    return WiseSaying(id, content, author)
}

fun List<WiseSaying>.buildDataJson(): String {
    return buildString {
        append("[\n")
        this@buildDataJson.forEachIndexed { index, wiseSaying ->
            append(wiseSaying.toJson("  "))
            append(if (index < this@buildDataJson.size - 1) ",\n" else "\n")
        }
        append("]")
    }
}
