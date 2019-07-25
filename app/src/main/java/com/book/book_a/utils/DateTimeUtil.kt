package com.book.book_a.utils

import android.annotation.SuppressLint

import java.text.SimpleDateFormat
import java.util.Date



/**
 * Created by admin on 2019/1/14.
 */

class DateTimeUtil {

    /**
     * 获取系统时间戳
     * @return
     */
    fun getCurTimeLong():Long{
        return System.currentTimeMillis()
    }
    /**
     * 获取当前时间
     * @param pattern
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun  getCurDate(pattern:String):String{
        return  SimpleDateFormat(pattern).format(Date())
    }

    /**
     * 时间戳转换成字符窜
     * @param milSecond
     * @param pattern
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getDateToString(milSecond:Long, pattern: String ) :String{
        return SimpleDateFormat(pattern).format(Date(milSecond))
    }

    /**
     * 将字符串转为时间戳
     * @param dateString
     * @param pattern
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getStringToDate(dateString:String, pattern:String ):Long {
        return SimpleDateFormat(pattern).parse(dateString).time
    }
}
