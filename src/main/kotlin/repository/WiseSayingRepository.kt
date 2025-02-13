package com.repository

import com.entity.WiseSaying

class WiseSayingRepository {
    private var wiseSayings = ArrayList<WiseSaying>()
    private var lastId = 0

    fun create(content: String, author: String): WiseSaying {
        val wiseSaying = WiseSaying(++lastId, content, author)
        wiseSayings.add(wiseSaying)
        return wiseSaying;
    }

    fun read(): ArrayList<WiseSaying> {
        return wiseSayings
    }

    fun delete(id: Int) : Boolean {
        val index = findById(id)
        return wiseSayings.remove(index)
    }

    fun findById(id: Int): WiseSaying? {
        return wiseSayings.find { it.id == id }
    }
}