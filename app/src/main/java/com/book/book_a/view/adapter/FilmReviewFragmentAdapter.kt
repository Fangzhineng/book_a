package com.book.book_a.view.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.book.book_a.R
import com.book.book_a.canstants.CustomCanstant.Companion.ViewOne
import com.book.book_a.canstants.CustomCanstant.Companion.ViewTwo
import com.book.book_a.databinding.ItemMainList0Binding
import com.book.book_a.databinding.ItemMainList2Binding
import com.book.book_a.databinding.ItemMainList3Binding
import com.book.book_a.databinding.ItemMainList4Binding
import com.book.book_a.model.Review
import com.book.book_a.model.ReviewBean
import com.book.book_a.view.activity.MainReviewDetailActivity
import com.book.book_a.view.activity.MainTopDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class FilmReviewFragmentAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = arrayListOf<ReviewBean>()

    private var review: Review? = null

    fun setBean(
            list: ArrayList<ReviewBean>,
            review: Review?
    ) {
        this.list = list
        this.review = review
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewOne -> {
                val binding: ItemMainList3Binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_main_list_3, p0, false)
                val holder: OneViewHolder = OneViewHolder(binding.root)
                holder.binding = binding
                holder
            }
            else -> {
                val binding: ItemMainList4Binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_main_list_4, p0, false)
                val holder: TwoViewHolder = TwoViewHolder(binding.root)
                holder.binding = binding
                holder
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) {
            if (review != null) {
                (holder as OneViewHolder).binding.tvTitle.text = review?.title
                Glide.with(context).load(review?.posterUrl).into(holder.binding.ivTop)

                holder.binding.item.setOnClickListener {
                    MainReviewDetailActivity().startActivity(context, review?.reviewID)
                }
            }
        } else {
            if (list.size != 0) {
                val i = position - 1
                (holder as TwoViewHolder).binding.tvTitle.text = list[i].title
                holder.binding.tvName.text = list[i].nickname
                when (list[i].rating) {
                    "" -> holder.binding.tvRating.visibility = View.GONE
                    else -> {
                        holder.binding.tvRating.visibility = View.VISIBLE
                        holder.binding.tvRating.text = list[i].rating
                    }
                }

                holder.binding.item.setOnClickListener {
                    MainReviewDetailActivity().startActivity(context,list[i].id)
                }

                //设置图片圆角角度
                //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                //val options:RequestOptions =RequestOptions.bitmapTransform(RoundedCorners(6)).override(300, 300)
                Glide.with(context).load(list[i].userImage).apply(RequestOptions.circleCropTransform()).into(holder.binding.ivNick)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ViewOne
            else -> ViewTwo
        }
    }

    class OneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ItemMainList3Binding

    }

    class TwoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ItemMainList4Binding

    }
}