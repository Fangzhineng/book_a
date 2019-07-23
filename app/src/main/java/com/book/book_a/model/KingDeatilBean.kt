package com.book.book_a.model

data class KingDeatilBean(
    val movies: List<Movy>,
    val nextTopList: NextTopList,
    val pageCount: Int,
    val previousTopList: PreviousTopList,
    val topList: TopList1,
    val totalCount: Int
)

data class Movy(
    val actor: String,
    val actor2: String,
    val decade: Int,
    val director: String,
    val id: Int,
    val movieType: String,
    val name: String,
    val nameEn: String,
    val posterUrl: String,
    val rankNum: Int,
    val rating: Double,
    val releaseDate: String,
    val releaseLocation: String,
    val remark: String
)

data class NextTopList(
    val toplistId: Int,
    val toplistItemType: Int,
    val toplistType: Int
)

data class PreviousTopList(
    val toplistId: Int,
    val toplistItemType: Int,
    val toplistType: Int
)

data class TopList1(
    val id: Int,
    val name: String,
    val summary: String
)