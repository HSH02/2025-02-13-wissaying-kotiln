package com.controller

import com.service.WiseSayingService
import com.util.extractIdFromCommand
import com.util.formatReadWiseSaying
import com.util.readUserInput

class WiseSayingController(private val wiseSayingService: WiseSayingService) {

    fun process(cmd: String) = when {
        cmd.startsWith("등록") -> handleCreate()
        cmd.startsWith("목록") -> handleRead()
        cmd.startsWith("수정") -> handleUpdate(cmd)
        cmd.startsWith("삭제") -> handleDelete(cmd)
        cmd.startsWith("빌드") -> handleBuild()
        else -> println("알 수 없는 명령어입니다. 다시 입력해주세요.")
    }

    private fun handleCreate() {
        val content = readUserInput("명언 : ").takeIf { it.isNotBlank() } ?: run {
            println("명언을 입력해주세요.")
            return
        }

        val author = readUserInput("작가 : ").takeIf { it.isNotBlank() } ?: run {
            println("작가을 입력해주세요.")
            return
        }

        wiseSayingService.create(content, author)
            .also { "${it.id}번 명언이 등록되었습니다." }
    }


    private fun handleRead() {
        println("번호 / 작가 / 명언")
        println("----------------------")
        wiseSayingService.findAll().forEach {
            println(formatReadWiseSaying(it.id, it.author, it.content))
        }
    }

    private fun handleUpdate(cmd: String) {
        extractIdFromCommand(cmd)?.let { id ->
            wiseSayingService.findById(id)?.let { wiseSaying ->
                println("명언(기존) : ${wiseSaying.content}")
                val content = readUserInput("명언) ")
                println("작가(기존) : ${wiseSaying.author}")
                val author = readUserInput("작가) ")

                wiseSayingService.update(wiseSaying.id, content, author)
                println("수정이 완료되었습니다.")
            } ?: println("${id}번 명언은 존재하지 않습니다.")
        } ?: println("올바른 id를 입력해주세요.")
    }

    private fun handleDelete(cmd: String) {
        extractIdFromCommand(cmd)?.let { id ->
            if (wiseSayingService.deleteById(id)) {
                println("${id}번 명언이 삭제되었습니다.")
            } else {
                println("${id}번 명언은 존재하지 않습니다.")
            }
        } ?: println("올바른 id를 입력해주세요.")
    }

    private fun handleBuild() {
        wiseSayingService.build()
        println("data.json 파일의 내용이 갱신되었습니다.")
    }
}
