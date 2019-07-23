package com.book.book_a.view.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.book.book_a.R
import com.book.book_a.canstants.CustomCanstant.Companion.ViewOne
import com.book.book_a.canstants.CustomCanstant.Companion.ViewThree
import com.book.book_a.canstants.CustomCanstant.Companion.ViewTwo
import com.book.book_a.databinding.ItemKingListBinding
import com.book.book_a.databinding.ItemKingTopBinding
import com.book.book_a.databinding.ItemMainList3Binding
import com.book.book_a.model.News
import com.book.book_a.model.TopList
import com.book.book_a.model.TopLists
import com.book.book_a.view.activity.KingDetailActivity
import com.bumptech.glide.Glide

class KingFragmentAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    private var list: List<TopLists> = arrayListOf()

    private var news:TopList ?= null

    fun setList(mList: List<TopLists>,news: TopList) {
        this.list = mList
        this.news = news
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        when (p1) {
            ViewOne -> {
                val binding: ItemMainList3Binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_main_list_3,p0,false)
                val holder: OneViewHodler = OneViewHodler(binding.root)
                holder.binding = binding
                return holder
            }
            ViewTwo -> {
                val binding:ItemKingTopBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.item_king_top,p0,false)
                val holder: TwoViewHodler = TwoViewHodler(binding.root)
                holder.binding = binding
                return holder
            }
            else -> {
                val binding:ItemKingListBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.item_king_list,p0,false)
                val holder: ThreeViewHodler = ThreeViewHodler(binding.root)
                holder.binding = binding
                return holder
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size + 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, i: Int) {
        when (i) {
            0 -> {
                if (news != null) {
                    (holder as OneViewHodler).binding.tvTitle.text = news!!.title
                    Glide.with(context).load(news!!.imageUrl).into(holder.binding.ivTop)
                    holder.binding.item.setOnClickListener {
                        KingDetailActivity().startActivity(context,"2",news!!.id)
                    }
                }
            }
            1 -> {
                (holder as TwoViewHodler).binding.recycler.layoutManager = GridLayoutManager(context,3)
                holder.binding.recycler.adapter = kingTopAdapter(context)
            }
            else -> {
                val position = i -2
                (holder as ThreeViewHodler).binding.tvTitle.text = list[position].topListNameCn
                holder.binding.tvTitle2.text = list[position].summary

                holder.binding.item.setOnClickListener {
                    KingDetailActivity().startActivity(context,"2", list[position].id)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ViewOne
            1 -> ViewTwo
            else -> ViewThree
        }

    }


    class OneViewHodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ItemMainList3Binding
    }

    class TwoViewHodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ItemKingTopBinding
    }

    class ThreeViewHodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ItemKingListBinding
    }
}