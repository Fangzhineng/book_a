package com.book.book_a.model

data class TopBean(
    val news: News,
    val review: Review,
    val topList: TopList,
    val trailer: Trailer,
    val viewingGuide: ViewingGuide
)

data class Review(
    val imageUrl: String,
    val movieName: String,
    val posterUrl: String,
    val reviewID: Int,
    val title: String
)

data class Trailer(
    val hightUrl: String,
    val imageUrl: String,
    val movieId: Int,
    val mp4Url: String,
    val title: String,
    val trailerID: Int
)

data class TopList(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val type: Int
)

data class News(
    val imageUrl: String,
    val newsID: Int,
    val title: String,
    val type: Int,
    val url: String
)

data class ViewingGuide(
    val id: String,
    val imageUrl: String
)