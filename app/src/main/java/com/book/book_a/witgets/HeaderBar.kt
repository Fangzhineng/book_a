package com.book.book_a.witgets

import android.content.Context
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.book.book_a.R
import com.book.book_a.ext.onClick
import com.book.book_a.base.ActivityManager
import kotlinx.android.synthetic.main.layout_header_bar.view.*


/**
 * Created by Jin on 10/15/2018
 */

class HeaderBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    //是否显示"返回"图标
    private var isShowBack = true
    //Title文字
    private var titleText: String? = null
    //右侧文字
    private var rightText: String? = null

    //右侧是否显示
    private var isShowRight =false

    //背景颜色
    private var backgroud: Int = 0

    //Title颜色
    private var titleColor: Int = 0

    init {
        //获取自定义属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar)

        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBack, true)

        isShowRight = typedArray.getBoolean(R.styleable.HeaderBar_isShowRight, false)


        titleText = typedArray.getString(R.styleable.HeaderBar_titleText)

        rightText = typedArray.getString(R.styleable.HeaderBar_rightText)

        backgroud = typedArray.getColor(R.styleable.HeaderBar_bar_background, context.resources.getColor(R.color.common_blue))

        titleColor = typedArray.getColor(R.styleable.HeaderBar_titleColor, context.resources.getColor(R.color.common_white))

        initView()
        typedArray.recycle()
    }

    /*
        初始化视图
     */
    private fun initView() {
        View.inflate(context, R.layout.layout_header_bar, this)

        mLeftIv.visibility = if (isShowBack) View.VISIBLE else View.GONE

        mRightTv.visibility = if (isShowRight) View.VISIBLE else View.GONE

        //标题不为空，设置值
        titleText?.let {
            mTitleTv.text = it
        }

        //右侧文字不为空，设置值
        rightText?.let {
            mRightTv.text = it
        }

        titleColor.let {
            mTitleTv.setTextColor(it)
        }

        backgroud.let {
            rl_all.setBackgroundColor(it)
        }

        //返回图标默认实现（关闭Activity）
        mLeftIv.onClick {
            ActivityManager().finishActivity()
        }


    }


    /*
        获取左侧视图
     */
    fun getLeftView(): ImageView {
        return mLeftIv
    }


    /*
        获取右侧视图
     */
    fun getRightView(): TextView {
        return mRightTv
    }


    fun getCenterTitle():TextView{
        return mTitleTv
    }



}

