package com.book.book_a.view.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.book.book_a.R
import com.book.book_a.databinding.ItemReviewDetailBinding
import com.book.book_a.model.MainReviewDetailBean
import com.book.book_a.utils.ViewUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MainReviewDetailAdapter(val context: Context) : RecyclerView.Adapter<MainReviewDetailAdapter.ViewHolder>() {

    private var bean: MainReviewDetailBean? =null

    fun set( bean: MainReviewDetailBean?){
        this.bean =bean
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ItemReviewDetailBinding
    }

    override fun getItemCount(): Int {
        return 1
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MainReviewDetailAdapter.ViewHolder {
        val binding: ItemReviewDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_review_detail,p0,false)
        val holder:ViewHolder = ViewHolder(binding.root)
        holder.binding = binding
        return holder
    }


    override fun onBindViewHolder(holder: MainReviewDetailAdapter.ViewHolder, position: Int) {
        if (bean != null){
            holder.binding.tvTime.text = bean?.time
            holder.binding.tvTitle.text = bean?.title
            Glide.with(context).load(bean?.userImage).apply(RequestOptions.circleCropTransform()).into( holder.binding.ivIcon)
            holder.binding.tvName.text = bean?.nickname
            holder.binding.tvComment.text = bean?.relatedObj?.title
            when (bean?.rating) {
                "" -> holder.binding.tvRating.visibility = View.GONE
                else -> {
                    holder.binding.tvRating.visibility = View.VISIBLE
                    holder.binding.tvRating.text = bean?.rating
                }
            }
            Glide.with(context).load(bean?.topImgUrl).into(holder.binding.ivTop)
            if (bean?.content!!.isNotEmpty()) {
                ViewUtils().fromText(context, bean?.content!!, holder.binding.tvContent)
            }
        }
    }

}