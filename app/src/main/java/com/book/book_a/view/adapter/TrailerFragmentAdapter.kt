package com.book.book_a.view.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.book.book_a.R
import com.book.book_a.databinding.ItemMainList5Binding
import com.book.book_a.model.Trailer
import com.book.book_a.model.TrailerBody
import com.bumptech.glide.Glide
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard

class TrailerFragmentAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = arrayListOf<TrailerBody>()

    private var trailer: Trailer? = null

    fun setBean(
            list: ArrayList<TrailerBody>,
            trailer: Trailer?
    ) {
        this.list = list
        this.trailer = trailer
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val binding: ItemMainList5Binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_main_list_5, p0, false)
        val holder: OneViewHolder = OneViewHolder(binding.root)
        holder.binding = binding
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list.size != 0){
            (holder as OneViewHolder).binding.tvTitle.text = list[position].videoTitle
            var typeV :String ?=  null
            for (index in list[position].type){
                if (typeV.isNullOrEmpty()){
                    typeV = index
                }else{
                    typeV += "$index,"
                }
            }

            if (list[position].type.size > 1){
                typeV?.substring(0,typeV.length - 1)
            }

            holder.binding.tvType.text = typeV
            Glide.with(context).load(list[position].coverImg).into(holder.binding.ivTop)

            holder.binding.ivTop.setOnClickListener {
                JCVideoPlayerStandard.startFullscreen(context, JCVideoPlayerStandard::class.java, list[position].hightUrl, "")
            }
        }
    }

    class OneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ItemMainList5Binding
    }

}