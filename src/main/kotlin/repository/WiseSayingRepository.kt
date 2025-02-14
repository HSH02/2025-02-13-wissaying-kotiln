package com.repository

import com.entity.WiseSaying
import com.util.DB.DB_URL
import com.util.DB.LAST_ID_FILE
import com.util.fromJson
import com.util.toJson
import java.io.File

class WiseSayingRepository {
    private var wiseSayings = arrayListOf<WiseSaying>()
    private var lastId = 0

    private val dbFolder = File(DB_URL)
    private val lastIdFile = File(dbFolder, LAST_ID_FILE)

    init {
        // 데이터베이스 폴더가 존재하지 않으면 생성
        if (!dbFolder.exists()) dbFolder.mkdirs()
        // 마지막 ID를 저장하는 파일이 없으면 생성 후 초기값 기록
        if (!lastIdFile.exists()) lastIdFile.writeText(lastId.toString())

        // 마지막 ID 파일을 읽어 lastId 변수에 할당, 없으면 0으로
        lastId = lastIdFile.readText().toIntOrNull() ?: 0

        //  dbFolder 내의 모든 JSON 파일을 순회하며 파일 내용을 읽어 객체로 변환 후 리스트에 추가
        dbFolder.listFiles { file -> file.extension == "json" }?.forEach { file ->
            val json = file.readText()
            fromJson(json)?.let { wiseSayings.add(it) }
        }
    }

    fun create(content: String, author: String): WiseSaying {
        val newId = ++lastId
        val wiseSaying = WiseSaying(newId, content, author)
        wiseSayings.add(wiseSaying)

        // 새 객체를 JSON 문자열로 변환하여 별도의 파일로 저장 (파일명은 ID.json)
        File(dbFolder, "$newId.json").writeText(toJson(wiseSaying))
        // 파일 관련: 마지막 ID 파일 업데이트
        lastIdFile.writeText(newId.toString())
        return wiseSaying
    }

    fun findAll(): ArrayList<WiseSaying> =
        wiseSayings

    fun findById(id: Int): WiseSaying? =
        wiseSayings.find { it.id == id }

    fun deleteById(id: Int): Boolean {
        val wiseSaying = findById(id) ?: return false
        if (wiseSayings.remove(wiseSaying)) {
            // 파일 관련: 삭제 대상 객체의 JSON 파일 제거
            File(dbFolder, "$id.json").delete()
            return true
        }
        return false
    }


    fun updateById(id: Int, content: String, author: String) {
        wiseSayings.indexOfFirst { it.id == id }
            .takeIf { it != -1 }
            ?.let { index ->
                val updated = wiseSayings[index].copy(content = content, author = author)
                wiseSayings[index] = updated
                // 업데이트된 객체를 JSON 문자열로 변환하여 기존 파일에 덮어쓰기
                File(dbFolder, "$id.json").writeText(toJson(updated))
            }
    }

}
