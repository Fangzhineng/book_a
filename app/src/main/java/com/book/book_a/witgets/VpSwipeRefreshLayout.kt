package com.book.book_a.witgets

import android.content.Context
import android.support.v4.view.MotionEventCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.MotionEvent


class VpSwipeRefreshLayout(context: Context, attrs: AttributeSet?) : SwipeRefreshLayout(context, attrs) {
    var lastx = 0f
    var lasty = 0f
    var ismovepic = false

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {


        if (ev.action == MotionEvent.ACTION_DOWN) {
            lastx = ev.x
            lasty = ev.y
            ismovepic = false
            return super.onInterceptTouchEvent(ev)
        }

        val action = MotionEventCompat.getActionMasked(ev)

        val x2 = Math.abs(ev.x - lastx).toInt()
        val y2 = Math.abs(ev.y - lasty).toInt()

        //滑动图片最小距离检查
        if (x2 > y2) {
            if (x2 >= 100) ismovepic = true
            return false
        }

        //是否移动图片(下拉刷新不处理)
        if (ismovepic) {
            return false
        }

        return super.onInterceptTouchEvent(ev)
    }
}