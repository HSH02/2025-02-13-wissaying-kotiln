package com.controller

import com.entity.WiseSaying
import com.service.WiseSayingService
import com.util.extractIdFromCommand
import com.util.formatReadWiseSaying
import com.util.readUserInput

class WiseSayingController(private val wiseSayingService: WiseSayingService) {

    fun process(cmd: String) {
        when {
            cmd.startsWith("등록") -> handleCreate()
            cmd.startsWith("목록") -> handleRead()
            cmd.startsWith("수정") -> handleUpdate(cmd)
            cmd.startsWith("삭제") -> handleDelete(cmd)
            else -> println("알 수 없는 명령어입니다. 다시 입력해주세요.")
        }
    }

    private fun handleCreate() {
        val content = readUserInput("명언 : ")
        if (content.isEmpty()) {
            println("명언을 입력해주세요.")
            return
        }

        val author = readUserInput("작가 : ")
        if (author.isEmpty()) {
            println("작가를 입력해주세요.")
            return
        }

        val wiseSaying = wiseSayingService.create(content, author)
        println("${wiseSaying.id}번 명언이 등록되었습니다.")
    }

    private fun handleRead() {
        val list: List<WiseSaying> = wiseSayingService.findAll()

        println("번호 / 작가 / 명언")
        println("----------------------")
        for (wiseSaying in list) {
            println(
                formatReadWiseSaying(
                    id = wiseSaying.id,
                    author = wiseSaying.author,
                    content = wiseSaying.content
                )
            )
        }


    }

    private fun handleUpdate(cmd: String) {
        val id = extractIdFromCommand(cmd)
        if (id == null) {
            println("올바른 id를 입력해주세요.")
            return
        }

        val wiseSaying = wiseSayingService.findById(id)
        if (wiseSaying == null) {
            println("${id}번 명언은 존재하지 않습니다.")
            return
        }

        println("명언(기존) : ${wiseSaying.content}")
        val content = readUserInput("명언) ")
        println("작가(기존) : ${wiseSaying.author}")
        val author = readUserInput("작가) ")

        wiseSayingService.update(wiseSaying.id, content, author)
        println("수정이 완료되었습니다.")
    }

    private fun handleDelete(cmd: String) {
        val id = extractIdFromCommand(cmd)

        if (id == null) {
            println("올바른 id를 입력해주세요.")
            return
        }

        if (wiseSayingService.deleteById(id)) {
            println("${id}번 명언이 삭제되었습니다.")
        } else {
            println("${id}번 명언은 존재하지 않습니다.")
        }
    }
}