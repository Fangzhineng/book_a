package com.book.book_a.application

import android.app.Application


/**
 * Created by Jin on 10/15/2018
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Instance = this
    }

    companion object {
        @JvmStatic
        lateinit var Instance: App

        init {
            //启用矢量图兼容
        }
    }

}