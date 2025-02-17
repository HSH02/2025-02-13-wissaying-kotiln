package com.domain.wisesaying.entity

data class WiseSaying(
    var content: String,
    var author: String,
    var id: Int = 0
) {
    fun modify(newContent: String, newAuthor: String) {
        content = newContent
        author = newAuthor
    }

    fun isNew() = id == 0

    val json: String
        get() = """
            {
                "id":$id,
                "content":"$content",
                "author":"$author"
            }
            """
            .trimIndent()

    companion object {
        fun fromJson(jsonStr: String): WiseSaying {
            val map = jsonStrToMap(jsonStr)
            return WiseSaying(
                content = map["content"] ?: "",
                author = map["author"] ?: "",
                id = (map["id"] ?: "0").toInt()
            )
        }

        private fun jsonStrToMap(jsonStr: String): Map<String, String> {
            return jsonStr
                .removeSurrounding("{", "}")
                .split(",")
                .mapNotNull {
                    val keyValue =
                        it.split(":", limit = 2).map { it.trim().removeSurrounding("\"") }
                    if (keyValue.size == 2) keyValue[0] to keyValue[1] else null
                }.toMap()
        }
    }

}