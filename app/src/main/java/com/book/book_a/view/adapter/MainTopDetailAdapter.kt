package com.book.book_a.view.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.book.book_a.R
import com.book.book_a.databinding.ItemMainTopDetialBinding
import com.book.book_a.model.MainTopDetailBean
import com.book.book_a.utils.ViewUtils
import com.book.book_a.witgets.GlideImageLoader
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer

class MainTopDetailAdapter(val context:Context) : RecyclerView.Adapter<MainTopDetailAdapter.ViewHolder>() {

    private var bean:MainTopDetailBean ? =null

    fun set( bean:MainTopDetailBean?){
        this.bean =bean
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MainTopDetailAdapter.ViewHolder {
        val binding:ItemMainTopDetialBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_main_top_detial,p0,false)
        val holder:ViewHolder = ViewHolder(binding.root)
        holder.binding = binding
        return holder
    }

    override fun getItemCount(): Int {
        return  1
    }

    override fun onBindViewHolder(holder: MainTopDetailAdapter.ViewHolder, p1: Int) {
        if (bean != null) {
            holder.binding.tvTitle.text = bean?.title
            holder.binding.tvTime.text = bean?.time
            holder.binding.tvName.text = bean?.editor
            holder.binding.tvComment.text = "评论:" + bean?.commentCount
            if (bean?.content!!.isNotEmpty()) {
                ViewUtils().fromText(context, bean?.content!!, holder.binding.tvContent)
            }

            if (bean?.images != null){
                holder.binding.banner.visibility = View.VISIBLE

                var imageArray = arrayListOf<String>()

                for (index in bean!!.images){
                    imageArray.add(index.url1)
                }
                //设置banner样式
                holder.binding.banner.setBannerStyle(BannerConfig.NUM_INDICATOR)
                //设置图片加载器
                holder.binding.banner.setImageLoader(GlideImageLoader())
                //设置图片集合
                holder.binding.banner.setImages(imageArray)
                //设置banner动画效果
                holder.binding.banner.setBannerAnimation(Transformer.RotateDown)
                //设置轮播时间
                holder.binding.banner.setDelayTime(3000)
                //设置指示器位置（当banner模式中有指示器时）
                holder.binding.banner.setIndicatorGravity(BannerConfig.CENTER)
                //banner设置方法全部调用完毕时最后调用
                holder.binding.banner.start()
            }else{
                holder.binding.banner.visibility = View.GONE
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding:ItemMainTopDetialBinding
    }

}