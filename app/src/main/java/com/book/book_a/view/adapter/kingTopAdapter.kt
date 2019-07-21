package com.book.book_a.view.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.book.book_a.R
import com.book.book_a.databinding.ItemGridTopBinding

class kingTopAdapter(val context: Context) : RecyclerView.Adapter<kingTopAdapter.ViewHolder>() {

    private val titles  = arrayOf("西瓜top100","华语top100","全球票房榜")

    private val tops  = arrayOf(R.mipmap.top01, R.mipmap.top02, R.mipmap.top03)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): kingTopAdapter.ViewHolder {
        val binding:ItemGridTopBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.item_grid_top,p0,false)
        val holder:ViewHolder = ViewHolder(binding.root)
        holder.binding=binding
        return holder
    }


    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: kingTopAdapter.ViewHolder, p1: Int) {
        holder.binding.tvTop.text = titles[p1]
        holder.binding.ivIcon.setImageResource(tops[p1])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ItemGridTopBinding
    }
}