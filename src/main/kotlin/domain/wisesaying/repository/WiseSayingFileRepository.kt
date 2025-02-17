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

    fun findByKeyword(keyword: String, keywordType: String?): List<WiseSaying> {
        return findAll()
            .filter { wiseSaying ->
                when (keywordType) {
                    "author" -> wiseSaying.author.contains(keyword)
                    "content" -> wiseSaying.content.contains(keyword)
                    else ->
                        wiseSaying.author.contains(keyword) ||
                        wiseSaying.content.contains(keyword)
                }
            }

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

    val tableDirPath: Path
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

    fun saveLastId(lastId: Int) {
        mkdirDbDir()

        tableDirPath.resolve("lastId.txt")
            .toFile()
            .writeText(lastId.toString())
    }

    fun loadLastId(): Int {
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