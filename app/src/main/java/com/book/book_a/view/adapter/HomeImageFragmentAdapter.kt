package com.book.book_a.view.adapter

import android.content.Context
import com.book.book_a.R
import com.book.book_a.model.Image
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class HomeImageFragmentAdapter (val context: Context, images:List<Image> = arrayListOf()): BaseQuickAdapter<Image, BaseViewHolder>(R.layout.item_main_list_img,images) {

    override fun convert(helper: BaseViewHolder?, item: Image?) {
        helper?:return
        item?:return
        var position:Int = helper.adapterPosition
        Glide.with(context).load(item.url1).into(helper.getView(R.id.iv_img))
    }
}