package com

import global.config.SingletonScope.wiseSayingController
import global.rq.Rq

class WiseSayingApp {

    fun run() {
        println("== 명언 앱 ==")

        while (true) {
            print("명령) ")

            val input = readlnOrNull()?.trim() ?: continue

            val rq = Rq(input)

            when (rq.action) {
                "종료" -> {
                    println("프로그램을 종료합니다.")
                    break
                }

                "등록" -> wiseSayingController.handleCreate()
                "목록" -> wiseSayingController.handleRead()
                "수정" -> wiseSayingController.handleUpdate(rq)
                "삭제" -> wiseSayingController.handleDelete(rq)
                "빌드" -> wiseSayingController.handleBuild()

                else -> println("알 수 없는 명령어입니다. 다시 입력해주세요.")
            }
        }

    }
}

