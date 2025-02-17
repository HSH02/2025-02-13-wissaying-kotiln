package global.config

import com.domain.wisesaying.controller.WiseSayingController
import com.domain.wisesaying.repository.WiseSayingFileRepository
import com.domain.wisesaying.service.WiseSayingService

object SingletonScope {
    val wiseSayingRepository by lazy { wiseSayingFileRepository }
    val wiseSayingFileRepository by lazy { WiseSayingFileRepository() }
    val wiseSayingController by lazy { WiseSayingController() }
    val wiseSayingService by lazy { WiseSayingService() }
}