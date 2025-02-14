package com.repository

import com.entity.WiseSaying

class WiseSayingRepository {
    private var wiseSayings = arrayListOf<WiseSaying>()
    private var lastId = 0

    fun create(content: String, author: String): WiseSaying {
        val wiseSaying = WiseSaying(++lastId, content, author)
        wiseSayings.add(wiseSaying)
        return wiseSaying
    }

    fun findAll(): ArrayList<WiseSaying> =
        wiseSayings

    fun findById(id: Int): WiseSaying? =
        wiseSayings.find { it.id == id }

    fun deleteById(id: Int): Boolean =
        wiseSayings.remove(findById(id))

    fun updateById(id: Int, content: String, author: String) {
        wiseSayings.indexOfFirst { it.id == id }
            .takeIf { it != -1 }
            ?.let { index ->
                wiseSayings[index] = wiseSayings[index].copy(content = content, author = author)
            }
    }

}
