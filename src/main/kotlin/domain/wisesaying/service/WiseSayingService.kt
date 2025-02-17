package com.domain.wisesaying.service

import com.domain.wisesaying.entity.WiseSaying
import global.config.SingletonScope
import standard.dto.Page

class WiseSayingService {
    private val wiseSayingRepository by lazy { SingletonScope.wiseSayingRepository }

    fun create(content: String, author: String): WiseSaying =
        wiseSayingRepository.save(WiseSaying(content = content, author = author))

    fun findAll(): List<WiseSaying> =
        wiseSayingRepository.findAll()

    fun findById(id: Int): WiseSaying? =
        wiseSayingRepository.findById(id)

    fun findByKeyword(keyword: String, keywordType: String?): List<WiseSaying> =
        wiseSayingRepository.findByKeyword(keyword, keywordType)

    fun findAllPaged(itemsPerPage: Int, pageNo: Int): Page<WiseSaying> {
        return wiseSayingRepository.findAllPaged(itemsPerPage, pageNo)
    }

    fun findByKeywordPaged(
        keywordType: String,
        keyword: String,
        itemsPerPage: Int = 5,
        pageNo: Int
    ): Page<WiseSaying> {
        return wiseSayingRepository.findByKeywordPaged(keywordType, keyword, itemsPerPage, pageNo)
    }

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

    fun isEmpty(): Boolean =
        wiseSayingRepository.isEmpty()
}