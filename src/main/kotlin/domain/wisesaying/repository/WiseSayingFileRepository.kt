package com.domain.wisesaying.repository

import com.domain.wisesaying.entity.WiseSaying
import com.global.bean.DBConfig
import com.ll.domain.wiseSaying.wiseSaying.repository.WiseSayingRepository
import java.nio.file.Path

class WiseSayingFileRepository : WiseSayingRepository {

    override fun save(wiseSaying: WiseSaying): WiseSaying {
        if (wiseSaying.isNew()) {
            wiseSaying.id = loadLastId() + 1
            saveLastId(wiseSaying.id)
        }

        saveOnDisk(wiseSaying)

        return wiseSaying
    }

    override fun isEmpty(): Boolean {
        return findAll().isEmpty()
    }

    override fun findAll(): List<WiseSaying> {
        return tableDirPath
            .toFile()
            .listFiles()
            ?.filter { it.extension == "json" }
            ?.map { WiseSaying.fromJson(it.readText()) }
            ?: emptyList()
    }

    override fun findById(id: Int): WiseSaying? {
        return tableDirPath
            .toFile()
            .listFiles()
            ?.find { it.name == "${id}.json" }
            ?.let { WiseSaying.fromJson(it.readText()) }
    }

    override fun delete(wiseSaying: WiseSaying) {
        tableDirPath.resolve("${wiseSaying.id}.json").toFile().delete()
    }

    override fun clear() {
        tableDirPath.toFile().deleteRecursively()
    }

    override fun build() {
        val wiseSayings = findAll()

        val jsonArray = wiseSayings.joinToString(
            prefix = "[\n",
            postfix = "\n]",
            separator = ",\n"
        ) { it.json.prependIndent("  ") }

        val buildJsonFile = tableDirPath.resolve("data.json").toFile()

        buildJsonFile.writeText(jsonArray)
    }

    private val tableDirPath: Path
        get() {
            return DBConfig.dbDirPath.resolve("wiseSaying")
        }

    private fun mkdirDbDir() {
        tableDirPath.toFile().run {
            if (!exists()) {
                mkdirs()
            }
        }
    }

    private fun saveOnDisk(wiseSaying: WiseSaying) {
        mkdirDbDir()

        val wiseSayingFile = tableDirPath.resolve("${wiseSaying.id}.json")
        wiseSayingFile.toFile().writeText(wiseSaying.json)
    }

    private fun saveLastId(lastId: Int) {
        mkdirDbDir()

        tableDirPath.resolve("lastId.txt")
            .toFile()
            .writeText(lastId.toString())
    }

    private fun loadLastId(): Int {
        return try {
            tableDirPath.resolve("lastId.txt")
                .toFile()
                .readText()
                .toInt()
        } catch (e: Exception) {
            0
        }
    }
}