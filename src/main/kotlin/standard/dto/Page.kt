package standard.dto

import kotlin.math.ceil

class Page<T>(
    val totalItems: Int,
    val itemsPerPage: Int,
    val pageNo: Int,
    val keywordType: String,
    val keyword: String,
    val _content: List<T>
) {
    private val totalPages = ceil(totalItems.toDouble() / itemsPerPage).toInt()

    val content: List<T>
        get() = _content
}