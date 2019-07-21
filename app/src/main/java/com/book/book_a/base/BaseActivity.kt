package com.book.book_a.base

import android.databinding.ViewDataBinding
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import com.book.book_a.R
import com.book.book_a.utils.Utils


/**
 * Created by Jin on 10/15/2018
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    private var mSnackBar: Snackbar? = null

    lateinit var binding: T

    val utils = Utils()

    var daoHangHeight: Int = 0

    private var mSavedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityManager().addActivity(this)

        binding = setCreateView()
        mSavedInstanceState = savedInstanceState

        //utils.setWindowNo(this)

        //状态栏透明和间距处理
        //StatusBarUtil.immersive(this,resources.getColor(R.color.common_blue))

        daoHangHeight = utils.getDaoHangHeight(this)

        initView()
        initData()
    }

    fun getBundle(): Bundle? {
        return mSavedInstanceState
    }

    abstract fun setCreateView(): T

    abstract fun initView()

    abstract fun initData()



    //snackBar 弹出框
    fun snack(view: View, text: String, actionTextId: Int = 0, onClickListener: View.OnClickListener? = null) {
        val ssb = SpannableStringBuilder()
                .append(text)
        ssb.setSpan(
                ForegroundColorSpan(Color.WHITE),
                0,
                text.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        mSnackBar = Snackbar.make(view, ssb, Snackbar.LENGTH_INDEFINITE)
        mSnackBar?.view?.setBackgroundResource(R.color.common_red)
        if (actionTextId != 0) {
            mSnackBar?.setAction(actionTextId, onClickListener)
            mSnackBar?.setActionTextColor(Color.WHITE)
        }
        mSnackBar?.show()
    }

    fun dismissSnack() {
        if (mSnackBar?.isShown == true) {
            mSnackBar?.dismiss()
        }
    }
}