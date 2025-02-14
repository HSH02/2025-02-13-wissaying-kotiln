package com.repository

import com.entity.WiseSaying

class WiseSayingRepository {
    private var wiseSayings = ArrayList<WiseSaying>()
    private var lastId = 0

    fun create(content: String, author: String): WiseSaying {
        val wiseSaying = WiseSaying(++lastId, content, author)
        wiseSayings.add(wiseSaying)
        return wiseSaying
    }

    fun findAll(): ArrayList<WiseSaying> {
        return wiseSayings
    }

    fun findById(id: Int): WiseSaying? {
        return wiseSayings.find { it.id == id }
    }

    fun deleteById(id: Int): Boolean {
        val index = findById(id)
        return wiseSayings.remove(index)
    }

    fun updateById(id: Int, content: String, author: String) {
        val index = wiseSayings.indexOfFirst { it.id == id }
        if (index != -1) {
            wiseSayings[index] = wiseSayings[index].copy(content = content, author = author)
        }

    }


}