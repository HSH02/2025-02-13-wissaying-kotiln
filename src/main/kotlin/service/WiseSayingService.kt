package com.service

import com.entity.WiseSaying
import com.repository.WiseSayingRepository

class WiseSayingService {

    private val wiseSayingRepository = WiseSayingRepository()

    fun create(content: String, author: String): WiseSaying {
        return wiseSayingRepository.create(content, author)
    }

    fun read(): ArrayList<WiseSaying> {
        return wiseSayingRepository.read()
    }

    fun delete(id: Int): Boolean {
        return wiseSayingRepository.delete(id)
    }
}