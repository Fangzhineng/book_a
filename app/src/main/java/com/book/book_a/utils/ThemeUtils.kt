package com.book.book_a.utils

import android.content.Context
import com.book.book_a.R

/**
 * User:Shine
 * Date:2015-11-20
 * Description:
 */
object ThemeUtils {

    private val APPCOMPAT_CHECK_ATTRS = intArrayOf(R.attr.colorPrimary)

    fun checkAppCompatTheme(context: Context) {
        val a = context.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS)
        val failed = !a!!.hasValue(0)
        a.recycle()
        if (failed) {
            throw IllegalArgumentException("You need to use a Theme.AppCompat theme " + "(or descendant) with the design library.")
        }
    }
}
