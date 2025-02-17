package com.domain.wisesaying.service

import com.domain.wisesaying.entity.WiseSaying
import global.config.SingletonScope

class WiseSayingService {
    private val wiseSayingRepository by lazy { SingletonScope.wiseSayingRepository }

    fun create(content: String, author: String): WiseSaying =
        wiseSayingRepository.save(WiseSaying(content = content, author = author))

    fun findAll(): List<WiseSaying> =
        wiseSayingRepository.findAll()

    fun isEmpty(): Boolean =
        wiseSayingRepository.isEmpty()

    fun findById(id: Int): WiseSaying? =
        wiseSayingRepository.findById(id)

    fun findByKeyword(keyword: String, keywordType: String?): List<WiseSaying> =
        wiseSayingRepository.findByKeyword(keyword, keywordType)

    fun delete(wiseSaying: WiseSaying) {
        wiseSayingRepository.delete(wiseSaying)
    }

    fun update(wiseSaying: WiseSaying, newContent: String, newAuthor: String) {
        wiseSaying.modify(newContent, newAuthor)
        wiseSayingRepository.save(wiseSaying)
    }

    fun build() {
        wiseSayingRepository.build()
    }
}