package com

import com.controller.WiseSayingController
import com.repository.WiseSayingRepository
import com.service.WiseSayingService

fun main(args: Array<String>) {
    val repository = WiseSayingRepository()
    val service = WiseSayingService(repository)
    val controller = WiseSayingController(service)
    val app = App(controller)
    app.run()
}