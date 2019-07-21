package com.book.book_a.http

import java.io.IOException


interface ResultCallBack {

    /***
     * 从服务器获取数据成功
     *
     * @param response 返回内容
     * @param flag     标识
     */
    fun onSuccess(response: String, flag: Int)


    /***
     * 从服务器获取数据失败
     *
     * @param e
     * @param flag    标识
     */
    fun onFailure(response: String?, e: IOException?, flag: Int)


}
