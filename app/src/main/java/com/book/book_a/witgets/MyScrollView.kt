package com.book.book_a.witgets

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

//使用自定义view继承自ScrollView
class MyScrollView : ScrollView {

    private var listener: OnScrollListener? = null

    fun setOnScrollListener(listener: OnScrollListener) {
        this.listener = listener
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    //设置接口
    interface OnScrollListener {
        fun onScroll(scrollY: Int)
    }

    //重写原生onScrollChanged方法，将参数传递给接口，由接口传递出去
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (listener != null) {

            //这里我只传了垂直滑动的距离
            listener!!.onScroll(t)
        }
    }

    companion object {

        //将像素转换为px
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        //将px转换为dp
        fun px2dp(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }
    }
}