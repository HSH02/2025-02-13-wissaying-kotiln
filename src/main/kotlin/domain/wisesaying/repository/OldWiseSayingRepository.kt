//package com.domain.wisesaying.repository
//
//import com.domain.wisesaying.entity.WiseSaying
//import com.global.bean.DB.DATA_JSON
//import com.global.bean.DB.DB_URL
//import com.global.bean.DB.LAST_ID_FILE
//import com.util.buildDataJson
//import com.util.fromJson
//import com.util.toJson
//import java.io.File
//
//// TODO: 예외 처리, 일관성 처리 필요
//class OldWiseSayingRepository {
//    private var wiseSayings = mutableListOf<WiseSaying>()
//    private var lastId = 0
//
//    private val dbFolder = File(DB_URL)
//    private val lastIdFile = File(dbFolder, LAST_ID_FILE)
//    private val buildJsonFile = File(dbFolder, DATA_JSON)
//
//    init {
//        // 데이터베이스, 파일이 없을 경우 생성
//        if (!dbFolder.exists()) dbFolder.mkdirs()
//        if (!lastIdFile.exists()) lastIdFile.writeText(lastId.toString())
//        if (!buildJsonFile.exists()) buildJsonFile.createNewFile()
//
//        // 마지막 ID 파일을 읽어 lastId 변수에 할당, 없으면 0으로
//        lastId = lastIdFile.readText().toIntOrNull() ?: 0
//
//        //  dbFolder 내의 모든 JSON 파일을 순회하며 파일 내용을 읽어 객체로 변환 후 리스트에 추가
//        dbFolder.listFiles { file ->
//            file.extension == "json" && file.name != DATA_JSON
//        }?.forEach { file ->
//            val json = file.readText()
//            json.fromJson()?.let { wiseSayings.add(it) }
//        }
//    }
//
//    fun create(wiseSaying: WiseSaying): WiseSaying {
//
//        if(wiseSaying.isNew) {
//            val newId = ++lastId
//            wiseSayings.add(wiseSaying)
//
//            // 파일 저장
//            File(dbFolder, "$newId.json").writeText(wiseSaying.toJson())
//            lastIdFile.writeText(newId.toString())
//        }
//
//        return wiseSaying
//    }
//
//    fun findAll(): List<WiseSaying> =
//        wiseSayings
//
//    fun findById(id: Int): WiseSaying? =
//        wiseSayings.firstOrNull() { it.id == id }
//
//    fun deleteById(id: Int): Boolean {
//        val wiseSaying = findById(id) ?: return false
//        if (wiseSayings.remove(wiseSaying)) {
//            // 파일 관련: 삭제 대상 객체의 JSON 파일 제거
//            File(dbFolder, "$id.json").delete()
//            return true
//        }
//        return false
//    }
//
//    fun updateById(id: Int, content: String, author: String) {
//        wiseSayings.indexOfFirst { it.id == id }
//            .takeIf { it != -1 }
//            ?.let { index ->
//                val updated = wiseSayings[index].copy(content = content, author = author)
//                wiseSayings[index] = updated
//                // 업데이트된 객체를 JSON 문자열로 변환하여 기존 파일에 덮어쓰기
//                File(dbFolder, "$id.json").writeText(updated.toJson())
//            }
//    }
//
//    fun build() {
//        val wiseSayings = findAll()
//        val dataJson = wiseSayings.buildDataJson()
//        buildJsonFile.writeText(dataJson)
//    }
//
//}
