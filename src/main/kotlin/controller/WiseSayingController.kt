package com.controller

import com.entity.WiseSaying
import com.service.WiseSayingService
import com.util.extractIdFromCommand

class WiseSayingController {

    private val wiseSayingService = WiseSayingService()

    fun process(cmd: String) {
        when {
            cmd.startsWith("등록") -> handleCreate()
            cmd.startsWith("목록") -> handleRead()
            cmd.startsWith("수정") -> handleUpdate()
            cmd.startsWith("삭제") -> handleDelete(cmd)
            else -> println("알 수 없는 명령어입니다. 다시 입력해주세요.")
        }
    }

    fun handleCreate() {
        print("명언 : ")
        val content = readLine()?.trim() ?: return
        print("작가 : ")
        val author = readLine()?.trim() ?: return
        val wiseSaying = wiseSayingService.create(content, author)
        println("${wiseSaying.id}번 명언이 등록되었습니다.")
    }

    fun handleRead() {
        val list: ArrayList<WiseSaying> = wiseSayingService.read()

        println("번호 / 작가 / 명언")
        println("----------------------")

        for (wiseSaying in list) {
            println("${wiseSaying.id} / ${wiseSaying.author} / ${wiseSaying.content}")
        }


    }

    fun handleUpdate() {

    }

    fun handleDelete(cmd: String) {
        val id = extractIdFromCommand(cmd)

        if (id == null) {
            println("올바른 id를 입력해주세요.")
            return
        }

        if (wiseSayingService.delete(id)) {
            println("${id}번 명언이 삭제되었습니다.")
        } else {
            println("${id}번 명언은 존재하지 않습니다.")
        }
    }
}