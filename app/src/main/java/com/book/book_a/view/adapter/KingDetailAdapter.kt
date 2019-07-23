package com.book.book_a.view.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.book.book_a.R
import com.book.book_a.databinding.ItemKingDetailListBinding
import com.book.book_a.model.Movy
import com.book.book_a.model.TopList1
import com.bumptech.glide.Glide
import java.util.*

class KingDetailAdapter(val context: Context) : RecyclerView.Adapter<KingDetailAdapter.ViewHolder>() {

    private var list = arrayListOf<Movy>()

    private var bean: TopList1 ?= null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): KingDetailAdapter.ViewHolder {
        val binding:ItemKingDetailListBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_king_detail_list,p0,false)
        val holder:ViewHolder = ViewHolder(binding.root)
        holder.binding =binding
        return holder
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: KingDetailAdapter.ViewHolder, i: Int) {
        if (list.size != 0) {
            if (i == 0) {
                holder.binding.ll0.visibility = View.VISIBLE
                holder.binding.tvTitle.text = bean?.name
                holder.binding.tvTitle2.text = bean?.summary
            } else {
                holder.binding.ll0.visibility = View.GONE
            }
            holder.binding.tvTitleList.text = list[i].name
            holder.binding.tvEn.text = list[i].nameEn
            holder.binding.tvDaoyan.text = "导演: "+list[i].director

            if (list[i].actor2.isNullOrEmpty()){
                holder.binding.tvZhuyan.text ="主演: "+list[i].actor
            }else{
                holder.binding.tvZhuyan.text ="主演: "+list[i].actor + " " +list[i].actor2
            }


            holder.binding.tvTimeAddress.text = list[i].releaseDate + " "+ list[i].releaseLocation
            Glide.with(context).load(list[i].posterUrl).into(holder.binding.ivOne)
            holder.binding.tvContent.text = list[i].remark
            holder.binding.tvRating.text = list[i].rating.toString()
            when(i){
                0-> {
                    holder.binding.tvKing.setBackgroundResource(R.drawable.bg_red_c)
                    holder.binding.tvKing.text = "1"
                }
                1-> {
                    holder.binding.tvKing.setBackgroundResource(R.drawable.bg_red_g)
                    holder.binding.tvKing.text = "2"
                }
                2-> {
                    holder.binding.tvKing.setBackgroundResource(R.drawable.bg_red_b)
                    holder.binding.tvKing.text = "3"
                }
                else-> {
                    holder.binding.tvKing.setBackgroundResource(R.drawable.bg_red_gray)
                    holder.binding.tvKing.text = (i+1).toString()
                }
            }
        }
    }

    fun setData(list: ArrayList<Movy> , bean:TopList1) {
        this.list = list
        this.bean=bean
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding:ItemKingDetailListBinding
    }
}