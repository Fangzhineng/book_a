package com.book.book_a.model

import com.chad.library.adapter.base.entity.MultiItemEntity


data class MainListBean(
    val newsList: List<NewsList>,
    val pageCount: Int,
    val totalCount: Int


)

data class NewsList(
    val commentCount: Int,
    val id: Int,
    val image: String,
    val images: List<Image>,
    val imagesCount: Int,
    val publishTime: Long,
    val summary: String,
    val summaryInfo: String,
    val tag: String,
    val title: String,
    val title2: String,
    val type: Int
): MultiItemEntity {
    private var itemType = 0
    override fun getItemType(): Int {
        return itemType
    }
}


data class Image(
    val desc: String,
    val gId: Int,
    val title: String,
    val url1: String,
    val url2: String
)
