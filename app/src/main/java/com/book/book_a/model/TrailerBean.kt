package com.book.book_a.model

data class TrailerBean(
    val trailers: List<TrailerBody>
)

data class TrailerBody(
    val coverImg: String,
    val hightUrl: String,
    val id: Int,
    val movieId: Int,
    val movieName: String,
    val rating: Double,
    val summary: String,
    val type: List<String>,
    val url: String,
    val videoLength: Int,
    val videoTitle: String
)