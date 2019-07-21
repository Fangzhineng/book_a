package com.book.book_a.model

data class ReviewBean(
    val commentCount: Int,
    val id: Int,
    val image: String,
    val nickname: String,
    val rating: String,
    val relatedObj: RelatedObj,
    val summary: String,
    val title: String,
    val userImage: String
)

data class RelatedObj(
    val id: Int,
    val image: String,
    val rating: String,
    val title: String,
    val titleEn: String,
    val type: Int,
    val year: String
)