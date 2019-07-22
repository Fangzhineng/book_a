package com.book.book_a.model

data class MainTopDetailBean(
    val author: String,
    val commentCount: Int,
    val content: String,
    val editor: String,
    val id: Int,
    val images: List<Images>,
    val nextNewsID: Int,
    val previousNewsID: Int,
    val relations: List<Relation>,
    val source: String,
    val time: String,
    val title: String,
    val title2: String,
    val type: Int,
    val url: String,
    val wapUrl: String
)

data class Images(
    val desc: String,
    val gId: Int,
    val title: String,
    val url1: String,
    val url2: String
)

data class Relation(
    val id: Int,
    val image: String,
    val name: String,
    val rating: Double,
    val relaseLocation: String,
    val releaseDate: String,
    val scoreCount: Int,
    val type: Int,
    val year: String
)