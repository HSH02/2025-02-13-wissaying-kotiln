package com.domain.wisesaying.controller

import com.domain.wisesaying.entity.WiseSaying
import global.config.SingletonScope
import global.rq.Rq

class WiseSayingController {
    private val wiseSayingService by lazy { SingletonScope.wiseSayingService }

    private fun prompt(promptMessage: String): String {
        print(promptMessage)
        return readlnOrNull()?.trim().orEmpty()
    }

    fun handleCreate() {
        val content = prompt("명언 : ")
        val author = prompt("작가 : ")

        wiseSayingService.create(content, author)
            .also { println("${it.id}번 명언이 등록되었습니다.") }
    }


    fun handleRead(rq: Rq) {
        if (wiseSayingService.isEmpty()) {
            println("등록된 명언이 없습니다.")
            return
        }

        val keyword = rq.getParamValue("keyword", "")
        val keywordType = rq.getParamValue("keywordType")

        val itemsPerPage = 5
        val pageNo: Int = rq.getParamValueAsInt("page", 1)


        val wiseSayingPage = if (keywordType?.isNotEmpty() == true) {
            wiseSayingService.findByKeywordPaged(keyword, keywordType, itemsPerPage, pageNo)
        } else {
            wiseSayingService.findAllPaged(itemsPerPage, pageNo)
        }

        if (keyword.isNotBlank()) {
            println("----------------------")
            println("검색타입 : $keywordType")
            println("검색어 : $keyword")
            println("----------------------")
        }

        println("번호 / 작가 / 명언")
        println("----------------------")
        wiseSayingPage.content.forEach {
            println("${it.id} / ${it.author} / ${it.content}")
        }

        println("----------------------")
        // 총 페이지 수 계산 (예: 전체 10개, 페이지당 5개이면 2페이지)
        val totalPages = if (wiseSayingPage.totalItems % itemsPerPage == 0)
            wiseSayingPage.totalItems / itemsPerPage
        else
            wiseSayingPage.totalItems / itemsPerPage + 1

        // 네비게이션 문자열 작성 (현재 페이지는 대괄호로 표시)
        val navigation = (1..totalPages).joinToString(" / ") { page ->
            if (page == wiseSayingPage.pageNo) "[$page]" else "$page"
        }

        println("페이지 : $navigation")
    }

    fun handleDelete(rq: Rq) {
        val wiseSaying = getWiseSayingById(rq) ?: return

        wiseSayingService.delete(wiseSaying)

        println("${wiseSaying.id}번 명언을 삭제하였습니다.")
    }

    fun handleUpdate(rq: Rq) {
        val wiseSaying = getWiseSayingById(rq) ?: return

        println("명언(기존) : ${wiseSaying.content}")
        val newContent = prompt("명언 : ")

        println("작가(기존) : ${wiseSaying.author}")
        val newAuthor = prompt("작가 : ")

        wiseSayingService.update(wiseSaying, newContent, newAuthor)

        println("${wiseSaying.id}번 명언을 수정하였습니다.")
    }

    fun handleBuild() {
        wiseSayingService.build()
        println("data.json 파일의 내용이 갱신되었습니다.")
    }

    private fun getWiseSayingById(rq: Rq): WiseSaying? {
        val id = rq.getParamValueAsInt("id", 0)
        if (id == 0) {
            println("id를 정확히 입력해주세요.")
            return null
        }

        val wiseSaying = wiseSayingService.findById(id)
        if (wiseSaying == null) {
            println("${id}번 명언은 존재하지 않습니다.")
            return null
        }

        return wiseSaying
    }


}
