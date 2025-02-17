package domain.wisesaying.repository

import com.domain.wisesaying.entity.WiseSaying
import com.global.bean.DBConfig
import global.config.SingletonScope
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class WiseSayingFileRepositoryTest {
    private val wiseSayingRepository = SingletonScope.wiseSayingFileRepository

    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            DBConfig.setModeToTest()
        }
    }

    @BeforeEach
    fun setUp() {
        wiseSayingRepository.clear()
    }

    @Test
    fun save() {
        val wiseSaying = wiseSayingRepository
            .save(WiseSaying("나의 죽음을 적들에게 알리지 말라.", "충무공 이순신"))

        val filePath = wiseSayingRepository
            .tableDirPath
            .toFile()
            .listFiles()
            ?.find { it.name == "${wiseSaying.id}.json" }

        assertThat(filePath).isNotNull
    }

    @Test
    fun findById() {
        val wiseSaying = wiseSayingRepository
            .save(WiseSaying("나의 죽음을 적들에게 알리지 말라.", "충무공 이순신"))

        val foundWiseSaying = wiseSayingRepository.findById(wiseSaying.id)

        assertThat(foundWiseSaying).isEqualTo(wiseSaying)
    }

    @Test
    fun `saveLastId, loadLastId`() {
        wiseSayingRepository.saveLastId(10)

        assertThat(wiseSayingRepository.loadLastId()).isEqualTo(10)
    }

    @Test
    fun delete() {
        val wiseSaying = wiseSayingRepository
            .save(WiseSaying("나의 죽음을 적들에게 알리지 말라.", "충무공 이순신"))

        wiseSayingRepository.delete(wiseSaying)

        assertThat(wiseSayingRepository.findById(wiseSaying.id)).isNull()
    }

    @Test
    fun findAll() {
        val wiseSaying1 = wiseSayingRepository
            .save(WiseSaying("나의 죽음을 적들에게 알리지 말라.", "충무공 이순신"))
        val wiseSaying2 = wiseSayingRepository
            .save(WiseSaying("나는 나를 파괴할 수 있는 사람이 없다.", "바토르"))

        val foundWiseSayings = wiseSayingRepository.findAll()

        assertThat(foundWiseSayings).containsExactly(wiseSaying1, wiseSaying2)
    }
}
