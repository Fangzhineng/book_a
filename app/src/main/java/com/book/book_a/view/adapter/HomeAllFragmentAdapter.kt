package com.book.book_a.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.book.book_a.R
import com.book.book_a.canstants.CustomCanstant
import com.book.book_a.canstants.CustomCanstant.Companion.ViewFour
import com.book.book_a.canstants.CustomCanstant.Companion.ViewOne
import com.book.book_a.canstants.CustomCanstant.Companion.ViewThree
import com.book.book_a.canstants.CustomCanstant.Companion.ViewTwo
import com.book.book_a.databinding.ItemMainList0Binding
import com.book.book_a.databinding.ItemMainList1Binding
import com.book.book_a.databinding.ItemMainList2Binding
import com.book.book_a.model.News
import com.book.book_a.model.NewsList
import com.book.book_a.utils.DateTimeUtil
import com.bumptech.glide.Glide
import com.book.book_a.databinding.ItemMainList3Binding as ItemMainList3Binding

class HomeAllFragmentAdapter (var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mData:List<NewsList> = arrayListOf()

    var news: News?=null

    fun setData(data:List<NewsList>){
        mData = data
        notifyDataSetChanged()
    }

    fun setData(news: News){
        this.news = news
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        when (p1) {
            ViewOne -> {
                val binding: ItemMainList3Binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_main_list_3,p0,false)
                val holder: OneViewHolder = OneViewHolder(binding.root)
                holder.binding = binding
                return holder
            }
            ViewTwo -> {
                val binding: ItemMainList0Binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_main_list_0,p0,false)
                val holder:TwoViewHolder = TwoViewHolder(binding.root)
                holder.binding = binding
                return holder
            }
            ViewThree -> {
                val binding: ItemMainList1Binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_main_list_1,p0,false)
                val holder: ThreeViewHolder = ThreeViewHolder(binding.root)
                holder.binding = binding
                return holder
            }
            else -> {
                val binding: ItemMainList2Binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_main_list_2,p0,false)
                val holder: FourViewHolder = FourViewHolder(binding.root)
                holder.binding = binding
                return holder
            }
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, i: Int) {
        if (i == 0){
            (holder as OneViewHolder).binding.tvTitle.text = news?.title
            Glide.with(context).load(news?.imageUrl).into(holder.binding.ivTop)
        }else{
            if (mData.isNotEmpty()){
                val p1= i -1
                when(mData[p1].type){
                    0-> {
                        (holder as TwoViewHolder).binding.tvTitle.text = mData[p1].title
                        holder.binding.tvTime.text =DateTimeUtil().getDateToString( mData[p1].publishTime, CustomCanstant.DF_YYYY_MM_DD_HH_MM)
                        holder.binding.tvCommentCount.text = "评论:"+mData[p1].commentCount.toString()
                        holder.binding.tvTitle2.text = mData[p1].title2
                        Glide.with(context).load(mData[p1].image).into(holder.binding.ivList)
                    }
                    1->{
                        (holder as ThreeViewHolder).binding.tvTitle.text = mData[p1].title
                        holder.binding.recyclerIv.layoutManager = GridLayoutManager(context,3)
                        holder.binding.recyclerIv.adapter = HomeImageFragmentAdapter(context,mData[p1].images)
                    }
                    else->{
                        (holder as FourViewHolder).binding.tvTitle.text = mData[p1].title
                        holder.binding.tvTime.text =DateTimeUtil().getDateToString( mData[p1].publishTime,CustomCanstant.DF_YYYY_MM_DD_HH_MM)
                        holder.binding.tvCommentCount.text ="评论:"+mData[p1].commentCount.toString()
                        Glide.with(context).load(mData[p1].image).into(holder.binding.ivList)
                        holder.binding.tvTitle2.text = mData[p1].title2
                        holder.binding.ivPlay.setOnClickListener {

                        }
                    }
                }

            }
        }

    }


    override fun getItemViewType(position: Int): Int {
        return  when(position){
            0-> ViewOne
            else-> {
                return when (mData[position-1].type){
                    0 -> ViewTwo
                    1-> ViewThree
                    else-> ViewFour
                }
            }


        }
    }

    override fun getItemCount(): Int {
        return mData.size + 1
    }


    class OneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ItemMainList3Binding
    }

    class TwoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ItemMainList0Binding

    }

    class ThreeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ItemMainList1Binding

    }


    class FourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ItemMainList2Binding

    }
}