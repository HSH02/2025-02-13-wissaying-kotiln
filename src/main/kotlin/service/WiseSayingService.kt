package com.service

import com.entity.WiseSaying
import com.repository.WiseSayingRepository

class WiseSayingService(private val wiseSayingRepository: WiseSayingRepository) {

    fun create(content: String, author: String): WiseSaying =
        wiseSayingRepository.create(content, author)

    fun findAll(): ArrayList<WiseSaying> =
        wiseSayingRepository.findAll()

    fun findById(id: Int): WiseSaying? =
        wiseSayingRepository.findById(id)

    fun deleteById(id: Int): Boolean =
        wiseSayingRepository.deleteById(id)

    fun update(id: Int, content: String, author: String) =
        wiseSayingRepository.updateById(id, content, author)
}