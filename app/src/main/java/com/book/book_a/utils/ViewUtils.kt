package com.book.book_a.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.book.book_a.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import me.wcy.htmltext.HtmlImageLoader
import me.wcy.htmltext.HtmlText
import me.wcy.htmltext.OnTagClickListener
import com.SuperKotlin.pictureviewer.ImagePagerActivity
import com.SuperKotlin.pictureviewer.PictureConfig


class ViewUtils {

   fun fromText(context:Context,taskVariablesLocal:String,view: TextView) {
      HtmlText.from(taskVariablesLocal).setImageLoader(object : HtmlImageLoader {
                 override fun loadImage(url: String, callback: HtmlImageLoader.Callback) {
                    Glide.with(context)
                            .asBitmap()
                            .load(url)
                            .into(object : SimpleTarget<Bitmap>(){
                                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                    callback.onLoadComplete(resource)
                                }
                            })
                 }

                 override fun getDefaultDrawable(): Drawable? {
                    return null
                 }

                 override fun getErrorDrawable(): Drawable? {
                    return ContextCompat.getDrawable(context, R.mipmap.error_icon)
                 }

                 override fun getMaxWidth(): Int {
                    return  view.width
                 }

                 override fun fitWidth(): Boolean {
                    return true
                 }
              })
              .setOnTagClickListener(object : OnTagClickListener {
                 override//  图片的点击事件
                 fun onImageClick(context: Context, imageUrlList: List<String>, position: Int) {
                    // 显示图片
                    jupActivity(context,position, imageUrlList)
                 }

                 override// 超链接点击事件
                 fun onLinkClick(context: Context, url: String) {
                    // link click
                 }
              })
              // tvInfo 展示富文本的textview控件
              .into(view)
   }


   fun jupActivity(context:Context,position: Int, list: List<String>?) {
      // 图片查看器
      val picture = PictureConfig.Builder()
              .setListData(list as ArrayList<String>)
              .setPosition(position)
              .setIsShowNumber(true)
              .setDownloadPath("PIC")
              .needDownload(true)// 是否支持图下载
              .setPlacrHolder(R.mipmap.loading)// 占位符
              .build()
      ImagePagerActivity.startActivity(context, picture)
   }




}