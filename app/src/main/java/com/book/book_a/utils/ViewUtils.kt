package com.book.book_a.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import me.wcy.htmltext.HtmlImageLoader
import me.wcy.htmltext.HtmlText
import me.wcy.htmltext.OnTagClickListener


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
                    return ContextCompat.getDrawable(context, com.book.book_a.R.mipmap.error_icon)
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
                 fun onImageClick(context: Context?, imageUrlList: MutableList<String>?, position: Int) {
                    // 显示图片
                    //jupActivity(context,position, imageUrlList)
                 }

                 override// 超链接点击事件
                 fun onLinkClick(context: Context, url: String) {
                    // link click
                 }
              })
              // tvInfo 展示富文本的textview控件
              .into(view)
   }


   fun jupActivity(context:Activity,position: Int, list: MutableList<String>) {
      // 图片查看器
//      val picture = PictureConfig.Builder()
//              .setListData(list as ArrayList<String>?)
//              .setPosition(0)
//              .setIsShowNumber(true)
//              .setDownloadPath("pictureviewer")
//              .needDownload(false)// 是否支持图下载
//              .setPlacrHolder(com.book.book_a.R.mipmap.loading)// 占位符
//              .build()
//      ImagePagerActivity.startActivity(context, picture)
   }




}