package domain.wisesaying.repository

import com.domain.wisesaying.entity.WiseSaying
import standard.dto.Page

interface WiseSayingRepository {
    fun save(wiseSaying: WiseSaying): WiseSaying

    fun isEmpty(): Boolean

    fun findAll(): List<WiseSaying>

    fun findById(id: Int): WiseSaying?

    fun findByKeyword(keyword: String, keywordType: String?): List<WiseSaying>

    fun findAllPaged(itemsPerPage: Int, pageNo: Int): Page<WiseSaying>

    fun findByKeywordPaged(
        keywordType: String,
        keyword: String,
        itemsPerPage: Int,
        pageNo: Int
    ): Page<WiseSaying>

    fun delete(wiseSaying: WiseSaying)

    fun clear()

    fun build()
}