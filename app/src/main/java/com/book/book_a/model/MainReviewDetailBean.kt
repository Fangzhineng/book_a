package com.book.book_a.model

data class MainReviewDetailBean(
    val commentCount: Int,
    val content: String,
    val id: Int,
    val nickname: String,
    val rating: String,
    val relatedObj: RelatedObjS,
    val summaryInfo: String,
    val time: String,
    val title: String,
    val topImgUrl: String,
    val type: List<String>,
    val url: String,
    val userImage: String
)

data class RelatedObjS(
    val id: Int,
    val image: String,
    val name: String,
    val rating: Double,
    val releaseDate: String,
    val releaseLocation: String,
    val runtime: String,
    val title: String,
    val titleCn: String,
    val titleEn: String,
    val type: Int,
    val url: String,
    val wapUrl: String
)