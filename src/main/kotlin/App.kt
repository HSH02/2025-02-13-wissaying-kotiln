package com

import com.controller.WiseSayingController

class App {

    private val wiseSayingController = WiseSayingController()

    fun run() {
        println("== 명언 앱 ==")

        while (true) {
            print("명령) ")
            val input = readLine()?.trim() ?: continue

            if(input.equals("종료")){
                println("프로그램을 종료합니다.")
                break;
            }

            wiseSayingController.process(input)

        }
    }
}
