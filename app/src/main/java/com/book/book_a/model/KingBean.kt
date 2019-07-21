package com.book.book_a.model

data class KingBean(
    val pageCount: Int,
    val topLists: List<TopLists>,
    val totalCount: Int
)

data class TopLists(
    val id: Int,
    val summary: String,
    val topListNameCn: String,
    val topListNameEn: String,
    val type: Int
)